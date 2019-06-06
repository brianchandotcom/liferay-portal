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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collection;
import java.util.function.Function;

import javax.annotation.Generated;

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

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowLog getWorkflowLog(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("workflowLogId") Long workflowLogId)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowLogResourceComponentServiceObjects,
			workflowLogResource -> _populateResourceContext(
				dataFetchingEnvironment, workflowLogResource),
			workflowLogResource -> workflowLogResource.getWorkflowLog(
				workflowLogId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WorkflowLog> getWorkflowTaskWorkflowLogsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("workflowTaskId") Long workflowTaskId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowLogResourceComponentServiceObjects,
			workflowLogResource -> _populateResourceContext(
				dataFetchingEnvironment, workflowLogResource),
			workflowLogResource -> {
				Page paginationPage =
					workflowLogResource.getWorkflowTaskWorkflowLogsPage(
						workflowTaskId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WorkflowTask> getRoleWorkflowTasksPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("roleId") Long roleId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			workflowTaskResource -> _populateResourceContext(
				dataFetchingEnvironment, workflowTaskResource),
			workflowTaskResource -> {
				Page paginationPage =
					workflowTaskResource.getRoleWorkflowTasksPage(
						roleId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WorkflowTask> getWorkflowTasksAssignedToMePage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			workflowTaskResource -> _populateResourceContext(
				dataFetchingEnvironment, workflowTaskResource),
			workflowTaskResource -> {
				Page paginationPage =
					workflowTaskResource.getWorkflowTasksAssignedToMePage(
						Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WorkflowTask> getWorkflowTasksAssignedToMyRolesPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			workflowTaskResource -> _populateResourceContext(
				dataFetchingEnvironment, workflowTaskResource),
			workflowTaskResource -> {
				Page paginationPage =
					workflowTaskResource.getWorkflowTasksAssignedToMyRolesPage(
						Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTask getWorkflowTask(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("workflowTaskId") Long workflowTaskId)
		throws Exception {

		return _applyComponentServiceObjects(
			_workflowTaskResourceComponentServiceObjects,
			workflowTaskResource -> _populateResourceContext(
				dataFetchingEnvironment, workflowTaskResource),
			workflowTaskResource -> workflowTaskResource.getWorkflowTask(
				workflowTaskId));
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
			DataFetchingEnvironment dataFetchingEnvironment,
			WorkflowLogResource workflowLogResource)
		throws Exception {

		workflowLogResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		workflowLogResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
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
	private static ComponentServiceObjects<WorkflowLogResource>
		_workflowLogResourceComponentServiceObjects;
	private static ComponentServiceObjects<WorkflowTaskResource>
		_workflowTaskResourceComponentServiceObjects;

}