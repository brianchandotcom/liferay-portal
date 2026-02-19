/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.model.CommerceInventoryAudit;
import com.liferay.commerce.inventory.service.CommerceInventoryAuditLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryAuditService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

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
public class CommerceInventoryAuditServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		_commerceInventoryAudit =
			_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
				_user.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), BigDecimal.ONE,
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		_role = _roleLocalService.addRole(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(), null, 0,
			RandomTestUtil.randomString(), null, null,
			RoleConstants.TYPE_REGULAR, null,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		_roleLocalService.addUserRole(_user.getUserId(), _role);
	}

	@Test
	public void testGetCommerceInventoryAudits() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertTrue(
				ListUtil.isEmpty(
					_commerceInventoryAuditService.getCommerceInventoryAudits(
						_user.getCompanyId(), _commerceInventoryAudit.getSku(),
						_commerceInventoryAudit.getUnitOfMeasureKey(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS)));
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertFalse(
				ListUtil.isEmpty(
					_commerceInventoryAuditService.getCommerceInventoryAudits(
						_user.getCompanyId(), _commerceInventoryAudit.getSku(),
						_commerceInventoryAudit.getUnitOfMeasureKey(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS)));
		}
	}

	@Test
	public void testGetCommerceInventoryAuditsCount() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertEquals(
				0,
				_commerceInventoryAuditService.getCommerceInventoryAuditsCount(
					_user.getCompanyId(), _commerceInventoryAudit.getSku(),
					_commerceInventoryAudit.getUnitOfMeasureKey()));
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertEquals(
				1,
				_commerceInventoryAuditService.getCommerceInventoryAuditsCount(
					_user.getCompanyId(), _commerceInventoryAudit.getSku(),
					_commerceInventoryAudit.getUnitOfMeasureKey()));
		}
	}

	private CommerceInventoryAudit _commerceInventoryAudit;

	@Inject
	private CommerceInventoryAuditLocalService
		_commerceInventoryAuditLocalService;

	@Inject
	private CommerceInventoryAuditService _commerceInventoryAuditService;

	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	private User _user;

}