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

package com.liferay.headless.workflow.internal.resource;

import com.liferay.headless.workflow.dto.WorkflowTask;
import com.liferay.headless.workflow.dto.WorkflowTaskCollection;
import com.liferay.headless.workflow.resource.WorkflowTaskResource;
import com.liferay.portal.vulcan.context.Pagination;

import java.util.Collections;

import javax.annotation.Generated;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-workflow-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE, service = WorkflowTaskResource.class
)
@Generated("")
public class WorkflowTaskResourceImpl implements WorkflowTaskResource {

	@Override
	public WorkflowTaskCollection<WorkflowTask> getRolesWorkflowTasks(
			String parentId, Pagination pagination)
		throws Exception {

		return new WorkflowTaskCollection(Collections.emptyList(), 0);
	}

	@Override
	public WorkflowTask getWorkflowTasks(Integer id) throws Exception {
		return new WorkflowTask();
	}

	@Override
	public WorkflowTaskCollection<WorkflowTask> getWorkflowTasks(
			Object genericparentid, Pagination pagination)
		throws Exception {

		return new WorkflowTaskCollection(Collections.emptyList(), 0);
	}

	@Override
	public WorkflowTask postWorkflowTasksAssignToMe(Integer id)
		throws Exception {

		return new WorkflowTask();
	}

	@Override
	public WorkflowTask postWorkflowTasksAssignToUser(Integer id)
		throws Exception {

		return new WorkflowTask();
	}

	@Override
	public WorkflowTask postWorkflowTasksChangeTransition(Integer id)
		throws Exception {

		return new WorkflowTask();
	}

	@Override
	public WorkflowTask postWorkflowTasksUpdateDueDate(Integer id)
		throws Exception {

		return new WorkflowTask();
	}

}