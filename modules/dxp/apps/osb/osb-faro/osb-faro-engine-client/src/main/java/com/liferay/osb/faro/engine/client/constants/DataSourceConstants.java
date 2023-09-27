/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.constants;

import com.liferay.osb.faro.engine.client.model.DataSource;
import com.liferay.osb.faro.engine.client.model.DataSourceProgress;
import com.liferay.osb.faro.engine.client.model.provider.CSVProvider;
import com.liferay.osb.faro.engine.client.model.provider.LiferayProvider;
import com.liferay.osb.faro.engine.client.model.provider.SalesforceProvider;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Matthew Kong
 */
public class DataSourceConstants {

	public static Map<String, String> getDisplayStatuses() {
		return _displayStatuses;
	}

	public static Map<String, String> getProgressStatuses() {
		return _progressStatuses;
	}

	public static Map<String, String> getStatuses() {
		return _statuses;
	}

	public static Map<String, String> getTypes() {
		return _types;
	}

	private static final Map<String, String> _displayStatuses =
		HashMapBuilder.put(
			"active", DataSource.Status.ACTIVE.name()
		).put(
			"configuring", DataSource.State.CONFIGURING.name()
		).put(
			"deleteError", DataSource.State.DELETE_ERROR.name()
		).put(
			"inactive", DataSource.Status.INACTIVE.name()
		).put(
			"inDeletion", DataSource.State.IN_DELETION.name()
		).build();
	private static final Map<String, String> _progressStatuses =
		HashMapBuilder.put(
			"completed", DataSourceProgress.Status.COMPLETED.name()
		).put(
			"failed", DataSourceProgress.Status.FAILED.name()
		).put(
			"inProgress", DataSourceProgress.Status.IN_PROGRESS.name()
		).put(
			"started", DataSourceProgress.Status.STARTED.name()
		).build();
	private static final Map<String, String> _statuses = HashMapBuilder.put(
		"active", DataSource.Status.ACTIVE.name()
	).put(
		"inactive", DataSource.Status.INACTIVE.name()
	).build();
	private static final Map<String, String> _types = HashMapBuilder.put(
		"csv", CSVProvider.TYPE
	).put(
		"liferay", LiferayProvider.TYPE
	).put(
		"salesforce", SalesforceProvider.TYPE
	).build();

}