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

import com.liferay.jenkins.results.parser.testray.TestrayBuild;
import com.liferay.jenkins.results.parser.testray.TestrayRoutine;

import java.io.IOException;

import java.text.NumberFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CISystemHistoryReportUtil {

	public static void writeTestrayDataJavaScriptFile(
			String filePath, TestrayRoutine testrayRoutine, String nameFilter)
		throws IOException {

		List<Callable<String>> callables = new ArrayList<>();

		for (String month : _recentTestrayBuilds.keySet()) {
			List<TestrayBuild> builds = testrayRoutine.getTestrayBuilds(
				2500, month, nameFilter);

			_recentTestrayBuilds.put(month, builds);

			for (final TestrayBuild testrayBuild : builds) {
				callables.add(
					new Callable<String>() {

						@Override
						public String call() throws Exception {
							return testrayBuild.getResult();
						}

					});
			}
		}

		ParallelExecutor<String> parallelExecutor = new ParallelExecutor<>(
			callables, _executorService);

		parallelExecutor.execute();

		StringBuilder sb = new StringBuilder();

		sb.append("var topLevelTotalBuildDurationData = ");
		sb.append(_getTopLevelTotalBuildDurationJSONObject());

		sb.append("\nvar topLevelActiveBuildDurationData = ");
		sb.append(_getTopLevelActiveBuildDurationJSONObject());

		sb.append("\nvar downstreamBuildDurationData = ");
		sb.append(_getDownstreamBuildDurationJSONObject());

		sb.append("\nvar testrayDataGeneratedDate = new Date(");
		sb.append(JenkinsResultsParserUtil.getCurrentTimeMillis());
		sb.append(");");

		JenkinsResultsParserUtil.write(filePath, sb.toString());
	}

	private static JSONObject _getDownstreamBuildDurationJSONObject() {
		JSONObject datesDurationsJSONObject = new JSONObject();

		JSONArray datesJSONArray = new JSONArray();
		JSONArray durationsJSONArray = new JSONArray();

		List<String> months = new ArrayList<>(_recentTestrayBuilds.keySet());

		Collections.sort(months);

		for (String month : months) {
			List<Long> durations = new ArrayList<>();

			for (TestrayBuild testrayBuild : _recentTestrayBuilds.get(month)) {
				List<Long> downstreamDurations =
					testrayBuild.getDownstreamBuildDurations();

				if (downstreamDurations == null) {
					continue;
				}

				for (Long downstreamDuration : downstreamDurations) {
					if ((downstreamDuration == null) ||
						(downstreamDuration < 0)) {

						continue;
					}

					durations.add(downstreamDuration);
				}
			}

			if (durations.isEmpty()) {
				datesJSONArray.put(month);
			}
			else {
				String meanDuration = JenkinsResultsParserUtil.combine(
					"mean: ",
					JenkinsResultsParserUtil.toDurationString(
						JenkinsResultsParserUtil.getAverage(durations)));

				NumberFormat numberFormat = NumberFormat.getNumberInstance(
					Locale.US);

				String total = JenkinsResultsParserUtil.combine(
					"total: ", numberFormat.format(durations.size()));

				datesJSONArray.put(new String[] {month, meanDuration, total});
			}

			Collections.sort(durations);

			durationsJSONArray.put(durations);
		}

		datesDurationsJSONObject.put("dates", datesJSONArray);
		datesDurationsJSONObject.put("durations", durationsJSONArray);

		return datesDurationsJSONObject;
	}

	private static JSONObject _getTopLevelActiveBuildDurationJSONObject() {
		JSONObject jsonObject = new JSONObject();

		JSONArray datesJSONArray = new JSONArray();
		JSONArray durationsJSONArray = new JSONArray();

		List<String> months = new ArrayList<>(_recentTestrayBuilds.keySet());

		Collections.sort(months);

		for (String month : months) {
			List<Long> durations = new ArrayList<>();

			for (TestrayBuild testrayBuild : _recentTestrayBuilds.get(month)) {
				Long duration = testrayBuild.getTopLevelActiveBuildDuration();

				if ((duration == null) || (duration < 0)) {
					continue;
				}

				durations.add(duration);
			}

			if (durations.isEmpty()) {
				datesJSONArray.put(month);
			}
			else {
				String meanDuration = JenkinsResultsParserUtil.combine(
					"mean: ",
					JenkinsResultsParserUtil.toDurationString(
						JenkinsResultsParserUtil.getAverage(durations)));

				NumberFormat numberFormat = NumberFormat.getNumberInstance(
					Locale.US);

				String total = JenkinsResultsParserUtil.combine(
					"total: ", numberFormat.format(durations.size()));

				datesJSONArray.put(new String[] {month, meanDuration, total});
			}

			Collections.sort(durations);

			durationsJSONArray.put(durations);
		}

		jsonObject.put("dates", datesJSONArray);
		jsonObject.put("durations", durationsJSONArray);

		return jsonObject;
	}

	private static JSONObject _getTopLevelTotalBuildDurationJSONObject() {
		JSONObject jsonObject = new JSONObject();

		JSONArray datesJSONArray = new JSONArray();
		JSONArray durationsJSONArray = new JSONArray();

		List<String> months = new ArrayList<>(_recentTestrayBuilds.keySet());

		Collections.sort(months);

		for (String month : months) {
			List<Long> durations = new ArrayList<>();

			for (TestrayBuild testrayBuild : _recentTestrayBuilds.get(month)) {
				Long duration = testrayBuild.getTopLevelBuildDuration();

				if ((duration == null) || (duration < 0)) {
					continue;
				}

				durations.add(duration);
			}

			if (durations.isEmpty()) {
				datesJSONArray.put(month);
			}
			else {
				String meanDuration = JenkinsResultsParserUtil.combine(
					"mean: ",
					JenkinsResultsParserUtil.toDurationString(
						JenkinsResultsParserUtil.getAverage(durations)));

				NumberFormat numberFormat = NumberFormat.getNumberInstance(
					Locale.US);

				String total = JenkinsResultsParserUtil.combine(
					"total: ", numberFormat.format(durations.size()));

				datesJSONArray.put(new String[] {month, meanDuration, total});
			}

			Collections.sort(durations);

			durationsJSONArray.put(durations);
		}

		jsonObject.put("dates", datesJSONArray);
		jsonObject.put("durations", durationsJSONArray);

		return jsonObject;
	}

	private static final int _MONTHS_PER_YEAR = 12;

	private static final ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(25, true);
	private static final HashMap<String, List<TestrayBuild>>
		_recentTestrayBuilds;

	static {
		_recentTestrayBuilds = new HashMap<String, List<TestrayBuild>>() {
			{
				LocalDate currentLocalDate = LocalDate.now();

				for (int i = 0; i < _MONTHS_PER_YEAR; i++) {
					LocalDate localDate = currentLocalDate.minusMonths(i);

					put(
						localDate.format(
							DateTimeFormatter.ofPattern("yyyy-MM")),
						new ArrayList<TestrayBuild>());
				}
			}
		};
	}

}