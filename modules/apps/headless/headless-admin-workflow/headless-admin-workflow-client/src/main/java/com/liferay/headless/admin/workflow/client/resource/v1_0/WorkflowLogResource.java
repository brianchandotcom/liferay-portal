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

package com.liferay.headless.admin.workflow.client.resource.v1_0;

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowLog;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowLogSerDes;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface WorkflowLogResource {

	public static Builder builder() {
		return new Builder();
	}

	public WorkflowLog getWorkflowLog(Long workflowLogId) throws Exception;

	public HttpInvoker.HttpResponse getWorkflowLogHttpResponse(
			Long workflowLogId)
		throws Exception;

	public Page<WorkflowLog> getWorkflowTaskWorkflowLogsPage(
			Long workflowTaskId, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse getWorkflowTaskWorkflowLogsPageHttpResponse(
			Long workflowTaskId, Pagination pagination)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public WorkflowLogResource build() {
			return new WorkflowLogResourceImpl(this);
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

	public static class WorkflowLogResourceImpl implements WorkflowLogResource {

		public WorkflowLog getWorkflowLog(Long workflowLogId) throws Exception {
			HttpInvoker.HttpResponse httpResponse = getWorkflowLogHttpResponse(
				workflowLogId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WorkflowLogSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getWorkflowLogHttpResponse(
				Long workflowLogId)
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
						"/o/headless-admin-workflow/v1.0/workflow-logs/{workflowLogId}",
				workflowLogId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<WorkflowLog> getWorkflowTaskWorkflowLogsPage(
				Long workflowTaskId, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowTaskWorkflowLogsPageHttpResponse(
					workflowTaskId, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, WorkflowLogSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getWorkflowTaskWorkflowLogsPageHttpResponse(
					Long workflowTaskId, Pagination pagination)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
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
						"/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/workflow-logs",
				workflowTaskId);

			if (_builder._cookie != null) {
				httpInvoker.cookie(_builder._cookie);
			}

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private WorkflowLogResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			WorkflowLogResource.class.getName());

		private Builder _builder;

	}

}