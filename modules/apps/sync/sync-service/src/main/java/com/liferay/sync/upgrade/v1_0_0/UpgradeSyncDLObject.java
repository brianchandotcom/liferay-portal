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

package com.liferay.sync.upgrade.v1_0_0;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.sync.constants.SyncDLObjectConstants;
import com.liferay.sync.service.SyncDLObjectLocalService;
import com.liferay.sync.util.VerifyUtil;

/**
 * @author Shinn Lok
 */
public class UpgradeSyncDLObject extends UpgradeProcess {

	public UpgradeSyncDLObject(
		SyncDLObjectLocalService syncDLObjectLocalService) {

		_syncDLObjectLocalService = syncDLObjectLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		int syncDLObjectsCount =
			_syncDLObjectLocalService.getSyncDLObjectsCount();

		if (syncDLObjectsCount != 0) {
			return;
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_syncDLObjectLocalService.deleteSyncDLObjects(
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION,
				SyncDLObjectConstants.TYPE_FILE);

			VerifyUtil.verify();
		}
	}

	private final SyncDLObjectLocalService _syncDLObjectLocalService;

}