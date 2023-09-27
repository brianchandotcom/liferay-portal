/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayRun {

	public TestrayRun(
		TestrayBuild testrayBuild, String batchName,
		List<File> propertiesFiles) {

		_testrayBuild = testrayBuild;
		_batchName = batchName;

		try {
			_properties.putAll(JenkinsResultsParserUtil.getBuildProperties());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		for (int i = propertiesFiles.size() - 1; i >= 0; i--) {
			_properties.putAll(
				JenkinsResultsParserUtil.getProperties(propertiesFiles.get(i)));
		}
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

	public String getRunID() {
		JSONObject runsJSONObject = _testrayBuild.getRunsJSONObject();

		if (runsJSONObject == null) {
			return null;
		}

		JSONArray dataJSONArray = runsJSONObject.optJSONArray("data");

		if ((dataJSONArray == JSONObject.NULL) || dataJSONArray.isEmpty()) {
			return null;
		}

		Map<String, String> factorValues = new HashMap<>();

		for (Factor factor : getFactors()) {
			String factorValue = factor.getValue();

			factorValues.put(factor.getName(), factorValue.toLowerCase());
		}

		for (int i = 0; i < dataJSONArray.length(); i++) {
			JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

			JSONArray testrayFactorsJSONArray = dataJSONObject.optJSONArray(
				"testrayFactors");

			if ((testrayFactorsJSONArray == JSONObject.NULL) ||
				testrayFactorsJSONArray.isEmpty()) {

				continue;
			}

			Map<String, String> factorOptionNames = new HashMap<>();

			for (int j = 0; j < testrayFactorsJSONArray.length(); j++) {
				JSONObject testrayFactorJSONObject =
					testrayFactorsJSONArray.getJSONObject(j);

				String factorCategoryName = testrayFactorJSONObject.optString(
					"testrayFactorCategoryName");
				String factorOptionName = testrayFactorJSONObject.optString(
					"testrayFactorOptionName");

				if (JenkinsResultsParserUtil.isNullOrEmpty(
						factorCategoryName) ||
					JenkinsResultsParserUtil.isNullOrEmpty(factorOptionName)) {

					continue;
				}

				factorOptionNames.put(
					factorCategoryName, factorOptionName.toLowerCase());
			}

			if (factorValues.equals(factorOptionNames)) {
				return dataJSONObject.getString("testrayRunId");
			}
		}

		return null;
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
		String factorName = JenkinsResultsParserUtil.getProperty(
			_properties,
			JenkinsResultsParserUtil.combine(
				_PROPERTY_KEY_FACTOR_NAME, "[", factorNameKey, "]"));

		if (!JenkinsResultsParserUtil.isNullOrEmpty(factorName)) {
			return factorName;
		}

		return null;
	}

	private Set<String> _getFactorNameKeys() {
		Set<String> factorNameKeys = new TreeSet<>();

		for (String propertyName : _properties.stringPropertyNames()) {
			Matcher matcher = _factorNamePattern.matcher(propertyName);

			if (!matcher.find()) {
				continue;
			}

			factorNameKeys.add(matcher.group("nameKey"));
		}

		return factorNameKeys;
	}

	private String _getFactorValue(String factorNameKey) {
		String matchingValueKey = null;
		String matchingPropertyName = null;

		for (String propertyName : _properties.stringPropertyNames()) {
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
				_properties, matchingPropertyName);
		}

		String factorValue = JenkinsResultsParserUtil.getProperty(
			_properties,
			JenkinsResultsParserUtil.combine(
				_PROPERTY_KEY_FACTOR_VALUE, "[", factorNameKey, "]"));

		if (JenkinsResultsParserUtil.isNullOrEmpty(factorValue)) {
			return null;
		}

		return factorValue;
	}

	private static final String _PROPERTY_KEY_FACTOR_NAME =
		"testray.environment.factor.name";

	private static final String _PROPERTY_KEY_FACTOR_VALUE =
		"testray.environment.factor.value";

	private static final Pattern _factorNamePattern = Pattern.compile(
		_PROPERTY_KEY_FACTOR_NAME + "\\[(?<nameKey>[^\\]]+)\\]");
	private static final Pattern _factorValuePattern = Pattern.compile(
		_PROPERTY_KEY_FACTOR_VALUE +
			"\\[(?<nameKey>[^\\]]+)\\](\\[(?<valueKey>[^\\]]+)\\])?");

	private final String _batchName;
	private final Properties _properties = new Properties();
	private final TestrayBuild _testrayBuild;

}