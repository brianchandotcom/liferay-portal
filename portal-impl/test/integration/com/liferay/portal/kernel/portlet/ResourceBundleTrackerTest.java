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
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Locale;
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
 */
public class ResourceBundleTrackerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
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

	private static GenericPortlet _genericPortlet;
	private static ServiceTracker<GenericPortlet, GenericPortlet>
		_genericPortletServiceTracker;

}