/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 */
public class DataDefinitionUtil {

	public static DataDefinition toDataDefinition(
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
			DDMStructure ddmStructure)
		throws Exception {

		DDMForm ddmForm = ddmStructure.getDDMForm();

		return new DataDefinition() {
			{
				availableLanguageIds = _toLanguageIds(
					ddmForm.getAvailableLocales());
				dataDefinitionFields = _toDataDefinitionFields(
					ddmForm.getDDMFormFields(),
					ddmFormFieldTypeServicesTracker);
				dataDefinitionKey = ddmStructure.getStructureKey();
				dateCreated = ddmStructure.getCreateDate();
				dateModified = ddmStructure.getModifiedDate();
				defaultDataLayout = DataLayoutUtil.toDataLayout(
					ddmStructure.fetchDDMStructureLayout());
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

	public static DDMForm toDDMForm(
		DataDefinition dataDefinition,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(
			_toLocales(dataDefinition.getAvailableLanguageIds()));
		ddmForm.setDDMFormFields(
			_toDDMFormFields(
				dataDefinition.getDataDefinitionFields(),
				ddmFormFieldTypeServicesTracker));
		ddmForm.setDefaultLocale(
			LocaleUtil.fromLanguageId(dataDefinition.getDefaultLanguageId()));

		return ddmForm;
	}

	private static Map<String, Object> _getCustomProperties(
		DDMFormField ddmFormField,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		Map<String, DDMFormField> settingsDDMFormFieldsMap =
			_getSettingsDDMFormFields(
				ddmFormFieldTypeServicesTracker, ddmFormField.getType());

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

		return customProperties;
	}

	private static DDMFormFieldOptions _getDDMFormFieldOptions(
		Map<String, List<Map<String, String>>> options) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		if (MapUtil.isEmpty(options)) {
			return ddmFormFieldOptions;
		}

		for (Map.Entry<String, List<Map<String, String>>> entry :
				options.entrySet()) {

			for (Map<String, String> option : entry.getValue()) {
				ddmFormFieldOptions.addOptionLabel(
					MapUtil.getString(option, "value"),
					LocaleUtil.fromLanguageId(entry.getKey()),
					MapUtil.getString(option, "label"));
			}
		}

		return ddmFormFieldOptions;
	}

	private static DDMFormFieldValidation _getDDMFormFieldValidation(
		Map<String, Object> value) {

		if (MapUtil.isEmpty(value)) {
			return null;
		}

		Map<String, String> expression = (Map<String, String>)value.get(
			"expression");

		if (Validator.isNull(MapUtil.getString(expression, "value"))) {
			return null;
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			new DDMFormFieldValidationExpression() {
				{
					setName(MapUtil.getString(expression, "name"));
					setValue(MapUtil.getString(expression, "value"));
				}
			});
		ddmFormFieldValidation.setErrorMessageLocalizedValue(
			LocalizedValueUtil.toLocalizedValue(
				(Map<String, Object>)value.get("errorMessage")));
		ddmFormFieldValidation.setParameterLocalizedValue(
			LocalizedValueUtil.toLocalizedValue(
				(Map<String, Object>)value.get("parameter")));

		return ddmFormFieldValidation;
	}

	private static Map<String, DDMFormField> _getSettingsDDMFormFields(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		String type) {

		DDMFormFieldType ddmFormFieldType =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

		Class<? extends DDMFormFieldTypeSettings> ddmFormFieldTypeSettings =
			DefaultDDMFormFieldTypeSettings.class;

		if (ddmFormFieldType != null) {
			ddmFormFieldTypeSettings =
				ddmFormFieldType.getDDMFormFieldTypeSettings();
		}

		DDMForm settingsDDMForm = DDMFormFactory.create(
			ddmFormFieldTypeSettings);

		return settingsDDMForm.getDDMFormFieldsMap(true);
	}

	private static DataDefinitionField _toDataDefinitionField(
		DDMFormField ddmFormField,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		return new DataDefinitionField() {
			{
				customProperties = _getCustomProperties(
					ddmFormField, ddmFormFieldTypeServicesTracker);
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
					ddmFormFieldTypeServicesTracker);
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
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		if (ListUtil.isEmpty(ddmFormFields)) {
			return new DataDefinitionField[0];
		}

		Stream<DDMFormField> stream = ddmFormFields.stream();

		return stream.map(
			ddmFormField -> _toDataDefinitionField(
				ddmFormField, ddmFormFieldTypeServicesTracker)
		).collect(
			Collectors.toList()
		).toArray(
			new DataDefinitionField[0]
		);
	}

	private static DDMFormField _toDDMFormField(
		DataDefinitionField dataDefinitionField,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		DDMFormField ddmFormField = new DDMFormField();

		ddmFormField.setIndexType(dataDefinitionField.getIndexTypeAsString());
		ddmFormField.setLabel(
			LocalizedValueUtil.toLocalizedValue(
				dataDefinitionField.getLabel()));
		ddmFormField.setLocalizable(
			GetterUtil.getBoolean(dataDefinitionField.getLocalizable()));
		ddmFormField.setName(dataDefinitionField.getName());
		ddmFormField.setNestedDDMFormFields(
			_toDDMFormFields(
				dataDefinitionField.getNestedDataDefinitionFields(),
				ddmFormFieldTypeServicesTracker));
		ddmFormField.setPredefinedValue(
			LocalizedValueUtil.toLocalizedValue(
				dataDefinitionField.getDefaultValue()));
		ddmFormField.setReadOnly(
			GetterUtil.getBoolean(dataDefinitionField.getReadOnly()));
		ddmFormField.setRepeatable(
			GetterUtil.getBoolean(dataDefinitionField.getRepeatable()));
		ddmFormField.setRequired(
			GetterUtil.getBoolean(dataDefinitionField.getRequired()));
		ddmFormField.setShowLabel(
			GetterUtil.getBoolean(dataDefinitionField.getShowLabel()));
		ddmFormField.setTip(
			LocalizedValueUtil.toLocalizedValue(dataDefinitionField.getTip()));
		ddmFormField.setType(dataDefinitionField.getFieldType());

		Map<String, Object> customProperties =
			dataDefinitionField.getCustomProperties();

		if (MapUtil.isNotEmpty(customProperties)) {
			Map<String, DDMFormField> settingsDDMFormFieldsMap =
				_getSettingsDDMFormFields(
					ddmFormFieldTypeServicesTracker,
					dataDefinitionField.getFieldType());

			for (Map.Entry<String, Object> entry :
					customProperties.entrySet()) {

				if (ArrayUtil.contains(
						_PREDEFINED_PROPERTIES, entry.getKey())) {

					continue;
				}

				DDMFormField settingsDDMFormField =
					settingsDDMFormFieldsMap.get(entry.getKey());

				if (settingsDDMFormField == null) {
					continue;
				}

				if (settingsDDMFormField.isLocalizable()) {
					ddmFormField.setProperty(
						entry.getKey(),
						LocalizedValueUtil.toLocalizedValue(
							(Map<String, Object>)entry.getValue()));
				}
				else if (Objects.equals(
							settingsDDMFormField.getDataType(), "boolean")) {

					ddmFormField.setProperty(
						entry.getKey(),
						GetterUtil.getBoolean(entry.getValue()));
				}
				else if (Objects.equals(
							settingsDDMFormField.getDataType(),
							"ddm-options")) {

					ddmFormField.setProperty(
						entry.getKey(),
						_getDDMFormFieldOptions(
							(Map<String, List<Map<String, String>>>)
								entry.getValue()));
				}
				else if (Objects.equals(
							settingsDDMFormField.getType(), "validation")) {

					ddmFormField.setProperty(
						entry.getKey(),
						_getDDMFormFieldValidation(
							(Map<String, Object>)entry.getValue()));
				}
				else {
					ddmFormField.setProperty(entry.getKey(), entry.getValue());
				}
			}
		}

		return ddmFormField;
	}

	private static List<DDMFormField> _toDDMFormFields(
		DataDefinitionField[] dataDefinitionFields,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		if (ArrayUtil.isEmpty(dataDefinitionFields)) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataDefinitionFields
		).map(
			dataDefinitionField -> _toDDMFormField(
				dataDefinitionField, ddmFormFieldTypeServicesTracker)
		).collect(
			Collectors.toList()
		);
	}

	private static String[] _toLanguageIds(Set<Locale> locales) {
		Stream<Locale> stream = locales.stream();

		return stream.map(
			LanguageUtil::getLanguageId
		).collect(
			Collectors.toList()
		).toArray(
			new String[0]
		);
	}

	private static Set<Locale> _toLocales(String[] languageIds) {
		if (ArrayUtil.isEmpty(languageIds)) {
			return Collections.emptySet();
		}

		return Stream.of(
			languageIds
		).map(
			LocaleUtil::fromLanguageId
		).collect(
			Collectors.toSet()
		);
	}

	private static Map<String, List<Map<String, String>>> _toMap(
		DDMFormFieldOptions ddmFormFieldOptions) {

		Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

		if (optionsValues.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<String, List<Map<String, String>>> options = new HashMap<>();

		for (String optionValue : optionsValues) {
			LocalizedValue localizedValue = ddmFormFieldOptions.getOptionLabels(
				optionValue);

			for (Locale locale : localizedValue.getAvailableLocales()) {
				String languageId = LanguageUtil.getLanguageId(locale);

				if (options.containsKey(languageId)) {
					List<Map<String, String>> values = options.get(languageId);

					values.add(
						HashMapBuilder.put(
							"label", localizedValue.getString(locale)
						).put(
							"value", optionValue
						).build());
				}
				else {
					options.put(
						languageId,
						ListUtil.toList(
							HashMapBuilder.put(
								"label", localizedValue.getString(locale)
							).put(
								"value", optionValue
							).build()));
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

}