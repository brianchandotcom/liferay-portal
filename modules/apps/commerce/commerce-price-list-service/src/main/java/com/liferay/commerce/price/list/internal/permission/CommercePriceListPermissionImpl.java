/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.permission;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.permission.CommercePriceListPermission;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.model.CommerceCatalog;
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
 * @author Riccardo Alberti
 */
@Component(service = CommercePriceListPermission.class)
public class CommercePriceListPermissionImpl
	implements CommercePriceListPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePriceList commercePriceList, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commercePriceList, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommercePriceList.class.getName(),
				commercePriceList.getCommercePriceListId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commercePriceListId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commercePriceListId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommercePriceList.class.getName(),
				commercePriceListId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePriceList commercePriceList, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, commercePriceList.getCommercePriceListId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commercePriceListId,
			String actionId)
		throws PortalException {

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCommercePriceList(
				commercePriceListId);

		if (commercePriceList == null) {
			return false;
		}

		return _contains(permissionChecker, commercePriceList, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] commercePriceListIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commercePriceListIds)) {
			return false;
		}

		for (long commercePriceListId : commercePriceListIds) {
			if (!contains(permissionChecker, commercePriceListId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CommercePriceList commercePriceList, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(
				commercePriceList.getCompanyId()) ||
			permissionChecker.isOmniadmin() ||
			permissionChecker.hasOwnerPermission(
				commercePriceList.getCompanyId(),
				CommercePriceList.class.getName(),
				commercePriceList.getCommercePriceListId(),
				commercePriceList.getUserId(), actionId)) {

			return true;
		}

		if ((actionId.equals(ActionKeys.UPDATE) ||
			 actionId.equals(ActionKeys.VIEW)) &&
			_hasRoleAccountSupplier(permissionChecker, commercePriceList)) {

			return true;
		}

		return permissionChecker.hasPermission(
			commercePriceList.getGroupId(), CommercePriceList.class.getName(),
			commercePriceList.getCommercePriceListId(), actionId);
	}

	private boolean _hasRoleAccountSupplier(
			PermissionChecker permissionChecker,
			CommercePriceList commercePriceList)
		throws PortalException {

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				commercePriceList.getGroupId());

		if ((commerceCatalog != null) &&
			(commerceCatalog.getAccountEntryId() == 0)) {

			return false;
		}

		List<AccountEntry> accountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				permissionChecker.getUserId(), 0L, StringPool.BLANK,
				new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_SUPPLIER},
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (AccountEntry accountEntry : accountEntries) {
			if ((accountEntry.getAccountEntryId() ==
					commerceCatalog.getAccountEntryId()) &&
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
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}