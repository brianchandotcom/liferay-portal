/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.internal.upgrade.v18_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Leilany Ulisses
 * @author Marcos Martins
 */
public class LayoutSetUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"update LayoutSet set themeId = 'osbfarotheme_WAR_osbfarotheme' " +
				"where themeId = 'osbfaro_WAR_osbfarotheme'");
	}

}