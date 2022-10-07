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

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.kernel.xml.XPath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Carvalho
 */
@Component(
	property = "ddm.form.deserializer.type=xsd",
	service = DDMFormDeserializer.class
)
public class DDMFormXSDDeserializer implements DDMFormDeserializer {

	@Override
	public DDMFormDeserializerDeserializeResponse deserialize(
		DDMFormDeserializerDeserializeRequest
			ddmFormDeserializerDeserializeRequest) {

		DDMForm ddmForm = new DDMForm();

		DDMFormDeserializerDeserializeResponse.Builder builder =
			DDMFormDeserializerDeserializeResponse.Builder.newBuilder(ddmForm);

		try {
			Document document = _saxReader.read(
				ddmFormDeserializerDeserializeRequest.getContent());

			setDDMFormAvailableLocales(document.getRootElement(), ddmForm);
			setDDMFormDefaultLocale(document.getRootElement(), ddmForm);
			setDDMFormFields(document.getRootElement(), ddmForm);

			setDDMFormLocalizedValuesDefaultLocale(ddmForm);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			builder = builder.exception(exception);
		}

		return builder.build();
	}

	protected void addOptionValueLabels(
		Element dynamicElementElement, DDMFormFieldOptions ddmFormFieldOptions,
		String optionValue) {

		List<Element> metadataElements = dynamicElementElement.elements(
			"meta-data");

		for (Element metadataElement : metadataElements) {
			String languageId = metadataElement.attributeValue("locale");

			Locale locale = LocaleUtil.fromLanguageId(languageId);

			Element labelElement = _fetchMetadataEntry(
				metadataElement, "label");

			ddmFormFieldOptions.addOptionLabel(
				optionValue, locale, labelElement.getText());
		}
	}

	protected Set<Locale> getAvailableLocales(Element rootElement) {
		Set<Locale> availableLocales = new HashSet<>();

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

	protected DDMFormField getDDMFormField(Element dynamicElementElement) {
		String name = dynamicElementElement.attributeValue("name");
		String type = dynamicElementElement.attributeValue("type");

		DDMFormField ddmFormField = new DDMFormField(name, type);

		_setDDMFormFieldDataType(dynamicElementElement, ddmFormField);
		_setDDMFormFieldIndexType(dynamicElementElement, ddmFormField);
		_setDDMFormFieldLocalizable(dynamicElementElement, ddmFormField);
		_setDDMFormFieldMultiple(dynamicElementElement, ddmFormField);
		_setDDMFormFieldNamespace(dynamicElementElement, ddmFormField);
		_setDDMFormFieldReadOnly(dynamicElementElement, ddmFormField);
		_setDDMFormFieldReference(dynamicElementElement, ddmFormField);
		_setDDMFormFieldRepeatable(dynamicElementElement, ddmFormField);
		_setDDMFormFieldRequired(dynamicElementElement, ddmFormField);
		_setDDMFormFieldShowLabel(dynamicElementElement, ddmFormField);

		List<Element> metadataElements = dynamicElementElement.elements(
			"meta-data");

		for (Element metadataElement : metadataElements) {
			_setDDMFormFieldMetadata(metadataElement, ddmFormField);
		}

		if (type.equals("radio") || type.equals("select")) {
			setDDMFormFieldOptions(dynamicElementElement, ddmFormField);
		}
		else {
			setNestedDDMFormField(dynamicElementElement, ddmFormField);
		}

		return ddmFormField;
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		List<Element> dynamicElementElements) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (Element dynamicElementElement : dynamicElementElements) {
			String value = dynamicElementElement.attributeValue("value");

			ddmFormFieldOptions.addOption(value);
			ddmFormFieldOptions.addOptionReference(
				value, dynamicElementElement.attributeValue("reference"));

			addOptionValueLabels(
				dynamicElementElement, ddmFormFieldOptions, value);
		}

		return ddmFormFieldOptions;
	}

	protected List<DDMFormField> getDDMFormFields(Element rootElement) {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (Element dynamicElement : rootElement.elements("dynamic-element")) {
			ddmFormFields.add(getDDMFormField(dynamicElement));
		}

		return ddmFormFields;
	}

	protected Locale getDefaultLocale(Element rootElement) {
		String defaultLanguageId = rootElement.attributeValue("default-locale");

		return LocaleUtil.fromLanguageId(defaultLanguageId);
	}

	protected void setDDMFormAvailableLocales(
		Element rootElement, DDMForm ddmForm) {

		ddmForm.setAvailableLocales(getAvailableLocales(rootElement));
	}

	protected void setDDMFormDefaultLocale(
		Element rootElement, DDMForm ddmForm) {

		ddmForm.setDefaultLocale(getDefaultLocale(rootElement));
	}

