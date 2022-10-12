/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.punchout.helper;

import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.CommerceAccountUserRelLocalService;
import com.liferay.commerce.punchout.constants.PunchOutConstants;
import com.liferay.commerce.punchout.service.PunchOutAccountRoleHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaclyn Ong
 */
@Component(immediate = true, service = PunchOutAccountRoleHelper.class)
public class PunchOutAccountRoleHelperImpl
	implements PunchOutAccountRoleHelper {

	@Override
	public boolean hasPunchOutRole(long userId, long commerceAccountId)
		throws PortalException {

		List<CommerceAccountUserRel> commerceAccountUserRels =
			_commerceAccountUserRelLocalService.getCommerceAccountUserRels(
				commerceAccountId);

		if (commerceAccountUserRels.isEmpty()) {
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

		for (CommerceAccountUserRel commerceAccountUserRel :
				commerceAccountUserRels) {

			List<UserGroupRole> userGroupRoles =
				commerceAccountUserRel.getUserGroupRoles();

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
	private CommerceAccountUserRelLocalService
		_commerceAccountUserRelLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}