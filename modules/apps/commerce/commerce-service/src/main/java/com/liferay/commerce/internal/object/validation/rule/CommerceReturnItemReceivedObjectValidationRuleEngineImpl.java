/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.validation.rule;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.math.BigDecimal;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = ObjectValidationRuleEngine.class)
public class CommerceReturnItemReceivedObjectValidationRuleEngineImpl
	extends BaseObjectValidationRuleEngineImpl {

	@Override
	protected String getObjectDefinitionName() {
		return "CommerceReturnItem";
	}

	@Override
	protected String getObjectFieldName() {
		return "received";
	}

	@Override
	protected boolean hasValidationCriteriaMet(Map<String, Object> inputObjects)
		throws Exception {

		Map<String, Object> entryDTO = (Map<String, Object>)inputObjects.get(
			"entryDTO");

		Map<String, Object> properties = (Map<String, Object>)entryDTO.get(
			"properties");

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(
				GetterUtil.getLong(
					properties.get(
						"r_commerceOrderItemToCommerceReturnItems_" +
							"commerceOrderItemId")));

		BigDecimal received = BigDecimal.valueOf(
			GetterUtil.getDouble(properties.get("received")));

		if (BigDecimalUtil.lte(
				received,
				BigDecimal.valueOf(
					GetterUtil.getDouble(properties.get("authorized")))) &&
			BigDecimalUtil.eq(
				received.remainder(
					commerceOrderItem.
						getUnitOfMeasureIncrementalOrderQuantity()),
				BigDecimal.ZERO)) {

			return true;
		}

		return false;
	}

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

}