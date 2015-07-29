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

package com.liferay.sync.engine.upgrade.v3_0_8;

import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.persistence.SyncFilePersistence;
import com.liferay.sync.engine.upgrade.UpgradeProcess;
import com.liferay.sync.engine.util.PropsValues;

import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Dennis Ju
 * @author Shinn Lok
 */
public class UpgradeProcess_3_0_8 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return 3008;
	}

	@Override
	public void upgrade() throws Exception {
		upgradeLoggerConfiguration();
		upgradeTable();
	}

	protected void upgradeLoggerConfiguration() throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			PropsValues.SYNC_LOGGER_CONFIGURATION_FILE);

		Path configurationFilePath = Paths.get(
			PropsValues.SYNC_CONFIGURATION_DIRECTORY);

		Path loggerConfigurationFilePath = configurationFilePath.resolve(
			PropsValues.SYNC_LOGGER_CONFIGURATION_FILE);

		Files.copy(
			inputStream, loggerConfigurationFilePath,
			StandardCopyOption.REPLACE_EXISTING);
	}

	protected void upgradeTable() throws Exception {
		SyncFilePersistence syncFilePersistence =
			SyncFileService.getSyncFilePersistence();

		syncFilePersistence.executeRaw(
			"CREATE INDEX syncfile_checksum_idx ON SyncFile(checksum)");
	}

}