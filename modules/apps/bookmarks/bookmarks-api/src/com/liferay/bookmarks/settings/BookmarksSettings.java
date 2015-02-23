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

package com.liferay.bookmarks.settings;

import com.liferay.bookmarks.constants.BookmarksConstants;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;

/**
 * @author Iván Zaera
 */
@Settings.Config(ids = BookmarksConstants.SERVICE_NAME)
public class BookmarksSettings {

	public static FallbackKeys getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add(
			"emailFromAddress", "email.from.address",
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS );
		fallbackKeys.add(
			"emailFromName", "email.from.name",
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		fallbackKeys.add(
			"entriesPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add(
			"foldersPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);

		return fallbackKeys;
	}

	public BookmarksSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public LocalizedValuesMap getEmailEntryAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailEntryAddedBody");
	}

	@Settings.Property(ignore = true)
	public String getEmailEntryAddedBodyXml() {
		LocalizedValuesMap emailEntryAddedBody = getEmailEntryAddedBody();

		return emailEntryAddedBody.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailEntryAddedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailEntryAddedSubject");
	}

	@Settings.Property(ignore = true)
	public String getEmailEntryAddedSubjectXml() {
		LocalizedValuesMap emailEntryAddedSubject = getEmailEntryAddedSubject();

		return emailEntryAddedSubject.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailEntryUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap("emailEntryUpdatedBody");
	}

	@Settings.Property(ignore = true)
	public String getEmailEntryUpdatedBodyXml() {
		LocalizedValuesMap emailEntryUpdatedBody = getEmailEntryUpdatedBody();

		return emailEntryUpdatedBody.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailEntryUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailEntryUpdatedSubject");
	}

	@Settings.Property(ignore = true)
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

	private final TypedSettings _typedSettings;

}