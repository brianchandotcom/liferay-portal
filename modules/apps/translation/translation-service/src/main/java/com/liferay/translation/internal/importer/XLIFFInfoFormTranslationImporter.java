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

package com.liferay.translation.internal.importer;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.translation.exception.XLIFFFileException;
import com.liferay.translation.importer.TranslationInfoItemFieldValuesImporter;
import com.liferay.translation.internal.util.XLIFFLocaleIdUtil;
import com.liferay.translation.snapshot.TranslationSnapshot;
import com.liferay.translation.snapshot.TranslationSnapshotProvider;

import java.io.CharConversionException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.sf.okapi.common.Event;
import net.sf.okapi.common.LocaleId;
import net.sf.okapi.common.exceptions.OkapiIllegalFilterOperationException;
import net.sf.okapi.common.resource.DocumentPart;
import net.sf.okapi.common.resource.ITextUnit;
import net.sf.okapi.common.resource.Property;
import net.sf.okapi.common.resource.RawDocument;
import net.sf.okapi.common.resource.StartDocument;
import net.sf.okapi.common.resource.StartSubDocument;
import net.sf.okapi.common.resource.TextContainer;
import net.sf.okapi.common.resource.TextFragment;
import net.sf.okapi.filters.autoxliff.AutoXLIFFFilter;
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
	service = {
		TranslationInfoItemFieldValuesImporter.class,
		TranslationSnapshotProvider.class
	}
)
public class XLIFFInfoFormTranslationImporter
	implements TranslationInfoItemFieldValuesImporter,
			   TranslationSnapshotProvider {

	@Override
	public TranslationSnapshot getTranslationSnapshot(
			long groupId, InfoItemReference infoItemReference,
			InputStream inputStream)
		throws IOException, PortalException {

		return _getTranslationSnapshot(
			groupId, infoItemReference, inputStream, true);
	}

	@Override
	public InfoItemFieldValues importInfoItemFieldValues(
			long groupId, InfoItemReference infoItemReference,
			InputStream inputStream)
		throws IOException, XLIFFFileException {

		TranslationSnapshot translationSnapshot = _getTranslationSnapshot(
			groupId, infoItemReference, inputStream, false);

		return translationSnapshot.getInfoItemFieldValues();
	}

	private InfoField _createInfoField(Locale locale, String value) {
		return InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			value
		).labelInfoLocalizedValue(
			InfoLocalizedValue.<String>builder(
			).value(
				locale, value
			).build()
		).localizable(
			true
		).build();
	}

	private InfoItemFieldValues _getInfoItemFieldValuesXLIFFv12(
			List<Event> events, InfoItemReference infoItemReference,
			boolean includeSource)
		throws XLIFFFileException {

		_validateDocumentPartVersion(events);

		StartSubDocument startSubDocument = _getStartSubdocument(events);

		_validateXLIFFStartSubdocument(infoItemReference, startSubDocument);

		Locale sourceLocale = _getSourceLocale(startSubDocument);
		Locale targetLocale = _getTargetLocale(startSubDocument);

		return InfoItemFieldValues.builder(
		).<XLIFFFileException>infoFieldValue(
			consumer -> _produceInfoFieldValuesXLIFFv12(
				consumer, events, sourceLocale, targetLocale, includeSource)
		).infoItemReference(
			infoItemReference
		).build();
	}

	private InfoItemFieldValues _getInfoItemFieldValuesXLIFFv20(
			long groupId, InfoItemReference infoItemReference, File tempFile,
			boolean includeSource)
		throws XLIFFFileException {

		XLIFFDocument xliffDocument = new XLIFFDocument();

		xliffDocument.load(tempFile);

		_validateXLIFFFile(groupId, infoItemReference, xliffDocument);

		StartXliffData startXliffData = xliffDocument.getStartXliffData();

		Locale sourceLocale = LocaleUtil.fromLanguageId(
			startXliffData.getSourceLanguage(), true, false);
		Locale targetLocale = LocaleUtil.fromLanguageId(
			startXliffData.getTargetLanguage(), true, false);

		return InfoItemFieldValues.builder(
		).<XLIFFFileException>infoFieldValue(
			consumer -> _produceInfoFieldValuesXLIFFv20(
				consumer, xliffDocument, sourceLocale, targetLocale,
				includeSource)
		).infoItemReference(
			infoItemReference
		).build();
	}

	private Locale _getSourceLocale(StartSubDocument startSubDocument) {
		Property sourceLanguageProperty = startSubDocument.getProperty(
			"sourceLanguage");

		if ((sourceLanguageProperty == null) ||
			(sourceLanguageProperty.getValue() == null)) {

			return null;
		}

		return LocaleUtil.fromLanguageId(sourceLanguageProperty.getValue());
	}

	private StartSubDocument _getStartSubdocument(List<Event> events) {
		for (Event event : events) {
			if (event.isStartSubDocument()) {
				return event.getStartSubDocument();
			}
		}

		return null;
	}

	private Locale _getTargetLocale(StartSubDocument startSubDocument) {
		Property targetLanguageProperty = startSubDocument.getProperty(
			"targetLanguage");

		if ((targetLanguageProperty == null) ||
			(targetLanguageProperty.getValue() == null)) {

			return null;
		}

		return LocaleUtil.fromLanguageId(targetLanguageProperty.getValue());
	}

	private TranslationSnapshot _getTranslationSnapshot(
			long groupId, InfoItemReference infoItemReference,
			InputStream inputStream, boolean includeSource)
		throws IOException, XLIFFFileException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			XLIFFInfoFormTranslationImporter.class.getClassLoader());

		try (AutoXLIFFFilter autoXLIFFFilter = new AutoXLIFFFilter()) {
			File tempFile = FileUtil.createTempFile(inputStream);

			Document document = _saxReader.read(tempFile);

			LocaleId sourceLocaleId = XLIFFLocaleIdUtil.getSourceLocaleId(
				document);
			LocaleId targetLocaleId = XLIFFLocaleIdUtil.getTargetLocaleId(
				document);

			autoXLIFFFilter.open(
				new RawDocument(
					tempFile.toURI(), document.getXMLEncoding(), sourceLocaleId,
					targetLocaleId));

			Stream<Event> stream = autoXLIFFFilter.stream();

			List<Event> events = stream.collect(Collectors.toList());

			if (_isVersion20(events)) {
				return new TranslationSnapshot(
					_getInfoItemFieldValuesXLIFFv20(
						groupId, infoItemReference, tempFile, includeSource),
					LocaleUtil.fromLanguageId(sourceLocaleId.toString()),
					LocaleUtil.fromLanguageId(targetLocaleId.toString()));
			}

			return new TranslationSnapshot(
				_getInfoItemFieldValuesXLIFFv12(
					events, infoItemReference, includeSource),
				LocaleUtil.fromLanguageId(sourceLocaleId.toString()),
				LocaleUtil.fromLanguageId(targetLocaleId.toString()));
		}
		catch (OkapiIllegalFilterOperationException | XLIFFException
					exception) {

			if (exception.getCause() instanceof CharConversionException) {
				throw new XLIFFFileException.MustHaveCorrectEncoding(exception);
			}

			throw new XLIFFFileException.MustBeValid(exception);
		}
		catch (DocumentException documentException) {
			throw new XLIFFFileException.MustHaveCorrectEncoding(
				documentException);
		}
		catch (InvalidParameterException invalidParameterException) {
			throw new XLIFFFileException.MustHaveValidParameter(
				invalidParameterException);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private boolean _isVersion20(List<Event> events) {
		for (Event event : events) {
			if (event.isStartDocument()) {
				StartDocument startDocument = event.getStartDocument();

				Property versionProperty = startDocument.getProperty("version");

				if (versionProperty != null) {
					double version = GetterUtil.getDouble(
						versionProperty.getValue());

					if ((version >= 2.0) && (version < 3.0)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private void _produceInfoFieldValuesXLIFFv12(
			UnsafeConsumer<InfoFieldValue<Object>, XLIFFFileException> consumer,
			List<Event> events, Locale sourceLocale, Locale targetLocale,
			boolean includeSource)
		throws XLIFFFileException {

		for (Event event : events) {
			if (!event.isTextUnit()) {
				continue;
			}

			ITextUnit iTextUnit = event.getTextUnit();

			_validateWellFormedTextUnit(targetLocale, iTextUnit);

			TextContainer sourceTextContainer = iTextUnit.getSource();

			for (LocaleId targetLocaleId : iTextUnit.getTargetLocales()) {
				TextContainer targetTextContainer = iTextUnit.getTarget(
					targetLocaleId);

				TextFragment targetTextFragment =
					targetTextContainer.getFirstContent();

				consumer.accept(
					new InfoFieldValue<>(
						_createInfoField(targetLocale, iTextUnit.getId()),
						InfoLocalizedValue.builder(
						).value(
							targetLocale, targetTextFragment.getText()
						).value(
							biConsumer -> {
								if (includeSource) {
									TextFragment sourceTextFragment =
										sourceTextContainer.getFirstContent();

									biConsumer.accept(
										sourceLocale,
										sourceTextFragment.getText());
								}
							}
						).build()));
			}
		}
	}

	private void _produceInfoFieldValuesXLIFFv20(
			UnsafeConsumer<InfoFieldValue<Object>, XLIFFFileException> consumer,
			XLIFFDocument xliffDocument, Locale sourceLocale,
			Locale targetLocale, boolean includeSource)
		throws XLIFFFileException {

		for (Unit unit : xliffDocument.getUnits()) {
			if (unit.getPartCount() != 1) {
				throw new XLIFFFileException.MustNotHaveMoreThanOne(
					"The file only can have one unit");
			}

			Part part = unit.getPart(0);

			Fragment targetFragment = part.getTarget();

			if (targetFragment == null) {
				throw new XLIFFFileException.MustBeWellFormed(
					"There is no translation target");
			}

			consumer.accept(
				new InfoFieldValue<>(
					_createInfoField(targetLocale, unit.getId()),
					InfoLocalizedValue.builder(
					).value(
						targetLocale, targetFragment.getPlainText()
					).value(
						biConsumer -> {
							if (includeSource) {
								Fragment sourceFragment = part.getSource();

								biConsumer.accept(
									sourceLocale,
									sourceFragment.getPlainText());
							}
						}
					).build()));
		}
	}

	private void _validateDocumentPartVersion(List<Event> events)
		throws XLIFFFileException.MustBeValid {

		for (Event event : events) {
			if (event.isDocumentPart()) {
				DocumentPart documentPart = event.getDocumentPart();

				Property versionProperty = documentPart.getProperty("version");

				if ((versionProperty != null) &&
					!Objects.equals("1.2", versionProperty.getValue())) {

					throw new XLIFFFileException.MustBeValid(
						"version must be 1.2");
				}
			}
		}
	}

	private void _validateWellFormedTextUnit(
			Locale targetLocale, ITextUnit iTextUnit)
		throws XLIFFFileException.MustBeWellFormed {

		TextContainer textContainer = iTextUnit.getSource();
		Set<LocaleId> targetLocaleIds = iTextUnit.getTargetLocales();

		if (!textContainer.isEmpty() && targetLocaleIds.isEmpty()) {
			throw new XLIFFFileException.MustBeWellFormed(
				"There is no translation target");
		}

		if (targetLocaleIds.size() > 1) {
			throw new XLIFFFileException.MustBeWellFormed(
				"Only one translation language per file is permitted");
		}

		for (LocaleId targetLocaleId : targetLocaleIds) {
			if ((targetLocale != null) &&
				!Objects.equals(
					targetLocale,
					LocaleUtil.fromLanguageId(targetLocaleId.toString()))) {

				throw new XLIFFFileException.MustBeWellFormed(
					"Only one translation language per file is permitted");
			}

			TextContainer targetTextContainer = iTextUnit.getTarget(
				targetLocaleId);

			if (!textContainer.isEmpty() && targetTextContainer.isEmpty()) {
				throw new XLIFFFileException.MustBeWellFormed(
					"There is no translation target");
			}
		}
	}

	private void _validateXLIFFCompletion(
			long groupId, XLIFFDocument xliffDocument)
		throws XLIFFFileException {

		StartXliffData startXliffData = xliffDocument.getStartXliffData();

		String sourceLanguage = startXliffData.getSourceLanguage();

		if (Validator.isNull(sourceLanguage)) {
			throw new XLIFFFileException.MustBeWellFormed(
				"There is no translation source");
		}

		Locale sourceLocale = LocaleUtil.fromLanguageId(
			sourceLanguage, true, false);

		if (sourceLocale == null) {
			throw new XLIFFFileException.MustBeSupportedLanguage(
				sourceLanguage);
		}

		Set<Locale> availableLocales = _language.getAvailableLocales(groupId);

		if (!availableLocales.contains(sourceLocale)) {
			throw new XLIFFFileException.MustBeSupportedLanguage(
				sourceLanguage);
		}

		String targetLanguage = startXliffData.getTargetLanguage();

		if (Validator.isNull(targetLanguage)) {
			throw new XLIFFFileException.MustBeWellFormed(
				"There is no translation target");
		}

		Locale targetLocale = LocaleUtil.fromLanguageId(
			targetLanguage, true, false);

		if (targetLocale == null) {
			throw new XLIFFFileException.MustBeSupportedLanguage(
				targetLanguage);
		}

		if (!availableLocales.contains(targetLocale)) {
			throw new XLIFFFileException.MustBeSupportedLanguage(
				targetLanguage);
		}
	}

	private void _validateXLIFFFile(
			long groupId, InfoItemReference infoItemReference,
			XLIFFDocument xliffDocument)
		throws XLIFFFileException {

		_validateXLIFFCompletion(groupId, xliffDocument);

		_validateXLIFFFileNode(infoItemReference, xliffDocument);
	}

	private void _validateXLIFFFileNode(
			InfoItemReference infoItemReference, XLIFFDocument xliffDocument)
		throws XLIFFFileException {

		List<String> fileNodeIds = xliffDocument.getFileNodeIds();

		if (fileNodeIds.size() != 1) {
			throw new XLIFFFileException.MustNotHaveMoreThanOne(
				"Only one node is allowed");
		}

		FileNode fileNode = xliffDocument.getFileNode(
			infoItemReference.getClassName() + StringPool.COLON +
				infoItemReference.getClassPK());

		if (fileNode == null) {
			throw new XLIFFFileException.MustHaveValidId("File ID is invalid");
		}
	}

	private void _validateXLIFFStartSubdocument(
			InfoItemReference infoItemReference,
			StartSubDocument startSubDocument)
		throws XLIFFFileException {

		if (startSubDocument == null) {
			throw new XLIFFFileException.MustBeWellFormed(
				"The XLIFF file is not well Formed");
		}

		String original =
			infoItemReference.getClassName() + StringPool.COLON +
				infoItemReference.getClassPK();

		if (!Objects.equals(startSubDocument.getName(), original)) {
			throw new XLIFFFileException.MustHaveValidId("File ID is invalid");
		}

		Property targetLanguageProperty = startSubDocument.getProperty(
			"targetLanguage");

		if ((targetLanguageProperty == null) ||
			(targetLanguageProperty.getValue() == null)) {

			throw new XLIFFFileException.MustBeWellFormed(
				"There is no translation target");
		}
	}

	@Reference
	private Language _language;

	@Reference
	private SAXReader _saxReader;

}