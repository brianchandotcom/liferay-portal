/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;

/**
 * @author Preston Crary
 */
public class UpgradeExpandoMVCCVersion extends MVCCVersionUpgradeProcess {

	@Override
	protected String[] getTableNames() {
		return new String[] {
			"ExpandoColumn", "ExpandoRow", "ExpandoTable", "ExpandoValue"
		};
	}

}