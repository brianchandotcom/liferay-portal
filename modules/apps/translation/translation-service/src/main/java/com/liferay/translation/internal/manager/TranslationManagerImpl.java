/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.manager;

import com.liferay.document.library.kernel.exception.FileMimeTypeException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterRegistry;
import com.liferay.translation.internal.helper.InfoItemHelper;
import com.liferay.translation.manager.TranslationManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = TranslationManager.class)
public class TranslationManagerImpl implements TranslationManager {

	@Override
	public String getEntryTitle(String className, long classPK, Locale locale) {
		InfoItemHelper infoItemHelper = new InfoItemHelper(
			className, _infoItemServiceRegistry);

		return infoItemHelper.getInfoItemTitle(classPK, locale);
	}

	@Override
	public File getXLIFFFile(
			String className, long classPK, String xliffMimeType, Locale locale,
			String sourceLanguageId, String targetLanguageId)
		throws IOException, PortalException {

		String fileName = _getXLIFFFileName(
			className, classPK, sourceLanguageId, targetLanguageId, locale);

		File file = new File(_createTempDirectory(), fileName);

		try (OutputStream outputStream = new FileOutputStream(file)) {
			StreamUtil.transfer(
				_getXLIFFInputStream(
					className, classPK, xliffMimeType, sourceLanguageId,
					targetLanguageId),
				outputStream);
		}

		return file;
	}

	@Override
	public File getXLIFFZipFile(
			String className, long[] classPKs, String xliffMimeType,
			Locale locale, String sourceLanguageId, String[] targetLanguageIds)
		throws IOException, PortalException {

		String fileName = _getZipFileName(
			className, classPKs, sourceLanguageId, locale);

		ZipWriter zipWriter = _zipWriterFactory.getZipWriter(
			new File(_createTempDirectory(), fileName));

		for (long classPK : classPKs) {
			_addZipEntry(
				zipWriter, className, classPK, xliffMimeType, sourceLanguageId,
				targetLanguageIds, locale);
		}

		return zipWriter.getFile();
	}

	private void _addZipEntry(
			ZipWriter zipWriter, String className, long classPK,
			String xliffMimeType, String sourceLanguageId,
			String[] targetLanguageIds, Locale locale)
		throws IOException, PortalException {

		for (String targetLanguageId : targetLanguageIds) {
			zipWriter.addEntry(
				_getXLIFFFileName(
					className, classPK, sourceLanguageId, targetLanguageId,
					locale),
				_getXLIFFInputStream(
					className, classPK, xliffMimeType, sourceLanguageId,
					targetLanguageId));
		}
	}

	private File _createTempDirectory() throws IOException {
		File directory = FileUtil.createTempFile();

		if (!directory.mkdir()) {
			throw new IOException(
				"Unable to create directory " + directory.getPath());
		}

		return directory;
	}

	private String _getPrefixName(
		String className, long[] classPKs, Locale locale) {

		if (classPKs.length > 1) {
			String title = _language.get(locale, "model.resource." + className);

			return title + StringPool.SPACE +
				_language.get(locale, "translations");
		}

		String entryTitle = getEntryTitle(className, classPKs[0], locale);

		if (entryTitle != null) {
			return entryTitle;
		}

		String title = _language.get(locale, "model.resource." + className);

		return title + StringPool.SPACE + classPKs[0];
	}

	private String _getXLIFFFileName(
		String className, long classPK, String sourceLanguageId,
		String targetLanguageId, Locale locale) {

		String title = getEntryTitle(className, classPK, locale);

		if (title == null) {
			title =
				_language.get(locale, "model.resource." + className) +
					StringPool.SPACE + classPK;
		}

		return StringBundler.concat(
			StringPool.FORWARD_SLASH,
			StringUtil.removeSubstrings(title, PropsValues.DL_CHAR_BLACKLIST),
			StringPool.DASH, sourceLanguageId, StringPool.DASH,
			targetLanguageId, ".xlf");
	}

	private InputStream _getXLIFFInputStream(
			String className, long classPK, String xliffMimeType,
			String sourceLanguageId, String targetLanguageId)
		throws IOException, PortalException {

		if (Validator.isBlank(xliffMimeType)) {
			throw new FileMimeTypeException("Unknown xliff mime type");
		}

		TranslationInfoItemFieldValuesExporter
			translationInfoItemFieldValuesExporter =
				_translationInfoItemFieldValuesExporterRegistry.
					getTranslationInfoItemFieldValuesExporter(xliffMimeType);

		if (translationInfoItemFieldValuesExporter == null) {
			throw new FileMimeTypeException(
				"Unknown xliff mime type: " + xliffMimeType);
		}

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class, className,
				ClassPKInfoItemIdentifier.INFO_ITEM_SERVICE_FILTER);

		Object object = infoItemObjectProvider.getInfoItem(
			new ClassPKInfoItemIdentifier(classPK));

		return translationInfoItemFieldValuesExporter.exportInfoItemFieldValues(
			infoItemFieldValuesProvider.getInfoItemFieldValues(object),
			LocaleUtil.fromLanguageId(sourceLanguageId),
			LocaleUtil.fromLanguageId(targetLanguageId));
	}

	private String _getZipFileName(
		String className, long[] classPKs, String sourceLanguageId,
		Locale locale) {

		return StringBundler.concat(
			StringUtil.removeSubstrings(
				_getPrefixName(className, classPKs, locale),
				PropsValues.DL_CHAR_BLACKLIST),
			StringPool.DASH, sourceLanguageId, ".zip");
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private Language _language;

	@Reference
	private TranslationInfoItemFieldValuesExporterRegistry
		_translationInfoItemFieldValuesExporterRegistry;

	@Reference
	private ZipWriterFactory _zipWriterFactory;

}