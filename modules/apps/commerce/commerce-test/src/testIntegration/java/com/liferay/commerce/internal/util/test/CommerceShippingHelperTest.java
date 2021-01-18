/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.internal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.Dimensions;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
@Sync
public class CommerceShippingHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(), 0);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_user.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse();

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());
	}

	@Test
	public void testGetDimensions() throws Exception {
		frutillaRule.scenario(
			"Verify that the product dimensions are correctly retrieved from " +
				"the order"
		).given(
			"I add some product instances with some dimensions"
		).when(
			"The products are available on a channel"
		).then(
			"The dimensions are correctly retrieved from the order"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());
		CPInstance cpInstance2 = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());
		CPInstance cpInstance3 = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());

		double dimension = 10.5;

		cpInstance1 = _addDimensions(cpInstance1, dimension);
		cpInstance2 = _addDimensions(cpInstance2, dimension);
		cpInstance3 = _addDimensions(cpInstance3, dimension);

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance1.getCPInstanceId(),
			1);
		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance2.getCPInstanceId(),
			1);
		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance3.getCPInstanceId(),
			1);

		Dimensions actualDimensions = _commerceShippingHelper.getDimensions(
			commerceOrder.getCommerceOrderItems());

		double volume = dimension * dimension * dimension * 3;

		double dim = Math.cbrt(volume);

		Dimensions expectedDimensions = new Dimensions(dim, dim, dim);

		Assert.assertEquals(
			expectedDimensions.getDepth(), actualDimensions.getDepth(),
			0.00001);
		Assert.assertEquals(
			expectedDimensions.getHeight(), actualDimensions.getHeight(),
			0.00001);
		Assert.assertEquals(
			expectedDimensions.getWidth(), actualDimensions.getWidth(),
			0.00001);

		CommerceOrderLocalServiceUtil.deleteCommerceOrder(
			commerceOrder.getCommerceOrderId());
	}

	@Test
	public void testGetWeight() throws Exception {
		frutillaRule.scenario(
			"Verify that the product weights are correctly retrieved from " +
				"the order"
		).given(
			"I add some product instances with some weights"
		).when(
			"The products are available on a channel"
		).then(
			"The weights are correctly retrieved from the order"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());
		CPInstance cpInstance2 = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());
		CPInstance cpInstance3 = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance1.getCPInstanceId(),
			1);
		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance2.getCPInstanceId(),
			1);
		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance3.getCPInstanceId(),
			1);

		double actualWeight = _commerceShippingHelper.getWeight(
			commerceOrder.getCommerceOrderItems());

		double expectedWeight =
			cpInstance1.getWeight() + cpInstance2.getWeight() +
				cpInstance3.getWeight();

		Assert.assertEquals(expectedWeight, actualWeight, 0.0001);

		CommerceOrderLocalServiceUtil.deleteCommerceOrder(
			commerceOrder.getCommerceOrderId());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private CPInstance _addDimensions(CPInstance cpInstance, double dimension) {
		cpInstance.setWidth(dimension);
		cpInstance.setHeight(dimension);
		cpInstance.setDepth(dimension);

		return _cpInstanceLocalService.updateCPInstance(cpInstance);
	}

	private static CommerceInventoryWarehouse _commerceInventoryWarehouse;

	@Inject
	private static CPInstanceLocalService _cpInstanceLocalService;

	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceShippingHelper _commerceShippingHelper;

	@DeleteAfterTestRun
	private Company _company;

	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}