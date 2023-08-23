/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.model.listener;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderThreadLocal;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.BigDecimalUtil;

import java.math.BigDecimal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 * @author Crescenzo Rega
 */
@Component(service = ModelListener.class)
public class CommerceOrderItemModelListener
	extends BaseModelListener<CommerceOrderItem> {

	@Override
	public void onAfterRemove(CommerceOrderItem commerceOrderItem) {
		try {
			if (CommerceOrderThreadLocal.isDeleteInProcess()) {
				return;
			}

			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			if (commerceOrder.getOrderStatus() ==
					CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED) {

				_commerceOrderEngine.checkCommerceOrderShipmentStatus(
					commerceOrderItem.getCommerceOrder(), true);
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	@Override
	public void onAfterUpdate(
		CommerceOrderItem originalCommerceOrderItem,
		CommerceOrderItem commerceOrderItem) {

		try {
			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			if ((commerceOrder.getOrderStatus() ==
					CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED) ||
				(commerceOrder.getOrderStatus() ==
					CommerceOrderConstants.ORDER_STATUS_SHIPPED)) {

				_commerceOrderEngine.checkCommerceOrderShipmentStatus(
					commerceOrderItem.getCommerceOrder(), true);
			}

			long customerCommerceOrderItemId =
				commerceOrderItem.getCustomerCommerceOrderItemId();

			if (customerCommerceOrderItemId > 0) {
				CommerceOrderItem customerCommerceOrderItem =
					_commerceOrderItemLocalService.getCommerceOrderItem(
						customerCommerceOrderItemId);

				BigDecimal originalShippedQuantity =
					originalCommerceOrderItem.getShippedQuantity();
				BigDecimal newShippedQuantity =
					commerceOrderItem.getShippedQuantity();

				boolean update = false;

				if (originalShippedQuantity != newShippedQuantity) {
					BigDecimal commerceShippedQuantity =
						customerCommerceOrderItem.getShippedQuantity();

					customerCommerceOrderItem.setShippedQuantity(
						commerceShippedQuantity.subtract(
							originalShippedQuantity
						).add(
							newShippedQuantity
						));

					update = true;
				}

				BigDecimal newQuantity = commerceOrderItem.getQuantity();

				if (!BigDecimalUtil.eq(
						newQuantity, originalCommerceOrderItem.getQuantity())) {

					customerCommerceOrderItem.setQuantity(newQuantity);

					update = true;
				}

				BigDecimal newDiscountAmount =
					commerceOrderItem.getDiscountAmount();

				int compareDiscountAmount = newDiscountAmount.compareTo(
					originalCommerceOrderItem.getDiscountAmount());

				if (compareDiscountAmount != 0) {
					customerCommerceOrderItem.setDiscountAmount(
						newDiscountAmount);
				}

				BigDecimal newUnitPrice = commerceOrderItem.getUnitPrice();

				int compareUnitPrice = newUnitPrice.compareTo(
					originalCommerceOrderItem.getUnitPrice());

				if (compareUnitPrice != 0) {
					customerCommerceOrderItem.setUnitPrice(newUnitPrice);

					update = true;
				}

				BigDecimal newFinalPrice = commerceOrderItem.getFinalPrice();

				int compareFinalPrice = newFinalPrice.compareTo(
					originalCommerceOrderItem.getFinalPrice());

				if (compareFinalPrice != 0) {
					customerCommerceOrderItem.setFinalPrice(newFinalPrice);

					update = true;
				}

				if (update) {
					customerCommerceOrderItem =
						_commerceOrderItemLocalService.updateCommerceOrderItem(
							customerCommerceOrderItem);

					_commerceOrderEngine.checkCommerceOrderShipmentStatus(
						customerCommerceOrderItem.getCommerceOrder(), false);
				}
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderItemModelListener.class);

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

}