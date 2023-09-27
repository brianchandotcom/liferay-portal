/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.type.controller.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Stian Sigvartsen
 */
@RunWith(Arquillian.class)
public class LayoutTypeURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = CompanyLocalServiceUtil.fetchCompany(
			TestPropsValues.getCompanyId());

		_group = GroupTestUtil.addGroup();

		_publicLayout = LayoutTestUtil.addTypePortletLayout(_group);

		setUpVirtualHostname();
	}

	@After
	public void tearDown() throws Exception {
		_company.setVirtualHostname(_originalVirtualHostname);
	}

	@Test
	public void testGetRegularURLLayoutTypeURL() throws Exception {
		ThemeDisplay themeDisplay = _initThemeDisplay();

		Layout layoutURLType = LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(), false,
			_publicLayout.getLayoutId(), "Link", "Link", "Test invalid URL",
			LayoutConstants.TYPE_URL, false, null,
			ServiceContextTestUtil.getServiceContext());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		UnicodeProperties unicodeProperties =
			layoutURLType.getTypeSettingsProperties();

		unicodeProperties.setProperty("url", "javascript:alert(1)");

		Assert.assertTrue(
			Validator.isUrl(
				layoutURLType.getRegularURL(mockHttpServletRequest), true));
	}

	protected void setUpVirtualHostname() {
		_originalVirtualHostname = _company.getVirtualHostname();

		_company.setVirtualHostname(_VIRTUAL_HOSTNAME);
	}

	private ThemeDisplay _initThemeDisplay() throws Exception {
		_company.setVirtualHostname(_VIRTUAL_HOSTNAME);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setI18nLanguageId(StringPool.BLANK);
		themeDisplay.setLayout(_publicLayout);
		themeDisplay.setLayoutSet(_publicLayout.getLayoutSet());
		themeDisplay.setSecure(false);
		themeDisplay.setServerName(_VIRTUAL_HOSTNAME);
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());
		themeDisplay.setWidget(false);

		return themeDisplay;
	}

	private static final String _VIRTUAL_HOSTNAME = "test.com";

	private Company _company;

	@DeleteAfterTestRun
	private Group _group;

	private String _originalVirtualHostname;
	private Layout _publicLayout;

}