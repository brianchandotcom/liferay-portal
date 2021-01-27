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

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMDataDefinitionConverter;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = DDMDataDefinitionConverter.class)
public class DDMDataDefinitionConverterImpl
	implements DDMDataDefinitionConverter {

	@Override
	public String convertDDMFormDataDefinition(
		DDMForm ddmForm, Locale defaultLocale) {

		if (Objects.equals(ddmForm.getDefinitionSchemaVersion(), "2.0")) {
			return DDMFormSerializeUtil.serialize(ddmForm, _ddmFormSerializer);
		}

		ddmForm.setDefinitionSchemaVersion("2.0");

		_upgradeFields(ddmForm.getDDMFormFields(), defaultLocale);

		ddmForm = _upgradeNestedFields(ddmForm);

		return DDMFormSerializeUtil.serialize(ddmForm, _ddmFormSerializer);
	}

	@Override
	public String convertDDMFormDataDefinition(
			String dataDefinition, Locale defaultLocale)
		throws Exception {

		DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
			_ddmFormDeserializer, dataDefinition);

		return convertDDMFormDataDefinition(ddmForm, defaultLocale);
	}

	@Override
	public String convertDDMFormLayoutDataDefinition(String dataDefinition)
		throws Exception {

		DDMFormLayout ddmFormLayout = DDMFormLayoutDeserializeUtil.deserialize(
			_ddmFormLayoutDeserializer, dataDefinition);

		ddmFormLayout.setDefinitionSchemaVersion("2.0");
		ddmFormLayout.setPaginationMode(DDMFormLayout.SINGLE_PAGE_MODE);

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			LocalizedValue localizedValue = ddmFormLayoutPage.getTitle();

			if (localizedValue == null) {
				localizedValue = new LocalizedValue();

				localizedValue.addString(
					ddmFormLayout.getDefaultLocale(),
					LanguageUtil.get(ddmFormLayout.getDefaultLocale(), "page"));

				for (Locale locale : ddmFormLayout.getAvailableLocales()) {
					localizedValue.addString(
						locale, LanguageUtil.get(locale, "page"));
				}
			}
			else {
				if (Validator.isNull(
						localizedValue.getString(
							ddmFormLayout.getDefaultLocale()))) {

					localizedValue.addString(
						ddmFormLayout.getDefaultLocale(),
						LanguageUtil.get(
							ddmFormLayout.getDefaultLocale(), "page"));
				}
			}

			ddmFormLayoutPage.setTitle(localizedValue);

			localizedValue = ddmFormLayoutPage.getDescription();

			if (localizedValue == null) {
				localizedValue = new LocalizedValue();

				localizedValue.addString(
					ddmFormLayout.getDefaultLocale(),
					LanguageUtil.get(
						ddmFormLayout.getDefaultLocale(), "description"));

				for (Locale locale : ddmFormLayout.getAvailableLocales()) {
					localizedValue.addString(
						locale, LanguageUtil.get(locale, "description"));
				}
			}
			else {
				if (Validator.isNull(
						localizedValue.getString(
							ddmFormLayout.getDefaultLocale()))) {

					localizedValue.addString(
						ddmFormLayout.getDefaultLocale(),
						LanguageUtil.get(
							ddmFormLayout.getDefaultLocale(), "description"));
				}
			}

			ddmFormLayoutPage.setDescription(localizedValue);
		}

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				_ddmFormLayoutSerializer.serialize(
					DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
						ddmFormLayout
					).build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	@Override
	public String convertDDMFormLayoutDataDefinition(
			String structureLayoutDataDefinition,
			String structureVersionDataDefinition)
		throws Exception {

		DDMFormLayout ddmFormLayout = DDMFormLayoutDeserializeUtil.deserialize(
			_ddmFormLayoutDeserializer, structureLayoutDataDefinition);

		DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
			_ddmFormDeserializer, structureVersionDataDefinition);

		DDMFormLayoutPage ddmFormLayoutPage =
			ddmFormLayout.getDDMFormLayoutPage(0);

		List<DDMFormLayoutRow> ddmFormLayoutRows = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

			ddmFormLayoutRow.addDDMFormLayoutColumn(
				new DDMFormLayoutColumn(
					DDMFormLayoutColumn.FULL, ddmFormField.getName()));

			ddmFormLayoutRows.add(ddmFormLayoutRow);
		}

		ddmFormLayoutPage.setDDMFormLayoutRows(ddmFormLayoutRows);

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				_ddmFormLayoutSerializer.serialize(
					DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
						ddmFormLayout
					).build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	private DDMFormField _createFieldSetDDMFormField(
		Locale defaultLocale, String name,
		List<DDMFormField> nestedDDMFormFields, boolean repeatable) {

		return new DDMFormField(name, "fieldset") {
			{
				setLabel(
					new LocalizedValue() {
						{
							addString(defaultLocale, StringPool.BLANK);
						}
					});
				setLocalizable(false);
				setNestedDDMFormFields(nestedDDMFormFields);
				setProperty("ddmStructureId", StringPool.BLANK);
				setProperty("ddmStructureLayoutId", StringPool.BLANK);
				setProperty("upgradedStructure", false);
				setReadOnly(false);
				setRepeatable(repeatable);
				setRequired(false);
				setShowLabel(false);
			}
		};
	}

	private DDMFormFieldOptions _getDDMFormFieldOptions(
		DDMFormField ddmFormField) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		LocalizedValue localizedValue = ddmFormField.getLabel();

		for (Locale locale : localizedValue.getAvailableLocales()) {
			ddmFormFieldOptions.addOptionLabel(
				ddmFormField.getName(), locale,
				localizedValue.getString(locale));
		}

		return ddmFormFieldOptions;
	}

	private String _getDDMFormFieldsRows(DDMFormField fieldSetDDMFormField) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMFormField ddmFormField :
				fieldSetDDMFormField.getNestedDDMFormFields()) {

			jsonArray.put(
				JSONUtil.put(
					"columns",
					JSONUtil.putAll(
						JSONUtil.put(
							"fields", JSONUtil.putAll(ddmFormField.getName())
						).put(
							"size", 12
						))));
		}

		return jsonArray.toJSONString();
	}

	private LocalizedValue _getEmptyLocalizedValue(Locale defaultLocale) {
		LocalizedValue localizedValue = new LocalizedValue(defaultLocale);

		localizedValue.addString(defaultLocale, StringPool.BLANK);

		return localizedValue;
	}

	private LocalizedValue _getLocalizedPredefinedValue(
		DDMFormField ddmFormField) {

		LocalizedValue newPredefinedValue = new LocalizedValue();

		LocalizedValue oldPredefinedValue = ddmFormField.getPredefinedValue();

		for (Locale locale : oldPredefinedValue.getAvailableLocales()) {
			if (GetterUtil.getBoolean(oldPredefinedValue.getString(locale))) {
				newPredefinedValue.addString(locale, ddmFormField.getName());
			}
			else {
				newPredefinedValue.addString(locale, StringPool.BLANK);
			}
		}

		newPredefinedValue.setDefaultLocale(
			oldPredefinedValue.getDefaultLocale());

		return newPredefinedValue;
	}

	private boolean _hasNestedFields(DDMForm ddmForm) {
		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			if (ListUtil.isNotEmpty(ddmFormField.getNestedDDMFormFields())) {
				return true;
			}
		}

		return false;
	}

	private void _upgradeBooleanField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("string");
		ddmFormField.setDDMFormFieldOptions(
			_getDDMFormFieldOptions(ddmFormField));
		ddmFormField.setPredefinedValue(
			_getLocalizedPredefinedValue(ddmFormField));
		ddmFormField.setType("checkbox_multiple");
	}

	private void _upgradeColorField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("string");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("color");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeDateField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("date");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("date");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeDecimalField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("double");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("numeric");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeDocumentLibraryField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("document-library");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("document_library");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeField(
		DDMFormField ddmFormField, Locale defaultLocale) {

		if (Objects.equals(ddmFormField.getType(), "checkbox")) {
			_upgradeBooleanField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-color")) {
			_upgradeColorField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-date")) {
			_upgradeDateField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-decimal")) {
			_upgradeDecimalField(ddmFormField);
		}
		else if (Objects.equals(
					ddmFormField.getType(), "ddm-documentlibrary")) {

			_upgradeDocumentLibraryField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-geolocation")) {
			_upgradeGeolocation(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-image")) {
			_upgradeImageField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-integer")) {
			_upgradeIntegerField(ddmFormField);
		}
		else if (Objects.equals(
					ddmFormField.getType(), "ddm-journal-article")) {

			_upgradeJournalArticleField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-link-to-page")) {
			_upgradeLinkToPageField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-number")) {
			_upgradeNumberField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-separator")) {
			_upgradeSeparatorField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "ddm-text-html")) {
			_upgradeHTMLField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "select")) {
			_upgradeSelectField(ddmFormField);
		}
		else if (Objects.equals(ddmFormField.getType(), "text")) {
			_upgradeTextField(ddmFormField, defaultLocale);
		}
		else if (Objects.equals(ddmFormField.getType(), "textarea")) {
			_upgradeTextArea(ddmFormField, defaultLocale);
		}

		if (!Objects.equals(ddmFormField.getType(), "separator") &&
			Validator.isNull(ddmFormField.getIndexType())) {

			ddmFormField.setIndexType("none");
		}

		_upgradeFields(ddmFormField.getNestedDDMFormFields(), defaultLocale);
	}

	private void _upgradeFields(
		List<DDMFormField> ddmFormFields, Locale defaultLocale) {

		if (ddmFormFields.isEmpty()) {
			return;
		}

		for (DDMFormField ddmFormField : ddmFormFields) {
			_upgradeField(ddmFormField, defaultLocale);
		}
	}

	private void _upgradeGeolocation(DDMFormField ddmFormField) {
		ddmFormField.setDataType("geolocation");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("geolocation");
	}

	private void _upgradeHTMLField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("string");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("rich_text");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeImageField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("image");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("image");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeIntegerField(DDMFormField ddmFormField) {
		ddmFormField.setType("numeric");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeJournalArticleField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("journal-article");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("journal_article");
	}

	private void _upgradeLinkToPageField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("link-to-page");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("link_to_layout");
	}

	private DDMForm _upgradeNestedFields(DDMForm ddmForm) {
		if (!_hasNestedFields(ddmForm)) {
			return ddmForm;
		}

		DDMForm newDDMForm = new DDMForm();

		newDDMForm.setAvailableLocales(ddmForm.getAvailableLocales());
		newDDMForm.setDefaultLocale(ddmForm.getDefaultLocale());
		newDDMForm.setDefinitionSchemaVersion(
			ddmForm.getDefinitionSchemaVersion());

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			if (ListUtil.isEmpty(ddmFormField.getNestedDDMFormFields())) {
				newDDMForm.addDDMFormField(ddmFormField);

				continue;
			}

			DDMFormField fieldSetDDMFormField = _createFieldSetDDMFormField(
				ddmForm.getDefaultLocale(), ddmFormField.getName() + "FieldSet",
				ListUtil.fromArray(ddmFormField), ddmFormField.isRepeatable());

			_upgradeNestedFields(
				ddmFormField.getNestedDDMFormFields(),
				ddmForm.getDefaultLocale(), fieldSetDDMFormField);

			fieldSetDDMFormField.setProperty(
				"rows", _getDDMFormFieldsRows(fieldSetDDMFormField));

			ddmFormField.setNestedDDMFormFields(Collections.emptyList());
			ddmFormField.setRepeatable(false);

			newDDMForm.addDDMFormField(fieldSetDDMFormField);
		}

		return newDDMForm;
	}

	private void _upgradeNestedFields(
		List<DDMFormField> ddmFormFields, Locale defaultLocale,
		DDMFormField parentFieldSetDDMFormField) {

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (ListUtil.isEmpty(ddmFormField.getNestedDDMFormFields())) {
				parentFieldSetDDMFormField.addNestedDDMFormField(ddmFormField);

				continue;
			}

			DDMFormField fieldSetDDMFormField = _createFieldSetDDMFormField(
				defaultLocale, ddmFormField.getName() + "FieldSet",
				ListUtil.fromArray(ddmFormField), ddmFormField.isRepeatable());

			_upgradeNestedFields(
				ddmFormField.getNestedDDMFormFields(), defaultLocale,
				fieldSetDDMFormField);

			fieldSetDDMFormField.setProperty(
				"rows", _getDDMFormFieldsRows(fieldSetDDMFormField));

			ddmFormField.setNestedDDMFormFields(Collections.emptyList());
			ddmFormField.setRepeatable(false);

			parentFieldSetDDMFormField.addNestedDDMFormField(
				fieldSetDDMFormField);
		}
	}

	private void _upgradeNumberField(DDMFormField ddmFormField) {
		ddmFormField.setDataType("double");
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("numeric");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeSelectField(DDMFormField ddmFormField) {
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setProperty("dataSourceType", "[manual]");
		ddmFormField.setProperty("ddmDataProviderInstanceId", "[]");
		ddmFormField.setProperty("ddmDataProviderInstanceOutput", "[]");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeSeparatorField(DDMFormField ddmFormField) {
		ddmFormField.setDataType(StringPool.BLANK);
		ddmFormField.setFieldNamespace(StringPool.BLANK);
		ddmFormField.setType("separator");
	}

	private void _upgradeTextArea(
		DDMFormField ddmFormField, Locale defaultLocale) {

		ddmFormField.setFieldNamespace(StringPool.BLANK);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("Option", defaultLocale, "Option");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setProperty("autocomplete", false);
		ddmFormField.setProperty("dataSourceType", "manual");
		ddmFormField.setProperty("ddmDataProviderInstanceId", "[]");
		ddmFormField.setProperty("ddmDataProviderInstanceOutput", "[]");
		ddmFormField.setProperty("displayStyle", "multiline");
		ddmFormField.setProperty(
			"placeholder", _getEmptyLocalizedValue(defaultLocale));
		ddmFormField.setProperty(
			"tooltip", _getEmptyLocalizedValue(defaultLocale));
		ddmFormField.setType("text");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private void _upgradeTextField(
		DDMFormField ddmFormField, Locale defaultLocale) {

		ddmFormField.setFieldNamespace(StringPool.BLANK);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel("Option", defaultLocale, "Option");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setProperty("autocomplete", false);
		ddmFormField.setProperty("dataSourceType", "manual");
		ddmFormField.setProperty("ddmDataProviderInstanceId", "[]");
		ddmFormField.setProperty("ddmDataProviderInstanceOutput", "[]");
		ddmFormField.setProperty("displayStyle", "singleline");
		ddmFormField.setProperty(
			"placeholder", _getEmptyLocalizedValue(defaultLocale));
		ddmFormField.setProperty(
			"tooltip", _getEmptyLocalizedValue(defaultLocale));

		ddmFormField.setType("text");
		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	@Reference
	private DDMFormDeserializer _ddmFormDeserializer;

	@Reference
	private DDMFormLayoutDeserializer _ddmFormLayoutDeserializer;

	@Reference(target = "(ddm.form.layout.serializer.type=json)")
	private DDMFormLayoutSerializer _ddmFormLayoutSerializer;

	@Reference
	private DDMFormSerializer _ddmFormSerializer;

}