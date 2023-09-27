/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.internal.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class GradlePluginsDefaultsUtilTest {

	@Test
	public void testGetBuildProfileFileNames() {
		_testGetBuildProfileFileNames(null, false);
		_testGetBuildProfileFileNames(null, true);

		_testGetBuildProfileFileNames(
			"foo", false, ".lfrbuild-foo", ".lfrbuild-foo-private");
		_testGetBuildProfileFileNames(
			"foo", true, ".lfrbuild-foo", ".lfrbuild-foo-public");

		_testGetBuildProfileFileNames(
			"portal", false, ".lfrbuild-portal", ".lfrbuild-portal-private");
		_testGetBuildProfileFileNames(
			"portal", true, ".lfrbuild-portal", ".lfrbuild-portal-public");

		_testGetBuildProfileFileNames(
			"portal-deprecated", false, ".lfrbuild-portal",
			".lfrbuild-portal-deprecated",
			".lfrbuild-portal-deprecated-private", ".lfrbuild-portal-private");
		_testGetBuildProfileFileNames(
			"portal-deprecated", true, ".lfrbuild-portal",
			".lfrbuild-portal-deprecated", ".lfrbuild-portal-deprecated-public",
			".lfrbuild-portal-public");

		_testGetBuildProfileFileNames(
			"portal-foo", false, ".lfrbuild-portal-foo",
			".lfrbuild-portal-foo-private");
		_testGetBuildProfileFileNames(
			"portal-foo", true, ".lfrbuild-portal-foo",
			".lfrbuild-portal-foo-public");

		_testGetBuildProfileFileNames(
			"portal-pre", false, ".lfrbuild-portal-pre",
			".lfrbuild-portal-pre-private");
		_testGetBuildProfileFileNames(
			"portal-pre", true, ".lfrbuild-portal-pre",
			".lfrbuild-portal-pre-public");
	}

	private void _testGetBuildProfileFileNames(
		String buildProfile, boolean publicBranch,
		String... expectedFileNames) {

		Set<String> expectedFileNamesSet = null;

		if (expectedFileNames.length > 0) {
			expectedFileNamesSet = new HashSet<>(
				Arrays.asList(expectedFileNames));
		}

		Assert.assertEquals(
			expectedFileNamesSet,
			GradlePluginsDefaultsUtil.getBuildProfileFileNames(
				buildProfile, publicBranch));
	}

}