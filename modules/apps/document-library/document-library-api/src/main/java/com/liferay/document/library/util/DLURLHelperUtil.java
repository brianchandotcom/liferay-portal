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

package com.liferay.document.library.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.portlet.PortletRequest;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public class DLURLHelperUtil {

	public static DLURLHelper getDLURLHelper() {
		if (_dlurlHelper == null) {
			throw new NullPointerException("DL URL Helper is null");
		}

		return _dlurlHelper;
	}

	public static void setDLURLHelper(DLURLHelper dlurlHelper) {
		if (_dlurlHelper != null) {
			return;
		}

		_dlurlHelper = dlurlHelper;
	}

	public String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return getDLURLHelper().getDownloadURL(
			fileEntry, fileVersion, themeDisplay, queryString);
	}

	public String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		return getDLURLHelper().getDownloadURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	public String getFileEntryControlPanelLink(
			PortletRequest portletRequest, long fileEntryId)
		throws PortalException {

		return getDLURLHelper().getFileEntryControlPanelLink(
			portletRequest, fileEntryId);
	}

	public String getFolderControlPanelLink(
			PortletRequest portletRequest, long folderId)
		throws PortalException {

		return getDLURLHelper().getFolderControlPanelLink(
			portletRequest, folderId);
	}

	public String getImagePreviewURL(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay)
		throws Exception {

		return getDLURLHelper().getImagePreviewURL(
			fileEntry, fileVersion, themeDisplay);
	}

	public String getImagePreviewURL(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay, String queryString,
			boolean appendVersion, boolean absoluteURL)
		throws PortalException {

		return getDLURLHelper().getImagePreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	public String getImagePreviewURL(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return getDLURLHelper().getImagePreviewURL(fileEntry, themeDisplay);
	}

	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return getDLURLHelper().getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString);
	}

	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		return getDLURLHelper().getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	public String getThumbnailSrc(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay)
		throws Exception {

		return getDLURLHelper().getThumbnailSrc(
			fileEntry, fileVersion, themeDisplay);
	}

	public String getThumbnailSrc(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return getDLURLHelper().getThumbnailSrc(fileEntry, themeDisplay);
	}

	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry)
		throws PortalException {

		return getDLURLHelper().getWebDavURL(themeDisplay, folder, fileEntry);
	}

	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired)
		throws PortalException {

		return getDLURLHelper().getWebDavURL(
			themeDisplay, folder, fileEntry, manualCheckInRequired);
	}

	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired, boolean officeExtensionRequired)
		throws PortalException {

		return getDLURLHelper().getWebDavURL(
			themeDisplay, folder, fileEntry, manualCheckInRequired,
			officeExtensionRequired);
	}

	private static DLURLHelper _dlurlHelper;

}