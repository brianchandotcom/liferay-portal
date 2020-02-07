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

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.base.AccountRoleLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountRole",
	service = AopService.class
)
public class AccountRoleLocalServiceImpl
	extends AccountRoleLocalServiceBaseImpl {

	@Override
	public AccountRole addAccountRole(
			long userId, long accountEntryId, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap)
		throws PortalException {

		AccountRole accountRole = createAccountRole(
			counterLocalService.increment());

		accountRole.setAccountEntryId(accountEntryId);

		User user = userLocalService.getUser(userId);

		accountRole.setCompanyId(user.getCompanyId());

		Role role = roleLocalService.addRole(
			userId, AccountRole.class.getName(), accountRole.getAccountRoleId(),
			name, titleMap, descriptionMap, RoleConstants.TYPE_PROVIDER, null,
			null);

		accountRole.setRoleId(role.getRoleId());

		return addAccountRole(accountRole);
	}

	@Override
	public void associateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws PortalException {

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		AccountRole accountRole = getAccountRole(accountRoleId);

		userGroupRoleLocalService.addUserGroupRoles(
			userId, accountEntry.getAccountEntryGroupId(),
			new long[] {accountRole.getRoleId()});
	}

	@Override
	public AccountRole deleteAccountRole(AccountRole accountRole)
		throws PortalException {

		accountRole = super.deleteAccountRole(accountRole);

		userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
			accountRole.getRoleId());

		roleLocalService.deleteRole(accountRole.getRoleId());

		return accountRole;
	}

	@Override
	public AccountRole deleteAccountRole(long accountRoleId)
		throws PortalException {

		AccountRole accountRole = super.deleteAccountRole(accountRoleId);

		userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
			accountRole.getRoleId());

		roleLocalService.deleteRole(accountRole.getRoleId());

		return accountRole;
	}

	@Override
	public void deleteAccountRolesByCompanyId(long companyId) {
		if (!CompanyThreadLocal.isDeleteInProcess()) {
			throw new UnsupportedOperationException(
				"Deleting account roles by company must be called when " +
					"deleting a company");
		}

		for (AccountRole accountRole :
				accountRolePersistence.findByCompanyId(companyId)) {

			userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
				accountRole.getRoleId());

			accountRolePersistence.remove(accountRole);
		}
	}

	@Override
	public AccountRole fetchAccountRoleByRoleId(long roleId) {
		return accountRolePersistence.fetchByRoleId(roleId);
	}

	@Override
	public AccountRole getAccountRoleByRoleId(long roleId)
		throws PortalException {

		return accountRolePersistence.findByRoleId(roleId);
	}

	@Override
	public List<AccountRole> getAccountRoles(long accountEntryId, long userId)
		throws PortalException {

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		return TransformUtil.transform(
			userGroupRoleLocalService.getUserGroupRoles(
				userId, accountEntry.getAccountEntryGroupId()),
			userGroupRole -> getAccountRoleByRoleId(userGroupRole.getRoleId()));
	}

	@Override
	public List<AccountRole> getAccountRolesByAccountEntryIds(
		long[] accountEntryIds) {

		return accountRolePersistence.findByAccountEntryId(accountEntryIds);
	}

	@Override
	public BaseModelSearchResult<AccountRole> searchAccountRoles(
		long accountEntryId, String keywords, int start, int end,
		OrderByComparator obc) {

		return searchAccountRoles(
			new long[] {accountEntryId}, keywords, start, end, obc);
	}

	@Override
	public BaseModelSearchResult<AccountRole> searchAccountRoles(
		long[] accountEntryIds, String keywords, int start, int end,
		OrderByComparator obc) {

		DynamicQuery roleDynamicQuery = _getRoleDynamicQuery(
			accountEntryIds, keywords, obc);

		if (roleDynamicQuery == null) {
			return new BaseModelSearchResult<>(
				Collections.<AccountRole>emptyList(), 0);
		}

		List<AccountRole> accountRoles = TransformUtil.transform(
			roleLocalService.<Role>dynamicQuery(roleDynamicQuery, start, end),
			userGroupRole -> getAccountRoleByRoleId(userGroupRole.getRoleId()));

		return new BaseModelSearchResult<>(
			accountRoles,
			(int)roleLocalService.dynamicQueryCount(
				_getRoleDynamicQuery(accountEntryIds, keywords, null)));
	}

	@Override
	public void unassociateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws PortalException {

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		AccountRole accountRole = getAccountRole(accountRoleId);

		userGroupRoleLocalService.deleteUserGroupRoles(
			userId, accountEntry.getAccountEntryGroupId(),
			new long[] {accountRole.getRoleId()});
	}

	private DynamicQuery _getRoleDynamicQuery(
		long[] accountEntryIds, String keywords, OrderByComparator obc) {

		DynamicQuery accountRoleDynamicQuery =
			accountRoleLocalService.dynamicQuery();

		accountRoleDynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountEntryId", Arrays.asList(accountEntryIds)));
		accountRoleDynamicQuery.setProjection(
			ProjectionFactoryUtil.property("roleId"));

		List<Long> roleIds = accountRoleLocalService.dynamicQuery(
			accountRoleDynamicQuery);

		if (roleIds.isEmpty()) {
			return null;
		}

		DynamicQuery roleDynamicQuery = roleLocalService.dynamicQuery();

		roleDynamicQuery.add(RestrictionsFactoryUtil.in("roleId", roleIds));

		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		disjunction.add(
			RestrictionsFactoryUtil.ilike(
				"name", StringUtil.quote(keywords, StringPool.PERCENT)));
		disjunction.add(
			RestrictionsFactoryUtil.ilike(
				"description", StringUtil.quote(keywords, StringPool.PERCENT)));

		roleDynamicQuery.add(disjunction);

		if (obc != null) {
			Order order;

			if (obc.isAscending()) {
				order = OrderFactoryUtil.asc(obc.getOrderByFields()[0]);
			}
			else {
				order = OrderFactoryUtil.desc(obc.getOrderByFields()[0]);
			}

			roleDynamicQuery.addOrder(order);
		}

		return roleDynamicQuery;
	}

}