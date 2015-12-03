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

	public static void processPerformanceData(
			String batch, String url, int size)
		throws Exception {

		if (url.contains("-source")) {
			JSONObject sourceJSON =
				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(url + "/api/json"));
			
			results.add(new Result(batch, sourceJSON));
		}
		else {
			JSONObject json = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					url + "/testReport/api/json"));

			results.addAll(getLongestResults(batch, json, size));
		}

		Collections.sort(results);

		truncate(results, size);
	}

	public static class Result implements Comparable<Result> {

		public Result(String batch, JSONObject sourceJSON)
			throws Exception {

			axis = "";
			this.batch = batch;
			className = "";
			duration = sourceJSON.getInt("duration") / 1000;
			name = sourceJSON.getString("fullDisplayName");
			status = sourceJSON.getString("result");
			url = sourceJSON.getString("url");
		}

		public Result(String batch, JSONObject caze, JSONObject child)
			throws Exception {

			this.batch = batch;
			className = caze.getString("className");
			duration = caze.getInt("duration");
			name = caze.getString("name");
			status = caze.getString("status");

			setAxis(child);
			setUrl(child);
		}

		public int compareTo(Result result) {
			return -1 * Float.compare(getDuration(), result.getDuration());
		}

		public String getAxis() {
			return axis;
		}

		public String getBatch() {
			return batch;
		}

		public String getClassName() {
			return className;
		}

		public float getDuration() {
			return duration;
		}

		public String getName() {
			return name;
		}

		public String getStatus() {
			return status;
		}

		public String getUrl() {
			return url;
		}

		private void setAxis(JSONObject childJSONObject) throws Exception {
			String url = childJSONObject.getString("url");

			url = URLDecoder.decode(url, "UTF-8");

			int x = url.indexOf("AXIS_VARIABLE");

			url = url.substring(x);

			int y = url.indexOf(",");

			axis = url.substring(0, y);
		}

		private void setUrl(JSONObject childJSONObject) throws Exception {
			String urlString = URLDecoder.decode(
				childJSONObject.getString("url"), "UTF-8");

			StringBuilder sb = new StringBuilder(urlString);

			sb.append("testReport/");

			int x = className.lastIndexOf(".");

			sb.append(className.substring(0, x));
			sb.append("/");
			sb.append(className.substring(x + 1));
			sb.append("/");

			if (className.contains("poshi")) {
				String poshiName = name;

				poshiName = poshiName.replaceAll("\\[", "_");
				poshiName = poshiName.replaceAll("\\]", "_");
				poshiName = poshiName.replaceAll("#", "_");

				sb.append(poshiName);
				sb.append("/");
			}
			else {
				sb.append(name);
			}

			URL urlObject = JenkinsResultsParserUtil.createURL(sb.toString());

			URI uri = urlObject.toURI();

			url = uri.toASCIIString();
		}

		protected String axis;
		protected String batch;
		protected String className;
		protected int duration;
		protected String name;
		protected String status;
		protected String url;

	}

	private static List<Result> getLongestResults(
			String name, JSONObject job, int maxSize)
		throws Exception {

		JSONArray childReports = job.getJSONArray(
			"childReports");
		List<Result> results = new ArrayList<>();

		for (int i = 0; i < childReports.length(); i++) {

			JSONObject childReport = childReports.getJSONObject(i);

			JSONObject child = childReport.getJSONObject("child");

			JSONObject childReportResult = childReport.getJSONObject("result");

			JSONArray suites = childReportResult.getJSONArray("suites");

			for (int j = 0; j < suites.length(); j++) {

				JSONObject suite = suites.getJSONObject(j);

				JSONArray cases = suite.getJSONArray(
					"cases");

				for (int k = 0; k < cases.length(); k++) {
					
					JSONObject caze = cases.getJSONObject(k);

					Result result = new Result(
						name, caze, child);

					results.add(result);
				}
			}
		}

		Collections.sort(results);

		truncate(results, maxSize);

		return results;
	}

	private static void truncate(List<?> list, int maxSize) {
		if (list.size() < maxSize) {
			return;
		}

		List<?> subList = list.subList(maxSize, list.size());

		subList.clear();
	}

	public static List<Result> getLongestResults() {
		return results;
	}
	
	public static void reset() {
		results.clear();
	}
	
	private static List<Result> results = new ArrayList<>();

}