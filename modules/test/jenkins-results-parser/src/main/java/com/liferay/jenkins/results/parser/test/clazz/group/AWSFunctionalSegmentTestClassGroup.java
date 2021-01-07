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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class AWSFunctionalSegmentTestClassGroup
	extends FunctionalSegmentTestClassGroup {

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.getTestCasePropertiesContent());
		sb.append("\n");

		List<Map.Entry<String, String>> entries = new ArrayList<>();

		entries.add(_getAppServerTypeEntry());
		entries.add(_getDatabaseTypeEntry());
		entries.add(_getDatabaseVersionEntry());
		entries.add(_getOperatingSystemTypeEntry());
		entries.add(_getOperatingSystemVersionEntry());

		entries.removeAll(Collections.singleton(null));

		for (Map.Entry<String, String> entry : entries) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			sb.append("\n");
		}

		return sb.toString();
	}

	protected AWSFunctionalSegmentTestClassGroup(
		FunctionalBatchTestClassGroup parentFunctionalBatchTestClassGroup) {

		super(parentFunctionalBatchTestClassGroup);

		_parentFunctionalBatchTestClassGroup =
			parentFunctionalBatchTestClassGroup;
	}

	private Map.Entry<String, String> _getAppServerTypeEntry() {
		return _getEnvironmentVariableEntry(
			"APP_SERVER_TYPE", "environment.app.server.type");
	}

	private Map.Entry<String, String> _getDatabaseTypeEntry() {
		return _getEnvironmentVariableEntry(
			"DATABASE_TYPE", "environment.database.type");
	}

	private Map.Entry<String, String> _getDatabaseVersionEntry() {
		return _getEnvironmentVariableEntry(
			"DATABASE_VERSION", "environment.database.version");
	}

	private Map.Entry<String, String> _getEnvironmentVariableEntry(
		String key, String name) {

		if ((key == null) || key.isEmpty() || (name == null) ||
			name.isEmpty()) {

			return null;
		}

		String value = JenkinsResultsParserUtil.getProperty(
			_parentFunctionalBatchTestClassGroup.getJobProperties(), name,
			_parentFunctionalBatchTestClassGroup.getBatchName());

		if ((value == null) || value.isEmpty()) {
			return null;
		}

		return new AbstractMap.SimpleEntry<>(key, value);
	}

	private Map.Entry<String, String> _getOperatingSystemTypeEntry() {
		return _getEnvironmentVariableEntry(
			"OPERATING_SYSTEM_TYPE", "environment.operating.system.type");
	}

	private Map.Entry<String, String> _getOperatingSystemVersionEntry() {
		return _getEnvironmentVariableEntry(
			"OPERATING_SYSTEM_VERSION", "environment.operating.system.version");
	}

	private final FunctionalBatchTestClassGroup
		_parentFunctionalBatchTestClassGroup;

}