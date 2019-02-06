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

package com.liferay.headless.workflow.resource;

import com.liferay.headless.workflow.dto.WorkflowLog;
import com.liferay.headless.workflow.dto.WorkflowTask;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.Date;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-workflow/1.0.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/1.0.0")
public interface WorkflowTaskResource {

	@GET
	@Path("/roles/{roles-id}/workflow-tasks")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<WorkflowTask> getRolesWorkflowTasksPage( @PathParam("roles-id") Long rolesId , @Context Pagination pagination ) throws Exception;

	@GET
	@Path("/workflow-tasks")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<WorkflowTask> getWorkflowTasksPage( @PathParam("generic-parent-id") Object genericParentId , @Context Pagination pagination ) throws Exception;

	@GET
	@Path("/workflow-tasks/{workflow-tasks-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public WorkflowTask getWorkflowTask( @PathParam("workflow-tasks-id") Long workflowTasksId ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/workflow-tasks/{workflow-tasks-id}/assign-to-me")
	@Produces("application/json")
	@RequiresScope("everything.write")
	public WorkflowTask postWorkflowTasksAssignToMe( @PathParam("workflow-tasks-id") Long workflowTasksId ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/workflow-tasks/{workflow-tasks-id}/assign-to-user")
	@Produces("application/json")
	@RequiresScope("everything.write")
	public WorkflowTask postWorkflowTasksAssignToUser( @PathParam("workflow-tasks-id") Long workflowTasksId ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/workflow-tasks/{workflow-tasks-id}/change-transition")
	@Produces("application/json")
	@RequiresScope("everything.write")
	public WorkflowTask postWorkflowTasksChangeTransition( @PathParam("workflow-tasks-id") Long workflowTasksId ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/workflow-tasks/{workflow-tasks-id}/update-due-date")
	@Produces("application/json")
	@RequiresScope("everything.write")
	public WorkflowTask postWorkflowTasksUpdateDueDate( @PathParam("workflow-tasks-id") Long workflowTasksId ) throws Exception;

}