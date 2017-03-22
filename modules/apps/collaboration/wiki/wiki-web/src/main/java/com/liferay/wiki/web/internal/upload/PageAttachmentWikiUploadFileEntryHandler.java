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

package com.liferay.wiki.web.internal.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.upload.UploadFileEntryHandler;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;

import java.io.InputStream;

/**
 * @author Roberto Díaz
 */
public class PageAttachmentWikiUploadFileEntryHandler
	implements UploadFileEntryHandler {

	public PageAttachmentWikiUploadFileEntryHandler(long classPK) {
		_classPK = classPK;
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long groupId, long folderId, String fileName,
			String contentType, InputStream inputStream, long size,
			ServiceContext serviceContext)
		throws PortalException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(_classPK);

		return WikiPageServiceUtil.addPageAttachment(
			page.getNodeId(), page.getTitle(), fileName, inputStream,
			contentType);
	}

	@Override
	public void checkPermission(
			long groupId, long folderId, PermissionChecker permissionChecker)
		throws PortalException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(_classPK);

		WikiNodePermissionChecker.check(
			permissionChecker, page.getNodeId(), ActionKeys.ADD_ATTACHMENT);
	}

	@Override
	public FileEntry fetchFileEntry(
			long userId, long groupId, long folderId, String fileName)
		throws PortalException {

		try {
			WikiPage page = WikiPageLocalServiceUtil.getPage(_classPK);

			Folder folder = page.addAttachmentsFolder();

			return PortletFileRepositoryUtil.getPortletFileEntry(
				groupId, folder.getFolderId(), fileName);
		}
		catch (PortalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return null;
		}
	}

	@Override
	public String getParameterName() {
		return "imageSelectorFileName";
	}

	@Override
	public void validateFile(String fileName, String contentType, long size)
		throws PortalException {
	}

	protected PageAttachmentWikiUploadFileEntryHandler() {
		this(0);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PageAttachmentWikiUploadFileEntryHandler.class);

	private final long _classPK;

}