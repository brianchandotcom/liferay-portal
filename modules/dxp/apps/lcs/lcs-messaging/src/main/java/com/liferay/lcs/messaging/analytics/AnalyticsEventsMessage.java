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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.liferay.lcs.messaging.Message;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Riccardo Ferrari
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes(
	{
		@JsonSubTypes.Type(name = "AT", value = ATAnalyticsEventsMessage.class),
		@JsonSubTypes.Type(
			name = "SCREENS", value = ScreensAnalyticsEventsMessage.class
		)
	}
)
@JsonTypeInfo(
	include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "messageFormat",
	use = JsonTypeInfo.Id.NAME, visible = true
)
public class AnalyticsEventsMessage extends Message {

	public long getAnonymousUserId() {
		return _anonymousUserId;
	}

	public String getApplicationId() {
		return _applicationId;
	}

	public String getChannel() {
		return _channel;
	}

	public String getClientIP() {
		return _clientIP;
	}

	public Context getContext() {
		return _context;
	}

	public List<Event> getEvents() {
		return _events;
	}

	public String getMessageFormat() {
		return _messageFormat;
	}

	public String getUserAgent() {
		return _userAgent;
	}

	public void setAnonymousUserId(long anonymousUserId) {
		_anonymousUserId = anonymousUserId;
	}

	public void setApplicationId(String applicationId) {
		_applicationId = applicationId;
	}

	public void setChannel(String channel) {
		_channel = channel;
	}

	public void setClientIP(String clientIP) {
		_clientIP = clientIP;
	}

	public void setContext(Context context) {
		_context = context;
	}

	public void setEvents(List<Event> events) {
		_events = events;
	}

	public void setMessageFormat(String messageFormat) {
		_messageFormat = messageFormat;
	}

	public void setUserAgent(String userAgent) {
		_userAgent = userAgent;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Context {

		public long getInstanceId() {
			return _instanceId;
		}

		public String getLanguageId() {
			return _languageId;
		}

		public String getURL() {
			return _url;
		}

		public long getUserId() {
			return _userId;
		}

		public void setInstanceId(long instanceId) {
			_instanceId = instanceId;
		}

		public void setLanguageId(String languageId) {
			_languageId = languageId;
		}

		public void setUrl(String url) {
			_url = url;
		}

		public void setUserId(long userId) {
			_userId = userId;
		}

		private long _instanceId;
		private String _languageId;
		private String _url;
		private long _userId;

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Event {

		public String getAdditionalInfo() {
			return _additionalInfo;
		}

		public String getEvent() {
			return _event;
		}

		public Properties getProperties() {
			return _properties;
		}

		public Date getTimestamp() {
			return _timestamp;
		}

		public void setAdditionalInfo(String additionalInfo) {
			_additionalInfo = additionalInfo;
		}

		public void setEvent(String event) {
			_event = event;
		}

		public void setProperties(Properties properties) {
			_properties = properties;
		}

		public void setTimestamp(Date timestamp) {
			_timestamp = timestamp;
		}

		private String _additionalInfo;
		private String _event;
		private Properties _properties;

		@JsonFormat(
			pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
			shape = JsonFormat.Shape.STRING, timezone = "UTC"
		)
		private Date _timestamp;

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Properties {

		public String getElementId() {
			return _elementId;
		}

		public String getEntityId() {
			return _entityId;
		}

		public String getEntityType() {
			return _entityType;
		}

		public List<Referrer> getReferrers() {
			return _referrers;
		}

		public void setElementId(String elementId) {
			_elementId = elementId;
		}

		public void setEntityId(String entityId) {
			_entityId = entityId;
		}

		public void setEntityType(String entityType) {
			_entityType = entityType;
		}

		public void setReferrers(List<Referrer> referrers) {
			_referrers = referrers;
		}

		private String _elementId;
		private String _entityId;
		private String _entityType;
		private List<Referrer> _referrers = Collections.emptyList();

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Referrer {

		public List<String> getReferrerEntityIds() {
			return _referrerEntityIds;
		}

		public String getReferrerEntityType() {
			return _referrerEntityType;
		}

		public void setReferrerEntityIds(List<String> referrerEntityIds) {
			_referrerEntityIds = referrerEntityIds;
		}

		public void setReferrerEntityType(String referrerEntityType) {
			_referrerEntityType = referrerEntityType;
		}

		private List<String> _referrerEntityIds = Collections.emptyList();
		private String _referrerEntityType;

	}

	private long _anonymousUserId;
	private String _applicationId;
	private String _channel;
	private String _clientIP;
	private Context _context;
	private List<Event> _events = Collections.emptyList();
	private String _messageFormat;
	private String _userAgent;

}