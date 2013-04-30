/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.layoutsadmin.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.service.LayoutServiceUtil;

import java.io.IOException;

/**
 * @author Julio Camarero
 */
public class ExportImportUtil {

	public static final String TEMP_FOLDER_NAME =
		ExportImportUtil.class.getName();

	public static FileEntry getTempFileEntry(long groupId, long userId)
		throws IOException, PortalException, SystemException {

		String[] tempFileEntryNames = LayoutServiceUtil.getTempFileEntryNames(
			groupId, TEMP_FOLDER_NAME);

		if (tempFileEntryNames.length == 0) {
			return null;
		}

		return TempFileUtil.getTempFile(
			groupId, userId, tempFileEntryNames[0], TEMP_FOLDER_NAME);
	}

}