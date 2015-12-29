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

package com.liferay.sync.engine.upgrade.v3_0_10;

import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.service.persistence.SyncFilePersistence;
import com.liferay.sync.engine.upgrade.UpgradeProcess;
import com.liferay.sync.engine.upgrade.util.UpgradeUtil;
import com.liferay.sync.engine.util.PropsValues;
import com.liferay.sync.engine.util.StreamUtil;

import java.io.FileOutputStream;
import java.io.InputStream;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Dennis Ju
 * @author Shinn Lok
 */
public class UpgradeProcess_3_0_10 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return 3010;
	}

	@Override
	public void upgrade() throws Exception {
		upgradeLogger();
		upgradeSchema();

		upgradeSyncSites();
	}

	protected void upgradeLogger() throws Exception {
		Path logsFolderPath = Paths.get(
			PropsValues.SYNC_CONFIGURATION_DIRECTORY, "logs");

		Path archiveFilePath = logsFolderPath.resolve("archive");

		Files.createDirectories(archiveFilePath);

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_MONTH, -7);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Path archiveZipFilePath = archiveFilePath.resolve(
			"sync-" + dateFormat.format(calendar.getTime()) + ".log.zip");

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

				InputStream inputStream = Files.newInputStream(filePath);

				byte[] bytes = new byte[4096];
				int length = 0;

				while ((length = inputStream.read(bytes)) > 0) {
					zipOutputStream.write(bytes, 0, length);
				}

				zipOutputStream.closeEntry();

				StreamUtil.cleanUp(inputStream);

				Files.delete(filePath);
			}

			zipOutputStream.close();
		}
		catch (Exception e) {
		}
		finally {
			StreamUtil.cleanUp(zipOutputStream);
		}

		UpgradeUtil.copyLoggerConfiguration();
	}

	protected void upgradeSchema() throws Exception {
		SyncFilePersistence syncFilePersistence =
			SyncFileService.getSyncFilePersistence();

		syncFilePersistence.executeRaw(
			"ALTER TABLE `SyncAccount` ADD COLUMN oAuthToken " +
				"VARCHAR(16777216) BEFORE password;");
		syncFilePersistence.executeRaw(
			"ALTER TABLE `SyncAccount` ADD COLUMN oAuthTokenSecret " +
				"VARCHAR(16777216) BEFORE password;");
		syncFilePersistence.executeRaw(
			"ALTER TABLE `SyncAccount` ADD COLUMN pluginVersion VARCHAR " +
				"BEFORE pollInterval;");

		syncFilePersistence.executeRaw(
			"ALTER TABLE `SyncFile` ADD COLUMN localExtraSettings " +
				"VARCHAR(16777216) BEFORE localSyncTime;");
	}

	protected void upgradeSyncSites() throws Exception {
		List<SyncAccount> syncAccounts = SyncAccountService.findAll();

		for (SyncAccount syncAccount : syncAccounts) {
			List<SyncSite> syncSites = SyncSiteService.findSyncSites(
				syncAccount.getSyncAccountId());

			for (SyncSite syncSite : syncSites) {
				if (syncSite.isActive() &&
					!Files.exists(Paths.get(syncSite.getFilePathName()))) {

					Files.createDirectories(
						Paths.get(syncSite.getFilePathName()));
				}

				SyncFile syncFile = SyncFileService.fetchSyncFile(
					syncSite.getFilePathName());

				if (syncFile != null) {
					continue;
				}

				SyncFileService.addSyncFile(
					null, null, false, null, syncSite.getFilePathName(), null,
					syncSite.getName(), 0, syncSite.getGroupId(), 0,
					SyncFile.STATE_SYNCED, syncSite.getSyncAccountId(),
					SyncFile.TYPE_SYSTEM);
			}
		}
	}

}