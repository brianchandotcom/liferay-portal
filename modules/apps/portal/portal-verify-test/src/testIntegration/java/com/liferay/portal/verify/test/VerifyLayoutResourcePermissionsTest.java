/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.VerifyResourcePermissions;
import com.liferay.portal.verify.model.LayoutVerifiableResourcedModel;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class VerifyLayoutResourcePermissionsTest
	extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();

		_groupActions = _resourceActions.getModelResourceGroupDefaultActions(
			Layout.class.getName());
		_groupRole = _roleLocalService.getDefaultGroupRole(_group.getGroupId());

		_guestActions = _resourceActions.getModelResourceGuestDefaultActions(
			Layout.class.getName());
		_guestRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		_ownerActions = _resourceActions.getModelResourceActions(
			Layout.class.getName());

		List<String> defaultOwnerActions =
			_resourceActions.getModelResourceOwnerDefaultActions(
				Layout.class.getName());

		if (!defaultOwnerActions.isEmpty()) {
			_ownerActions.retainAll(defaultOwnerActions);
		}

		_ownerRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.OWNER);

		_user = UserTestUtil.addUser();
	}

	@Test
	@TestInfo("LPD-37372")
	public void testIsSkipVerifyResourcePermissions() throws Exception {
		Layout publicLayout1 = LayoutTestUtil.addTypePortletLayout(_group);
		Layout publicLayout2 = LayoutTestUtil.addTypePortletLayout(_group);

		_assertResourcePermission(
			_guestActions, _guestRole, publicLayout1, publicLayout2);

		Layout privateLayout1 = LayoutTestUtil.addTypePortletLayout(
			_group, true);
		Layout privateLayout2 = LayoutTestUtil.addTypePortletLayout(
			_group, true);

		_assertResourcePermission(
			_groupActions, _groupRole, publicLayout1, publicLayout2,
			privateLayout1, privateLayout2);

		Layout userGroupLayout1 = LayoutTestUtil.addTypePortletLayout(
			_user.getGroup());
		Layout userGroupLayout2 = LayoutTestUtil.addTypePortletLayout(
			_user.getGroup());

		_assertResourcePermission(
			_ownerActions, _ownerRole, publicLayout1, publicLayout2,
			privateLayout1, privateLayout2, userGroupLayout1, userGroupLayout2);

		_deleteResourcePermissions(
			privateLayout1, publicLayout1, userGroupLayout1);

		int count =
			_resourcePermissionLocalService.getResourcePermissionsCount();

		VerifyResourcePermissions.verify(new LayoutVerifiableResourcedModel());

		_assertResourcePermission(_guestActions, _guestRole, publicLayout2);
		_assertResourcePermission(
			_groupActions, _groupRole, publicLayout2, privateLayout2);
		_assertResourcePermission(
			_ownerActions, _ownerRole, publicLayout2, privateLayout2,
			userGroupLayout2);

		_assertEmptyResourcePermissions(
			privateLayout1, publicLayout1, userGroupLayout1);

		Assert.assertEquals(
			count,
			_resourcePermissionLocalService.getResourcePermissionsCount());
	}

	@Test
	public void testVerify() throws Exception {
		Layout publicLayout1 = LayoutTestUtil.addTypePortletLayout(_group);
		Layout publicLayout2 = LayoutTestUtil.addTypePortletLayout(_group);

		_assertResourcePermission(
			_guestActions, _guestRole, publicLayout1, publicLayout2);

		Layout privateLayout1 = LayoutTestUtil.addTypePortletLayout(
			_group, true);
		Layout privateLayout2 = LayoutTestUtil.addTypePortletLayout(
			_group, true);

		_assertResourcePermission(
			_groupActions, _groupRole, publicLayout1, publicLayout2,
			privateLayout1, privateLayout2);

		Layout userGroupLayout1 = LayoutTestUtil.addTypePortletLayout(
			_user.getGroup());
		Layout userGroupLayout2 = LayoutTestUtil.addTypePortletLayout(
			_user.getGroup());

		_assertResourcePermission(
			_ownerActions, _ownerRole, publicLayout1, publicLayout2,
			privateLayout1, privateLayout2, userGroupLayout1, userGroupLayout2);

		_deleteResourcePermissions(
			privateLayout1, publicLayout1, userGroupLayout1);

		Release release = _releaseLocalService.fetchRelease(
			"com.liferay.layout.service");

		String originalSchemaVersion = release.getSchemaVersion();

		release.setSchemaVersion("0.0.0");

		release = _releaseLocalService.updateRelease(release);

		try {
			VerifyResourcePermissions.verify(
				new LayoutVerifiableResourcedModel());
		}
		finally {
			release.setSchemaVersion(originalSchemaVersion);

			_releaseLocalService.updateRelease(release);
		}

		_assertResourcePermission(
			_guestActions, _guestRole, publicLayout1, publicLayout2);
		_assertResourcePermission(
			_groupActions, _groupRole, publicLayout1, publicLayout2,
			privateLayout1, privateLayout2);
		_assertResourcePermission(
			_ownerActions, _ownerRole, publicLayout1, publicLayout2,
			privateLayout1, privateLayout2, userGroupLayout1, userGroupLayout2);
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyResourcePermissions();
	}

	private void _assertEmptyResourcePermissions(Layout... layouts) {
		for (Layout layout : layouts) {
			Assert.assertTrue(
				ListUtil.isEmpty(
					_resourcePermissionLocalService.getResourcePermissions(
						layout.getCompanyId(), Layout.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(layout.getPlid()))));
		}
	}

	private void _assertResourcePermission(
			List<String> actions, Role role, Layout... layouts)
		throws Exception {

		for (String action : actions) {
			for (Layout layout : layouts) {
				Assert.assertTrue(
					action,
					_resourcePermissionLocalService.hasResourcePermission(
						layout.getCompanyId(), Layout.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(layout.getPlid()),
						new long[] {role.getRoleId()}, action));
			}
		}
	}

	private void _deleteResourcePermissions(Layout... layouts)
		throws Exception {

		for (Layout layout : layouts) {
			Assert.assertTrue(
				ListUtil.isNotEmpty(
					_resourcePermissionLocalService.getResourcePermissions(
						layout.getCompanyId(), Layout.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(layout.getPlid()))));

			_resourcePermissionLocalService.deleteResourcePermissions(
				layout.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(layout.getPlid()));

			Assert.assertTrue(
				ListUtil.isEmpty(
					_resourcePermissionLocalService.getResourcePermissions(
						layout.getCompanyId(), Layout.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(layout.getPlid()))));
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	private List<String> _groupActions;
	private Role _groupRole;
	private List<String> _guestActions;
	private Role _guestRole;
	private List<String> _ownerActions;
	private Role _ownerRole;

	@Inject
	private ReleaseLocalService _releaseLocalService;

	@Inject
	private ResourceActions _resourceActions;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

}