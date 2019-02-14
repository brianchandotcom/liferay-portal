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

import com.liferay.headless.workflow.dto.v1_0.WorkflowLog;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

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
public interface WorkflowLogResource {

	@GET
	@Path("/workflow-logs/{workflow-log-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public WorkflowLog getWorkflowLog( @PathParam("workflow-log-id") Long workflowLogId ) throws Exception;

	@GET
	@Path("/workflow-tasks/{workflow-task-id}/workflow-logs")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<WorkflowLog> getWorkflowTaskWorkflowLogsPage( @PathParam("workflow-task-id") Long workflowTaskId , @Context Pagination pagination ) throws Exception;

}