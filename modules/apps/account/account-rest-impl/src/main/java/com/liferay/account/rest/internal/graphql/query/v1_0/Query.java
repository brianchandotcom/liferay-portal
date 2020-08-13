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

package com.liferay.account.rest.internal.graphql.query.v1_0;

import com.liferay.account.rest.dto.v1_0.Account;
import com.liferay.account.rest.dto.v1_0.AccountRole;
import com.liferay.account.rest.dto.v1_0.AccountUser;
import com.liferay.account.rest.resource.v1_0.AccountResource;
import com.liferay.account.rest.resource.v1_0.AccountRoleResource;
import com.liferay.account.rest.resource.v1_0.AccountUserResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Drew Brokke
 * @generated
 */
@Generated("")
public class Query {

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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accounts(filter: ___, keywords: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the accounts. Results can be paginated, filtered, searched, and sorted."
	)
	public AccountPage accounts(
			@GraphQLName("keywords") String keywords,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> new AccountPage(
				accountResource.getAccountsPage(
					keywords,
					_filterBiFunction.apply(accountResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(accountResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {account(accountId: ___){description, domains, id, name, organizationIds, parentAccountId, status}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "")
	public Account account(@GraphQLName("accountId") Long accountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.getAccount(accountId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountRoles(accountId: ___, keywords: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Gets the account's roles")
	public AccountRolePage accountRoles(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountRoleResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountRoleResource -> new AccountRolePage(
				accountRoleResource.getAccountRolesPage(
					accountId, keywords, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(accountRoleResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountUsers(accountId: ___, filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Gets the users assigned to an account")
	public AccountUserPage accountUsers(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountUserResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountUserResource -> new AccountUserPage(
				accountUserResource.getAccountUsersPage(
					accountId, search,
					_filterBiFunction.apply(accountUserResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(accountUserResource, sortsString))));
	}

	@GraphQLTypeExtension(Account.class)
	public class GetAccountUsersPageTypeExtension {

		public GetAccountUsersPageTypeExtension(Account account) {
			_account = account;
		}

		@GraphQLField(description = "Gets the users assigned to an account")
		public AccountUserPage users(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_accountUserResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountUserResource -> new AccountUserPage(
					accountUserResource.getAccountUsersPage(
						_account.getId(), search,
						_filterBiFunction.apply(
							accountUserResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							accountUserResource, sortsString))));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(AccountRole.class)
	public class GetAccountTypeExtension {

		public GetAccountTypeExtension(AccountRole accountRole) {
			_accountRole = accountRole;
		}

		@GraphQLField(description = "")
		public Account account() throws Exception {
			return _applyComponentServiceObjects(
				_accountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountResource -> accountResource.getAccount(
					_accountRole.getAccountId()));
		}

		private AccountRole _accountRole;

	}

	@GraphQLTypeExtension(Account.class)
	public class GetAccountRolesPageTypeExtension {

		public GetAccountRolesPageTypeExtension(Account account) {
			_account = account;
		}

		@GraphQLField(description = "Gets the account's roles")
		public AccountRolePage roles(
				@GraphQLName("keywords") String keywords,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_accountRoleResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountRoleResource -> new AccountRolePage(
					accountRoleResource.getAccountRolesPage(
						_account.getId(), keywords,
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							accountRoleResource, sortsString))));
		}

		private Account _account;

	}

	@GraphQLName("AccountPage")
	public class AccountPage {

		public AccountPage(Page accountPage) {
			actions = accountPage.getActions();
			items = accountPage.getItems();
			lastPage = accountPage.getLastPage();
			page = accountPage.getPage();
			pageSize = accountPage.getPageSize();
			totalCount = accountPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Account> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AccountRolePage")
	public class AccountRolePage {

		public AccountRolePage(Page accountRolePage) {
			actions = accountRolePage.getActions();
			items = accountRolePage.getItems();
			lastPage = accountRolePage.getLastPage();
			page = accountRolePage.getPage();
			pageSize = accountRolePage.getPageSize();
			totalCount = accountRolePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<AccountRole> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AccountUserPage")
	public class AccountUserPage {

		public AccountUserPage(Page accountUserPage) {
			actions = accountUserPage.getActions();
			items = accountUserPage.getItems();
			lastPage = accountUserPage.getLastPage();
			page = accountUserPage.getPage();
			pageSize = accountUserPage.getPageSize();
			totalCount = accountUserPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<AccountUser> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLTypeExtension(Account.class)
	public class ParentAccountAccountIdTypeExtension {

		public ParentAccountAccountIdTypeExtension(Account account) {
			_account = account;
		}

		@GraphQLField(description = "")
		public Account parentAccount() throws Exception {
			return _applyComponentServiceObjects(
				_accountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountResource -> accountResource.getAccount(
					_account.getParentAccountId()));
		}

		private Account _account;

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
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}