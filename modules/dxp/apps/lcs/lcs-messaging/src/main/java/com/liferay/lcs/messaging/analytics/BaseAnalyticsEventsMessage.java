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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.liferay.lcs.messaging.Message;

import javax.validation.constraints.NotNull;

/**
 * @author Riccardo Ferrari
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes(
	{@JsonSubTypes.Type(name = "AT", value = ATAnalyticsEventsMessage.class)}
)
@JsonTypeInfo(
	include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "messageFormat",
	use = JsonTypeInfo.Id.NAME, visible = true
)
public abstract class BaseAnalyticsEventsMessage extends Message {

	public String getAnalyticsKey() {
		return _analyticsKey;
	}

	public String getApplicationId() {
		return _applicationId;
	}

	public String getChannel() {
		return _channel;
	}

	public String getMessageFormat() {
		return _messageFormat;
	}

	public void setAnalyticsKey(String analyticsKey) {
		_analyticsKey = analyticsKey;
	}

	public void setApplicationId(String applicationId) {
		_applicationId = applicationId;
	}

	public void setChannel(String channel) {
		_channel = channel;
	}

	public void setMessageFormat(String messageFormat) {
		_messageFormat = messageFormat;
	}

	@NotNull
	private String _analyticsKey;

	@NotNull
	private String _applicationId;

	@NotNull
	private String _channel;

	@NotNull
	private String _messageFormat;

}