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
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.PortalFixpackEnvironmentJob;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class EnvironmentFunctionalSegmentTestClassGroup
	extends FunctionalSegmentTestClassGroup {

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.getTestCasePropertiesContent());
		sb.append("\n");

		List<Map.Entry<String, String>> entries = new ArrayList<>();

		entries.add(_getAppServerTypeEntry());
		entries.add(_getAppServerVersionEntry());
		entries.add(_getBrowserTypeEntry());
		entries.add(_getBrowserVersionEntry());
		entries.add(_getDatabaseTypeEntry());
		entries.add(_getDatabaseVersionEntry());
		entries.add(_getFixPackURLEntry());
		entries.add(_getJavaJDKArchitectureEntry());
		entries.add(_getJavaJDKTypeEntry());
		entries.add(_getJavaJDKVersionEntry());
		entries.add(_getOperatingSystemTypeEntry());
		entries.add(_getOperatingSystemVersionEntry());
		entries.add(_getTestrayBuildNameEntry());

		entries.removeAll(Collections.singleton(null));

		for (Map.Entry<String, String> entry : entries) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			sb.append("\n");
		}

		return sb.toString();
	}

	protected EnvironmentFunctionalSegmentTestClassGroup(
		EnvironmentFunctionalBatchTestClassGroup
			parentEnvironmentFunctionalBatchTestClassGroup) {

		super(parentEnvironmentFunctionalBatchTestClassGroup);

		_parentEnvironmentFunctionalBatchTestClassGroup =
			parentEnvironmentFunctionalBatchTestClassGroup;
	}

	private Map.Entry<String, String> _getAppServerTypeEntry() {
		return _getEnvironmentVariableEntry(
			"APP_SERVER_TYPE", "environment.app.server.type");
	}

	private Map.Entry<String, String> _getAppServerVersionEntry() {
		return _getEnvironmentVariableEntry(
			"APP_SERVER_VERSION", "environment.app.server.version");
	}

	private Map.Entry<String, String> _getBrowserTypeEntry() {
		return _getEnvironmentVariableEntry(
			"BROWSER_TYPE", "environment.browser.type");
	}

	private Map.Entry<String, String> _getBrowserVersionEntry() {
		return _getEnvironmentVariableEntry(
			"BROWSER_VERSION", "environment.browser.version");
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
			_parentEnvironmentFunctionalBatchTestClassGroup.getJobProperties(),
			name,
			_parentEnvironmentFunctionalBatchTestClassGroup.getBatchName());

		if ((value == null) || value.isEmpty()) {
			return null;
		}

		return new AbstractMap.SimpleEntry<>(key, value);
	}

	private Map.Entry<String, String> _getFixPackURLEntry() {
		Job job = getJob();

		if (!(job instanceof PortalFixpackEnvironmentJob)) {
			return null;
		}

		String fixPackZipURL = System.getenv("TEST_BUILD_FIX_PACK_ZIP_URL");

		if ((fixPackZipURL == null) || !fixPackZipURL.matches("https?://.*")) {
			return null;
		}

		return new AbstractMap.SimpleEntry<>(
			"TEST_BUILD_FIX_PACK_ZIP_URL", fixPackZipURL);
	}

	private Map.Entry<String, String> _getJavaJDKArchitectureEntry() {
		return _getEnvironmentVariableEntry(
			"JAVA_JDK_ARCHITECTURE", "environment.java.jdk.architecture");
	}

	private Map.Entry<String, String> _getJavaJDKTypeEntry() {
		return _getEnvironmentVariableEntry(
			"JAVA_JDK_TYPE", "environment.java.jdk.type");
	}

	private Map.Entry<String, String> _getJavaJDKVersionEntry() {
		return _getEnvironmentVariableEntry(
			"JAVA_JDK_VERSION", "environment.java.jdk.version");
	}

	private Map.Entry<String, String> _getOperatingSystemTypeEntry() {
		return _getEnvironmentVariableEntry(
			"OPERATING_SYSTEM_TYPE", "environment.operating.system.type");
	}

	private Map.Entry<String, String> _getOperatingSystemVersionEntry() {
		return _getEnvironmentVariableEntry(
			"OPERATING_SYSTEM_VERSION", "environment.operating.system.version");
	}

	private Map.Entry<String, String> _getTestrayBuildNameEntry() {
		Job job = getJob();

		if (!(job instanceof PortalFixpackEnvironmentJob)) {
			return null;
		}

		String testrayBuildName = System.getenv("TESTRAY_BUILD_NAME");

		if ((testrayBuildName == null) || testrayBuildName.isEmpty()) {
			return null;
		}

		return new AbstractMap.SimpleEntry<>(
			"TESTRAY_BUILD_NAME", testrayBuildName);
	}

	private final EnvironmentFunctionalBatchTestClassGroup
		_parentEnvironmentFunctionalBatchTestClassGroup;

}