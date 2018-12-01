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

package com.liferay.portal.internal.increment;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class BufferedIncrementConfigurationTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		_properties = new Properties();

		PropsUtil.addProperties(_properties);
	}

	@After
	public void tearDown() {
		PropsUtil.removeProperties(_properties);
	}

	@Test
	public void testInvalidSettingWithLog() {
		try (CaptureHandler captureHandler = _testInvalidSetting(
				Level.WARNING)) {

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 2, logRecords.size());

			LogRecord logRecord1 = logRecords.get(0);

			Assert.assertEquals(
				PropsKeys.BUFFERED_INCREMENT_THREADPOOL_KEEP_ALIVE_TIME +
					"[]=-3. Auto reset to 0.",
				logRecord1.getMessage());

			LogRecord logRecord2 = logRecords.get(1);

			Assert.assertEquals(
				PropsKeys.BUFFERED_INCREMENT_THREADPOOL_MAX_SIZE +
					"[]=-4. Auto reset to 1.",
				logRecord2.getMessage());
		}
	}

	@Test
	public void testInvalidSettingWithoutLog() {
		try (CaptureHandler captureHandler = _testInvalidSetting(Level.OFF)) {
			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.toString(), logRecords.isEmpty());
		}
	}

	@Test
	public void testValidSetting() {
		_properties.put(PropsKeys.BUFFERED_INCREMENT_ENABLED, "false");
		_properties.put(
			PropsKeys.BUFFERED_INCREMENT_STANDBY_QUEUE_THRESHOLD, "10");
		_properties.put(
			PropsKeys.BUFFERED_INCREMENT_STANDBY_TIME_UPPER_LIMIT, "20");
		_properties.put(
			PropsKeys.BUFFERED_INCREMENT_THREADPOOL_KEEP_ALIVE_TIME, "30");
		_properties.put(PropsKeys.BUFFERED_INCREMENT_THREADPOOL_MAX_SIZE, "40");

		BufferedIncrementConfiguration bufferedIncrementConfiguration =
			new BufferedIncrementConfiguration(StringPool.BLANK);

		Assert.assertFalse(bufferedIncrementConfiguration.isEnabled());
		Assert.assertEquals(
			10, bufferedIncrementConfiguration.getStandbyQueueThreshold());
		Assert.assertEquals(
			20, bufferedIncrementConfiguration.getStandbyTimeUpperLimit());
		Assert.assertEquals(
			30, bufferedIncrementConfiguration.getThreadpoolKeepAliveTime());
		Assert.assertEquals(
			40, bufferedIncrementConfiguration.getThreadpoolMaxSize());
		Assert.assertTrue(bufferedIncrementConfiguration.isStandbyEnabled());

		try {
			bufferedIncrementConfiguration.calculateStandbyTime(-1);
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Negative queue length -1", iae.getMessage());
		}

		int standbyQueueThreshold =
			bufferedIncrementConfiguration.getStandbyQueueThreshold();
		long standbyTimeUpperLimit =
			bufferedIncrementConfiguration.getStandbyTimeUpperLimit();

		long standbyTime = bufferedIncrementConfiguration.calculateStandbyTime(
			0);

		Assert.assertEquals(standbyTimeUpperLimit * 1000, standbyTime);

		standbyTime = bufferedIncrementConfiguration.calculateStandbyTime(
			standbyQueueThreshold / 2);

		Assert.assertEquals(standbyTimeUpperLimit * 1000 / 2, standbyTime);

		standbyTime = bufferedIncrementConfiguration.calculateStandbyTime(
			standbyQueueThreshold);

		Assert.assertEquals(0, standbyTime);

		standbyTime = bufferedIncrementConfiguration.calculateStandbyTime(
			standbyQueueThreshold + 1);

		Assert.assertEquals(0, standbyTime);

		standbyTime = bufferedIncrementConfiguration.calculateStandbyTime(
			standbyQueueThreshold * 2);

		Assert.assertEquals(0, standbyTime);
	}

	private CaptureHandler _testInvalidSetting(Level level) {
		_properties.put(PropsKeys.BUFFERED_INCREMENT_ENABLED, "false");

		if (level == Level.OFF) {
			_properties.put(
				PropsKeys.BUFFERED_INCREMENT_STANDBY_QUEUE_THRESHOLD, "1");
			_properties.put(
				PropsKeys.BUFFERED_INCREMENT_STANDBY_TIME_UPPER_LIMIT, "-1");
		}
		else {
			_properties.put(
				PropsKeys.BUFFERED_INCREMENT_STANDBY_QUEUE_THRESHOLD, "-1");
			_properties.put(
				PropsKeys.BUFFERED_INCREMENT_STANDBY_TIME_UPPER_LIMIT, "1");
		}

		_properties.put(
			PropsKeys.BUFFERED_INCREMENT_THREADPOOL_KEEP_ALIVE_TIME, "-3");
		_properties.put(PropsKeys.BUFFERED_INCREMENT_THREADPOOL_MAX_SIZE, "-4");

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			BufferedIncrementConfiguration.class.getName(), level);

		BufferedIncrementConfiguration bufferedIncrementConfiguration =
			new BufferedIncrementConfiguration(StringPool.BLANK);

		Assert.assertFalse(bufferedIncrementConfiguration.isEnabled());

		if (level == Level.OFF) {
			Assert.assertEquals(
				1, bufferedIncrementConfiguration.getStandbyQueueThreshold());
			Assert.assertEquals(
				-1, bufferedIncrementConfiguration.getStandbyTimeUpperLimit());
		}
		else {
			Assert.assertEquals(
				-1, bufferedIncrementConfiguration.getStandbyQueueThreshold());
			Assert.assertEquals(
				1, bufferedIncrementConfiguration.getStandbyTimeUpperLimit());
		}

		Assert.assertEquals(
			0, bufferedIncrementConfiguration.getThreadpoolKeepAliveTime());
		Assert.assertEquals(
			1, bufferedIncrementConfiguration.getThreadpoolMaxSize());
		Assert.assertFalse(bufferedIncrementConfiguration.isStandbyEnabled());

		try {
			bufferedIncrementConfiguration.calculateStandbyTime(0);
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals("Standby is disabled", ise.getMessage());
		}

		return captureHandler;
	}

	private Properties _properties;

}