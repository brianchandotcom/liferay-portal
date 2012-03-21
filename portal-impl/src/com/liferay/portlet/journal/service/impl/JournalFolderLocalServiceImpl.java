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
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.FolderNameException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.base.JournalFolderLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The implementation of the journal article folder local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.journal.service.JournalFolderLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Juan Fernández
 * @see com.liferay.portlet.journal.service.base.JournalFolderLocalServiceBaseImpl
 * @see com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil
 */
public class JournalFolderLocalServiceImpl
	extends JournalFolderLocalServiceBaseImpl {

	public JournalFolder addFolder(
			long userId, long groupId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		User user = userPersistence.findByPrimaryKey(userId);
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validate(name);

		long folderId = counterLocalService.increment();

		JournalFolder folder = journalFolderPersistence.create(folderId);

		folder.setUuid(serviceContext.getUuid());
		folder.setGroupId(groupId);
		folder.setCompanyId(user.getCompanyId());
		folder.setUserId(user.getUserId());
		folder.setUserName(user.getFullName());
		folder.setCreateDate(serviceContext.getCreateDate(now));
		folder.setModifiedDate(serviceContext.getModifiedDate(now));
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		journalFolderPersistence.update(folder, false);

		// Resources

		resourceLocalService.addModelResources(folder, serviceContext);

		return folder;
	}

	public void deleteFolder(JournalFolder folder)
		throws PortalException, SystemException {

		// Folders

		List<JournalFolder> folders = journalFolderPersistence.findByG_P(
			folder.getGroupId(), folder.getFolderId());

		for (JournalFolder curFolder : folders) {
			deleteFolder(curFolder);
		}

		// Folder

		journalFolderPersistence.remove(folder);

		// Resources

		resourceLocalService.deleteResource(
			folder, ResourceConstants.SCOPE_INDIVIDUAL);

		// Entries

		journalArticleLocalService.deleteArticles(
			folder.getGroupId(), folder.getFolderId());

		// Expando

		expandoValueLocalService.deleteValues(
			JournalFolder.class.getName(), folder.getFolderId());
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderPersistence.findByPrimaryKey(
			folderId);

		deleteFolder(folder);
	}

	public void deleteFolders(long groupId)
		throws PortalException, SystemException {

		List<JournalFolder> folders = journalFolderPersistence.findByG_P(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (JournalFolder folder : folders) {
			deleteFolder(folder);
		}
	}

	public List<JournalFolder> getCompanyFolders(
			long companyId, int start, int end)
		throws SystemException {

		return journalFolderPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId) throws SystemException {
		return journalFolderPersistence.countByCompanyId(companyId);
	}

	public JournalFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return journalFolderPersistence.findByPrimaryKey(folderId);
	}

	public List<JournalFolder> getFolders(long groupId)
		throws SystemException {

		return journalFolderPersistence.findByGroupId(groupId);
	}

	public List<JournalFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return journalFolderPersistence.findByG_P(groupId, parentFolderId);
	}

	public List<JournalFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return journalFolderPersistence.findByG_P(
			groupId, parentFolderId, start, end);
	}

	public List<Object> getFoldersAndArticles(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalFolderFinder.findF_JAByG_F(
			groupId, folderId, start, end, obc);
	}

	public int getFoldersAndArticlesCount(long groupId, long folderId)
		throws SystemException {

		return journalFolderFinder.countF_JA_ByG_F(groupId, folderId);
	}

	public int getFoldersArticlesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		if (folderIds.size() <= PropsValues.SQL_DATA_MAX_PARAMETERS) {
			return journalArticleFinder.countByG_F_S(
				groupId, folderIds, status);
		}
		else {
			int start = 0;
			int end = PropsValues.SQL_DATA_MAX_PARAMETERS;

			int articlesCount = journalArticleFinder.countByG_F_S(
				groupId, folderIds.subList(start, end), status);

			folderIds.subList(start, end).clear();

			articlesCount += getFoldersArticlesCount(
				groupId, folderIds, status);

			return articlesCount;
		}
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return journalFolderPersistence.countByG_P(groupId, parentFolderId);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<JournalFolder> folders = journalFolderPersistence.findByG_P(
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

		// Merge folders

		JournalFolder folder = journalFolderPersistence.findByPrimaryKey(
			folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		if (mergeWithParentFolder && (folderId != parentFolderId)) {
			mergeFolders(folder, parentFolderId);

			return folder;
		}

		// Folder

		validate(name);

		folder.setModifiedDate(serviceContext.getModifiedDate(null));
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		journalFolderPersistence.update(folder, false);

		return folder;
	}

	protected long getParentFolderId(JournalFolder folder, long parentFolderId)
		throws SystemException {

		if (parentFolderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return parentFolderId;
		}

		if (folder.getFolderId() == parentFolderId) {
			return folder.getParentFolderId();
		}
		else {
			JournalFolder parentFolder =
				journalFolderPersistence.fetchByPrimaryKey(parentFolderId);

			if ((parentFolder == null) ||
				(folder.getGroupId() != parentFolder.getGroupId())) {

				return folder.getParentFolderId();
			}

			List<Long> subfolderIds = new ArrayList<Long>();

			getSubfolderIds(
				subfolderIds, folder.getGroupId(), folder.getFolderId());

			if (subfolderIds.contains(parentFolderId)) {
				return folder.getParentFolderId();
			}

			return parentFolderId;
		}
	}

	protected long getParentFolderId(long groupId, long parentFolderId)
		throws SystemException {

		if (parentFolderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			JournalFolder parentFolder =
				journalFolderPersistence.fetchByPrimaryKey(parentFolderId);

			if ((parentFolder == null) ||
				(groupId != parentFolder.getGroupId())) {

				parentFolderId =
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected void mergeFolders(JournalFolder fromFolder, long toFolderId)
		throws PortalException, SystemException {

		List<JournalFolder> folders = journalFolderPersistence.findByG_P(
			fromFolder.getGroupId(), fromFolder.getFolderId());

		for (JournalFolder folder : folders) {
			mergeFolders(folder, toFolderId);
		}

		List<JournalArticle> articles = journalArticlePersistence.findByG_F(
			fromFolder.getGroupId(), fromFolder.getFolderId());

		for (JournalArticle article : articles) {
			article.setFolderId(toFolderId);

			journalArticlePersistence.update(article, false);

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				JournalArticle.class);

			indexer.reindex(article);
		}

		deleteFolder(fromFolder);
	}

	protected void validate(String name) throws PortalException {
		if ((Validator.isNull(name)) || (name.indexOf("\\\\") != -1) ||
			(name.indexOf("//") != -1)) {

			throw new FolderNameException();
		}
	}

}