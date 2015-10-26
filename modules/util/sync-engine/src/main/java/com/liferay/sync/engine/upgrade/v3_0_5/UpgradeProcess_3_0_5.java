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

package com.liferay.sync.engine.upgrade.v3_0_5;

import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.persistence.SyncFilePersistence;
import com.liferay.sync.engine.upgrade.UpgradeProcess;

/**
 * @author Dennis Ju
 * @author Shinn Lok
 */
public class UpgradeProcess_3_0_5 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return 3005;
	}

	@Override
	public void upgrade() throws Exception {
		SyncFilePersistence syncFilePersistence =
			SyncFileService.getSyncFilePersistence();

		syncFilePersistence.executeRaw(
			"ALTER TABLE `SyncFile` ADD COLUMN userId LONG BEFORE version;");
		syncFilePersistence.executeRaw(
			"ALTER TABLE `SyncFile` ADD COLUMN userName VARCHAR(16777216) " +
				"BEFORE version;");
	}

}