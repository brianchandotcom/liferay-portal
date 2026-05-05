/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.upgrade.v3_5_2;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Anthony Chu
 * @author Drew Brokke
 */
public class CETConfigurationUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("Configuration_")) {
			return;
		}

		runSQL(
			"delete from Configuration_ where configurationId like '" +
				_CLASS_NAME_CET_CONFIGURATION + "%'");
	}

	private static final String _CLASS_NAME_CET_CONFIGURATION =
		"com.liferay.client.extension.type.configuration.CETConfiguration";

}