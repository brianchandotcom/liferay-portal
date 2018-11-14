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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Pei-Jung Lan
 */
public class UADExportBackgroundTaskManagerUtil {

	public static BackgroundTask fetchLastBackgroundTask(
		String applicationKey, long groupId, long userId, int status) {

		DynamicQuery dynamicQuery = _getDynamicQuery(groupId, userId);

		dynamicQuery = dynamicQuery.add(
			RestrictionsFactoryUtil.eq("status", status));
		dynamicQuery = dynamicQuery.addOrder(
			OrderFactoryUtil.desc("createDate"));

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTaskModels = BackgroundTaskLocalServiceUtil.dynamicQuery(
				dynamicQuery);

		Stream<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTaskModelsStream = backgroundTaskModels.stream();

		backgroundTaskModels = backgroundTaskModelsStream.filter(
			backgroundTaskModel -> {
				Map taskContextMap = backgroundTaskModel.getTaskContextMap();

				return applicationKey.equals(
					taskContextMap.get("applicationKey"));
			}
		).collect(
			Collectors.toList()
		);

		if (ListUtil.isEmpty(backgroundTaskModels)) {
			return null;
		}

		return _getBackgroundTask(backgroundTaskModels.get(0));
	}

	public static List<BackgroundTask> getBackgroundTasks(
		long groupId, long userId) {

		DynamicQuery dynamicQuery = _getDynamicQuery(groupId, userId);

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTaskModels = BackgroundTaskLocalServiceUtil.dynamicQuery(
				dynamicQuery);

		return _translate(backgroundTaskModels);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		long groupId, long userId, int status) {

		DynamicQuery dynamicQuery = _getDynamicQuery(groupId, userId);

		dynamicQuery = dynamicQuery.add(
			RestrictionsFactoryUtil.eq("status", status));

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTaskModels = BackgroundTaskLocalServiceUtil.dynamicQuery(
				dynamicQuery);

		return _translate(backgroundTaskModels);
	}

	public static int getBackgroundTasksCount(long groupId, long userId) {
		DynamicQuery dynamicQuery = _getDynamicQuery(groupId, userId);

		return (int)BackgroundTaskLocalServiceUtil.dynamicQueryCount(
			dynamicQuery);
	}

	public static int getBackgroundTasksCount(
		long groupId, long userId, int status) {

		DynamicQuery dynamicQuery = _getDynamicQuery(groupId, userId);

		dynamicQuery = dynamicQuery.add(
			RestrictionsFactoryUtil.eq("status", status));

		return (int)BackgroundTaskLocalServiceUtil.dynamicQueryCount(
			dynamicQuery);
	}

	private static BackgroundTask _getBackgroundTask(
		com.liferay.portal.background.task.model.BackgroundTask
			backgroundTask) {

		return BackgroundTaskManagerUtil.fetchBackgroundTask(
			backgroundTask.getBackgroundTaskId());
	}

	private static DynamicQuery _getDynamicQuery(long groupId, long userId) {
		DynamicQuery dynamicQuery =
			BackgroundTaskLocalServiceUtil.dynamicQuery();

		dynamicQuery = dynamicQuery.add(
			RestrictionsFactoryUtil.eq("groupId", groupId));
		dynamicQuery = dynamicQuery.add(
			RestrictionsFactoryUtil.eq("name", String.valueOf(userId)));
		dynamicQuery = dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"taskExecutorClassName", _BACKGROUND_TASK_EXECUTOR_CLASS_NAME));

		return dynamicQuery;
	}

	private static List<BackgroundTask> _translate(
		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTaskModels) {

		if (backgroundTaskModels.isEmpty()) {
			return Collections.emptyList();
		}

		List<BackgroundTask> backgroundTasks = new ArrayList<>(
			backgroundTaskModels.size());

		for (com.liferay.portal.background.task.model.BackgroundTask
				backgroundTaskModel : backgroundTaskModels) {

			backgroundTasks.add(_getBackgroundTask(backgroundTaskModel));
		}

		return backgroundTasks;
	}

	private static final String _BACKGROUND_TASK_EXECUTOR_CLASS_NAME =
		UADExportBackgroundTaskExecutor.class.getName();

}