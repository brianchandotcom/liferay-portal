/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Harrison Schueler
 */
public class UpgradeLayoutRevision extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alterColumnType("LayoutRevision", "css", "TEXT null");
	}

}