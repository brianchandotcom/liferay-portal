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

package com.liferay.commerce.internal.util;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.util.SplitCommerceOrderHelper;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = SplitCommerceOrderHelper.class)
public class SplitCommerceOrderHelperImpl implements SplitCommerceOrderHelper {

	@Override
	public void splitByCatalog(long commerceOrderId) throws Exception {
		CommerceOrder customerCommerceOrder =
			_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		if (_isSplitted(customerCommerceOrder)) {
			return;
		}

		Map<Long, List<CommerceOrderItem>> commerceOrderItemMap =
			_getCommerceOrderItemMap(
				customerCommerceOrder.getCommerceOrderItems());

		int numberCommerceOrder = commerceOrderItemMap.size();

		if (numberCommerceOrder > 1) {
			_createSupplierOrders(customerCommerceOrder, commerceOrderItemMap);
		}
	}

	private BigDecimal _calculateSubtotalDiscountAmount(
		CommerceOrder customerCommerceOrder, RoundingMode roundingMode,
		BigDecimal subtotal) {

		BigDecimal newSubtotalDiscountAmount = BigDecimal.ZERO;
		BigDecimal customerSubtotalDiscountAmount =
			customerCommerceOrder.getSubtotalDiscountAmount();

		if (customerSubtotalDiscountAmount.signum() > 0) {
			BigDecimal customerCommerceOrderSubtotal =
				customerCommerceOrder.getSubtotal();

			newSubtotalDiscountAmount = customerSubtotalDiscountAmount.multiply(
				subtotal);
			newSubtotalDiscountAmount = newSubtotalDiscountAmount.divide(
				customerCommerceOrderSubtotal, roundingMode);
		}

		return newSubtotalDiscountAmount;
	}

	private BigDecimal _calculateTotalDiscountAmount(
		CommerceOrder customerCommerceOrder, RoundingMode roundingMode,
		BigDecimal supplierTotal) {

		BigDecimal newTotalDiscountAmount = BigDecimal.ZERO;
		BigDecimal customerTotalDiscountAmount =
			customerCommerceOrder.getTotalDiscountAmount();

		if (customerTotalDiscountAmount.signum() > 0) {
			BigDecimal customerCommerceOrderTotal =
				customerCommerceOrder.getTotal();

			newTotalDiscountAmount = customerTotalDiscountAmount.multiply(
				supplierTotal);
			BigDecimal add = customerCommerceOrderTotal.add(
				customerTotalDiscountAmount);

			newTotalDiscountAmount = newTotalDiscountAmount.divide(
				add, roundingMode);
		}

		return newTotalDiscountAmount;
	}

