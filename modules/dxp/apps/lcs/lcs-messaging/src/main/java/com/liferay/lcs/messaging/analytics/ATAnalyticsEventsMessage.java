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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class ATAnalyticsEventsMessage extends BaseAnalyticsEventsMessage {

	public long getAnonymousUserId() {
		return _anonymousUserId;
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

	public String getUserAgent() {
		return _userAgent;
	}

	public void setAnonymousUserId(long anonymousUserId) {
		_anonymousUserId = anonymousUserId;
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

		public void setURL(String url) {
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

		public Map<String, String> getProperties() {
			return Collections.unmodifiableMap(_properties);
		}

		public List<Referrer> getReferrers() {
			return Collections.unmodifiableList(_referrers);
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

		public void setProperties(Map<String, String> properties) {
			_properties = properties;
		}

		public void setReferrers(List<Referrer> referrers) {
			_referrers = referrers;
		}

		public void setTimestamp(Date timestamp) {
			_timestamp = timestamp;
		}

		private String _additionalInfo;
		private String _event;
		private Map<String, String> _properties = Collections.emptyMap();
		private List<Referrer> _referrers = Collections.emptyList();

		@JsonFormat(
			pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
			shape = JsonFormat.Shape.STRING, timezone = "UTC"
		)
		private Date _timestamp;

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
	private String _clientIP;
	private Context _context;
	private List<Event> _events = Collections.emptyList();
	private String _userAgent;

}