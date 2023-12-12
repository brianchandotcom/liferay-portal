/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.validation.rule;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.constants.ObjectValidationRuleSettingConstants;
import com.liferay.object.internal.entry.util.ObjectEntrySearchUtil;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mateus Santana
 */
@Component(service = ObjectValidationRuleEngine.class)
public class UniqueCompositeKeyObjectValidationRuleEngineImpl
	implements ObjectValidationRuleEngine {

	@Override
	public Map<String, Object> execute(
		Map<String, Object> inputObjects, String script) {

		Map<String, Object> results = HashMapBuilder.<String, Object>put(
			"validationCriteriaMet", true
		).build();

		ObjectValidationRule objectValidationRule =
			(ObjectValidationRule)inputObjects.get("objectValidationRule");

		Map<String, Object> baseModel = (Map<String, Object>)inputObjects.get(
			"baseModel");
		Map<String, Object> entryDTO = (Map<String, Object>)inputObjects.get(
			"entryDTO");

		long objectEntriesCount = 0;

		try {
			objectEntriesCount = _objectEntryLocalService.getObjectEntriesCount(
				GetterUtil.getLong(baseModel.get("groupId")),
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectValidationRule.getObjectDefinitionId()),
				_getPredicate(
					GetterUtil.getLong(entryDTO.get("id")),
					(Map<String, Object>)entryDTO.get("properties"),
					objectValidationRule));
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			results.put("validationCriteriaMet", false);
		}

		if (objectEntriesCount > 0) {
			results.put("validationCriteriaMet", false);
		}

		return results;
	}

	@Override
	public String getKey() {
		return ObjectValidationRuleConstants.ENGINE_TYPE_COMPOSITE_KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, getKey());
	}

	private Predicate _getPredicate(
		long objectEntryId, Map<String, Object> entryValues,
		ObjectValidationRule objectValidationRule) {

		Predicate predicate = ObjectEntryTable.INSTANCE.objectEntryId.neq(
			objectEntryId);

		for (ObjectValidationRuleSetting objectValidationRuleSetting :
				objectValidationRule.getObjectValidationRuleSettings()) {

			if (!objectValidationRuleSetting.compareName(
					ObjectValidationRuleSettingConstants.
						NAME_COMPOSITE_KEY_OBJECT_FIELD_ID)) {

				continue;
			}

			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				GetterUtil.getLong(objectValidationRuleSetting.getValue()));

			Table<?> table = null;

			try {
				table = _objectFieldLocalService.getTable(
					objectValidationRule.getObjectDefinitionId(),
					objectField.getName());
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}

			Column<?, ?> column = table.getColumn(
				objectField.getDBColumnName());

			if (column == null) {
				continue;
			}

			Object value = entryValues.get(objectField.getName());

			if (StringUtil.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

				Map<String, Object> objectFieldProperties =
					(Map<String, Object>)entryValues.get(objectField.getName());

				if (objectFieldProperties != null) {
					value = objectFieldProperties.get("key");
				}
				else {
					value = null;
				}
			}

			Predicate uniqueCompositeKeyObjectFieldPredicate =
				ObjectEntrySearchUtil.getUniqueCompositeKeyObjectFieldPredicate(
					(Column<?, Object>)column, objectField.getDBType(), value);

			if (uniqueCompositeKeyObjectFieldPredicate == null) {
				continue;
			}

			predicate = predicate.and(uniqueCompositeKeyObjectFieldPredicate);
		}

		return predicate;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UniqueCompositeKeyObjectValidationRuleEngineImpl.class);

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}