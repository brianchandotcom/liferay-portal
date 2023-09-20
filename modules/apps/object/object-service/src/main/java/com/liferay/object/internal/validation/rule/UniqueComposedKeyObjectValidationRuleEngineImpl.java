/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.validation.rule;

import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.constants.ObjectValidationRuleSettingConstants;
import com.liferay.object.internal.entry.util.ObjectEntrySearchUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mateus Santana
 */
@Component(service = ObjectValidationRuleEngine.class)
public class UniqueComposedKeyObjectValidationRuleEngineImpl
	implements ObjectValidationRuleEngine {

	@Override
	public Map<String, Object> execute(
		Map<String, Object> inputObjects, String script) {

		Map<String, Object> results = HashMapBuilder.<String, Object>put(
			"validationCriteriaMet", true
		).build();

		ObjectValidationRule objectValidationRule =
			(ObjectValidationRule)inputObjects.get("objectValidationRule");

		List<ObjectField> objectFields = new ArrayList<>();

		for (ObjectValidationRuleSetting objectValidationRuleSetting :
				objectValidationRule.getObjectValidationRuleSettings()) {

			if (objectValidationRuleSetting.compareName(
					ObjectValidationRuleSettingConstants.
						NAME_KEY_OBJECT_FIELD_ID)) {

				objectFields.add(
					_objectFieldLocalService.fetchObjectField(
						GetterUtil.getLong(
							objectValidationRuleSetting.getValue())));
			}
		}

		Map<String, Object> baseModel = (Map<String, Object>)inputObjects.get(
			"baseModel");
		Map<String, Object> entryDTO = (Map<String, Object>)inputObjects.get(
			"entryDTO");
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectValidationRule.getObjectDefinitionId());

		long objectEntriesCount =
			_objectEntryLocalService.getObjectEntriesCount(
				GetterUtil.getLong(baseModel.get("groupId")),
				new DynamicObjectDefinitionTable(
					objectDefinition, objectFields,
					objectDefinition.getDBTableName()),
				new DynamicObjectDefinitionTable(
					objectDefinition, objectFields,
					objectDefinition.getExtensionDBTableName()),
				objectDefinition.getScope(),
				_getPredicate(
					(Map<String, Object>)entryDTO.get("properties"),
					objectValidationRule.getObjectDefinitionId(),
					objectFields));

		if (objectEntriesCount > 0) {
			results.put("validationCriteriaMet", false);
		}

		return results;
	}

	@Override
	public String getKey() {
		return ObjectValidationRuleConstants.ENGINE_TYPE_COMPOSED_KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, getKey());
	}

	private Predicate _getPredicate(
		Map<String, Object> entryValues, long objectDefinitionId,
		List<ObjectField> objectFields) {

		Predicate predicate = null;

		for (ObjectField objectField : objectFields) {
			Table<?> table = null;

			try {
				table = _objectFieldLocalService.getTable(
					objectDefinitionId, objectField.getName());
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}

			Column<?, ?> column = table.getColumn(
				objectField.getDBColumnName());

			if (column == null) {
				continue;
			}

			Predicate uniqueComposedKeyObjectFieldPredicate =
				ObjectEntrySearchUtil.getUniqueComposedKeyObjectFieldPredicate(
					(Column<?, Object>)column, objectField.getDBType(),
					GetterUtil.getString(
						entryValues.get(objectField.getName())));

			if (predicate == null) {
				predicate = uniqueComposedKeyObjectFieldPredicate;
			}
			else {
				predicate = predicate.and(
					uniqueComposedKeyObjectFieldPredicate);
			}
		}

		return predicate;
	}

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}