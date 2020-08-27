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

package com.liferay.headless.commerce.admin.order.client.resource.v1_0;

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.order.client.problem.Problem;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public interface BillingAddressResource {

	public static Builder builder() {
		return new Builder();
	}

	public BillingAddress getOrderByExternalReferenceCodeBillingAddress(
			String externalReferenceCode)
		throws Exception;

	public HttpInvoker.HttpResponse
			getOrderByExternalReferenceCodeBillingAddressHttpResponse(
				String externalReferenceCode)
		throws Exception;

	public void patchOrderByExternalReferenceCodeBillingAddress(
			String externalReferenceCode, BillingAddress billingAddress)
		throws Exception;

	public HttpInvoker.HttpResponse
			patchOrderByExternalReferenceCodeBillingAddressHttpResponse(
				String externalReferenceCode, BillingAddress billingAddress)
		throws Exception;

	public BillingAddress getOrderIdBillingAddress(Long id) throws Exception;

	public HttpInvoker.HttpResponse getOrderIdBillingAddressHttpResponse(
			Long id)
		throws Exception;

	public void patchOrderIdBillingAddress(
			Long id, BillingAddress billingAddress)
		throws Exception;

	public HttpInvoker.HttpResponse patchOrderIdBillingAddressHttpResponse(
			Long id, BillingAddress billingAddress)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public BillingAddressResource build() {
			return new BillingAddressResourceImpl(this);
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

	public static class BillingAddressResourceImpl
		implements BillingAddressResource {

		public BillingAddress getOrderByExternalReferenceCodeBillingAddress(
				String externalReferenceCode)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getOrderByExternalReferenceCodeBillingAddressHttpResponse(
					externalReferenceCode);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return com.liferay.headless.commerce.admin.order.client.serdes.
					v1_0.BillingAddressSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getOrderByExternalReferenceCodeBillingAddressHttpResponse(
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
						"/o/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/billingAddress",
				externalReferenceCode);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void patchOrderByExternalReferenceCodeBillingAddress(
				String externalReferenceCode, BillingAddress billingAddress)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				patchOrderByExternalReferenceCodeBillingAddressHttpResponse(
					externalReferenceCode, billingAddress);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse
				patchOrderByExternalReferenceCodeBillingAddressHttpResponse(
					String externalReferenceCode, BillingAddress billingAddress)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(billingAddress.toString(), "application/json");

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
						"/o/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/billingAddress",
				externalReferenceCode);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public BillingAddress getOrderIdBillingAddress(Long id)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getOrderIdBillingAddressHttpResponse(id);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return com.liferay.headless.commerce.admin.order.client.serdes.
					v1_0.BillingAddressSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getOrderIdBillingAddressHttpResponse(
				Long id)
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
						"/o/headless-commerce-admin-order/v1.0/orders/{id}/billingAddress",
				id);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void patchOrderIdBillingAddress(
				Long id, BillingAddress billingAddress)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				patchOrderIdBillingAddressHttpResponse(id, billingAddress);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse patchOrderIdBillingAddressHttpResponse(
				Long id, BillingAddress billingAddress)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(billingAddress.toString(), "application/json");

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
						"/o/headless-commerce-admin-order/v1.0/orders/{id}/billingAddress",
				id);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private BillingAddressResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			BillingAddressResource.class.getName());

		private Builder _builder;

	}

}