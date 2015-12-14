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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class JenkinsPerformanceDataProcessor {

	public static void processPerformanceData(
			String buildName, String jenkinsJobURL, int reportSize)
		throws Exception {

		JSONObject jobJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(
				jenkinsJobURL + "testReport/api/json"));

		if (jobJSONObject != null) {
			List<Result> resultList = getLongestResults(
				buildName, jobJSONObject, reportSize);

			resultsList.addAll(resultList);

			Collections.sort(resultsList);

			truncateList(resultsList, reportSize);
		}
		else {
			System.out.println(
				"JSON data could not be loaded. URL: " + jenkinsJobURL +
					"testReport/api/json");
		}
	}

	protected static List<Result> getLongestResults(
			String buildName, JSONObject jobJSONObject, int resultCount)
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

					Result result =
						new Result(
							buildName, caseJSONObject, childJSONObject);

					resultList.add(result);
				}
			}
		}

		Collections.sort(resultList);

		truncateList(resultList, resultCount);

		return resultList;
	}

	protected static void truncateList(
		List<Result> list, int maxSize) {

		while (list.size() > maxSize) {
			list.remove(list.size() - 1);
		}
	}

	protected static final List<Result> resultsList =
		new ArrayList<>();

}