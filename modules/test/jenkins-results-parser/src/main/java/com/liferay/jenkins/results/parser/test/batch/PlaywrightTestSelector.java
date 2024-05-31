/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

import java.util.Collections;
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
		Properties properties, String batchName, String relevantRuleName,
		String testSuiteName) {

		super(properties, batchName, relevantRuleName, testSuiteName);

		validate();

		String playwrightProjectNamesValue = getProperty(
			PLAYWRIGHT_TEST_PROJECT);

		Collections.addAll(
			_playwrightProjectNames, playwrightProjectNamesValue.split(","));
	}

	public Set<String> getPlaywrightProjectNames() {
		return _playwrightProjectNames;
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
	}

	public void validate() {
		validate(PLAYWRIGHT_TEST_PROJECT);
	}

	private final Set<String> _playwrightProjectNames = new TreeSet<>();

}