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

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.ChangeTransition;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseWorkflowTaskResourceImpl
	implements WorkflowTaskResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "roleId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/roles/{roleId}/workflow-tasks")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getRoleWorkflowTasksPage(
			@NotNull @Parameter(hidden = true) @PathParam("roleId") Long roleId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/workflow-tasks/assigned-to-me")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getWorkflowTasksAssignedToMePage(
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/workflow-tasks/assigned-to-my-roles")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getWorkflowTasksAssignedToMyRolesPage(
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@GET
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "workflowTaskId")}
	)
	@Path("/workflow-tasks/{workflowTaskId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask getWorkflowTask(
			@NotNull @Parameter(hidden = true) @PathParam("workflowTaskId") Long
				workflowTaskId)
		throws Exception {

		return new WorkflowTask();
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "workflowTaskId")}
	)
	@Path("/workflow-tasks/{workflowTaskId}/assign-to-me")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskAssignToMe(
			@NotNull @Parameter(hidden = true) @PathParam("workflowTaskId") Long
				workflowTaskId,
			WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		return new WorkflowTask();
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "workflowTaskId")}
	)
	@Path("/workflow-tasks/{workflowTaskId}/assign-to-user")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskAssignToUser(
			@NotNull @Parameter(hidden = true) @PathParam("workflowTaskId") Long
				workflowTaskId,
			WorkflowTaskAssignToUser workflowTaskAssignToUser)
		throws Exception {

		return new WorkflowTask();
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "workflowTaskId")}
	)
	@Path("/workflow-tasks/{workflowTaskId}/change-transition")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskChangeTransition(
			@NotNull @Parameter(hidden = true) @PathParam("workflowTaskId") Long
				workflowTaskId,
			ChangeTransition changeTransition)
		throws Exception {

		return new WorkflowTask();
	}

	@Override
	@Consumes({"application/json", "application/xml"})
	@POST
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "workflowTaskId")}
	)
	@Path("/workflow-tasks/{workflowTaskId}/update-due-date")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskUpdateDueDate(
			@NotNull @Parameter(hidden = true) @PathParam("workflowTaskId") Long
				workflowTaskId,
			WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		return new WorkflowTask();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(
		WorkflowTask workflowTask, WorkflowTask existingWorkflowTask) {
	}

	protected <T, R> List<R> transform(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
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