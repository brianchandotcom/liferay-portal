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
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.job.property.JobProperty;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

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
		BatchTestClassGroup parentBatchTestClassGroup) {

		super(parentBatchTestClassGroup);
	}

	protected EnvironmentFunctionalSegmentTestClassGroup(
		BatchTestClassGroup parentBatchTestClassGroup, JSONObject jsonObject) {

		super(parentBatchTestClassGroup, jsonObject);
	}

	private String _getAppServerType() {
		BatchTestClassGroup batchTestClassGroup = getBatchTestClassGroup();

		JobProperty jobProperty = batchTestClassGroup.getJobProperty(
			"environment.app.server.type");

		String jobPropertyValue = jobProperty.getValue();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue)) {
			return jobPropertyValue;
		}

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			batchTestClassGroup.getPortalGitWorkingDirectory();

		if (portalGitWorkingDirectory == null) {
			return null;
		}

		return JenkinsResultsParserUtil.getProperty(
			portalGitWorkingDirectory.getAppServerProperties(),
			"app.server.type");
	}

	private Map.Entry<String, String> _getAppServerTypeEntry() {
		String appServerType = _getAppServerType();

		if (JenkinsResultsParserUtil.isNullOrEmpty(appServerType)) {
			return null;
		}

		return new AbstractMap.SimpleEntry<>("APP_SERVER_TYPE", appServerType);
	}

	private String _getAppServerVersion() {
		BatchTestClassGroup batchTestClassGroup = getBatchTestClassGroup();

		JobProperty jobProperty = batchTestClassGroup.getJobProperty(
			"environment.app.server.version");

		String jobPropertyValue = jobProperty.getValue();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue)) {
			return jobPropertyValue;
		}

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			batchTestClassGroup.getPortalGitWorkingDirectory();
		String appServerType = _getAppServerType();

		if ((portalGitWorkingDirectory == null) ||
			JenkinsResultsParserUtil.isNullOrEmpty(appServerType)) {

			return null;
		}

		return JenkinsResultsParserUtil.getProperty(
			portalGitWorkingDirectory.getAppServerProperties(),
			"app.server." + appServerType + ".version");
	}

	private Map.Entry<String, String> _getAppServerVersionEntry() {
		String appServerVersion = _getAppServerVersion();

		if (JenkinsResultsParserUtil.isNullOrEmpty(appServerVersion)) {
			return null;
		}

		return new AbstractMap.SimpleEntry<>(
			"APP_SERVER_VERSION", appServerVersion);
	}

	private Map.Entry<String, String> _getBrowserTypeEntry() {
		return getEnvironmentVariableEntry(
			"BROWSER_TYPE", "environment.browser.type");
	}

	private Map.Entry<String, String> _getBrowserVersionEntry() {
		return getEnvironmentVariableEntry(
			"BROWSER_VERSION", "environment.browser.version");
	}

	private Map.Entry<String, String> _getDatabaseTypeEntry() {
		return getEnvironmentVariableEntry(
			"DATABASE_TYPE", "environment.database.type");
	}

	private Map.Entry<String, String> _getDatabaseVersionEntry() {
		return getEnvironmentVariableEntry(
			"DATABASE_VERSION", "environment.database.version");
	}

	private Map.Entry<String, String> _getFixPackURLEntry() {
		Job job = getJob();

		if (!(job instanceof PortalFixpackEnvironmentJob)) {
			return null;
		}

		String fixPackZipURL = System.getenv("TEST_BUILD_FIX_PACK_ZIP_URL");

		if ((fixPackZipURL == null) || !fixPackZipURL.matches("https?://.*")) {
			fixPackZipURL = getBuildStartProperty(
				"TEST_BUILD_FIX_PACK_ZIP_URL");
		}

		if ((fixPackZipURL == null) || !fixPackZipURL.matches("https?://.*")) {
			return null;
		}

		return new AbstractMap.SimpleEntry<>(
			"TEST_BUILD_FIX_PACK_ZIP_URL", fixPackZipURL);
	}

	private Map.Entry<String, String> _getJavaJDKArchitectureEntry() {
		return getEnvironmentVariableEntry(
			"JAVA_JDK_ARCHITECTURE", "environment.java.jdk.architecture");
	}

	private Map.Entry<String, String> _getJavaJDKTypeEntry() {
		return getEnvironmentVariableEntry(
			"JAVA_JDK_TYPE", "environment.java.jdk.type");
	}

	private Map.Entry<String, String> _getJavaJDKVersionEntry() {
		return getEnvironmentVariableEntry(
			"JAVA_JDK_VERSION", "environment.java.jdk.version");
	}

	private Map.Entry<String, String> _getOperatingSystemTypeEntry() {
		return getEnvironmentVariableEntry(
			"OPERATING_SYSTEM_TYPE", "environment.operating.system.type");
	}

	private Map.Entry<String, String> _getOperatingSystemVersionEntry() {
		return getEnvironmentVariableEntry(
			"OPERATING_SYSTEM_VERSION", "environment.operating.system.version");
	}

	private Map.Entry<String, String> _getTestrayBuildNameEntry() {
		Job job = getJob();

		if (!(job instanceof PortalFixpackEnvironmentJob)) {
			return null;
		}

		String testrayBuildName = System.getenv("TESTRAY_BUILD_NAME");

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayBuildName)) {
			testrayBuildName = getBuildStartProperty("TESTRAY_BUILD_NAME");
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayBuildName)) {
			return null;
		}

		return new AbstractMap.SimpleEntry<>(
			"TESTRAY_BUILD_NAME", testrayBuildName);
	}

}