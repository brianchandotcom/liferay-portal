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

package com.liferay.petra.io;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.SwappableSecurityManager;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class AutoDeleteFileInputStreamTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() throws IOException {
		Files.deleteIfExists(_tempFile.toPath());

		Assert.assertTrue(_tempFile.createNewFile());
	}

	@After
	public void tearDown() throws IOException {
		Files.deleteIfExists(_tempFile.toPath());
	}

	@Test
	public void testCloseWithFileChannel() throws IOException {
		try (AutoDeleteFileInputStream autoRemoveFileInputStream =
				new AutoDeleteFileInputStream(_tempFile)) {

			Assert.assertNotNull(autoRemoveFileInputStream.getChannel());

			Assert.assertTrue(_tempFile.exists());
		}

		Assert.assertFalse(_tempFile.exists());
	}

	@Test
	public void testFileNotExistOnClose() throws IOException {
		try (AutoDeleteFileInputStream autoRemoveFileInputStream =
				new AutoDeleteFileInputStream(_tempFile)) {

			ReflectionTestUtil.setFieldValue(
				autoRemoveFileInputStream, "_file", new File("NotExist"));
		}

		Assert.assertTrue(_tempFile.exists());
	}

	@Test
	public void testNormalClose() throws IOException {
		try (AutoDeleteFileInputStream autoRemoveFileInputStream =
				new AutoDeleteFileInputStream(_tempFile)) {

			Assert.assertTrue(_tempFile.exists());
		}

		Assert.assertFalse(_tempFile.exists());
	}

	@Test
	public void testUnableToDeleteOnClose() throws IOException {
		try (SwappableSecurityManager autoCloseSwappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkDelete(String file) {
						if (file.equals(_tempFile.getPath())) {
							throw new SecurityException("Unable to delete");
						}
					}

				}) {

			autoCloseSwappableSecurityManager.install();

			try (AutoDeleteFileInputStream autoRemoveFileInputStream =
					new AutoDeleteFileInputStream(_tempFile)) {

				Assert.assertTrue(_tempFile.exists());
			}

			Assert.fail();
		}
		catch (SecurityException se) {
			Assert.assertEquals("Unable to delete", se.getMessage());
		}

		Assert.assertTrue(_tempFile.exists());
	}

	private final File _tempFile = new File("tempFile");

}