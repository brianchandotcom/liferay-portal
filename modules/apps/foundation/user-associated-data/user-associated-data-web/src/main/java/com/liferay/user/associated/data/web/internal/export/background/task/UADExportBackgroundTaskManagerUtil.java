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

package com.liferay.user.associated.data.web.internal.export.background.task;

import com.liferay.portal.background.task.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Pei-Jung Lan
 */
public class UADExportBackgroundTaskManagerUtil {

	public static BackgroundTask fetchLastBackgroundTask(
		String name, long userId, int status) {

		DynamicQuery dynamicQuery =
			BackgroundTaskLocalServiceUtil.dynamicQuery();

		dynamicQuery = dynamicQuery.add(
			RestrictionsFactoryUtil.eq("name", name));

		dynamicQuery = dynamicQuery.add(
			RestrictionsFactoryUtil.eq("userId", userId));

		dynamicQuery = dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"taskExecutorClassName", _BACKGROUND_TASK_EXECUTOR_CLASS_NAME));

		dynamicQuery = dynamicQuery.add(
			RestrictionsFactoryUtil.eq("status", status));

		dynamicQuery = dynamicQuery.addOrder(
			OrderFactoryUtil.desc("createDate"));

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTaskModels = BackgroundTaskLocalServiceUtil.dynamicQuery(
				dynamicQuery, 0, 1);

		if (ListUtil.isEmpty(backgroundTaskModels)) {
			return null;
		}

		return _getBackgroundTask(backgroundTaskModels.get(0));
	}

	private static BackgroundTask _getBackgroundTask(
		com.liferay.
			portal.background.task.model.BackgroundTask backgroundTask) {

		return BackgroundTaskManagerUtil.fetchBackgroundTask(
			backgroundTask.getBackgroundTaskId());
	}

	private static final String _BACKGROUND_TASK_EXECUTOR_CLASS_NAME =
		UADExportBackgroundTaskExecutor.class.getName();

}