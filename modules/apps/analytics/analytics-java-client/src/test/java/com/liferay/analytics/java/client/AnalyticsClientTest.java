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

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Eduardo Garcia
 */
@Ignore
public class AnalyticsClientTest {

	@Test
	public void testAnalyticsEventCreation() {
		AnalyticsEventsMessage.Builder analyticsEventsMessageBuilder =
			AnalyticsEventsMessage.builder("WXYZ", 1234);

		Map<String, String> context = new HashMap<>();

		context.put("instanceId", "1234");
		context.put("languageId", "en_US");
		context.put("url", "http://www.liferay.com");

		analyticsEventsMessageBuilder.context(context);

		AnalyticsEventsMessage.Event.Builder eventBuilder =
			AnalyticsEventsMessage.Event.builder("AT", "view");

		eventBuilder.property("elementId", "banner1");

		analyticsEventsMessageBuilder.event(eventBuilder.build());

		Response response = _analyticsClient.sendAnalytics(
			analyticsEventsMessageBuilder.build());

		Assert.assertEquals(HttpStatus.SC_OK, response.getStatus());
	}

	private final AnalyticsClient _analyticsClient = new AnalyticsClient();

}