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

package com.liferay.headless.workflow.resource.v1_0;

import com.liferay.headless.workflow.dto.v1_0.ChangeTransition;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

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
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-workflow/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface WorkflowTaskResource {

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/roles/{roleId}/workflow-tasks")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getRoleWorkflowTasksPage(
			@NotNull @PathParam("roleId") Long roleId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/workflow-tasks/assigned-to-me")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getWorkflowTasksAssignedToMePage(
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/workflow-tasks/assigned-to-my-roles")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getWorkflowTasksAssignedToMyRolesPage(
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/workflow-tasks/{workflowTaskId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask getWorkflowTask(
			@NotNull @PathParam("workflowTaskId") Long workflowTaskId)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/workflow-tasks/{workflowTaskId}/assign-to-me")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskAssignToMe(
			@NotNull @PathParam("workflowTaskId") Long workflowTaskId,
			WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/workflow-tasks/{workflowTaskId}/assign-to-user")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskAssignToUser(
			@NotNull @PathParam("workflowTaskId") Long workflowTaskId,
			WorkflowTaskAssignToUser workflowTaskAssignToUser)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/workflow-tasks/{workflowTaskId}/change-transition")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskChangeTransition(
			@NotNull @PathParam("workflowTaskId") Long workflowTaskId,
			ChangeTransition changeTransition)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/workflow-tasks/{workflowTaskId}/update-due-date")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskUpdateDueDate(
			@NotNull @PathParam("workflowTaskId") Long workflowTaskId,
			WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception;

	@Context
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	@Context
	public void setContextCompany(Company contextCompany);

	@Context
	public void setContextUriInfo(UriInfo contextUriInfo);

}