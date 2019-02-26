/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.internal.graphql.query.v1_0;

import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.ProcessResourceImpl;
import com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0.SLAResourceImpl;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Process> getProcessesPage(
			@GraphQLName("title") String title,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		ProcessResource processResource = _createProcessResource();

		processResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = processResource.getProcessesPage(
			title, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public SLA getProcessSla(
			@GraphQLName("process-id") Long processId,
			@GraphQLName("sla-id") Long slaId)
		throws Exception {

		SLAResource sLAResource = _createSLAResource();

		sLAResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return sLAResource.getProcessSla(processId, slaId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<SLA> getProcessSLAsPage(
			@GraphQLName("process-id") Long processId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		SLAResource sLAResource = _createSLAResource();

		sLAResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = sLAResource.getProcessSLAsPage(
			processId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	private static ProcessResource _createProcessResource() {
		return new ProcessResourceImpl();
	}

	private static SLAResource _createSLAResource() {
		return new SLAResourceImpl();
	}

}