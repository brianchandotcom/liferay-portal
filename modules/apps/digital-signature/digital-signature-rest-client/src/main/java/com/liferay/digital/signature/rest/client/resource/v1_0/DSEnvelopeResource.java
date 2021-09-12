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

package com.liferay.digital.signature.rest.client.resource.v1_0;

import com.liferay.digital.signature.rest.client.dto.v1_0.DSEnvelope;
import com.liferay.digital.signature.rest.client.http.HttpInvoker;
import com.liferay.digital.signature.rest.client.pagination.Page;
import com.liferay.digital.signature.rest.client.pagination.Pagination;
import com.liferay.digital.signature.rest.client.problem.Problem;
import com.liferay.digital.signature.rest.client.serdes.v1_0.DSEnvelopeSerDes;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author José Abelenda
 * @generated
 */
@Generated("")
public interface DSEnvelopeResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<DSEnvelope> getDSEnvelopesPage(
			Long companyId, Long groupId, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse getDSEnvelopesPageHttpResponse(
			Long companyId, Long groupId, Pagination pagination)
		throws Exception;

	public DSEnvelope postDSEnvelope(
			Long companyId, Long groupId, DSEnvelope dsEnvelope)
		throws Exception;

	public HttpInvoker.HttpResponse postDSEnvelopeHttpResponse(
			Long companyId, Long groupId, DSEnvelope dsEnvelope)
		throws Exception;

	public void postDSEnvelopeBatch(
			Long companyId, Long groupId, DSEnvelope dsEnvelope,
			String callbackURL, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse postDSEnvelopeBatchHttpResponse(
			Long companyId, Long groupId, DSEnvelope dsEnvelope,
			String callbackURL, Object object)
		throws Exception;

	public DSEnvelope getDSEnvelope(
			Long companyId, Long groupId, String envelopeId)
		throws Exception;

	public HttpInvoker.HttpResponse getDSEnvelopeHttpResponse(
			Long companyId, Long groupId, String envelopeId)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public DSEnvelopeResource build() {
			return new DSEnvelopeResourceImpl(this);
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

		public Builder parameters(String... parameters) {
			if ((parameters.length % 2) != 0) {
				throw new IllegalArgumentException(
					"Parameters length is not an even number");
			}

			for (int i = 0; i < parameters.length; i += 2) {
				String parameterName = String.valueOf(parameters[i]);
				String parameterValue = String.valueOf(parameters[i + 1]);

				_parameters.put(parameterName, parameterValue);
			}

			return this;
		}

		private Builder() {
		}

		private Map<String, String> _headers = new LinkedHashMap<>();
		private String _host = "localhost";
		private Locale _locale;
		private String _login = "";
		private String _password = "";
		private Map<String, String> _parameters = new LinkedHashMap<>();
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class DSEnvelopeResourceImpl implements DSEnvelopeResource {

		public Page<DSEnvelope> getDSEnvelopesPage(
				Long companyId, Long groupId, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getDSEnvelopesPageHttpResponse(companyId, groupId, pagination);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return Page.of(content, DSEnvelopeSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getDSEnvelopesPageHttpResponse(
				Long companyId, Long groupId, Pagination pagination)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-digital-signature/v1.0/envelopes/{companyId}/{groupId}");

			httpInvoker.path("companyId", companyId);
			httpInvoker.path("groupId", groupId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DSEnvelope postDSEnvelope(
				Long companyId, Long groupId, DSEnvelope dsEnvelope)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = postDSEnvelopeHttpResponse(
				companyId, groupId, dsEnvelope);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return DSEnvelopeSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse postDSEnvelopeHttpResponse(
				Long companyId, Long groupId, DSEnvelope dsEnvelope)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(dsEnvelope.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-digital-signature/v1.0/envelopes/{companyId}/{groupId}");

			httpInvoker.path("companyId", companyId);
			httpInvoker.path("groupId", groupId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postDSEnvelopeBatch(
				Long companyId, Long groupId, DSEnvelope dsEnvelope,
				String callbackURL, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postDSEnvelopeBatchHttpResponse(
					companyId, groupId, dsEnvelope, callbackURL, object);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}
		}

		public HttpInvoker.HttpResponse postDSEnvelopeBatchHttpResponse(
				Long companyId, Long groupId, DSEnvelope dsEnvelope,
				String callbackURL, Object object)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(object.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			if (callbackURL != null) {
				httpInvoker.parameter(
					"callbackURL", String.valueOf(callbackURL));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-digital-signature/v1.0/envelopes/{companyId}/{groupId}/batch");

			httpInvoker.path("companyId", companyId);
			httpInvoker.path("groupId", groupId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public DSEnvelope getDSEnvelope(
				Long companyId, Long groupId, String envelopeId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = getDSEnvelopeHttpResponse(
				companyId, groupId, envelopeId);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return DSEnvelopeSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getDSEnvelopeHttpResponse(
				Long companyId, Long groupId, String envelopeId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-digital-signature/v1.0/envelopes/{companyId}/{groupId}/{envelopeId}");

			httpInvoker.path("companyId", companyId);
			httpInvoker.path("groupId", groupId);
			httpInvoker.path("envelopeId", envelopeId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private DSEnvelopeResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			DSEnvelopeResource.class.getName());

		private Builder _builder;

	}

}