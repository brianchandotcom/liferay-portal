/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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