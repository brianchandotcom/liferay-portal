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

package com.liferay.document.library.internal.bulk.selection.util;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

/**
 * @author Alejandro Tardín
 */
public class BulkSelectionFactoryUtil {

	public static long getFolderId(Map<String, String[]> parameterMap) {
		return GetterUtil.getLongValues(
			parameterMap.get("folderId"),
			new long[] {DLFolderConstants.DEFAULT_PARENT_FOLDER_ID})[0];
	}

	public static long getRepositoryId(Map<String, String[]> parameterMap) {
		if (!parameterMap.containsKey("repositoryId")) {
			throw new IllegalArgumentException();
		}

		String[] repositoryIds = parameterMap.get("repositoryId");

		long repositoryId = GetterUtil.getLong(repositoryIds[0]);

		if (repositoryId == 0) {
			throw new IllegalArgumentException();
		}

		return repositoryId;
	}

	public static boolean isSelectAll(Map<String, String[]> parameterMap) {
		return MapUtil.getBoolean(parameterMap, "selectAll");
	}

}