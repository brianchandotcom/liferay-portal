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