/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.membershippolicy.OrganizationMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.OrganizationMembershipPolicyFactory;

/**
 * @author Sergio González
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class OrganizationMembershipPolicyFactoryImpl
	implements OrganizationMembershipPolicyFactory {

	@Override
	public OrganizationMembershipPolicy getOrganizationMembershipPolicy() {
		return _organizationMembershipPolicySnapshot.get();
	}

	private OrganizationMembershipPolicyFactoryImpl() {
	}

	private static final Snapshot<OrganizationMembershipPolicy>
		_organizationMembershipPolicySnapshot = new Snapshot<>(
			OrganizationMembershipPolicyFactoryImpl.class,
			OrganizationMembershipPolicy.class, null, true);

}