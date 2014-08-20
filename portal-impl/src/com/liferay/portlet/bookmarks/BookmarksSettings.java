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

package com.liferay.portlet.bookmarks;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.util.BookmarksConstants;

import java.util.Map;

/**
 * @author Iván Zaera
 */
public class BookmarksSettings {

	public static final String[] ALL_KEYS = {
		"emailEntryAddedBody", "emailEntryAddedSubject",
		"emailEntryUpdatedBody", "emailEntryUpdatedSubject", "emailFromAddress",
		"emailFromName", "entriesPerPage", "entryColumns", "folderColumns",
		"foldersPerPage", "rootFolderId", "emailEntryAddedEnabled",
		"emailEntryUpdatedEnabled", "enableRelatedAssets", "showFoldersSearch",
		"showSubfolders"
	};

	public static BookmarksSettings getInstance(long groupId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			groupId, BookmarksConstants.SERVICE_NAME);

		return new BookmarksSettings(settings);
	}

	public static BookmarksSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			groupId, BookmarksConstants.SERVICE_NAME);

		ParameterMapSettings parameterMapSettings = new ParameterMapSettings(
			parameterMap, settings);

		return new BookmarksSettings(parameterMapSettings);
	}

	public BookmarksSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public LocalizedValuesMap getEmailEntryAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailEntryAddedBody");
	}

	public String getEmailEntryAddedBodyXml() {
		LocalizedValuesMap emailEntryAddedBody = getEmailEntryAddedBody();

		return emailEntryAddedBody.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailEntryAddedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailEntryAddedSubject");
	}

	public String getEmailEntryAddedSubjectXml() {
		LocalizedValuesMap emailEntryAddedSubject = getEmailEntryAddedSubject();

		return emailEntryAddedSubject.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailEntryUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap("emailEntryUpdatedBody");
	}

	public String getEmailEntryUpdatedBodyXml() {
		LocalizedValuesMap emailEntryUpdatedBody = getEmailEntryUpdatedBody();

		return emailEntryUpdatedBody.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailEntryUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailEntryUpdatedSubject");
	}

	public String getEmailEntryUpdatedSubjectXml() {
		LocalizedValuesMap emailEntryUpdatedSubject =
			getEmailEntryUpdatedSubject();

		return emailEntryUpdatedSubject.getLocalizationXml();
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	public int getEntriesPerPage() {
		return _typedSettings.getIntegerValue("entriesPerPage");
	}

	public String[] getEntryColumns() {
		return _typedSettings.getValues("entryColumns");
	}

	public String[] getFolderColumns() {
		return _typedSettings.getValues("folderColumns");
	}

	public int getFoldersPerPage() {
		return _typedSettings.getIntegerValue("foldersPerPage");
	}

	public long getRootFolderId() {
		return _typedSettings.getLongValue(
			"rootFolderId", BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public boolean isEmailEntryAddedEnabled() {
		return _typedSettings.getBooleanValue("emailEntryAddedEnabled");
	}

	public boolean isEmailEntryUpdatedEnabled() {
		return _typedSettings.getBooleanValue("emailEntryUpdatedEnabled");
	}

	public boolean isEnableRelatedAssets() {
		return _typedSettings.getBooleanValue("enableRelatedAssets");
	}

	public boolean isShowFoldersSearch() {
		return _typedSettings.getBooleanValue("showFoldersSearch");
	}

	public boolean isShowSubfolders() {
		return _typedSettings.getBooleanValue("showSubfolders");
	}

	private static FallbackKeys _getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add(
			"emailEntryAddedBody", PropsKeys.BOOKMARKS_EMAIL_ENTRY_ADDED_BODY);
		fallbackKeys.add(
			"emailEntryAddedEnabled",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_ADDED_ENABLED);
		fallbackKeys.add(
			"emailEntryAddedSubject",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_ADDED_SUBJECT);
		fallbackKeys.add(
			"emailEntryUpdatedBody",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_UPDATED_BODY);
		fallbackKeys.add(
			"emailEntryUpdatedEnabled",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_UPDATED_ENABLED);
		fallbackKeys.add(
			"emailEntryUpdatedSubject",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_UPDATED_SUBJECT);
		fallbackKeys.add(
			"emailFromAddress", PropsKeys.BOOKMARKS_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS );
		fallbackKeys.add(
			"emailFromName", PropsKeys.BOOKMARKS_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		fallbackKeys.add(
			"enableRelatedAssets", PropsKeys.BOOKMARKS_RELATED_ASSETS_ENABLED);
		fallbackKeys.add(
			"entriesPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add("entryColumns", PropsKeys.BOOKMARKS_ENTRY_COLUMNS);
		fallbackKeys.add("folderColumns", PropsKeys.BOOKMARKS_FOLDER_COLUMNS);
		fallbackKeys.add(
			"foldersPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add(
			"showFoldersSearch", PropsKeys.BOOKMARKS_FOLDERS_SEARCH_VISIBLE);
		fallbackKeys.add(
			"showSubfolders", PropsKeys.BOOKMARKS_SUBFOLDERS_VISIBLE);

		return fallbackKeys;
	}

	private static final String[] _MULTI_VALUED_KEYS = {
		"entryColumns", "folderColumns"
	};

	static {
		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		settingsFactory.registerSettingsMetadata(
			BookmarksConstants.SERVICE_NAME, _getFallbackKeys(),
			_MULTI_VALUED_KEYS);
	}

	private final TypedSettings _typedSettings;

}