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

package com.liferay.account.service.impl;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.base.AccountEntryServiceBaseImpl;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.PortalPermission;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=account",
		"json.web.service.context.path=AccountEntry"
	},
	service = AopService.class
)
public class AccountEntryServiceImpl extends AccountEntryServiceBaseImpl {

	@Override
	public void activateAccountEntries(long[] accountEntryIds)
		throws PortalException {

		for (long accountEntryId : accountEntryIds) {
			activateAccountEntry(accountEntryId);
		}
	}

	@Override
	public AccountEntry activateAccountEntry(long accountEntryId)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		_accountEntryModelResourcePermission.check(
			permissionChecker, accountEntryId, ActionKeys.UPDATE);

		return _withServiceContext(
			() -> accountEntryLocalService.activateAccountEntry(accountEntryId),
			permissionChecker.getUserId());
	}

	/**
	 * @param userId the creator's userId. Required.
	 * @param parentAccountEntryId the parent account's ID. Use 0 if there
	 *        is no parent account.
	 * @param name the account's name. Required.
	 * @param description the account's description. Optional.
	 * @param domains an array of email domains associated with the account.
	 *        This will define which users can be managed by the account
	 *        administrator, as well as optionally restricting membership to
	 *        users with matching email address domains. If the user does not
	 *        have the MANAGE_DOMAINS permission for accounts, this parameter
	 *        is ignored. Optional.
	 * @param email the email address associated with this account.
	 *        Optional.
	 * @param logoBytes the account's logo bytes. Optional.
	 * @param taxIdNumber the account's tax ID number. Optional.
	 * @param type the account's type. Must be one of: "business", "person"
	 * @param status the account's workflow status. If workflow is enabled for
	 *        the AccountEntry model, this parameter is ignored.
	 * @param serviceContext the service context object. This object can contain
	 *        information about the account's asset tags and categories, and
	 *        custom fields.
	 *
	 * @return the newly created AccountEntry instance
	 * @throws PortalException
	 */
	@Override
	public AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, String email,
			byte[] logoBytes, String taxIdNumber, String type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		_portalPermission.check(
			getPermissionChecker(), AccountActionKeys.ADD_ACCOUNT_ENTRY);

		return accountEntryLocalService.addAccountEntry(
			userId, parentAccountEntryId, name, description,
			_getManageableDomains(0L, domains), email, logoBytes, taxIdNumber,
			type, status, serviceContext);
	}

	@Override
	public AccountEntry addOrUpdateAccountEntry(
			String externalReferenceCode, long userId,
			long parentAccountEntryId, String name, String description,
			String[] domains, String emailAddress, byte[] logoBytes,
			String taxIdNumber, String type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		AccountEntry accountEntry =
			accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				permissionChecker.getCompanyId(), externalReferenceCode);

		long accountEntryId = 0;

		if (accountEntry == null) {
			_portalPermission.check(
				permissionChecker, AccountActionKeys.ADD_ACCOUNT_ENTRY);
		}
		else {
			_accountEntryModelResourcePermission.check(
				permissionChecker, permissionChecker.getCompanyId(),
				ActionKeys.UPDATE);

			accountEntryId = accountEntry.getAccountEntryId();
		}

		return accountEntryLocalService.addOrUpdateAccountEntry(
			externalReferenceCode, userId, parentAccountEntryId, name,
			description, _getManageableDomains(accountEntryId, domains),
			emailAddress, logoBytes, taxIdNumber, type, status, serviceContext);
	}

	@Override
	public void deactivateAccountEntries(long[] accountEntryIds)
		throws PortalException {

		for (long accountEntryId : accountEntryIds) {
			deactivateAccountEntry(accountEntryId);
		}
	}

	@Override
	public AccountEntry deactivateAccountEntry(long accountEntryId)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		_accountEntryModelResourcePermission.check(
			permissionChecker, accountEntryId, ActionKeys.DELETE);

		return _withServiceContext(
			() -> accountEntryLocalService.deactivateAccountEntry(
				accountEntryId),
			permissionChecker.getUserId());
	}

	@Override
	public void deleteAccountEntries(long[] accountEntryIds)
		throws PortalException {

		for (long accountEntryId : accountEntryIds) {
			deleteAccountEntry(accountEntryId);
		}
	}

	@Override
	public void deleteAccountEntry(long accountEntryId) throws PortalException {
		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.DELETE);

		accountEntryLocalService.deleteAccountEntry(accountEntryId);
	}

	@Override
	public AccountEntry fetchAccountEntry(long accountEntryId)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.VIEW);

		return accountEntryLocalService.fetchAccountEntry(accountEntryId);
	}

	@Override
	public List<AccountEntry> getAccountEntries(
			long companyId, int status, int start, int end,
			OrderByComparator<AccountEntry> orderByComparator)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.hasPermission(
				null, AccountEntry.class.getName(), companyId,
				ActionKeys.VIEW)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, AccountEntry.class.getName(), 0,
				ActionKeys.VIEW);
		}

		return accountEntryLocalService.getAccountEntries(
			companyId, status, start, end, orderByComparator);
	}

	@Override
	public AccountEntry getAccountEntry(long accountEntryId)
		throws PortalException {

		AccountEntry accountEntry = accountEntryLocalService.getAccountEntry(
			accountEntryId);

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.VIEW);

		return accountEntry;
	}

	@Override
	public BaseModelSearchResult<AccountEntry> searchAccountEntries(
			String keywords, LinkedHashMap<String, Object> params, int cur,
			int delta, String orderByField, boolean reverse)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (params == null) {
			params = new LinkedHashMap<>();
		}

		params.put("permissionUserId", permissionChecker.getUserId());

		return accountEntryLocalService.searchAccountEntries(
			permissionChecker.getCompanyId(), keywords, params, cur, delta,
			orderByField, reverse);
	}

	@Override
	public AccountEntry updateAccountEntry(AccountEntry accountEntry)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntry, ActionKeys.UPDATE);

		if (!_accountEntryModelResourcePermission.contains(
				getPermissionChecker(), accountEntry.getAccountEntryId(),
				AccountActionKeys.MANAGE_DOMAINS)) {

			AccountEntry originalAccountEntry =
				accountEntryLocalService.getAccountEntry(
					accountEntry.getAccountEntryId());

			accountEntry.setDomains(originalAccountEntry.getDomains());
			accountEntry.setRestrictMembership(
				originalAccountEntry.isRestrictMembership());
		}

		return accountEntryLocalService.updateAccountEntry(accountEntry);
	}

	/**
	 * @param accountEntryId the account's ID. Required.
	 * @param parentAccountEntryId the parent account's ID. Use 0 if there
	 *        is no parent account.
	 * @param name the account's name. Required.
	 * @param description the account's description. Optional.
	 * @param domains an array of email domains associated with the account.
	 *        This will define which users can be managed by the account
	 *        administrator, as well as optionally restricting membership to
	 *        users with matching email address domains. If the user does not
	 *        have the MANAGE_DOMAINS permission for accounts, this parameter
	 *        is ignored. Optional.
	 * @param emailAddress the email address associated with this account.
	 *        Optional.
	 * @param logoBytes the account's logo bytes. Optional.
	 * @param taxIdNumber the account's tax ID number. Optional.
	 * @param status the account's workflow status. If workflow is enabled for
	 *        the AccountEntry model, this parameter is ignored.
	 * @param serviceContext the service context object. This object can contain
	 *        information about the account's asset tags and categories, and
	 *        custom fields.
	 *
	 * @return the updated AccountEntry instance
	 * @throws PortalException
	 */
	@Override
	public AccountEntry updateAccountEntry(
			long accountEntryId, long parentAccountEntryId, String name,
			String description, boolean deleteLogo, String[] domains,
			String emailAddress, byte[] logoBytes, String taxIdNumber,
			int status, ServiceContext serviceContext)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.UPDATE);

		return accountEntryLocalService.updateAccountEntry(
			accountEntryId, parentAccountEntryId, name, description, deleteLogo,
			_getManageableDomains(accountEntryId, domains), emailAddress,
			logoBytes, taxIdNumber, status, serviceContext);
	}

	@Override
	public AccountEntry updateDomains(long accountEntryId, String[] domains)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId,
			AccountActionKeys.MANAGE_DOMAINS);

		return accountEntryLocalService.updateDomains(accountEntryId, domains);
	}

	@Override
	public AccountEntry updateExternalReferenceCode(
			long accountEntryId, String externalReferenceCode)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.UPDATE);

		return accountEntryLocalService.updateExternalReferenceCode(
			accountEntryId, externalReferenceCode);
	}

	@Override
	public AccountEntry updateRestrictMembership(
			long accountEntryId, boolean restrictMembership)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId,
			AccountActionKeys.MANAGE_DOMAINS);

		return accountEntryLocalService.updateRestrictMembership(
			accountEntryId, restrictMembership);
	}

	private String[] _getManageableDomains(
			long accountEntryId, String[] domains)
		throws PortalException {

		if (_accountEntryModelResourcePermission.contains(
				getPermissionChecker(), accountEntryId,
				AccountActionKeys.MANAGE_DOMAINS)) {

			return domains;
		}

		return null;
	}

	private AccountEntry _withServiceContext(
			UnsafeSupplier<AccountEntry, PortalException> unsafeRunnable,
			long userId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(userId);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			return unsafeRunnable.get();
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private PortalPermission _portalPermission;

}