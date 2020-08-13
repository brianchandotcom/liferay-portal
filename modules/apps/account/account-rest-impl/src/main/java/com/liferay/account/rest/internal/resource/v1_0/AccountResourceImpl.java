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

package com.liferay.account.rest.internal.resource.v1_0;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.rest.dto.v1_0.Account;
import com.liferay.account.rest.internal.dto.v1_0.converter.AccountResourceDTOConverter;
import com.liferay.account.rest.internal.odata.entity.v1_0.AccountEntityModel;
import com.liferay.account.rest.resource.v1_0.AccountResource;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Drew Brokke
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account.properties",
	scope = ServiceScope.PROTOTYPE, service = AccountResource.class
)
public class AccountResourceImpl
	extends BaseAccountResourceImpl implements EntityModelResource {

	@Override
	public void deleteAccount(Long accountId) throws Exception {
		_accountEntryLocalService.deleteAccountEntry(accountId);
	}

	@Override
	public void deleteAccountByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		deleteAccount(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode));
	}

	@Override
	public Account getAccount(Long accountId) throws Exception {
		return _toAccount(_accountEntryLocalService.getAccountEntry(accountId));
	}

	@Override
	public Account getAccountByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		return getAccount(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode));
	}

	@Override
	public Page<Account> getAccountsPage(
			String keywords, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
			},
			filter, AccountEntry.class, keywords, pagination,
			queryConfig -> {
			},
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (Validator.isNotNull(keywords)) {
					searchContext.setKeywords(keywords);
				}
			},
			document -> {
				long accountEntryId = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				return _toAccount(
					_accountEntryLocalService.getAccountEntry(accountEntryId));
			},
			sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Account postAccount(Account account) throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			contextUser.getUserId(), _getParentAccountId(account),
			account.getName(), account.getDescription(), _getDomains(account),
			null, null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			_getStatus(account), null);

		if (account.getExternalReferenceCode() != null) {
			accountEntry.setExternalReferenceCode(
				account.getExternalReferenceCode());

			accountEntry = _accountEntryLocalService.updateAccountEntry(
				accountEntry);
		}

		_accountEntryOrganizationRelLocalService.
			setAccountEntryOrganizationRels(
				accountEntry.getAccountEntryId(), _getOrganizationIds(account));

		return _toAccount(accountEntry);
	}

	@Override
	public Account putAccount(Long accountId, Account account)
		throws Exception {

		_accountEntryOrganizationRelLocalService.
			setAccountEntryOrganizationRels(
				accountId, _getOrganizationIds(account));

		return _toAccount(
			_accountEntryLocalService.updateAccountEntry(
				accountId, _getParentAccountId(account), account.getName(),
				account.getDescription(), false, _getDomains(account), null,
				null, _getStatus(account), null));
	}

	@Override
	public Account putAccountByExternalReferenceCode(
			String externalReferenceCode, Account account)
		throws Exception {

		return putAccount(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode),
			account);
	}

	private String[] _getDomains(Account account) {
		return Optional.ofNullable(
			account.getDomains()
		).orElse(
			new String[0]
		);
	}

	private long[] _getOrganizationIds(Account account) {
		return Optional.ofNullable(
			account.getOrganizationIds()
		).map(
			ArrayUtil::toArray
		).orElse(
			new long[0]
		);
	}

	private long _getParentAccountId(Account account) {
		return Optional.ofNullable(
			account.getParentAccountId()
		).orElse(
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT
		);
	}

	private int _getStatus(Account account) {
		return Optional.ofNullable(
			account.getStatus()
		).orElse(
			WorkflowConstants.STATUS_APPROVED
		);
	}

	private Account _toAccount(AccountEntry accountEntry) throws Exception {
		return _accountResourceDTOConverter.toDTO(accountEntry);
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountResourceDTOConverter _accountResourceDTOConverter;

	private final EntityModel _entityModel = new AccountEntityModel();

}