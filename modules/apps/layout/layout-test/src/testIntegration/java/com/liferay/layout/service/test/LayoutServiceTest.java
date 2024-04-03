/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class LayoutServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testFetchLayout() throws Exception {
		Layout newLayout = LayoutTestUtil.addTypePortletLayout(_group);

		Layout layout = _layoutService.fetchLayout(
			0L, newLayout.isPrivateLayout(), newLayout.getLayoutId());

		Assert.assertNull(layout);

		layout = _layoutService.fetchLayout(
			_group.getGroupId(), !newLayout.isPrivateLayout(),
			newLayout.getLayoutId());

		Assert.assertNull(layout);

		layout = _layoutService.fetchLayout(
			_group.getGroupId(), newLayout.isPrivateLayout(), 0L);

		Assert.assertNull(layout);

		layout = _layoutService.fetchLayout(
			_group.getGroupId(), newLayout.isPrivateLayout(),
			newLayout.getLayoutId());

		Assert.assertNotNull(layout);

		Assert.assertEquals(layout.getPlid(), newLayout.getPlid());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testFetchLayoutWithoutPermissions() throws Exception {
		Layout newLayout = LayoutTestUtil.addTypePortletLayout(_group, true);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			User user = UserTestUtil.addUser();

			_roleLocalService.deleteUserRoles(
				user.getUserId(), user.getRoleIds());

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			_layoutService.fetchLayout(
				_group.getGroupId(), newLayout.isPrivateLayout(),
				newLayout.getLayoutId());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutService _layoutService;

	@Inject
	private RoleLocalService _roleLocalService;

}