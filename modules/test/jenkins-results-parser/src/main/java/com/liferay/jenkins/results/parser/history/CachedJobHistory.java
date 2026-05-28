/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import com.liferay.jenkins.results.parser.CloudBucketUtil;
import com.liferay.jenkins.results.parser.Env;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CachedJobHistory extends BaseJobHistory {

	@Override
	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	@Override
	public URL getTestrayURL() {
		if (_testrayURL != null) {
			return _testrayURL;
		}

		if (_jsonObject == null) {
			return null;
		}

		try {
			String testrayURLString = _jsonObject.optString(
				"testray_url", "https://testray.liferay.com");

			if (JenkinsResultsParserUtil.isURL(testrayURLString)) {
				_testrayURL = new URL(testrayURLString);

				return _testrayURL;
			}
		}
		catch (MalformedURLException malformedURLException) {
		}

		return null;
	}

	protected CachedJobHistory(String portalUpstreamBranchName) {
		super(portalUpstreamBranchName);

		_jsonObject = _getJSONObject();

		JSONArray batchesJSONArray = _jsonObject.optJSONArray("batches");

		if ((batchesJSONArray == null) || batchesJSONArray.isEmpty()) {
			return;
		}

		for (int i = 0; i < batchesJSONArray.length(); i++) {
			JSONObject batchJSONObject = batchesJSONArray.getJSONObject(i);

			addBatchHistory(
				HistoryFactory.newBatchHistory(
					batchJSONObject.getString("batchName"), this,
					batchesJSONArray.getJSONObject(i)));
		}
	}

	private String _getCIHistoryURL() {
		try {
			String ciHistoryJSONURL = JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"ci.history.json.url", getPortalUpstreamBranchName());

			if (JenkinsResultsParserUtil.isNullOrEmpty(ciHistoryJSONURL)) {
				return null;
			}

			return ciHistoryJSONURL;
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}

		return null;
	}

	private JSONObject _getJSONObject() {
		String ciHistoryURL = _getCIHistoryURL();

		if (ciHistoryURL == null) {
			return new JSONObject();
		}

		File tempGzipFile = new File(
			Env.get("WORKSPACE"),
			JenkinsResultsParserUtil.getDistinctTimeStamp() + ".gz");

		try {
			if (ciHistoryURL.startsWith(
					CloudBucketUtil.GCP_BUCKET_PATH_JENKINS_CI_DATA) ||
				ciHistoryURL.startsWith(
					CloudBucketUtil.GCP_BUCKET_PATH_PATCHER_SHARED) ||
				ciHistoryURL.startsWith(
					CloudBucketUtil.GCP_BUCKET_PATH_TESTRAY_RESULTS)) {

				CloudBucketUtil.copyGCPFile(
					JenkinsResultsParserUtil.getCanonicalPath(tempGzipFile),
					ciHistoryURL);
			}
			else {
				JenkinsResultsParserUtil.toFile(
					new URL(ciHistoryURL), tempGzipFile);
			}

			String content = JenkinsResultsParserUtil.read(tempGzipFile);

			if (JenkinsResultsParserUtil.isNullOrEmpty(content)) {
				return new JSONObject();
			}

			return new JSONObject(content);
		}
		catch (IOException | JSONException exception) {
			return new JSONObject();
		}
		finally {
			if (tempGzipFile.exists()) {
				JenkinsResultsParserUtil.delete(tempGzipFile);
			}
		}
	}

	private final JSONObject _jsonObject;
	private URL _testrayURL;

}