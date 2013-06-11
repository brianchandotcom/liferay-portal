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

package com.liferay.portal.lar.backgroundtask.executor;

import com.liferay.portal.backgroundtask.executor.BaseBackgroundTaskExecutor;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Daniel Kocsis
 * @author Michael C. Han
 */
public class LayoutExportBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public LayoutExportBackgroundTaskExecutor() {
		setSerial(true);
	}

	@Override
	public void execute(BackgroundTask backgroundTask) throws Exception {
		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long groupId = (Long)taskContextMap.get("groupId");
		boolean privateLayout = (Boolean)taskContextMap.get("privateLayout");
		long[] layoutIds = (long[])taskContextMap.get("layoutIds");
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)taskContextMap.get("parameterMap");
		Date startDate = (Date)taskContextMap.get("startDate");
		Date endDate = (Date)taskContextMap.get("endDate");

		File larFile = LayoutServiceUtil.exportLayoutsAsFile(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);

		long userId = (Long)taskContextMap.get("userId");

		BackgroundTaskLocalServiceUtil.addBackgroundTaskAttachment(
			userId, backgroundTask.getBackgroundTaskId(), larFile.getName(),
			larFile);
	}

}