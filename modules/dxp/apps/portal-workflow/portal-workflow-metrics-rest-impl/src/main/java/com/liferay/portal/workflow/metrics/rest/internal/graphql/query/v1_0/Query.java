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
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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

		Page paginationPage = processResource.getProcessesPage(
			title, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<SLA> getProcessSlasPage(
			@GraphQLName("process-id") Long processId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		SLAResource sLAResource = _createSLAResource();

		Page paginationPage = sLAResource.getProcessSlasPage(
			processId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public SLA getProcessSla(
			@GraphQLName("process-id") Long processId,
			@GraphQLName("sla-id") Long slaId)
		throws Exception {

		SLAResource sLAResource = _createSLAResource();

		return sLAResource.getProcessSla(processId, slaId);
	}

	private static ProcessResource _createProcessResource() throws Exception {
		ProcessResource processResource =
			_processResourceServiceTracker.getService();

		processResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return processResource;
	}

	private static final ServiceTracker<ProcessResource, ProcessResource>
		_processResourceServiceTracker;

	private static SLAResource _createSLAResource() throws Exception {
		SLAResource sLAResource = _sLAResourceServiceTracker.getService();

		sLAResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return sLAResource;
	}

	private static final ServiceTracker<SLAResource, SLAResource>
		_sLAResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Query.class);

		ServiceTracker<ProcessResource, ProcessResource>
			processResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), ProcessResource.class, null);

		processResourceServiceTracker.open();

		_processResourceServiceTracker = processResourceServiceTracker;
		ServiceTracker<SLAResource, SLAResource> sLAResourceServiceTracker =
			new ServiceTracker<>(
				bundle.getBundleContext(), SLAResource.class, null);

		sLAResourceServiceTracker.open();

		_sLAResourceServiceTracker = sLAResourceServiceTracker;
	}

}