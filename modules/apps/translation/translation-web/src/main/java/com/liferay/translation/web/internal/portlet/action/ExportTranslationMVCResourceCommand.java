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

package com.liferay.translation.web.internal.portlet.action;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterTracker;
import com.liferay.translation.web.internal.helper.InfoItemHelper;
import com.liferay.translation.web.internal.helper.TranslationRequestHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Locale;
import java.util.Optional;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + TranslationPortletKeys.TRANSLATION,
		"mvc.command.name=/translation/export_translation"
	},
	service = MVCResourceCommand.class
)
public class ExportTranslationMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			long[] segmentsExperienceIds = ParamUtil.getLongValues(
				resourceRequest, "segmentsExperienceIds");

			TranslationRequestHelper translationRequestHelper =
				new TranslationRequestHelper(
					_infoItemServiceTracker, resourceRequest);

			String className = translationRequestHelper.getClassName(
				segmentsExperienceIds);

			String exportMimeType = ParamUtil.getString(
				resourceRequest, "exportMimeType");
			String sourceLanguageId = ParamUtil.getString(
				resourceRequest, "sourceLanguageId");
			String[] targetLanguageIds = ParamUtil.getStringValues(
				resourceRequest, "targetLanguageIds");

			ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

			for (long classPK :
					_getClassPKs(
						className, segmentsExperienceIds,
						translationRequestHelper)) {

				if ((classPK == SegmentsExperienceConstants.ID_DEFAULT) &&
					className.equals(SegmentsExperience.class.getName())) {

					_addZipEntry(
						zipWriter, translationRequestHelper.getModelClassName(),
						translationRequestHelper.getModelClassPK(),
						exportMimeType, sourceLanguageId, targetLanguageIds,
						_portal.getLocale(resourceRequest));
				}
				else {
					_addZipEntry(
						zipWriter, className, classPK, exportMimeType,
						sourceLanguageId, targetLanguageIds,
						_portal.getLocale(resourceRequest));
				}
			}

			try (InputStream inputStream = new FileInputStream(
					zipWriter.getFile())) {

				PortletResponseUtil.sendFile(
					resourceRequest, resourceResponse,
					_getZipFileName(
						translationRequestHelper.getModelClassName(),
						translationRequestHelper.getModelClassPK(),
						sourceLanguageId, _portal.getLocale(resourceRequest)),
					inputStream, ContentTypes.APPLICATION_ZIP);
			}

			return false;
		}
		catch (IOException | PortalException exception) {
			throw new PortletException(exception);
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
			String className, long classPK, String sourceLanguageId,
			Locale locale)
		throws NoSuchInfoItemException {

		InfoItemHelper infoItemHelper = new InfoItemHelper(
			className, _infoItemServiceTracker);

		Optional<String> infoItemTitleOptional =
			infoItemHelper.getInfoItemTitleOptional(classPK, locale);

		String infoItemTitle = infoItemTitleOptional.orElseGet(
			() ->
				LanguageUtil.get(locale, "model.resource." + className) +
					StringPool.SPACE + classPK);

		String escapedTitle = StringUtil.removeSubstrings(
			infoItemTitle, PropsValues.DL_CHAR_BLACKLIST);

		return StringBundler.concat(
			escapedTitle, StringPool.DASH, sourceLanguageId, ".zip");
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