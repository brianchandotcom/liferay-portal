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

package com.liferay.analytics.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Garcia
 * @author Marcellus Tavares
 */
public final class AnalyticsEventsMessage implements Serializable {

	public static AnalyticsEventsMessage.Builder builder(
		AnalyticsEventsMessage analyticsEventsMessage) {

		return new AnalyticsEventsMessage.Builder(analyticsEventsMessage);
	}

	public static AnalyticsEventsMessage.Builder builder(String analyticsKey) {
		return new AnalyticsEventsMessage.Builder(analyticsKey);
	}

	public static AnalyticsEventsMessage.Builder builder(
		String analyticsKey, String userId) {

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

	public String getUserId() {
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

		public Builder userId(String userId) {
			_analyticsEventsMessage._userId = userId;

			return this;
		}

		protected Builder(AnalyticsEventsMessage analyticsEventsMessage) {
			_analyticsEventsMessage._analyticsKey =
				analyticsEventsMessage.getAnalyticsKey();

			_analyticsEventsMessage._context =
				analyticsEventsMessage.getContext();

			_analyticsEventsMessage._events =
				analyticsEventsMessage.getEvents();

			_analyticsEventsMessage._protocolVersion =
				analyticsEventsMessage.getProtocolVersion();

			_analyticsEventsMessage._userId =
				analyticsEventsMessage.getUserId();
		}

		protected Builder(String analyticsKey) {
			_analyticsEventsMessage._analyticsKey = analyticsKey;
		}

		protected Builder(String analyticsKey, String userId) {
			_analyticsEventsMessage._analyticsKey = analyticsKey;
			_analyticsEventsMessage._userId = userId;
		}

		private final AnalyticsEventsMessage _analyticsEventsMessage =
			new AnalyticsEventsMessage();

	}

	public static final class Event implements Serializable {

		public static Event.Builder builder(
			String applicationId, String eventId) {

			return new Event.Builder(applicationId, eventId);
		}

		public String getApplicationId() {
			return _applicationId;
		}

		public Date getEventDate() {
			return new Date(_eventDate.getTime());
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
		private Date _eventDate = new Date();
		private String _eventId;
		private Map<String, String> _properties = new HashMap<>();

	}

	private AnalyticsEventsMessage() {
	}

	private String _analyticsKey;
	private Map<String, String> _context = new HashMap<>();
	private List<Event> _events = new ArrayList<>();
	private String _protocolVersion;
	private String _userId;

}