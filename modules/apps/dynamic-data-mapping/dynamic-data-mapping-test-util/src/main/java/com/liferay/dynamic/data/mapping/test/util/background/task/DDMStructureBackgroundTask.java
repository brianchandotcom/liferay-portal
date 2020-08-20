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

package com.liferay.dynamic.data.mapping.test.util.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Lucas Marques de Paula
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public class DDMStructureBackgroundTask implements BackgroundTask {

	public DDMStructureBackgroundTask(long structureId) {
		setTaskContextMap(Collections.singletonMap("structureId", structureId));
	}

	@Override
	public void addAttachment(long userId, String fileName, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void addAttachment(
			long userId, String fileName, InputStream inputStream)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Folder addAttachmentsFolder() throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries() throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getAttachmentsFileEntriesCount() throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getAttachmentsFolderId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getBackgroundTaskId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getCompanyId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getCompletionDate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getCreateDate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getGroupId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public BaseModel<?> getModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getServletContextNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getStatus() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getStatusLabel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getStatusMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Serializable> getTaskContextMap() {
		return _taskContextMap;
	}

	@Override
	public String getTaskExecutorClassName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getUserId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCompleted() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isInProgress() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTaskContextMap(Map<String, Serializable> taskContextMap) {
		_taskContextMap = taskContextMap;
	}

	private Map<String, Serializable> _taskContextMap;

}