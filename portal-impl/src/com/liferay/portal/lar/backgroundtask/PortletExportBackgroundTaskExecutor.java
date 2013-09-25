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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Daniel Kocsis
 * @author Michael C. Han
 */
public class PortletExportBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public PortletExportBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new ExportBackgroundTaskStatusMessageTranslator());
		setSerial(true);
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long userId = MapUtil.getLong(taskContextMap, "userId");
		String fileName = MapUtil.getString(taskContextMap, "fileName");

		long plid = MapUtil.getLong(taskContextMap, "plid");
		long groupId = MapUtil.getLong(taskContextMap, "groupId");
		String portletId = MapUtil.getString(taskContextMap, "portletId");
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)taskContextMap.get("parameterMap");
		Date startDate = (Date)taskContextMap.get("startDate");
		Date endDate = (Date)taskContextMap.get("endDate");

		File larFile = LayoutLocalServiceUtil.exportPortletInfoAsFile(
			plid, groupId, portletId, parameterMap, startDate, endDate);

		BackgroundTaskLocalServiceUtil.addBackgroundTaskAttachment(
			userId, backgroundTask.getBackgroundTaskId(), fileName, larFile);

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public String handleException(BackgroundTask backgroundTask, Exception e) {
		JSONObject jsonObject = StagingUtil.getExceptionMessagesJSONObject(
			getLocale(backgroundTask), e, backgroundTask.getTaskContextMap());

		return jsonObject.toString();
	}

}