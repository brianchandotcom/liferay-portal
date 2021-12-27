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

package com.liferay.portal.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Dante Wang
 */
public class JarUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDownloadAndInstallJar() throws Exception {
		DigesterUtil digesterUtil = new DigesterUtil();

		digesterUtil.setDigester(new DigesterImpl());

		_testDownloadAndInstallJar(_REAL_SHA1);
		_testDownloadAndInstallJar(_FAKE_SHA1);
	}

	private void _testDownloadAndInstallJar(String sha1) throws Exception {
		URL url = JarUtilTest.class.getResource("dependencies/test.jar");

		Path tempFilePath = Files.createTempFile(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try {
			JarUtil.downloadAndInstallJar(url, tempFilePath, sha1);

			if (sha1.equals(_FAKE_SHA1)) {
				Assert.fail(
					"Download should fail when invalid sha1 is provided");
			}
		}
		catch (Exception exception) {
			if (sha1.equals(_REAL_SHA1)) {
				throw exception;
			}

			String message = exception.getMessage();

			Assert.assertTrue(
				message, message.contains("due to integrity check failure"));
		}
		finally {
			Files.deleteIfExists(tempFilePath);
		}
	}

	private static final String _FAKE_SHA1 = "FAKE_SHA1";

	private static final String _REAL_SHA1 =
		"98e93e8db707aa2d31118c9c88a2d6b642d896fd";

}