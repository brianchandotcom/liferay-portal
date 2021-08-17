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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.headless.admin.user.dto.v1_0.AccountRole;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.AccountResourceDTOConverter;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.UserResourceDTOConverter;
import com.liferay.headless.admin.user.resource.v1_0.AccountRoleResource;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.comparator.RoleDescriptionComparator;
import com.liferay.portal.kernel.util.comparator.RoleNameComparator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.Objects;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account-role.properties",
	scope = ServiceScope.PROTOTYPE, service = AccountRoleResource.class
)
public class AccountRoleResourceImpl extends BaseAccountRoleResourceImpl {

	@Override
	public void deleteAccountAccountRoleUserAccountAssociation(
			Long accountId, Long accountRoleId, Long userAccountId)
		throws Exception {

		_accountRoleLocalService.unassociateUser(
			accountId, accountRoleId, userAccountId);
	}

	@Override
	public void
			deleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
				String accountExternalReferenceCode, Long accountRoleId,
				String emailAddress)
		throws Exception {

		User user = _userLocalService.getUserByEmailAddress(
			contextCompany.getCompanyId(), emailAddress);

		deleteAccountAccountRoleUserAccountAssociation(
			_accountResourceDTOConverter.getAccountEntryId(
				accountExternalReferenceCode),
			accountRoleId, user.getUserId());
	}

	@Override
	public void
			deleteAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode(
				String accountExternalReferenceCode, Long accountRoleId,
				String userAccountExternalReferenceCode)
		throws Exception {

		deleteAccountAccountRoleUserAccountAssociation(
			_accountResourceDTOConverter.getAccountEntryId(
				accountExternalReferenceCode),
			accountRoleId,
			_userResourceDTOConverter.getUserId(
				userAccountExternalReferenceCode));
	}

	@Override
	public Page<AccountRole> getAccountAccountRolesByExternalReferenceCodePage(
			String externalReferenceCode, String keywords,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return getAccountAccountRolesPage(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode),
			keywords, pagination, sorts);
	}

	@Override
	public Page<AccountRole> getAccountAccountRolesPage(
		Long accountId, String keywords, Pagination pagination, Sort[] sorts) {

		BaseModelSearchResult<com.liferay.account.model.AccountRole>
			baseModelSearchResult = _accountRoleLocalService.searchAccountRoles(
				contextCompany.getCompanyId(),
				new long[] {
					AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, accountId
				},
				keywords, pagination.getStartPosition(),
				pagination.getEndPosition(), _getOrderByComparator(sorts));

		return Page.of(
			transform(
				baseModelSearchResult.getBaseModels(), this::_toAccountRole),
			pagination, baseModelSearchResult.getLength());
	}

	@Override
	public Page<AccountRole>
			getAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage(
				String externalReferenceCode, String emailAddress)
		throws Exception {

		User user = _userLocalService.getUserByEmailAddress(
			contextCompany.getCompanyId(), emailAddress);

		return Page.of(
			transform(
				_accountRoleLocalService.getAccountRoles(
					_accountResourceDTOConverter.getAccountEntryId(
						externalReferenceCode),
					user.getUserId()),
				accountRole -> _toAccountRole(accountRole)));
	}

	@Override
	public Page<AccountRole>
			getAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage(
				String accountExternalReferenceCode,
				String userAccountExternalReferenceCode)
		throws Exception {

		return Page.of(
			transform(
				_accountRoleLocalService.getAccountRoles(
					_accountResourceDTOConverter.getAccountEntryId(
						accountExternalReferenceCode),
					_userResourceDTOConverter.getUserId(
						userAccountExternalReferenceCode)),
				accountRole -> _toAccountRole(accountRole)));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public AccountRole postAccountAccountRole(
			Long accountId, AccountRole accountRole)
		throws Exception {

		return _toAccountRole(
			_accountRoleLocalService.addAccountRole(
				contextUser.getUserId(), accountId, accountRole.getName(),
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					accountRole.getDisplayName()),
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					accountRole.getDescription())));
	}

	@Override
	public AccountRole postAccountAccountRoleByExternalReferenceCode(
			String externalReferenceCode, AccountRole accountRole)
		throws Exception {

		return postAccountAccountRole(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode),
			accountRole);
	}

	@Override
	public void postAccountAccountRoleUserAccountAssociation(
			Long accountId, Long accountRoleId, Long userAccountId)
		throws Exception {

		_accountRoleLocalService.associateUser(
			accountId, accountRoleId, userAccountId);
	}

	@Override
	public void
			postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
				String accountExternalReferenceCode, Long accountRoleId,
				String emailAddress)
		throws Exception {

		User user = _userLocalService.getUserByEmailAddress(
			contextCompany.getCompanyId(), emailAddress);

		postAccountAccountRoleUserAccountAssociation(
			_accountResourceDTOConverter.getAccountEntryId(
				accountExternalReferenceCode),
			accountRoleId, user.getUserId());
	}

	@Override
	public void
			postAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode(
				String accountExternalReferenceCode, Long accountRoleId,
				String userAccountExternalReferenceCode)
		throws Exception {

		postAccountAccountRoleUserAccountAssociation(
			_accountResourceDTOConverter.getAccountEntryId(
				accountExternalReferenceCode),
			accountRoleId,
			_userResourceDTOConverter.getUserId(
				userAccountExternalReferenceCode));
	}

	private OrderByComparator<?> _getOrderByComparator(Sort[] sorts) {
		if ((sorts == null) || (sorts.length == 0)) {
			return _roleNameComparator;
		}

		Sort sort = sorts[0];

		if (Objects.equals(sort.getFieldName(), "description")) {
			return new RoleDescriptionComparator(!sort.isReverse());
		}
		else if (Objects.equals(sort.getFieldName(), "name")) {
			return new RoleNameComparator(!sort.isReverse());
		}

		return _roleNameComparator;
	}

	private AccountRole _toAccountRole(
			com.liferay.account.model.AccountRole serviceBuilderAccountRole)
		throws Exception {

		Role role = serviceBuilderAccountRole.getRole();

		return new AccountRole() {
			{
				accountId = serviceBuilderAccountRole.getAccountEntryId();
				description = role.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				displayName = role.getTitle(
					contextAcceptLanguage.getPreferredLocale());
				id = serviceBuilderAccountRole.getAccountRoleId();
				name = serviceBuilderAccountRole.getRoleName();
				roleId = serviceBuilderAccountRole.getRoleId();
			}
		};
	}

	private static final RoleNameComparator _roleNameComparator =
		new RoleNameComparator();

	@Reference
	private AccountResourceDTOConverter _accountResourceDTOConverter;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	private final EntityModel _entityModel = Collections::emptyMap;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserResourceDTOConverter _userResourceDTOConverter;

}