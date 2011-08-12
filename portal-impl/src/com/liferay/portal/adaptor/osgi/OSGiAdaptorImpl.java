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

import com.liferay.portal.kernel.adaptor.Adaptor;
import com.liferay.portal.kernel.adaptor.AdaptorException;
import com.liferay.portal.kernel.adaptor.AdaptorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServiceLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.UniqueList;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
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

import javax.servlet.ServletContext;

import org.aopalliance.intercept.MethodInterceptor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.Advised;
import org.springframework.context.ApplicationContext;

/**
 * @author Raymond Augé
 */
public class OSGiAdaptorImpl implements Adaptor {

	public void init(
			ServletContext servletContext, Object applicationContext)
		throws AdaptorException {

		try {
			doInit(servletContext, applicationContext);
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
				_framework.waitForStop(0);
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
			Constants.FRAMEWORK_BUNDLE_PARENT,
			Constants.FRAMEWORK_BUNDLE_PARENT_APP);
		properties.put(
			Constants.FRAMEWORK_STORAGE, PropsValues.OSGI_FRAMEWORK_STORAGE);

		UniqueList<String> packages = new UniqueList<String>();

		getBundleExportPackages(
			Company.class, "com.liferay.portal.service", packages);
		getBundleExportPackages(
			CompanyImpl.class, "com.liferay.portal.impl", packages);
		getBundleExportPackages(
			MVCPortlet.class, "com.liferay.util.bridges", packages);
		getBundleExportPackages(
			"com.liferay.taglib.util.ThemeUtil",
			"com.liferay.util.taglib", packages);
		getBundleExportPackages(
			UniqueList.class, "com.liferay.util.java", packages);

		packages.addAll(Arrays.asList(PropsValues.OSGI_SYSTEM_PACKAGES_EXTRA));

		Collections.sort(packages);

		properties.put(
			Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
			StringUtil.merge(packages));

		return properties;
	}

	protected void doInit(
			ServletContext servletContext, Object applicationContext)
		throws Exception {

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

		bundleContext.addServiceListener(
			new ServiceListener(bundleContext), _PORTAL_SERVICE_FILTER);

		registerApplicationContext((ApplicationContext)applicationContext);
	}

	protected void getBundleExportPackages(
		Class<?> clazz, String bundleSymbolicName, List<String> packages) {

		Manifest manifest = getBundleManifest(clazz, bundleSymbolicName);

		if (manifest != null) {
			Attributes attributes = manifest.getMainAttributes();

			String value = attributes.getValue(Constants.EXPORT_PACKAGE);

			String[] values = StringUtil.split(value);

			packages.addAll(Arrays.asList(values));
		}
	}

	protected void getBundleExportPackages(
		String className, String bundleSymbolicName, List<String> packages) {

		try {
			getBundleExportPackages(
				Class.forName(className), bundleSymbolicName, packages);
		}
		catch (ClassNotFoundException cnfe) {
			_log.error(cnfe, cnfe);
		}
	}

	protected Manifest getBundleManifest(
		Class<?> clazz, String bundleSymbolicName) {

		try {
			ClassLoader classLoader = clazz.getClassLoader();

			Enumeration<URL> enu = classLoader.getResources(
				"META-INF/MANIFEST.MF");

			while (enu.hasMoreElements()) {
				URL url = enu.nextElement();

				Manifest manifest = new Manifest(url.openStream());

				Attributes attributes = manifest.getMainAttributes();

				String curBundleSymoblicName = attributes.getValue(
					Constants.BUNDLE_SYMBOLICNAME);

				if (Validator.isNotNull(curBundleSymoblicName) &&
					curBundleSymoblicName.startsWith(bundleSymbolicName)) {

					return manifest;
				}
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		return null;
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
			catch (Exception e) {
				_log.error(e.getMessage());
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

	private static Log _log = LogFactoryUtil.getLog(AdaptorUtil.class);

	private Framework _framework;

}