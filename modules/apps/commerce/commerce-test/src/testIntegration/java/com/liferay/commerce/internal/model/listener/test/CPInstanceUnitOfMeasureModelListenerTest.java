/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlags("COMMERCE-11287")
@RunWith(Arquillian.class)
public class CPInstanceUnitOfMeasureModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			_group.getCompanyId(), _group.getGroupId(),
			_commerceCurrency.getUserId(), _commerceCurrency.getCode());

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSkuFromCatalog(
			_commerceCatalog.getGroupId());

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			_cpInstance.getCPDefinition());
	}

	@Test
	public void testAddCPInstanceUnitOfMeasure() throws Exception {
		frutillaRule.scenario(
			"Add multiple CPInstance Unit Of Measure"
		).given(
			"An open order"
		).when(
			"The CPInstance Unit Of Measure is added"
		).then(
			"The second unit of measure does not update the order items"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_cpInstance.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		BigDecimal quantity = BigDecimal.valueOf(2);

		CommerceOrderItem commerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), quantity);

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure1 =
			CPTestUtil.addCPInstanceUnitOfMeasure(
				_group.getGroupId(), _cpInstance.getCPInstanceId(),
				RandomTestUtil.randomString(), BigDecimal.TEN,
				_cpInstance.getSku());

		commerceOrderItem = _commerceOrderItemLocalService.getCommerceOrderItem(
			commerceOrderItem.getCommerceOrderItemId());

		Assert.assertEquals(
			cpInstanceUnitOfMeasure1.getKey(),
			commerceOrderItem.getUnitOfMeasureKey());
		Assert.assertTrue(
			BigDecimalUtil.eq(
				commerceOrderItem.getQuantity(),
				quantity.multiply(
					cpInstanceUnitOfMeasure1.getIncrementalOrderQuantity())));

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure2 =
			CPTestUtil.addCPInstanceUnitOfMeasure(
				_group.getGroupId(), _cpInstance.getCPInstanceId(),
				RandomTestUtil.randomString(), BigDecimal.ONE,
				_cpInstance.getSku());

		commerceOrderItem = _commerceOrderItemLocalService.getCommerceOrderItem(
			commerceOrderItem.getCommerceOrderItemId());

		Assert.assertNotEquals(
			cpInstanceUnitOfMeasure2.getKey(),
			commerceOrderItem.getUnitOfMeasureKey());
		Assert.assertFalse(
			BigDecimalUtil.eq(
				commerceOrderItem.getQuantity(),
				quantity.multiply(
					cpInstanceUnitOfMeasure2.getIncrementalOrderQuantity())));
	}

	@Test
	public void testAddFirstCPInstanceUnitOfMeasure() throws Exception {
		frutillaRule.scenario(
			"Add the first CPInstance Unit Of Measure"
		).given(
			"An open order and a closed one"
		).when(
			"The CPInstance Unit Of Measure is added"
		).then(
			"Only the open order item unit of measure is updated"
		);

		CommerceOrder commerceOrder1 = CommerceTestUtil.addB2CCommerceOrder(
			_cpInstance.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		BigDecimal quantity = BigDecimal.valueOf(2);

		CommerceOrderItem commerceOrderItem1 =
			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder1.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), quantity);

		CommerceOrder commerceOrder2 = CommerceTestUtil.addB2CCommerceOrder(
			_cpInstance.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		commerceOrder2.setOrderStatus(
			CommerceOrderConstants.ORDER_STATUS_COMPLETED);

		commerceOrder2 = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder2);

		CommerceOrderItem commerceOrderItem2 =
			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder2.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), quantity);

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			CPTestUtil.addCPInstanceUnitOfMeasure(
				_group.getGroupId(), _cpInstance.getCPInstanceId(),
				RandomTestUtil.randomString(), BigDecimal.TEN,
				_cpInstance.getSku());

		commerceOrderItem1 =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItem1.getCommerceOrderItemId());

		Assert.assertEquals(
			cpInstanceUnitOfMeasure.getKey(),
			commerceOrderItem1.getUnitOfMeasureKey());
		Assert.assertTrue(
			BigDecimalUtil.eq(
				commerceOrderItem1.getQuantity(),
				quantity.multiply(
					cpInstanceUnitOfMeasure.getIncrementalOrderQuantity())));

		commerceOrderItem2 =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItem2.getCommerceOrderItemId());

		Assert.assertNotEquals(
			cpInstanceUnitOfMeasure.getKey(),
			commerceOrderItem2.getUnitOfMeasureKey());
		Assert.assertTrue(
			BigDecimalUtil.eq(commerceOrderItem2.getQuantity(), quantity));
	}

	@Test
	public void testDeleteCPInstanceUnitOfMeasure() throws Exception {
		frutillaRule.scenario(
			"Delete a CPInstance Unit Of Measure"
		).given(
			"An open order"
		).when(
			"The CPInstance Unit Of Measure is deleted"
		).then(
			"The order item unit of measure is not updated"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_cpInstance.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		CommerceOrderItem commerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), BigDecimal.ONE);

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			CPTestUtil.addCPInstanceUnitOfMeasure(
				_group.getGroupId(), _cpInstance.getCPInstanceId(),
				RandomTestUtil.randomString(), BigDecimal.ONE,
				_cpInstance.getSku());

		commerceOrderItem = _commerceOrderItemLocalService.getCommerceOrderItem(
			commerceOrderItem.getCommerceOrderItemId());

		Assert.assertEquals(
			cpInstanceUnitOfMeasure.getKey(),
			commerceOrderItem.getUnitOfMeasureKey());

		_cpInstanceUnitOfMeasureLocalService.deleteCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasure);

		commerceOrderItem = _commerceOrderItemLocalService.getCommerceOrderItem(
			commerceOrderItem.getCommerceOrderItemId());

		Assert.assertEquals(
			cpInstanceUnitOfMeasure.getKey(),
			commerceOrderItem.getUnitOfMeasureKey());
	}

	@Test
	public void testUpdateCPInstanceUnitOfMeasure() throws Exception {
		frutillaRule.scenario(
			"Update a CPInstance Unit Of Measure"
		).given(
			"An open order and a closed one"
		).when(
			"The CPInstance Unit Of Measure is updated"
		).then(
			"The order item unit of measure is removed only updated for open " +
				"orders"
		);

		CommerceOrder commerceOrder1 = CommerceTestUtil.addB2CCommerceOrder(
			_cpInstance.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		BigDecimal quantity = BigDecimal.valueOf(3);

		CommerceOrderItem commerceOrderItem1 =
			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder1.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), quantity);

		CommerceOrder commerceOrder2 = CommerceTestUtil.addB2CCommerceOrder(
			_cpInstance.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		CommerceOrderItem commerceOrderItem2 =
			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder2.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), quantity);

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			CPTestUtil.addCPInstanceUnitOfMeasure(
				_group.getGroupId(), _cpInstance.getCPInstanceId(),
				RandomTestUtil.randomString(), BigDecimal.TEN,
				_cpInstance.getSku());

		commerceOrderItem1 =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItem1.getCommerceOrderItemId());

		Assert.assertEquals(
			cpInstanceUnitOfMeasure.getKey(),
			commerceOrderItem1.getUnitOfMeasureKey());
		Assert.assertTrue(
			BigDecimalUtil.eq(
				commerceOrderItem1.getQuantity(),
				quantity.multiply(
					cpInstanceUnitOfMeasure.getIncrementalOrderQuantity())));

		commerceOrderItem2 =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItem2.getCommerceOrderItemId());

		Assert.assertEquals(
			cpInstanceUnitOfMeasure.getKey(),
			commerceOrderItem2.getUnitOfMeasureKey());
		Assert.assertTrue(
			BigDecimalUtil.eq(
				commerceOrderItem2.getQuantity(),
				quantity.multiply(
					cpInstanceUnitOfMeasure.getIncrementalOrderQuantity())));

		commerceOrder2 = _commerceOrderLocalService.getCommerceOrder(
			commerceOrder2.getCommerceOrderId());

		commerceOrder2.setOrderStatus(
			CommerceOrderConstants.ORDER_STATUS_COMPLETED);

		_commerceOrderLocalService.updateCommerceOrder(commerceOrder2);

		cpInstanceUnitOfMeasure.setIncrementalOrderQuantity(
			BigDecimal.valueOf(2));
		cpInstanceUnitOfMeasure.setKey(RandomTestUtil.randomString());

		cpInstanceUnitOfMeasure =
			_cpInstanceUnitOfMeasureLocalService.updateCPInstanceUnitOfMeasure(
				cpInstanceUnitOfMeasure);

		commerceOrderItem1 =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItem1.getCommerceOrderItemId());

		Assert.assertEquals(
			cpInstanceUnitOfMeasure.getKey(),
			commerceOrderItem1.getUnitOfMeasureKey());
		Assert.assertTrue(
			BigDecimalUtil.eq(
				commerceOrderItem1.getQuantity(),
				quantity.multiply(
					cpInstanceUnitOfMeasure.getIncrementalOrderQuantity())));

		commerceOrderItem2 =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItem2.getCommerceOrderItemId());

		Assert.assertNotEquals(
			cpInstanceUnitOfMeasure.getKey(),
			commerceOrderItem2.getUnitOfMeasureKey());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	@DeleteAfterTestRun
	private CommerceCatalog _commerceCatalog;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@DeleteAfterTestRun
	private CommercePriceList _commercePriceList;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@Inject
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

	@DeleteAfterTestRun
	private Group _group;

}