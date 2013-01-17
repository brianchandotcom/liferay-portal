/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.osgi.bootstrap;

import aQute.libg.header.OSGiHeader;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServiceLoader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;

import org.springframework.beans.factory.BeanIsAbstractException;
import org.springframework.context.ApplicationContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class ModuleFrameworkImpl
	implements ModuleFramework, ModuleFrameworkConstants {

	public Object addBundle(String location) throws PortalException {
		return addBundle(location, null);
	}

	public Object addBundle(String location, InputStream inputStream)
		throws PortalException {

		return _addBundle(location, inputStream, true);
	}

	public void getBundleExportPackages(
			String[] bundleSymbolicNames, List<String> packages)
		throws Exception {

		ClassLoader classLoader = PACLClassLoaderUtil.getPortalClassLoader();

		Enumeration<URL> enu = classLoader.getResources("META-INF/MANIFEST.MF");

		while (enu.hasMoreElements()) {
			URL url = enu.nextElement();

			Manifest manifest = new Manifest(url.openStream());

			Attributes attributes = manifest.getMainAttributes();

			String bundleSymbolicName = attributes.getValue(
				Constants.BUNDLE_SYMBOLICNAME);

			if (Validator.isNull(bundleSymbolicName)) {
				continue;
			}

			for (String curBundleSymbolicName : bundleSymbolicNames) {
				if (!bundleSymbolicName.startsWith(curBundleSymbolicName)) {
					continue;
				}

				String exportPackage = attributes.getValue(
					Constants.EXPORT_PACKAGE);

				Map<String, Map<String, String>> exportPackageMap =
					OSGiHeader.parseHeader(exportPackage);

				for (Map.Entry<String, Map<String, String>> entry :
						exportPackageMap.entrySet()) {

					String javaPackage = entry.getKey();
					Map<String, String> javaPackageMap = entry.getValue();

					StringBundler sb = new StringBundler(6);

					sb.append(javaPackage);
					sb.append(";");
					sb.append(Constants.VERSION_ATTRIBUTE);
					sb.append("=\"");

					if (javaPackageMap.containsKey(
							Constants.VERSION_ATTRIBUTE)) {

						String version = javaPackageMap.get(
							Constants.VERSION_ATTRIBUTE);

						sb.append(version);
					}
					else {
						String bundleVersionString = attributes.getValue(
							Constants.BUNDLE_VERSION);

						sb.append(bundleVersionString);
					}

					sb.append("\"");

					javaPackage = sb.toString();

					packages.add(javaPackage);
				}

				break;
			}
		}
	}

	public Framework getFramework() {
		return _framework;
	}

	public String getState(long bundleId) throws PortalException {
		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

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

	public void setBundleStartLevel(long bundleId, int startLevel)
		throws PortalException {

		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

		if (bundle == null) {
			throw new PortalException("No bundle with ID " + bundleId);
		}

		BundleStartLevel bundleStartLevel = bundle.adapt(
			BundleStartLevel.class);

		bundleStartLevel.setStartLevel(startLevel);
	}

	public void startBundle(long bundleId) throws PortalException {
		startBundle(bundleId, 0);
	}

	public void startBundle(long bundleId, int options) throws PortalException {
		_startBundle(bundleId, 0, true);
	}

	public void startFramework() throws Exception {
		List<FrameworkFactory> frameworkFactories = ServiceLoader.load(
			FrameworkFactory.class);

		if (frameworkFactories.isEmpty()) {
			return;
		}

		FrameworkFactory frameworkFactory = frameworkFactories.get(0);

		Map<String, String> properties = _buildProperties();

		_framework = frameworkFactory.newFramework(properties);

		_framework.init();

		_framework.start();
	}

	public void startRuntime() throws Exception {
		if (_framework == null) {
			return;
		}

		FrameworkStartLevel frameworkStartLevel = _framework.adapt(
			FrameworkStartLevel.class);

		frameworkStartLevel.setStartLevel(
			PropsValues.MODULE_FRAMEWORK_RUNTIME_START_LEVEL,
			(FrameworkListener)null);
	}

	public void stopBundle(long bundleId) throws PortalException {
		stopBundle(bundleId, 0);
	}

	public void stopBundle(long bundleId, int options) throws PortalException {
		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

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

	public void stopFramework() throws Exception {
		if (_framework == null) {
			return;
		}

		_framework.stop();
	}

	public void stopRuntime() throws Exception {
		if (_framework == null) {
			return;
		}

		FrameworkStartLevel frameworkStartLevel = _framework.adapt(
			FrameworkStartLevel.class);

		frameworkStartLevel.setStartLevel(
			PropsValues.MODULE_FRAMEWORK_BEGINNING_START_LEVEL,
			(FrameworkListener)null);
	}

	public void uninstallBundle(long bundleId) throws PortalException {
		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

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

	public void updateBundle(long bundleId) throws PortalException {
		updateBundle(bundleId, null);
	}

	public void updateBundle(long bundleId, InputStream inputStream)
		throws PortalException {

		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

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

	private Object _addBundle(
			String location, InputStream inputStream, boolean checkPermissions)
		throws PortalException {

		if (_framework == null) {
			return null;
		}

		if (checkPermissions) {
			_checkPermission();
		}

		BundleContext bundleContext = _framework.getBundleContext();

		try {
			return bundleContext.installBundle(location, inputStream);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new PortalException(be);
		}
	}

	private Map<String, String> _buildProperties() {
		Map<String, String> properties = new HashMap<String, String>();

		properties.put(
			Constants.BUNDLE_DESCRIPTION, ReleaseInfo.getReleaseInfo());
		properties.put(Constants.BUNDLE_NAME, ReleaseInfo.getName());
		properties.put(Constants.BUNDLE_VENDOR, ReleaseInfo.getVendor());
		properties.put(Constants.BUNDLE_VERSION, ReleaseInfo.getVersion());
		properties.put(
			Constants.FRAMEWORK_BEGINNING_STARTLEVEL,
			String.valueOf(PropsValues.MODULE_FRAMEWORK_BEGINNING_START_LEVEL));
		properties.put(
			Constants.FRAMEWORK_BUNDLE_PARENT,
			Constants.FRAMEWORK_BUNDLE_PARENT_APP);
		properties.put(
			Constants.FRAMEWORK_STORAGE,
			PropsValues.MODULE_FRAMEWORK_STATE_DIR);

		UniqueList<String> packages = new UniqueList<String>();

		try {
			getBundleExportPackages(
				PropsValues.MODULE_FRAMEWORK_SYSTEM_BUNDLE_EXPORT_PACKAGES,
				packages);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		packages.addAll(
			Arrays.asList(PropsValues.MODULE_FRAMEWORK_SYSTEM_PACKAGES_EXTRA));

		Collections.sort(packages);

		properties.put(
			Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
			StringUtil.merge(packages));

		return properties;
	}

	private void _checkPermission() throws PrincipalException {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker == null) || !permissionChecker.isOmniadmin()) {
			throw new PrincipalException();
		}
	}

	private Bundle _getBundle(long bundleId) {
		if (_framework == null) {
			return null;
		}

		BundleContext bundleContext = _framework.getBundleContext();

		return bundleContext.getBundle(bundleId);
	}

	private Set<Class<?>> _getInterfaces(Object bean) {
		Set<Class<?>> interfaces = new HashSet<Class<?>>();

		Class<?> beanClass = bean.getClass();

		for (Class<?> interfaceClass : beanClass.getInterfaces()) {
			interfaces.add(interfaceClass);
		}

		while ((beanClass = beanClass.getSuperclass()) != null) {
			for (Class<?> interfaceClass : beanClass.getInterfaces()) {
				if (!interfaces.contains(interfaceClass)) {
					interfaces.add(interfaceClass);
				}
			}
		}

		return interfaces;
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
			names.add(interfaceClass.getName());
		}

		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.put(SERVICE_PROPERTY_KEY_BEAN_ID, beanName);
		properties.put(SERVICE_PROPERTY_KEY_ORIGINAL_BEAN, Boolean.TRUE);
		properties.put(
			SERVICE_PROPERTY_KEY_SERVICE_VENDOR, ReleaseInfo.getVendor());

		bundleContext.registerService(
			names.toArray(new String[names.size()]), bean, properties);
	}

	private void _registerServletContext(ServletContext servletContext) {
		BundleContext bundleContext = _framework.getBundleContext();

		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.put(
			SERVICE_PROPERTY_KEY_BEAN_ID, ServletContext.class.getName());
		properties.put(SERVICE_PROPERTY_KEY_ORIGINAL_BEAN, Boolean.TRUE);
		properties.put(
			SERVICE_PROPERTY_KEY_SERVICE_VENDOR, ReleaseInfo.getVendor());

		bundleContext.registerService(
			new String[] {ServletContext.class.getName()}, servletContext,
			properties);
	}

	private void _startBundle(
			long bundleId, int options, boolean checkPermissions)
		throws PortalException {

		if (checkPermissions) {
			_checkPermission();
		}

		Bundle bundle = _getBundle(bundleId);

		if (bundle == null) {
			throw new PortalException("No bundle with ID " + bundleId);
		}

		try {
			bundle.start(options);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new PortalException(be);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ModuleFrameworkUtil.class);

	private Framework _framework;

}