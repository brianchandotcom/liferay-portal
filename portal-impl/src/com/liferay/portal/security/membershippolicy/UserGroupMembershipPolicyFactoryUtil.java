/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.security.membershippolicy.UserGroupMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.UserGroupMembershipPolicyFactory;

/**
 * @author Roberto Díaz
 */
public class UserGroupMembershipPolicyFactoryUtil {

	public static UserGroupMembershipPolicy getUserGroupMembershipPolicy() {
		return _userGroupMembershipPolicyFactory.getUserGroupMembershipPolicy();
	}

	public void setUserGroupMembershipPolicyFactory(
		UserGroupMembershipPolicyFactory userGroupMembershipPolicyFactory) {

		_userGroupMembershipPolicyFactory = userGroupMembershipPolicyFactory;
	}

	private static UserGroupMembershipPolicyFactory
		_userGroupMembershipPolicyFactory;

}