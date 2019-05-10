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

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.InstanceResource;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseInstanceResourceImpl implements InstanceResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "processId"),
			@Parameter(in = ParameterIn.QUERY, name = "slaStatuses"),
			@Parameter(in = ParameterIn.QUERY, name = "statuses"),
			@Parameter(in = ParameterIn.QUERY, name = "taskKeys"),
			@Parameter(in = ParameterIn.QUERY, name = "timeRange"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/processes/{processId}/instances")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Instance")})
	public Page<Instance> getProcessInstancesPage(
			@NotNull @Parameter(hidden = true) @PathParam("processId") Long
				processId,
			@Parameter(hidden = true) @QueryParam("slaStatuses") String[]
				slaStatuses,
			@Parameter(hidden = true) @QueryParam("statuses") String[] statuses,
			@Parameter(hidden = true) @QueryParam("taskKeys") String[] taskKeys,
			@Parameter(hidden = true) @QueryParam("timeRange") Integer
				timeRange,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "processId"),
			@Parameter(in = ParameterIn.PATH, name = "instanceId")
		}
	)
	@Path("/processes/{processId}/instances/{instanceId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Instance")})
	public Instance getProcessInstance(
			@NotNull @Parameter(hidden = true) @PathParam("processId") Long
				processId,
			@NotNull @Parameter(hidden = true) @PathParam("instanceId") Long
				instanceId)
		throws Exception {

		return new Instance();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(Instance instance, Instance existingInstance) {
	}

	protected <T, R> List<R> transform(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

	@Context
	protected UriInfo contextUriInfo;

}