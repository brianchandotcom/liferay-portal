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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.service.ServiceContext;

import java.io.InputStream;

/**
 * @author Sergio González
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
public class TempImageBlogsUploadHandler extends BaseBlogsUploadHandler {

	@Override
	protected FileEntry addFileEntry(
			long userId, long groupId, long folderId, String fileName,
			String contentType, InputStream inputStream, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return TempFileEntryUtil.addTempFileEntry(
			groupId, userId, _TEMP_FOLDER_NAME, fileName, inputStream,
			contentType);
	}

	@Override
	protected FileEntry fetchFileEntry(
		long userId, long groupId, long folderId, String fileName) {

		try {
			return TempFileEntryUtil.getTempFileEntry(
				groupId, userId, _TEMP_FOLDER_NAME, fileName);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TempImageBlogsUploadHandler.class);

}