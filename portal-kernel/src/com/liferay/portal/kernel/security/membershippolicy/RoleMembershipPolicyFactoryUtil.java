/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.membershippolicy;

/**
 * @author Roberto Díaz
 */
public class RoleMembershipPolicyFactoryUtil {

	public static RoleMembershipPolicy getRoleMembershipPolicy() {
		return _roleMembershipPolicyFactory.getRoleMembershipPolicy();
	}

	public static RoleMembershipPolicyFactory getRoleMembershipPolicyFactory() {
		return _roleMembershipPolicyFactory;
	}

	public void setRoleMembershipPolicyFactory(
		RoleMembershipPolicyFactory roleMembershipPolicyFactory) {

		_roleMembershipPolicyFactory = roleMembershipPolicyFactory;
	}

	private static RoleMembershipPolicyFactory _roleMembershipPolicyFactory;

}