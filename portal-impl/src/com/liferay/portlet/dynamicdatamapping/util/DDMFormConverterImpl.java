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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Pablo Carvalho
 */
public class DDMFormConverterImpl implements DDMFormConverter {

	@Override
	public DDMForm getForm(String xsd) throws Exception {
		DDMForm form = new DDMForm();

		Document document = SAXReaderUtil.read(xsd);

		setFormAvailableLocales(document.getRootElement(), form);
		setFormDefaultLocale(document.getRootElement(), form);
		setFormFields(document.getRootElement(), form);

		return form;
	}

	protected void addOptionValueLabels(
		Element dynamicElementElement, DDMFormFieldOptions options,
		String optionValue) {

		List<Element> metadataElements = dynamicElementElement.elements(
			"meta-data");

		for (Element metadataElement : metadataElements) {
			String languageId = metadataElement.attributeValue("locale");

			Locale locale = LocaleUtil.fromLanguageId(languageId);

			Element labelElement = fetchMetadataEntry(metadataElement, "label");

			options.addOptionLabel(optionValue, locale, labelElement.getText());
		}
	}

	protected Element fetchMetadataEntry(
		Element parentElement, String entryName) {

		StringBundler sb = new StringBundler(3);

		sb.append("entry[@name=");
		sb.append(HtmlUtil.escapeXPathAttribute(entryName));
		sb.append(StringPool.CLOSE_BRACKET);

		XPath xPathSelector = SAXReaderUtil.createXPath(sb.toString());

		return (Element)xPathSelector.selectSingleNode(parentElement);
	}

	protected List<Locale> getAvailableLocales(Element rootElement) {
		List<Locale> availableLocales = new ArrayList<Locale>();

		String availableLanguageIds = rootElement.attributeValue(
			"available-locales");

		for (String availableLanguageId :
				StringUtil.split(availableLanguageIds)) {

			Locale availableLocale = LocaleUtil.fromLanguageId(
				availableLanguageId);

			availableLocales.add(availableLocale);
		}

		return availableLocales;
	}

	protected Locale getDefaultLocale(Element rootElement) {
		String defaultLanguageId = rootElement.attributeValue("default-locale");

		return LocaleUtil.fromLanguageId(defaultLanguageId);
	}

	protected DDMFormField getField(Element dynamicElementElement) {
		String name = dynamicElementElement.attributeValue("name");
		String type = dynamicElementElement.attributeValue("type");

		DDMFormField field = new DDMFormField(name, type);

		setFieldDataType(dynamicElementElement, field);
		setFieldIndexType(dynamicElementElement, field);
		setFieldMultiple(dynamicElementElement, field);
		setFieldRequired(dynamicElementElement, field);

		List<Element> metadataElements = dynamicElementElement.elements(
			"meta-data");

		for (Element metadataElement : metadataElements) {
			setFieldMetadata(metadataElement, field);
		}

		if (type.equals("radio") || type.equals("select")) {
			setFieldOptions(dynamicElementElement, field);
		}
		else {
			setFieldNestedField(dynamicElementElement, field);
		}

		return field;
	}

	protected List<DDMFormField> getFields(Element rootElement) {
		List<DDMFormField> fields = new ArrayList<DDMFormField>();

		for (Element dynamicElement : rootElement.elements("dynamic-element")) {
			DDMFormField field = getField(dynamicElement);

			fields.add(field);
		}

		return fields;
	}

	protected DDMFormFieldOptions getOptions(
		List<Element> dynamicElementElements) {

		DDMFormFieldOptions options = new DDMFormFieldOptions();

		for (Element dynamicElementElement : dynamicElementElements) {
			String value = dynamicElementElement.attributeValue("value");

			options.addOption(value);

			addOptionValueLabels(dynamicElementElement, options, value);
		}

		return options;
	}

	protected void setFieldDataType(
		Element dynamicElementElement, DDMFormField field) {

		String dataType = dynamicElementElement.attributeValue("dataType");

		field.setDataType(dataType);
	}

	protected void setFieldIndexType(
		Element dynamicElementElement, DDMFormField field) {

		String indexType = dynamicElementElement.attributeValue("indexType");

		field.setIndexType(indexType);
	}

	protected void setFieldMetadata(
		Element metadataElement, DDMFormField field) {

		String languageId = metadataElement.attributeValue("locale");

		Locale currentLocale = LocaleUtil.fromLanguageId(languageId);

		Element labelElement = fetchMetadataEntry(metadataElement, "label");

		if (labelElement != null) {
			LocalizedValue fieldLabel = field.getLabel();

			fieldLabel.addValue(currentLocale, labelElement.getText());
		}

		Element predefinedValueElement = fetchMetadataEntry(
			metadataElement, "predefinedValue");

		if (predefinedValueElement != null) {
			LocalizedValue fieldPredefinedValue = field.getPredefinedValue();

			fieldPredefinedValue.addValue(
				currentLocale, predefinedValueElement.getText());
		}

		Element tipElement = fetchMetadataEntry(metadataElement, "tip");

		if (tipElement != null) {
			LocalizedValue fieldTip = field.getTip();

			fieldTip.addValue(currentLocale, tipElement.getText());
		}

		Element styleElement = fetchMetadataEntry(metadataElement, "style");

		if (styleElement != null) {
			LocalizedValue fieldStyle = field.getStyle();

			fieldStyle.addValue(currentLocale, styleElement.getText());
		}
	}

	protected void setFieldMultiple(
		Element dynamicElementElement, DDMFormField field) {

		boolean multiple = GetterUtil.getBoolean(
			dynamicElementElement.attributeValue("multiple"));

		field.setMultiple(multiple);
	}

	protected void setFieldNestedField(
		Element dynamicElementElement, DDMFormField field) {

		List<DDMFormField> nestedFields = getFields(dynamicElementElement);

		field.setNestedFields(nestedFields);
	}

	protected void setFieldOptions(
		Element dynamicElementElement, DDMFormField field) {

		DDMFormFieldOptions options = getOptions(
			dynamicElementElement.elements("dynamic-element"));

		field.setOptions(options);
	}

	protected void setFieldRequired(
		Element dynamicElementElement, DDMFormField field) {

		boolean required = GetterUtil.getBoolean(
			dynamicElementElement.attributeValue("required"));

		field.setRequired(required);
	}

	protected void setFormAvailableLocales(Element rootElement, DDMForm form) {
		List<Locale> availableLocales = getAvailableLocales(rootElement);

		form.setAvailableLocales(availableLocales);
	}

	protected void setFormDefaultLocale(Element rootElement, DDMForm form) {
		Locale defaultLocale = getDefaultLocale(rootElement);

		form.setDefaultLocale(defaultLocale);
	}

	protected void setFormFields(Element rootElement, DDMForm form) {
		List<DDMFormField> fields = getFields(rootElement);

		form.setFields(fields);
	}

}