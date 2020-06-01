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

package com.liferay.translation.web.internal.exporter;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.InfoForm;
import com.liferay.info.field.InfoFormValues;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.translation.exporter.InfoFormTranslationExporter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "content.type=application/xliff+xml",
	service = InfoFormTranslationExporter.class
)
public class XLIFFInfoFormTranslationExporter<T>
	implements InfoFormTranslationExporter<T> {

	@Override
	public InputStream export(
			InfoItemFormProvider<T> infoFormProvider, T t, Locale sourceLocale,
			Locale targetLocale)
		throws IOException {

		Document document = SAXReaderUtil.createDocument();

		Element xliffElement = document.addElement("xliff");

		xliffElement.addAttribute(
			"srcLang", LocaleUtil.toLanguageId(sourceLocale));
		xliffElement.addAttribute(
			"trgLang", LocaleUtil.toLanguageId(targetLocale));
		xliffElement.addAttribute("version", "2.0");
		xliffElement.addAttribute(
			"xmlns", "urn:oasis:names:tc:xliff:document:2.0");

		Element fileElement = xliffElement.addElement("file");

		InfoForm infoForm = infoFormProvider.getInfoForm(t);

		fileElement.addAttribute("id", infoForm.getName());

		InfoFormValues infoFormValues = infoFormProvider.getInfoFormValues(t);

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoFormValues.getInfoFieldValues();

		for (InfoFieldValue infoFieldValue : infoFieldValues) {
			InfoField infoField = infoFieldValue.getInfoField();

			Element unitElement = fileElement.addElement("unit");

			unitElement.addAttribute("id", infoField.getName());

			Element segmentElement = unitElement.addElement("segment");

			Element sourceElement = segmentElement.addElement("source");

			sourceElement.addCDATA(
				(String)infoFieldValue.getValue(sourceLocale));

			Element targetElement = segmentElement.addElement("target");

			targetElement.addCDATA(
				(String)infoFieldValue.getValue(targetLocale));
		}

		String formattedString = document.formattedString();

		return new ByteArrayInputStream(formattedString.getBytes());
	}

}