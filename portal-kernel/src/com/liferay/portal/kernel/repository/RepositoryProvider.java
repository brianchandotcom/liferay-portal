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

package com.liferay.portal.kernel.repository;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Iván Zaera
 */
@ProviderType
public interface RepositoryProvider {

	public LocalRepository fetchFileEntryLocalRepository(long fileEntryId)
		throws PortalException;

	public LocalRepository getFileEntryLocalRepository(long fileEntryId)
		throws PortalException;

	public Repository getFileEntryRepository(long fileEntryId)
		throws PortalException;

	public LocalRepository getFileShortcutLocalRepository(long fileShortcutId)
		throws PortalException;

	public Repository getFileShortcutRepository(long fileShortcutId)
		throws PortalException;

	public LocalRepository getFileVersionLocalRepository(long fileVersionId)
		throws PortalException;

	public Repository getFileVersionRepository(long fileVersionId)
		throws PortalException;

	public LocalRepository getFolderLocalRepository(long folderId)
		throws PortalException;

	public Repository getFolderRepository(long folderId) throws PortalException;

	public List<LocalRepository> getGroupLocalRepositories(long groupId)
		throws PortalException;

	public List<Repository> getGroupRepositories(long groupId)
		throws PortalException;

	public LocalRepository getImageLocalRepository(long imageId)
		throws PortalException;

	public Repository getImageRepository(long imageId) throws PortalException;

	public LocalRepository getLocalRepository(long repositoryId)
		throws PortalException;

	public Repository getRepository(long repositoryId) throws PortalException;

}