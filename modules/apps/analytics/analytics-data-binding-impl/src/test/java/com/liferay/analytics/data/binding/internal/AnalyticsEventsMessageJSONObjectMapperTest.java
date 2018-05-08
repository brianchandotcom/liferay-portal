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

package com.liferay.analytics.data.binding.internal;

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import com.liferay.analytics.data.binding.JSONObjectMapper;
import com.liferay.analytics.model.AnalyticsEventsMessage;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.text.ParsePosition;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class AnalyticsEventsMessageJSONObjectMapperTest {

	@Test
	public void testJSONDeserialization() throws Exception {
		String jsonString = read("analytics_events_message.json");

		AnalyticsEventsMessage analyticsEventsMessage = _jsonObjectMapper.map(
			jsonString);

		Assert.assertEquals(
			"AnalyticsKey", analyticsEventsMessage.getAnalyticsKey());

		Map<String, String> context = analyticsEventsMessage.getContext();

		Assert.assertEquals("v1", context.get("k1"));
		Assert.assertEquals("v2", context.get("k2"));

		List<AnalyticsEventsMessage.Event> events =
			analyticsEventsMessage.getEvents();

		Assert.assertEquals(events.toString(), 1, events.size());

		AnalyticsEventsMessage.Event event = events.get(0);

		Assert.assertEquals("ApplicationId", event.getApplicationId());
		Assert.assertEquals(
			ISO8601Utils.parse(
				"2017-11-20T19:52:56.723Z", new ParsePosition(0)),
			event.getEventDate());
		Assert.assertEquals("View", event.getEventId());

		Map<String, String> properties = event.getProperties();

		Assert.assertEquals("v1", properties.get("k1"));
		Assert.assertEquals("v2", properties.get("k2"));

		Assert.assertEquals("1.0", analyticsEventsMessage.getProtocolVersion());
		Assert.assertEquals("UserId", analyticsEventsMessage.getUserId());
	}

	@Test
	public void testJSONSerialization() throws Exception {
		AnalyticsEventsMessage.Builder messageBuilder =
			AnalyticsEventsMessage.builder("AnalyticsKey", "UserId");

		messageBuilder.contextProperty("k1", "v1");
		messageBuilder.contextProperty("k2", "v2");

		AnalyticsEventsMessage.Event.Builder eventBuilder =
			AnalyticsEventsMessage.Event.builder("ApplicationId", "View");

		eventBuilder.property("k1", "v1");
		eventBuilder.property("k2", "v2");

		AnalyticsEventsMessage.Event event = eventBuilder.build();

		messageBuilder.event(event);

		messageBuilder.protocolVersion("1.0");

		String expectedJSON = read("analytics_events_message.json");

		expectedJSON = expectedJSON.replace(
			"2017-11-20T19:52:56.723Z",
			ISO8601Utils.format(event.getEventDate(), true));

		String actualJSON = _jsonObjectMapper.map(messageBuilder.build());

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	protected String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes, "UTF-8");
	}

	private final JSONObjectMapper<AnalyticsEventsMessage> _jsonObjectMapper =
		new AnalyticsEventsMessageJSONObjectMapper();

}