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

package com.liferay.headless.admin.user.internal.graphql.servlet.v1_0;

import com.liferay.headless.admin.user.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.admin.user.internal.graphql.query.v1_0.Query;
import com.liferay.headless.admin.user.internal.resource.v1_0.AccountResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.AccountRoleResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.EmailAddressResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.OrganizationResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.PhoneResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.PostalAddressResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.RoleResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.SegmentResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.SegmentUserResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.SiteResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.SubscriptionResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.UserAccountResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.UserGroupResourceImpl;
import com.liferay.headless.admin.user.internal.resource.v1_0.WebUrlResourceImpl;
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
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setAccountResourceComponentServiceObjects(
			_accountResourceComponentServiceObjects);
		Mutation.setAccountRoleResourceComponentServiceObjects(
			_accountRoleResourceComponentServiceObjects);
		Mutation.setOrganizationResourceComponentServiceObjects(
			_organizationResourceComponentServiceObjects);
		Mutation.setRoleResourceComponentServiceObjects(
			_roleResourceComponentServiceObjects);
		Mutation.setSubscriptionResourceComponentServiceObjects(
			_subscriptionResourceComponentServiceObjects);
		Mutation.setUserAccountResourceComponentServiceObjects(
			_userAccountResourceComponentServiceObjects);
		Mutation.setUserGroupResourceComponentServiceObjects(
			_userGroupResourceComponentServiceObjects);

		Query.setAccountResourceComponentServiceObjects(
			_accountResourceComponentServiceObjects);
		Query.setAccountRoleResourceComponentServiceObjects(
			_accountRoleResourceComponentServiceObjects);
		Query.setEmailAddressResourceComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects);
		Query.setOrganizationResourceComponentServiceObjects(
			_organizationResourceComponentServiceObjects);
		Query.setPhoneResourceComponentServiceObjects(
			_phoneResourceComponentServiceObjects);
		Query.setPostalAddressResourceComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects);
		Query.setRoleResourceComponentServiceObjects(
			_roleResourceComponentServiceObjects);
		Query.setSegmentResourceComponentServiceObjects(
			_segmentResourceComponentServiceObjects);
		Query.setSegmentUserResourceComponentServiceObjects(
			_segmentUserResourceComponentServiceObjects);
		Query.setSiteResourceComponentServiceObjects(
			_siteResourceComponentServiceObjects);
		Query.setSubscriptionResourceComponentServiceObjects(
			_subscriptionResourceComponentServiceObjects);
		Query.setUserAccountResourceComponentServiceObjects(
			_userAccountResourceComponentServiceObjects);
		Query.setUserGroupResourceComponentServiceObjects(
			_userGroupResourceComponentServiceObjects);
		Query.setWebUrlResourceComponentServiceObjects(
			_webUrlResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Admin.User";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-admin-user-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#createAccount",
			new ObjectValuePair<>(AccountResourceImpl.class, "postAccount"));
		_resourceMethodPairs.put(
			"mutation#createAccountBatch",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "postAccountBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountResourceImpl.class,
				"deleteAccountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchAccountByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountResourceImpl.class,
				"patchAccountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateAccountByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountResourceImpl.class,
				"putAccountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteAccount",
			new ObjectValuePair<>(AccountResourceImpl.class, "deleteAccount"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountBatch",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "deleteAccountBatch"));
		_resourceMethodPairs.put(
			"mutation#patchAccount",
			new ObjectValuePair<>(AccountResourceImpl.class, "patchAccount"));
		_resourceMethodPairs.put(
			"mutation#updateAccount",
			new ObjectValuePair<>(AccountResourceImpl.class, "putAccount"));
		_resourceMethodPairs.put(
			"mutation#updateAccountBatch",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "putAccountBatch"));
		_resourceMethodPairs.put(
			"mutation#patchOrganizationMoveAccounts",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "patchOrganizationMoveAccounts"));
		_resourceMethodPairs.put(
			"mutation#patchOrganizationMoveAccountsByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountResourceImpl.class,
				"patchOrganizationMoveAccountsByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteOrganizationAccounts",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "deleteOrganizationAccounts"));
		_resourceMethodPairs.put(
			"mutation#createOrganizationAccounts",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "postOrganizationAccounts"));
		_resourceMethodPairs.put(
			"mutation#deleteOrganizationAccountsByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountResourceImpl.class,
				"deleteOrganizationAccountsByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#createOrganizationAccountsByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountResourceImpl.class,
				"postOrganizationAccountsByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class,
				"deleteAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#createAccountAccountRoleByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class,
				"postAccountAccountRoleByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class,
				"deleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountAccountRole",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class, "postAccountAccountRole"));
		_resourceMethodPairs.put(
			"mutation#createAccountAccountRoleBatch",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class, "postAccountAccountRoleBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountAccountRoleUserAccountAssociation",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class,
				"deleteAccountAccountRoleUserAccountAssociation"));
		_resourceMethodPairs.put(
			"mutation#createAccountAccountRoleUserAccountAssociation",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class,
				"postAccountAccountRoleUserAccountAssociation"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountByExternalReferenceCodeOrganization",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"deleteAccountByExternalReferenceCodeOrganization"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeOrganization",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"postAccountByExternalReferenceCodeOrganization"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountOrganization",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class, "deleteAccountOrganization"));
		_resourceMethodPairs.put(
			"mutation#createAccountOrganization",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class, "postAccountOrganization"));
		_resourceMethodPairs.put(
			"mutation#createOrganization",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class, "postOrganization"));
		_resourceMethodPairs.put(
			"mutation#createOrganizationBatch",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class, "postOrganizationBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteOrganizationByExternalReferenceCode",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"deleteOrganizationByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchOrganizationByExternalReferenceCode",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"patchOrganizationByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateOrganizationByExternalReferenceCode",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"putOrganizationByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteOrganization",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class, "deleteOrganization"));
		_resourceMethodPairs.put(
			"mutation#deleteOrganizationBatch",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class, "deleteOrganizationBatch"));
		_resourceMethodPairs.put(
			"mutation#patchOrganization",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class, "patchOrganization"));
		_resourceMethodPairs.put(
			"mutation#updateOrganization",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class, "putOrganization"));
		_resourceMethodPairs.put(
			"mutation#updateOrganizationBatch",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class, "putOrganizationBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteUserAccountsByEmailAddress",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"deleteUserAccountsByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#createUserAccountsByEmailAddress",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"postUserAccountsByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#deleteUserAccountByEmailAddress",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"deleteUserAccountByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#createUserAccountByEmailAddress",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"postUserAccountByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#deleteRoleUserAccountAssociation",
			new ObjectValuePair<>(
				RoleResourceImpl.class, "deleteRoleUserAccountAssociation"));
		_resourceMethodPairs.put(
			"mutation#createRoleUserAccountAssociation",
			new ObjectValuePair<>(
				RoleResourceImpl.class, "postRoleUserAccountAssociation"));
		_resourceMethodPairs.put(
			"mutation#deleteOrganizationRoleUserAccountAssociation",
			new ObjectValuePair<>(
				RoleResourceImpl.class,
				"deleteOrganizationRoleUserAccountAssociation"));
		_resourceMethodPairs.put(
			"mutation#createOrganizationRoleUserAccountAssociation",
			new ObjectValuePair<>(
				RoleResourceImpl.class,
				"postOrganizationRoleUserAccountAssociation"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteRoleUserAccountAssociation",
			new ObjectValuePair<>(
				RoleResourceImpl.class,
				"deleteSiteRoleUserAccountAssociation"));
		_resourceMethodPairs.put(
			"mutation#createSiteRoleUserAccountAssociation",
			new ObjectValuePair<>(
				RoleResourceImpl.class, "postSiteRoleUserAccountAssociation"));
		_resourceMethodPairs.put(
			"mutation#deleteMyUserAccountSubscription",
			new ObjectValuePair<>(
				SubscriptionResourceImpl.class,
				"deleteMyUserAccountSubscription"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountByExternalReferenceCodeUserAccountByExternalReferenceCode",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"deleteAccountByExternalReferenceCodeUserAccountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeUserAccountByExternalReferenceCode",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"postAccountByExternalReferenceCodeUserAccountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#createAccountUserAccountByExternalReferenceCode",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"postAccountUserAccountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountUserAccountsByExternalReferenceCodeByEmailAddress",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"deleteAccountUserAccountsByExternalReferenceCodeByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountUserAccountsByExternalReferenceCodeByEmailAddress",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"postAccountUserAccountsByExternalReferenceCodeByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountUserAccountByExternalReferenceCodeByEmailAddress",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"deleteAccountUserAccountByExternalReferenceCodeByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountUserAccountByExternalReferenceCodeByEmailAddress",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"postAccountUserAccountByExternalReferenceCodeByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountUserAccount",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "postAccountUserAccount"));
		_resourceMethodPairs.put(
			"mutation#createAccountUserAccountBatch",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "postAccountUserAccountBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountUserAccountsByEmailAddress",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"deleteAccountUserAccountsByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountUserAccountsByEmailAddress",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"postAccountUserAccountsByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountUserAccountByEmailAddress",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"deleteAccountUserAccountByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountUserAccountByEmailAddress",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"postAccountUserAccountByEmailAddress"));
		_resourceMethodPairs.put(
			"mutation#createUserAccount",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "postUserAccount"));
		_resourceMethodPairs.put(
			"mutation#createUserAccountBatch",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "postUserAccountBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteUserAccountByExternalReferenceCode",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"deleteUserAccountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateUserAccountByExternalReferenceCode",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"putUserAccountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteUserAccount",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "deleteUserAccount"));
		_resourceMethodPairs.put(
			"mutation#deleteUserAccountBatch",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "deleteUserAccountBatch"));
		_resourceMethodPairs.put(
			"mutation#patchUserAccount",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "patchUserAccount"));
		_resourceMethodPairs.put(
			"mutation#updateUserAccount",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "putUserAccount"));
		_resourceMethodPairs.put(
			"mutation#updateUserAccountBatch",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "putUserAccountBatch"));
		_resourceMethodPairs.put(
			"mutation#createUserGroup",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class, "postUserGroup"));
		_resourceMethodPairs.put(
			"mutation#createUserGroupBatch",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class, "postUserGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteUserGroupByExternalReferenceCode",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class,
				"deleteUserGroupByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchUserGroupByExternalReferenceCode",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class,
				"patchUserGroupByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateUserGroupByExternalReferenceCode",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class,
				"putUserGroupByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteUserGroup",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class, "deleteUserGroup"));
		_resourceMethodPairs.put(
			"mutation#deleteUserGroupBatch",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class, "deleteUserGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#patchUserGroup",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class, "patchUserGroup"));
		_resourceMethodPairs.put(
			"mutation#updateUserGroup",
			new ObjectValuePair<>(UserGroupResourceImpl.class, "putUserGroup"));
		_resourceMethodPairs.put(
			"mutation#updateUserGroupBatch",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class, "putUserGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteUserGroupUsers",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class, "deleteUserGroupUsers"));
		_resourceMethodPairs.put(
			"mutation#createUserGroupUsers",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class, "postUserGroupUsers"));
		_resourceMethodPairs.put(
			"query#accounts",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "getAccountsPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountResourceImpl.class,
				"getAccountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#account",
			new ObjectValuePair<>(AccountResourceImpl.class, "getAccount"));
		_resourceMethodPairs.put(
			"query#organizationAccounts",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "getOrganizationAccountsPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRoles",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class,
				"getAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage"));
		_resourceMethodPairs.put(
			"query#accountAccountRolesByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class,
				"getAccountAccountRolesByExternalReferenceCodePage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeUserAccountByEmailAddressAccountRoles",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class,
				"getAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage"));
		_resourceMethodPairs.put(
			"query#accountAccountRoles",
			new ObjectValuePair<>(
				AccountRoleResourceImpl.class, "getAccountAccountRolesPage"));
		_resourceMethodPairs.put(
			"query#emailAddress",
			new ObjectValuePair<>(
				EmailAddressResourceImpl.class, "getEmailAddress"));
		_resourceMethodPairs.put(
			"query#organizationEmailAddresses",
			new ObjectValuePair<>(
				EmailAddressResourceImpl.class,
				"getOrganizationEmailAddressesPage"));
		_resourceMethodPairs.put(
			"query#userAccountEmailAddresses",
			new ObjectValuePair<>(
				EmailAddressResourceImpl.class,
				"getUserAccountEmailAddressesPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeOrganizations",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"getAccountByExternalReferenceCodeOrganizationsPage"));
		_resourceMethodPairs.put(
			"query#accountOrganizations",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class, "getAccountOrganizationsPage"));
		_resourceMethodPairs.put(
			"query#organizations",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class, "getOrganizationsPage"));
		_resourceMethodPairs.put(
			"query#organizationByExternalReferenceCode",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"getOrganizationByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#organization",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class, "getOrganization"));
		_resourceMethodPairs.put(
			"query#organizationChildOrganizations",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"getOrganizationChildOrganizationsPage"));
		_resourceMethodPairs.put(
			"query#organizationOrganizations",
			new ObjectValuePair<>(
				OrganizationResourceImpl.class,
				"getOrganizationOrganizationsPage"));
		_resourceMethodPairs.put(
			"query#organizationPhones",
			new ObjectValuePair<>(
				PhoneResourceImpl.class, "getOrganizationPhonesPage"));
		_resourceMethodPairs.put(
			"query#phone",
			new ObjectValuePair<>(PhoneResourceImpl.class, "getPhone"));
		_resourceMethodPairs.put(
			"query#userAccountPhones",
			new ObjectValuePair<>(
				PhoneResourceImpl.class, "getUserAccountPhonesPage"));
		_resourceMethodPairs.put(
			"query#accountPostalAddresses",
			new ObjectValuePair<>(
				PostalAddressResourceImpl.class,
				"getAccountPostalAddressesPage"));
		_resourceMethodPairs.put(
			"query#organizationPostalAddresses",
			new ObjectValuePair<>(
				PostalAddressResourceImpl.class,
				"getOrganizationPostalAddressesPage"));
		_resourceMethodPairs.put(
			"query#postalAddress",
			new ObjectValuePair<>(
				PostalAddressResourceImpl.class, "getPostalAddress"));
		_resourceMethodPairs.put(
			"query#userAccountPostalAddresses",
			new ObjectValuePair<>(
				PostalAddressResourceImpl.class,
				"getUserAccountPostalAddressesPage"));
		_resourceMethodPairs.put(
			"query#roles",
			new ObjectValuePair<>(RoleResourceImpl.class, "getRolesPage"));
		_resourceMethodPairs.put(
			"query#role",
			new ObjectValuePair<>(RoleResourceImpl.class, "getRole"));
		_resourceMethodPairs.put(
			"query#segments",
			new ObjectValuePair<>(
				SegmentResourceImpl.class, "getSiteSegmentsPage"));
		_resourceMethodPairs.put(
			"query#userAccountSegments",
			new ObjectValuePair<>(
				SegmentResourceImpl.class, "getSiteUserAccountSegmentsPage"));
		_resourceMethodPairs.put(
			"query#segmentUserAccounts",
			new ObjectValuePair<>(
				SegmentUserResourceImpl.class, "getSegmentUserAccountsPage"));
		_resourceMethodPairs.put(
			"query#myUserAccountSites",
			new ObjectValuePair<>(
				SiteResourceImpl.class, "getMyUserAccountSitesPage"));
		_resourceMethodPairs.put(
			"query#byFriendlyUrlPath",
			new ObjectValuePair<>(
				SiteResourceImpl.class, "getSiteByFriendlyUrlPath"));
		_resourceMethodPairs.put(
			"query#site",
			new ObjectValuePair<>(SiteResourceImpl.class, "getSite"));
		_resourceMethodPairs.put(
			"query#myUserAccountSubscriptions",
			new ObjectValuePair<>(
				SubscriptionResourceImpl.class,
				"getMyUserAccountSubscriptionsPage"));
		_resourceMethodPairs.put(
			"query#myUserAccountSubscription",
			new ObjectValuePair<>(
				SubscriptionResourceImpl.class,
				"getMyUserAccountSubscription"));
		_resourceMethodPairs.put(
			"query#accountUserAccountsByExternalReferenceCode",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"getAccountUserAccountsByExternalReferenceCodePage"));
		_resourceMethodPairs.put(
			"query#accountUserAccounts",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "getAccountUserAccountsPage"));
		_resourceMethodPairs.put(
			"query#myUserAccount",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "getMyUserAccount"));
		_resourceMethodPairs.put(
			"query#organizationUserAccounts",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"getOrganizationUserAccountsPage"));
		_resourceMethodPairs.put(
			"query#siteUserAccounts",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "getSiteUserAccountsPage"));
		_resourceMethodPairs.put(
			"query#userAccounts",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "getUserAccountsPage"));
		_resourceMethodPairs.put(
			"query#userAccountByExternalReferenceCode",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class,
				"getUserAccountByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#userAccount",
			new ObjectValuePair<>(
				UserAccountResourceImpl.class, "getUserAccount"));
		_resourceMethodPairs.put(
			"query#userUserGroups",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class, "getUserUserGroups"));
		_resourceMethodPairs.put(
			"query#userGroups",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class, "getUserGroupsPage"));
		_resourceMethodPairs.put(
			"query#userGroupByExternalReferenceCode",
			new ObjectValuePair<>(
				UserGroupResourceImpl.class,
				"getUserGroupByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#userGroup",
			new ObjectValuePair<>(UserGroupResourceImpl.class, "getUserGroup"));
		_resourceMethodPairs.put(
			"query#organizationWebUrls",
			new ObjectValuePair<>(
				WebUrlResourceImpl.class, "getOrganizationWebUrlsPage"));
		_resourceMethodPairs.put(
			"query#userAccountWebUrls",
			new ObjectValuePair<>(
				WebUrlResourceImpl.class, "getUserAccountWebUrlsPage"));
		_resourceMethodPairs.put(
			"query#webUrl",
			new ObjectValuePair<>(WebUrlResourceImpl.class, "getWebUrl"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountRoleResource>
		_accountRoleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrganizationResource>
		_organizationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<RoleResource>
		_roleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SubscriptionResource>
		_subscriptionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<UserAccountResource>
		_userAccountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<UserGroupResource>
		_userGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<EmailAddressResource>
		_emailAddressResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PhoneResource>
		_phoneResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PostalAddressResource>
		_postalAddressResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SegmentResource>
		_segmentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SegmentUserResource>
		_segmentUserResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SiteResource>
		_siteResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WebUrlResource>
		_webUrlResourceComponentServiceObjects;

}