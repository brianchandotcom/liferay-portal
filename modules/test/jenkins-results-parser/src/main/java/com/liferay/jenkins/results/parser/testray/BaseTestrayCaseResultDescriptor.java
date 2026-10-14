/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.QAWebsitesGitRepositoryJob;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.job.property.JobPropertyFactory;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.io.File;
import java.io.IOException;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestrayCaseResultDescriptor
	implements TestrayCaseResultDescriptor {

	@Override
	public String getBatchName() {
		AxisTestClassGroup axisTestClassGroup = getAxisTestClassGroup();

		if (axisTestClassGroup == null) {
			return null;
		}

		return axisTestClassGroup.getBatchName();
	}

	@Override
	public String getComponentName() {
		TestClass testClass = getTestClass();

		if (testClass != null) {
			String testrayMainComponentName =
				testClass.getTestrayMainComponentName();

			if (!JenkinsResultsParserUtil.isNullOrEmpty(
					testrayMainComponentName)) {

				return testrayMainComponentName;
			}
		}

		if (getAxisTestClassGroup() != null) {
			JobProperty testrayComponentNameJobProperty = _getJobProperty(
				"testray.component.name");

			String testrayComponentName =
				testrayComponentNameJobProperty.getValue();

			if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayComponentName)) {
				return testrayComponentName;
			}
		}

		return _getBuildProperty("testray.case.component");
	}

	@Override
	public Job getJob() {
		return _job;
	}

	@Override
	public TestrayCaseResultDescriptor getParentTestrayCaseResultDescriptor() {
		return _parentTestrayCaseResultDescriptor;
	}

	@Override
	public int getPriority() {
		String testrayCasePriority = _getBuildProperty("testray.case.priority");

		if ((testrayCasePriority != null) &&
			testrayCasePriority.matches("\\d+")) {

			return Integer.parseInt(testrayCasePriority);
		}

		return 5;
	}

	@Override
	public String getSubcomponentNames() {
		return "";
	}

	@Override
	public String getTeamName() {
		if (getAxisTestClassGroup() == null) {
			return _getBuildProperty("testray.case.team");
		}

		JobProperty teamNamesJobProperty = _getJobProperty(
			"testray.team.names");

		String teamNames = teamNamesJobProperty.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(teamNames)) {
			return _getBuildProperty("testray.case.team");
		}

		String componentName = getComponentName();

		for (String teamName : teamNames.split(",")) {
			JobProperty teamComponentNamesJobProperty = _getJobProperty(
				"testray.team." + teamName + ".component.names");

			if (teamComponentNamesJobProperty == null) {
				continue;
			}

			String teamComponentNames =
				teamComponentNamesJobProperty.getValue();

			if (JenkinsResultsParserUtil.isNullOrEmpty(teamComponentNames)) {
				continue;
			}

			for (String teamComponentName : teamComponentNames.split(",")) {
				if (teamComponentName.equals(componentName)) {
					return _upperCaseFirstLetterOfEachWord(
						teamName.replace("-", " "));
				}
			}
		}

		return _getBuildProperty("testray.case.team");
	}

	@Override
	public File getTestBaseDir() {
		AxisTestClassGroup axisTestClassGroup = getAxisTestClassGroup();

		if (axisTestClassGroup == null) {
			return null;
		}

		return axisTestClassGroup.getTestBaseDir();
	}

	@Override
	public String getTestSuiteName() {
		if (_job == null) {
			return null;
		}

		return _job.getTestSuiteName();
	}

	@Override
	public String getType() {
		return _getBuildProperty("testray.case.type");
	}

	protected BaseTestrayCaseResultDescriptor(
		Job job,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor) {

		_job = job;
		_parentTestrayCaseResultDescriptor = parentTestrayCaseResultDescriptor;
	}

	protected AxisTestClassGroup getAxisTestClassGroup() {
		return null;
	}

	protected TestClass getTestClass() {
		return null;
	}

	private String _getBuildProperty(String basePropertyName) {
		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(), basePropertyName,
				getBatchName());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private JobProperty _getJobProperty(String basePropertyName) {
		AxisTestClassGroup axisTestClassGroup = getAxisTestClassGroup();

		Job job = axisTestClassGroup.getJob();

		if (job instanceof QAWebsitesGitRepositoryJob) {
			return JobPropertyFactory.newJobProperty(
				basePropertyName, getBatchName(), job,
				axisTestClassGroup.getTestBaseDir(),
				JobProperty.Type.QA_WEBSITES_TEST_DIR);
		}

		return JobPropertyFactory.newJobProperty(
			basePropertyName, getBatchName(), job);
	}

	private String _upperCaseFirstLetterOfEachWord(String string) {
		StringBuilder sb = new StringBuilder(string);

		for (int i = 0; i < sb.length(); i++) {
			if ((i == 0) || (sb.charAt(i - 1) == ' ')) {
				sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
			}
		}

		return sb.toString();
	}

	private final Job _job;
	private final TestrayCaseResultDescriptor
		_parentTestrayCaseResultDescriptor;

}