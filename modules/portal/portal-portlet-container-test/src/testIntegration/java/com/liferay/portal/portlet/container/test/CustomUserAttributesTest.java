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

package com.liferay.portal.portlet.container.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.CustomUserAttributes;
import com.liferay.portlet.DefaultCustomUserAttributes;
import com.liferay.portlet.UserInfoFactory;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class CustomUserAttributesTest extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Test
	public void testCustom() throws Exception {
		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), TEST_PORTLET_ID);

		Assert.assertFalse(portlet.isUndeployedPortlet());

		PortletApp portletApp = portlet.getPortletApp();

		properties = new Hashtable<>();

		properties.put(
			"custom.user.attribute",
			new String[] {"user.name.random", "user.value.test"});
		properties.put(
			"servlet.context.name", portletApp.getServletContextName());

		final String randomValue = RandomTestUtil.randomString();

		BundleContext bundleContext = getBundleContext();

		serviceRegistration = bundleContext.registerService(
			CustomUserAttributes.class, new DefaultCustomUserAttributes() {

				@Override
				public String getValue(
					String name, Map<String, String> userInfo) {

					if ("user.value.test".equals(name)) {
						return "test.value";
					}
					else if ("user.name.random".equals(name)) {
						return randomValue;
					}

					return null;
				}

			}, properties);

		serviceRegistrations.add(serviceRegistration);

		LinkedHashMap<String, String> userInfo = UserInfoFactory.getUserInfo(
			TestPropsValues.getUserId(), portlet);

		Assert.assertTrue(userInfo.containsKey("user.value.test"));
		Assert.assertEquals("test.value", userInfo.get("user.value.test"));
		Assert.assertTrue(userInfo.containsKey("user.name.random"));
		Assert.assertEquals(randomValue, userInfo.get("user.name.random"));
	}

	@Test
	public void testDefault() throws Exception {
		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), TEST_PORTLET_ID);

		Assert.assertFalse(portlet.isUndeployedPortlet());

		LinkedHashMap<String, String> userInfo = UserInfoFactory.getUserInfo(
			TestPropsValues.getUserId(), portlet);

		Assert.assertTrue(userInfo.containsKey("user.name.random"));

		String userNameRandom = userInfo.get("user.name.random");

		if (!"Aaa".equals(userNameRandom) && !"Bbb".equals(userNameRandom) &&
			!"Ccc".equals(userNameRandom)) {

			Assert.fail(
				"Expected any of [Aaa, Bbb, Ccc] but was <" + userNameRandom +
					">");
		}
	}

}