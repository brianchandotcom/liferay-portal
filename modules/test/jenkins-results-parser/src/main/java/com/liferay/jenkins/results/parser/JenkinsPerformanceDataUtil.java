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
			String buildName, String jenkinsJobURL, int reportSize)
		throws Exception {

		if (jenkinsJobURL.contains("-source")) {
			JSONObject sourceJobJSONObject =
				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						jenkinsJobURL + "/api/json"));
			resultsList.add(new Result(buildName, sourceJobJSONObject));
		}
		else {
			JSONObject jobJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					jenkinsJobURL + "/testReport/api/json"));

			List<Result> resultList = getLongestResults(
				buildName, jobJSONObject, reportSize);

			resultsList.addAll(resultList);
		}

		Collections.sort(resultsList);

		truncateList(resultsList, reportSize);
	}

	public static class Result implements Comparable<Result> {

		public Result(String buildName, JSONObject sourceJobJSONObject)
			throws Exception {

			axis = "";
			batch = buildName;
			className = "";
			duration = sourceJobJSONObject.getInt("duration") / 1000;
			name = sourceJobJSONObject.getString("fullDisplayName");
			status = sourceJobJSONObject.getString("result");
			url = sourceJobJSONObject.getString("url");
		}

		public Result(
				String buildName, JSONObject caseJSONObject,
				JSONObject childJSONObject)
			throws Exception {

			batch = buildName;

			setAxis(childJSONObject);
			setCaseInfo(caseJSONObject);
			setUrl(childJSONObject);
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

		public JSONObject toJSONObject() {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("axis", axis);
			jsonObject.put("batch", batch);
			jsonObject.put("className", className);
			jsonObject.put("duration", duration);
			jsonObject.put("name", name);
			jsonObject.put("status", status);

			return jsonObject;
		}

		public String toString() {
			return toJSONObject().toString(4);
		}

		protected void setAxis(JSONObject childJSONObject) throws Exception {
			String urlString = childJSONObject.getString("url");

			urlString = URLDecoder.decode(urlString, "UTF-8");

			int x = urlString.indexOf("AXIS_VARIABLE");

			urlString = urlString.substring(x);

			int y = urlString.indexOf(",");

			axis = urlString.substring(0, y);
		}

		protected void setCaseInfo(JSONObject caseObject) {
			className = caseObject.getString("className");
			duration = caseObject.getInt("duration");
			name = caseObject.getString("name");
			status = caseObject.getString("status");
		}

		protected void setUrl(JSONObject childJSONObject) throws Exception {
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

	protected static List<Result> getLongestResults(
			String buildName, JSONObject jobJSONObject, int maxSize)
		throws Exception {

		JSONArray childReportsJSONArray = jobJSONObject.getJSONArray(
			"childReports");
		List<Result> resultList = new ArrayList<>();

		for (int i = 0; i < childReportsJSONArray.length(); i++) {
			JSONObject childReportJSONObject =
				childReportsJSONArray.getJSONObject(i);

			JSONObject childJSONObject = childReportJSONObject.getJSONObject(
				"child");

			JSONObject resultJSONObject = childReportJSONObject.getJSONObject(
				"result");

			JSONArray suitesJSONArray = resultJSONObject.getJSONArray("suites");

			for (int j = 0; j < suitesJSONArray.length(); j++) {
				JSONObject suiteJSONObject = suitesJSONArray.getJSONObject(j);

				JSONArray casesJSONArray = suiteJSONObject.getJSONArray(
					"cases");

				for (int k = 0; k < casesJSONArray.length(); k++) {
					JSONObject caseJSONObject = casesJSONArray.getJSONObject(k);

					Result result = new Result(
						buildName, caseJSONObject, childJSONObject);

					resultList.add(result);
				}
			}
		}

		Collections.sort(resultList);

		truncateList(resultList, maxSize);

		return resultList;
	}

	protected static void truncateList(List<Result> list, int maxSize) {
		if (list.size() < maxSize) {
			return;
		}

		List<Result> subList = list.subList(maxSize, list.size());

		subList.clear();
	}

	protected static final List<Result> resultsList = new ArrayList<>();

}