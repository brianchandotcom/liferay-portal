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

package com.liferay.document.library.internal.util;

import com.liferay.document.library.constants.DLFileVersionPreviewConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DL;
import com.liferay.document.library.kernel.util.ImageProcessorUtil;
import com.liferay.document.library.kernel.util.PDFProcessorUtil;
import com.liferay.document.library.kernel.util.VideoProcessorUtil;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.webdav.DLWebDAVUtil;
import com.liferay.trash.TrashHelper;

import java.util.Date;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = DLURLHelper.class)
public class DLURLHelperImpl implements DLURLHelper {

	@Override
	public String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return getDownloadURL(
			fileEntry, fileVersion, themeDisplay, queryString, true, true);
	}

	@Override
	public String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		String previewURL = getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);

		return _http.addParameter(previewURL, "download", true);
	}

	@Override
	public String getFileEntryControlPanelLink(
		PortletRequest portletRequest, long fileEntryId) {

		String portletId = PortletProviderUtil.getPortletId(
			FileEntry.class.getName(), PortletProvider.Action.MANAGE);

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			portletRequest, portletId, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/view_file_entry");
		portletURL.setParameter("fileEntryId", String.valueOf(fileEntryId));

		return portletURL.toString();
	}

	@Override
	public String getFolderControlPanelLink(
		PortletRequest portletRequest, long folderId) {

		String portletId = PortletProviderUtil.getPortletId(
			Folder.class.getName(), PortletProvider.Action.MANAGE);

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			portletRequest, portletId, PortletRequest.RENDER_PHASE);

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view");
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view_folder");
		}

		portletURL.setParameter("folderId", String.valueOf(folderId));

		return portletURL.toString();
	}

	public String getImagePreviewURL(
		FileEntry fileEntry, FileVersion fileVersion,
		ThemeDisplay themeDisplay) {

		return getImagePreviewURL(
			fileEntry, fileVersion, themeDisplay, null, true, true);
	}

	@Override
	public String getImagePreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		if (_dlFileVersionPreviewLocalService.hasDLFileVersionPreview(
				fileEntry.getFileEntryId(), fileVersion.getFileVersionId(),
				DLFileVersionPreviewConstants.STATUS_FAILURE)) {

			return StringPool.BLANK;
		}

		String previewQueryString = queryString;

		if (Validator.isNull(previewQueryString)) {
			previewQueryString = StringPool.BLANK;
		}

		if (ImageProcessorUtil.isSupported(fileVersion.getMimeType())) {
			previewQueryString = previewQueryString.concat("&imagePreview=1");
		}
		else if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED) {
			if (PDFProcessorUtil.hasImages(fileVersion)) {
				previewQueryString = previewQueryString.concat(
					"&previewFileIndex=1");
			}
			else if (VideoProcessorUtil.hasVideo(fileVersion)) {
				previewQueryString = previewQueryString.concat(
					"&videoThumbnail=1");
			}
		}

		return getImageSrc(
			fileEntry, fileVersion, themeDisplay, previewQueryString,
			appendVersion, absoluteURL);
	}

	@Override
	public String getImagePreviewURL(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return getImagePreviewURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay);
	}

	@Override
	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, true, true);
	}

	@Override
	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		StringBundler sb = new StringBundler(15);

		if ((themeDisplay != null) && absoluteURL) {
			sb.append(themeDisplay.getPortalURL());
		}

		sb.append(_portal.getPathContext());
		sb.append("/documents/");
		sb.append(fileEntry.getRepositoryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getFolderId());
		sb.append(StringPool.SLASH);

		String fileName = fileEntry.getFileName();

		if (fileEntry.isInTrash()) {
			fileName = _trashHelper.getOriginalTitle(fileEntry.getFileName());
		}

		sb.append(URLCodec.encodeURL(HtmlUtil.unescape(fileName)));

		sb.append(StringPool.SLASH);
		sb.append(URLCodec.encodeURL(fileEntry.getUuid()));

		if (appendVersion) {
			sb.append("?version=");
			sb.append(fileVersion.getVersion());
			sb.append("&t=");
		}
		else {
			sb.append("?t=");
		}

		Date modifiedDate = fileVersion.getModifiedDate();

		sb.append(modifiedDate.getTime());

		sb.append(queryString);

		String previewURL = sb.toString();

		if ((themeDisplay != null) && themeDisplay.isAddSessionIdToURL()) {
			return _portal.getURLWithSessionId(
				previewURL, themeDisplay.getSessionId());
		}

		return previewURL;
	}

	@Override
	public String getThumbnailSrc(
		FileEntry fileEntry, FileVersion fileVersion,
		ThemeDisplay themeDisplay) {

		if (_dlFileVersionPreviewLocalService.hasDLFileVersionPreview(
				fileEntry.getFileEntryId(), fileVersion.getFileVersionId(),
				DLFileVersionPreviewConstants.STATUS_FAILURE)) {

			return StringPool.BLANK;
		}

		String thumbnailQueryString = null;

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {
			if (ImageProcessorUtil.hasImages(fileVersion)) {
				thumbnailQueryString = "&imageThumbnail=1";
			}
			else if (PDFProcessorUtil.hasImages(fileVersion)) {
				thumbnailQueryString = "&documentThumbnail=1";
			}
			else if (VideoProcessorUtil.hasVideo(fileVersion)) {
				thumbnailQueryString = "&videoThumbnail=1";
			}
		}

		return getImageSrc(
			fileEntry, fileVersion, themeDisplay, thumbnailQueryString);
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

		return getWebDavURL(themeDisplay, folder, fileEntry, false);
	}

	@Override
	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired)
		throws PortalException {

		return getWebDavURL(
			themeDisplay, folder, fileEntry, manualCheckInRequired, false);
	}

	@Override
	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired, boolean openDocumentUrl)
		throws PortalException {

		StringBundler webDavURLSB = new StringBundler(7);

		boolean secure = false;

		if (themeDisplay.isSecure() ||
			PropsValues.WEBDAV_SERVLET_HTTPS_REQUIRED) {

			secure = true;
		}

		String portalURL = _portal.getPortalURL(
			themeDisplay.getServerName(), themeDisplay.getServerPort(), secure);

		webDavURLSB.append(portalURL);

		webDavURLSB.append(themeDisplay.getPathContext());
		webDavURLSB.append("/webdav");

		if (manualCheckInRequired) {
			webDavURLSB.append(DL.MANUAL_CHECK_IN_REQUIRED_PATH);
		}

		Group group = null;

		if (fileEntry != null) {
			group = _groupLocalService.getGroup(fileEntry.getGroupId());
		}
		else {
			group = themeDisplay.getScopeGroup();
		}

		webDavURLSB.append(group.getFriendlyURL());
		webDavURLSB.append("/document_library");

		StringBuilder sb = new StringBuilder();

		if ((folder != null) &&
			(folder.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			Folder curFolder = folder;

			while (true) {
				sb.insert(0, URLCodec.encodeURL(curFolder.getName(), true));
				sb.insert(0, StringPool.SLASH);

				if (curFolder.getParentFolderId() ==
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

					break;
				}

				curFolder = _dlAppLocalService.getFolder(
					curFolder.getParentFolderId());
			}
		}

		if (fileEntry != null) {
			sb.append(StringPool.SLASH);
			sb.append(DLWebDAVUtil.escapeURLTitle(fileEntry.getTitle()));
		}

		webDavURLSB.append(sb.toString());

		return webDavURLSB.toString();
	}

	protected String getImageSrc(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return getImageSrc(
			fileEntry, fileVersion, themeDisplay, queryString, true, true);
	}

	protected String getImageSrc(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		String thumbnailSrc = StringPool.BLANK;

		if (Validator.isNotNull(queryString)) {
			thumbnailSrc = getPreviewURL(
				fileEntry, fileVersion, themeDisplay, queryString,
				appendVersion, absoluteURL);
		}

		return thumbnailSrc;
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

	@Reference
	private TrashHelper _trashHelper;

}