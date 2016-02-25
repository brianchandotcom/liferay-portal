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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.io.File;
import java.io.InputStream;

/**
 * @author     Sergio González
 * @author     Matthew Kong
 * @author     Alexander Chow
 * @author     Iván Zaera
 * @deprecated As of 7.0.0, replaced by {@link TempFileEntryUtil}
 */
@Deprecated
public class TempFileUtil {

	public static FileEntry addTempFileEntry(
			long groupId, long userId, String fileName, String tempFolderName,
			File file, String mimeType)
		throws PortalException {

		return TempFileEntryUtil.addTempFileEntry(
			groupId, userId, fileName, tempFolderName, file, mimeType);
	}

	public static FileEntry addTempFileEntry(
			long groupId, long userId, String fileName, String tempFolderName,
			InputStream inputStream, String mimeType)
		throws PortalException {

		return TempFileEntryUtil.addTempFileEntry(
			groupId, userId, fileName, tempFolderName, inputStream, mimeType);
	}

	public static void deleteTempFileEntry(long fileEntryId)
		throws PortalException {

		TempFileEntryUtil.deleteTempFileEntry(fileEntryId);
	}

	public static void deleteTempFileEntry(
			long groupId, long userId, String folderName, String fileName)
		throws PortalException {

		TempFileEntryUtil.deleteTempFileEntry(
			groupId, userId, folderName, fileName);
	}

	public static FileEntry getTempFileEntry(
			long groupId, long userId, String folderName, String fileName)
		throws PortalException {

		return TempFileEntryUtil.getTempFileEntry(
			groupId, userId, folderName, fileName);
	}

	public static String[] getTempFileNames(
			long groupId, long userId, String folderName)
		throws PortalException {

		return TempFileEntryUtil.getTempFileNames(groupId, userId, folderName);
	}

}