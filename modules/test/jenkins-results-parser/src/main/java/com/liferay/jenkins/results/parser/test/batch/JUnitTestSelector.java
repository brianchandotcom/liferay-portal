/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

import java.nio.file.PathMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Kenji Heigel
 */
public class JUnitTestSelector extends BaseTestSelector {

	public static final String
		MODULES_INCLUDES_REQUIRED_TEST_BATCH_CLASS_NAMES_EXCLUDES =
			"modules.includes.required.test.batch.class.names.excludes";

	public static final String
		MODULES_INCLUDES_REQUIRED_TEST_BATCH_CLASS_NAMES_INCLUDES =
			"modules.includes.required.test.batch.class.names.includes";

	public JUnitTestSelector(
		Properties properties, String batchName, String relevantRuleName,
		String testSuiteName) {

		super(properties, batchName, relevantRuleName, testSuiteName);

		validate();
	}

	public List<PathMatcher> getExcludesPathMatchers() {
		return _excludesPathMatchers;
	}

	public List<PathMatcher> getFilterPathMatchers() {
		return _filterPathMatchers;
	}

	public List<PathMatcher> getIncludesPathMatchers() {
		return _includesPathMatchers;
	}

	@Override
	public void merge(TestSelector testSelector) {
		if (!(testSelector instanceof JUnitTestSelector)) {
			throw new RuntimeException("Unable to merge test selectors");
		}

		JUnitTestSelector jUnitTestSelector = (JUnitTestSelector)testSelector;

		_excludesPathMatchers.addAll(
			jUnitTestSelector.getExcludesPathMatchers());
		_filterPathMatchers.addAll(jUnitTestSelector.getFilterPathMatchers());
		_includesPathMatchers.addAll(
			jUnitTestSelector.getIncludesPathMatchers());
	}

	@Override
	public void validate() {
		validate(MODULES_INCLUDES_REQUIRED_TEST_BATCH_CLASS_NAMES_INCLUDES);
	}

	private final List<PathMatcher> _excludesPathMatchers = new ArrayList<>();
	private final List<PathMatcher> _filterPathMatchers = new ArrayList<>();
	private final List<PathMatcher> _includesPathMatchers = new ArrayList<>();

}