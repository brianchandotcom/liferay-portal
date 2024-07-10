/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

import com.liferay.jenkins.results.parser.job.property.JobProperty;

import java.io.File;

import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Kenji Heigel
 */
public class PlaywrightTestSelector extends BaseTestSelector {

	public static final String PLAYWRIGHT_TEST_PROJECT =
		"playwright.test.project";

	public PlaywrightTestSelector(
		File propertiesFile, Properties properties, String batchName,
		String relevantRuleName, String testSuiteName) {

		super(
			propertiesFile, properties, batchName, relevantRuleName,
			testSuiteName);

		validate();

		String playwrightProjectNamesValue = getPlaywrightProjectNamesValue();

		Collections.addAll(
			_playwrightProjectNames, playwrightProjectNamesValue.split(","));
	}

	public Set<JobProperty> getPlaywrightJobProperties() {
		return _playwrightJobProperties;
	}

	public Set<String> getPlaywrightProjectNames() {
		return _playwrightProjectNames;
	}

	public String getPlaywrightProjectNamesValue() {
		JobProperty playwrightJobProperty = getJobProperty(
			PLAYWRIGHT_TEST_PROJECT, JobProperty.Type.MODULE_TEST_DIR);

		_playwrightJobProperties.add(playwrightJobProperty);

		return playwrightJobProperty.getValue();
	}

	@Override
	public void merge(TestSelector testSelector) {
		if (!(testSelector instanceof PlaywrightTestSelector)) {
			throw new RuntimeException("Unable to merge test selectors");
		}

		PlaywrightTestSelector playwrightTestSelector =
			(PlaywrightTestSelector)testSelector;

		_playwrightProjectNames.addAll(
			playwrightTestSelector.getPlaywrightProjectNames());

		_playwrightJobProperties.addAll(
			playwrightTestSelector.getPlaywrightJobProperties());
	}

	public void validate() {
		validate(PLAYWRIGHT_TEST_PROJECT);
	}

	private final Set<JobProperty> _playwrightJobProperties = new HashSet<>();
	private final Set<String> _playwrightProjectNames = new TreeSet<>();

}