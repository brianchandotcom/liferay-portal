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

package com.liferay.account.rest.internal.graphql.mutation.v1_0;

import com.liferay.account.rest.dto.v1_0.Account;
import com.liferay.account.rest.dto.v1_0.AccountRole;
import com.liferay.account.rest.dto.v1_0.AccountUser;
import com.liferay.account.rest.resource.v1_0.AccountResource;
import com.liferay.account.rest.resource.v1_0.AccountRoleResource;
import com.liferay.account.rest.resource.v1_0.AccountUserResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Drew Brokke
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

	public static void setAccountRoleResourceComponentServiceObjects(
		ComponentServiceObjects<AccountRoleResource>
			accountRoleResourceComponentServiceObjects) {

		_accountRoleResourceComponentServiceObjects =
			accountRoleResourceComponentServiceObjects;
	}

	public static void setAccountUserResourceComponentServiceObjects(
		ComponentServiceObjects<AccountUserResource>
			accountUserResourceComponentServiceObjects) {

		_accountUserResourceComponentServiceObjects =
			accountUserResourceComponentServiceObjects;
	}

	@GraphQLField(description = "Creates a new account")
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

	@GraphQLField(description = "Deletes an account.")
	public boolean deleteAccount(@GraphQLName("accountId") Long accountId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.deleteAccount(accountId));

		return true;
	}

	@GraphQLField
	public Response deleteAccountBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.deleteAccountBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Updates the account with information sent in the request body. Only the provided fields are updated."
	)
	public Account patchAccount(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("account") Account account)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.patchAccount(
				accountId, account));
	}

	@GraphQLField(
		description = "Replaces the account with information sent in the request body. Any missing fields are deleted unless they are required."
	)
	public Account updateAccount(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("account") Account account)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.putAccount(accountId, account));
	}

	@GraphQLField
	public Response updateAccountBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.putAccountBatch(
				callbackURL, object));
	}

	@GraphQLField(description = "Adds a role for the account")
	public AccountRole createAccountRole(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("accountRole") AccountRole accountRole)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountRoleResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountRoleResource -> accountRoleResource.postAccountRole(
				accountId, accountRole));
	}

	@GraphQLField(description = "Unassigns account users to the account role")
	public boolean deleteAccountRoleUserAssociation(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("accountRoleId") Long accountRoleId,
			@GraphQLName("accountUserId") Long accountUserId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_accountRoleResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountRoleResource ->
				accountRoleResource.deleteAccountRoleUserAssociation(
					accountId, accountRoleId, accountUserId));

		return true;
	}

	@GraphQLField(description = "Assigns account users to the account role")
	public boolean createAccountRoleUserAssociation(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("accountRoleId") Long accountRoleId,
			@GraphQLName("accountUserId") Long accountUserId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_accountRoleResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountRoleResource ->
				accountRoleResource.postAccountRoleUserAssociation(
					accountId, accountRoleId, accountUserId));

		return true;
	}

	@GraphQLField(
		description = "Creates a user and assigns them to the account"
	)
	public AccountUser createAccountUser(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("accountUser") AccountUser accountUser)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountUserResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountUserResource -> accountUserResource.postAccountUser(
				accountId, accountUser));
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
	}

	private void _populateResourceContext(
			AccountRoleResource accountRoleResource)
		throws Exception {

		accountRoleResource.setContextAcceptLanguage(_acceptLanguage);
		accountRoleResource.setContextCompany(_company);
		accountRoleResource.setContextHttpServletRequest(_httpServletRequest);
		accountRoleResource.setContextHttpServletResponse(_httpServletResponse);
		accountRoleResource.setContextUriInfo(_uriInfo);
		accountRoleResource.setContextUser(_user);
		accountRoleResource.setGroupLocalService(_groupLocalService);
		accountRoleResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			AccountUserResource accountUserResource)
		throws Exception {

		accountUserResource.setContextAcceptLanguage(_acceptLanguage);
		accountUserResource.setContextCompany(_company);
		accountUserResource.setContextHttpServletRequest(_httpServletRequest);
		accountUserResource.setContextHttpServletResponse(_httpServletResponse);
		accountUserResource.setContextUriInfo(_uriInfo);
		accountUserResource.setContextUser(_user);
		accountUserResource.setGroupLocalService(_groupLocalService);
		accountUserResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;
	private static ComponentServiceObjects<AccountRoleResource>
		_accountRoleResourceComponentServiceObjects;
	private static ComponentServiceObjects<AccountUserResource>
		_accountUserResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}