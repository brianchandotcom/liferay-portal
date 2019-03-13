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

package com.liferay.bulk.rest.internal.util;

import com.liferay.bulk.rest.dto.v1_0.SelectionScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class ParameterMapUtil {

	public static Map<String, String[]> getParameterMap(
		String[] rowIdsFileEntry, SelectionScope selectionScope) {

		if (selectionScope.getRepositoryId() == 0) {
			return Collections.singletonMap("rowIdsFileEntry", rowIdsFileEntry);
		}

		return new HashMap<String, String[]>() {
			{
				put(
					"folderId",
					new String[] {
						String.valueOf(selectionScope.getFolderId())
					});
				put(
					"repositoryId",
					new String[] {
						String.valueOf(selectionScope.getRepositoryId())
					});
				put("rowIdsFileEntry", rowIdsFileEntry);
				put(
					"selectAll",
					new String[] {
						Boolean.toString(selectionScope.getSelectAll())
					});
			}
		};
	}

}