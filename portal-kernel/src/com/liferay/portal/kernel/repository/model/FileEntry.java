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

package com.liferay.portal.kernel.repository.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Accessor;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alexander Chow
 */
@JSON
@ProviderType
public interface FileEntry extends RepositoryEntry, RepositoryModel<FileEntry> {

	public static final Accessor<FileEntry, Long> FILE_ENTRY_ID_ACCESSOR =
		new Accessor<FileEntry, Long>() {

			@Override
			public Long get(FileEntry fileEntry) {
				return fileEntry.getFileEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<FileEntry> getTypeClass() {
				return FileEntry.class;
			}

		};

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException;

	@Override
	public long getCompanyId();

	/**
	 * Returns the content stream of the current file version. In a Liferay
	 * repository, this is the latest approved version. In third-party
	 * repositories, the latest content stream may be returned, regardless of
	 * workflow state.
	 *
	 * @return the content stream of the current file version
	 * @see    #getFileVersion()
	 */
	@JSON(include = false)
	public InputStream getContentStream() throws PortalException;

	public InputStream getContentStream(String version) throws PortalException;

	@Override
	public Date getCreateDate();

	public String getDescription();

	public Date getExpirationDate();

	public String getExtension();

	public default String getExternalReferenceCode() {
		return String.valueOf(getFileEntryId());
	}

	public long getFileEntryId();

	public String getFileName();

	public List<FileShortcut> getFileShortcuts();

	/**
	 * Returns the current file version. The workflow state of the latest file
	 * version may affect the file version that is returned. In a Liferay
	 * repository, the latest approved version is returned; the latest version
	 * regardless of workflow state can be retrieved by {@link
	 * #getLatestFileVersion()}. In third-party repositories, these two methods
	 * may function identically.
	 *
	 * @return the current file version
	 */
	public FileVersion getFileVersion() throws PortalException;

	public FileVersion getFileVersion(String version) throws PortalException;

	public List<FileVersion> getFileVersions(int status);

	public int getFileVersionsCount(int status);

	public Folder getFolder();

	public long getFolderId();

	@Override
	public long getGroupId();

	public String getIcon();

	public String getIconCssClass();

	/**
	 * Returns the latest file version. In a Liferay repository, the latest
	 * version is returned, regardless of workflow state. In third-party
	 * repositories, the functionality of this method and {@link
	 * #getFileVersion()} may be identical.
	 *
	 * @return the latest file version
	 */
	public FileVersion getLatestFileVersion() throws PortalException;

	/**
	 * Returns the latest file version, optionally bypassing security checks. In
	 * a Liferay repository, the latest version is returned, regardless of
	 * workflow state. In third-party repositories, the functionality of this
	 * method and {@link #getFileVersion()} may be identical.
	 *
	 * @param  trusted whether to bypass permission checks. In third-party
	 *         repositories, this parameter may be ignored.
	 * @return the latest file version
	 */
	public FileVersion getLatestFileVersion(boolean trusted)
		throws PortalException;

	public Lock getLock();

	public String getMimeType();

	public String getMimeType(String version);

	@Override
	public Date getModifiedDate();

	public long getReadCount();

	public <T extends Capability> T getRepositoryCapability(
		Class<T> capabilityClass);

	public long getRepositoryId();

	public Date getReviewDate();

	public long getSize();

	public String getTitle();

	@Override
	public long getUserId();

	@Override
	public String getUserName();

	@Override
	public String getUserUuid();

	@Override
	public String getUuid();

	public String getVersion();

	public boolean hasLock();

	public boolean isCheckedOut();

	public boolean isDefaultRepository();

	public boolean isInTrash();

	public boolean isInTrashContainer();

	public boolean isManualCheckInRequired();

	public <T extends Capability> boolean isRepositoryCapabilityProvided(
		Class<T> capabilityClass);

	public boolean isSupportsLocking();

	public boolean isSupportsMetadata();

	public boolean isSupportsSocial();

}