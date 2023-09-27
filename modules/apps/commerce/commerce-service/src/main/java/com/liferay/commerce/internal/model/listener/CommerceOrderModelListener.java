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
			if (commerceOrder.getOrderStatus() ==
					CommerceOrderConstants.ORDER_STATUS_SHIPPED) {

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
							originalCommerceOrder, commerceOrder,
							customerCommerceOrder);

						if (_updateShippingAmount(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder) ||
							_updateShippingDiscountAmount(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder) ||
							_updateSubtotal(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder) ||
							_updateSubtotalDiscountAmount(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder) ||
							_updateTaxAmount(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder) ||
							_updateTotal(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder) ||
							_updateTotalDiscountAmount(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder)) {

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

		Long firstSupplierCommerceOrderId = supplierCommerceOrderIds.get(0);

		CommerceOrder firstSupplierCommerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				firstSupplierCommerceOrderId);

		int orderStatus = firstSupplierCommerceOrder.getOrderStatus();

		if ((supplierCommerceOrderIds.size() == 1) &&
			(orderStatus != commerceOrder.getOrderStatus())) {

			return true;
		}

		for (int i = 1; i < supplierCommerceOrderIds.size(); i++) {
			CommerceOrder supplierCommerceOrder =
				_commerceOrderLocalService.getCommerceOrder(
					supplierCommerceOrderIds.get(i));

			if (orderStatus != supplierCommerceOrder.getOrderStatus()) {
				return false;
			}
		}

		return true;
	}

	private void _updateOrderStatus(
			CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
			CommerceOrder customerCommerceOrder)
		throws PortalException {

		int newOrderStatus = commerceOrder.getOrderStatus();
		int originalOrderStatus = originalCommerceOrder.getOrderStatus();

		if (originalOrderStatus != newOrderStatus) {
			_commerceOrderEngine.checkCommerceOrderShipmentStatus(
				customerCommerceOrder, false);

			if ((newOrderStatus ==
					CommerceOrderConstants.ORDER_STATUS_COMPLETED) &&
				_transitionOrderStatusCompleted(customerCommerceOrder)) {

				_commerceOrderEngine.transitionCommerceOrder(
					customerCommerceOrder, newOrderStatus, 0, false);
			}
		}
	}

	private boolean _updateShippingAmount(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalShippingAmount =
			originalCommerceOrder.getShippingAmount();
		BigDecimal newShippingAmount = commerceOrder.getShippingAmount();

		int compareShippingAmount = originalShippingAmount.compareTo(
			newShippingAmount);

		if (compareShippingAmount != 0) {
			BigDecimal customerShippingAmount =
				customerCommerceOrder.getShippingAmount();

			BigDecimal subtractOriginalValue = customerShippingAmount.subtract(
				originalShippingAmount);

			customerCommerceOrder.setShippingAmount(
				subtractOriginalValue.add(newShippingAmount));

			return true;
		}

		return false;
	}

	private boolean _updateShippingDiscountAmount(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalShippingDiscountAmount =
			originalCommerceOrder.getShippingDiscountAmount();
		BigDecimal newShippingDiscountAmount =
			commerceOrder.getShippingDiscountAmount();

		int compareShippingDiscountAmount =
			originalShippingDiscountAmount.compareTo(newShippingDiscountAmount);

		if (compareShippingDiscountAmount != 0) {
			BigDecimal customerShippingDiscountAmount =
				customerCommerceOrder.getShippingDiscountAmount();

			BigDecimal subtractOriginalValue =
				customerShippingDiscountAmount.subtract(
					originalShippingDiscountAmount);

			customerCommerceOrder.setShippingDiscountAmount(
				subtractOriginalValue.add(newShippingDiscountAmount));

			return true;
		}

		return false;
	}

	private boolean _updateSubtotal(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalSubtotal = originalCommerceOrder.getSubtotal();
		BigDecimal newSubtotal = commerceOrder.getSubtotal();

		int compareSubtotal = originalSubtotal.compareTo(newSubtotal);

		if (compareSubtotal != 0) {
			BigDecimal customerSubtotal = customerCommerceOrder.getSubtotal();

			BigDecimal subtractOriginalValue = customerSubtotal.subtract(
				originalSubtotal);

			customerCommerceOrder.setSubtotal(
				subtractOriginalValue.add(newSubtotal));

			return true;
		}

		return false;
	}

	private boolean _updateSubtotalDiscountAmount(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalSubtotalDiscountAmount =
			originalCommerceOrder.getSubtotalDiscountAmount();

		BigDecimal newSubtotalDiscountAmount =
			commerceOrder.getSubtotalDiscountAmount();

		int compareSubtotalDiscountAmount =
			originalSubtotalDiscountAmount.compareTo(newSubtotalDiscountAmount);

		if (compareSubtotalDiscountAmount != 0) {
			BigDecimal customerSubtotalDiscountAmount =
				customerCommerceOrder.getSubtotalDiscountAmount();

			BigDecimal subtractOriginalValue =
				customerSubtotalDiscountAmount.subtract(
					originalSubtotalDiscountAmount);

			customerCommerceOrder.setSubtotalDiscountAmount(
				subtractOriginalValue.add(newSubtotalDiscountAmount));

			return true;
		}

		return false;
	}

	private boolean _updateTaxAmount(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalTaxAmount = originalCommerceOrder.getTaxAmount();
		BigDecimal newTaxAmount = commerceOrder.getTaxAmount();

		int compareTaxAmount = originalTaxAmount.compareTo(newTaxAmount);

		if (compareTaxAmount != 0) {
			BigDecimal customerTaxAmount = customerCommerceOrder.getTaxAmount();

			BigDecimal subtractOriginalValue = customerTaxAmount.subtract(
				originalTaxAmount);

			customerCommerceOrder.setTaxAmount(
				subtractOriginalValue.add(newTaxAmount));

			return true;
		}

		return false;
	}

	private boolean _updateTotal(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalTotal = originalCommerceOrder.getTotal();
		BigDecimal newTotal = commerceOrder.getTotal();

		int compareTotal = originalTotal.compareTo(newTotal);

		if (compareTotal != 0) {
			BigDecimal customerTotal = customerCommerceOrder.getTotal();

			BigDecimal subtractOriginalValue = customerTotal.subtract(
				originalTotal);

			customerCommerceOrder.setTotal(subtractOriginalValue.add(newTotal));

			return true;
		}

		return false;
	}

	private boolean _updateTotalDiscountAmount(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalTotalDiscountAmount =
			originalCommerceOrder.getTotalDiscountAmount();
		BigDecimal newTotalDiscountAmount =
			commerceOrder.getTotalDiscountAmount();

		int compareTotalDiscountAmount = originalTotalDiscountAmount.compareTo(
			newTotalDiscountAmount);

		if (compareTotalDiscountAmount != 0) {
			BigDecimal customerTotalDiscountAmount =
				customerCommerceOrder.getTotalDiscountAmount();

			BigDecimal subtractOriginalValue =
				customerTotalDiscountAmount.subtract(
					originalTotalDiscountAmount);

			customerCommerceOrder.setTotalDiscountAmount(
				subtractOriginalValue.add(newTotalDiscountAmount));

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderModelListener.class);

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

}