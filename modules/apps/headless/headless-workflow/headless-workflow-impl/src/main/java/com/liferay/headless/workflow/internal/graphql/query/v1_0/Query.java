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

package com.liferay.headless.workflow.internal.graphql.query.v1_0;

import com.liferay.headless.workflow.dto.v1_0.WorkflowLog;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.workflow.resource.v1_0.WorkflowLogResource;
import com.liferay.headless.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowLog getWorkflowLog( @GraphQLName("workflow-log-id") Long workflowLogId ) throws Exception {
return _getWorkflowLogResource().getWorkflowLog( workflowLogId );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WorkflowLog> getWorkflowTaskWorkflowLogsPage( @GraphQLName("workflow-task-id") Long workflowTaskId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getWorkflowLogResource().getWorkflowTaskWorkflowLogsPage(

					workflowTaskId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WorkflowTask> getRoleWorkflowTasksPage( @GraphQLName("role-id") Long roleId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getWorkflowTaskResource().getRoleWorkflowTasksPage(

					roleId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WorkflowTask> getWorkflowTasksPage( @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getWorkflowTaskResource().getWorkflowTasksPage(

					Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTask getWorkflowTask( @GraphQLName("workflow-task-id") Long workflowTaskId ) throws Exception {
return _getWorkflowTaskResource().getWorkflowTask( workflowTaskId );
	}

	private static WorkflowLogResource _getWorkflowLogResource() {
			return _workflowLogResourceServiceTracker.getService();
	}

	private static final ServiceTracker<WorkflowLogResource, WorkflowLogResource> _workflowLogResourceServiceTracker;
	private static WorkflowTaskResource _getWorkflowTaskResource() {
			return _workflowTaskResourceServiceTracker.getService();
	}

	private static final ServiceTracker<WorkflowTaskResource, WorkflowTaskResource> _workflowTaskResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Query.class);

			ServiceTracker<WorkflowLogResource, WorkflowLogResource> workflowLogResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), WorkflowLogResource.class, null);

			workflowLogResourceServiceTracker.open();

			_workflowLogResourceServiceTracker = workflowLogResourceServiceTracker;
			ServiceTracker<WorkflowTaskResource, WorkflowTaskResource> workflowTaskResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), WorkflowTaskResource.class, null);

			workflowTaskResourceServiceTracker.open();

			_workflowTaskResourceServiceTracker = workflowTaskResourceServiceTracker;
	}

}