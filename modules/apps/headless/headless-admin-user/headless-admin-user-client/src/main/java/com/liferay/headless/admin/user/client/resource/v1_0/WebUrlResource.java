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

import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.serdes.v1_0.WebUrlSerDes;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface WebUrlResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<WebUrl> getOrganizationWebUrlsPage(Long organizationId)
		throws Exception;

	public HttpInvoker.HttpResponse getOrganizationWebUrlsPageHttpResponse(
			Long organizationId)
		throws Exception;

	public Page<WebUrl> getUserAccountWebUrlsPage(Long userAccountId)
		throws Exception;

	public HttpInvoker.HttpResponse getUserAccountWebUrlsPageHttpResponse(
			Long userAccountId)
		throws Exception;

	public WebUrl getWebUrl(Long webUrlId) throws Exception;

	public HttpInvoker.HttpResponse getWebUrlHttpResponse(Long webUrlId)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public WebUrlResource build() {
			return new WebUrlResourceImpl(this);
		}

		public Builder cookieAuthentication(String cookie) {
			_cookie = cookie;

			return this;
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

		private String _cookie;
		private String _host = "localhost";
		private Locale _locale;
		private String _login = "test@liferay.com";
		private String _password = "test";
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class WebUrlResourceImpl implements WebUrlResource {

		public Page<WebUrl> getOrganizationWebUrlsPage(Long organizationId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getOrganizationWebUrlsPageHttpResponse(organizationId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, WebUrlSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse getOrganizationWebUrlsPageHttpResponse(
				Long organizationId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/organizations/{organizationId}/web-urls",
				organizationId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<WebUrl> getUserAccountWebUrlsPage(Long userAccountId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getUserAccountWebUrlsPageHttpResponse(userAccountId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, WebUrlSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse getUserAccountWebUrlsPageHttpResponse(
				Long userAccountId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/user-accounts/{userAccountId}/web-urls",
				userAccountId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public WebUrl getWebUrl(Long webUrlId) throws Exception {
			HttpInvoker.HttpResponse httpResponse = getWebUrlHttpResponse(
				webUrlId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WebUrlSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getWebUrlHttpResponse(Long webUrlId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/web-urls/{webUrlId}",
				webUrlId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private WebUrlResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			WebUrlResource.class.getName());

		private Builder _builder;

	}

}