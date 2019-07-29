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

import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowLog;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowLogResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowLog(workflowLogId: ___){auditPerson, commentLog, dateCreated, id, person, previousPerson, previousState, state, taskId, type}}"}' -u 'test@liferay.com:test'
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTaskWorkflowLogs(page: ___, pageSize: ___, workflowTaskId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowLogPage workflowTaskWorkflowLogs(
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowLogResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowLogResource -> new WorkflowLogPage(
				workflowLogResource.getWorkflowTaskWorkflowLogsPage(
					workflowTaskId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {roleWorkflowTasks(page: ___, pageSize: ___, roleId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WorkflowTaskPage roleWorkflowTasks(
			@GraphQLName("roleId") Long roleId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource -> new WorkflowTaskPage(
				workflowTaskResource.getRoleWorkflowTasksPage(
					roleId, Pagination.of(page, pageSize))));
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workflowTask(workflowTaskId: ___){completed, dateCompleted, dateCreated, definitionName, description, dueDate, id, name, objectReviewed, transitions}}"}' -u 'test@liferay.com:test'
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

	@GraphQLTypeExtension(WorkflowTask.class)
	public class GetWorkflowTaskWorkflowLogsPageTypeExtension {

		public GetWorkflowTaskWorkflowLogsPageTypeExtension(
			WorkflowTask workflowTask) {

			_workflowTask = workflowTask;
		}

		@GraphQLField
		public WorkflowLogPage workflowLogs(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_workflowLogResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				workflowLogResource -> new WorkflowLogPage(
					workflowLogResource.getWorkflowTaskWorkflowLogsPage(
						_workflowTask.getId(), Pagination.of(page, pageSize))));
		}

		private WorkflowTask _workflowTask;

	}

	@GraphQLName("WorkflowLogPage")
	public class WorkflowLogPage {

		public WorkflowLogPage(Page workflowLogPage) {
			items = workflowLogPage.getItems();
			page = workflowLogPage.getPage();
			pageSize = workflowLogPage.getPageSize();
			totalCount = workflowLogPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<WorkflowLog> items;

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
			items = workflowTaskPage.getItems();
			page = workflowTaskPage.getPage();
			pageSize = workflowTaskPage.getPageSize();
			totalCount = workflowTaskPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<WorkflowTask> items;

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

	private static ComponentServiceObjects<WorkflowLogResource>
		_workflowLogResourceComponentServiceObjects;
	private static ComponentServiceObjects<WorkflowTaskResource>
		_workflowTaskResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private Company _company;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private UriInfo _uriInfo;
	private User _user;

}