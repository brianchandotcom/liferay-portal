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

package com.liferay.user.associated.data.web.internal.export;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.user.associated.data.web.internal.export.background.task.UADExportBackgroundTaskExecutor;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pei-Jung Lan
 */
public class UADExporter {

	public static long exportApplicationDataInBackground(
			String applicationName, long userId, long groupId)
		throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put("userId", userId);

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.addBackgroundTask(
				userId, groupId, applicationName,
				UADExportBackgroundTaskExecutor.class.getName(), taskContextMap,
				new ServiceContext());

		return backgroundTask.getBackgroundTaskId();
	}

}