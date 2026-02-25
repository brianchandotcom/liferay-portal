/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.petra.string.StringBundler;
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
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class CommerceInventoryWarehouseServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		CommerceInventoryTestUtil.addCommerceInventoryWarehouse(true);

		_role = _roleLocalService.addRole(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(), null, 0,
			RandomTestUtil.randomString(), null, null,
			RoleConstants.TYPE_REGULAR, null,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
		_user = UserTestUtil.addUser();

		_roleLocalService.addUserRole(_user.getUserId(), _role);
	}

	@Test
	public void testGetCommerceInventoryWarehouses1() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), false, -1, -1, null);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), false, -1, -1, null);
		}
	}

	@Test
	public void testGetCommerceInventoryWarehouses3() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), -1, -1, null);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), -1, -1, null);
		}
	}

	@Test
	public void testGetCommerceInventoryWarehouses4() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), 0, _user.getGroupId(), false);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), 0, _user.getGroupId(), false);
		}
	}

	@Test
	public void testGetCommerceInventoryWarehousesCount1() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				getCommerceInventoryWarehousesCount(
					_user.getCompanyId(), false, null);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				getCommerceInventoryWarehousesCount(
					_user.getCompanyId(), false, null);
		}
	}

	@Test
	public void testGetCommerceInventoryWarehousesCount2() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				getCommerceInventoryWarehousesCount(_user.getCompanyId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				getCommerceInventoryWarehousesCount(_user.getCompanyId());
		}
	}

	@Test
	public void testSearch() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.search(
				_user.getCompanyId(), false, null, null, -1, -1, null);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.search(
				_user.getCompanyId(), false, null, null, -1, -1, null);
		}
	}

	@Test
	public void testSearchCommerceInventoryWarehousesCount() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				searchCommerceInventoryWarehousesCount(
					_user.getCompanyId(), false, null, null);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				searchCommerceInventoryWarehousesCount(
					_user.getCompanyId(), false, null, null);
		}
	}

	private void _assertMessage(String actionKey, String message, long userId) {
		Assert.assertTrue(
			message.contains(
				StringBundler.concat(
					"User ", userId, " must have ", actionKey,
					" permission for")));
	}

	@Inject
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	private User _user;

}