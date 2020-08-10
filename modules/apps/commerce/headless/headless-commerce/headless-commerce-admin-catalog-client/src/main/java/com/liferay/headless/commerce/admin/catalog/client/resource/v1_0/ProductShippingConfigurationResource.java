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

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductShippingConfiguration;
import com.liferay.headless.commerce.admin.catalog.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.catalog.client.problem.Problem;

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
public interface ProductShippingConfigurationResource {

	public static Builder builder() {
		return new Builder();
	}

	public ProductShippingConfiguration
			getProductByExternalReferenceCodeShippingConfiguration(
				String externalReferenceCode)
		throws Exception;

	public HttpInvoker.HttpResponse
			getProductByExternalReferenceCodeShippingConfigurationHttpResponse(
				String externalReferenceCode)
		throws Exception;

	public void patchProductByExternalReferenceCodeShippingConfiguration(
			String externalReferenceCode,
			ProductShippingConfiguration productShippingConfiguration)
		throws Exception;

	public HttpInvoker.HttpResponse
			patchProductByExternalReferenceCodeShippingConfigurationHttpResponse(
				String externalReferenceCode,
				ProductShippingConfiguration productShippingConfiguration)
		throws Exception;

	public ProductShippingConfiguration getProductIdShippingConfiguration(
			Long id)
		throws Exception;

	public HttpInvoker.HttpResponse
			getProductIdShippingConfigurationHttpResponse(Long id)
		throws Exception;

	public void patchProductIdShippingConfiguration(
			Long id, ProductShippingConfiguration productShippingConfiguration)
		throws Exception;

	public HttpInvoker.HttpResponse
			patchProductIdShippingConfigurationHttpResponse(
				Long id,
				ProductShippingConfiguration productShippingConfiguration)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public ProductShippingConfigurationResource build() {
			return new ProductShippingConfigurationResourceImpl(this);
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

	public static class ProductShippingConfigurationResourceImpl
		implements ProductShippingConfigurationResource {

		public ProductShippingConfiguration
				getProductByExternalReferenceCodeShippingConfiguration(
					String externalReferenceCode)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getProductByExternalReferenceCodeShippingConfigurationHttpResponse(
					externalReferenceCode);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return com.liferay.headless.commerce.admin.catalog.client.
					serdes.v1_0.ProductShippingConfigurationSerDes.toDTO(
						content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getProductByExternalReferenceCodeShippingConfigurationHttpResponse(
					String externalReferenceCode)
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
						"/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/shippingConfiguration",
				externalReferenceCode);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void patchProductByExternalReferenceCodeShippingConfiguration(
				String externalReferenceCode,
				ProductShippingConfiguration productShippingConfiguration)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				patchProductByExternalReferenceCodeShippingConfigurationHttpResponse(
					externalReferenceCode, productShippingConfiguration);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				patchProductByExternalReferenceCodeShippingConfigurationHttpResponse(
					String externalReferenceCode,
					ProductShippingConfiguration productShippingConfiguration)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				productShippingConfiguration.toString(), "application/json");

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

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.PATCH);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/shippingConfiguration",
				externalReferenceCode);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ProductShippingConfiguration getProductIdShippingConfiguration(
				Long id)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getProductIdShippingConfigurationHttpResponse(id);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return com.liferay.headless.commerce.admin.catalog.client.
					serdes.v1_0.ProductShippingConfigurationSerDes.toDTO(
						content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getProductIdShippingConfigurationHttpResponse(Long id)
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
						"/o/headless-commerce-admin-catalog/v1.0/products/{id}/shippingConfiguration",
				id);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void patchProductIdShippingConfiguration(
				Long id,
				ProductShippingConfiguration productShippingConfiguration)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				patchProductIdShippingConfigurationHttpResponse(
					id, productShippingConfiguration);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				patchProductIdShippingConfigurationHttpResponse(
					Long id,
					ProductShippingConfiguration productShippingConfiguration)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				productShippingConfiguration.toString(), "application/json");

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

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.PATCH);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-commerce-admin-catalog/v1.0/products/{id}/shippingConfiguration",
				id);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private ProductShippingConfigurationResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			ProductShippingConfigurationResource.class.getName());

		private Builder _builder;

	}

}