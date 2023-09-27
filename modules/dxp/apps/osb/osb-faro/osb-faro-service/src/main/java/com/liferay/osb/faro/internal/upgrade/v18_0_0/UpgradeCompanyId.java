/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.internal.upgrade.v18_0_0;

import com.liferay.portal.kernel.upgrade.BaseCompanyIdUpgradeProcess;

/**
 * @author Leilany Ulisses
 * @author Marcos Martins
 */
public class UpgradeCompanyId extends BaseCompanyIdUpgradeProcess {

	@Override
	protected TableUpdater[] getTableUpdaters() {
		return new TableUpdater[] {
			new TableUpdater("OSBFaro_FaroChannel", "Group_", "groupId"),
			new TableUpdater("OSBFaro_FaroNotification", "Group_", "groupId"),
			new TableUpdater("OSBFaro_FaroPreferences", "Group_", "groupId"),
			new TableUpdater("OSBFaro_FaroProject", "Group_", "groupId"),
			new TableUpdater(
				"OSBFaro_FaroProjectEmailDomain", "Group_", "groupId"),
			new TableUpdater("OSBFaro_FaroUser", "Group_", "groupId")
		};
	}

}