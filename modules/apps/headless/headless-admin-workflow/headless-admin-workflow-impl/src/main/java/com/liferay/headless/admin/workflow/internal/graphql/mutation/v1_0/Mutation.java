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
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import graphql.schema.DataFetchingEnvironment;

import java.util.function.Function;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setWorkflowTaskResourceComponentServiceObjects(
		ComponentServiceObjects<WorkflowTaskResource>
			workflowTaskResourceComponentServiceObjects) {

		_workflowTaskResourceComponentServiceObjects =
			workflowTaskResourceComponentServiceObjects;
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTask postWorkflowTaskAssignToMe(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("workflowTaskAssignToMe") WorkflowTaskAssignToMe
				workflowTaskAssignToMe)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			workflowTaskResource -> _populateResourceContext(
				dataFetchingEnvironment, workflowTaskResource),
			workflowTaskResource ->
				workflowTaskResource.postWorkflowTaskAssignToMe(
					workflowTaskId, workflowTaskAssignToMe));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTask postWorkflowTaskAssignToUser(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("workflowTaskAssignToUser") WorkflowTaskAssignToUser
				workflowTaskAssignToUser)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			workflowTaskResource -> _populateResourceContext(
				dataFetchingEnvironment, workflowTaskResource),
			workflowTaskResource ->
				workflowTaskResource.postWorkflowTaskAssignToUser(
					workflowTaskId, workflowTaskAssignToUser));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTask postWorkflowTaskChangeTransition(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("changeTransition") ChangeTransition changeTransition)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			workflowTaskResource -> _populateResourceContext(
				dataFetchingEnvironment, workflowTaskResource),
			workflowTaskResource ->
				workflowTaskResource.postWorkflowTaskChangeTransition(
					workflowTaskId, changeTransition));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTask postWorkflowTaskUpdateDueDate(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("workflowTaskAssignToMe") WorkflowTaskAssignToMe
				workflowTaskAssignToMe)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			workflowTaskResource -> _populateResourceContext(
				dataFetchingEnvironment, workflowTaskResource),
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
			DataFetchingEnvironment dataFetchingEnvironment,
			WorkflowTaskResource workflowTaskResource)
		throws Exception {

		workflowTaskResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		workflowTaskResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	public static void setAcceptLanguageFunction(
		Function<Object, AcceptLanguage> acceptLanguageFunction) {

		_acceptLanguageFunction = acceptLanguageFunction;
	}

	private static Function<Object, AcceptLanguage> _acceptLanguageFunction;
	private static ComponentServiceObjects<WorkflowTaskResource>
		_workflowTaskResourceComponentServiceObjects;

}