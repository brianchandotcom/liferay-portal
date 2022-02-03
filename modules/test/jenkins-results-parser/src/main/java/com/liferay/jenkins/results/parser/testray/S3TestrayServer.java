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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestrayResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.io.File;

import java.util.Date;

/**
 * @author Michael Hashimoto
 */
public class S3TestrayServer extends BaseTestrayServer {

	public S3TestrayServer(String urlString) {
		super(urlString);
	}

	@Override
	public void importCaseResults(TopLevelBuild topLevelBuild) {
		File resultsDir = getResultsDir();

		File resultsTarGzFile = new File(
			resultsDir.getParentFile(), "results.tar.gz");

		TestrayResultsParserUtil.processTestrayResultFiles(resultsDir);

		JenkinsResultsParserUtil.tarGzip(resultsDir, resultsTarGzFile);

		TestrayS3Bucket testrayS3Bucket = TestrayS3Bucket.getInstance();

		String relativeBuildDirPath = _getRelativeBuildDirPath(topLevelBuild);

		testrayS3Bucket.createTestrayS3Object(
			relativeBuildDirPath + "/results.tar.gz", resultsTarGzFile);

		testrayS3Bucket.createTestrayS3Object(
			relativeBuildDirPath + "/test-completed", "");
	}

	private String _getRelativeBuildDirPath(TopLevelBuild topLevelBuild) {
		StringBuilder sb = new StringBuilder();

		Date topLevelStartDate = new Date(topLevelBuild.getStartTime());

		sb.append(
			JenkinsResultsParserUtil.toDateString(
				topLevelStartDate, "yyyy-MM", "America/Los_Angeles"));

		JenkinsMaster jenkinsMaster = topLevelBuild.getJenkinsMaster();

		sb.append("/");
		sb.append(jenkinsMaster.getName());
		sb.append("/");
		sb.append(topLevelBuild.getJobName());
		sb.append("/");
		sb.append(topLevelBuild.getBuildNumber());

		return sb.toString();
	}

}