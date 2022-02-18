/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.machine.learning.client.resource.v1_0;

import com.liferay.headless.commerce.machine.learning.client.dto.v1_0.AccountForecast;
import com.liferay.headless.commerce.machine.learning.client.http.HttpInvoker;
import com.liferay.headless.commerce.machine.learning.client.pagination.Page;
import com.liferay.headless.commerce.machine.learning.client.pagination.Pagination;
import com.liferay.headless.commerce.machine.learning.client.problem.Problem;
import com.liferay.headless.commerce.machine.learning.client.serdes.v1_0.AccountForecastSerDes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
public interface AccountForecastResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<AccountForecast> getAccountForecastsByMonthlyRevenuePage(
			Long[] accountIds, Integer forecastLength,
			java.util.Date forecastStartDate, Integer historyLength,
			Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getAccountForecastsByMonthlyRevenuePageHttpResponse(
				Long[] accountIds, Integer forecastLength,
				java.util.Date forecastStartDate, Integer historyLength,
				Pagination pagination)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public AccountForecastResource build() {
			return new AccountForecastResourceImpl(this);
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

	public static class AccountForecastResourceImpl
		implements AccountForecastResource {

		public Page<AccountForecast> getAccountForecastsByMonthlyRevenuePage(
				Long[] accountIds, Integer forecastLength,
				java.util.Date forecastStartDate, Integer historyLength,
				Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getAccountForecastsByMonthlyRevenuePageHttpResponse(
					accountIds, forecastLength, forecastStartDate,
					historyLength, pagination);

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
				return Page.of(content, AccountForecastSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getAccountForecastsByMonthlyRevenuePageHttpResponse(
					Long[] accountIds, Integer forecastLength,
					java.util.Date forecastStartDate, Integer historyLength,
					Pagination pagination)
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

			DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssXX");

			if (accountIds != null) {
				for (int i = 0; i < accountIds.length; i++) {
					httpInvoker.parameter(
						"accountIds", String.valueOf(accountIds[i]));
				}
			}

			if (forecastLength != null) {
				httpInvoker.parameter(
					"forecastLength", String.valueOf(forecastLength));
			}

			if (forecastStartDate != null) {
				httpInvoker.parameter(
					"forecastStartDate",
					liferayToJSONDateFormat.format(forecastStartDate));
			}

			if (historyLength != null) {
				httpInvoker.parameter(
					"historyLength", String.valueOf(historyLength));
			}

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-commerce-machine-learning/v1.0/accountForecasts/by-monthlyRevenue");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private AccountForecastResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			AccountForecastResource.class.getName());

		private Builder _builder;

	}

}