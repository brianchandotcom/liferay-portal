/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.snapshot;

/**
 * @author Michael C. Han
 */
public class SnapshotRepositoryDetails {

	public static final String FS_REPOSITORY_TYPE = "fs";

	public SnapshotRepositoryDetails(
		String name, String type, String settingsJSON) {

		_name = name;
		_type = type;
		_settingsJSON = settingsJSON;
	}

	public String getName() {
		return _name;
	}

	public String getSettingsJSON() {
		return _settingsJSON;
	}

	public String getType() {
		return _type;
	}

	private final String _name;
	private final String _settingsJSON;
	private final String _type;

}