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

package com.liferay.portal.kernel.nio.intraband.welder.fifo;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.kernel.test.NewEnvMethodRule;
import com.liferay.portal.kernel.test.SwappableSecurityManager;
import com.liferay.portal.kernel.util.OSDetector;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
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

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testConstructor() {
		new FIFOUtil();
	}

	@NewEnv(type = NewEnv.Type.NONE)
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

		try (SwappableSecurityManager swappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkDelete(String fileName) {
						if (!checkFlag.get() &&
							fileName.contains("temp-fifo-")) {

							checkFlag.set(true);

							if (checkDeleteCount.getAndIncrement() == 0) {
								File file = new File(fileName);

								Assert.assertTrue(file.delete());
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

				}) {

			swappableSecurityManager.install();

			Assert.assertTrue(FIFOUtil.isFIFOSupported());
		}

		Assert.assertEquals(2, checkDeleteCount.get());
	}

	@Test
	public void testReadOnlyTempFolderWithLog() {
		if (!_shouldTest()) {
			return;
		}

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			FIFOUtil.class.getName(), Level.WARNING);

		try {
			List<LogRecord> logRecords = captureHandler.getLogRecords();

			File newTmpDir = new File("newTmpDir");

			newTmpDir.delete();

			String oldTmpDirName = System.getProperty("java.io.tmpdir");

			System.setProperty("java.io.tmpdir", newTmpDir.getAbsolutePath());

			try {
				Assert.assertFalse(FIFOUtil.isFIFOSupported());
			}
			finally {
				System.setProperty("java.io.tmpdir", oldTmpDirName);
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
					"Unable to create FIFO with command \"mkfifo\", " +
						"external process returned "));
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testReadOnlyTempFolderWithoutLog() {
		if (!_shouldTest()) {
			return;
		}

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			FIFOUtil.class.getName(), Level.OFF);

		try {
			List<LogRecord> logRecords = captureHandler.getLogRecords();

			File newTmpDir = new File("newTmpDir");

			newTmpDir.delete();

			String oldTmpDirName = System.getProperty("java.io.tmpdir");

			System.setProperty("java.io.tmpdir", newTmpDir.getAbsolutePath());

			try {
				Assert.assertFalse(FIFOUtil.isFIFOSupported());
			}
			finally {
				System.setProperty("java.io.tmpdir", oldTmpDirName);
			}

			Assert.assertTrue(logRecords.isEmpty());
		}
		finally {
			captureHandler.close();
		}
	}

	@Rule
	public final NewEnvMethodRule newEnvMethodRule = new NewEnvMethodRule();

	private static boolean _shouldTest() {
		if (OSDetector.isWindows()) {
			if (_log.isWarnEnabled()) {
				_log.warn("This test only runs on nonwindows OS");
			}

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(FIFOUtilTest.class);

}