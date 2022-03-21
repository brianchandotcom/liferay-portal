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

package com.liferay.headless.admin.user.internal.graphql.query.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.dto.v1_0.AccountRole;
import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.dto.v1_0.Role;
import com.liferay.headless.admin.user.dto.v1_0.Segment;
import com.liferay.headless.admin.user.dto.v1_0.SegmentUser;
import com.liferay.headless.admin.user.dto.v1_0.Site;
import com.liferay.headless.admin.user.dto.v1_0.Subscription;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.dto.v1_0.UserGroup;
import com.liferay.headless.admin.user.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.resource.v1_0.AccountRoleResource;
import com.liferay.headless.admin.user.resource.v1_0.EmailAddressResource;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.headless.admin.user.resource.v1_0.PhoneResource;
import com.liferay.headless.admin.user.resource.v1_0.PostalAddressResource;
import com.liferay.headless.admin.user.resource.v1_0.RoleResource;
import com.liferay.headless.admin.user.resource.v1_0.SegmentResource;
import com.liferay.headless.admin.user.resource.v1_0.SegmentUserResource;
import com.liferay.headless.admin.user.resource.v1_0.SiteResource;
import com.liferay.headless.admin.user.resource.v1_0.SubscriptionResource;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.headless.admin.user.resource.v1_0.UserGroupResource;
import com.liferay.headless.admin.user.resource.v1_0.WebUrlResource;
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