	protected void setDDMFormFieldLocalizedValueDefaultLocale(
		LocalizedValue localizedValue, Locale defaultLocale) {

		if (localizedValue == null) {
			return;
		}

		localizedValue.setDefaultLocale(defaultLocale);
	}

	protected void setDDMFormFieldLocalizedValuesDefaultLocale(
		DDMFormField ddmFormField, Locale defaultLocale) {

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getLabel(), defaultLocale);

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getPredefinedValue(), defaultLocale);

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getStyle(), defaultLocale);

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getTip(), defaultLocale);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		if (ddmFormFieldOptions != null) {
			ddmFormFieldOptions.setDefaultLocale(defaultLocale);
		}

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			setDDMFormFieldLocalizedValuesDefaultLocale(
				nestedDDMFormField, defaultLocale);
		}
	}

	protected void setDDMFormFieldOptions(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		ddmFormField.setDDMFormFieldOptions(
			getDDMFormFieldOptions(
				dynamicElementElement.elements("dynamic-element")));
	}

	protected void setDDMFormFields(Element rootElement, DDMForm ddmForm) {
		ddmForm.setDDMFormFields(getDDMFormFields(rootElement));
	}

	protected void setDDMFormLocalizedValuesDefaultLocale(DDMForm ddmForm) {
		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			setDDMFormFieldLocalizedValuesDefaultLocale(
				ddmFormField, ddmForm.getDefaultLocale());
		}
	}

	protected void setNestedDDMFormField(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		ddmFormField.setNestedDDMFormFields(
			getDDMFormFields(dynamicElementElement));
	}

	private Element _fetchMetadataEntry(
		Element parentElement, String entryName) {

		XPath xPathSelector = _saxReader.createXPath(
			"entry[@name=" + HtmlUtil.escapeXPathAttribute(entryName) +
				StringPool.CLOSE_BRACKET);

		return (Element)xPathSelector.selectSingleNode(parentElement);
	}

	private void _setDDMFormFieldDataType(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		ddmFormField.setDataType(
			dynamicElementElement.attributeValue("dataType"));
	}

	private void _setDDMFormFieldIndexType(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		ddmFormField.setIndexType(
			dynamicElementElement.attributeValue("indexType"));
	}

	private void _setDDMFormFieldLocalizable(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		ddmFormField.setLocalizable(
			GetterUtil.getBoolean(
				dynamicElementElement.attributeValue("localizable"), true));
	}

	private void _setDDMFormFieldMetadata(
		Element metadataElement, DDMFormField ddmFormField) {

		String languageId = metadataElement.attributeValue("locale");

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		Element labelElement = _fetchMetadataEntry(metadataElement, "label");

		if (labelElement != null) {
			LocalizedValue label = ddmFormField.getLabel();

			label.addString(locale, labelElement.getText());
		}

		Element predefinedValueElement = _fetchMetadataEntry(
			metadataElement, "predefinedValue");

		if (predefinedValueElement != null) {
			LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

			predefinedValue.addString(locale, predefinedValueElement.getText());
		}

		Element styleElement = _fetchMetadataEntry(metadataElement, "style");

		if (styleElement != null) {
			LocalizedValue style = ddmFormField.getStyle();

			style.addString(locale, styleElement.getText());
		}

		Element tipElement = _fetchMetadataEntry(metadataElement, "tip");

		if (tipElement != null) {
			LocalizedValue tip = ddmFormField.getTip();

			tip.addString(locale, tipElement.getText());
		}
	}

	private void _setDDMFormFieldMultiple(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		ddmFormField.setMultiple(
			GetterUtil.getBoolean(
				dynamicElementElement.attributeValue("multiple")));
	}

	private void _setDDMFormFieldNamespace(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		ddmFormField.setFieldNamespace(
			dynamicElementElement.attributeValue("fieldNamespace"));
	}

	private void _setDDMFormFieldReadOnly(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		ddmFormField.setReadOnly(
			GetterUtil.getBoolean(
				dynamicElementElement.attributeValue("readOnly")));
	}

	private void _setDDMFormFieldReference(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		String fieldReference = dynamicElementElement.attributeValue(
			"fieldReference");

		if (Validator.isNotNull(fieldReference)) {
			ddmFormField.setFieldReference(fieldReference);
		}
	}

	private void _setDDMFormFieldRepeatable(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		ddmFormField.setRepeatable(
			GetterUtil.getBoolean(
				dynamicElementElement.attributeValue("repeatable")));
	}

	private void _setDDMFormFieldRequired(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		ddmFormField.setRequired(
			GetterUtil.getBoolean(
				dynamicElementElement.attributeValue("required")));
	}

	private void _setDDMFormFieldShowLabel(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		ddmFormField.setShowLabel(
			GetterUtil.getBoolean(
				dynamicElementElement.attributeValue("showLabel"), true));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormXSDDeserializer.class);

	@Reference
	private SAXReader _saxReader;

}