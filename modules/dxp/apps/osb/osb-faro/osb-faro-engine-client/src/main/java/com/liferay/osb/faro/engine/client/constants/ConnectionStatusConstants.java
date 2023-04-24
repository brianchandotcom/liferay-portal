/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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