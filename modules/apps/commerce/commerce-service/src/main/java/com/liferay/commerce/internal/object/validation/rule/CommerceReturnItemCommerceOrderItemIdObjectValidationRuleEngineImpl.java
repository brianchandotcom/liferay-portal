/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.validation.rule;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

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
public class CommerceReturnItemCommerceOrderItemIdObjectValidationRuleEngineImpl
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
							"r_commerceOrderItemToCommerceReturnItems" +
								"_commerceOrderItemId")));

			if (commerceOrderItem == null) {
				return results;
			}

			ObjectDefinition commerceReturnObjectDefinition =
				_objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						"L_COMMERCE_RETURN", CompanyThreadLocal.getCompanyId());

			if (commerceReturnObjectDefinition == null) {
				return results;
			}

			ObjectEntry commerceReturnObjectEntry =
				_objectEntryLocalService.fetchObjectEntry(
					GetterUtil.getString(
						properties.get(
							"r_commerceReturnToCommerceReturnItems_c" +
								"_commerceReturnERC")),
					commerceReturnObjectDefinition.getObjectDefinitionId());

			if (commerceReturnObjectEntry == null) {
				return results;
			}

			Map<String, Serializable> commerceReturnValues =
				commerceReturnObjectEntry.getValues();

			if (commerceOrderItem.getCommerceOrderId() == GetterUtil.getLong(
					commerceReturnValues.get(
						"r_commerceOrderToCommerceReturns_commerceOrderId"))) {

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
		return "javaDelegate#commerceReturnItemCommerceOrderItemId";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			locale, "commerce-return-item-commerce-order-item-id");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReturnItemCommerceOrderItemIdObjectValidationRuleEngineImpl.
			class);

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}