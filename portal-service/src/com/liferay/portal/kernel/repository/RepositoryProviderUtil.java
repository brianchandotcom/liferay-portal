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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class RepositoryProviderUtil {

	public static List<LocalRepository> getLocalRepositoriesByGroupId(
			long groupId)
		throws PortalException {

		return getRepositoryProvider().getLocalRepositoriesByGroupId(groupId);
	}

	public static LocalRepository getLocalRepository(long repositoryId)
		throws PortalException {

		return getRepositoryProvider().getLocalRepository(repositoryId);
	}

	public static LocalRepository getLocalRepositoryByFileEntryId(
			long fileEntryId)
		throws PortalException {

		return getRepositoryProvider().getLocalRepositoryByFileEntryId(
			fileEntryId);
	}

	public static LocalRepository getLocalRepositoryByFileShortcutId(
			long fileShortcutId)
		throws PortalException {

		return getRepositoryProvider().getLocalRepositoryByFileShortcutId(
			fileShortcutId);
	}

	public static LocalRepository getLocalRepositoryByFileVersionId(
			long fileVersionId)
		throws PortalException {

		return getRepositoryProvider().getLocalRepositoryByFileVersionId(
			fileVersionId);
	}

	public static LocalRepository getLocalRepositoryByFolderId(long folderId)
		throws PortalException {

		return getRepositoryProvider().getLocalRepositoryByFolderId(folderId);
	}

	public static LocalRepository getLocalRepositoryByImageId(long imageId)
		throws PortalException {

		return getRepositoryProvider().getLocalRepositoryByImageId(imageId);
	}

	public static List<Repository> getRepositoriesByGroupId(long groupId)
		throws PortalException {

		return getRepositoryProvider().getRepositoriesByGroupId(groupId);
	}

	public static Repository getRepository(long repositoryId)
		throws PortalException {

		return getRepositoryProvider().getRepository(repositoryId);
	}

	public static Repository getRepositoryByFileEntryId(long fileEntryId)
		throws PortalException {

		return getRepositoryProvider().getRepositoryByFileEntryId(fileEntryId);
	}

	public static Repository getRepositoryByFileShortcutId(long fileShortcutId)
		throws PortalException {

		return getRepositoryProvider().getRepositoryByFileShortcutId(
			fileShortcutId);
	}

	public static Repository getRepositoryByFileVersionId(long fileVersionId)
		throws PortalException {

		return getRepositoryProvider().getRepositoryByFileVersionId(
			fileVersionId);
	}

	public static Repository getRepositoryByFolderId(long folderId)
		throws PortalException {

		return getRepositoryProvider().getRepositoryByFolderId(folderId);
	}

	public static Repository getRepositoryByImageId(long imageId)
		throws PortalException {

		return getRepositoryProvider().getRepositoryByImageId(imageId);
	}

	public static RepositoryProvider getRepositoryProvider() {
		PortalRuntimePermission.checkGetBeanProperty(RepositoryProvider.class);

		return _repositoryProvider;
	}

	public static void invalidateRepository(long repositoryId) {
		getRepositoryProvider().invalidateRepository(repositoryId);
	}

	public void setRepositoryProvider(RepositoryProvider repositoryProvider) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_repositoryProvider = repositoryProvider;
	}

	private static RepositoryProvider _repositoryProvider;

}