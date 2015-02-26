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

package com.liferay.portal.lar.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Daniel Kocsis
 * @author Michael C. Han
 * @author Akos Thurzo
 */
public class PortletExportBackgroundTaskExecutor
	extends BaseExportImportBackgroundTaskExecutor {

	public PortletExportBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new PortletExportImportBackgroundTaskStatusMessageTranslator());
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long sourcePlid = MapUtil.getLong(settingsMap, "sourcePlid");
		long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");
		String portletId = MapUtil.getString(settingsMap, "portletId");
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");
		Date startDate = (Date)settingsMap.get("startDate");
		Date endDate = (Date)settingsMap.get("endDate");

		File larFile = LayoutLocalServiceUtil.exportPortletInfoAsFile(
			sourcePlid, sourceGroupId, portletId, parameterMap, startDate,
			endDate);

		long userId = MapUtil.getLong(settingsMap, "userId");
		String fileName = MapUtil.getString(settingsMap, "fileName");

		BackgroundTaskLocalServiceUtil.addBackgroundTaskAttachment(
			userId, backgroundTask.getBackgroundTaskId(), fileName, larFile);

		return BackgroundTaskResult.SUCCESS;
	}

}