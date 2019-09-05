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