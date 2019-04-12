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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenWhitelistUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.bundle.authtokenwhitelistutil.TestAuthTokenIgnoreOrigins;
import com.liferay.portal.security.auth.bundle.authtokenwhitelistutil.TestMVCActionCommand;
import com.liferay.portal.security.auth.bundle.authtokenwhitelistutil.TestMVCRenderCommand;
import com.liferay.portal.security.auth.bundle.authtokenwhitelistutil.TestMVCResourceCommand;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleClassTestRule;
import com.liferay.portal.util.PropsValues;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 * @author Tomas Polesovsky
 */
public class AuthTokenWhitelistUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleClassTestRule("bundle.authtokenwhitelistutil"));

	@Test
	public void testIsOriginCSRFWhitelistedFromBundle() {
		Assert.assertTrue(
			AuthTokenWhitelistUtil.isOriginCSRFWhitelisted(
				0,
				TestAuthTokenIgnoreOrigins.TEST_AUTH_TOKEN_IGNORE_ORIGINS_URL));
	}

	@Test
	public void testIsOriginCSRFWhitelistedFromPortalProperties() {
		String[] origins = PropsValues.AUTH_TOKEN_IGNORE_ORIGINS;

		for (String origin : origins) {
			Assert.assertTrue(
				AuthTokenWhitelistUtil.isOriginCSRFWhitelisted(0, origin));
		}
	}

	@Test
	public void testIsPortletCSRFWhitelistedForMVCActionCommand() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		String namespace = PortalUtil.getPortletNamespace(
			TestMVCActionCommand.TEST_PORTLET_ID);

		mockHttpServletRequest.setParameter(
			namespace + ActionRequest.ACTION_NAME,
			TestMVCActionCommand.TEST_MVC_COMMAND_NAME);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestMVCActionCommand.TEST_PORTLET_ID);

		Assert.assertTrue(
			AuthTokenWhitelistUtil.isPortletCSRFWhitelisted(
				mockHttpServletRequest, portlet));
	}

	@Test
	public void testIsPortletInvocationWhitelistedForMVCActionCommand() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		String namespace = PortalUtil.getPortletNamespace(
			TestMVCActionCommand.TEST_PORTLET_ID);

		mockHttpServletRequest.setParameter(
			namespace + ActionRequest.ACTION_NAME,
			TestMVCActionCommand.TEST_MVC_COMMAND_NAME);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLifecycleAction(true);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestMVCActionCommand.TEST_PORTLET_ID);

		Assert.assertTrue(
			AuthTokenWhitelistUtil.isPortletInvocationWhitelisted(
				mockHttpServletRequest, portlet));
	}

	@Test
	public void testIsPortletInvocationWhitelistedForMVCRenderCommand() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		String namespace = PortalUtil.getPortletNamespace(
			TestMVCRenderCommand.TEST_PORTLET_ID);

		mockHttpServletRequest.setParameter(
			namespace + "mvcRenderCommandName",
			TestMVCRenderCommand.TEST_MVC_COMMAND_NAME);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLifecycleRender(true);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestMVCRenderCommand.TEST_PORTLET_ID);

		Assert.assertTrue(
			AuthTokenWhitelistUtil.isPortletInvocationWhitelisted(
				mockHttpServletRequest, portlet));
	}

	@Test
	public void testIsPortletInvocationWhitelistedForMVCResourceCommand() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter(
			"p_p_id", TestMVCResourceCommand.TEST_PORTLET_ID);
		mockHttpServletRequest.setParameter(
			"p_p_resource_id", TestMVCResourceCommand.TEST_MVC_COMMAND_NAME);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLifecycleResource(true);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestMVCResourceCommand.TEST_PORTLET_ID);

		Assert.assertTrue(
			AuthTokenWhitelistUtil.isPortletInvocationWhitelisted(
				mockHttpServletRequest, portlet));
	}

	@Test
	public void testIsPortletURLCSRFWhitelistedForMVCActionCommand() {
		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			new MockHttpServletRequest(), TestMVCActionCommand.TEST_PORTLET_ID,
			0, PortletRequest.ACTION_PHASE);

		liferayPortletURL.setParameter(
			ActionRequest.ACTION_NAME,
			TestMVCActionCommand.TEST_MVC_COMMAND_NAME);

		Assert.assertTrue(
			AuthTokenWhitelistUtil.isPortletURLCSRFWhitelisted(
				liferayPortletURL));
	}

	@Test
	public void testIsPortletURLInvocationWhitelistedForMVCActionCommand() {
		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			new MockHttpServletRequest(), TestMVCActionCommand.TEST_PORTLET_ID,
			0, PortletRequest.ACTION_PHASE);

		liferayPortletURL.setParameter(
			ActionRequest.ACTION_NAME,
			TestMVCActionCommand.TEST_MVC_COMMAND_NAME);

		Assert.assertTrue(
			AuthTokenWhitelistUtil.isPortletURLPortletInvocationWhitelisted(
				liferayPortletURL));
	}

	@Test
	public void testIsPortletURLInvocationWhitelistedForMVCRenderCommand() {
		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			new MockHttpServletRequest(), TestMVCRenderCommand.TEST_PORTLET_ID,
			0, PortletRequest.RENDER_PHASE);

		liferayPortletURL.setParameter(
			"mvcRenderCommandName", TestMVCRenderCommand.TEST_MVC_COMMAND_NAME);

		Assert.assertTrue(
			AuthTokenWhitelistUtil.isPortletURLPortletInvocationWhitelisted(
				liferayPortletURL));
	}

	@Test
	public void testIsPortletURLInvocationWhitelistedForMVCResourceCommand() {
		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			new MockHttpServletRequest(),
			TestMVCResourceCommand.TEST_PORTLET_ID, 0,
			PortletRequest.RESOURCE_PHASE);

		liferayPortletURL.setResourceID(
			TestMVCResourceCommand.TEST_MVC_COMMAND_NAME);

		Assert.assertTrue(
			AuthTokenWhitelistUtil.isPortletURLPortletInvocationWhitelisted(
				liferayPortletURL));
	}

}