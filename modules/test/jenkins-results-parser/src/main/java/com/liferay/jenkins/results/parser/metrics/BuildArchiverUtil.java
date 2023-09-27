/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.metrics;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.ParallelExecutor;

import java.io.File;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class BuildArchiverUtil {

	public static final String OUTPUT_DIR =
		"/opt/dev/projects/github/liferay-portal/tmp/jenkins";

	public static void archive(
		String startDateString, String endDateString, String outputDir) {

		try {
			Properties properties =
				JenkinsResultsParserUtil.getBuildProperties();

			String groovyScript = JenkinsResultsParserUtil.readInputStream(
				JenkinsResultsParserUtil.class.getResourceAsStream(
					"dependencies/get-build-data.groovy"));

			groovyScript = groovyScript.replaceFirst(
				"startDate\\.format\\(\"yyyyMMdd\"\\) \\+ \"",
				"\"" + startDateString);

			groovyScript = groovyScript.replaceFirst(
				"new Date\\(\\)",
				"Date.parse(\"yyyyMMdd hh:mm:ss\", \"" + endDateString +
					" 00:00:00\")");

			System.out.println(groovyScript);

			recordGroovyScriptResponses(
				JenkinsResultsParserUtil.getJenkinsMasters(
					properties, 12, 2, "test-1"),
				groovyScript, outputDir);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get get-build-data.groovy", ioException);
		}
	}

	public static void archiveOneDay(String startDateString) {
		String startDateTimeString = startDateString + " 00:00:00";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
			"yyyyMMdd HH:mm:ss");

		LocalDateTime localDateTime = LocalDateTime.parse(
			startDateTimeString, dateTimeFormatter);

		localDateTime = localDateTime.plusDays(1);

		String endDateString = localDateTime.format(
			DateTimeFormatter.ofPattern("yyyyMMdd"));

		archive(
			startDateString, endDateString,
			OUTPUT_DIR + "/" + startDateString + "/");
	}

	public static boolean isValidJSON(String json) {
		try {
			new JSONObject(json);
		}
		catch (JSONException jsonException1) {
			try {
				new JSONArray(json);
			}
			catch (JSONException jsonException2) {
				return false;
			}
		}

		return true;
	}

	protected static void recordGroovyScriptResponses(
		List<JenkinsMaster> jenkinsMasters, final String groovyScript,
		final String outputDir) {

		List<Callable<Boolean>> callables = new ArrayList<>();

		for (final JenkinsMaster jenkinsMaster : jenkinsMasters) {
			Callable<Boolean> callable = new Callable<Boolean>() {

				@Override
				public Boolean call() {
					try {
						String response =
							JenkinsResultsParserUtil.executeJenkinsScript(
								jenkinsMaster.getName(), groovyScript);

						if (response == null) {
							return false;
						}

						response = response.trim();

						if (response.isEmpty()) {
							return false;
						}

						File file = new File(
							outputDir + jenkinsMaster.getName() +
								"_builds.json");

						if (file.exists()) {
							String fileContent = JenkinsResultsParserUtil.read(
								file);

							if ((fileContent != null) &&
								(fileContent.equals(response) ||
								 (fileContent.length() > response.length()))) {

								System.out.println(
									"Complete data for " + file +
										" already exists");

								return false;
							}
						}

						if (isValidJSON(response)) {
							System.out.println(
								"Writing CI data for " +
									jenkinsMaster.getName() + " to: " + file);

							JenkinsResultsParserUtil.write(file, response);

							return true;
						}
					}
					catch (Exception exception) {
						System.out.println(
							"Unable to get data for " +
								jenkinsMaster.getName());

						exception.printStackTrace();
					}

					return false;
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Boolean> parallelExecutor = new ParallelExecutor<>(
			callables, _executorService);

		parallelExecutor.execute();
	}

	private static final ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(8, true);

}