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

import com.liferay.jenkins.results.parser.testray.TestrayS3Object;

import java.net.URL;

import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public interface TopLevelBuildReport extends BuildReport {

	public Map<String, String> getBuildParameters();

	public URL getBuildReportJSONTestrayURL();

	public URL getBuildReportJSONUserContentURL();

	public TestrayS3Object getBuildReportTestrayS3Object();

	public URL getBuildResultJSONTestrayURL();

	public URL getBuildResultJSONUserContentURL();

	public TestrayS3Object getBuildResultTestrayS3Object();

	public List<DownstreamBuildReport> getDownstreamBuildReports();

	public URL getTestResultsJSONUserContentURL();

	public String getTestSuiteName();

	public long getTopLevelActiveDuration();

	public long getTopLevelPassiveDuration();

}