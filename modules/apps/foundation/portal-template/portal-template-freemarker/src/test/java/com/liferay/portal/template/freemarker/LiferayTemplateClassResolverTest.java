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

package com.liferay.portal.template.freemarker;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import freemarker.core.TemplateClassResolver;

import freemarker.template.TemplateException;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Tomas Polesovsky
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class LiferayTemplateClassResolverTest {

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(objectClass=" + TemplateClassResolver.class.getName() + "))");

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();

		_liferayTemplateClassResolver =
			(TemplateClassResolver)_serviceTracker.getService();
	}

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			_liferayTemplateClassResolver.getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		try {
			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			_freemarkerTemplateConfiguration =
				configurationAdmin.getConfiguration(
					"com.liferay.portal.template.freemarker.configuration." +
						"FreeMarkerEngineConfiguration",
					null);

			_properties = _freemarkerTemplateConfiguration.getProperties();
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@After
	public void tearDown() throws Exception {
		_freemarkerTemplateConfiguration.update(_properties);
	}

	@Test()
	public void testResolveAllowedClassByClassName() throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			"allowedClasses", "freemarker.template.utility.ClassUtil");
		properties.put("restrictedClasses", "");

		_freemarkerTemplateConfiguration.update(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ClassUtil", null, null);
	}

	@Test()
	public void testResolveAllowedClassByStar() throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("allowedClasses", "freemarker.template.utility.*");
		properties.put("restrictedClasses", "");

		_freemarkerTemplateConfiguration.update(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ClassUtil", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveAllowedExecuteClass() throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("allowedClasses", "freemarker.template.utility.*");
		properties.put("restrictedClasses", "");

		_freemarkerTemplateConfiguration.update(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.Execute", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveAllowedObjectConstructorClass() throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("allowedClasses", "freemarker.template.utility.*");
		properties.put("restrictedClasses", "");

		_freemarkerTemplateConfiguration.update(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ObjectConstructor", null, null);
	}

	@Test
	public void testResolveAllowedPortalClass() throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			"allowedClasses", "com.liferay.portal.kernel.model.User");

		_freemarkerTemplateConfiguration.update(properties);

		_liferayTemplateClassResolver.resolve(
			"com.liferay.portal.kernel.model.User", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveAllowedPortalClassExplicitlyRestricted()
		throws Exception {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			"allowedClasses", "com.liferay.portal.kernel.model.User");
		properties.put(
			"restrictedClasses", "com.liferay.portal.kernel.model.*");

		_freemarkerTemplateConfiguration.update(properties);

		_liferayTemplateClassResolver.resolve(
			"com.liferay.portal.kernel.model.User", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveExecuteClass() throws Exception {
		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.Execute", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveNotAllowedPortalClass() throws Exception {
		_liferayTemplateClassResolver.resolve(
			"com.liferay.portal.kernel.model.User", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveObjectConstructorClass() throws Exception {
		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ObjectConstructor", null, null);
	}

	private static TemplateClassResolver _liferayTemplateClassResolver;
	private static ServiceTracker _serviceTracker;

	private Configuration _freemarkerTemplateConfiguration;
	private Dictionary<String, Object> _properties;

}