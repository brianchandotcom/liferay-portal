/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v11_5_3.test;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class CommercePermissionUpgradeProcessTest {

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
	public void testUpgrade() throws Exception {
		_removeResourcePermissions();

		_runUpgrade();

		Assert.assertTrue(
			ListUtil.exists(
				_resourcePermissionLocalService.getRoles(
					_group.getCompanyId(), CommerceOrderConstants.RESOURCE_NAME,
					ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					CommerceOrderActionKeys.
						MANAGE_COMMERCE_ORDER_MULTISHIPPING),
				role -> StringUtil.equals(
					role.getName(),
					AccountRoleConstants.
						REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR)));
		Assert.assertTrue(
			ListUtil.exists(
				_resourcePermissionLocalService.getRoles(
					_group.getCompanyId(), CommerceOrderConstants.RESOURCE_NAME,
					ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					CommerceOrderActionKeys.
						MANAGE_COMMERCE_ORDER_MULTISHIPPING),
				role -> StringUtil.equals(
					role.getName(),
					AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER)));
		Assert.assertTrue(
			ListUtil.exists(
				_resourcePermissionLocalService.getRoles(
					_group.getCompanyId(), CommerceOrderConstants.RESOURCE_NAME,
					ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					CommerceOrderActionKeys.
						MANAGE_COMMERCE_ORDER_MULTISHIPPING),
				role -> StringUtil.equals(
					role.getName(),
					AccountRoleConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER)));
	}

	private void _removeResourcePermissions() throws Exception {
		Role role = _roleLocalService.fetchRole(
			_group.getCompanyId(),
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR);

		_resourcePermissionLocalService.removeResourcePermission(
			_group.getCompanyId(), CommerceOrderConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			role.getRoleId(),
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_MULTISHIPPING);

		role = _roleLocalService.fetchRole(
			_group.getCompanyId(),
			AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER);

		_resourcePermissionLocalService.removeResourcePermission(
			_group.getCompanyId(), CommerceOrderConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			role.getRoleId(),
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_MULTISHIPPING);

		role = _roleLocalService.fetchRole(
			_group.getCompanyId(),
			AccountRoleConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER);

		_resourcePermissionLocalService.removeResourcePermission(
			_group.getCompanyId(), CommerceOrderConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			role.getRoleId(),
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_MULTISHIPPING);
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.commerce.internal.upgrade.v11_5_3." +
			"CommercePermissionUpgradeProcess";

	@Inject
	private static ResourcePermissionLocalService
		_resourcePermissionLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.commerce.internal.upgrade.registry.CommerceServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private RoleLocalService _roleLocalService;

}