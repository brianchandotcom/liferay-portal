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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.service.LayoutServiceUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class PortletImportBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public PortletImportBackgroundTaskExecutor() {
		setSerial(true);
	}

	@Override
	public void execute(BackgroundTask backgroundTask) throws Exception {
		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long plid = MapUtil.getLong(taskContextMap, "plid");
		long groupId = MapUtil.getLong(taskContextMap, "groupId");
		String portletId = MapUtil.getString(taskContextMap, "portletId");
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)taskContextMap.get("parameterMap");

		List<FileEntry> attachmentsFileEntries =
			backgroundTask.getAttachmentsFileEntries();

		for (FileEntry attachmentsFileEntry : attachmentsFileEntries) {
			LayoutServiceUtil.importPortletInfo(
				plid, groupId, portletId, parameterMap,
				attachmentsFileEntry.getContentStream());
		}
	}

}