/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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