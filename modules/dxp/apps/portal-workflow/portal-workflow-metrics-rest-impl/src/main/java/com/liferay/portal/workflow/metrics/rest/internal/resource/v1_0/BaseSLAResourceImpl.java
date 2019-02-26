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

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;

import java.net.URI;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseSLAResourceImpl implements SLAResource {

	@DELETE
	@Override
	@Path("/processes/{process-id}/slas/{sla-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public boolean deleteProcessSla(
			@PathParam("process-id") Long processId,
			@PathParam("sla-id") Long slaId)
		throws Exception {

		return false;
	}

	@GET
	@Override
	@Path("/processes/{process-id}/slas/{sla-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public SLA getProcessSla(
			@PathParam("process-id") Long processId,
			@PathParam("sla-id") Long slaId)
		throws Exception {

		return new SLA();
	}

	@GET
	@Override
	@Path("/processes/{process-id}/slas")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<SLA> getProcessSLAsPage(
			@PathParam("process-id") Long processId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Consumes("application/json")
	@Override
	@Path("/processes/{process-id}/slas")
	@POST
	@Produces("application/json")
	@RequiresScope("everything.read")
	public SLA postProcessSlas(@PathParam("process-id") Long processId, SLA sLA)
		throws Exception {

		return new SLA();
	}

	@Consumes("application/json")
	@Override
	@Path("/processes/{process-id}/slas/{sla-id}")
	@Produces("application/json")
	@PUT
	@RequiresScope("everything.read")
	public SLA putProcessSla(
			@PathParam("process-id") Long processId,
			@PathParam("sla-id") Long slaId, SLA sLA)
		throws Exception {

		return new SLA();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected String getJAXRSLink(String methodName, Object... values) {
		URI baseURI = contextUriInfo.getBaseUri();

		URI resourceURI = UriBuilder.fromResource(
			BaseSLAResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseSLAResourceImpl.class, methodName
		).build(
			values
		);

		return baseURI.toString() + resourceURI.toString() +
			methodURI.toString();
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

	@Context
	protected UriInfo contextUriInfo;

}