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

package com.liferay.commerce.account.model.impl;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.CommerceAccountLocalServiceUtil;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelLocalServiceUtil;
import com.liferay.commerce.account.service.CommerceAccountUserRelLocalServiceUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountImpl extends CommerceAccountBaseImpl {

	public static CommerceAccount fromAccountEntry(AccountEntry accountEntry) {
		if (accountEntry == null) {
			return null;
		}

		CommerceAccount commerceAccount = new CommerceAccountImpl();

		Map<String, Object> modelAttributes = accountEntry.getModelAttributes();

		Map<String, BiConsumer<CommerceAccount, Object>>
			attributeSetterBiConsumers =
				commerceAccount.getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : modelAttributes.entrySet()) {
			String key = entry.getKey();

			BiConsumer<CommerceAccount, Object>
				commerceAccountObjectBiConsumer =
					attributeSetterBiConsumers.get(key);

			if (commerceAccountObjectBiConsumer != null) {
				Object value = entry.getValue();

				if (key.equals("type")) {
					value = toCommerceAccountType((String)value);
				}

				commerceAccountObjectBiConsumer.accept(commerceAccount, value);
			}
		}

		commerceAccount.setCommerceAccountId(accountEntry.getAccountEntryId());
		commerceAccount.setParentCommerceAccountId(
			accountEntry.getParentAccountEntryId());
		commerceAccount.setEmail(accountEntry.getEmailAddress());
		commerceAccount.setTaxId(accountEntry.getTaxIdNumber());
		commerceAccount.setActive(
			toCommerceAccountActive(accountEntry.getStatus()));
		commerceAccount.setDisplayDate(null);
		commerceAccount.setExpirationDate(null);
		commerceAccount.setUserUuid(accountEntry.getUserUuid());

		return commerceAccount;
	}

	public static Integer toAccountEntryStatus(Boolean commerceAccountActive) {
		if (commerceAccountActive == null) {
			return null;
		}

		if (commerceAccountActive) {
			return WorkflowConstants.STATUS_APPROVED;
		}

		return WorkflowConstants.STATUS_INACTIVE;
	}

	public static String toAccountEntryType(int commerceAccountType) {
		if (commerceAccountType ==
				CommerceAccountConstants.ACCOUNT_TYPE_BUSINESS) {

			return AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS;
		}
		else if (commerceAccountType ==
					CommerceAccountConstants.ACCOUNT_TYPE_GUEST) {

			return AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST;
		}
		else if (commerceAccountType ==
					CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL) {

			return AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON;
		}

		return null;
	}

	public static String[] toAccountEntryTypes(int commerceSiteType) {
		if (commerceSiteType == CommerceAccountConstants.SITE_TYPE_B2X) {
			return new String[] {
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
				AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
			};
		}
		else if (commerceSiteType == CommerceAccountConstants.SITE_TYPE_B2B) {
			return new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS};
		}
		else if (commerceSiteType == CommerceAccountConstants.SITE_TYPE_B2C) {
			return new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON};
		}

		return null;
	}

	public static boolean toCommerceAccountActive(int accountEntryStatus) {
		if (accountEntryStatus == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}

		return false;
	}

	public static Integer toCommerceAccountType(String accountEntryType) {
		if (accountEntryType.equals(
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS)) {

			return CommerceAccountConstants.ACCOUNT_TYPE_BUSINESS;
		}
		else if (accountEntryType.equals(
					AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST)) {

			return CommerceAccountConstants.ACCOUNT_TYPE_GUEST;
		}
		else if (accountEntryType.equals(
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON)) {

			return CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL;
		}

		return CommerceAccountConstants.ACCOUNT_TYPE_GUEST;
	}

	public CommerceAccountImpl() {
	}

	@Override
	public Group getCommerceAccountGroup() throws PortalException {
		return CommerceAccountLocalServiceUtil.getCommerceAccountGroup(
			getCommerceAccountId());
	}

	@Override
	public long getCommerceAccountGroupId() throws PortalException {
		Group group = CommerceAccountLocalServiceUtil.getCommerceAccountGroup(
			getCommerceAccountId());

		return group.getGroupId();
	}

	@Override
	public List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels() {

		return CommerceAccountOrganizationRelLocalServiceUtil.
			getCommerceAccountOrganizationRels(getCommerceAccountId());
	}

	@Override
	public List<CommerceAccountUserRel> getCommerceAccountUserRels() {
		return CommerceAccountUserRelLocalServiceUtil.
			getCommerceAccountUserRels(getCommerceAccountId());
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), AccountEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public CommerceAccount getParentCommerceAccount() throws PortalException {
		if (isRoot()) {
			return null;
		}

		return CommerceAccountLocalServiceUtil.getCommerceAccount(
			getParentCommerceAccountId());
	}

	@Override
	public boolean isBusinessAccount() {
		if (getType() == CommerceAccountConstants.ACCOUNT_TYPE_BUSINESS) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isPersonalAccount() {
		if (getType() == CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoot() {
		if (getParentCommerceAccountId() ==
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID) {

			return true;
		}

		return false;
	}

}