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

import com.liferay.sync.engine.upgrade.BaseUpgradeProcess;

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
	public void upgradeSchema() throws Exception {
		runSQL(
			"ALTER TABLE `SyncAccount` ADD COLUMN " +
				"authenticationRetryInterval INTEGER BEFORE batchFileMaxSize;");
		runSQL(
			"ALTER TABLE `SyncAccount` ALTER COLUMN batchFileMaxSize INTEGER;");
		runSQL("ALTER TABLE `SyncAccount` ALTER COLUMN oAuthEnabled BOOLEAN;");
		runSQL(
			"ALTER TABLE `SyncAccount` ALTER COLUMN pluginVersion " +
				"VARCHAR(255);");
		runSQL("ALTER TABLE `SyncAccount` ADD COLUMN uuid VARCHAR(255);");

		runSQL("ALTER TABLE `SyncFile` ALTER COLUMN userName VARCHAR(255);");

		runSQL("CREATE INDEX syncaccount_state_idx ON SyncAccount(state);");
		runSQL("CREATE INDEX syncfile_state_idx ON SyncFile(state);");
		runSQL("CREATE INDEX syncsite_state_idx ON SyncSite(state);");
	}

}