/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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