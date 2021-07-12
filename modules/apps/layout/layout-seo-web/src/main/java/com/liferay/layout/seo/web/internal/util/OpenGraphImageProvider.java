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

package com.liferay.layout.seo.web.internal.util;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.WebImage;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.model.LayoutSEOSite;
import com.liferay.layout.seo.service.LayoutSEOSiteLocalService;
import com.liferay.layout.seo.template.LayoutSEOTemplateProcessor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Alejandro Tardín
 */
public class OpenGraphImageProvider {

	public OpenGraphImageProvider(
		DDMStructureLocalService ddmStructureLocalService,
		DLAppLocalService dlAppLocalService,
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService,
		DLURLHelper dlurlHelper,
		LayoutSEOSiteLocalService layoutSEOSiteLocalService,
		LayoutSEOTemplateProcessor layoutSEOTemplateProcessor, Portal portal,
		StorageEngine storageEngine) {

		_dlAppLocalService = dlAppLocalService;
		_dlurlHelper = dlurlHelper;
		_layoutSEOSiteLocalService = layoutSEOSiteLocalService;
		_layoutSEOTemplateProcessor = layoutSEOTemplateProcessor;

		_fileEntryMetadataOpenGraphTagsProvider =
			new FileEntryMetadataOpenGraphTagsProvider(
				ddmStructureLocalService, dlFileEntryMetadataLocalService,
				portal, storageEngine);
	}

	public Optional<OpenGraphImage> getOpenGraphImageOptional(
		InfoItemFieldValues infoItemFieldValues, Layout layout,
		LayoutSEOEntry layoutSEOEntry, ThemeDisplay themeDisplay) {

		return _getMappedOpenGraphImageOptional(
			infoItemFieldValues, layout, layoutSEOEntry, themeDisplay
		).map(
			Optional::of
		).orElseGet(
			() -> _getFileEntryOpenGraphImageOptional(
				infoItemFieldValues, layout, layoutSEOEntry, themeDisplay)
		);
	}

	public interface OpenGraphImage {

		public Optional<String> getAltOptional();

		public Iterable<KeyValuePair> getMetadataTagKeyValuePairs();

		public Optional<String> getMimeTypeOptional();

		public String getUrl();

	}

	private String _getAbsoluteURL(ThemeDisplay themeDisplay, String url) {
		if (url.startsWith("http")) {
			return url;
		}

		return themeDisplay.getPortalURL() + url;
	}

