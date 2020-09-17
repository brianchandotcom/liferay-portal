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

import com.liferay.jenkins.results.parser.spira.SpiraProject;
import com.liferay.jenkins.results.parser.spira.SpiraRelease;
import com.liferay.jenkins.results.parser.spira.SpiraReleaseBuild;

import java.io.IOException;

import java.text.DecimalFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class CISystemStatusReportUtil {

	public static void writeJenkinsDataJavaScriptFile(String filePath)
		throws IOException {

		JenkinsCohort jenkinsCohort = new JenkinsCohort(
			_DEFAULT_JENKINS_COHORT);

		jenkinsCohort.writeDataJavaScriptFile(filePath);
	}

	public static void writeSpiraDataJavaScriptFile(String filePath)
		throws IOException {

		SpiraProject spiraProject = SpiraProject.getSpiraProjectByID(
			_LIFERAY_DXP_PROJECT_ID);

		SpiraRelease spiraRelease = spiraProject.getSpiraReleaseByID(
			_LIFERAY_DXP_73_PULL_REQUEST_RELEVANT_SUITE_RELEASE_ID);

		List<SpiraReleaseBuild> spiraReleaseBuilds =
			SpiraReleaseBuild.getSpiraReleaseBuilds(spiraProject, spiraRelease);

		for (SpiraReleaseBuild spiraReleaseBuild : spiraReleaseBuilds) {
			LocalDate localDate = LocalDate.parse(
				spiraReleaseBuild.getCreationDate(), _dateTimeFormatter);

			if (_recentSpiraReleaseBuilds.containsKey(localDate)) {
				List<SpiraReleaseBuild> builds = _recentSpiraReleaseBuilds.get(
					localDate);

				builds.add(spiraReleaseBuild);

				_recentSpiraReleaseBuilds.put(localDate, builds);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var relevantSuiteBuildData = ");

		JSONObject relevantSuiteBuildDataJSONObject =
			_getrelevantSuiteBuildDataJSONObject();

		sb.append(relevantSuiteBuildDataJSONObject.toString());

		sb.append(";\n var successRateData = ");

		JSONArray successRateTableDataJSONArray =
			_getSuccessRateDataJSONArray();

		sb.append(successRateTableDataJSONArray.toString());

		sb.append(";");

		JenkinsResultsParserUtil.write(filePath, sb.toString());
	}

	private static String _getPercentage(Integer dividend, Integer divisor) {
		double quotient = (double)dividend / (double)divisor;

		DecimalFormat decimalFormat = new DecimalFormat("###.##%");

		return decimalFormat.format(quotient);
	}

	private static JSONObject _getrelevantSuiteBuildDataJSONObject() {
		JSONObject relevantSuiteBuildDataJSONObject = new JSONObject();

		JSONArray datesJSONArray = new JSONArray();
		JSONArray failedBuildsJSONArray = new JSONArray();
		JSONArray passedBuildsJSONArray = new JSONArray();
		JSONArray unstableBuildsJSONArray = new JSONArray();

		List<LocalDate> dates = new ArrayList<>(
			_recentSpiraReleaseBuilds.keySet());

		Collections.sort(dates);

		for (LocalDate date : dates) {
			datesJSONArray.put(date.toString());

			int failedBuilds = 0;
			int passedBuilds = 0;
			int unstableBuilds = 0;

			for (SpiraReleaseBuild spiraReleaseBuild :
					_recentSpiraReleaseBuilds.get(date)) {

				String buildStatusName = spiraReleaseBuild.getBuildStatusName();

				if (buildStatusName.equals("Failed")) {
					failedBuilds++;

					continue;
				}

				if (buildStatusName.equals("Succeeded")) {
					passedBuilds++;

					continue;
				}

				if (buildStatusName.equals("Unstable")) {
					unstableBuilds++;
				}
			}

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

		LocalDateTime currentLocalDateTime = LocalDateTime.now();

		successRateDataJSONArray.put(
			_getSuccessRateJSONArray(
				"Last 24 Hours", currentLocalDateTime.minusDays(1),
				currentLocalDateTime));
		successRateDataJSONArray.put(
			_getSuccessRateJSONArray(
				"Last 7 Days", currentLocalDateTime.minusDays(7),
				currentLocalDateTime));
		successRateDataJSONArray.put(
			_getSuccessRateJSONArray(
				"Previous 7 Days", currentLocalDateTime.minusDays(14),
				currentLocalDateTime.minusDays(7)));

		return successRateDataJSONArray;
	}

	private static JSONArray _getSuccessRateJSONArray(
		String title, LocalDateTime startLocalDateTime,
		LocalDateTime endLocalDateTime) {

		if (startLocalDateTime.compareTo(endLocalDateTime) >= 0) {
			throw new IllegalArgumentException(
				"Start time must preceed end time");
		}

		JSONArray successRateJSONArray = new JSONArray();

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
			for (SpiraReleaseBuild spiraReleaseBuild :
					_recentSpiraReleaseBuilds.get(localDate)) {

				LocalDateTime localDateTime = LocalDateTime.parse(
					spiraReleaseBuild.getCreationDate(), _dateTimeFormatter);

				if (startLocalDateTime.compareTo(localDateTime) >= 0) {
					continue;
				}

				if (endLocalDateTime.compareTo(localDateTime) <= 0) {
					continue;
				}

				String buildStatusName = spiraReleaseBuild.getBuildStatusName();

				if (buildStatusName.equals("Failed")) {
					failedBuilds++;

					continue;
				}

				if (buildStatusName.equals("Succeeded")) {
					passedBuilds++;

					continue;
				}

				if (buildStatusName.equals("Unstable")) {
					unstableBuilds++;
				}
			}
		}

		int totalBuilds = failedBuilds + passedBuilds + unstableBuilds;

		successRateJSONArray.put(title);
		successRateJSONArray.put(
			_getPercentage(passedBuilds + unstableBuilds, totalBuilds));
		successRateJSONArray.put(_getPercentage(passedBuilds, totalBuilds));
		successRateJSONArray.put(totalBuilds);

		return successRateJSONArray;
	}

	private static final int _DAYS_AGO = 28;

	private static final String _DEFAULT_JENKINS_COHORT = "test-1";

	private static final int
		_LIFERAY_DXP_73_PULL_REQUEST_RELEVANT_SUITE_RELEASE_ID = 1312;

	private static final int _LIFERAY_DXP_PROJECT_ID = 16;

	private static final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	private static final HashMap<LocalDate, List<SpiraReleaseBuild>>
		_recentSpiraReleaseBuilds =
			new HashMap<LocalDate, List<SpiraReleaseBuild>>() {
				{
					LocalDate localDate = LocalDate.now();

					for (int i = 0; i < _DAYS_AGO; i++) {
						put(
							localDate.minusDays(i),
							new ArrayList<SpiraReleaseBuild>());
					}
				}
			};

}