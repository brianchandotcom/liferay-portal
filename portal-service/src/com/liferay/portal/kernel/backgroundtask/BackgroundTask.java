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
import com.liferay.portal.kernel.model.BaseModel;
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
public interface BackgroundTask {

	public void addAttachment(long userId, String fileName, File file)
		throws PortalException;

	public void addAttachment(
			long userId, String fileName, InputStream inputStream)
		throws PortalException;

	public Folder addAttachmentsFolder() throws PortalException;

	public List<FileEntry> getAttachmentsFileEntries() throws PortalException;

	public int getAttachmentsFileEntriesCount() throws PortalException;

	public long getAttachmentsFolderId();

	public long getBackgroundTaskId();

	public long getCompanyId();

	public Date getCompletionDate();

	public Date getCreateDate();

	public long getGroupId();

	public BaseModel<?> getModel();

	public String getName();

	public String getServletContextNames();

	public int getStatus();

	public String getStatusLabel();

	public String getStatusMessage();

	public Map<String, Serializable> getTaskContextMap();

	public String getTaskExecutorClassName();

	public long getUserId();

	public boolean isCompleted();

	public boolean isInProgress();

	public void setTaskContextMap(Map<String, Serializable> taskContextMap);

}