/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.spring.extender.context;

import com.liferay.spring.extender.blueprint.ModuleBeanFactoryPostProcessor;
import com.liferay.spring.extender.classloader.BundleResolverClassLoader;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;

import org.eclipse.gemini.blueprint.context.DelegatedExecutionOsgiBundleApplicationContext;
import org.eclipse.gemini.blueprint.context.support.OsgiBundleXmlApplicationContext;
import org.eclipse.gemini.blueprint.extender.OsgiApplicationContextCreator;
import org.eclipse.gemini.blueprint.extender.support.internal.ConfigUtils;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.InputStreamResource;

/**
 * @author Miguel Pastor
 */
public class ModuleApplicationContextCreator
	implements OsgiApplicationContextCreator {

	@Override
	public DelegatedExecutionOsgiBundleApplicationContext
			createApplicationContext(BundleContext bundleContext)
		throws Exception {

		Dictionary<String, String> headers =
			bundleContext.getBundle().getHeaders();

		String configs = headers.get("Spring-Context");

		if (configs == null) {
			return null;
		}

		Bundle bundle = bundleContext.getBundle();
		Bundle extenderBundle = _getExtenderBundle();

		ClassLoader classLoader = new BundleResolverClassLoader(
			bundle, extenderBundle);

		String liferayService = headers.get("Liferay-Service");

		ApplicationContext parentAppContext = null;

		if (liferayService != null) {
			parentAppContext = _buildParentContext(extenderBundle, classLoader);
		}

		String[] locations = ConfigUtils.getHeaderLocations(headers);

		OsgiBundleXmlApplicationContext osgiBundleXmlApplicationContext =
			new OsgiBundleXmlApplicationContext(locations, parentAppContext);

		osgiBundleXmlApplicationContext.setBundleContext(bundleContext);

		osgiBundleXmlApplicationContext.addBeanFactoryPostProcessor(
			new ModuleBeanFactoryPostProcessor(classLoader));

		return osgiBundleXmlApplicationContext;
	}

	private ApplicationContext _buildParentContext(
			Bundle bundle, ClassLoader classLoader)
		throws IOException {

		BundleContext bundleContext = bundle.getBundleContext();

		String liferayService = bundleContext.getBundle().getHeaders().get(
			"Liferay-Service");

		if (liferayService == null) {
			return null;
		}

		GenericApplicationContext applicationContext =
			new GenericApplicationContext();

		XmlBeanDefinitionReader xmlBeanDefinitionReader =
			new XmlBeanDefinitionReader(applicationContext);

		xmlBeanDefinitionReader.setValidating(false);
		xmlBeanDefinitionReader.setValidationMode(
			XmlBeanDefinitionReader.VALIDATION_NONE);

		Enumeration<URL> entries = bundle.findEntries(
			"META-INF/spring/parent", "*.xml", true);

		applicationContext.setClassLoader(classLoader);

		while (entries.hasMoreElements()) {
			URL xmlBeanDefinitionUrl = entries.nextElement();

			InputStream inputStream = xmlBeanDefinitionUrl.openStream();

			xmlBeanDefinitionReader.loadBeanDefinitions(
				new InputStreamResource(inputStream));
		}

		applicationContext.refresh();

		return applicationContext;
	}

	private Bundle _getExtenderBundle() {
		return FrameworkUtil.getBundle(ModuleApplicationContextCreator.class);
	}

}