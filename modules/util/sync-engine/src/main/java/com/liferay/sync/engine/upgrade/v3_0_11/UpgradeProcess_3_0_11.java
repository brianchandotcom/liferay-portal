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

package com.liferay.sync.engine.upgrade.v3_0_11;

import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.upgrade.UpgradeProcess;
import com.liferay.sync.engine.util.FileKeyUtil;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

/**
 * @author Dennis Ju
 * @author Shinn Lok
 */
public class UpgradeProcess_3_0_11 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return 3011;
	}

	@Override
	public void upgrade() throws Exception {
		List<SyncAccount> syncAccounts = SyncAccountService.findAll();

		for (SyncAccount syncAccount : syncAccounts) {
			List<SyncSite> syncSites = SyncSiteService.findSyncSites(
				syncAccount.getSyncAccountId());

			for (SyncSite syncSite : syncSites) {
				if (!syncSite.isActive() ||
					!Files.exists(Paths.get(syncSite.getFilePathName()))) {

					continue;
				}

				SyncFile syncFile = SyncFileService.fetchSyncFile(
					syncSite.getFilePathName());

				if (syncFile == null) {
					syncFile = SyncFileService.fetchSyncFile(
						syncSite.getGroupId(), syncSite.getSyncAccountId(), 0);

					syncFile.setName(syncSite.getName());
					syncFile.setFilePathName(syncSite.getFilePathName());

					SyncFileService.update(syncFile);
				}

				FileKeyUtil.writeFileKey(
					Paths.get(syncSite.getFilePathName()),
					String.valueOf(syncFile.getSyncFileId()), true);
			}
		}
	}

}