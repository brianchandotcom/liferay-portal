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
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.permission.PermissionChecker;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Chow
 */
public interface FileEntry extends RepositoryModel<FileEntry> {

	public static final Accessor<FileEntry, Long> FILE_ENTRY_ID_ACCESSOR =

		new Accessor<FileEntry, Long>() {

			public Long get(FileEntry fileEntry) {
				return fileEntry.getFileEntryId();
			}

		};

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException, SystemException;

	@Override
	public long getCompanyId();

	/**
	 * Retrieves the content stream of the current file version. In a Liferay
	 * repository, this is the latest approved version. In third-party
	 * repositories, this may be the latest content regardless of workflow
	 * state.
	 *
	 * @return content stream of the current file version
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 * @see    #getFileVersion()
	 */
	public InputStream getContentStream()
		throws PortalException, SystemException;

	public InputStream getContentStream(String version)
		throws PortalException, SystemException;

	@Override
	public Date getCreateDate();

	public String getDescription();

	public String getExtension();

	public long getFileEntryId();

	/**
	 * Retrieves the current file version. The workflow state of the latest file
	 * version may affect what is returned by this method. In a Liferay
	 * repository, this will return the latest approved version; the latest
	 * version regardless of workflow state can be retrieved by {@link
	 * #getLatestFileVersion()}. In third-party repositories, these two methods
	 * may function identically.
	 *
	 * @return current file version
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public FileVersion getFileVersion() throws PortalException, SystemException;

	public FileVersion getFileVersion(String version)
		throws PortalException, SystemException;

	public List<FileVersion> getFileVersions(int status)
		throws SystemException;

	public Folder getFolder();

	public long getFolderId();

	@Override
	public long getGroupId();

	public String getIcon();

	/**
	 * Retrieves the latest file version. In a Liferay repository, this means
	 * the latest version regardless of workflow state. In third-party
	 * repositories, this may have an identical functionality with {@link
	 * #getFileVersion()}.
	 *
	 * @return latest file version
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public FileVersion getLatestFileVersion()
		throws PortalException, SystemException;

	public Lock getLock();

	public String getMimeType();

	public String getMimeType(String version);

	@Override
	public Date getModifiedDate();

	public int getReadCount();

	public long getRepositoryId();

	public long getSize();

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

	public long getVersionUserId();

	public String getVersionUserName();

	public String getVersionUserUuid() throws SystemException;

	public boolean hasLock();

	public boolean isCheckedOut();

	public boolean isDefaultRepository();

	public boolean isManualCheckInRequired();

	public boolean isSupportsLocking();

	public boolean isSupportsMetadata();

	public boolean isSupportsSocial();

}