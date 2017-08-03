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