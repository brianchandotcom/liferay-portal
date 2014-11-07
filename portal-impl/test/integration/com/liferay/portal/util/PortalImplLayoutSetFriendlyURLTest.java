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

package com.liferay.portal.util;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Sierra
 * @author Akos Thurzo
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PortalImplLayoutSetFriendlyURLTest
	extends PortalImplBaseURLTestCase {

	@Test
	public void testAccessFromVirtualHost() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "VIRTUAL_HOSTS_DEFAULT_SITE_NAME");

		Object value = field.get(null);

		Group defaultGroup = GroupTestUtil.addGroup();

		try {
			field.set(null, defaultGroup.getName());

			ThemeDisplay themeDisplay = initThemeDisplay(
				company, group, publicLayout, LOCALHOST, VIRTUAL_HOSTNAME);

			Layout layout = LayoutTestUtil.addLayout(defaultGroup);

			String friendlyURL = PortalUtil.getLayoutSetFriendlyURL(
				layout.getLayoutSet(), themeDisplay);

			Assert.assertFalse(friendlyURL.contains(LOCALHOST));
		}
		finally {
			field.set(null, value);

			GroupLocalServiceUtil.deleteGroup(defaultGroup);
		}
	}

	@Test
	public void testPreserveParameters() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, controlPanelLayout, VIRTUAL_HOSTNAME);

		themeDisplay.setDoAsUserId("impersonated");

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			group.getGroupId(), false);

		String layoutSetFriendlyURL = PortalUtil.getLayoutSetFriendlyURL(
			layoutSet, themeDisplay);

		Assert.assertEquals(
			"impersonated",
			HttpUtil.getParameter(layoutSetFriendlyURL, "doAsUserId"));
	}

}