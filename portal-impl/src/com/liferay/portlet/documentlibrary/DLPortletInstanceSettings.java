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
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.Map;

/**
 * @author Sergio González
 */
public class DLPortletInstanceSettings {

	public static final String[] ALL_KEYS = {
		"rootFolderId", "displayViews", "enableFileEntryDrafts",
		"entriesPerPage", "entryColumns", "fileEntriesPerPage",
		"fileEntryColumns", "folderColumns", "foldersPerPage", "mimeTypes",
		"enableCommentRatings", "enableRatings", "enableRelatedAssets",
		"showActions", "showFolderMenu", "showFoldersSearch", "showSubfolders",
		"showTabs",
	};

	public static DLPortletInstanceSettings getInstance(
			Layout layout, String portletId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getPortletInstanceSettings(
			layout, portletId);

		return new DLPortletInstanceSettings(settings);
	}

	public static DLPortletInstanceSettings getInstance(
			Layout layout, String portletId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getPortletInstanceSettings(
			layout, portletId);

		Settings parameterMapSettings = new ParameterMapSettings(
			parameterMap, settings);

		return new DLPortletInstanceSettings(parameterMapSettings);
	}

	public DLPortletInstanceSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public long getDefaultFolderId() {
		return _typedSettings.getLongValue(
			"rootFolderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public String[] getDisplayViews() {
		return _typedSettings.getValues("displayViews");
	}

	public int getEntriesPerPage() {
		return _typedSettings.getIntegerValue("entriesPerPage");
	}

	public String[] getEntryColumns() {
		return _typedSettings.getValues("entryColumns");
	}

	public int getFileEntriesPerPage() {
		return _typedSettings.getIntegerValue("fileEntriesPerPage");
	}

	public String[] getFileEntryColumns() {
		return _typedSettings.getValues("fileEntryColumns");
	}

	public String[] getFolderColumns() {
		return _typedSettings.getValues("folderColumns");
	}

	public int getFoldersPerPage() {
		return _typedSettings.getIntegerValue("foldersPerPage");
	}

	public String[] getMimeTypes() {
		return _typedSettings.getValues("mimeTypes", _MIME_TYPES_DEFAULT);
	}

	public long getRootFolderId() {
		return _typedSettings.getLongValue(
			"rootFolderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public boolean isEnableCommentRatings() {
		return _typedSettings.getBooleanValue("enableCommentRatings");
	}

	public boolean isEnableFileEntryDrafts() {
		return _typedSettings.getBooleanValue("enableFileEntryDrafts");
	}

	public boolean isEnableRatings() {
		return _typedSettings.getBooleanValue("enableRatings");
	}

	public boolean isEnableRelatedAssets() {
		return _typedSettings.getBooleanValue("enableRelatedAssets");
	}

	public boolean isShowActions() {
		return _typedSettings.getBooleanValue("showActions");
	}

	public boolean isShowFolderMenu() {
		return _typedSettings.getBooleanValue("showFolderMenu");
	}

	public boolean isShowFoldersSearch() {
		return _typedSettings.getBooleanValue("showFoldersSearch");
	}

	public boolean isShowSubfolders() {
		return _typedSettings.getBooleanValue("showSubfolders");
	}

	public boolean isShowTabs() {
		return _typedSettings.getBooleanValue("showTabs");
	}

	private static FallbackKeys _getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add("displayViews", PropsKeys.DL_DISPLAY_VIEWS);
		fallbackKeys.add(
			"enableCommentRatings", PropsKeys.DL_COMMENT_RATINGS_ENABLED);
		fallbackKeys.add(
			"enableFileEntryDrafts", PropsKeys.DL_FILE_ENTRY_DRAFTS_ENABLED);
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
		fallbackKeys.add("showActions", PropsKeys.DL_ACTIONS_VISIBLE);
		fallbackKeys.add("showFolderMenu", PropsKeys.DL_FOLDER_MENU_VISIBLE);
		fallbackKeys.add(
			"showFoldersSearch", PropsKeys.DL_FOLDERS_SEARCH_VISIBLE);
		fallbackKeys.add("showSubfolders", PropsKeys.DL_SUBFOLDERS_VISIBLE);
		fallbackKeys.add("showTabs", PropsKeys.DL_TABS_VISIBLE);

		return fallbackKeys;
	}

	private static final String[] _MIME_TYPES_DEFAULT = ArrayUtil.toStringArray(
		DLUtil.getAllMediaGalleryMimeTypes());

	private static final String[] _MULTI_VALUED_KEYS = {
		"displayViews", "entryColumns", "fileEntryColumns", "folderColumns",
		"mimeTypes"
	};

	static {
		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		settingsFactory.registerSettingsMetadata(
			PortletKeys.DOCUMENT_LIBRARY, _getFallbackKeys(),
			_MULTI_VALUED_KEYS);
		settingsFactory.registerSettingsMetadata(
			PortletKeys.DOCUMENT_LIBRARY_ADMIN, _getFallbackKeys(),
			_MULTI_VALUED_KEYS);
		settingsFactory.registerSettingsMetadata(
			PortletKeys.DOCUMENT_LIBRARY_DISPLAY, _getFallbackKeys(),
			_MULTI_VALUED_KEYS);
		settingsFactory.registerSettingsMetadata(
			PortletKeys.MEDIA_GALLERY_DISPLAY, _getFallbackKeys(),
			_MULTI_VALUED_KEYS);
	}

	private final TypedSettings _typedSettings;

}