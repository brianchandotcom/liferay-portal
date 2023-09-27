/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.upgrade.v1_1_0;

import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;

/**
 * @author Adam Brandizzi
 */
public class UpgradePortletId extends BasePortletIdUpgradeProcess {

	@Override
	protected String[] getUninstanceablePortletIds() {
		return new String[] {
			"com_liferay_calendar_web_portlet_CalendarPortlet"
		};
	}

}