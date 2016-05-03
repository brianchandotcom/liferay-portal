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

package com.liferay.portal.template.freemarker.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import freemarker.core.TemplateClassResolver;

import freemarker.template.TemplateException;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Tomas Polesovsky
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class LiferayTemplateClassResolverTest {

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			LiferayTemplateClassResolverTest.class);

		_serviceTracker = new ServiceTracker<>(
			bundle.getBundleContext(), TemplateClassResolver.class, null);

		_serviceTracker.open();

		_liferayTemplateClassResolver = _serviceTracker.getService();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTracker.close();
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

	@Test
	public void testResolveAllowedClassByClassName() throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			"allowedClasses", "freemarker.template.utility.ClassUtil");
		properties.put("restrictedClasses", "");

		_updateProperties(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ClassUtil", null, null);
	}

	@Test
	public void testResolveAllowedClassByStar() throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("allowedClasses", "freemarker.template.utility.*");
		properties.put("restrictedClasses", "");

		_updateProperties(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ClassUtil", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveAllowedExecuteClass() throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("allowedClasses", "freemarker.template.utility.*");
		properties.put("restrictedClasses", "");

		_updateProperties(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.Execute", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveAllowedObjectConstructorClass() throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("allowedClasses", "freemarker.template.utility.*");
		properties.put("restrictedClasses", "");

		_updateProperties(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ObjectConstructor", null, null);
	}

	@Test
	public void testResolveAllowedPortalClass() throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			"allowedClasses", "com.liferay.portal.kernel.model.User");

		_updateProperties(properties);

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

		_updateProperties(properties);

		_liferayTemplateClassResolver.resolve(
			"com.liferay.portal.kernel.model.User", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveClassClass() throws Exception {
		_liferayTemplateClassResolver.resolve("java.lang.Class", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveClassLoaderClass() throws Exception {
		_liferayTemplateClassResolver.resolve(
			"java.lang.ClassLoader", null, null);
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

	@Test(expected = TemplateException.class)
	public void testResolveThreadClass() throws Exception {
		_liferayTemplateClassResolver.resolve("java.lang.Thread", null, null);
	}

	private void _updateProperties(Dictionary<String, Object> dictionary)
		throws Exception {

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		Bundle bundle = FrameworkUtil.getBundle(
			_liferayTemplateClassResolver.getClass());

		final BundleContext bundleContext = bundle.getBundleContext();

		ServiceListener serviceListener = new ServiceListener() {

			@Override
			public void serviceChanged(ServiceEvent serviceEvent) {
				if (serviceEvent.getType() != ServiceEvent.MODIFIED) {
					return;
				}

				ServiceReference<?> serviceReference =
					serviceEvent.getServiceReference();

				Object service = bundleContext.getService(serviceReference);

				if (service == _liferayTemplateClassResolver) {
					countDownLatch.countDown();
				}
			}

		};

		bundleContext.addServiceListener(serviceListener);

		try {
			_freemarkerTemplateConfiguration.update(dictionary);

			countDownLatch.await();
		}
		finally {
			bundleContext.removeServiceListener(serviceListener);
		}
	}

	private static TemplateClassResolver _liferayTemplateClassResolver;
	private static ServiceTracker<TemplateClassResolver, TemplateClassResolver>
		_serviceTracker;

	private Configuration _freemarkerTemplateConfiguration;
	private Dictionary<String, Object> _properties;

}