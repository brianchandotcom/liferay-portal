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
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowTaskSerDes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class WorkflowTaskResource {

	public static Page<WorkflowTask> getRoleWorkflowTasksPage(
			Long roleId, Pagination pagination)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getRoleWorkflowTasksPageHttpResponse(roleId, pagination);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, WorkflowTaskSerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse getRoleWorkflowTasksPageHttpResponse(
			Long roleId, Pagination pagination)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (pagination != null) {
			httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
			httpInvoker.parameter(
				"pageSize", String.valueOf(pagination.getPageSize()));
		}

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-workflow/v1.0/roles/{roleId}/workflow-tasks",
			roleId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static Page<WorkflowTask> getWorkflowTasksAssignedToMePage(
			Pagination pagination)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getWorkflowTasksAssignedToMePageHttpResponse(pagination);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, WorkflowTaskSerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse
			getWorkflowTasksAssignedToMePageHttpResponse(Pagination pagination)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (pagination != null) {
			httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
			httpInvoker.parameter(
				"pageSize", String.valueOf(pagination.getPageSize()));
		}

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-me");

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static Page<WorkflowTask> getWorkflowTasksAssignedToMyRolesPage(
			Pagination pagination)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getWorkflowTasksAssignedToMyRolesPageHttpResponse(pagination);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, WorkflowTaskSerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse
			getWorkflowTasksAssignedToMyRolesPageHttpResponse(
				Pagination pagination)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (pagination != null) {
			httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
			httpInvoker.parameter(
				"pageSize", String.valueOf(pagination.getPageSize()));
		}

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-my-roles");

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static WorkflowTask getWorkflowTask(Long workflowTaskId)
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
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse getWorkflowTaskHttpResponse(
			Long workflowTaskId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}",
			workflowTaskId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static WorkflowTask postWorkflowTaskAssignToMe(
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
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse
			postWorkflowTaskAssignToMeHttpResponse(
				Long workflowTaskId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(workflowTaskAssignToMe.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-me",
			workflowTaskId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static WorkflowTask postWorkflowTaskAssignToUser(
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
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse
			postWorkflowTaskAssignToUserHttpResponse(
				Long workflowTaskId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					WorkflowTaskAssignToUser workflowTaskAssignToUser)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			workflowTaskAssignToUser.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-user",
			workflowTaskId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static WorkflowTask postWorkflowTaskChangeTransition(
			Long workflowTaskId,
			com.liferay.headless.admin.workflow.client.dto.v1_0.ChangeTransition
				changeTransition)
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
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse
			postWorkflowTaskChangeTransitionHttpResponse(
				Long workflowTaskId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					ChangeTransition changeTransition)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(changeTransition.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/change-transition",
			workflowTaskId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static WorkflowTask postWorkflowTaskUpdateDueDate(
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
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse
			postWorkflowTaskUpdateDueDateHttpResponse(
				Long workflowTaskId,
				com.liferay.headless.admin.workflow.client.dto.v1_0.
					WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(workflowTaskAssignToMe.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/update-due-date",
			workflowTaskId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	private static final Logger _logger = Logger.getLogger(
		WorkflowTaskResource.class.getName());

}