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

package com.liferay.headless.commerce.admin.catalog.client.resource.v1_0;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.OptionValue;
import com.liferay.headless.commerce.admin.catalog.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Page;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.catalog.client.problem.Problem;
import com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0.OptionValueSerDes;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public interface OptionValueResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<OptionValue> getOptionByExternalReferenceCodeOptionValuesPage(
			String externalReferenceCode, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getOptionByExternalReferenceCodeOptionValuesPageHttpResponse(
				String externalReferenceCode, Pagination pagination)
		throws Exception;

	public OptionValue postOptionByExternalReferenceCodeOptionValue(
			String externalReferenceCode, OptionValue optionValue)
		throws Exception;

	public HttpInvoker.HttpResponse
			postOptionByExternalReferenceCodeOptionValueHttpResponse(
				String externalReferenceCode, OptionValue optionValue)
		throws Exception;

	public Page<OptionValue> getOptionIdOptionValuesPage(
			Long id, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse getOptionIdOptionValuesPageHttpResponse(
			Long id, Pagination pagination)
		throws Exception;

	public OptionValue postOptionIdOptionValue(Long id, OptionValue optionValue)
		throws Exception;

	public HttpInvoker.HttpResponse postOptionIdOptionValueHttpResponse(
			Long id, OptionValue optionValue)
		throws Exception;

	public void postOptionIdOptionValueBatch(
			Long id, String callbackURL, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse postOptionIdOptionValueBatchHttpResponse(
			Long id, String callbackURL, Object object)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public OptionValueResource build() {
			return new OptionValueResourceImpl(this);
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
		private String _login = "";
		private String _password = "";
		private Map<String, String> _parameters = new LinkedHashMap<>();
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class OptionValueResourceImpl implements OptionValueResource {

		public Page<OptionValue>
				getOptionByExternalReferenceCodeOptionValuesPage(
					String externalReferenceCode, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getOptionByExternalReferenceCodeOptionValuesPageHttpResponse(
					externalReferenceCode, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, OptionValueSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getOptionByExternalReferenceCodeOptionValuesPageHttpResponse(
					String externalReferenceCode, Pagination pagination)
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
						"/o/headless-commerce-admin-catalog/v1.0/options/by-externalReferenceCode/{externalReferenceCode}/optionValues",
				externalReferenceCode);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public OptionValue postOptionByExternalReferenceCodeOptionValue(
				String externalReferenceCode, OptionValue optionValue)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postOptionByExternalReferenceCodeOptionValueHttpResponse(
					externalReferenceCode, optionValue);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return OptionValueSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postOptionByExternalReferenceCodeOptionValueHttpResponse(
					String externalReferenceCode, OptionValue optionValue)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(optionValue.toString(), "application/json");

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
						"/o/headless-commerce-admin-catalog/v1.0/options/by-externalReferenceCode/{externalReferenceCode}/optionValues",
				externalReferenceCode);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<OptionValue> getOptionIdOptionValuesPage(
				Long id, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getOptionIdOptionValuesPageHttpResponse(id, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, OptionValueSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getOptionIdOptionValuesPageHttpResponse(
				Long id, Pagination pagination)
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
						"/o/headless-commerce-admin-catalog/v1.0/options/{id}/optionValues",
				id);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public OptionValue postOptionIdOptionValue(
				Long id, OptionValue optionValue)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postOptionIdOptionValueHttpResponse(id, optionValue);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return OptionValueSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse postOptionIdOptionValueHttpResponse(
				Long id, OptionValue optionValue)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(optionValue.toString(), "application/json");

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
						"/o/headless-commerce-admin-catalog/v1.0/options/{id}/optionValues",
				id);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postOptionIdOptionValueBatch(
				Long id, String callbackURL, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postOptionIdOptionValueBatchHttpResponse(
					id, callbackURL, object);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				postOptionIdOptionValueBatchHttpResponse(
					Long id, String callbackURL, Object object)
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
						"/o/headless-commerce-admin-catalog/v1.0/options/{id}/optionValues/batch",
				id);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private OptionValueResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			OptionValueResource.class.getName());

		private Builder _builder;

	}

}