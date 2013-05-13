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
import com.liferay.portal.security.permission.PermissionChecker;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Chow
 */
public interface Folder extends RepositoryModel<Folder> {

	public static final Accessor<Folder, Long> FOLDER_ID_ACCESSOR =

		new Accessor<Folder, Long>() {

			public Long get(Folder folder) {
				return folder.getFolderId();
			}

		};

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException, SystemException;

	public List<Long> getAncestorFolderIds()
		throws PortalException, SystemException;

	public List<Folder> getAncestors()
		throws PortalException, SystemException;

	@Override
	public long getCompanyId();

	@Override
	public Date getCreateDate();

	public String getDescription();

	public long getFolderId();

	@Override
	public long getGroupId();

	public Date getLastPostDate();

	@Override
	public Date getModifiedDate();

	public String getName();

	public Folder getParentFolder() throws PortalException, SystemException;

	public long getParentFolderId();

	public long getRepositoryId();

	@Override
	public long getUserId();

	@Override
	public String getUserName();

	@Override
	public String getUserUuid() throws SystemException;

	@Override
	public String getUuid();

	public boolean hasInheritableLock();

	public boolean hasLock();

	public boolean isDefaultRepository();

	public boolean isLocked();

	public boolean isMountPoint();

	public boolean isRoot();

	public boolean isSupportsLocking();

	public boolean isSupportsMetadata();

	public boolean isSupportsMultipleUpload();

	public boolean isSupportsShortcuts();

	public boolean isSupportsSocial();

	public boolean isSupportsSubscribing();

}