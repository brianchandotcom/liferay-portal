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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.test.rule.PortalProps;
import com.liferay.portlet.PortalPreferencesImpl;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Dante Wang
 */
public class SessionClicksTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@PortalProps(
		properties = PropsKeys.SESSION_CLICKS_MAX_ALLOWED_VALUES + "=" + _MAX_ALLOWED_VALUES
	)
	@Test
	public void testPutForMaxAllowedValues() {
		PortalPreferences portalPreferences = new PortalPreferencesImpl();

		PortletPreferencesFactoryUtil portletPreferencesFactoryUtil =
			new PortletPreferencesFactoryUtil();

		portletPreferencesFactoryUtil.setPortletPreferencesFactory(
			(PortletPreferencesFactory)ProxyUtil.newProxyInstance(
				SessionClicksTest.class.getClassLoader(),
				new Class<?>[] {PortletPreferencesFactory.class},
				(proxy, method, args) -> {
					String methodName = method.getName();

					if (methodName.equals("getPortalPreferences") &&
						(args.length == 1) &&
						(args[0] instanceof HttpServletRequest)) {

						return portalPreferences;
					}

					return null;
				}));

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		for (int i = 1; i <= _MAX_ALLOWED_VALUES; i++) {
			SessionClicks.put(httpServletRequest, "key" + i, "value" + i);
		}

		SessionClicks.put(httpServletRequest, "keyExceedMax", "valueExceedMax");

		Assert.assertEquals(
			StringBundler.concat(
				"The size of key-values in PortalPreferences should not ",
				"exceed session.clicks.max.allowed.values=",
				_MAX_ALLOWED_VALUES, " when putting through SessionClicks."),
			_MAX_ALLOWED_VALUES, portalPreferences.size());
	}

	private static final int _MAX_ALLOWED_VALUES = 10;

}