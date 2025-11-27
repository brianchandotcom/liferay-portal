/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.manager;

import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterRegistry;
import com.liferay.translation.internal.helper.InfoItemHelper;
import com.liferay.translation.manager.TranslationManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = TranslationManager.class)
public class TranslationManagerImpl implements TranslationManager {

	@Override
	public void addZipEntry(
			ZipWriter zipWriter, String className, long classPK,
			String exportMimeType, String sourceLanguageId,
			String[] targetLanguageIds, Locale locale)
		throws IOException, PortalException {

		for (String targetLanguageId : targetLanguageIds) {
			zipWriter.addEntry(
				getXLIFFFileName(
					className, classPK, sourceLanguageId, targetLanguageId,
					locale),
				getXLIFFInputStream(
					className, classPK, exportMimeType, sourceLanguageId,
					targetLanguageId));
		}
	}

	@Override
	public File exportXLIFFZipFile(
			String className, long classPK, String exportMimeType,
			Locale locale, String sourceLanguageId, String[] targetLanguageIds,
			User user)
		throws IOException, PortalException {

		ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

		addZipEntry(
			zipWriter, className, classPK, exportMimeType, sourceLanguageId,
			targetLanguageIds, locale);

		return zipWriter.getFile();
	}

	@Override
	public String getInfoItemTitle(
		String className, long classPK, Locale locale) {

		InfoItemHelper infoItemHelper = new InfoItemHelper(
			className, _infoItemServiceRegistry);

		return infoItemHelper.getInfoItemTitle(classPK, locale);
	}

	@Override
	public String getXLIFFFileName(
		String className, long classPK, String sourceLanguageId,
		String targetLanguageId, Locale locale) {

		String title = getInfoItemTitle(className, classPK, locale);

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

	@Override
	public InputStream getXLIFFInputStream(
			String className, long classPK, String exportMimeType,
			String sourceLanguageId, String targetLanguageId)
		throws IOException, PortalException {

		TranslationInfoItemFieldValuesExporter
			translationInfoItemFieldValuesExporter =
				_translationInfoItemFieldValuesExporterRegistry.
					getTranslationInfoItemFieldValuesExporter(exportMimeType);

		if (translationInfoItemFieldValuesExporter == null) {
			throw new PortalException(
				"Unknown export mime type: " + exportMimeType);
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

	@Override
	public String getZipFileName(
		String className, long classPK, String classNameTitle,
		boolean multipleModels, String sourceLanguageId, Locale locale) {

		String infoItemTitle = getInfoItemTitle(className, classPK, locale);

		return StringBundler.concat(
			StringUtil.removeSubstrings(
				_getPrefixName(
					classPK, classNameTitle, infoItemTitle, multipleModels,
					locale),
				PropsValues.DL_CHAR_BLACKLIST),
			StringPool.DASH, sourceLanguageId, ".zip");
	}

	private String _getPrefixName(
		long classPK, String classNameTitle, String infoItemTitle,
		boolean multipleModels, Locale locale) {

		if (multipleModels) {
			return classNameTitle + StringPool.SPACE +
				_language.get(locale, "translations");
		}

		if (infoItemTitle != null) {
			return infoItemTitle;
		}

		return classNameTitle + StringPool.SPACE + classPK;
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private Language _language;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private TranslationInfoItemFieldValuesExporterRegistry
		_translationInfoItemFieldValuesExporterRegistry;

	@Reference
	private ZipWriterFactory _zipWriterFactory;

}