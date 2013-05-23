/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.nio.intraband.welder.fifo;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.util.OSDetector;

import java.io.File;
import java.io.IOException;

import java.security.Permission;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewClassLoaderJUnitTestRunner.class)
public class FIFOUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				if (!_shouldTest()) {
					assertClasses.clear();
				}
			}

		};

	@Test
	public void testCreateFIFOWithBrokenFile() throws Exception {
		if (!_shouldTest()) {
			return;
		}

		try {
			FIFOUtil.createFIFO(
				new File("") {

					@Override
					public String getAbsolutePath() {
						return null;
					}

				});

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
	}

	@Test
	public void testIsFIFOSupported() {
		if (!_shouldTest()) {
			return;
		}

		Assert.assertTrue(FIFOUtil.isFIFOSupported());
	}

	@Test
	public void testPhantomDeleteOnDetecting() {
		if (!_shouldTest()) {
			return;
		}

		final AtomicInteger checkDeleteCount = new AtomicInteger();
		final AtomicBoolean checkFlag = new AtomicBoolean();

		SecurityManager securityManager = new SecurityManager() {

			@Override
			public void checkDelete(String file) {
				if (!checkFlag.get() && file.contains("temp-fifo-")) {
					checkFlag.set(true);

					if (checkDeleteCount.getAndIncrement() == 0) {
						Assert.assertTrue(new File(file).delete());
					}

					checkFlag.set(false);
				}
			}

			@Override
			public void checkRead(String file) {
				if (!checkFlag.get() && file.contains("temp-fifo-")) {
					try {
						checkFlag.set(true);

						new File(file).createNewFile();

						checkFlag.set(false);
					}
					catch (IOException ioe) {
						Assert.fail(ioe.getMessage());
					}
				}
			}

			@Override
			public void checkPermission(Permission permission) {
			}

		};

		System.setSecurityManager(securityManager);

		try {
			Assert.assertTrue(FIFOUtil.isFIFOSupported());
		}
		finally {
			System.setSecurityManager(null);
		}

		Assert.assertEquals(2, checkDeleteCount.get());
	}

	@Test
	public void testReadOnlyTempFolderWithLog() {
		if (!_shouldTest()) {
			return;
		}

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			FIFOUtil.class.getName(), Level.WARNING);

		File tempFolder = new File("tempFolder");

		tempFolder.mkdirs();

		tempFolder.setReadOnly();

		String oldTempFolder = System.getProperty("java.io.tmpdir");

		System.setProperty("java.io.tmpdir", tempFolder.getAbsolutePath());

		try {
			Assert.assertFalse(FIFOUtil.isFIFOSupported());
		}
		finally {
			System.setProperty("java.io.tmpdir", oldTempFolder);
		}

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unable to detect FIFO support", logRecord.getMessage());

		Throwable throwable = logRecord.getThrown();

		Assert.assertEquals(Exception.class, throwable.getClass());

		String message = throwable.getMessage();

		Assert.assertTrue(
			message.startsWith(
				"Unable to create FIFO with command \"mkfifo\", external " +
					"process returned "));
	}

	@Test
	public void testReadOnlyTempFolderWithoutLog() {
		if (!_shouldTest()) {
			return;
		}

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			FIFOUtil.class.getName(), Level.OFF);

		File tempFolder = new File("tempFolder");

		tempFolder.mkdirs();

		tempFolder.setReadOnly();

		String oldTempFolder = System.getProperty("java.io.tmpdir");

		System.setProperty("java.io.tmpdir", tempFolder.getAbsolutePath());

		try {
			Assert.assertFalse(FIFOUtil.isFIFOSupported());
		}
		finally {
			System.setProperty("java.io.tmpdir", oldTempFolder);
		}

		Assert.assertTrue(logRecords.isEmpty());
	}

	private static boolean _shouldTest() {
		if (OSDetector.isWindows()) {
			_log.warn("This test only runs on nonwindows OS");

			return false;
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(FIFOUtilTest.class);

}