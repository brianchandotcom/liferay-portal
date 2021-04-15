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

package com.liferay.content.dashboard.web.internal.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Alejandro Tardín
 */
public class ContentDashboardSearchClassNameUtil {

	public static String getClassName(String searchClassName) {
		return _searchClassNamesMap.getOrDefault(
			searchClassName, searchClassName);
	}

	public static String getSearchClassName(String className) {
		return _classNamesMap.getOrDefault(className, className);
	}

	private static final Map<String, String> _classNamesMap =
		HashMapBuilder.put(
			FileEntry.class.getName(), DLFileEntry.class.getName()
		).build();
	private static final Map<String, String> _searchClassNamesMap =
		HashMapBuilder.put(
			DLFileEntry.class.getName(), FileEntry.class.getName()
		).build();

}