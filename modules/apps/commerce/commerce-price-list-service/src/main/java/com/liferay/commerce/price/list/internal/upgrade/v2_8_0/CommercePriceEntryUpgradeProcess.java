/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
			"DECIMAL(30,16)");

		addIndexes(connection, indexMetadatas);
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				CommercePriceEntryModelImpl.TABLE_NAME,
				"quantity DECIMAL(30,16)", "unitOfMeasureKey VARCHAR(75) null")
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