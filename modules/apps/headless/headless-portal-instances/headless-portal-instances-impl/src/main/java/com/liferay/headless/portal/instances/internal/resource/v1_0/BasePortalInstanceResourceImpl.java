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

package com.liferay.headless.portal.instances.internal.resource.v1_0;

import com.liferay.headless.portal.instances.dto.v1_0.PortalInstance;
import com.liferay.headless.portal.instances.resource.v1_0.PortalInstanceResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;

/**
 * @author Alberto Chaparro
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BasePortalInstanceResourceImpl
	implements PortalInstanceResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-portal-instances/v1.0/portal-instances'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the portal instances")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "skipDefault")}
	)
	@Path("/portal-instances")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "PortalInstance")})
	public Page<PortalInstance> getPortalInstancesPage(
			@Parameter(hidden = true) @QueryParam("skipDefault") Boolean
				skipDefault)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-portal-instances/v1.0/portal-instances' -d $'{"companyId": ___, "domain": ___, "portalInstanceId": ___, "portalInstanceInitializerKey": ___, "portalInstanceInitializerPayload": ___, "virtualHost": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Adds a new portal instance")
	@Override
	@Path("/portal-instances")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "PortalInstance")})
	public PortalInstance postPortalInstance(PortalInstance portalInstance)
		throws Exception {

		return new PortalInstance();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(description = "Removes the portal instance")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "portalInstanceId")}
	)
	@Path("/portal-instances/{portalInstanceId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "PortalInstance")})
	public void deletePortalInstance(
			@NotNull @Parameter(hidden = true) @PathParam("portalInstanceId")
				String portalInstanceId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the portal instance")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "portalInstanceId")}
	)
	@Path("/portal-instances/{portalInstanceId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "PortalInstance")})
	public PortalInstance getPortalInstance(
			@NotNull @Parameter(hidden = true) @PathParam("portalInstanceId")
				String portalInstanceId)
		throws Exception {

		return new PortalInstance();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}' -d $'{"companyId": ___, "domain": ___, "portalInstanceId": ___, "portalInstanceInitializerKey": ___, "portalInstanceInitializerPayload": ___, "virtualHost": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates the portal instance with information sent in the request body. Only the provided fields are updated."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "portalInstanceId")}
	)
	@PATCH
	@Path("/portal-instances/{portalInstanceId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "PortalInstance")})
	public PortalInstance patchPortalInstance(
			@NotNull @Parameter(hidden = true) @PathParam("portalInstanceId")
				String portalInstanceId,
			PortalInstance portalInstance)
		throws Exception {

		return new PortalInstance();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}/activate'  -u 'test@liferay.com:test'
	 */
	@Operation(description = "Activates the portal instance")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "portalInstanceId")}
	)
	@Path("/portal-instances/{portalInstanceId}/activate")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "PortalInstance")})
	public void putPortalInstanceActivate(
			@NotNull @Parameter(hidden = true) @PathParam("portalInstanceId")
				String portalInstanceId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}/deactivate'  -u 'test@liferay.com:test'
	 */
	@Operation(
		description = "Deactivates the portal instance. When a portal instance is deactivated, its virtual host will not longer respond requests."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "portalInstanceId")}
	)
	@Path("/portal-instances/{portalInstanceId}/deactivate")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "PortalInstance")})
	public void putPortalInstanceDeactivate(
			@NotNull @Parameter(hidden = true) @PathParam("portalInstanceId")
				String portalInstanceId)
		throws Exception {
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany) {

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

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser) {

		this.contextUser = contextUser;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	protected Map<String, String> addAction(
		String actionName, GroupedModel groupedModel, String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, Long ownerId,
		String permissionName, Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			ownerId, permissionName, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName,
		ModelResourcePermission modelResourcePermission) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			modelResourcePermission, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, null, permissionName, siteId);
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
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected GroupLocalService groupLocalService;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;

}