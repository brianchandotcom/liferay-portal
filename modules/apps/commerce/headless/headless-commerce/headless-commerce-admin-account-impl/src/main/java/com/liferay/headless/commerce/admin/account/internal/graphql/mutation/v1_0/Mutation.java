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

package com.liferay.headless.commerce.admin.account.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.admin.account.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountAddress;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountGroup;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountMember;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountOrganization;
import com.liferay.headless.commerce.admin.account.dto.v1_0.User;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountAddressResource;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountGroupResource;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountMemberResource;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountOrganizationResource;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountResource;
import com.liferay.headless.commerce.admin.account.resource.v1_0.UserResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setAccountResourceComponentServiceObjects(
		ComponentServiceObjects<AccountResource>
			accountResourceComponentServiceObjects) {

		_accountResourceComponentServiceObjects =
			accountResourceComponentServiceObjects;
	}

	public static void setAccountAddressResourceComponentServiceObjects(
		ComponentServiceObjects<AccountAddressResource>
			accountAddressResourceComponentServiceObjects) {

		_accountAddressResourceComponentServiceObjects =
			accountAddressResourceComponentServiceObjects;
	}

	public static void setAccountGroupResourceComponentServiceObjects(
		ComponentServiceObjects<AccountGroupResource>
			accountGroupResourceComponentServiceObjects) {

		_accountGroupResourceComponentServiceObjects =
			accountGroupResourceComponentServiceObjects;
	}

	public static void setAccountMemberResourceComponentServiceObjects(
		ComponentServiceObjects<AccountMemberResource>
			accountMemberResourceComponentServiceObjects) {

		_accountMemberResourceComponentServiceObjects =
			accountMemberResourceComponentServiceObjects;
	}

	public static void setAccountOrganizationResourceComponentServiceObjects(
		ComponentServiceObjects<AccountOrganizationResource>
			accountOrganizationResourceComponentServiceObjects) {

		_accountOrganizationResourceComponentServiceObjects =
			accountOrganizationResourceComponentServiceObjects;
	}

	public static void setUserResourceComponentServiceObjects(
		ComponentServiceObjects<UserResource>
			userResourceComponentServiceObjects) {

		_userResourceComponentServiceObjects =
			userResourceComponentServiceObjects;
	}

	@GraphQLField
	public Response createAccountGroupByExternalReferenceCodeAccount(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("account") Account account)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource ->
				accountResource.postAccountGroupByExternalReferenceCodeAccount(
					externalReferenceCode, account));
	}

	@GraphQLField
	public Response deleteAccountGroupByExternalReferenceCodeAccount(
			@GraphQLName("accountExternalReferenceCode") String
				accountExternalReferenceCode,
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource ->
				accountResource.
					deleteAccountGroupByExternalReferenceCodeAccount(
						accountExternalReferenceCode, externalReferenceCode));
	}

	@GraphQLField
	public Account createAccount(@GraphQLName("account") Account account)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.postAccount(account));
	}

	@GraphQLField
	public Response createAccountBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.postAccountBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Response deleteAccountByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource ->
				accountResource.deleteAccountByExternalReferenceCode(
					externalReferenceCode));
	}

	@GraphQLField
	public Response patchAccountByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("account") Account account)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource ->
				accountResource.patchAccountByExternalReferenceCode(
					externalReferenceCode, account));
	}

	@GraphQLField
	@GraphQLName(
		description = "null",
		value = "postAccountByExternalReferenceCodeLogoExternalReferenceCodeMultipartBody"
	)
	public Response createAccountByExternalReferenceCodeLogo(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource ->
				accountResource.postAccountByExternalReferenceCodeLogo(
					externalReferenceCode, multipartBody));
	}

	@GraphQLField
	public Response deleteAccount(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.deleteAccount(id));
	}

	@GraphQLField
	public Response deleteAccountBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.deleteAccountBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response patchAccount(
			@GraphQLName("id") Long id, @GraphQLName("account") Account account)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.patchAccount(id, account));
	}

	@GraphQLField
	@GraphQLName(description = "null", value = "postAccountLogoIdMultipartBody")
	public Response createAccountLogo(
			@GraphQLName("id") Long id,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.postAccountLogo(
				id, multipartBody));
	}

	@GraphQLField
	public Response deleteAccountAddressByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource ->
				accountAddressResource.
					deleteAccountAddressByExternalReferenceCode(
						externalReferenceCode));
	}

	@GraphQLField
	public Response patchAccountAddressByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("accountAddress") AccountAddress accountAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource ->
				accountAddressResource.
					patchAccountAddressByExternalReferenceCode(
						externalReferenceCode, accountAddress));
	}

	@GraphQLField
	public Response deleteAccountAddress(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource ->
				accountAddressResource.deleteAccountAddress(id));
	}

	@GraphQLField
	public Response deleteAccountAddressBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource ->
				accountAddressResource.deleteAccountAddressBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public AccountAddress patchAccountAddress(
			@GraphQLName("id") Long id,
			@GraphQLName("accountAddress") AccountAddress accountAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource ->
				accountAddressResource.patchAccountAddress(id, accountAddress));
	}

	@GraphQLField
	public AccountAddress updateAccountAddress(
			@GraphQLName("id") Long id,
			@GraphQLName("accountAddress") AccountAddress accountAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource -> accountAddressResource.putAccountAddress(
				id, accountAddress));
	}

	@GraphQLField
	public Response updateAccountAddressBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource ->
				accountAddressResource.putAccountAddressBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public AccountAddress createAccountByExternalReferenceCodeAccountAddress(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("accountAddress") AccountAddress accountAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource ->
				accountAddressResource.
					postAccountByExternalReferenceCodeAccountAddress(
						externalReferenceCode, accountAddress));
	}

	@GraphQLField
	public AccountAddress createAccountIdAccountAddress(
			@GraphQLName("id") Long id,
			@GraphQLName("accountAddress") AccountAddress accountAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource ->
				accountAddressResource.postAccountIdAccountAddress(
					id, accountAddress));
	}

	@GraphQLField
	public Response createAccountIdAccountAddressBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource ->
				accountAddressResource.postAccountIdAccountAddressBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public AccountGroup createAccountGroup(
			@GraphQLName("accountGroup") AccountGroup accountGroup)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountGroupResource -> accountGroupResource.postAccountGroup(
				accountGroup));
	}

	@GraphQLField
	public Response createAccountGroupBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountGroupResource -> accountGroupResource.postAccountGroupBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Response deleteAccountGroupByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountGroupResource ->
				accountGroupResource.deleteAccountGroupByExternalReferenceCode(
					externalReferenceCode));
	}

	@GraphQLField
	public Response patchAccountGroupByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("accountGroup") AccountGroup accountGroup)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountGroupResource ->
				accountGroupResource.patchAccountGroupByExternalReferenceCode(
					externalReferenceCode, accountGroup));
	}

	@GraphQLField
	public Response deleteAccountGroup(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountGroupResource -> accountGroupResource.deleteAccountGroup(
				id));
	}

	@GraphQLField
	public Response deleteAccountGroupBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountGroupResource ->
				accountGroupResource.deleteAccountGroupBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public Response patchAccountGroup(
			@GraphQLName("id") Long id,
			@GraphQLName("accountGroup") AccountGroup accountGroup)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountGroupResource -> accountGroupResource.patchAccountGroup(
				id, accountGroup));
	}

	@GraphQLField
	public AccountMember createAccountByExternalReferenceCodeAccountMember(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("accountMember") AccountMember accountMember)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountMemberResource ->
				accountMemberResource.
					postAccountByExternalReferenceCodeAccountMember(
						externalReferenceCode, accountMember));
	}

	@GraphQLField
	public Response deleteAccountByExternalReferenceCodeAccountMember(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("userId") Long userId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountMemberResource ->
				accountMemberResource.
					deleteAccountByExternalReferenceCodeAccountMember(
						externalReferenceCode, userId));
	}

	@GraphQLField
	public Response patchAccountByExternalReferenceCodeAccountMember(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("userId") Long userId,
			@GraphQLName("accountMember") AccountMember accountMember)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountMemberResource ->
				accountMemberResource.
					patchAccountByExternalReferenceCodeAccountMember(
						externalReferenceCode, userId, accountMember));
	}

	@GraphQLField
	public AccountMember createAccountIdAccountMember(
			@GraphQLName("id") Long id,
			@GraphQLName("accountMember") AccountMember accountMember)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountMemberResource ->
				accountMemberResource.postAccountIdAccountMember(
					id, accountMember));
	}

	@GraphQLField
	public Response createAccountIdAccountMemberBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountMemberResource ->
				accountMemberResource.postAccountIdAccountMemberBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public Response deleteAccountIdAccountMember(
			@GraphQLName("id") Long id, @GraphQLName("userId") Long userId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountMemberResource ->
				accountMemberResource.deleteAccountIdAccountMember(id, userId));
	}

	@GraphQLField
	public Response patchAccountIdAccountMember(
			@GraphQLName("id") Long id, @GraphQLName("userId") Long userId,
			@GraphQLName("accountMember") AccountMember accountMember)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountMemberResource ->
				accountMemberResource.patchAccountIdAccountMember(
					id, userId, accountMember));
	}

	@GraphQLField
	public AccountOrganization
			createAccountByExternalReferenceCodeAccountOrganization(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("accountOrganization") AccountOrganization
					accountOrganization)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountOrganizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountOrganizationResource ->
				accountOrganizationResource.
					postAccountByExternalReferenceCodeAccountOrganization(
						externalReferenceCode, accountOrganization));
	}

	@GraphQLField
	public Response deleteAccountByExternalReferenceCodeAccountOrganization(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountOrganizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountOrganizationResource ->
				accountOrganizationResource.
					deleteAccountByExternalReferenceCodeAccountOrganization(
						externalReferenceCode, organizationId));
	}

	@GraphQLField
	public AccountOrganization createAccountIdAccountOrganization(
			@GraphQLName("id") Long id,
			@GraphQLName("accountOrganization") AccountOrganization
				accountOrganization)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountOrganizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountOrganizationResource ->
				accountOrganizationResource.postAccountIdAccountOrganization(
					id, accountOrganization));
	}

	@GraphQLField
	public Response createAccountIdAccountOrganizationBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountOrganizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountOrganizationResource ->
				accountOrganizationResource.
					postAccountIdAccountOrganizationBatch(
						id, callbackURL, object));
	}

	@GraphQLField
	public Response deleteAccountIdAccountOrganization(
			@GraphQLName("id") Long id,
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountOrganizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountOrganizationResource ->
				accountOrganizationResource.deleteAccountIdAccountOrganization(
					id, organizationId));
	}

	@GraphQLField
	public User createAccountByExternalReferenceCodeAccountMemberCreateUser(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("user") User user)
		throws Exception {

		return _applyComponentServiceObjects(
			_userResourceComponentServiceObjects,
			this::_populateResourceContext,
			userResource ->
				userResource.
					postAccountByExternalReferenceCodeAccountMemberCreateUser(
						externalReferenceCode, user));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(AccountResource accountResource)
		throws Exception {

		accountResource.setContextAcceptLanguage(_acceptLanguage);
		accountResource.setContextCompany(_company);
		accountResource.setContextHttpServletRequest(_httpServletRequest);
		accountResource.setContextHttpServletResponse(_httpServletResponse);
		accountResource.setContextUriInfo(_uriInfo);
		accountResource.setContextUser(_user);
		accountResource.setGroupLocalService(_groupLocalService);
		accountResource.setRoleLocalService(_roleLocalService);

		accountResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			AccountAddressResource accountAddressResource)
		throws Exception {

		accountAddressResource.setContextAcceptLanguage(_acceptLanguage);
		accountAddressResource.setContextCompany(_company);
		accountAddressResource.setContextHttpServletRequest(
			_httpServletRequest);
		accountAddressResource.setContextHttpServletResponse(
			_httpServletResponse);
		accountAddressResource.setContextUriInfo(_uriInfo);
		accountAddressResource.setContextUser(_user);
		accountAddressResource.setGroupLocalService(_groupLocalService);
		accountAddressResource.setRoleLocalService(_roleLocalService);

		accountAddressResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			AccountGroupResource accountGroupResource)
		throws Exception {

		accountGroupResource.setContextAcceptLanguage(_acceptLanguage);
		accountGroupResource.setContextCompany(_company);
		accountGroupResource.setContextHttpServletRequest(_httpServletRequest);
		accountGroupResource.setContextHttpServletResponse(
			_httpServletResponse);
		accountGroupResource.setContextUriInfo(_uriInfo);
		accountGroupResource.setContextUser(_user);
		accountGroupResource.setGroupLocalService(_groupLocalService);
		accountGroupResource.setRoleLocalService(_roleLocalService);

		accountGroupResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			AccountMemberResource accountMemberResource)
		throws Exception {

		accountMemberResource.setContextAcceptLanguage(_acceptLanguage);
		accountMemberResource.setContextCompany(_company);
		accountMemberResource.setContextHttpServletRequest(_httpServletRequest);
		accountMemberResource.setContextHttpServletResponse(
			_httpServletResponse);
		accountMemberResource.setContextUriInfo(_uriInfo);
		accountMemberResource.setContextUser(_user);
		accountMemberResource.setGroupLocalService(_groupLocalService);
		accountMemberResource.setRoleLocalService(_roleLocalService);

		accountMemberResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			AccountOrganizationResource accountOrganizationResource)
		throws Exception {

		accountOrganizationResource.setContextAcceptLanguage(_acceptLanguage);
		accountOrganizationResource.setContextCompany(_company);
		accountOrganizationResource.setContextHttpServletRequest(
			_httpServletRequest);
		accountOrganizationResource.setContextHttpServletResponse(
			_httpServletResponse);
		accountOrganizationResource.setContextUriInfo(_uriInfo);
		accountOrganizationResource.setContextUser(_user);
		accountOrganizationResource.setGroupLocalService(_groupLocalService);
		accountOrganizationResource.setRoleLocalService(_roleLocalService);

		accountOrganizationResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(UserResource userResource)
		throws Exception {

		userResource.setContextAcceptLanguage(_acceptLanguage);
		userResource.setContextCompany(_company);
		userResource.setContextHttpServletRequest(_httpServletRequest);
		userResource.setContextHttpServletResponse(_httpServletResponse);
		userResource.setContextUriInfo(_uriInfo);
		userResource.setContextUser(_user);
		userResource.setGroupLocalService(_groupLocalService);
		userResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;
	private static ComponentServiceObjects<AccountAddressResource>
		_accountAddressResourceComponentServiceObjects;
	private static ComponentServiceObjects<AccountGroupResource>
		_accountGroupResourceComponentServiceObjects;
	private static ComponentServiceObjects<AccountMemberResource>
		_accountMemberResourceComponentServiceObjects;
	private static ComponentServiceObjects<AccountOrganizationResource>
		_accountOrganizationResourceComponentServiceObjects;
	private static ComponentServiceObjects<UserResource>
		_userResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}