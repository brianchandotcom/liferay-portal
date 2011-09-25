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

package com.liferay.portal.adaptor.osgi;

import aQute.libg.header.OSGiHeader;

import com.liferay.portal.kernel.adaptor.Adaptor;
import com.liferay.portal.kernel.adaptor.AdaptorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServiceLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.UniqueList;

import java.io.Serializable;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.aopalliance.intercept.MethodInterceptor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.BeanIsAbstractException;
import org.springframework.context.ApplicationContext;

/**
 * @author Raymond Augé
 */
public class OSGiAdaptorImpl implements Adaptor, OSGiAdaptor {

	public Framework getFramework() {
		return _framework;
	}

	public void init(Object applicationContext) throws AdaptorException {
		try {
			doInit(applicationContext);
		}
		catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	public void start() throws AdaptorException {
		try {
			if (_framework != null) {
				_framework.start();
			}
		}
		catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	public void stop() throws AdaptorException {
		try {
			if (_framework != null) {
				_framework.stop();
			}
		}
		catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	protected Map<String, String> buildProperties() {
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
			getBundleExportPackages(
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

	protected void doInit(Object applicationContext) throws Exception {
		List<FrameworkFactory> frameworkFactories = ServiceLoader.load(
			FrameworkFactory.class);

		if (frameworkFactories.isEmpty()) {
			return;
		}

		FrameworkFactory frameworkFactory = frameworkFactories.get(0);

		Map<String, String> properties = buildProperties();

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

		registerApplicationContext((ApplicationContext)applicationContext);
	}

	protected void getBundleExportPackages(
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

	protected List<Class<?>> getInterfaces(Class<?> clazz) {
		Class<?>[] interfacesArray = clazz.getInterfaces();

		List<Class<?>> interfaces = new ArrayList<Class<?>>(
			interfacesArray.length);

		for (Class<?> interfaceClass : interfacesArray) {
			interfaces.add(interfaceClass);
		}

		return interfaces;
	}

	protected void registerApplicationContext(
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
				registerApplicationContext(bundleContext, beanName, bean);
			}
		}
	}

	protected void registerApplicationContext(
		BundleContext bundleContext, String beanName, Object bean) {

		Class<?> beanClazz = bean.getClass();

		List<Class<?>> interfaces = new ArrayList<Class<?>>();

		interfaces.addAll(getInterfaces(beanClazz));

		while (beanClazz.getSuperclass() != null) {
			beanClazz = beanClazz.getSuperclass();

			interfaces.addAll(getInterfaces(beanClazz));
		}

		List<String> names = new ArrayList<String>();

		for (Class<?> interfaceClass : interfaces) {
			String name = interfaceClass.getName();

			if (name.equals(Advised.class.getName()) ||
				name.equals(MethodInterceptor.class.getName()) ||
				name.equals(Serializable.class.getName()) ||
				name.equals(SpringProxy.class.getName())) {

				continue;
			}

			names.add(name);
		}

		if (names.isEmpty()) {
			return;
		}

		Hashtable<String,Object> properties = new Hashtable<String, Object>();

		properties.put(Constants.SERVICE_RANKING, 1000);
		properties.put(Constants.SERVICE_VENDOR, ReleaseInfo.getVendor());

		properties.put(OSGiConstants.PORTAL_SERVICE, Boolean.TRUE);
		properties.put(OSGiConstants.PORTAL_SERVICE_BEAN_NAME, beanName);
		properties.put(OSGiConstants.PORTAL_SERVICE_CORE, Boolean.TRUE);

		bundleContext.registerService(
			names.toArray(new String[names.size()]), bean, properties);
	}

	private static final String _PORTAL_SERVICE_FILTER =
		"(&(portal.service=*)(!(portal.service.core=*))" +
			"(!(portal.service.previous=*)))";

	private static Log _log = LogFactoryUtil.getLog(OSGiAdaptorImpl.class);

	private Framework _framework;

}