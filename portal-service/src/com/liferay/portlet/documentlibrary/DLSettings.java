/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.settings.BaseServiceSettings;
import com.liferay.portal.settings.FallbackKeys;
import com.liferay.portal.settings.LocalizedValuesMap;
import com.liferay.portal.settings.Settings;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public class DLSettings extends BaseServiceSettings {

	public DLSettings(Settings settings) {
		super(settings, _fallbackKeys);
	}

	public long getDefaultFolderId() {
		return typedSettings.getLongValue(
			"rootFolderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public String getDisplayViews() {
		return typedSettings.getValue("displayViews");
	}

	public LocalizedValuesMap getEmailFileEntryAddedBody() {
		return typedSettings.getLocalizedValuesMap("emailFileEntryAddedBody");
	}

	public boolean getEmailFileEntryAddedEnabled() {
		return typedSettings.getBooleanValue("emailFileEntryAddedEnabled");
	}

	public LocalizedValuesMap getEmailFileEntryAddedSubject() {
		return typedSettings.getLocalizedValuesMap("emailFileEntryAddedSubject");
	}

	public boolean getEmailFileEntryAnyEventEnabled() {
		return getEmailFileEntryAddedEnabled() ||
			getEmailFileEntryUpdatedEnabled();
	}

	public LocalizedValuesMap getEmailFileEntryUpdatedBody() {
		return typedSettings.getLocalizedValuesMap("emailFileEntryUpdatedBody");
	}

	public boolean getEmailFileEntryUpdatedEnabled() {
		return typedSettings.getBooleanValue("emailFileEntryUpdatedEnabled");
	}

	public LocalizedValuesMap getEmailFileEntryUpdatedSubject() {
		return typedSettings.getLocalizedValuesMap("emailFileEntryUpdatedSubject");
	}

	public String getEmailFromAddress() {
		return typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return typedSettings.getValue("emailFromName");
	}

	public boolean getEnableCommentRatings() {
		return typedSettings.getBooleanValue("enableCommentRatings", true);
	}

	public boolean getEnableRatings() {
		return typedSettings.getBooleanValue("enableRatings", true);
	}

	public boolean getEnableRelatedAssets() {
		return typedSettings.getBooleanValue("enableRelatedAssets", true);
	}

	public int getEntriesPerPage() {
		return typedSettings.getIntegerValue(
			"entriesPerPage", SearchContainer.DEFAULT_DELTA);
	}

	public String getEntryColumns() {
		return typedSettings.getValue(
			"entryColumns", DLUtil.getDefaultEntryColumns(getShowActions()));
	}

	public String[] getMediaGalleryMimeTypes() {
		return typedSettings.getValues(
			"mimeTypes", _defaultMediaGalleryMimeTypes);
	}

	public long getRootFolderId() {
		return typedSettings.getLongValue(
			"rootFolderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public boolean getShowActions() {
		return typedSettings.getBooleanValue("showActions");
	}

	public boolean getShowAssetMetadata() {
		return typedSettings.getBooleanValue("showAssetMetadata");
	}

	public boolean getShowFolderMenu() {
		return typedSettings.getBooleanValue("showFolderMenu");
	}

	public boolean getShowFoldersSearch() {
		return typedSettings.getBooleanValue("showFoldersSearch", true);
	}

	public boolean getShowHeader() {
		return typedSettings.getBooleanValue("showHeader", true);
	}

	public boolean getShowMinimalActionButtons() {
		return typedSettings.getBooleanValue("showMinimalActionButtons");
	}

	public boolean getShowTabs() {
		return typedSettings.getBooleanValue("showTabs");
	}

	private static String[] _defaultMediaGalleryMimeTypes;
	private static FallbackKeys _fallbackKeys = new FallbackKeys();

	static {
		_fallbackKeys.add("displayViews", PropsKeys.DL_DISPLAY_VIEWS);
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

		Set<String> allMimeTypes = DLUtil.getAllMediaGalleryMimeTypes();

		_defaultMediaGalleryMimeTypes = allMimeTypes.toArray(
			new String[allMimeTypes.size()]);
	}

}