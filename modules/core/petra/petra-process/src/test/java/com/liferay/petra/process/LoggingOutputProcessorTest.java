/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.nio.charset.Charset;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class LoggingOutputProcessorTest extends BaseOutputProcessorTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testLoggingFail() {
		testFailToRead(new LoggingOutputProcessor(null));
	}

	@Test
	public void testLoggingSuccess() throws Exception {
		List<Map.Entry<Boolean, String>> logRecords = new ArrayList<>();

		LoggingOutputProcessor loggingOutputProcessor =
			new LoggingOutputProcessor(
				(stdErr, line) -> logRecords.add(
					new AbstractMap.SimpleImmutableEntry<>(stdErr, line)));

		String stdErrString = "This is standard error message.";

		byte[] stdErrBytes = stdErrString.getBytes(Charset.defaultCharset());

		Assert.assertNull(
			loggingOutputProcessor.processStdErr(
				new UnsyncByteArrayInputStream(stdErrBytes)));

		Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

		Map.Entry<Boolean, String> logRecord = logRecords.get(0);

		Assert.assertTrue(logRecord.toString(), logRecord.getKey());
		Assert.assertEquals(stdErrString, logRecord.getValue());

		logRecords.clear();

		String stdOutString = "This is standard out message.";

		byte[] stdOutBytes = stdOutString.getBytes(Charset.defaultCharset());

		Assert.assertNull(
			loggingOutputProcessor.processStdOut(
				new UnsyncByteArrayInputStream(stdOutBytes)));

		Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertFalse(logRecord.toString(), logRecord.getKey());
		Assert.assertEquals(stdOutString, logRecord.getValue());
	}

}