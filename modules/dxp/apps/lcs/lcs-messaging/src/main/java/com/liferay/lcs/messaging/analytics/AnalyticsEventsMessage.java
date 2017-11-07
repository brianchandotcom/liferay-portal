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

package com.liferay.lcs.messaging.analytics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.liferay.lcs.messaging.Message;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Ivica Cardic
 * @author Riccardo Ferrari
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
	defaultImpl = AnalyticsEventsMessage.class,
	include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "messageFormat",
	use = JsonTypeInfo.Id.NAME, visible = true
)
public class AnalyticsEventsMessage extends Message {

	@NotNull
	public String getAnalyticsKey() {
		return _analyticsKey;
	}

	public String getClientIP() {
		return _clientIP;
	}

	public Map<String, String> getContext() {
		return _context;
	}

	@Size(min = 1)
	@Valid
	public List<Event> getEvents() {
		return _events;
	}

	public String getUserId() {
		return _userId;
	}

	public void setAnalyticsKey(String analyticsKey) {
		_analyticsKey = analyticsKey;
	}

	public void setClientIP(String clientIP) {
		_clientIP = clientIP;
	}

	public void setContext(Map<String, String> context) {
		_context = context;
	}

	public void setEvents(List<Event> events) {
		_events = events;
	}

	public void setUserId(String userId) {
		_userId = userId;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Event {

		@NotNull
		public String getApplicationId() {
			return _applicationId;
		}

		@NotNull
		public String getEventId() {
			return _eventId;
		}

		public Map<String, String> getProperties() {
			return Collections.unmodifiableMap(_properties);
		}

		public void setApplicationId(String applicationId) {
			_applicationId = applicationId;
		}

		public void setEventId(String eventId) {
			_eventId = eventId;
		}

		public void setProperties(Map<String, String> properties) {
			_properties = properties;
		}

		private String _applicationId;
		private String _eventId;
		private Map<String, String> _properties = Collections.emptyMap();

	}

	private String _analyticsKey;
	private String _clientIP;
	private Map<String, String> _context = Collections.emptyMap();
	private List<Event> _events = Collections.emptyList();
	private String _userId;

}