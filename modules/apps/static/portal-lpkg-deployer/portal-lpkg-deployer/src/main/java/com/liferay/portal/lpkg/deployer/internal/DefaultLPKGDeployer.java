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
import com.liferay.portal.kernel.module.framework.ThrowableCollector;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;
import com.liferay.portal.lpkg.deployer.LPKGVerifier;
import com.liferay.portal.lpkg.deployer.LPKGVerifyException;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
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
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.wiring.FrameworkWiring;
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
public class DefaultLPKGDeployer implements LPKGDeployer {

	@Activate
	public void activate(BundleContext bundleContext) {
		try {
			_activate(bundleContext);
		}
		catch (Throwable t) {
			_throwableCollector.collect(t);
		}
	}

	@Override
	public List<Bundle> deploy(BundleContext bundleContext, File lpkgFile)
		throws IOException {

		Path lpkgFilePath = lpkgFile.toPath();

		if (!lpkgFilePath.startsWith(_deploymentDirPath)) {
			throw new LPKGVerifyException(
				"Unable to deploy " + lpkgFile +
					" from outside the deployment directory " +
						_deploymentDirPath);
		}

		List<Bundle> oldBundles = _lpkgVerifier.verify(lpkgFile);

		for (Bundle bundle : oldBundles) {
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
			String location = lpkgFile.getCanonicalPath();

			Bundle lpkgBundle = bundleContext.getBundle(location);

			if (lpkgBundle != null) {
				return Collections.emptyList();
			}

			List<Bundle> bundles = new ArrayList<>();

			lpkgBundle = bundleContext.installBundle(
				location, toBundle(lpkgFile));

			BundleStartLevel bundleStartLevel = lpkgBundle.adapt(
				BundleStartLevel.class);

			bundleStartLevel.setStartLevel(
				PropsValues.MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL);

			bundles.add(lpkgBundle);

			List<Bundle> newBundles = _lpkgBundleTracker.getObject(lpkgBundle);

			if (newBundles != null) {
				bundles.addAll(newBundles);
			}

			if (LPKGIndexValidatorThreadLocal.isEnabled()) {
				_lpkgIndexValidator.updateIntegrityProperties();
			}

			if (!oldBundles.isEmpty()) {
				_refreshRemovalPendingBundles(bundleContext, lpkgBundle);
			}

			return bundles;
		}
		catch (Exception e) {
			throw new IOException(e);
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
	}

	private void _activate(final BundleContext bundleContext) throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			URLConstants.URL_HANDLER_PROTOCOL, new String[] {"lpkg"});

		bundleContext.registerService(
			URLStreamHandlerService.class.getName(),
			new LPKGURLStreamHandlerService(_urls), properties);

		_deploymentDirPath = _getDeploymentDirPath(bundleContext);

		Path overrideDirPath = _deploymentDirPath.resolve("override");

		List<File> jarFiles = _scanFiles(overrideDirPath, ".jar");

		_uninstallOrphanOverridingJars(bundleContext, jarFiles);

		List<File> warFiles = _scanFiles(overrideDirPath, ".war");

		_uninstallOrphanOverridingWars(bundleContext, warFiles);

		_lpkgBundleTracker = new BundleTracker<>(
			bundleContext, ~Bundle.UNINSTALLED,
			new LPKGBundleTrackerCustomizer(
				bundleContext, _urls, _toFileNames(jarFiles, warFiles)));

		_lpkgBundleTracker.open();

		List<File> lpkgFiles = _scanFiles(_deploymentDirPath, ".lpkg");

		_lpkgIndexValidator.setLPKGDeployer(this);
		_lpkgIndexValidator.setJarFiles(jarFiles);

		boolean updateIntegrityProperties = _lpkgIndexValidator.validate(
			lpkgFiles);

		boolean enabled = LPKGIndexValidatorThreadLocal.isEnabled();

		LPKGIndexValidatorThreadLocal.setEnabled(false);

