/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v4_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Marco Leo
 */
public class CommerceCountryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("CommerceCountry", "channelFilterEnabled")) {
			alterTableAddColumn(
				"CommerceCountry", "channelFilterEnabled", "BOOLEAN");

			runSQL(
				"update CommerceCountry set channelFilterEnabled = [$FALSE$]");
		}
	}

}