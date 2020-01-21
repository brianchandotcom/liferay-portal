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

package com.liferay.headless.admin.workflow.internal.graphql.query.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.Assignee;
import com.liferay.headless.admin.workflow.dto.v1_0.Transition;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowLog;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignableUsers;
import com.liferay.headless.admin.workflow.resource.v1_0.AssigneeResource;
import com.liferay.headless.admin.workflow.resource.v1_0.TransitionResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowInstanceResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowLogResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskAssignableUsersResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Date;
import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	public static void setAssigneeResourceComponentServiceObjects(
		ComponentServiceObjects<AssigneeResource>
			assigneeResourceComponentServiceObjects) {

		_assigneeResourceComponentServiceObjects =
			assigneeResourceComponentServiceObjects;
	}

	public static void setTransitionResourceComponentServiceObjects(
		ComponentServiceObjects<TransitionResource>
			transitionResourceComponentServiceObjects) {

		_transitionResourceComponentServiceObjects =
			transitionResourceComponentServiceObjects;
	}

	public static void setWorkflowDefinitionResourceComponentServiceObjects(
		ComponentServiceObjects<WorkflowDefinitionResource>
			workflowDefinitionResourceComponentServiceObjects) {

		_workflowDefinitionResourceComponentServiceObjects =
			workflowDefinitionResourceComponentServiceObjects;
	}

	public static void setWorkflowInstanceResourceComponentServiceObjects(
		ComponentServiceObjects<WorkflowInstanceResource>
			workflowInstanceResourceComponentServiceObjects) {

		_workflowInstanceResourceComponentServiceObjects =
			workflowInstanceResourceComponentServiceObjects;
	}

	public static void setWorkflowLogResourceComponentServiceObjects(
		ComponentServiceObjects<WorkflowLogResource>
			workflowLogResourceComponentServiceObjects) {

		_workflowLogResourceComponentServiceObjects =
			workflowLogResourceComponentServiceObjects;
	}

	public static void setWorkflowTaskResourceComponentServiceObjects(
		ComponentServiceObjects<WorkflowTaskResource>
			workflowTaskResourceComponentServiceObjects) {

		_workflowTaskResourceComponentServiceObjects =
			workflowTaskResourceComponentServiceObjects;
	}

	public static void
		setWorkflowTaskAssignableUsersResourceComponentServiceObjects(
			ComponentServiceObjects<WorkflowTaskAssignableUsersResource>
				workflowTaskAssignableUsersResourceComponentServiceObjects) {

		_workflowTaskAssignableUsersResourceComponentServiceObjects =
			workflowTaskAssignableUsersResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTaskAssignableUsers(page: ___, pageSize: ___, workflowTaskId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AssigneePage workflowTaskAssignableUsers(
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_assigneeResourceComponentServiceObjects,
			this::_populateResourceContext,
			assigneeResource -> new AssigneePage(
				assigneeResource.getWorkflowTaskAssignableUsersPage(
					workflowTaskId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowInstanceNextTransitions(page: ___, pageSize: ___, workflowInstanceId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TransitionPage workflowInstanceNextTransitions(
			@GraphQLName("workflowInstanceId") Long workflowInstanceId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_transitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			transitionResource -> new TransitionPage(
				transitionResource.getWorkflowInstanceNextTransitionsPage(
					workflowInstanceId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTaskNextTransitions(page: ___, pageSize: ___, workflowTaskId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TransitionPage workflowTaskNextTransitions(
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_transitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			transitionResource -> new TransitionPage(
				transitionResource.getWorkflowTaskNextTransitionsPage(
					workflowTaskId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowDefinitions(page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowDefinitionPage workflowDefinitions(
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowDefinitionResource -> new WorkflowDefinitionPage(
				workflowDefinitionResource.getWorkflowDefinitionsPage(
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowDefinitionByName(name: ___){active, content, dateModified, description, name, title, version}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowDefinition workflowDefinitionByName(
			@GraphQLName("name") String name)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowDefinitionResource ->
				workflowDefinitionResource.getWorkflowDefinitionByName(name));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowInstances(assetClassNames: ___, assetPrimaryKeys: ___, completed: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowInstancePage workflowInstances(
			@GraphQLName("assetClassNames") String[] assetClassNames,
			@GraphQLName("assetPrimaryKeys") Long[] assetPrimaryKeys,
			@GraphQLName("completed") Boolean completed,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowInstanceResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowInstanceResource -> new WorkflowInstancePage(
				workflowInstanceResource.getWorkflowInstancesPage(
					assetClassNames, assetPrimaryKeys, completed,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowInstance(workflowInstanceId: ___){completed, dateCompletion, dateCreated, definitionName, definitionVersion, id, objectReviewed}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowInstance workflowInstance(
			@GraphQLName("workflowInstanceId") Long workflowInstanceId)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowInstanceResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowInstanceResource ->
				workflowInstanceResource.getWorkflowInstance(
					workflowInstanceId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowInstanceWorkflowLogs(page: ___, pageSize: ___, types: ___, workflowInstanceId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowLogPage workflowInstanceWorkflowLogs(
			@GraphQLName("workflowInstanceId") Long workflowInstanceId,
			@GraphQLName("types") String[] types,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowLogResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowLogResource -> new WorkflowLogPage(
				workflowLogResource.getWorkflowInstanceWorkflowLogsPage(
					workflowInstanceId, types, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowLog(workflowLogId: ___){auditPerson, commentLog, dateCreated, id, person, previousPerson, previousRole, previousState, role, state, type, workflowTaskId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowLog workflowLog(
			@GraphQLName("workflowLogId") Long workflowLogId)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowLogResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowLogResource -> workflowLogResource.getWorkflowLog(
				workflowLogId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTaskWorkflowLogs(page: ___, pageSize: ___, types: ___, workflowTaskId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowLogPage workflowTaskWorkflowLogs(
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("types") String[] types,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowLogResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowLogResource -> new WorkflowLogPage(
				workflowLogResource.getWorkflowTaskWorkflowLogsPage(
					workflowTaskId, types, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowInstanceWorkflowTasks(completed: ___, page: ___, pageSize: ___, workflowInstanceId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTaskPage workflowInstanceWorkflowTasks(
			@GraphQLName("workflowInstanceId") Long workflowInstanceId,
			@GraphQLName("completed") Boolean completed,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource -> new WorkflowTaskPage(
				workflowTaskResource.getWorkflowInstanceWorkflowTasksPage(
					workflowInstanceId, completed,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowInstanceWorkflowTasksAssignedToMe(completed: ___, page: ___, pageSize: ___, workflowInstanceId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTaskPage workflowInstanceWorkflowTasksAssignedToMe(
			@GraphQLName("workflowInstanceId") Long workflowInstanceId,
			@GraphQLName("completed") Boolean completed,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource -> new WorkflowTaskPage(
				workflowTaskResource.
					getWorkflowInstanceWorkflowTasksAssignedToMePage(
						workflowInstanceId, completed,
						Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowInstanceWorkflowTasksAssignedToUser(assigneeId: ___, completed: ___, page: ___, pageSize: ___, workflowInstanceId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTaskPage workflowInstanceWorkflowTasksAssignedToUser(
			@GraphQLName("workflowInstanceId") Long workflowInstanceId,
			@GraphQLName("assigneeId") Long assigneeId,
			@GraphQLName("completed") Boolean completed,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource -> new WorkflowTaskPage(
				workflowTaskResource.
					getWorkflowInstanceWorkflowTasksAssignedToUserPage(
						workflowInstanceId, assigneeId, completed,
						Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTasks(andOperator: ___, assetPrimaryKeys: ___, assetTitle: ___, assetTypes: ___, assigneeUserIds: ___, completed: ___, dateDueEnd: ___, dateDueStart: ___, page: ___, pageSize: ___, searchByUserRoles: ___, sorts: ___, taskNames: ___, workflowInstanceIds: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTaskPage workflowTasks(
			@GraphQLName("andOperator") Boolean andOperator,
			@GraphQLName("assetPrimaryKeys") Long[] assetPrimaryKeys,
			@GraphQLName("assetTitle") String assetTitle,
			@GraphQLName("assetTypes") String[] assetTypes,
			@GraphQLName("assigneeUserIds") Long[] assigneeUserIds,
			@GraphQLName("completed") Boolean completed,
			@GraphQLName("dateDueEnd") Date dateDueEnd,
			@GraphQLName("dateDueStart") Date dateDueStart,
			@GraphQLName("searchByUserRoles") Boolean searchByUserRoles,
			@GraphQLName("taskNames") String[] taskNames,
			@GraphQLName("workflowInstanceIds") Long[] workflowInstanceIds,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource -> new WorkflowTaskPage(
				workflowTaskResource.getWorkflowTasksPage(
					andOperator, assetPrimaryKeys, assetTitle, assetTypes,
					assigneeUserIds, completed, dateDueEnd, dateDueStart,
					searchByUserRoles, taskNames, workflowInstanceIds,
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						workflowTaskResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTasksAssignedToMe(page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTaskPage workflowTasksAssignedToMe(
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource -> new WorkflowTaskPage(
				workflowTaskResource.getWorkflowTasksAssignedToMePage(
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTasksAssignedToMyRoles(page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTaskPage workflowTasksAssignedToMyRoles(
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource -> new WorkflowTaskPage(
				workflowTaskResource.getWorkflowTasksAssignedToMyRolesPage(
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTasksAssignedToRole(page: ___, pageSize: ___, roleId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTaskPage workflowTasksAssignedToRole(
			@GraphQLName("roleId") Long roleId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource -> new WorkflowTaskPage(
				workflowTaskResource.getWorkflowTasksAssignedToRolePage(
					roleId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTasksAssignedToUser(assigneeId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTaskPage workflowTasksAssignedToUser(
			@GraphQLName("assigneeId") Long assigneeId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource -> new WorkflowTaskPage(
				workflowTaskResource.getWorkflowTasksAssignedToUserPage(
					assigneeId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTasksAssignedToUserRoles(assigneeId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTaskPage workflowTasksAssignedToUserRoles(
			@GraphQLName("assigneeId") Long assigneeId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource -> new WorkflowTaskPage(
				workflowTaskResource.getWorkflowTasksAssignedToUserRolesPage(
					assigneeId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTasksSubmittingUser(creatorId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTaskPage workflowTasksSubmittingUser(
			@GraphQLName("creatorId") Long creatorId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource -> new WorkflowTaskPage(
				workflowTaskResource.getWorkflowTasksSubmittingUserPage(
					creatorId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTask(workflowTaskId: ___){assigneePerson, assigneeRoles, completed, dateCompletion, dateCreated, dateDue, definitionId, definitionName, definitionVersion, description, id, name, objectReviewed, workflowInstanceId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTask workflowTask(
			@GraphQLName("workflowTaskId") Long workflowTaskId)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource -> workflowTaskResource.getWorkflowTask(
				workflowTaskId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTaskHasAssignableUsers(workflowTaskId: ___){}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public String workflowTaskHasAssignableUsers(
			@GraphQLName("workflowTaskId") Long workflowTaskId)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource ->
				workflowTaskResource.getWorkflowTaskHasAssignableUsers(
					workflowTaskId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTaskAssignableUser(workflowTaskIds: ___){workflowTaskAssignableUsers}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTaskAssignableUsers workflowTaskAssignableUser(
			@GraphQLName("workflowTaskIds") Long[] workflowTaskIds)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskAssignableUsersResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskAssignableUsersResource ->
				workflowTaskAssignableUsersResource.
					getWorkflowTaskAssignableUser(workflowTaskIds));
	}

	@GraphQLTypeExtension(WorkflowInstance.class)
	public class GetWorkflowInstanceWorkflowTasksPageTypeExtension {

		public GetWorkflowInstanceWorkflowTasksPageTypeExtension(
			WorkflowInstance workflowInstance) {

			_workflowInstance = workflowInstance;
		}

		@GraphQLField
		public WorkflowTaskPage workflowTasks(
				@GraphQLName("completed") Boolean completed,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_workflowTaskResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				workflowTaskResource -> new WorkflowTaskPage(
					workflowTaskResource.getWorkflowInstanceWorkflowTasksPage(
						_workflowInstance.getId(), completed,
						Pagination.of(page, pageSize))));
		}

		private WorkflowInstance _workflowInstance;

	}

	@GraphQLTypeExtension(WorkflowInstance.class)
	public class GetWorkflowInstanceWorkflowLogsPageTypeExtension {

		public GetWorkflowInstanceWorkflowLogsPageTypeExtension(
			WorkflowInstance workflowInstance) {

			_workflowInstance = workflowInstance;
		}

		@GraphQLField
		public WorkflowLogPage workflowLogs(
				@GraphQLName("types") String[] types,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_workflowLogResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				workflowLogResource -> new WorkflowLogPage(
					workflowLogResource.getWorkflowInstanceWorkflowLogsPage(
						_workflowInstance.getId(), types,
						Pagination.of(page, pageSize))));
		}

		private WorkflowInstance _workflowInstance;

	}

	@GraphQLTypeExtension(WorkflowTask.class)
	public class GetWorkflowTaskHasAssignableUsersTypeExtension {

		public GetWorkflowTaskHasAssignableUsersTypeExtension(
			WorkflowTask workflowTask) {

			_workflowTask = workflowTask;
		}

		@GraphQLField
		public String hasAssignableUsers() throws Exception {
			return _applyComponentServiceObjects(
				_workflowTaskResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				workflowTaskResource ->
					workflowTaskResource.getWorkflowTaskHasAssignableUsers(
						_workflowTask.getId()));
		}

		private WorkflowTask _workflowTask;

	}

	@GraphQLTypeExtension(WorkflowInstance.class)
	public class GetWorkflowInstanceWorkflowTasksAssignedToMePageTypeExtension {

		public GetWorkflowInstanceWorkflowTasksAssignedToMePageTypeExtension(
			WorkflowInstance workflowInstance) {

			_workflowInstance = workflowInstance;
		}

		@GraphQLField
		public WorkflowTaskPage workflowTasksAssignedToMe(
				@GraphQLName("completed") Boolean completed,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_workflowTaskResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				workflowTaskResource -> new WorkflowTaskPage(
					workflowTaskResource.
						getWorkflowInstanceWorkflowTasksAssignedToMePage(
							_workflowInstance.getId(), completed,
							Pagination.of(page, pageSize))));
		}

		private WorkflowInstance _workflowInstance;

	}

	@GraphQLTypeExtension(WorkflowTask.class)
	public class GetWorkflowInstanceTypeExtension {

		public GetWorkflowInstanceTypeExtension(WorkflowTask workflowTask) {
			_workflowTask = workflowTask;
		}

		@GraphQLField
		public WorkflowInstance workflowInstance() throws Exception {
			return _applyComponentServiceObjects(
				_workflowInstanceResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				workflowInstanceResource ->
					workflowInstanceResource.getWorkflowInstance(
						_workflowTask.getWorkflowInstanceId()));
		}

		private WorkflowTask _workflowTask;

	}

	@GraphQLTypeExtension(WorkflowTaskAssignToUser.class)
	public class GetWorkflowTaskTypeExtension {

		public GetWorkflowTaskTypeExtension(
			WorkflowTaskAssignToUser workflowTaskAssignToUser) {

			_workflowTaskAssignToUser = workflowTaskAssignToUser;
		}

		@GraphQLField
		public WorkflowTask workflowTask() throws Exception {
			return _applyComponentServiceObjects(
				_workflowTaskResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				workflowTaskResource -> workflowTaskResource.getWorkflowTask(
					_workflowTaskAssignToUser.getWorkflowTaskId()));
		}

		private WorkflowTaskAssignToUser _workflowTaskAssignToUser;

	}

	@GraphQLTypeExtension(WorkflowTask.class)
	public class GetWorkflowTaskAssignableUsersPageTypeExtension {

		public GetWorkflowTaskAssignableUsersPageTypeExtension(
			WorkflowTask workflowTask) {

			_workflowTask = workflowTask;
		}

		@GraphQLField
		public AssigneePage assignableUsers(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_assigneeResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				assigneeResource -> new AssigneePage(
					assigneeResource.getWorkflowTaskAssignableUsersPage(
						_workflowTask.getId(), Pagination.of(page, pageSize))));
		}

		private WorkflowTask _workflowTask;

	}

	@GraphQLTypeExtension(WorkflowTask.class)
	public class GetWorkflowTaskNextTransitionsPageTypeExtension {

		public GetWorkflowTaskNextTransitionsPageTypeExtension(
			WorkflowTask workflowTask) {

			_workflowTask = workflowTask;
		}

		@GraphQLField
		public TransitionPage nextTransitions(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_transitionResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				transitionResource -> new TransitionPage(
					transitionResource.getWorkflowTaskNextTransitionsPage(
						_workflowTask.getId(), Pagination.of(page, pageSize))));
		}

		private WorkflowTask _workflowTask;

	}

	@GraphQLTypeExtension(WorkflowInstance.class)
	public class
		GetWorkflowInstanceWorkflowTasksAssignedToUserPageTypeExtension {

		public GetWorkflowInstanceWorkflowTasksAssignedToUserPageTypeExtension(
			WorkflowInstance workflowInstance) {

			_workflowInstance = workflowInstance;
		}

		@GraphQLField
		public WorkflowTaskPage workflowTasksAssignedToUser(
				@GraphQLName("assigneeId") Long assigneeId,
				@GraphQLName("completed") Boolean completed,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_workflowTaskResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				workflowTaskResource -> new WorkflowTaskPage(
					workflowTaskResource.
						getWorkflowInstanceWorkflowTasksAssignedToUserPage(
							_workflowInstance.getId(), assigneeId, completed,
							Pagination.of(page, pageSize))));
		}

		private WorkflowInstance _workflowInstance;

	}

	@GraphQLTypeExtension(WorkflowInstance.class)
	public class GetWorkflowInstanceNextTransitionsPageTypeExtension {

		public GetWorkflowInstanceNextTransitionsPageTypeExtension(
			WorkflowInstance workflowInstance) {

			_workflowInstance = workflowInstance;
		}

		@GraphQLField
		public TransitionPage nextTransitions(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_transitionResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				transitionResource -> new TransitionPage(
					transitionResource.getWorkflowInstanceNextTransitionsPage(
						_workflowInstance.getId(),
						Pagination.of(page, pageSize))));
		}

		private WorkflowInstance _workflowInstance;

	}

	@GraphQLTypeExtension(WorkflowTask.class)
	public class GetWorkflowTaskWorkflowLogsPageTypeExtension {

		public GetWorkflowTaskWorkflowLogsPageTypeExtension(
			WorkflowTask workflowTask) {

			_workflowTask = workflowTask;
		}

		@GraphQLField
		public WorkflowLogPage workflowLogs(
				@GraphQLName("types") String[] types,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_workflowLogResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				workflowLogResource -> new WorkflowLogPage(
					workflowLogResource.getWorkflowTaskWorkflowLogsPage(
						_workflowTask.getId(), types,
						Pagination.of(page, pageSize))));
		}

		private WorkflowTask _workflowTask;

	}

	@GraphQLName("AssigneePage")
	public class AssigneePage {

		public AssigneePage(Page assigneePage) {
			actions = assigneePage.getActions();
			items = assigneePage.getItems();
			lastPage = assigneePage.getLastPage();
			page = assigneePage.getPage();
			pageSize = assigneePage.getPageSize();
			totalCount = assigneePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Assignee> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("TransitionPage")
	public class TransitionPage {

		public TransitionPage(Page transitionPage) {
			actions = transitionPage.getActions();
			items = transitionPage.getItems();
			lastPage = transitionPage.getLastPage();
			page = transitionPage.getPage();
			pageSize = transitionPage.getPageSize();
			totalCount = transitionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Transition> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WorkflowDefinitionPage")
	public class WorkflowDefinitionPage {

		public WorkflowDefinitionPage(Page workflowDefinitionPage) {
			actions = workflowDefinitionPage.getActions();
			items = workflowDefinitionPage.getItems();
			lastPage = workflowDefinitionPage.getLastPage();
			page = workflowDefinitionPage.getPage();
			pageSize = workflowDefinitionPage.getPageSize();
			totalCount = workflowDefinitionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<WorkflowDefinition> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WorkflowInstancePage")
	public class WorkflowInstancePage {

		public WorkflowInstancePage(Page workflowInstancePage) {
			actions = workflowInstancePage.getActions();
			items = workflowInstancePage.getItems();
			lastPage = workflowInstancePage.getLastPage();
			page = workflowInstancePage.getPage();
			pageSize = workflowInstancePage.getPageSize();
			totalCount = workflowInstancePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<WorkflowInstance> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WorkflowLogPage")
	public class WorkflowLogPage {

		public WorkflowLogPage(Page workflowLogPage) {
			actions = workflowLogPage.getActions();
			items = workflowLogPage.getItems();
			lastPage = workflowLogPage.getLastPage();
			page = workflowLogPage.getPage();
			pageSize = workflowLogPage.getPageSize();
			totalCount = workflowLogPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<WorkflowLog> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WorkflowTaskPage")
	public class WorkflowTaskPage {

		public WorkflowTaskPage(Page workflowTaskPage) {
			actions = workflowTaskPage.getActions();
			items = workflowTaskPage.getItems();
			lastPage = workflowTaskPage.getLastPage();
			page = workflowTaskPage.getPage();
			pageSize = workflowTaskPage.getPageSize();
			totalCount = workflowTaskPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<WorkflowTask> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WorkflowTaskAssignableUsersPage")
	public class WorkflowTaskAssignableUsersPage {

		public WorkflowTaskAssignableUsersPage(
			Page workflowTaskAssignableUsersPage) {

			actions = workflowTaskAssignableUsersPage.getActions();
			items = workflowTaskAssignableUsersPage.getItems();
			lastPage = workflowTaskAssignableUsersPage.getLastPage();
			page = workflowTaskAssignableUsersPage.getPage();
			pageSize = workflowTaskAssignableUsersPage.getPageSize();
			totalCount = workflowTaskAssignableUsersPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<WorkflowTaskAssignableUsers> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(AssigneeResource assigneeResource)
		throws Exception {

		assigneeResource.setContextAcceptLanguage(_acceptLanguage);
		assigneeResource.setContextCompany(_company);
		assigneeResource.setContextHttpServletRequest(_httpServletRequest);
		assigneeResource.setContextHttpServletResponse(_httpServletResponse);
		assigneeResource.setContextUriInfo(_uriInfo);
		assigneeResource.setContextUser(_user);
	}

	private void _populateResourceContext(TransitionResource transitionResource)
		throws Exception {

		transitionResource.setContextAcceptLanguage(_acceptLanguage);
		transitionResource.setContextCompany(_company);
		transitionResource.setContextHttpServletRequest(_httpServletRequest);
		transitionResource.setContextHttpServletResponse(_httpServletResponse);
		transitionResource.setContextUriInfo(_uriInfo);
		transitionResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			WorkflowDefinitionResource workflowDefinitionResource)
		throws Exception {

		workflowDefinitionResource.setContextAcceptLanguage(_acceptLanguage);
		workflowDefinitionResource.setContextCompany(_company);
		workflowDefinitionResource.setContextHttpServletRequest(
			_httpServletRequest);
		workflowDefinitionResource.setContextHttpServletResponse(
			_httpServletResponse);
		workflowDefinitionResource.setContextUriInfo(_uriInfo);
		workflowDefinitionResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			WorkflowInstanceResource workflowInstanceResource)
		throws Exception {

		workflowInstanceResource.setContextAcceptLanguage(_acceptLanguage);
		workflowInstanceResource.setContextCompany(_company);
		workflowInstanceResource.setContextHttpServletRequest(
			_httpServletRequest);
		workflowInstanceResource.setContextHttpServletResponse(
			_httpServletResponse);
		workflowInstanceResource.setContextUriInfo(_uriInfo);
		workflowInstanceResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			WorkflowLogResource workflowLogResource)
		throws Exception {

		workflowLogResource.setContextAcceptLanguage(_acceptLanguage);
		workflowLogResource.setContextCompany(_company);
		workflowLogResource.setContextHttpServletRequest(_httpServletRequest);
		workflowLogResource.setContextHttpServletResponse(_httpServletResponse);
		workflowLogResource.setContextUriInfo(_uriInfo);
		workflowLogResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			WorkflowTaskResource workflowTaskResource)
		throws Exception {

		workflowTaskResource.setContextAcceptLanguage(_acceptLanguage);
		workflowTaskResource.setContextCompany(_company);
		workflowTaskResource.setContextHttpServletRequest(_httpServletRequest);
		workflowTaskResource.setContextHttpServletResponse(
			_httpServletResponse);
		workflowTaskResource.setContextUriInfo(_uriInfo);
		workflowTaskResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			WorkflowTaskAssignableUsersResource
				workflowTaskAssignableUsersResource)
		throws Exception {

		workflowTaskAssignableUsersResource.setContextAcceptLanguage(
			_acceptLanguage);
		workflowTaskAssignableUsersResource.setContextCompany(_company);
		workflowTaskAssignableUsersResource.setContextHttpServletRequest(
			_httpServletRequest);
		workflowTaskAssignableUsersResource.setContextHttpServletResponse(
			_httpServletResponse);
		workflowTaskAssignableUsersResource.setContextUriInfo(_uriInfo);
		workflowTaskAssignableUsersResource.setContextUser(_user);
	}

	private static ComponentServiceObjects<AssigneeResource>
		_assigneeResourceComponentServiceObjects;
	private static ComponentServiceObjects<TransitionResource>
		_transitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<WorkflowDefinitionResource>
		_workflowDefinitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<WorkflowInstanceResource>
		_workflowInstanceResourceComponentServiceObjects;
	private static ComponentServiceObjects<WorkflowLogResource>
		_workflowLogResourceComponentServiceObjects;
	private static ComponentServiceObjects<WorkflowTaskResource>
		_workflowTaskResourceComponentServiceObjects;
	private static ComponentServiceObjects<WorkflowTaskAssignableUsersResource>
		_workflowTaskAssignableUsersResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private com.liferay.portal.kernel.model.Company _company;
	private com.liferay.portal.kernel.model.User _user;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private UriInfo _uriInfo;

}