/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Julio Camarero
 */
public class LayoutStagingBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public LayoutStagingBackgroundTaskExecutor() {
		setSerial(true);
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long sourceGroupId = MapUtil.getLong(taskContextMap, "sourceGroupId");
		long targetGroupId = MapUtil.getLong(taskContextMap, "targetGroupId");
		boolean privateLayout = MapUtil.getBoolean(
			taskContextMap, "privateLayout");
		long[] layoutIds = GetterUtil.getLongValues(
			taskContextMap.get("layoutIds"));
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)taskContextMap.get("parameterMap");
		Date startDate = (Date)taskContextMap.get("startDate");
		Date endDate = (Date)taskContextMap.get("endDate");

		long userId = MapUtil.getLong(taskContextMap, "userId");

		StagingUtil.lockGroup(userId, targetGroupId);

		File larFile = LayoutLocalServiceUtil.exportLayoutsAsFile(
			sourceGroupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);

		try {
			LayoutLocalServiceUtil.importLayouts(
				userId, targetGroupId, privateLayout, parameterMap, larFile);
		}
		finally {
			larFile.delete();

			StagingUtil.unlockGroup(targetGroupId);
		}

		return BackgroundTaskResult.SUCCESS;
	}

}