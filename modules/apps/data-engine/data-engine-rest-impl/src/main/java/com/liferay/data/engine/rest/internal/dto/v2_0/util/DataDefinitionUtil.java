/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeRegistry;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.spi.converter.SPIDDMFormRuleConverter;
import com.liferay.dynamic.data.mapping.util.SettingsDDMFormFieldsUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jeyvison Nascimento
 */
public class DataDefinitionUtil {

	public static DataDefinition toDataDefinition(
			DataDefinitionContentTypeRegistry dataDefinitionContentTypeRegistry,
			DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
			DDMStructure ddmStructure,
			DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
			SPIDDMFormRuleConverter spiDDMFormRuleConverter)
		throws Exception {

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DataDefinitionContentType dataDefinitionContentType =
			dataDefinitionContentTypeRegistry.getDataDefinitionContentType(
				ddmStructure.getClassNameId());

		return new DataDefinition() {
			{
				availableLanguageIds = _toLanguageIds(
					ddmForm.getAvailableLocales());
				contentType = dataDefinitionContentType.getContentType();
				dataDefinitionFields = _toDataDefinitionFields(
					ddmForm.getDDMFormFields(),
					ddmFormFieldTypeServicesRegistry,
					ddmStructureLayoutLocalService);
				dataDefinitionKey = ddmStructure.getStructureKey();
				dateCreated = ddmStructure.getCreateDate();
				dateModified = ddmStructure.getModifiedDate();
				defaultDataLayout = DataLayoutUtil.toDataLayout(
					ddmFormFieldTypeServicesRegistry,
					ddmStructure.fetchDDMStructureLayout(),
					spiDDMFormRuleConverter);
				defaultLanguageId = LanguageUtil.getLanguageId(
					ddmForm.getDefaultLocale());
				description = LocalizedValueUtil.toStringObjectMap(
					ddmStructure.getDescriptionMap());
				id = ddmStructure.getStructureId();
				name = LocalizedValueUtil.toStringObjectMap(
					ddmStructure.getNameMap());
				siteId = ddmStructure.getGroupId();
				storageType = ddmStructure.getStorageType();
				userId = ddmStructure.getUserId();
			}
		};
	}

	private static Map<String, Object> _getCustomProperties(
		DDMFormField ddmFormField,
		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService) {

		Map<String, DDMFormField> settingsDDMFormFieldsMap =
			SettingsDDMFormFieldsUtil.getSettingsDDMFormFields(
				ddmFormFieldTypeServicesRegistry, ddmFormField.getType());

		Map<String, Object> properties = ddmFormField.getProperties();

		Map<String, Object> customProperties = new HashMap<>();

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (ArrayUtil.contains(_PREDEFINED_PROPERTIES, entry.getKey())) {
				continue;
			}

			DDMFormField settingsDDMFormField = settingsDDMFormFieldsMap.get(
				entry.getKey());

			if (settingsDDMFormField == null) {
				continue;
			}

			if (settingsDDMFormField.isLocalizable()) {
				customProperties.put(
					entry.getKey(),
					LocalizedValueUtil.toLocalizedValuesMap(
						(LocalizedValue)entry.getValue()));
			}
			else if (Objects.equals(
						settingsDDMFormField.getDataType(), "boolean")) {

				customProperties.put(
					entry.getKey(), GetterUtil.getBoolean(entry.getValue()));
			}
			else if (Objects.equals(
						settingsDDMFormField.getDataType(), "ddm-options")) {

				customProperties.put(
					entry.getKey(),
					_toMap((DDMFormFieldOptions)entry.getValue()));
			}
			else if (Objects.equals(settingsDDMFormField.getType(), "select") ||
					 _isFieldSetRows(ddmFormField, settingsDDMFormField)) {

				customProperties.put(
					entry.getKey(),
					_toJSONArray(String.valueOf(entry.getValue())));
			}
			else if (Objects.equals(
						settingsDDMFormField.getType(), "validation")) {

				customProperties.put(
					entry.getKey(),
					_toMap((DDMFormFieldValidation)entry.getValue()));
			}
			else {
				customProperties.put(entry.getKey(), entry.getValue());
			}
		}

		if (Validator.isNotNull(
				ddmFormField.getProperty("ddmStructureLayoutId"))) {

			customProperties.put(
				"rows",
				_getRows(
					GetterUtil.getLong(
						ddmFormField.getProperty("ddmStructureLayoutId")),
					ddmStructureLayoutLocalService));
		}

