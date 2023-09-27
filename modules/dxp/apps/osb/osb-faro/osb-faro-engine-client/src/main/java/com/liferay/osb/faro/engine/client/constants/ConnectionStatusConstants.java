/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Shinn Lok
 */
public class ConnectionStatusConstants {

	public static final int STATUS_NOT_SYNCING = 3;

	public static final int STATUS_SYNCED = 1;

	public static final int STATUS_SYNCING = 2;

	public static final int STATUS_UNCONFIGURED = 0;

	public static Map<String, Integer> getStatuses() {
		return _statuses;
	}

	private static final Map<String, Integer> _statuses = HashMapBuilder.put(
		"notSyncing", STATUS_NOT_SYNCING
	).put(
		"synced", STATUS_SYNCED
	).put(
		"syncing", STATUS_SYNCING
	).put(
		"unconfigured", STATUS_UNCONFIGURED
	).build();

}