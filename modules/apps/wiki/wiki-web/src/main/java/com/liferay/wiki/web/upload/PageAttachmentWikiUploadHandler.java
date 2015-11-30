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

package com.liferay.wiki.web.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upload.BaseUploadHandler;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;

import java.io.InputStream;

/**
 * @author Roberto Díaz
 */
public class PageAttachmentWikiUploadHandler extends BaseUploadHandler {

	public PageAttachmentWikiUploadHandler(long classPK) {
		_classPK = classPK;
	}

	protected PageAttachmentWikiUploadHandler() {
		this(0);
	}

	@Override
	protected FileEntry addFileEntry(
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
	protected void checkPermission(
			long groupId, long folderId, PermissionChecker permissionChecker)
		throws PortalException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(_classPK);

		WikiNodePermissionChecker.check(
			permissionChecker, page.getNodeId(), ActionKeys.ADD_ATTACHMENT);
	}

	@Override
	protected FileEntry fetchFileEntry(
			long userId, long groupId, long folderId, String fileName)
		throws PortalException {

		try {
			WikiPage page = WikiPageLocalServiceUtil.getPage(_classPK);

			Folder folder = page.addAttachmentsFolder();

			return PortletFileRepositoryUtil.getPortletFileEntry(
				groupId, folder.getFolderId(), fileName);
		}
		catch (PortalException pe) {
			return null;
		}
	}

	@Override
	protected String getParameterName() {
		return "imageSelectorFileName";
	}

	@Override
	protected void validateFile(String fileName, String contentType, long size)
		throws PortalException {
	}

	private final long _classPK;

}