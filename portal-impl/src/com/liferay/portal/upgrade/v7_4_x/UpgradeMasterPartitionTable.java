/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Sofía Mendoza Gutiérrez
 */
public class UpgradeMasterPartitionTable extends UpgradeProcess {

	public UpgradeMasterPartitionTable(String tableName) {
		_tableName = tableName;
	}

	@Override
	protected void doUpgrade() throws Exception {
		DBPartitionUtil.replaceViewByTable(connection, _tableName);
	}

	private final String _tableName;

}