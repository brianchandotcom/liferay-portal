/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.model.listener;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.ListUtil;

import java.math.BigDecimal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 * @author Crescenzo Rega
 */
@Component(service = ModelListener.class)
public class CommerceOrderModelListener
	extends BaseModelListener<CommerceOrder> {

	@Override
	public void onAfterUpdate(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder) {

		try {
			if ((originalCommerceOrder.getOrderStatus() !=
					commerceOrder.getOrderStatus()) &&
				(commerceOrder.getOrderStatus() ==
					CommerceOrderConstants.ORDER_STATUS_SHIPPED)) {

				_commerceOrderEngine.checkCommerceOrderShipmentStatus(
					commerceOrder, true);
			}

			ListUtil.isNotEmptyForEach(
				originalCommerceOrder.getCustomerCommerceOrderIds(),
				customerCommerceOrderId -> {
					try {
						CommerceOrder customerCommerceOrder =
							_commerceOrderLocalService.getCommerceOrder(
								customerCommerceOrderId);

						_updateOrderStatus(
							customerCommerceOrder,
							commerceOrder.getOrderStatus(),
							originalCommerceOrder.getOrderStatus());

						if (_updateShippingAmount(
								customerCommerceOrder,
								commerceOrder.getShippingAmount(),
								originalCommerceOrder.getShippingAmount()) ||
							_updateShippingDiscountAmount(
								customerCommerceOrder,
								commerceOrder.getShippingDiscountAmount(),
								originalCommerceOrder.
									getShippingDiscountAmount()) ||
							_updateSubtotal(
								customerCommerceOrder,
								commerceOrder.getSubtotal(),
								originalCommerceOrder.getSubtotal()) ||
							_updateSubtotalDiscountAmount(
								customerCommerceOrder,
								commerceOrder.getSubtotalDiscountAmount(),
								originalCommerceOrder.
									getSubtotalDiscountAmount()) ||
							_updateTaxAmount(
								customerCommerceOrder,
								commerceOrder.getTaxAmount(),
								originalCommerceOrder.getTaxAmount()) ||
							_updateTotal(
								customerCommerceOrder, commerceOrder.getTotal(),
								originalCommerceOrder.getTotal()) ||
							_updateTotalDiscountAmount(
								customerCommerceOrder,
								commerceOrder.getTotalDiscountAmount(),
								originalCommerceOrder.
									getTotalDiscountAmount())) {

							_commerceOrderLocalService.updateCommerceOrder(
								customerCommerceOrder);
						}
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(portalException);
						}
					}
				});
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private boolean _transitionOrderStatusCompleted(CommerceOrder commerceOrder)
		throws PortalException {

		List<Long> supplierCommerceOrderIds =
			commerceOrder.getSupplierCommerceOrderIds();

		if (ListUtil.isEmpty(supplierCommerceOrderIds)) {
			return false;
		}

		CommerceOrder firstSupplierCommerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				supplierCommerceOrderIds.get(0));

		int orderStatus = firstSupplierCommerceOrder.getOrderStatus();

		if ((supplierCommerceOrderIds.size() == 1) &&
			(orderStatus != commerceOrder.getOrderStatus())) {

			return true;
		}

		for (Long supplierCommerceOrderId : supplierCommerceOrderIds) {
			CommerceOrder supplierCommerceOrder =
				_commerceOrderLocalService.getCommerceOrder(
					supplierCommerceOrderId);

			if (orderStatus != supplierCommerceOrder.getOrderStatus()) {
				return false;
			}
		}

		return true;
	}

	private void _updateOrderStatus(
			CommerceOrder customerCommerceOrder, int newOrderStatus,
			int originalOrderStatus)
		throws PortalException {

		if (originalOrderStatus == newOrderStatus) {
			return;
		}

		_commerceOrderEngine.checkCommerceOrderShipmentStatus(
			customerCommerceOrder, false);

		if ((newOrderStatus == CommerceOrderConstants.ORDER_STATUS_COMPLETED) &&
			_transitionOrderStatusCompleted(customerCommerceOrder)) {

			_commerceOrderEngine.transitionCommerceOrder(
				customerCommerceOrder, newOrderStatus, 0, false);
		}
	}

	private boolean _updateShippingAmount(
		CommerceOrder customerCommerceOrder, BigDecimal newShippingAmount,
		BigDecimal originalShippingAmount) {

		int compareShippingAmount = originalShippingAmount.compareTo(
			newShippingAmount);

		if (compareShippingAmount == 0) {
			return false;
		}

		BigDecimal customerShippingAmount =
			customerCommerceOrder.getShippingAmount();

		BigDecimal subtractOriginalValue = customerShippingAmount.subtract(
			originalShippingAmount);

		customerCommerceOrder.setShippingAmount(
			subtractOriginalValue.add(newShippingAmount));

		return true;
	}

	private boolean _updateShippingDiscountAmount(
		CommerceOrder customerCommerceOrder,
		BigDecimal newShippingDiscountAmount,
		BigDecimal originalShippingDiscountAmount) {

		int compareShippingDiscountAmount =
			originalShippingDiscountAmount.compareTo(newShippingDiscountAmount);

		if (compareShippingDiscountAmount == 0) {
			return false;
		}

		BigDecimal customerShippingDiscountAmount =
			customerCommerceOrder.getShippingDiscountAmount();

		BigDecimal subtractOriginalValue =
			customerShippingDiscountAmount.subtract(
				originalShippingDiscountAmount);

		customerCommerceOrder.setShippingDiscountAmount(
			subtractOriginalValue.add(newShippingDiscountAmount));

		return true;
	}

	private boolean _updateSubtotal(
		CommerceOrder customerCommerceOrder, BigDecimal newSubtotal,
		BigDecimal originalSubtotal) {

		int compareSubtotal = originalSubtotal.compareTo(newSubtotal);

		if (compareSubtotal == 0) {
			return false;
		}

		BigDecimal customerSubtotal = customerCommerceOrder.getSubtotal();

		BigDecimal subtractOriginalValue = customerSubtotal.subtract(
			originalSubtotal);

		customerCommerceOrder.setSubtotal(
			subtractOriginalValue.add(newSubtotal));

		return true;
	}

	private boolean _updateSubtotalDiscountAmount(
		CommerceOrder customerCommerceOrder,
		BigDecimal newSubtotalDiscountAmount,
		BigDecimal originalSubtotalDiscountAmount) {

		int compareSubtotalDiscountAmount =
			originalSubtotalDiscountAmount.compareTo(newSubtotalDiscountAmount);

		if (compareSubtotalDiscountAmount == 0) {
			return false;
		}

		BigDecimal customerSubtotalDiscountAmount =
			customerCommerceOrder.getSubtotalDiscountAmount();

		BigDecimal subtractOriginalValue =
			customerSubtotalDiscountAmount.subtract(
				originalSubtotalDiscountAmount);

		customerCommerceOrder.setSubtotalDiscountAmount(
			subtractOriginalValue.add(newSubtotalDiscountAmount));

		return true;
	}

	private boolean _updateTaxAmount(
		CommerceOrder customerCommerceOrder, BigDecimal newTaxAmount,
		BigDecimal originalTaxAmount) {

		int compareTaxAmount = originalTaxAmount.compareTo(newTaxAmount);

		if (compareTaxAmount == 0) {
			return false;
		}

		BigDecimal customerTaxAmount = customerCommerceOrder.getTaxAmount();

		BigDecimal subtractOriginalValue = customerTaxAmount.subtract(
			originalTaxAmount);

		customerCommerceOrder.setTaxAmount(
			subtractOriginalValue.add(newTaxAmount));

		return true;
	}

	private boolean _updateTotal(
		CommerceOrder customerCommerceOrder, BigDecimal newTotal,
		BigDecimal originalTotal) {

		int compareTotal = originalTotal.compareTo(newTotal);

		if (compareTotal == 0) {
			return false;
		}

		BigDecimal customerTotal = customerCommerceOrder.getTotal();

		BigDecimal subtractOriginalValue = customerTotal.subtract(
			originalTotal);

		customerCommerceOrder.setTotal(subtractOriginalValue.add(newTotal));

		return true;
	}

	private boolean _updateTotalDiscountAmount(
		CommerceOrder customerCommerceOrder, BigDecimal newTotalDiscountAmount,
		BigDecimal originalTotalDiscountAmount) {

		int compareTotalDiscountAmount = originalTotalDiscountAmount.compareTo(
			newTotalDiscountAmount);

		if (compareTotalDiscountAmount != 0) {
			return false;
		}

		BigDecimal customerTotalDiscountAmount =
			customerCommerceOrder.getTotalDiscountAmount();

		BigDecimal subtractOriginalValue = customerTotalDiscountAmount.subtract(
			originalTotalDiscountAmount);

		customerCommerceOrder.setTotalDiscountAmount(
			subtractOriginalValue.add(newTotalDiscountAmount));

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderModelListener.class);

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

}