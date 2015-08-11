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

package com.liferay.portal.backgroundtask.internal;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.backgroundtask.util.comparator.BackgroundTaskCompletionDateComparator;
import com.liferay.portlet.backgroundtask.util.comparator.BackgroundTaskCreateDateComparator;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskManagerImpl implements BackgroundTaskManager {

	@Override
	public BackgroundTask addBackgroundTask(
			long userId, long groupId, String name,
			String[] servletContextNames, Class<?> taskExecutorClass,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException {

		com.liferay.portal.model.BackgroundTask backgroundTaskModel =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				userId, groupId, name, servletContextNames, taskExecutorClass,
				taskContextMap, serviceContext);

		return new BackgroundTaskImpl(backgroundTaskModel);
	}

	@Override
	public void addBackgroundTaskAttachment(
			long userId, long backgroundTaskId, String fileName, File file)
		throws PortalException {

		BackgroundTaskLocalServiceUtil.addBackgroundTaskAttachment(
			userId, backgroundTaskId, fileName, file);
	}

	@Override
	public void addBackgroundTaskAttachment(
			long userId, long backgroundTaskId, String fileName,
			InputStream inputStream)
		throws PortalException {

		BackgroundTaskLocalServiceUtil.addBackgroundTaskAttachment(
			userId, backgroundTaskId, fileName, inputStream);
	}

	@Override
	public BackgroundTask
		amendBackgroundTask(
			long backgroundTaskId, Map<String, Serializable> taskContextMap,
			int status, ServiceContext serviceContext) {

		com.liferay.portal.model.BackgroundTask backgroundTaskModel =
			BackgroundTaskLocalServiceUtil.amendBackgroundTask(
				backgroundTaskId, taskContextMap, status, serviceContext);

		return new BackgroundTaskImpl(backgroundTaskModel);
	}

	@Override
	public BackgroundTask
		amendBackgroundTask(
			long backgroundTaskId, Map<String, Serializable> taskContextMap,
			int status, String statusMessage, ServiceContext serviceContext) {

		com.liferay.portal.model.BackgroundTask backgroundTaskModel =
			BackgroundTaskLocalServiceUtil.amendBackgroundTask(
				backgroundTaskId, taskContextMap, status, statusMessage,
				serviceContext);

		return new BackgroundTaskImpl(backgroundTaskModel);
	}

	@Override
	public void cleanUpBackgroundTask(
		BackgroundTask backgroundTask, int status) {

		BackgroundTaskLocalServiceUtil.cleanUpBackgroundTask(
			backgroundTask.getBackgroundTaskId(), status);
	}

	@Override
	public void cleanUpBackgroundTasks() {
		BackgroundTaskLocalServiceUtil.cleanUpBackgroundTasks();
	}

	@Override
	public BackgroundTask
			deleteBackgroundTask(long backgroundTaskId)
		throws PortalException {

		com.liferay.portal.model.BackgroundTask backgroundTaskModel =
			BackgroundTaskLocalServiceUtil.deleteBackgroundTask(
				backgroundTaskId);

		return new BackgroundTaskImpl(backgroundTaskModel);
	}

	@Override
	public void deleteCompanyBackgroundTasks(long companyId)
		throws PortalException {

		BackgroundTaskLocalServiceUtil.deleteCompanyBackgroundTasks(companyId);
	}

	@Override
	public void deleteGroupBackgroundTasks(long groupId)
		throws PortalException {

		BackgroundTaskLocalServiceUtil.deleteGroupBackgroundTasks(groupId);
	}

	@Override
	public BackgroundTask
		fetchBackgroundTask(long backgroundTaskId) {

		com.liferay.portal.model.BackgroundTask backgroundTaskModel =
			BackgroundTaskLocalServiceUtil.fetchBackgroundTask(
				backgroundTaskId);

		if (backgroundTaskModel == null) {
			return null;
		}

		return new BackgroundTaskImpl(backgroundTaskModel);
	}

	@Override
	public BackgroundTask
		fetchFirstBackgroundTask(
			long groupId, String taskExecutorClassName, boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator) {

		com.liferay.portal.model.BackgroundTask backgroundTaskModel =
			BackgroundTaskLocalServiceUtil.fetchFirstBackgroundTask(
				groupId, taskExecutorClassName, completed,
				convert(orderByComparator));

		if (backgroundTaskModel == null) {
			return null;
		}

		return new BackgroundTaskImpl(backgroundTaskModel);
	}

	@Override
	public BackgroundTask
		fetchFirstBackgroundTask(String taskExecutorClassName, int status) {

		com.liferay.portal.model.BackgroundTask bcakgroundTaskModel =
			BackgroundTaskLocalServiceUtil.fetchFirstBackgroundTask(
				taskExecutorClassName, status);

		if (bcakgroundTaskModel == null) {
			return null;
		}

		return new BackgroundTaskImpl(bcakgroundTaskModel);
	}

	@Override
	public BackgroundTask
		fetchFirstBackgroundTask(
			String taskExecutorClassName, int status,
			OrderByComparator<BackgroundTask> orderByComparator) {

		com.liferay.portal.model.BackgroundTask backgroundTaskModel =
			BackgroundTaskLocalServiceUtil.fetchFirstBackgroundTask(
				taskExecutorClassName, status, convert(orderByComparator));

		if (backgroundTaskModel == null) {
			return null;
		}

		return new BackgroundTaskImpl(backgroundTaskModel);
	}

	@Override
	public BackgroundTask
			getBackgroundTask(long backgroundTaskId)
		throws PortalException {

		com.liferay.portal.model.BackgroundTask backgroundTaskModel =
			BackgroundTaskLocalServiceUtil.getBackgroundTask(backgroundTaskId);

		return new BackgroundTaskImpl(backgroundTaskModel);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(long groupId, int status) {
		List<com.liferay.portal.model.BackgroundTask> backgroundTaskModels =
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(groupId, status);

		return convert(backgroundTaskModels);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName) {

		List<com.liferay.portal.model.BackgroundTask> backgroundTaskModels =
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(
				groupId, taskExecutorClassName);

		return convert(backgroundTaskModels);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName, int status) {

		List<com.liferay.portal.model.BackgroundTask> backgroundTaskModels =
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(
				groupId, taskExecutorClassName, status);

		return convert(backgroundTaskModels);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.model.BackgroundTask> backgroundTaskModels =
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(
				groupId, taskExecutorClassName, start, end,
				convert(orderByComparator));

		return convert(backgroundTaskModels);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.model.BackgroundTask> backgroundTaskModels =
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(
				groupId, name, taskExecutorClassName, start, end,
				convert(orderByComparator));

		return convert(backgroundTaskModels);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames) {

		List<com.liferay.portal.model.BackgroundTask> backgroundTaskModels =
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(
				groupId, taskExecutorClassNames);

		return convert(backgroundTaskModels);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames, int status) {

		List<com.liferay.portal.model.BackgroundTask> backgroundTaskModels =
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(
				groupId, taskExecutorClassNames, status);

		return convert(backgroundTaskModels);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.model.BackgroundTask> backgroundTaskModels =
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(
				groupId, taskExecutorClassNames, start, end,
				convert(orderByComparator));

		return convert(backgroundTaskModels);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		String taskExecutorClassName, int status) {

		List<com.liferay.portal.model.BackgroundTask> backgroundTaskModels =
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(
				taskExecutorClassName, status);

		return convert(backgroundTaskModels);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		String taskExecutorClassName, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.model.BackgroundTask> backgroundTaskModels =
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(
				taskExecutorClassName, status, start, end,
				convert(orderByComparator));

		return convert(backgroundTaskModels);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		String[] taskExecutorClassNames, int status) {

		List<com.liferay.portal.model.BackgroundTask> backgroundTaskModels =
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(
				taskExecutorClassNames, status);

		return convert(backgroundTaskModels);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		String[] taskExecutorClassNames, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.model.BackgroundTask> backgroundTaskModels =
			BackgroundTaskLocalServiceUtil.getBackgroundTasks(
				taskExecutorClassNames, status, start, end,
				convert(orderByComparator));

		return convert(backgroundTaskModels);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String taskExecutorClassName) {

		return BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(
			groupId, taskExecutorClassName);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String taskExecutorClassName, boolean completed) {

		return BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(
			groupId, taskExecutorClassName, completed);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String name, String taskExecutorClassName) {

		return BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(
			groupId, name, taskExecutorClassName);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String name, String taskExecutorClassName,
		boolean completed) {

		return BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(
			groupId, name, taskExecutorClassName, completed);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String[] taskExecutorClassNames) {

		return BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(
			groupId, taskExecutorClassNames);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String[] taskExecutorClassNames, boolean completed) {

		return BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(
			groupId, taskExecutorClassNames, completed);
	}

	@Override
	public String getBackgroundTaskStatusJSON(long backgroundTaskId) {
		return BackgroundTaskLocalServiceUtil.getBackgroundTaskStatusJSON(
			backgroundTaskId);
	}

	@Override
	public void resumeBackgroundTask(long backgroundTaskId) {
		BackgroundTaskLocalServiceUtil.resumeBackgroundTask(backgroundTaskId);
	}

	@Override
	public void triggerBackgroundTask(long backgroundTaskId) {
		BackgroundTaskLocalServiceUtil.triggerBackgroundTask(backgroundTaskId);
	}

	@Override
	public BackgroundTask updateBackgroundTask(BackgroundTask backgroundTask) {
		com.liferay.portal.model.BackgroundTask backgroundTaskModel =
			(com.liferay.portal.model.BackgroundTask)backgroundTask.getModel();

		backgroundTaskModel =
			BackgroundTaskLocalServiceUtil.updateBackgroundTask(
				backgroundTaskModel);

		return new BackgroundTaskImpl(backgroundTaskModel);
	}

	protected List<BackgroundTask>
		convert(
			List<com.liferay.portal.model.BackgroundTask>
				backgroundTaskModels) {

		if (backgroundTaskModels.isEmpty()) {
			return Collections.emptyList();
		}

		List<BackgroundTask> backgroundTasks = new ArrayList<>(
			backgroundTaskModels.size());

		for (com.liferay.portal.model.BackgroundTask backgroundTaskModel :
				backgroundTaskModels) {

			backgroundTasks.add(new BackgroundTaskImpl(backgroundTaskModel));
		}

		return backgroundTasks;
	}

	protected OrderByComparator<com.liferay.portal.model.BackgroundTask>
		convert(OrderByComparator<BackgroundTask> orderByComparator) {

		if (orderByComparator instanceof
				BackgroundTaskCompletionDateComparator) {

			return new com.liferay.portal.backgroundtask.internal.comparator.
				BackgroundTaskCompletionDateComparator(
					orderByComparator.isAscending());
		}
		else if (orderByComparator instanceof
					BackgroundTaskCreateDateComparator) {

			return new com.liferay.portal.backgroundtask.internal.comparator.
				BackgroundTaskCreateDateComparator(
					orderByComparator.isAscending());
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					"No comparator available to convert: " +
						orderByComparator.getClass().getName());
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskManagerImpl.class);

}