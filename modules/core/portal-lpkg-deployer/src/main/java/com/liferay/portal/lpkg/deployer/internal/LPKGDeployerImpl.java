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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;
import com.liferay.portal.lpkg.deployer.LPKGVerifier;
import com.liferay.portal.lpkg.deployer.LPKGWARBundleRegistry;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = LPKGDeployer.class)
public class LPKGDeployerImpl implements LPKGDeployer {

	@Activate
	public void activate(final BundleContext bundleContext) throws IOException {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			URLConstants.URL_HANDLER_PROTOCOL, new String[] {"lpkg"});

		bundleContext.registerService(
			URLStreamHandlerService.class.getName(),
			new LPKGURLStreamHandlerService(_urls), properties);

		_warWrapperBundlerTracker = new BundleTracker<>(
			bundleContext, ~Bundle.UNINSTALLED,
			new WARWrapperBundleTrackCustomizer(_lpkgWarBundleRegistry));

		_warWrapperBundlerTracker.open();

		_lpkgBundleTracker = new BundleTracker<>(
			bundleContext, ~Bundle.UNINSTALLED,
			new LPKGBundleTrackerCustomizer(bundleContext, _urls));

		_lpkgBundleTracker.open();

		String deploymentDir = GetterUtil.getString(
			bundleContext.getProperty("lpkg.deployer.dir"),
			PropsValues.MODULE_FRAMEWORK_BASE_DIR + "/marketplace");

		Path deploymentDirPath = Paths.get(deploymentDir);

		Files.createDirectories(deploymentDirPath);

		Files.walkFileTree(
			deploymentDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					Path fileNamePath = filePath.getFileName();

					String fileName = StringUtil.toLowerCase(
						fileNamePath.toString());

					if (!fileName.endsWith(".lpkg")) {
						return FileVisitResult.CONTINUE;
					}

					try {
						List<Bundle> bundles = deploy(
							bundleContext, filePath.toFile());

						for (Bundle bundle : bundles) {
							Dictionary<String, String> headers =
								bundle.getHeaders();

							String fragmentHost = headers.get(
								Constants.FRAGMENT_HOST);

							if (fragmentHost != null) {
								continue;
							}

							try {
								bundle.start();
							}
							catch (BundleException be) {
								_log.error(
									"Unable to start " + bundle + " for " +
										filePath,
									be);
							}
						}
					}
					catch (Exception e) {
						_log.error("Unable to deploy LPKG file " + filePath, e);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	@Override
	public List<Bundle> deploy(BundleContext bundleContext, File lpkgFile)
		throws IOException {

		for (Bundle bundle : _lpkgVerifier.verify(lpkgFile)) {
			try {
				bundle.uninstall();

				if (_log.isInfoEnabled()) {
					_log.info(
						"Uninstalled older LPKG bundle " + bundle +
							" in order to install " + lpkgFile);
				}

				String location = bundle.getLocation();

				if (!location.equals(lpkgFile.getCanonicalPath()) &&
					Files.deleteIfExists(Paths.get(bundle.getLocation())) &&
					_log.isInfoEnabled()) {

					_log.info(
						"Removed old LPKG bundle " + bundle.getLocation());
				}
			}
			catch (BundleException be) {
				_log.error(
					"Unable to uninstall " + bundle + " in order to install " +
						lpkgFile,
					be);
			}
		}

		try {
			List<Bundle> bundles = new ArrayList<>();

			Bundle lpkgBundle = bundleContext.installBundle(
				lpkgFile.getCanonicalPath(), toBundle(lpkgFile));

			BundleStartLevel bundleStartLevel = lpkgBundle.adapt(
				BundleStartLevel.class);

			bundleStartLevel.setStartLevel(
				PropsValues.MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL);

			bundles.add(lpkgBundle);

			List<Bundle> newBundles = _lpkgBundleTracker.getObject(lpkgBundle);

			if (newBundles != null) {
				bundles.addAll(newBundles);
			}

			return bundles;
		}
		catch (BundleException be) {
			throw new IOException(be);
		}
	}

	@Override
	public Map<Bundle, List<Bundle>> getDeployedLPKGBundles() {
		return _lpkgBundleTracker.getTracked();
	}

	@Override
	public InputStream toBundle(File lpkgFile) throws IOException {
		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (ZipFile zipFile = new ZipFile(lpkgFile);
				JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				_writeManifest(zipFile, jarOutputStream);

				Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();

				while (zipEntries.hasMoreElements()) {
					ZipEntry zipEntry = zipEntries.nextElement();

					jarOutputStream.putNextEntry(
						new ZipEntry(zipEntry.getName()));

					StreamUtil.transfer(
						zipFile.getInputStream(zipEntry), jarOutputStream,
						false);

					jarOutputStream.closeEntry();
				}
			}

			return new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}
	}

	@Deactivate
	protected void deactivate() {
		_lpkgBundleTracker.close();
		_warWrapperBundlerTracker.close();
	}

	private void _writeManifest(
			ZipFile zipFile, JarOutputStream jarOutputStream)
		throws IOException {

		Manifest manifest = new Manifest();

		Attributes attributes = manifest.getMainAttributes();

		Properties properties = new Properties();

		properties.load(
			zipFile.getInputStream(
				zipFile.getEntry("liferay-marketplace.properties")));

		attributes.putValue(
			Constants.BUNDLE_DESCRIPTION,
			properties.getProperty("description"));
		attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
		attributes.putValue(
			Constants.BUNDLE_SYMBOLICNAME, properties.getProperty("title"));
		attributes.putValue(
			Constants.BUNDLE_VERSION, properties.getProperty("version"));
		attributes.putValue("Manifest-Version", "2");

		jarOutputStream.putNextEntry(new ZipEntry(JarFile.MANIFEST_NAME));

		manifest.write(jarOutputStream);

		jarOutputStream.closeEntry();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LPKGDeployerImpl.class);

	private BundleTracker<List<Bundle>> _lpkgBundleTracker;

	@Reference
	private LPKGVerifier _lpkgVerifier;

	@Reference
	private LPKGWARBundleRegistry _lpkgWarBundleRegistry;

	private final Map<String, URL> _urls = new ConcurrentHashMap<>();
	private BundleTracker<Bundle> _warWrapperBundlerTracker;

}