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

import com.liferay.headless.workflow.internal.dto.v1_0.WorkflowLogImpl;
import com.liferay.headless.workflow.internal.dto.v1_0.WorkflowTaskImpl;
import com.liferay.headless.workflow.internal.resource.v1_0.WorkflowLogResourceImpl;
import com.liferay.headless.workflow.internal.resource.v1_0.WorkflowTaskResourceImpl;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowLogImpl getWorkflowLog(
	@GraphQLName("workflow-log-id") Long workflowLogId)
			throws Exception {

				WorkflowLogResourceImpl workflowLogResourceImpl = _getWorkflowLogResource();

				workflowLogResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return workflowLogResourceImpl.getWorkflowLog(
					workflowLogId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WorkflowTaskImpl> getWorkflowTaskWorkflowLogsPage(
	@GraphQLName("workflow-task-id") Long workflowTaskId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				WorkflowLogResourceImpl workflowLogResourceImpl = _getWorkflowLogResource();

				workflowLogResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = workflowLogResourceImpl.getWorkflowTaskWorkflowLogsPage(
					workflowTaskId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WorkflowTaskImpl> getRoleWorkflowTasksPage(
	@GraphQLName("role-id") Long roleId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				WorkflowTaskResourceImpl workflowTaskResourceImpl = _getWorkflowTaskResource();

				workflowTaskResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = workflowTaskResourceImpl.getRoleWorkflowTasksPage(
					roleId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<WorkflowTaskImpl> getWorkflowTasksPage(
	@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				WorkflowTaskResourceImpl workflowTaskResourceImpl = _getWorkflowTaskResource();

				workflowTaskResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = workflowTaskResourceImpl.getWorkflowTasksPage(
					Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public WorkflowTaskImpl getWorkflowTask(
	@GraphQLName("workflow-task-id") Long workflowTaskId)
			throws Exception {

				WorkflowTaskResourceImpl workflowTaskResourceImpl = _getWorkflowTaskResource();

				workflowTaskResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return workflowTaskResourceImpl.getWorkflowTask(
					workflowTaskId);
	}

	private static WorkflowLogResourceImpl _getWorkflowLogResource() {
		return new WorkflowLogResourceImpl();
	}

	private static WorkflowTaskResourceImpl _getWorkflowTaskResource() {
		return new WorkflowTaskResourceImpl();
	}

}