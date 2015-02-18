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

package com.liferay.sync.engine.upgrade.v2_0_6;

import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.persistence.SyncFilePersistence;
import com.liferay.sync.engine.upgrade.UpgradeProcess;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.OSDetector;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

/**
 * @author Dennis Ju
 * @author Shinn Lok
 */
public class UpgradeProcess_2_0_6 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return 2006;
	}

	@Override
	public void upgrade() throws Exception {
		if (OSDetector.isWindows()) {
			return;
		}

		SyncFilePersistence syncFilePersistence =
			SyncFileService.getSyncFilePersistence();

		List<SyncFile> syncFiles = syncFilePersistence.queryForAll();

		for (SyncFile syncFile : syncFiles) {
			Path filePath = Paths.get(syncFile.getFilePathName());

			if (Files.exists(filePath)) {
				FileUtil.writeFileKey(
					filePath, String.valueOf(syncFile.getSyncFileId()));
			}
		}

		syncFilePersistence.updateRaw(
			"ALTER TABLE `SyncWatchEvent` ADD COLUMN previousFilePathName" +
				" VARCHAR(16777216);");
	}

}