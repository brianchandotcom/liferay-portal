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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.portlet.bundle.resourcebundletracker.TestPortlet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;

import java.util.HashMap;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 * @author Tomas Polesovsky
 */
public class ResourceBundleTrackerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.resourcebundletracker"));

	@BeforeClass
	public static void setUpClass() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=" + TestPortlet.PORTLET_NAME +
				")(objectClass=javax.portlet.Portlet))");

		_genericPortletServiceTracker = registry.trackServices(filter);

		_genericPortletServiceTracker.open();

		_genericPortlet = _genericPortletServiceTracker.getService();
	}

	@AfterClass
	public static void tearDownClass() {
		_genericPortletServiceTracker.close();
	}

	@Test
	public void testGetResourceBundleFromPortletConfig() throws Exception {
		PortletConfig portletConfig = _genericPortlet.getPortletConfig();

		Locale locale = LocaleUtil.fromLanguageId("es_ES");

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		Assert.assertEquals(
			"esto", ResourceBundleUtil.getString(resourceBundle, "this"));
		Assert.assertEquals(
			"es", ResourceBundleUtil.getString(resourceBundle, "is"));
		Assert.assertEquals(
			"un", ResourceBundleUtil.getString(resourceBundle, "a"));
		Assert.assertEquals(
			"prueba", ResourceBundleUtil.getString(resourceBundle, "test"));
		Assert.assertEquals(
			"paquete de recursos",
			ResourceBundleUtil.getString(resourceBundle, "resourcebundle"));
		Assert.assertEquals(
			"clave de extensión del paquete de recursos",
			ResourceBundleUtil.getString(
				resourceBundle, "resourcebundle-extension-key"));
	}

	@Test
	public void testResourceBundlesHierarchy() {
		PortletConfig portletConfig = _genericPortlet.getPortletConfig();

		ServiceRegistration<ResourceBundle> serviceRegistrationA =
			registerResourceBundle(
				createResourceBundle(
					"common-key", "th_TH_TH", "th_TH_TH", "th_TH_TH"),
				"th_TH_TH", 100);

		portletConfig.getResourceBundle(new Locale("th", "TH", "TH"));

		ServiceRegistration<ResourceBundle> serviceRegistrationB =
			registerResourceBundle(
				createResourceBundle("common-key", "th_TH", "th_TH", "th_TH"),
				"th_TH", 100);
		ServiceRegistration<ResourceBundle> serviceRegistrationC =
			registerResourceBundle(
				createResourceBundle("common-key", "th", "th", "th"), "th",
				100);
		ServiceRegistration<ResourceBundle> serviceRegistrationD =
			registerResourceBundle(
				createResourceBundle(
					"common-key", "root-bundle", "root-bundle", "root-bundle"),
				"", 100);

		ResourceBundle portletResourceBundleA = portletConfig.getResourceBundle(
			new Locale("th", "TH", "TH"));
		ResourceBundle portletResourceBundleB = portletConfig.getResourceBundle(
			new Locale("th", "TH"));
		ResourceBundle portletResourceBundleC = portletConfig.getResourceBundle(
			new Locale("th"));
		ResourceBundle portletResourceBundleD = portletConfig.getResourceBundle(
			new Locale(""));

		Assert.assertEquals(
			"th_TH_TH",
			ResourceBundleUtil.getString(portletResourceBundleA, "th_TH_TH"));
		Assert.assertEquals(
			"th_TH",
			ResourceBundleUtil.getString(portletResourceBundleA, "th_TH"));
		Assert.assertEquals(
			"th",
			ResourceBundleUtil.getString(portletResourceBundleA, "th"));
		Assert.assertEquals(
			"root-bundle",
			ResourceBundleUtil.getString(
				portletResourceBundleA, "root-bundle"));
		Assert.assertEquals(
			"th_TH_TH",
			ResourceBundleUtil.getString(portletResourceBundleA, "common-key"));
		Assert.assertEquals(
			"th_TH",
			ResourceBundleUtil.getString(portletResourceBundleB, "common-key"));
		Assert.assertFalse(portletResourceBundleB.containsKey("th_TH_TH"));
		Assert.assertEquals(
			"th",
			ResourceBundleUtil.getString(portletResourceBundleC, "common-key"));
		Assert.assertFalse(portletResourceBundleC.containsKey("th_TH"));
		Assert.assertEquals(
			"root-bundle",
			ResourceBundleUtil.getString(portletResourceBundleD, "common-key"));
		Assert.assertFalse(portletResourceBundleD.containsKey("th"));

		serviceRegistrationA.unregister();

		portletResourceBundleA = portletConfig.getResourceBundle(
			new Locale("th", "TH", "TH"));

		Assert.assertFalse(portletResourceBundleA.containsKey("th_TH_TH"));
		Assert.assertEquals(
			"th_TH",
			ResourceBundleUtil.getString(portletResourceBundleA, "common-key"));

		serviceRegistrationB.unregister();

		portletResourceBundleA = portletConfig.getResourceBundle(
			new Locale("th", "TH", "TH"));

		Assert.assertFalse(portletResourceBundleA.containsKey("th_TH"));
		Assert.assertEquals(
			"th",
			ResourceBundleUtil.getString(portletResourceBundleA, "common-key"));

		serviceRegistrationC.unregister();

		portletResourceBundleA = portletConfig.getResourceBundle(
			new Locale("th", "TH", "TH"));

		Assert.assertFalse(portletResourceBundleA.containsKey("th"));
		Assert.assertEquals(
			"root-bundle",
			ResourceBundleUtil.getString(portletResourceBundleA, "common-key"));

		serviceRegistrationD.unregister();

		portletResourceBundleA = portletConfig.getResourceBundle(
			new Locale("th", "TH", "TH"));

		Assert.assertFalse(portletResourceBundleA.containsKey("root-bundle"));
		Assert.assertFalse(portletResourceBundleA.containsKey("common-key"));
	}

	@Test
	public void testResourceBundlesOverride() {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("javax.portlet.name", TestPortlet.PORTLET_NAME);
		properties.put("language.id", "es_ES");
		properties.put("service.ranking", 1000);

		ServiceRegistration<ResourceBundle> serviceRegistration =
			registry.registerService(
				ResourceBundle.class,
				createResourceBundle("key", "value", "this", "esto2"),
				properties);

		try {
			PortletConfig portletConfig = _genericPortlet.getPortletConfig();

			ResourceBundle resourceBundle = portletConfig.getResourceBundle(
				LocaleUtil.fromLanguageId("es_ES"));

			Assert.assertEquals(
				"value", ResourceBundleUtil.getString(resourceBundle, "key"));
			Assert.assertEquals(
				"esto2", ResourceBundleUtil.getString(resourceBundle, "this"));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	protected ResourceBundle createResourceBundle(
		final String... keysAndValues) {

		if ((keysAndValues.length % 2) != 0) {
			throw new RuntimeException(
				"Keys and values length is not an even number");
		}

		return new ListResourceBundle() {

			@Override
			protected Object[][] getContents() {
				Object[][] contents = new Object[keysAndValues.length / 2][];

				for (int i = 0; i < contents.length; i++) {
					contents[i] = new Object[] {
						keysAndValues[i * 2], keysAndValues[i * 2 + 1]
					};
				}

				return contents;
			}

		};
	}

	protected ServiceRegistration<ResourceBundle> registerResourceBundle(
		ResourceBundle resourceBundle, String languageId, int ranking) {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("javax.portlet.name", TestPortlet.PORTLET_NAME);
		properties.put("language.id", languageId);
		properties.put("service.ranking", ranking);

		return registry.registerService(
			ResourceBundle.class, resourceBundle, properties);
	}

	private static GenericPortlet _genericPortlet;
	private static ServiceTracker<GenericPortlet, GenericPortlet>
		_genericPortletServiceTracker;

}