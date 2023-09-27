/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class RepositoryProviderUtil {

	public static LocalRepository getFileEntryLocalRepository(long fileEntryId)
		throws PortalException {

		return _repositoryProvider.getFileEntryLocalRepository(fileEntryId);
	}

	public static Repository getFileEntryRepository(long fileEntryId)
		throws PortalException {

		return _repositoryProvider.getFileEntryRepository(fileEntryId);
	}

	public static LocalRepository getFileShortcutLocalRepository(
			long fileShortcutId)
		throws PortalException {

		return _repositoryProvider.getFileShortcutLocalRepository(
			fileShortcutId);
	}

	public static Repository getFileShortcutRepository(long fileShortcutId)
		throws PortalException {

		return _repositoryProvider.getFileShortcutRepository(fileShortcutId);
	}

	public static LocalRepository getFileVersionLocalRepository(
			long fileVersionId)
		throws PortalException {

		return _repositoryProvider.getFileVersionLocalRepository(fileVersionId);
	}

	public static Repository getFileVersionRepository(long fileVersionId)
		throws PortalException {

		return _repositoryProvider.getFileVersionRepository(fileVersionId);
	}

	public static LocalRepository getFolderLocalRepository(long folderId)
		throws PortalException {

		return _repositoryProvider.getFolderLocalRepository(folderId);
	}

	public static Repository getFolderRepository(long folderId)
		throws PortalException {

		return _repositoryProvider.getFolderRepository(folderId);
	}

	public static List<LocalRepository> getGroupLocalRepositories(long groupId)
		throws PortalException {

		return _repositoryProvider.getGroupLocalRepositories(groupId);
	}

	public static List<Repository> getGroupRepositories(long groupId)
		throws PortalException {

		return _repositoryProvider.getGroupRepositories(groupId);
	}

	public static LocalRepository getImageLocalRepository(long imageId)
		throws PortalException {

		return _repositoryProvider.getImageLocalRepository(imageId);
	}

	public static Repository getImageRepository(long imageId)
		throws PortalException {

		return _repositoryProvider.getImageRepository(imageId);
	}

	public static LocalRepository getLocalRepository(long repositoryId)
		throws PortalException {

		return _repositoryProvider.getLocalRepository(repositoryId);
	}

	public static Repository getRepository(long repositoryId)
		throws PortalException {

		return _repositoryProvider.getRepository(repositoryId);
	}

	public static RepositoryProvider getRepositoryProvider() {
		return _repositoryProvider;
	}

	public void setRepositoryProvider(RepositoryProvider repositoryProvider) {
		_repositoryProvider = repositoryProvider;
	}

	private static RepositoryProvider _repositoryProvider;

}