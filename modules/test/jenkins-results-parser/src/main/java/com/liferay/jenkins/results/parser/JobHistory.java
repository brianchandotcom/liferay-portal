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

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JobHistory {

	public BatchHistory getBatchHistory(String batchName) {
		Matcher matcher = _pattern.matcher(batchName);

		if (matcher.find()) {
			batchName = matcher.group("batchName");
		}

		return _batchHistories.get(batchName);
	}

	public URL getTestrayURL() {
		return _testrayURL;
	}

	public String getUpstreamBranchName() {
		return _upstreamBranchName;
	}

	protected JobHistory(URL ciHistoryURL) {
		JSONObject ciHistoryJSONObject = _getCIHistoryJSONObject(ciHistoryURL);

		if (ciHistoryJSONObject == null) {
			_testrayURL = null;
			_upstreamBranchName = null;

			return;
		}

		JSONArray batchesJSONArray = ciHistoryJSONObject.optJSONArray(
			"batches");

		if (batchesJSONArray != JSONObject.NULL) {
			for (int i = 0; i < batchesJSONArray.length(); i++) {
				BatchHistory batchHistory = new BatchHistory(
					this, batchesJSONArray.getJSONObject(i));

				_batchHistories.put(batchHistory.getBatchName(), batchHistory);
			}
		}

		URL testrayURL = null;

		try {
			String testrayURLString = ciHistoryJSONObject.optString(
				"testray_url", "https://testray.liferay.com");

			if (JenkinsResultsParserUtil.isURL(testrayURLString)) {
				testrayURL = new URL(testrayURLString);
			}
		}
		catch (MalformedURLException malformedURLException) {
		}

		_testrayURL = testrayURL;

		_upstreamBranchName = ciHistoryJSONObject.optString(
			"upstream_branch_name");
	}

	private JSONObject _getCIHistoryJSONObject(URL ciHistoryURL) {
		if (ciHistoryURL == null) {
			return null;
		}

		File tempGzipFile = new File(
			System.getenv("WORKSPACE"),
			JenkinsResultsParserUtil.getDistinctTimeStamp() + ".gz");

		try {
			JenkinsResultsParserUtil.toFile(ciHistoryURL, tempGzipFile);

			String content = JenkinsResultsParserUtil.read(tempGzipFile);

			if (JenkinsResultsParserUtil.isNullOrEmpty(content)) {
				return null;
			}

			return new JSONObject(content);
		}
		catch (IOException ioException) {
			return null;
		}
		finally {
			if (tempGzipFile.exists()) {
				JenkinsResultsParserUtil.delete(tempGzipFile);
			}
		}
	}

	private static final Pattern _pattern = Pattern.compile(
		"(?<batchName>.+)_stable");

	private final Map<String, BatchHistory> _batchHistories = new HashMap<>();
	private final URL _testrayURL;
	private final String _upstreamBranchName;

}