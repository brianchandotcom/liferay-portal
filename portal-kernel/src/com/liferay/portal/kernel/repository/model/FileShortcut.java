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
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
@ProviderType
public interface FileShortcut
	extends RepositoryEntry, RepositoryModel<FileShortcut> {

	public static final Accessor<FileShortcut, Long> FILE_SHORTCUT_ID_ACCESSOR =
		new Accessor<FileShortcut, Long>() {

			@Override
			public Long get(FileShortcut fileShortcut) {
				return fileShortcut.getFileShortcutId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<FileShortcut> getTypeClass() {
				return FileShortcut.class;
			}

		};

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException;

	public long getFileShortcutId();

	public FileVersion getFileVersion() throws PortalException;

	public Folder getFolder() throws PortalException;

	public long getFolderId();

	public long getRepositoryId();

	public long getToFileEntryId();

	public String getToTitle();

}