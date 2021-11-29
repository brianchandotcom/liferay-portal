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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "content.type=application/x-xliff+xml",
	service = TranslationInfoItemFieldValuesExporter.class
)
public class XLIFF12InfoFormTranslationExporter
	implements TranslationInfoItemFieldValuesExporter {

	@Override
	public InputStream exportInfoItemFieldValues(
			InfoItemFieldValues infoItemFieldValues, Locale sourceLocale,
			Locale targetLocale)
		throws IOException {

		Document document = SAXReaderUtil.createDocument();

		Element xliffElement = document.addElement(
			"xliff", "urn:oasis:names:tc:xliff:document:1.2");

		xliffElement.addAttribute("version", "1.2");

		Element fileElement = xliffElement.addElement("file");

		fileElement.addAttribute("datatype", "plaintext");

		InfoItemReference infoItemReference =
			infoItemFieldValues.getInfoItemReference();

		fileElement.addAttribute(
			"original",
			infoItemReference.getClassName() + StringPool.COLON +
				infoItemReference.getClassPK());

		fileElement.addAttribute(
			"source-language", LocaleUtil.toBCP47LanguageId(sourceLocale));
		fileElement.addAttribute(
			"target-language", LocaleUtil.toBCP47LanguageId(targetLocale));
		fileElement.addAttribute("tool", "Liferay");

		Element bodyElement = fileElement.addElement("body");

		Map<String, List<InfoFieldValue<Object>>> infoFieldValuesMap =
			new LinkedHashMap<>();

		for (InfoFieldValue<Object> infoFieldValue :
				infoItemFieldValues.getInfoFieldValues()) {

			InfoField infoField = infoFieldValue.getInfoField();

			if (_translationInfoFieldChecker.isTranslatable(infoField)) {
				List<InfoFieldValue<Object>> infoFieldValuesList =
					infoFieldValuesMap.computeIfAbsent(
						infoField.getName(), name -> new ArrayList<>());

				infoFieldValuesList.add(infoFieldValue);
			}
		}

		for (Map.Entry<String, List<InfoFieldValue<Object>>> entry :
				infoFieldValuesMap.entrySet()) {

			Element transUnitElement = bodyElement.addElement("trans-unit");

			transUnitElement.addAttribute("id", entry.getKey());

			Element sourceElement = transUnitElement.addElement("source");

			sourceElement.addAttribute(
				"xml:lang", fileElement.attributeValue("source-language"));

			List<InfoFieldValue<Object>> infoFieldValuesList = entry.getValue();

			Stream<InfoFieldValue<Object>> stream =
				infoFieldValuesList.stream();

			sourceElement.addCDATA(
				_getStringValue(
					stream.map(
						infoFieldValue -> (String)infoFieldValue.getValue(
							sourceLocale)
					).collect(
						Collectors.joining(StringPool.COMMA_AND_SPACE)
					)));

			if (infoFieldValuesList.size() > 1) {
				Element segSourceElement = transUnitElement.addElement(
					"seg-source");

				int mid = 0;

				for (InfoFieldValue<Object> infoFieldValue :
						infoFieldValuesList) {

					Element mrkElement = segSourceElement.addElement("mrk");

					mrkElement.addAttribute("mid", String.valueOf(mid));
					mrkElement.addAttribute("mtype", "seg");
					mrkElement.addCDATA(
						(String)infoFieldValue.getValue(sourceLocale));

					mid++;
				}
			}

			Element targetElement = transUnitElement.addElement("target");

			targetElement.addAttribute(
				"xml:lang", fileElement.attributeValue("target-language"));

			if (infoFieldValuesList.size() > 1) {
				int mid = 0;

				for (InfoFieldValue<Object> infoFieldValue :
						infoFieldValuesList) {

					Element mrkElement = targetElement.addElement("mrk");

					mrkElement.addAttribute("mid", String.valueOf(mid));
					mrkElement.addAttribute("mtype", "seg");
					mrkElement.addCDATA(
						(String)infoFieldValue.getValue(targetLocale));

					mid++;
				}
			}
			else {
				InfoFieldValue<Object> infoFieldValue = infoFieldValuesList.get(
					0);

				targetElement.addCDATA(
					_getStringValue(infoFieldValue.getValue(targetLocale)));
			}
		}

		String formattedString = document.formattedString();

		return new ByteArrayInputStream(formattedString.getBytes());
	}

	@Override
	public String getMimeType() {
		return "application/x-xliff+xml";
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