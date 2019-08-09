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

import com.liferay.adaptive.media.configuration.AMSystemImagesConfiguration;
import com.liferay.adaptive.media.exception.AMImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PrefsPropsUtil;

import java.io.IOException;

import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
@Component(
	immediate = true, service = AMCompanyThumbnailConfigurationInitializer.class
)
public class AMCompanyThumbnailConfigurationInitializer {

	public void initializeCompany(Company company) throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		int dlFileEntryPreviewMaxHeight = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT);
		int dlFileEntryPreviewMaxWidth = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH);

		if ((dlFileEntryPreviewMaxHeight > 0) ||
			(dlFileEntryPreviewMaxWidth > 0)) {

			AMImageConfigurationEntry previewAMConfigurationEntry =
				_createAMDocumentLibraryPreviewConfiguration(
					company, dlFileEntryPreviewMaxHeight,
					dlFileEntryPreviewMaxWidth);

			if (previewAMConfigurationEntry != null) {
				properties.put(
					"previewlAMConfiguration",
					previewAMConfigurationEntry.getUUID());
			}
		}

		int dlFileEntryThumbnailMaxHeight = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT);
		int dlFileEntryThumbnailMaxWidth = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH);

		if ((dlFileEntryThumbnailMaxHeight > 0) &&
			(dlFileEntryThumbnailMaxWidth > 0)) {

			AMImageConfigurationEntry thumbnailAMConfigurationEntry =
				_createAMDocumentLibraryThumbnailConfiguration(
					company, dlFileEntryThumbnailMaxHeight,
					dlFileEntryThumbnailMaxWidth);

			if (thumbnailAMConfigurationEntry != null) {
				properties.put(
					"thumbnailAMConfiguration",
					thumbnailAMConfigurationEntry.getUUID());
			}
		}

		int dlFileEntryThumbnailCustom1MaxHeight = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT);
		int dlFileEntryThumbnailCustom1MaxWidth = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH);

		if ((dlFileEntryThumbnailCustom1MaxHeight > 0) &&
			(dlFileEntryThumbnailCustom1MaxWidth > 0)) {

			AMImageConfigurationEntry thumbnailCustom1AMConfigurationEntry =
				_createAMDocumentLibraryThumbnailConfiguration(
					company, dlFileEntryThumbnailCustom1MaxHeight,
					dlFileEntryThumbnailCustom1MaxWidth);

			if (thumbnailCustom1AMConfigurationEntry != null) {
				properties.put(
					"thumbnailCustom1AMConfiguration",
					thumbnailCustom1AMConfigurationEntry.getUUID());
			}
		}

		int dlFileEntryThumbnailCustom2MaxHeight = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_HEIGHT);
		int dlFileEntryThumbnailCustom2MaxWidth = PrefsPropsUtil.getInteger(
			PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_WIDTH);

		if ((dlFileEntryThumbnailCustom2MaxHeight > 0) &&
			(dlFileEntryThumbnailCustom2MaxWidth > 0)) {

			AMImageConfigurationEntry thumbnailCustom2AMConfigurationEntry =
				_createAMDocumentLibraryThumbnailConfiguration(
					company, dlFileEntryThumbnailCustom2MaxHeight,
					dlFileEntryThumbnailCustom2MaxWidth);

			if (thumbnailCustom2AMConfigurationEntry != null) {
				properties.put(
					"thumbnailCustom2AMConfiguration",
					thumbnailCustom2AMConfigurationEntry.getUUID());
			}
		}

		if (!properties.isEmpty()) {
			_writeConfigurations(properties);
		}
	}

	private AMImageConfigurationEntry _createAMDocumentLibraryConfiguration(
			Company company, String name, int maxHeight, int maxWidth)
		throws AMImageConfigurationException, IOException {

		String uuid = _normalize(name);

		if (!_hasConfiguration(company.getCompanyId(), name, uuid)) {
			Map<String, String> properties = new HashMap<>();

			properties.put("max-height", String.valueOf(maxHeight));
			properties.put("max-width", String.valueOf(maxWidth));

			return _amImageConfigurationHelper.addAMImageConfigurationEntry(
				company.getCompanyId(), name,
				"This image resolution was automatically added.", uuid,
				properties);
		}

		return null;
	}

	private AMImageConfigurationEntry
			_createAMDocumentLibraryPreviewConfiguration(
				Company company, int maxHeight, int maxWidth)
		throws AMImageConfigurationException, IOException {

		String name = String.format("%s %dx%d", "Preview", maxWidth, maxHeight);

		return _createAMDocumentLibraryConfiguration(
			company, name, maxHeight, maxWidth);
	}

	private AMImageConfigurationEntry
			_createAMDocumentLibraryThumbnailConfiguration(
				Company company, int maxHeight, int maxWidth)
		throws AMImageConfigurationException, IOException {

		String name = String.format(
			"%s %dx%d", "Thumbnail", maxWidth, maxHeight);

		return _createAMDocumentLibraryConfiguration(
			company, name, maxHeight, maxWidth);
	}

	private boolean _hasConfiguration(
		long companyId, String name, String uuid) {

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				companyId, amImageConfigurationEntry -> true);

		Stream<AMImageConfigurationEntry> amImageConfigurationEntryStream =
			amImageConfigurationEntries.stream();

		Optional<AMImageConfigurationEntry>
			duplicateNameAMImageConfigurationEntryOptional =
				amImageConfigurationEntryStream.filter(
					amImageConfigurationEntry ->
						name.equals(amImageConfigurationEntry.getName()) ||
						uuid.equals(amImageConfigurationEntry.getUUID())
				).findFirst();

		return duplicateNameAMImageConfigurationEntryOptional.isPresent();
	}

	private String _normalize(String str) {
		Matcher matcher = _pattern.matcher(str);

		return matcher.replaceAll(StringPool.DASH);
	}

	private void _writeConfigurations(Dictionary<String, Object> properties)
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			AMSystemImagesConfiguration.class.getName(), StringPool.QUESTION);

		configuration.update(properties);

		PortletPreferences portletPreferences = _prefsProps.getPreferences();

		portletPreferences.store();
	}

	private static final Pattern _pattern = Pattern.compile("[^\\w-]");

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private PrefsProps _prefsProps;

}