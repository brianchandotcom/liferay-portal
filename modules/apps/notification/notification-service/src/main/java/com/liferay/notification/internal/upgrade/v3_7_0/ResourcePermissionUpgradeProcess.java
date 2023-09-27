/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.upgrade.v3_7_0;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;

/**
 * @author Murilo Stodolni
 */
public class ResourcePermissionUpgradeProcess
	extends BasePortletIdUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateResourcePermission(
			"com.liferay.notification",
			NotificationConstants.RESOURCE_NAME_NOTIFICATION_TEMPLATE, true);

		updateResourceAction(
			"com.liferay.notification",
			NotificationConstants.RESOURCE_NAME_NOTIFICATION_TEMPLATE);
	}

}