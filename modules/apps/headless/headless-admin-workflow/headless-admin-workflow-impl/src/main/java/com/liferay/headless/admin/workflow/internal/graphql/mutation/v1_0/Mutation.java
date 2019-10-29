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

package com.liferay.headless.admin.workflow.internal.graphql.mutation.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.ChangeTransition;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowInstanceSubmit;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToRole;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowInstanceResource;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

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
public class Mutation {

	public static void setWorkflowInstanceResourceComponentServiceObjects(
		ComponentServiceObjects<WorkflowInstanceResource>
			workflowInstanceResourceComponentServiceObjects) {

		_workflowInstanceResourceComponentServiceObjects =
			workflowInstanceResourceComponentServiceObjects;
	}

	public static void setWorkflowTaskResourceComponentServiceObjects(
		ComponentServiceObjects<WorkflowTaskResource>
			workflowTaskResourceComponentServiceObjects) {

		_workflowTaskResourceComponentServiceObjects =
			workflowTaskResourceComponentServiceObjects;
	}

	@GraphQLField
	public WorkflowInstance createWorkflowInstanceSubmit(
			@GraphQLName("workflowInstanceSubmit") WorkflowInstanceSubmit
				workflowInstanceSubmit)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowInstanceResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowInstanceResource ->
				workflowInstanceResource.postWorkflowInstanceSubmit(
					workflowInstanceSubmit));
	}

	@GraphQLField
	public boolean deleteWorkflowInstance(
			@GraphQLName("workflowInstanceId") Long workflowInstanceId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_workflowInstanceResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowInstanceResource ->
				workflowInstanceResource.deleteWorkflowInstance(
					workflowInstanceId));

		return true;
	}

	@GraphQLField
	public WorkflowInstance createWorkflowInstanceChangeTransition(
			@GraphQLName("workflowInstanceId") Long workflowInstanceId,
			@GraphQLName("changeTransition") ChangeTransition changeTransition)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowInstanceResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowInstanceResource ->
				workflowInstanceResource.postWorkflowInstanceChangeTransition(
					workflowInstanceId, changeTransition));
	}

	@GraphQLField
	public WorkflowTask createWorkflowTaskAssignToMe(
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("workflowTaskAssignToMe") WorkflowTaskAssignToMe
				workflowTaskAssignToMe)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource ->
				workflowTaskResource.postWorkflowTaskAssignToMe(
					workflowTaskId, workflowTaskAssignToMe));
	}

	@GraphQLField
	public WorkflowTask createWorkflowTaskAssignToRole(
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("workflowTaskAssignToRole") WorkflowTaskAssignToRole
				workflowTaskAssignToRole)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource ->
				workflowTaskResource.postWorkflowTaskAssignToRole(
					workflowTaskId, workflowTaskAssignToRole));
	}

	@GraphQLField
	public WorkflowTask createWorkflowTaskAssignToUser(
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("workflowTaskAssignToUser") WorkflowTaskAssignToUser
				workflowTaskAssignToUser)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource ->
				workflowTaskResource.postWorkflowTaskAssignToUser(
					workflowTaskId, workflowTaskAssignToUser));
	}

	@GraphQLField
	public WorkflowTask createWorkflowTaskChangeTransition(
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("changeTransition") ChangeTransition changeTransition)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource ->
				workflowTaskResource.postWorkflowTaskChangeTransition(
					workflowTaskId, changeTransition));
	}

	@GraphQLField
	public WorkflowTask createWorkflowTaskUpdateDueDate(
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("workflowTaskAssignToMe") WorkflowTaskAssignToMe
				workflowTaskAssignToMe)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			this::_populateResourceContext,
			workflowTaskResource ->
				workflowTaskResource.postWorkflowTaskUpdateDueDate(
					workflowTaskId, workflowTaskAssignToMe));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
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

	private static ComponentServiceObjects<WorkflowInstanceResource>
		_workflowInstanceResourceComponentServiceObjects;
	private static ComponentServiceObjects<WorkflowTaskResource>
		_workflowTaskResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private Company _company;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private UriInfo _uriInfo;
	private User _user;

}