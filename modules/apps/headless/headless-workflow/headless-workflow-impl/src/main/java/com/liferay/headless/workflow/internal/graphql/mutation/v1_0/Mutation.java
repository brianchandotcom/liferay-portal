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

package com.liferay.headless.workflow.internal.graphql.mutation.v1_0;

import com.liferay.headless.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.workflow.resource.v1_0.WorkflowTaskResource;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTask postWorkflowTaskAssignToMe(
	@GraphQLName("workflow-task-id") Long workflowTaskId,@GraphQLName("WorkflowTask") WorkflowTask workflowTask)
			throws Exception {

				return _getWorkflowTaskResource().postWorkflowTaskAssignToMe(
					workflowTaskId,workflowTask);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTask postWorkflowTaskAssignToUser(
	@GraphQLName("workflow-task-id") Long workflowTaskId,@GraphQLName("WorkflowTask") WorkflowTask workflowTask)
			throws Exception {

				return _getWorkflowTaskResource().postWorkflowTaskAssignToUser(
					workflowTaskId,workflowTask);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTask postWorkflowTaskChangeTransition(
	@GraphQLName("workflow-task-id") Long workflowTaskId,@GraphQLName("WorkflowTask") WorkflowTask workflowTask)
			throws Exception {

				return _getWorkflowTaskResource().postWorkflowTaskChangeTransition(
					workflowTaskId,workflowTask);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTask postWorkflowTaskUpdateDueDate(
	@GraphQLName("workflow-task-id") Long workflowTaskId,@GraphQLName("WorkflowTask") WorkflowTask workflowTask)
			throws Exception {

				return _getWorkflowTaskResource().postWorkflowTaskUpdateDueDate(
					workflowTaskId,workflowTask);
	}

	private static WorkflowTaskResource _getWorkflowTaskResource() {
			return _workflowTaskResourceServiceTracker.getService();
	}

	private static final ServiceTracker<WorkflowTaskResource, WorkflowTaskResource>
			_workflowTaskResourceServiceTracker;

		static {
			Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

				ServiceTracker<WorkflowTaskResource, WorkflowTaskResource>
					workflowTaskResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							WorkflowTaskResource.class, null);

				workflowTaskResourceServiceTracker.open();

				_workflowTaskResourceServiceTracker =
					workflowTaskResourceServiceTracker;
	}

}