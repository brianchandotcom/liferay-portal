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

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseWorkflowDefinitionResourceImpl
	implements WorkflowDefinitionResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-definitions'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/workflow-definitions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowDefinition")})
	public Page<WorkflowDefinition> getWorkflowDefinitionsPage(
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-definitions/deploy'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@POST
	@Path("/workflow-definitions/deploy")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowDefinition")})
	public WorkflowDefinition postWorkflowDefinitionDeploy(
			WorkflowDefinition workflowDefinition)
		throws Exception {

		return new WorkflowDefinition();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-definitions/find-by-name'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Parameters(value = {@Parameter(in = ParameterIn.QUERY, name = "name")})
	@Path("/workflow-definitions/find-by-name")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowDefinition")})
	public WorkflowDefinition getWorkflowDefinitionFindByName(
			@Parameter(hidden = true) @QueryParam("name") String name)
		throws Exception {

		return new WorkflowDefinition();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-definitions/save'  -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@POST
	@Path("/workflow-definitions/save")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowDefinition")})
	public WorkflowDefinition postWorkflowDefinitionSave(
			WorkflowDefinition workflowDefinition)
		throws Exception {

		return new WorkflowDefinition();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-definitions/undeploy'  -u 'test@liferay.com:test'
	 */
	@Override
	@DELETE
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "name"),
			@Parameter(in = ParameterIn.QUERY, name = "version")
		}
	)
	@Path("/workflow-definitions/undeploy")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "WorkflowDefinition")})
	public void deleteWorkflowDefinitionUndeploy(
			@NotNull @Parameter(hidden = true) @QueryParam("name") String name,
			@NotNull @Parameter(hidden = true) @QueryParam("version") String
				version)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-definitions/update-active'  -u 'test@liferay.com:test'
	 */
	@Override
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "active"),
			@Parameter(in = ParameterIn.QUERY, name = "name"),
			@Parameter(in = ParameterIn.QUERY, name = "version")
		}
	)
	@Path("/workflow-definitions/update-active")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowDefinition")})
	public WorkflowDefinition postWorkflowDefinitionUpdateActive(
			@NotNull @Parameter(hidden = true) @QueryParam("active") Boolean
				active,
			@NotNull @Parameter(hidden = true) @QueryParam("name") String name,
			@NotNull @Parameter(hidden = true) @QueryParam("version") String
				version)
		throws Exception {

		return new WorkflowDefinition();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-definitions/update-title'  -u 'test@liferay.com:test'
	 */
	@Override
	@POST
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "name"),
			@Parameter(in = ParameterIn.QUERY, name = "title"),
			@Parameter(in = ParameterIn.QUERY, name = "version")
		}
	)
	@Path("/workflow-definitions/update-title")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowDefinition")})
	public WorkflowDefinition postWorkflowDefinitionUpdateTitle(
			@NotNull @Parameter(hidden = true) @QueryParam("name") String name,
			@NotNull @Parameter(hidden = true) @QueryParam("title") String
				title,
			@NotNull @Parameter(hidden = true) @QueryParam("version") String
				version)
		throws Exception {

		return new WorkflowDefinition();
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(User contextUser) {
		this.contextUser = contextUser;
	}

	protected void preparePatch(
		WorkflowDefinition workflowDefinition,
		WorkflowDefinition existingWorkflowDefinition) {
	}

	protected <T, R> List<R> transform(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected UriInfo contextUriInfo;
	protected User contextUser;

}