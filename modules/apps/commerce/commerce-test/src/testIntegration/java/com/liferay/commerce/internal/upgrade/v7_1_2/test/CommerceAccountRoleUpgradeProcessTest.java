/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v7_1_2.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balazs Breier
 */
@RunWith(Arquillian.class)
public class CommerceAccountRoleUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpdateRoleClassNameId() throws Exception {
		Group group = GroupTestUtil.addGroup();

		group.setClassNameId(
			_classNameLocalService.getClassNameId(AccountEntry.class));

		group = _groupLocalService.updateGroup(group);

		Role role = _roleLocalService.addRole(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(), null, 0,
			RandomTestUtil.randomString(), null, null, RoleConstants.TYPE_SITE,
			null, ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		_userGroupRoleLocalService.addUserGroupRole(
			TestPropsValues.getUserId(), group.getGroupId(), role.getRoleId());

		_runUpgrade();

		EntityCacheUtil.clearCache();

		Role updatedRole = _roleLocalService.getRole(role.getRoleId());

		Assert.assertEquals(
			_classNameLocalService.getClassNameId(AccountRole.class),
			updatedRole.getClassNameId());
		Assert.assertEquals(RoleConstants.TYPE_ACCOUNT, updatedRole.getType());

		AccountRole accountRole = _accountRoleLocalService.fetchAccountRole(
			updatedRole.getClassPK());

		Assert.assertNotNull(accountRole);
		Assert.assertEquals(
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			accountRole.getAccountEntryId());
		Assert.assertEquals(role.getCompanyId(), accountRole.getCompanyId());
		Assert.assertEquals(role.getRoleId(), accountRole.getRoleId());
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private static final String _CLASS_NAME =
		"com.liferay.commerce.internal.upgrade.v7_1_2." +
			"CommerceAccountRoleUpgradeProcess";

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static GroupLocalService _groupLocalService;

	@Inject
	private static RoleLocalService _roleLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.commerce.internal.upgrade.registry.CommerceServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private static UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

}