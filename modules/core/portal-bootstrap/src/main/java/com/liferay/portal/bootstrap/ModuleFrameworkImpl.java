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

package com.liferay.portal.bootstrap;

import aQute.bnd.header.OSGiHeader;
import aQute.bnd.header.Parameters;
import aQute.bnd.version.Version;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.lpkg.StaticLPKGResolver;
import com.liferay.portal.kernel.module.framework.ThrowableCollector;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServiceLoader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.module.framework.ModuleFramework;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.collections.ServiceTrackerMapFactory;
import com.liferay.registry.collections.ServiceTrackerMapFactoryUtil;
import com.liferay.registry.internal.RegistryImpl;
import com.liferay.registry.internal.ServiceTrackerMapFactoryImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.FrameworkWiring;

import org.springframework.beans.factory.BeanIsAbstractException;
import org.springframework.context.ApplicationContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 * @author Kamesh Sampath
 * @author Gregory Amerson
 */
public class ModuleFrameworkImpl implements ModuleFramework {

	@Override
	public long addBundle(String location) throws PortalException {
		Bundle bundle = _addBundle(location, null, true);

		return bundle.getBundleId();
	}

	@Override
	public long addBundle(String location, InputStream inputStream)
		throws PortalException {

		Bundle bundle = _addBundle(location, inputStream, true);

		return bundle.getBundleId();
	}

	public Bundle getBundle(
			BundleContext bundleContext, InputStream inputStream)
		throws PortalException {

		try {
			JarInputStream jarInputStream = new JarInputStream(inputStream);

			Manifest manifest = jarInputStream.getManifest();

			Attributes attributes = manifest.getMainAttributes();

			String bundleSymbolicNameAttributeValue = attributes.getValue(
				Constants.BUNDLE_SYMBOLICNAME);

			Parameters parameters = OSGiHeader.parseHeader(
				bundleSymbolicNameAttributeValue);

			Set<String> set = parameters.keySet();

			Iterator<String> iterator = set.iterator();

			String bundleSymbolicName = iterator.next();

			String bundleVersionAttributeValue = attributes.getValue(
				Constants.BUNDLE_VERSION);

			Version bundleVersion = Version.parseVersion(
				bundleVersionAttributeValue);

			for (Bundle bundle : bundleContext.getBundles()) {
				Version curBundleVersion = Version.parseVersion(
					String.valueOf(bundle.getVersion()));

				if (bundleSymbolicName.equals(bundle.getSymbolicName()) &&
					bundleVersion.equals(curBundleVersion)) {

					return bundle;
				}
			}

			return null;
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	public Bundle getBundle(long bundleId) {
		if (_framework == null) {
			return null;
		}

		BundleContext bundleContext = _framework.getBundleContext();

		return bundleContext.getBundle(bundleId);
	}

	@Override
	public URL getBundleResource(long bundleId, String name) {
		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			return null;
		}

		return bundle.getResource(name);
	}

	@Override
	public Framework getFramework() {
		return _framework;
	}

	@Override
	public String getState(long bundleId) throws PortalException {
		_checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new PortalException("No bundle with ID " + bundleId);
		}

		int state = bundle.getState();

		if (state == Bundle.ACTIVE) {
			return "active";
		}
		else if (state == Bundle.INSTALLED) {
			return "installed";
		}
		else if (state == Bundle.RESOLVED) {
			return "resolved";
		}
		else if (state == Bundle.STARTING) {
			return "starting";
		}
		else if (state == Bundle.STOPPING) {
			return "stopping";
		}
		else if (state == Bundle.UNINSTALLED) {
			return "uninstalled";
		}

		return StringPool.BLANK;
	}

