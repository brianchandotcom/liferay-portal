/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.shipment.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceShipmentItemLocalServiceUtil;
import com.liferay.commerce.service.CommerceShipmentLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.shipment.client.dto.v1_0.ShipmentItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class ShipmentItemResourceTest extends BaseShipmentItemResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testGroup.getGroupId());

		_user = UserLocalServiceUtil.getUser(_serviceContext.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testCompany.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());

		BigDecimal value = BigDecimal.valueOf(RandomTestUtil.nextDouble());

		_commerceOrder = CommerceTestUtil.createCommerceOrderForShipping(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId(), value);

		BigDecimal price = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			_commerceOrder.getGroupId(), price);

		_cpInstance = CPInstanceLocalServiceUtil.updateCPInstance(_cpInstance);

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse,
			BigDecimal.valueOf(100), _cpInstance.getSku(), StringPool.BLANK);

		_commerceShipment =
			CommerceShipmentLocalServiceUtil.addCommerceShipment(
				RandomTestUtil.randomString(), _commerceOrder.getGroupId(),
				_commerceOrder.getCommerceAccountId(),
				_commerceOrder.getShippingAddressId(),
				_commerceOrder.getCommerceShippingMethodId(),
				_commerceOrder.getShippingOptionName(), _serviceContext);
	}

	@Override
	@Test
	public void testPatchShipmentItemByExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		ShipmentItem shipmentItem = _addShipmentItem(
			externalReferenceCode, randomShipmentItem());

		BigDecimal quantity = shipmentItem.getQuantity();

		quantity = quantity.subtract(BigDecimal.ONE);

		shipmentItem.setQuantity(quantity);

		ShipmentItem newShipmentItem =
			shipmentItemResource.patchShipmentItemByExternalReferenceCode(
				externalReferenceCode, shipmentItem);

		Assert.assertEquals(
			String.valueOf(quantity),
			String.valueOf(newShipmentItem.getQuantity()));
	}

	@Override
	@Test
	public void testPutShipmentByExternalReferenceCodeItem() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"quantity", "warehouseId"};
	}

	@Override
	protected ShipmentItem randomShipmentItem() throws Exception {
		CommerceOrderItem commerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				_commerceOrder.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), BigDecimal.valueOf(5));

		return new ShipmentItem() {
			{
				createDate = RandomTestUtil.nextDate();
				externalReferenceCode = RandomTestUtil.randomString();
				modifiedDate = RandomTestUtil.nextDate();
				orderItemId = commerceOrderItem.getCommerceOrderItemId();
				quantity = commerceOrderItem.getQuantity();
				shipmentId = _commerceShipment.getCommerceShipmentId();
				userName = commerceOrderItem.getUserName();
				warehouseId =
					_commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId();
			}
		};
	}

	@Override
	protected ShipmentItem testDeleteShipmentItem_addShipmentItem()
		throws Exception {

		return _addShipmentItem();
	}

	@Override
	protected ShipmentItem
			testDeleteShipmentItemByExternalReferenceCode_addShipmentItem()
		throws Exception {

		return _addShipmentItem();
	}

	@Override
	protected ShipmentItem
			testGetShipmentByExternalReferenceCodeItem_addShipmentItem()
		throws Exception {

		return _addShipmentItem();
	}

	@Override
	protected ShipmentItem
			testGetShipmentByExternalReferenceCodeItemsPage_addShipmentItem(
				String externalReferenceCode, ShipmentItem shipmentItem)
		throws Exception {

		return _addShipmentItem(
			shipmentItem.getExternalReferenceCode(), shipmentItem);
	}

	@Override
	protected String
			testGetShipmentByExternalReferenceCodeItemsPage_getExternalReferenceCode()
		throws Exception {

		return _commerceShipment.getExternalReferenceCode();
	}

	@Override
	protected ShipmentItem testGetShipmentItem_addShipmentItem()
		throws Exception {

		return _addShipmentItem();
	}

	@Override
	protected ShipmentItem testGetShipmentItemsPage_addShipmentItem(
			Long shipmentId, ShipmentItem shipmentItem)
		throws Exception {

		return _addShipmentItem(
			RandomTestUtil.randomString(), shipmentId,
			shipmentItem.getOrderItemId());
	}

	@Override
	protected Long testGetShipmentItemsPage_getShipmentId() throws Exception {
		return _commerceShipment.getCommerceShipmentId();
	}

	@Override
	protected ShipmentItem testGraphQLShipmentItem_addShipmentItem()
		throws Exception {

		return _addShipmentItem();
	}

	@Override
	protected ShipmentItem testPatchShipmentItem_addShipmentItem()
		throws Exception {

		return _addShipmentItem();
	}

	@Override
	protected ShipmentItem testPostShipmentItem_addShipmentItem(
			ShipmentItem shipmentItem)
		throws Exception {

		return _addShipmentItem(
			shipmentItem.getExternalReferenceCode(), shipmentItem);
	}

	@Override
	protected ShipmentItem
			testPutShipmentByExternalReferenceCodeItem_addShipmentItem()
		throws Exception {

		return _addShipmentItem();
	}

	private ShipmentItem _addShipmentItem() throws Exception {
		CommerceOrderItem commerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				_commerceOrder.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), BigDecimal.valueOf(5));

		return _addShipmentItem(
			RandomTestUtil.randomString(),
			_commerceShipment.getCommerceShipmentId(),
			commerceOrderItem.getCommerceOrderItemId());
	}

	private ShipmentItem _addShipmentItem(
			String externalReferenceCode, long commerceShipmentId,
			long commerceOrderItemId)
		throws Exception {

		_commerceShipmentItem =
			CommerceShipmentItemLocalServiceUtil.addCommerceShipmentItem(
				externalReferenceCode, commerceShipmentId, commerceOrderItemId,
				_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				BigDecimal.valueOf(5), null, true, _serviceContext);

		_commerceShipmentItems.add(_commerceShipmentItem);

		return _toShipmentItem(_commerceShipmentItem);
	}

	private ShipmentItem _addShipmentItem(
			String externalReferenceCode, ShipmentItem shipmentItem)
		throws Exception {

		return _addShipmentItem(
			externalReferenceCode, shipmentItem.getShipmentId(),
			shipmentItem.getOrderItemId());
	}

	private ShipmentItem _toShipmentItem(
		CommerceShipmentItem commerceShipmentItem) {

		return new ShipmentItem() {
			{
				createDate = commerceShipmentItem.getCreateDate();
				externalReferenceCode =
					commerceShipmentItem.getExternalReferenceCode();
				id = commerceShipmentItem.getCommerceShipmentItemId();
				modifiedDate = commerceShipmentItem.getModifiedDate();
				orderItemId = commerceShipmentItem.getCommerceOrderItemId();
				quantity = commerceShipmentItem.getQuantity();
				shipmentId = commerceShipmentItem.getCommerceShipmentId();
				userName = commerceShipmentItem.getUserName();
				warehouseId =
					commerceShipmentItem.getCommerceInventoryWarehouseId();
			}
		};
	}

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@DeleteAfterTestRun
	private CommerceShipment _commerceShipment;

	@DeleteAfterTestRun
	private CommerceShipmentItem _commerceShipmentItem;

	@DeleteAfterTestRun
	private final List<CommerceShipmentItem> _commerceShipmentItems =
		new ArrayList<>();

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	private ServiceContext _serviceContext;
	private User _user;

}