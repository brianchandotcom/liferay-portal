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

package com.liferay.translation.internal.exporter;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "content.type=application/xliff+xml",
	service = TranslationInfoItemFieldValuesExporter.class
)
public class XLIFF20InfoFormTranslationExporter
	implements TranslationInfoItemFieldValuesExporter {

	@Override
	public InputStream exportInfoItemFieldValues(
			InfoItemFieldValues infoItemFieldValues, Locale sourceLocale,
			Locale targetLocale)
		throws IOException {

		Document document = SAXReaderUtil.createDocument();

		Element xliffElement = document.addElement(
			"xliff", "urn:oasis:names:tc:xliff:document:2.0");

		xliffElement.addAttribute(
			"srcLang", LocaleUtil.toBCP47LanguageId(sourceLocale));
		xliffElement.addAttribute(
			"trgLang", LocaleUtil.toBCP47LanguageId(targetLocale));
		xliffElement.addAttribute("version", "2.0");

		Element fileElement = xliffElement.addElement("file");

		InfoItemReference infoItemReference =
			infoItemFieldValues.getInfoItemReference();

		fileElement.addAttribute(
			"id",
			infoItemReference.getClassName() + StringPool.COLON +
				infoItemReference.getClassPK());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues();

		for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
			InfoField infoField = infoFieldValue.getInfoField();

			if (!_translationInfoFieldChecker.isTranslatable(infoField)) {
				continue;
			}

			Element unitElement = fileElement.addElement("unit");

			unitElement.addAttribute("id", infoField.getName());

			Element segmentElement = unitElement.addElement("segment");

			Element sourceElement = segmentElement.addElement("source");

			sourceElement.addCDATA(
				_getStringValue(infoFieldValue.getValue(sourceLocale)));

			Element targetElement = segmentElement.addElement("target");

			targetElement.addCDATA(
				_getStringValue(infoFieldValue.getValue(targetLocale)));
		}

		String formattedString = document.formattedString();

		return new ByteArrayInputStream(formattedString.getBytes());
	}

	@Override
	public String getMimeType() {
		return "application/xliff+xml";
	}

	private String _getStringValue(Object value) {
		if (value == null) {
			return null;
		}

		return value.toString();
	}

	@Reference
	private TranslationInfoFieldChecker _translationInfoFieldChecker;

}