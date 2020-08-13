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

import com.liferay.account.rest.dto.v1_0.AccountRole;
import com.liferay.account.rest.internal.dto.v1_0.converter.AccountResourceDTOConverter;
import com.liferay.account.rest.internal.dto.v1_0.converter.AccountUserResourceDTOConverter;
import com.liferay.account.rest.resource.v1_0.AccountRoleResource;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.comparator.RoleDescriptionComparator;
import com.liferay.portal.kernel.util.comparator.RoleNameComparator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.util.Collections;
import java.util.Objects;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Drew Brokke
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account-role.properties",
	scope = ServiceScope.PROTOTYPE, service = AccountRoleResource.class
)
public class AccountRoleResourceImpl
	extends BaseAccountRoleResourceImpl implements EntityModelResource {

	@Override
	public void deleteAccountRoleUserAssociation(
			Long accountId, Long accountRoleId, Long accountUserId)
		throws Exception {

		_accountRoleLocalService.unassociateUser(
			accountId, accountRoleId, accountUserId);
	}

	@Override
	public void deleteAccountRoleUserAssociationByExternalReferenceCode(
			String accountExternalReferenceCode, Long accountRoleId,
			String accountUserExternalReferenceCode)
		throws Exception {

		deleteAccountRoleUserAssociation(
			_accountResourceDTOConverter.getAccountEntryId(
				accountExternalReferenceCode),
			accountRoleId,
			_accountUserResourceDTOConverter.getUserId(
				accountUserExternalReferenceCode));
	}

	@Override
	public Page<AccountRole> getAccountRolesByExternalReferenceCodePage(
			String externalReferenceCode, String keywords,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return getAccountRolesPage(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode),
			keywords, pagination, sorts);
	}

	@Override
	public Page<AccountRole> getAccountRolesPage(
		Long accountId, String keywords, Pagination pagination, Sort[] sorts) {

		BaseModelSearchResult<com.liferay.account.model.AccountRole>
			baseModelSearchResult = _accountRoleLocalService.searchAccountRoles(
				accountId, keywords, pagination.getStartPosition(),
				pagination.getEndPosition(), _getOrderByComparator(sorts));

		return Page.of(
			transform(
				baseModelSearchResult.getBaseModels(), this::_toAccountRole),
			pagination, baseModelSearchResult.getLength());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public AccountRole postAccountRole(Long accountId, AccountRole accountRole)
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
	public AccountRole postAccountRoleByExternalReferenceCode(
			String externalReferenceCode, AccountRole accountRole)
		throws Exception {

		return postAccountRole(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode),
			accountRole);
	}

	@Override
	public void postAccountRoleUserAssociation(
			Long accountId, Long accountRoleId, Long accountUserId)
		throws Exception {

		_accountRoleLocalService.associateUser(
			accountId, accountRoleId, accountUserId);
	}

	@Override
	public void postAccountRoleUserAssociationByExternalReferenceCode(
			String accountExternalReferenceCode, Long accountRoleId,
			String accountUserExternalReferenceCode)
		throws Exception {

		postAccountRoleUserAssociation(
			_accountResourceDTOConverter.getAccountEntryId(
				accountExternalReferenceCode),
			accountRoleId,
			_accountUserResourceDTOConverter.getUserId(
				accountUserExternalReferenceCode));
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

	@Reference
	private AccountUserResourceDTOConverter _accountUserResourceDTOConverter;

	private final EntityModel _entityModel = Collections::emptyMap;

}