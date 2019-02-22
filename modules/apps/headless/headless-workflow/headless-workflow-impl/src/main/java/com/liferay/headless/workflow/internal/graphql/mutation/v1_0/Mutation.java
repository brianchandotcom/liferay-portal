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

import com.liferay.headless.workflow.internal.dto.v1_0.WorkflowTaskImpl;
import com.liferay.headless.workflow.internal.resource.v1_0.WorkflowTaskResourceImpl;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTaskImpl postWorkflowTaskAssignToMe(
			@GraphQLName("workflow-task-id") Long workflowTaskId,
			@GraphQLName("WorkflowTask") WorkflowTaskImpl workflowTaskImpl)
		throws Exception {

		return (WorkflowTaskImpl)
			_getWorkflowTaskResourceImpl().postWorkflowTaskAssignToMe(
				workflowTaskId, workflowTaskImpl);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTaskImpl postWorkflowTaskAssignToUser(
			@GraphQLName("workflow-task-id") Long workflowTaskId,
			@GraphQLName("WorkflowTask") WorkflowTaskImpl workflowTaskImpl)
		throws Exception {

		return (WorkflowTaskImpl)
			_getWorkflowTaskResourceImpl().postWorkflowTaskAssignToUser(
				workflowTaskId, workflowTaskImpl);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTaskImpl postWorkflowTaskChangeTransition(
			@GraphQLName("workflow-task-id") Long workflowTaskId,
			@GraphQLName("WorkflowTask") WorkflowTaskImpl workflowTaskImpl)
		throws Exception {

		return (WorkflowTaskImpl)
			_getWorkflowTaskResourceImpl().postWorkflowTaskChangeTransition(
				workflowTaskId, workflowTaskImpl);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTaskImpl postWorkflowTaskUpdateDueDate(
			@GraphQLName("workflow-task-id") Long workflowTaskId,
			@GraphQLName("WorkflowTask") WorkflowTaskImpl workflowTaskImpl)
		throws Exception {

		return (WorkflowTaskImpl)
			_getWorkflowTaskResourceImpl().postWorkflowTaskUpdateDueDate(
				workflowTaskId, workflowTaskImpl);
	}

	private static WorkflowTaskResourceImpl _getWorkflowTaskResourceImpl() {
		return new WorkflowTaskResourceImpl();
	}

}