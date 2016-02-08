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
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
@Settings.Config(settingsIds = DLConstants.SERVICE_NAME)
public class DLGroupServiceSettings {

	public static DLGroupServiceSettings getInstance(long groupId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new GroupServiceSettingsLocator(groupId, DLConstants.SERVICE_NAME));

		return new DLGroupServiceSettings(settings);
	}

	public static DLGroupServiceSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new GroupServiceSettingsLocator(groupId, DLConstants.SERVICE_NAME));

		Settings parameterMapSettings = new ParameterMapSettings(
			parameterMap, settings);

		return new DLGroupServiceSettings(parameterMapSettings);
	}

	public static void registerSettingsMetadata() {
		SettingsFactoryUtil.registerSettingsMetadata(
			DLGroupServiceSettings.class, null, _getFallbackKeys());
	}

	public DLGroupServiceSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public LocalizedValuesMap getEmailFileEntryAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailFileEntryAddedBody");
	}

	@Settings.Property(ignore = true)
	public String getEmailFileEntryAddedBodyXml() {
		return LocalizationUtil.getXml(
			getEmailFileEntryAddedBody(), "emailFileEntryAdded");
	}

	public LocalizedValuesMap getEmailFileEntryAddedSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryAddedSubject");
	}

	@Settings.Property(ignore = true)
	public String getEmailFileEntryAddedSubjectXml() {
		return LocalizationUtil.getXml(
			getEmailFileEntryAddedSubject(), "emailFileEntryAddedSubject");
	}

	public LocalizedValuesMap getEmailFileEntryUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryUpdatedBody");
	}

	@Settings.Property(ignore = true)
	public String getEmailFileEntryUpdatedBodyXml() {
		return LocalizationUtil.getXml(
			getEmailFileEntryUpdatedBody(), "emailFileEntryUpdatedBody");
	}

	public LocalizedValuesMap getEmailFileEntryUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryUpdatedSubject");
	}

	@Settings.Property(ignore = true)
	public String getEmailFileEntryUpdatedSubjectXml() {
		return LocalizationUtil.getXml(
			getEmailFileEntryUpdatedSubject(), "emailFileEntryUpdatedSubject");
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

	static {
		SettingsFactoryUtil.registerSettingsMetadata(
			DLGroupServiceSettings.class, null, _getFallbackKeys());
	}

	private final TypedSettings _typedSettings;

}