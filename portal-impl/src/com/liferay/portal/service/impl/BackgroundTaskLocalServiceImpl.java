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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.BackgroundTaskConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.BackgroundTaskLocalServiceBaseImpl;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.comparator.BackgroundTaskCreateDateComparator;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
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
	public BackgroundTask addBackgroundTask(
			long userId, long groupId, String name,
			String[] servletContextNames, Class<?> taskExecutorClass,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		final long backgroundTaskId = counterLocalService.increment();

		BackgroundTask backgroundTask = backgroundTaskPersistence.create(
			backgroundTaskId);

		backgroundTask.setCompanyId(user.getCompanyId());
		backgroundTask.setCreateDate(serviceContext.getCreateDate(now));
		backgroundTask.setGroupId(groupId);
		backgroundTask.setModifiedDate(serviceContext.getModifiedDate(now));
		backgroundTask.setUserId(userId);
		backgroundTask.setUserName(user.getFullName());
		backgroundTask.setName(name);
		backgroundTask.setServletContextNames(
			StringUtil.merge(servletContextNames));
		backgroundTask.setTaskExecutorClassName(taskExecutorClass.getName());

		if (taskContextMap != null) {
			String taskContext = JSONFactoryUtil.serialize(taskContextMap);

			backgroundTask.setTaskContext(taskContext);
		}

		backgroundTask.setStatus(BackgroundTaskConstants.STATUS_NEW);

		backgroundTaskPersistence.update(backgroundTask);

		TransactionCommitCallbackRegistryUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Message message = new Message();

					message.put("backgroundTaskId", backgroundTaskId);

					MessageBusUtil.sendMessage(
						DestinationNames.BACKGROUND_TASK, message);

					return null;
				}

			});

		return backgroundTask;
	}

	@Override
	public void addBackgroundTaskAttachment(
			long userId, long backgroundTaskId, String fileName, File file)
		throws PortalException, SystemException {

		BackgroundTask backgroundTask = getBackgroundTask(backgroundTaskId);

		Folder folder = backgroundTask.addAttachmentsFolder();

		PortletFileRepositoryUtil.addPortletFileEntry(
			backgroundTask.getGroupId(), userId, BackgroundTask.class.getName(),
			backgroundTask.getPrimaryKey(), PortletKeys.BACKGROUND_TASK,
			folder.getFolderId(), file, fileName, null);
	}

	@Override
	public void addBackgroundTaskAttachment(
			long userId, long backgroundTaskId, String fileName,
			InputStream inputStream)
		throws PortalException, SystemException {

		BackgroundTask backgroundTask = getBackgroundTask(backgroundTaskId);

		Folder folder = backgroundTask.addAttachmentsFolder();

		PortletFileRepositoryUtil.addPortletFileEntry(
			backgroundTask.getGroupId(), userId, BackgroundTask.class.getName(),
			backgroundTask.getPrimaryKey(), PortletKeys.BACKGROUND_TASK,
			folder.getFolderId(), inputStream, fileName, null);
	}

	@Override
	public BackgroundTask deleteBackgroundTask(BackgroundTask backgroundTask)
		throws PortalException, SystemException {

		long folderId = backgroundTask.getAttachmentsFolderId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			PortletFileRepositoryUtil.deleteFolder(folderId);
		}

		return backgroundTaskPersistence.remove(backgroundTask);
	}

	@Override
	public BackgroundTask fetchBackgroundTask(long backgroundTaskId)
		throws SystemException {

		return backgroundTaskPersistence.fetchByPrimaryKey(backgroundTaskId);
	}

	@Override
	public BackgroundTask fetchFirstBackgroundTask(
			String taskExecutorClassName, int status)
		throws SystemException {

		return fetchFirstBackgroundTask(
			taskExecutorClassName, status,
			new BackgroundTaskCreateDateComparator(true));
	}

	@Override
	public BackgroundTask fetchFirstBackgroundTask(
			String taskExecutorClassName, int status,
			OrderByComparator orderByComparator)
		throws SystemException {

		return backgroundTaskPersistence.fetchByT_S_First(
			taskExecutorClassName, status, orderByComparator);
	}

	@Override
	public BackgroundTask getBackgroundTask(long backgroundTaskId)
		throws PortalException, SystemException {

		return backgroundTaskPersistence.findByPrimaryKey(backgroundTaskId);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
			long groupId, String taskExecutorClassName)
		throws SystemException {

		return backgroundTaskPersistence.findByG_T(
			groupId, taskExecutorClassName);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
			long groupId, String taskExecutorClassName, int status)
		throws SystemException {

		return backgroundTaskPersistence.findByG_T_S(
			groupId, taskExecutorClassName, status);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
			String taskExecutorClassName, int status)
		throws SystemException {

		return getBackgroundTasks(
			taskExecutorClassName, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new BackgroundTaskCreateDateComparator(true));
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
			String taskExecutorClassName, int status, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return backgroundTaskPersistence.findByT_S(
			taskExecutorClassName, status, start, end, orderByComparator);
	}

	@Override
	public void resumeBackgroundTask(long backgroundTaskId)
		throws SystemException {

		BackgroundTask backgroundTask = fetchBackgroundTask(backgroundTaskId);

		if ((backgroundTask == null) ||
			(backgroundTask.getStatus() !=
				BackgroundTaskConstants.STATUS_QUEUED)) {

			return;
		}

		Message message = new Message();

		message.put("backgroundTaskId", backgroundTaskId);

		MessageBusUtil.sendMessage(DestinationNames.BACKGROUND_TASK, message);
	}

	@Override
	public BackgroundTask updateBackgroundTask(
			long backgroundTaskId, Map<String, Serializable> taskContextMap,
			int status, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Date now = new Date();

		BackgroundTask backgroundTask =
			backgroundTaskPersistence.findByPrimaryKey(backgroundTaskId);

		backgroundTask.setModifiedDate(serviceContext.getModifiedDate(now));

		if (taskContextMap != null) {
			String taskContext = JSONFactoryUtil.serialize(taskContextMap);

			backgroundTask.setTaskContext(taskContext);
		}

		if ((status == BackgroundTaskConstants.STATUS_FAILED) ||
			(status == BackgroundTaskConstants.STATUS_SUCCESSFUL)) {

			backgroundTask.setCompleted(true);
			backgroundTask.setCompletionDate(now);
		}

		backgroundTask.setStatus(status);

		backgroundTaskPersistence.update(backgroundTask);

		return backgroundTask;
	}

}