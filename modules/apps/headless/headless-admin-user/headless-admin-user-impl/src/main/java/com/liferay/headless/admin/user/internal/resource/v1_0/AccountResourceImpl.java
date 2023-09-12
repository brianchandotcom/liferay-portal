/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountListTypeConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderAddressUtil;
import com.liferay.headless.admin.user.internal.odata.entity.v1_0.AccountEntityModel;
import com.liferay.headless.admin.user.resource.v1_0.AccountResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.util.DTOConverterUtil;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Drew Brokke
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = AccountResource.class
)
public class AccountResourceImpl extends BaseAccountResourceImpl {

	@Override
	public void deleteAccount(Long accountId) throws Exception {
		_accountEntryService.deleteAccountEntry(accountId);
	}

	@Override
	public void deleteAccountByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		deleteAccount(
			DTOConverterUtil.getModelPrimaryKey(
				_accountResourceDTOConverter, externalReferenceCode));
	}

	@Override
	public void deleteOrganizationAccounts(
			Long organizationId, Long[] accountIds)
		throws Exception {

		for (Long accountId : accountIds) {
			_accountEntryOrganizationRelLocalService.
				deleteAccountEntryOrganizationRel(accountId, organizationId);
		}
	}

	@Override
	public void deleteOrganizationAccountsByExternalReferenceCode(
			Long organizationId, String[] externalReferenceCodes)
		throws Exception {

		for (String externalReferenceCode : externalReferenceCodes) {
			_accountEntryOrganizationRelLocalService.
				deleteAccountEntryOrganizationRel(
					DTOConverterUtil.getModelPrimaryKey(
						_accountResourceDTOConverter, externalReferenceCode),
					organizationId);
		}
	}

	@Override
	public Account getAccount(Long accountId) throws Exception {
		return _toAccount(_accountEntryService.getAccountEntry(accountId));
	}

	@Override
	public Account getAccountByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		return getAccount(
			DTOConverterUtil.getModelPrimaryKey(
				_accountResourceDTOConverter, externalReferenceCode));
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
					_accountEntryService.getAccountEntry(accountEntryId));
			});
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@NestedField(
		parentClass = Organization.class, value = "organizationAccounts"
	)
	@Override
	public Page<Account> getOrganizationAccountsPage(
			String organizationId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getOrganizationAccountsPage(
			Collections.emptyMap(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"organizationIds",
						String.valueOf(
							DTOConverterUtil.getModelPrimaryKey(
								_organizationResourceDTOConverter,
								organizationId))),
					BooleanClauseOccur.MUST);
			},
			search, filter, pagination, sorts);
	}

	@Override
	public void patchOrganizationMoveAccounts(
			Long sourceOrganizationId, Long targetOrganizationId,
			Long[] accountIds)
		throws Exception {

		deleteOrganizationAccounts(sourceOrganizationId, accountIds);
		postOrganizationAccounts(targetOrganizationId, accountIds);
	}

	@Override
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
		AccountEntry accountEntry = _accountEntryService.addAccountEntry(
			contextUser.getUserId(), _getParentAccountId(account),
			account.getName(), account.getDescription(), _getDomains(account),
			null, null, account.getTaxId(), _getType(account),
			_getStatus(account), _createServiceContext(account));

		if (_isValidId(account.getDefaultBillingAddressId())) {
			_accountEntryLocalService.updateDefaultBillingAddressId(
				accountEntry.getAccountEntryId(),
				account.getDefaultBillingAddressId());
		}

		if (_isValidId(account.getDefaultShippingAddressId())) {
			_accountEntryLocalService.updateDefaultShippingAddressId(
				accountEntry.getAccountEntryId(),
				account.getDefaultShippingAddressId());
		}

		accountEntry = _accountEntryService.updateExternalReferenceCode(
			accountEntry.getAccountEntryId(),
			account.getExternalReferenceCode());

		_accountEntryOrganizationRelLocalService.
			setAccountEntryOrganizationRels(
				accountEntry.getAccountEntryId(), _getOrganizationIds(account));

		_accountEntryUserRelLocalService.setAccountEntryUserRels(
			accountEntry.getAccountEntryId(),
			_getAccountUserAccountIds(account));

		for (Address address : _getAddresses(account)) {
			_addressLocalService.addAddress(
				address.getExternalReferenceCode(), contextUser.getUserId(),
				AccountEntry.class.getName(), accountEntry.getAccountEntryId(),
				address.getName(), address.getDescription(),
				address.getStreet1(), address.getStreet2(),
				address.getStreet3(), address.getCity(), address.getZip(),
				address.getRegionId(), address.getCountryId(),
				address.getListTypeId(), address.getMailing(),
				address.getPrimary(), address.getPhoneNumber(),
				_createServiceContext(account));
		}

		return _toAccount(accountEntry);
	}

	@Override
	public void postOrganizationAccounts(Long organizationId, Long[] accountIds)
		throws Exception {

		for (Long accountId : accountIds) {
			_accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(accountId, organizationId);
		}
	}

	@Override
	public void postOrganizationAccountsByExternalReferenceCode(
			Long organizationId, String[] externalReferenceCodes)
		throws Exception {

		for (String externalReferenceCode : externalReferenceCodes) {
			_accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(
					DTOConverterUtil.getModelPrimaryKey(
						_accountResourceDTOConverter, externalReferenceCode),
					organizationId);
		}
	}

	@Override
	public Account putAccount(Long accountId, Account account)
		throws Exception {

		_accountEntryService.updateExternalReferenceCode(
			accountId, account.getExternalReferenceCode());

		_accountEntryOrganizationRelLocalService.
			setAccountEntryOrganizationRels(
				accountId, _getOrganizationIds(account));

		_accountEntryUserRelLocalService.setAccountEntryUserRels(
			accountId, _getAccountUserAccountIds(account));

		for (Address address : _getAddresses(account)) {
			_addressLocalService.addAddress(
				address.getExternalReferenceCode(), contextUser.getUserId(),
				AccountEntry.class.getName(), accountId, address.getName(),
				address.getDescription(), address.getStreet1(),
				address.getStreet2(), address.getStreet3(), address.getCity(),
				address.getZip(), address.getRegionId(), address.getCountryId(),
				address.getListTypeId(), address.getMailing(),
				address.getPrimary(), address.getPhoneNumber(),
				_createServiceContext(account));
		}

		return _toAccount(
			_accountEntryService.updateAccountEntry(
				accountId, _getParentAccountId(account), account.getName(),
				account.getDescription(), false, _getDomains(account), null,
				null, null, _getStatus(account),
				_createServiceContext(account)));
	}

	@Override
	public Account putAccountByExternalReferenceCode(
			String externalReferenceCode, Account account)
		throws Exception {

		return _toAccount(
			_accountEntryService.addOrUpdateAccountEntry(
				externalReferenceCode, contextUser.getUserId(),
				_getParentAccountId(account), account.getName(),
				account.getDescription(), _getDomains(account), null, null,
				null, _getType(account), _getStatus(account),
				_createServiceContext(account)));
	}

	private ServiceContext _createServiceContext(Account account)
		throws Exception {

		ServiceContext serviceContext = ServiceContextBuilder.create(
			contextCompany.getGroupId(), contextHttpServletRequest, null
		).expandoBridgeAttributes(
			CustomFieldsUtil.toMap(
				AccountEntry.class.getName(), contextCompany.getCompanyId(),
				account.getCustomFields(),
				contextAcceptLanguage.getPreferredLocale())
		).build();

		serviceContext.setCompanyId(contextCompany.getCompanyId());
		serviceContext.setUserId(contextUser.getUserId());

		return serviceContext;
	}

	private long[] _getAccountUserAccountIds(Account account) {
		UserAccount[] userAccounts = account.getAccountUserAccounts();

		if (userAccounts == null) {
			return new long[0];
		}

		Long[] userAccountIds = transform(
			userAccounts, userAccount -> userAccount.getId(), Long.class);

		return ArrayUtil.toArray(userAccountIds);
	}

	private List<Address> _getAddresses(Account account) {
		PostalAddress[] postalAddresses = account.getPostalAddresses();

		if (postalAddresses == null) {
			return Collections.emptyList();
		}

		return ListUtil.filter(
			transformToList(
				postalAddresses,
				_postalAddress ->
					ServiceBuilderAddressUtil.toServiceBuilderAddress(
						contextCompany.getCompanyId(), _postalAddress,
						AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS)),
			Objects::nonNull);
	}

	private String[] _getDomains(Account account) {
		String[] domains = account.getDomains();

		if (domains == null) {
			return new String[0];
		}

		return domains;
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
				_accountEntryService.getAccountEntry(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	private long[] _getOrganizationIds(Account account) {
		Long[] organizationIds = account.getOrganizationIds();

		if (organizationIds == null) {
			return new long[0];
		}

		return ArrayUtil.toArray(organizationIds);
	}

	private long _getParentAccountId(Account account) {
		Long parentAccountId = account.getParentAccountId();

		if (parentAccountId == null) {
			return AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT;
		}

		return parentAccountId;
	}

	private int _getStatus(Account account) {
		Integer status = account.getStatus();

		if (status == null) {
			return WorkflowConstants.STATUS_APPROVED;
		}

		return status;
	}

	private String _getType(Account account) {
		String type = account.getTypeAsString();

		if (type == null) {
			return AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS;
		}

		return type;
	}

	private boolean _isValidId(Long value) {
		if ((value == null) || (value <= 0)) {
			return false;
		}

		return true;
	}

	private Account _toAccount(AccountEntry accountEntry) throws Exception {
		return _accountResourceDTOConverter.toDTO(
			_getDTOConverterContext(accountEntry.getAccountEntryId()));
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference(target = DTOConverterConstants.ACCOUNT_RESOURCE_DTO_CONVERTER)
	private DTOConverter<AccountEntry, Account> _accountResourceDTOConverter;

	@Reference
	private AddressLocalService _addressLocalService;

	private final EntityModel _entityModel = new AccountEntityModel();

	@Reference(
		target = DTOConverterConstants.ORGANIZATION_RESOURCE_DTO_CONVERTER
	)
	private DTOConverter
		<com.liferay.portal.kernel.model.Organization, Organization>
			_organizationResourceDTOConverter;

}