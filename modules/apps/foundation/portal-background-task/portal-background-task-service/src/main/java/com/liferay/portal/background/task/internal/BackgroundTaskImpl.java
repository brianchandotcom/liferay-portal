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

package com.liferay.portal.background.task.internal;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskImpl implements BackgroundTask {

	public BackgroundTaskImpl(
		com.liferay.portal.background.task.model.BackgroundTask
			backgroundTask) {

		_backgroundTask = backgroundTask;
	}

	@Override
	public void addAttachment(long userId, String fileName, File file)
		throws PortalException {

		_backgroundTask.addAttachment(userId, fileName, file);
	}

	@Override
	public void addAttachment(
			long userId, String fileName, InputStream inputStream)
		throws PortalException {

		_backgroundTask.addAttachment(userId, fileName, inputStream);
	}

	@Override
	public Folder addAttachmentsFolder() throws PortalException {
		return _backgroundTask.addAttachmentsFolder();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BackgroundTaskImpl)) {
			return false;
		}

		BackgroundTaskImpl backgroundTaskImpl = (BackgroundTaskImpl)obj;

		return _backgroundTask.equals(backgroundTaskImpl._backgroundTask);
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries() throws PortalException {
		return _backgroundTask.getAttachmentsFileEntries();
	}

	@Override
	public int getAttachmentsFileEntriesCount() throws PortalException {
		return _backgroundTask.getAttachmentsFileEntriesCount();
	}

	@Override
	public long getAttachmentsFolderId() {
		return _backgroundTask.getAttachmentsFolderId();
	}

	@Override
	public long getBackgroundTaskId() {
		return _backgroundTask.getBackgroundTaskId();
	}

	@Override
	public long getCompanyId() {
		return _backgroundTask.getCompanyId();
	}

	@Override
	public Date getCompletionDate() {
		return _backgroundTask.getCompletionDate();
	}

	@Override
	public Date getCreateDate() {
		return _backgroundTask.getCreateDate();
	}

	@Override
	public long getGroupId() {
		return _backgroundTask.getGroupId();
	}

	@Override
	public com.liferay.portal.background.task.model.BackgroundTask getModel() {
		return _backgroundTask;
	}

	@Override
	public String getName() {
		return _backgroundTask.getName();
	}

	@Override
	public String getServletContextNames() {
		return _backgroundTask.getServletContextNames();
	}

	@Override
	public int getStatus() {
		return _backgroundTask.getStatus();
	}

	@Override
	public String getStatusLabel() {
		return _backgroundTask.getStatusLabel();
	}

	@Override
	public String getStatusMessage() {
		return _backgroundTask.getStatusMessage();
	}

	@Override
	public Map<String, Serializable> getTaskContextMap() {
		return _backgroundTask.getTaskContextMap();
	}

	@Override
	public String getTaskExecutorClassName() {
		return _backgroundTask.getTaskExecutorClassName();
	}

	@Override
	public long getUserId() {
		return _backgroundTask.getUserId();
	}

	@Override
	public int hashCode() {
		return _backgroundTask.hashCode();
	}

	@Override
	public boolean isCompleted() {
		return _backgroundTask.isCompleted();
	}

	@Override
	public boolean isInProgress() {
		return _backgroundTask.isInProgress();
	}

	@Override
	public void setTaskContextMap(Map<String, Serializable> taskContextMap) {
		_backgroundTask.setTaskContextMap(taskContextMap);
	}

	@Override
	public String toString() {
		return _backgroundTask.toString();
	}

	private final com.liferay.portal.background.task.model.BackgroundTask
		_backgroundTask;

}