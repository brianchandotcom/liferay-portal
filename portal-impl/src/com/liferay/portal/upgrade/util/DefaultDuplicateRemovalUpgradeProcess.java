/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.upgrade.DuplicateRemovalUpgradeProcess;

/**
 * @author Jorge Avalos
 */
public class DefaultDuplicateRemovalUpgradeProcess
	extends DuplicateRemovalUpgradeProcess {

	public DefaultDuplicateRemovalUpgradeProcess(String tableName, String columns) {
		super(tableName, columns, null, null);
	}

	public DefaultDuplicateRemovalUpgradeProcess(
		String tableName, String columns, String orderByColumns,
		String orderByClause) {

		super(tableName, columns, orderByColumns, orderByClause);
	}

	@Override
	protected void doUpgrade() {
		super.doUpgrade();
	}

}