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

package com.liferay.commerce.internal.model.listener;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.membershippolicy.RoleMembershipPolicyUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(service = ModelListener.class)
public class UserGroupRoleModelListener
	extends BaseModelListener<UserGroupRole> {

	@Override
	public void onAfterCreate(UserGroupRole userGroupRole)
		throws ModelListenerException {

		try {
			AccountRole accountRole =
				_accountRoleLocalService.fetchAccountRoleByRoleId(
					userGroupRole.getRoleId());

			if ((accountRole != null) &&
				AccountRoleConstants.ROLE_NAME_ACCOUNT_SUPPLIER.contentEquals(
					accountRole.getRoleName())) {

				Role supplierRole = _roleLocalService.fetchRole(
					userGroupRole.getCompanyId(),
					AccountRoleConstants.ROLE_NAME_SUPPLIER);

				if (supplierRole != null) {
					_propagateRoles(
						new long[] {userGroupRole.getUserId()},
						new long[] {supplierRole.getRoleId()}, new long[0]);
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	@Override
	public void onAfterRemove(UserGroupRole userGroupRole)
		throws ModelListenerException {

		try {
			if (_isUserSupplierInOtherAccounts(userGroupRole.getUserId())) {
				return;
			}

			AccountRole accountRole =
				_accountRoleLocalService.fetchAccountRoleByRoleId(
					userGroupRole.getRoleId());

			if ((accountRole != null) &&
				AccountRoleConstants.ROLE_NAME_ACCOUNT_SUPPLIER.equals(
					accountRole.getRoleName())) {

				Role supplierRole = _roleLocalService.fetchRole(
					userGroupRole.getCompanyId(),
					AccountRoleConstants.ROLE_NAME_SUPPLIER);

				if (supplierRole != null) {
					_propagateRoles(
						new long[] {userGroupRole.getUserId()}, new long[0],
						new long[] {supplierRole.getRoleId()});
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private boolean _isUserSupplierInOtherAccounts(long userId)
		throws PortalException {

		List<AccountRole> accountRoles = TransformUtil.transform(
			ListUtil.filter(
				_userGroupRoleLocalService.getUserGroupRoles(userId),
				userGroupRole -> {
					try {
						Role role = userGroupRole.getRole();

						if (role.getType() == RoleConstants.TYPE_ACCOUNT) {
							return true;
						}

						return false;
					}
					catch (PortalException portalException) {
						_log.error(portalException);

						return false;
					}
				}),
			userGroupRole -> _accountRoleLocalService.getAccountRoleByRoleId(
				userGroupRole.getRoleId()));

		for (AccountRole accountRole : accountRoles) {
			if (AccountRoleConstants.ROLE_NAME_ACCOUNT_SUPPLIER.equals(
					accountRole.getRoleName())) {

				return true;
			}
		}

		return false;
	}

	private void _propagateRoles(
		long[] userId, long[] addRoleId, long[] removeRoleId) {

		try {
			if (addRoleId.length > 0) {
				_userLocalService.addRoleUsers(addRoleId[0], userId);

				RoleMembershipPolicyUtil.propagateRoles(
					userId, new long[] {addRoleId[0]}, null);
			}

			if (removeRoleId.length > 0) {
				_userLocalService.deleteRoleUser(removeRoleId[0], userId[0]);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupRoleModelListener.class);

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}