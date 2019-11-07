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

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.background.task.internal.BackgroundTaskImpl;
import com.liferay.portal.background.task.internal.lock.BackgroundTaskLockHelper;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.base.BackgroundTaskLocalServiceBaseImpl;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocalManager;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 * @author Michael C. Han
 */
@Component(
	property = "model.class.name=com.liferay.portal.background.task.model.BackgroundTask",
	service = AopService.class
)
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
			_backgroundTaskLockHelper.unlockBackgroundTask(
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

					_messageBus.sendMessage(
						DestinationNames.BACKGROUND_TASK_STATUS, message);

					return null;
				}

			});
	}

	@Clusterable(onMaster = true)
	@Override
	public void cleanUpBackgroundTasks() {
		List<BackgroundTask> backgroundTasks =
			backgroundTaskPersistence.findByCompleted(false);

		for (BackgroundTask backgroundTask : backgroundTasks) {
			if (backgroundTask.getStatus() ==
					BackgroundTaskConstants.STATUS_IN_PROGRESS) {

				backgroundTask.setCompleted(true);
				backgroundTask.setStatus(BackgroundTaskConstants.STATUS_FAILED);

				backgroundTaskPersistence.update(backgroundTask);
			}

			cleanUpBackgroundTask(
				backgroundTask.getBackgroundTaskId(),
				backgroundTask.getStatus());
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
	public void deleteGroupBackgroundTasks(
			long groupId, String name, String taskExecutorClassName)
		throws PortalException {

		List<BackgroundTask> backgroundTasks =
			backgroundTaskPersistence.findByG_N_T(
				groupId, name, taskExecutorClassName);

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
		long groupId, String taskExecutorClassName, boolean completed,
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.findByG_T_C(
			groupId, taskExecutorClassName, completed, start, end,
			orderByComparator);
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
		long groupId, String[] taskExecutorClassNames, int status) {

		return backgroundTaskPersistence.findByG_T_S(
			groupId, taskExecutorClassNames, status);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.findByG_N_T(
			groupIds, name, new String[] {taskExecutorClassName}, start, end,
			orderByComparator);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String name, String[] taskExecutorClassNames,
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.findByG_N_T(
			groupIds, name, taskExecutorClassNames, start, end,
			orderByComparator);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String[] taskExecutorClassNames) {

		return backgroundTaskPersistence.findByG_T(
			groupIds, taskExecutorClassNames);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed) {

		return backgroundTaskPersistence.findByG_T_C(
			groupIds, taskExecutorClassNames, completed);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed,
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.findByG_T_C(
			groupIds, taskExecutorClassNames, completed, start, end,
			orderByComparator);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String[] taskExecutorClassNames, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return backgroundTaskPersistence.findByG_T(
			groupIds, taskExecutorClassNames, start, end, orderByComparator);
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
	public List<BackgroundTask> getBackgroundTasksByDuration(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed,
		int start, int end, boolean orderByType) {

		return backgroundTaskFinder.findByG_T_C(
			groupIds, taskExecutorClassNames, completed, start, end,
			orderByType);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasksByDuration(
		long[] groupIds, String[] taskExecutorClassNames, int start, int end,
		boolean orderByType) {

		return backgroundTaskFinder.findByG_T_C(
			groupIds, taskExecutorClassNames, null, start, end, orderByType);
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
		long[] groupIds, String name, String taskExecutorClassName) {

		return backgroundTaskPersistence.countByG_N_T(
			groupIds, name, new String[] {taskExecutorClassName});
	}

	@Override
	public int getBackgroundTasksCount(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed) {

		return backgroundTaskPersistence.countByG_N_T_C(
			groupIds, name, taskExecutorClassName, completed);
	}

	@Override
	public int getBackgroundTasksCount(
		long[] groupIds, String name, String[] taskExecutorClassName) {

		return backgroundTaskPersistence.countByG_N_T(
			groupIds, name, taskExecutorClassName);
	}

	@Override
	public int getBackgroundTasksCount(
		long[] groupIds, String[] taskExecutorClassNames) {

		return backgroundTaskPersistence.countByG_T(
			groupIds, taskExecutorClassNames);
	}

	@Override
	public int getBackgroundTasksCount(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed) {

		return backgroundTaskPersistence.countByG_T_C(
			groupIds, taskExecutorClassNames, completed);
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

			if (_log.isDebugEnabled()) {
				_log.debug(
					"No background task found with queued status for " +
						"background task ID " + backgroundTaskId);
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Attempting to resume background task " + backgroundTaskId);
		}

		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID, backgroundTaskId);

		_messageBus.sendMessage(DestinationNames.BACKGROUND_TASK, message);
	}

	@Clusterable(onMaster = true)
	@Override
	public void triggerBackgroundTask(long backgroundTaskId) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Attempting to trigger background task " + backgroundTaskId);
		}

		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID, backgroundTaskId);

		_messageBus.sendMessage(DestinationNames.BACKGROUND_TASK, message);
	}

	@Activate
	protected void activate() {
		_backgroundTaskLockHelper = new BackgroundTaskLockHelper(_lockManager);
	}

	protected BackgroundTask addBackgroundTask(
			long userId, long groupId, String name,
			String[] servletContextNames, String taskExecutorClassName,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException {

		User user = null;

		if (userId != UserConstants.USER_ID_DEFAULT) {
			user = userLocalService.fetchUser(userId);
		}

		final long backgroundTaskId = counterLocalService.increment();

		BackgroundTask backgroundTask = backgroundTaskPersistence.create(
			backgroundTaskId);

		if (user != null) {
			backgroundTask.setCompanyId(user.getCompanyId());
			backgroundTask.setUserName(user.getFullName());
		}
		else {
			backgroundTask.setCompanyId(CompanyConstants.SYSTEM);
			backgroundTask.setUserName(StringPool.BLANK);
		}

		backgroundTask.setGroupId(groupId);
		backgroundTask.setUserId(userId);
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

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskLocalServiceImpl.class);

	private BackgroundTaskLockHelper _backgroundTaskLockHelper;

	@Reference
	private BackgroundTaskStatusRegistry _backgroundTaskStatusRegistry;

	@Reference
	private BackgroundTaskThreadLocalManager _backgroundTaskThreadLocalManager;

	@Reference
	private LockManager _lockManager;

	@Reference
	private MessageBus _messageBus;

}