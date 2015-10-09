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

package com.liferay.wiki.web.wiki.portlet.action;

import com.liferay.portal.kernel.flash.FlashMagicBytesUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageServiceUtil;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(
	property = "path=/wiki/get_page_attachment", service = StrutsAction.class
)
public class GetPageAttachmentAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			long nodeId = ParamUtil.getLong(request, "nodeId");
			String title = ParamUtil.getString(request, "title");
			String fileName = ParamUtil.getString(request, "fileName");
			int status = ParamUtil.getInteger(
				request, "status", WorkflowConstants.STATUS_APPROVED);

			getFile(nodeId, title, fileName, status, request, response);

			return null;
		}
		catch (Exception e) {
			if ((e instanceof NoSuchPageException) ||
				(e instanceof NoSuchFileException)) {

				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}
			else {
				PortalUtil.sendError(e, request, response);
			}

			return null;
		}
	}

	protected void getFile(
			long nodeId, String title, String fileName, int status,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		WikiPage wikiPage = WikiPageServiceUtil.getPage(nodeId, title);

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			wikiPage.getGroupId(), wikiPage.getAttachmentsFolderId(), fileName);

		if ((status != WorkflowConstants.STATUS_IN_TRASH) &&
			fileEntry.isInTrash()) {

			return;
		}

		if (fileEntry.isInTrash()) {
			fileName = TrashUtil.getOriginalTitle(fileEntry.getTitle());
		}

		InputStream is = fileEntry.getContentStream();

		FlashMagicBytesUtil.Result flashMagicBytesUtilResult =
			FlashMagicBytesUtil.check(is);

		if (flashMagicBytesUtilResult.isFlash()) {
			fileName = FileUtil.stripExtension(fileName) + ".swf";
		}

		is = flashMagicBytesUtilResult.getInputStream();

		ServletResponseUtil.sendFile(
			request, response, fileName, is, fileEntry.getSize(),
			fileEntry.getMimeType());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetPageAttachmentAction.class);

}