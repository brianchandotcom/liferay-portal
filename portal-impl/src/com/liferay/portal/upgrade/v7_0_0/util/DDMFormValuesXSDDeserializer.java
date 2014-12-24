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

package com.liferay.portal.upgrade.v7_0_0.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Pablo Carvalho
 */
public class DDMFormValuesXSDDeserializer {

	public static DDMFormValues deserialize(DDMForm ddmForm, String xml) {
		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		try {
			Document document = SAXReaderUtil.read(xml);

			Element rootElement = document.getRootElement();

			// TODO(pablo.carvalho): set ddmFormValues available and default
			// languages

			ddmFormValues.setDDMFormFieldValues(
				getDDMFormFieldValues(rootElement.elements("dynamic-element")));

			updateLocales(ddmFormValues);
		}
		catch (DocumentException e) {

			// TODO(pablo.carvalho): decide what to do with this exception.
			// throw again?

		}

		return ddmFormValues;
	}

	protected static DDMFormFieldValue getDDMFormFieldValue(
		Element dynamicElement) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(dynamicElement.attributeValue("name"));

		List<Element> dynamicContentElements = dynamicElement.elements(
			"dynamic-content");

		ddmFormFieldValue.setValue(getValue(dynamicContentElements));

		ddmFormFieldValue.setNestedDDMFormFields(
			getDDMFormFieldValues(dynamicElement.elements("dynamic-element")));

		return ddmFormFieldValue;
	}

	protected static List<DDMFormFieldValue> getDDMFormFieldValues(
		List<Element> dynamicElements) {

		if (dynamicElements == null) {
			return null;
		}

		List<DDMFormFieldValue> ddmFormFieldValues =
			new ArrayList<DDMFormFieldValue>();

		for (Element dynamicElement : dynamicElements) {
			ddmFormFieldValues.add(getDDMFormFieldValue(dynamicElement));
		}

		return ddmFormFieldValues;
	}

	protected static Value getValue(List<Element> dynamicContentElements) {
		Value value = new LocalizedValue();

		for (Element dynamicContentElement : dynamicContentElements) {
			String fieldValue = dynamicContentElement.getText();

			String languageId = dynamicContentElement.attributeValue(
				"language-id");

			Locale locale = LocaleUtil.fromLanguageId(languageId);

			value.addString(locale, fieldValue);
		}

		return value;
	}

	protected static void updateLocales(DDMFormValues ddmFormValues) {
		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Set<Locale> availableLocales = new LinkedHashSet<Locale>();

		Locale defaultLocale = null;

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			Value value = ddmFormFieldValue.getValue();

			for (Locale availableLocale : value.getAvailableLocales()) {
				availableLocales.add(availableLocale);
			}

			if (defaultLocale == null) {
				defaultLocale = value.getDefaultLocale();
			}
		}

		ddmFormValues.setAvailableLocales(availableLocales);
		ddmFormValues.setDefaultLocale(defaultLocale);
	}

}