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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for AccountEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface AccountEntryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the account entry remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link AccountEntryServiceUtil} if injection and service tracking are not available.
	 */
	public void activateAccountEntries(long[] accountEntryIds)
		throws PortalException;

	public AccountEntry activateAccountEntry(long accountEntryId)
		throws PortalException;

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
	public AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, String email,
			byte[] logoBytes, String taxIdNumber, String type, int status,
			ServiceContext serviceContext)
		throws PortalException;

	public AccountEntry addOrUpdateAccountEntry(
			String externalReferenceCode, long userId,
			long parentAccountEntryId, String name, String description,
			String[] domains, String emailAddress, byte[] logoBytes,
			String taxIdNumber, String type, int status,
			ServiceContext serviceContext)
		throws PortalException;

	public void deactivateAccountEntries(long[] accountEntryIds)
		throws PortalException;

	public AccountEntry deactivateAccountEntry(long accountEntryId)
		throws PortalException;

	public void deleteAccountEntries(long[] accountEntryIds)
		throws PortalException;

	public void deleteAccountEntry(long accountEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AccountEntry fetchAccountEntry(long accountEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AccountEntry> getAccountEntries(
			long companyId, int status, int start, int end,
			OrderByComparator<AccountEntry> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AccountEntry getAccountEntry(long accountEntryId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<AccountEntry> searchAccountEntries(
			String keywords, LinkedHashMap<String, Object> params, int cur,
			int delta, String orderByField, boolean reverse)
		throws PortalException;

	public AccountEntry updateAccountEntry(AccountEntry accountEntry)
		throws PortalException;

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
	public AccountEntry updateAccountEntry(
			long accountEntryId, long parentAccountEntryId, String name,
			String description, boolean deleteLogo, String[] domains,
			String emailAddress, byte[] logoBytes, String taxIdNumber,
			int status, ServiceContext serviceContext)
		throws PortalException;

	public AccountEntry updateDomains(long accountEntryId, String[] domains)
		throws PortalException;

	public AccountEntry updateExternalReferenceCode(
			long accountEntryId, String externalReferenceCode)
		throws PortalException;

	public AccountEntry updateRestrictMembership(
			long accountEntryId, boolean restrictMembership)
		throws PortalException;

}