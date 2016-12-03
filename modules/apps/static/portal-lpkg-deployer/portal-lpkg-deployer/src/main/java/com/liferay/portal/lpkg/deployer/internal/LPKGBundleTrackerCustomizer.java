/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.lpkg.deployer.internal;

import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.lpkg.StaticLPKGResolver;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.lpkg.deployer.internal.wrapper.bundle.URLStreamHandlerServiceServiceTrackerCustomizer;
import com.liferay.portal.lpkg.deployer.internal.wrapper.bundle.WARBundleWrapperBundleActivator;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.Version;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.wiring.FrameworkWiring;
import org.osgi.service.url.URLConstants;
import org.osgi.util.tracker.BundleTrackerCustomizer;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
public class LPKGBundleTrackerCustomizer
	implements BundleTrackerCustomizer<List<Bundle>> {

	public LPKGBundleTrackerCustomizer(
		BundleContext bundleContext, Map<String, URL> urls,
		Set<String> overrideFileNames) {

		_bundleContext = bundleContext;
		_urls = urls;
		_overrideFileNames = overrideFileNames;
	}

	@Override
	public List<Bundle> addingBundle(Bundle bundle, BundleEvent bundleEvent) {
		URL url = bundle.getEntry("liferay-marketplace.properties");

		if (url == null) {
			return null;
		}

		String symbolicName = bundle.getSymbolicName();

		if (symbolicName.equals(
				StaticLPKGResolver.getStaticLPKGBundleSymbolicName())) {

			return Collections.emptyList();
		}

		List<Bundle> bundles = new ArrayList<>();

		try {
			Enumeration<URL> enumeration = bundle.findEntries(
				"/", "*.jar", false);

			if (enumeration != null) {
				while (enumeration.hasMoreElements()) {
					url = enumeration.nextElement();

					if (_checkOverridden(symbolicName, url)) {
						continue;
					}

					Bundle newBundle = _bundleContext.installBundle(
						url.getPath(), url.openStream());

					BundleStartLevel bundleStartLevel = newBundle.adapt(
						BundleStartLevel.class);

					bundleStartLevel.setStartLevel(
						PropsValues.
							MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL);

					bundles.add(newBundle);
				}
			}

			enumeration = bundle.findEntries("/", "*.war", false);

			if (enumeration == null) {
				return bundles;
			}

			while (enumeration.hasMoreElements()) {
				url = enumeration.nextElement();

				if (_checkOverridden(symbolicName, url)) {
					continue;
				}

				// Install a wrapper bundle for this WAR bundle. The wrapper
				// bundle defers the WAR bundle installation until the WAB
				// protocol handler is ready. The installed WAR bundle is always
				// tied its wrapper bundle. When the wrapper bundle is
				// uninstalled, its wrapped WAR bundle will also be unintalled.

				Bundle newBundle = _bundleContext.installBundle(
					url.getPath(), _toWARWrapperBundle(bundle, url));

				BundleStartLevel bundleStartLevel = newBundle.adapt(
					BundleStartLevel.class);

				bundleStartLevel.setStartLevel(
					PropsValues.MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL);

				bundles.add(newBundle);
			}

			Bundle systemBundle = _bundleContext.getBundle(0);

			FrameworkWiring frameworkWiring = systemBundle.adapt(
				FrameworkWiring.class);

			final DefaultNoticeableFuture<FrameworkEvent>
				defaultNoticeableFuture = new DefaultNoticeableFuture<>();

			frameworkWiring.refreshBundles(
				null,
				new FrameworkListener() {

					@Override
					public void frameworkEvent(FrameworkEvent frameworkEvent) {
						defaultNoticeableFuture.set(frameworkEvent);
					}

				});

			FrameworkEvent frameworkEvent = defaultNoticeableFuture.get();

			if (frameworkEvent.getType() != FrameworkEvent.PACKAGES_REFRESHED) {
				throw frameworkEvent.getThrowable();
			}
		}
		catch (Throwable t) {
			_log.error("Rollback bundle installation for " + bundles, t);

			for (Bundle newBundle : bundles) {
				try {
					newBundle.uninstall();
				}
				catch (BundleException be) {
					_log.error("Unable to uninstall bundle " + newBundle, be);
				}
			}

			return null;
		}

		return bundles;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent, List<Bundle> bundles) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent, List<Bundle> bundles) {

		if (bundle.getState() != Bundle.UNINSTALLED) {
			return;
		}

		String lpkgBundleSymbolicName = bundle.getSymbolicName();

		String prefix = lpkgBundleSymbolicName.concat(StringPool.DASH);

		for (Bundle newBundle : bundles) {
			try {
				_uninstallBundle(prefix, newBundle);
			}
			catch (BundleException be) {
				_log.error(
					"Unable to uninstall " + newBundle +
						" in response to uninstallation of " + bundle,
					be);
			}
		}
	}

	private String _buildImportPackageString(Class<?>... classes) {
		StringBundler sb = new StringBundler(classes.length * 2);

		for (Class<?> clazz : classes) {
			Package pkg = clazz.getPackage();

			sb.append(pkg.getName());

			sb.append(StringPool.COMMA);
		}

		int index = sb.index();

		if (index > 0) {
			sb.setIndex(index - 1);
		}

		return sb.toString();
	}

	private boolean _checkOverridden(String symbolicName, URL url)
		throws BundleException {

		String path = url.getPath();

		Matcher matcher = _pattern.matcher(path);

		if (matcher.matches()) {
			path = matcher.group(1) + matcher.group(4);
		}

		path = StringUtil.toLowerCase(path);

		if (_overrideFileNames.contains(path)) {
			Bundle bundle = _bundleContext.getBundle(url.getPath());

			if (bundle != null) {
				_uninstallBundle(symbolicName.concat(StringPool.DASH), bundle);
			}

			if (_log.isInfoEnabled()) {
				_log.info("Disabled " + symbolicName + ":" + url.getPath());
			}

			return true;
		}

		return false;
	}

	private String _readServletContextName(URL url) throws IOException {
		String pathString = url.getPath();

		String servletContextName = pathString.substring(
			pathString.lastIndexOf('/') + 1, pathString.lastIndexOf(".war"));

		int index = servletContextName.lastIndexOf('-');

		if (index >= 0) {
			servletContextName = servletContextName.substring(0, index);
		}

		Path tempFilePath = Files.createTempFile(null, null);

		try (InputStream inputStream1 = url.openStream()) {
			Files.copy(
				inputStream1, tempFilePath,
				StandardCopyOption.REPLACE_EXISTING);

			try (ZipFile zipFile = new ZipFile(tempFilePath.toFile());
				InputStream inputStream2 = zipFile.getInputStream(
					new ZipEntry(
						"WEB-INF/liferay-plugin-package.properties"))) {

				if (inputStream2 != null) {
					Properties properties = new Properties();

					properties.load(inputStream2);

					String configuredServletContextName =
						properties.getProperty("servlet-context-name");

					if (configuredServletContextName != null) {
						servletContextName = configuredServletContextName;
					}
				}
			}
		}
		finally {
			Files.delete(tempFilePath);
		}

		return servletContextName;
	}

	private InputStream _toWARWrapperBundle(Bundle bundle, URL url)
		throws IOException {

		StringBundler sb = new StringBundler(7);

		sb.append("lpkg://");
		sb.append(URLCodec.encodeURL(bundle.getSymbolicName()));
		sb.append(StringPool.DASH);
		sb.append(bundle.getVersion());
		sb.append(StringPool.SLASH);

		String servletContextName = _readServletContextName(url);

		sb.append(servletContextName);

		sb.append(".war");

		String lpkgURL = sb.toString();

		// The bundle URL changes after a reboot. To ensure we do not install
		// the same bundle multiple times over reboots, we must map the ever
		// changing bundle URL to a fixed LPKG URL.

		_urls.put(lpkgURL, url);

		String pathString = url.getPath();

		String fileName = pathString.substring(
			pathString.lastIndexOf('/') + 1, pathString.lastIndexOf(".war"));

		String version = String.valueOf(bundle.getVersion());

		int index = fileName.lastIndexOf('-');

		if (index >= 0) {
			version = fileName.substring(index + 1);
		}

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				_writeManifest(
					bundle, servletContextName, version, lpkgURL,
					jarOutputStream);

				_writeClasses(
					jarOutputStream, WARBundleWrapperBundleActivator.class,
					URLStreamHandlerServiceServiceTrackerCustomizer.class);
			}

			return new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}
	}

	private void _uninstallBundle(String prefix, Bundle bundle)
		throws BundleException {

		String symbolicName = bundle.getSymbolicName();

		if (symbolicName.startsWith(prefix) &&
			symbolicName.endsWith("-wrapper")) {

			String wrappedBundleSymbolicName = symbolicName.substring(
				prefix.length(), symbolicName.length() - 8);

			Version version = bundle.getVersion();

			for (Bundle curBundle : _bundleContext.getBundles()) {
				if (wrappedBundleSymbolicName.equals(
						curBundle.getSymbolicName()) &&
					version.equals(curBundle.getVersion())) {

					curBundle.uninstall();
				}
			}
		}

		bundle.uninstall();
	}

	private void _writeClasses(
			JarOutputStream jarOutputStream, Class<?>... classes)
		throws IOException {

		for (Class<?> clazz : classes) {
			String className = clazz.getName();

			String path = StringUtil.replace(
				className, CharPool.PERIOD, CharPool.SLASH);

			String resourcePath = path.concat(".class");

			jarOutputStream.putNextEntry(new ZipEntry(resourcePath));

			ClassLoader classLoader = clazz.getClassLoader();

			StreamUtil.transfer(
				classLoader.getResourceAsStream(resourcePath), jarOutputStream,
				false);

			jarOutputStream.closeEntry();
		}
	}

	private void _writeManifest(
			Bundle bundle, String contextName, String version, String lpkgURL,
			JarOutputStream jarOutputStream)
		throws IOException {

		Manifest manifest = new Manifest();

		Attributes attributes = manifest.getMainAttributes();

		attributes.putValue(
			Constants.BUNDLE_ACTIVATOR,
			WARBundleWrapperBundleActivator.class.getName());
		attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
		attributes.putValue(
			Constants.BUNDLE_SYMBOLICNAME,
			bundle.getSymbolicName() + "-" + contextName + "-wrapper");

		attributes.putValue(Constants.BUNDLE_VERSION, version);
		attributes.putValue(
			Constants.IMPORT_PACKAGE,
			_buildImportPackageString(
				BundleActivator.class, BundleStartLevel.class,
				ServiceTrackerCustomizer.class, URLConstants.class));
		attributes.putValue("Liferay-WAB-Context-Name", contextName);
		attributes.putValue("Liferay-WAB-LPKG-URL", lpkgURL);
		attributes.putValue(
			"Liferay-WAB-Start-Level",
			String.valueOf(
				PropsValues.MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL));
		attributes.putValue("Manifest-Version", "2");

		jarOutputStream.putNextEntry(new ZipEntry(JarFile.MANIFEST_NAME));

		manifest.write(jarOutputStream);

		jarOutputStream.closeEntry();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LPKGBundleTrackerCustomizer.class);

	private static final Pattern _pattern = Pattern.compile(
		"/(.*?)(-\\d+\\.\\d+\\.\\d+)(\\..+)?(\\.[jw]ar)");

	private final BundleContext _bundleContext;
	private final Set<String> _overrideFileNames;
	private final Map<String, URL> _urls;

}