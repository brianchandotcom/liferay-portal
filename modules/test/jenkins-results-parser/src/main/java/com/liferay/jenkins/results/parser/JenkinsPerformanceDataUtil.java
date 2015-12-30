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

import java.io.FileNotFoundException;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class JenkinsPerformanceDataUtil {

	public static List<Result> getSlowestResults() {
		return _results;
	}

	public static void processPerformanceData(
			String jobName, String url, int size)
		throws Exception {

		JSONObject jsonObject = null;

		if (url.contains("-source")) {
			jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(url + "/api/json"), false);

			_results.add(new Result(jobName, jsonObject));

			Collections.sort(_results);

			_truncate(_results, size);

			return;
		}

		int retryCount = 0;

		while (true) {
			try {
				jsonObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						url + "/testReport/api/json"),
					false);
			}
			catch (FileNotFoundException fnfe) {
				jsonObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(url + "/api/json"),
					false);

				_results.add(new Result(jobName, jsonObject));

				break;
			}

			try {
				_results.addAll(_getSlowestResults(jobName, jsonObject, size));

				break;
			}
			catch (IllegalArgumentException iae) {
				String message = iae.getMessage();

				if (!message.contains("Result is not available for ")) {
					throw iae;
				}

				retryCount++;

				if (retryCount > 5) {
					System.out.println("Exceeded max retries");

					throw iae;
				}

				System.out.println("Retry in 60 seconds: " + message);

				Thread.sleep(60 * 1000);
			}
		}

		Collections.sort(_results);

		_truncate(_results, size);
	}

	public static void reset() {
		_results.clear();
	}

	public static class Result implements Comparable<Result> {

		public Result(
				JSONObject caseJSONObject, JSONObject childJSONObject,
				String jobName)
			throws Exception {

			_jobName = jobName;

			_className = caseJSONObject.getString("className");
			_duration = caseJSONObject.getInt("duration");
			_name = caseJSONObject.getString("name");
			_status = caseJSONObject.getString("status");

			_setAxis(childJSONObject);
			_setUrl(childJSONObject);
		}

		public Result(String jobName, JSONObject sourceJSONObject)
			throws Exception {

			_axis = "";
			_className = "";
			_duration = sourceJSONObject.getInt("duration") / 1000;
			_jobName = jobName;
			_name = sourceJSONObject.getString("fullDisplayName");
			_status = sourceJSONObject.getString("result");
			_url = sourceJSONObject.getString("url");
		}

		@Override
		public int compareTo(Result result) {
			return -1 * Float.compare(getDuration(), result.getDuration());
		}

		public String getAxis() {
			return _axis;
		}

		public String getClassName() {
			return _className;
		}

		public float getDuration() {
			return _duration;
		}

		public String getJobName() {
			return _jobName;
		}

		public String getName() {
			return _name;
		}

		public String getStatus() {
			return _status;
		}

		public String getUrl() {
			return _url;
		}

		private void _setAxis(JSONObject childJSONObject) throws Exception {
			String _url = childJSONObject.getString("url");

			_url = URLDecoder.decode(_url, "UTF-8");

			int x = _url.indexOf("AXIS_VARIABLE");

			_url = _url.substring(x);

			int y = _url.indexOf(",");

			_axis = _url.substring(0, y);
		}

		private void _setUrl(JSONObject childJSONObject) throws Exception {
			String urlString = URLDecoder.decode(
				childJSONObject.getString("url"), "UTF-8");

			StringBuilder sb = new StringBuilder(urlString);

			sb.append("testReport/");

			int x = _className.lastIndexOf(".");

			sb.append(_className.substring(0, x));
			sb.append("/");
			sb.append(_className.substring(x + 1));
			sb.append("/");

			if (_className.contains("poshi")) {
				String poshiName = _name;

				poshiName = poshiName.replaceAll("\\[", "_");
				poshiName = poshiName.replaceAll("\\]", "_");
				poshiName = poshiName.replaceAll("#", "_");

				sb.append(poshiName);

				sb.append("/");
			}
			else {
				sb.append(_name);
			}

			URL url = JenkinsResultsParserUtil.createURL(sb.toString());

			URI uri = url.toURI();

			_url = uri.toASCIIString();
		}

		private String _axis;
		private final String _className;
		private final int _duration;
		private final String _jobName;
		private final String _name;
		private final String _status;
		private String _url;

	}

	private static List<Result> _getSlowestResults(
			String name, JSONObject jobJSONObject, int maxSize)
		throws Exception {

		List<Result> results = new ArrayList<>();

		JSONArray childReportsJSONArray = jobJSONObject.getJSONArray(
			"childReports");

		for (int i = 0; i < childReportsJSONArray.length(); i++) {
			JSONObject childReportJSONObject =
				childReportsJSONArray.getJSONObject(i);

			JSONObject childJSONObject = childReportJSONObject.getJSONObject(
				"child");

			if (!childReportJSONObject.has("result") ||
				childReportJSONObject.isNull("result")) {

				throw new IllegalArgumentException(
					"Result is not available for " +
						childJSONObject.getString("url"));
			}

			JSONObject childResultJSONObject =
				childReportJSONObject.getJSONObject("result");

			JSONArray suitesJSONArray = childResultJSONObject.getJSONArray(
				"suites");

			for (int j = 0; j < suitesJSONArray.length(); j++) {
				JSONObject suiteJSONObject = suitesJSONArray.getJSONObject(j);

				JSONArray casesJSONArray = suiteJSONObject.getJSONArray(
					"cases");

				for (int k = 0; k < casesJSONArray.length(); k++) {
					JSONObject caseJSONObject = casesJSONArray.getJSONObject(k);

					Result result = new Result(
						caseJSONObject, childJSONObject, name);

					results.add(result);
				}
			}
		}

		Collections.sort(results);

		_truncate(results, maxSize);

		return results;
	}

	private static void _truncate(List<?> list, int maxSize) {
		if (list.size() < maxSize) {
			return;
		}

		List<?> subList = list.subList(maxSize, list.size());

		subList.clear();
	}

	private static final List<Result> _results = new ArrayList<>();

}