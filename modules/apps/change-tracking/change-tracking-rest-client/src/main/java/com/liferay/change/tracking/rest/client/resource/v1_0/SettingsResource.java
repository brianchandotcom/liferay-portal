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

package com.liferay.change.tracking.rest.client.resource.v1_0;

import com.liferay.change.tracking.rest.client.dto.v1_0.Settings;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.serdes.v1_0.SettingsSerDes;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public interface SettingsResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<Settings> getSettingsPage(Long companyId, Long userId)
		throws Exception;

	public HttpInvoker.HttpResponse getSettingsPageHttpResponse(
			Long companyId, Long userId)
		throws Exception;

	public Settings putSettings(
			Long companyId, Long userId,
			com.liferay.change.tracking.rest.client.dto.v1_0.SettingsUpdate
				settingsUpdate)
		throws Exception;

	public HttpInvoker.HttpResponse putSettingsHttpResponse(
			Long companyId, Long userId,
			com.liferay.change.tracking.rest.client.dto.v1_0.SettingsUpdate
				settingsUpdate)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public SettingsResource build() {
			return new SettingsResourceImpl(this);
		}

		public Builder endpoint(String host, int port, String scheme) {
			_host = host;
			_port = port;
			_scheme = scheme;

			return this;
		}

		public Builder locale(Locale locale) {
			_locale = locale;

			return this;
		}

		private Builder() {
		}

		private String _host = "localhost";
		private Locale _locale;
		private String _login = "test@liferay.com";
		private String _password = "test";
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class SettingsResourceImpl implements SettingsResource {

		public Page<Settings> getSettingsPage(Long companyId, Long userId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = getSettingsPageHttpResponse(
				companyId, userId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, SettingsSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse getSettingsPageHttpResponse(
				Long companyId, Long userId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (companyId != null) {
				httpInvoker.parameter("companyId", String.valueOf(companyId));
			}

			if (userId != null) {
				httpInvoker.parameter("userId", String.valueOf(userId));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port + "/o/change-tracking/v1.0/settings");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Settings putSettings(
				Long companyId, Long userId,
				com.liferay.change.tracking.rest.client.dto.v1_0.SettingsUpdate
					settingsUpdate)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = putSettingsHttpResponse(
				companyId, userId, settingsUpdate);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return SettingsSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse putSettingsHttpResponse(
				Long companyId, Long userId,
				com.liferay.change.tracking.rest.client.dto.v1_0.SettingsUpdate
					settingsUpdate)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(settingsUpdate.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

			if (companyId != null) {
				httpInvoker.parameter("companyId", String.valueOf(companyId));
			}

			if (userId != null) {
				httpInvoker.parameter("userId", String.valueOf(userId));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port + "/o/change-tracking/v1.0/settings");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private SettingsResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			SettingsResource.class.getName());

		private Builder _builder;

	}

}