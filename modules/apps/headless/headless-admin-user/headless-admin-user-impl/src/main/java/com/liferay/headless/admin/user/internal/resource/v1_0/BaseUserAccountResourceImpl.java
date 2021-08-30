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

import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.petra.function.UnsafeConsumer;
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
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
public abstract class BaseUserAccountResourceImpl
	implements EntityModelResource, UserAccountResource,
			   VulcanBatchEngineTaskItemDelegate<UserAccount> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{userAccountExternalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Removes a user by their external reference code from an account by external reference code"
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
		"/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{userAccountExternalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public void
			deleteAccountByExternalReferenceCodeUserAccountByExternalReferenceCode(
				@NotNull @Parameter(hidden = true)
				@PathParam("accountExternalReferenceCode")
				String accountExternalReferenceCode,
				@NotNull @Parameter(hidden = true)
				@PathParam("userAccountExternalReferenceCode")
				String userAccountExternalReferenceCode)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{userAccountExternalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@Operation(
		description = "Assigns a user by their external reference code to an account by external reference code"
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
		"/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{userAccountExternalReferenceCode}"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public void
			postAccountByExternalReferenceCodeUserAccountByExternalReferenceCode(
				@NotNull @Parameter(hidden = true)
				@PathParam("accountExternalReferenceCode")
				String accountExternalReferenceCode,
				@NotNull @Parameter(hidden = true)
				@PathParam("userAccountExternalReferenceCode")
				String userAccountExternalReferenceCode)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Gets the users assigned to an account")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public Page<UserAccount> getAccountUserAccountsByExternalReferenceCodePage(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts' -d $'{"additionalName": ___, "alternateName": ___, "birthDate": ___, "customFields": ___, "emailAddress": ___, "familyName": ___, "givenName": ___, "honorificPrefix": ___, "honorificSuffix": ___, "jobTitle": ___, "userAccountContactInformation": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a user and assigns them to the account")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount postAccountUserAccountByExternalReferenceCode(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			UserAccount userAccount)
		throws Exception {

		return new UserAccount();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@DELETE
	@Operation(
		description = "Removes users from an account by their email addresses"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public void deleteAccountUserAccountsByExternalReferenceCodeByEmailAddress(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			String[] strings)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Assigns users to an account by their email addresses"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public void postAccountUserAccountsByExternalReferenceCodeByEmailAddress(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			String[] strings)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Removes a user from an account by external reference code by their email address"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode"),
			@Parameter(in = ParameterIn.PATH, name = "emailAddress")
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public void deleteAccountUserAccountByExternalReferenceCodeByEmailAddress(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			@NotNull @Parameter(hidden = true) @PathParam("emailAddress") String
				emailAddress)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}'  -u 'test@liferay.com:test'
	 */
	@Operation(
		description = "Assigns a user to an account by external reference code by their email address"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode"),
			@Parameter(in = ParameterIn.PATH, name = "emailAddress")
		}
	)
	@Path(
		"/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public void postAccountUserAccountByExternalReferenceCodeByEmailAddress(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			@NotNull @Parameter(hidden = true) @PathParam("emailAddress") String
				emailAddress)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/{accountId}/user-accounts'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Gets the users assigned to an account")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "accountId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/accounts/{accountId}/user-accounts")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public Page<UserAccount> getAccountUserAccountsPage(
			@NotNull @Parameter(hidden = true) @PathParam("accountId") Long
				accountId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/{accountId}/user-accounts' -d $'{"additionalName": ___, "alternateName": ___, "birthDate": ___, "customFields": ___, "emailAddress": ___, "familyName": ___, "givenName": ___, "honorificPrefix": ___, "honorificSuffix": ___, "jobTitle": ___, "userAccountContactInformation": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a user and assigns them to the account")
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "accountId")})
	@Path("/accounts/{accountId}/user-accounts")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount postAccountUserAccount(
			@NotNull @Parameter(hidden = true) @PathParam("accountId") Long
				accountId,
			UserAccount userAccount)
		throws Exception {

		return new UserAccount();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "accountId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/accounts/{accountId}/user-accounts/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public Response postAccountUserAccountBatch(
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
				UserAccount.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/by-email-address'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@DELETE
	@Operation(
		description = "Removes users from an account by their email addresses"
	)
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "accountId")})
	@Path("/accounts/{accountId}/user-accounts/by-email-address")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public void deleteAccountUserAccountsByEmailAddress(
			@NotNull @Parameter(hidden = true) @PathParam("accountId") Long
				accountId,
			String[] strings)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/by-email-address'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Assigns users to an account by their email addresses"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "accountId"),
			@Parameter(in = ParameterIn.QUERY, name = "accountRoleIds")
		}
	)
	@Path("/accounts/{accountId}/user-accounts/by-email-address")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public Page<UserAccount> postAccountUserAccountsByEmailAddress(
			@NotNull @Parameter(hidden = true) @PathParam("accountId") Long
				accountId,
			@Parameter(hidden = true) @QueryParam("accountRoleIds") String
				accountRoleIds,
			String[] strings)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/by-email-address/{emailAddress}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Removes a user from an account by their email address"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "accountId"),
			@Parameter(in = ParameterIn.PATH, name = "emailAddress")
		}
	)
	@Path("/accounts/{accountId}/user-accounts/by-email-address/{emailAddress}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public void deleteAccountUserAccountByEmailAddress(
			@NotNull @Parameter(hidden = true) @PathParam("accountId") Long
				accountId,
			@NotNull @Parameter(hidden = true) @PathParam("emailAddress") String
				emailAddress)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/by-email-address/{emailAddress}'  -u 'test@liferay.com:test'
	 */
	@Operation(
		description = "Assigns a user to an account by their email address"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "accountId"),
			@Parameter(in = ParameterIn.PATH, name = "emailAddress")
		}
	)
	@Path("/accounts/{accountId}/user-accounts/by-email-address/{emailAddress}")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount postAccountUserAccountByEmailAddress(
			@NotNull @Parameter(hidden = true) @PathParam("accountId") Long
				accountId,
			@NotNull @Parameter(hidden = true) @PathParam("emailAddress") String
				emailAddress)
		throws Exception {

		return new UserAccount();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/my-user-account'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves information about the user who made the request."
	)
	@Override
	@Path("/my-user-account")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount getMyUserAccount() throws Exception {
		return new UserAccount();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the organization's members (users). Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "organizationId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/organizations/{organizationId}/user-accounts")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public Page<UserAccount> getOrganizationUserAccountsPage(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/sites/{siteId}/user-accounts'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the site members' user accounts. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/sites/{siteId}/user-accounts")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public Page<UserAccount> getSiteUserAccountsPage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/user-accounts'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the user accounts. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/user-accounts")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public Page<UserAccount> getUserAccountsPage(
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/user-accounts' -d $'{"additionalName": ___, "alternateName": ___, "birthDate": ___, "customFields": ___, "emailAddress": ___, "familyName": ___, "givenName": ___, "honorificPrefix": ___, "honorificSuffix": ___, "jobTitle": ___, "userAccountContactInformation": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new user account")
	@Override
	@Path("/user-accounts")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {})
	public UserAccount postUserAccount(UserAccount userAccount)
		throws Exception {

		return new UserAccount();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/user-accounts/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/user-accounts/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {})
	public Response postUserAccountBatch(
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
				UserAccount.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/user-accounts/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path("/user-accounts/by-external-reference-code/{externalReferenceCode}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public void deleteUserAccountByExternalReferenceCode(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/user-accounts/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path("/user-accounts/by-external-reference-code/{externalReferenceCode}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount getUserAccountByExternalReferenceCode(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode)
		throws Exception {

		return new UserAccount();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-admin-user/v1.0/user-accounts/by-external-reference-code/{externalReferenceCode}' -d $'{"additionalName": ___, "alternateName": ___, "birthDate": ___, "customFields": ___, "emailAddress": ___, "familyName": ___, "givenName": ___, "honorificPrefix": ___, "honorificSuffix": ___, "jobTitle": ___, "userAccountContactInformation": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path("/user-accounts/by-external-reference-code/{externalReferenceCode}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {})
	public UserAccount putUserAccountByExternalReferenceCode(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			UserAccount userAccount)
		throws Exception {

		return new UserAccount();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/user-accounts/{userAccountId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(description = "Deletes the user account")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "userAccountId")}
	)
	@Path("/user-accounts/{userAccountId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public void deleteUserAccount(
			@NotNull @Parameter(hidden = true) @PathParam("userAccountId") Long
				userAccountId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/user-accounts/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/user-accounts/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public Response deleteUserAccountBatch(
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
			vulcanBatchEngineImportTaskResource.deleteImportTask(
				UserAccount.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/user-accounts/{userAccountId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the user account.")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "userAccountId")}
	)
	@Path("/user-accounts/{userAccountId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount getUserAccount(
			@NotNull @Parameter(hidden = true) @PathParam("userAccountId") Long
				userAccountId)
		throws Exception {

		return new UserAccount();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-admin-user/v1.0/user-accounts/{userAccountId}' -d $'{"additionalName": ___, "alternateName": ___, "birthDate": ___, "customFields": ___, "emailAddress": ___, "familyName": ___, "givenName": ___, "honorificPrefix": ___, "honorificSuffix": ___, "jobTitle": ___, "userAccountContactInformation": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates the user account with information sent in the request body. Only the provided fields are updated."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "userAccountId")}
	)
	@PATCH
	@Path("/user-accounts/{userAccountId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {})
	public UserAccount patchUserAccount(
			@NotNull @Parameter(hidden = true) @PathParam("userAccountId") Long
				userAccountId,
			UserAccount userAccount)
		throws Exception {

		UserAccount existingUserAccount = getUserAccount(userAccountId);

		if (userAccount.getActions() != null) {
			existingUserAccount.setActions(userAccount.getActions());
		}

		if (userAccount.getAdditionalName() != null) {
			existingUserAccount.setAdditionalName(
				userAccount.getAdditionalName());
		}

		if (userAccount.getAlternateName() != null) {
			existingUserAccount.setAlternateName(
				userAccount.getAlternateName());
		}

		if (userAccount.getBirthDate() != null) {
			existingUserAccount.setBirthDate(userAccount.getBirthDate());
		}

		if (userAccount.getDashboardURL() != null) {
			existingUserAccount.setDashboardURL(userAccount.getDashboardURL());
		}

		if (userAccount.getDateCreated() != null) {
			existingUserAccount.setDateCreated(userAccount.getDateCreated());
		}

		if (userAccount.getDateModified() != null) {
			existingUserAccount.setDateModified(userAccount.getDateModified());
		}

		if (userAccount.getEmailAddress() != null) {
			existingUserAccount.setEmailAddress(userAccount.getEmailAddress());
		}

		if (userAccount.getExternalReferenceCode() != null) {
			existingUserAccount.setExternalReferenceCode(
				userAccount.getExternalReferenceCode());
		}

		if (userAccount.getFamilyName() != null) {
			existingUserAccount.setFamilyName(userAccount.getFamilyName());
		}

		if (userAccount.getGivenName() != null) {
			existingUserAccount.setGivenName(userAccount.getGivenName());
		}

		if (userAccount.getHonorificPrefix() != null) {
			existingUserAccount.setHonorificPrefix(
				userAccount.getHonorificPrefix());
		}

		if (userAccount.getHonorificSuffix() != null) {
			existingUserAccount.setHonorificSuffix(
				userAccount.getHonorificSuffix());
		}

		if (userAccount.getImage() != null) {
			existingUserAccount.setImage(userAccount.getImage());
		}

		if (userAccount.getJobTitle() != null) {
			existingUserAccount.setJobTitle(userAccount.getJobTitle());
		}

		if (userAccount.getKeywords() != null) {
			existingUserAccount.setKeywords(userAccount.getKeywords());
		}

		if (userAccount.getName() != null) {
			existingUserAccount.setName(userAccount.getName());
		}

		if (userAccount.getProfileURL() != null) {
			existingUserAccount.setProfileURL(userAccount.getProfileURL());
		}

		preparePatch(userAccount, existingUserAccount);

		return putUserAccount(userAccountId, existingUserAccount);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-admin-user/v1.0/user-accounts/{userAccountId}' -d $'{"additionalName": ___, "alternateName": ___, "birthDate": ___, "customFields": ___, "emailAddress": ___, "familyName": ___, "givenName": ___, "honorificPrefix": ___, "honorificSuffix": ___, "jobTitle": ___, "userAccountContactInformation": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the user account with information sent in the request body. Any missing fields are deleted unless they are required."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "userAccountId")}
	)
	@Path("/user-accounts/{userAccountId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {})
	public UserAccount putUserAccount(
			@NotNull @Parameter(hidden = true) @PathParam("userAccountId") Long
				userAccountId,
			UserAccount userAccount)
		throws Exception {

		return new UserAccount();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-admin-user/v1.0/user-accounts/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/user-accounts/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {})
	public Response putUserAccountBatch(
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
			vulcanBatchEngineImportTaskResource.putImportTask(
				UserAccount.class.getName(), callbackURL, object)
		).build();
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<UserAccount> userAccounts,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeConsumer<UserAccount, Exception> userAccountUnsafeConsumer =
			userAccount -> postAccountUserAccount(
				Long.parseLong((String)parameters.get("accountId")),
				userAccount);

		for (UserAccount userAccount : userAccounts) {
			userAccountUnsafeConsumer.accept(userAccount);
		}
	}

	@Override
	public void delete(
			java.util.Collection<UserAccount> userAccounts,
			Map<String, Serializable> parameters)
		throws Exception {

		for (UserAccount userAccount : userAccounts) {
			deleteUserAccount(userAccount.getId());
		}
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
	public Page<UserAccount> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		if (parameters.containsKey("siteId")) {
			return getSiteUserAccountsPage(
				(Long)parameters.get("siteId"), search, filter, pagination,
				sorts);
		}
		else {
			return getAccountUserAccountsPage(
				Long.parseLong((String)parameters.get("accountId")), search,
				filter, pagination, sorts);
		}
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
			java.util.Collection<UserAccount> userAccounts,
			Map<String, Serializable> parameters)
		throws Exception {

		for (UserAccount userAccount : userAccounts) {
			putUserAccount(
				userAccount.getId() != null ? userAccount.getId() :
					Long.parseLong((String)parameters.get("userAccountId")),
				userAccount);
		}
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

	public void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		this.resourceActionLocalService = resourceActionLocalService;
	}

	public void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		this.resourcePermissionLocalService = resourcePermissionLocalService;
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

	protected void preparePatch(
		UserAccount userAccount, UserAccount existingUserAccount) {
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