/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.base.JournalFolderServiceBaseImpl;
import com.liferay.portlet.journal.service.permission.JournalFolderPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Fernández
 */
public class JournalFolderServiceImpl extends JournalFolderServiceBaseImpl {

	@Override
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

	@Override
	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderLocalService.getFolder(folderId);

		JournalFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.DELETE);

		journalFolderLocalService.deleteFolder(folderId);
	}

	@Override
	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderLocalService.getFolder(folderId);

		JournalFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.DELETE);

		journalFolderLocalService.deleteFolder(folderId, includeTrashedEntries);
	}

	@Override
	public JournalFolder getFolder(long folderId)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderLocalService.getFolder(folderId);

		JournalFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.VIEW);

		return folder;
	}

	@Override
	public List<Long> getFolderIds(long groupId, long folderId)
			throws PortalException, SystemException {

		JournalFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		List<Long> folderIds = getSubfolderIds(groupId, folderId, true);

		folderIds.add(0, folderId);

		return folderIds;
	}

	@Override
	public List<JournalFolder> getFolders(long groupId) throws SystemException {
		return journalFolderPersistence.filterFindByGroupId(groupId);
	}

	@Override
	public List<JournalFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return getFolders(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public List<JournalFolder> getFolders(
			long groupId, long parentFolderId, int status)
		throws SystemException {

		return journalFolderPersistence.filterFindByG_P_S(
			groupId, parentFolderId, status);
	}

	@Override
	public List<JournalFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return getFolders(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED, start,
			end);
	}

	@Override
	public List<JournalFolder> getFolders(
			long groupId, long parentFolderId, int status, int start, int end)
		throws SystemException {

		return journalFolderPersistence.filterFindByG_P_S(
			groupId, parentFolderId, status, start, end);
	}

	@Override
	public List<Object> getFoldersAndArticles(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY, start, end, obc);

		return journalFolderFinder.filterFindF_A_ByG_F(
			groupId, folderId, queryDefinition);
	}

	@Override
	public int getFoldersAndArticlesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		if (folderIds.size() <= PropsValues.SQL_DATA_MAX_PARAMETERS) {
			return journalArticleFinder.filterCountByG_F(
				groupId, folderIds, queryDefinition);
		}
		else {
			int start = 0;
			int end = PropsValues.SQL_DATA_MAX_PARAMETERS;

			int articlesCount = journalArticleFinder.filterCountByG_F(
				groupId, folderIds.subList(start, end), queryDefinition);

			folderIds.subList(start, end).clear();

			articlesCount += getFoldersAndArticlesCount(
				groupId, folderIds, status);

			return articlesCount;
		}
	}

	@Override
	public int getFoldersAndArticlesCount(long groupId, long folderId)
		throws SystemException {

		return getFoldersAndArticlesCount(
			groupId, folderId, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public int getFoldersAndArticlesCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return journalFolderFinder.filterCountF_A_ByG_F(
			groupId, folderId, new QueryDefinition(status));
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return journalFolderPersistence.filterCountByG_P_S(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<JournalFolder> folders = journalFolderPersistence.filterFindByG_P(
			groupId, folderId);

		for (JournalFolder folder : folders) {
			if (folder.isInTrash() || folder.isInTrashContainer()) {
				continue;
			}

			folderIds.add(folder.getFolderId());

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId());
		}
	}

	@Override
	public List<Long> getSubfolderIds(
			long groupId, long folderId, boolean recurse)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		getSubfolderIds(folderIds, groupId, folderId);

		return folderIds;
	}

	@Override
	public JournalFolder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderLocalService.getFolder(folderId);

		JournalFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.UPDATE);

		return journalFolderLocalService.moveFolder(
			folderId, parentFolderId, serviceContext);
	}

	@Override
	public JournalFolder moveFolderFromTrash(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderLocalService.getFolder(folderId);

		JournalFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.UPDATE);

		return journalFolderLocalService.moveFolderFromTrash(
			getUserId(), folderId, parentFolderId, serviceContext);
	}

	@Override
	public void moveFolderToTrash(long folderId)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderLocalService.getFolder(folderId);

		JournalFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.DELETE);

		journalFolderLocalService.moveFolderToTrash(getUserId(), folderId);
	}

	@Override
	public void restoreFolderFromTrash(long folderId)
			throws PortalException, SystemException {

		JournalFolder folder = journalFolderLocalService.getFolder(folderId);

		JournalFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.UPDATE);

		journalFolderLocalService.restoreFolderFromTrash(getUserId(), folderId);
	}

	@Override
	public JournalFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			boolean mergeWithParentFolder, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderLocalService.getFolder(folderId);

		JournalFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.UPDATE);

		return journalFolderLocalService.updateFolder(
			getUserId(), folderId, parentFolderId, name, description,
			mergeWithParentFolder, serviceContext);
	}

}