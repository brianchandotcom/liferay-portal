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

package com.liferay.headless.admin.user.client.resource.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.serdes.v1_0.PhoneSerDes;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface PhoneResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<Phone> getOrganizationPhonesPage(Long organizationId)
		throws Exception;

	public HttpInvoker.HttpResponse getOrganizationPhonesPageHttpResponse(
			Long organizationId)
		throws Exception;

	public Phone getPhone(Long phoneId) throws Exception;

	public HttpInvoker.HttpResponse getPhoneHttpResponse(Long phoneId)
		throws Exception;

	public Page<Phone> getUserAccountPhonesPage(Long userAccountId)
		throws Exception;

	public HttpInvoker.HttpResponse getUserAccountPhonesPageHttpResponse(
			Long userAccountId)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public PhoneResource build() {
			return new PhoneResourceImpl(this);
		}

		public Builder endpoint(String host, int port, String scheme) {
			_host = host;
			_port = port;
			_scheme = scheme;

			return this;
		}

		public Builder header(String key, String value) {
			_headers.put(key, value);

			return this;
		}

		public Builder locale(Locale locale) {
			_locale = locale;

			return this;
		}

		public Builder parameter(String key, String value) {
			_parameters.put(key, value);

			return this;
		}

		private Builder() {
		}

		private Map<String, String> _headers = new LinkedHashMap<>();
		private String _host = "localhost";
		private Locale _locale;
		private String _login = "test@liferay.com";
		private String _password = "test";
		private Map<String, String> _parameters = new LinkedHashMap<>();
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class PhoneResourceImpl implements PhoneResource {

		public Page<Phone> getOrganizationPhonesPage(Long organizationId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getOrganizationPhonesPageHttpResponse(organizationId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, PhoneSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse getOrganizationPhonesPageHttpResponse(
				Long organizationId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			Map<String, String> headers = _builder._headers;

			Set<Map.Entry<String, String>> headerEntries = headers.entrySet();

			for (Map.Entry<String, String> entry : headerEntries) {
				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			Map<String, String> parameters = _builder._parameters;

			Set<Map.Entry<String, String>> parameterEntries =
				parameters.entrySet();

			for (Map.Entry<String, String> entry : parameterEntries) {
				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/organizations/{organizationId}/phones",
				organizationId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Phone getPhone(Long phoneId) throws Exception {
			HttpInvoker.HttpResponse httpResponse = getPhoneHttpResponse(
				phoneId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return PhoneSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getPhoneHttpResponse(Long phoneId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			Map<String, String> headers = _builder._headers;

			Set<Map.Entry<String, String>> headerEntries = headers.entrySet();

			for (Map.Entry<String, String> entry : headerEntries) {
				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			Map<String, String> parameters = _builder._parameters;

			Set<Map.Entry<String, String>> parameterEntries =
				parameters.entrySet();

			for (Map.Entry<String, String> entry : parameterEntries) {
				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/phones/{phoneId}",
				phoneId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Phone> getUserAccountPhonesPage(Long userAccountId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getUserAccountPhonesPageHttpResponse(userAccountId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, PhoneSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse getUserAccountPhonesPageHttpResponse(
				Long userAccountId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			Map<String, String> headers = _builder._headers;

			Set<Map.Entry<String, String>> headerEntries = headers.entrySet();

			for (Map.Entry<String, String> entry : headerEntries) {
				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			Map<String, String> parameters = _builder._parameters;

			Set<Map.Entry<String, String>> parameterEntries =
				parameters.entrySet();

			for (Map.Entry<String, String> entry : parameterEntries) {
				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/user-accounts/{userAccountId}/phones",
				userAccountId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private PhoneResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			PhoneResource.class.getName());

		private Builder _builder;

	}

}