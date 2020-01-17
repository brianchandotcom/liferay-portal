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

package com.liferay.exportimport.internal.upgrade.v1_0_1;

import com.liferay.exportimport.internal.upgrade.BaseUpgradeBackgroundTaskExecutorClassNames;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;

/**
 * @author Jonathan McCann
 */
public class UpgradeBackgroundTaskExecutorClassNames
	extends BaseUpgradeBackgroundTaskExecutorClassNames {

	@Override
	protected String[][] getRenameTaskExecutorClassNames() {
		return new String[][] {
			{
				"com.liferay.dynamic.data.mapping.background.task." +
					"DDMStructureIndexerBackgroundTaskExecutor",
				BackgroundTaskExecutorNames.
					DDM_STRUCTURE_INDEXER_BACKGROUND_TASK_EXECUTOR
			}
		};
	}

}