/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestTaskReport implements TestTaskReport {

	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public boolean hasRun() {
		if (getDuration() >= 0) {
			return true;
		}

		return false;
	}

	protected BaseTestTaskReport(JSONObject jsonObject) {
		_duration = jsonObject.optLong("duration");
		_name = jsonObject.getString("name");
	}

	private final long _duration;
	private final String _name;

}