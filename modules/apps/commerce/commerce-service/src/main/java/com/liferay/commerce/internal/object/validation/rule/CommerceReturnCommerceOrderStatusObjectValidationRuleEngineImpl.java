/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.validation.rule;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = ObjectValidationRuleEngine.class)
public class CommerceReturnCommerceOrderStatusObjectValidationRuleEngineImpl
	implements ObjectDefinitionScoped, ObjectValidationRuleEngine {

	public static final String
		COMMERCE_RETURN_COMMERCE_ORDER_STATUS_VALIDATION_RULE_ENGINE =
			"commerce-return-commerce-order-status-validation-rule-engine";

	@Override
	public Map<String, Object> execute(
		Map<String, Object> inputObjects, String script) {

		Map<String, Object> results = HashMapBuilder.<String, Object>put(
			"validationCriteriaMet", false
		).build();

		try {
			Map<String, Object> entryDTO =
				(Map<String, Object>)inputObjects.get("entryDTO");

			Map<String, Object> properties = (Map<String, Object>)entryDTO.get(
				"properties");

			CommerceOrder commerceOrder =
				_commerceOrderService.getCommerceOrder(
					GetterUtil.getLong(
						properties.get(
							"r_commerceOrderToCommerceReturns_" +
								"commerceOrderId")));

			if (CommerceOrderConstants.ORDER_STATUS_COMPLETED ==
					commerceOrder.getOrderStatus()) {

				results.put("validationCriteriaMet", true);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return results;
	}

	@Override
	public List<String> getAllowedObjectDefinitionNames() {
		return Arrays.asList("CommerceReturn");
	}

	@Override
	public String getKey() {
		return ObjectValidationRuleConstants.ENGINE_TYPE_JAVA_DELEGATE_PREFIX +
			COMMERCE_RETURN_COMMERCE_ORDER_STATUS_VALIDATION_RULE_ENGINE;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			locale,
			COMMERCE_RETURN_COMMERCE_ORDER_STATUS_VALIDATION_RULE_ENGINE);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReturnCommerceOrderStatusObjectValidationRuleEngineImpl.class);

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private Language _language;

}