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

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.client.problem.Problem;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowInstanceSerDes;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface WorkflowInstanceResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<WorkflowInstance> getWorkflowInstancesPage(
			String[] assetClassNames, Long[] assetPrimaryKeys,
			Boolean completed, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse getWorkflowInstancesPageHttpResponse(
			String[] assetClassNames, Long[] assetPrimaryKeys,
			Boolean completed, Pagination pagination)
		throws Exception;

	public WorkflowInstance postWorkflowInstanceSubmit(
			com.liferay.headless.admin.workflow.client.dto.v1_0.
				WorkflowInstanceSubmit workflowInstanceSubmit)
		throws Exception;

	public HttpInvoker.HttpResponse postWorkflowInstanceSubmitHttpResponse(
			com.liferay.headless.admin.workflow.client.dto.v1_0.
				WorkflowInstanceSubmit workflowInstanceSubmit)
		throws Exception;

	public void deleteWorkflowInstance(Long workflowInstanceId)
		throws Exception;

	public HttpInvoker.HttpResponse deleteWorkflowInstanceHttpResponse(
			Long workflowInstanceId)
		throws Exception;

	public WorkflowInstance getWorkflowInstance(Long workflowInstanceId)
		throws Exception;

	public HttpInvoker.HttpResponse getWorkflowInstanceHttpResponse(
			Long workflowInstanceId)
		throws Exception;

	public WorkflowInstance postWorkflowInstanceChangeTransition(
			Long workflowInstanceId,
			com.liferay.headless.admin.workflow.client.dto.v1_0.ChangeTransition
				changeTransition)
		throws Exception;

	public HttpInvoker.HttpResponse
			postWorkflowInstanceChangeTransitionHttpResponse(
				Long workflowInstanceId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					ChangeTransition changeTransition)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public WorkflowInstanceResource build() {
			return new WorkflowInstanceResourceImpl(this);
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

	public static class WorkflowInstanceResourceImpl
		implements WorkflowInstanceResource {

		public Page<WorkflowInstance> getWorkflowInstancesPage(
				String[] assetClassNames, Long[] assetPrimaryKeys,
				Boolean completed, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowInstancesPageHttpResponse(
					assetClassNames, assetPrimaryKeys, completed, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, WorkflowInstanceSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getWorkflowInstancesPageHttpResponse(
				String[] assetClassNames, Long[] assetPrimaryKeys,
				Boolean completed, Pagination pagination)
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

			if (assetClassNames != null) {
				for (int i = 0; i < assetClassNames.length; i++) {
					httpInvoker.parameter(
						"assetClassNames", String.valueOf(assetClassNames[i]));
				}
			}

			if (assetPrimaryKeys != null) {
				for (int i = 0; i < assetPrimaryKeys.length; i++) {
					httpInvoker.parameter(
						"assetPrimaryKeys",
						String.valueOf(assetPrimaryKeys[i]));
				}
			}

			if (completed != null) {
				httpInvoker.parameter("completed", String.valueOf(completed));
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
						"/o/headless-admin-workflow/v1.0/workflow-instances");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public WorkflowInstance postWorkflowInstanceSubmit(
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					WorkflowInstanceSubmit workflowInstanceSubmit)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postWorkflowInstanceSubmitHttpResponse(workflowInstanceSubmit);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WorkflowInstanceSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse postWorkflowInstanceSubmitHttpResponse(
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					WorkflowInstanceSubmit workflowInstanceSubmit)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				workflowInstanceSubmit.toString(), "application/json");

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
						"/o/headless-admin-workflow/v1.0/workflow-instances/submit");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteWorkflowInstance(Long workflowInstanceId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteWorkflowInstanceHttpResponse(workflowInstanceId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return;
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse deleteWorkflowInstanceHttpResponse(
				Long workflowInstanceId)
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

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}",
				workflowInstanceId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public WorkflowInstance getWorkflowInstance(Long workflowInstanceId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowInstanceHttpResponse(workflowInstanceId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WorkflowInstanceSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getWorkflowInstanceHttpResponse(
				Long workflowInstanceId)
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
						"/o/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}",
				workflowInstanceId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public WorkflowInstance postWorkflowInstanceChangeTransition(
				Long workflowInstanceId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					ChangeTransition changeTransition)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postWorkflowInstanceChangeTransitionHttpResponse(
					workflowInstanceId, changeTransition);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WorkflowInstanceSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postWorkflowInstanceChangeTransitionHttpResponse(
					Long workflowInstanceId,
					com.liferay.headless.admin.workflow.client.dto.v1_0.
						ChangeTransition changeTransition)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(changeTransition.toString(), "application/json");

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
						"/o/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/change-transition",
				workflowInstanceId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private WorkflowInstanceResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			WorkflowInstanceResource.class.getName());

		private Builder _builder;

	}

}