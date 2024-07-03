/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.validation.rule;

import com.liferay.commerce.constants.CommerceReturnConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemService;
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
public class CommerceReturnItemAccountEntryIdObjectValidationRuleEngineImpl
	implements ObjectDefinitionScoped, ObjectValidationRuleEngine {

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

			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemService.fetchCommerceOrderItem(
					GetterUtil.getLong(
						properties.get(
							CommerceReturnConstants.
								RETURN_ITEM_FIELD_COMMERCE_ORDER_ITEM_ID)));

			if (commerceOrderItem == null) {
				return results;
			}

			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			if (commerceOrder.getCommerceAccountId() == GetterUtil.getLong(
					properties.get(
						CommerceReturnConstants.
							RETURN_ITEM_FIELD_ACCOUNT_ENTRY_ID))) {

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
		return Arrays.asList("CommerceReturnItem");
	}

	@Override
	public String getKey() {
		return "javaDelegate#commerceReturnItemAccountEntryId";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "commerce-return-item-account-entry-id");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReturnItemAccountEntryIdObjectValidationRuleEngineImpl.class);

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private Language _language;

}