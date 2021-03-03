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

package com.liferay.asset.publisher.web.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class CheckAssetEntryMessageListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetTriggerEmptyCronExpression() {
		String cronExpression = "";
		int repeatInterval = 3;

		Trigger trigger = ReflectionTestUtil.invoke(
			_messageListener, "_getTrigger",
			new Class<?>[] {String.class, int.class}, cronExpression,
			repeatInterval);

		Serializable wrappedTrigger = trigger.getWrappedTrigger();

		Integer actualRepeatInterval = ReflectionTestUtil.invoke(
			wrappedTrigger, "getRepeatInterval", new Class<?>[0]);

		Assert.assertEquals(repeatInterval, actualRepeatInterval.intValue());

		Object repeatIntervalUnit = ReflectionTestUtil.invoke(
			wrappedTrigger, "getRepeatIntervalUnit", new Class<?>[0]);

		Assert.assertEquals("HOUR", repeatIntervalUnit.toString());
	}

	@Test
	public void testGetTriggerInvalidCronExpression() {
		String cronExpression = "a a a";
		int repeatInterval = 3;

		Trigger trigger = null;

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.asset.publisher.web.internal.messaging." +
					"CheckAssetEntryMessageListener",
				LoggerTestUtil.ERROR)) {

			trigger = ReflectionTestUtil.invoke(
				_messageListener, "_getTrigger",
				new Class<?>[] {String.class, int.class}, cronExpression,
				repeatInterval);
		}

		Serializable wrappedTrigger = trigger.getWrappedTrigger();

		Integer actualRepeatInterval = ReflectionTestUtil.invoke(
			wrappedTrigger, "getRepeatInterval", new Class<?>[0]);

		Assert.assertEquals(repeatInterval, actualRepeatInterval.intValue());

		Object repeatIntervalUnit = ReflectionTestUtil.invoke(
			wrappedTrigger, "getRepeatIntervalUnit", new Class<?>[0]);

		Assert.assertEquals("HOUR", repeatIntervalUnit.toString());
	}

	@Test
	public void testGetTriggerValidCronExpression() {
		String cronExpression = "0 20 10 ? * * *";
		int repeatInterval = 3;

		Trigger trigger = ReflectionTestUtil.invoke(
			_messageListener, "_getTrigger",
			new Class<?>[] {String.class, int.class}, cronExpression,
			repeatInterval);

		String actualCronExpression = ReflectionTestUtil.invoke(
			trigger.getWrappedTrigger(), "getCronExpression", new Class<?>[0]);

		Assert.assertEquals(cronExpression, actualCronExpression);
	}

	@Inject(filter = "component.name=*.CheckAssetEntryMessageListener")
	private MessageListener _messageListener;

}