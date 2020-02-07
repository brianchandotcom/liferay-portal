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

package com.liferay.asset.category.property.internal.upgrade.v2_2_0;

import com.liferay.asset.category.property.internal.upgrade.v2_2_0.util.AssetCategoryPropertyTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Jürgen Kappler
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			AssetCategoryPropertyTable.class,
			new AlterColumnType("key_", "VARCHAR(255) null"));

		alter(
			AssetCategoryPropertyTable.class,
			new AlterColumnType("value", "VARCHAR(255) null"));
	}

}