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