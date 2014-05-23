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

import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;

/**
 * @author Iván Zaera
 */
public class BookmarksSettings {

	public static FallbackKeys FALLBACK_KEYS = new FallbackKeys();

	static {
		FALLBACK_KEYS.add(
			"emailEntryAddedBody", PropsKeys.BOOKMARKS_EMAIL_ENTRY_ADDED_BODY);
		FALLBACK_KEYS.add(
			"emailEntryAddedEnabled",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_ADDED_ENABLED);
		FALLBACK_KEYS.add(
			"emailEntryAddedSubject",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_ADDED_SUBJECT);
		FALLBACK_KEYS.add(
			"emailEntryUpdatedBody",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_UPDATED_BODY);
		FALLBACK_KEYS.add(
			"emailEntryUpdatedEnabled",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_UPDATED_ENABLED);
		FALLBACK_KEYS.add(
			"emailEntryUpdatedSubject",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_UPDATED_SUBJECT);
		FALLBACK_KEYS.add(
			"emailFromAddress", PropsKeys.BOOKMARKS_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS );
		FALLBACK_KEYS.add(
			"emailFromName", PropsKeys.BOOKMARKS_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		FALLBACK_KEYS.add(
			"enableRelatedAssets", PropsKeys.BOOKMARKS_RELATED_ASSETS_ENABLED);
		FALLBACK_KEYS.add(
			"entriesPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		FALLBACK_KEYS.add("entryColumns", PropsKeys.BOOKMARKS_ENTRY_COLUMNS);
		FALLBACK_KEYS.add("folderColumns", PropsKeys.BOOKMARKS_FOLDER_COLUMNS);
		FALLBACK_KEYS.add(
			"foldersPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		FALLBACK_KEYS.add(
			"showFoldersSearch", PropsKeys.BOOKMARKS_FOLDERS_SEARCH_VISIBLE);
		FALLBACK_KEYS.add(
			"showSubfolders", PropsKeys.BOOKMARKS_SUBFOLDERS_VISIBLE);
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

	public boolean getEmailEntryAddedEnabled() {
		return _typedSettings.getBooleanValue("emailEntryAddedEnabled");
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

	public boolean getEmailEntryUpdatedEnabled() {
		return _typedSettings.getBooleanValue("emailEntryUpdatedEnabled");
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

	public boolean getEnableRelatedAssets() {
		return _typedSettings.getBooleanValue("enableRelatedAssets");
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

	public boolean getShowFoldersSearch() {
		return _typedSettings.getBooleanValue("showFoldersSearch");
	}

	public boolean getShowSubfolders() {
		return _typedSettings.getBooleanValue("showSubfolders");
	}

	private TypedSettings _typedSettings;

}