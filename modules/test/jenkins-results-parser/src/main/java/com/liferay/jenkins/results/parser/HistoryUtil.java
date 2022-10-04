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

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class HistoryUtil {

	public static BatchHistory getBatchHistory(String batchName, Job job) {
		String ciHistoryJSONURL = _getCIHistoryJSONURL(job);

		Map<String, BatchHistory> batchHistories = _batchHistoriesMap.get(
			ciHistoryJSONURL);

		if (batchHistories == null) {
			_initializeHistory(job);

			batchHistories = _batchHistoriesMap.get(ciHistoryJSONURL);
		}

		if (batchHistories == null) {
			return null;
		}

		if (batchName.endsWith("_stable")) {
			batchName = batchName.replaceAll("(.+)_stable", "$1");
		}

		return batchHistories.get(batchName);
	}

	private static String _getCIHistoryJSONURL(Job job) {
		String jobName = job.getJobName();

		String testSuiteName = null;

		if (job instanceof TestSuiteJob) {
			TestSuiteJob testSuiteJob = (TestSuiteJob)job;

			testSuiteName = testSuiteJob.getTestSuiteName();
		}

		String upstreamBranchName = null;

		if (job instanceof PortalTestClassJob) {
			PortalTestClassJob portalTestClassJob = (PortalTestClassJob)job;

			PortalGitWorkingDirectory portalGitWorkingDirectory =
				portalTestClassJob.getPortalGitWorkingDirectory();

			if (portalGitWorkingDirectory != null) {
				upstreamBranchName =
					portalGitWorkingDirectory.getUpstreamBranchName();
			}
		}

		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"ci.history.json.url", jobName, testSuiteName,
				upstreamBranchName);
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}

		return null;
	}

	private static void _initializeHistory(Job job) {
		String ciHistoryURLString = _getCIHistoryJSONURL(job);

		if (!JenkinsResultsParserUtil.isURL(ciHistoryURLString) ||
			_batchHistoriesMap.containsKey(ciHistoryURLString)) {

			return;
		}

		File tempGzipFile = new File(
			System.getenv("WORKSPACE"),
			JenkinsResultsParserUtil.getDistinctTimeStamp() + ".gz");

		Map<String, BatchHistory> batchHistories = new HashMap<>();

		try {
			JenkinsResultsParserUtil.toFile(
				new URL(ciHistoryURLString), tempGzipFile);

			String content = JenkinsResultsParserUtil.read(tempGzipFile);

			if (JenkinsResultsParserUtil.isNullOrEmpty(content)) {
				return;
			}

			_batchHistoriesMap.put(
				ciHistoryURLString, new HashMap<String, BatchHistory>());

			JSONObject historyJSONObject = new JSONObject(content);

			JSONArray batchesJSONArray = historyJSONObject.optJSONArray(
				"batches");

			if ((batchesJSONArray == JSONObject.NULL) ||
				batchesJSONArray.isEmpty()) {

				return;
			}

			for (int i = 0; i < batchesJSONArray.length(); i++) {
				BatchHistory batchHistory = new BatchHistory(
					batchesJSONArray.getJSONObject(i));

				batchHistories.put(batchHistory.getBatchName(), batchHistory);
			}
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
		finally {
			if (tempGzipFile.exists()) {
				JenkinsResultsParserUtil.delete(tempGzipFile);
			}

			_batchHistoriesMap.put(ciHistoryURLString, batchHistories);
		}
	}

	private static final Map<String, Map<String, BatchHistory>>
		_batchHistoriesMap = new HashMap<>();

}