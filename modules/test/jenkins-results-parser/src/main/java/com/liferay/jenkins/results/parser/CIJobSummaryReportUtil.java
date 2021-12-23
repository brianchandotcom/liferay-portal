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

import java.io.File;
import java.io.IOException;

/**
 * @author Michael Hashimoto
 */
public class CIJobSummaryReportUtil {

	public static void writeJobSummaryReport(File summaryDir, Job job)
		throws IOException {

		if (!summaryDir.exists()) {
			summaryDir.mkdirs();
		}

		JenkinsResultsParserUtil.write(
			new File(summaryDir, "js/job-data.js"),
			"data=" + job.getJSONObject());

		JenkinsResultsParserUtil.write(
			new File(summaryDir, "index.html"),
			JenkinsResultsParserUtil.getResourceFileContent(
				"dependencies/job/summary/index.html"));

		JenkinsResultsParserUtil.write(
			new File(summaryDir, "css/main.css"),
			JenkinsResultsParserUtil.getResourceFileContent(
				"dependencies/job/summary/css/main.css"));

		JenkinsResultsParserUtil.write(
			new File(summaryDir, "js/main.js"),
			JenkinsResultsParserUtil.getResourceFileContent(
				"dependencies/job/summary/js/main.js"));
	}

}