/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.contributor;

import com.liferay.commerce.constants.CommerceReturnConstants;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.CommerceOrderItemPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.object.entry.ObjectEntryContext;
import com.liferay.object.entry.contributor.ObjectEntryValuesContributor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = ObjectEntryValuesContributor.class)
public class CommerceReturnItemObjectEntryValuesContributor
	implements ObjectEntryValuesContributor {

	@Override
	public void contribute(ObjectEntryContext objectEntryContext) {
		Map<String, Serializable> commerceReturnItemValues =
			objectEntryContext.getValues();

		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntryContext.getObjectDefinitionId());

			if (!StringUtil.equals(
					objectDefinition.getName(), "CommerceReturnItem")) {

				return;
			}

			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemService.getCommerceOrderItem(
					GetterUtil.getLong(
						commerceReturnItemValues.get(
							CommerceReturnConstants.
								RETURN_ITEM_FIELD_COMMERCE_ORDER_ITEM_ID)));

			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			CommerceOrderItemPrice commerceOrderItemPricePerUnit =
				_commerceOrderPriceCalculation.getCommerceOrderItemPricePerUnit(
					commerceOrder.getCommerceCurrency(), commerceOrderItem);

			CommerceMoney finalPrice =
				commerceOrderItemPricePerUnit.getFinalPrice();

			commerceReturnItemValues.put(
				CommerceReturnConstants.RETURN_ITEM_FIELD_AMOUNT,
				BigDecimalUtil.multiply(
					new BigDecimal(
						String.valueOf(
							commerceReturnItemValues.get(
								CommerceReturnConstants.
									RETURN_ITEM_FIELD_QUANTITY))),
					finalPrice.getPrice()));

			if (Boolean.parseBoolean(
					String.valueOf(
						commerceReturnItemValues.get(
							"skipCommerceReturnItemContributor")))) {

				return;
			}

			ObjectEntry commerceReturnObjectEntry =
				_objectEntryLocalService.getObjectEntry(
					GetterUtil.getLong(
						commerceReturnItemValues.get(
							CommerceReturnConstants.
								RETURN_ITEM_FIELD_COMMERCE_RETURN_ID)));

			Map<String, Serializable> commerceReturnValues =
				commerceReturnObjectEntry.getValues();

			String returnStatus = GetterUtil.getString(
				commerceReturnValues.get(
					CommerceReturnConstants.RETURN_FIELD_RETURN_STATUS));

			if (StringUtil.equals(
					returnStatus,
					CommerceReturnConstants.RETURN_STATUS_DRAFT)) {

				return;
			}

			String nextReturnItemStatus = _calculateNextReturnItemStatus(
				commerceReturnItemValues, returnStatus);

			commerceReturnItemValues.put(
				CommerceReturnConstants.RETURN_ITEM_FIELD_RETURN_ITEM_STATUS,
				nextReturnItemStatus);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private String _calculateNextReturnItemStatus(
		Map<String, Serializable> commerceReturnItemValues,
		String returnStatus) {

		long authorized = GetterUtil.getLong(
			commerceReturnItemValues.get(
				CommerceReturnConstants.RETURN_ITEM_FIELD_AUTHORIZED));

		if (authorized == 0) {
			return CommerceReturnConstants.RETURN_ITEM_STATUS_NOT_AUTHORIZED;
		}

		if (Boolean.parseBoolean(
				String.valueOf(
					commerceReturnItemValues.get(
						CommerceReturnConstants.
							RETURN_ITEM_FIELD_AUTHORIZE_RETURN_WITHOUT_RETURNING_PRODUCTS)))) {

			if (Validator.isNotNull(
					String.valueOf(
						commerceReturnItemValues.get(
							CommerceReturnConstants.
								RETURN_ITEM_FIELD_RETURN_RESOLUTION_METHOD)))) {

				return CommerceReturnConstants.
					RETURN_ITEM_STATUS_TO_BE_PROCESSED;
			}

			return CommerceReturnConstants.RETURN_ITEM_STATUS_RECEIVED;
		}

		long received = GetterUtil.getLong(
			commerceReturnItemValues.get(
				CommerceReturnConstants.RETURN_ITEM_FIELD_RECEIVED));

		if (received == 0) {
			if (StringUtil.equals(
					returnStatus,
					CommerceReturnConstants.RETURN_STATUS_PENDING)) {

				long quantity = GetterUtil.getLong(
					commerceReturnItemValues.get(
						CommerceReturnConstants.RETURN_ITEM_FIELD_QUANTITY));

				if (authorized < quantity) {
					return CommerceReturnConstants.
						RETURN_ITEM_STATUS_PARTIALLY_AUTHORIZED;
				}

				if (authorized == quantity) {
					return CommerceReturnConstants.
						RETURN_ITEM_STATUS_AUTHORIZED;
				}
			}

			if (StringUtil.equals(
					returnStatus,
					CommerceReturnConstants.RETURN_STATUS_AUTHORIZED) &&
				(authorized > 0)) {

				return CommerceReturnConstants.
					RETURN_ITEM_STATUS_RECEIPT_REJECTED;
			}
		}

		if (Validator.isNotNull(
				String.valueOf(
					commerceReturnItemValues.get(
						CommerceReturnConstants.
							RETURN_ITEM_FIELD_RETURN_RESOLUTION_METHOD)))) {

			return CommerceReturnConstants.RETURN_ITEM_STATUS_TO_BE_PROCESSED;
		}

		if (received < authorized) {
			return CommerceReturnConstants.
				RETURN_ITEM_STATUS_PARTIALLY_RECEIVED;
		}

		return CommerceReturnConstants.RETURN_ITEM_STATUS_RECEIVED;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReturnItemObjectEntryValuesContributor.class);

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemService;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}