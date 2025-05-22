/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;

/**
 * @author Rubén Pulido
 */
public class LayoutExternalReferenceCodeUpgradeProcess
	extends BaseExternalReferenceCodeUpgradeProcess {

	@Override
	protected String[][] getTableAndPrimaryKeyColumnNames() {
		return new String[][] {{"Layout", "plid"}};
	}

	@Override
	protected boolean isUseUUID(String tableName) {
		return false;
	}

}