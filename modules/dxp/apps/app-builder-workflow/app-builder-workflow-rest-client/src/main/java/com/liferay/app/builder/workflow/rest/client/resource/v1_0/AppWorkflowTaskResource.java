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

package com.liferay.app.builder.workflow.rest.client.resource.v1_0;

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.client.http.HttpInvoker;
import com.liferay.app.builder.workflow.rest.client.pagination.Page;
import com.liferay.app.builder.workflow.rest.client.problem.Problem;
import com.liferay.app.builder.workflow.rest.client.serdes.v1_0.AppWorkflowTaskSerDes;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public interface AppWorkflowTaskResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<AppWorkflowTask> getAppWorkflowTasksPage(Long appId)
		throws Exception;

	public HttpInvoker.HttpResponse getAppWorkflowTasksPageHttpResponse(
			Long appId)
		throws Exception;

	public Page<AppWorkflowTask> postAppWorkflowTasks(
			Long appId, AppWorkflowTask[] appWorkflowTasks)
		throws Exception;

	public HttpInvoker.HttpResponse postAppWorkflowTasksHttpResponse(
			Long appId, AppWorkflowTask[] appWorkflowTasks)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public AppWorkflowTaskResource build() {
			return new AppWorkflowTaskResourceImpl(this);
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

	public static class AppWorkflowTaskResourceImpl
		implements AppWorkflowTaskResource {

		public Page<AppWorkflowTask> getAppWorkflowTasksPage(Long appId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getAppWorkflowTasksPageHttpResponse(appId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, AppWorkflowTaskSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getAppWorkflowTasksPageHttpResponse(
				Long appId)
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
						"/o/app-builder-workflow/v1.0/apps/{appId}/app-workflow-tasks",
				appId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<AppWorkflowTask> postAppWorkflowTasks(
				Long appId, AppWorkflowTask[] appWorkflowTasks)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postAppWorkflowTasksHttpResponse(appId, appWorkflowTasks);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, AppWorkflowTaskSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse postAppWorkflowTasksHttpResponse(
				Long appId, AppWorkflowTask[] appWorkflowTasks)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				Stream.of(
					appWorkflowTasks
				).map(
					value -> String.valueOf(value)
				).collect(
					Collectors.toList()
				).toString(),
				"application/json");

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
						"/o/app-builder-workflow/v1.0/apps/{appId}/app-workflow-tasks",
				appId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private AppWorkflowTaskResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			AppWorkflowTaskResource.class.getName());

		private Builder _builder;

	}

}