/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.job.property.JobProperty;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Kenji Heigel
 */
public class PoshiTestSelector extends BaseTestSelector {

	public static final String TEST_BATCH_RUN_PROPERTY_GLOBAL_QUERY =
		"test.batch.run.property.global.query";

	public static final String TEST_BATCH_RUN_PROPERTY_QUERY =
		"test.batch.run.property.query";

	public PoshiTestSelector(
		File propertiesFile, Properties properties, String batchName,
		String relevantRuleName, String testSuiteName) {

		super(
			propertiesFile, properties, batchName, relevantRuleName,
			testSuiteName);

		validate();

		addPoshiQuery();

		_propertiesFile = propertiesFile;

		JenkinsResultsParserUtil.validatePQL(_poshiQuery, _propertiesFile);
	}

	public void addPoshiQuery() {
		JobProperty poshiJobProperty = getJobProperty(
			TEST_BATCH_RUN_PROPERTY_QUERY, JobProperty.Type.MODULE_TEST_DIR);

		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				poshiJobProperty.getValue())) {

			_poshiJobProperties.add(poshiJobProperty);

			_poshiQuery = poshiJobProperty.getValue();
		}
	}

	public String getGlobalPoshiQuery() {
		JobProperty globalJobProperty = getGlobalJobProperty(
			TEST_BATCH_RUN_PROPERTY_GLOBAL_QUERY);

		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				globalJobProperty.getValue())) {

			_poshiJobProperties.add(globalJobProperty);
		}

		return globalJobProperty.getValue();
	}

	public List<JobProperty> getPoshiJobProperties() {
		return _poshiJobProperties;
	}

	public String getPoshiQuery() {
		return _poshiQuery;
	}

	public File getPropertiesFile() {
		return _propertiesFile;
	}

	@Override
	public void merge(TestSelector testSelector) {
		if (!(testSelector instanceof PoshiTestSelector)) {
			throw new RuntimeException("Unable to merge test selectors");
		}

		_mergePQL(testSelector);

		String globalPQL = getGlobalPoshiQuery();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(globalPQL) &&
			!_poshiQuery.contains(globalPQL)) {

			_globalPoshiQuery = globalPQL;

			_poshiQuery = JenkinsResultsParserUtil.combine(
				"(", globalPQL, ") AND (", _poshiQuery, ")");
		}
	}

	@Override
	public void validate() {
		validate(TEST_BATCH_RUN_PROPERTY_QUERY);
	}

	private void _mergePQL(TestSelector testSelector) {
		PoshiTestSelector poshiTestSelector = (PoshiTestSelector)testSelector;

		String newPQL = poshiTestSelector.getPoshiQuery();

		_poshiJobProperties.addAll(poshiTestSelector.getPoshiJobProperties());

		JenkinsResultsParserUtil.validatePQL(newPQL, _propertiesFile);

		if (newPQL.contains(_poshiQuery)) {
			_poshiQuery = newPQL;
		}
		else if (!_poshiQuery.contains(newPQL)) {
			_poshiQuery += JenkinsResultsParserUtil.combine(
				" OR (", newPQL, ")");
		}
	}

	private String _globalPoshiQuery;
	private final List<JobProperty> _poshiJobProperties = new ArrayList<>();
	private String _poshiQuery;
	private final File _propertiesFile;

}