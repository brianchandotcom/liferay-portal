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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class CISystemStatusReportUtil {

	public static void writeJenkinsDataJavaScriptFile(String filePath)
		throws IOException {

		JenkinsCohort jenkinsCohort = new JenkinsCohort(
			JenkinsResultsParserUtil.getBuildProperty(
				"ci.system.status.report.jenkins.cohort"));

		jenkinsCohort.writeDataJavaScriptFile(filePath);
	}

	public static void writeTestrayDataJavaScriptFile(
			String filePath, TestrayRoutine testrayRoutine, String nameFilter)
		throws IOException {

		List<Callable<String>> callables = new ArrayList<>();

		for (LocalDate localDate : _recentTestrayBuilds.keySet()) {
			List<TestrayBuild> builds = testrayRoutine.getTestrayBuilds(
				200, localDate.toString(), nameFilter);

			_recentTestrayBuilds.put(localDate, builds);

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

		sb.append("var relevantSuiteBuildData = ");
		sb.append(_getRelevantSuiteBuildDataJSONObject());

		sb.append("\nvar topLevelTotalBuildDurationData = ");
		sb.append(_getTopLevelTotalBuildDurationJSONObject());

		sb.append("\nvar topLevelActiveBuildDurationData = ");
		sb.append(_getTopLevelActiveBuildDurationJSONObject());

		sb.append("\nvar downstreamBuildDurationData = ");
		sb.append(_getDownstreamBuildDurationJSONObject());

		sb.append("\nvar testrayDataGeneratedDate = new Date(");
		sb.append(JenkinsResultsParserUtil.getCurrentTimeMillis());
		sb.append(");");

		sb.append("\nvar successRateData = ");
		sb.append(_getSuccessRateDataJSONArray());
		sb.append(";");

		JenkinsResultsParserUtil.write(filePath, sb.toString());
	}

	protected static String getPercentage(Integer dividend, Integer divisor) {
		double quotient = 0;

		if (divisor != 0) {
			quotient = (double)dividend / (double)divisor;
		}

		DecimalFormat decimalFormat = new DecimalFormat("###.##%");

		return decimalFormat.format(quotient);
	}

	private static JSONObject _getDownstreamBuildDurationJSONObject() {
		JSONObject datesDurationsJSONObject = new JSONObject();

		JSONArray datesJSONArray = new JSONArray();
		JSONArray durationsJSONArray = new JSONArray();

		List<LocalDate> dates = new ArrayList<>(_recentTestrayBuilds.keySet());

		Collections.sort(dates);

		for (LocalDate date : dates) {
			List<Long> durations = new ArrayList<>();

			for (TestrayBuild testrayBuild : _recentTestrayBuilds.get(date)) {
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

			durations.removeAll(Collections.singleton(null));

			if (durations.isEmpty()) {
				datesJSONArray.put(date.toString());
			}
			else {
				String meanDuration = JenkinsResultsParserUtil.combine(
					"mean: ",
					JenkinsResultsParserUtil.toDurationString(
						JenkinsResultsParserUtil.getAverage(durations)));

				datesJSONArray.put(
					new String[] {date.toString(), meanDuration});
			}

			Collections.sort(durations);

			durationsJSONArray.put(durations);
		}

		datesDurationsJSONObject.put("dates", datesJSONArray);
		datesDurationsJSONObject.put("durations", durationsJSONArray);

		return datesDurationsJSONObject;
	}

	private static JSONObject _getRelevantSuiteBuildDataJSONObject() {
		JSONObject relevantSuiteBuildDataJSONObject = new JSONObject();

		JSONArray datesJSONArray = new JSONArray();
		JSONArray failedBuildsJSONArray = new JSONArray();
		JSONArray passedBuildsJSONArray = new JSONArray();
		JSONArray unstableBuildsJSONArray = new JSONArray();

		List<LocalDate> dates = new ArrayList<>(_recentTestrayBuilds.keySet());

		Collections.sort(dates);

		for (LocalDate date : dates) {
			int failedBuilds = 0;
			int passedBuilds = 0;
			int unstableBuilds = 0;

			for (TestrayBuild testrayBuild : _recentTestrayBuilds.get(date)) {
				String result = testrayBuild.getResult();

				if (result.equals("FAILURE")) {
					failedBuilds++;

					continue;
				}

				if (result.equals("SUCCESS")) {
					passedBuilds++;

					continue;
				}

				if (result.equals("APPROVED")) {
					unstableBuilds++;
				}
			}

			datesJSONArray.put(date.toString());
			passedBuildsJSONArray.put(passedBuilds);
			failedBuildsJSONArray.put(failedBuilds);
			unstableBuildsJSONArray.put(unstableBuilds);
		}

		relevantSuiteBuildDataJSONObject.put("dates", datesJSONArray);
		relevantSuiteBuildDataJSONObject.put("failed", failedBuildsJSONArray);
		relevantSuiteBuildDataJSONObject.put(
			"succeeded", passedBuildsJSONArray);
		relevantSuiteBuildDataJSONObject.put(
			"unstable", unstableBuildsJSONArray);

		return relevantSuiteBuildDataJSONObject;
	}

	private static JSONArray _getSuccessRateDataJSONArray() {
		JSONArray successRateDataJSONArray = new JSONArray();

		JSONArray titlesJSONArray = new JSONArray();

		titlesJSONArray.put("Time Period");
		titlesJSONArray.put("Adjusted Success Rate");
		titlesJSONArray.put("Success Rate");
		titlesJSONArray.put("Builds Run");

		successRateDataJSONArray.put(titlesJSONArray);

		LocalDateTime currentLocalDateTime = LocalDateTime.now(ZoneOffset.UTC);

		successRateDataJSONArray.put(
			_getSuccessRateJSONArray(
				"Last 24 Hours", currentLocalDateTime.minusDays(1),
				currentLocalDateTime));
		successRateDataJSONArray.put(
			_getSuccessRateJSONArray(
				"Last 7 Days", currentLocalDateTime.minusDays(_DAYS_PER_WEEK),
				currentLocalDateTime));
		successRateDataJSONArray.put(
			_getSuccessRateJSONArray(
				"Previous 7 Days",
				currentLocalDateTime.minusDays(_DAYS_PER_WEEK * 2),
				currentLocalDateTime.minusDays(_DAYS_PER_WEEK)));

		return successRateDataJSONArray;
	}

	private static JSONArray _getSuccessRateJSONArray(
		String title, LocalDateTime startLocalDateTime,
		LocalDateTime endLocalDateTime) {

		if (startLocalDateTime.compareTo(endLocalDateTime) >= 0) {
			throw new IllegalArgumentException(
				"Start time must preceed end time");
		}

		Set<LocalDate> localDates = new HashSet<>();

		for (int i = 0;
			 startLocalDateTime.compareTo(endLocalDateTime.minusDays(i)) <= 0;
			 i++) {

			LocalDateTime localDateTime = endLocalDateTime.minusDays(i);

			localDates.add(localDateTime.toLocalDate());
		}

		int failedBuilds = 0;
		int passedBuilds = 0;
		int unstableBuilds = 0;

		for (LocalDate localDate : localDates) {
			for (TestrayBuild testrayBuild :
					_recentTestrayBuilds.get(localDate)) {

				String testrayBuildName = testrayBuild.getName();

				Matcher matcher = _dateTimePattern.matcher(testrayBuildName);

				if (!matcher.find()) {
					continue;
				}

				Date date;

				try {
					date = _simpleDateFormat.parse(matcher.group("date"));
				}
				catch (ParseException parseException) {
					throw new RuntimeException(parseException);
				}

				Instant instant = Instant.ofEpochMilli(date.getTime());

				ZonedDateTime zonedDateTime = instant.atZone(
					ZoneId.systemDefault());

				LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

				if ((startLocalDateTime.compareTo(localDateTime) >= 0) ||
					(endLocalDateTime.compareTo(localDateTime) <= 0)) {

					continue;
				}

				String testrayBuildResult = testrayBuild.getResult();

				if (testrayBuildResult.equals("FAILURE")) {
					failedBuilds++;

					continue;
				}

				if (testrayBuildResult.equals("SUCCESS")) {
					passedBuilds++;

					continue;
				}

				if (testrayBuildResult.equals("APPROVED")) {
					unstableBuilds++;
				}
			}
		}

		int totalBuilds = failedBuilds + passedBuilds + unstableBuilds;

		JSONArray successRateJSONArray = new JSONArray();

		successRateJSONArray.put(title);
		successRateJSONArray.put(
			getPercentage(passedBuilds + unstableBuilds, totalBuilds));
		successRateJSONArray.put(getPercentage(passedBuilds, totalBuilds));
		successRateJSONArray.put(totalBuilds);

		return successRateJSONArray;
	}

	private static JSONObject _getTopLevelActiveBuildDurationJSONObject() {
		JSONObject jsonObject = new JSONObject();

		JSONArray datesJSONArray = new JSONArray();
		JSONArray durationsJSONArray = new JSONArray();

		List<LocalDate> dates = new ArrayList<>(_recentTestrayBuilds.keySet());

		Collections.sort(dates);

		for (LocalDate date : dates) {
			List<Long> durations = new ArrayList<>();

			for (TestrayBuild testrayBuild : _recentTestrayBuilds.get(date)) {
				Long duration = testrayBuild.getTopLevelActiveBuildDuration();

				if ((duration == null) || (duration < 0)) {
					continue;
				}

				durations.add(duration);
			}

			durations.removeAll(Collections.singleton(null));

			if (durations.isEmpty()) {
				datesJSONArray.put(date.toString());
			}
			else {
				String meanDuration = JenkinsResultsParserUtil.combine(
					"mean: ",
					JenkinsResultsParserUtil.toDurationString(
						JenkinsResultsParserUtil.getAverage(durations)));

				datesJSONArray.put(
					new String[] {date.toString(), meanDuration});
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

		List<LocalDate> dates = new ArrayList<>(_recentTestrayBuilds.keySet());

		Collections.sort(dates);

		for (LocalDate date : dates) {
			List<Long> durations = new ArrayList<>();

			for (TestrayBuild testrayBuild : _recentTestrayBuilds.get(date)) {
				Long duration = testrayBuild.getTopLevelBuildDuration();

				if ((duration == null) || (duration < 0)) {
					continue;
				}

				durations.add(duration);
			}

			durations.removeAll(Collections.singleton(null));

			if (durations.isEmpty()) {
				datesJSONArray.put(date.toString());
			}
			else {
				String meanDuration = JenkinsResultsParserUtil.combine(
					"mean: ",
					JenkinsResultsParserUtil.toDurationString(
						JenkinsResultsParserUtil.getAverage(durations)));

				datesJSONArray.put(
					new String[] {date.toString(), meanDuration});
			}

			Collections.sort(durations);

			durationsJSONArray.put(durations);
		}

		jsonObject.put("dates", datesJSONArray);
		jsonObject.put("durations", durationsJSONArray);

		return jsonObject;
	}

	private static final int _DAYS_PER_WEEK = 7;

	private static final Pattern _dateTimePattern = Pattern.compile(
		".*(?<date>\\d{4}-\\d{2}-\\d{2}\\[\\d{2}:\\d{2}:\\d{2}\\]).*");
	private static final ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(25, true);
	private static final HashMap<LocalDate, List<TestrayBuild>>
		_recentTestrayBuilds;
	private static final SimpleDateFormat _simpleDateFormat =
		new SimpleDateFormat("yyyy-MM-dd[HH:mm:ss]");

	static {
		_recentTestrayBuilds = new HashMap<LocalDate, List<TestrayBuild>>() {
			{
				LocalDate localDate = LocalDate.now(ZoneOffset.UTC);

				for (int i = 0; i <= (_DAYS_PER_WEEK * 2); i++) {
					put(localDate.minusDays(i), new ArrayList<TestrayBuild>());
				}
			}
		};
	}

}