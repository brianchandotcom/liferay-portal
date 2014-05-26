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
import com.liferay.portal.kernel.xml.DocumentException;
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
	public DDMForm convert(String xsd) throws DocumentException {
		DDMForm ddmForm = new DDMForm();

		Document document = SAXReaderUtil.read(xsd);

		Element root = document.getRootElement();

		ddmForm.setAvailableLocales(getAvailableLocales(root));
		ddmForm.setDefaultLocale(getDefaultLocale(root));
		ddmForm.setFields(getFields(root));

		return ddmForm;
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

	protected List<Locale> getAvailableLocales(Element root) {
		String availableLanguageIds = root.attributeValue("available-locales");

		String[] splitLanguageIds = StringUtil.split(availableLanguageIds);

		List<Locale> availableLocales = new ArrayList<Locale>();

		for (String languageId : splitLanguageIds) {
			availableLocales.add(LocaleUtil.fromLanguageId(languageId));
		}

		return availableLocales;
	}

	protected Locale getDefaultLocale(Element root) {
		String defaultLanguageId = root.attributeValue("default-locale");

		return LocaleUtil.fromLanguageId(defaultLanguageId);
	}

	protected DDMFormField getField(Element dynamicElement) {
		DDMFormField field = new DDMFormField();

		field.setDataType(dynamicElement.attributeValue("dataType"));
		field.setIndexType(dynamicElement.attributeValue("indexType"));
		field.setMultiple(
			GetterUtil.getBoolean(dynamicElement.attributeValue("multiple")));
		field.setName(dynamicElement.attributeValue("name"));
		field.setRequired(
			GetterUtil.getBoolean(dynamicElement.attributeValue("required")));

		String fieldType = dynamicElement.attributeValue("type");

		field.setType(fieldType);

		parseElementMetadata(dynamicElement.elements("meta-data"), field);

		if (fieldType.equals("radio") || fieldType.equals("select")) {
			field.setOptions(
				getOptions(dynamicElement.elements("dynamic-element")));
		}
		else {
			field.setNestedFields(getFields(dynamicElement));
		}

		return field;
	}

	protected List<DDMFormField> getFields(Element root) {
		List<DDMFormField> fields = new ArrayList<DDMFormField>();

		List<Element> dynamicElements = root.elements("dynamic-element");

		for (Element dynamicElement : dynamicElements) {
			fields.add(getField(dynamicElement));
		}

		return fields;
	}

	protected DDMFormFieldOptions getOptions(List<Element> optionElements) {
		DDMFormFieldOptions options = new DDMFormFieldOptions();

		for (Element optionElement : optionElements) {
			String value = optionElement.attributeValue("value");

			options.addOption(value);

			List<Element> metadataElements = optionElement.elements("meta-data");

			for (Element metadataElement : metadataElements) {
				String languageId = metadataElement.attributeValue("locale");
				Locale locale = LocaleUtil.fromLanguageId(languageId);

				Element labelElement = fetchMetadataEntry(
					metadataElement, "label");

				options.addLabelToOption(value, locale, labelElement.getText());
			}
		}

		return options;
	}

	protected void parseElementMetadata(
		List<Element> metadataElements, DDMFormField field) {

		for (Element metadataElement : metadataElements) {
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
	}
}