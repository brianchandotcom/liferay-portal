/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.Properties;

import org.junit.Assume;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class JenkinsBuildPropertiesTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testBinariesCacheEnabledProperties() {
		_testGetProperty(
			"binaries.cache.enabled", "false", "forward-pullrequest");
		_testGetProperty(
			"binaries.cache.enabled", "false", "test-portal-source-format");
		_testGetProperty(
			"binaries.cache.enabled", "true", "test-portal-release");
	}

	@Test
	public void testBuildCachingEnabledProperties() {
		_testGetProperty(
			"build.caching.enabled", "false", "forward-pullrequest");
		_testGetProperty(
			"build.caching.enabled", "false", "test-portal-source-format");
		_testGetProperty(
			"build.caching.enabled", "true", "test-portal-fixpack-release");
		_testGetProperty(
			"build.caching.enabled", "true", "test-portal-hotfix-release");
		_testGetProperty(
			"build.caching.enabled", "true", "test-portal-release");
	}

	@Test
	public void testGitArchiveEnabledProperties() {
		_testGetProperty("git.archive.enabled", "false", "forward-pullrequest");
		_testGetProperty(
			"git.archive.enabled", "false", "test-portal-source-format");
		_testGetProperty("git.archive.enabled", "true", "test-portal-release");
	}

	private Properties _getBuildAwsProperties() {
		File jenkinsRepositoryDir =
			JenkinsResultsParserUtil.getJenkinsRepositoryDir();

		File buildAwsPropertiesFile = new File(
			jenkinsRepositoryDir, "commands/build-aws.properties");

		Assume.assumeTrue(
			JenkinsResultsParserUtil.getCanonicalPath(buildAwsPropertiesFile) +
				" does not exist",
			buildAwsPropertiesFile.exists());

		return JenkinsResultsParserUtil.getProperties(buildAwsPropertiesFile);
	}

	private void _testGetProperty(
		String basePropertyName, String expectedValue, String jobName) {

		testEquals(
			expectedValue,
			JenkinsResultsParserUtil.getProperty(
				_getBuildAwsProperties(), basePropertyName, jobName));
	}

}