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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.base.AccountRoleLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.TransformUtil;

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

		Role role = roleLocalService.addRole(
			userId, AccountRole.class.getName(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, name, titleMap,
			descriptionMap, RoleConstants.TYPE_ACCOUNT, null, null);

		AccountRole accountRole = fetchAccountRoleByRoleId(role.getRoleId());

		if (accountRole != null) {
			accountRole.setAccountEntryId(accountEntryId);

			return updateAccountRole(accountRole);
		}

		accountRole = createAccountRole(counterLocalService.increment());

		accountRole.setCompanyId(role.getCompanyId());
		accountRole.setAccountEntryId(accountEntryId);
		accountRole.setRoleId(role.getRoleId());

		role.setClassPK(accountRole.getAccountRoleId());

		roleLocalService.updateRole(role);

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
	public void associateUser(
			long accountEntryId, long[] accountRoleIds, long userId)
		throws PortalException {

		for (long accountRoleId : accountRoleIds) {
			associateUser(accountEntryId, accountRoleId, userId);
		}
	}

	@Override
	public void checkCompanyAccountRoles(long companyId)
		throws PortalException {

		Company company = companyLocalService.getCompany(companyId);

		_checkAccountRole(
			company, AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER);
		_checkAccountRole(
			company,
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR);

		Role role = roleLocalService.fetchRole(
			companyId, AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER);

		if (role == null) {
			User defaultUser = company.getDefaultUser();

			roleLocalService.addRole(
				defaultUser.getUserId(), null, 0,
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER, null,
				_roleDescriptionsMaps.get(
					AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER),
				RoleConstants.TYPE_ORGANIZATION, null, null);
		}
	}

	@Override
	public AccountRole deleteAccountRole(AccountRole accountRole)
		throws PortalException {

		accountRole = super.deleteAccountRole(accountRole);

		Role role = roleLocalService.fetchRole(accountRole.getRoleId());

		if (role != null) {
			userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
				accountRole.getRoleId());

			roleLocalService.deleteRole(accountRole.getRoleId());
		}

		return accountRole;
	}

	@Override
	public AccountRole deleteAccountRole(long accountRoleId)
		throws PortalException {

		return deleteAccountRole(getAccountRole(accountRoleId));
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

			accountRolePersistence.remove(accountRole);

			userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
				accountRole.getRoleId());
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

		List<UserGroupRole> userGroupRoles =
			userGroupRoleLocalService.getUserGroupRoles(
				userId, accountEntry.getAccountEntryGroupId());

		return TransformUtil.transform(
			ListUtil.filter(
				userGroupRoles,
				userGroupRole -> {
					try {
						Role role = userGroupRole.getRole();

						return role.getType() == RoleConstants.TYPE_ACCOUNT;
					}
					catch (PortalException portalException) {
						_log.error(portalException, portalException);

						return false;
					}
				}),
			userGroupRole -> getAccountRoleByRoleId(userGroupRole.getRoleId()));
	}

	@Override
	public List<AccountRole> getAccountRolesByAccountEntryIds(
		long[] accountEntryIds) {

		return accountRolePersistence.findByAccountEntryId(accountEntryIds);
	}

	@Override
	public boolean hasUserAccountRole(
			long accountEntryId, long accountRoleId, long userId)
		throws PortalException {

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		AccountRole accountRole = getAccountRole(accountRoleId);

		return userGroupRoleLocalService.hasUserGroupRole(
			userId, accountEntry.getAccountEntryGroupId(),
			accountRole.getRoleId());
	}

	@Override
	public BaseModelSearchResult<AccountRole> searchAccountRoles(
		long companyId, long accountEntryId, String keywords, int start,
		int end, OrderByComparator<?> orderByComparator) {

		return searchAccountRoles(
			companyId, new long[] {accountEntryId}, keywords, start, end,
			orderByComparator);
	}

	@Override
	public BaseModelSearchResult<AccountRole> searchAccountRoles(
		long companyId, long[] accountEntryIds, String keywords, int start,
		int end, OrderByComparator<?> orderByComparator) {

		DynamicQuery roleDynamicQuery = _getRoleDynamicQuery(
			companyId, accountEntryIds, keywords, orderByComparator);

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
				_getRoleDynamicQuery(
					companyId, accountEntryIds, keywords, null)));
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public BaseModelSearchResult<AccountRole> searchAccountRoles(
		long accountEntryId, String keywords, int start, int end,
		OrderByComparator<?> orderByComparator) {

		return searchAccountRoles(
			CompanyThreadLocal.getCompanyId(), new long[] {accountEntryId},
			keywords, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public BaseModelSearchResult<AccountRole> searchAccountRoles(
		long[] accountEntryIds, String keywords, int start, int end,
		OrderByComparator<?> orderByComparator) {

		return searchAccountRoles(
			CompanyThreadLocal.getCompanyId(), accountEntryIds, keywords, start,
			end, orderByComparator);
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

	private void _checkAccountRole(Company company, String roleName)
		throws PortalException {

		Role role = roleLocalService.fetchRole(
			company.getCompanyId(), roleName);

		if (role != null) {
			if (MapUtil.isEmpty(role.getDescriptionMap())) {
				role.setDescriptionMap(
					_roleDescriptionsMaps.get(role.getName()));

				roleLocalService.updateRole(role);
			}

			return;
		}

		User defaultUser = company.getDefaultUser();

		addAccountRole(
			defaultUser.getUserId(), AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			roleName, null, _roleDescriptionsMaps.get(roleName));
	}

	private DynamicQuery _getRoleDynamicQuery(
		long companyId, long[] accountEntryIds, String keywords,
		OrderByComparator<?> orderByComparator) {

		DynamicQuery accountRoleDynamicQuery =
			accountRoleLocalService.dynamicQuery();

		accountRoleDynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountEntryId", ListUtil.fromArray(accountEntryIds)));
		accountRoleDynamicQuery.add(
			RestrictionsFactoryUtil.eq("companyId", companyId));
		accountRoleDynamicQuery.setProjection(
			ProjectionFactoryUtil.property("roleId"));

		List<Long> roleIds = accountRoleLocalService.dynamicQuery(
			accountRoleDynamicQuery);

		if (roleIds.isEmpty()) {
			return null;
		}

		DynamicQuery roleDynamicQuery = roleLocalService.dynamicQuery();

		roleDynamicQuery.add(RestrictionsFactoryUtil.in("roleId", roleIds));

		if (Validator.isNotNull(keywords)) {
			Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

			disjunction.add(
				RestrictionsFactoryUtil.ilike(
					"name", StringUtil.quote(keywords, StringPool.PERCENT)));
			disjunction.add(
				RestrictionsFactoryUtil.ilike(
					"description",
					StringUtil.quote(keywords, StringPool.PERCENT)));
			disjunction.add(
				RestrictionsFactoryUtil.ilike(
					"title", StringUtil.quote(keywords, StringPool.PERCENT)));

			roleDynamicQuery.add(disjunction);
		}

		OrderFactoryUtil.addOrderByComparator(
			roleDynamicQuery, orderByComparator);

		return roleDynamicQuery;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountRoleLocalServiceImpl.class);

	private static final Map<String, Map<Locale, String>>
		_roleDescriptionsMaps = HashMapBuilder.<String, Map<Locale, String>>put(
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR,
			Collections.singletonMap(
				LocaleUtil.US,
				"Account Administrators are super users of their account.")
		).put(
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER,
			Collections.singletonMap(
				LocaleUtil.US,
				"Account Managers who belong to an organization can " +
					"administer all accounts associated to that organization.")
		).put(
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER,
			Collections.singletonMap(
				LocaleUtil.US,
				"All users who belong to an account have this role within " +
					"that account.")
		).build();

}