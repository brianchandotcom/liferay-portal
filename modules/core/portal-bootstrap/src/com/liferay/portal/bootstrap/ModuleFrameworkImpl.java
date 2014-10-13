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

import aQute.bnd.header.Attrs;
import aQute.bnd.header.OSGiHeader;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;
import aQute.bnd.osgi.Verifier;
import aQute.bnd.version.Version;

import com.liferay.portal.cache.key.JavaMD5CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServiceLoader;
import com.liferay.portal.kernel.util.ServiceLoaderCondition;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.module.framework.ModuleFramework;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.internal.RegistryImpl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.security.CodeSource;
import java.security.NoSuchAlgorithmException;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;
import org.osgi.framework.wiring.FrameworkWiring;

import org.springframework.beans.factory.BeanIsAbstractException;
import org.springframework.context.ApplicationContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class ModuleFrameworkImpl implements ModuleFramework {

	@Override
	public Object addBundle(String location) throws PortalException {
		return addBundle(location, null);
	}

	@Override
	public Object addBundle(String location, InputStream inputStream)
		throws PortalException {

		return addBundle(location, inputStream, true);
	}

	public Object addBundle(
			String location, InputStream inputStream, boolean checkPermission)
		throws PortalException {

		if (_framework == null) {
			return null;
		}

		if (checkPermission) {
			_checkPermission();
		}

		BundleContext bundleContext = _framework.getBundleContext();

		if (inputStream != null) {
			Bundle bundle = getBundle(bundleContext, inputStream);

			if (bundle != null) {
				return bundle;
			}
		}

		try {
			return bundleContext.installBundle(location, inputStream);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new PortalException(be);
		}
	}

	/**
	 * @see com.liferay.modulesadmin.portlet.ModulesAdminPortlet#getBundle(
	 *      BundleContext, InputStream)
	 */
	public Bundle getBundle(
			BundleContext bundleContext, InputStream inputStream)
		throws PortalException {

		try {
			if (inputStream.markSupported()) {

				// 1 megabyte is more than enough for even the largest manifest
				// file

				inputStream.mark(1024 * 1000);
			}

			JarInputStream jarInputStream = new JarInputStream(inputStream);

			Manifest manifest = jarInputStream.getManifest();

			if (inputStream.markSupported()) {
				inputStream.reset();
			}

			Attributes attributes = manifest.getMainAttributes();

			String bundleSymbolicNameAttributeValue = attributes.getValue(
				Constants.BUNDLE_SYMBOLICNAME);

			Parameters parameters = OSGiHeader.parseHeader(
				bundleSymbolicNameAttributeValue);

			Set<String> bundleSymbolicNameSet = parameters.keySet();

			Iterator<String> bundleSymbolicNameIterator =
				bundleSymbolicNameSet.iterator();

			String bundleSymbolicName = bundleSymbolicNameIterator.next();

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
	public Map<String, List<URL>> getExtraPackageMap() {
		return _extraPackageMap;
	}

	@Override
	public List<URL> getExtraPackageURLs() {
		if (_extraPackageURLs != null) {
			return _extraPackageURLs;
		}

		_extraPackageLock.lock();

		try {
			List<URL> extraPackageURLs = new ArrayList<URL>();

			for (List<URL> urls : _extraPackageMap.values()) {
				extraPackageURLs.addAll(urls);
			}

			_extraPackageURLs = Collections.unmodifiableList(extraPackageURLs);
		}
		finally {
			_extraPackageLock.unlock();
		}

		return _extraPackageURLs;
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
		else {
			return StringPool.BLANK;
		}
	}

	@Override
	public void registerContext(Object context) {
		if (context == null) {
			return;
		}

		if ((context instanceof ApplicationContext) &&
			PropsValues.MODULE_FRAMEWORK_REGISTER_LIFERAY_SERVICES) {

			ApplicationContext applicationContext = (ApplicationContext)context;

			_registerApplicationContext(applicationContext);
		}
		else if (context instanceof ServletContext) {
			ServletContext servletContext = (ServletContext)context;

			_registerServletContext(servletContext);
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
		List<ServiceLoaderCondition> serviceLoaderConditions =
			ServiceLoader.load(ServiceLoaderCondition.class);

		ServiceLoaderCondition serviceLoaderCondition =
			serviceLoaderConditions.get(0);

		List<FrameworkFactory> frameworkFactories = ServiceLoader.load(
			FrameworkFactory.class, serviceLoaderCondition);

		FrameworkFactory frameworkFactory = frameworkFactories.get(0);

		Map<String, String> properties = _buildFrameworkProperties(
			frameworkFactory.getClass());

		_framework = frameworkFactory.newFramework(properties);

		_framework.init();

		_framework.start();

		RegistryUtil.setRegistry(
			new RegistryImpl(_framework.getBundleContext()));

		_setupInitialBundles();
	}

	@Override
	public void startRuntime() throws Exception {
		if (_framework == null) {
			return;
		}

		FrameworkStartLevel frameworkStartLevel = _framework.adapt(
			FrameworkStartLevel.class);

		frameworkStartLevel.setStartLevel(
			PropsValues.MODULE_FRAMEWORK_RUNTIME_START_LEVEL);
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
	public void stopFramework() throws Exception {
		if (_framework == null) {
			return;
		}

		_framework.stop();

		FrameworkEvent frameworkEvent = _framework.waitForStop(5000);

		if (_log.isInfoEnabled()) {
			_log.info(frameworkEvent);
		}

		RegistryUtil.setRegistry(null);
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

	private Map<String, String> _buildFrameworkProperties(Class<?> clazz) {
		Map<String, String> properties = new HashMap<String, String>();

		properties.put(
			Constants.BUNDLE_DESCRIPTION, ReleaseInfo.getReleaseInfo());
		properties.put(Constants.BUNDLE_NAME, ReleaseInfo.getName());
		properties.put(Constants.BUNDLE_VENDOR, ReleaseInfo.getVendor());
		properties.put(Constants.BUNDLE_VERSION, ReleaseInfo.getVersion());
		properties.put(
			FrameworkPropsKeys.FELIX_FILEINSTALL_DIR,
			_getFelixFileInstallDir());
		properties.put(
			FrameworkPropsKeys.FELIX_FILEINSTALL_LOG_LEVEL,
			_getFelixFileInstallLogLevel());
		properties.put(
			FrameworkPropsKeys.FELIX_FILEINSTALL_POLL,
			String.valueOf(PropsValues.MODULE_FRAMEWORK_AUTO_DEPLOY_INTERVAL));
		properties.put(
			FrameworkPropsKeys.FELIX_FILEINSTALL_TMPDIR,
			SystemProperties.get(SystemProperties.TMP_DIR));
		properties.put(
			Constants.FRAMEWORK_BEGINNING_STARTLEVEL,
			String.valueOf(PropsValues.MODULE_FRAMEWORK_BEGINNING_START_LEVEL));
		properties.put(
			Constants.FRAMEWORK_BUNDLE_PARENT,
			Constants.FRAMEWORK_BUNDLE_PARENT_APP);
		properties.put(
			Constants.FRAMEWORK_STORAGE,
			PropsValues.MODULE_FRAMEWORK_STATE_DIR);

		properties.put("eclipse.security", null);
		properties.put("java.security.manager", null);
		properties.put("org.osgi.framework.security", null);

		ProtectionDomain protectionDomain = clazz.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL codeSourceURL = codeSource.getLocation();

		properties.put(
			FrameworkPropsKeys.OSGI_FRAMEWORK, codeSourceURL.toExternalForm());

		File frameworkFile = new File(codeSourceURL.getFile());

		properties.put(
			FrameworkPropsKeys.OSGI_INSTALL_AREA, frameworkFile.getParent());

		Properties extraProperties = PropsUtil.getProperties(
			PropsKeys.MODULE_FRAMEWORK_PROPERTIES, true);

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

		String systemPackagesExtra = _getSystemPackagesExtra();

		properties.put(
			Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, systemPackagesExtra);

		return properties;
	}

	private String _calculateExportPackage(Jar jar) {
		StringBundler sb = new StringBundler();

		String delimiter = StringPool.BLANK;

		Map<String, Map<String, Resource>> directories = jar.getDirectories();

		for (String directory : directories.keySet()) {
			if (directory.equals("META-INF") ||
				directory.startsWith("META-INF/")) {

				continue;
			}

			if (directory.equals("OSGI-OPT") ||
				directory.startsWith("OSGI-OPT/")) {

				continue;
			}

			if (directory.equals(StringPool.SLASH)) {
				continue;
			}

			if (directory.endsWith(StringPool.SLASH)) {
				directory = directory.substring(0, directory.length() - 1);
			}

			if (directory.endsWith(StringPool.SLASH)) {
				directory = directory.substring(0, directory.length() - 1);
			}

			String className = directory.replace(
				StringPool.SLASH, StringPool.PERIOD);

			if (directories.get(directory) != null) {
				sb.append(delimiter);
				sb.append(className);

				delimiter = StringPool.COMMA;
			}
		}

		return sb.toString();
	}

	private Manifest _calculateManifest(URL url, Manifest manifest) {
		Analyzer analyzer = new Analyzer();

		Jar jar = null;

		try {
			URLConnection urlConnection = url.openConnection();

			String fileName = url.getFile();

			if (urlConnection instanceof JarURLConnection) {
				JarURLConnection jarURLConnection =
					(JarURLConnection)urlConnection;

				URL jarFileURL = jarURLConnection.getJarFileURL();

				fileName = jarFileURL.getFile();
			}

			File file = new File(fileName);

			if (!file.exists() || !file.canRead()) {
				return manifest;
			}

			fileName = file.getName();

			analyzer.setJar(new Jar(fileName, file));

			jar = analyzer.getJar();

			String bundleSymbolicName = fileName;

			Matcher matcher = _bundleSymbolicNamePattern.matcher(
				bundleSymbolicName);

			if (matcher.matches()) {
				bundleSymbolicName = matcher.group(1);
			}

			analyzer.setProperty(
				Analyzer.BUNDLE_SYMBOLICNAME, bundleSymbolicName);

			String exportPackage = _calculateExportPackage(jar);

			analyzer.setProperty(Analyzer.EXPORT_PACKAGE, exportPackage);

			analyzer.mergeManifest(manifest);

			String bundleVersion = analyzer.getProperty(
				Analyzer.BUNDLE_VERSION);

			if (bundleVersion != null) {
				bundleVersion = Builder.cleanupVersion(bundleVersion);

				analyzer.setProperty(Analyzer.BUNDLE_VERSION, bundleVersion);
			}

			return analyzer.calcManifest();
		}
		catch (Exception e) {
			_log.error(e, e);

			return manifest;
		}
		finally {
			if (jar != null) {
				jar.close();
			}

			analyzer.close();
		}
	}

	private void _checkPermission() throws PrincipalException {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker == null) || !permissionChecker.isOmniadmin()) {
			throw new PrincipalException();
		}
	}

	private String _getFelixFileInstallDir() {
		return PropsValues.MODULE_FRAMEWORK_PORTAL_DIR + StringPool.COMMA +
			StringUtil.merge(PropsValues.MODULE_FRAMEWORK_AUTO_DEPLOY_DIRS);
	}

	private String _getFelixFileInstallLogLevel() {
		int level = 0;

		if (_log.isDebugEnabled()) {
			level = 4;
		}
		else if (_log.isErrorEnabled()) {
			level = 1;
		}
		else if (_log.isInfoEnabled()) {
			level = 3;
		}
		else if (_log.isWarnEnabled()) {
			level = 2;
		}

		return String.valueOf(level);
	}

	private String _getHashcode(String[]... keys) {
		try {
			CacheKeyGenerator cacheKeyGenerator = new JavaMD5CacheKeyGenerator(
				128);

			for (String[] key : keys) {
				cacheKeyGenerator.append(key);
			}

			return String.valueOf(cacheKeyGenerator.finish());
		}
		catch (NoSuchAlgorithmException nsae) {
			throw new RuntimeException(nsae);
		}
	}

	private Set<Class<?>> _getInterfaces(Object bean) {
		Set<Class<?>> interfaces = new HashSet<Class<?>>();

		Class<?> beanClass = bean.getClass();

		interfaces.add(beanClass);

		for (Class<?> interfaceClass : beanClass.getInterfaces()) {
			interfaces.add(interfaceClass);
		}

		while ((beanClass = beanClass.getSuperclass()) != null) {
			interfaces.add(beanClass);

			for (Class<?> interfaceClass : beanClass.getInterfaces()) {
				if (!interfaces.contains(interfaceClass)) {
					interfaces.add(interfaceClass);
				}
			}
		}

		return interfaces;
	}

	private String _getSystemPackagesExtra() {
		String[] systemPackagesExtra =
			PropsValues.MODULE_FRAMEWORK_SYSTEM_PACKAGES_EXTRA;

		String hashcode = _getHashcode(
			systemPackagesExtra,
			PropsValues.MODULE_FRAMEWORK_SYSTEM_BUNDLE_IGNORED_FRAGMENTS);

		File coreDir = new File(
			PropsValues.LIFERAY_WEB_PORTAL_CONTEXT_TEMPDIR, "osgi");

		File cacheFile = new File(coreDir, "system-packages.txt");
		File hashcodeFile = new File(coreDir, "system-packages.hash");

		if (cacheFile.exists() && hashcodeFile.exists() &&
			_hasMatchingHashcode(hashcodeFile, hashcode)) {

			ObjectInputStream objectInputStream = null;

			try {
				objectInputStream = new ObjectInputStream(
					new FileInputStream(cacheFile));

				_extraPackageMap =
					(Map<String, List<URL>>)objectInputStream.readObject();

				return (String)objectInputStream.readObject();
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
			catch (ClassNotFoundException cnfe) {
				_log.error(cnfe, cnfe);
			}
			finally {
				if (objectInputStream != null) {
					try {
						objectInputStream.close();
					}
					catch (IOException ioe) {
						_log.error(ioe, ioe);
					}
				}
			}
		}

		_extraPackageMap = new TreeMap<String, List<URL>>();

		StringBundler sb = new StringBundler();

		for (String extraPackage : systemPackagesExtra) {
			sb.append(extraPackage);
			sb.append(StringPool.COMMA);
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		PrintStream err = System.err;

		try {
			System.setErr(
				new PrintStream(err) {

					@Override
					public void println(String string) {
						if (_log.isDebugEnabled()) {
							_log.debug(string);
						}
					}

				}
			);

			Enumeration<URL> enu = classLoader.getResources(
				"META-INF/MANIFEST.MF");

			while (enu.hasMoreElements()) {
				URL url = enu.nextElement();

				_processURL(
					sb, url,
					PropsValues.
						MODULE_FRAMEWORK_SYSTEM_BUNDLE_IGNORED_FRAGMENTS);
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
		finally {
			System.setErr(err);
		}

		_extraPackageMap = Collections.unmodifiableMap(_extraPackageMap);

		sb.setIndex(sb.index() - 1);

		if (_log.isTraceEnabled()) {
			String s = sb.toString();

			s = s.replace(",", "\n");

			_log.trace(
				"The portal's system bundle is exporting the following " +
					"packages:\n" +s);
		}

		if (!coreDir.exists()) {
			coreDir.mkdir();
		}

		ObjectOutputStream objectOutputStream = null;

		try {
			objectOutputStream = new ObjectOutputStream(
				new FileOutputStream(cacheFile));

			objectOutputStream.writeObject(_extraPackageMap);
			objectOutputStream.writeObject(sb.toString());

			FileUtil.write(hashcodeFile, hashcode);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
		finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}
		}

		return sb.toString();
	}

	private boolean _hasLazyActivationPolicy(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders();

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

	private boolean _hasMatchingHashcode(
		File hashcodeFile, String expectedHashcode) {

		try {
			String actualHashcode = FileUtil.read(hashcodeFile);

			if (actualHashcode.equals(expectedHashcode)) {
				return true;
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		return false;
	}

	private void _installInitialBundle(
		String location, List<Bundle> lazyActivationBundles,
		List<Bundle> startBundles, List<Bundle> refreshBundles) {

		boolean start = false;
		int startLevel = PropsValues.MODULE_FRAMEWORK_BEGINNING_START_LEVEL;

		int index = location.lastIndexOf(StringPool.AT);

		if (index != -1) {
			String[] parts = StringUtil.split(
				location.substring(index + 1), StringPool.COLON);

			for (String part : parts) {
				if (part.equals("start")) {
					start = true;
				}
				else {
					startLevel = GetterUtil.getInteger(part);
				}
			}

			location = location.substring(0, index);
		}

		InputStream inputStream = null;

		try {
			if (!location.startsWith("file:")) {
				location = "file:".concat(
					PropsValues.LIFERAY_LIB_PORTAL_DIR.concat(location));
			}

			URL initialBundleURL = new URL(location);

			try {
				inputStream = new BufferedInputStream(
					initialBundleURL.openStream());
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioe.getMessage());
				}

				return;
			}

			Bundle bundle = (Bundle)addBundle(
				initialBundleURL.toString(), inputStream, false);

			if ((bundle == null) || _isFragmentBundle(bundle)) {
				return;
			}

			if (!start && _hasLazyActivationPolicy(bundle)) {
				lazyActivationBundles.add(bundle);

				return;
			}

			if (((bundle.getState() & Bundle.UNINSTALLED) == 0) &&
				(startLevel > 0)) {

				BundleStartLevel bundleStartLevel = bundle.adapt(
					BundleStartLevel.class);

				bundleStartLevel.setStartLevel(startLevel);
			}

			if (start) {
				startBundles.add(bundle);
			}

			if ((bundle.getState() & Bundle.INSTALLED) != 0) {
				refreshBundles.add(bundle);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	private boolean _isFragmentBundle(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders();

		String fragmentHost = headers.get(Constants.FRAGMENT_HOST);

		if (fragmentHost == null) {
			return false;
		}

		return true;
	}

	private boolean _isIgnoredInterface(String interfaceClassName) {
		for (String ignoredClass :
				PropsValues.MODULE_FRAMEWORK_SERVICES_IGNORED_INTERFACES) {

			if (ignoredClass.equals(interfaceClassName) ||
				(ignoredClass.endsWith(StringPool.STAR) &&
				 interfaceClassName.startsWith(
					 ignoredClass.substring(0, ignoredClass.length() - 1)))) {

				return true;
			}
		}

		return false;
	}

	private void _processURL(
		StringBundler sb, URL url, String[] ignoredFragments) {

		Manifest manifest = null;

		try {
			manifest = new Manifest(url.openStream());
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			return;
		}

		Attributes attributes = manifest.getMainAttributes();

		String bundleSymbolicName = attributes.getValue(
			Constants.BUNDLE_SYMBOLICNAME);

		if (Validator.isNull(bundleSymbolicName)) {
			String urlString = url.toString();

			if (urlString.contains(PropsValues.LIFERAY_LIB_PORTAL_DIR)) {
				manifest = _calculateManifest(url, manifest);

				attributes = manifest.getMainAttributes();

				bundleSymbolicName = attributes.getValue(
					new Attributes.Name(Constants.BUNDLE_SYMBOLICNAME));

				if (Validator.isNull(bundleSymbolicName)) {
					return;
				}
			}
			else {
				return;
			}
		}

		String rootBundleSymbolicName = bundleSymbolicName;

		int index = rootBundleSymbolicName.indexOf(StringPool.SEMICOLON);

		if (index != -1) {
			rootBundleSymbolicName = rootBundleSymbolicName.substring(0, index);
		}

		for (String ignoredFragment : ignoredFragments) {
			String ignoredFramentPrefix = ignoredFragment.substring(
				0, ignoredFragment.length() - 1);

			if (rootBundleSymbolicName.equals(ignoredFragment) ||
				(ignoredFragment.endsWith(StringPool.STAR) &&
				 rootBundleSymbolicName.startsWith(ignoredFramentPrefix))) {

				return;
			}
		}

		String exportPackage = GetterUtil.getString(
			attributes.getValue(Constants.EXPORT_PACKAGE));

		Parameters parameters = OSGiHeader.parseHeader(exportPackage);

		for (Map.Entry<String, Attrs> entry : parameters.entrySet()) {
			String key = entry.getKey();

			List<URL> urls = _extraPackageMap.get(key);

			if (urls == null) {
				urls = new ArrayList<URL>();

				_extraPackageMap.put(key, urls);
			}

			urls.add(url);

			sb.append(key);

			Attrs value = entry.getValue();

			if (value.containsKey("version")) {
				sb.append(";version=\"");
				sb.append(value.get("version"));
				sb.append("\"");
			}

			sb.append(StringPool.COMMA);
		}
	}

	private void _registerApplicationContext(
		ApplicationContext applicationContext) {

		BundleContext bundleContext = _framework.getBundleContext();

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
				_registerService(bundleContext, beanName, bean);
			}
		}
	}

	private void _registerService(
		BundleContext bundleContext, String beanName, Object bean) {

		Set<Class<?>> interfaces = _getInterfaces(bean);

		if (interfaces.isEmpty()) {
			return;
		}

		List<String> names = new ArrayList<String>(interfaces.size());

		for (Class<?> interfaceClass : interfaces) {
			String interfaceClassName = interfaceClass.getName();

			if (!_isIgnoredInterface(interfaceClassName)) {
				names.add(interfaceClassName);
			}
		}

		if (names.isEmpty()) {
			return;
		}

		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		Map<String, Object> osgiBeanProperties =
			OSGiBeanProperties.Convert.fromObject(bean);

		if (osgiBeanProperties != null) {
			properties.putAll(osgiBeanProperties);
		}

		properties.put(ServicePropsKeys.BEAN_ID, beanName);
		properties.put(ServicePropsKeys.ORIGINAL_BEAN, Boolean.TRUE);
		properties.put(ServicePropsKeys.VENDOR, ReleaseInfo.getVendor());

		bundleContext.registerService(
			names.toArray(new String[names.size()]), bean, properties);
	}

	private void _registerServletContext(ServletContext servletContext) {
		BundleContext bundleContext = _framework.getBundleContext();

		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.put(
			ServicePropsKeys.BEAN_ID, ServletContext.class.getName());
		properties.put(ServicePropsKeys.ORIGINAL_BEAN, Boolean.TRUE);
		properties.put(ServicePropsKeys.VENDOR, ReleaseInfo.getVendor());

		bundleContext.registerService(
			new String[] {ServletContext.class.getName()}, servletContext,
			properties);
	}

	private void _setupInitialBundles() throws Exception {
		FrameworkWiring frameworkWiring = _framework.adapt(
			FrameworkWiring.class);

		List<Bundle> lazyActivationBundles = new ArrayList<Bundle>();
		List<Bundle> startBundles = new ArrayList<Bundle>();
		List<Bundle> refreshBundles = new ArrayList<Bundle>();

		for (String initialBundle :
				PropsValues.MODULE_FRAMEWORK_INITIAL_BUNDLES) {

			_installInitialBundle(
				initialBundle, lazyActivationBundles, startBundles,
				refreshBundles);
		}

		FrameworkListener frameworkListener = new StartupFrameworkListener(
			startBundles, lazyActivationBundles);

		frameworkWiring.refreshBundles(refreshBundles, frameworkListener);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModuleFrameworkImpl.class);

	private final Pattern _bundleSymbolicNamePattern = Pattern.compile(
		"(" + Verifier.SYMBOLICNAME.pattern() + ")(-[0-9])?.*\\.jar");
	private final Lock _extraPackageLock = new ReentrantLock();
	private Map<String, List<URL>> _extraPackageMap;
	private List<URL> _extraPackageURLs;
	private Framework _framework;

	private class StartupFrameworkListener implements FrameworkListener {

		public StartupFrameworkListener(
			List<Bundle> startBundles, List<Bundle> lazyActivationBundles) {

			_startBundles = startBundles;
			_lazyActivationBundles = lazyActivationBundles;
		}

		@Override
		public void frameworkEvent(FrameworkEvent frameworkEvent) {
			if (frameworkEvent.getType() != FrameworkEvent.PACKAGES_REFRESHED) {
				return;
			}

			for (Bundle bundle : _startBundles) {
				try {
					startBundle(bundle, 0, false);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			for (Bundle bundle : _lazyActivationBundles) {
				try {
					startBundle(bundle, Bundle.START_ACTIVATION_POLICY, false);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			try {
				Bundle bundle = frameworkEvent.getBundle();

				BundleContext bundleContext = bundle.getBundleContext();

				bundleContext.removeFrameworkListener(this);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		private final List<Bundle> _lazyActivationBundles;
		private final List<Bundle> _startBundles;

	}

}