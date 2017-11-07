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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Garcia
 * @author Marcellus Tavares
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class AnalyticsEventsMessage implements Serializable {

	public static AnalyticsEventsMessage.Builder builder(
		String analyticsKey, long userId) {

		return new AnalyticsEventsMessage.Builder(analyticsKey, userId);
	}

	public String getAnalyticsKey() {
		return _analyticsKey;
	}

	public Map<String, String> getContext() {
		return Collections.unmodifiableMap(_context);
	}

	public List<Event> getEvents() {
		return _events;
	}

	public String getProtocolVersion() {
		return _protocolVersion;
	}

	public long getUserId() {
		return _userId;
	}

	public static final class Builder {

		public AnalyticsEventsMessage build() {
			if (_analyticsEventsMessage._events.size() == 0) {
				throw new IllegalStateException(
					"The message should contain at least one event");
			}

			return _analyticsEventsMessage;
		}

		public Builder context(Map<String, String> context) {
			_analyticsEventsMessage._context = context;

			return this;
		}

		public Builder contextProperty(String key, String value) {
			_analyticsEventsMessage._context.put(key, value);

			return this;
		}

		public Builder event(Event event) {
			_analyticsEventsMessage._events.add(event);

			return this;
		}

		public Builder protocolVersion(String protocolVersion) {
			_analyticsEventsMessage._protocolVersion = protocolVersion;

			return this;
		}

		protected Builder(String analyticsKey, long userId) {
			_analyticsEventsMessage._analyticsKey = analyticsKey;
			_analyticsEventsMessage._userId = userId;
		}

		private final AnalyticsEventsMessage _analyticsEventsMessage =
			new AnalyticsEventsMessage();

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class Event {

		public static Event.Builder builder(
			String applicationId, String eventId) {

			return new Event.Builder(applicationId, eventId);
		}

		public String getApplicationId() {
			return _applicationId;
		}

		public String getEventId() {
			return _eventId;
		}

		public Map<String, String> getProperties() {
			return Collections.unmodifiableMap(_properties);
		}

		public static final class Builder {

			public AnalyticsEventsMessage.Event build() {
				return _event;
			}

			public Event.Builder properties(Map<String, String> properties) {
				_event._properties = properties;

				return this;
			}

			public Event.Builder property(String key, String value) {
				_event._properties.put(key, value);

				return this;
			}

			protected Builder(String applicationId, String eventId) {
				_event._applicationId = applicationId;
				_event._eventId = eventId;
			}

			private final AnalyticsEventsMessage.Event _event =
				new AnalyticsEventsMessage.Event();

		}

		private Event() {
		}

		private String _applicationId;
		private String _eventId;
		private Map<String, String> _properties = new HashMap<>();

	}

	private AnalyticsEventsMessage() {
	}

	private String _analyticsKey;
	private Map<String, String> _context = new HashMap<>();
	private final List<Event> _events = new ArrayList<>();
	private String _protocolVersion;
	private long _userId;

}