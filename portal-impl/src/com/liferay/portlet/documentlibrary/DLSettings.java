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

package com.liferay.portlet.documentlibrary;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portlet.documentlibrary.util.DLConstants;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class DLSettings {

	public static final String[] ALL_KEYS = {
		"emailFileEntryAddedBody", "emailFileEntryAddedSubject",
		"emailFileEntryUpdatedBody", "emailFileEntryUpdatedSubject",
		"emailFromAddress", "emailFromName", "emailFileEntryAddedEnabled",
		"emailFileEntryUpdatedEnabled", "showHiddenMountFolders"
	};

	public static DLSettings getInstance(long groupId) throws PortalException {
		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			groupId, DLConstants.SERVICE_NAME);

		return new DLSettings(settings);
	}

	public static DLSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			groupId, DLConstants.SERVICE_NAME);

		Settings parameterMapSettings = new ParameterMapSettings(
			parameterMap, settings);

		return new DLSettings(parameterMapSettings);
	}

	public DLSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public LocalizedValuesMap getEmailFileEntryAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailFileEntryAddedBody");
	}

	public String getEmailFileEntryAddedBodyXml() {
		LocalizedValuesMap emailFileEntryAddedBody =
			getEmailFileEntryAddedBody();

		return emailFileEntryAddedBody.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailFileEntryAddedSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryAddedSubject");
	}

	public String getEmailFileEntryAddedSubjectXml() {
		LocalizedValuesMap emailFileEntryAddedSubject =
			getEmailFileEntryAddedSubject();

		return emailFileEntryAddedSubject.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailFileEntryUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryUpdatedBody");
	}

	public String getEmailFileEntryUpdatedBodyXml() {
		LocalizedValuesMap emailFileEntryUpdatedBody =
			getEmailFileEntryUpdatedBody();

		return emailFileEntryUpdatedBody.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailFileEntryUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryUpdatedSubject");
	}

	public String getEmailFileEntryUpdatedSubjectXml() {
		LocalizedValuesMap emailFileEntryUpdatedSubject =
			getEmailFileEntryUpdatedSubject();

		return emailFileEntryUpdatedSubject.getLocalizationXml();
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	public boolean isEmailFileEntryAddedEnabled() {
		return _typedSettings.getBooleanValue("emailFileEntryAddedEnabled");
	}

	public boolean isEmailFileEntryUpdatedEnabled() {
		return _typedSettings.getBooleanValue("emailFileEntryUpdatedEnabled");
	}

	public boolean isShowHiddenMountFolders() {
		return _typedSettings.getBooleanValue("showHiddenMountFolders");
	}

	private static FallbackKeys _getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add(
			"emailFileEntryAddedBody",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_BODY);
		fallbackKeys.add(
			"emailFileEntryAddedEnabled",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_ENABLED);
		fallbackKeys.add(
			"emailFileEntryAddedSubject",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_SUBJECT);
		fallbackKeys.add(
			"emailFileEntryUpdatedBody",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_BODY);
		fallbackKeys.add(
			"emailFileEntryUpdatedEnabled",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_ENABLED);
		fallbackKeys.add(
			"emailFileEntryUpdatedSubject",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_SUBJECT);
		fallbackKeys.add(
			"emailFromAddress", PropsKeys.DL_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		fallbackKeys.add(
			"emailFromName", PropsKeys.DL_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		fallbackKeys.add(
			"enableCommentRatings", PropsKeys.DL_COMMENT_RATINGS_ENABLED);
		fallbackKeys.add("enableRatings", PropsKeys.DL_RATINGS_ENABLED);
		fallbackKeys.add(
			"enableRelatedAssets", PropsKeys.DL_RELATED_ASSETS_ENABLED);
		fallbackKeys.add(
			"entriesPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add("entryColumns", PropsKeys.DL_ENTRY_COLUMNS);
		fallbackKeys.add("fileEntryColumns", PropsKeys.DL_FILE_ENTRY_COLUMNS);
		fallbackKeys.add("folderColumns", PropsKeys.DL_FOLDER_COLUMNS);
		fallbackKeys.add(
			"foldersPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add(
			"fileEntriesPerPage",
			PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add(
			"showFoldersSearch", PropsKeys.DL_FOLDERS_SEARCH_VISIBLE);
		fallbackKeys.add(
			"showHiddenMountFolders", PropsKeys.DL_SHOW_HIDDEN_MOUNT_FOLDERS);
		fallbackKeys.add("showSubfolders", PropsKeys.DL_SUBFOLDERS_VISIBLE);

		return fallbackKeys;
	}

	private static final String[] _MULTI_VALUED_KEYS = {};

	static {
		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		settingsFactory.registerSettingsMetadata(
			DLConstants.SERVICE_NAME, _getFallbackKeys(), _MULTI_VALUED_KEYS);
	}

	private final TypedSettings _typedSettings;

}