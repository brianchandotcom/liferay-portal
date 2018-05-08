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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public final class IdentityContextMessage implements Serializable {

	public static IdentityContextMessage.Builder builder(String analyticsKey) {
		return new IdentityContextMessage.Builder(analyticsKey);
	}

	public String getAnalyticsKey() {
		return _analyticsKey;
	}

	public String getBrowserPluginDetails() {
		return _browserPluginDetails;
	}

	public String getCanvasFingerPrint() {
		return _canvasFingerPrint;
	}

	public String getDataSourceIdentifier() {
		return _dataSourceIdentifier;
	}

	public String getDataSourceIndividualIdentifier() {
		return _dataSourceIndividualIdentifier;
	}

	public String getDomain() {
		return _domain;
	}

	public String getHttpAcceptHeaders() {
		return _httpAcceptHeaders;
	}

	public Map<String, String> getIdentityFields() {
		return Collections.unmodifiableMap(_identityFields);
	}

	public String getLanguage() {
		return _language;
	}

	public String getPlatform() {
		return _platform;
	}

	public String getProtocolVersion() {
		return _protocolVersion;
	}

	public String getScreenSizeAndColorDepth() {
		return _screenSizeAndColorDepth;
	}

	public String getSystemFonts() {
		return _systemFonts;
	}

	public String getTimezone() {
		return _timezone;
	}

	public String getUserAgent() {
		return _userAgent;
	}

	public String getUserId() {
		return _userId;
	}

	public String getWebGLFingerPrint() {
		return _webGLFingerPrint;
	}

	public boolean isCookiesEnabled() {
		return _cookiesEnabled;
	}

	public boolean isTouchSupport() {
		return _touchSupport;
	}

	public static final class Builder {

		public Builder browserPluginDetails(String browserPluginDetails) {
			_identityMessage._browserPluginDetails = browserPluginDetails;

			return this;
		}

		public IdentityContextMessage build() {
			return _identityMessage;
		}

		public Builder canvasFingerPrint(String canvasFingerPrint) {
			_identityMessage._canvasFingerPrint = canvasFingerPrint;

			return this;
		}

		public Builder cookiesEnabled(boolean cookiesEnabled) {
			_identityMessage._cookiesEnabled = cookiesEnabled;

			return this;
		}

		public Builder dataSourceIdentifier(String dataSourceIdentifier) {
			_identityMessage._dataSourceIdentifier = dataSourceIdentifier;

			return this;
		}

		public Builder dataSourceIndividualIdentifier(
			String dataSourceIndividualIdentifier) {

			_identityMessage._dataSourceIndividualIdentifier =
				dataSourceIndividualIdentifier;

			return this;
		}

		public Builder domain(String domain) {
			_identityMessage._domain = domain;

			return this;
		}

		public Builder httpAcceptHeaders(String httpAcceptHeaders) {
			_identityMessage._httpAcceptHeaders = httpAcceptHeaders;

			return this;
		}

		public Builder identityFields(Map<String, String> identityFields) {
			_identityMessage._identityFields = identityFields;

			return this;
		}

		public Builder identityFieldsProperty(String key, String value) {
			_identityMessage._identityFields.put(key, value);

			return this;
		}

		public Builder language(String language) {
			_identityMessage._language = language;

			return this;
		}

		public Builder platform(String platform) {
			_identityMessage._platform = platform;

			return this;
		}

		public Builder protocolVersion(String protocolVersion) {
			_identityMessage._protocolVersion = protocolVersion;

			return this;
		}

		public Builder screenSizeAndColorDepth(String screenSizeAndColorDepth) {
			_identityMessage._screenSizeAndColorDepth = screenSizeAndColorDepth;

			return this;
		}

		public Builder systemFonts(String systemFonts) {
			_identityMessage._systemFonts = systemFonts;

			return this;
		}

		public Builder timezone(String timezone) {
			_identityMessage._timezone = timezone;

			return this;
		}

		public Builder touchSupport(boolean touchSupport) {
			_identityMessage._touchSupport = touchSupport;

			return this;
		}

		public Builder userAgent(String userAgent) {
			_identityMessage._userAgent = userAgent;

			return this;
		}

		public Builder userId(String temporaryUserID) {
			_identityMessage._userId = temporaryUserID;

			return this;
		}

		public Builder webGLFingerPrint(String webGLFingerPrint) {
			_identityMessage._webGLFingerPrint = webGLFingerPrint;

			return this;
		}

		protected Builder(String analyticsKey) {
			_identityMessage._analyticsKey = analyticsKey;
		}

		private final IdentityContextMessage _identityMessage =
			new IdentityContextMessage();

	}

	private IdentityContextMessage() {
	}

	private String _analyticsKey;
	private String _browserPluginDetails;
	private String _canvasFingerPrint;
	private boolean _cookiesEnabled;
	private String _dataSourceIdentifier;
	private String _dataSourceIndividualIdentifier;
	private String _domain;
	private String _httpAcceptHeaders;
	private Map<String, String> _identityFields = new HashMap<>();
	private String _language;
	private String _platform;
	private String _protocolVersion;
	private String _screenSizeAndColorDepth;
	private String _systemFonts;
	private String _timezone;
	private boolean _touchSupport;
	private String _userAgent;
	private String _userId;
	private String _webGLFingerPrint;

}