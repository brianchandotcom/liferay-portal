/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.settings.web.internal.upgrade.v1_0_1;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.settings.constants.PortalSettingsPortletKeys;

/**
 * @author Drew Brokke
 */
public class UpgradeInstanceSettingsPortletId
	extends BasePortletIdUpgradeProcess {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			{
				PortalSettingsPortletKeys.PORTAL_SETTINGS,
				ConfigurationAdminPortletKeys.INSTANCE_SETTINGS
			}
		};
	}

}