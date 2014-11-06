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

package com.liferay.portal.kernel.process;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class LoggingOutputProcessorTest extends BaseOutputProcessorTestCase {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testLoggingFail() {
		testFailToRead(new LoggingOutputProcessor());
	}

	@Test
	public void testLoggingSuccess() throws Exception {
		LoggingOutputProcessor loggingOutputProcessor =
			new LoggingOutputProcessor();

		String stdErrString = "This is standard error message.";

		byte[] stdErrBytes = stdErrString.getBytes(
			StringPool.DEFAULT_CHARSET_NAME);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			LoggingOutputProcessor.class.getName(), Level.OFF);

		try {
			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertNull(
				loggingOutputProcessor.processStdErr(
					new UnsyncByteArrayInputStream(stdErrBytes)));
			Assert.assertTrue(logRecords.isEmpty());

			logRecords = captureHandler.resetLogLevel(Level.SEVERE);

			Assert.assertNull(
				invokeProcessStdErr(
					loggingOutputProcessor,
					new UnsyncByteArrayInputStream(stdErrBytes)));
			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(stdErrString, logRecord.getMessage());

			String stdOutString = "This is standard out message.";

			byte[] stdOutBytes = stdOutString.getBytes(
				StringPool.DEFAULT_CHARSET_NAME);

			Assert.assertNull(
				loggingOutputProcessor.processStdOut(
					new UnsyncByteArrayInputStream(stdOutBytes)));

			logRecords = captureHandler.resetLogLevel(Level.SEVERE);

			Assert.assertTrue(logRecords.isEmpty());

			logRecords = captureHandler.resetLogLevel(Level.INFO);

			Assert.assertNull(
				invokeProcessStdOut(
					loggingOutputProcessor,
					new UnsyncByteArrayInputStream(stdOutBytes)));
			Assert.assertEquals(1, logRecords.size());

			logRecord = logRecords.get(0);

			Assert.assertEquals(stdOutString, logRecord.getMessage());
		}
		finally {
			captureHandler.close();
		}
	}

}