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

import com.liferay.portal.kernel.settings.BaseServiceSettings;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.util.PropsKeys;

/**
 * @author Adolfo Pérez
 */
public class DLSettings extends BaseServiceSettings {

	public static final String[] MULTI_VALUED_KEYS = {};

	public DLSettings(Settings settings) {
		super(settings, _fallbackKeys);
	}

	public LocalizedValuesMap getEmailFileEntryAddedBody() {
		return typedSettings.getLocalizedValuesMap("emailFileEntryAddedBody");
	}

	public String getEmailFileEntryAddedBodyXml() {
		LocalizedValuesMap emailFileEntryAddedBody =
			getEmailFileEntryAddedBody();

		return emailFileEntryAddedBody.getLocalizationXml();
	}

	public boolean getEmailFileEntryAddedEnabled() {
		return typedSettings.getBooleanValue("emailFileEntryAddedEnabled");
	}

	public LocalizedValuesMap getEmailFileEntryAddedSubject() {
		return typedSettings.getLocalizedValuesMap(
			"emailFileEntryAddedSubject");
	}

	public String getEmailFileEntryAddedSubjectXml() {
		LocalizedValuesMap emailFileEntryAddedSubject =
			getEmailFileEntryAddedSubject();

		return emailFileEntryAddedSubject.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailFileEntryUpdatedBody() {
		return typedSettings.getLocalizedValuesMap("emailFileEntryUpdatedBody");
	}

	public String getEmailFileEntryUpdatedBodyXml() {
		LocalizedValuesMap emailFileEntryUpdatedBody =
			getEmailFileEntryUpdatedBody();

		return emailFileEntryUpdatedBody.getLocalizationXml();
	}

	public boolean getEmailFileEntryUpdatedEnabled() {
		return typedSettings.getBooleanValue("emailFileEntryUpdatedEnabled");
	}

	public LocalizedValuesMap getEmailFileEntryUpdatedSubject() {
		return typedSettings.getLocalizedValuesMap(
			"emailFileEntryUpdatedSubject");
	}

	public String getEmailFileEntryUpdatedSubjectXml() {
		LocalizedValuesMap emailFileEntryUpdatedSubject =
			getEmailFileEntryUpdatedSubject();

		return emailFileEntryUpdatedSubject.getLocalizationXml();
	}

	public String getEmailFromAddress() {
		return typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return typedSettings.getValue("emailFromName");
	}

	private static FallbackKeys _fallbackKeys = new FallbackKeys();

	static {
		_fallbackKeys.add(
			"emailFileEntryAddedBody",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_BODY);
		_fallbackKeys.add(
			"emailFileEntryAddedEnabled",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_ENABLED);
		_fallbackKeys.add(
			"emailFileEntryAddedSubject",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_SUBJECT);
		_fallbackKeys.add(
			"emailFileEntryUpdatedBody",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_BODY);
		_fallbackKeys.add(
			"emailFileEntryUpdatedEnabled",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_ENABLED);
		_fallbackKeys.add(
			"emailFileEntryUpdatedSubject",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_SUBJECT);
		_fallbackKeys.add(
			"emailFromAddress", PropsKeys.DL_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		_fallbackKeys.add(
			"emailFromName", PropsKeys.DL_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		_fallbackKeys.add(
			"enableCommentRatings", PropsKeys.DL_COMMENT_RATINGS_ENABLED);
		_fallbackKeys.add("enableRatings", PropsKeys.DL_RATINGS_ENABLED);
		_fallbackKeys.add(
			"enableRelatedAssets", PropsKeys.DL_RELATED_ASSETS_ENABLED);
		_fallbackKeys.add(
			"entriesPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		_fallbackKeys.add("entryColumns", PropsKeys.DL_ENTRY_COLUMNS);
		_fallbackKeys.add("fileEntryColumns", PropsKeys.DL_FILE_ENTRY_COLUMNS);
		_fallbackKeys.add("folderColumns", PropsKeys.DL_FOLDER_COLUMNS);
		_fallbackKeys.add(
			"foldersPerPage", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		_fallbackKeys.add(
			"fileEntriesPerPage",
			PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		_fallbackKeys.add(
			"showFoldersSearch", PropsKeys.DL_FOLDERS_SEARCH_VISIBLE);
		_fallbackKeys.add("showSubfolders", PropsKeys.DL_SUBFOLDERS_VISIBLE);
	}

}