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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.AxisBuild;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.spira.SpiraCustomProperty;
import com.liferay.jenkins.results.parser.spira.SpiraCustomPropertyValue;
import com.liferay.jenkins.results.parser.spira.SpiraProject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseComponent;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseObject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalBatchTestClassGroup;
import com.liferay.poshi.core.PoshiContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author Michael Hashimoto
 */
public class FunctionalAxisSpiraTestResult extends BaseAxisSpiraTestResult {

	@Override
	public Integer getDuration() {
		TestResult testResult = getTestResult();

		if (testResult != null) {
			return (int)testResult.getDuration();
		}

		return super.getDuration();
	}

	@Override
	public List<TestResult> getFailedTestResults() {
		List<TestResult> failedTestResults = new ArrayList<>();

		TestResult testResult = getTestResult();

		if ((testResult != null) && testResult.isFailing()) {
			failedTestResults.add(testResult);
		}

		return failedTestResults;
	}

	public String getPoshiPropertyValue(String propertyName) {
		Properties poshiProperties =
			PoshiContext.getNamespacedClassCommandNameProperties(
				_functionalTestClass.getTestClassMethodName());

		if ((poshiProperties == null) ||
			!poshiProperties.containsKey(propertyName)) {

			return null;
		}

		return poshiProperties.getProperty(propertyName);
	}

	@Override
	public SpiraTestCaseObject getSpiraTestCaseObject() {
		SpiraTestCaseObject spiraTestCaseObject =
			super.getSpiraTestCaseObject();

		if (!_isSpiraPropertyUpdateEnabled()) {
			return spiraTestCaseObject;
		}

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		SpiraProject spiraProject = spiraBuildResult.getSpiraProject();

		String priority = _getPriority();

		if ((priority != null) && priority.matches("\\d+")) {
			spiraTestCaseObject.updateSpiraTestCasePriority(
				spiraProject.getSpiraTestCasePriorityByScore(
					Integer.valueOf(priority)));
		}

		String teamName = getTeamName();

		if ((teamName != null) && !teamName.isEmpty()) {
			SpiraCustomProperty spiraCustomProperty =
				SpiraCustomProperty.createSpiraCustomProperty(
					spiraProject, SpiraTestCaseObject.class, "Team",
					SpiraCustomProperty.Type.LIST);

			spiraTestCaseObject.updateSpiraCustomPropertyValues(
				SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
					spiraCustomProperty, teamName));
		}

		Set<String> componentNameSet = new HashSet<>();

		String mainComponentName = _getMainComponentName();

		if ((mainComponentName != null) && !mainComponentName.isEmpty()) {
			componentNameSet.add(mainComponentName);
		}

		String componentNames = _getComponentNames();

		if ((componentNames != null) && !componentNames.isEmpty()) {
			Collections.addAll(componentNameSet, componentNames.split(","));
		}

		if (!componentNameSet.isEmpty()) {
			List<SpiraTestCaseComponent> spiraTestCaseComponents =
				new ArrayList<>();

			for (String componentName : componentNameSet) {
				spiraTestCaseComponents.add(
					SpiraTestCaseComponent.createSpiraTestCaseComponent(
						spiraProject, componentName));
			}

			spiraTestCaseObject.updateSpiraTestCaseComponents(
				spiraTestCaseComponents);
		}

		return spiraTestCaseObject;
	}

	public SpiraTestCaseRun.Status getSpiraTestCaseRunStatus() {
		TestResult testResult = getTestResult();

		if (testResult != null) {
			if (testResult.isFailing()) {
				return SpiraTestCaseRun.Status.FAILED;
			}

			return SpiraTestCaseRun.Status.PASSED;
		}

		return super.getSpiraTestCaseRunStatus();
	}

	public String getTeamName() {
		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		String teamNames = JenkinsResultsParserUtil.getProperty(
			spiraBuildResult.getPortalTestProperties(), "testray.team.names");

		if (teamNames.isEmpty()) {
			return null;
		}

		for (String teamName : teamNames.split(",")) {
			String teamComponentNamesString =
				JenkinsResultsParserUtil.getProperty(
					spiraBuildResult.getPortalTestProperties(),
					JenkinsResultsParserUtil.combine(
						"testray.team.", teamName, ".component.names"));

			List<String> teamComponentNames = Arrays.asList(
				teamComponentNamesString.split(","));

			if (!teamComponentNames.contains(_getMainComponentName())) {
				continue;
			}

			StringBuilder sb = new StringBuilder();

			for (String teamNameWord : teamName.split("-")) {
				sb.append(StringUtils.capitalize(teamNameWord));
				sb.append(" ");
			}

			if (sb.length() > 0) {
				sb.setLength(sb.length() - 1);
			}

			return sb.toString();
		}

		return null;
	}

	public String getTestClassMethodName() {
		return _functionalTestClass.getTestClassMethodName();
	}

	public TestClassResult getTestClassResult() {
		TestResult testResult = getTestResult();

		if (testResult == null) {
			return null;
		}

		return testResult.getTestClassResult();
	}

	@Override
	public String getTestName() {
		String testName = _functionalTestClass.getTestClassMethodName();

		return testName.replaceAll("[^\\.]+\\.(.*)", "$1");
	}

	public TestResult getTestResult() {
		if (_testResult != null) {
			return _testResult;
		}

		AxisBuild axisBuild = getAxisBuild();

		return _testResult = axisBuild.getTestResult(
			_functionalTestClass.getTestClassMethodName());
	}

	protected FunctionalAxisSpiraTestResult(
		SpiraBuildResult spiraBuildResult,
		FunctionalAxisTestClassGroup axisTestClassGroup,
		FunctionalBatchTestClassGroup.FunctionalTestClass functionalTestClass) {

		super(spiraBuildResult, axisTestClassGroup);

		_functionalTestClass = functionalTestClass;
	}

	private String _getComponentNames() {
		return getPoshiPropertyValue("testray.component.names");
	}

	private String _getMainComponentName() {
		return getPoshiPropertyValue("testray.main.component.name");
	}

	private String _getPriority() {
		return getPoshiPropertyValue("priority");
	}

	private boolean _isSpiraPropertyUpdateEnabled() {
		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		TopLevelBuild topLevelBuild = spiraBuildResult.getTopLevelBuild();

		String spiraPropertyUpdate = JenkinsResultsParserUtil.getProperty(
			spiraBuildResult.getPortalTestProperties(),
			"test.batch.spira.property.update", topLevelBuild.getJobName(),
			topLevelBuild.getTestSuiteName());

		if ((spiraPropertyUpdate != null) &&
			spiraPropertyUpdate.equals("true")) {

			return true;
		}

		return false;
	}

	private final FunctionalBatchTestClassGroup.FunctionalTestClass
		_functionalTestClass;
	private TestResult _testResult;

}