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
public abstract class BaseTestSelector implements TestSelector {

	public BaseTestSelector(
		Properties properties, String batchName, String relevantRuleName,
		String testSuiteName) {

		_properties = properties;
		_batchName = batchName;
		_relevantRuleName = relevantRuleName;
		_testSuiteName = testSuiteName;
	}

	public String getProperty(String propertyName) {
		return JenkinsResultsParserUtil.getProperty(
			_properties, propertyName, _batchName, _relevantRuleName,
			_testSuiteName);
	}

	public TestBatch getTestBatch() {
		return _testBatch;
	}

	public void setTestBatch(TestBatch testBatch) {
		_testBatch = testBatch;
	}

	protected void validate(String propertyName) {
		if (getProperty(propertyName) == null) {
			throw new IllegalStateException(
				"Unable to create batch " + _batchName + " since " +
					propertyName + " is not set");
		}
	}

	private final String _batchName;
	private final Properties _properties;
	private final String _relevantRuleName;
	private TestBatch _testBatch;
	private final String _testSuiteName;

}