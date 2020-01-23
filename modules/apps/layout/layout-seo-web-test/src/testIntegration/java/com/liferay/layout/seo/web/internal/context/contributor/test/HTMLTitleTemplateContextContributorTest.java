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

package com.liferay.layout.seo.web.internal.context.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class HTMLTitleTemplateContextContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testPrepare() throws PortalException {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Map<String, Object> map = new HashMap<>();

		_templateContextContributor.prepare(map, _getHttpServletRequest());

		Assert.assertEquals(
			StringBundler.concat(
				_layout.getName(LocaleUtil.US), " - ",
				_group.getDescriptiveName(LocaleUtil.US), " - ",
				company.getName()),
			map.get("htmlTitle"));
	}

	private HttpServletRequest _getHttpServletRequest() throws PortalException {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());
		mockHttpServletRequest.setRequestURI(
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() + _layout.getFriendlyURL());

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		themeDisplay.setLanguageId(_group.getDefaultLanguageId());
		themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()));
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(
			_layoutSetLocalService.getLayoutSet(_group.getGroupId(), false));
		themeDisplay.setPortalURL(company.getPortalURL(_group.getGroupId()));
		themeDisplay.setPortalDomain("localhost");
		themeDisplay.setSecure(true);
		themeDisplay.setServerName("localhost");
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(_group.getGroupId());

		return themeDisplay;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private Group _group;
	private Layout _layout;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject(
		filter = "component.name=com.liferay.layout.seo.web.internal.context.contributor.HTMLTitleTemplateContextContributor"
	)
	private TemplateContextContributor _templateContextContributor;

}