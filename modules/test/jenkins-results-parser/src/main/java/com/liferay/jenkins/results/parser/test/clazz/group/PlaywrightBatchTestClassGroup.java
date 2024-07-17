/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.AntUtil;
import com.liferay.jenkins.results.parser.GitRepositoryJob;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.NotificationUtil;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.batch.PlaywrightTestBatch;
import com.liferay.jenkins.results.parser.test.batch.PlaywrightTestSelector;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassFactory;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringEscapeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class PlaywrightBatchTestClassGroup extends BatchTestClassGroup {

	public void addDefaultProjectJobProperty(String batchName) {
		JobProperty jobProperty = getJobProperty(
			PLAYWRIGHT_TEST_PROJECT_PROPERTY_NAME, testSuiteName, batchName);

		String jobPropertyValue = jobProperty.getValue();

		System.out.println("job prop value: " + jobPropertyValue);

		if (JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue)) {
			String playwrightProjectNamesEnv = System.getenv(
				"PLAYWRIGHT_PROJECT_NAME");

			if (!JenkinsResultsParserUtil.isNullOrEmpty(
					playwrightProjectNamesEnv)) {

				jobPropertyValue = playwrightProjectNamesEnv;
				
				System.out.println("env job value: " + jobPropertyValue);
			}
			else {
				jobPropertyValue = _getProjectJSONObj();

				System.out.println("proj job value: " + jobPropertyValue);

				return;
			}
		}

		_addProjectNames(jobPropertyValue);

		recordJobProperty(jobProperty);
	}

	protected PlaywrightBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		this(
			jsonObject, _DEFAULT_PLAYWRIGHT_RELATIVE_DIR_PATH,
			portalTestClassJob);
	}

	protected PlaywrightBatchTestClassGroup(
		JSONObject jsonObject, String playwrightRelativeDirPath,
		PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);

		_playwrightRelativeDirPath = playwrightRelativeDirPath;

		prepareTestClassGroup(batchName);
	}

	protected PlaywrightBatchTestClassGroup(
		String batchName, PlaywrightTestBatch playwrightTestBatch,
		PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		PlaywrightTestSelector playwrightTestSelector =
			playwrightTestBatch.getTestSelector();

		_playwrightRelativeDirPath = _DEFAULT_PLAYWRIGHT_RELATIVE_DIR_PATH;

		Set<JobProperty> playwrightJobProperties =
			playwrightTestSelector.getPlaywrightJobProperties();

		playwrightJobProperties.removeAll(Collections.singleton(null));

		for (JobProperty jobProperty : playwrightJobProperties) {
			Collections.addAll(
				_projectNames,
				jobProperty.getValue(
				).split(
					","
				));
		}

		recordJobProperties(new ArrayList<>(playwrightJobProperties));

		setTestClasses();
	}

	protected PlaywrightBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		this(
			batchName, _DEFAULT_PLAYWRIGHT_RELATIVE_DIR_PATH,
			portalTestClassJob);
	}

	protected PlaywrightBatchTestClassGroup(
		String batchName, String playwrightRelativeDirPath,
		PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		_playwrightRelativeDirPath = playwrightRelativeDirPath;

		prepareTestClassGroup(batchName);
	}

	protected List<JobProperty> getRelevantPlaywrightJobProperties() {
		Set<JobProperty> playwrightJobProperties = new HashSet<>();

		for (File modifiedFile :
				portalGitWorkingDirectory.getModifiedFilesList(false)) {

			List<JobProperty> playwrightTestProjectJobProperties =
				getJobProperties(
					modifiedFile, PLAYWRIGHT_TEST_PROJECT_PROPERTY_NAME,
					JobProperty.Type.MODULE_TEST_DIR, null);

			for (JobProperty playwrightTestProjectJobProperty :
					playwrightTestProjectJobProperties) {

				if (playwrightTestProjectJobProperty.getValue() != null) {
					String projectNames =
						playwrightTestProjectJobProperty.getValue();

					_addProjectNames(projectNames);

					playwrightJobProperties.add(
						playwrightTestProjectJobProperty);
				}
			}
		}

		playwrightJobProperties.removeAll(Collections.singleton(null));

		return new ArrayList<>(playwrightJobProperties);
	}

	protected List<JSONObject> getSpecJSONObjects() {
		return _specJSONObjects;
	}

	protected void prepareTestClassGroup(String batchName) {
		if (ignore()) {
			return;
		}

		if (testRelevantChanges) {
			List<JobProperty> relevantPlaywrightJobProperties =
				getRelevantPlaywrightJobProperties();

			if (!relevantPlaywrightJobProperties.isEmpty()) {
				recordJobProperties(relevantPlaywrightJobProperties);
			}
		}

		addDefaultProjectJobProperty(batchName);

		setTestClasses();
	}

	protected void setTestClasses() {
		long start = System.currentTimeMillis();

		_loadPlaywrightJSONObjects();

		for (String projectName : _projectNames) {
			List<TestClass> testClasses = _getTestClasses(projectName);

			if (testClasses.isEmpty()) {
				continue;
			}

			SegmentTestClassGroup segmentTestClassGroup =
				TestClassGroupFactory.newSegmentTestClassGroup(this);

			if (segmentTestClassGroup instanceof
					PlaywrightSegmentTestClassGroup) {

				PlaywrightSegmentTestClassGroup
					playwrightSegmentTestClassGroup =
						(PlaywrightSegmentTestClassGroup)segmentTestClassGroup;

				playwrightSegmentTestClassGroup.setProjectName(projectName);

				AxisTestClassGroup axisTestClassGroup =
					TestClassGroupFactory.newAxisTestClassGroup(this);

				playwrightSegmentTestClassGroup.addAxisTestClassGroup(
					axisTestClassGroup);

				for (TestClass testClass : testClasses) {
					axisTestClassGroup.addTestClass(testClass);

					addTestClass(testClass);
				}

				addAxisTestClassGroup(axisTestClassGroup);

				addSegmentTestClassGroup(playwrightSegmentTestClassGroup);
			}
		}

		List<TestClass> testClasses = getTestClasses();

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"[", getBatchName(), "] ", "Found ",
				String.valueOf(testClasses.size()),
				" Playwright test classes in ",
				JenkinsResultsParserUtil.toDurationString(duration)));
	}

	protected static final String PLAYWRIGHT_TEST_PROJECT_PROPERTY_NAME =
		"playwright.test.project";

	private void _addProjectNames(String projectNames) {
		projectNames = projectNames.trim();

		Collections.addAll(_projectNames, projectNames.split("\\s*,\\s*"));
	}

	private String _callNPMCommand(File baseDir, String npmCommand) {
		StringBuilder sb = new StringBuilder();

		if (JenkinsResultsParserUtil.isCINode()) {
			sb.append("export CI=true\n");
		}

		sb.append("export PATH=");

		String npmHome = _getPortalProperty("npm.home");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(npmHome)) {
			sb.append(npmHome);
			sb.append(":");
		}

		String nodeHome = _getPortalProperty("node.home");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(nodeHome)) {
			sb.append(nodeHome);
			sb.append("/bin:");
		}

		sb.append("${PATH}\n");

		sb.append(npmCommand);

		File npmScriptFile = new File(baseDir, "npm_script.sh");

		try {
			JenkinsResultsParserUtil.write(npmScriptFile, sb.toString());

			Process process = JenkinsResultsParserUtil.executeBashCommands(
				true, baseDir, 1000 * 60 * 10, sb.toString());

			return JenkinsResultsParserUtil.readInputStream(
				process.getInputStream());
		}
		catch (IOException | TimeoutException exception) {
			throw new RuntimeException(exception);
		}
		finally {
			JenkinsResultsParserUtil.delete(npmScriptFile);
		}
	}

	private String _getPortalProperty(String propertyName) {
		File workingDirectory = JenkinsResultsParserUtil.getCanonicalFile(
			portalGitWorkingDirectory.getWorkingDirectory());

		Properties portalProperties = JenkinsResultsParserUtil.getProperties(
			new File(workingDirectory, "build.properties"),
			new File(workingDirectory, "app.server.properties"),
			new File(workingDirectory, "release.properties"),
			new File(workingDirectory, "test.properties"));

		portalProperties.setProperty(
			"project.dir", workingDirectory.toString());

		return JenkinsResultsParserUtil.getProperty(
			portalProperties, propertyName);
	}

	private String _getProjectJSONObj() {
		_loadPlaywrightJSONObjects();

		JSONArray projectsJSONArray = _playwrightJSONObject.getJSONArray(
			"projects");

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < projectsJSONArray.length(); i++) {
			JSONObject projectJSONObject = projectsJSONArray.getJSONObject(i);

			sb.append(projectJSONObject.optString("name"));

			sb.append(",");
		}

		sb.setLength(sb.length() - 1);

		return sb.toString();
	}

	private List<JSONObject> _getSpecJSONObjects(JSONObject jsonObject) {
		List<JSONObject> specJSONObjects = new ArrayList<>();

		JSONArray suitesJSONArray = jsonObject.getJSONArray("suites");

		for (int i = 0; i < suitesJSONArray.length(); i++) {
			JSONObject suiteJSONObject = suitesJSONArray.getJSONObject(i);

			if (suiteJSONObject.has("suites")) {
				specJSONObjects.addAll(_getSpecJSONObjects(suiteJSONObject));
			}

			JSONArray specsJSONArray = suiteJSONObject.optJSONArray("specs");

			if (specsJSONArray == null) {
				continue;
			}

			for (int j = 0; j < specsJSONArray.length(); j++) {
				specJSONObjects.add(specsJSONArray.getJSONObject(j));
			}
		}

		return specJSONObjects;
	}

	private List<TestClass> _getTestClasses(String projectName) {
		List<TestClass> testClasses = new ArrayList<>();

		JSONObject configJSONObject = _playwrightJSONObject.getJSONObject(
			"config");

		File rootDir = new File(configJSONObject.getString("rootDir"));

		for (JSONObject specJSONObject : getSpecJSONObjects()) {
			JSONArray testsJSONArray = specJSONObject.optJSONArray("tests");

			if ((testsJSONArray == null) || testsJSONArray.isEmpty()) {
				continue;
			}

			JSONObject testJSONObject = testsJSONArray.getJSONObject(0);

			if (!Objects.equals(
					projectName, testJSONObject.optString("projectName"))) {

				continue;
			}

			testClasses.add(
				TestClassFactory.newTestClass(
					this, new File(rootDir, specJSONObject.getString("file")),
					specJSONObject.getString("title")));
		}

		return testClasses;
	}

	private void _loadPlaywrightJSONObjects() {
		synchronized (_playwrightJSONObjectsLoaded) {
			if (_playwrightJSONObjectsLoaded.get()) {
				return;
			}

			Job job = getJob();

			GitWorkingDirectory gitWorkingDirectory = null;

			if (job instanceof PortalTestClassJob) {
				PortalTestClassJob portalReleaseJob = (PortalTestClassJob)job;

				gitWorkingDirectory =
					portalReleaseJob.getPortalGitWorkingDirectory();
			}
			else {
				GitRepositoryJob gitRepositoryJob = (GitRepositoryJob)job;

				gitWorkingDirectory = gitRepositoryJob.getGitWorkingDirectory();
			}

			File workingDirectory = gitWorkingDirectory.getWorkingDirectory();

			File playwrightBaseDir = new File(
				workingDirectory, _playwrightRelativeDirPath);

			try {
				AntUtil.callTarget(
					portalGitWorkingDirectory.getWorkingDirectory(),
					"build.xml", "setup-yarn");
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}

			System.out.println("base dir: " + playwrightBaseDir);

			_callNPMCommand(playwrightBaseDir, "npm install");

			String result = _callNPMCommand(
				playwrightBaseDir,
				"npm run playwright test -- --list --reporter=json");

			System.out.println("results: " + result);

			try {
				int index = result.indexOf("\n{");

				result = result.substring(index);

				result = result.replace("Finished executing Bash commands.", "");

				_playwrightJSONObject = new JSONObject(result.trim());
			}
			catch (JSONException jsonException) {
				StringBuilder sb = new StringBuilder();

				sb.append("Unable to parse Playwright JSON object ");
				sb.append("<@U04GTH03Q>, <@U01EV0V1Y6N>\n");

				sb.append(System.getenv("TOP_LEVEL_BUILD_URL"));

				NotificationUtil.sendSlackNotification(
					sb.toString(), "#ci-notifications", ":playwright:",
					"Playwright Batch Creation Failure", "Liferay Playwright");
			}
			catch(Exception exception){
				exception.getMessage();
			}

			JSONArray errorsJSONArray = _playwrightJSONObject.optJSONArray(
				"errors");

			if ((errorsJSONArray != null) && (errorsJSONArray.length() > 0)) {
				StringBuilder sb = new StringBuilder();

				sb.append("Errors found in Playwright tests.\n");

				for (int i = 0; i < errorsJSONArray.length(); i++) {
					JSONObject errorJSONObject = errorsJSONArray.getJSONObject(
						i);

					System.out.println(errorJSONObject);

					String errorMessage = errorJSONObject.optString("message");

					if ((errorMessage != null) && !errorMessage.isEmpty()) {
						sb.append(errorMessage);
					}

					JSONObject errorLocationJSONObject =
						errorJSONObject.optJSONObject("location");

					if (errorLocationJSONObject != null) {
						sb.append(" [file://");
						sb.append(errorLocationJSONObject.opt("file"));
						sb.append(":");
						sb.append(errorLocationJSONObject.opt("line"));
						sb.append(":");
						sb.append(errorLocationJSONObject.opt("column"));
						sb.append("]");
					}

					String errorStack = errorJSONObject.optString("stack");

					if ((errorStack != null) && !errorStack.isEmpty()) {
						sb.append("\n");

						sb.append(errorStack);
					}

					String errorSnippet = errorJSONObject.optString("snippet");

					if ((errorSnippet != null) && !errorSnippet.isEmpty()) {
						sb.append("\n");

						sb.append(StringEscapeUtils.unescapeJava(errorSnippet));
					}

					sb.append("\n");
				}

				System.out.println(sb.toString());

				throw new RuntimeException(sb.toString());
			}

			_specJSONObjects.addAll(_getSpecJSONObjects(_playwrightJSONObject));

			_playwrightJSONObjectsLoaded.set(true);
		}
	}

	private static final String _DEFAULT_PLAYWRIGHT_RELATIVE_DIR_PATH =
		"modules/test/playwright";

	private static JSONObject _playwrightJSONObject;
	private static final AtomicBoolean _playwrightJSONObjectsLoaded =
		new AtomicBoolean();
	private static final List<JSONObject> _specJSONObjects =
		Collections.synchronizedList(new ArrayList<JSONObject>());

	private final String _playwrightRelativeDirPath;
	private final Set<String> _projectNames = new HashSet<>();

}