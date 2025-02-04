/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v2_11_2.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.impl.ResourceActionImpl;
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
 * @author Joao Cordeiro
 */
@DataGuard(scope = DataGuard.Scope.NONE)
@RunWith(Arquillian.class)
public class RoleResourceUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());
	}

	@Test
	public void testRoleResourceUpgradeProcess() throws Exception {
		Role role = _roleLocalService.addRole(
			RandomTestUtil.randomString(), _serviceContext.getUserId(), null, 0,
			RandomTestUtil.randomString(), null, null,
			RoleConstants.TYPE_REGULAR, null, _serviceContext);

		_resourcePermissionLocalService.addResourcePermission(
			_serviceContext.getCompanyId(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_serviceContext.getCompanyId()), role.getRoleId(),
			ActionKeys.DELETE);

		_runUpgrade();

		CacheRegistryUtil.clear();

		_entityCache.clearCache(ResourceActionImpl.class);
		_finderCache.clearCache(ResourceActionImpl.class);

		_resourceActionLocalService.checkResourceActions();

		Assert.assertNotNull(
			_resourceActionLocalService.fetchResourceAction(
				AccountEntry.class.getName(), ActionKeys.DEACTIVATE));
		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				_serviceContext.getCompanyId(), AccountEntry.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(_serviceContext.getCompanyId()),
				role.getRoleId(), ActionKeys.DEACTIVATE));
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private static final String _CLASS_NAME =
		"com.liferay.account.internal.upgrade.v2_11_2." +
			"RoleResourceUpgradeProcess";

	@Inject
	private static ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private static ResourcePermissionLocalService
		_resourcePermissionLocalService;

	@Inject
	private static RoleLocalService _roleLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.account.internal.upgrade.registry.AccountServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private FinderCache _finderCache;

	private Group _group;
	private ServiceContext _serviceContext;
	private User _user;

}