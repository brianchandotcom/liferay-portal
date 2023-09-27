/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.testray.TestrayBuild;
import com.liferay.jenkins.results.parser.testray.TestrayFactory;
import com.liferay.jenkins.results.parser.testray.TestrayRoutine;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class UpstreamFailureUtil {

	public static synchronized List<String> getUpstreamJobFailures(
		String type, TopLevelBuild topLevelBuild) {

		if (_upstreamFailures.containsKey(type)) {
			return _upstreamFailures.get(type);
		}

		List<String> upstreamFailures = new ArrayList<>();

		_upstreamFailures.put(type, upstreamFailures);

		TopLevelBuildReport topLevelBuildReport =
			getUpstreamTopLevelBuildReport(topLevelBuild);

		if (topLevelBuildReport == null) {
			return upstreamFailures;
		}

		for (DownstreamBuildReport downstreamBuildReport :
				topLevelBuildReport.getDownstreamBuildReports()) {

			String result = downstreamBuildReport.getResult();

			if (!result.equals("FAILURE") && !result.equals("REGRESSION") &&
				!result.equals("UNSTABLE")) {

				continue;
			}

			String batchName = _getBatchName(
				downstreamBuildReport.getBatchName());

			if (type.equals("build")) {
				upstreamFailures.add(
					_formatUpstreamBuildFailure(batchName, result));
			}
			else if (type.equals("test")) {
				for (TestReport testReport :
						downstreamBuildReport.getTestReports()) {

					String testReportStatus = testReport.getStatus();

					if (!testReportStatus.equals("PASSED")) {
						upstreamFailures.add(
							_formatUpstreamTestFailure(
								batchName, testReport.getTestName()));
					}

					List<TestClassReport> testClassReports =
						downstreamBuildReport.getTestClassReports();

					if (testReportStatus.equals("PASSED") &&
						(testClassReports.size() == 1)) {

						upstreamFailures.add(
							_formatUpstreamTestFailure(
								batchName, testReport.getTestName()));
					}
				}
			}
		}

		return upstreamFailures;
	}

	public static String getUpstreamJobFailuresSHA(
		TopLevelBuild topLevelBuild) {

		return _getUpstreamJobFailuresSHA(
			getUpstreamTopLevelBuildReport(topLevelBuild));
	}

	public static TestrayBuild getUpstreamTestrayBuild(
		TopLevelBuild topLevelBuild) {

		if (_upstreamTestrayBuild != null) {
			return _upstreamTestrayBuild;
		}

		if (!_upstreamComparisonAvailable) {
			return null;
		}

		TestrayRoutine testrayRoutine = _getUpstreamTestrayRoutine(
			topLevelBuild);

		if ((testrayRoutine == null) ||
			!(topLevelBuild instanceof PortalBranchInformationBuild)) {

			_upstreamComparisonAvailable = false;

			return null;
		}

		String upstreamBranchName = topLevelBuild.getBranchName();

		if (topLevelBuild instanceof PullRequestSubrepositoryTopLevelBuild) {
			PullRequestSubrepositoryTopLevelBuild
				pullRequestSubrepositoryTopLevelBuild =
					(PullRequestSubrepositoryTopLevelBuild)topLevelBuild;

			upstreamBranchName =
				pullRequestSubrepositoryTopLevelBuild.
					getPortalUpstreamBranchName();
		}

		GitWorkingDirectory gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				upstreamBranchName, (File)null, "liferay-portal");

		for (TestrayBuild testrayBuild : testrayRoutine.getTestrayBuilds()) {
			if (!gitWorkingDirectory.refContainsSHA(
					"HEAD", testrayBuild.getPortalSHA())) {

				continue;
			}

			_upstreamTestrayBuild = testrayBuild;

			TopLevelBuildReport topLevelBuildReport =
				testrayBuild.getTopLevelBuildReport();

			List<DownstreamBuildReport> downstreamBuildReports =
				topLevelBuildReport.getDownstreamBuildReports();

			if ((topLevelBuildReport == null) ||
				downstreamBuildReports.isEmpty()) {

				continue;
			}

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Comparing with test results from ",
					String.valueOf(topLevelBuildReport.getBuildURL()),
					" at SHA ",
					_getUpstreamJobFailuresSHA(topLevelBuildReport)));

			return _upstreamTestrayBuild;
		}

		_upstreamComparisonAvailable = false;

		return null;
	}

	public static TestrayBuild getUpstreamTestrayBuild(
		TopLevelBuild topLevelBuild, String upstreamBranchSHA) {

		TestrayRoutine testrayRoutine = _getUpstreamTestrayRoutine(
			topLevelBuild);

		if (testrayRoutine == null) {
			return null;
		}

		for (TestrayBuild testrayBuild : testrayRoutine.getTestrayBuilds()) {
			if (!Objects.equals(
					upstreamBranchSHA, testrayBuild.getPortalSHA())) {

				continue;
			}

			return testrayBuild;
		}

		return null;
	}

	public static TopLevelBuildReport getUpstreamTopLevelBuildReport(
		TopLevelBuild topLevelBuild) {

		if (_upstreamTopLevelBuildReport != null) {
			return _upstreamTopLevelBuildReport;
		}

		if (!_upstreamComparisonAvailable) {
			return null;
		}

		TestrayBuild upstreamTestrayBuild = getUpstreamTestrayBuild(
			topLevelBuild);

		if (upstreamTestrayBuild == null) {
			return null;
		}

		_upstreamTopLevelBuildReport =
			upstreamTestrayBuild.getTopLevelBuildReport();

		return _upstreamTopLevelBuildReport;
	}

	public static TopLevelBuildReport getUpstreamTopLevelBuildReport(
		TopLevelBuild topLevelBuild, String upstreamBranchSHA) {

		JobReport jobReport = JobReport.getInstance(
			topLevelBuild.getAcceptanceUpstreamJobURL());

		for (TopLevelBuildReport topLevelBuildReport :
				jobReport.getTopLevelBuildReports(25)) {

			String portalGitCommit = JenkinsResultsParserUtil.getBuildParameter(
				String.valueOf(topLevelBuildReport.getBuildURL()),
				"PORTAL_GIT_COMMIT");

			if (!Objects.equals(upstreamBranchSHA, portalGitCommit)) {
				continue;
			}

			return topLevelBuildReport;
		}

		return null;
	}

	public static boolean isUpstreamComparisonAvailable(
		TopLevelBuild topLevelBuild) {

		getUpstreamTopLevelBuildReport(topLevelBuild);

		return _upstreamComparisonAvailable;
	}

	public static void reset() {
		_upstreamComparisonAvailable = true;
		_upstreamJobFailuresSHA = null;
		_upstreamTestrayBuild = null;
		_upstreamTestrayRoutine = null;
		_upstreamTopLevelBuildReport = null;
	}

	public static void resetUpstreamJobFailuresJSONObject() {
		reset();
	}

	private static String _formatUpstreamBuildFailure(
		String batchName, String testResult) {

		return JenkinsResultsParserUtil.combine(batchName, ",", testResult);
	}

	private static String _formatUpstreamTestFailure(
		String jobVariant, String testName) {

		return JenkinsResultsParserUtil.combine(testName, ",", jobVariant);
	}

	private static String _getBatchName(String jobVariant) {
		jobVariant = jobVariant.replaceAll("(.*)/.*", "$1");

		return jobVariant.replaceAll("_stable$", "");
	}

	private static String _getUpstreamJobFailuresSHA(
		TopLevelBuildReport upstreamTopLevelBuildReport) {

		if (!JenkinsResultsParserUtil.isNullOrEmpty(_upstreamJobFailuresSHA)) {
			return _upstreamJobFailuresSHA;
		}

		if (upstreamTopLevelBuildReport == null) {
			System.out.println(
				"Unable to get upstream acceptance failure data");

			_upstreamJobFailuresSHA = "";

			return _upstreamJobFailuresSHA;
		}

		Map<String, String> buildParameters =
			upstreamTopLevelBuildReport.getBuildParameters();

		_upstreamJobFailuresSHA = buildParameters.get("PORTAL_GIT_COMMIT");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(_upstreamJobFailuresSHA)) {
			return _upstreamJobFailuresSHA;
		}

		_upstreamJobFailuresSHA = JenkinsResultsParserUtil.getBuildParameter(
			String.valueOf(upstreamTopLevelBuildReport.getBuildURL()),
			"PORTAL_GIT_COMMIT");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(_upstreamJobFailuresSHA)) {
			return _upstreamJobFailuresSHA;
		}

		File testResultsJSONFile = new File(
			System.getenv("WORKSPACE"), "test.results.json");

		try {
			JenkinsResultsParserUtil.toFile(
				upstreamTopLevelBuildReport.getTestResultsJSONUserContentURL(),
				testResultsJSONFile);

			JSONObject upstreamJobFailuresJSONObject = new JSONObject(
				JenkinsResultsParserUtil.read(testResultsJSONFile));

			_upstreamJobFailuresSHA = upstreamJobFailuresJSONObject.getString(
				"SHA");

			return _upstreamJobFailuresSHA;
		}
		catch (Exception exception) {
			System.out.println(
				"Unable to get upstream acceptance failure data");

			_upstreamJobFailuresSHA = "";

			return _upstreamJobFailuresSHA;
		}
		finally {
			if (testResultsJSONFile.exists()) {
				JenkinsResultsParserUtil.delete(testResultsJSONFile);
			}
		}
	}

	private static TestrayRoutine _getUpstreamTestrayRoutine(
		TopLevelBuild topLevelBuild) {

		if (_upstreamTestrayRoutine != null) {
			return _upstreamTestrayRoutine;
		}

		if (!(topLevelBuild instanceof PortalBranchInformationBuild)) {
			return null;
		}

		PortalBranchInformationBuild portalBranchInformationBuild =
			(PortalBranchInformationBuild)topLevelBuild;

		Build.BranchInformation branchInformation =
			portalBranchInformationBuild.getPortalBranchInformation();

		try {
			_upstreamTestrayRoutine = TestrayFactory.newTestrayRoutine(
				JenkinsResultsParserUtil.getProperty(
					JenkinsResultsParserUtil.getBuildProperties(),
					"test.history.routine.url",
					branchInformation.getUpstreamBranchName()));

			return _upstreamTestrayRoutine;
		}
		catch (IOException ioException) {
			return null;
		}
	}

	private static boolean _upstreamComparisonAvailable = true;
	private static final Map<String, List<String>> _upstreamFailures =
		new HashMap<>();
	private static String _upstreamJobFailuresSHA;
	private static TestrayBuild _upstreamTestrayBuild;
	private static TestrayRoutine _upstreamTestrayRoutine;
	private static TopLevelBuildReport _upstreamTopLevelBuildReport;

}