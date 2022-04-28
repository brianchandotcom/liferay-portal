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

package com.liferay.translation.web.internal.servlet;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterTracker;
import com.liferay.translation.web.internal.helper.InfoItemHelper;
import com.liferay.translation.web.internal.helper.TranslationRequestHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.translation.web.internal.servlet.ExportTranslationServlet",
		"osgi.http.whiteboard.servlet.pattern=/translation/export_translation",
		"servlet.init.httpMethods=GET"
	},
	service = Servlet.class
)
public class ExportTranslationServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			long[] segmentsExperienceIds = ParamUtil.getLongValues(
				httpServletRequest, "segmentsExperienceIds");

			TranslationRequestHelper translationRequestHelper =
				new TranslationRequestHelper(
					httpServletRequest, _infoItemServiceTracker,
					_segmentsExperienceLocalService);

			String className = translationRequestHelper.getClassName(
				segmentsExperienceIds);

			String exportMimeType = ParamUtil.getString(
				httpServletRequest, "exportMimeType");
			String sourceLanguageId = ParamUtil.getString(
				httpServletRequest, "sourceLanguageId");
			String[] targetLanguageIds = ParamUtil.getStringValues(
				httpServletRequest, "targetLanguageIds");

			ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

			Set<Long> classPKs = SetUtil.fromArray(
				_getClassPKs(
					className, segmentsExperienceIds,
					translationRequestHelper));

			for (long classPK : classPKs) {
				_addZipEntry(
					zipWriter, className, classPK, exportMimeType,
					sourceLanguageId, targetLanguageIds,
					_portal.getLocale(httpServletRequest));
			}

			try (InputStream inputStream = new FileInputStream(
					zipWriter.getFile())) {

				ServletResponseUtil.sendFile(
					httpServletRequest, httpServletResponse,
					_getZipFileName(
						translationRequestHelper.getModelClassName(),
						translationRequestHelper.getModelClassPK(),
						LanguageUtil.get(
							_portal.getLocale(httpServletRequest),
							"model.resource." + className),
						_isMultipleModels(
							translationRequestHelper.getModelClassPKs()),
						sourceLanguageId,
						_portal.getLocale(httpServletRequest)),
					inputStream, ContentTypes.APPLICATION_ZIP);
			}
		}
		catch (PortalException portalException) {
			throw new IOException(portalException);
		}
	}

	private void _addZipEntry(
			ZipWriter zipWriter, String className, long classPK,
			String exportMimeType, String sourceLanguageId,
			String[] targetLanguageIds, Locale locale)
		throws IOException, PortalException {

		InfoItemHelper infoItemHelper = new InfoItemHelper(
			className, _infoItemServiceTracker);

		Optional<String> infoItemTitleOptional =
			infoItemHelper.getInfoItemTitleOptional(classPK, locale);

		String infoItemTitle = infoItemTitleOptional.orElseGet(
			() ->
				LanguageUtil.get(locale, "model.resource." + className) +
					StringPool.SPACE + classPK);

		Optional<TranslationInfoItemFieldValuesExporter>
			exportFileFormatOptional =
				_translationInfoItemFieldValuesExporterTracker.
					getTranslationInfoItemFieldValuesExporterOptional(
						exportMimeType);

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, className);

		Object object = infoItemObjectProvider.getInfoItem(classPK);

		TranslationInfoItemFieldValuesExporter
			translationInfoItemFieldValuesExporter =
				exportFileFormatOptional.orElseThrow(
					() -> new PortalException(
						"Unknown export mime type: " + exportMimeType));

		for (String targetLanguageId : targetLanguageIds) {
			zipWriter.addEntry(
				_getXLIFFFileName(
					infoItemTitle, sourceLanguageId, targetLanguageId),
				translationInfoItemFieldValuesExporter.
					exportInfoItemFieldValues(
						infoItemFieldValuesProvider.getInfoItemFieldValues(
							object),
						LocaleUtil.fromLanguageId(sourceLanguageId),
						LocaleUtil.fromLanguageId(targetLanguageId)));
		}
	}

	private long[] _getClassPKs(
			String className, long[] segmentsExperienceIds,
			TranslationRequestHelper translationRequestHelper)
		throws PortalException {

		if (!className.equals(Layout.class.getName())) {
			return translationRequestHelper.getClassPKs(segmentsExperienceIds);
		}

		long[] classPKs = translationRequestHelper.getClassPKs(
			segmentsExperienceIds);

		long[] replacementClassPKs = new long[classPKs.length];

		for (int i = 0; i < classPKs.length; i++) {
			replacementClassPKs[i] = _getDraftLayoutPlid(classPKs[i]);
		}

		return replacementClassPKs;
	}

	private long _getDraftLayoutPlid(long plid) {
		Layout layout = _layoutLocalService.fetchLayout(plid);

		if ((layout == null) || layout.isDraftLayout()) {
			return plid;
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout == null) {
			return plid;
		}

		return draftLayout.getPlid();
	}

	private String _getPrefixName(
		long classPK, String classNameTitle,
		Optional<String> infoItemTitleOptional, boolean multipleModels,
		Locale locale) {

		if (multipleModels) {
			return classNameTitle + StringPool.SPACE +
				LanguageUtil.get(locale, "translations");
		}

		return infoItemTitleOptional.orElseGet(
			() -> classNameTitle + StringPool.SPACE + classPK);
	}

	private String _getXLIFFFileName(
			String title, String sourceLanguageId, String targetLanguageId)
		throws PortalException {

		return StringBundler.concat(
			StringPool.FORWARD_SLASH,
			StringUtil.removeSubstrings(title, PropsValues.DL_CHAR_BLACKLIST),
			StringPool.DASH, sourceLanguageId, StringPool.DASH,
			targetLanguageId, ".xlf");
	}

	private String _getZipFileName(
			String className, long classPK, String classNameTitle,
			boolean multipleModels, String sourceLanguageId, Locale locale)
		throws NoSuchInfoItemException {

		InfoItemHelper infoItemHelper = new InfoItemHelper(
			className, _infoItemServiceTracker);

		Optional<String> infoItemTitleOptional =
			infoItemHelper.getInfoItemTitleOptional(classPK, locale);

		return StringBundler.concat(
			StringUtil.removeSubstrings(
				_getPrefixName(
					classPK, classNameTitle, infoItemTitleOptional,
					multipleModels, locale),
				PropsValues.DL_CHAR_BLACKLIST),
			StringPool.DASH, sourceLanguageId, ".zip");
	}

	private boolean _isMultipleModels(long[] classPKs) {
		if (classPKs.length > 1) {
			return true;
		}

		return false;
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private TranslationInfoItemFieldValuesExporterTracker
		_translationInfoItemFieldValuesExporterTracker;

}