		return customProperties;
	}

	private static String _getRows(
		long ddmStructureLayoutId,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService) {

		try {
			DDMStructureLayout ddmStructureLayout =
				ddmStructureLayoutLocalService.getStructureLayout(
					ddmStructureLayoutId);

			JSONArray jsonArray = JSONUtil.getValueAsJSONArray(
				JSONFactoryUtil.createJSONObject(
					StringUtil.replace(
						ddmStructureLayout.getDefinition(), "fieldNames",
						"fields")),
				"JSONArray/pages", "Object/0", "JSONArray/rows");

			return jsonArray.toString();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return StringPool.BLANK;
	}

	private static boolean _isFieldSetRows(
		DDMFormField ddmFormField, DDMFormField settingsDDMFormField) {

		if (Objects.equals(
				ddmFormField.getType(), DDMFormFieldTypeConstants.FIELDSET) &&
			Objects.equals(settingsDDMFormField.getName(), "rows")) {

			return true;
		}

		return false;
	}

	private static DataDefinitionField _toDataDefinitionField(
		DDMFormField ddmFormField,
		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService) {

		return new DataDefinitionField() {
			{
				customProperties = _getCustomProperties(
					ddmFormField, ddmFormFieldTypeServicesRegistry,
					ddmStructureLayoutLocalService);
				defaultValue = LocalizedValueUtil.toLocalizedValuesMap(
					ddmFormField.getPredefinedValue());
				fieldType = ddmFormField.getType();
				indexable = Validator.isNotNull(ddmFormField.getIndexType());
				indexType = DataDefinitionField.IndexType.create(
					ddmFormField.getIndexType());
				label = LocalizedValueUtil.toLocalizedValuesMap(
					ddmFormField.getLabel());
				localizable = ddmFormField.isLocalizable();
				name = ddmFormField.getName();
				nestedDataDefinitionFields = _toDataDefinitionFields(
					ddmFormField.getNestedDDMFormFields(),
					ddmFormFieldTypeServicesRegistry,
					ddmStructureLayoutLocalService);
				readOnly = ddmFormField.isReadOnly();
				repeatable = ddmFormField.isRepeatable();
				required = ddmFormField.isRequired();
				showLabel = ddmFormField.isShowLabel();
				tip = LocalizedValueUtil.toLocalizedValuesMap(
					ddmFormField.getTip());
			}
		};
	}

	private static DataDefinitionField[] _toDataDefinitionFields(
		List<DDMFormField> ddmFormFields,
		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService) {

		return TransformUtil.transformToArray(
			ddmFormFields,
			ddmFormField -> _toDataDefinitionField(
				ddmFormField, ddmFormFieldTypeServicesRegistry,
				ddmStructureLayoutLocalService),
			DataDefinitionField.class);
	}

	private static JSONArray _toJSONArray(String value) {
		try {
			return JSONFactoryUtil.createJSONArray(value);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return JSONFactoryUtil.createJSONArray();
	}

	private static String[] _toLanguageIds(Set<Locale> locales) {
		return TransformUtil.transformToArray(
			locales, LanguageUtil::getLanguageId, String.class);
	}

	private static Map<String, List<JSONObject>> _toMap(
		DDMFormFieldOptions ddmFormFieldOptions) {

		Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

		if (optionsValues.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<String, List<JSONObject>> options = new HashMap<>();

		for (String optionValue : optionsValues) {
			LocalizedValue localizedValue = ddmFormFieldOptions.getOptionLabels(
				optionValue);

			for (Locale locale : localizedValue.getAvailableLocales()) {
				String languageId = LanguageUtil.getLanguageId(locale);

				if (options.containsKey(languageId)) {
					List<JSONObject> values = options.get(languageId);

					values.add(
						JSONUtil.put(
							"label", localizedValue.getString(locale)
						).put(
							"reference",
							ddmFormFieldOptions.getOptionReference(optionValue)
						).put(
							"value", optionValue
						));
				}
				else {
					options.put(
						languageId,
						ListUtil.toList(
							JSONUtil.put(
								"label", localizedValue.getString(locale)
							).put(
								"reference",
								ddmFormFieldOptions.getOptionReference(
									optionValue)
							).put(
								"value", optionValue
							)));
				}
			}
		}

		return options;
	}

	private static Map<String, Object> _toMap(
		DDMFormFieldValidation ddmFormFieldValidation) {

		if (ddmFormFieldValidation == null) {
			return Collections.emptyMap();
		}

		return HashMapBuilder.<String, Object>put(
			"errorMessage",
			LocalizedValueUtil.toLocalizedValuesMap(
				ddmFormFieldValidation.getErrorMessageLocalizedValue())
		).put(
			"expression",
			_toMap(ddmFormFieldValidation.getDDMFormFieldValidationExpression())
		).put(
			"parameter",
			LocalizedValueUtil.toLocalizedValuesMap(
				ddmFormFieldValidation.getParameterLocalizedValue())
		).build();
	}

	private static Map<String, Object> _toMap(
		DDMFormFieldValidationExpression ddmFormFieldValidationExpression) {

		if (ddmFormFieldValidationExpression == null) {
			return Collections.emptyMap();
		}

		return HashMapBuilder.<String, Object>put(
			"name", ddmFormFieldValidationExpression.getName()
		).put(
			"value", ddmFormFieldValidationExpression.getValue()
		).build();
	}

	private static final String[] _PREDEFINED_PROPERTIES = {
		"indexType", "label", "localizable", "name", "predefinedValue",
		"readOnly", "repeatable", "required", "showLabel", "tip", "type"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DataDefinitionUtil.class);

}