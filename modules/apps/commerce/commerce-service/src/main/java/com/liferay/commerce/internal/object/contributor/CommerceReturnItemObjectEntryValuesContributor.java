/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.contributor;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.CommerceOrderItemPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.object.entry.ObjectEntryContext;
import com.liferay.object.entry.contributor.ObjectEntryValuesContributor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
		Map<String, Serializable> values = objectEntryContext.getValues();

		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntryContext.getObjectDefinitionId());

			if (StringUtil.equals(
					objectDefinition.getName(), "CommerceReturnItem")) {

				CommerceOrderItem commerceOrderItem =
					_commerceOrderItemService.getCommerceOrderItem(
						GetterUtil.getLong(
							values.get(
								"r_commerceOrderItemToCommerceReturnItems_" +
									"commerceOrderItemId")));

				values.put(
					"amount",
					_calculateAmount(
						commerceOrderItem.getCommerceOrder(), commerceOrderItem,
						values));
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private double _calculateAmount(
			CommerceOrder commerceOrder, CommerceOrderItem commerceOrderItem,
			Map<String, Serializable> values)
		throws PortalException {

		CommerceOrderItemPrice commerceOrderItemPricePerUnit =
			_commerceOrderPriceCalculation.getCommerceOrderItemPricePerUnit(
				commerceOrder.getCommerceCurrency(), commerceOrderItem);

		CommerceMoney finalPrice =
			commerceOrderItemPricePerUnit.getFinalPrice();

		return BigDecimalUtil.multiply(
			new BigDecimal(String.valueOf(values.get("quantity"))),
			finalPrice.getPrice());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReturnItemObjectEntryValuesContributor.class);

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemService;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}