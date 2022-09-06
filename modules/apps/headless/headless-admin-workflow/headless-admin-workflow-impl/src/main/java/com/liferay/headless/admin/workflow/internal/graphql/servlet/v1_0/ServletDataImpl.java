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

package com.liferay.headless.admin.workflow.internal.graphql.servlet.v1_0;

import com.liferay.headless.admin.workflow.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.admin.workflow.internal.graphql.query.v1_0.Query;
import com.liferay.headless.admin.workflow.internal.resource.v1_0.AssigneeResourceImpl;
import com.liferay.headless.admin.workflow.internal.resource.v1_0.TransitionResourceImpl;
import com.liferay.headless.admin.workflow.internal.resource.v1_0.WorkflowDefinitionResourceImpl;
import com.liferay.headless.admin.workflow.internal.resource.v1_0.WorkflowInstanceResourceImpl;
import com.liferay.headless.admin.workflow.internal.resource.v1_0.WorkflowLogResourceImpl;
import com.liferay.headless.admin.workflow.internal.resource.v1_0.WorkflowTaskAssignableUsersResourceImpl;
import com.liferay.headless.admin.workflow.internal.resource.v1_0.WorkflowTaskResourceImpl;
import com.liferay.headless.admin.workflow.internal.resource.v1_0.WorkflowTaskTransitionsResourceImpl;
import com.liferay.headless.admin.workflow.resource.v1_0.AssigneeResource;
import com.liferay.headless.admin.workflow.resource.v1_0.TransitionResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowInstanceResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowLogResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskAssignableUsersResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskTransitionsResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setWorkflowDefinitionResourceComponentServiceObjects(
			_workflowDefinitionResourceComponentServiceObjects);
		Mutation.setWorkflowInstanceResourceComponentServiceObjects(
			_workflowInstanceResourceComponentServiceObjects);
		Mutation.setWorkflowTaskResourceComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects);
		Mutation.setWorkflowTaskAssignableUsersResourceComponentServiceObjects(
			_workflowTaskAssignableUsersResourceComponentServiceObjects);
		Mutation.setWorkflowTaskTransitionsResourceComponentServiceObjects(
			_workflowTaskTransitionsResourceComponentServiceObjects);

		Query.setAssigneeResourceComponentServiceObjects(
			_assigneeResourceComponentServiceObjects);
		Query.setTransitionResourceComponentServiceObjects(
			_transitionResourceComponentServiceObjects);
		Query.setWorkflowDefinitionResourceComponentServiceObjects(
			_workflowDefinitionResourceComponentServiceObjects);
		Query.setWorkflowInstanceResourceComponentServiceObjects(
			_workflowInstanceResourceComponentServiceObjects);
		Query.setWorkflowLogResourceComponentServiceObjects(
			_workflowLogResourceComponentServiceObjects);
		Query.setWorkflowTaskResourceComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Admin.Workflow";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-admin-workflow-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#createWorkflowDefinition",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class,
				"postWorkflowDefinition"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowDefinitionBatch",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class,
				"postWorkflowDefinitionBatch"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowDefinitionDeploy",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class,
				"postWorkflowDefinitionDeploy"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowDefinitionSave",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class,
				"postWorkflowDefinitionSave"));
		_resourceMethodPairs.put(
			"mutation#deleteWorkflowDefinitionUndeploy",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class,
				"deleteWorkflowDefinitionUndeploy"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowDefinitionUpdateActive",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class,
				"postWorkflowDefinitionUpdateActive"));
		_resourceMethodPairs.put(
			"mutation#deleteWorkflowDefinition",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class,
				"deleteWorkflowDefinition"));
		_resourceMethodPairs.put(
			"mutation#deleteWorkflowDefinitionBatch",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class,
				"deleteWorkflowDefinitionBatch"));
		_resourceMethodPairs.put(
			"mutation#updateWorkflowDefinition",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class, "putWorkflowDefinition"));
		_resourceMethodPairs.put(
			"mutation#updateWorkflowDefinitionBatch",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class,
				"putWorkflowDefinitionBatch"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowInstanceSubmit",
			new ObjectValuePair<>(
				WorkflowInstanceResourceImpl.class,
				"postWorkflowInstanceSubmit"));
		_resourceMethodPairs.put(
			"mutation#deleteWorkflowInstance",
			new ObjectValuePair<>(
				WorkflowInstanceResourceImpl.class, "deleteWorkflowInstance"));
		_resourceMethodPairs.put(
			"mutation#deleteWorkflowInstanceBatch",
			new ObjectValuePair<>(
				WorkflowInstanceResourceImpl.class,
				"deleteWorkflowInstanceBatch"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowInstanceChangeTransition",
			new ObjectValuePair<>(
				WorkflowInstanceResourceImpl.class,
				"postWorkflowInstanceChangeTransition"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowTasksPage",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class, "postWorkflowTasksPage"));
		_resourceMethodPairs.put(
			"mutation#patchWorkflowTaskAssignToUser",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"patchWorkflowTaskAssignToUser"));
		_resourceMethodPairs.put(
			"mutation#patchWorkflowTaskChangeTransition",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"patchWorkflowTaskChangeTransition"));
		_resourceMethodPairs.put(
			"mutation#patchWorkflowTaskUpdateDueDate",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"patchWorkflowTaskUpdateDueDate"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowTaskAssignToMe",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class, "postWorkflowTaskAssignToMe"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowTaskAssignToRole",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"postWorkflowTaskAssignToRole"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowTaskAssignToUser",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"postWorkflowTaskAssignToUser"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowTaskChangeTransition",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"postWorkflowTaskChangeTransition"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowTaskUpdateDueDate",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"postWorkflowTaskUpdateDueDate"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowTaskAssignableUser",
			new ObjectValuePair<>(
				WorkflowTaskAssignableUsersResourceImpl.class,
				"postWorkflowTaskAssignableUser"));
		_resourceMethodPairs.put(
			"mutation#createWorkflowTaskTransition",
			new ObjectValuePair<>(
				WorkflowTaskTransitionsResourceImpl.class,
				"postWorkflowTaskTransition"));
		_resourceMethodPairs.put(
			"query#workflowTaskAssignableUsers",
			new ObjectValuePair<>(
				AssigneeResourceImpl.class,
				"getWorkflowTaskAssignableUsersPage"));
		_resourceMethodPairs.put(
			"query#workflowInstanceNextTransitions",
			new ObjectValuePair<>(
				TransitionResourceImpl.class,
				"getWorkflowInstanceNextTransitionsPage"));
		_resourceMethodPairs.put(
			"query#workflowTaskNextTransitions",
			new ObjectValuePair<>(
				TransitionResourceImpl.class,
				"getWorkflowTaskNextTransitionsPage"));
		_resourceMethodPairs.put(
			"query#workflowDefinitions",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class,
				"getWorkflowDefinitionsPage"));
		_resourceMethodPairs.put(
			"query#workflowDefinitionByName",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class,
				"getWorkflowDefinitionByName"));
		_resourceMethodPairs.put(
			"query#workflowDefinition",
			new ObjectValuePair<>(
				WorkflowDefinitionResourceImpl.class, "getWorkflowDefinition"));
		_resourceMethodPairs.put(
			"query#workflowInstances",
			new ObjectValuePair<>(
				WorkflowInstanceResourceImpl.class,
				"getWorkflowInstancesPage"));
		_resourceMethodPairs.put(
			"query#workflowInstance",
			new ObjectValuePair<>(
				WorkflowInstanceResourceImpl.class, "getWorkflowInstance"));
		_resourceMethodPairs.put(
			"query#workflowInstanceWorkflowLogs",
			new ObjectValuePair<>(
				WorkflowLogResourceImpl.class,
				"getWorkflowInstanceWorkflowLogsPage"));
		_resourceMethodPairs.put(
			"query#workflowLog",
			new ObjectValuePair<>(
				WorkflowLogResourceImpl.class, "getWorkflowLog"));
		_resourceMethodPairs.put(
			"query#workflowTaskWorkflowLogs",
			new ObjectValuePair<>(
				WorkflowLogResourceImpl.class,
				"getWorkflowTaskWorkflowLogsPage"));
		_resourceMethodPairs.put(
			"query#workflowInstanceWorkflowTasks",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"getWorkflowInstanceWorkflowTasksPage"));
		_resourceMethodPairs.put(
			"query#workflowInstanceWorkflowTasksAssignedToMe",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"getWorkflowInstanceWorkflowTasksAssignedToMePage"));
		_resourceMethodPairs.put(
			"query#workflowInstanceWorkflowTasksAssignedToUser",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"getWorkflowInstanceWorkflowTasksAssignedToUserPage"));
		_resourceMethodPairs.put(
			"query#workflowTasksAssignedToMe",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"getWorkflowTasksAssignedToMePage"));
		_resourceMethodPairs.put(
			"query#workflowTasksAssignedToMyRoles",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"getWorkflowTasksAssignedToMyRolesPage"));
		_resourceMethodPairs.put(
			"query#workflowTasksAssignedToRole",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"getWorkflowTasksAssignedToRolePage"));
		_resourceMethodPairs.put(
			"query#workflowTasksAssignedToUser",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"getWorkflowTasksAssignedToUserPage"));
		_resourceMethodPairs.put(
			"query#workflowTasksAssignedToUserRoles",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"getWorkflowTasksAssignedToUserRolesPage"));
		_resourceMethodPairs.put(
			"query#workflowTasksSubmittingUser",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"getWorkflowTasksSubmittingUserPage"));
		_resourceMethodPairs.put(
			"query#workflowTask",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class, "getWorkflowTask"));
		_resourceMethodPairs.put(
			"query#workflowTaskHasAssignableUsers",
			new ObjectValuePair<>(
				WorkflowTaskResourceImpl.class,
				"getWorkflowTaskHasAssignableUsers"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WorkflowDefinitionResource>
		_workflowDefinitionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WorkflowInstanceResource>
		_workflowInstanceResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WorkflowTaskResource>
		_workflowTaskResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WorkflowTaskAssignableUsersResource>
		_workflowTaskAssignableUsersResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WorkflowTaskTransitionsResource>
		_workflowTaskTransitionsResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AssigneeResource>
		_assigneeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TransitionResource>
		_transitionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WorkflowLogResource>
		_workflowLogResourceComponentServiceObjects;

}