import javax.validation.constraints.NotEmpty;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
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

	public static void setEmailAddressResourceComponentServiceObjects(
		ComponentServiceObjects<EmailAddressResource>
			emailAddressResourceComponentServiceObjects) {

		_emailAddressResourceComponentServiceObjects =
			emailAddressResourceComponentServiceObjects;
	}

	public static void setOrganizationResourceComponentServiceObjects(
		ComponentServiceObjects<OrganizationResource>
			organizationResourceComponentServiceObjects) {

		_organizationResourceComponentServiceObjects =
			organizationResourceComponentServiceObjects;
	}

	public static void setPhoneResourceComponentServiceObjects(
		ComponentServiceObjects<PhoneResource>
			phoneResourceComponentServiceObjects) {

		_phoneResourceComponentServiceObjects =
			phoneResourceComponentServiceObjects;
	}

	public static void setPostalAddressResourceComponentServiceObjects(
		ComponentServiceObjects<PostalAddressResource>
			postalAddressResourceComponentServiceObjects) {

		_postalAddressResourceComponentServiceObjects =
			postalAddressResourceComponentServiceObjects;
	}

	public static void setRoleResourceComponentServiceObjects(
		ComponentServiceObjects<RoleResource>
			roleResourceComponentServiceObjects) {

		_roleResourceComponentServiceObjects =
			roleResourceComponentServiceObjects;
	}

	public static void setSegmentResourceComponentServiceObjects(
		ComponentServiceObjects<SegmentResource>
			segmentResourceComponentServiceObjects) {

		_segmentResourceComponentServiceObjects =
			segmentResourceComponentServiceObjects;
	}

	public static void setSegmentUserResourceComponentServiceObjects(
		ComponentServiceObjects<SegmentUserResource>
			segmentUserResourceComponentServiceObjects) {

		_segmentUserResourceComponentServiceObjects =
			segmentUserResourceComponentServiceObjects;
	}

	public static void setSiteResourceComponentServiceObjects(
		ComponentServiceObjects<SiteResource>
			siteResourceComponentServiceObjects) {

		_siteResourceComponentServiceObjects =
			siteResourceComponentServiceObjects;
	}

	public static void setSubscriptionResourceComponentServiceObjects(
		ComponentServiceObjects<SubscriptionResource>
			subscriptionResourceComponentServiceObjects) {

		_subscriptionResourceComponentServiceObjects =
			subscriptionResourceComponentServiceObjects;
	}

	public static void setUserAccountResourceComponentServiceObjects(
		ComponentServiceObjects<UserAccountResource>
			userAccountResourceComponentServiceObjects) {

		_userAccountResourceComponentServiceObjects =
			userAccountResourceComponentServiceObjects;
	}

	public static void setUserGroupResourceComponentServiceObjects(
		ComponentServiceObjects<UserGroupResource>
			userGroupResourceComponentServiceObjects) {

		_userGroupResourceComponentServiceObjects =
			userGroupResourceComponentServiceObjects;
	}

	public static void setWebUrlResourceComponentServiceObjects(
		ComponentServiceObjects<WebUrlResource>
			webUrlResourceComponentServiceObjects) {

		_webUrlResourceComponentServiceObjects =
			webUrlResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accounts(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the accounts. Results can be paginated, filtered, searched, and sorted."
	)
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountByExternalReferenceCode(externalReferenceCode: ___){accountUserAccounts, actions, description, domains, externalReferenceCode, id, name, numberOfUsers, organizationIds, parentAccountId, status, type}}"}' -u 'test@liferay.com:test'
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {account(accountId: ___){accountUserAccounts, actions, description, domains, externalReferenceCode, id, name, numberOfUsers, organizationIds, parentAccountId, status, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {organizationAccounts(filter: ___, organizationId: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the organization's members (accounts). Results can be paginated, filtered, searched, and sorted."
	)
	public AccountPage organizationAccounts(
			@GraphQLName("organizationId") String organizationId,
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
				accountResource.getOrganizationAccountsPage(
					organizationId, search,
					_filterBiFunction.apply(accountResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(accountResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRoles(accountExternalReferenceCode: ___, userAccountExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Gets a user's account roles by their external reference code from an account by external reference code"
	)
	public AccountRolePage
			accountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRoles(
				@GraphQLName("accountExternalReferenceCode") String
					accountExternalReferenceCode,
				@GraphQLName("userAccountExternalReferenceCode") String
					userAccountExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountRoleResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountRoleResource -> new AccountRolePage(
				accountRoleResource.
					getAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage(
						accountExternalReferenceCode,
						userAccountExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountAccountRolesByExternalReferenceCode(externalReferenceCode: ___, filter: ___, keywords: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Gets the account's roles")
	public AccountRolePage accountAccountRolesByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountRoleResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountRoleResource -> new AccountRolePage(
				accountRoleResource.
					getAccountAccountRolesByExternalReferenceCodePage(
						externalReferenceCode, keywords,
						_filterBiFunction.apply(
							accountRoleResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							accountRoleResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountByExternalReferenceCodeUserAccountByEmailAddressAccountRoles(emailAddress: ___, externalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Gets a user's account roles by their email address from an account by external reference code"
	)
	public AccountRolePage
			accountByExternalReferenceCodeUserAccountByEmailAddressAccountRoles(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("emailAddress") String emailAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountRoleResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountRoleResource -> new AccountRolePage(
				accountRoleResource.
					getAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage(
						externalReferenceCode, emailAddress)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountAccountRoles(accountId: ___, filter: ___, keywords: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Gets the account's roles")
	public AccountRolePage accountAccountRoles(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountRoleResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountRoleResource -> new AccountRolePage(
				accountRoleResource.getAccountAccountRolesPage(
					accountId, keywords,
					_filterBiFunction.apply(accountRoleResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(accountRoleResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {emailAddress(emailAddressId: ___){emailAddress, id, primary, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the email address.")
	public EmailAddress emailAddress(
			@GraphQLName("emailAddressId") Long emailAddressId)
		throws Exception {

		return _applyComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			emailAddressResource -> emailAddressResource.getEmailAddress(
				emailAddressId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {organizationEmailAddresses(organizationId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the organization's email addresses.")
	public EmailAddressPage organizationEmailAddresses(
			@GraphQLName("organizationId") String organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			emailAddressResource -> new EmailAddressPage(
				emailAddressResource.getOrganizationEmailAddressesPage(
					organizationId)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {userAccountEmailAddresses(userAccountId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the user's email addresses.")
	public EmailAddressPage userAccountEmailAddresses(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			emailAddressResource -> new EmailAddressPage(
				emailAddressResource.getUserAccountEmailAddressesPage(
					userAccountId)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountByExternalReferenceCodeOrganizations(externalReferenceCode: ___, filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the account's organizations. Results can be paginated, filtered, searched, and sorted."
	)
	public OrganizationPage accountByExternalReferenceCodeOrganizations(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> new OrganizationPage(
				organizationResource.
					getAccountByExternalReferenceCodeOrganizationsPage(
						externalReferenceCode, search,
						_filterBiFunction.apply(
							organizationResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							organizationResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountOrganizations(accountId: ___, filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the account's organizations. Results can be paginated, filtered, searched, and sorted."
	)
	public OrganizationPage accountOrganizations(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> new OrganizationPage(
				organizationResource.getAccountOrganizationsPage(
					accountId, search,
					_filterBiFunction.apply(organizationResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						organizationResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {organizations(filter: ___, flatten: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the organizations. Results can be paginated, filtered, searched, and sorted."
	)
	public OrganizationPage organizations(
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> new OrganizationPage(
				organizationResource.getOrganizationsPage(
					flatten, search,
					_filterBiFunction.apply(organizationResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						organizationResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {organizationByExternalReferenceCode(externalReferenceCode: ___){actions, childOrganizations, comment, customFields, dateCreated, dateModified, externalReferenceCode, id, image, keywords, location, name, numberOfAccounts, numberOfOrganizations, numberOfUsers, organizationAccounts, organizationContactInformation, parentOrganization, services, userAccounts}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Organization organizationByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource ->
				organizationResource.getOrganizationByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {organization(organizationId: ___){actions, childOrganizations, comment, customFields, dateCreated, dateModified, externalReferenceCode, id, image, keywords, location, name, numberOfAccounts, numberOfOrganizations, numberOfUsers, organizationAccounts, organizationContactInformation, parentOrganization, services, userAccounts}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the organization.")
	public Organization organization(
			@GraphQLName("organizationId") String organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> organizationResource.getOrganization(
				organizationId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {organizationChildOrganizations(filter: ___, flatten: ___, organizationId: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the parent organization's child organizations. Results can be paginated, filtered, searched, and sorted."
	)
	public OrganizationPage organizationChildOrganizations(
			@GraphQLName("organizationId") String organizationId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> new OrganizationPage(
				organizationResource.getOrganizationChildOrganizationsPage(
					organizationId, flatten, search,
					_filterBiFunction.apply(organizationResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						organizationResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {organizationOrganizations(filter: ___, flatten: ___, page: ___, pageSize: ___, parentOrganizationId: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the parent organization's child organizations. Results can be paginated, filtered, searched, and sorted."
	)
	public OrganizationPage organizationOrganizations(
			@GraphQLName("parentOrganizationId") String parentOrganizationId,
			@GraphQLName("flatten") Boolean flatten,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_organizationResourceComponentServiceObjects,
			this::_populateResourceContext,
			organizationResource -> new OrganizationPage(
				organizationResource.getOrganizationOrganizationsPage(
					parentOrganizationId, flatten, search,
					_filterBiFunction.apply(organizationResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						organizationResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {organizationPhones(organizationId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the organization's phone numbers.")
	public PhonePage organizationPhones(
			@GraphQLName("organizationId") String organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_phoneResourceComponentServiceObjects,
			this::_populateResourceContext,
			phoneResource -> new PhonePage(
				phoneResource.getOrganizationPhonesPage(organizationId)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {phone(phoneId: ___){extension, id, phoneNumber, phoneType, primary}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the phone number.")
	public Phone phone(@GraphQLName("phoneId") Long phoneId) throws Exception {
		return _applyComponentServiceObjects(
			_phoneResourceComponentServiceObjects,
			this::_populateResourceContext,
			phoneResource -> phoneResource.getPhone(phoneId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {userAccountPhones(userAccountId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the user's phone numbers.")
	public PhonePage userAccountPhones(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_phoneResourceComponentServiceObjects,
			this::_populateResourceContext,
			phoneResource -> new PhonePage(
				phoneResource.getUserAccountPhonesPage(userAccountId)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {organizationPostalAddresses(organizationId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the organization's postal addresses."
	)
	public PostalAddressPage organizationPostalAddresses(
			@GraphQLName("organizationId") String organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			postalAddressResource -> new PostalAddressPage(
				postalAddressResource.getOrganizationPostalAddressesPage(
					organizationId)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {postalAddress(postalAddressId: ___){addressCountry, addressCountry_i18n, addressLocality, addressRegion, addressType, id, postalCode, primary, streetAddressLine1, streetAddressLine2, streetAddressLine3}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the postal address.")
	public PostalAddress postalAddress(
			@GraphQLName("postalAddressId") Long postalAddressId)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			postalAddressResource -> postalAddressResource.getPostalAddress(
				postalAddressId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {userAccountPostalAddresses(userAccountId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the user's postal addresses.")
	public PostalAddressPage userAccountPostalAddresses(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			postalAddressResource -> new PostalAddressPage(
				postalAddressResource.getUserAccountPostalAddressesPage(
					userAccountId)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {roles(page: ___, pageSize: ___, types: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the portal instance's roles. Results can be paginated."
	)
	public RolePage roles(
			@GraphQLName("types") Integer[] types,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			this::_populateResourceContext,
			roleResource -> new RolePage(
				roleResource.getRolesPage(
					types, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {role(roleId: ___){actions, availableLanguages, creator, dateCreated, dateModified, description, description_i18n, id, name, name_i18n, roleType}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the role.")
	public Role role(@GraphQLName("roleId") Long roleId) throws Exception {
		return _applyComponentServiceObjects(
			_roleResourceComponentServiceObjects,
			this::_populateResourceContext,
			roleResource -> roleResource.getRole(roleId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {segments(page: ___, pageSize: ___, siteKey: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Gets a site's segments.")
	public SegmentPage segments(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_segmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			segmentResource -> new SegmentPage(
				segmentResource.getSiteSegmentsPage(
					Long.valueOf(siteKey), Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {userAccountSegments(siteKey: ___, userAccountId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Gets a user's segments. The set of available headers are: Accept-Language (string), Host (string), User-Agent (string), X-Browser (string), X-Cookies (collection string), X-Device-Brand (string), X-Device-Model (string), X-Device-Screen-Resolution-Height (double), X-Device-Screen-Resolution-Width (double), X-Last-Sign-In-Date-Time (date time) and X-Signed-In (boolean). Local date will be always present in the request."
	)
	public SegmentPage userAccountSegments(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_segmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			segmentResource -> new SegmentPage(
				segmentResource.getSiteUserAccountSegmentsPage(
					Long.valueOf(siteKey), userAccountId)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {segmentUserAccounts(page: ___, pageSize: ___, segmentId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Gets a segment's users.")
	public SegmentUserPage segmentUserAccounts(
			@GraphQLName("segmentId") Long segmentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_segmentUserResourceComponentServiceObjects,
			this::_populateResourceContext,
			segmentUserResource -> new SegmentUserPage(
				segmentUserResource.getSegmentUserAccountsPage(
					segmentId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {myUserAccountSites(page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SitePage myUserAccountSites(
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_siteResourceComponentServiceObjects,
			this::_populateResourceContext,
			siteResource -> new SitePage(
				siteResource.getMyUserAccountSitesPage(
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byFriendlyUrlPath(friendlyUrlPath: ___){availableLanguages, creator, description, description_i18n, friendlyUrlPath, id, key, membershipType, name, name_i18n, parentSiteId, sites}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Site byFriendlyUrlPath(
			@GraphQLName("friendlyUrlPath") String friendlyUrlPath)
		throws Exception {

		return _applyComponentServiceObjects(
			_siteResourceComponentServiceObjects,
			this::_populateResourceContext,
			siteResource -> siteResource.getSiteByFriendlyUrlPath(
				friendlyUrlPath));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {site(siteKey: ___){availableLanguages, creator, description, description_i18n, friendlyUrlPath, id, key, membershipType, name, name_i18n, parentSiteId, sites}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Site site(@GraphQLName("siteKey") @NotEmpty String siteKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_siteResourceComponentServiceObjects,
			this::_populateResourceContext,
			siteResource -> siteResource.getSite(Long.valueOf(siteKey)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {myUserAccountSubscriptions(contentType: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SubscriptionPage myUserAccountSubscriptions(
			@GraphQLName("contentType") String contentType,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_subscriptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			subscriptionResource -> new SubscriptionPage(
				subscriptionResource.getMyUserAccountSubscriptionsPage(
					contentType, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {myUserAccountSubscription(subscriptionId: ___){contentId, contentType, dateCreated, dateModified, frequency, id, siteId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Subscription myUserAccountSubscription(
			@GraphQLName("subscriptionId") Long subscriptionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_subscriptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			subscriptionResource ->
				subscriptionResource.getMyUserAccountSubscription(
					subscriptionId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountUserAccountsByExternalReferenceCode(externalReferenceCode: ___, filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Gets the users assigned to an account")
	public UserAccountPage accountUserAccountsByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> new UserAccountPage(
				userAccountResource.
					getAccountUserAccountsByExternalReferenceCodePage(
						externalReferenceCode, search,
						_filterBiFunction.apply(
							userAccountResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							userAccountResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {accountUserAccounts(accountId: ___, filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Gets the users assigned to an account")
	public UserAccountPage accountUserAccounts(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> new UserAccountPage(
				userAccountResource.getAccountUserAccountsPage(
					accountId, search,
					_filterBiFunction.apply(userAccountResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(userAccountResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {myUserAccount{accountBriefs, actions, additionalName, alternateName, birthDate, customFields, dashboardURL, dateCreated, dateModified, emailAddress, externalReferenceCode, familyName, givenName, honorificPrefix, honorificSuffix, id, image, jobTitle, keywords, lastLoginDate, name, organizationBriefs, password, profileURL, roleBriefs, siteBriefs, userAccountContactInformation}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves information about the user who made the request."
	)
	public UserAccount myUserAccount() throws Exception {
		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.getMyUserAccount());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {organizationUserAccounts(filter: ___, organizationId: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the organization's members (users). Results can be paginated, filtered, searched, and sorted."
	)
	public UserAccountPage organizationUserAccounts(
			@GraphQLName("organizationId") String organizationId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> new UserAccountPage(
				userAccountResource.getOrganizationUserAccountsPage(
					organizationId, search,
					_filterBiFunction.apply(userAccountResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(userAccountResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {siteUserAccounts(filter: ___, page: ___, pageSize: ___, search: ___, siteKey: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the site members' user accounts. Results can be paginated, filtered, searched, and sorted."
	)
	public UserAccountPage siteUserAccounts(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> new UserAccountPage(
				userAccountResource.getSiteUserAccountsPage(
					Long.valueOf(siteKey), search,
					_filterBiFunction.apply(userAccountResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(userAccountResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {userAccounts(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the user accounts. Results can be paginated, filtered, searched, and sorted."
	)
	public UserAccountPage userAccounts(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> new UserAccountPage(
				userAccountResource.getUserAccountsPage(
					search,
					_filterBiFunction.apply(userAccountResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(userAccountResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {userAccountByExternalReferenceCode(externalReferenceCode: ___){accountBriefs, actions, additionalName, alternateName, birthDate, customFields, dashboardURL, dateCreated, dateModified, emailAddress, externalReferenceCode, familyName, givenName, honorificPrefix, honorificSuffix, id, image, jobTitle, keywords, lastLoginDate, name, organizationBriefs, password, profileURL, roleBriefs, siteBriefs, userAccountContactInformation}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public UserAccount userAccountByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource ->
				userAccountResource.getUserAccountByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {userAccount(userAccountId: ___){accountBriefs, actions, additionalName, alternateName, birthDate, customFields, dashboardURL, dateCreated, dateModified, emailAddress, externalReferenceCode, familyName, givenName, honorificPrefix, honorificSuffix, id, image, jobTitle, keywords, lastLoginDate, name, organizationBriefs, password, profileURL, roleBriefs, siteBriefs, userAccountContactInformation}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the user account.")
	public UserAccount userAccount(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.getUserAccount(
				userAccountId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {userGroups(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public UserGroupPage userGroups(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_userGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			userGroupResource -> new UserGroupPage(
				userGroupResource.getUserGroupsPage(
					search,
					_filterBiFunction.apply(userGroupResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(userGroupResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {userGroupByExternalReferenceCode(externalReferenceCode: ___){actions, description, externalReferenceCode, id, name, usersCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public UserGroup userGroupByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_userGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			userGroupResource ->
				userGroupResource.getUserGroupByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {userGroup(userGroupId: ___){actions, description, externalReferenceCode, id, name, usersCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public UserGroup userGroup(@GraphQLName("userGroupId") Long userGroupId)
		throws Exception {

		return _applyComponentServiceObjects(
			_userGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			userGroupResource -> userGroupResource.getUserGroup(userGroupId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {organizationWebUrls(organizationId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the organization's URLs.")
	public WebUrlPage organizationWebUrls(
			@GraphQLName("organizationId") String organizationId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webUrlResourceComponentServiceObjects,
			this::_populateResourceContext,
			webUrlResource -> new WebUrlPage(
				webUrlResource.getOrganizationWebUrlsPage(organizationId)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {userAccountWebUrls(userAccountId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the user's URLs.")
	public WebUrlPage userAccountWebUrls(
			@GraphQLName("userAccountId") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webUrlResourceComponentServiceObjects,
			this::_populateResourceContext,
			webUrlResource -> new WebUrlPage(
				webUrlResource.getUserAccountWebUrlsPage(userAccountId)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {webUrl(webUrlId: ___){id, primary, url, urlType}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the web URL.")
	public WebUrl webUrl(@GraphQLName("webUrlId") Long webUrlId)
		throws Exception {

		return _applyComponentServiceObjects(
			_webUrlResourceComponentServiceObjects,
			this::_populateResourceContext,
			webUrlResource -> webUrlResource.getWebUrl(webUrlId));
	}

	@GraphQLTypeExtension(Account.class)
	public class GetAccountUserAccountsPageTypeExtension {

		public GetAccountUserAccountsPageTypeExtension(Account account) {
			_account = account;
		}

		@GraphQLField(description = "Gets the users assigned to an account")
		public UserAccountPage userAccounts(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_userAccountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				userAccountResource -> new UserAccountPage(
					userAccountResource.getAccountUserAccountsPage(
						_account.getId(), search,
						_filterBiFunction.apply(
							userAccountResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							userAccountResource, sortsString))));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(UserAccount.class)
	public class GetUserAccountPhonesPageTypeExtension {

		public GetUserAccountPhonesPageTypeExtension(UserAccount userAccount) {
			_userAccount = userAccount;
		}

		@GraphQLField(description = "Retrieves the user's phone numbers.")
		public PhonePage phones() throws Exception {
			return _applyComponentServiceObjects(
				_phoneResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				phoneResource -> new PhonePage(
					phoneResource.getUserAccountPhonesPage(
						_userAccount.getId())));
		}

		private UserAccount _userAccount;

	}

	@GraphQLTypeExtension(Account.class)
	public class GetAccountAccountRolesPageTypeExtension {

		public GetAccountAccountRolesPageTypeExtension(Account account) {
			_account = account;
		}

		@GraphQLField(description = "Gets the account's roles")
		public AccountRolePage accountRoles(
				@GraphQLName("keywords") String keywords,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_accountRoleResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountRoleResource -> new AccountRolePage(
					accountRoleResource.getAccountAccountRolesPage(
						_account.getId(), keywords,
						_filterBiFunction.apply(
							accountRoleResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							accountRoleResource, sortsString))));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Account.class)
	public class
		GetAccountAccountRolesByExternalReferenceCodePageTypeExtension {

		public GetAccountAccountRolesByExternalReferenceCodePageTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField(description = "Gets the account's roles")
		public AccountRolePage accountRolesByExternalReferenceCode(
				@GraphQLName("keywords") String keywords,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_accountRoleResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountRoleResource -> new AccountRolePage(
					accountRoleResource.
						getAccountAccountRolesByExternalReferenceCodePage(
							_account.getExternalReferenceCode(), keywords,
							_filterBiFunction.apply(
								accountRoleResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								accountRoleResource, sortsString))));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Organization.class)
	public class GetAccountByExternalReferenceCodeTypeExtension {

		public GetAccountByExternalReferenceCodeTypeExtension(
			Organization organization) {

			_organization = organization;
		}

		@GraphQLField
		public Account accountByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_accountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountResource ->
					accountResource.getAccountByExternalReferenceCode(
						_organization.getExternalReferenceCode()));
		}

		private Organization _organization;

	}

	@GraphQLTypeExtension(Organization.class)
	public class GetOrganizationAccountsPageTypeExtension {

		public GetOrganizationAccountsPageTypeExtension(
			Organization organization) {

			_organization = organization;
		}

		@GraphQLField(
			description = "Retrieves the organization's members (accounts). Results can be paginated, filtered, searched, and sorted."
		)
		public AccountPage accounts(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_accountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountResource -> new AccountPage(
					accountResource.getOrganizationAccountsPage(
						_organization.getId(), search,
						_filterBiFunction.apply(accountResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(accountResource, sortsString))));
		}

		private Organization _organization;

	}

	@GraphQLTypeExtension(Site.class)
	public class GetSiteUserAccountSegmentsPageTypeExtension {

		public GetSiteUserAccountSegmentsPageTypeExtension(Site site) {
			_site = site;
		}

		@GraphQLField(
			description = "Gets a user's segments. The set of available headers are: Accept-Language (string), Host (string), User-Agent (string), X-Browser (string), X-Cookies (collection string), X-Device-Brand (string), X-Device-Model (string), X-Device-Screen-Resolution-Height (double), X-Device-Screen-Resolution-Width (double), X-Last-Sign-In-Date-Time (date time) and X-Signed-In (boolean). Local date will be always present in the request."
		)
		public SegmentPage userAccountSegments(
				@GraphQLName("userAccountId") Long userAccountId)
			throws Exception {

			return _applyComponentServiceObjects(
				_segmentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				segmentResource -> new SegmentPage(
					segmentResource.getSiteUserAccountSegmentsPage(
						_site.getId(), userAccountId)));
		}

		private Site _site;

	}

	@GraphQLTypeExtension(UserAccount.class)
	public class GetUserAccountPostalAddressesPageTypeExtension {

		public GetUserAccountPostalAddressesPageTypeExtension(
			UserAccount userAccount) {

			_userAccount = userAccount;
		}

		@GraphQLField(description = "Retrieves the user's postal addresses.")
		public PostalAddressPage postalAddresses() throws Exception {
			return _applyComponentServiceObjects(
				_postalAddressResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				postalAddressResource -> new PostalAddressPage(
					postalAddressResource.getUserAccountPostalAddressesPage(
						_userAccount.getId())));
		}

		private UserAccount _userAccount;

	}

	@GraphQLTypeExtension(UserAccount.class)
	public class GetUserAccountEmailAddressesPageTypeExtension {

		public GetUserAccountEmailAddressesPageTypeExtension(
			UserAccount userAccount) {

			_userAccount = userAccount;
		}

		@GraphQLField(description = "Retrieves the user's email addresses.")
		public EmailAddressPage emailAddresses() throws Exception {
			return _applyComponentServiceObjects(
				_emailAddressResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				emailAddressResource -> new EmailAddressPage(
					emailAddressResource.getUserAccountEmailAddressesPage(
						_userAccount.getId())));
		}

		private UserAccount _userAccount;

	}

	@GraphQLTypeExtension(Account.class)
	public class GetUserAccountByExternalReferenceCodeTypeExtension {

		public GetUserAccountByExternalReferenceCodeTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField
		public UserAccount userAccountByExternalReferenceCode()
			throws Exception {

			return _applyComponentServiceObjects(
				_userAccountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				userAccountResource ->
					userAccountResource.getUserAccountByExternalReferenceCode(
						_account.getExternalReferenceCode()));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Organization.class)
	public class GetOrganizationPostalAddressesPageTypeExtension {

		public GetOrganizationPostalAddressesPageTypeExtension(
			Organization organization) {

			_organization = organization;
		}

		@GraphQLField(
			description = "Retrieves the organization's postal addresses."
		)
		public PostalAddressPage postalAddresses() throws Exception {
			return _applyComponentServiceObjects(
				_postalAddressResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				postalAddressResource -> new PostalAddressPage(
					postalAddressResource.getOrganizationPostalAddressesPage(
						_organization.getId())));
		}

		private Organization _organization;

	}

	@GraphQLTypeExtension(Account.class)
	public class
		GetAccountByExternalReferenceCodeOrganizationsPageTypeExtension {

		public GetAccountByExternalReferenceCodeOrganizationsPageTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField(
			description = "Retrieves the account's organizations. Results can be paginated, filtered, searched, and sorted."
		)
		public OrganizationPage byExternalReferenceCodeOrganizations(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_organizationResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				organizationResource -> new OrganizationPage(
					organizationResource.
						getAccountByExternalReferenceCodeOrganizationsPage(
							_account.getExternalReferenceCode(), search,
							_filterBiFunction.apply(
								organizationResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								organizationResource, sortsString))));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(AccountRole.class)
	public class GetAccountTypeExtension {

		public GetAccountTypeExtension(AccountRole accountRole) {
			_accountRole = accountRole;
		}

		@GraphQLField
		public Account account() throws Exception {
			return _applyComponentServiceObjects(
				_accountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountResource -> accountResource.getAccount(
					_accountRole.getAccountId()));
		}

		private AccountRole _accountRole;

	}

	@GraphQLTypeExtension(Organization.class)
	public class GetOrganizationOrganizationsPageTypeExtension {

		public GetOrganizationOrganizationsPageTypeExtension(
			Organization organization) {

			_organization = organization;
		}

		@GraphQLField(
			description = "Retrieves the parent organization's child organizations. Results can be paginated, filtered, searched, and sorted."
		)
		public OrganizationPage organizations(
				@GraphQLName("flatten") Boolean flatten,
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_organizationResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				organizationResource -> new OrganizationPage(
					organizationResource.getOrganizationOrganizationsPage(
						_organization.getId(), flatten, search,
						_filterBiFunction.apply(
							organizationResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							organizationResource, sortsString))));
		}

		private Organization _organization;

	}

	@GraphQLTypeExtension(Organization.class)
	public class GetOrganizationEmailAddressesPageTypeExtension {

		public GetOrganizationEmailAddressesPageTypeExtension(
			Organization organization) {

			_organization = organization;
		}

		@GraphQLField(
			description = "Retrieves the organization's email addresses."
		)
		public EmailAddressPage emailAddresses() throws Exception {
			return _applyComponentServiceObjects(
				_emailAddressResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				emailAddressResource -> new EmailAddressPage(
					emailAddressResource.getOrganizationEmailAddressesPage(
						_organization.getId())));
		}

		private Organization _organization;

	}

	@GraphQLTypeExtension(Account.class)
	public class GetUserGroupByExternalReferenceCodeTypeExtension {

		public GetUserGroupByExternalReferenceCodeTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField
		public UserGroup userGroupByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_userGroupResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				userGroupResource ->
					userGroupResource.getUserGroupByExternalReferenceCode(
						_account.getExternalReferenceCode()));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Site.class)
	public class GetSiteUserAccountsPageTypeExtension {

		public GetSiteUserAccountsPageTypeExtension(Site site) {
			_site = site;
		}

		@GraphQLField(
			description = "Retrieves the site members' user accounts. Results can be paginated, filtered, searched, and sorted."
		)
		public UserAccountPage userAccounts(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_userAccountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				userAccountResource -> new UserAccountPage(
					userAccountResource.getSiteUserAccountsPage(
						_site.getId(), search,
						_filterBiFunction.apply(
							userAccountResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							userAccountResource, sortsString))));
		}

		private Site _site;

	}

	@GraphQLTypeExtension(Account.class)
	public class
		GetAccountUserAccountsByExternalReferenceCodePageTypeExtension {

		public GetAccountUserAccountsByExternalReferenceCodePageTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField(description = "Gets the users assigned to an account")
		public UserAccountPage userAccountsByExternalReferenceCode(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_userAccountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				userAccountResource -> new UserAccountPage(
					userAccountResource.
						getAccountUserAccountsByExternalReferenceCodePage(
							_account.getExternalReferenceCode(), search,
							_filterBiFunction.apply(
								userAccountResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								userAccountResource, sortsString))));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(AccountRole.class)
	public class GetRoleTypeExtension {

		public GetRoleTypeExtension(AccountRole accountRole) {
			_accountRole = accountRole;
		}

		@GraphQLField(description = "Retrieves the role.")
		public Role role() throws Exception {
			return _applyComponentServiceObjects(
				_roleResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				roleResource -> roleResource.getRole(_accountRole.getRoleId()));
		}

		private AccountRole _accountRole;

	}

	@GraphQLTypeExtension(Account.class)
	public class GetAccountOrganizationsPageTypeExtension {

		public GetAccountOrganizationsPageTypeExtension(Account account) {
			_account = account;
		}

		@GraphQLField(
			description = "Retrieves the account's organizations. Results can be paginated, filtered, searched, and sorted."
		)
		public OrganizationPage organizations(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_organizationResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				organizationResource -> new OrganizationPage(
					organizationResource.getAccountOrganizationsPage(
						_account.getId(), search,
						_filterBiFunction.apply(
							organizationResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							organizationResource, sortsString))));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Account.class)
	public class
		GetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPageTypeExtension {

		public GetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPageTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField(
			description = "Gets a user's account roles by their email address from an account by external reference code"
		)
		public AccountRolePage
				byExternalReferenceCodeUserAccountByEmailAddressAccountRoles(
					@GraphQLName("emailAddress") String emailAddress)
			throws Exception {

			return _applyComponentServiceObjects(
				_accountRoleResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountRoleResource -> new AccountRolePage(
					accountRoleResource.
						getAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage(
							_account.getExternalReferenceCode(),
							emailAddress)));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Site.class)
	public class GetSiteSegmentsPageTypeExtension {

		public GetSiteSegmentsPageTypeExtension(Site site) {
			_site = site;
		}

		@GraphQLField(description = "Gets a site's segments.")
		public SegmentPage segments(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_segmentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				segmentResource -> new SegmentPage(
					segmentResource.getSiteSegmentsPage(
						_site.getId(), Pagination.of(page, pageSize))));
		}

		private Site _site;

	}

	@GraphQLTypeExtension(Organization.class)
	public class GetOrganizationWebUrlsPageTypeExtension {

		public GetOrganizationWebUrlsPageTypeExtension(
			Organization organization) {

			_organization = organization;
		}

		@GraphQLField(description = "Retrieves the organization's URLs.")
		public WebUrlPage webUrls() throws Exception {
			return _applyComponentServiceObjects(
				_webUrlResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				webUrlResource -> new WebUrlPage(
					webUrlResource.getOrganizationWebUrlsPage(
						_organization.getId())));
		}

		private Organization _organization;

	}

	@GraphQLTypeExtension(Subscription.class)
	public class GetSiteTypeExtension {

		public GetSiteTypeExtension(Subscription subscription) {
			_subscription = subscription;
		}

		@GraphQLField
		public Site site() throws Exception {
			return _applyComponentServiceObjects(
				_siteResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				siteResource -> siteResource.getSite(
					_subscription.getSiteId()));
		}

		private Subscription _subscription;

	}

	@GraphQLTypeExtension(UserAccount.class)
	public class GetUserAccountWebUrlsPageTypeExtension {

		public GetUserAccountWebUrlsPageTypeExtension(UserAccount userAccount) {
			_userAccount = userAccount;
		}

		@GraphQLField(description = "Retrieves the user's URLs.")
		public WebUrlPage webUrls() throws Exception {
			return _applyComponentServiceObjects(
				_webUrlResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				webUrlResource -> new WebUrlPage(
					webUrlResource.getUserAccountWebUrlsPage(
						_userAccount.getId())));
		}

		private UserAccount _userAccount;

	}

	@GraphQLTypeExtension(Account.class)
	public class GetOrganizationByExternalReferenceCodeTypeExtension {

		public GetOrganizationByExternalReferenceCodeTypeExtension(
			Account account) {

			_account = account;
		}

		@GraphQLField
		public Organization organizationByExternalReferenceCode()
			throws Exception {

			return _applyComponentServiceObjects(
				_organizationResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				organizationResource ->
					organizationResource.getOrganizationByExternalReferenceCode(
						_account.getExternalReferenceCode()));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Organization.class)
	public class GetOrganizationPhonesPageTypeExtension {

		public GetOrganizationPhonesPageTypeExtension(
			Organization organization) {

			_organization = organization;
		}

		@GraphQLField(
			description = "Retrieves the organization's phone numbers."
		)
		public PhonePage phones() throws Exception {
			return _applyComponentServiceObjects(
				_phoneResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				phoneResource -> new PhonePage(
					phoneResource.getOrganizationPhonesPage(
						_organization.getId())));
		}

		private Organization _organization;

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

	@GraphQLName("EmailAddressPage")
	public class EmailAddressPage {

		public EmailAddressPage(Page emailAddressPage) {
			actions = emailAddressPage.getActions();

			items = emailAddressPage.getItems();
			lastPage = emailAddressPage.getLastPage();
			page = emailAddressPage.getPage();
			pageSize = emailAddressPage.getPageSize();
			totalCount = emailAddressPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<EmailAddress> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OrganizationPage")
	public class OrganizationPage {

		public OrganizationPage(Page organizationPage) {
			actions = organizationPage.getActions();

			items = organizationPage.getItems();
			lastPage = organizationPage.getLastPage();
			page = organizationPage.getPage();
			pageSize = organizationPage.getPageSize();
			totalCount = organizationPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Organization> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PhonePage")
	public class PhonePage {

		public PhonePage(Page phonePage) {
			actions = phonePage.getActions();

			items = phonePage.getItems();
			lastPage = phonePage.getLastPage();
			page = phonePage.getPage();
			pageSize = phonePage.getPageSize();
			totalCount = phonePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Phone> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PostalAddressPage")
	public class PostalAddressPage {

		public PostalAddressPage(Page postalAddressPage) {
			actions = postalAddressPage.getActions();

			items = postalAddressPage.getItems();
			lastPage = postalAddressPage.getLastPage();
			page = postalAddressPage.getPage();
			pageSize = postalAddressPage.getPageSize();
			totalCount = postalAddressPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<PostalAddress> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("RolePage")
	public class RolePage {

		public RolePage(Page rolePage) {
			actions = rolePage.getActions();

			items = rolePage.getItems();
			lastPage = rolePage.getLastPage();
			page = rolePage.getPage();
			pageSize = rolePage.getPageSize();
			totalCount = rolePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Role> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SegmentPage")
	public class SegmentPage {

		public SegmentPage(Page segmentPage) {
			actions = segmentPage.getActions();

			items = segmentPage.getItems();
			lastPage = segmentPage.getLastPage();
			page = segmentPage.getPage();
			pageSize = segmentPage.getPageSize();
			totalCount = segmentPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Segment> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SegmentUserPage")
	public class SegmentUserPage {

		public SegmentUserPage(Page segmentUserPage) {
			actions = segmentUserPage.getActions();

			items = segmentUserPage.getItems();
			lastPage = segmentUserPage.getLastPage();
			page = segmentUserPage.getPage();
			pageSize = segmentUserPage.getPageSize();
			totalCount = segmentUserPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<SegmentUser> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SitePage")
	public class SitePage {

		public SitePage(Page sitePage) {
			actions = sitePage.getActions();

			items = sitePage.getItems();
			lastPage = sitePage.getLastPage();
			page = sitePage.getPage();
			pageSize = sitePage.getPageSize();
			totalCount = sitePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Site> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SubscriptionPage")
	public class SubscriptionPage {

		public SubscriptionPage(Page subscriptionPage) {
			actions = subscriptionPage.getActions();

			items = subscriptionPage.getItems();
			lastPage = subscriptionPage.getLastPage();
			page = subscriptionPage.getPage();
			pageSize = subscriptionPage.getPageSize();
			totalCount = subscriptionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Subscription> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("UserAccountPage")
	public class UserAccountPage {

		public UserAccountPage(Page userAccountPage) {
			actions = userAccountPage.getActions();

			items = userAccountPage.getItems();
			lastPage = userAccountPage.getLastPage();
			page = userAccountPage.getPage();
			pageSize = userAccountPage.getPageSize();
			totalCount = userAccountPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<UserAccount> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("UserGroupPage")
	public class UserGroupPage {

		public UserGroupPage(Page userGroupPage) {
			actions = userGroupPage.getActions();

			items = userGroupPage.getItems();
			lastPage = userGroupPage.getLastPage();
			page = userGroupPage.getPage();
			pageSize = userGroupPage.getPageSize();
			totalCount = userGroupPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<UserGroup> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WebUrlPage")
	public class WebUrlPage {

		public WebUrlPage(Page webUrlPage) {
			actions = webUrlPage.getActions();

			items = webUrlPage.getItems();
			lastPage = webUrlPage.getLastPage();
			page = webUrlPage.getPage();
			pageSize = webUrlPage.getPageSize();
			totalCount = webUrlPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<WebUrl> items;

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

		@GraphQLField
		public Account parentAccount() throws Exception {
			if (_account.getParentAccountId() == null) {
				return null;
			}

			return _applyComponentServiceObjects(
				_accountResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				accountResource -> accountResource.getAccount(
					_account.getParentAccountId()));
		}

		private Account _account;

	}

	@GraphQLTypeExtension(Site.class)
	public class ParentSiteSiteIdTypeExtension {

		public ParentSiteSiteIdTypeExtension(Site site) {
			_site = site;
		}

		@GraphQLField
		public Site parentSite() throws Exception {
			if (_site.getParentSiteId() == null) {
				return null;
			}

			return _applyComponentServiceObjects(
				_siteResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				siteResource -> siteResource.getSite(_site.getParentSiteId()));
		}

		private Site _site;

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
			EmailAddressResource emailAddressResource)
		throws Exception {

		emailAddressResource.setContextAcceptLanguage(_acceptLanguage);
		emailAddressResource.setContextCompany(_company);
		emailAddressResource.setContextHttpServletRequest(_httpServletRequest);
		emailAddressResource.setContextHttpServletResponse(
			_httpServletResponse);
		emailAddressResource.setContextUriInfo(_uriInfo);
		emailAddressResource.setContextUser(_user);
		emailAddressResource.setGroupLocalService(_groupLocalService);
		emailAddressResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			OrganizationResource organizationResource)
		throws Exception {

		organizationResource.setContextAcceptLanguage(_acceptLanguage);
		organizationResource.setContextCompany(_company);
		organizationResource.setContextHttpServletRequest(_httpServletRequest);
		organizationResource.setContextHttpServletResponse(
			_httpServletResponse);
		organizationResource.setContextUriInfo(_uriInfo);
		organizationResource.setContextUser(_user);
		organizationResource.setGroupLocalService(_groupLocalService);
		organizationResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(PhoneResource phoneResource)
		throws Exception {

		phoneResource.setContextAcceptLanguage(_acceptLanguage);
		phoneResource.setContextCompany(_company);
		phoneResource.setContextHttpServletRequest(_httpServletRequest);
		phoneResource.setContextHttpServletResponse(_httpServletResponse);
		phoneResource.setContextUriInfo(_uriInfo);
		phoneResource.setContextUser(_user);
		phoneResource.setGroupLocalService(_groupLocalService);
		phoneResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PostalAddressResource postalAddressResource)
		throws Exception {

		postalAddressResource.setContextAcceptLanguage(_acceptLanguage);
		postalAddressResource.setContextCompany(_company);
		postalAddressResource.setContextHttpServletRequest(_httpServletRequest);
		postalAddressResource.setContextHttpServletResponse(
			_httpServletResponse);
		postalAddressResource.setContextUriInfo(_uriInfo);
		postalAddressResource.setContextUser(_user);
		postalAddressResource.setGroupLocalService(_groupLocalService);
		postalAddressResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(RoleResource roleResource)
		throws Exception {

		roleResource.setContextAcceptLanguage(_acceptLanguage);
		roleResource.setContextCompany(_company);
		roleResource.setContextHttpServletRequest(_httpServletRequest);
		roleResource.setContextHttpServletResponse(_httpServletResponse);
		roleResource.setContextUriInfo(_uriInfo);
		roleResource.setContextUser(_user);
		roleResource.setGroupLocalService(_groupLocalService);
		roleResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(SegmentResource segmentResource)
		throws Exception {

		segmentResource.setContextAcceptLanguage(_acceptLanguage);
		segmentResource.setContextCompany(_company);
		segmentResource.setContextHttpServletRequest(_httpServletRequest);
		segmentResource.setContextHttpServletResponse(_httpServletResponse);
		segmentResource.setContextUriInfo(_uriInfo);
		segmentResource.setContextUser(_user);
		segmentResource.setGroupLocalService(_groupLocalService);
		segmentResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			SegmentUserResource segmentUserResource)
		throws Exception {

		segmentUserResource.setContextAcceptLanguage(_acceptLanguage);
		segmentUserResource.setContextCompany(_company);
		segmentUserResource.setContextHttpServletRequest(_httpServletRequest);
		segmentUserResource.setContextHttpServletResponse(_httpServletResponse);
		segmentUserResource.setContextUriInfo(_uriInfo);
		segmentUserResource.setContextUser(_user);
		segmentUserResource.setGroupLocalService(_groupLocalService);
		segmentUserResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(SiteResource siteResource)
		throws Exception {

		siteResource.setContextAcceptLanguage(_acceptLanguage);
		siteResource.setContextCompany(_company);
		siteResource.setContextHttpServletRequest(_httpServletRequest);
		siteResource.setContextHttpServletResponse(_httpServletResponse);
		siteResource.setContextUriInfo(_uriInfo);
		siteResource.setContextUser(_user);
		siteResource.setGroupLocalService(_groupLocalService);
		siteResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			SubscriptionResource subscriptionResource)
		throws Exception {

		subscriptionResource.setContextAcceptLanguage(_acceptLanguage);
		subscriptionResource.setContextCompany(_company);
		subscriptionResource.setContextHttpServletRequest(_httpServletRequest);
		subscriptionResource.setContextHttpServletResponse(
			_httpServletResponse);
		subscriptionResource.setContextUriInfo(_uriInfo);
		subscriptionResource.setContextUser(_user);
		subscriptionResource.setGroupLocalService(_groupLocalService);
		subscriptionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			UserAccountResource userAccountResource)
		throws Exception {

		userAccountResource.setContextAcceptLanguage(_acceptLanguage);
		userAccountResource.setContextCompany(_company);
		userAccountResource.setContextHttpServletRequest(_httpServletRequest);
		userAccountResource.setContextHttpServletResponse(_httpServletResponse);
		userAccountResource.setContextUriInfo(_uriInfo);
		userAccountResource.setContextUser(_user);
		userAccountResource.setGroupLocalService(_groupLocalService);
		userAccountResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(UserGroupResource userGroupResource)
		throws Exception {

		userGroupResource.setContextAcceptLanguage(_acceptLanguage);
		userGroupResource.setContextCompany(_company);
		userGroupResource.setContextHttpServletRequest(_httpServletRequest);
		userGroupResource.setContextHttpServletResponse(_httpServletResponse);
		userGroupResource.setContextUriInfo(_uriInfo);
		userGroupResource.setContextUser(_user);
		userGroupResource.setGroupLocalService(_groupLocalService);
		userGroupResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(WebUrlResource webUrlResource)
		throws Exception {

		webUrlResource.setContextAcceptLanguage(_acceptLanguage);
		webUrlResource.setContextCompany(_company);
		webUrlResource.setContextHttpServletRequest(_httpServletRequest);
		webUrlResource.setContextHttpServletResponse(_httpServletResponse);
		webUrlResource.setContextUriInfo(_uriInfo);
		webUrlResource.setContextUser(_user);
		webUrlResource.setGroupLocalService(_groupLocalService);
		webUrlResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;
	private static ComponentServiceObjects<AccountRoleResource>
		_accountRoleResourceComponentServiceObjects;
	private static ComponentServiceObjects<EmailAddressResource>
		_emailAddressResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrganizationResource>
		_organizationResourceComponentServiceObjects;
	private static ComponentServiceObjects<PhoneResource>
		_phoneResourceComponentServiceObjects;
	private static ComponentServiceObjects<PostalAddressResource>
		_postalAddressResourceComponentServiceObjects;
	private static ComponentServiceObjects<RoleResource>
		_roleResourceComponentServiceObjects;
	private static ComponentServiceObjects<SegmentResource>
		_segmentResourceComponentServiceObjects;
	private static ComponentServiceObjects<SegmentUserResource>
		_segmentUserResourceComponentServiceObjects;
	private static ComponentServiceObjects<SiteResource>
		_siteResourceComponentServiceObjects;
	private static ComponentServiceObjects<SubscriptionResource>
		_subscriptionResourceComponentServiceObjects;
	private static ComponentServiceObjects<UserAccountResource>
		_userAccountResourceComponentServiceObjects;
	private static ComponentServiceObjects<UserGroupResource>
		_userGroupResourceComponentServiceObjects;
	private static ComponentServiceObjects<WebUrlResource>
		_webUrlResourceComponentServiceObjects;

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