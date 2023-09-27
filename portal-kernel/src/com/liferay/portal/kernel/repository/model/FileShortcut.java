/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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