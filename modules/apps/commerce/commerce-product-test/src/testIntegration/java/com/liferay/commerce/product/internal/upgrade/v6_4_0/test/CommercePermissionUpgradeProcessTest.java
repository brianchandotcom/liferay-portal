/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v6_4_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.change.tracking.test.CPTaxCategoryTableReferenceDefinitionTest;
import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.impl.ResourceActionImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tancredi Covioli
 */
@RunWith(Arquillian.class)
public class CommercePermissionUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Group group = GroupTestUtil.addGroup();
		User user = UserTestUtil.addUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getCompanyId(), group.getGroupId(), user.getUserId());

		_cpTaxCategory = _cpTaxCategoryLocalService.addCPTaxCategory(
			CPTaxCategoryTableReferenceDefinitionTest.class.getSimpleName(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), _serviceContext);

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
	}

	@Test
	public void testDoUpgrade() throws Exception {
		_resourceActionLocalService.checkResourceActions(
			"com.liferay.commerce.tax",
			Collections.singletonList(
				"MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES"));

		_resourcePermissionLocalService.addResourcePermission(
			_serviceContext.getCompanyId(), "com.liferay.commerce.tax",
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_serviceContext.getCompanyId()), _role.getRoleId(),
			"MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES");

		Assert.assertEquals(
			1,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				_cpTaxCategory.getCompanyId(), CPTaxCategory.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_cpTaxCategory.getCPTaxCategoryId())));

		_resourceLocalService.deleteResource(
			_cpTaxCategory, ResourceConstants.SCOPE_INDIVIDUAL);

		_runUpgrade();

		CacheRegistryUtil.clear();

		_entityCache.clearCache();
		_finderCache.clearCache(ResourceActionImpl.class);
		_multiVMPool.clear();

		Assert.assertNull(
			_resourceActionLocalService.fetchResourceAction(
				"com.liferay.commerce.tax",
				"MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES"));
		Assert.assertEquals(
			1,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				_cpTaxCategory.getCompanyId(), CPTaxCategory.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_cpTaxCategory.getCPTaxCategoryId())));
		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				_serviceContext.getCompanyId(), "com.liferay.commerce.tax",
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(_serviceContext.getCompanyId()),
				_role.getRoleId(),
				CPActionKeys.ADD_COMMERCE_PRODUCT_TAX_CATEGORIES));
		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				_serviceContext.getCompanyId(), CPTaxCategory.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(_serviceContext.getCompanyId()),
				_role.getRoleId(), ActionKeys.DELETE));
		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				_serviceContext.getCompanyId(), CPTaxCategory.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(_serviceContext.getCompanyId()),
				_role.getRoleId(), ActionKeys.PERMISSIONS));
		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				_serviceContext.getCompanyId(), CPTaxCategory.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(_serviceContext.getCompanyId()),
				_role.getRoleId(), ActionKeys.UPDATE));
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private static final String _CLASS_NAME =
		"com.liferay.commerce.product.internal.upgrade.v6_4_0." +
			"CommercePermissionUpgradeProcess";

	@Inject
	private static CPTaxCategoryLocalService _cpTaxCategoryLocalService;

	@Inject
	private static EntityCache _entityCache;

	@Inject
	private static FinderCache _finderCache;

	@Inject
	private static MultiVMPool _multiVMPool;

	@Inject
	private static ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private static ResourceLocalService _resourceLocalService;

	@Inject
	private static ResourcePermissionLocalService
		_resourcePermissionLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.commerce.product.internal.upgrade.registry.CommerceProductServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private CPTaxCategory _cpTaxCategory;
	private Role _role;
	private ServiceContext _serviceContext;

}