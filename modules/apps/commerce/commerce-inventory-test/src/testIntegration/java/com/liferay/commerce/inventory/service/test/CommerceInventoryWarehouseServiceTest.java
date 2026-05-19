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
		_commerceInventoryWarehouse = _addCommerceInventoryWarehouse();

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
			CommerceInventoryConstants.RESOURCE_NAME,
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
				_commerceInventoryWarehouse.getCommerceInventoryWarehouseId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.DELETE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			CommerceInventoryWarehouse.class.getName(), ActionKeys.DELETE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.deleteCommerceInventoryWarehouse(
				_commerceInventoryWarehouse.getCommerceInventoryWarehouseId());
		}
	}

	@Test
	public void testFetchByCommerceInventoryWarehouse() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				fetchByCommerceInventoryWarehouse(
					_commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			CommerceInventoryWarehouse.class.getName(), ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				fetchByCommerceInventoryWarehouse(
					_commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId());
		}
	}

	@Test
	public void testFetchCommerceInventoryWarehouseByExternalReferenceCode()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				fetchCommerceInventoryWarehouseByExternalReferenceCode(
					_commerceInventoryWarehouse.getExternalReferenceCode(),
					_commerceInventoryWarehouse.getCompanyId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			CommerceInventoryWarehouse.class.getName(), ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				fetchCommerceInventoryWarehouseByExternalReferenceCode(
					_commerceInventoryWarehouse.getExternalReferenceCode(),
					_commerceInventoryWarehouse.getCompanyId());
		}
	}

	@Test
	public void testGeolocateCommerceInventoryWarehouse() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				geolocateCommerceInventoryWarehouse(
					_commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					RandomTestUtil.nextDouble(), RandomTestUtil.nextDouble());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.UPDATE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			CommerceInventoryWarehouse.class.getName(), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				geolocateCommerceInventoryWarehouse(
					_commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					RandomTestUtil.nextDouble(), RandomTestUtil.nextDouble());
		}
	}

	@Test
	public void testGetCommerceInventoryWarehouse() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
				_commerceInventoryWarehouse.getCommerceInventoryWarehouseId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			CommerceInventoryWarehouse.class.getName(), ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
				_commerceInventoryWarehouse.getCommerceInventoryWarehouseId());
		}
	}

	@Test
	public void testGetCommerceInventoryWarehouses() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

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

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), false, null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), 0, 0, false);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			CommerceInventoryConstants.RESOURCE_NAME,
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);
			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), false, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);
			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), false, null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);
			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_user.getCompanyId(), 0, 0, false);
		}
	}

	@Test
	public void testGetCommerceInventoryWarehousesCount() throws Exception {
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
			CommerceInventoryConstants.RESOURCE_NAME,
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				getCommerceInventoryWarehousesCount(_user.getCompanyId());
			_commerceInventoryWarehouseService.
				getCommerceInventoryWarehousesCount(
					_user.getCompanyId(), false, null);
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
			CommerceInventoryConstants.RESOURCE_NAME,
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
			CommerceInventoryConstants.RESOURCE_NAME,
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
				_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				false);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.UPDATE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			CommerceInventoryWarehouse.class.getName(), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.setActive(
				_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				false);
		}
	}

	@Test
	public void testUpdateCommerceInventoryWarehouse() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.updateCommerceInventoryWarehouse(
				_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null, null,
				RandomTestUtil.nextDouble(), RandomTestUtil.nextDouble(),
				_commerceInventoryWarehouse.getMvccVersion(),
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.UPDATE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			CommerceInventoryWarehouse.class.getName(), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.updateCommerceInventoryWarehouse(
				_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null, null,
				RandomTestUtil.nextDouble(), RandomTestUtil.nextDouble(),
				_commerceInventoryWarehouse.getMvccVersion(),
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
		}
	}

	@Test
	public void testUpdateCommerceInventoryWarehouseExternalReferenceCode()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				updateCommerceInventoryWarehouseExternalReferenceCode(
					RandomTestUtil.randomString(),
					_commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.UPDATE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(
			CommerceInventoryWarehouse.class.getName(), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryWarehouseService.
				updateCommerceInventoryWarehouseExternalReferenceCode(
					RandomTestUtil.randomString(),
					_commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId());
		}
	}

	private CommerceInventoryWarehouse _addCommerceInventoryWarehouse()
		throws Exception {

		return _commerceInventoryWarehouseService.addCommerceInventoryWarehouse(
			RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), true,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.nextDouble(), RandomTestUtil.nextDouble(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
	}

	private void _assertMessage(String actionId, String message, long userId) {
		Assert.assertTrue(
			message.contains(
				StringBundler.concat(
					"User ", userId, " must have ", actionId,
					" permission for")));
	}

	private void _setResourcePermissions(String name, String actionId)
		throws Exception {

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), name,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), _role.getRoleId(),
			new String[] {actionId});
	}

	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

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