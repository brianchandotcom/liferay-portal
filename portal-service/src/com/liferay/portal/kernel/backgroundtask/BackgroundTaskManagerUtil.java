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
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskManagerUtil {

	public static BackgroundTask addBackgroundTask(
			long userId, long groupId, String name,
			String taskExecutorClassName,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException {

		return _backgroundTaskManager.addBackgroundTask(
			userId, groupId, name, taskExecutorClassName, taskContextMap,
			serviceContext);
	}

	public static BackgroundTask addBackgroundTask(
			long userId, long groupId, String name,
			String[] servletContextNames, Class<?> taskExecutorClass,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException {

		return _backgroundTaskManager.addBackgroundTask(
			userId, groupId, name, servletContextNames, taskExecutorClass,
			taskContextMap, serviceContext);
	}

	public static void addBackgroundTaskAttachment(
			long userId, long backgroundTaskId, String fileName, File file)
		throws PortalException {

		_backgroundTaskManager.addBackgroundTaskAttachment(
			userId, backgroundTaskId, fileName, file);
	}

	public static BackgroundTask amendBackgroundTask(
		long backgroundTaskId, Map<String, Serializable> taskContextMap,
		int status, ServiceContext serviceContext) {

		return _backgroundTaskManager.amendBackgroundTask(
			backgroundTaskId, taskContextMap, status, serviceContext);
	}

	public static BackgroundTask amendBackgroundTask(
		long backgroundTaskId, Map<String, Serializable> taskContextMap,
		int status, String statusMessage, ServiceContext serviceContext) {

		return _backgroundTaskManager.amendBackgroundTask(
			backgroundTaskId, taskContextMap, status, statusMessage,
			serviceContext);
	}

	public static void cleanUpBackgroundTask(
		BackgroundTask backgroundTask, int status) {

		_backgroundTaskManager.cleanUpBackgroundTask(backgroundTask, status);
	}

	public static void cleanUpBackgroundTasks() {
		_backgroundTaskManager.cleanUpBackgroundTasks();
	}

	public static BackgroundTask deleteBackgroundTask(long backgroundTaskId)
		throws PortalException {

		return _backgroundTaskManager.deleteBackgroundTask(backgroundTaskId);
	}

	public static void deleteCompanyBackgroundTasks(long companyId)
		throws PortalException {

		_backgroundTaskManager.deleteCompanyBackgroundTasks(companyId);
	}

	public static void deleteGroupBackgroundTasks(long groupId)
		throws PortalException {

		_backgroundTaskManager.deleteGroupBackgroundTasks(groupId);
	}

	public static void deleteGroupBackgroundTasks(
			long groupId, String name, String taskExecutorClassName)
		throws PortalException {

		_backgroundTaskManager.deleteGroupBackgroundTasks(
			groupId, name, taskExecutorClassName);
	}

	public static BackgroundTask fetchBackgroundTask(long backgroundTaskId) {
		return _backgroundTaskManager.fetchBackgroundTask(backgroundTaskId);
	}

	public static BackgroundTask fetchFirstBackgroundTask(
		long groupId, String taskExecutorClassName, boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return _backgroundTaskManager.fetchFirstBackgroundTask(
			groupId, taskExecutorClassName, completed, orderByComparator);
	}

	public static BackgroundTask fetchFirstBackgroundTask(
		String taskExecutorClassName, int status) {

		return _backgroundTaskManager.fetchFirstBackgroundTask(
			taskExecutorClassName, status);
	}

	public static BackgroundTask fetchFirstBackgroundTask(
		String taskExecutorClassName, int status,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return _backgroundTaskManager.fetchFirstBackgroundTask(
			taskExecutorClassName, status, orderByComparator);
	}

	public static BackgroundTask getBackgroundTask(long backgroundTaskId)
		throws PortalException {

		return _backgroundTaskManager.getBackgroundTask(backgroundTaskId);
	}

	public static List<BackgroundTask>
		getBackgroundTasks(long groupId, int status) {

		return _backgroundTaskManager.getBackgroundTasks(groupId, status);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName) {

		return _backgroundTaskManager.getBackgroundTasks(
			groupId, taskExecutorClassName);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName, int status) {

		return _backgroundTaskManager.getBackgroundTasks(
			groupId, taskExecutorClassName, status);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return _backgroundTaskManager.getBackgroundTasks(
			groupId, taskExecutorClassName, start, end, orderByComparator);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		long groupId, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		return _backgroundTaskManager.getBackgroundTasks(
			groupId, name, taskExecutorClassName, start, end,
			orderByComparator);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames) {

		return _backgroundTaskManager.getBackgroundTasks(
			groupId, taskExecutorClassNames);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames, int status) {

		return _backgroundTaskManager.getBackgroundTasks(
			groupId, taskExecutorClassNames, status);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return _backgroundTaskManager.getBackgroundTasks(
			groupId, taskExecutorClassNames, start, end, orderByComparator);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		return _backgroundTaskManager.getBackgroundTasks(
			groupIds, name, taskExecutorClassName, start, end,
			orderByComparator);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		String taskExecutorClassName, int status) {

		return _backgroundTaskManager.getBackgroundTasks(
			taskExecutorClassName, status);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		String taskExecutorClassName, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return _backgroundTaskManager.getBackgroundTasks(
			taskExecutorClassName, status, start, end, orderByComparator);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		String[] taskExecutorClassNames, int status) {

		return _backgroundTaskManager.getBackgroundTasks(
			taskExecutorClassNames, status);
	}

	public static List<BackgroundTask> getBackgroundTasks(
		String[] taskExecutorClassNames, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return _backgroundTaskManager.getBackgroundTasks(
			taskExecutorClassNames, status, start, end, orderByComparator);
	}

	public static int getBackgroundTasksCount(
		long groupId, String taskExecutorClassName) {

		return _backgroundTaskManager.getBackgroundTasksCount(
			groupId, taskExecutorClassName);
	}

	public static int getBackgroundTasksCount(
		long groupId, String taskExecutorClassName, boolean completed) {

		return _backgroundTaskManager.getBackgroundTasksCount(
			groupId, taskExecutorClassName, completed);
	}

	public static int getBackgroundTasksCount(
		long groupId, String name, String taskExecutorClassName) {

		return _backgroundTaskManager.getBackgroundTasksCount(
			groupId, name, taskExecutorClassName);
	}

	public static int getBackgroundTasksCount(
		long groupId, String name, String taskExecutorClassName,
		boolean completed) {

		return _backgroundTaskManager.getBackgroundTasksCount(
			groupId, name, taskExecutorClassName, completed);
	}

	public static int getBackgroundTasksCount(
		long groupId, String[] taskExecutorClassNames) {

		return _backgroundTaskManager.getBackgroundTasksCount(
			groupId, taskExecutorClassNames);
	}

	public static int getBackgroundTasksCount(
		long groupId, String[] taskExecutorClassNames, boolean completed) {

		return _backgroundTaskManager.getBackgroundTasksCount(
			groupId, taskExecutorClassNames, completed);
	}

	public static int getBackgroundTasksCount(
		long[] groupIds, String name, String taskExecutorClassName) {

		return _backgroundTaskManager.getBackgroundTasksCount(
			groupIds, name, taskExecutorClassName);
	}

	public static int getBackgroundTasksCount(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed) {

		return _backgroundTaskManager.getBackgroundTasksCount(
			groupIds, name, taskExecutorClassName, completed);
	}

	public static String getBackgroundTaskStatusJSON(long backgroundTaskId) {
		return _backgroundTaskManager.getBackgroundTaskStatusJSON(
			backgroundTaskId);
	}

	public static void resumeBackgroundTask(long backgroundTaskId) {
		_backgroundTaskManager.resumeBackgroundTask(backgroundTaskId);
	}

	public static void triggerBackgroundTask(long backgroundTaskId) {
		_backgroundTaskManager.triggerBackgroundTask(backgroundTaskId);
	}

	private static final BackgroundTaskManager
		_backgroundTaskManager = ProxyFactory.newServiceTrackedInstance(
			BackgroundTaskManager.class);

}