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

import com.liferay.osgi.util.bundle.BundleStartLevelUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.lpkg.StaticLPKGResolver;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lpkg.deployer.internal.wrapper.bundle.URLStreamHandlerServiceServiceTrackerCustomizer;
import com.liferay.portal.lpkg.deployer.internal.wrapper.bundle.WARBundleWrapperBundleActivator;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
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
		if (bundle.getEntry(_MARKER_FILE) != null) {
			try {
				bundle.uninstall();
			}
			catch (BundleException be) {
				_log.error("Unable to uninstall LPKG " + bundle, be);
			}

			return null;
		}

		try {
			Properties properties = _readMarketplaceProperties(bundle);

			if (properties == null) {
				return null;
			}

			if (_outdatedRemoteAppIds.contains(
					properties.getProperty("remote-app-id"))) {

				_processOutdatedBundle(bundle);

				return null;
			}

			String supersedesRemoteAppIds = properties.getProperty(
				"supersedes-remote-app-ids");

			if (supersedesRemoteAppIds != null) {
				Collections.addAll(
					_outdatedRemoteAppIds,
					StringUtil.split(supersedesRemoteAppIds, StringPool.COMMA));

				for (Bundle installedBundle : _bundleContext.getBundles()) {
					properties = _readMarketplaceProperties(installedBundle);

					if (properties == null) {
						continue;
					}

					if (_outdatedRemoteAppIds.contains(
							properties.getProperty("remote-app-id"))) {

						_processOutdatedBundle(installedBundle);
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to determine if LPKG " + bundle + " is outdated", e);
		}

		String symbolicName = bundle.getSymbolicName();

		if (_staticLPKGBundleSymbolicNames.contains(symbolicName)) {
			return Collections.emptyList();
		}

		List<Bundle> bundles = new ArrayList<>();

		try {
			List<Bundle> installedBundles = new ArrayList<>();

			Enumeration<URL> enumeration = bundle.findEntries(
				"/", "*.jar", false);

			if (enumeration != null) {
				while (enumeration.hasMoreElements()) {
					URL url = enumeration.nextElement();

					String location =
						LPKGInnerBundleLocationUtil.generateInnerBundleLocation(
							bundle, url.getPath());

					if (_isOverridden(symbolicName, url, location)) {
						continue;
					}

					if (_isBundleInstalled(bundle, url, location)) {
						continue;
					}

					Bundle newBundle = _bundleContext.getBundle(location);

					if (newBundle != null) {
						bundles.add(newBundle);

						continue;
					}

					newBundle = _bundleContext.installBundle(
						location, url.openStream());

					if (newBundle.getState() == Bundle.UNINSTALLED) {
						continue;
					}

					installedBundles.add(newBundle);

					bundles.add(newBundle);
				}
			}

			enumeration = bundle.findEntries("/", "*.war", false);

			if (enumeration != null) {
				while (enumeration.hasMoreElements()) {
					URL url = enumeration.nextElement();

					String location =
						LPKGInnerBundleLocationUtil.generateInnerBundleLocation(
							bundle, url.getPath());

					if (_isOverridden(symbolicName, url, location)) {
						continue;
					}

					Bundle newBundle = _bundleContext.getBundle(location);

					if (newBundle != null) {
						bundles.add(newBundle);

						continue;
					}

					// Install a wrapper bundle for this WAR bundle. The wrapper
					// bundle defers the WAR bundle installation until the WAB
					// protocol handler is ready. The installed WAR bundle is
					// always tied its wrapper bundle. When the wrapper bundle
					// is uninstalled, its wrapped WAR bundle will also be
					// unintalled.

					newBundle = _bundleContext.installBundle(
						location, _toWARWrapperBundle(bundle, url));

					if (newBundle.getState() == Bundle.UNINSTALLED) {
						continue;
					}

					bundles.add(newBundle);

					installedBundles.add(newBundle);
				}
			}

			for (Bundle installedBundle : installedBundles) {
				BundleStartLevelUtil.setStartLevelAndStart(
					installedBundle,
					PropsValues.MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL,
					_bundleContext);
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

		if ((bundle.getState() != Bundle.RESOLVED) ||
			(bundleEvent.getType() != BundleEvent.RESOLVED)) {

			return;
		}

		try {
			List<Bundle> newBundles = addingBundle(bundle, bundleEvent);

			if (newBundles != null) {
				bundles.removeAll(newBundles);
			}

			for (Bundle installedBundle : bundles) {
				if (installedBundle.getState() != Bundle.UNINSTALLED) {
					installedBundle.uninstall();

					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Uninstalled ", String.valueOf(installedBundle),
								"because ", String.valueOf(bundle),
								" was updated"));
					}
				}
			}

			bundles.clear();

			if (newBundles != null) {
				bundles.addAll(newBundles);
			}

			for (Bundle installedBundle : bundles) {
				if (installedBundle.getState() == Bundle.RESOLVED) {
					installedBundle.start();
				}
			}
		}
		catch (Exception e) {
			_log.error("Rollback bundle refresh for " + bundles, e);

			for (Bundle newBundle : bundles) {
				try {
					newBundle.uninstall();
				}
				catch (BundleException be) {
					_log.error("Unable to uninstall bundle " + newBundle, be);
				}
			}
		}
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
			catch (Throwable t) {
				_log.error(
					StringBundler.concat(
						"Unable to uninstall ", String.valueOf(newBundle),
						" in response to uninstallation of ",
						String.valueOf(bundle)),
					t);
			}
		}
	}

	private static Properties _readMarketplaceProperties(Bundle bundle)
		throws IOException {

		URL url = bundle.getEntry("liferay-marketplace.properties");

		if (url == null) {
			return null;
		}

		try (InputStream in = url.openStream()) {
			Properties properties = new Properties();

			properties.load(in);

			return properties;
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

	private boolean _isBundleInstalled(Bundle bundle, URL url, String location)
		throws IOException {

		try (InputStream inputStream = url.openStream();
			JarInputStream jarInputStream = new JarInputStream(inputStream)) {

			Manifest manifest = jarInputStream.getManifest();

			Attributes attributes = manifest.getMainAttributes();

			String symbolicName = attributes.getValue(
				Constants.BUNDLE_SYMBOLICNAME);

			Version version = new Version(
				attributes.getValue(Constants.BUNDLE_VERSION));

			for (Bundle installedBundle : _bundleContext.getBundles()) {
				if (symbolicName.equals(installedBundle.getSymbolicName()) &&
					version.equals(installedBundle.getVersion()) &&
					!location.equals(installedBundle.getLocation())) {

					if (_log.isInfoEnabled()) {
						StringBundler sb = new StringBundler(7);

						sb.append("Skipping installation of ");
						sb.append(symbolicName);
						sb.append(" with version ");
						sb.append(version.toString());
						sb.append(" in ");
						sb.append(bundle.getSymbolicName());
						sb.append(" because an identical bundle exists");

						_log.info(sb.toString());
					}

					return true;
				}
			}
		}

		return false;
	}

	private boolean _isOverridden(String symbolicName, URL url, String location)
		throws Throwable {

		String path = url.getPath();

		Matcher matcher = _pattern.matcher(path);

		if (matcher.matches()) {
			path = matcher.group(1) + matcher.group(3);
		}

		path = StringUtil.toLowerCase(path);

		if (_overrideFileNames.contains(path)) {
			Bundle bundle = _bundleContext.getBundle(location);

			if (bundle != null) {
				_uninstallBundle(symbolicName.concat(StringPool.DASH), bundle);
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Disabled ", symbolicName, ":", url.getPath()));
			}

			return true;
		}

		return false;
	}

	private void _processOutdatedBundle(Bundle bundle) throws Exception {
		Path path = Paths.get(bundle.getLocation());

		try (FileSystem fileSystem = FileSystems.newFileSystem(path, null)) {
			Files.createFile(fileSystem.getPath(_MARKER_FILE));
		}

		if (_log.isInfoEnabled()) {
			_log.info("Uninstalling outdated bundle " + bundle);
		}

		bundle.uninstall();
	}

	private String[] _readServletContextNameAndPortalProfileNames(URL url)
		throws IOException {

		String pathString = url.getPath();

		String servletContextName = pathString.substring(
			pathString.lastIndexOf('/') + 1, pathString.lastIndexOf(".war"));

		int index = servletContextName.lastIndexOf('-');

		if (index >= 0) {
			servletContextName = servletContextName.substring(0, index);
		}

		String portalProfileNames = null;

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

					portalProfileNames = properties.getProperty(
						"liferay-portal-profile-names");
				}
			}
		}
		finally {
			Files.delete(tempFilePath);
		}

		return new String[] {servletContextName, portalProfileNames};
	}

	private InputStream _toWARWrapperBundle(Bundle bundle, URL url)
		throws IOException {

		StringBundler sb = new StringBundler(10);

		sb.append("lpkg:/");
		sb.append(URLCodec.encodeURL(bundle.getSymbolicName()));
		sb.append(StringPool.DASH);
		sb.append(bundle.getVersion());
		sb.append(StringPool.SLASH);

		String[] servletContextNameAndPortalProfileNames =
			_readServletContextNameAndPortalProfileNames(url);

		String servletContextName = servletContextNameAndPortalProfileNames[0];

		sb.append(servletContextName);

		sb.append(".war");

		String portalProfileNames = servletContextNameAndPortalProfileNames[1];

		if (Validator.isNotNull(portalProfileNames)) {
			sb.append(StringPool.QUESTION);
			sb.append("liferay-portal-profile-names=");
			sb.append(portalProfileNames);
		}

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
					jarOutputStream, BundleStartLevelUtil.class,
					WABWrapperUtil.class, WARBundleWrapperBundleActivator.class,
					URLStreamHandlerServiceServiceTrackerCustomizer.class);
			}

			return new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}
	}

	private void _uninstallBundle(String prefix, Bundle bundle)
		throws Throwable {

		if (bundle.getState() == Bundle.UNINSTALLED) {
			return;
		}

		String symbolicName = bundle.getSymbolicName();

		Set<Bundle> uninstalledBundles = new HashSet<>();

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (symbolicName.startsWith(prefix) &&
			Boolean.valueOf(headers.get("Wrapper-Bundle"))) {

			String wrappedBundleSymbolicName = symbolicName.substring(
				prefix.length(), symbolicName.length() - 8);

			Version version = bundle.getVersion();

			for (Bundle curBundle : _bundleContext.getBundles()) {
				if (wrappedBundleSymbolicName.equals(
						curBundle.getSymbolicName()) &&
					version.equals(curBundle.getVersion())) {

					curBundle.uninstall();

					uninstalledBundles.add(curBundle);
				}
			}
		}

		bundle.uninstall();

		uninstalledBundles.add(bundle);

		Bundle systemBundle = _bundleContext.getBundle(0);

		FrameworkWiring frameworkWiring = systemBundle.adapt(
			FrameworkWiring.class);

		final DefaultNoticeableFuture<FrameworkEvent> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		frameworkWiring.refreshBundles(
			uninstalledBundles,
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
			StringBundler.concat(
				bundle.getSymbolicName(), "-", contextName, "-wrapper"));

		attributes.putValue(Constants.BUNDLE_VERSION, version);
		attributes.putValue(
			Constants.IMPORT_PACKAGE,
			_buildImportPackageString(
				BundleActivator.class, BundleStartLevel.class,
				ServiceTrackerCustomizer.class, StringBundler.class,
				URLConstants.class));
		attributes.putValue("Liferay-WAB-Context-Name", contextName);
		attributes.putValue("Liferay-WAB-LPKG-URL", lpkgURL);
		attributes.putValue(
			"Liferay-WAB-Start-Level",
			String.valueOf(
				PropsValues.MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL));
		attributes.putValue("Manifest-Version", "2");
		attributes.putValue("Wrapper-Bundle", "true");

		jarOutputStream.putNextEntry(new ZipEntry(JarFile.MANIFEST_NAME));

		manifest.write(jarOutputStream);

		jarOutputStream.closeEntry();
	}

	private static final String _MARKER_FILE = ".lfr-outdated";

	private static final Log _log = LogFactoryUtil.getLog(
		LPKGBundleTrackerCustomizer.class);

	private static final Pattern _pattern = Pattern.compile(
		"/(.*?)-\\d+\\.\\d+\\.\\d+(\\..+)?(\\.[jw]ar)");
	private static final List<String> _staticLPKGBundleSymbolicNames =
		StaticLPKGResolver.getStaticLPKGBundleSymbolicNames();

	private final BundleContext _bundleContext;
	private final Set<String> _outdatedRemoteAppIds = new HashSet<>();
	private final Set<String> _overrideFileNames;
	private final Map<String, URL> _urls;

}