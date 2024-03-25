/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.extensions.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Gregory Amerson
 * @author Lawrence Lee
 */
public class VersionUtilTest {

	@Test
	public void testGetMajorVersion() throws Exception {
		for (TestCase testCase : _testCases) {
			Assert.assertEquals(
				testCase.input, testCase.expectedMajorVersion,
				VersionUtil.getMajorVersion(testCase.input));
		}
	}

	@Test
	public void testGetMicroVersion() throws Exception {
		for (TestCase testCase : _testCases) {
			Assert.assertEquals(
				testCase.input, testCase.expectedMicroVersion,
				VersionUtil.getMicroVersion(testCase.input));
		}
	}

	@Test
	public void testGetMinorVersion() throws Exception {
		for (TestCase testCase : _testCases) {
			Assert.assertEquals(
				testCase.input, testCase.expectedMinorVersion,
				VersionUtil.getMinorVersion(testCase.input));
		}
	}

	@Test
	public void testIsLiferayVersion() throws Exception {
		for (String versionString :
				new String[] {"x", "6.2", "7.0test", "07.1.0"}) {

			Assert.assertFalse(
				versionString, VersionUtil.isLiferayVersion(versionString));
		}

		for (TestCase testCase : _testCases) {
			Assert.assertTrue(
				testCase.input, VersionUtil.isLiferayVersion(testCase.input));
		}
	}

	private static final TestCase[] _testCases = {
		new TestCase("7.0.10", 7, 0, 10), new TestCase("7.0.10.1", 7, 0, 10),
		new TestCase("7.0.10.fp21", 7, 0, 10), new TestCase("7.1.10", 7, 1, 10),
		new TestCase("7.1.10.1", 7, 1, 10),
		new TestCase("7.1.10.fp21", 7, 1, 10),
		new TestCase("7.1.10.fp1-1", 7, 1, 10),
		new TestCase("7.3.10.ep4", 7, 3, 10), new TestCase("8.0.0", 8, 0, 0),
		new TestCase("10.0.0", 10, 0, 0), new TestCase("100.0.0", 100, 0, 0),
		new TestCase("7.2", 7, 2, 0), new TestCase("7.4.1-1", 7, 4, 1),
		new TestCase("7.4.11.1-1", 7, 4, 11),
		new TestCase("2023.q1.2", 2023, 1, 2)
	};

	private static class TestCase {

		public final int expectedMajorVersion;
		public final int expectedMicroVersion;
		public final int expectedMinorVersion;
		public final String input;

		private TestCase(
			String input, int expectedMajorVersion, int expectedMinorVersion,
			int expectedMicroVersion) {

			this.input = input;
			this.expectedMajorVersion = expectedMajorVersion;
			this.expectedMinorVersion = expectedMinorVersion;
			this.expectedMicroVersion = expectedMicroVersion;
		}

	}

}