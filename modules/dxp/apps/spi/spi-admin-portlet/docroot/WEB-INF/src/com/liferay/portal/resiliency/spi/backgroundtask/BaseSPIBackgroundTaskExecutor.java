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
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionLocalServiceUtil;
import com.liferay.portal.resiliency.spi.util.SPIAdminConstants;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public abstract class BaseSPIBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	@Override
	public String handleException(BackgroundTask backgroundTask, Exception e) {
		String errorMessage = super.handleException(backgroundTask, e);

		if (_log.isWarnEnabled()) {
			_log.warn(errorMessage, e);
		}

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long spiDefinitionId = MapUtil.getLong(
			taskContextMap, "spiDefinitionId");

		try {
			SPIDefinitionLocalServiceUtil.updateSPIDefinition(
				spiDefinitionId, SPIAdminConstants.STATUS_STOPPED,
				errorMessage);
		}
		catch (Exception ex) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to update status", e);
			}
		}

		return errorMessage;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSPIBackgroundTaskExecutor.class);

}