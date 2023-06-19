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

package com.liferay.commerce.product.internal.permission;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.permission.CommerceCatalogPermission;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(service = CommerceCatalogPermission.class)
public class CommerceCatalogPermissionImpl
	implements CommerceCatalogPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceCatalog commerceCatalog, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceCatalog, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceCatalog.class.getName(),
				commerceCatalog.getCommerceCatalogId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceCatalogId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceCatalogId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceCatalog.class.getName(),
				commerceCatalogId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceCatalog commerceCatalog, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, commerceCatalog.getCommerceCatalogId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceCatalogId,
			String actionId)
		throws PortalException {

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.fetchCommerceCatalog(
				commerceCatalogId);

		if (commerceCatalog == null) {
			return false;
		}

		return _contains(permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceCatalogIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceCatalogIds)) {
			return false;
		}

		for (long commerceCatalogId : commerceCatalogIds) {
			if (!contains(permissionChecker, commerceCatalogId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CommerceCatalog commerceCatalog, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(commerceCatalog.getCompanyId()) ||
			permissionChecker.hasOwnerPermission(
				commerceCatalog.getCompanyId(), CommerceCatalog.class.getName(),
				commerceCatalog.getCommerceCatalogId(),
				commerceCatalog.getUserId(), actionId)) {

			return true;
		}

		if ((commerceCatalog.getAccountEntryId() > 0) &&
			(actionId.equals(ActionKeys.UPDATE) ||
			 actionId.equals(ActionKeys.VIEW))) {

			List<AccountEntry> accountEntries =
				_accountEntryLocalService.getUserAccountEntries(
					permissionChecker.getUserId(), 0L, StringPool.BLANK,
					new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_SUPPLIER},
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (AccountEntry accountEntry : accountEntries) {
				if ((commerceCatalog.getAccountEntryId() ==
						accountEntry.getAccountEntryId()) &&
					_userGroupRoleLocalService.hasUserGroupRole(
						permissionChecker.getUserId(),
						accountEntry.getAccountEntryGroupId(),
						AccountRoleConstants.ROLE_NAME_ACCOUNT_SUPPLIER)) {

					return true;
				}
			}
		}

		return permissionChecker.hasPermission(
			commerceCatalog.getGroupId(), CommerceCatalog.class.getName(),
			commerceCatalog.getCommerceCatalogId(), actionId);
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}