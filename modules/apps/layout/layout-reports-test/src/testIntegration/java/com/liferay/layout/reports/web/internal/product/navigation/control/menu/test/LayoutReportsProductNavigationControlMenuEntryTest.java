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

package com.liferay.layout.reports.web.internal.product.navigation.control.menu.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.reports.web.internal.util.LayoutReportsTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;

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
public class LayoutReportsProductNavigationControlMenuEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0);

		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	public void testIsShow() throws Exception {
		LayoutReportsTestUtil.
			withLayoutReportsGooglePageSpeedGroupConfiguration(
				StringPool.BLANK, true, _group.getGroupId(),
				() -> Assert.assertTrue(
					_productNavigationControlMenuEntry.isShow(
						_getHttpServletRequest())));
	}

	@Test
	public void testIsShowWithLayoutTypeAssetDisplay() throws Exception {
		_layout.setType(LayoutConstants.TYPE_ASSET_DISPLAY);

		_layout = _layoutLocalService.updateLayout(_layout);

		LayoutReportsTestUtil.
			withLayoutReportsGooglePageSpeedGroupConfiguration(
				RandomTestUtil.randomString(), true, _group.getGroupId(),
				() -> Assert.assertTrue(
					_productNavigationControlMenuEntry.isShow(
						_getHttpServletRequest())));
	}

	@Test
	public void testIsShowWithoutEnableCompanyConfiguration() throws Exception {
		LayoutReportsTestUtil.
			withLayoutReportsGooglePageSpeedCompanyConfiguration(
				_group.getCompanyId(), false,
				() -> Assert.assertFalse(
					_productNavigationControlMenuEntry.isShow(
						_getHttpServletRequest())));
	}

	@Test
	public void testIsShowWithoutEnableSystemConfiguration() throws Exception {
		LayoutReportsTestUtil.withLayoutReportsGooglePageSpeedConfiguration(
			false,
			() -> Assert.assertFalse(
				_productNavigationControlMenuEntry.isShow(
					_getHttpServletRequest())));
	}

	@Test
	public void testIsShowWithUserDocumentEditPermission() throws Exception {
		User user = UserTestUtil.addUser();

		long roleId = RoleTestUtil.addRegularRole(TestPropsValues.getGroupId());

		try {
			_resourcePermissionLocalService.addResourcePermission(
				TestPropsValues.getCompanyId(), DLFileEntry.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(TestPropsValues.getCompanyId()), roleId,
				ActionKeys.UPDATE);

			_userLocalService.setRoleUsers(
				roleId, new long[] {user.getUserId()});

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			Assert.assertTrue(
				_productNavigationControlMenuEntry.isShow(
					_getHttpServletRequest(permissionChecker, user)));
		}
		finally {
			_userLocalService.deleteUser(user);
			_roleLocalService.deleteRole(roleId);
		}
	}

	@Test
	public void testIsShowWithUserWithBlogsEditPermission() throws Exception {
		User user = UserTestUtil.addUser();

		long roleId = RoleTestUtil.addRegularRole(TestPropsValues.getGroupId());

		try {
			_resourcePermissionLocalService.addResourcePermission(
				TestPropsValues.getCompanyId(), BlogsEntry.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(TestPropsValues.getCompanyId()), roleId,
				ActionKeys.UPDATE);

			_userLocalService.setRoleUsers(
				roleId, new long[] {user.getUserId()});

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			Assert.assertTrue(
				_productNavigationControlMenuEntry.isShow(
					_getHttpServletRequest(permissionChecker, user)));
		}
		finally {
			_userLocalService.deleteUser(user);
			_roleLocalService.deleteRole(roleId);
		}
	}

	@Test
	public void testIsShowWithUserWithWebContentEditPermission()
		throws Exception {

		User user = UserTestUtil.addUser();

		long roleId = RoleTestUtil.addRegularRole(TestPropsValues.getGroupId());

		try {
			_resourcePermissionLocalService.addResourcePermission(
				TestPropsValues.getCompanyId(), JournalArticle.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(TestPropsValues.getCompanyId()), roleId,
				ActionKeys.UPDATE);

			_userLocalService.setRoleUsers(
				roleId, new long[] {user.getUserId()});

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			Assert.assertTrue(
				_productNavigationControlMenuEntry.isShow(
					_getHttpServletRequest(permissionChecker, user)));
		}
		finally {
			_userLocalService.deleteUser(user);
			_roleLocalService.deleteRole(roleId);
		}
	}

	private HttpServletRequest _getHttpServletRequest() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);

		User user = TestPropsValues.getUser();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(_layout);
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setUser(user);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private HttpServletRequest _getHttpServletRequest(
			PermissionChecker permissionChecker, User user)
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(_layout);
		themeDisplay.setPermissionChecker(permissionChecker);
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setUser(user);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(
		filter = "component.name=com.liferay.layout.reports.web.internal.product.navigation.control.menu.LayoutReportsProductNavigationControlMenuEntry"
	)
	private ProductNavigationControlMenuEntry
		_productNavigationControlMenuEntry;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}