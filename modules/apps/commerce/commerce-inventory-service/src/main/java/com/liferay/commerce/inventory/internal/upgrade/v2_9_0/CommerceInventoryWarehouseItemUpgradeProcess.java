/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.upgrade.v2_9_0;

import com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseItemModelImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Andrea Sbarra
 */
public class CommerceInventoryWarehouseItemUpgradeProcess
	extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		_alterColumnType(
			CommerceInventoryWarehouseItemModelImpl.TABLE_NAME, "quantity",
			"DECIMAL(30,16)");

		_alterColumnType(
			CommerceInventoryWarehouseItemModelImpl.TABLE_NAME,
			"reservedQuantity", "DECIMAL(30,16)");
	}

	private void _alterColumnType(
			String tableName, String columnName, String newColumnType)
		throws Exception {

		if (!hasColumnType(tableName, columnName, columnName)) {
			StringBundler sb = new StringBundler(6);

			sb.append("alter_column_type ");
			sb.append(tableName);
			sb.append(StringPool.SPACE);
			sb.append(columnName);
			sb.append(StringPool.SPACE);
			sb.append(newColumnType);

			runSQL(connection, sb.toString());
		}
	}

}