/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.experiment.web.internal.product.navigation.control.menu.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class SegmentsExperimentProductNavigationControlMenuEntryTest {

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
	}

	@Test
	@TestInfo("LPS-97882")
	public void testIsShowReturnsFalseForUserWithOnlyLayoutViewPermission()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		User user = UserTestUtil.addUser();

		long roleId = RoleTestUtil.addRegularRole(TestPropsValues.getGroupId());

		try {
			_resourcePermissionLocalService.addResourcePermission(
				TestPropsValues.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(TestPropsValues.getCompanyId()), roleId,
				ActionKeys.VIEW);

			_userLocalService.setRoleUsers(
				roleId, new long[] {user.getUserId()});

			Assert.assertFalse(
				_productNavigationControlMenuEntry.isShow(
					_getHttpServletRequest(
						layout, PermissionCheckerFactoryUtil.create(user),
						user)));
		}
		finally {
			_userLocalService.deleteUser(user);
			_roleLocalService.deleteRole(roleId);
		}
	}

	@Test
	@TestInfo("LPS-101055")
	public void testIsShowReturnsFalseForWidgetPages() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		Assert.assertFalse(
			_productNavigationControlMenuEntry.isShow(
				_getHttpServletRequest(
					layout,
					PermissionCheckerFactoryUtil.create(
						TestPropsValues.getUser()),
					TestPropsValues.getUser())));
	}

	@Test
	@TestInfo("LPS-97882")
	public void testIsShowReturnsTrueForUserWithLayoutUpdatePermission()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		User user = UserTestUtil.addUser();

		long roleId = RoleTestUtil.addRegularRole(TestPropsValues.getGroupId());

		try {
			_resourcePermissionLocalService.addResourcePermission(
				TestPropsValues.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(TestPropsValues.getCompanyId()), roleId,
				ActionKeys.UPDATE);

			_userLocalService.setRoleUsers(
				roleId, new long[] {user.getUserId()});

			Assert.assertTrue(
				_productNavigationControlMenuEntry.isShow(
					_getHttpServletRequest(
						layout, PermissionCheckerFactoryUtil.create(user),
						user)));
		}
		finally {
			_userLocalService.deleteUser(user);
			_roleLocalService.deleteRole(roleId);
		}
	}

	private HttpServletRequest _getHttpServletRequest(
			Layout layout, PermissionChecker permissionChecker, User user)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(layout);
		themeDisplay.setPermissionChecker(permissionChecker);
		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSignedIn(true);
		themeDisplay.setUser(user);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.segments.experiment.web.internal.product.navigation.control.menu.SegmentsExperimentProductNavigationControlMenuEntry"
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