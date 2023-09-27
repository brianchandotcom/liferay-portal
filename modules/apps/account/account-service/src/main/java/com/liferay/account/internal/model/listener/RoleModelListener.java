/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.model.listener;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredRoleException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.util.PortalInstances;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = ModelListener.class)
public class RoleModelListener extends BaseModelListener<Role> {

	@Override
	public void onAfterCreate(Role role) throws ModelListenerException {
		if (!Objects.equals(
				role.getClassNameId(),
				_portal.getClassNameId(AccountRole.class)) ||
			!Objects.equals(role.getType(), RoleConstants.TYPE_ACCOUNT)) {

			return;
		}

		AccountRole accountRole =
			_accountRoleLocalService.fetchAccountRoleByRoleId(role.getRoleId());

		if (accountRole != null) {
			return;
		}

		accountRole = _accountRoleLocalService.createAccountRole(
			_counterLocalService.increment());

		accountRole.setCompanyId(role.getCompanyId());
		accountRole.setAccountEntryId(
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);
		accountRole.setRoleId(role.getRoleId());

		try {
			_resourceLocalService.addResources(
				role.getCompanyId(), 0, role.getUserId(),
				AccountRole.class.getName(), accountRole.getAccountRoleId(),
				false, false, false);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}

		_accountRoleLocalService.addAccountRole(accountRole);

		role.setClassPK(accountRole.getAccountRoleId());

		_roleLocalService.updateRole(role);
	}

	@Override
	public void onAfterRemove(Role role) throws ModelListenerException {
		if (role == null) {
			return;
		}

		AccountRole accountRole =
			_accountRoleLocalService.fetchAccountRoleByRoleId(role.getRoleId());

		if (accountRole != null) {
			try {
				_accountRoleLocalService.deleteAccountRole(accountRole);
			}
			catch (PortalException portalException) {
				throw new ModelListenerException(portalException);
			}
		}
	}

	@Override
	public void onBeforeRemove(Role role) throws ModelListenerException {
		if (PortalInstances.isCurrentCompanyInDeletionProcess()) {
			return;
		}

		if (ArrayUtil.contains(
				AccountRoleConstants.REQUIRED_ROLE_NAMES, role.getName())) {

			throw new ModelListenerException(
				new RequiredRoleException(
					"Role \"" + role.getName() +
						"\" is a default account role"));
		}

		AccountRole accountRole =
			_accountRoleLocalService.fetchAccountRoleByRoleId(role.getRoleId());

		if (accountRole != null) {
			if (!Objects.equals(
					AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
					accountRole.getAccountEntryId())) {

				throw new ModelListenerException(
					new RequiredRoleException(
						StringBundler.concat(
							"Role \"", role.getName(),
							"\" is required by account role ",
							accountRole.getAccountRoleId())));
			}

			_userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
				role.getRoleId());
		}
	}

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}