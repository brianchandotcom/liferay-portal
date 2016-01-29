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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public interface BackgroundTaskManager {

	public BackgroundTask addBackgroundTask(
			long userId, long groupId, String name,
			String taskExecutorClassName,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException;

	public BackgroundTask addBackgroundTask(
			long userId, long groupId, String name,
			String[] servletContextNames, Class<?> taskExecutorClass,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException;

	public void addBackgroundTaskAttachment(
			long userId, long backgroundTaskId, String fileName, File file)
		throws PortalException;

	public void addBackgroundTaskAttachment(
			long userId, long backgroundTaskId, String fileName,
			InputStream inputStream)
		throws PortalException;

	public BackgroundTask amendBackgroundTask(
		long backgroundTaskId, Map<String, Serializable> taskContextMap,
		int status, ServiceContext serviceContext);

	public BackgroundTask amendBackgroundTask(
		long backgroundTaskId, Map<String, Serializable> taskContextMap,
		int status, String statusMessage, ServiceContext serviceContext);

	public void cleanUpBackgroundTask(
		BackgroundTask backgroundTask, int status);

	public void cleanUpBackgroundTasks();

	public BackgroundTask deleteBackgroundTask(long backgroundTaskId)
		throws PortalException;

	public void deleteCompanyBackgroundTasks(long companyId)
		throws PortalException;

	public void deleteGroupBackgroundTasks(long groupId) throws PortalException;

	public void deleteGroupBackgroundTasks(
			long groupId, String name, String taskExecutorClassName)
		throws PortalException;

	public BackgroundTask fetchBackgroundTask(long backgroundTaskId);

	public BackgroundTask fetchFirstBackgroundTask(
		long groupId, String taskExecutorClassName, boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator);

	public BackgroundTask fetchFirstBackgroundTask(
		String taskExecutorClassName, int status);

	public BackgroundTask fetchFirstBackgroundTask(
		String taskExecutorClassName, int status,
		OrderByComparator<BackgroundTask> orderByComparator);

	public BackgroundTask getBackgroundTask(long backgroundTaskId)
		throws PortalException;

	public List<BackgroundTask> getBackgroundTasks(long groupId, int status);

	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName);

	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName, int status);

	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator);

	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator);

	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames);

	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames, int status);

	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator);

	public List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator);

	public List<BackgroundTask> getBackgroundTasks(
		String taskExecutorClassName, int status);

	public List<BackgroundTask> getBackgroundTasks(
		String taskExecutorClassName, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator);

	public List<BackgroundTask> getBackgroundTasks(
		String[] taskExecutorClassNames, int status);

	public List<BackgroundTask> getBackgroundTasks(
		String[] taskExecutorClassNames, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator);

	public int getBackgroundTasksCount(
		long groupId, String taskExecutorClassName);

	public int getBackgroundTasksCount(
		long groupId, String taskExecutorClassName, boolean completed);

	public int getBackgroundTasksCount(
		long groupId, String name, String taskExecutorClassName);

	public int getBackgroundTasksCount(
		long groupId, String name, String taskExecutorClassName,
		boolean completed);

	public int getBackgroundTasksCount(
		long groupId, String[] taskExecutorClassNames);

	public int getBackgroundTasksCount(
		long groupId, String[] taskExecutorClassNames, boolean completed);

	public int getBackgroundTasksCount(
		long[] groupIds, String name, String taskExecutorClassName);

	public int getBackgroundTasksCount(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed);

	public String getBackgroundTaskStatusJSON(long backgroundTaskId);

	public void resumeBackgroundTask(long backgroundTaskId);

	public void triggerBackgroundTask(long backgroundTaskId);

}