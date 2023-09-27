/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Accessor;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Chow
 */
public interface Folder extends RepositoryEntry, RepositoryModel<Folder> {

	public static final Accessor<Folder, Long> FOLDER_ID_ACCESSOR =
		new Accessor<Folder, Long>() {

			@Override
			public Long get(Folder folder) {
				return folder.getFolderId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Folder> getTypeClass() {
				return Folder.class;
			}

		};

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException;

	public List<Long> getAncestorFolderIds() throws PortalException;

	public List<Folder> getAncestors() throws PortalException;

	@Override
	public long getCompanyId();

	@Override
	public Date getCreateDate();

	public String getDescription();

	public default String getExternalReferenceCode() {
		return getUuid();
	}

	public long getFolderId();

	@Override
	public long getGroupId();

	public Date getLastPostDate();

	@Override
	public Date getModifiedDate();

	public String getName();

	public Folder getParentFolder() throws PortalException;

	public long getParentFolderId();

	public default <T extends Capability> T getRepositoryCapability(
		Class<T> capabilityClass) {

		throw new IllegalArgumentException(
			String.format(
				"Capability %s is not exported by repository %d",
				capabilityClass.getName(), getRepositoryId()));
	}

	public long getRepositoryId();

	@Override
	public long getUserId();

	@Override
	public String getUserName();

	@Override
	public String getUserUuid();

	@Override
	public String getUuid();

	public boolean hasInheritableLock();

	public boolean hasLock();

	public boolean isDefaultRepository();

	public boolean isLocked();

	public boolean isMountPoint();

	public default <T extends Capability> boolean
		isRepositoryCapabilityProvided(Class<T> capabilityClass) {

		return false;
	}

	public boolean isRoot();

	public boolean isSupportsLocking();

	public boolean isSupportsMetadata();

	public boolean isSupportsMultipleUpload();

	public boolean isSupportsShortcuts();

	public boolean isSupportsSocial();

	public boolean isSupportsSubscribing();

}