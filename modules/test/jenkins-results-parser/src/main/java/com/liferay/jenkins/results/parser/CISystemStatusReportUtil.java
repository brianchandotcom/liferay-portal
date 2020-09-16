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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
				spiraReleaseBuild.getCreationDate(),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

			if (_recentSpiraReleaseBuilds.containsKey(localDate)) {
				List<SpiraReleaseBuild> builds = _recentSpiraReleaseBuilds.get(
					localDate);

				builds.add(spiraReleaseBuild);

				_recentSpiraReleaseBuilds.put(localDate, builds);
			}
		}

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

		StringBuilder sb = new StringBuilder();

		sb.append("var relevantSuiteBuildData = ");
		sb.append(relevantSuiteBuildDataJSONObject.toString());
		sb.append(";");

		JenkinsResultsParserUtil.write(filePath, sb.toString());
	}

	private static final int _DAYS_AGO = 28;

	private static final String _DEFAULT_JENKINS_COHORT = "test-1";

	private static final int
		_LIFERAY_DXP_73_PULL_REQUEST_RELEVANT_SUITE_RELEASE_ID = 1312;

	private static final int _LIFERAY_DXP_PROJECT_ID = 16;

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