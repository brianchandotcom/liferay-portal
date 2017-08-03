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

package com.liferay.portal.resiliency.spi.backgroundtask.messaging;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.resiliency.spi.backgroundtask.StartSPIBackgroundTaskExecutor;
import com.liferay.portal.resiliency.spi.backgroundtask.StopSPIBackgroundTaskExecutor;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionLocalServiceUtil;
import com.liferay.portal.resiliency.spi.util.SPIAdminConstants;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public class SPIBackgroundTaskStatusMessageListener
	extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		String taskExecutorClassName = message.getString(
			"taskExecutorClassName");

		if (!taskExecutorClassName.equals(
				StartSPIBackgroundTaskExecutor.class.getName()) &&
			!taskExecutorClassName.equals(
				StopSPIBackgroundTaskExecutor.class.getName())) {

			return;
		}

		int status = message.getInteger("status");

		if ((status != BackgroundTaskConstants.STATUS_SUCCESSFUL) &&
			(status != BackgroundTaskConstants.STATUS_FAILED)) {

			return;
		}

		long backgroundTaskId = message.getLong("backgroundTaskId");

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.fetchBackgroundTask(backgroundTaskId);

		if (backgroundTask == null) {
			return;
		}

		BackgroundTaskManagerUtil.deleteBackgroundTask(
			backgroundTask.getBackgroundTaskId());

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long spiDefinitionId = GetterUtil.getLong(
			taskContextMap.get("spiDefinitionId"));

		if (spiDefinitionId == 0) {
			return;
		}

		SPIDefinition spiDefinition =
			SPIDefinitionLocalServiceUtil.getSPIDefinition(spiDefinitionId);

		UnicodeProperties typeSettingsProperties =
			spiDefinition.getTypeSettingsProperties();

		typeSettingsProperties.remove("backgroundTaskId");

		spiDefinition.setTypeSettingsProperties(typeSettingsProperties);

		if (status == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			spiDefinition.setStatusMessage(null);

			if (spiDefinition.getStatus() ==
					SPIAdminConstants.STATUS_STARTING) {

				spiDefinition.setStatus(SPIAdminConstants.STATUS_STARTED);
			}
			else if (spiDefinition.getStatus() ==
						SPIAdminConstants.STATUS_STOPPING) {

				spiDefinition.setStatus(SPIAdminConstants.STATUS_STOPPED);
			}
		}
		else {
			spiDefinition.setStatusMessage(backgroundTask.getStatusMessage());

			if (spiDefinition.getStatus() ==
					SPIAdminConstants.STATUS_STARTING) {

				spiDefinition.setStatus(SPIAdminConstants.STATUS_STOPPED);
			}
			else if (spiDefinition.getStatus() ==
						SPIAdminConstants.STATUS_STOPPING) {

				if (spiDefinition.isAlive()) {
					spiDefinition.setStatus(SPIAdminConstants.STATUS_STARTED);
				}
				else {
					spiDefinition.setStatus(SPIAdminConstants.STATUS_STOPPED);
				}
			}
		}

		SPIDefinitionLocalServiceUtil.updateSPIDefinition(spiDefinition);
	}

}