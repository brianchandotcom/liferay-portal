/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class TestrayRun {

	public TestrayRun(TestrayBuild testrayBuild, String batchName) {
		_testrayBuild = testrayBuild;
		_batchName = batchName;
	}

	public String getBatchName() {
		return _batchName;
	}

	public List<Factor> getFactors() {
		List<Factor> factors = new ArrayList<>();

		for (String factorNameKey : _getFactorNameKeys()) {
			String factoryName = _getFactorName(factorNameKey);
			String factoryValue = _getFactorValue(factorNameKey);

			if (JenkinsResultsParserUtil.isNullOrEmpty(factoryName) ||
				JenkinsResultsParserUtil.isNullOrEmpty(factoryValue)) {

				continue;
			}

			factors.add(new Factor(factoryName, factoryValue));
		}

		return factors;
	}

	public String getRunIDString() {
		List<String> factorValues = new ArrayList<>();

		for (Factor factor : getFactors()) {
			factorValues.add(factor.getValue());
		}

		return JenkinsResultsParserUtil.join("|", factorValues);
	}

	public TestrayBuild getTestrayBuild() {
		return _testrayBuild;
	}

	public static class Factor {

		public Factor(String name, String value) {
			_name = name;
			_value = value;
		}

		public String getName() {
			return _name;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return getName() + "=" + getValue();
		}

		private final String _name;
		private final String _value;

	}

	private String _getFactorName(String factorNameKey) {
		try {
			return JenkinsResultsParserUtil.getBuildProperty(
				JenkinsResultsParserUtil.combine(
					_FACTOR_NAME_PROPERTY, "[", factorNameKey, "]"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private Set<String> _getFactorNameKeys() {
		Set<String> factorNameKeys = new TreeSet<>();

		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		for (String propertyName : buildProperties.stringPropertyNames()) {
			Matcher matcher = _factorNamePattern.matcher(propertyName);

			if (!matcher.find()) {
				continue;
			}

			factorNameKeys.add(matcher.group("nameKey"));
		}

		return factorNameKeys;
	}

	private String _getFactorValue(String factorNameKey) {
		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String matchingValueKey = null;
		String matchingPropertyName = null;

		for (String propertyName : buildProperties.stringPropertyNames()) {
			Matcher matcher = _factorValuePattern.matcher(propertyName);

			if (!matcher.find()) {
				continue;
			}

			String nameKey = matcher.group("nameKey");

			if (!nameKey.equals(factorNameKey)) {
				continue;
			}

			String valueKey = matcher.group("valueKey");

			if ((valueKey == null) || !_batchName.contains(valueKey)) {
				continue;
			}

			if ((matchingValueKey == null) ||
				(valueKey.length() > matchingValueKey.length())) {

				matchingValueKey = valueKey;
				matchingPropertyName = propertyName;
			}
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(matchingPropertyName)) {
			return JenkinsResultsParserUtil.getProperty(
				buildProperties, matchingPropertyName);
		}

		return JenkinsResultsParserUtil.getProperty(
			buildProperties,
			JenkinsResultsParserUtil.combine(
				_FACTOR_VALUE_PROPERTY, "[", factorNameKey, "]"));
	}

	private static final String _FACTOR_NAME_PROPERTY =
		"testray.environment.factor.name";

	private static final String _FACTOR_VALUE_PROPERTY =
		"testray.environment.factor.value";

	private static final Pattern _factorNamePattern = Pattern.compile(
		_FACTOR_NAME_PROPERTY + "\\[(?<nameKey>[^\\]]+)\\]");
	private static final Pattern _factorValuePattern = Pattern.compile(
		_FACTOR_VALUE_PROPERTY +
			"\\[(?<nameKey>[^\\]]+)\\](\\[(?<valueKey>[^\\]]+)\\])?");

	private final String _batchName;
	private final TestrayBuild _testrayBuild;

}