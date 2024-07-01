/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.suite.RelevantTestSuite;

import java.io.File;

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

	public static final String TEST_BATCH_CLASS_NAMES_FILTER =
		"test.batch.class.names.filter";

	public JUnitTestSelector(
		File propertiesFile, Properties properties, String batchName,
		String relevantRuleName, String testSuiteName) {

		super(
			propertiesFile, properties, batchName, relevantRuleName,
			testSuiteName, RelevantTestSuite.getJob());

		validate();

		addJobProperties();
	}

	public void addJobProperties() {
		_excludesJobProperties.add(getExcludesJobProperty());
		_filterJobProperties.add(getFilterJobProperty());
		_includesJobProperties.add(getIncludesJobProperty());
	}

	public List<JobProperty> getExcludesJobProperties() {
		return _excludesJobProperties;
	}

	public JobProperty getExcludesJobProperty() {
		return getJobProperty(
			MODULES_INCLUDES_REQUIRED_TEST_BATCH_CLASS_NAMES_EXCLUDES,
			JobProperty.Type.MODULE_EXCLUDE_GLOB);
	}

	public List<JobProperty> getFilterJobProperties() {
		return _filterJobProperties;
	}

	public JobProperty getFilterJobProperty() {
		return getJobProperty(
			TEST_BATCH_CLASS_NAMES_FILTER, JobProperty.Type.FILTER_GLOB);
	}

	public List<JobProperty> getIncludesJobProperties() {
		return _includesJobProperties;
	}

	public JobProperty getIncludesJobProperty() {
		return getJobProperty(
			MODULES_INCLUDES_REQUIRED_TEST_BATCH_CLASS_NAMES_INCLUDES,
			JobProperty.Type.MODULE_INCLUDE_GLOB);
	}

	@Override
	public void merge(TestSelector testSelector) {
		if (!(testSelector instanceof JUnitTestSelector)) {
			throw new RuntimeException("Unable to merge test selectors");
		}

		JUnitTestSelector jUnitTestSelector = (JUnitTestSelector)testSelector;

		_excludesJobProperties.add(jUnitTestSelector.getExcludesJobProperty());
		_filterJobProperties.add(jUnitTestSelector.getFilterJobProperty());
		_includesJobProperties.add(jUnitTestSelector.getIncludesJobProperty());
	}

	@Override
	public void validate() {
		validate(MODULES_INCLUDES_REQUIRED_TEST_BATCH_CLASS_NAMES_INCLUDES);
	}

	private final List<JobProperty> _excludesJobProperties = new ArrayList<>();
	private final List<JobProperty> _filterJobProperties = new ArrayList<>();
	private final List<JobProperty> _includesJobProperties = new ArrayList<>();

}