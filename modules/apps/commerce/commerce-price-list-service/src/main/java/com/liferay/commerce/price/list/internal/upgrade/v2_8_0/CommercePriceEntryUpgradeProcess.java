/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.upgrade.v2_8_0;

import com.liferay.commerce.price.list.model.impl.CommercePriceEntryModelImpl;
import com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryModelImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.util.List;

/**
 * @author Crescenzo Rega
 */
public class CommercePriceEntryUpgradeProcess extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		List<IndexMetadata> indexMetadatas = dropIndexes(
			CommerceTierPriceEntryModelImpl.TABLE_NAME, "minQuantity");

		_alterColumnType(
			CommerceTierPriceEntryModelImpl.TABLE_NAME, "minQuantity",
			"BIGDECIMAL");

		addIndexes(connection, indexMetadatas);
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				CommercePriceEntryModelImpl.TABLE_NAME, "quantity BIGDECIMAL",
				"unitOfMeasureKey VARCHAR(75) null")
		};
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