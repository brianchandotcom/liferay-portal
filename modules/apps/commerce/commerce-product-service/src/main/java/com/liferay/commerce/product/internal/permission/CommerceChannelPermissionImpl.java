/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.permission;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.permission.CommerceChannelPermission;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
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
@Component(service = CommerceChannelPermission.class)
public class CommerceChannelPermissionImpl
	implements CommerceChannelPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceChannel commerceChannel, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceChannel, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceChannel.class.getName(),
				commerceChannel.getCommerceChannelId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceChannelId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceChannelId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceChannel.class.getName(),
				commerceChannelId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceChannel commerceChannel, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, commerceChannel.getCommerceChannelId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceChannelId,
			String actionId)
		throws PortalException {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannel(
				commerceChannelId);

		if (commerceChannel == null) {
			return false;
		}

		return _contains(permissionChecker, commerceChannel, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceChannelIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceChannelIds)) {
			return false;
		}

		for (long commerceChannelId : commerceChannelIds) {
			if (!contains(permissionChecker, commerceChannelId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CommerceChannel commerceChannel, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(commerceChannel.getCompanyId()) ||
			permissionChecker.hasOwnerPermission(
				commerceChannel.getCompanyId(), CommerceChannel.class.getName(),
				commerceChannel.getCommerceChannelId(),
				commerceChannel.getUserId(), actionId)) {

			return true;
		}

		if ((actionId.equals(ActionKeys.UPDATE) ||
			 actionId.equals(ActionKeys.VIEW)) &&
			_hasRoleAccountSupplier(permissionChecker, commerceChannel)) {

			return true;
		}

		Group group = _commerceChannelLocalService.getCommerceChannelGroup(
			commerceChannel.getCommerceChannelId());

		return permissionChecker.hasPermission(
			group, CommerceChannel.class.getName(),
			commerceChannel.getCommerceChannelId(), actionId);
	}

	private boolean _hasRoleAccountSupplier(
			PermissionChecker permissionChecker,
			CommerceChannel commerceChannel)
		throws PortalException {

		if (commerceChannel.getAccountEntryId() == 0) {
			return false;
		}

		List<AccountEntry> accountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				permissionChecker.getUserId(), 0L, StringPool.BLANK,
				new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_SUPPLIER},
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (AccountEntry accountEntry : accountEntries) {
			if ((accountEntry.getAccountEntryId() ==
					commerceChannel.getAccountEntryId()) &&
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
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}