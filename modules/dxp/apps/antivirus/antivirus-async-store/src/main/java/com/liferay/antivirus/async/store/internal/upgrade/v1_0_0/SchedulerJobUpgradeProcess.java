/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.internal.upgrade.v1_0_0;

import com.liferay.antivirus.async.store.constants.AntivirusAsyncConstants;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Tina Tian
 */
public class SchedulerJobUpgradeProcess extends UpgradeProcess {

	public SchedulerJobUpgradeProcess(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_schedulerEngineHelper.delete(
			AntivirusAsyncConstants.SCHEDULER_GROUP_NAME_ANTIVIRUS_BATCH,
			StorageType.PERSISTED);
		_schedulerEngineHelper.delete(
			AntivirusAsyncConstants.SCHEDULER_GROUP_NAME_ANTIVIRUS,
			StorageType.PERSISTED);
	}

	private final SchedulerEngineHelper _schedulerEngineHelper;

}