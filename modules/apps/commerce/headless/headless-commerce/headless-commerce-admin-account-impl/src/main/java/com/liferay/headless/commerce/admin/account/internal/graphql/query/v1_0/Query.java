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

package com.liferay.headless.commerce.admin.account.internal.graphql.query.v1_0;

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
 * @author Alessio Antonio Rendina
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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accounts(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountPage accounts(
			@GraphQLName("search") String search,
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
					search,
					_filterBiFunction.apply(accountResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(accountResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountByExternalReferenceCode(externalReferenceCode: ___){accountAddresses, accountMembers, accountOrganizations, active, customFields, dateCreated, dateModified, defaultBillingAccountAddressId, defaultShippingAccountAddressId, emailAddresses, externalReferenceCode, id, logoId, logoURL, name, root, taxId, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Account accountByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource ->
				accountResource.getAccountByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {account(id: ___){accountAddresses, accountMembers, accountOrganizations, active, customFields, dateCreated, dateModified, defaultBillingAccountAddressId, defaultShippingAccountAddressId, emailAddresses, externalReferenceCode, id, logoId, logoURL, name, root, taxId, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Account account(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.getAccount(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountAddressByExternalReferenceCode(externalReferenceCode: ___){city, countryISOCode, defaultBilling, defaultShipping, description, externalReferenceCode, id, latitude, longitude, name, phoneNumber, regionISOCode, street1, street2, street3, type, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountAddress accountAddressByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource ->
				accountAddressResource.getAccountAddressByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountAddress(id: ___){city, countryISOCode, defaultBilling, defaultShipping, description, externalReferenceCode, id, latitude, longitude, name, phoneNumber, regionISOCode, street1, street2, street3, type, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountAddress accountAddress(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource -> accountAddressResource.getAccountAddress(
				id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountByExternalReferenceCodeAccountAddresses(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountAddressPage accountByExternalReferenceCodeAccountAddresses(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource -> new AccountAddressPage(
				accountAddressResource.
					getAccountByExternalReferenceCodeAccountAddressesPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountIdAccountAddresses(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountAddressPage accountIdAccountAddresses(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountAddressResource -> new AccountAddressPage(
				accountAddressResource.getAccountIdAccountAddressesPage(
					id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountGroups(filter: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountGroupPage accountGroups(
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountGroupResource -> new AccountGroupPage(
				accountGroupResource.getAccountGroupsPage(
					_filterBiFunction.apply(accountGroupResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						accountGroupResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountGroupByExternalReferenceCode(externalReferenceCode: ___){customFields, externalReferenceCode, id, name}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountGroup accountGroupByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountGroupResource ->
				accountGroupResource.getAccountGroupByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountGroup(id: ___){customFields, externalReferenceCode, id, name}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountGroup accountGroup(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountGroupResource -> accountGroupResource.getAccountGroup(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountByExternalReferenceCodeAccountMembers(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountMemberPage accountByExternalReferenceCodeAccountMembers(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountMemberResource -> new AccountMemberPage(
				accountMemberResource.
					getAccountByExternalReferenceCodeAccountMembersPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountByExternalReferenceCodeAccountMember(externalReferenceCode: ___, userId: ___){accountId, accountRoles, email, externalReferenceCode, name, userExternalReferenceCode, userId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountMember accountByExternalReferenceCodeAccountMember(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("userId") Long userId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountMemberResource ->
				accountMemberResource.
					getAccountByExternalReferenceCodeAccountMember(
						externalReferenceCode, userId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountIdAccountMembers(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountMemberPage accountIdAccountMembers(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountMemberResource -> new AccountMemberPage(
				accountMemberResource.getAccountIdAccountMembersPage(
					id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountIdAccountMember(id: ___, userId: ___){accountId, accountRoles, email, externalReferenceCode, name, userExternalReferenceCode, userId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountMember accountIdAccountMember(
			@GraphQLName("id") Long id, @GraphQLName("userId") Long userId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountMemberResource ->
				accountMemberResource.getAccountIdAccountMember(id, userId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountByExternalReferenceCodeAccountOrganizations(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountOrganizationPage
			accountByExternalReferenceCodeAccountOrganizations(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountOrganizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountOrganizationResource -> new AccountOrganizationPage(
				accountOrganizationResource.
					getAccountByExternalReferenceCodeAccountOrganizationsPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountByExternalReferenceCodeAccountOrganization(externalReferenceCode: ___, organizationId: ___){accountId, name, organizationExternalReferenceCode, organizationId, treePath}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountOrganization
			accountByExternalReferenceCodeAccountOrganization(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountOrganizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountOrganizationResource ->
				accountOrganizationResource.
					getAccountByExternalReferenceCodeAccountOrganization(
						externalReferenceCode, organizationId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountIdAccountOrganizations(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountOrganizationPage accountIdAccountOrganizations(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountOrganizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountOrganizationResource -> new AccountOrganizationPage(
				accountOrganizationResource.
					getAccountIdAccountOrganizationsPage(
						id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountIdAccountOrganization(id: ___, organizationId: ___){accountId, name, organizationExternalReferenceCode, organizationId, treePath}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AccountOrganization accountIdAccountOrganization(
			@GraphQLName("id") Long id,
			@GraphQLName("organizationId") Long organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountOrganizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountOrganizationResource ->
				accountOrganizationResource.getAccountIdAccountOrganization(
					id, organizationId));
	}

	@GraphQLTypeExtension(Account.class)
	public class GetAccountGroupByExternalReferenceCodeTypeExtension {

		public GetAccountGroupByExternalReferenceCodeTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField
		public AccountGroup groupByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_accountGroupResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountGroupResource ->
					accountGroupResource.getAccountGroupByExternalReferenceCode(
						_account.getExternalReferenceCode()));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Account.class)
	public class GetAccountByExternalReferenceCodeAccountMemberTypeExtension {

		public GetAccountByExternalReferenceCodeAccountMemberTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField
		public AccountMember byExternalReferenceCodeAccountMember(
				@GraphQLName("userId") Long userId)
			throws Exception {

			return _applyComponentServiceObjects(
				_accountMemberResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountMemberResource ->
					accountMemberResource.
						getAccountByExternalReferenceCodeAccountMember(
							_account.getExternalReferenceCode(), userId));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Account.class)
	public class
		GetAccountByExternalReferenceCodeAccountOrganizationTypeExtension {

		public GetAccountByExternalReferenceCodeAccountOrganizationTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField
		public AccountOrganization byExternalReferenceCodeAccountOrganization(
				@GraphQLName("organizationId") Long organizationId)
			throws Exception {

			return _applyComponentServiceObjects(
				_accountOrganizationResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountOrganizationResource ->
					accountOrganizationResource.
						getAccountByExternalReferenceCodeAccountOrganization(
							_account.getExternalReferenceCode(),
							organizationId));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Account.class)
	public class
		GetAccountByExternalReferenceCodeAccountMembersPageTypeExtension {

		public GetAccountByExternalReferenceCodeAccountMembersPageTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField
		public AccountMemberPage byExternalReferenceCodeAccountMembers(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_accountMemberResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountMemberResource -> new AccountMemberPage(
					accountMemberResource.
						getAccountByExternalReferenceCodeAccountMembersPage(
							_account.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Account.class)
	public class
		GetAccountByExternalReferenceCodeAccountAddressesPageTypeExtension {

		public GetAccountByExternalReferenceCodeAccountAddressesPageTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField
		public AccountAddressPage byExternalReferenceCodeAccountAddresses(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_accountAddressResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountAddressResource -> new AccountAddressPage(
					accountAddressResource.
						getAccountByExternalReferenceCodeAccountAddressesPage(
							_account.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Account.class)
	public class
		GetAccountByExternalReferenceCodeAccountOrganizationsPageTypeExtension {

		public GetAccountByExternalReferenceCodeAccountOrganizationsPageTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField
		public AccountOrganizationPage
				byExternalReferenceCodeAccountOrganizations(
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_accountOrganizationResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountOrganizationResource -> new AccountOrganizationPage(
					accountOrganizationResource.
						getAccountByExternalReferenceCodeAccountOrganizationsPage(
							_account.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(AccountAddress.class)
	public class GetAccountByExternalReferenceCodeTypeExtension {

		public GetAccountByExternalReferenceCodeTypeExtension(
			AccountAddress accountAddress) {

			_accountAddress = accountAddress;
		}

		@GraphQLField
		public Account accountByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_accountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountResource ->
					accountResource.getAccountByExternalReferenceCode(
						_accountAddress.getExternalReferenceCode()));
		}

		private AccountAddress _accountAddress;

	}

	@GraphQLTypeExtension(Account.class)
	public class GetAccountAddressByExternalReferenceCodeTypeExtension {

		public GetAccountAddressByExternalReferenceCodeTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField
		public AccountAddress addressByExternalReferenceCode()
			throws Exception {

			return _applyComponentServiceObjects(
				_accountAddressResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountAddressResource ->
					accountAddressResource.
						getAccountAddressByExternalReferenceCode(
							_account.getExternalReferenceCode()));
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

	@GraphQLName("AccountAddressPage")
	public class AccountAddressPage {

		public AccountAddressPage(Page accountAddressPage) {
			actions = accountAddressPage.getActions();

			items = accountAddressPage.getItems();
			lastPage = accountAddressPage.getLastPage();
			page = accountAddressPage.getPage();
			pageSize = accountAddressPage.getPageSize();
			totalCount = accountAddressPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<AccountAddress> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AccountGroupPage")
	public class AccountGroupPage {

		public AccountGroupPage(Page accountGroupPage) {
			actions = accountGroupPage.getActions();

			items = accountGroupPage.getItems();
			lastPage = accountGroupPage.getLastPage();
			page = accountGroupPage.getPage();
			pageSize = accountGroupPage.getPageSize();
			totalCount = accountGroupPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<AccountGroup> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AccountMemberPage")
	public class AccountMemberPage {

		public AccountMemberPage(Page accountMemberPage) {
			actions = accountMemberPage.getActions();

			items = accountMemberPage.getItems();
			lastPage = accountMemberPage.getLastPage();
			page = accountMemberPage.getPage();
			pageSize = accountMemberPage.getPageSize();
			totalCount = accountMemberPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<AccountMember> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AccountOrganizationPage")
	public class AccountOrganizationPage {

		public AccountOrganizationPage(Page accountOrganizationPage) {
			actions = accountOrganizationPage.getActions();

			items = accountOrganizationPage.getItems();
			lastPage = accountOrganizationPage.getLastPage();
			page = accountOrganizationPage.getPage();
			pageSize = accountOrganizationPage.getPageSize();
			totalCount = accountOrganizationPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<AccountOrganization> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

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