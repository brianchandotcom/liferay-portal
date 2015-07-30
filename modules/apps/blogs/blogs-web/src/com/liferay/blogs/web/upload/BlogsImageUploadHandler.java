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

package com.liferay.blogs.web.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.BlogsConstants;

import java.io.InputStream;

/**
 * @author Roberto Díaz
 */
public class BlogsImageUploadHandler extends BaseBlogsImageUploadHandler {

	@Override
	protected FileEntry addFileEntry(
			ThemeDisplay themeDisplay, String fileName, InputStream inputStream,
			String contentType)
		throws PortalException {

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId());

		return PortletFileRepositoryUtil.addPortletFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			BlogsEntry.class.getName(), 0, BlogsConstants.SERVICE_NAME,
			folder.getFolderId(), inputStream, fileName, contentType, true);
	}

	@Override
	protected FileEntry fetchFileEntry(
			ThemeDisplay themeDisplay, String fileName)
		throws PortalException {

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId());

		try {
			return PortletFileRepositoryUtil.getPortletFileEntry(
				themeDisplay.getScopeGroupId(), folder.getFolderId(), fileName);
		}
		catch (PortalException pe) {
			return null;
		}
	}

}