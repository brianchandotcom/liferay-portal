/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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