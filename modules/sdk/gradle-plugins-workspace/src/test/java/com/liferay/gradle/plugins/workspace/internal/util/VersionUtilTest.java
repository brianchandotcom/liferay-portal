/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.internal.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class VersionUtilTest {

	@Test
	public void testIsDXPVersion() throws Exception {
		for (String nondxpVersionString :
				Arrays.asList("x", "7.1.0", "7.1.1")) {

			Assert.assertFalse(
				nondxpVersionString,
				VersionUtil.isDXPVersion(nondxpVersionString));
		}

		for (String dxpVersionString :
				Arrays.asList(
					"7.0.10", "7.0.10.1", "7.0.10.fp21", "7.1.10", "7.1.10.1",
					"7.1.10.fp21", "7.1.10.fp1-1", "7.1.10.fp123-456",
					"7.3.10.ep4", "7.4.13.u1", "2023.q4.0", "2023.q4.1",
					"2024.q1.0")) {

			Assert.assertTrue(
				dxpVersionString, VersionUtil.isDXPVersion(dxpVersionString));
		}
	}

	@Test
	public void testNormalizeTargetPlatformVersion() throws Exception {
		_testNormalizeTargetPlatformVersion("7.0.0", "7.0.0");
		_testNormalizeTargetPlatformVersion("7.0.0", "7.0-GA1");
		_testNormalizeTargetPlatformVersion("7.0.0", "7.0-ga1");
		_testNormalizeTargetPlatformVersion("7.0.6", "7.0-GA7");
		_testNormalizeTargetPlatformVersion("7.0.6", "7.0-ga7");
		_testNormalizeTargetPlatformVersion("7.0.10.1", "7.0.10.1");
		_testNormalizeTargetPlatformVersion("7.0.10.fp1", "7.0.10.fp1");
		_testNormalizeTargetPlatformVersion("7.4.13.u1", "7.4.13.u1");
		_testNormalizeTargetPlatformVersion("2023.q4.0", "2023.q4.0");
		_testNormalizeTargetPlatformVersion("2023.q4.1", "2023.q4.1");
		_testNormalizeTargetPlatformVersion("2024.q1.0", "2024.q1.0");
	}

	private void _testNormalizeTargetPlatformVersion(
		String expected, String input) {

		Assert.assertEquals(
			expected, VersionUtil.normalizeTargetPlatformVersion(input));
	}

}