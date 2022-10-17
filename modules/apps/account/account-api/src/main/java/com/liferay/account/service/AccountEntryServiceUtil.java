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

package com.liferay.account.service;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for AccountEntry. This utility wraps
 * <code>com.liferay.account.service.impl.AccountEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryService
 * @generated
 */
public class AccountEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void activateAccountEntries(long[] accountEntryIds)
		throws PortalException {

		getService().activateAccountEntries(accountEntryIds);
	}

	public static AccountEntry activateAccountEntry(long accountEntryId)
		throws PortalException {

		return getService().activateAccountEntry(accountEntryId);
	}

	/**
	 * @param userId the creator's userId. Required.
	 * @param parentAccountEntryId the parent account's ID. Use 0 if there
	 is no parent account.
	 * @param name the account's name. Required.
	 * @param description the account's description. Optional.
	 * @param domains an array of email domains associated with the account.
	 This will define which users can be managed by the account
	 administrator, as well as optionally restricting membership to
	 users with matching email address domains. If the user does not
	 have the MANAGE_DOMAINS permission for accounts, this parameter
	 is ignored. Optional.
	 * @param email the email address associated with this account.
	 Optional.
	 * @param logoBytes the account's logo bytes. Optional.
	 * @param taxIdNumber the account's tax ID number. Optional.
	 * @param type the account's type. Must be one of: "business", "person"
	 * @param status the account's workflow status. If workflow is enabled for
	 the AccountEntry model, this parameter is ignored.
	 * @param serviceContext the service context object. This object can contain
	 information about the account's asset tags and categories, and
	 custom fields.
	 * @return the newly created AccountEntry instance
	 * @throws PortalException
	 */
	public static AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, String email,
			byte[] logoBytes, String taxIdNumber, String type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addAccountEntry(
			userId, parentAccountEntryId, name, description, domains, email,
			logoBytes, taxIdNumber, type, status, serviceContext);
	}

	public static AccountEntry addOrUpdateAccountEntry(
			String externalReferenceCode, long userId,
			long parentAccountEntryId, String name, String description,
			String[] domains, String emailAddress, byte[] logoBytes,
			String taxIdNumber, String type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateAccountEntry(
			externalReferenceCode, userId, parentAccountEntryId, name,
			description, domains, emailAddress, logoBytes, taxIdNumber, type,
			status, serviceContext);
	}

	public static void deactivateAccountEntries(long[] accountEntryIds)
		throws PortalException {

		getService().deactivateAccountEntries(accountEntryIds);
	}

	public static AccountEntry deactivateAccountEntry(long accountEntryId)
		throws PortalException {

		return getService().deactivateAccountEntry(accountEntryId);
	}

	public static void deleteAccountEntries(long[] accountEntryIds)
		throws PortalException {

		getService().deleteAccountEntries(accountEntryIds);
	}

	public static void deleteAccountEntry(long accountEntryId)
		throws PortalException {

		getService().deleteAccountEntry(accountEntryId);
	}

	public static AccountEntry fetchAccountEntry(long accountEntryId)
		throws PortalException {

		return getService().fetchAccountEntry(accountEntryId);
	}

	public static List<AccountEntry> getAccountEntries(
			long companyId, int status, int start, int end,
			OrderByComparator<AccountEntry> orderByComparator)
		throws PortalException {

		return getService().getAccountEntries(
			companyId, status, start, end, orderByComparator);
	}

	public static AccountEntry getAccountEntry(long accountEntryId)
		throws PortalException {

		return getService().getAccountEntry(accountEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<AccountEntry> searchAccountEntries(
				String keywords, java.util.LinkedHashMap<String, Object> params,
				int cur, int delta, String orderByField, boolean reverse)
			throws PortalException {

		return getService().searchAccountEntries(
			keywords, params, cur, delta, orderByField, reverse);
	}

	public static AccountEntry updateAccountEntry(AccountEntry accountEntry)
		throws PortalException {

		return getService().updateAccountEntry(accountEntry);
	}

	/**
	 * @param accountEntryId the account's ID. Required.
	 * @param parentAccountEntryId the parent account's ID. Use 0 if there
	 is no parent account.
	 * @param name the account's name. Required.
	 * @param description the account's description. Optional.
	 * @param domains an array of email domains associated with the account.
	 This will define which users can be managed by the account
	 administrator, as well as optionally restricting membership to
	 users with matching email address domains. If the user does not
	 have the MANAGE_DOMAINS permission for accounts, this parameter
	 is ignored. Optional.
	 * @param emailAddress the email address associated with this account.
	 Optional.
	 * @param logoBytes the account's logo bytes. Optional.
	 * @param taxIdNumber the account's tax ID number. Optional.
	 * @param status the account's workflow status. If workflow is enabled for
	 the AccountEntry model, this parameter is ignored.
	 * @param serviceContext the service context object. This object can contain
	 information about the account's asset tags and categories, and
	 custom fields.
	 * @return the updated AccountEntry instance
	 * @throws PortalException
	 */
	public static AccountEntry updateAccountEntry(
			long accountEntryId, long parentAccountEntryId, String name,
			String description, boolean deleteLogo, String[] domains,
			String emailAddress, byte[] logoBytes, String taxIdNumber,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateAccountEntry(
			accountEntryId, parentAccountEntryId, name, description, deleteLogo,
			domains, emailAddress, logoBytes, taxIdNumber, status,
			serviceContext);
	}

	public static AccountEntry updateDomains(
			long accountEntryId, String[] domains)
		throws PortalException {

		return getService().updateDomains(accountEntryId, domains);
	}

	public static AccountEntry updateExternalReferenceCode(
			long accountEntryId, String externalReferenceCode)
		throws PortalException {

		return getService().updateExternalReferenceCode(
			accountEntryId, externalReferenceCode);
	}

	public static AccountEntry updateRestrictMembership(
			long accountEntryId, boolean restrictMembership)
		throws PortalException {

		return getService().updateRestrictMembership(
			accountEntryId, restrictMembership);
	}

	public static AccountEntryService getService() {
		return _service;
	}

	private static volatile AccountEntryService _service;

}