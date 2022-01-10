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

package com.liferay.wiki.web.internal.helper;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.service.TrashEntryService;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageService;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = WikiAttachmentsHelper.class)
public class WikiAttachmentsHelper {

	public void addAttachments(ActionRequest actionRequest) throws Exception {
		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		int numOfFiles = ParamUtil.getInteger(actionRequest, "numOfFiles");

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<>();
		List<FileEntry> tempFileEntries = new ArrayList<>();

		try {
			if (numOfFiles == 0) {
				InputStream inputStream = uploadPortletRequest.getFileAsStream(
					"file");

				if (inputStream != null) {
					ObjectValuePair<String, InputStream> inputStreamOVP =
						new ObjectValuePair<>(
							uploadPortletRequest.getFileName("file"),
							inputStream);

					inputStreamOVPs.add(inputStreamOVP);
				}
			}
			else {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				String[] selectUploadedFiles = ParamUtil.getParameterValues(
					actionRequest, "selectUploadedFile");

				for (String selectUploadedFile : selectUploadedFiles) {
					FileEntry tempFileEntry =
						TempFileEntryUtil.getTempFileEntry(
							themeDisplay.getScopeGroupId(),
							themeDisplay.getUserId(),
							WikiConstants.TEMP_FOLDER_NAME, selectUploadedFile);

					WikiPage wikiPage = _wikiPageService.getPage(nodeId, title);

					String uniqueFileName = DLUtil.getUniqueFileName(
						wikiPage.getGroupId(),
						wikiPage.getAttachmentsFolderId(),
						TempFileEntryUtil.getOriginalTempFileName(
							tempFileEntry.getFileName()),
						false);

					ObjectValuePair<String, InputStream> inputStreamOVP =
						new ObjectValuePair<>(
							uniqueFileName, tempFileEntry.getContentStream());

					inputStreamOVPs.add(inputStreamOVP);

					tempFileEntries.add(tempFileEntry);
				}
			}

			if (ListUtil.isNotEmpty(inputStreamOVPs)) {
				_wikiPageService.addPageAttachments(
					nodeId, title, inputStreamOVPs);
			}
		}
		finally {
			for (ObjectValuePair<String, InputStream> inputStreamOVP :
					inputStreamOVPs) {

				try (InputStream inputStream = inputStreamOVP.getValue()) {
				}
				catch (IOException ioException) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioException, ioException);
					}
				}
			}

			for (FileEntry tempFileEntry : tempFileEntries) {
				TempFileEntryUtil.deleteTempFileEntry(
					tempFileEntry.getFileEntryId());
			}
		}
	}

	public TrashedModel deleteAttachment(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");
		String attachment = ParamUtil.getString(actionRequest, "fileName");

		TrashedModel trashedModel = null;

		if (moveToTrash) {
			FileEntry fileEntry = _wikiPageService.movePageAttachmentToTrash(
				nodeId, title, attachment);

			if (fileEntry.isRepositoryCapabilityProvided(
					TrashCapability.class)) {

				trashedModel = (TrashedModel)fileEntry.getModel();
			}
		}
		else {
			_wikiPageService.deletePageAttachment(nodeId, title, attachment);
		}

		return trashedModel;
	}

	public void emptyTrash(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		_wikiPageService.deleteTrashPageAttachments(nodeId, title);
	}

	public void restoreEntries(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");
		String fileName = ParamUtil.getString(actionRequest, "fileName");

		_wikiPageService.restorePageAttachmentFromTrash(
			nodeId, title, fileName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiAttachmentsHelper.class);

	@Reference
	private Portal _portal;

	@Reference
	private TrashEntryService _trashEntryService;

	@Reference
	private WikiPageService _wikiPageService;

}