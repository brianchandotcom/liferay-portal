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

package com.liferay.portlet.dynamicdatamapping.io;

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
import java.util.Set;

/**
 * @author Pablo Carvalho
 */
public class DDMFormXSDSerializerImpl implements DDMFormXSDSerializer {

	@Override
	public String serialize(DDMForm ddmForm) {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute(
			"available-locales", getAvailableLanguagesIds(ddmForm));
		rootElement.addAttribute(
			"default-locale",
			LocaleUtil.toLanguageId(ddmForm.getDefaultLocale()));

		addDynamicElementElements(ddmForm.getDDMFormFields(), rootElement);

		return document.asXML();
	}

	protected void addDynamicElementAttributes(
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
		dynamicElementElement.addAttribute(
			"showLabel", Boolean.toString(ddmFormField.isShowLabel()));
		dynamicElementElement.addAttribute("type", ddmFormField.getType());
	}

	protected void addDynamicElementElement(
		DDMFormField ddmFormField, Element element) {

		Element dynamicElementElement = SAXReaderUtil.createElement(
			"dynamic-element");

		addDynamicElementAttributes(ddmFormField, dynamicElementElement);

		addDynamicElementElements(
			ddmFormField.getNestedDDMFormFields(), dynamicElementElement);

		String ddmFormFieldType = ddmFormField.getType();

		if (ddmFormFieldType.equals("radio") ||
			ddmFormFieldType.equals("select")) {

			addOptionsDynamicElements(
				ddmFormField.getDDMFormFieldOptions(), dynamicElementElement);
		}

		Map<Locale, Map<String, String>> metadataMap =
			getDDMFormFieldMetadataMap(ddmFormField);

		addMetadataElements(metadataMap, dynamicElementElement);

		List<Element> elements = dynamicElementElement.elements();

		if (!elements.isEmpty()) {
			element.add(dynamicElementElement);
		}
	}

	protected void addDynamicElementElements(
		List<DDMFormField> ddmFormFields, Element element) {

		for (DDMFormField ddmFormField : ddmFormFields) {
			addDynamicElementElement(ddmFormField, element);
		}
	}

	protected void addMetadataElements(
		Map<Locale, Map<String, String>> metadataMap,
		Element dynamicElementElement) {

		for (Locale locale : metadataMap.keySet()) {
			Element metadataElement = dynamicElementElement.addElement(
				"meta-data");

			metadataElement.addAttribute(
				"locale", LocaleUtil.toLanguageId(locale));

			addMetadataEntry(metadataMap.get(locale), metadataElement);
		}
	}

	protected void addMetadataEntry(
		Map<String, String> entryMap, Element metadataElement) {

		for (Map.Entry<String, String> entry : entryMap.entrySet()) {
			Element entryElement = metadataElement.addElement("entry");

			entryElement.addAttribute("name", entry.getKey());

			entryElement.addText(entry.getValue());
		}
	}

	protected void addMetadataEntryValues(
		Map<Locale, Map<String, String>> ddmFormFieldMetadataMap,
		String entryName, LocalizedValue localizedValue) {

		for (Locale availableLocale : localizedValue.getAvailableLocales()) {
			Map<String, String> metadataMap = ddmFormFieldMetadataMap.get(
				availableLocale);

			if (metadataMap == null) {
				metadataMap = new HashMap<String, String>();

				ddmFormFieldMetadataMap.put(availableLocale, metadataMap);
			}

			metadataMap.put(
				entryName, localizedValue.getString(availableLocale));
		}
	}

	protected Element addOptionDynamicElement(
		String optionValue, Element dynamicElement) {

		Element optionElement = dynamicElement.addElement("dynamic-element");

		optionElement.addAttribute("name", "option_" + StringUtil.randomId());
		optionElement.addAttribute("type", "option");
		optionElement.addAttribute("value", optionValue);

		return optionElement;
	}

	protected void addOptionsDynamicElements(
		DDMFormFieldOptions ddmFormFieldOptions,
		Element dynamicElementElement) {

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
			Element optionElement = addOptionDynamicElement(
				optionValue, dynamicElementElement);

			Map<Locale, Map<String, String>> optionLabelsMap =
				getOptionLabelsMap(
					ddmFormFieldOptions.getOptionLabels(optionValue));

			addMetadataElements(optionLabelsMap, optionElement);
		}
	}

	protected String getAvailableLanguagesIds(DDMForm ddmForm) {
		Set<Locale> availableLocales = ddmForm.getAvailableLocales();

		String[] availableLanguageIds = LocaleUtil.toLanguageIds(
			availableLocales.toArray(new Locale[availableLocales.size()]));

		return StringUtil.merge(availableLanguageIds);
	}

	protected Map<Locale, Map<String, String>> getDDMFormFieldMetadataMap(
		DDMFormField ddmFormField) {

		Map<Locale, Map<String, String>> ddmFormFieldMetadataMap =
			new HashMap<Locale, Map<String, String>>();

		addMetadataEntryValues(
			ddmFormFieldMetadataMap, "label", ddmFormField.getLabel());

		addMetadataEntryValues(
			ddmFormFieldMetadataMap, "predefinedValue",
			ddmFormField.getPredefinedValue());

		addMetadataEntryValues(
			ddmFormFieldMetadataMap, "tip", ddmFormField.getTip());

		return ddmFormFieldMetadataMap;
	}

	protected Map<Locale, Map<String, String>> getOptionLabelsMap(
		LocalizedValue optionLabels) {

		Map<Locale, Map<String, String>> optionLabelsMap =
			new HashMap<Locale, Map<String, String>>();

		for (Locale availableLocale : optionLabels.getAvailableLocales()) {
			Map<String, String> optionMetadataEntries =
				new HashMap<String, String>();

			optionMetadataEntries.put(
				"label", optionLabels.getString(availableLocale));

			optionLabelsMap.put(availableLocale, optionMetadataEntries);
		}

		return optionLabelsMap;
	}

}