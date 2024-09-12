/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.upgrade.v6_6_0;

import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;

/**
 * @author Marco Galluzzi
 */
public class MBCategoryExternalReferenceCodeUpgradeProcess
	extends BaseExternalReferenceCodeUpgradeProcess {

	@Override
	protected String[][] getTableAndPrimaryKeyColumnNames() {
		return new String[][] {{"MBCategory", "categoryId"}};
	}

}