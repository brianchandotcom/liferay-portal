/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.internal.upgrade.v20_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Marcos Martins
 */
public class UpgradeFaroProjectUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"update OSBFaro_FaroProject set subscriptionModifiedTime = " +
				"createTime");
	}

}