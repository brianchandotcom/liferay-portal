/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.upgrade.v1_0_9.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.PublicationRoleConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class PublicationsAdminRoleUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeAdminRolePermissions() throws Exception {
		User user = UserTestUtil.addUser();

		Group group = GroupTestUtil.addGroup();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		CTCollection ctCollection =
			CTCollectionLocalServiceUtil.addCTCollection(
				null, TestPropsValues.getCompanyId(), user.getUserId(), 0,
				RandomTestUtil.randomString(), null);

		Role role = RoleTestUtil.addRole(
			PublicationRoleConstants.NAME_ADMIN,
			RoleConstants.TYPE_PUBLICATIONS);

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), group.getGroupId(), role.getRoleId());

		_runUpgrade();

		Assert.assertTrue(
			_ctCollectionModelResourcePermission.contains(
				permissionChecker, ctCollection, CTActionKeys.INVITE_USERS));
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private static final String _CLASS_NAME =
		"com.liferay.change.tracking.web.internal.upgrade.v1_0_9." +
			"PublicationsAdminRoleUpgradeProcess";

	@Inject(
		filter = "model.class.name=com.liferay.change.tracking.model.CTCollection"
	)
	private static ModelResourcePermission<CTCollection>
		_ctCollectionModelResourcePermission;

	@Inject(
		filter = "(&(component.name=com.liferay.change.tracking.web.internal.upgrade.registry.PublicationsWebUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}