/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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

		if (permissionChecker.hasOwnerPermission(
				commerceCatalog.getCompanyId(), CommerceCatalog.class.getName(),
				commerceCatalog.getCommerceCatalogId(),
				commerceCatalog.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				commerceCatalog.getGroupId(), CommerceCatalog.class.getName(),
				commerceCatalog.getCommerceCatalogId(), actionId) ||
			permissionChecker.isCompanyAdmin(commerceCatalog.getCompanyId()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		if ((actionId.equals(ActionKeys.UPDATE) ||
			 actionId.equals(ActionKeys.VIEW)) &&
			_hasRoleAccountSupplier(permissionChecker, commerceCatalog)) {

			return true;
		}

		return false;
	}

	private boolean _hasRoleAccountSupplier(
			PermissionChecker permissionChecker,
			CommerceCatalog commerceCatalog)
		throws PortalException {

		if (commerceCatalog.getAccountEntryId() == 0) {
			return false;
		}

		List<AccountEntry> accountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				permissionChecker.getUserId(), 0L, StringPool.BLANK,
				new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_SUPPLIER},
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

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

		return false;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}