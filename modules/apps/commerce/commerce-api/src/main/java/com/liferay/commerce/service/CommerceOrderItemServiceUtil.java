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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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
	public static com.liferay.commerce.model.CommerceOrderItem
			addCommerceOrderItem(
				long commerceOrderId, long cpInstanceId, int quantity,
				int shippedQuantity, String json,
				com.liferay.commerce.context.CommerceContext commerceContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceOrderItem(
			commerceOrderId, cpInstanceId, quantity, shippedQuantity, json,
			commerceContext, serviceContext);
	}

	public static int countSubscriptionCommerceOrderItems(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().countSubscriptionCommerceOrderItems(
			commerceOrderId);
	}

	public static void deleteCommerceOrderItem(long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceOrderItem(commerceOrderItemId);
	}

	public static void deleteCommerceOrderItem(
			long commerceOrderItemId,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceOrderItem(
			commerceOrderItemId, commerceContext);
	}

	public static void deleteCommerceOrderItems(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceOrderItems(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			fetchCommerceOrderItem(long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceOrderItem(commerceOrderItemId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderItem>
			getAvailableForShipmentCommerceOrderItems(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAvailableForShipmentCommerceOrderItems(
			commerceOrderId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderItem>
			getChildCommerceOrderItems(long parentCommerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getChildCommerceOrderItems(
			parentCommerceOrderItemId);
	}

	public static int getCommerceInventoryWarehouseItemQuantity(
			long commerceOrderItemId, long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceInventoryWarehouseItemQuantity(
			commerceOrderItemId, commerceInventoryWarehouseId);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			getCommerceOrderItem(long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceOrderItem(commerceOrderItemId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderItem>
			getCommerceOrderItems(long commerceOrderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceOrderItems(commerceOrderId, start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderItem>
			getCommerceOrderItems(
				long groupId, long commerceAccountId, int[] orderStatuses,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceOrderItems(
			groupId, commerceAccountId, orderStatuses, start, end);
	}

	public static int getCommerceOrderItemsCount(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceOrderItemsCount(commerceOrderId);
	}

	public static int getCommerceOrderItemsCount(
			long commerceOrderId, long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceOrderItemsCount(
			commerceOrderId, cpInstanceId);
	}

	public static int getCommerceOrderItemsCount(
			long groupId, long commerceAccountId, int[] orderStatuses)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceOrderItemsCount(
			groupId, commerceAccountId, orderStatuses);
	}

	public static int getCommerceOrderItemsQuantity(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {

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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceOrderItem> search(
				long commerceOrderId, long parentCommerceOrderItemId,
				String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(
			commerceOrderId, parentCommerceOrderItemId, keywords, start, end,
			sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceOrderItem> search(
				long commerceOrderId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(commerceOrderId, keywords, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceOrderItem> search(
				long commerceOrderId, String sku, String name,
				boolean andOperator, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(
			commerceOrderId, sku, name, andOperator, start, end, sort);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			updateCommerceOrderItem(
				long commerceOrderItemId, int quantity,
				com.liferay.commerce.context.CommerceContext commerceContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceOrderItem(
			commerceOrderItemId, quantity, commerceContext, serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			updateCommerceOrderItem(
				long commerceOrderItemId, int quantity, String json,
				com.liferay.commerce.context.CommerceContext commerceContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceOrderItem(
			commerceOrderItemId, quantity, json, commerceContext,
			serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			updateCommerceOrderItemDeliveryDate(
				long commerceOrderItemId, java.util.Date requestedDeliveryDate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceOrderItemDeliveryDate(
			commerceOrderItemId, requestedDeliveryDate);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			updateCommerceOrderItemInfo(
				long commerceOrderItemId, String deliveryGroup,
				long shippingAddressId, String printedNote)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceOrderItemInfo(
			commerceOrderItemId, deliveryGroup, shippingAddressId, printedNote);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			updateCommerceOrderItemInfo(
				long commerceOrderItemId, String deliveryGroup,
				long shippingAddressId, String printedNote,
				int requestedDeliveryDateMonth, int requestedDeliveryDateDay,
				int requestedDeliveryDateYear)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceOrderItemInfo(
			commerceOrderItemId, deliveryGroup, shippingAddressId, printedNote,
			requestedDeliveryDateMonth, requestedDeliveryDateDay,
			requestedDeliveryDateYear);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceOrderItem
			updateCommerceOrderItemInfo(
				long commerceOrderItemId, String deliveryGroup,
				long shippingAddressId, String printedNote,
				int requestedDeliveryDateMonth, int requestedDeliveryDateDay,
				int requestedDeliveryDateYear, int requestedDeliveryDateHour,
				int requestedDeliveryDateMinute,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceOrderItemInfo(
			commerceOrderItemId, deliveryGroup, shippingAddressId, printedNote,
			requestedDeliveryDateMonth, requestedDeliveryDateDay,
			requestedDeliveryDateYear, requestedDeliveryDateHour,
			requestedDeliveryDateMinute, serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			updateCommerceOrderItemPrices(
				long commerceOrderItemId, java.math.BigDecimal unitPrice,
				java.math.BigDecimal promoPrice,
				java.math.BigDecimal discountAmount,
				java.math.BigDecimal finalPrice,
				java.math.BigDecimal discountPercentageLevel1,
				java.math.BigDecimal discountPercentageLevel2,
				java.math.BigDecimal discountPercentageLevel3,
				java.math.BigDecimal discountPercentageLevel4)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceOrderItemPrices(
			commerceOrderItemId, unitPrice, promoPrice, discountAmount,
			finalPrice, discountPercentageLevel1, discountPercentageLevel2,
			discountPercentageLevel3, discountPercentageLevel4);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			updateCommerceOrderItemPrices(
				long commerceOrderItemId, java.math.BigDecimal unitPrice,
				java.math.BigDecimal promoPrice,
				java.math.BigDecimal discountAmount,
				java.math.BigDecimal finalPrice,
				java.math.BigDecimal discountPercentageLevel1,
				java.math.BigDecimal discountPercentageLevel2,
				java.math.BigDecimal discountPercentageLevel3,
				java.math.BigDecimal discountPercentageLevel4,
				java.math.BigDecimal unitPriceWithTaxAmount,
				java.math.BigDecimal promoPriceWithTaxAmount,
				java.math.BigDecimal discountAmountWithTaxAmount,
				java.math.BigDecimal finalPriceWithTaxAmount,
				java.math.BigDecimal discountPercentageLevel1WithTaxAmount,
				java.math.BigDecimal discountPercentageLevel2WithTaxAmount,
				java.math.BigDecimal discountPercentageLevel3WithTaxAmount,
				java.math.BigDecimal discountPercentageLevel4WithTaxAmount)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceOrderItemPrices(
			commerceOrderItemId, unitPrice, promoPrice, discountAmount,
			finalPrice, discountPercentageLevel1, discountPercentageLevel2,
			discountPercentageLevel3, discountPercentageLevel4,
			unitPriceWithTaxAmount, promoPriceWithTaxAmount,
			discountAmountWithTaxAmount, finalPriceWithTaxAmount,
			discountPercentageLevel1WithTaxAmount,
			discountPercentageLevel2WithTaxAmount,
			discountPercentageLevel3WithTaxAmount,
			discountPercentageLevel4WithTaxAmount);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceOrderItem
			updateCommerceOrderItemUnitPrice(
				long commerceOrderItemId, java.math.BigDecimal unitPrice)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceOrderItemUnitPrice(
			commerceOrderItemId, unitPrice);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			updateCommerceOrderItemUnitPrice(
				long commerceOrderItemId, java.math.BigDecimal unitPrice,
				int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceOrderItemUnitPrice(
			commerceOrderItemId, unitPrice, quantity);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			updateCustomFields(
				long commerceOrderItemId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCustomFields(
			commerceOrderItemId, serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrderItem
			upsertCommerceOrderItem(
				long commerceOrderId, long cpInstanceId, int quantity,
				int shippedQuantity, String json,
				com.liferay.commerce.context.CommerceContext commerceContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceOrderItem(
			commerceOrderId, cpInstanceId, quantity, shippedQuantity, json,
			commerceContext, serviceContext);
	}

	public static CommerceOrderItemService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceOrderItemService, CommerceOrderItemService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceOrderItemService.class);

		ServiceTracker<CommerceOrderItemService, CommerceOrderItemService>
			serviceTracker =
				new ServiceTracker
					<CommerceOrderItemService, CommerceOrderItemService>(
						bundle.getBundleContext(),
						CommerceOrderItemService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}