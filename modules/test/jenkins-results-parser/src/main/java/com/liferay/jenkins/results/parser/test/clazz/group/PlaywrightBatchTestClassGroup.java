/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.google.common.collect.Lists;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.clazz.TestClassFactory;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class PlaywrightBatchTestClassGroup extends BatchTestClassGroup {

	protected PlaywrightBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);
	}

	protected PlaywrightBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		if (ignore()) {
			return;
		}

		if (testRelevantChanges) {
			List<JobProperty> relevantPlaywrightJobProperties =
				getRelevantPlaywrightJobProperties();

			if (relevantPlaywrightJobProperties.isEmpty()) {
				return;
			}

			recordJobProperties(relevantPlaywrightJobProperties);
		}
		else {
			JobProperty jobProperty = getJobProperty(
				PLAYWRIGHT_TEST_PROJECT_PROPERTY_NAME, testSuiteName,
				batchName);

			if (jobProperty.getValue() == null) {
				return;
			}

			recordJobProperty(jobProperty);
		}

		File buildTestBatchFile = new File(
			portalGitWorkingDirectory.getWorkingDirectory(),
			"build-test-batch.xml");

		for (int axisIndex = 0; axisIndex < getAxisCount(); axisIndex++) {
			addTestClass(
				TestClassFactory.newTestClass(
					this, buildTestBatchFile, String.valueOf(axisIndex)));
		}

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
	}

	protected List<JobProperty> getRelevantPlaywrightJobProperties() {
		Set<File> modifiedModuleDirsSet;

		try {
			modifiedModuleDirsSet = new HashSet<>(
				portalGitWorkingDirectory.getModifiedModuleDirsList());
		}
		catch (IOException ioException) {
			File workingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get relevant module group directories in ",
					workingDirectory.getPath()),
				ioException);
		}

		modifiedModuleDirsSet.addAll(
			getRequiredModuleDirs(Lists.newArrayList(modifiedModuleDirsSet)));

		Set<JobProperty> playwrightJobProperties = new HashSet<>();

		for (File modifiedModuleDir : modifiedModuleDirsSet) {
			JobProperty playwrightTestProjectJobProperty = getJobProperty(
				PLAYWRIGHT_TEST_PROJECT_PROPERTY_NAME, modifiedModuleDir,
				JobProperty.Type.MODULE_TEST_DIR);

			if (playwrightTestProjectJobProperty.getValue() != null) {
				playwrightJobProperties.add(playwrightTestProjectJobProperty);
			}
		}

		playwrightJobProperties.removeAll(Collections.singleton(null));

		return new ArrayList<>(playwrightJobProperties);
	}

	protected static final String PLAYWRIGHT_TEST_PROJECT_PROPERTY_NAME =
		"playwright.test.project";

}