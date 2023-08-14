/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.util;

import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Sofía Mendoza Gutiérrez
 */
public class UpgradePartitionedControlTable extends UpgradeProcess {

	public UpgradePartitionedControlTable(String tableName) {
		_tableName = tableName;
	}

	@Override
	protected void doUpgrade() throws Exception {
		DBPartitionUtil.replaceByTable(connection, _tableName);
	}

	@Override
	protected boolean isSkipUpgradeProcess() {
		return !DBPartition.isPartitionEnabled();
	}

	private final String _tableName;

}