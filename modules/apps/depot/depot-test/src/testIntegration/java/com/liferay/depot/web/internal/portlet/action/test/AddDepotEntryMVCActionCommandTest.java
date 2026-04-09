/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotPortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Akhash Ramprakash
 */
@RunWith(Arquillian.class)
public class AddDepotEntryMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		_mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());
		_mockLiferayPortletActionRequest.setParameter(
			"redirect", RandomTestUtil.randomString());
		_mockLiferayPortletActionRequest.setParameter(
			"name", RandomTestUtil.randomString());
		_mockLiferayPortletActionRequest.setParameter(
			"description", RandomTestUtil.randomString());

		_mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();
	}

	@Test
	public void testRedirectURLGenerated() throws Exception {
		_mvcActionCommand.processAction(
			_mockLiferayPortletActionRequest,
			_mockLiferayPortletActionResponse);

		MockHttpServletResponse mockHttpServletResponse =
			(MockHttpServletResponse)
				_mockLiferayPortletActionResponse.getHttpServletResponse();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			mockHttpServletResponse.getContentAsString());

		String redirectURL = jsonObject.getString("redirectURL");

		Assert.assertTrue(redirectURL.contains("/group/asset-library-"));
		Assert.assertTrue(redirectURL.contains(DepotPortletKeys.DEPOT_ADMIN));
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setSiteGroupId(TestPropsValues.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private MockLiferayPortletActionRequest _mockLiferayPortletActionRequest;
	private MockLiferayPortletActionResponse _mockLiferayPortletActionResponse;

	@Inject(filter = "mvc.command.name=/depot/add_depot_entry")
	private MVCActionCommand _mvcActionCommand;

}