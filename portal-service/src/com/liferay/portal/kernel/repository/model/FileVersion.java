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

package com.liferay.portal.kernel.repository.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.InputStream;

import java.util.Date;

/**
 * @author Alexander Chow
 */
public interface FileVersion extends RepositoryModel<FileVersion> {

	public String getChangeLog();

	@Override
	public long getCompanyId();

	public InputStream getContentStream(boolean incrementCounter)
		throws PortalException, SystemException;

	@Override
	public Date getCreateDate();

	public String getDescription();

	@Override
	public ExpandoBridge getExpandoBridge();

	public String getExtension();

	public String getExtraSettings();

	public FileEntry getFileEntry() throws PortalException, SystemException;

	public long getFileEntryId();

	public long getFileVersionId();

	@Override
	public long getGroupId();

	public String getIcon();

	public String getMimeType();

	public long getRepositoryId();

	public long getSize();

	public int getStatus();

	public long getStatusByUserId();

	public String getStatusByUserName();

	public String getStatusByUserUuid() throws SystemException;

	public Date getStatusDate();

	public String getTitle();

	@Override
	public long getUserId();

	@Override
	public String getUserName();

	@Override
	public String getUserUuid() throws SystemException;

	@Override
	public String getUuid();

	public String getVersion();

	public boolean isApproved();

	public boolean isDefaultRepository();

	public boolean isDraft();

	public boolean isExpired();

	public boolean isInTrash();

	public boolean isInTrashContainer();

	public boolean isPending();

}