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

package com.liferay.portal.increment;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class BufferedIncrementConfigurationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@AdviseWith(adviceClasses = PropsUtilAdvice.class)
	@Test
	public void testInvalidSettingWithLog() {
		try (CaptureHandler captureHandler =
				_testInvalidSetting(Level.WARNING)) {

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

	@AdviseWith(adviceClasses = PropsUtilAdvice.class)
	@Test
	public void testInvalidSettingWithoutLog() {
		try (CaptureHandler captureHandler = _testInvalidSetting(Level.OFF)) {
			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}
	}

	@AdviseWith(adviceClasses = PropsUtilAdvice.class)
	@Test
	public void testValidSetting() {
		Map<String, String> props = new HashMap<>();

		props.put(PropsKeys.BUFFERED_INCREMENT_ENABLED, "false");
		props.put(PropsKeys.BUFFERED_INCREMENT_STANDBY_QUEUE_THRESHOLD, "10");
		props.put(PropsKeys.BUFFERED_INCREMENT_STANDBY_TIME_UPPER_LIMIT, "20");
		props.put(
			PropsKeys.BUFFERED_INCREMENT_THREADPOOL_KEEP_ALIVE_TIME, "30");
		props.put(PropsKeys.BUFFERED_INCREMENT_THREADPOOL_MAX_SIZE, "40");

		PropsUtilAdvice.setProps(props);

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

	@Aspect
	public static class PropsUtilAdvice {

		public static void setProps(Map<String, String> props) {
			_props = props;
		}

		@Around(
			"execution(public static String com.liferay.portal.util." +
				"PropsUtil.get(String, com.liferay.portal.kernel." +
					"configuration.Filter)) && args(key, filter)"
		)
		public Object get(String key, Filter filter) {
			return _props.get(key);
		}

		private static Map<String, String> _props = Collections.emptyMap();

	}

	private CaptureHandler _testInvalidSetting(Level level) {
		Map<String, String> props = new HashMap<>();

		props.put(PropsKeys.BUFFERED_INCREMENT_ENABLED, "false");

		if (level == Level.OFF) {
			props.put(
				PropsKeys.BUFFERED_INCREMENT_STANDBY_QUEUE_THRESHOLD, "1");
			props.put(
				PropsKeys.BUFFERED_INCREMENT_STANDBY_TIME_UPPER_LIMIT, "-1");
		}
		else {
			props.put(
				PropsKeys.BUFFERED_INCREMENT_STANDBY_QUEUE_THRESHOLD, "-1");
			props.put(
				PropsKeys.BUFFERED_INCREMENT_STANDBY_TIME_UPPER_LIMIT, "1");
		}

		props.put(
			PropsKeys.BUFFERED_INCREMENT_THREADPOOL_KEEP_ALIVE_TIME, "-3");
		props.put(PropsKeys.BUFFERED_INCREMENT_THREADPOOL_MAX_SIZE, "-4");

		PropsUtilAdvice.setProps(props);

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

}