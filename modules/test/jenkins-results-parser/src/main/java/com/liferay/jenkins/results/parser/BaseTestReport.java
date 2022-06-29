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

package com.liferay.jenkins.results.parser;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTestReport implements TestReport {

	@Override
	public DownstreamBuildReport getDownstreamBuildReport() {
		return _downstreamBuildReport;
	}

	@Override
	public long getDuration() {
		return _jsonObject.getLong("duration");
	}

	@Override
	public String getErrorDetails() {
		return _jsonObject.optString("errorDetails");
	}

	@Override
	public String getStatus() {
		return _jsonObject.getString("status");
	}

	@Override
	public String getTestName() {
		return _jsonObject.getString("name");
	}

	protected BaseTestReport(
		DownstreamBuildReport downstreamBuildReport, JSONObject jsonObject) {

		_downstreamBuildReport = downstreamBuildReport;
		_jsonObject = jsonObject;
	}

	private final DownstreamBuildReport _downstreamBuildReport;
	private final JSONObject _jsonObject;

}