/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.upgrade.v2_5_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteAppEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("RemoteAppEntry", "customElementUseESM")) {
			alterTableAddColumn(
				"RemoteAppEntry", "customElementUseESM", "BOOLEAN");

			runSQL("update RemoteAppEntry set customElementUseESM = [$FALSE$]");
		}
	}

}