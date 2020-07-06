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

package com.liferay.adaptive.media.web.internal.background.task;

import com.liferay.adaptive.media.constants.AMOptimizeImagesBackgroundTaskConstants;
import com.liferay.adaptive.media.web.internal.background.task.display.OptimizeImagesBackgroundTaskDisplay;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Sergio González
 */
public abstract class OptimizeImagesBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public OptimizeImagesBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new OptimizeImagesBackgroundTaskStatusMessageTranslator());
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_COMPANY);
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		String configurationEntryUuid = (String)taskContextMap.get(
			AMOptimizeImagesBackgroundTaskConstants.CONFIGURATION_ENTRY_UUID);
		long companyId = GetterUtil.getLong(
			taskContextMap.get(
				AMOptimizeImagesBackgroundTaskConstants.COMPANY_ID));

		optimizeImages(configurationEntryUuid, companyId);

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return new OptimizeImagesBackgroundTaskDisplay(backgroundTask);
	}

	protected abstract void optimizeImages(
			String configurationEntryUuid, long companyId)
		throws Exception;

}