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

package com.liferay.headless.form.client.resource.v1_0;

import com.liferay.headless.form.client.dto.v1_0.FormDocument;
import com.liferay.headless.form.client.http.HttpInvoker;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface FormDocumentResource {

	public static Builder builder() {
		return new Builder();
	}

	public void deleteFormDocument(Long formDocumentId) throws Exception;

	public HttpInvoker.HttpResponse deleteFormDocumentHttpResponse(
			Long formDocumentId)
		throws Exception;

	public FormDocument getFormDocument(Long formDocumentId) throws Exception;

	public HttpInvoker.HttpResponse getFormDocumentHttpResponse(
			Long formDocumentId)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public FormDocumentResource build() {
			return new FormDocumentResourceImpl(this);
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

	public static class FormDocumentResourceImpl
		implements FormDocumentResource {

		public void deleteFormDocument(Long formDocumentId) throws Exception {
			HttpInvoker.HttpResponse httpResponse =
				deleteFormDocumentHttpResponse(formDocumentId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse deleteFormDocumentHttpResponse(
				Long formDocumentId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-form/v1.0/form-documents/{formDocumentId}",
				formDocumentId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public FormDocument getFormDocument(Long formDocumentId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = getFormDocumentHttpResponse(
				formDocumentId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return com.liferay.headless.form.client.serdes.v1_0.
					FormDocumentSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getFormDocumentHttpResponse(
				Long formDocumentId)
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
						"/o/headless-form/v1.0/form-documents/{formDocumentId}",
				formDocumentId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private FormDocumentResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			FormDocumentResource.class.getName());

		private Builder _builder;

	}

}