	private Optional<OpenGraphImage> _getFileEntryOpenGraphImageOptional(
		InfoItemFieldValues infoItemFieldValues, Layout layout,
		LayoutSEOEntry layoutSEOEntry, ThemeDisplay themeDisplay) {

		try {
			long openGraphImageFileEntryId = _getOpenGraphImageFileEntryId(
				layout, layoutSEOEntry);

			if (openGraphImageFileEntryId != 0) {
				FileEntry fileEntry = _dlAppLocalService.getFileEntry(
					openGraphImageFileEntryId);

				Iterable<KeyValuePair>
					fileEntryMetadataOpenGraphTagKeyValuePairs =
						_fileEntryMetadataOpenGraphTagsProvider.
							getFileEntryMetadataOpenGraphTagKeyValuePairs(
								fileEntry);

				String imagePreviewURL = _dlurlHelper.getImagePreviewURL(
					fileEntry, themeDisplay);

				return Optional.of(
					new OpenGraphImage() {

						@Override
						public Optional<String> getAltOptional() {
							return Optional.ofNullable(
								_getImageAltTagValue(
									infoItemFieldValues, layout, layoutSEOEntry,
									themeDisplay.getLocale()));
						}

						@Override
						public Iterable<KeyValuePair>
							getMetadataTagKeyValuePairs() {

							return fileEntryMetadataOpenGraphTagKeyValuePairs;
						}

						@Override
						public Optional<String> getMimeTypeOptional() {
							return Optional.of(fileEntry.getMimeType());
						}

						@Override
						public String getUrl() {
							return imagePreviewURL;
						}

					});
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return Optional.empty();
	}

	private String _getImageAltTagValue(
		InfoItemFieldValues infoItemFieldValues, Layout layout,
		LayoutSEOEntry layoutSEOEntry, Locale locale) {

		String mappedImageAltTagValue = _getMappedStringValue(
			null, "openGraphImageAlt", infoItemFieldValues, layout, locale);

		if (Validator.isNotNull(mappedImageAltTagValue)) {
			return mappedImageAltTagValue;
		}

		if ((layoutSEOEntry != null) &&
			(layoutSEOEntry.getOpenGraphImageFileEntryId() > 0)) {

			return layoutSEOEntry.getOpenGraphImageAlt(locale);
		}

		LayoutSEOSite layoutSEOSite =
			_layoutSEOSiteLocalService.fetchLayoutSEOSiteByGroupId(
				layout.getGroupId());

		if ((layoutSEOSite != null) &&
			(layoutSEOSite.getOpenGraphImageFileEntryId() > 0)) {

			return layoutSEOSite.getOpenGraphImageAlt(locale);
		}

		String imageAltMappingFieldKey = layout.getTypeSettingsProperty(
			"mapped-openGraphImageAlt", null);

		if (Validator.isNotNull(imageAltMappingFieldKey)) {
			return _layoutSEOTemplateProcessor.processTemplate(
				imageAltMappingFieldKey, infoItemFieldValues, locale);
		}

		return null;
	}

	private Optional<OpenGraphImage> _getMappedOpenGraphImageOptional(
		InfoItemFieldValues infoItemFieldValues, Layout layout,
		LayoutSEOEntry layoutSEOEntry, ThemeDisplay themeDisplay) {

		Object mappedImageObject = _getMappedValue(
			null, "openGraphImage", infoItemFieldValues, layout,
			themeDisplay.getLocale());

		if (mappedImageObject instanceof WebImage) {
			WebImage mappedWebImage = (WebImage)mappedImageObject;

			return Optional.of(
				new OpenGraphImage() {

					@Override
					public Optional<String> getAltOptional() {
						String openGraphImageAlt = _getImageAltTagValue(
							infoItemFieldValues, layout, layoutSEOEntry,
							themeDisplay.getLocale());

						if (Validator.isNotNull(openGraphImageAlt)) {
							return Optional.of(openGraphImageAlt);
						}

						Optional<InfoLocalizedValue<String>>
							altInfoLocalizedValueOptional =
								mappedWebImage.
									getAltInfoLocalizedValueOptional();

						return altInfoLocalizedValueOptional.map(
							altInfoLocalizedValue ->
								altInfoLocalizedValue.getValue(
									themeDisplay.getLocale()));
					}

					@Override
					public Iterable<KeyValuePair>
						getMetadataTagKeyValuePairs() {

						return Collections.emptyList();
					}

					@Override
					public Optional<String> getMimeTypeOptional() {
						return Optional.empty();
					}

					@Override
					public String getUrl() {
						return _getAbsoluteURL(
							themeDisplay, mappedWebImage.getUrl());
					}

				});
		}

		return Optional.empty();
	}

	private String _getMappedStringValue(
		String defaultFieldName, String fieldName,
		InfoItemFieldValues infoItemFieldValues, Layout layout, Locale locale) {

		Object mappedValueObject = _getMappedValue(
			defaultFieldName, fieldName, infoItemFieldValues, layout, locale);

		if (mappedValueObject != null) {
			return String.valueOf(mappedValueObject);
		}

		return null;
	}

	private Object _getMappedValue(
		String defaultFieldName, String fieldName,
		InfoItemFieldValues infoItemFieldValues, Layout layout, Locale locale) {

		if (infoItemFieldValues == null) {
			return null;
		}

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue(
				layout.getTypeSettingsProperty(
					"mapped-" + fieldName, defaultFieldName));

		if (infoFieldValue != null) {
			return infoFieldValue.getValue(locale);
		}

		return null;
	}

	private long _getOpenGraphImageFileEntryId(
		Layout layout, LayoutSEOEntry layoutSEOEntry) {

		if ((layoutSEOEntry != null) &&
			(layoutSEOEntry.getOpenGraphImageFileEntryId() > 0)) {

			return layoutSEOEntry.getOpenGraphImageFileEntryId();
		}

		LayoutSEOSite layoutSEOSite =
			_layoutSEOSiteLocalService.fetchLayoutSEOSiteByGroupId(
				layout.getGroupId());

		if ((layoutSEOSite == null) ||
			(layoutSEOSite.getOpenGraphImageFileEntryId() == 0)) {

			return 0;
		}

		return layoutSEOSite.getOpenGraphImageFileEntryId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenGraphImageProvider.class);

	private final DLAppLocalService _dlAppLocalService;
	private final DLURLHelper _dlurlHelper;
	private final FileEntryMetadataOpenGraphTagsProvider
		_fileEntryMetadataOpenGraphTagsProvider;
	private final LayoutSEOSiteLocalService _layoutSEOSiteLocalService;
	private final LayoutSEOTemplateProcessor _layoutSEOTemplateProcessor;

}