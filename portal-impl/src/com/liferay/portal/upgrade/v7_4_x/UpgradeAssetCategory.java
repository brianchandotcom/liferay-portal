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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Vendel Toreki
 * @author Luis Miguel Barcos
 */
public class UpgradeAssetCategory extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumnType("AssetCategory", "title", "TEXT null")) {
			alterColumnType("AssetCategory", "title", "TEXT null");
		}

		if (!hasColumnType("AssetCategory", "description", "TEXT null")) {
			alterColumnType("AssetCategory", "description", "TEXT null");
		}

		if (!hasColumn("AssetCategory", "externalReferenceCode")) {
			alterTableAddColumn(
				"AssetCategory", "externalReferenceCode", "VARCHAR(75)");
		}

		runSQL(
			StringBundler.concat(
				"update AssetCategory set externalReferenceCode = ",
				"CAST_TEXT(categoryId) where externalReferenceCode is null or ",
				"externalReferenceCode =''"));
	}

}