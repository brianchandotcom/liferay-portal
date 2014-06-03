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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pablo Carvalho
 */
public class DDMFormXSDSerializerImpl implements DDMFormXSDSerializer {

	@Override
	public String serialize(DDMForm ddmForm) {
		Element rootElement = SAXReaderUtil.createElement("root");

		rootElement.addAttribute(
			"available-locales", getAvailableLanguagesString(ddmForm));

		rootElement.addAttribute(
			"default-locale",
			LocaleUtil.toLanguageId(ddmForm.getDefaultLocale()));

		addFields(ddmForm.getDDMFormFields(), rootElement);

		Document document = SAXReaderUtil.createDocument(rootElement);

		return document.asXML();
	}

	protected void addElementAttributes(
		DDMFormField ddmFormField, Element dynamicElementElement) {

		dynamicElementElement.addAttribute(
			"dataType", ddmFormField.getDataType());
		dynamicElementElement.addAttribute(
			"fieldNamespace", ddmFormField.getNamespace());
		dynamicElementElement.addAttribute(
			"indexType", ddmFormField.getIndexType());
		dynamicElementElement.addAttribute(
			"localizable", Boolean.toString(ddmFormField.isLocalizable()));
		dynamicElementElement.addAttribute(
			"multiple", Boolean.toString(ddmFormField.isMultiple()));
		dynamicElementElement.addAttribute("name", ddmFormField.getName());
		dynamicElementElement.addAttribute(
			"readOnly", Boolean.toString(ddmFormField.isReadOnly()));
		dynamicElementElement.addAttribute(
			"repeatable", Boolean.toString(ddmFormField.isRepeatable()));
		dynamicElementElement.addAttribute(
			"required", Boolean.toString(ddmFormField.isRequired()));
		dynamicElementElement.addAttribute("type", ddmFormField.getType());
	}

	protected void addField(DDMFormField ddmFormField, Element rootElement) {
		Element dynamicElementElement = SAXReaderUtil.createElement(
			"dynamic-element");

		addElementAttributes(ddmFormField, dynamicElementElement);

		addFields(ddmFormField.getNestedDDMFormFields(), dynamicElementElement);

		String ddmFormFieldType = ddmFormField.getType();

		if (ddmFormFieldType.equals("radio") ||
			ddmFormFieldType.equals("select")) {

			addFieldOptions(ddmFormField, dynamicElementElement);
		}

		Map<Locale, Map<String, String>> localizationMap =
			createFieldLocalizationMap(ddmFormField);

		addMetadataElements(localizationMap, dynamicElementElement);

		rootElement.add(dynamicElementElement);
	}

	protected void addFieldOptions(
		DDMFormField ddmFormField, Element dynamicElementElement) {

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
			Element optionElement = createOptionDynamicElement(optionValue);

			Map<Locale, Map<String, String>> optionLocalizationMap =
				createOptionLocalizationMap(
					ddmFormFieldOptions.getOptionLabels(optionValue));

			addMetadataElements(optionLocalizationMap, optionElement);

			dynamicElementElement.add(optionElement);
		}
	}

	protected void addFields(
		List<DDMFormField> ddmFormFields, Element rootElement) {

		for (DDMFormField ddmFormField : ddmFormFields) {
			addField(ddmFormField, rootElement);
		}
	}

	protected void addMetadataElement(
		Element metadataElement, Map<String, String> entriesMap) {

		for (Map.Entry<String, String> entry : entriesMap.entrySet()) {
			Element entryElement = SAXReaderUtil.createElement("entry");

			entryElement.addAttribute("name", entry.getKey());

			entryElement.addText(entry.getValue());

			metadataElement.add(entryElement);
		}
	}

	protected void addMetadataElements(
		Map<Locale, Map<String, String>> localizationMap,
		Element dynamicElementElement) {

		for (Locale locale : localizationMap.keySet()) {
			Element metadataElement = SAXReaderUtil.createElement("meta-data");

			metadataElement.addAttribute(
				"locale", LocaleUtil.toLanguageId(locale));

			addMetadataElement(metadataElement, localizationMap.get(locale));

			dynamicElementElement.add(metadataElement);
		}
	}

	protected void addMetadataEntry(
		Map<Locale, Map<String, String>> localizationMap, String entryName,
		LocalizedValue localizedValue) {

		for (Locale locale : localizedValue.getAvailableLocales()) {
			Map<String, String> labelEntry = localizationMap.get(locale);

			if (labelEntry == null) {
				labelEntry = new HashMap<String, String>();
				localizationMap.put(locale, labelEntry);
			}

			labelEntry.put(entryName, localizedValue.getValue(locale));
		}
	}

	protected Map<Locale, Map<String, String>> createFieldLocalizationMap(
		DDMFormField ddmFormField) {

		Map<Locale, Map<String, String>> localizationMap =
			new HashMap<Locale, Map<String, String>>();

		addMetadataEntry(localizationMap, "label", ddmFormField.getLabel());

		addMetadataEntry(
			localizationMap, "predefinedValue",
			ddmFormField.getPredefinedValue());

		addMetadataEntry(localizationMap, "tip", ddmFormField.getTip());

		return localizationMap;
	}

	protected Element createOptionDynamicElement(String optionValue) {
		Element optionElement = SAXReaderUtil.createElement("dynamic-element");

		optionElement.addAttribute("name", "option_" + StringUtil.randomId());
		optionElement.addAttribute("type", "option");
		optionElement.addAttribute("value", optionValue);

		return optionElement;
	}

	protected Map<Locale, Map<String, String>> createOptionLocalizationMap(
		LocalizedValue optionLabels) {

		Map<Locale, Map<String, String>> localizationMap =
			new HashMap<Locale, Map<String, String>>();

		for (Locale locale : optionLabels.getAvailableLocales()) {
			Map<String, String> optionMetadataEntries =
				new HashMap<String, String>();

			optionMetadataEntries.put("label", optionLabels.getValue(locale));

			localizationMap.put(locale, optionMetadataEntries);
		}

		return localizationMap;
	}

	protected String getAvailableLanguagesString(DDMForm ddmForm) {
		List<Locale> availableLocales = ddmForm.getAvailableLocales();

		StringBuilder sb = new StringBuilder(availableLocales.size()*2 - 1);

		boolean firstLocale = true;

		for (Locale locale : availableLocales) {
			if (!firstLocale) {
				sb.append(",");
			}

			sb.append(LocaleUtil.toLanguageId(locale));

			firstLocale = false;
		}

		return sb.toString();
	}

}