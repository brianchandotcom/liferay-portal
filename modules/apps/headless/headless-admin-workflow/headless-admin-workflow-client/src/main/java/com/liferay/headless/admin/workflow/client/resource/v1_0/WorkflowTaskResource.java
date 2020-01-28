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

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.client.problem.Problem;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowTaskSerDes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface WorkflowTaskResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<WorkflowTask> getWorkflowInstanceWorkflowTasksPage(
			Long workflowInstanceId, Boolean completed, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getWorkflowInstanceWorkflowTasksPageHttpResponse(
				Long workflowInstanceId, Boolean completed,
				Pagination pagination)
		throws Exception;

	public Page<WorkflowTask> getWorkflowInstanceWorkflowTasksAssignedToMePage(
			Long workflowInstanceId, Boolean completed, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getWorkflowInstanceWorkflowTasksAssignedToMePageHttpResponse(
				Long workflowInstanceId, Boolean completed,
				Pagination pagination)
		throws Exception;

	public Page<WorkflowTask>
			getWorkflowInstanceWorkflowTasksAssignedToUserPage(
				Long workflowInstanceId, Long assigneeId, Boolean completed,
				Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getWorkflowInstanceWorkflowTasksAssignedToUserPageHttpResponse(
				Long workflowInstanceId, Long assigneeId, Boolean completed,
				Pagination pagination)
		throws Exception;

	public Page<WorkflowTask> getWorkflowTasksPage(
			Boolean andOperator, Long[] assetPrimaryKeys, String assetTitle,
			String[] assetTypes, Long[] assigneeUserIds, Boolean completed,
			java.util.Date dateDueEnd, java.util.Date dateDueStart,
			Boolean searchByUserRoles, String[] taskNames,
			Long workflowDefinitionId, Long[] workflowInstanceIds,
			Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse getWorkflowTasksPageHttpResponse(
			Boolean andOperator, Long[] assetPrimaryKeys, String assetTitle,
			String[] assetTypes, Long[] assigneeUserIds, Boolean completed,
			java.util.Date dateDueEnd, java.util.Date dateDueStart,
			Boolean searchByUserRoles, String[] taskNames,
			Long workflowDefinitionId, Long[] workflowInstanceIds,
			Pagination pagination, String sortString)
		throws Exception;

	public void patchWorkflowTaskAssignToUser(
			com.liferay.headless.admin.workflow.client.dto.v1_0.
				WorkflowTaskAssignToUser[] workflowTaskAssignToUsers)
		throws Exception;

	public HttpInvoker.HttpResponse patchWorkflowTaskAssignToUserHttpResponse(
			com.liferay.headless.admin.workflow.client.dto.v1_0.
				WorkflowTaskAssignToUser[] workflowTaskAssignToUsers)
		throws Exception;

	public Page<WorkflowTask> getWorkflowTasksAssignedToMePage(
			Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getWorkflowTasksAssignedToMePageHttpResponse(Pagination pagination)
		throws Exception;

	public Page<WorkflowTask> getWorkflowTasksAssignedToMyRolesPage(
			Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getWorkflowTasksAssignedToMyRolesPageHttpResponse(
				Pagination pagination)
		throws Exception;

	public Page<WorkflowTask> getWorkflowTasksAssignedToRolePage(
			Long roleId, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getWorkflowTasksAssignedToRolePageHttpResponse(
				Long roleId, Pagination pagination)
		throws Exception;

	public Page<WorkflowTask> getWorkflowTasksAssignedToUserPage(
			Long assigneeId, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getWorkflowTasksAssignedToUserPageHttpResponse(
				Long assigneeId, Pagination pagination)
		throws Exception;

	public Page<WorkflowTask> getWorkflowTasksAssignedToUserRolesPage(
			Long assigneeId, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getWorkflowTasksAssignedToUserRolesPageHttpResponse(
				Long assigneeId, Pagination pagination)
		throws Exception;

	public Page<WorkflowTask> getWorkflowTasksSubmittingUserPage(
			Long creatorId, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getWorkflowTasksSubmittingUserPageHttpResponse(
				Long creatorId, Pagination pagination)
		throws Exception;

	public WorkflowTask getWorkflowTask(Long workflowTaskId) throws Exception;

	public HttpInvoker.HttpResponse getWorkflowTaskHttpResponse(
			Long workflowTaskId)
		throws Exception;

	public WorkflowTask postWorkflowTaskAssignToMe(
			Long workflowTaskId,
			com.liferay.headless.admin.workflow.client.dto.v1_0.
				WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception;

	public HttpInvoker.HttpResponse postWorkflowTaskAssignToMeHttpResponse(
			Long workflowTaskId,
			com.liferay.headless.admin.workflow.client.dto.v1_0.
				WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception;

	public WorkflowTask postWorkflowTaskAssignToRole(
			Long workflowTaskId,
			com.liferay.headless.admin.workflow.client.dto.v1_0.
				WorkflowTaskAssignToRole workflowTaskAssignToRole)
		throws Exception;

	public HttpInvoker.HttpResponse postWorkflowTaskAssignToRoleHttpResponse(
			Long workflowTaskId,
			com.liferay.headless.admin.workflow.client.dto.v1_0.
				WorkflowTaskAssignToRole workflowTaskAssignToRole)
		throws Exception;

	public WorkflowTask postWorkflowTaskAssignToUser(
			Long workflowTaskId,
			com.liferay.headless.admin.workflow.client.dto.v1_0.
				WorkflowTaskAssignToUser workflowTaskAssignToUser)
		throws Exception;

	public HttpInvoker.HttpResponse postWorkflowTaskAssignToUserHttpResponse(
			Long workflowTaskId,
			com.liferay.headless.admin.workflow.client.dto.v1_0.
				WorkflowTaskAssignToUser workflowTaskAssignToUser)
		throws Exception;

	public WorkflowTask postWorkflowTaskChangeTransition(
			Long workflowTaskId,
			com.liferay.headless.admin.workflow.client.dto.v1_0.ChangeTransition
				changeTransition)
		throws Exception;

	public HttpInvoker.HttpResponse
			postWorkflowTaskChangeTransitionHttpResponse(
				Long workflowTaskId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					ChangeTransition changeTransition)
		throws Exception;

	public String getWorkflowTaskHasAssignableUsers(Long workflowTaskId)
		throws Exception;

	public HttpInvoker.HttpResponse
			getWorkflowTaskHasAssignableUsersHttpResponse(Long workflowTaskId)
		throws Exception;

	public WorkflowTask postWorkflowTaskUpdateDueDate(
			Long workflowTaskId,
			com.liferay.headless.admin.workflow.client.dto.v1_0.
				WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception;

	public HttpInvoker.HttpResponse postWorkflowTaskUpdateDueDateHttpResponse(
			Long workflowTaskId,
			com.liferay.headless.admin.workflow.client.dto.v1_0.
				WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public WorkflowTaskResource build() {
			return new WorkflowTaskResourceImpl(this);
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

	public static class WorkflowTaskResourceImpl
		implements WorkflowTaskResource {

		public Page<WorkflowTask> getWorkflowInstanceWorkflowTasksPage(
				Long workflowInstanceId, Boolean completed,
				Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowInstanceWorkflowTasksPageHttpResponse(
					workflowInstanceId, completed, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, WorkflowTaskSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getWorkflowInstanceWorkflowTasksPageHttpResponse(
					Long workflowInstanceId, Boolean completed,
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
						"/o/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks",
				workflowInstanceId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<WorkflowTask>
				getWorkflowInstanceWorkflowTasksAssignedToMePage(
					Long workflowInstanceId, Boolean completed,
					Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowInstanceWorkflowTasksAssignedToMePageHttpResponse(
					workflowInstanceId, completed, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, WorkflowTaskSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getWorkflowInstanceWorkflowTasksAssignedToMePageHttpResponse(
					Long workflowInstanceId, Boolean completed,
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
						"/o/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks/assigned-to-me",
				workflowInstanceId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<WorkflowTask>
				getWorkflowInstanceWorkflowTasksAssignedToUserPage(
					Long workflowInstanceId, Long assigneeId, Boolean completed,
					Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowInstanceWorkflowTasksAssignedToUserPageHttpResponse(
					workflowInstanceId, assigneeId, completed, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, WorkflowTaskSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getWorkflowInstanceWorkflowTasksAssignedToUserPageHttpResponse(
					Long workflowInstanceId, Long assigneeId, Boolean completed,
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

			if (assigneeId != null) {
				httpInvoker.parameter("assigneeId", String.valueOf(assigneeId));
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
						"/o/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks/assigned-to-user",
				workflowInstanceId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<WorkflowTask> getWorkflowTasksPage(
				Boolean andOperator, Long[] assetPrimaryKeys, String assetTitle,
				String[] assetTypes, Long[] assigneeUserIds, Boolean completed,
				java.util.Date dateDueEnd, java.util.Date dateDueStart,
				Boolean searchByUserRoles, String[] taskNames,
				Long workflowDefinitionId, Long[] workflowInstanceIds,
				Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowTasksPageHttpResponse(
					andOperator, assetPrimaryKeys, assetTitle, assetTypes,
					assigneeUserIds, completed, dateDueEnd, dateDueStart,
					searchByUserRoles, taskNames, workflowDefinitionId,
					workflowInstanceIds, pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, WorkflowTaskSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getWorkflowTasksPageHttpResponse(
				Boolean andOperator, Long[] assetPrimaryKeys, String assetTitle,
				String[] assetTypes, Long[] assigneeUserIds, Boolean completed,
				java.util.Date dateDueEnd, java.util.Date dateDueStart,
				Boolean searchByUserRoles, String[] taskNames,
				Long workflowDefinitionId, Long[] workflowInstanceIds,
				Pagination pagination, String sortString)
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
				"yyyy-MM-dd'T'HH:mm:ss'Z'");

			if (andOperator != null) {
				httpInvoker.parameter(
					"andOperator", String.valueOf(andOperator));
			}

			if (assetPrimaryKeys != null) {
				for (int i = 0; i < assetPrimaryKeys.length; i++) {
					httpInvoker.parameter(
						"assetPrimaryKeys",
						String.valueOf(assetPrimaryKeys[i]));
				}
			}

			if (assetTitle != null) {
				httpInvoker.parameter("assetTitle", String.valueOf(assetTitle));
			}

			if (assetTypes != null) {
				for (int i = 0; i < assetTypes.length; i++) {
					httpInvoker.parameter(
						"assetTypes", String.valueOf(assetTypes[i]));
				}
			}

			if (assigneeUserIds != null) {
				for (int i = 0; i < assigneeUserIds.length; i++) {
					httpInvoker.parameter(
						"assigneeUserIds", String.valueOf(assigneeUserIds[i]));
				}
			}

			if (completed != null) {
				httpInvoker.parameter("completed", String.valueOf(completed));
			}

			if (dateDueEnd != null) {
				httpInvoker.parameter(
					"dateDueEnd", liferayToJSONDateFormat.format(dateDueEnd));
			}

			if (dateDueStart != null) {
				httpInvoker.parameter(
					"dateDueStart",
					liferayToJSONDateFormat.format(dateDueStart));
			}

			if (searchByUserRoles != null) {
				httpInvoker.parameter(
					"searchByUserRoles", String.valueOf(searchByUserRoles));
			}

			if (taskNames != null) {
				for (int i = 0; i < taskNames.length; i++) {
					httpInvoker.parameter(
						"taskNames", String.valueOf(taskNames[i]));
				}
			}

			if (workflowDefinitionId != null) {
				httpInvoker.parameter(
					"workflowDefinitionId",
					String.valueOf(workflowDefinitionId));
			}

			if (workflowInstanceIds != null) {
				for (int i = 0; i < workflowInstanceIds.length; i++) {
					httpInvoker.parameter(
						"workflowInstanceIds",
						String.valueOf(workflowInstanceIds[i]));
				}
			}

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			if (sortString != null) {
				httpInvoker.parameter("sort", sortString);
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-workflow/v1.0/workflow-tasks");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void patchWorkflowTaskAssignToUser(
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					WorkflowTaskAssignToUser[] workflowTaskAssignToUsers)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				patchWorkflowTaskAssignToUserHttpResponse(
					workflowTaskAssignToUsers);

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

		public HttpInvoker.HttpResponse
				patchWorkflowTaskAssignToUserHttpResponse(
					com.liferay.headless.admin.workflow.client.dto.v1_0.
						WorkflowTaskAssignToUser[] workflowTaskAssignToUsers)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				Stream.of(
					workflowTaskAssignToUsers
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

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.PATCH);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-workflow/v1.0/workflow-tasks/assign-to-user");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<WorkflowTask> getWorkflowTasksAssignedToMePage(
				Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowTasksAssignedToMePageHttpResponse(pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, WorkflowTaskSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getWorkflowTasksAssignedToMePageHttpResponse(
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

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-me");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<WorkflowTask> getWorkflowTasksAssignedToMyRolesPage(
				Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowTasksAssignedToMyRolesPageHttpResponse(pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, WorkflowTaskSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getWorkflowTasksAssignedToMyRolesPageHttpResponse(
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

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-my-roles");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<WorkflowTask> getWorkflowTasksAssignedToRolePage(
				Long roleId, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowTasksAssignedToRolePageHttpResponse(
					roleId, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, WorkflowTaskSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getWorkflowTasksAssignedToRolePageHttpResponse(
					Long roleId, Pagination pagination)
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

			if (roleId != null) {
				httpInvoker.parameter("roleId", String.valueOf(roleId));
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
						"/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-role");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<WorkflowTask> getWorkflowTasksAssignedToUserPage(
				Long assigneeId, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowTasksAssignedToUserPageHttpResponse(
					assigneeId, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, WorkflowTaskSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getWorkflowTasksAssignedToUserPageHttpResponse(
					Long assigneeId, Pagination pagination)
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

			if (assigneeId != null) {
				httpInvoker.parameter("assigneeId", String.valueOf(assigneeId));
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
						"/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-user");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<WorkflowTask> getWorkflowTasksAssignedToUserRolesPage(
				Long assigneeId, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowTasksAssignedToUserRolesPageHttpResponse(
					assigneeId, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, WorkflowTaskSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getWorkflowTasksAssignedToUserRolesPageHttpResponse(
					Long assigneeId, Pagination pagination)
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

			if (assigneeId != null) {
				httpInvoker.parameter("assigneeId", String.valueOf(assigneeId));
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
						"/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-user-roles");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<WorkflowTask> getWorkflowTasksSubmittingUserPage(
				Long creatorId, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowTasksSubmittingUserPageHttpResponse(
					creatorId, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, WorkflowTaskSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getWorkflowTasksSubmittingUserPageHttpResponse(
					Long creatorId, Pagination pagination)
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

			if (creatorId != null) {
				httpInvoker.parameter("creatorId", String.valueOf(creatorId));
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
						"/o/headless-admin-workflow/v1.0/workflow-tasks/submitting-user");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public WorkflowTask getWorkflowTask(Long workflowTaskId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = getWorkflowTaskHttpResponse(
				workflowTaskId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WorkflowTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getWorkflowTaskHttpResponse(
				Long workflowTaskId)
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
						"/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}",
				workflowTaskId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public WorkflowTask postWorkflowTaskAssignToMe(
				Long workflowTaskId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					WorkflowTaskAssignToMe workflowTaskAssignToMe)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postWorkflowTaskAssignToMeHttpResponse(
					workflowTaskId, workflowTaskAssignToMe);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WorkflowTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse postWorkflowTaskAssignToMeHttpResponse(
				Long workflowTaskId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					WorkflowTaskAssignToMe workflowTaskAssignToMe)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				workflowTaskAssignToMe.toString(), "application/json");

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
						"/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-me",
				workflowTaskId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public WorkflowTask postWorkflowTaskAssignToRole(
				Long workflowTaskId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					WorkflowTaskAssignToRole workflowTaskAssignToRole)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postWorkflowTaskAssignToRoleHttpResponse(
					workflowTaskId, workflowTaskAssignToRole);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WorkflowTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postWorkflowTaskAssignToRoleHttpResponse(
					Long workflowTaskId,
					com.liferay.headless.admin.workflow.client.dto.v1_0.
						WorkflowTaskAssignToRole workflowTaskAssignToRole)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				workflowTaskAssignToRole.toString(), "application/json");

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
						"/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-role",
				workflowTaskId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public WorkflowTask postWorkflowTaskAssignToUser(
				Long workflowTaskId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					WorkflowTaskAssignToUser workflowTaskAssignToUser)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postWorkflowTaskAssignToUserHttpResponse(
					workflowTaskId, workflowTaskAssignToUser);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WorkflowTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postWorkflowTaskAssignToUserHttpResponse(
					Long workflowTaskId,
					com.liferay.headless.admin.workflow.client.dto.v1_0.
						WorkflowTaskAssignToUser workflowTaskAssignToUser)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				workflowTaskAssignToUser.toString(), "application/json");

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
						"/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-user",
				workflowTaskId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public WorkflowTask postWorkflowTaskChangeTransition(
				Long workflowTaskId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					ChangeTransition changeTransition)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postWorkflowTaskChangeTransitionHttpResponse(
					workflowTaskId, changeTransition);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WorkflowTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postWorkflowTaskChangeTransitionHttpResponse(
					Long workflowTaskId,
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
						"/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/change-transition",
				workflowTaskId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public String getWorkflowTaskHasAssignableUsers(Long workflowTaskId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getWorkflowTaskHasAssignableUsersHttpResponse(workflowTaskId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return content;
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getWorkflowTaskHasAssignableUsersHttpResponse(
					Long workflowTaskId)
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
						"/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/has-assignable-users",
				workflowTaskId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public WorkflowTask postWorkflowTaskUpdateDueDate(
				Long workflowTaskId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					WorkflowTaskAssignToMe workflowTaskAssignToMe)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postWorkflowTaskUpdateDueDateHttpResponse(
					workflowTaskId, workflowTaskAssignToMe);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return WorkflowTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postWorkflowTaskUpdateDueDateHttpResponse(
					Long workflowTaskId,
					com.liferay.headless.admin.workflow.client.dto.v1_0.
						WorkflowTaskAssignToMe workflowTaskAssignToMe)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				workflowTaskAssignToMe.toString(), "application/json");

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
						"/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/update-due-date",
				workflowTaskId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private WorkflowTaskResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			WorkflowTaskResource.class.getName());

		private Builder _builder;

	}

}