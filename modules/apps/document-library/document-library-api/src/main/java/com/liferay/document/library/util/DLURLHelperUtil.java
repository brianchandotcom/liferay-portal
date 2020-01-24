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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.portlet.PortletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Adolfo Pérez
 */
public class DLURLHelperUtil {

	public static DLURLHelper getDLURLHelper() {
		_dlURLHelper = _serviceTracker.getService();

		if (_dlURLHelper == null) {
			throw new NullPointerException("DL URL helper is null");
		}

		return _dlURLHelper;
	}

	public static String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return getDLURLHelper().getDownloadURL(
			fileEntry, fileVersion, themeDisplay, queryString);
	}

	public static String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		return getDLURLHelper().getDownloadURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	public static String getFileEntryControlPanelLink(
			PortletRequest portletRequest, long fileEntryId)
		throws PortalException {

		return getDLURLHelper().getFileEntryControlPanelLink(
			portletRequest, fileEntryId);
	}

	public static String getFolderControlPanelLink(
			PortletRequest portletRequest, long folderId)
		throws PortalException {

		return getDLURLHelper().getFolderControlPanelLink(
			portletRequest, folderId);
	}

	public static String getImagePreviewURL(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay)
		throws Exception {

		return getDLURLHelper().getImagePreviewURL(
			fileEntry, fileVersion, themeDisplay);
	}

	public static String getImagePreviewURL(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay, String queryString,
			boolean appendVersion, boolean absoluteURL)
		throws PortalException {

		return getDLURLHelper().getImagePreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	public static String getImagePreviewURL(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return getDLURLHelper().getImagePreviewURL(fileEntry, themeDisplay);
	}

	public static String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return getDLURLHelper().getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString);
	}

	public static String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		return getDLURLHelper().getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	public static String getThumbnailSrc(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay)
		throws Exception {

		return getDLURLHelper().getThumbnailSrc(
			fileEntry, fileVersion, themeDisplay);
	}

	public static String getThumbnailSrc(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return getDLURLHelper().getThumbnailSrc(fileEntry, themeDisplay);
	}

	public static String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry)
		throws PortalException {

		return getDLURLHelper().getWebDavURL(themeDisplay, folder, fileEntry);
	}

	public static String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired)
		throws PortalException {

		return getDLURLHelper().getWebDavURL(
			themeDisplay, folder, fileEntry, manualCheckInRequired);
	}

	public static String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired, boolean officeExtensionRequired)
		throws PortalException {

		return getDLURLHelper().getWebDavURL(
			themeDisplay, folder, fileEntry, manualCheckInRequired,
			officeExtensionRequired);
	}

	@Deprecated
	public static void setDLURLHelper(DLURLHelper dlURLHelper) {
		if (_dlURLHelper != null) {
			return;
		}

		_dlURLHelper = dlURLHelper;
	}

	private static DLURLHelper _dlURLHelper;
	private static final ServiceTracker<DLURLHelper, DLURLHelper>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DLURLHelperUtil.class);

		ServiceTracker<DLURLHelper, DLURLHelper> serviceTracker =
			new ServiceTracker<>(
				bundle.getBundleContext(), DLURLHelper.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}