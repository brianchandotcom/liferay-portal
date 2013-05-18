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

package com.liferay.portlet.backgroundtask.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.backgroundtask.model.BTEntry;
import com.liferay.portlet.backgroundtask.service.base.BTEntryLocalServiceBaseImpl;
import com.liferay.portlet.backgroundtask.util.BackgroundTaskConstants;
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
 *
 */
public class BTEntryLocalServiceImpl extends BTEntryLocalServiceBaseImpl {

	public BTEntry addBTEntry(
			long userId, long groupId, Class taskExecutorClass, String name,
			Map<String, Serializable> taskContext,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addBTEntry(
			userId, groupId, taskExecutorClass, StringPool.EMPTY_ARRAY, name,
			taskContext, serviceContext);
	}

	public BTEntry addBTEntry(
			long userId, long groupId, Class taskExecutorClass, String name,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addBTEntry(
			userId, groupId, taskExecutorClass, StringPool.EMPTY_ARRAY, name,
			null, serviceContext);
	}

	public BTEntry addBTEntry(
			long userId, long groupId, Class taskExecutorClass,
			String[] servletContextNames, String name,
			Map<String, Serializable> taskContext,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		final long btEntryId = counterLocalService.increment();

		BTEntry btEntry = btEntryPersistence.create(btEntryId);

		btEntry.setCompanyId(user.getCompanyId());
		btEntry.setCreateDate(serviceContext.getCreateDate(now));
		btEntry.setGroupId(groupId);
		btEntry.setModifiedDate(serviceContext.getModifiedDate(now));
		btEntry.setUserId(userId);
		btEntry.setUserName(user.getFullName());

		// Model attributes

		btEntry.setName(name);

		String servletContextNamesString = StringUtil.merge(
			servletContextNames, StringPool.COMMA);

		if (Validator.isNotNull(servletContextNamesString)) {
			btEntry.setServletContextNames(servletContextNamesString);
		}

		btEntry.setStatus(BackgroundTaskConstants.STATUS_NEW);

		if (taskContext != null) {
			String taskContextString = JSONFactoryUtil.serialize(taskContext);

			btEntry.setTaskContext(taskContextString);
		}

		btEntry.setTaskExecutorClassName(taskExecutorClass.getName());

		btEntryPersistence.update(btEntry);

		TransactionCommitCallbackRegistryUtil.registerCallback(
			new Callable<Void>() {

				public Void call() throws Exception {
					Message message = new Message();
					message.put("btEntryId", btEntryId);

					MessageBusUtil.sendMessage(
						DestinationNames.BACKGROUND_TASK, message);

					return null;
				}

			});

		return btEntry;
	}

	public void addBTEntryAttachment(
			long userId, long btEntryId, String fileName, File file)
		throws PortalException, SystemException {

		BTEntry btEntry = getBTEntry(btEntryId);

		Folder folder = btEntry.addAttachmentsFolder();

		PortletFileRepositoryUtil.addPortletFileEntry(
			btEntry.getGroupId(), userId, BTEntry.class.getName(),
			btEntry.getPrimaryKey(), PortletKeys.BACKGROUND_TASK,
			folder.getFolderId(), file, fileName, null);
	}

	public void addBTEntryAttachment(
			long userId, long btEntryId, String fileName,
			InputStream inputStream)
		throws PortalException, SystemException {

		BTEntry btEntry = getBTEntry(btEntryId);

		Folder folder = btEntry.addAttachmentsFolder();

		PortletFileRepositoryUtil.addPortletFileEntry(
			btEntry.getGroupId(), userId, BTEntry.class.getName(),
			btEntry.getPrimaryKey(), PortletKeys.BACKGROUND_TASK,
			folder.getFolderId(), inputStream, fileName, null);
	}

	@Override
	public BTEntry deleteBTEntry(BTEntry btEntry)
		throws PortalException, SystemException {

		long folderId = btEntry.getAttachmentsFolderId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			PortletFileRepositoryUtil.deleteFolder(folderId);
		}

		return btEntryPersistence.remove(btEntry);
	}

	public BTEntry fetchBTEntry(long btEntryId) throws SystemException {
		return btEntryPersistence.fetchByPrimaryKey(btEntryId);
	}

	public List<BTEntry> getBTEntries(
			long groupId, String taskExecutorClassName)
		throws SystemException {

		return btEntryPersistence.findByG_T(groupId, taskExecutorClassName);
	}

	public List<BTEntry> getBTEntries(
			long groupId, String taskExecutorClassName, int status)
		throws SystemException {

		return btEntryPersistence.findByG_T_S(
			groupId, taskExecutorClassName, status);
	}

	public BTEntry updateBTEntry(
			long btEntryId, int status, Map<String, Serializable> taskContext,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Date now = new Date();

		BTEntry btEntry = btEntryPersistence.findByPrimaryKey(btEntryId);

		if ((status == BackgroundTaskConstants.STATUS_SUCCESSFUL) ||
			(status == BackgroundTaskConstants.STATUS_FAILED)) {

			btEntry.setCompleted(true);

			btEntry.setCompletionDate(now);
		}

		btEntry.setModifiedDate(serviceContext.getModifiedDate(now));
		btEntry.setStatus(status);

		if (taskContext != null) {
			String taskContextString = JSONFactoryUtil.serialize(taskContext);

			btEntry.setTaskContext(taskContextString);
		}

		btEntryPersistence.update(btEntry);

		return btEntry;
	}

	public BTEntry updateBTEntry(
			long btEntryId, int status, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateBTEntry(btEntryId, status, null, serviceContext);
	}

}