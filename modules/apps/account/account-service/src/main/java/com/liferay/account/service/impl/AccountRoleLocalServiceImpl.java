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
import com.liferay.account.model.AccountRoleTable;
import com.liferay.account.service.base.AccountRoleLocalServiceBaseImpl;
import com.liferay.account.service.persistence.AccountEntryPersistence;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleTable;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		Role role = _roleLocalService.addRole(
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

		_roleLocalService.updateRole(role);

		_resourceLocalService.addResources(
			role.getCompanyId(), 0, userId, AccountRole.class.getName(),
			accountRole.getAccountRoleId(), false, false, false);

		return addAccountRole(accountRole);
	}

	@Override
	public void associateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		AccountRole accountRole = getAccountRole(accountRoleId);

		_userGroupRoleLocalService.addUserGroupRoles(
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

		Company company = _companyLocalService.getCompany(companyId);

		_checkAccountRole(
			company, AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER);
		_checkAccountRole(
			company,
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR);

		Role role = _roleLocalService.fetchRole(
			companyId, AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER);

		if (role == null) {
			User defaultUser = company.getDefaultUser();

			_roleLocalService.addRole(
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

		_resourceLocalService.deleteResource(
			accountRole.getCompanyId(), AccountRole.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, accountRole.getAccountRoleId());

		Role role = _roleLocalService.fetchRole(accountRole.getRoleId());

		if (role != null) {
			_userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
				accountRole.getRoleId());

			_roleLocalService.deleteRole(accountRole.getRoleId());
		}

		return accountRole;
	}

	@Override
	public AccountRole deleteAccountRole(long accountRoleId)
		throws PortalException {

		return deleteAccountRole(getAccountRole(accountRoleId));
	}

	@Override
	public void deleteAccountRolesByCompanyId(long companyId)
		throws PortalException {

		if (!CompanyThreadLocal.isDeleteInProcess()) {
			throw new UnsupportedOperationException(
				"Deleting account roles by company must be called when " +
					"deleting a company");
		}

		for (AccountRole accountRole :
				accountRolePersistence.findByCompanyId(companyId)) {

			deleteAccountRole(accountRole);
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

		AccountEntry accountEntry = _accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.getUserGroupRoles(
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

		AccountEntry accountEntry = _accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		AccountRole accountRole = getAccountRole(accountRoleId);

		return _userGroupRoleLocalService.hasUserGroupRole(
			userId, accountEntry.getAccountEntryGroupId(),
			accountRole.getRoleId());
	}

	@Override
	public BaseModelSearchResult<AccountRole> searchAccountRoles(
		long companyId, long[] accountEntryIds, String keywords,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<?> orderByComparator) {

		LinkedHashMap<String, Object> searchParams;

		if (params == null) {
			searchParams = new LinkedHashMap<>();
		}
		else {
			searchParams = params;
		}

		return BaseModelSearchResult.createWithStartAndEnd(
			startAndEnd -> accountRoleLocalService.dslQuery(
				_getGroupByStep(
					accountEntryIds, companyId,
					DSLQueryFactoryUtil.select(AccountRoleTable.INSTANCE),
					keywords, searchParams
				).orderBy(
					RoleTable.INSTANCE, orderByComparator
				).limit(
					startAndEnd.getStart(), startAndEnd.getEnd()
				)),
			accountRoleLocalService.dslQueryCount(
				_getGroupByStep(
					accountEntryIds, companyId,
					DSLQueryFactoryUtil.countDistinct(
						AccountRoleTable.INSTANCE.roleId),
					keywords, searchParams)),
			start, end);
	}

	@Override
	public void setUserAccountRoles(
			long accountEntryId, long[] accountRoleIds, long userId)
		throws PortalException {

		List<AccountRole> removeAccountRoles = new ArrayList<>();

		List<AccountRole> currentAccountRoles = getAccountRoles(
			accountEntryId, userId);

		for (AccountRole accountRole : currentAccountRoles) {
			if (!ArrayUtil.contains(
					accountRoleIds, accountRole.getAccountRoleId())) {

				removeAccountRoles.add(accountRole);
			}
		}

		associateUser(accountEntryId, accountRoleIds, userId);

		for (AccountRole accountRole : removeAccountRoles) {
			unassociateUser(
				accountEntryId, accountRole.getAccountRoleId(), userId);
		}
	}

	@Override
	public void unassociateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		AccountRole accountRole = getAccountRole(accountRoleId);

		_userGroupRoleLocalService.deleteUserGroupRoles(
			userId, accountEntry.getAccountEntryGroupId(),
			new long[] {accountRole.getRoleId()});
	}

	private void _checkAccountRole(Company company, String roleName)
		throws PortalException {

		Role role = _roleLocalService.fetchRole(
			company.getCompanyId(), roleName);

		if (role != null) {
			if (MapUtil.isEmpty(role.getDescriptionMap())) {
				role.setDescriptionMap(
					_roleDescriptionsMaps.get(role.getName()));

				_roleLocalService.updateRole(role);
			}

			return;
		}

		User defaultUser = company.getDefaultUser();

		addAccountRole(
			defaultUser.getUserId(), AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			roleName, null, _roleDescriptionsMaps.get(roleName));
	}

	private GroupByStep _getGroupByStep(
		long[] accountEntryIds, long companyId, FromStep fromStep,
		String keywords, LinkedHashMap<String, Object> params) {

		return fromStep.from(
			AccountRoleTable.INSTANCE
		).innerJoinON(
			RoleTable.INSTANCE,
			RoleTable.INSTANCE.roleId.eq(AccountRoleTable.INSTANCE.roleId)
		).where(
			AccountRoleTable.INSTANCE.companyId.eq(
				companyId
			).and(
				() -> {
					if (ArrayUtil.isEmpty(accountEntryIds)) {
						return null;
					}

					return AccountRoleTable.INSTANCE.accountEntryId.in(
						ArrayUtil.toLongArray(accountEntryIds));
				}
			).and(
				() -> {
					String[] excludedRoleNames = (String[])params.get(
						"excludedRoleNames");

					if (ArrayUtil.isEmpty(excludedRoleNames)) {
						return null;
					}

					return RoleTable.INSTANCE.name.notIn(excludedRoleNames);
				}
			).and(
				() -> {
					Long[] excludedRoleIds = (Long[])params.get(
						"excludedRoleIds");

					if (ArrayUtil.isEmpty(excludedRoleIds)) {
						return null;
					}

					return RoleTable.INSTANCE.roleId.notIn(excludedRoleIds);
				}
			).and(
				() -> {
					if (Validator.isNull(keywords)) {
						return null;
					}

					return Predicate.withParentheses(
						_customSQL.getKeywordsPredicate(
							DSLFunctionFactoryUtil.lower(
								RoleTable.INSTANCE.name),
							_customSQL.keywords(keywords, true)
						).or(
							_customSQL.getKeywordsPredicate(
								DSLFunctionFactoryUtil.lower(
									RoleTable.INSTANCE.title),
								_customSQL.keywords(keywords))
						).or(
							_customSQL.getKeywordsPredicate(
								DSLFunctionFactoryUtil.lower(
									RoleTable.INSTANCE.description),
								_customSQL.keywords(keywords))
						));
				}
			)
		);
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

	@Reference
	private AccountEntryPersistence _accountEntryPersistence;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}