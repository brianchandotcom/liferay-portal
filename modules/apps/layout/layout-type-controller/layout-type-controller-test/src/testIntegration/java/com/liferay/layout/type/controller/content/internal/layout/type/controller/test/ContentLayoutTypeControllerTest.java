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

package com.liferay.layout.type.controller.content.internal.layout.type.controller.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.util.LayoutTypeControllerTracker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class ContentLayoutTypeControllerTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_httpServletRequest = new MockHttpServletRequest();

		_httpServletResponse = new MockHttpServletResponse();

		LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test(expected = NoSuchLayoutException.class)
	public void testContentLayoutTypeControllerNoPublishedPageGuestUser()
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_CONTENT);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_initThemeDisplay(
			_userLocalService.getDefaultUser(_group.getCompanyId()));

		layoutTypeController.includeLayoutContent(
			_httpServletRequest, _httpServletResponse, layout);
	}

	@Test
	public void testContentLayoutTypeControllerNoPublishedPagePermissionUser()
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_CONTENT);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_initThemeDisplay(TestPropsValues.getUser());

		Assert.assertFalse(
			layoutTypeController.includeLayoutContent(
				_httpServletRequest, _httpServletResponse, layout));
	}

	private ThemeDisplay _initThemeDisplay(User user) throws Exception {
		UserTestUtil.setUser(user);

		Company company = CompanyLocalServiceUtil.getCompany(
			_group.getCompanyId());

		ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

		themeDisplay.setCompany(company);
		themeDisplay.setLanguageId(_group.getDefaultLanguageId());
		themeDisplay.setLayoutSet(
			_layoutSetLocalService.getLayoutSet(_group.getGroupId(), false));
		themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()));
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setPortalDomain(company.getVirtualHostname());
		themeDisplay.setPortalURL(company.getPortalURL(_group.getGroupId()));
		themeDisplay.setRequest(_httpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerPort(8080);
		themeDisplay.setSignedIn(true);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		_httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return themeDisplay;
	}

	@DeleteAfterTestRun
	private Group _group;

	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private UserLocalService _userLocalService;

}