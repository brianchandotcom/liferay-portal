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

package com.liferay.headless.commerce.admin.account.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.admin.account.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.admin.account.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.admin.account.internal.resource.v1_0.AccountAddressResourceImpl;
import com.liferay.headless.commerce.admin.account.internal.resource.v1_0.AccountChannelEntryResourceImpl;
import com.liferay.headless.commerce.admin.account.internal.resource.v1_0.AccountGroupResourceImpl;
import com.liferay.headless.commerce.admin.account.internal.resource.v1_0.AccountMemberResourceImpl;
import com.liferay.headless.commerce.admin.account.internal.resource.v1_0.AccountOrganizationResourceImpl;
import com.liferay.headless.commerce.admin.account.internal.resource.v1_0.AccountResourceImpl;
import com.liferay.headless.commerce.admin.account.internal.resource.v1_0.UserResourceImpl;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountAddressResource;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountChannelEntryResource;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountGroupResource;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountMemberResource;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountOrganizationResource;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountResource;
import com.liferay.headless.commerce.admin.account.resource.v1_0.UserResource;
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
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setAccountResourceComponentServiceObjects(
			_accountResourceComponentServiceObjects);
		Mutation.setAccountAddressResourceComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects);
		Mutation.setAccountChannelEntryResourceComponentServiceObjects(
			_accountChannelEntryResourceComponentServiceObjects);
		Mutation.setAccountGroupResourceComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects);
		Mutation.setAccountMemberResourceComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects);
		Mutation.setAccountOrganizationResourceComponentServiceObjects(
			_accountOrganizationResourceComponentServiceObjects);
		Mutation.setUserResourceComponentServiceObjects(
			_userResourceComponentServiceObjects);

		Query.setAccountResourceComponentServiceObjects(
			_accountResourceComponentServiceObjects);
		Query.setAccountAddressResourceComponentServiceObjects(
			_accountAddressResourceComponentServiceObjects);
		Query.setAccountChannelEntryResourceComponentServiceObjects(
			_accountChannelEntryResourceComponentServiceObjects);
		Query.setAccountGroupResourceComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects);
		Query.setAccountMemberResourceComponentServiceObjects(
			_accountMemberResourceComponentServiceObjects);
		Query.setAccountOrganizationResourceComponentServiceObjects(
			_accountOrganizationResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Commerce.Admin.Account";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-admin-account-graphql/v1_0";
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
			"mutation#createAccountGroupByExternalReferenceCodeAccount",
			new ObjectValuePair<>(
				AccountResourceImpl.class,
				"postAccountGroupByExternalReferenceCodeAccount"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountGroupByExternalReferenceCodeAccount",
			new ObjectValuePair<>(
				AccountResourceImpl.class,
				"deleteAccountGroupByExternalReferenceCodeAccount"));
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
			"mutation#createAccountByExternalReferenceCodeLogo",
			new ObjectValuePair<>(
				AccountResourceImpl.class,
				"postAccountByExternalReferenceCodeLogo"));
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
			"mutation#createAccountLogo",
			new ObjectValuePair<>(
				AccountResourceImpl.class, "postAccountLogo"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountAddressByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class,
				"deleteAccountAddressByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchAccountAddressByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class,
				"patchAccountAddressByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountAddress",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class, "deleteAccountAddress"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountAddressBatch",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class, "deleteAccountAddressBatch"));
		_resourceMethodPairs.put(
			"mutation#patchAccountAddress",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class, "patchAccountAddress"));
		_resourceMethodPairs.put(
			"mutation#updateAccountAddress",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class, "putAccountAddress"));
		_resourceMethodPairs.put(
			"mutation#updateAccountAddressBatch",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class, "putAccountAddressBatch"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountAddress",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountAddress",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class,
				"postAccountIdAccountAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountAddressBatch",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class,
				"postAccountIdAccountAddressBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountChannelBillingAddressId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"deleteAccountChannelBillingAddressId"));
		_resourceMethodPairs.put(
			"mutation#patchAccountChannelBillingAddressId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"patchAccountChannelBillingAddressId"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountChannelCurrencyId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"deleteAccountChannelCurrencyId"));
		_resourceMethodPairs.put(
			"mutation#patchAccountChannelCurrencyId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"patchAccountChannelCurrencyId"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountChannelDeliveryTermId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"deleteAccountChannelDeliveryTermId"));
		_resourceMethodPairs.put(
			"mutation#patchAccountChannelDeliveryTermId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"patchAccountChannelDeliveryTermId"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountChannelDiscountId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"deleteAccountChannelDiscountId"));
		_resourceMethodPairs.put(
			"mutation#patchAccountChannelDiscountId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"patchAccountChannelDiscountId"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountChannelPaymentTermId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"deleteAccountChannelPaymentTermId"));
		_resourceMethodPairs.put(
			"mutation#patchAccountChannelPaymentTermId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"patchAccountChannelPaymentTermId"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountChannelPriceListId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"deleteAccountChannelPriceListId"));
		_resourceMethodPairs.put(
			"mutation#patchAccountChannelPriceListId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"patchAccountChannelPriceListId"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountChannelShippingAddressId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"deleteAccountChannelShippingAddressId"));
		_resourceMethodPairs.put(
			"mutation#patchAccountChannelShippingAddressId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"patchAccountChannelShippingAddressId"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountChannelUserId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"deleteAccountChannelUserId"));
		_resourceMethodPairs.put(
			"mutation#patchAccountChannelUserId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"patchAccountChannelUserId"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountChannelBillingAddress",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountChannelBillingAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountChannelCurrency",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountChannelCurrency"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountChannelDeliveryTerm",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountChannelDeliveryTerm"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountChannelDiscount",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountChannelDiscount"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountChannelPaymentTerm",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountChannelPaymentTerm"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountChannelPriceList",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountChannelPriceList"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountChannelShippingAddress",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountChannelShippingAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountChannelUser",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountChannelUser"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountChannelBillingAddress",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountIdAccountChannelBillingAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountChannelCurrency",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountIdAccountChannelCurrency"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountChannelDeliveryTerm",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountIdAccountChannelDeliveryTerm"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountChannelDiscount",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountIdAccountChannelDiscount"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountChannelPaymentTerm",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountIdAccountChannelPaymentTerm"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountChannelPriceList",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountIdAccountChannelPriceList"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountChannelShippingAddress",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountIdAccountChannelShippingAddress"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountChannelUser",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"postAccountIdAccountChannelUser"));
		_resourceMethodPairs.put(
			"mutation#createAccountGroup",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class, "postAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#createAccountGroupBatch",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class, "postAccountGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountGroupByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class,
				"deleteAccountGroupByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchAccountGroupByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class,
				"patchAccountGroupByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountGroup",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class, "deleteAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountGroupBatch",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class, "deleteAccountGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#patchAccountGroup",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class, "patchAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountMember",
			new ObjectValuePair<>(
				AccountMemberResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountMember"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountByExternalReferenceCodeAccountMember",
			new ObjectValuePair<>(
				AccountMemberResourceImpl.class,
				"deleteAccountByExternalReferenceCodeAccountMember"));
		_resourceMethodPairs.put(
			"mutation#patchAccountByExternalReferenceCodeAccountMember",
			new ObjectValuePair<>(
				AccountMemberResourceImpl.class,
				"patchAccountByExternalReferenceCodeAccountMember"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountMember",
			new ObjectValuePair<>(
				AccountMemberResourceImpl.class, "postAccountIdAccountMember"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountMemberBatch",
			new ObjectValuePair<>(
				AccountMemberResourceImpl.class,
				"postAccountIdAccountMemberBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountIdAccountMember",
			new ObjectValuePair<>(
				AccountMemberResourceImpl.class,
				"deleteAccountIdAccountMember"));
		_resourceMethodPairs.put(
			"mutation#patchAccountIdAccountMember",
			new ObjectValuePair<>(
				AccountMemberResourceImpl.class,
				"patchAccountIdAccountMember"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountOrganization",
			new ObjectValuePair<>(
				AccountOrganizationResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountOrganization"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountByExternalReferenceCodeAccountOrganization",
			new ObjectValuePair<>(
				AccountOrganizationResourceImpl.class,
				"deleteAccountByExternalReferenceCodeAccountOrganization"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountOrganization",
			new ObjectValuePair<>(
				AccountOrganizationResourceImpl.class,
				"postAccountIdAccountOrganization"));
		_resourceMethodPairs.put(
			"mutation#createAccountIdAccountOrganizationBatch",
			new ObjectValuePair<>(
				AccountOrganizationResourceImpl.class,
				"postAccountIdAccountOrganizationBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteAccountIdAccountOrganization",
			new ObjectValuePair<>(
				AccountOrganizationResourceImpl.class,
				"deleteAccountIdAccountOrganization"));
		_resourceMethodPairs.put(
			"mutation#createAccountByExternalReferenceCodeAccountMemberCreateUser",
			new ObjectValuePair<>(
				UserResourceImpl.class,
				"postAccountByExternalReferenceCodeAccountMemberCreateUser"));
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
			"query#accountAddressByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class,
				"getAccountAddressByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#accountAddress",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class, "getAccountAddress"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountAddresses",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountAddressesPage"));
		_resourceMethodPairs.put(
			"query#accountIdAccountAddresses",
			new ObjectValuePair<>(
				AccountAddressResourceImpl.class,
				"getAccountIdAccountAddressesPage"));
		_resourceMethodPairs.put(
			"query#accountChannelBillingAddressId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountChannelBillingAddressId"));
		_resourceMethodPairs.put(
			"query#accountChannelCurrencyId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountChannelCurrencyId"));
		_resourceMethodPairs.put(
			"query#accountChannelDeliveryTermId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountChannelDeliveryTermId"));
		_resourceMethodPairs.put(
			"query#accountChannelDiscountId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountChannelDiscountId"));
		_resourceMethodPairs.put(
			"query#accountChannelPaymentTermId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountChannelPaymentTermId"));
		_resourceMethodPairs.put(
			"query#accountChannelPriceListId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountChannelPriceListId"));
		_resourceMethodPairs.put(
			"query#accountChannelShippingAddressId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountChannelShippingAddressId"));
		_resourceMethodPairs.put(
			"query#accountChannelUserId",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountChannelUserId"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountChannelBillingAddresses",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountChannelBillingAddressesPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountChannelCurrencies",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountChannelCurrenciesPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountChannelDeliveryTerms",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountChannelDeliveryTermsPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountChannelDiscounts",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountChannelDiscountsPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountChannelPaymentTerms",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountChannelPaymentTermsPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountChannelPriceLists",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountChannelPriceListsPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountChannelShippingAddresses",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountChannelShippingAddressesPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountChannelUsers",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountChannelUsersPage"));
		_resourceMethodPairs.put(
			"query#accountIdAccountChannelBillingAddresses",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountIdAccountChannelBillingAddressesPage"));
		_resourceMethodPairs.put(
			"query#accountIdAccountChannelCurrencies",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountIdAccountChannelCurrenciesPage"));
		_resourceMethodPairs.put(
			"query#accountIdAccountChannelDeliveryTerms",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountIdAccountChannelDeliveryTermsPage"));
		_resourceMethodPairs.put(
			"query#accountIdAccountChannelDiscounts",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountIdAccountChannelDiscountsPage"));
		_resourceMethodPairs.put(
			"query#accountIdAccountChannelPaymentTerms",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountIdAccountChannelPaymentTermsPage"));
		_resourceMethodPairs.put(
			"query#accountIdAccountChannelPriceLists",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountIdAccountChannelPriceListsPage"));
		_resourceMethodPairs.put(
			"query#accountIdAccountChannelShippingAddresses",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountIdAccountChannelShippingAddressesPage"));
		_resourceMethodPairs.put(
			"query#accountIdAccountChannelUsers",
			new ObjectValuePair<>(
				AccountChannelEntryResourceImpl.class,
				"getAccountIdAccountChannelUsersPage"));
		_resourceMethodPairs.put(
			"query#accountGroups",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class, "getAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#accountGroupByExternalReferenceCode",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class,
				"getAccountGroupByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#accountGroup",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class, "getAccountGroup"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountGroups",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#accountIdAccountGroups",
			new ObjectValuePair<>(
				AccountGroupResourceImpl.class,
				"getAccountIdAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountMembers",
			new ObjectValuePair<>(
				AccountMemberResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountMembersPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountMember",
			new ObjectValuePair<>(
				AccountMemberResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountMember"));
		_resourceMethodPairs.put(
			"query#accountIdAccountMembers",
			new ObjectValuePair<>(
				AccountMemberResourceImpl.class,
				"getAccountIdAccountMembersPage"));
		_resourceMethodPairs.put(
			"query#accountIdAccountMember",
			new ObjectValuePair<>(
				AccountMemberResourceImpl.class, "getAccountIdAccountMember"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountOrganizations",
			new ObjectValuePair<>(
				AccountOrganizationResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountOrganizationsPage"));
		_resourceMethodPairs.put(
			"query#accountByExternalReferenceCodeAccountOrganization",
			new ObjectValuePair<>(
				AccountOrganizationResourceImpl.class,
				"getAccountByExternalReferenceCodeAccountOrganization"));
		_resourceMethodPairs.put(
			"query#accountIdAccountOrganizations",
			new ObjectValuePair<>(
				AccountOrganizationResourceImpl.class,
				"getAccountIdAccountOrganizationsPage"));
		_resourceMethodPairs.put(
			"query#accountIdAccountOrganization",
			new ObjectValuePair<>(
				AccountOrganizationResourceImpl.class,
				"getAccountIdAccountOrganization"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountAddressResource>
		_accountAddressResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountChannelEntryResource>
		_accountChannelEntryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountGroupResource>
		_accountGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountMemberResource>
		_accountMemberResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountOrganizationResource>
		_accountOrganizationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<UserResource>
		_userResourceComponentServiceObjects;

}