	@Override
	public void initFramework() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Initializing the OSGi framework");
		}

		_validateModuleFrameworkBaseDirForEquinox();

		_initRequiredStartupDirs();

		Thread currentThread = Thread.currentThread();

		List<FrameworkFactory> frameworkFactories = ServiceLoader.load(
			new URLClassLoader(_getClassPathURLs(), null),
			currentThread.getContextClassLoader(), FrameworkFactory.class,
			null);

		FrameworkFactory frameworkFactory = frameworkFactories.get(0);

		if (_log.isDebugEnabled()) {
			Class<?> clazz = frameworkFactory.getClass();

			_log.debug("Using the OSGi framework factory " + clazz.getName());
		}

		Map<String, String> properties = _buildFrameworkProperties(
			frameworkFactory.getClass());

		if (_log.isDebugEnabled()) {
			_log.debug("Creating a new OSGi framework instance");
		}

		_framework = frameworkFactory.newFramework(properties);

		if (_log.isDebugEnabled()) {
			_log.debug("Initializing the new OSGi framework instance");
		}

		_framework.init();

		if (_log.isDebugEnabled()) {
			_log.debug("Binding the OSGi framework to the registry API");
		}

		RegistryUtil.setRegistry(
			new RegistryImpl(_framework.getBundleContext()));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Binding the OSGi framework to the service tracker map " +
					"factory");
		}

		ServiceTrackerMapFactoryUtil.setServiceTrackerMapFactory(
			new ServiceTrackerMapFactoryImpl(_framework.getBundleContext()));

		if (_log.isDebugEnabled()) {
			_log.debug("Initialized the OSGi framework");
		}
	}

	@Override
	public void registerContext(Object context) {
		if (context == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Registering context " + context);
		}

		if (context instanceof ApplicationContext) {
			ApplicationContext applicationContext = (ApplicationContext)context;

			_registerApplicationContext(applicationContext);
		}
		else if (context instanceof ServletContext) {
			ServletContext servletContext = (ServletContext)context;

			_registerServletContext(servletContext);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Registered context " + context);
		}
	}

	@Override
	public void setBundleStartLevel(long bundleId, int startLevel)
		throws PortalException {

		_checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new PortalException("No bundle with ID " + bundleId);
		}

		BundleStartLevel bundleStartLevel = bundle.adapt(
			BundleStartLevel.class);

		bundleStartLevel.setStartLevel(startLevel);
	}

	public void startBundle(
			Bundle bundle, int options, boolean checkPermissions)
		throws PortalException {

		if (checkPermissions) {
			_checkPermission();
		}

		if (_isFragmentBundle(bundle) ||
			((bundle.getState() & Bundle.ACTIVE) == Bundle.ACTIVE)) {

			return;
		}

		try {
			bundle.start(options);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new PortalException(be);
		}
	}

	@Override
	public void startBundle(long bundleId) throws PortalException {
		startBundle(bundleId, 0);
	}

	@Override
	public void startBundle(long bundleId, int options) throws PortalException {
		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new PortalException("No bundle with ID " + bundleId);
		}

		startBundle(bundle, 0, true);
	}

	@Override
	public void startFramework() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Starting the OSGi framework");
		}

		_framework.start();

		_setUpPrerequisiteFrameworkServices(_framework.getBundleContext());

		Set<Bundle> initialBundles = _setUpInitialBundles();

		_startDynamicBundles(initialBundles);

		if (_log.isDebugEnabled()) {
			_log.debug("Started the OSGi framework");
		}
	}

	@Override
	public void startRuntime() throws Exception {
		if (_framework == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Starting the OSGi runtime");
		}

		FrameworkStartLevel frameworkStartLevel = _framework.adapt(
			FrameworkStartLevel.class);

		frameworkStartLevel.setStartLevel(
			PropsValues.MODULE_FRAMEWORK_RUNTIME_START_LEVEL);

		if (_log.isDebugEnabled()) {
			_log.debug("Started the OSGi runtime");
		}
	}

	@Override
	public void stopBundle(long bundleId) throws PortalException {
		stopBundle(bundleId, 0);
	}

	@Override
	public void stopBundle(long bundleId, int options) throws PortalException {
		_checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new PortalException("No bundle with ID " + bundleId);
		}

		try {
			bundle.stop(options);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new PortalException(be);
		}
	}

	@Override
	public void stopFramework(long timeout) throws Exception {
		if (_framework == null) {
			return;
		}

		Registry registry = RegistryUtil.getRegistry();

		if (registry instanceof RegistryImpl) {
			RegistryImpl registryImpl = (RegistryImpl)registry;

			registryImpl.closeServiceTrackers();
		}

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		if (serviceTrackerMapFactory instanceof ServiceTrackerMapFactoryImpl) {
			ServiceTrackerMapFactoryImpl serviceTrackerMapFactoryImpl =
				(ServiceTrackerMapFactoryImpl)serviceTrackerMapFactory;

			serviceTrackerMapFactoryImpl.clearServiceTrackerMaps();
		}

		_framework.stop();

		FrameworkEvent frameworkEvent = _framework.waitForStop(timeout);

		if (frameworkEvent.getType() == FrameworkEvent.WAIT_TIMEDOUT) {
			_log.error(
				StringBundler.concat(
					"OSGi framework event ", String.valueOf(frameworkEvent),
					" triggered after a ", String.valueOf(timeout),
					"ms timeout"));
		}
		else if (_log.isInfoEnabled()) {
			_log.info(frameworkEvent);
		}

		RegistryUtil.setRegistry(null);

		ServiceTrackerMapFactoryUtil.setServiceTrackerMapFactory(null);
	}

	@Override
	public void stopRuntime() throws Exception {
		if (_framework == null) {
			return;
		}

		FrameworkStartLevel frameworkStartLevel = _framework.adapt(
			FrameworkStartLevel.class);

		frameworkStartLevel.setStartLevel(
			PropsValues.MODULE_FRAMEWORK_BEGINNING_START_LEVEL);
	}

	@Override
	public void uninstallBundle(long bundleId) throws PortalException {
		_checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new PortalException("No bundle with ID " + bundleId);
		}

		try {
			bundle.uninstall();
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new PortalException(be);
		}
	}

	@Override
	public void unregisterContext(Object context) {
		if (context == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Unregistering context " + context);
		}

		if (!(context instanceof ApplicationContext)) {
			return;
		}

		_unregisterApplicationContext((ApplicationContext)context);

		if (_log.isDebugEnabled()) {
			_log.debug("Registered context " + context);
		}
	}

	@Override
	public void updateBundle(long bundleId) throws PortalException {
		updateBundle(bundleId, null);
	}

	@Override
	public void updateBundle(long bundleId, InputStream inputStream)
		throws PortalException {

		_checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new PortalException("No bundle with ID " + bundleId);
		}

		try {
			bundle.update(inputStream);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new PortalException(be);
		}
	}

	private static URL[] _getClassPathURLs() throws IOException {
		File coreDir = new File(PropsValues.MODULE_FRAMEWORK_BASE_DIR, "core");

		File[] files = coreDir.listFiles();

		if (files == null) {
			throw new IllegalStateException(
				"Missing " + coreDir.getCanonicalPath());
		}

		URL[] urls = new URL[files.length];

		for (int i = 0; i < urls.length; i++) {
			URI uri = files[i].toURI();

			urls[i] = uri.toURL();
		}

		return urls;
	}

	private Bundle _addBundle(
			String location, InputStream inputStream, boolean checkPermission)
		throws PortalException {

		if (_framework == null) {
			throw new IllegalStateException(
				"OSGi framework is not initialized");
		}

		if (checkPermission) {
			_checkPermission();
		}

		BundleContext bundleContext = _framework.getBundleContext();

		if (inputStream != null) {
			UnsyncBufferedInputStream unsyncBufferedInputStream =
				new UnsyncBufferedInputStream(inputStream);

			unsyncBufferedInputStream.mark(1024 * 1000);

			Bundle bundle = null;

			if (location.contains("static=true")) {
				bundle = _getStaticBundle(
					bundleContext, unsyncBufferedInputStream, location);
			}
			else {
				bundle = getBundle(bundleContext, unsyncBufferedInputStream);
			}

			try {
				unsyncBufferedInputStream.reset();
			}
			catch (IOException ioe) {
				throw new PortalException(ioe);
			}

			if (bundle != null) {
				return bundle;
			}

			inputStream = unsyncBufferedInputStream;
		}

		try {
			return bundleContext.installBundle(location, inputStream);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new PortalException(be);
		}
	}

	private Map<String, String> _buildFrameworkProperties(Class<?> clazz)
		throws URISyntaxException {

		if (_log.isDebugEnabled()) {
			_log.debug("Building OSGi framework properties");
		}

		Map<String, String> properties = new HashMap<>();

		// Release

		properties.put(
			Constants.BUNDLE_DESCRIPTION, ReleaseInfo.getReleaseInfo());
		properties.put(Constants.BUNDLE_NAME, ReleaseInfo.getName());
		properties.put(Constants.BUNDLE_VENDOR, ReleaseInfo.getVendor());
		properties.put(Constants.BUNDLE_VERSION, ReleaseInfo.getVersion());

		// Fileinstall. See LPS-56385.

		properties.put(
			FrameworkPropsKeys.FELIX_FILEINSTALL_DIR,
			_getFelixFileInstallDir());
		properties.put(
			FrameworkPropsKeys.FELIX_FILEINSTALL_POLL,
			String.valueOf(PropsValues.MODULE_FRAMEWORK_AUTO_DEPLOY_INTERVAL));
		properties.put(
			FrameworkPropsKeys.FELIX_FILEINSTALL_START_LEVEL,
			String.valueOf(
				PropsValues.MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL));
		properties.put(
			FrameworkPropsKeys.FELIX_FILEINSTALL_TMPDIR,
			SystemProperties.get(SystemProperties.TMP_DIR));

		// Framework

		properties.put(
			Constants.FRAMEWORK_BUNDLE_PARENT,
			Constants.FRAMEWORK_BUNDLE_PARENT_APP);
		properties.put(
			Constants.FRAMEWORK_STORAGE,
			PropsValues.MODULE_FRAMEWORK_STATE_DIR);

		properties.put("eclipse.security", null);
		properties.put(
			"equinox.resolver.revision.batch.size",
			PropsValues.MODULE_FRAMEWORK_RESOLVER_REVISION_BATCH_SIZE);
		properties.put("java.security.manager", null);
		properties.put("org.osgi.framework.security", null);
		properties.put("osgi.home", PropsValues.LIFERAY_HOME);

		ProtectionDomain protectionDomain = clazz.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL codeSourceURL = codeSource.getLocation();

		properties.put(
			FrameworkPropsKeys.OSGI_FRAMEWORK, codeSourceURL.toExternalForm());

		File frameworkFile = new File(codeSourceURL.toURI());

		properties.put(
			FrameworkPropsKeys.OSGI_INSTALL_AREA, frameworkFile.getParent());

		// Overrides

		Properties extraProperties = PropsUtil.getProperties(
			PropsKeys.MODULE_FRAMEWORK_PROPERTIES, true);

		Parameters extraCapabilitiesParameters = OSGiHeader.parseHeader(
			extraProperties.getProperty(
				Constants.FRAMEWORK_SYSTEMCAPABILITIES_EXTRA));

		Attributes attributes = _getExtraManifestAttributes();

		String provideCapability = attributes.getValue(
			Constants.PROVIDE_CAPABILITY);

		Parameters provideCapabilityParameters = new Parameters(
			provideCapability);

		if (!extraCapabilitiesParameters.isEmpty()) {
			extraCapabilitiesParameters.putAll(provideCapabilityParameters);
		}
		else {
			extraCapabilitiesParameters = provideCapabilityParameters;
		}

		extraProperties.setProperty(
			Constants.FRAMEWORK_SYSTEMCAPABILITIES_EXTRA, provideCapability);

		for (Map.Entry<Object, Object> entry : extraProperties.entrySet()) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			// We need to support an empty string and a null value distinctly.
			// This is due to some different behaviors between OSGi
			// implementations. If a property is passed as xyz= it will be
			// treated as an empty string. Otherwise, xyz=null will be treated
			// as an explicit null value.

			if (value.equals(StringPool.NULL)) {
				value = null;
			}

			properties.put(key, value);
		}

		String systemPackagesExtra = _getSystemPackagesExtra(
			attributes.getValue(Constants.EXPORT_PACKAGE));

		properties.put(
			Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, systemPackagesExtra);

		if (_log.isDebugEnabled()) {
			for (Map.Entry<String, String> entry : properties.entrySet()) {
				_log.debug(
					StringBundler.concat(
						"OSGi framework property key \"", entry.getKey(),
						"\" with value \"", entry.getValue(), "\""));
			}
		}

		return properties;
	}

	private void _checkPermission() throws PrincipalException {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new PrincipalException();
		}

		if (!permissionChecker.isOmniadmin()) {
			throw new PrincipalException.MustBeOmniadmin(permissionChecker);
		}
	}

	private Map<String, Bundle> _deployStaticBundlesFromFile(
			File file, Set<String> overrideStaticFileNames)
		throws IOException {

		Map<String, Bundle> bundles = new HashMap<>();

		try (ZipFile zipFile = new ZipFile(file)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			List<ZipEntry> zipEntries = new ArrayList<>();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = StringUtil.toLowerCase(zipEntry.getName());

				if (!name.endsWith(".jar")) {
					continue;
				}

				Matcher matcher = _pattern.matcher(name);

				if (matcher.matches()) {
					String fileName = matcher.group(1) + ".jar";

					if (overrideStaticFileNames.contains(fileName)) {
						if (_log.isInfoEnabled()) {
							StringBundler sb = new StringBundler(7);

							sb.append(zipFile);
							sb.append(":");
							sb.append(zipEntry);
							sb.append(" is overridden by ");
							sb.append(PropsValues.MODULE_FRAMEWORK_BASE_DIR);
							sb.append("/static/");
							sb.append(fileName);

							_log.info(sb.toString());
						}

						continue;
					}
				}

				zipEntries.add(zipEntry);
			}

			Collections.sort(
				zipEntries,
				new Comparator<ZipEntry>() {

					@Override
					public int compare(ZipEntry zipEntry1, ZipEntry zipEntry2) {
						String name1 = zipEntry1.getName();
						String name2 = zipEntry2.getName();

						return name1.compareTo(name2);
					}

				});

			for (ZipEntry zipEntry : zipEntries) {
				String zipEntryName = zipEntry.getName();

				String location =
					"file:/" + zipEntryName + "?protocol=lpkg&static=true";

				try (InputStream inputStream = zipFile.getInputStream(
						zipEntry)) {

					Bundle bundle = _installInitialBundle(
						location, inputStream);

					if (bundle != null) {
						bundles.put(location, bundle);
					}
				}
			}
		}

		return bundles;
	}

	private Attributes _getExtraManifestAttributes() {
		try (InputStream inputStream =
				ModuleFrameworkImpl.class.getResourceAsStream(
					"/META-INF/system.packages.extra.mf")) {

			Manifest manifest = new Manifest(inputStream);

			return manifest.getMainAttributes();
		}
		catch (IOException ioe) {
			return ReflectionUtil.throwException(ioe);
		}
	}

	private String _getFelixFileInstallDir() {
		return PropsValues.MODULE_FRAMEWORK_PORTAL_DIR + StringPool.COMMA +
			StringUtil.merge(PropsValues.MODULE_FRAMEWORK_AUTO_DEPLOY_DIRS);
	}

	private Dictionary<String, Object> _getProperties(
		Object bean, String beanName) {

		Class<?> clazz = bean.getClass();

		OSGiBeanProperties osgiBeanProperties = clazz.getAnnotation(
			OSGiBeanProperties.class);

		return _getProperties(osgiBeanProperties, beanName);
	}

	private Dictionary<String, Object> _getProperties(
		OSGiBeanProperties osgiBeanProperties, String beanName) {

		HashMapDictionary<String, Object> properties =
			new HashMapDictionary<>();

		if (osgiBeanProperties != null) {
			properties.putAll(
				OSGiBeanProperties.Convert.toMap(osgiBeanProperties));
		}

		properties.put(ServicePropsKeys.BEAN_ID, beanName);
		properties.put(ServicePropsKeys.ORIGINAL_BEAN, Boolean.TRUE);
		properties.put(ServicePropsKeys.VENDOR, ReleaseInfo.getVendor());

		return properties;
	}

	private Bundle _getStaticBundle(
			BundleContext bundleContext, InputStream inputStream,
			String location)
		throws PortalException {

		try {
			JarInputStream jarInputStream = new JarInputStream(inputStream);

			Manifest manifest = jarInputStream.getManifest();

			if (manifest == null) {
				throw new IllegalStateException(
					"No manifest found at location " + location);
			}

			Attributes attributes = manifest.getMainAttributes();

			String bundleSymbolicNameAttributeValue = attributes.getValue(
				Constants.BUNDLE_SYMBOLICNAME);

			Parameters parameters = OSGiHeader.parseHeader(
				bundleSymbolicNameAttributeValue);

			Set<String> set = parameters.keySet();

			Iterator<String> iterator = set.iterator();

			String bundleSymbolicName = iterator.next();

			String bundleVersionAttributeValue = attributes.getValue(
				Constants.BUNDLE_VERSION);

			Version bundleVersion = Version.parseVersion(
				bundleVersionAttributeValue);

			for (Bundle bundle : bundleContext.getBundles()) {
				if (bundleSymbolicName.equals(bundle.getSymbolicName())) {
					Version curBundleVersion = Version.parseVersion(
						String.valueOf(bundle.getVersion()));

					if (bundleVersion.equals(curBundleVersion)) {
						return bundle;
					}
					else {
						bundle.uninstall();

						_refreshBundles(Collections.singletonList(bundle));

						return null;
					}
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	private String _getSystemPackagesExtra(String exportedPackages) {
		String[] systemPackagesExtra =
			PropsValues.MODULE_FRAMEWORK_SYSTEM_PACKAGES_EXTRA;

		StringBundler sb = new StringBundler();

		for (String extraPackage : systemPackagesExtra) {
			sb.append(extraPackage);
			sb.append(StringPool.COMMA);
		}

		sb.append(exportedPackages);

		if (_log.isDebugEnabled()) {
			String s = sb.toString();

			s = s.replace(",", "\n");

			_log.debug(
				"The portal's system bundle is exporting the following " +
					"packages:\n" + s);
		}

		return sb.toString();
	}

	private boolean _hasLazyActivationPolicy(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String fragmentHost = headers.get(Constants.FRAGMENT_HOST);

		if (fragmentHost != null) {
			return false;
		}

		String activationPolicy = headers.get(
			Constants.BUNDLE_ACTIVATIONPOLICY);

		if (activationPolicy == null) {
			return false;
		}

		Parameters parameters = OSGiHeader.parseHeader(activationPolicy);

		if (parameters.containsKey(Constants.ACTIVATION_LAZY)) {
			return true;
		}

		return false;
	}

	private void _initRequiredStartupDirs() {
		if (_log.isDebugEnabled()) {
			_log.debug("Initializing required startup directories");
		}

		String[] dirNames = StringUtil.split(_getFelixFileInstallDir());

		for (String dirName : dirNames) {
			FileUtil.mkdirs(dirName);
		}

		FileUtil.mkdirs(PropsValues.MODULE_FRAMEWORK_BASE_DIR + "/static");
		FileUtil.mkdirs(
			PropsValues.MODULE_FRAMEWORK_MARKETPLACE_DIR + "/override");
	}

	private Bundle _installInitialBundle(
		String location, InputStream inputStream) {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Adding initial bundle " + location);
			}

			Bundle bundle = _addBundle(location, inputStream, false);

			if (_log.isDebugEnabled()) {
				_log.debug("Added initial bundle " + bundle);
			}

			if ((bundle == null) || _isFragmentBundle(bundle)) {
				return bundle;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Setting bundle ", String.valueOf(bundle),
						" at start level ",
						String.valueOf(
							PropsValues.
								MODULE_FRAMEWORK_BEGINNING_START_LEVEL)));
			}

			BundleStartLevel bundleStartLevel = bundle.adapt(
				BundleStartLevel.class);

			bundleStartLevel.setStartLevel(
				PropsValues.MODULE_FRAMEWORK_BEGINNING_START_LEVEL);

			return bundle;
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	private boolean _isFragmentBundle(Bundle bundle) {
		BundleRevision bundleRevision = bundle.adapt(BundleRevision.class);

		if ((bundleRevision.getTypes() & BundleRevision.TYPE_FRAGMENT) == 0) {
			return false;
		}

		return true;
	}

	private void _refreshBundles(List<Bundle> refreshBundles) {
		FrameworkWiring frameworkWiring = _framework.adapt(
			FrameworkWiring.class);

		final DefaultNoticeableFuture<FrameworkEvent> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		frameworkWiring.refreshBundles(
			refreshBundles,
			new FrameworkListener() {

				@Override
				public void frameworkEvent(FrameworkEvent frameworkEvent) {
					defaultNoticeableFuture.set(frameworkEvent);
				}

			});

		try {
			FrameworkEvent frameworkEvent = defaultNoticeableFuture.get();

			if (frameworkEvent.getType() != FrameworkEvent.PACKAGES_REFRESHED) {
				throw frameworkEvent.getThrowable();
			}
		}
		catch (Throwable t) {
			ReflectionUtil.throwException(t);
		}
	}

	private void _registerApplicationContext(
		ApplicationContext applicationContext) {

		if (_log.isDebugEnabled()) {
			_log.debug("Register application context");
		}

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		for (String beanName : applicationContext.getBeanDefinitionNames()) {
			Object bean = null;

			try {
				bean = applicationContext.getBean(beanName);
			}
			catch (BeanIsAbstractException biae) {
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			if (bean != null) {
				ServiceRegistration<?> serviceRegistration = _registerService(
					_framework.getBundleContext(), beanName, bean);

				if (serviceRegistration != null) {
					serviceRegistrations.add(serviceRegistration);
				}
			}
		}

		_springContextServices.put(applicationContext, serviceRegistrations);
	}

	private ServiceRegistration<?> _registerService(
		BundleContext bundleContext, String beanName, Object bean) {

		Class<?> clazz = bean.getClass();

		OSGiBeanProperties osgiBeanProperties = clazz.getAnnotation(
			OSGiBeanProperties.class);

		Set<String> names = OSGiBeanProperties.Service.interfaceNames(
			bean, osgiBeanProperties,
			PropsValues.MODULE_FRAMEWORK_SERVICES_IGNORED_INTERFACES);

		if (names.isEmpty()) {
			return null;
		}

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				names.toArray(new String[names.size()]), bean,
				_getProperties(osgiBeanProperties, beanName));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Registered service as " + serviceRegistration.getReference());
		}

		return serviceRegistration;
	}

	private void _registerServletContext(ServletContext servletContext) {
		BundleContext bundleContext = _framework.getBundleContext();

		if (_log.isDebugEnabled()) {
			_log.debug("Register servlet context");
		}

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				new String[] {ServletContext.class.getName()}, servletContext,
				_getProperties(servletContext, "liferayServletContext"));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Registered servlet context as " +
					serviceRegistration.getReference());
		}
	}

	private Set<Bundle> _setUpInitialBundles() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Starting initial bundles");
		}

		BundleContext bundleContext = _framework.getBundleContext();

		ThrowableCollector throwableCollector = new ThrowableCollector();

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("throwable.collector", "initial.bundles");

		bundleContext.registerService(
			ThrowableCollector.class, throwableCollector, dictionary);

		final Map<String, Bundle> bundles = new LinkedHashMap<>();

		final List<Path> jarPaths = new ArrayList<>();

		Files.walkFileTree(
			Paths.get(PropsValues.MODULE_FRAMEWORK_BASE_DIR, "static"),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path fileNamePath = filePath.getFileName();

					String fileName = StringUtil.toLowerCase(
						fileNamePath.toString());

					if (!fileName.endsWith(".jar")) {
						return FileVisitResult.CONTINUE;
					}

					Matcher matcher = _pattern.matcher(fileName);

					if (!matcher.matches()) {
						jarPaths.add(filePath.toAbsolutePath());

						return FileVisitResult.CONTINUE;
					}

					if (_log.isWarnEnabled()) {
						_log.warn(
							"Override static jar " + fileName +
								" has an invalid name and will be ignored");
					}

					return FileVisitResult.CONTINUE;
				}

			});

		for (String staticJarFileName :
				PropsValues.MODULE_FRAMEWORK_STATIC_JARS) {

			File staticJarFile = new File(
				PropsValues.LIFERAY_LIB_PORTAL_DIR, staticJarFileName);

			if (staticJarFile.exists()) {
				jarPaths.add(staticJarFile.toPath());
			}
			else {
				_log.error("Missing " + staticJarFile);
			}
		}

		Collections.sort(jarPaths);

		List<Bundle> refreshBundles = new ArrayList<>();

		for (Bundle bundle : bundleContext.getBundles()) {
			String location = bundle.getLocation();

			if (!location.contains("protocol=jar&static=true")) {
				continue;
			}

			URI uri = new URI(location);

			File file = new File(uri.getPath());

			if (jarPaths.contains(file.toPath())) {
				bundles.put(bundle.getLocation(), bundle);

				continue;
			}

			bundle.uninstall();

			refreshBundles.add(bundle);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Uninstalled orphan overriding static JAR bundle " +
						location);
			}
		}

		_refreshBundles(refreshBundles);

		refreshBundles.clear();

		Set<String> overrideStaticFileNames = new HashSet<>();

		for (Path jarPath : jarPaths) {
			try (InputStream inputStream = Files.newInputStream(jarPath)) {
				URI uri = jarPath.toUri();

				String uriString = uri.toString();

				String location = uriString.concat("?protocol=jar&static=true");

				Bundle bundle = _installInitialBundle(location, inputStream);

				if (bundle != null) {
					bundles.put(location, bundle);

					overrideStaticFileNames.add(
						uriString.substring(
							uriString.lastIndexOf(StringPool.SLASH) + 1));
				}
			}
		}

		String deployDir = bundleContext.getProperty("lpkg.deployer.dir");

		for (String staticFileName :
				StaticLPKGResolver.getStaticLPKGFileNames()) {

			File file = new File(deployDir + StringPool.SLASH + staticFileName);

			if (file.exists()) {
				bundles.putAll(
					_deployStaticBundlesFromFile(
						file, overrideStaticFileNames));
			}
		}

		Set<String> overrideLPKGFileNames = new HashSet<>();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				Paths.get(deployDir, "override"))) {

			for (Path path : directoryStream) {
				String fileName = String.valueOf(path.getFileName());

				String pathName = StringUtil.toLowerCase(fileName);

				if (pathName.endsWith("jar")) {
					overrideLPKGFileNames.add(fileName);
				}
			}
		}

		for (Bundle bundle : bundleContext.getBundles()) {
			String location = bundle.getLocation();

			Matcher matcher = _pattern.matcher(location);

			if (matcher.find()) {
				location = matcher.group(1) + "*.jar";
			}

			if (overrideLPKGFileNames.contains(location)) {
				bundle.uninstall();
			}
		}

		for (Bundle bundle : bundles.values()) {
			if (!_isFragmentBundle(bundle)) {
				bundle.stop();
			}
		}

		Bundle[] initialBundles = bundleContext.getBundles();

		FrameworkStartLevel frameworkStartLevel = _framework.adapt(
			FrameworkStartLevel.class);

		final DefaultNoticeableFuture<FrameworkEvent> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		frameworkStartLevel.setStartLevel(
			PropsValues.MODULE_FRAMEWORK_BEGINNING_START_LEVEL,
			new FrameworkListener() {

				@Override
				public void frameworkEvent(FrameworkEvent frameworkEvent) {
					defaultNoticeableFuture.set(frameworkEvent);
				}

			});

		FrameworkEvent frameworkEvent = defaultNoticeableFuture.get();

		if (frameworkEvent.getType() != FrameworkEvent.STARTLEVEL_CHANGED) {
			ReflectionUtil.throwException(frameworkEvent.getThrowable());
		}

		Runtime runtime = Runtime.getRuntime();

		ExecutorService executorService = Executors.newFixedThreadPool(
			runtime.availableProcessors(),
			new NamedThreadFactory(
				"ModuleFramework-Static-Bundles", Thread.NORM_PRIORITY,
				ModuleFrameworkImpl.class.getClassLoader()));

		List<Future<Void>> futures = new ArrayList<>();

		FrameworkWiring frameworkWiring = _framework.adapt(
			FrameworkWiring.class);

		frameworkWiring.resolveBundles(bundles.values());

		for (final Bundle bundle : bundles.values()) {
			if (!_isFragmentBundle(bundle)) {
				futures.add(
					executorService.submit(
						new Callable<Void>() {

							@Override
							public Void call() throws BundleException {
								bundle.start();

								return null;
							}

						}));
			}
		}

		executorService.shutdown();

		for (Future<Void> future : futures) {
			try {
				future.get();
			}
			catch (ExecutionException ee) {
				throwableCollector.collect(ee.getCause());
			}
			catch (InterruptedException ie) {
				throwableCollector.collect(ie);
			}
		}

		throwableCollector.rethrow();

		if (_log.isDebugEnabled()) {
			_log.debug("Started initial bundles");
		}

		Bundle[] installedBundles = bundleContext.getBundles();

		Set<Bundle> fragmentBundles = new HashSet<>();

		for (Bundle bundle : installedBundles) {
			if (_isFragmentBundle(bundle)) {
				fragmentBundles.add(bundle);
			}
		}

		frameworkWiring.resolveBundles(fragmentBundles);

		return new HashSet<>(Arrays.asList(initialBundles));
	}

	private void _setUpPrerequisiteFrameworkServices(
		BundleContext bundleContext) {

		if (_log.isDebugEnabled()) {
			_log.debug("Setting up required services");
		}

		Props props = PropsUtil.getProps();

		ServiceRegistration<Props> serviceRegistration =
			bundleContext.registerService(
				Props.class, props,
				_getProperties(props, Props.class.getName()));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Registered required service as " +
					serviceRegistration.getReference());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Registered required services");
		}
	}

	private void _startDynamicBundles(Set<Bundle> installedBundles)
		throws Exception {

		Bundle fileInstallBundle = null;

		for (Bundle bundle : installedBundles) {
			if ("org.apache.felix.fileinstall".equals(
					bundle.getSymbolicName())) {

				fileInstallBundle = bundle;

				break;
			}
		}

		if (fileInstallBundle == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find the Apache Felix File Install bundle to " +
						"synchronize the starting of dynamic bundles");
			}
		}
		else {
			fileInstallBundle.stop(Bundle.STOP_TRANSIENT);
		}

		FrameworkStartLevel frameworkStartLevel = _framework.adapt(
			FrameworkStartLevel.class);

		final DefaultNoticeableFuture<FrameworkEvent> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		frameworkStartLevel.setStartLevel(
			PropsValues.MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL,
			new FrameworkListener() {

				@Override
				public void frameworkEvent(FrameworkEvent fe) {
					defaultNoticeableFuture.set(fe);
				}

			});

		FrameworkEvent frameworkEvent = defaultNoticeableFuture.get();

		if (frameworkEvent.getType() == FrameworkEvent.ERROR) {
			ReflectionUtil.throwException(frameworkEvent.getThrowable());
		}

		BundleContext bundleContext = _framework.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			if (installedBundles.contains(bundle) ||
				((bundle.getState() != Bundle.INSTALLED) &&
				 (bundle.getState() != Bundle.RESOLVED))) {

				continue;
			}

			BundleRevision bundleRevision = bundle.adapt(BundleRevision.class);

			if ((bundleRevision.getTypes() & BundleRevision.TYPE_FRAGMENT) !=
					0) {

				continue;
			}

			BundleStartLevel bundleStartLevel = bundle.adapt(
				BundleStartLevel.class);

			if (bundleStartLevel.getStartLevel() ==
					PropsValues.MODULE_FRAMEWORK_DYNAMIC_INSTALL_START_LEVEL) {

				try {
					bundle.start();
				}
				catch (BundleException be) {
					_log.error(
						"Unable to start bundle " + bundle.getSymbolicName(),
						be);
				}
			}
		}

		if (fileInstallBundle != null) {
			fileInstallBundle.start(Bundle.START_TRANSIENT);
		}
	}

	private void _unregisterApplicationContext(
		ApplicationContext applicationContext) {

		List<ServiceRegistration<?>> serviceRegistrations =
			_springContextServices.remove(applicationContext);

		if (serviceRegistrations == null) {
			return;
		}

		for (ServiceRegistration<?> serviceRegistration :
				serviceRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (IllegalStateException ise) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Service registration " + serviceRegistration +
							" is already unregistered");
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void _validateModuleFrameworkBaseDirForEquinox()
		throws MalformedURLException {

		File baseDir = new File(PropsValues.MODULE_FRAMEWORK_BASE_DIR);

		baseDir = baseDir.getAbsoluteFile();

		// See LPS-71758. Do what Equinox does internally to get a file path and
		// validate it. Equinox converts a File into a URL using File#toURL(),
		// and later creates the OSGi persistence directory using the URL, which
		// does not properly handle special character escaping and decoding.

		URL url = baseDir.toURL();

		File equinoxBaseDir = new File(url.getFile());

		if (!baseDir.equals(equinoxBaseDir) && _log.isWarnEnabled()) {
			StringBundler sb = new StringBundler(6);

			sb.append("The module.framework.base.dir path \"");
			sb.append(baseDir);
			sb.append("\" contains characters that Equinox cannot handle. ");
			sb.append("The OSGi persistence data will be stored under \"");
			sb.append(equinoxBaseDir);
			sb.append("\"");

			_log.warn(sb.toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModuleFrameworkImpl.class);

	private static final Pattern _pattern = Pattern.compile(
		"(.*?)-\\d+\\.\\d+\\.\\d+(\\..+)?\\.jar");

	private Framework _framework;
	private final Map<ApplicationContext, List<ServiceRegistration<?>>>
		_springContextServices = new ConcurrentHashMap<>();

}