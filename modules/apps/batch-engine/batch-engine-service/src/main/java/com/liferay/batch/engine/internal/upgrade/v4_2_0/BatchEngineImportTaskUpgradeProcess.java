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

package com.liferay.batch.engine.internal.upgrade.v4_2_0;

import com.liferay.batch.engine.internal.upgrade.v4_2_0.util.BatchEngineImportTaskTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Matija Petanjek
 */
public class BatchEngineImportTaskUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(
				BatchEngineImportTaskTable.TABLE_NAME, "processedItemsCount")) {

			alter(
				BatchEngineImportTaskTable.class,
				new AlterTableAddColumn("processedItemsCount", "INTEGER"));
		}

		if (!hasColumn(
				BatchEngineImportTaskTable.TABLE_NAME, "totalItemsCount")) {

			alter(
				BatchEngineImportTaskTable.class,
				new AlterTableAddColumn("totalItemsCount", "INTEGER"));
		}
	}

}