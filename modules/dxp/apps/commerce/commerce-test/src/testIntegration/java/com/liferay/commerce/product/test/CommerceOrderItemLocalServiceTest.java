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

package com.liferay.commerce.product.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceCountryLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceRegionLocalService;
import com.liferay.commerce.service.CommerceWarehouseItemLocalService;
import com.liferay.commerce.service.CommerceWarehouseLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

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
@RunWith(Arquillian.class)
public class CommerceOrderItemLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();
	}

	@Test(expected = CommerceOrderValidatorException.class)
	public void testAddNotPublishedProductToOrder() throws Exception {
		frutillaRule.scenario(
			"Add a SKU (cpInstance) to an order"
		).given(
			"A group"
		).and(
			"A user"
		).and(
			"A SKU linked to a not published product"
		).when(
			"There is availability for the SKU"
		).then(
			"I'm able to add the SKU to an order"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		_cpDefinitionLocalService.updateStatus(
			_user.getUserId(), cpDefinition.getCPDefinitionId(),
			WorkflowConstants.STATUS_DRAFT, serviceContext, null);

		CommerceCountry commerceCountry =
			_commerceCountryLocalService.fetchCommerceCountry(
				serviceContext.getScopeGroupId(), 380);

		CommerceRegion commerceRegion =
			_commerceRegionLocalService.getCommerceRegion(
				commerceCountry.getCommerceCountryId(), "VE");

		CommerceWarehouse commerceWarehouse =
			_commerceWarehouseLocalService.addCommerceWarehouse(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				true, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				commerceRegion.getCommerceRegionId(),
				commerceCountry.getCommerceCountryId(), 45.4386111, 12.3266667,
				serviceContext);

		_commerceWarehouseItemLocalService.addCommerceWarehouseItem(
			commerceWarehouse.getCommerceWarehouseId(),
			cpInstance.getCPInstanceId(), 2, serviceContext);

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addUserCommerceOrder(
				_group.getGroupId(), _user.getUserId());

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				_group.getGroupId());

		CommerceContext commerceContext = new TestCommerceContext(
			commerceCurrency);

		_commerceOrderItemLocalService.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance.getCPInstanceId(), 1,
			0, null, new BigDecimal(3), commerceContext, serviceContext);
	}

	@Test(expected = CommerceOrderValidatorException.class)
	public void testAddNotPublishedSKUToOrder() throws Exception {
		frutillaRule.scenario(
			"Add a SKU (cpInstance) to an order"
		).given(
			"A group"
		).and(
			"A user"
		).and(
			"A not pubblished SKU"
		).when(
			"There is availability for the SKU"
		).then(
			"I'm not able to add the SKU to an order"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_cpInstanceLocalService.updateStatus(
			_user.getUserId(), cpInstance.getCPInstanceId(),
			WorkflowConstants.STATUS_DRAFT, serviceContext, null);

		CommerceCountry commerceCountry =
			_commerceCountryLocalService.fetchCommerceCountry(
				serviceContext.getScopeGroupId(), 380);

		CommerceRegion commerceRegion =
			_commerceRegionLocalService.getCommerceRegion(
				commerceCountry.getCommerceCountryId(), "VE");

		CommerceWarehouse commerceWarehouse =
			_commerceWarehouseLocalService.addCommerceWarehouse(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				true, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				commerceRegion.getCommerceRegionId(),
				commerceCountry.getCommerceCountryId(), 45.4386111, 12.3266667,
				serviceContext);

		_commerceWarehouseItemLocalService.addCommerceWarehouseItem(
			commerceWarehouse.getCommerceWarehouseId(),
			cpInstance.getCPInstanceId(), 2, serviceContext);

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addUserCommerceOrder(
				_group.getGroupId(), _user.getUserId());

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				_group.getGroupId());

		CommerceContext commerceContext = new TestCommerceContext(
			commerceCurrency);

		_commerceOrderItemLocalService.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance.getCPInstanceId(), 1,
			0, null, new BigDecimal(3), commerceContext, serviceContext);
	}

	@Test
	public void testAddSKUToOrder() throws Exception {
		frutillaRule.scenario(
			"Add a SKU (cpInstance) to an order"
		).given(
			"A group"
		).and(
			"A user"
		).and(
			"A published SKU"
		).when(
			"There is availability for the SKU"
		).then(
			"I'm able to add the SKU to an order"
		);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		CommerceCountry commerceCountry =
			_commerceCountryLocalService.fetchCommerceCountry(
				serviceContext.getScopeGroupId(), 380);

		CommerceRegion commerceRegion =
			_commerceRegionLocalService.getCommerceRegion(
				commerceCountry.getCommerceCountryId(), "VE");

		CommerceWarehouse commerceWarehouse =
			_commerceWarehouseLocalService.addCommerceWarehouse(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				true, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				commerceRegion.getCommerceRegionId(),
				commerceCountry.getCommerceCountryId(), 45.4386111, 12.3266667,
				serviceContext);

		_commerceWarehouseItemLocalService.addCommerceWarehouseItem(
			commerceWarehouse.getCommerceWarehouseId(),
			cpInstance.getCPInstanceId(), 2, serviceContext);

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addUserCommerceOrder(
				_group.getGroupId(), _user.getUserId());

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				_group.getGroupId());

		CommerceContext commerceContext = new TestCommerceContext(
			commerceCurrency);

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				cpInstance.getCPInstanceId(), 1, 0, null, new BigDecimal(3),
				commerceContext, serviceContext);

		int end = _commerceOrderItemLocalService.getCommerceOrderItemsCount(
			commerceOrder.getCommerceOrderId());

		List<CommerceOrderItem> commerceOrderItems =
			_commerceOrderItemLocalService.getCommerceOrderItems(
				commerceOrder.getCommerceOrderId(), 0, end);

		Assert.assertEquals(
			commerceOrderItems.toString(), 1, commerceOrderItems.size());

		CommerceOrderItem commerceOrderItemRetrieved = commerceOrderItems.get(
			0);

		Assert.assertEquals(
			commerceOrderItem.getCommerceOrderItemId(),
			commerceOrderItemRetrieved.getCommerceOrderItemId());
	}

	public FrutillaRule frutillaRule = new FrutillaRule();

	@Inject
	private CommerceCountryLocalService _commerceCountryLocalService;

	@Inject
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Inject
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommerceRegionLocalService _commerceRegionLocalService;

	@Inject
	private CommerceWarehouseItemLocalService
		_commerceWarehouseItemLocalService;

	@Inject
	private CommerceWarehouseLocalService _commerceWarehouseLocalService;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

	private class TestCommerceContext implements CommerceContext {

		public TestCommerceContext(CommerceCurrency commerceCurrency) {
			_commerceCurrency = commerceCurrency;
		}

		@Override
		public CommerceCurrency getCommerceCurrency() throws PortalException {
			return _commerceCurrency;
		}

		@Override
		public CommerceOrder getCommerceOrder() throws PortalException {
			return null;
		}

		@Override
		public Optional<CommercePriceList> getCommercePriceList()
			throws PortalException {

			return null;
		}

		@Override
		public long[] getCommerceUserSegmentEntryIds() throws PortalException {
			return null;
		}

		@Override
		public List<CPRule> getCPRules() throws PortalException {
			return null;
		}

		@Override
		public Organization getOrganization() throws PortalException {
			return null;
		}

		private final CommerceCurrency _commerceCurrency;

	}

}