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

import com.liferay.account.constants.AccountActionKeys;
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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Map;
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

	public void deleteOrganizationAccounts(
			Long organizationId, Long[] accountIds)
		throws Exception {

		for (Long accountId : accountIds) {
			_accountEntryOrganizationRelLocalService.
				deleteAccountEntryOrganizationRel(accountId, organizationId);
		}
	}

	public void deleteOrganizationAccountsByExternalReferenceCode(
			Long organizationId, String[] externalReferenceCodes)
		throws Exception {

		for (String externalReferenceCode : externalReferenceCodes) {
			_accountEntryOrganizationRelLocalService.
				deleteAccountEntryOrganizationRel(
					_accountResourceDTOConverter.getAccountEntryId(
						externalReferenceCode),
					organizationId);
		}
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
			HashMapBuilder.<String, Map<String, String>>put(
				"create",
				addAction(
					AccountActionKeys.ADD_ACCOUNT_ENTRY, "postAccount",
					AccountConstants.RESOURCE_NAME, 0L)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, 0L, "getAccountsPage",
					_accountEntryModelResourcePermission)
			).build(),
			booleanQuery -> {
			},
			filter, AccountEntry.class.getName(), keywords, pagination,
			queryConfig -> {
			},
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (Validator.isNotNull(keywords)) {
					searchContext.setKeywords(keywords);
				}
			},
			sorts,
			document -> {
				long accountEntryId = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				return _toAccount(
					_accountEntryLocalService.getAccountEntry(accountEntryId));
			});
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	public void patchOrganizationMoveAccounts(
			Long sourceOrganizationId, Long targetOrganizationId,
			Long[] accountIds)
		throws Exception {

		deleteOrganizationAccounts(sourceOrganizationId, accountIds);
		postOrganizationAccounts(targetOrganizationId, accountIds);
	}

	public void patchOrganizationMoveAccountsByExternalReferenceCode(
			Long sourceOrganizationId, Long targetOrganizationId,
			String[] externalReferenceCodes)
		throws Exception {

		deleteOrganizationAccountsByExternalReferenceCode(
			sourceOrganizationId, externalReferenceCodes);
		postOrganizationAccountsByExternalReferenceCode(
			targetOrganizationId, externalReferenceCodes);
	}

	@Override
	public Account postAccount(Account account) throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			contextUser.getUserId(), _getParentAccountId(account),
			account.getName(), account.getDescription(), _getDomains(account),
			null, null, null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
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

	public void postOrganizationAccounts(Long organizationId, Long[] accountIds)
		throws Exception {

		for (Long accountId : accountIds) {
			_accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(accountId, organizationId);
		}
	}

	public void postOrganizationAccountsByExternalReferenceCode(
			Long organizationId, String[] externalReferenceCodes)
		throws Exception {

		for (String externalReferenceCode : externalReferenceCodes) {
			_accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(
					_accountResourceDTOConverter.getAccountEntryId(
						externalReferenceCode),
					organizationId);
		}
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
				null, null, _getStatus(account), null));
	}

	@Override
	public Account putAccountByExternalReferenceCode(
			String externalReferenceCode, Account account)
		throws Exception {

		return _toAccount(
			_accountEntryLocalService.addOrUpdateAccountEntry(
				externalReferenceCode, contextUser.getUserId(),
				_getParentAccountId(account), account.getName(),
				account.getDescription(), _getDomains(account), null, null,
				null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
				_getStatus(account), null));
	}

	private Map<String, Map<String, String>> _getActions(Long accountEntryId) {
		return HashMapBuilder.<String, Map<String, String>>put(
			"create-organization-accounts",
			addAction(
				AccountActionKeys.MANAGE_ORGANIZATIONS, accountEntryId,
				"postOrganizationAccounts",
				_accountEntryModelResourcePermission)
		).put(
			"create-organization-accounts-by-external-reference-code",
			addAction(
				AccountActionKeys.MANAGE_ORGANIZATIONS, accountEntryId,
				"postOrganizationAccountsByExternalReferenceCode",
				_accountEntryModelResourcePermission)
		).put(
			"delete",
			addAction(
				ActionKeys.DELETE, accountEntryId, "deleteAccount",
				_accountEntryModelResourcePermission)
		).put(
			"delete-by-external-reference-code",
			addAction(
				ActionKeys.DELETE, accountEntryId,
				"deleteAccountByExternalReferenceCode",
				_accountEntryModelResourcePermission)
		).put(
			"delete-organization-accounts",
			addAction(
				AccountActionKeys.MANAGE_ORGANIZATIONS, accountEntryId,
				"deleteOrganizationAccounts",
				_accountEntryModelResourcePermission)
		).put(
			"delete-organization-accounts-by-external-reference-code",
			addAction(
				AccountActionKeys.MANAGE_ORGANIZATIONS, accountEntryId,
				"deleteOrganizationAccountsByExternalReferenceCode",
				_accountEntryModelResourcePermission)
		).put(
			"get",
			addAction(
				ActionKeys.VIEW, accountEntryId, "getAccount",
				_accountEntryModelResourcePermission)
		).put(
			"get-by-external-reference-code",
			addAction(
				ActionKeys.VIEW, accountEntryId,
				"getAccountByExternalReferenceCode",
				_accountEntryModelResourcePermission)
		).put(
			"move-organization-accounts",
			addAction(
				AccountActionKeys.MANAGE_ORGANIZATIONS, accountEntryId,
				"patchOrganizationMoveAccounts",
				_accountEntryModelResourcePermission)
		).put(
			"move-organization-accounts-by-external-reference-code",
			addAction(
				AccountActionKeys.MANAGE_ORGANIZATIONS, accountEntryId,
				"patchOrganizationMoveAccountsByExternalReferenceCode",
				_accountEntryModelResourcePermission)
		).put(
			"replace",
			addAction(
				ActionKeys.UPDATE, accountEntryId, "putAccount",
				_accountEntryModelResourcePermission)
		).put(
			"replace-by-external-reference-code",
			addAction(
				ActionKeys.UPDATE, accountEntryId,
				"putAccountByExternalReferenceCode",
				_accountEntryModelResourcePermission)
		).put(
			"update",
			addAction(
				ActionKeys.UPDATE, accountEntryId, "patchAccount",
				_accountEntryModelResourcePermission)
		).put(
			"update-by-external-reference-code",
			addAction(
				ActionKeys.UPDATE, accountEntryId,
				"patchAccountByExternalReferenceCode",
				_accountEntryModelResourcePermission)
		).build();
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
		Account account = _accountResourceDTOConverter.toDTO(accountEntry);

		account.setActions(_getActions(accountEntry.getAccountEntryId()));

		return account;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountResourceDTOConverter _accountResourceDTOConverter;

	private final EntityModel _entityModel = new AccountEntityModel();

}