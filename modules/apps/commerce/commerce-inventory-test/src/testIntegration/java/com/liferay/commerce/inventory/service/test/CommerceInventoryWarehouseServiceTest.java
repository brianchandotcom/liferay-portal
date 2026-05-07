/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
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
		_commerceInventoryWarehouse1 =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(true);
		_commerceInventoryWarehouse2 =
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
	public void testAddCommerceInventoryWarehouse() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_addCommerceInventoryWarehouse();

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.ADD_WAREHOUSE,
				exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			TestPropsValues.getCompanyId(),
			CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.ADD_WAREHOUSE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_addCommerceInventoryWarehouse();
		}
	}

	@Test
	public void testDeleteCommerceInventoryWarehouse() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.deleteCommerceInventoryWarehouse(
				_commerceInventoryWarehouse1.getCommerceInventoryWarehouseId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.DELETE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			_commerceInventoryWarehouse1.getCompanyId(),
			CommerceInventoryWarehouse.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(
				_commerceInventoryWarehouse1.getCommerceInventoryWarehouseId()),
			ActionKeys.DELETE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.deleteCommerceInventoryWarehouse(
				_commerceInventoryWarehouse1.getCommerceInventoryWarehouseId());
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.deleteCommerceInventoryWarehouse(
				_commerceInventoryWarehouse2.getCommerceInventoryWarehouseId());
			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.DELETE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			TestPropsValues.getCompanyId(),
			CommerceInventoryWarehouse.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), ActionKeys.DELETE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.deleteCommerceInventoryWarehouse(
				_commerceInventoryWarehouse2.getCommerceInventoryWarehouseId());
		}
	}

	@Test
	public void testGetCommerceInventoryWarehouse() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
				_commerceInventoryWarehouse1.getCommerceInventoryWarehouseId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			_commerceInventoryWarehouse1.getCompanyId(),
			CommerceInventoryWarehouse.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(
				_commerceInventoryWarehouse1.getCommerceInventoryWarehouseId()),
			ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
				_commerceInventoryWarehouse1.getCommerceInventoryWarehouseId());
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
				_commerceInventoryWarehouse2.getCommerceInventoryWarehouseId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			TestPropsValues.getCompanyId(),
			CommerceInventoryWarehouse.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
				_commerceInventoryWarehouse2.getCommerceInventoryWarehouseId());
		}
	}

	@Test
	public void testGetCommerceInventoryWarehouses() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), false, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			TestPropsValues.getCompanyId(),
			CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), false, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);
		}
	}

	@Test
	public void testGetCommerceInventoryWarehousesCount() throws Exception {
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

		_setResourcePermissions(
			TestPropsValues.getCompanyId(),
			CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				getCommerceInventoryWarehousesCount(
					_user.getCompanyId(), false, null);
		}

		RoleTestUtil.removeResourcePermission(
			_role.getName(), CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

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

		_setResourcePermissions(
			TestPropsValues.getCompanyId(),
			CommerceInventoryConstants.RESOURCE_NAME,
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
				_user.getCompanyId(), false, null, null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			TestPropsValues.getCompanyId(),
			CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.search(
				_user.getCompanyId(), false, null, null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);
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

		_setResourcePermissions(
			TestPropsValues.getCompanyId(),
			CommerceInventoryConstants.RESOURCE_NAME,
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

	@Test
	public void testSetActive() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.setActive(
				_commerceInventoryWarehouse1.getCommerceInventoryWarehouseId(),
				false);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.UPDATE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			_commerceInventoryWarehouse1.getCompanyId(),
			CommerceInventoryWarehouse.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(
				_commerceInventoryWarehouse1.getCommerceInventoryWarehouseId()),
			ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.setActive(
				_commerceInventoryWarehouse1.getCommerceInventoryWarehouseId(),
				false);
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.setActive(
				_commerceInventoryWarehouse2.getCommerceInventoryWarehouseId(),
				false);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.UPDATE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			TestPropsValues.getCompanyId(),
			CommerceInventoryWarehouse.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.setActive(
				_commerceInventoryWarehouse2.getCommerceInventoryWarehouseId(),
				false);
		}
	}

	private CommerceInventoryWarehouse _addCommerceInventoryWarehouse()
		throws Exception {

		return _commerceInventoryWarehouseService.addCommerceInventoryWarehouse(
			null, RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), true,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.nextDouble(), RandomTestUtil.nextDouble(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
	}

	private void _assertMessage(String actionKey, String message, long userId) {
		Assert.assertTrue(
			message.contains(
				StringBundler.concat(
					"User ", userId, " must have ", actionKey,
					" permission for")));
	}

	private void _setResourcePermissions(
			long companyId, String name, int scope, String primKey,
			String actionId)
		throws Exception {

		_resourcePermissionLocalService.setResourcePermissions(
			companyId, name, scope, primKey, _role.getRoleId(),
			new String[] {actionId});
	}

	private CommerceInventoryWarehouse _commerceInventoryWarehouse1;
	private CommerceInventoryWarehouse _commerceInventoryWarehouse2;

	@Inject
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	private User _user;

}