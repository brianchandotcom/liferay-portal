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
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionLocalServiceUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class StartSPIBackgroundTaskExecutor
	extends BaseSPIBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		StartSPIBackgroundTaskExecutor startSPIBackgroundTaskExecutor =
			new StartSPIBackgroundTaskExecutor();

		startSPIBackgroundTaskExecutor.setBackgroundTaskStatusMessageTranslator(
			getBackgroundTaskStatusMessageTranslator());
		startSPIBackgroundTaskExecutor.setIsolationLevel(getIsolationLevel());

		return startSPIBackgroundTaskExecutor;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long spiDefinitionId = MapUtil.getLong(
			taskContextMap, "spiDefinitionId");

		SPIDefinition spiDefinition =
			SPIDefinitionLocalServiceUtil.getSPIDefinition(spiDefinitionId);

		Set<String> servletContextNames = ServletContextPool.keySet();

		if (!servletContextNames.containsAll(
				SetUtil.fromArray(
					StringUtil.split(
						spiDefinition.getServletContextNames())))) {

			return new BackgroundTaskResult(
				BackgroundTaskConstants.STATUS_QUEUED);
		}

		SPIDefinitionLocalServiceUtil.startSPI(spiDefinitionId);

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

}