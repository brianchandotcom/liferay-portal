/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseRelLocalServiceUtil;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.CommerceOrderTypeLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.headless.commerce.admin.inventory.client.dto.v1_0.OrderType;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class OrderTypeResourceTest extends BaseOrderTypeResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_commerceOrderType =
			CommerceOrderTypeLocalServiceUtil.addCommerceOrderType(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomBoolean(), displayDateConfig.getMonth(),
				displayDateConfig.getDay(), displayDateConfig.getYear(),
				displayDateConfig.getHour(), displayDateConfig.getMinute(), 0,
				expirationDateConfig.getMonth(), expirationDateConfig.getDay(),
				expirationDateConfig.getYear(), expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(), true, _serviceContext);

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetWarehouseOrderTypeOrderType() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetWarehouseOrderTypeOrderTypeNotFound()
		throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected OrderType randomOrderType() throws Exception {
		return new OrderType() {
			{
				id = _commerceOrderType.getCommerceOrderTypeId();
				name = LanguageUtils.getLanguageIdMap(
					_commerceOrderType.getNameMap());
			}
		};
	}

	@Override
	protected OrderType testGetWarehouseOrderTypeOrderType_addOrderType()
		throws Exception {

		return _addOrderType();
	}

	@Override
	protected Long testGetWarehouseOrderTypeOrderType_getWarehouseOrderTypeId()
		throws Exception {

		return _getWarehouseOrderTypeId();
	}

	@Override
	protected Long
			testGraphQLGetWarehouseOrderTypeOrderType_getWarehouseOrderTypeId()
		throws Exception {

		return _getWarehouseOrderTypeId();
	}

	@Override
	protected OrderType testGraphQLOrderType_addOrderType() throws Exception {
		return _addOrderType();
	}

	private OrderType _addOrderType() throws Exception {
		return new OrderType() {
			{
				id = _commerceOrderType.getCommerceOrderTypeId();
				name = LanguageUtils.getLanguageIdMap(
					_commerceOrderType.getNameMap());
			}
		};
	}

	private Long _getWarehouseOrderTypeId() throws Exception {
		_commerceInventoryWarehouseRel =
			CommerceInventoryWarehouseRelLocalServiceUtil.
				addCommerceInventoryWarehouseRel(
					_user.getUserId(), CommerceOrderType.class.getName(),
					_commerceOrderType.getCommerceOrderTypeId(),
					_commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId());

		return _commerceInventoryWarehouseRel.
			getCommerceInventoryWarehouseRelId();
	}

	@DeleteAfterTestRun
	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	@DeleteAfterTestRun
	private CommerceInventoryWarehouseRel _commerceInventoryWarehouseRel;

	@DeleteAfterTestRun
	private CommerceOrderType _commerceOrderType;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}