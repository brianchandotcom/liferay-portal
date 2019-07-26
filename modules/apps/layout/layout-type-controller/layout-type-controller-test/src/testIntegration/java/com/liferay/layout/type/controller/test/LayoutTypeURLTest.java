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
import com.liferay.portal.kernel.service.ServiceContext;
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

		_publicLayout = LayoutTestUtil.addLayout(_group);

		setUpVirtualHostName();
	}

	@After
	public void tearDown() throws Exception {
		_company.setVirtualHostname(_originalVirtualHostName);
	}

	@Test
	public void testGetRegularURLLayoutTypeURL() throws Exception {
		ThemeDisplay themeDisplay = _initThemeDisplay();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		Layout layoutURLType = LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(), false,
			_publicLayout.getLayoutId(), "Link", "Link", "Test invalid URL",
			LayoutConstants.TYPE_URL, false, null, serviceContext);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		UnicodeProperties properties =
			layoutURLType.getTypeSettingsProperties();

		properties.setProperty("url", "javascript:alert(1)");

		Assert.assertTrue(
			Validator.isUrl(
				layoutURLType.getRegularURL(mockHttpServletRequest), true));
	}

	protected void setUpVirtualHostName() {
		_originalVirtualHostName = _company.getVirtualHostname();

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

	private String _originalVirtualHostName;
	private Layout _publicLayout;

}