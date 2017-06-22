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

package com.liferay.wiki.web.internal;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.trash.service.TrashEntryService;
import com.liferay.wiki.service.WikiPageService;

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

		try {
			if (numOfFiles == 0) {
				String fileName = uploadPortletRequest.getFileName("file");
				InputStream inputStream = uploadPortletRequest.getFileAsStream(
					"file");

				if (inputStream != null) {
					ObjectValuePair<String, InputStream> inputStreamOVP =
						new ObjectValuePair<>(fileName, inputStream);

					inputStreamOVPs.add(inputStreamOVP);
				}
			}
			else {
				for (int i = 1; i <= numOfFiles; i++) {
					String fileName = uploadPortletRequest.getFileName(
						"file" + i);
					InputStream inputStream =
						uploadPortletRequest.getFileAsStream("file" + i);

					if (inputStream == null) {
						continue;
					}

					ObjectValuePair<String, InputStream> inputStreamOVP =
						new ObjectValuePair<>(fileName, inputStream);

					inputStreamOVPs.add(inputStreamOVP);
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

				InputStream inputStream = inputStreamOVP.getValue();

				StreamUtil.cleanUp(inputStream);
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

			if (fileEntry.getModel() instanceof DLFileEntry) {
				trashedModel = (DLFileEntry)fileEntry.getModel();
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

	@Reference(unbind = "-")
	protected void setTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageService(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	@Reference
	private Portal _portal;

	private TrashEntryService _trashEntryService;
	private WikiPageService _wikiPageService;

}