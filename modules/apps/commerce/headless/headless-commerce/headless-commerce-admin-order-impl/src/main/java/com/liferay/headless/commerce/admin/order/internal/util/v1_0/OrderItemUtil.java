/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.util.v1_0;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.exception.CPInstanceSkuException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.Date;

/**
 * @author Alessio Antonio Rendina
 */
public class OrderItemUtil {

	public static CommerceOrderItem addCommerceOrderItem(
			CPInstanceService cpInstanceService,
			CommerceOrderItemService commerceOrderItemService,
			ModelResourcePermission<CommerceOrder>
				commerceOrderModelResourcePermission,
			OrderItem orderItem, CommerceOrder commerceOrder,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws Exception {

		ExportImportThreadLocal.setPortletImportInProcess(true);

		CPInstance cpInstance = null;

		if (orderItem.getSkuId() != null) {
			cpInstance = cpInstanceService.getCPInstance(orderItem.getSkuId());
		}

		if ((cpInstance == null) &&
			(orderItem.getSkuExternalReferenceCode() != null)) {

			cpInstance = cpInstanceService.fetchByExternalReferenceCode(
				orderItem.getSkuExternalReferenceCode(),
				serviceContext.getCompanyId());
		}

		if (cpInstance == null) {
			throw new CPInstanceSkuException();
		}

		CommerceOrderItem commerceOrderItem;

		if (commerceOrder.isOpen()) {
			commerceOrderItem = commerceOrderItemService.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				cpInstance.getCPInstanceId(),
				GetterUtil.getString(orderItem.getOptions(), null),
				BigDecimal.valueOf(GetterUtil.get(orderItem.getQuantity(), 0)),
				GetterUtil.getLong(orderItem.getReplacedSkuId()),
				BigDecimalUtil.get(
					orderItem.getShippedQuantity(), BigDecimal.ZERO),
				StringPool.BLANK, commerceContext, serviceContext);
		}
		else {
			commerceOrderItem =
				commerceOrderItemService.importCommerceOrderItem(
					GetterUtil.getString(orderItem.getExternalReferenceCode()),
					GetterUtil.getLong(orderItem.getId()),
					commerceOrder.getCommerceOrderId(),
					cpInstance.getCPInstanceId(),
					GetterUtil.getString(orderItem.getUnitOfMeasure()),
					BigDecimal.valueOf(
						GetterUtil.get(orderItem.getQuantity(), 0)),
					BigDecimalUtil.get(
						orderItem.getShippedQuantity(), BigDecimal.ZERO),
					BigDecimal.ZERO, StringPool.BLANK, serviceContext);
		}

		commerceOrderItem =
			commerceOrderItemService.updateCommerceOrderItemInfo(
				commerceOrderItem.getCommerceOrderItemId(),
				GetterUtil.get(orderItem.getShippingAddressId(), 0L),
				GetterUtil.get(orderItem.getDeliveryGroup(), StringPool.BLANK),
				GetterUtil.get(orderItem.getPrintedNote(), StringPool.BLANK));

		Date requestedDeliveryDate = orderItem.getRequestedDeliveryDate();

		if (requestedDeliveryDate != null) {
			commerceOrderItem =
				commerceOrderItemService.updateCommerceOrderItemDeliveryDate(
					commerceOrderItem.getCommerceOrderItemId(),
					requestedDeliveryDate);
		}

		if (Validator.isNotNull(orderItem.getExternalReferenceCode())) {
			commerceOrderItemService.updateExternalReferenceCode(
				commerceOrderItem.getCommerceOrderItemId(),
				orderItem.getExternalReferenceCode());
		}

		PortletResourcePermission portletResourcePermission =
			commerceOrderModelResourcePermission.getPortletResourcePermission();

		if (portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				commerceOrder.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_ORDER_PRICES)) {

			commerceOrderItem =
				commerceOrderItemService.updateCommerceOrderItemPrices(
					commerceOrderItem.getCommerceOrderItemId(),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountAmount(),
						commerceOrderItem.getDiscountAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountWithTaxAmount(),
						commerceOrderItem.getDiscountWithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel1(),
						commerceOrderItem.getDiscountPercentageLevel1()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel1WithTaxAmount(),
						commerceOrderItem.
							getDiscountPercentageLevel1WithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel2(),
						commerceOrderItem.getDiscountPercentageLevel2()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel2WithTaxAmount(),
						commerceOrderItem.
							getDiscountPercentageLevel2WithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel3(),
						commerceOrderItem.getDiscountPercentageLevel3()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel3WithTaxAmount(),
						commerceOrderItem.
							getDiscountPercentageLevel3WithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel4(),
						commerceOrderItem.getDiscountPercentageLevel4()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel4WithTaxAmount(),
						commerceOrderItem.
							getDiscountPercentageLevel4WithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getFinalPrice(),
						commerceOrderItem.getFinalPrice()),
					(BigDecimal)GetterUtil.get(
						orderItem.getFinalPriceWithTaxAmount(),
						commerceOrderItem.getFinalPriceWithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getPromoPrice(),
						commerceOrderItem.getPromoPrice()),
					(BigDecimal)GetterUtil.get(
						orderItem.getPromoPriceWithTaxAmount(),
						commerceOrderItem.getPromoPriceWithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getUnitPrice(),
						commerceOrderItem.getUnitPrice()),
					(BigDecimal)GetterUtil.get(
						orderItem.getUnitPriceWithTaxAmount(),
						commerceOrderItem.getUnitPriceWithTaxAmount()));
		}

		return commerceOrderItem;
	}

	public static CommerceOrderItem addOrUpdateCommerceOrderItem(
			CPInstanceService cpInstanceService,
			CommerceOrderItemService commerceOrderItemService,
			ModelResourcePermission<CommerceOrder>
				commerceOrderModelResourcePermission,
			OrderItem orderItem, CommerceOrder commerceOrder,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws Exception {

		ExportImportThreadLocal.setPortletImportInProcess(true);

		CPInstance cpInstance = null;

		if (orderItem.getSkuId() != null) {
			cpInstance = cpInstanceService.getCPInstance(orderItem.getSkuId());
		}

		if (orderItem.getSkuExternalReferenceCode() != null) {
			cpInstance = cpInstanceService.fetchByExternalReferenceCode(
				orderItem.getSkuExternalReferenceCode(),
				serviceContext.getCompanyId());
		}

		if (cpInstance == null) {
			throw new CPInstanceSkuException();
		}

		String deliveryGroup = StringPool.BLANK;
		String json = null;
		String printedNote = StringPool.BLANK;
		BigDecimal quantity = BigDecimal.ZERO;
		BigDecimal shippedQuantity = BigDecimal.ZERO;
		long shippingAddressId = 0;

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemService.fetchCommerceOrderItem(
				GetterUtil.getLong(orderItem.getId()));

		if ((commerceOrderItem == null) &&
			!Validator.isBlank(orderItem.getExternalReferenceCode())) {

			commerceOrderItem =
				commerceOrderItemService.fetchByExternalReferenceCode(
					orderItem.getExternalReferenceCode(),
					serviceContext.getCompanyId());
		}

		if (commerceOrderItem != null) {
			deliveryGroup = commerceOrderItem.getDeliveryGroup();
			json = commerceOrderItem.getJson();
			printedNote = commerceOrderItem.getPrintedNote();
			quantity = commerceOrderItem.getQuantity();
			shippedQuantity = commerceOrderItem.getShippedQuantity();
			shippingAddressId = commerceOrderItem.getShippingAddressId();
		}

		if (commerceOrder.isOpen()) {
			commerceOrderItem =
				commerceOrderItemService.addOrUpdateCommerceOrderItem(
					commerceOrder.getCommerceOrderId(),
					cpInstance.getCPInstanceId(),
					GetterUtil.getString(orderItem.getOptions(), json),
					BigDecimal.valueOf(
						GetterUtil.get(
							orderItem.getQuantity(), quantity.intValue())),
					GetterUtil.getLong(orderItem.getReplacedSkuId()),
					BigDecimalUtil.get(
						orderItem.getShippedQuantity(), shippedQuantity),
					StringPool.BLANK, commerceContext, serviceContext);
		}
		else {
			quantity = BigDecimal.valueOf(
				GetterUtil.get(orderItem.getQuantity(), quantity.intValue()));

			commerceOrderItem =
				commerceOrderItemService.importCommerceOrderItem(
					orderItem.getExternalReferenceCode(),
					GetterUtil.getLong(orderItem.getId()),
					commerceOrder.getCommerceOrderId(),
					cpInstance.getCPInstanceId(),
					GetterUtil.getString(orderItem.getUnitOfMeasure()),
					quantity,
					BigDecimalUtil.get(
						orderItem.getShippedQuantity(), shippedQuantity),
					BigDecimal.ONE, StringPool.BLANK, serviceContext);
		}

		commerceOrderItem =
			commerceOrderItemService.updateCommerceOrderItemInfo(
				commerceOrderItem.getCommerceOrderItemId(),
				GetterUtil.get(
					orderItem.getShippingAddressId(), shippingAddressId),
				GetterUtil.get(orderItem.getDeliveryGroup(), deliveryGroup),
				GetterUtil.get(orderItem.getPrintedNote(), printedNote));

		Date requestedDeliveryDate = orderItem.getRequestedDeliveryDate();

		if (requestedDeliveryDate != null) {
			commerceOrderItem =
				commerceOrderItemService.updateCommerceOrderItemDeliveryDate(
					commerceOrderItem.getCommerceOrderItemId(),
					requestedDeliveryDate);
		}

		if (Validator.isNotNull(orderItem.getExternalReferenceCode())) {
			commerceOrderItemService.updateExternalReferenceCode(
				commerceOrderItem.getCommerceOrderItemId(),
				orderItem.getExternalReferenceCode());
		}

		// Pricing

		PortletResourcePermission portletResourcePermission =
			commerceOrderModelResourcePermission.getPortletResourcePermission();

		if (portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				commerceOrder.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_ORDER_PRICES)) {

			commerceOrderItem =
				commerceOrderItemService.updateCommerceOrderItemPrices(
					commerceOrderItem.getCommerceOrderItemId(),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountAmount(),
						commerceOrderItem.getDiscountAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountWithTaxAmount(),
						commerceOrderItem.getDiscountWithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel1(),
						commerceOrderItem.getDiscountPercentageLevel1()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel1WithTaxAmount(),
						commerceOrderItem.
							getDiscountPercentageLevel1WithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel2(),
						commerceOrderItem.getDiscountPercentageLevel2()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel2WithTaxAmount(),
						commerceOrderItem.
							getDiscountPercentageLevel2WithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel3(),
						commerceOrderItem.getDiscountPercentageLevel3()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel3WithTaxAmount(),
						commerceOrderItem.
							getDiscountPercentageLevel3WithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel4(),
						commerceOrderItem.getDiscountPercentageLevel4()),
					(BigDecimal)GetterUtil.get(
						orderItem.getDiscountPercentageLevel4WithTaxAmount(),
						commerceOrderItem.
							getDiscountPercentageLevel4WithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getFinalPrice(),
						commerceOrderItem.getFinalPrice()),
					(BigDecimal)GetterUtil.get(
						orderItem.getFinalPriceWithTaxAmount(),
						commerceOrderItem.getFinalPriceWithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getPromoPrice(),
						commerceOrderItem.getPromoPrice()),
					(BigDecimal)GetterUtil.get(
						orderItem.getPromoPriceWithTaxAmount(),
						commerceOrderItem.getPromoPriceWithTaxAmount()),
					(BigDecimal)GetterUtil.get(
						orderItem.getUnitPrice(),
						commerceOrderItem.getUnitPrice()),
					(BigDecimal)GetterUtil.get(
						orderItem.getUnitPriceWithTaxAmount(),
						commerceOrderItem.getUnitPriceWithTaxAmount()));
		}

		return commerceOrderItem;
	}

}