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

package com.liferay.analytics.java.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jeyvison Nascimento
 * @author Marcellus Tavares
 */
public class AnalyticsEventsMessageBuilderTest {

	@Test
	public void testCreateEvent() {
		String expectedApplicationId = randomString();
		String expectedEventId = randomString();

		Map<String, String> expectedProperties = new HashMap<>();

		expectedProperties.put(randomString(), randomString());
		expectedProperties.put(randomString(), randomString());
		expectedProperties.put(randomString(), randomString());

		AnalyticsEventsMessage.Event actualEvent = createEvent(
			expectedApplicationId, expectedEventId, expectedProperties);

		assertEvent(
			expectedApplicationId, expectedEventId, expectedProperties,
			actualEvent);
	}

	@Test
	public void testCreateMessage() {

		// Context

		Map<String, String> expectedContext = createContext(
			randomLong(), randomString(), randomString(), randomLong());

		// Events

		List<AnalyticsEventsMessage.Event> expectedEvents = new ArrayList<>();

		String expectedApplicationId = randomString();
		String expectedEventId = randomString();

		Map<String, String> expectedProperties = new HashMap<>();

		expectedProperties.put(randomString(), randomString());

		expectedEvents.add(
			createEvent(
				expectedApplicationId, expectedEventId, expectedProperties));

		// Message

		String expectedAnalyticsKey = randomString();
		long expectedUserId = randomLong();
		String expectedProtocolVersion = randomString();

		AnalyticsEventsMessage actualAnalyticsEventsMessage =
			createAnalyticsEventsMessage(
				expectedAnalyticsKey, expectedUserId, expectedContext,
				expectedEvents, expectedProtocolVersion);

		Assert.assertEquals(
			expectedAnalyticsKey,
			actualAnalyticsEventsMessage.getAnalyticsKey());
		Assert.assertEquals(
			expectedUserId, actualAnalyticsEventsMessage.getUserId());
		Assert.assertEquals(
			expectedContext, actualAnalyticsEventsMessage.getContext());

		List<AnalyticsEventsMessage.Event> actualEvents =
			actualAnalyticsEventsMessage.getEvents();

		Assert.assertEquals(
			expectedEvents.toString(), expectedEvents.size(),
			actualEvents.size());

		int i = 0;

		for (AnalyticsEventsMessage.Event expectedEvent : expectedEvents) {
			assertEvent(
				expectedEvent.getApplicationId(), expectedEvent.getEventId(),
				expectedEvent.getProperties(), actualEvents.get(i++));
		}

		Assert.assertEquals(
			expectedProtocolVersion,
			actualAnalyticsEventsMessage.getProtocolVersion());
	}

	@Test(expected = IllegalStateException.class)
	public void testCreateMessageWithoutEvent() {
		createAnalyticsEventsMessage(
			randomString(), randomLong(), new HashMap<>(), new ArrayList<>(),
			randomString());
	}

	protected void assertEvent(
		String expectedApplicationId, String expectedEventId,
		Map<String, String> expectedProperties,
		AnalyticsEventsMessage.Event actualEvent) {

		Assert.assertEquals(
			expectedApplicationId, actualEvent.getApplicationId());
		Assert.assertEquals(expectedEventId, actualEvent.getEventId());
		Assert.assertEquals(expectedProperties, actualEvent.getProperties());
	}

	protected AnalyticsEventsMessage createAnalyticsEventsMessage(
		String analyticsKey, long userId, Map<String, String> context,
		List<AnalyticsEventsMessage.Event> events, String protocolVersion) {

		AnalyticsEventsMessage.Builder messageBuilder =
			AnalyticsEventsMessage.builder(analyticsKey, userId);

		messageBuilder.context(context);

		for (AnalyticsEventsMessage.Event event : events) {
			messageBuilder.event(event);
		}

		messageBuilder.protocolVersion(protocolVersion);

		return messageBuilder.build();
	}

	protected Map<String, String> createContext(
		long instanceId, String languageId, String url, long userId) {

		Map<String, String> context = new HashMap<>();

		context.put("instanceId", String.valueOf(instanceId));
		context.put("languageId", languageId);
		context.put("url", url);
		context.put("userId", String.valueOf(userId));

		return context;
	}

	protected AnalyticsEventsMessage.Event createEvent(
		String applicationId, String eventId, Map<String, String> properties) {

		AnalyticsEventsMessage.Event.Builder eventBuilder =
			AnalyticsEventsMessage.Event.builder(applicationId, eventId);

		for (Map.Entry<String, String> entry : properties.entrySet()) {
			eventBuilder.property(entry.getKey(), entry.getValue());
		}

		return eventBuilder.build();
	}

	protected long randomLong() {
		return RandomUtils.nextLong();
	}

	protected String randomString() {
		return RandomStringUtils.random(5);
	}

}