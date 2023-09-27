/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.navigation.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.CamelCaseUpgradePortletPreferences;

/**
 * @author Sergio González
 */
public class UpgradePortletPreferences
	extends CamelCaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {"%_WAR_wikinavigationportlet_INSTANCE_%"};
	}

}