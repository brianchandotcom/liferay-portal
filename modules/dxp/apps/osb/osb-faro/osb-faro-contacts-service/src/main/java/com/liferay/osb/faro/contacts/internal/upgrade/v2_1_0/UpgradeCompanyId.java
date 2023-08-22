/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.internal.upgrade.v2_1_0;

import com.liferay.portal.kernel.upgrade.BaseCompanyIdUpgradeProcess;

/**
 * @author Rachael Koestartyo
 */
public class UpgradeCompanyId extends BaseCompanyIdUpgradeProcess {

	@Override
	protected TableUpdater[] getTableUpdaters() {
		return new TableUpdater[] {
			new TableUpdater(
				"OSBFaro_ContactsCardTemplate", "Group_", "groupId"),
			new TableUpdater(
				"OSBFaro_ContactsLayoutTemplate", "Group_", "groupId")
		};
	}

}