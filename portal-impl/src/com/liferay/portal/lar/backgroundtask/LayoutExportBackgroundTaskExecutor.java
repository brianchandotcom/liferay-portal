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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.io.File;
import java.io.Serializable;

import java.util.Map;

/**
 * @author Daniel Kocsis
 * @author Michael C. Han
 */
public class LayoutExportBackgroundTaskExecutor
	extends BaseExportImportBackgroundTaskExecutor {

	public LayoutExportBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new LayoutExportImportBackgroundTaskStatusMessageTranslator());
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long userId = MapUtil.getLong(settingsMap, "userId");
		long groupId = MapUtil.getLong(settingsMap, "sourceGroupId");
		long[] layoutIds = GetterUtil.getLongValues(
			settingsMap.get("layoutIds"));
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");
		DateRange dateRange = ExportImportDateUtil.getDateRange(
			exportImportConfiguration);

		StringBundler sb = new StringBundler(4);

		sb.append(
			StringUtil.replace(
				exportImportConfiguration.getName(), StringPool.SPACE,
				StringPool.UNDERLINE));
		sb.append(StringPool.DASH);
		sb.append(Time.getShortTimestamp());
		sb.append(".lar");

		File larFile = LayoutLocalServiceUtil.exportLayoutsAsFile(
			groupId, layoutIds, parameterMap, dateRange.getStartDate(),
			dateRange.getEndDate());

		BackgroundTaskLocalServiceUtil.addBackgroundTaskAttachment(
			userId, backgroundTask.getBackgroundTaskId(), sb.toString(),
			larFile);

		boolean updateLastPublishDate = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

		if (updateLastPublishDate) {
			ExportImportDateUtil.updateLastPublishDate(
				groupId, dateRange, dateRange.getEndDate());
		}

		return BackgroundTaskResult.SUCCESS;
	}

}