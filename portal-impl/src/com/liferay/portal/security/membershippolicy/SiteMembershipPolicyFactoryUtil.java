/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteMembershipPolicyFactoryUtil {

	public static SiteMembershipPolicy getSiteMembershipPolicy() {
		return _siteMembershipPolicyFactory.getSiteMembershipPolicy();
	}

	public static SiteMembershipPolicyFactory getSiteMembershipPolicyFactory() {
		return _siteMembershipPolicyFactory;
	}

	public void setSiteMembershipPolicyFactory(
		SiteMembershipPolicyFactory siteMembershipPolicyFactory) {

		_siteMembershipPolicyFactory = siteMembershipPolicyFactory;
	}

	private static SiteMembershipPolicyFactory _siteMembershipPolicyFactory;

}