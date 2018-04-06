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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.analytics.data.binding.JSONObjectMapper;
import com.liferay.analytics.model.IdentityContextMessage;

import java.io.IOException;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = "model=com.liferay.analytics.model.IdentityContextMessage",
	service = JSONObjectMapper.class
)
public class IdentityContextMessageJSONObjectMapper
	implements JSONObjectMapper<IdentityContextMessage> {

	@Override
	public String map(IdentityContextMessage identityContextMessage)
		throws IOException {

		return _objectMapper.writeValueAsString(identityContextMessage);
	}

	@Override
	public IdentityContextMessage map(String jsonString) throws IOException {
		return _objectMapper.readValue(
			jsonString, IdentityContextMessage.class);
	}

	private final ObjectMapper _objectMapper = new ObjectMapper();

	{
		_objectMapper.addMixIn(
			IdentityContextMessage.class, IdentityContextMessageMixIn.class);

		_objectMapper.configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	private static final class IdentityContextMessageMixIn {

		@JsonIgnore
		private String _analyticsKey;

		@JsonProperty("browserPluginDetails")
		private String _browserPluginDetails;

		@JsonProperty("canvasFingerPrint")
		private String _canvasFingerPrint;

		@JsonProperty("cookiesEnabled")
		private boolean _cookiesEnabled;

		@JsonProperty("dataSourceIdentifier")
		private String _dataSourceIdentifier;

		@JsonProperty("dataSourceIndividualIdentifier")
		private String _dataSourceIndividualIdentifier;

		@JsonProperty("domain")
		private String _domain;

		@JsonProperty("httpAcceptHeaders")
		private String _httpAcceptHeaders;

		@JsonProperty("identity")
		private Map<String, String> _identityFields;

		@JsonProperty("language")
		private String _language;

		@JsonProperty("platform")
		private String _platform;

		@JsonProperty("protocolVersion")
		private String _protocolVersion;

		@JsonProperty("screenSizeAndColorDepth")
		private String _screenSizeAndColorDepth;

		@JsonProperty("systemFonts")
		private String _systemFonts;

		@JsonProperty("timezone")
		private String _timezone;

		@JsonProperty("touchSupport")
		private boolean _touchSupport;

		@JsonProperty("userAgent")
		private String _userAgent;

		@JsonProperty("userId")
		private String _userId;

		@JsonProperty("webGLFingerPrint")
		private String _webGLFingerPrint;

	}

}