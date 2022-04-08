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
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.LayoutTypeControllerTracker;

import javax.servlet.http.HttpServletRequest;

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

		LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test(expected = NoSuchLayoutException.class)
	public void testContentLayoutTypeControllerNoPublishedPageGuestUser()
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_CONTENT);

		layoutTypeController.includeLayoutContent(
			_getHttpServletRequest(
				_userLocalService.getDefaultUser(_group.getCompanyId())),
			new MockHttpServletResponse(),
			LayoutTestUtil.addTypeContentLayout(_group));
	}

	@Test
	public void testContentLayoutTypeControllerNoPublishedPagePermissionUser()
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_CONTENT);

		Assert.assertFalse(
			layoutTypeController.includeLayoutContent(
				_getHttpServletRequest(TestPropsValues.getUser()),
				new MockHttpServletResponse(),
				LayoutTestUtil.addTypeContentLayout(_group)));
	}

	@Test
	public void testContentLayoutTypeControllerPublishedPageGuestUser()
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_CONTENT);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		_layoutLocalService.updateStatus(
			TestPropsValues.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				_group.getCompanyId(), _group.getGroupId(),
				TestPropsValues.getUserId()));

		Assert.assertFalse(
			layoutTypeController.includeLayoutContent(
				_getHttpServletRequest(
					_userLocalService.getDefaultUser(_group.getCompanyId())),
				new MockHttpServletResponse(), layout));
	}

	@Test
	public void testContentLayoutTypeControllerPublishedPagePermissionUser()
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_CONTENT);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		_layoutLocalService.updateStatus(
			TestPropsValues.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				_group.getCompanyId(), _group.getGroupId(),
				TestPropsValues.getUserId()));

		Assert.assertFalse(
			layoutTypeController.includeLayoutContent(
				_getHttpServletRequest(TestPropsValues.getUser()),
				new MockHttpServletResponse(), layout));
	}

	private HttpServletRequest _getHttpServletRequest(User user)
		throws Exception {

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		UserTestUtil.setUser(user);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompany(
			_group.getCompanyId());

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
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerPort(8080);
		themeDisplay.setSignedIn(true);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return httpServletRequest;
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private UserLocalService _userLocalService;

}