/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.testray.TestrayBuild;
import com.liferay.jenkins.results.parser.testray.TestrayCaseResult;
import com.liferay.jenkins.results.parser.testray.TestrayFactory;
import com.liferay.jenkins.results.parser.testray.TestrayServer;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Charlotte Wong
 * @author Kyle Miho
 */
public class GenerateTestrayCSVUtil {

	public static void generate(
		String projectBuildDir, long projectTestrayBuildId,
		URL testrayServerURL) {

		if (testrayServerURL == null) {
			try {
				testrayServerURL = new URL(
					JenkinsResultsParserUtil.getBuildProperty(
						"testray.server.url"));
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}

		TestrayServer testrayServer = TestrayFactory.newTestrayServer(
			String.valueOf(testrayServerURL));

		TestrayBuild testrayBuild = testrayServer.getTestrayBuildByID(
			projectTestrayBuildId);

		System.out.println("Generating Testray CSV.");

		StringBuilder sb = new StringBuilder();

		sb.append(
			JenkinsResultsParserUtil.join(
				_CSV_DELIMITER, "Case Name", "Case History URL", "Failure Type",
				"Error Message"));
		sb.append("\n");

		List<TestrayCaseResult> testrayCaseResults = new ArrayList<>();

		for (TestrayCaseResult testrayCaseResult :
				testrayBuild.getTestrayCaseResults()) {

			if ((testrayCaseResult.getStatus() !=
					TestrayCaseResult.Status.FAILED) ||
				Objects.equals(
					testrayCaseResult.getName(), "Top Level Build")) {

				continue;
			}

			testrayCaseResults.add(testrayCaseResult);
		}

		if (testrayCaseResults.isEmpty()) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"There are no Testray case results to report. Testray may ",
					"not have imported the results yet or the results ",
					"contained no failures."));

			return;
		}

		sb.append(_generate(testrayCaseResults, Type.UNIQUE));
		sb.append("\n");
		sb.append(_generate(testrayCaseResults, Type.DID_NOT_RUN));
		sb.append("\n");
		sb.append(_generate(testrayCaseResults, Type.COMMON));

		try {
			System.out.println("Setting testray results to: " + sb.toString());

			JenkinsResultsParserUtil.write(
				new File(
					projectBuildDir,
					JenkinsResultsParserUtil.combine(
						"testray-results-",
						String.valueOf(testrayBuild.getID()), ".csv")),
				sb.toString());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static void generate(
		String projectBuildDir, String projectTestrayBuildId) {

		generate(projectBuildDir, Long.valueOf(projectTestrayBuildId), null);
	}

	public enum Type {

		COMMON("Common"), DID_NOT_RUN("Did not run"), UNIQUE("Unique");

		@Override
		public String toString() {
			return _description;
		}

		private Type(String description) {
			_description = description;
		}

		private final String _description;

	}

	private static String _cleanCSVData(String string) {
		return string.replace(_CSV_DELIMITER, ".");
	}

	private static String _generate(
		List<TestrayCaseResult> testrayCaseResults,
		Type testrayCaseResultType) {

		System.out.println(
			"Parsing Testray case results for " +
				testrayCaseResultType.toString() + ".");

		StringBuilder sb = new StringBuilder();

		for (TestrayCaseResult testrayCaseResult : testrayCaseResults) {
			if (_getType(testrayCaseResult) != testrayCaseResultType) {
				continue;
			}

			sb.append(_generateCSV(testrayCaseResult, testrayCaseResultType));
			sb.append("\n");
		}

		if (sb.length() == 0) {
			sb.append(
				JenkinsResultsParserUtil.join(
					_CSV_DELIMITER, "NONE", "N/A",
					testrayCaseResultType.toString(), "N/A"));
			sb.append("\n");
		}

		return JenkinsResultsParserUtil.combine(
			testrayCaseResultType.toString(), " Failures\n", sb.toString());
	}

	private static String _generateCSV(
		TestrayCaseResult testrayCaseResult, Type testrayCaseResultType) {

		return JenkinsResultsParserUtil.join(
			_CSV_DELIMITER, _cleanCSVData(testrayCaseResult.getName()),
			_cleanCSVData(String.valueOf(testrayCaseResult.getHistoryURL())),
			_cleanCSVData(String.valueOf(testrayCaseResultType)),
			_cleanCSVData(testrayCaseResult.getErrors()));
	}

	private static Type _getType(TestrayCaseResult testrayCaseResult) {
		for (String didNotRunErrorMessage : _didNotRunErrorMessages) {
			Pattern pattern = Pattern.compile(didNotRunErrorMessage);

			Matcher matcher = pattern.matcher(testrayCaseResult.getErrors());

			if (matcher.find()) {
				return Type.DID_NOT_RUN;
			}
		}

		for (TestrayCaseResult previousTestrayCaseResult :
				testrayCaseResult.getTestrayCaseResultHistory(25)) {

			if (Objects.equals(
					testrayCaseResult.getID(),
					previousTestrayCaseResult.getID())) {

				continue;
			}

			if (_isSimilarError(testrayCaseResult, previousTestrayCaseResult) &&
				!Objects.equals(
					testrayCaseResult.getPullRequestSenderUsername(),
					previousTestrayCaseResult.getPullRequestSenderUsername())) {

				return Type.COMMON;
			}
		}

		return Type.UNIQUE;
	}

	private static boolean _isSimilarError(
		TestrayCaseResult testrayCaseResult1,
		TestrayCaseResult testrayCaseResult2) {

		String testrayCaseResultErrors1 = testrayCaseResult1.getErrors();
		String testrayCaseResultErrors2 = testrayCaseResult2.getErrors();

		try {
			Double distance = StringUtils.getJaroWinklerDistance(
				testrayCaseResultErrors1, testrayCaseResultErrors2);

			if (distance > _MAX_DISTANCE) {
				return true;
			}

			return false;
		}
		catch (IllegalArgumentException illegalArgumentException) {
			if (testrayCaseResultErrors1 == testrayCaseResultErrors2) {
				return true;
			}

			return false;
		}
	}

	private static final String _CSV_DELIMITER = ",";

	private static final double _MAX_DISTANCE = 0.8;

	private static final List<String> _didNotRunErrorMessages = Arrays.asList(
		"Aborted prior to running test", "Failed prior to running test",
		"Failed for unknown reason", "timed out after 2 hours");

}