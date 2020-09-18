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
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
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
			_buildProperties.getProperty(
				"ci.system.status.report.jenkins.cohort"));

		jenkinsCohort.writeDataJavaScriptFile(filePath);
	}

	public static void writeSpiraDataJavaScriptFile(String filePath)
		throws IOException {

		SpiraProject spiraProject = SpiraProject.getSpiraProjectByID(
			SpiraProject.getID("dxp"));

		SpiraRelease spiraRelease = spiraProject.getSpiraReleaseByID(
			SpiraRelease.getID(
				"test-portal-acceptance-pullrequest(master)", "relevant"));

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

		sb.append("\nvar spiraDataGeneratedDate = new Date(");
		sb.append(System.currentTimeMillis());
		sb.append(");\nvar successRateData = ");

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

		LocalDateTime currentLocalDateTime = LocalDateTime.now(ZoneOffset.UTC);

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

	private static final Properties _buildProperties;
	private static final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static final HashMap<LocalDate, List<SpiraReleaseBuild>>
		_recentSpiraReleaseBuilds;

	static {
		try {
			_buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build.properties", ioException);
		}

		_recentSpiraReleaseBuilds =
			new HashMap<LocalDate, List<SpiraReleaseBuild>>() {
				{
					LocalDate localDate = LocalDate.now(ZoneOffset.UTC);

					int spiraHistoryLength = Integer.parseInt(
						_buildProperties.getProperty(
							"ci.system.status.report.spira.history.length"));

					for (int i = 0; i < spiraHistoryLength; i++) {
						put(
							localDate.minusDays(i),
							new ArrayList<SpiraReleaseBuild>());
					}
				}
			};
	}

}