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

package com.liferay.portal.background.task.service.impl;

import com.liferay.portal.background.task.internal.BackgroundTaskImpl;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.base.BackgroundTaskLocalServiceBaseImpl;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskLockHelperUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocalManager;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Daniel Kocsis
 * @author Michael C. Han
 */
public class BackgroundTaskLocalServiceImpl
	extends BackgroundTaskLocalServiceBaseImpl {

	@Override
	public BackgroundTask addBackgroundTask(BackgroundTask backgroundTask) {
		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		if (taskContextMap == null) {
			taskContextMap = new HashMap<>();

			backgroundTask.setTaskContextMap(taskContextMap);
		}

		_backgroundTaskThreadLocalManager.serializeThreadLocals(taskContextMap);

		backgroundTask = super.addBackgroundTask(backgroundTask);

		final long backgroundTaskId = backgroundTask.getBackgroundTaskId();

		TransactionCommitCallbackUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					backgroundTaskLocalService.triggerBackgroundTask(
						backgroundTaskId);

					return null;
				}

			});

		return backgroundTask;
	}

	@Override
	public BackgroundTask addBackgroundTask(
			long userId, long groupId, String name,
			String taskExecutorClassName,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException {

		return addBackgroundTask(
			userId, groupId, name, null, taskExecutorClassName, taskContextMap,
			serviceContext);
	}

	@Override
	public BackgroundTask addBackgroundTask(
			long userId, long groupId, String name,
			String[] servletContextNames, Class<?> taskExecutorClass,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException {

		return addBackgroundTask(
			userId, groupId, name, servletContextNames,
			taskExecutorClass.getName(), taskContextMap, serviceContext);
	}

	@Override
	public void addBackgroundTaskAttachment(
			long userId, long backgroundTaskId, String fileName, File file)
		throws PortalException {

		BackgroundTask backgroundTask = getBackgroundTask(backgroundTaskId);

		Folder folder = backgroundTask.addAttachmentsFolder();

		PortletFileRepositoryUtil.addPortletFileEntry(
			backgroundTask.getGroupId(), userId, BackgroundTask.class.getName(),
			backgroundTask.getPrimaryKey(), PortletKeys.BACKGROUND_TASK,
			folder.getFolderId(), file, fileName, ContentTypes.APPLICATION_ZIP,
			false);
	}

	@Override
	public void addBackgroundTaskAttachment(
			long userId, long backgroundTaskId, String fileName,
			InputStream inputStream)
		throws PortalException {

		BackgroundTask backgroundTask = getBackgroundTask(backgroundTaskId);

		Folder folder = backgroundTask.addAttachmentsFolder();

		PortletFileRepositoryUtil.addPortletFileEntry(
			backgroundTask.getGroupId(), userId, BackgroundTask.class.getName(),
			backgroundTask.getPrimaryKey(), PortletKeys.BACKGROUND_TASK,
			folder.getFolderId(), inputStream, fileName,
			ContentTypes.APPLICATION_ZIP, false);
	}

	@Override
	public BackgroundTask amendBackgroundTask(
		long backgroundTaskId, Map<String, Serializable> taskContextMap,
		int status, ServiceContext serviceContext) {

		return amendBackgroundTask(
			backgroundTaskId, taskContextMap, status, null, serviceContext);
	}

	@Override
	public BackgroundTask amendBackgroundTask(
		long backgroundTaskId, Map<String, Serializable> taskContextMap,
		int status, String statusMessage, ServiceContext serviceContext) {

		BackgroundTask backgroundTask =
			backgroundTaskPersistence.fetchByPrimaryKey(backgroundTaskId);

		if (backgroundTask == null) {
			return null;
		}

		if (taskContextMap != null) {
			_backgroundTaskThreadLocalManager.serializeThreadLocals(
				taskContextMap);

			backgroundTask.setTaskContextMap(taskContextMap);
		}

		if ((status == BackgroundTaskConstants.STATUS_FAILED) ||
			(status == BackgroundTaskConstants.STATUS_SUCCESSFUL)) {

			backgroundTask.setCompleted(true);
			backgroundTask.setCompletionDate(new Date());
		}

		backgroundTask.setStatus(status);

		if (Validator.isNotNull(statusMessage)) {
			backgroundTask.setStatusMessage(statusMessage);
		}

		backgroundTaskPersistence.update(backgroundTask);

		return backgroundTask;
	}

	@Clusterable(onMaster = true)
	@Override
	public void cleanUpBackgroundTask(long backgroundTaskId, final int status) {
		final BackgroundTask backgroundTask = fetchBackgroundTask(
			backgroundTaskId);

		try {
			BackgroundTaskLockHelperUtil.unlockBackgroundTask(
				new BackgroundTaskImpl(backgroundTask));
		}
		catch (Exception e) {
		}

		TransactionCommitCallbackUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Message message = new Message();

					message.put(
						BackgroundTaskConstants.BACKGROUND_TASK_ID,
						backgroundTask.getBackgroundTaskId());
					message.put("name", backgroundTask.getName());
					message.put("status", status);
					message.put(
						"taskExecutorClassName",
						backgroundTask.getTaskExecutorClassName());

					MessageBusUtil.sendMessage(
						DestinationNames.BACKGROUND_TASK_STATUS, message);

					return null;
				}

			}
		);
	}

	@Clusterable(onMaster = true)
	@Override
	public void cleanUpBackgroundTasks() {
		List<BackgroundTask> backgroundTasks =
			backgroundTaskPersistence.findByStatus(
				BackgroundTaskConstants.STATUS_IN_PROGRESS);

		for (BackgroundTask backgroundTask : backgroundTasks) {
			backgroundTask.setStatus(BackgroundTaskConstants.STATUS_FAILED);

			cleanUpBackgroundTask(
				backgroundTask.getBackgroundTaskId(),
				BackgroundTaskConstants.STATUS_FAILED);
		}
	}

	@Override
	public BackgroundTask deleteBackgroundTask(BackgroundTask backgroundTask)
		throws PortalException {

		long folderId = backgroundTask.getAttachmentsFolderId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			PortletFileRepositoryUtil.deletePortletFolder(folderId);
		}

		if (backgroundTask.getStatus() ==
				BackgroundTaskConstants.STATUS_IN_PROGRESS) {

			cleanUpBackgroundTask(
				backgroundTask.getBackgroundTaskId(),
				BackgroundTaskConstants.STATUS_CANCELLED);
		}

		return backgroundTaskPersistence.remove(backgroundTask);
	}

	@Override
	public BackgroundTask deleteBackgroundTask(long backgroundTaskId)
		throws PortalException {

		BackgroundTask backgroundTask =
			backgroundTaskPersistence.findByPrimaryKey(backgroundTaskId);

		return deleteBackgroundTask(backgroundTask);
	}

	@Override
	public void deleteCompanyBackgroundTasks(long companyId)
		throws PortalException {

		List<BackgroundTask> backgroundTasks =
			backgroundTaskPersistence.findByCompanyId(companyId);

		for (BackgroundTask backgroundTask : backgroundTasks) {
			deleteBackgroundTask(backgroundTask);
		}
	}

	@Override
	public void deleteGroupBackgroundTasks(long groupId)
		throws PortalException {

		List<BackgroundTask> backgroundTasks =
			backgroundTaskPersistence.findByGroupId(groupId);

		for (BackgroundTask backgroundTask : backgroundTasks) {
			deleteBackgroundTask(backgroundTask);
		}
	}

	@Override
	public BackgroundTask fetchBackgroundTask(long backgroundTaskId) {
		return backgroundTaskPersistence.fetchByPrimaryKey(backgroundTaskId);
	}

	@Override
	public BackgroundTask fetchFirstBackgroundTask(
		long groupId, String taskExecutorClassName, boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.fetchByG_T_C_First(
			groupId, taskExecutorClassName, completed, orderByComparator);
	}

	@Override
	public BackgroundTask fetchFirstBackgroundTask(
		String taskExecutorClassName, int status) {

		return fetchFirstBackgroundTask(taskExecutorClassName, status, null);
	}

	@Override
	public BackgroundTask fetchFirstBackgroundTask(
		String taskExecutorClassName, int status,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.fetchByT_S_First(
			taskExecutorClassName, status, orderByComparator);
	}

	@Override
	public BackgroundTask getBackgroundTask(long backgroundTaskId)
		throws PortalException {

		return backgroundTaskPersistence.findByPrimaryKey(backgroundTaskId);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(long groupId, int status) {
		return backgroundTaskPersistence.findByG_S(groupId, status);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName) {

		return backgroundTaskPersistence.findByG_T(
			groupId, taskExecutorClassName);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName, int status) {

		return backgroundTaskPersistence.findByG_T_S(
			groupId, taskExecutorClassName, status);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.findByG_T(
			groupId, taskExecutorClassName, start, end, orderByComparator);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.findByG_N_T(
			groupId, name, taskExecutorClassName, start, end,
			orderByComparator);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames) {

		return backgroundTaskPersistence.findByG_T(
			groupId, taskExecutorClassNames);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames, int status) {

		return backgroundTaskPersistence.findByG_T_S(
			groupId, taskExecutorClassNames, status);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.findByG_T(
			groupId, taskExecutorClassNames, start, end, orderByComparator);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.findByG_N_T(
			groupIds, name, taskExecutorClassName, start, end,
			orderByComparator);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		String taskExecutorClassName, int status) {

		return backgroundTaskPersistence.findByT_S(
			taskExecutorClassName, status);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		String taskExecutorClassName, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.findByT_S(
			taskExecutorClassName, status, start, end, orderByComparator);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		String[] taskExecutorClassNames, int status) {

		return backgroundTaskPersistence.findByT_S(
			taskExecutorClassNames, status);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		String[] taskExecutorClassNames, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.findByT_S(
			taskExecutorClassNames, status, start, end, orderByComparator);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String taskExecutorClassName) {

		return backgroundTaskPersistence.countByG_T(
			groupId, taskExecutorClassName);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String taskExecutorClassName, boolean completed) {

		return backgroundTaskPersistence.countByG_T_C(
			groupId, taskExecutorClassName, completed);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String name, String taskExecutorClassName) {

		return backgroundTaskPersistence.countByG_N_T(
			groupId, name, taskExecutorClassName);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String name, String taskExecutorClassName,
		boolean completed) {

		return backgroundTaskPersistence.countByG_N_T_C(
			groupId, name, taskExecutorClassName, completed);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String[] taskExecutorClassNames) {

		return backgroundTaskPersistence.countByG_T(
			groupId, taskExecutorClassNames);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String[] taskExecutorClassNames, boolean completed) {

		return backgroundTaskPersistence.countByG_T_C(
			groupId, taskExecutorClassNames, completed);
	}

	@Override
	public int getBackgroundTasksCount(
		long[] groupIds, String name, String taskExecutorClassName) {

		return backgroundTaskPersistence.countByG_N_T(
			groupIds, name, taskExecutorClassName);
	}

	@Override
	public int getBackgroundTasksCount(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed) {

		return backgroundTaskPersistence.countByG_N_T_C(
			groupIds, name, taskExecutorClassName, completed);
	}

	@Clusterable(onMaster = true)
	@Override
	public String getBackgroundTaskStatusJSON(long backgroundTaskId) {
		BackgroundTaskStatus backgroundTaskStatus =
			_backgroundTaskStatusRegistry.getBackgroundTaskStatus(
				backgroundTaskId);

		if (backgroundTaskStatus != null) {
			return backgroundTaskStatus.getAttributesJSON();
		}

		return StringPool.BLANK;
	}

	@Clusterable(onMaster = true)
	@Override
	public void resumeBackgroundTask(long backgroundTaskId) {
		BackgroundTask backgroundTask =
			backgroundTaskPersistence.fetchByPrimaryKey(backgroundTaskId);

		if ((backgroundTask == null) ||
			(backgroundTask.getStatus() !=
				BackgroundTaskConstants.STATUS_QUEUED)) {

			return;
		}

		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID, backgroundTaskId);

		MessageBusUtil.sendMessage(DestinationNames.BACKGROUND_TASK, message);
	}

	@Clusterable(onMaster = true)
	@Override
	public void triggerBackgroundTask(long backgroundTaskId) {
		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID, backgroundTaskId);

		MessageBusUtil.sendMessage(DestinationNames.BACKGROUND_TASK, message);
	}

	protected BackgroundTask addBackgroundTask(
			long userId, long groupId, String name,
			String[] servletContextNames, String taskExecutorClassName,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		final long backgroundTaskId = counterLocalService.increment();

		BackgroundTask backgroundTask = backgroundTaskPersistence.create(
			backgroundTaskId);

		backgroundTask.setCompanyId(user.getCompanyId());
		backgroundTask.setGroupId(groupId);
		backgroundTask.setUserId(userId);
		backgroundTask.setUserName(user.getFullName());
		backgroundTask.setName(name);

		if (ArrayUtil.isNotEmpty(servletContextNames)) {
			backgroundTask.setServletContextNames(
				StringUtil.merge(servletContextNames));
		}

		backgroundTask.setTaskExecutorClassName(taskExecutorClassName);

		if (taskContextMap == null) {
			taskContextMap = new HashMap<>();
		}

		_backgroundTaskThreadLocalManager.serializeThreadLocals(taskContextMap);

		backgroundTask.setTaskContextMap(taskContextMap);

		backgroundTask.setStatus(BackgroundTaskConstants.STATUS_NEW);

		backgroundTaskPersistence.update(backgroundTask);

		TransactionCommitCallbackUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					backgroundTaskLocalService.triggerBackgroundTask(
						backgroundTaskId);

					return null;
				}

			});

		return backgroundTask;
	}

	protected void setBackgroundTaskStatusRegistry(
		BackgroundTaskStatusRegistry backgroundTaskStatusRegistry) {

		_backgroundTaskStatusRegistry = backgroundTaskStatusRegistry;
	}

	protected void setBackgroundTaskThreadLocalManager(
		BackgroundTaskThreadLocalManager backgroundTaskThreadLocalManager) {

		_backgroundTaskThreadLocalManager = backgroundTaskThreadLocalManager;
	}

	@ServiceReference(type = BackgroundTaskStatusRegistry.class)
	private BackgroundTaskStatusRegistry _backgroundTaskStatusRegistry;

	@ServiceReference(type = BackgroundTaskThreadLocalManager.class)
	private BackgroundTaskThreadLocalManager _backgroundTaskThreadLocalManager;

}