	private void _createSupplierOrders(
			CommerceOrder customerCommerceOrder,
			Map<Long, List<CommerceOrderItem>> commerceOrderItemMap)
		throws Exception {

		CommerceCurrency commerceCurrency =
			customerCommerceOrder.getCommerceCurrency();

		RoundingMode roundingMode = RoundingMode.valueOf(
			commerceCurrency.getRoundingMode());

		for (List<CommerceOrderItem> commerceOrderItemList :
				commerceOrderItemMap.values()) {

			CommerceOrder supplierCommerceOrder =
				customerCommerceOrder.cloneWithOriginalValues();

			supplierCommerceOrder.setUuid(_portalUUID.generate());
			supplierCommerceOrder.setExternalReferenceCode(
				_portalUUID.generate());

			long newCommerceOrderId = _counterLocalService.increment();

			supplierCommerceOrder.setCommerceOrderId(newCommerceOrderId);

			BigDecimal subtotal = BigDecimal.ZERO;
			BigDecimal taxAmount = BigDecimal.ZERO;

			for (CommerceOrderItem commerceOrderItem : commerceOrderItemList) {
				CommerceOrderItem newCommerceOrderItem =
					commerceOrderItem.cloneWithOriginalValues();

				newCommerceOrderItem.setUuid(_portalUUID.generate());
				newCommerceOrderItem.setExternalReferenceCode(
					_portalUUID.generate());
				newCommerceOrderItem.setCommerceOrderItemId(
					_counterLocalService.increment());
				newCommerceOrderItem.setCommerceOrderId(newCommerceOrderId);
				newCommerceOrderItem.setCustomerCommerceOrderItemId(
					commerceOrderItem.getCommerceOrderItemId());
				newCommerceOrderItem.setParentCommerceOrderItemId(0);

				_commerceOrderItemLocalService.addCommerceOrderItem(
					newCommerceOrderItem);

				BigDecimal finalPrice = newCommerceOrderItem.getFinalPrice();

				subtotal = subtotal.add(finalPrice);

				BigDecimal finalPriceWithTaxAmount =
					newCommerceOrderItem.getFinalPriceWithTaxAmount();

				BigDecimal tax = finalPriceWithTaxAmount.subtract(finalPrice);

				taxAmount = taxAmount.add(tax);
			}

			supplierCommerceOrder.setShippingAmount(BigDecimal.ZERO);
			supplierCommerceOrder.setShippingDiscountAmount(BigDecimal.ZERO);
			supplierCommerceOrder.setSubtotal(subtotal);
			supplierCommerceOrder.setTaxAmount(taxAmount);

			BigDecimal newSubtotalDiscountAmount =
				_calculateSubtotalDiscountAmount(
					customerCommerceOrder, roundingMode, subtotal);

			supplierCommerceOrder.setSubtotalDiscountAmount(
				newSubtotalDiscountAmount);

			BigDecimal supplierTotal = subtotal.add(taxAmount);

			BigDecimal newTotalDiscountAmount = _calculateTotalDiscountAmount(
				customerCommerceOrder, roundingMode, supplierTotal);

			supplierCommerceOrder.setTotalDiscountAmount(
				newTotalDiscountAmount);

			supplierCommerceOrder.setShippingDiscountAmount(BigDecimal.ZERO);

			supplierTotal = supplierTotal.subtract(newSubtotalDiscountAmount);
			supplierTotal = supplierTotal.subtract(newTotalDiscountAmount);

			supplierCommerceOrder.setTotal(supplierTotal);

			_commerceOrderLocalService.addCommerceOrder(supplierCommerceOrder);
		}
	}

	private Map<Long, List<CommerceOrderItem>> _getCommerceOrderItemMap(
		List<CommerceOrderItem> commerceOrderItems) {

		Map<Long, List<CommerceOrderItem>> commerceOrderItemMap =
			new HashMap<>();

		ListUtil.isNotEmptyForEach(
			commerceOrderItems,
			commerceOrderItem -> {
				try {
					CPDefinition cpDefinition =
						commerceOrderItem.getCPDefinition();

					CommerceCatalog commerceCatalog =
						cpDefinition.getCommerceCatalog();

					long commerceCatalogId =
						commerceCatalog.getCommerceCatalogId();

					if (commerceOrderItemMap.containsKey(commerceCatalogId)) {
						List<CommerceOrderItem> splitCommerceOrderItems =
							commerceOrderItemMap.get(commerceCatalogId);

						splitCommerceOrderItems.add(commerceOrderItem);

						commerceOrderItemMap.put(
							commerceCatalogId, splitCommerceOrderItems);
					}
					else {
						commerceOrderItemMap.put(
							commerceCatalogId,
							ListUtil.toList(commerceOrderItem));
					}
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			});

		return commerceOrderItemMap;
	}

	private boolean _isSplitted(CommerceOrder customerCommerceOrder) {
		int supplierCommerceOrderIdsCount =
			customerCommerceOrder.getSupplierCommerceOrderIdsCount();

		int customerCommerceOrderIdsCount =
			customerCommerceOrder.getCustomerCommerceOrderIdsCount();

		if ((supplierCommerceOrderIdsCount > 0) ||
			(customerCommerceOrderIdsCount > 0)) {

			return true;
		}

		return false;
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private PortalUUID _portalUUID;

}