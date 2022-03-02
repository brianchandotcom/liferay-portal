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

import com.liferay.petra.string.StringBundler;
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

		_testDownloadAndInstallJar(_SHA1_REAL);
		_testDownloadAndInstallJar(_SHA1_FAKE);
	}

	private void _testDownloadAndInstallJar(String sha1) throws Exception {
		URL url = JarUtilTest.class.getResource("dependencies/test.jar");

		Path tempFilePath = Files.createTempFile(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try {
			JarUtil.downloadAndInstallJar(url, tempFilePath, sha1);

			if (sha1.equals(_SHA1_FAKE)) {
				Assert.fail(
					"Download should fail when invalid SHA1 is provided");
			}
		}
		catch (Exception exception) {
			if (sha1.equals(_SHA1_REAL)) {
				throw exception;
			}

			String message = exception.getMessage();

			Assert.assertTrue(
				message,
				message.contains(
					StringBundler.concat(
						"because ", _SHA1_FAKE, " does not equal ",
						_SHA1_REAL)));
		}
		finally {
			Files.deleteIfExists(tempFilePath);
		}
	}

	private static final String _SHA1_FAKE = "SHA1_FAKE";

	private static final String _SHA1_REAL =
		"98e93e8db707aa2d31118c9c88a2d6b642d896fd";

}