/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.suite.RelevantRuleConfigurationException;

import java.io.File;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Kenji Heigel
 */
public class PlaywrightTestSelector extends BaseTestSelector {

	public static final String PLAYWRIGHT_TEST_PROJECT =
		"playwright.test.project";

	public PlaywrightTestSelector(
			File propertiesFile, Properties properties, String batchName,
			String relevantRuleName, String testSuiteName)
		throws RelevantRuleConfigurationException {

		super(
			propertiesFile, properties, batchName, relevantRuleName,
			testSuiteName);

		validate();

		_playwrightJobProperties.add(
			getJobProperty(
				PLAYWRIGHT_TEST_PROJECT, JobProperty.Type.MODULE_TEST_DIR));
	}

	public Set<JobProperty> getPlaywrightJobProperties() {
		return _playwrightJobProperties;
	}

	@Override
	public void merge(TestSelector testSelector) {
		if (!(testSelector instanceof PlaywrightTestSelector)) {
			throw new RuntimeException("Unable to merge test selectors");
		}

		PlaywrightTestSelector playwrightTestSelector =
			(PlaywrightTestSelector)testSelector;

		_playwrightJobProperties.addAll(
			playwrightTestSelector.getPlaywrightJobProperties());
	}

	@Override
	public void validate() throws RelevantRuleConfigurationException {
		validate(PLAYWRIGHT_TEST_PROJECT);
	}

	private final Set<JobProperty> _playwrightJobProperties = new HashSet<>();

}