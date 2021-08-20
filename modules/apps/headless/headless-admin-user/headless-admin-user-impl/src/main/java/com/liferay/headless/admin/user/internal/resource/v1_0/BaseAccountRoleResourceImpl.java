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

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.AccountRole;
import com.liferay.headless.admin.user.resource.v1_0.AccountRoleResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseAccountRoleResourceImpl
	implements AccountRoleResource, EntityModelResource,
			   VulcanBatchEngineTaskItemDelegate<AccountRole> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-external-reference-code/{userAccountExternalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Unassigns account users by external reference code from the account role"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "accountExternalReferenceCode"
			),
			@Parameter(in = ParameterIn.PATH, name = "accountRoleId"),
			@Parameter(
				in = ParameterIn.PATH, name = "userAccountExternalReferenceCode"
			)
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{accountExternalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-external-reference-code/{userAccountExternalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AccountRole")})
	public void
			deleteAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode(
				@NotNull @Parameter(hidden = true)
				@PathParam("accountExternalReferenceCode")
				String accountExternalReferenceCode,
				@NotNull @Parameter(hidden = true) @PathParam("accountRoleId")
					Long accountRoleId,
				@NotNull @Parameter(hidden = true)
				@PathParam("userAccountExternalReferenceCode")
				String userAccountExternalReferenceCode)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-external-reference-code/{userAccountExternalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@Operation(
		description = "Assigns account users by external reference code to the account role"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "accountExternalReferenceCode"
			),
			@Parameter(in = ParameterIn.PATH, name = "accountRoleId"),
			@Parameter(
				in = ParameterIn.PATH, name = "userAccountExternalReferenceCode"
			)
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{accountExternalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-external-reference-code/{userAccountExternalReferenceCode}"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AccountRole")})
	public void
			postAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode(
				@NotNull @Parameter(hidden = true)
				@PathParam("accountExternalReferenceCode")
				String accountExternalReferenceCode,
				@NotNull @Parameter(hidden = true) @PathParam("accountRoleId")
					Long accountRoleId,
				@NotNull @Parameter(hidden = true)
				@PathParam("userAccountExternalReferenceCode")
				String userAccountExternalReferenceCode)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{userAccountExternalReferenceCode}/account-roles'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Gets a user's account roles by their external reference code from an account by external reference code"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "accountExternalReferenceCode"
			),
			@Parameter(
				in = ParameterIn.PATH, name = "userAccountExternalReferenceCode"
			)
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{userAccountExternalReferenceCode}/account-roles"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AccountRole")})
	public Page<AccountRole>
			getAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("accountExternalReferenceCode")
				String accountExternalReferenceCode,
				@NotNull @Parameter(hidden = true)
				@PathParam("userAccountExternalReferenceCode")
				String userAccountExternalReferenceCode)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Gets the account's roles")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode"),
			@Parameter(in = ParameterIn.QUERY, name = "keywords"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{externalReferenceCode}/account-roles"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AccountRole")})
	public Page<AccountRole> getAccountAccountRolesByExternalReferenceCodePage(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			@Parameter(hidden = true) @QueryParam("keywords") String keywords,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles' -d $'{"description": ___, "displayName": ___, "name": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Adds a role for the account")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{externalReferenceCode}/account-roles"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AccountRole")})
	public AccountRole postAccountAccountRoleByExternalReferenceCode(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			AccountRole accountRole)
		throws Exception {

		return new AccountRole();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-email-address/{emailAddress}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Unassigns account users by email address from the account role"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode"),
			@Parameter(in = ParameterIn.PATH, name = "accountRoleId"),
			@Parameter(in = ParameterIn.PATH, name = "emailAddress")
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{externalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-email-address/{emailAddress}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AccountRole")})
	public void
			deleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
				@NotNull @Parameter(hidden = true)
				@PathParam("externalReferenceCode")
				String externalReferenceCode,
				@NotNull @Parameter(hidden = true) @PathParam("accountRoleId")
					Long accountRoleId,
				@NotNull @Parameter(hidden = true) @PathParam("emailAddress")
					String emailAddress)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-email-address/{emailAddress}'  -u 'test@liferay.com:test'
	 */
	@Operation(
		description = "Assigns account users by email address to the account role"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode"),
			@Parameter(in = ParameterIn.PATH, name = "accountRoleId"),
			@Parameter(in = ParameterIn.PATH, name = "emailAddress")
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{externalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-email-address/{emailAddress}"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AccountRole")})
	public void
			postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
				@NotNull @Parameter(hidden = true)
				@PathParam("externalReferenceCode")
				String externalReferenceCode,
				@NotNull @Parameter(hidden = true) @PathParam("accountRoleId")
					Long accountRoleId,
				@NotNull @Parameter(hidden = true) @PathParam("emailAddress")
					String emailAddress)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}/account-roles'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Gets a user's account roles by their email address from an account by external reference code"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode"),
			@Parameter(in = ParameterIn.PATH, name = "emailAddress")
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}/account-roles"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AccountRole")})
	public Page<AccountRole>
			getAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("externalReferenceCode")
				String externalReferenceCode,
				@NotNull @Parameter(hidden = true) @PathParam("emailAddress")
					String emailAddress)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/{accountId}/account-roles'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Gets the account's roles")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "accountId"),
			@Parameter(in = ParameterIn.QUERY, name = "keywords"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/accounts/{accountId}/account-roles")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AccountRole")})
	public Page<AccountRole> getAccountAccountRolesPage(
			@NotNull @Parameter(hidden = true) @PathParam("accountId") Long
				accountId,
			@Parameter(hidden = true) @QueryParam("keywords") String keywords,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/{accountId}/account-roles' -d $'{"description": ___, "displayName": ___, "name": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Adds a role for the account")
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "accountId")})
	@Path("/accounts/{accountId}/account-roles")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AccountRole")})
	public AccountRole postAccountAccountRole(
			@NotNull @Parameter(hidden = true) @PathParam("accountId") Long
				accountId,
			AccountRole accountRole)
		throws Exception {

		return new AccountRole();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/{accountId}/account-roles/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "accountId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/accounts/{accountId}/account-roles/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "AccountRole")})
	public Response postAccountAccountRoleBatch(
			@NotNull @Parameter(hidden = true) @PathParam("accountId") Long
				accountId,
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			Object object)
		throws Exception {

		vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineImportTaskResource.postImportTask(
				AccountRole.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/{accountId}/account-roles/{accountRoleId}/user-accounts/{userAccountId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(description = "Unassigns account users to the account role")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "accountId"),
			@Parameter(in = ParameterIn.PATH, name = "accountRoleId"),
			@Parameter(in = ParameterIn.PATH, name = "userAccountId")
		}
	)
	@Path(
		"/accounts/{accountId}/account-roles/{accountRoleId}/user-accounts/{userAccountId}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AccountRole")})
	public void deleteAccountAccountRoleUserAccountAssociation(
			@NotNull @Parameter(hidden = true) @PathParam("accountId") Long
				accountId,
			@NotNull @Parameter(hidden = true) @PathParam("accountRoleId") Long
				accountRoleId,
			@NotNull @Parameter(hidden = true) @PathParam("userAccountId") Long
				userAccountId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/{accountId}/account-roles/{accountRoleId}/user-accounts/{userAccountId}'  -u 'test@liferay.com:test'
	 */
	@Operation(description = "Assigns account users to the account role")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "accountId"),
			@Parameter(in = ParameterIn.PATH, name = "accountRoleId"),
			@Parameter(in = ParameterIn.PATH, name = "userAccountId")
		}
	)
	@Path(
		"/accounts/{accountId}/account-roles/{accountRoleId}/user-accounts/{userAccountId}"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "AccountRole")})
	public void postAccountAccountRoleUserAccountAssociation(
			@NotNull @Parameter(hidden = true) @PathParam("accountId") Long
				accountId,
			@NotNull @Parameter(hidden = true) @PathParam("accountRoleId") Long
				accountRoleId,
			@NotNull @Parameter(hidden = true) @PathParam("userAccountId") Long
				userAccountId)
		throws Exception {
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<AccountRole> accountRoles,
			Map<String, Serializable> parameters)
		throws Exception {

		for (AccountRole accountRole : accountRoles) {
			postAccountAccountRole(
				Long.parseLong((String)parameters.get("accountId")),
				accountRole);
		}
	}

	@Override
	public void delete(
			java.util.Collection<AccountRole> accountRoles,
			Map<String, Serializable> parameters)
		throws Exception {
	}

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return getEntityModel(
			new MultivaluedHashMap<String, Object>(multivaluedMap));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return null;
	}

	@Override
	public Page<AccountRole> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return getAccountAccountRolesPage(
			Long.parseLong((String)parameters.get("accountId")),
			(String)parameters.get("keywords"), pagination, sorts);
	}

	@Override
	public void setLanguageId(String languageId) {
		this.contextAcceptLanguage = new AcceptLanguage() {

			@Override
			public List<Locale> getLocales() {
				return null;
			}

			@Override
			public String getPreferredLanguageId() {
				return languageId;
			}

			@Override
			public Locale getPreferredLocale() {
				return LocaleUtil.fromLanguageId(languageId);
			}

		};
	}

	@Override
	public void update(
			java.util.Collection<AccountRole> accountRoles,
			Map<String, Serializable> parameters)
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
	protected VulcanBatchEngineImportTaskResource
		vulcanBatchEngineImportTaskResource;

}