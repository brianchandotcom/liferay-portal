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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

import com.liferay.analytics.data.binding.JSONObjectMapper;
import com.liferay.analytics.model.AnalyticsEventsMessage;

import java.io.IOException;

import java.text.FieldPosition;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = "model=com.liferay.analytics.model.AnalyticsEventsMessage",
	service = JSONObjectMapper.class
)
public class AnalyticsEventsMessageJSONObjectMapper
	implements JSONObjectMapper<AnalyticsEventsMessage> {

	@Override
	public String map(AnalyticsEventsMessage analyticsEventsMessage)
		throws IOException {

		return ObjectMapperHolder._objectMapper.writeValueAsString(
			analyticsEventsMessage);
	}

	@Override
	public AnalyticsEventsMessage map(String jsonString) throws IOException {
		return ObjectMapperHolder._objectMapper.readValue(
			jsonString, AnalyticsEventsMessage.class);
	}

	private static final class AnalyticsEventsMessageMixIn {

		@JsonProperty("context")
		private Map<String, String> _context;

		@JsonProperty("dataSourceId")
		private String _dataSourceId;

		@JsonProperty("events")
		private List<?> _events;

		@JsonProperty("protocolVersion")
		private String _protocolVersion;

		@JsonProperty("userId")
		private String _userId;

	}

	private static final class EventMixIn {

		@JsonProperty("applicationId")
		private String _applicationId;

		@JsonProperty("eventDate")
		private Date _eventDate;

		@JsonProperty("eventId")
		private String _eventId;

		@JsonProperty("properties")
		private Map<String, String> _properties;

	}

	private static class ISO8601MillisDateFormat extends ISO8601DateFormat {

		@Override
		public StringBuffer format(
			Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {

			String value = ISO8601Utils.format(date, true);

			toAppendTo.append(value);

			return toAppendTo;
		}

	}

	private static class ObjectMapperHolder {

		private static ObjectMapper _getObjectMapper() {
			ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.addMixIn(
				AnalyticsEventsMessage.class,
				AnalyticsEventsMessageMixIn.class);

			objectMapper.addMixIn(
				AnalyticsEventsMessage.Event.class, EventMixIn.class);

			objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			objectMapper.configure(
				SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

			objectMapper.setDateFormat(new ISO8601MillisDateFormat());

			return objectMapper;
		}

		private static final ObjectMapper _objectMapper = _getObjectMapper();

	}

}