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

package com.liferay.batch.engine.core.internal.item.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class ColumnUtil {

	@SuppressWarnings("unchecked")
	public static void handleLocalizationColumn(
		String columnName, String value, Map<String, Object> columnNameValueMap,
		int lastDelimiterIndex) {

		String language = columnName.substring(lastDelimiterIndex + 1);

		columnName = columnName.substring(0, lastDelimiterIndex);

		Map<String, String> localizationMap =
			(Map<String, String>)columnNameValueMap.get(columnName);

		if (localizationMap == null) {
			localizationMap = new HashMap<>();

			columnNameValueMap.put(columnName, localizationMap);
		}

		localizationMap.put(language, value);
	}

}