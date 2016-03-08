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

package com.liferay.sync.engine.upgrade.v3_1_0;

import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.upgrade.BaseUpgradeProcess;
import com.liferay.sync.engine.upgrade.util.UpgradeUtil;
import com.liferay.sync.engine.util.PropsValues;
import com.liferay.sync.engine.util.StreamUtil;

import java.io.FileOutputStream;
import java.io.InputStream;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Dennis Ju
 * @author Shinn Lok
 */
public class UpgradeProcess_3_1_0 extends BaseUpgradeProcess {

	@Override
	public int getThreshold() {
		return 3100;
	}

	@Override
	public void upgrade() throws Exception {
		upgradeLogger();
		upgradeSyncFiles();
	}

	@Override
	public void upgradeSchema() throws Exception {
		runSQL(
			"ALTER TABLE SyncAccount ADD COLUMN authenticationRetryInterval " +
				"INTEGER BEFORE batchFileMaxSize;");
		runSQL(
			"ALTER TABLE SyncAccount ALTER COLUMN batchFileMaxSize INTEGER;");
		runSQL("ALTER TABLE SyncAccount ALTER COLUMN oAuthEnabled BOOLEAN;");
		runSQL(
			"ALTER TABLE SyncAccount ALTER COLUMN pluginVersion VARCHAR(255);");
		runSQL("ALTER TABLE SyncAccount ADD COLUMN uuid VARCHAR(255);");

		runSQL("ALTER TABLE SyncFile ALTER COLUMN userName VARCHAR(255);");

		runSQL("CREATE INDEX syncaccount_state_idx ON SyncAccount(state);");
		runSQL("CREATE INDEX syncfile_state_idx ON SyncFile(state);");
		runSQL("CREATE INDEX syncsite_state_idx ON SyncSite(state);");
	}

	protected void upgradeLogger() throws Exception {
		Path logsFolderPath = Paths.get(
			PropsValues.SYNC_CONFIGURATION_DIRECTORY, "logs");

		Path archiveFilePath = logsFolderPath.resolve("archive");

		Files.createDirectories(archiveFilePath);

		Path archiveZipFilePath = archiveFilePath.resolve(
			"sync-" + System.currentTimeMillis() + ".log.zip");

		FileOutputStream fileOutputStream = new FileOutputStream(
			archiveZipFilePath.toFile());

		ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

		try (DirectoryStream<Path> filePaths =
				Files.newDirectoryStream(logsFolderPath)) {

			for (Path filePath : filePaths) {
				if (filePath.equals(archiveFilePath)) {
					continue;
				}

				ZipEntry zipEntry = new ZipEntry(
					String.valueOf(filePath.getFileName()));

				zipOutputStream.putNextEntry(zipEntry);

				InputStream inputStream = null;

				try {
					inputStream = Files.newInputStream(filePath);

					byte[] bytes = new byte[4096];
					int length = 0;

					while ((length = inputStream.read(bytes)) > 0) {
						zipOutputStream.write(bytes, 0, length);
					}

					zipOutputStream.closeEntry();

					Files.delete(filePath);
				}
				catch (Exception e) {
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}
			}

			zipOutputStream.close();
		}
		finally {
			StreamUtil.cleanUp(zipOutputStream);
		}

		UpgradeUtil.copyLoggerConfiguration();
	}

	protected void upgradeSyncFiles() throws Exception {
		runSQL(
			"UPDATE SyncFile SET uiEvent = " + SyncFile.UI_EVENT_DOWNLOADING +
				" WHERE type = '" + SyncFile.TYPE_FILE + "' AND uiEvent = " +
					SyncFile.UI_EVENT_ADDED_REMOTE + ";");
	}

}