		try {
			_instalLPKGs(bundleContext, lpkgFiles);

			_installOverrideJars(bundleContext, jarFiles);

			_installOverrideWars(bundleContext, warFiles);

			if (updateIntegrityProperties) {
				_lpkgIndexValidator.updateIntegrityProperties();
			}
		}
		finally {
			LPKGIndexValidatorThreadLocal.setEnabled(enabled);
		}
	}

	private Path _getDeploymentDirPath(BundleContext bundleContext)
		throws IOException {

		String deploymentDir = GetterUtil.getString(
			bundleContext.getProperty("lpkg.deployer.dir"),
			PropsValues.MODULE_FRAMEWORK_MARKETPLACE_DIR);

		Path deploymentDirPath = Paths.get(deploymentDir);

		Files.createDirectories(deploymentDirPath);

		return deploymentDirPath;
	}

	private void _installOverrideJars(
			BundleContext bundleContext, List<File> jarFiles)
		throws Exception {

		for (File jarFile : jarFiles) {
			String location = _LPKG_OVERRIDE_PREFIX.concat(
				jarFile.getCanonicalPath());

			Bundle jarBundle = bundleContext.getBundle(location);

			if (jarBundle != null) {
				if (_log.isInfoEnabled()) {
					_log.info("Using overriding JAR bundle " + location);
				}

				continue;
			}

			jarBundle = bundleContext.installBundle(
				location, new FileInputStream(jarFile));

			BundleStartLevel bundleStartLevel = jarBundle.adapt(
				BundleStartLevel.class);

			bundleStartLevel.setStartLevel(
				PropsValues.MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL);

			_startBundle(jarBundle);

			if (_log.isInfoEnabled()) {
				_log.info("Installed override JAR bundle " + location);
			}
		}
	}

	private void _installOverrideWars(
			BundleContext bundleContext, List<File> warFiles)
		throws Exception {

		Properties properties = _loadOverrideWarsProperties(bundleContext);

		Path osgiWarDir = Paths.get(PropsValues.MODULE_FRAMEWORK_WAR_DIR);

		boolean modified = false;

		for (File warFile : warFiles) {
			String sourceLocation = warFile.getCanonicalPath();

			String targetLocation = properties.getProperty(sourceLocation);

			if (targetLocation != null) {
				if (_log.isInfoEnabled()) {
					_log.info("Using overridding WAR bundle " + targetLocation);
				}

				continue;
			}

			Path sourceWarPath = warFile.toPath();

			Path targetWarPath = osgiWarDir.resolve(
				sourceWarPath.getFileName());

			Files.copy(
				sourceWarPath, targetWarPath,
				StandardCopyOption.REPLACE_EXISTING);

			targetLocation = targetWarPath.toString();

			properties.put(sourceLocation, targetLocation);

			if (_log.isInfoEnabled()) {
				_log.info("Deployed override WAR bundle to " + targetLocation);
			}

			modified = true;
		}

		if (modified) {
			_saveOverrideWarsProperties(bundleContext, properties);
		}
	}

	private void _instalLPKGs(
		BundleContext bundleContext, List<File> lpkgFiles) {

		for (File lpkgFile : lpkgFiles) {
			try {
				List<Bundle> bundles = deploy(bundleContext, lpkgFile);

				for (Bundle bundle : bundles) {
					_startBundle(bundle);
				}
			}
			catch (Exception e) {
				_log.error("Unable to deploy LPKG file " + lpkgFile, e);
			}
		}
	}

	private Properties _loadOverrideWarsProperties(BundleContext bundleContext)
		throws IOException {

		Bundle bundle = bundleContext.getBundle(0);

		BundleContext systemBundleContext = bundle.getBundleContext();

		File overrideWarsPropertiesFile = systemBundleContext.getDataFile(
			"override-wars.properties");

		Properties overrideWarsProperties = new Properties();

		if (overrideWarsPropertiesFile.exists()) {
			try (InputStream inputStream = new FileInputStream(
					overrideWarsPropertiesFile)) {

				overrideWarsProperties.load(inputStream);
			}
		}

		return overrideWarsProperties;
	}

	/**
	 * @see FrameworkWiring#getRemovalPendingBundles
	 */
	private void _refreshRemovalPendingBundles(
			BundleContext bundleContext, Bundle bundle)
		throws Exception {

		Bundle systemBundle = bundleContext.getBundle(0);

		FrameworkWiring frameworkWiring = systemBundle.adapt(
			FrameworkWiring.class);

		final DefaultNoticeableFuture<FrameworkEvent> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Start refreshing references to point to the new bundle " +
					bundle);
		}

		frameworkWiring.refreshBundles(
			null,
			new FrameworkListener() {

				@Override
				public void frameworkEvent(FrameworkEvent frameworkEvent) {
					defaultNoticeableFuture.set(frameworkEvent);
				}

			});

		FrameworkEvent frameworkEvent = defaultNoticeableFuture.get();

		if (frameworkEvent.getType() == FrameworkEvent.PACKAGES_REFRESHED) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Finished refreshing references to point to the new " +
						"bundle " + bundle);
			}
		}
		else {
			throw new Exception(
				"Unable to refresh references to the new bundle " + bundle +
					" because of framework event " + frameworkEvent,
				frameworkEvent.getThrowable());
		}
	}

	private void _saveOverrideWarsProperties(
			BundleContext bundleContext, Properties properties)
		throws IOException {

		Bundle bundle = bundleContext.getBundle(0);

		BundleContext systemBundleContext = bundle.getBundleContext();

		File overrideWarsPropertiesFile = systemBundleContext.getDataFile(
			"override-wars.properties");

		try (OutputStream outputStream = new FileOutputStream(
				overrideWarsPropertiesFile)) {

			properties.store(outputStream, null);
		}
	}

	private List<File> _scanFiles(Path dirPath, String extension)
		throws IOException {

		if (Files.notExists(dirPath)) {
			return Collections.emptyList();
		}

		List<File> files = new ArrayList<>();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				dirPath)) {

			for (Path path : directoryStream) {
				String pathName = StringUtil.toLowerCase(
					String.valueOf(path.getFileName()));

				if (pathName.endsWith(extension)) {
					files.add(path.toFile());
				}
			}
		}

		return files;
	}

	private void _startBundle(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders();

		String fragmentHost = headers.get(Constants.FRAGMENT_HOST);

		if (fragmentHost != null) {
			return;
		}

		try {
			bundle.start();
		}
		catch (BundleException be) {
			_log.error("Unable to start " + bundle, be);
		}
	}

	private Set<String> _toFileNames(List<File> jarFiles, List<File> warFiles) {
		Set<String> fileNames = new HashSet<>();

		for (File file : jarFiles) {
			fileNames.add(StringUtil.toLowerCase(file.getName()));
		}

		for (File file : warFiles) {
			fileNames.add(StringUtil.toLowerCase(file.getName()));
		}

		return fileNames;
	}

	private void _uninstallOrphanOverridingJars(
			BundleContext bundleContext, List<File> jarFiles)
		throws BundleException {

		for (Bundle bundle : bundleContext.getBundles()) {
			String location = bundle.getLocation();

			if (!location.startsWith(_LPKG_OVERRIDE_PREFIX)) {
				continue;
			}

			String filePath = location.substring(
				_LPKG_OVERRIDE_PREFIX.length());

			if (jarFiles.contains(new File(filePath))) {
				continue;
			}

			bundle.uninstall();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Uninstalled orphan overriding JAR bundle " + location);
			}
		}
	}

	private void _uninstallOrphanOverridingWars(
			BundleContext bundleContext, List<File> warFiles)
		throws IOException {

		Properties properties = _loadOverrideWarsProperties(bundleContext);

		Set<Entry<Object, Object>> entrySet = properties.entrySet();

		Iterator<Entry<Object, Object>> iterator = entrySet.iterator();

		boolean modified = false;

		while (iterator.hasNext()) {
			Entry<Object, Object> entry = iterator.next();

			if (warFiles.contains(new File((String)entry.getKey()))) {
				continue;
			}

			iterator.remove();

			Files.deleteIfExists(Paths.get((String)entry.getValue()));

			modified = true;
		}

		if (modified) {
			_saveOverrideWarsProperties(bundleContext, properties);
		}
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
		attributes.putValue("Liferay-Releng-Bundle-Type", "lpkg");
		attributes.putValue("Manifest-Version", "2");

		jarOutputStream.putNextEntry(new ZipEntry(JarFile.MANIFEST_NAME));

		manifest.write(jarOutputStream);

		jarOutputStream.closeEntry();
	}

	private static final String _LPKG_OVERRIDE_PREFIX = "LPKG-Override::";

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultLPKGDeployer.class);

	private Path _deploymentDirPath;
	private BundleTracker<List<Bundle>> _lpkgBundleTracker;

	@Reference
	private LPKGIndexValidator _lpkgIndexValidator;

	@Reference
	private LPKGVerifier _lpkgVerifier;

	@Reference(target = "(throwable.collector=initial.bundles)")
	private ThrowableCollector _throwableCollector;

	private final Map<String, URL> _urls = new ConcurrentHashMap<>();

}