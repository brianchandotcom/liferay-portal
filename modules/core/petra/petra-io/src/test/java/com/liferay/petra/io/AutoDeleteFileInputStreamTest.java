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

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class AutoDeleteFileInputStreamTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testAutoRemoveFileInputStream() throws Exception {

		// Normal close

		File tempFile = new File("tempFile");

		Assert.assertTrue(tempFile.createNewFile());

		AutoDeleteFileInputStream autoRemoveFileInputStream =
			new AutoDeleteFileInputStream(tempFile);

		final AtomicInteger checkDeleteCount = new AtomicInteger();

		SwappableSecurityManager swappableSecurityManager =
			new SwappableSecurityManager() {

				@Override
				public void checkDelete(String file) {
					if (file.contains("tempFile")) {
						checkDeleteCount.getAndIncrement();
					}
				}

			};

		try (SwappableSecurityManager autoCloseSwappableSecurityManager =
				swappableSecurityManager) {

			autoCloseSwappableSecurityManager.install();

			autoRemoveFileInputStream.close();
		}

		Assert.assertFalse(tempFile.exists());
		Assert.assertEquals(1, checkDeleteCount.get());

		// File already not exist on close

		checkDeleteCount.set(0);

		Assert.assertTrue(tempFile.createNewFile());

		autoRemoveFileInputStream = new AutoDeleteFileInputStream(tempFile);

		Assert.assertTrue(tempFile.delete());

		try (SwappableSecurityManager autoCloseSwappableSecurityManager =
				swappableSecurityManager) {

			autoCloseSwappableSecurityManager.install();

			autoRemoveFileInputStream.close();
		}

		Assert.assertFalse(tempFile.exists());
		Assert.assertEquals(0, checkDeleteCount.get());

		Set<String> files = ReflectionTestUtil.getFieldValue(
			Class.forName("java.io.DeleteOnExitHook"), "files");

		Assert.assertFalse(files.toString(), files.contains(tempFile));

		// Unable to delete on close

		checkDeleteCount.set(0);

		Assert.assertTrue(tempFile.createNewFile());

		autoRemoveFileInputStream = new AutoDeleteFileInputStream(tempFile);

		String path = ReflectionTestUtil.getFieldValue(tempFile, "path");

		try (SwappableSecurityManager autoCloseSwappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkDelete(String file) {
						if (file.contains("tempFile")) {
							ReflectionTestUtil.setFieldValue(
								tempFile, "path", "\u0000");

							ReflectionTestUtil.setFieldValue(
								tempFile, "status", null);

							checkDeleteCount.getAndIncrement();
						}
						else if (file.equals("\u0000")) {
							ReflectionTestUtil.setFieldValue(
								tempFile, "path", path);

							ReflectionTestUtil.setFieldValue(
								tempFile, "status", null);
						}
					}

				}) {

			autoCloseSwappableSecurityManager.install();

			autoRemoveFileInputStream.close();
		}

		Assert.assertTrue(tempFile.delete());
		Assert.assertEquals(1, checkDeleteCount.get());
		Assert.assertTrue(files.toString(), files.contains(tempFile.getPath()));

		// Close with FileChannel

		Assert.assertTrue(tempFile.createNewFile());

		autoRemoveFileInputStream = new AutoDeleteFileInputStream(tempFile);

		Assert.assertNotNull(autoRemoveFileInputStream.getChannel());

		try (SwappableSecurityManager autoCloseSwappableSecurityManager =
				swappableSecurityManager) {

			autoCloseSwappableSecurityManager.install();

			autoRemoveFileInputStream.close();
		}

		Assert.assertFalse(tempFile.exists());
		Assert.assertEquals(2, checkDeleteCount.get());
		Assert.assertFalse(files.toString(), files.contains(tempFile));
	}

}