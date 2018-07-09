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

package com.liferay.adaptive.media.document.library.thumbnails.internal.util;

import com.liferay.adaptive.media.exception.AMImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PrefsPropsUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, service = AMCompanyThumbnailConfigurationInitializer.class
)
public class AMCompanyThumbnailConfigurationInitializer {

	public void initializeCompany(Company company)
		throws AMImageConfigurationException, IOException {

		int dlFileEntryPreviewMaxHeight = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT);
		int dlFileEntryPreviewMaxWidth = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH);

		if ((dlFileEntryPreviewMaxHeight > 0) ||
			(dlFileEntryPreviewMaxWidth > 0)) {

			_createAMDocumentLibraryPreviewConfiguration(
				company, dlFileEntryPreviewMaxHeight,
				dlFileEntryPreviewMaxWidth);
		}

		int dlFileEntryThumbnailMaxHeight = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT);
		int dlFileEntryThumbnailMaxWidth = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH);

		if ((dlFileEntryThumbnailMaxHeight > 0) &&
			(dlFileEntryThumbnailMaxWidth > 0)) {

			_createAMDocumentLibraryThumbnailConfiguration(
				company, dlFileEntryThumbnailMaxHeight,
				dlFileEntryThumbnailMaxWidth);
		}

		int dlFileEntryThumbnailCustom1MaxHeight = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT);
		int dlFileEntryThumbnailCustom1MaxWidth = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH);

		if ((dlFileEntryThumbnailCustom1MaxHeight > 0) &&
			(dlFileEntryThumbnailCustom1MaxWidth > 0)) {

			_createAMDocumentLibraryThumbnailConfiguration(
				company, dlFileEntryThumbnailCustom1MaxHeight,
				dlFileEntryThumbnailCustom1MaxWidth);
		}

		int dlFileEntryThumbnailCustom2MaxHeight = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_HEIGHT);
		int dlFileEntryThumbnailCustom2MaxWidth = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_WIDTH);

		if ((dlFileEntryThumbnailCustom2MaxHeight > 0) &&
			(dlFileEntryThumbnailCustom2MaxWidth > 0)) {

			_createAMDocumentLibraryThumbnailConfiguration(
				company, dlFileEntryThumbnailCustom2MaxHeight,
				dlFileEntryThumbnailCustom2MaxWidth);
		}
	}

	private void _createAMDocumentLibraryPreviewConfiguration(
			Company company, int maxHeight, int maxWidth)
		throws AMImageConfigurationException, IOException {

		String name = String.format("%s %dx%d", "Preview", maxWidth, maxHeight);

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", String.valueOf(maxHeight));
		properties.put("max-width", String.valueOf(maxWidth));

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			company.getCompanyId(), name,
			"This image resolution was automatically added.", name, properties);
	}

	private void _createAMDocumentLibraryThumbnailConfiguration(
			Company company, int maxHeight, int maxWidth)
		throws AMImageConfigurationException, IOException {

		String name = String.format(
			"%s %dx%d", "Thumbnail", maxWidth, maxHeight);

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", String.valueOf(maxHeight));
		properties.put("max-width", String.valueOf(maxWidth));

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			company.getCompanyId(), name,
			"This image resolution was automatically added.", name, properties);
	}

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

}