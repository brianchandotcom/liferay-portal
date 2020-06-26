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
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.translation.exception.XLIFFFileException;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import net.sf.okapi.lib.xliff2.InvalidParameterException;
import net.sf.okapi.lib.xliff2.XLIFFException;
import net.sf.okapi.lib.xliff2.core.Fragment;
import net.sf.okapi.lib.xliff2.core.Part;
import net.sf.okapi.lib.xliff2.core.StartXliffData;
import net.sf.okapi.lib.xliff2.core.Unit;
import net.sf.okapi.lib.xliff2.document.FileNode;
import net.sf.okapi.lib.xliff2.document.XLIFFDocument;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "content.type=application/xliff+xml",
	service = TranslationInfoItemFieldValuesExporter.class
)
public class XLIFFInfoFormTranslationExporter<T>
	implements TranslationInfoItemFieldValuesExporter<T> {

	@Override
	public InputStream export(
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

		InfoItemClassPKReference infoItemClassPKReference =
			infoItemFieldValues.getInfoItemClassPKReference();

		fileElement.addAttribute(
			"id",
			infoItemClassPKReference.getClassName() + StringPool.COLON +
				infoItemClassPKReference.getClassPK());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues();

		for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
			InfoField infoField = infoFieldValue.getInfoField();

			if (!_translationInfoFieldChecker.isTranslatable(infoField)) {
				continue;
			}

			Object sourceValue = infoFieldValue.getValue(sourceLocale);

			if (Validator.isNull(sourceValue)) {
				continue;
			}

			Element unitElement = fileElement.addElement("unit");

			unitElement.addAttribute("id", infoField.getName());

			Element segmentElement = unitElement.addElement("segment");

			Element sourceElement = segmentElement.addElement("source");

			sourceElement.addCDATA(_getStringValue(sourceValue));

			Element targetElement = segmentElement.addElement("target");

			targetElement.addCDATA(
				_getStringValue(infoFieldValue.getValue(targetLocale)));
		}

		String formattedString = document.formattedString();

		return new ByteArrayInputStream(formattedString.getBytes());
	}

	@Override
	public InfoItemFieldValues importXLIFF(
			long groupId, InfoItemClassPKReference infoItemClassPKReference,
			InputStream inputStream)
		throws IOException, XLIFFFileException {

		InfoItemFieldValues infoItemFieldValues = new InfoItemFieldValues(
			infoItemClassPKReference);

		try {
			XLIFFDocument xliffDocument = new XLIFFDocument();

			xliffDocument.load(FileUtil.createTempFile(inputStream));

			_validateXLIFFFile(infoItemClassPKReference, xliffDocument);

			StartXliffData startXliffData = xliffDocument.getStartXliffData();

			Locale targetLocale = LocaleUtil.fromLanguageId(
				startXliffData.getTargetLanguage(), true, false);

			List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

			for (Unit unit : xliffDocument.getUnits()) {
				String field = unit.getId();

				if (unit.getPartCount() != 1) {
					throw new XLIFFFileException.MustNotHaveMoreThanOne(
						"The file only can have one unit");
				}

				Part valuePart = unit.getPart(0);

				Fragment value = valuePart.getTarget();

				InfoLocalizedValue<String> infoLocalizedValue =
					InfoLocalizedValue.builder(
					).addValue(
						targetLocale, field
					).build();

				InfoField infoField = new InfoField(
					TextInfoFieldType.INSTANCE, infoLocalizedValue, true,
					field);

				InfoFieldValue<Object> infoFieldValue = new InfoFieldValue<>(
					infoField, value.getPlainText());

				infoFieldValues.add(infoFieldValue);
			}

			infoItemFieldValues.addAll(infoFieldValues);

			return infoItemFieldValues;
		}
		catch (InvalidParameterException invalidParameterException) {
			throw new XLIFFFileException.MustNotHaveInvalidParameter(
				invalidParameterException);
		}
		catch (XLIFFException xliffException) {
			throw new XLIFFFileException.MustNotBeInvalidFile(xliffException);
		}
	}

	private String _getStringValue(Object value) {
		if (value == null) {
			return null;
		}

		return value.toString();
	}

	private void _validateXLIFFCompletion(XLIFFDocument xliffDocument)
		throws XLIFFFileException {

		StartXliffData startXliffData = xliffDocument.getStartXliffData();

		String sourceLanguage = startXliffData.getSourceLanguage();

		if (Validator.isNull(sourceLanguage)) {
			throw new XLIFFFileException.MustNotBeIncomplete(
				"There is no translation source");
		}

		Locale sourceLocale = LocaleUtil.fromLanguageId(
			sourceLanguage, true, false);

		if (sourceLocale == null) {
			throw new XLIFFFileException.MustNotBeUnsupportedLanguage(
				sourceLanguage);
		}

		String targetLanguage = startXliffData.getTargetLanguage();

		if (Validator.isNull(targetLanguage)) {
			throw new XLIFFFileException.MustNotBeIncomplete(
				"There is no translation target");
		}

		Locale targetLocale = LocaleUtil.fromLanguageId(
			targetLanguage, true, false);

		if (targetLocale == null) {
			throw new XLIFFFileException.MustNotBeUnsupportedLanguage(
				targetLanguage);
		}
	}

	private void _validateXLIFFFile(
			InfoItemClassPKReference infoItemClassPKReference,
			XLIFFDocument xliffDocument)
		throws XLIFFFileException {

		_validateXLIFFCompletion(xliffDocument);

		_validateXLIFFFileNode(infoItemClassPKReference, xliffDocument);
	}

	private void _validateXLIFFFileNode(
			InfoItemClassPKReference infoItemClassPKReference,
			XLIFFDocument xliffDocument)
		throws XLIFFFileException {

		List<String> fileNodeIds = xliffDocument.getFileNodeIds();

		if (fileNodeIds.size() != 1) {
			throw new XLIFFFileException.MustNotHaveMoreThanOne(
				"Only one node is allowed");
		}

		FileNode fileNode = xliffDocument.getFileNode(
			infoItemClassPKReference.getClassName() + StringPool.COLON +
				infoItemClassPKReference.getClassPK());

		if (fileNode == null) {
			throw new XLIFFFileException.MustNotHaveInvalidId(
				"File ID is invalid");
		}
	}

	@Reference
	private TranslationInfoFieldChecker _translationInfoFieldChecker;

}