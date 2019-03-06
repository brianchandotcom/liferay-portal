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

package com.liferay.headless.workflow.internal.resource.v1_0;

import com.liferay.headless.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.net.URI;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseWorkflowTaskResourceImpl
	implements WorkflowTaskResource {

	@GET
	@Override
	@Path("/roles/{role-id}/workflow-tasks")
	@Produces("application/json")
	public Page<WorkflowTask> getRoleWorkflowTasksPage(
			@PathParam("role-id") Long roleId, @Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@GET
	@Override
	@Path("/workflow-tasks/{workflow-task-id}")
	@Produces("application/json")
	public WorkflowTask getWorkflowTask(
			@PathParam("workflow-task-id") Long workflowTaskId)
		throws Exception {

		return new WorkflowTask();
	}

	@GET
	@Override
	@Path("/workflow-tasks")
	@Produces("application/json")
	public Page<WorkflowTask> getWorkflowTasksPage(
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Consumes("application/json")
	@Override
	@Path("/workflow-tasks/{workflow-task-id}/assign-to-me")
	@POST
	@Produces("application/json")
	public WorkflowTask postWorkflowTaskAssignToMe(
			@PathParam("workflow-task-id") Long workflowTaskId,
			WorkflowTask workflowTask)
		throws Exception {

		return new WorkflowTask();
	}

	@Consumes("application/json")
	@Override
	@Path("/workflow-tasks/{workflow-task-id}/assign-to-user")
	@POST
	@Produces("application/json")
	public WorkflowTask postWorkflowTaskAssignToUser(
			@PathParam("workflow-task-id") Long workflowTaskId,
			WorkflowTask workflowTask)
		throws Exception {

		return new WorkflowTask();
	}

	@Consumes("application/json")
	@Override
	@Path("/workflow-tasks/{workflow-task-id}/change-transition")
	@POST
	@Produces("application/json")
	public WorkflowTask postWorkflowTaskChangeTransition(
			@PathParam("workflow-task-id") Long workflowTaskId,
			WorkflowTask workflowTask)
		throws Exception {

		return new WorkflowTask();
	}

	@Consumes("application/json")
	@Override
	@Path("/workflow-tasks/{workflow-task-id}/update-due-date")
	@POST
	@Produces("application/json")
	public WorkflowTask postWorkflowTaskUpdateDueDate(
			@PathParam("workflow-task-id") Long workflowTaskId,
			WorkflowTask workflowTask)
		throws Exception {

		return new WorkflowTask();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected String getJAXRSLink(String methodName, Object... values) {
		String baseURIString = String.valueOf(contextUriInfo.getBaseUri());

		if (baseURIString.endsWith(StringPool.FORWARD_SLASH)) {
			baseURIString = baseURIString.substring(
				0, baseURIString.length() - 1);
		}

		URI resourceURI = UriBuilder.fromResource(
			BaseWorkflowTaskResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseWorkflowTaskResourceImpl.class, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	protected void preparePatch(WorkflowTask workflowTask) {
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(list, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transformToArray(list, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

	@Context
	protected UriInfo contextUriInfo;

}