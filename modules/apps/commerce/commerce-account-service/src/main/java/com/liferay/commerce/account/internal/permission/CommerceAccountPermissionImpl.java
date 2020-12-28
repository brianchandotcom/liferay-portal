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

package com.liferay.commerce.account.internal.permission;

import com.liferay.commerce.account.constants.CommerceAccountActionKeys;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.permission.CommerceAccountPermission;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = CommerceAccountPermission.class
)
public class CommerceAccountPermissionImpl
	implements CommerceAccountPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceAccount commerceAccount, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceAccount, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceAccount.class.getName(),
				commerceAccount.getCommerceAccountId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceAccountId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceAccountId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceAccount.class.getName(),
				commerceAccountId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceAccount commerceAccount, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, commerceAccount.getCommerceAccountId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceAccountId,
			String actionId)
		throws PortalException {

		if (_portletResourcePermission.contains(
				permissionChecker, null,
				CommerceAccountActionKeys.MANAGE_ALL_ACCOUNTS)) {

			return true;
		}

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getCommerceAccount(
				permissionChecker.getUserId(), commerceAccountId);

		if (commerceAccount == null) {
			return false;
		}

		return _contains(permissionChecker, commerceAccount, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceAccountIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceAccountIds)) {
			return false;
		}

		for (long commerceAccountId : commerceAccountIds) {
			if (!contains(permissionChecker, commerceAccountId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CommerceAccount commerceAccount, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(commerceAccount.getCompanyId()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		if (actionId.equals(ActionKeys.UPDATE)) {
			return _containsUpdatePermission(
				commerceAccount, permissionChecker);
		}
		else if (actionId.equals(ActionKeys.VIEW)) {
			return _containsViewPermission(commerceAccount, permissionChecker);
		}
		else if (actionId.equals(
					CommerceAccountActionKeys.MANAGE_ORGANIZATIONS)) {

			return _containsManageOrganizationPermission(
				commerceAccount, permissionChecker);
		}
		else {
			if (_portletResourcePermission.contains(
					permissionChecker, null,
					CommerceAccountActionKeys.MANAGE_AVAILABLE_ACCOUNTS)) {

				return true;
			}

			return permissionChecker.hasPermission(
				commerceAccount.getCommerceAccountGroupId(),
				CommerceAccount.class.getName(),
				commerceAccount.getCommerceAccountId(), actionId);
		}
	}

	private boolean _containsManageOrganizationPermission(
			CommerceAccount commerceAccount,
			PermissionChecker permissionChecker)
		throws PortalException {

		if (commerceAccount.isPersonalAccount()) {
			return false;
		}

		return permissionChecker.hasPermission(
			commerceAccount.getCommerceAccountGroupId(),
			CommerceAccount.class.getName(),
			commerceAccount.getCommerceAccountId(),
			CommerceAccountActionKeys.MANAGE_ORGANIZATIONS);
	}

	private boolean _containsUpdatePermission(
			CommerceAccount commerceAccount,
			PermissionChecker permissionChecker)
		throws PortalException {

		if (commerceAccount.isPersonalAccount()) {
			return _hasOwnerPermission(commerceAccount, permissionChecker);
		}

		if (_portletResourcePermission.contains(
				permissionChecker, null,
				CommerceAccountActionKeys.MANAGE_AVAILABLE_ACCOUNTS)) {

			return true;
		}

		return permissionChecker.hasPermission(
			commerceAccount.getCommerceAccountGroupId(),
			CommerceAccount.class.getName(),
			commerceAccount.getCommerceAccountId(), ActionKeys.UPDATE);
	}

	private boolean _containsViewPermission(
			CommerceAccount commerceAccount,
			PermissionChecker permissionChecker)
		throws PortalException {

		if (commerceAccount.isPersonalAccount()) {
			return _hasOwnerPermission(commerceAccount, permissionChecker);
		}

		if (_portletResourcePermission.contains(
				permissionChecker, null,
				CommerceAccountActionKeys.MANAGE_AVAILABLE_ACCOUNTS)) {

			return true;
		}

		return permissionChecker.hasPermission(
			commerceAccount.getCommerceAccountGroupId(),
			CommerceAccount.class.getName(),
			commerceAccount.getCommerceAccountId(), ActionKeys.VIEW);
	}

	private boolean _hasOwnerPermission(
		CommerceAccount commerceAccount, PermissionChecker permissionChecker) {

		if (permissionChecker.getUserId() == commerceAccount.getUserId()) {
			return true;
		}

		return false;
	}

	@Reference
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Reference(
		target = "(resource.name=" + CommerceAccountConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}