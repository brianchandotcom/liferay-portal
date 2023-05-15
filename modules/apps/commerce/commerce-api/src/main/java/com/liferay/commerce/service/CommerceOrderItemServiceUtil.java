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

package com.liferay.commerce.service;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CommerceOrderItem. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceOrderItemServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderItemService
 * @generated
 */
public class CommerceOrderItemServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceOrderItemServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceOrderItem addCommerceOrderItem(
			long commerceOrderId, long cpInstanceId, String json, int quantity,
			long replacedCPInstanceId, int shippedQuantity,
			com.liferay.commerce.context.CommerceContext commerceContext,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceOrderItem(
			commerceOrderId, cpInstanceId, json, quantity, replacedCPInstanceId,
			shippedQuantity, commerceContext, serviceContext);
	}

	public static CommerceOrderItem addOrUpdateCommerceOrderItem(
			long commerceOrderId, long cpInstanceId, String json, int quantity,
			long replacedCPInstanceId, int shippedQuantity,
			com.liferay.commerce.context.CommerceContext commerceContext,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateCommerceOrderItem(
			commerceOrderId, cpInstanceId, json, quantity, replacedCPInstanceId,
			shippedQuantity, commerceContext, serviceContext);
	}

	public static int countSubscriptionCommerceOrderItems(long commerceOrderId)
		throws PortalException {

		return getService().countSubscriptionCommerceOrderItems(
			commerceOrderId);
	}

	public static void deleteCommerceOrderItem(long commerceOrderItemId)
		throws PortalException {

		getService().deleteCommerceOrderItem(commerceOrderItemId);
	}

	public static void deleteCommerceOrderItem(
			long commerceOrderItemId,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws PortalException {

		getService().deleteCommerceOrderItem(
			commerceOrderItemId, commerceContext);
	}

	public static void deleteCommerceOrderItems(long commerceOrderId)
		throws PortalException {

		getService().deleteCommerceOrderItems(commerceOrderId);
	}

	public static void deleteMissingCommerceOrderItems(
			long commerceOrderId, Long[] commerceOrderItemIds,
			String[] externalReferenceCodes)
		throws PortalException {

		getService().deleteMissingCommerceOrderItems(
			commerceOrderId, commerceOrderItemIds, externalReferenceCodes);
	}

	public static CommerceOrderItem fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static CommerceOrderItem fetchCommerceOrderItem(
			long commerceOrderItemId)
		throws PortalException {

		return getService().fetchCommerceOrderItem(commerceOrderItemId);
	}

	public static List<CommerceOrderItem>
			getAvailableForShipmentCommerceOrderItems(long commerceOrderId)
		throws PortalException {

		return getService().getAvailableForShipmentCommerceOrderItems(
			commerceOrderId);
	}

	public static List<CommerceOrderItem> getChildCommerceOrderItems(
			long parentCommerceOrderItemId)
		throws PortalException {

		return getService().getChildCommerceOrderItems(
			parentCommerceOrderItemId);
	}

	public static int getCommerceInventoryWarehouseItemQuantity(
			long commerceOrderItemId, long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItemQuantity(
			commerceOrderItemId, commerceInventoryWarehouseId);
	}

	public static CommerceOrderItem getCommerceOrderItem(
			long commerceOrderItemId)
		throws PortalException {

		return getService().getCommerceOrderItem(commerceOrderItemId);
	}

	public static List<CommerceOrderItem> getCommerceOrderItems(
			long commerceOrderId, int start, int end)
		throws PortalException {

		return getService().getCommerceOrderItems(commerceOrderId, start, end);
	}

	public static List<CommerceOrderItem> getCommerceOrderItems(
			long groupId, long commerceAccountId, int[] orderStatuses,
			int start, int end)
		throws PortalException {

		return getService().getCommerceOrderItems(
			groupId, commerceAccountId, orderStatuses, start, end);
	}

	public static int getCommerceOrderItemsCount(long commerceOrderId)
		throws PortalException {

		return getService().getCommerceOrderItemsCount(commerceOrderId);
	}

	public static int getCommerceOrderItemsCount(
			long commerceOrderId, long cpInstanceId)
		throws PortalException {

		return getService().getCommerceOrderItemsCount(
			commerceOrderId, cpInstanceId);
	}

	public static int getCommerceOrderItemsCount(
			long groupId, long commerceAccountId, int[] orderStatuses)
		throws PortalException {

		return getService().getCommerceOrderItemsCount(
			groupId, commerceAccountId, orderStatuses);
	}

	public static int getCommerceOrderItemsQuantity(long commerceOrderId)
		throws PortalException {

		return getService().getCommerceOrderItemsQuantity(commerceOrderId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceOrderItem importCommerceOrderItem(
			String externalReferenceCode, long commerceOrderItemId,
			long commerceOrderId, long cpInstanceId,
			String cpMeasurementUnitKey, java.math.BigDecimal decimalQuantity,
			int quantity, int shippedQuantity,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().importCommerceOrderItem(
			externalReferenceCode, commerceOrderItemId, commerceOrderId,
			cpInstanceId, cpMeasurementUnitKey, decimalQuantity, quantity,
			shippedQuantity, serviceContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommerceOrderItem> searchCommerceOrderItems(
				long commerceOrderId, long parentCommerceOrderItemId,
				String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchCommerceOrderItems(
			commerceOrderId, parentCommerceOrderItemId, keywords, start, end,
			sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommerceOrderItem> searchCommerceOrderItems(
				long commerceOrderId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchCommerceOrderItems(
			commerceOrderId, keywords, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommerceOrderItem> searchCommerceOrderItems(
				long commerceOrderId, String name, String sku,
				boolean andOperator, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchCommerceOrderItems(
			commerceOrderId, name, sku, andOperator, start, end, sort);
	}

	public static CommerceOrderItem updateCommerceOrderItem(
			long commerceOrderItemId, int quantity,
			com.liferay.commerce.context.CommerceContext commerceContext,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceOrderItem(
			commerceOrderItemId, quantity, commerceContext, serviceContext);
	}

	public static CommerceOrderItem updateCommerceOrderItem(
			long commerceOrderItemId, long cpMeasurementUnitId, int quantity,
			com.liferay.commerce.context.CommerceContext commerceContext,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceOrderItem(
			commerceOrderItemId, cpMeasurementUnitId, quantity, commerceContext,
			serviceContext);
	}

	public static CommerceOrderItem updateCommerceOrderItem(
			long commerceOrderItemId, long cpMeasurementUnitId, int quantity,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceOrderItem(
			commerceOrderItemId, cpMeasurementUnitId, quantity, serviceContext);
	}

	public static CommerceOrderItem updateCommerceOrderItem(
			long commerceOrderItemId, String json, int quantity,
			com.liferay.commerce.context.CommerceContext commerceContext,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceOrderItem(
			commerceOrderItemId, json, quantity, commerceContext,
			serviceContext);
	}

	public static CommerceOrderItem updateCommerceOrderItemDeliveryDate(
			long commerceOrderItemId, java.util.Date requestedDeliveryDate)
		throws PortalException {

		return getService().updateCommerceOrderItemDeliveryDate(
			commerceOrderItemId, requestedDeliveryDate);
	}

	public static CommerceOrderItem updateCommerceOrderItemInfo(
			long commerceOrderItemId, long shippingAddressId,
			String deliveryGroup, String printedNote)
		throws PortalException {

		return getService().updateCommerceOrderItemInfo(
			commerceOrderItemId, shippingAddressId, deliveryGroup, printedNote);
	}

	public static CommerceOrderItem updateCommerceOrderItemInfo(
			long commerceOrderItemId, long shippingAddressId,
			String deliveryGroup, String printedNote,
			int requestedDeliveryDateMonth, int requestedDeliveryDateDay,
			int requestedDeliveryDateYear)
		throws PortalException {

		return getService().updateCommerceOrderItemInfo(
			commerceOrderItemId, shippingAddressId, deliveryGroup, printedNote,
			requestedDeliveryDateMonth, requestedDeliveryDateDay,
			requestedDeliveryDateYear);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static CommerceOrderItem updateCommerceOrderItemInfo(
			long commerceOrderItemId, String deliveryGroup,
			long shippingAddressId, String printedNote,
			int requestedDeliveryDateMonth, int requestedDeliveryDateDay,
			int requestedDeliveryDateYear, int requestedDeliveryDateHour,
			int requestedDeliveryDateMinute,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceOrderItemInfo(
			commerceOrderItemId, deliveryGroup, shippingAddressId, printedNote,
			requestedDeliveryDateMonth, requestedDeliveryDateDay,
			requestedDeliveryDateYear, requestedDeliveryDateHour,
			requestedDeliveryDateMinute, serviceContext);
	}

	public static CommerceOrderItem updateCommerceOrderItemPrices(
			long commerceOrderItemId, java.math.BigDecimal discountAmount,
			java.math.BigDecimal discountPercentageLevel1,
			java.math.BigDecimal discountPercentageLevel2,
			java.math.BigDecimal discountPercentageLevel3,
			java.math.BigDecimal discountPercentageLevel4,
			java.math.BigDecimal finalPrice, java.math.BigDecimal promoPrice,
			java.math.BigDecimal unitPrice)
		throws PortalException {

		return getService().updateCommerceOrderItemPrices(
			commerceOrderItemId, discountAmount, discountPercentageLevel1,
			discountPercentageLevel2, discountPercentageLevel3,
			discountPercentageLevel4, finalPrice, promoPrice, unitPrice);
	}

	public static CommerceOrderItem updateCommerceOrderItemPrices(
			long commerceOrderItemId, java.math.BigDecimal discountAmount,
			java.math.BigDecimal discountAmountWithTaxAmount,
			java.math.BigDecimal discountPercentageLevel1,
			java.math.BigDecimal discountPercentageLevel1WithTaxAmount,
			java.math.BigDecimal discountPercentageLevel2,
			java.math.BigDecimal discountPercentageLevel2WithTaxAmount,
			java.math.BigDecimal discountPercentageLevel3,
			java.math.BigDecimal discountPercentageLevel3WithTaxAmount,
			java.math.BigDecimal discountPercentageLevel4,
			java.math.BigDecimal discountPercentageLevel4WithTaxAmount,
			java.math.BigDecimal finalPrice,
			java.math.BigDecimal finalPriceWithTaxAmount,
			java.math.BigDecimal promoPrice,
			java.math.BigDecimal promoPriceWithTaxAmount,
			java.math.BigDecimal unitPrice,
			java.math.BigDecimal unitPriceWithTaxAmount)
		throws PortalException {

		return getService().updateCommerceOrderItemPrices(
			commerceOrderItemId, discountAmount, discountAmountWithTaxAmount,
			discountPercentageLevel1, discountPercentageLevel1WithTaxAmount,
			discountPercentageLevel2, discountPercentageLevel2WithTaxAmount,
			discountPercentageLevel3, discountPercentageLevel3WithTaxAmount,
			discountPercentageLevel4, discountPercentageLevel4WithTaxAmount,
			finalPrice, finalPriceWithTaxAmount, promoPrice,
			promoPriceWithTaxAmount, unitPrice, unitPriceWithTaxAmount);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static CommerceOrderItem updateCommerceOrderItemUnitPrice(
			long commerceOrderItemId, java.math.BigDecimal unitPrice)
		throws PortalException {

		return getService().updateCommerceOrderItemUnitPrice(
			commerceOrderItemId, unitPrice);
	}

	public static CommerceOrderItem updateCommerceOrderItemUnitPrice(
			long commerceOrderItemId, java.math.BigDecimal decimalQuantity,
			java.math.BigDecimal unitPrice)
		throws PortalException {

		return getService().updateCommerceOrderItemUnitPrice(
			commerceOrderItemId, decimalQuantity, unitPrice);
	}

	public static CommerceOrderItem updateCommerceOrderItemUnitPrice(
			long commerceOrderItemId, int quantity,
			java.math.BigDecimal unitPrice)
		throws PortalException {

		return getService().updateCommerceOrderItemUnitPrice(
			commerceOrderItemId, quantity, unitPrice);
	}

	public static CommerceOrderItem updateCustomFields(
			long commerceOrderItemId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCustomFields(
			commerceOrderItemId, serviceContext);
	}

	public static CommerceOrderItem updateExternalReferenceCode(
			long commerceOrderItemId, String externalReferenceCode)
		throws PortalException {

		return getService().updateExternalReferenceCode(
			commerceOrderItemId, externalReferenceCode);
	}

	public static CommerceOrderItemService getService() {
		return _service;
	}

	public static void setService(CommerceOrderItemService service) {
		_service = service;
	}

	private static volatile CommerceOrderItemService _service;

}