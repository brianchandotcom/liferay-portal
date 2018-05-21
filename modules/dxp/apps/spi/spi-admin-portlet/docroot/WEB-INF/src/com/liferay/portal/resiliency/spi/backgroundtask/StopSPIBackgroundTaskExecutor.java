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

package com.liferay.portal.resiliency.spi.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionLocalServiceUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public class StopSPIBackgroundTaskExecutor
	extends BaseSPIBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		StopSPIBackgroundTaskExecutor stopSPIBackgroundTaskExecutor =
			new StopSPIBackgroundTaskExecutor();

		stopSPIBackgroundTaskExecutor.setBackgroundTaskStatusMessageTranslator(
			getBackgroundTaskStatusMessageTranslator());
		stopSPIBackgroundTaskExecutor.setIsolationLevel(getIsolationLevel());

		return stopSPIBackgroundTaskExecutor;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long spiDefinitionId = MapUtil.getLong(
			taskContextMap, "spiDefinitionId");

		SPIDefinitionLocalServiceUtil.stopSPI(spiDefinitionId);

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

}