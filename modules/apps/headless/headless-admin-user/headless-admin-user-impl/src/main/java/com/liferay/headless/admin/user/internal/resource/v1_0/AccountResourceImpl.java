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

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.AccountResourceDTOConverter;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.OrganizationResourceDTOConverter;
import com.liferay.headless.admin.user.internal.odata.entity.v1_0.AccountEntityModel;
import com.liferay.headless.admin.user.resource.v1_0.AccountResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
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
	scope = ServiceScope.PROTOTYPE,
	service = {AccountResource.class, NestedFieldSupport.class}
)
public class AccountResourceImpl
	extends BaseAccountResourceImpl implements NestedFieldSupport {

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
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.<String, Map<String, String>>put(
				"create",
				addAction(
					AccountActionKeys.ADD_ACCOUNT_ENTRY, "postAccount",
					PortletKeys.PORTAL, 0L)
			).put(
				"create-by-external-reference-code",
				addAction(
					AccountActionKeys.ADD_ACCOUNT_ENTRY,
					"putAccountByExternalReferenceCode", PortletKeys.PORTAL, 0L)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, 0L, "getAccountsPage",
					_accountEntryModelResourcePermission)
			).build(),
			booleanQuery -> {
			},
			filter, AccountEntry.class.getName(), search, pagination,
			queryConfig -> {
			},
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (Validator.isNotNull(search)) {
					searchContext.setKeywords(search);
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

	@NestedField(
		parentClass = com.liferay.headless.admin.user.dto.v1_0.Organization.class,
		value = "organizationAccounts"
	)
	@Override
	public Page<Account> getOrganizationAccountsPage(
			String organizationId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		Organization organization = _organizationResourceDTOConverter.getObject(
			organizationId);

		return _getOrganizationAccountsPage(
			Collections.emptyMap(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"organizationIds",
						String.valueOf(organization.getOrganizationId())),
					BooleanClauseOccur.MUST);
			},
			search, filter, pagination, sorts);
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
			null, null, null, _getType(account), _getStatus(account), null);

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
				null, _getType(account), _getStatus(account), null));
	}

	private String[] _getDomains(Account account) {
		return Optional.ofNullable(
			account.getDomains()
		).orElse(
			new String[0]
		);
	}

	private DTOConverterContext _getDTOConverterContext(long accountEntryId) {
		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			HashMapBuilder.<String, Map<String, String>>put(
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
			).build(),
			null, contextHttpServletRequest, accountEntryId,
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private Page<Account> _getOrganizationAccountsPage(
			Map<String, Map<String, String>> actions,
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			actions, booleanQueryUnsafeConsumer, filter,
			AccountEntry.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> _toAccount(
				_accountEntryLocalService.getAccountEntry(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
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

	private String _getType(Account account) {
		return Optional.ofNullable(
			account.getTypeAsString()
		).orElse(
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS
		);
	}

	private Account _toAccount(AccountEntry accountEntry) throws Exception {
		return _accountResourceDTOConverter.toDTO(
			_getDTOConverterContext(accountEntry.getAccountEntryId()));
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

	@Reference
	private OrganizationResourceDTOConverter _organizationResourceDTOConverter;

}