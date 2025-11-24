/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.consent.management.platform.integration.internal.servlet.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.consent.management.platform.integration.configuration.ConsentManagementPlatformConfiguration;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Christian Moura
 */
@RunWith(Arquillian.class)
public class ConsentManagementPlatformTopHeadDynamicIncludeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@FeatureFlag("LPD-65286")
	@Test
	public void testIncludeScriptTag() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		ConfigurationTestUtil.saveConfiguration(
			ConsentManagementPlatformConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"enabled", true
			).put(
				"providerName", RandomTestUtil.randomString()
			).put(
				"scriptTag", _SCRIPT_TAG
			).build());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(layout);

		themeDisplay.setCompany(company);

		themeDisplay.setScopeGroupId(group.getGroupId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		_dynamicInclude.include(
			mockHttpServletRequest, mockHttpServletResponse,
			RandomTestUtil.randomString());

		Assert.assertTrue(
			StringUtil.contains(
				mockHttpServletResponse.getContentAsString(), _SCRIPT_TAG,
				StringPool.NEW_LINE));
	}

	private static final String _SCRIPT_TAG =
		"<script id=\"Cookiebot\" src=\"https://consent.cookiebot.com" +
			"/uc.js\" data-cbid=\"000000\" type=\"text/javascript\"></script>";

	@Inject(
		filter = "component.name=com.liferay.consent.management.platform.integration.internal.servlet.taglib.ConsentManagementPlatformTopHeadDynamicInclude"
	)
	private DynamicInclude _dynamicInclude;

}