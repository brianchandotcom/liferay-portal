/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipment.test.util;

import com.liferay.account.constants.AccountConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.commerce.product.service.CommerceChannelRelLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.commerce.service.CommerceShipmentItemLocalServiceUtil;
import com.liferay.commerce.service.CommerceShipmentLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.math.BigDecimal;

/**
 * @author Luca Pellizzon
 */
public class CommerceShipmentTestUtil {

	public static CommerceShipmentItem addCommerceShipmentItem(
			CommerceContext commerceContext, CPInstance cpInstance,
			long groupId, long userId, long commerceOrderId,
			long commerceShipmentId, int createQuantity, int addQuantity)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				serviceContext.getCompanyId());

		CommerceChannel commerceChannel =
			CommerceChannelLocalServiceUtil.fetchCommerceChannel(
				commerceContext.getCommerceChannelId());

		if (commerceChannel == null) {
			commerceChannel =
				CommerceChannelLocalServiceUtil.addCommerceChannel(
					null, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, groupId,
					"Test Channel", CommerceChannelConstants.CHANNEL_TYPE_SITE,
					null, commerceCurrency.getCode(), serviceContext);
		}

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse();

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			commerceChannel.getCommerceChannelId(), serviceContext);

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			userId, commerceInventoryWarehouse,
			BigDecimal.valueOf(createQuantity), cpInstance.getSku(),
			StringPool.BLANK);

		CommerceOrderItem commerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				commerceOrderId, cpInstance.getCPInstanceId(),
				BigDecimal.valueOf(createQuantity), commerceContext);

		return CommerceShipmentItemLocalServiceUtil.addCommerceShipmentItem(
			null, commerceShipmentId,
			commerceOrderItem.getCommerceOrderItemId(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			BigDecimal.valueOf(addQuantity), null, true, serviceContext);
	}

	public static CommerceShipment createEmptyOrderShipment(
			long groupId, long orderId)
		throws PortalException {

		return CommerceShipmentLocalServiceUtil.addCommerceShipment(
			orderId, ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static void createOrderItemsOnlyShipment(
			long groupId, long orderId, long warehouseId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceOrder commerceOrder =
			CommerceOrderLocalServiceUtil.getCommerceOrder(orderId);

		CommerceShipment commerceShipment =
			CommerceShipmentLocalServiceUtil.addCommerceShipment(
				orderId, serviceContext);

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			CommerceShipmentItemLocalServiceUtil.addCommerceShipmentItem(
				null, commerceShipment.getCommerceShipmentId(),
				commerceOrderItem.getCommerceOrderItemId(), warehouseId,
				commerceOrderItem.getQuantity(), null, true, serviceContext);
		}
	}

	public static CommerceShipment createOrderShipment(
			long groupId, long commerceOrderId, long commerceWarehouseId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceShipment commerceShipment =
			CommerceShipmentLocalServiceUtil.addCommerceShipment(
				commerceOrderId, serviceContext);

		CommerceOrder commerceOrder =
			CommerceOrderLocalServiceUtil.getCommerceOrder(commerceOrderId);

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			CommerceShipmentItemLocalServiceUtil.addCommerceShipmentItem(
				null, commerceShipment.getCommerceShipmentId(),
				commerceOrderItem.getCommerceOrderItemId(), commerceWarehouseId,
				commerceOrderItem.getQuantity(), null, true, serviceContext);
		}

		return commerceShipment;
	}

}