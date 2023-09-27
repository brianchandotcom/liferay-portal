/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.punchout.helper;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.commerce.punchout.constants.PunchOutConstants;
import com.liferay.commerce.punchout.service.PunchOutAccountRoleHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaclyn Ong
 */
@Component(service = PunchOutAccountRoleHelper.class)
public class PunchOutAccountRoleHelperImpl
	implements PunchOutAccountRoleHelper {

	@Override
	public boolean hasPunchOutRole(long userId, long accountEntryId)
		throws PortalException {

		List<AccountEntryUserRel> accountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountEntryId(accountEntryId);

		if (accountEntryUserRels.isEmpty()) {
			return false;
		}

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			return false;
		}

		Role punchOutRole = _roleLocalService.fetchRole(
			user.getCompanyId(), PunchOutConstants.ROLE_NAME_ACCOUNT_PUNCH_OUT);

		if (punchOutRole == null) {
			return false;
		}

		for (AccountEntryUserRel accountEntryUserRel : accountEntryUserRels) {
			AccountEntry accountEntry =
				_accountEntryLocalService.getAccountEntry(accountEntryId);

			List<UserGroupRole> userGroupRoles =
				_userGroupRoleLocalService.getUserGroupRoles(
					accountEntryUserRel.getAccountUserId(),
					accountEntry.getAccountEntryGroupId());

			for (UserGroupRole userGroupRole : userGroupRoles) {
				Role role = userGroupRole.getRole();

				if ((userGroupRole.getUserId() == userId) &&
					(role.getRoleId() == punchOutRole.getRoleId())) {

					return true;
				}
			}
		}

		return false;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}