/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.osgi.service;

import aQute.libg.header.OSGiHeader;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServiceLoader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.BundleListener;
import com.liferay.portal.osgi.FrameworkListener;
import com.liferay.portal.osgi.OSGiException;
import com.liferay.portal.osgi.ServiceListener;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.UniqueList;

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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.framework.startlevel.BundleStartLevel;

import org.springframework.beans.factory.BeanIsAbstractException;
import org.springframework.context.ApplicationContext;

/**
 * @author Raymond Augé
 */
public class OSGiServiceUtil {

	public static Object addBundle(String location) throws PortalException {
		return addBundle(location, null);
	}

	public static Object addBundle(String location, InputStream inputStream)
		throws PortalException {

		return _instance._addBundle(location, inputStream);
	}

	public static Framework getFramework() {
		return _instance._getFramework();
	}

	public static String getState(long bundleId) throws PortalException {
		return _instance._getState(bundleId);
	}

	public static void init() throws Exception {
		_instance._init();
	}

	public static void registerContext(Object context) {
		_instance._registerContext(context);
	}

	public static void setBundleStartLevel(long bundleId, int startLevel)
		throws PortalException {

		_instance._setBundleStartLevel(bundleId, startLevel);
	}

	public static void start() throws Exception {
		_instance._start();
	}

	public static void startBundle(long bundleId) throws PortalException {
		_instance._startBundle(bundleId);
	}

	public static void startBundle(long bundleId, int options)
		throws PortalException {

		_instance._startBundle(bundleId, options);
	}

	public static void stop() throws Exception {
		_instance._stop();
	}

	public static void stopBundle(long bundleId) throws PortalException {
		_instance._stopBundle(bundleId);
	}

	public static void stopBundle(long bundleId, int options)
		throws PortalException {

		_instance._stopBundle(bundleId, options);
	}

	public static void uninstallBundle(long bundleId) throws PortalException {
		_instance._uninstallBundle(bundleId);
	}

	public static void updateBundle(long bundleId) throws PortalException {
		_instance._updateBundle(bundleId);
	}

	public static void updateBundle(long bundleId, InputStream inputStream)
		throws PortalException {

		_instance._updateBundle(bundleId, inputStream);
	}

	private OSGiServiceUtil() {
	}

	private Object _addBundle(String location, InputStream inputStream)
		throws PortalException {

		_checkPermission();

		if (_framework == null) {
			return null;
		}

		BundleContext bundleContext = _framework.getBundleContext();

		try {
			return bundleContext.installBundle(location, inputStream);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
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
			String.valueOf(PropsValues.OSGI_FRAMEWORK_BEGINNING_START_LEVEL));
		properties.put(
			Constants.FRAMEWORK_BUNDLE_PARENT,
			Constants.FRAMEWORK_BUNDLE_PARENT_APP);
		properties.put(
			Constants.FRAMEWORK_STORAGE, PropsValues.OSGI_FRAMEWORK_STORAGE);

		UniqueList<String> packages = new UniqueList<String>();

		try {
			_getBundleExportPackages(
				PropsValues.OSGI_SYSTEM_BUNDLE_EXPORT_PACKAGES, packages);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		packages.addAll(Arrays.asList(PropsValues.OSGI_SYSTEM_PACKAGES_EXTRA));

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

	private void _getBundleExportPackages(
			String[] bundleSymbolicNames, List<String> packages)
		throws Exception {

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

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

					if (javaPackageMap.containsKey("version")) {
						String version = javaPackageMap.get("version");

						javaPackage = javaPackage.concat(
							";version=\"").concat(version).concat("\"");
					}

					packages.add(javaPackage);
				}

				break;
			}
		}
	}

	private Framework _getFramework() {
		return _framework;
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

	private String _getState(long bundleId) throws PortalException {
		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
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

	private void _init() throws Exception {
		List<FrameworkFactory> frameworkFactories = ServiceLoader.load(
			FrameworkFactory.class);

		if (frameworkFactories.isEmpty()) {
			return;
		}

		FrameworkFactory frameworkFactory = frameworkFactories.get(0);

		Map<String, String> properties = _buildProperties();

		_framework = frameworkFactory.newFramework(properties);

		_framework.init();

		BundleContext bundleContext = _framework.getBundleContext();

		BundleListener bundleListener = new BundleListener();

		bundleContext.addBundleListener(bundleListener);

		FrameworkListener frameworkListener = new FrameworkListener();

		bundleContext.addFrameworkListener(frameworkListener);

		ServiceListener serviceListener = new ServiceListener(bundleContext);

		bundleContext.addServiceListener(
			serviceListener, _PORTAL_SERVICE_FILTER);
	}

	private void _registerContext(Object context) {
		if ((context == null) || !(context instanceof ApplicationContext) ||
			!PropsValues.OSGI_REGISTER_LIFERAY_SERVICES) {

			return;
		}

		ApplicationContext applicationContext = (ApplicationContext)context;

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

	private ServiceReference<?> _registerService(
		BundleContext bundleContext, String beanName, Object bean) {

		Set<Class<?>> interfaces = _getInterfaces(bean);

		List<String> names = new ArrayList<String>();

		for (Class<?> interfaceClass : interfaces) {
			names.add(interfaceClass.getName());
		}

		if (names.isEmpty()) {
			return null;
		}

		Hashtable<String,Object> properties = new Hashtable<String, Object>();

		properties.put("bean.name", beanName);
		properties.put("original.bean", Boolean.TRUE);

		ServiceRegistration<?> registerService = bundleContext.registerService(
			names.toArray(new String[names.size()]), bean, properties);

		return registerService.getReference();
	}

	private void _setBundleStartLevel(long bundleId, int startLevel)
		throws PortalException {

		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		BundleStartLevel bundleStartLevel = (BundleStartLevel)bundle;

		bundleStartLevel.setStartLevel(startLevel);
	}

	private void _start() throws Exception {
		if (_framework != null) {
			_framework.start();
		}
	}

	private void _startBundle(long bundleId) throws PortalException {
		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.start();
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	private void _startBundle(long bundleId, int options)
		throws PortalException {

		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.start(options);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	private void _stop() throws Exception {
		if (_framework != null) {
			_framework.stop();
		}
	}

	private void _stopBundle(long bundleId) throws PortalException {
		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.stop();
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	private void _stopBundle(long bundleId, int options)
		throws PortalException {

		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.stop(options);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	private void _uninstallBundle(long bundleId) throws PortalException {
		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.uninstall();
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	private void _updateBundle(long bundleId) throws PortalException {
		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.update();
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	private void _updateBundle(long bundleId, InputStream inputStream)
		throws PortalException {

		_checkPermission();

		Bundle bundle = _getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.update(inputStream);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	private static final String _PORTAL_SERVICE_FILTER =
		"(&(portal.service=*)(!(portal.service.core=*))" +
			"(!(portal.service.previous=*)))";

	private static final Log _log = LogFactoryUtil.getLog(
		OSGiServiceUtil.class);

	private static OSGiServiceUtil _instance = new OSGiServiceUtil();

	private Framework _framework;

}