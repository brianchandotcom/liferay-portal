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
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.base.AccountEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.OrganizationModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.OrganizationPermission;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collections;
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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addAccountEntry(long, long, String, String, String[],
	 *             byte[], int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, byte[] logoBytes, int status)
		throws PortalException {

		return addAccountEntry(
			userId, parentAccountEntryId, name, description, domains, logoBytes,
			status, null);
	}

	@Override
	public AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, byte[] logoBytes, int status,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, AccountActionKeys.ADD_ACCOUNT_ENTRY);

		return accountEntryLocalService.addAccountEntry(
			userId, parentAccountEntryId, name, description, domains, logoBytes,
			status, serviceContext);
	}

	@Override
	public List<AccountEntry> getAccountEntries(
			long companyId, int status, int start, int end,
			OrderByComparator<AccountEntry> obc)
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
			companyId, status, start, end, obc);
	}

	@Override
	public BaseModelSearchResult<AccountEntry> search(
		String keywords, LinkedHashMap<String, Object> params, int cur,
		int delta, String orderByField, boolean reverse) {

		PermissionChecker permissionChecker = _getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			try {
				User user = userLocalService.getUser(
					permissionChecker.getUserId());

				LinkedHashMap<String, Object> organizationParams =
					LinkedHashMapBuilder.<String, Object>put(
						"organizationsTree",
						ListUtil.filter(
							user.getOrganizations(true),
							organization -> _hasManageAccountsPermission(
								permissionChecker, organization))
					).build();

				BaseModelSearchResult<Organization> baseModelSearchResult =
					_organizationLocalService.searchOrganizations(
						user.getCompanyId(),
						OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null,
						organizationParams, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null);

				if (baseModelSearchResult.getLength() == 0) {
					return new BaseModelSearchResult<>(
						Collections.<AccountEntry>emptyList(), 0);
				}

				if (params == null) {
					params = new LinkedHashMap<>();
				}

				params.put(
					"organizationIds",
					ListUtil.toLongArray(
						baseModelSearchResult.getBaseModels(),
						OrganizationModel::getOrganizationId));
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException, portalException);
				}

				return new BaseModelSearchResult<>(
					Collections.<AccountEntry>emptyList(), 0);
			}
		}

		return accountEntryLocalService.search(
			permissionChecker.getCompanyId(), keywords, params, cur, delta,
			orderByField, reverse);
	}

	private PermissionChecker _getPermissionChecker() {
		try {
			return getPermissionChecker();
		}
		catch (PrincipalException principalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(principalException, principalException);
			}

			return PermissionThreadLocal.getPermissionChecker();
		}
	}

	private boolean _hasManageAccountsPermission(
		PermissionChecker permissionChecker, Organization organization) {

		try {
			_organizationPermission.check(
				permissionChecker, organization,
				AccountActionKeys.MANAGE_ACCOUNTS);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryServiceImpl.class);

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private OrganizationPermission _organizationPermission;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(resource.name=" + AccountConstants.RESOURCE_NAME + ")"
	)
	private volatile PortletResourcePermission _portletResourcePermission;

}