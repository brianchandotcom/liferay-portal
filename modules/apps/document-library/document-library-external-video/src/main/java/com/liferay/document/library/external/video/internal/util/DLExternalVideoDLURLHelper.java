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

package com.liferay.document.library.external.video.internal.util;

import com.liferay.document.library.external.video.internal.constants.DLExternalVideoConstants;
import com.liferay.document.library.external.video.internal.helper.DLExternalVideoMetadataHelper;
import com.liferay.document.library.external.video.internal.helper.DLExternalVideoMetadataHelperFactory;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "service.ranking:Integer=100", service = DLURLHelper.class
)
public class DLExternalVideoDLURLHelper implements DLURLHelper {

	@Override
	public String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return _dlURLHelper.getDownloadURL(
			fileEntry, fileVersion, themeDisplay, queryString);
	}

	@Override
	public String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		return _dlURLHelper.getDownloadURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	@Override
	public String getFileEntryControlPanelLink(
			PortletRequest portletRequest, long fileEntryId)
		throws PortalException {

		return _dlURLHelper.getFileEntryControlPanelLink(
			portletRequest, fileEntryId);
	}

	@Override
	public String getFolderControlPanelLink(
			PortletRequest portletRequest, long folderId)
		throws PortalException {

		return _dlURLHelper.getFolderControlPanelLink(portletRequest, folderId);
	}

	@Override
	public String getImagePreviewURL(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay)
		throws Exception {

		return _dlURLHelper.getImagePreviewURL(
			fileEntry, fileVersion, themeDisplay);
	}

	@Override
	public String getImagePreviewURL(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay, String queryString,
			boolean appendVersion, boolean absoluteURL)
		throws PortalException {

		return _dlURLHelper.getImagePreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	@Override
	public String getImagePreviewURL(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return _dlURLHelper.getImagePreviewURL(fileEntry, themeDisplay);
	}

	@Override
	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return _dlURLHelper.getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString);
	}

	@Override
	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		return _dlURLHelper.getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	@Override
	public String getThumbnailSrc(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay)
		throws Exception {

		DLExternalVideoMetadataHelper dlExternalVideoMetadataHelper =
			_dlExternalVideoMetadataHelperFactory.
				getDLExternalVideoMetadataHelper(fileVersion);

		if (dlExternalVideoMetadataHelper != null) {
			String thumbnailURL = dlExternalVideoMetadataHelper.getFieldValue(
				DLExternalVideoConstants.DDM_FIELD_NAME_THUMBNAIL_URL);

			if (Validator.isNotNull(thumbnailURL)) {
				return thumbnailURL;
			}
		}

		return _dlURLHelper.getThumbnailSrc(
			fileEntry, fileVersion, themeDisplay);
	}

	@Override
	public String getThumbnailSrc(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return getThumbnailSrc(
			fileEntry, fileEntry.getFileVersion(), themeDisplay);
	}

	@Override
	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry)
		throws PortalException {

		return _dlURLHelper.getWebDavURL(themeDisplay, folder, fileEntry);
	}

	@Override
	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired)
		throws PortalException {

		return _dlURLHelper.getWebDavURL(
			themeDisplay, folder, fileEntry, manualCheckInRequired);
	}

	@Override
	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired, boolean openDocumentUrl)
		throws PortalException {

		return _dlURLHelper.getWebDavURL(
			themeDisplay, folder, fileEntry, manualCheckInRequired,
			openDocumentUrl);
	}

	@Reference
	private DLExternalVideoMetadataHelperFactory
		_dlExternalVideoMetadataHelperFactory;

	@Reference(
		target = "(!(component.name=com.liferay.document.library.external.video.internal.util.DLExternalVideoDLURLHelper))"
	)
	private DLURLHelper _dlURLHelper;

}