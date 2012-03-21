/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.base.JournalFolderServiceBaseImpl;
import com.liferay.portlet.journal.service.permission.JournalFolderPermission;

import java.util.List;

/**
 * The implementation of the journal article folder remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.journal.service.JournalFolderService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Juan Fernández
 * @see com.liferay.portlet.journal.service.base.JournalFolderServiceBaseImpl
 * @see com.liferay.portlet.journal.service.JournalFolderServiceUtil
 */
public class JournalFolderServiceImpl
	extends JournalFolderServiceBaseImpl {

	public JournalFolder addFolder(
			long groupId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalFolderPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			parentFolderId, ActionKeys.ADD_FOLDER);

		return journalFolderLocalService.addFolder(
			getUserId(), groupId, parentFolderId, name, description,
			serviceContext);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderLocalService.getFolder(folderId);

		JournalFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.DELETE);

		journalFolderLocalService.deleteFolder(folderId);
	}

	public JournalFolder getFolder(long folderId)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderLocalService.getFolder(folderId);

		JournalFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.VIEW);

		return folder;
	}

	public List<JournalFolder> getFolders(long groupId)
		throws SystemException {

		return journalFolderPersistence.filterFindByGroupId(groupId);
	}

	public List<JournalFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return journalFolderPersistence.filterFindByG_P(
			groupId, parentFolderId);
	}

	public List<JournalFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return journalFolderPersistence.filterFindByG_P(
			groupId, parentFolderId, start, end);
	}

	public List<Object> getFoldersAndArticles(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalFolderFinder.filterFindF_JAByG_F(
			groupId, folderId, start, end, obc);
	}

	public int getFoldersAndArticlesCount(long groupId, long folderId)
		throws SystemException {

		return journalFolderFinder.filterCountF_JA_ByG_F(groupId, folderId);
	}

	public int getFoldersArticlesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		if (folderIds.size() <= PropsValues.SQL_DATA_MAX_PARAMETERS) {
			return journalArticleFinder.filterCountByG_F_S(
				groupId, folderIds, status);
		}
		else {
			int start = 0;
			int end = PropsValues.SQL_DATA_MAX_PARAMETERS;

			int articlesCount = journalArticleFinder.filterCountByG_F_S(
				groupId, folderIds.subList(start, end), status);

			folderIds.subList(start, end).clear();

			articlesCount += getFoldersArticlesCount(
				groupId, folderIds, status);

			return articlesCount;
		}
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return journalFolderPersistence.filterCountByG_P(
			groupId, parentFolderId);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<JournalFolder> folders = journalFolderPersistence.filterFindByG_P(
			groupId, folderId);

		for (JournalFolder folder : folders) {
			folderIds.add(folder.getFolderId());

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId());
		}
	}

	public JournalFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			boolean mergeWithParentFolder, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderLocalService.getFolder(folderId);

		JournalFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.UPDATE);

		return journalFolderLocalService.updateFolder(
			folderId, parentFolderId, name, description, mergeWithParentFolder,
			serviceContext);
	}

}