/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.util.Properties;

/**
 * @author Kenji Heigel
 */
public class PoshiTestSelector extends BaseTestSelector {

	public static final String TEST_BATCH_RUN_PROPERTY_QUERY =
		"test.batch.run.property.query";

	public PoshiTestSelector(
		Properties properties, String batchName, String relevantRuleName,
		String testSuiteName) {

		super(properties, batchName, relevantRuleName, testSuiteName);

		validate();
	}

	public String getPoshiQuery() {
		return _poshiQuery;
	}

	@Override
	public void merge(TestSelector testSelector) {
		if (!(testSelector instanceof PoshiTestSelector)) {
			throw new RuntimeException("Unable to merge test selectors");
		}

		PoshiTestSelector poshiTestSelector = (PoshiTestSelector)testSelector;

		_poshiQuery = JenkinsResultsParserUtil.combine(
			"(", _poshiQuery, ") AND (", poshiTestSelector.getPoshiQuery(),
			")");
	}

	@Override
	public void validate() {
		validate(TEST_BATCH_RUN_PROPERTY_QUERY);
	}

	private String _poshiQuery;

}