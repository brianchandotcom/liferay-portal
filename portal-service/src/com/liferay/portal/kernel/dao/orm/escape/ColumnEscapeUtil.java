/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.orm.escape;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.io.IOException;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
class ColumnEscapeUtil {

	static String escape(String columnName) {
		String escapedColumnName = _escapeMap.get(columnName);

		if (escapedColumnName == null) {
			escapedColumnName = columnName;
		}

		return escapedColumnName;
	}

	private static final String _BAD_COLUMN_NAMES_FILE_PATH =
		"com/liferay/portal/tools/servicebuilder/dependencies/" +
			"bad_column_names.txt";

	private static final Map<String, String> _escapeMap =
		new HashMap<String, String>();

	static {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(
				classLoader.getResourceAsStream(_BAD_COLUMN_NAMES_FILE_PATH)));

		String line = null;

		try {
			while ((line = unsyncBufferedReader.readLine()) != null) {
				_escapeMap.put(line, line.concat("_"));
			}

			unsyncBufferedReader.close();
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}