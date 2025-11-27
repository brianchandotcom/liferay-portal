/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.util;

import com.liferay.data.cleanup.DataCleanup;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Maríano Álvaro Sáiz
 */
public class DataCleanupUtil {

	public static List<DataCleanup> getModuleDataCleanups() {
		return Collections.unmodifiableList(_moduleDataCleanups);
	}

	public static List<DataCleanup> getSystemDataCleanups() {
		return Collections.unmodifiableList(_systemDataCleanups);
	}

	public static void registerDataCleanup(DataCleanup dataCleanup) {
		List<DataCleanup> dataCleanups = _getDataCleanups(
			dataCleanup.getType());

		dataCleanups.add(dataCleanup);
	}

	public static void unregisterDataCleanup(DataCleanup dataCleanup) {
		List<DataCleanup> dataCleanups = _getDataCleanups(
			dataCleanup.getType());

		dataCleanups.remove(dataCleanup);
	}

	private static List<DataCleanup> _getDataCleanups(String type) {
		if (StringUtil.equalsIgnoreCase(
				type, DataCleanup.MODULE_DATA_CLEANUP)) {

			return _moduleDataCleanups;
		}
		else if (StringUtil.equalsIgnoreCase(
					type, DataCleanup.SYSTEM_DATA_CLEANUP)) {

			return _systemDataCleanups;
		}

		throw new IllegalArgumentException(
			"Type : " + type + " is not allowed");
	}

	private static final List<DataCleanup> _moduleDataCleanups =
		new CopyOnWriteArrayList<>();
	private static final List<DataCleanup> _systemDataCleanups =
		new CopyOnWriteArrayList<>();

}