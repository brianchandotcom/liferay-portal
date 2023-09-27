/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.membershippolicy;

/**
 * @author Roberto Díaz
 */
public class OrganizationMembershipPolicyFactoryUtil {

	public static OrganizationMembershipPolicy
		getOrganizationMembershipPolicy() {

		return _organizationMembershipPolicyFactory.
			getOrganizationMembershipPolicy();
	}

	public static OrganizationMembershipPolicyFactory
		getOrganizationMembershipPolicyFactory() {

		return _organizationMembershipPolicyFactory;
	}

	public void setOrganizationMembershipPolicyFactory(
		OrganizationMembershipPolicyFactory
			organizationMembershipPolicyFactory) {

		_organizationMembershipPolicyFactory =
			organizationMembershipPolicyFactory;
	}

	private static OrganizationMembershipPolicyFactory
		_organizationMembershipPolicyFactory;

}