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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AccountEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryService
 * @generated
 */
public class AccountEntryServiceWrapper
	implements AccountEntryService, ServiceWrapper<AccountEntryService> {

	public AccountEntryServiceWrapper() {
		this(null);
	}

	public AccountEntryServiceWrapper(AccountEntryService accountEntryService) {
		_accountEntryService = accountEntryService;
	}

	@Override
	public void activateAccountEntries(long[] accountEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryService.activateAccountEntries(accountEntryIds);
	}

	@Override
	public com.liferay.account.model.AccountEntry activateAccountEntry(
			long accountEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.activateAccountEntry(accountEntryId);
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
	@Override
	public com.liferay.account.model.AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, String email,
			byte[] logoBytes, String taxIdNumber, String type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.addAccountEntry(
			userId, parentAccountEntryId, name, description, domains, email,
			logoBytes, taxIdNumber, type, status, serviceContext);
	}

	@Override
	public com.liferay.account.model.AccountEntry addOrUpdateAccountEntry(
			String externalReferenceCode, long userId,
			long parentAccountEntryId, String name, String description,
			String[] domains, String emailAddress, byte[] logoBytes,
			String taxIdNumber, String type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.addOrUpdateAccountEntry(
			externalReferenceCode, userId, parentAccountEntryId, name,
			description, domains, emailAddress, logoBytes, taxIdNumber, type,
			status, serviceContext);
	}

	@Override
	public void deactivateAccountEntries(long[] accountEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryService.deactivateAccountEntries(accountEntryIds);
	}

	@Override
	public com.liferay.account.model.AccountEntry deactivateAccountEntry(
			long accountEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.deactivateAccountEntry(accountEntryId);
	}

	@Override
	public void deleteAccountEntries(long[] accountEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryService.deleteAccountEntries(accountEntryIds);
	}

	@Override
	public void deleteAccountEntry(long accountEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryService.deleteAccountEntry(accountEntryId);
	}

	@Override
	public com.liferay.account.model.AccountEntry fetchAccountEntry(
			long accountEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.fetchAccountEntry(accountEntryId);
	}

	@Override
	public java.util.List<com.liferay.account.model.AccountEntry>
			getAccountEntries(
				long companyId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.account.model.AccountEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.getAccountEntries(
			companyId, status, start, end, orderByComparator);
	}

	@Override
	public com.liferay.account.model.AccountEntry getAccountEntry(
			long accountEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.getAccountEntry(accountEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _accountEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.account.model.AccountEntry> searchAccountEntries(
				String keywords, java.util.LinkedHashMap<String, Object> params,
				int cur, int delta, String orderByField, boolean reverse)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.searchAccountEntries(
			keywords, params, cur, delta, orderByField, reverse);
	}

	@Override
	public com.liferay.account.model.AccountEntry updateAccountEntry(
			com.liferay.account.model.AccountEntry accountEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.updateAccountEntry(accountEntry);
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
	@Override
	public com.liferay.account.model.AccountEntry updateAccountEntry(
			long accountEntryId, long parentAccountEntryId, String name,
			String description, boolean deleteLogo, String[] domains,
			String emailAddress, byte[] logoBytes, String taxIdNumber,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.updateAccountEntry(
			accountEntryId, parentAccountEntryId, name, description, deleteLogo,
			domains, emailAddress, logoBytes, taxIdNumber, status,
			serviceContext);
	}

	@Override
	public com.liferay.account.model.AccountEntry updateDomains(
			long accountEntryId, String[] domains)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.updateDomains(accountEntryId, domains);
	}

	@Override
	public com.liferay.account.model.AccountEntry updateExternalReferenceCode(
			long accountEntryId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.updateExternalReferenceCode(
			accountEntryId, externalReferenceCode);
	}

	@Override
	public com.liferay.account.model.AccountEntry updateRestrictMembership(
			long accountEntryId, boolean restrictMembership)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.updateRestrictMembership(
			accountEntryId, restrictMembership);
	}

	@Override
	public AccountEntryService getWrappedService() {
		return _accountEntryService;
	}

	@Override
	public void setWrappedService(AccountEntryService accountEntryService) {
		_accountEntryService = accountEntryService;
	}

	private AccountEntryService _accountEntryService;

}