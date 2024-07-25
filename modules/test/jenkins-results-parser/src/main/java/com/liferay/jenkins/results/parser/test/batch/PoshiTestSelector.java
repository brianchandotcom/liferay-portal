/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.suite.RelevantRuleConfigurationException;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Kenji Heigel
 */
public class PoshiTestSelector extends BaseTestSelector {

	public PoshiTestSelector(
			File propertiesFile, Properties properties, String batchName,
			String relevantRuleName, String testSuiteName)
		throws RelevantRuleConfigurationException {

		super(
			propertiesFile, properties, batchName, relevantRuleName,
			testSuiteName);

		validate();

		_addPoshiQuery();
	}

	public List<JobProperty> getPoshiJobProperties() {
		return _poshiJobProperties;
	}

	public String getPoshiQuery() {
		return getPoshiQuery(true);
	}

	public String getPoshiQuery(boolean includeGlobalPoshiQuery) {
		String globalPoshiQuery = _getGlobalPoshiQuery();

		if (includeGlobalPoshiQuery &&
			!JenkinsResultsParserUtil.isNullOrEmpty(globalPoshiQuery) &&
			!_poshiQuery.contains(globalPoshiQuery)) {

			return JenkinsResultsParserUtil.combine(
				"(", globalPoshiQuery, ") AND (", _poshiQuery, ")");
		}

		return _poshiQuery;
	}

	@Override
	public void merge(TestSelector testSelector) {
		if (!(testSelector instanceof PoshiTestSelector)) {
			throw new RuntimeException("Unable to merge test selectors");
		}

		PoshiTestSelector poshiTestSelector = (PoshiTestSelector)testSelector;

		String newPoshiQuery = poshiTestSelector.getPoshiQuery(false);

		_poshiJobProperties.addAll(poshiTestSelector.getPoshiJobProperties());

		JenkinsResultsParserUtil.validatePQL(
			newPoshiQuery, getPropertiesFile());

		if (newPoshiQuery.contains(_poshiQuery)) {
			_poshiQuery = newPoshiQuery;
		}
		else if (!_poshiQuery.contains(newPoshiQuery)) {
			_poshiQuery += JenkinsResultsParserUtil.combine(
				" OR (", newPoshiQuery, ")");
		}
	}

	@Override
	public void validate() throws RelevantRuleConfigurationException {
		validate(_TEST_BATCH_RUN_PROPERTY_QUERY);
	}

	private void _addPoshiQuery() throws RelevantRuleConfigurationException {
		JobProperty poshiJobProperty = getJobProperty(
			_TEST_BATCH_RUN_PROPERTY_QUERY, JobProperty.Type.MODULE_TEST_DIR);

		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				poshiJobProperty.getValue())) {

			_poshiJobProperties.add(poshiJobProperty);

			_poshiQuery = poshiJobProperty.getValue();

			try {
				JenkinsResultsParserUtil.validatePQL(
					_poshiQuery, getPropertiesFile());
			}
			catch (Exception exception) {
				throw new RelevantRuleConfigurationException(
					exception.getMessage());
			}
		}
	}

	private String _getGlobalPoshiQuery() {
		if (_globalPoshiQuery != null) {
			return _globalPoshiQuery;
		}

		JobProperty globalJobProperty = getGlobalJobProperty(
			_TEST_BATCH_RUN_PROPERTY_GLOBAL_QUERY);

		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				globalJobProperty.getValue())) {

			_poshiJobProperties.add(globalJobProperty);
		}

		_globalPoshiQuery = globalJobProperty.getValue();

		return _globalPoshiQuery;
	}

	private static final String _TEST_BATCH_RUN_PROPERTY_GLOBAL_QUERY =
		"test.batch.run.property.global.query";

	private static final String _TEST_BATCH_RUN_PROPERTY_QUERY =
		"test.batch.run.property.query";

	private String _globalPoshiQuery;
	private final List<JobProperty> _poshiJobProperties = new ArrayList<>();
	private String _poshiQuery;

}