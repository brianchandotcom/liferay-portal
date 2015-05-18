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

/**
 * @author Iván Zaera
 */
public interface RepositoryProvider {

	public List<LocalRepository> getLocalRepositoriesByGroupId(long groupId)
		throws PortalException;

	public LocalRepository getLocalRepository(long repositoryId)
		throws PortalException;

	public LocalRepository getLocalRepositoryByFileEntryId(long fileEntryId)
		throws PortalException;

	public LocalRepository getLocalRepositoryByFileShortcutId(
			long fileShortcutId)
		throws PortalException;

	public LocalRepository getLocalRepositoryByFileVersionId(long fileVersionId)
		throws PortalException;

	public LocalRepository getLocalRepositoryByFolderId(long folderId)
		throws PortalException;

	public LocalRepository getLocalRepositoryByImageId(long imageId)
		throws PortalException;

	public List<Repository> getRepositoriesByGroupId(long groupId)
		throws PortalException;

	public Repository getRepository(long repositoryId) throws PortalException;

	public Repository getRepositoryByFileEntryId(long fileEntryId)
		throws PortalException;

	public Repository getRepositoryByFileShortcutId(
		long fileShortcutId) throws PortalException;

	public Repository getRepositoryByFileVersionId(
		long fileVersionId) throws PortalException;

	public Repository getRepositoryByFolderId(long folderId)
		throws PortalException;

	public Repository getRepositoryByImageId(long imageId)
		throws PortalException;

	public void invalidateRepository(long repositoryId);

}