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
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.journal.DuplicateFolderNameException;
import com.liferay.portlet.journal.FolderNameException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.base.JournalFolderLocalServiceBaseImpl;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Juan Fernández
 */
public class JournalFolderLocalServiceImpl
	extends JournalFolderLocalServiceBaseImpl {

	@Override
	public JournalFolder addFolder(
			long userId, long groupId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		User user = userPersistence.findByPrimaryKey(userId);
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validateFolder(0, groupId, parentFolderId, name);

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

		journalFolderPersistence.update(folder);

		// Resources

		resourceLocalService.addModelResources(folder, serviceContext);

		// Asset

		updateAsset(
			userId, folder, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		return folder;
	}

	@Override
	@Indexable(type = IndexableType.DELETE)
	public JournalFolder deleteFolder(JournalFolder folder)
		throws PortalException, SystemException {

		return deleteFolder(folder, true);
	}

	@Override
	@Indexable(type = IndexableType.DELETE)
	public JournalFolder deleteFolder(
			JournalFolder folder, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		// Folders

		List<JournalFolder> folders = journalFolderPersistence.findByG_P(
			folder.getGroupId(), folder.getFolderId());

		for (JournalFolder curFolder : folders) {
			if (includeTrashedEntries || !curFolder.isInTrash()) {
				deleteFolder(curFolder);
			}
		}

		// Folder

		journalFolderPersistence.remove(folder);

		// Resources

		resourceLocalService.deleteResource(
			folder, ResourceConstants.SCOPE_INDIVIDUAL);

		// Entries

		journalArticleLocalService.deleteArticles(
			folder.getGroupId(), folder.getFolderId(), includeTrashedEntries);

		// Asset

		assetEntryLocalService.deleteEntry(
			JournalFolder.class.getName(), folder.getFolderId());

		// Expando

		expandoValueLocalService.deleteValues(
			JournalFolder.class.getName(), folder.getFolderId());

		// Trash

		trashEntryLocalService.deleteEntry(
			JournalFolder.class.getName(), folder.getFolderId());

		return folder;
	}

	@Override
	@Indexable(type = IndexableType.DELETE)
	public JournalFolder deleteFolder(long folderId)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderPersistence.findByPrimaryKey(
			folderId);

		return deleteFolder(folder, true);
	}

	@Override
	@Indexable(type = IndexableType.DELETE)
	public JournalFolder deleteFolder(
			long folderId, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderPersistence.findByPrimaryKey(
			folderId);

		return deleteFolder(folder, includeTrashedEntries);
	}

	@Override
	public void deleteFolders(long groupId)
		throws PortalException, SystemException {

		List<JournalFolder> folders = journalFolderPersistence.findByG_P(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (JournalFolder folder : folders) {
			journalFolderLocalService.deleteFolder(folder);
		}
	}

	@Override
	public JournalFolder fetchFolder(
			long groupId, long parentFolderId, String name)
		throws SystemException {

		return journalFolderPersistence.fetchByG_P_N(
				groupId, parentFolderId, name);
	}

	@Override
	public JournalFolder fetchFolder(long groupId, String name)
		throws SystemException {

		return journalFolderPersistence.fetchByG_N(groupId, name);
	}

	@Override
	public List<JournalFolder> getCompanyFolders(
			long companyId, int start, int end)
		throws SystemException {

		return journalFolderPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public int getCompanyFoldersCount(long companyId) throws SystemException {
		return journalFolderPersistence.countByCompanyId(companyId);
	}

	@Override
	public JournalFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return journalFolderPersistence.findByPrimaryKey(folderId);
	}

	@Override
	public List<JournalFolder> getFolders(long groupId) throws SystemException {
		return journalFolderPersistence.findByGroupId(groupId);
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

		return journalFolderPersistence.findByG_P_S(
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

		return journalFolderPersistence.findByG_P_S(
				groupId, parentFolderId, status, start, end);
	}

	@Override
	public List<Object> getFoldersAndArticles(long groupId, long folderId)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY);

		return journalFolderFinder.findF_A_ByG_F(
			groupId, folderId, queryDefinition);
	}

	@Override
	public List<Object> getFoldersAndArticles(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY, start, end, obc);

		return journalFolderFinder.findF_A_ByG_F(
			groupId, folderId, queryDefinition);
	}

	@Override
	public int getFoldersAndArticlesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		if (folderIds.size() <= PropsValues.SQL_DATA_MAX_PARAMETERS) {
			return journalArticleFinder.countByG_F(
				groupId, folderIds, queryDefinition);
		}
		else {
			int start = 0;
			int end = PropsValues.SQL_DATA_MAX_PARAMETERS;

			int articlesCount = journalArticleFinder.countByG_F(
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

		return journalFolderFinder.countF_A_ByG_F(
			groupId, folderId,
			new QueryDefinition(WorkflowConstants.STATUS_ANY));
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return getFoldersCount(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId, int status)
		throws SystemException {

		return journalFolderPersistence.countByG_P_S(
			groupId, parentFolderId, status);
	}

	@Override
	public List<JournalFolder> getNoAssetFolders() throws SystemException {
		return journalFolderFinder.findF_ByNoAssets();
	}

	@Override
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

	@Override
	@Indexable(type = IndexableType.REINDEX)
	public JournalFolder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderPersistence.findByPrimaryKey(
			folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		validateFolder(
			folder.getFolderId(), folder.getGroupId(), parentFolderId,
			folder.getName());

		folder.setModifiedDate(serviceContext.getModifiedDate(null));
		folder.setParentFolderId(parentFolderId);
		folder.setExpandoBridgeAttributes(serviceContext);

		journalFolderPersistence.update(folder);

		return folder;
	}

	@Override
	public JournalFolder moveFolderFromTrash(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderPersistence.findByPrimaryKey(
			folderId);

		if (folder.isInTrash()) {
			restoreFolderFromTrash(userId, folderId);
		}
		else {
			updateStatus(userId, folder, WorkflowConstants.STATUS_APPROVED);
		}

		return journalFolderLocalService.moveFolder(
			folderId, parentFolderId, serviceContext);
	}

	@Override
	public void moveFolderToTrash(long userId, long folderId)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderPersistence.findByPrimaryKey(
			folderId);

		folder = updateStatus(
			userId, folder, WorkflowConstants.STATUS_IN_TRASH);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			JournalFolder.class.getName(), folder.getFolderId());

		String trashTitle = TrashUtil.getTrashTitle(trashEntry.getEntryId());

		folder.setName(trashTitle);

		journalFolderPersistence.update(folder);

		// Social

		socialActivityCounterLocalService.disableActivityCounters(
			JournalFolder.class.getName(), folder.getFolderId());

		socialActivityLocalService.addActivity(
			userId, folder.getGroupId(), JournalFolder.class.getName(),
			folder.getFolderId(), SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			StringPool.BLANK, 0);
	}

	@Override
	public void restoreFolderFromTrash(long userId, long folderId)
		throws PortalException, SystemException {

		JournalFolder folder = journalFolderPersistence.findByPrimaryKey(
			folderId);

		folder.setName(TrashUtil.getOriginalTitle(folder.getName()));

		journalFolderPersistence.update(folder);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			JournalFolder.class.getName(), folderId);

		updateStatus(userId, folder, trashEntry.getStatus());

		// Social

		socialActivityCounterLocalService.enableActivityCounters(
			JournalFolder.class.getName(), folder.getFolderId());

		socialActivityLocalService.addActivity(
			userId, folder.getGroupId(), JournalFolder.class.getName(),
			folder.getFolderId(),
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH, StringPool.BLANK,
			0);
	}

	@Override
	public void updateAsset(
			long userId, JournalFolder folder, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException, SystemException {

		AssetEntry assetEntry = assetEntryLocalService.updateEntry(
			userId, folder.getGroupId(), folder.getCreateDate(),
			folder.getModifiedDate(), JournalFolder.class.getName(),
			folder.getFolderId(), folder.getUuid(), 0, assetCategoryIds,
			assetTagNames, true, null, null, null, ContentTypes.TEXT_PLAIN,
			folder.getName(), folder.getDescription(), null, null, null, 0, 0,
			null, false);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	@Override
	@Indexable(type = IndexableType.REINDEX)
	public JournalFolder updateFolder(
			long userId, long folderId, long parentFolderId, String name,
			String description, boolean mergeWithParentFolder,
			ServiceContext serviceContext)
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

		validateFolder(folderId, folder.getGroupId(), parentFolderId, name);

		folder.setModifiedDate(serviceContext.getModifiedDate(null));
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		journalFolderPersistence.update(folder);

		// Asset

		updateAsset(
			userId, folder, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		return folder;
	}

	@Override
	public JournalFolder updateStatus(
			long userId, JournalFolder folder, int status)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		int oldStatus = folder.getStatus();

		folder.setStatus(status);
		folder.setStatusByUserId(userId);
		folder.setStatusByUserName(user.getFullName());
		folder.setStatusDate(new Date());

		journalFolderPersistence.update(folder);

		// Folders and entries

		List<Object> foldersAndEntries =
			journalFolderLocalService.getFoldersAndArticles(
				folder.getGroupId(), folder.getFolderId());

		updateDependentStatus(foldersAndEntries, status);

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// Asset

			assetEntryLocalService.updateVisible(
				JournalFolder.class.getName(), folder.getFolderId(), true);

			// Social

			socialActivityCounterLocalService.enableActivityCounters(
				JournalFolder.class.getName(), folder.getFolderId());
		}
		else if (status == WorkflowConstants.STATUS_IN_TRASH) {

			// Asset

			assetEntryLocalService.updateVisible(
				JournalFolder.class.getName(), folder.getFolderId(), false);

			// Social

			socialActivityCounterLocalService.disableActivityCounters(
				JournalFolder.class.getName(), folder.getFolderId());
		}

		// Trash

		if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
			trashEntryLocalService.deleteEntry(
				JournalFolder.class.getName(), folder.getFolderId());
		}
		else if (status == WorkflowConstants.STATUS_IN_TRASH) {
			UnicodeProperties typeSettingsProperties = new UnicodeProperties();

			typeSettingsProperties.put("title", folder.getName());

			trashEntryLocalService.addTrashEntry(
				userId, folder.getGroupId(), JournalFolder.class.getName(),
				folder.getFolderId(), oldStatus, null, typeSettingsProperties);
		}

		// Index

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			JournalFolder.class);

		indexer.reindex(folder);

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

			journalArticlePersistence.update(article);

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				JournalArticle.class);

			indexer.reindex(article);
		}

		journalFolderLocalService.deleteFolder(fromFolder);
	}

	protected void updateDependentStatus(
			List<Object> foldersAndEntries, int status)
		throws PortalException, SystemException {

		for (Object object : foldersAndEntries) {
			if (object instanceof JournalArticle) {
				JournalArticle article = (JournalArticle)object;

				if (status == WorkflowConstants.STATUS_IN_TRASH) {

					// Asset

					if (article.getStatus() ==
							WorkflowConstants.STATUS_APPROVED) {

						assetEntryLocalService.updateVisible(
							JournalArticle.class.getName(),
							article.getResourcePrimKey(), false);
					}

					// Social

					socialActivityCounterLocalService.disableActivityCounters(
						JournalArticle.class.getName(),
						article.getResourcePrimKey());

					if (article.getStatus() ==
							WorkflowConstants.STATUS_PENDING) {

						article.setStatus(WorkflowConstants.STATUS_DRAFT);

						journalArticlePersistence.update(article);
					}
				}
				else {

					// Asset

					if (article.getStatus() ==
							WorkflowConstants.STATUS_APPROVED) {

						assetEntryLocalService.updateVisible(
							JournalArticle.class.getName(),
							article.getResourcePrimKey(), true);
					}

					// Social

					socialActivityCounterLocalService.enableActivityCounters(
						JournalArticle.class.getName(),
						article.getResourcePrimKey());
				}

				// Workflow

				if (status != WorkflowConstants.STATUS_APPROVED) {
					List<JournalArticle> articleVersions =
						journalArticlePersistence.findByG_A(
							article.getGroupId(), article.getArticleId());

					for (JournalArticle curArticle : articleVersions) {
						if (!curArticle.isPending()) {
							continue;
						}

						curArticle.setStatus(WorkflowConstants.STATUS_DRAFT);

						journalArticlePersistence.update(curArticle);

						workflowInstanceLinkLocalService.
							deleteWorkflowInstanceLink(
								curArticle.getCompanyId(),
								curArticle.getGroupId(),
								JournalArticle.class.getName(),
								curArticle.getId());
					}
				}

				// Indexer

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					JournalArticle.class);

				indexer.reindex(article);
			}
			else if (object instanceof JournalFolder) {
				JournalFolder folder = (JournalFolder)object;

				if (folder.isInTrash()) {
					continue;
				}

				// Folders and articles

				List<Object> curFoldersAndEntries = getFoldersAndArticles(
					folder.getGroupId(), folder.getFolderId());

				updateDependentStatus(curFoldersAndEntries, status);

				if (status == WorkflowConstants.STATUS_IN_TRASH) {

					// Asset

					assetEntryLocalService.updateVisible(
						JournalFolder.class.getName(), folder.getFolderId(),
						false);

					// Social

					socialActivityCounterLocalService.disableActivityCounters(
						JournalFolder.class.getName(), folder.getFolderId());
				}
				else {

					// Asset

					assetEntryLocalService.updateVisible(
						JournalFolder.class.getName(), folder.getFolderId(),
						true);

					// Social

					socialActivityCounterLocalService.enableActivityCounters(
						JournalFolder.class.getName(), folder.getFolderId());
				}

				// Index

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					JournalFolder.class);

				indexer.reindex(folder);
			}
		}
	}

	protected void validateFolder(
			long folderId, long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		validateFolderName(name);

		JournalFolder folder = journalFolderPersistence.fetchByG_P_N(
			groupId, parentFolderId, name);

		if ((folder != null) && (folder.getFolderId() != folderId)) {
			throw new DuplicateFolderNameException(name);
		}
	}

	protected void validateFolderName(String name) throws PortalException {
		if (!AssetUtil.isValidWord(name)) {
			throw new FolderNameException();
		}

		if (name.contains("\\\\") || name.contains("//")) {
			throw new FolderNameException();
		}
	}

}