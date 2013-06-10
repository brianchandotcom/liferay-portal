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

package com.liferay.portlet.asset.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.SystemEventLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.SystemEventPersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetCategoryLocalService;
import com.liferay.portlet.asset.service.AssetCategoryPropertyLocalService;
import com.liferay.portlet.asset.service.AssetCategoryPropertyService;
import com.liferay.portlet.asset.service.AssetCategoryService;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetEntryService;
import com.liferay.portlet.asset.service.AssetLinkLocalService;
import com.liferay.portlet.asset.service.AssetTagLocalService;
import com.liferay.portlet.asset.service.AssetTagPropertyLocalService;
import com.liferay.portlet.asset.service.AssetTagPropertyService;
import com.liferay.portlet.asset.service.AssetTagService;
import com.liferay.portlet.asset.service.AssetTagStatsLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyService;
import com.liferay.portlet.asset.service.persistence.AssetCategoryFinder;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyFinder;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence;
import com.liferay.portlet.asset.service.persistence.AssetEntryFinder;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetLinkFinder;
import com.liferay.portlet.asset.service.persistence.AssetLinkPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagFinder;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagPropertyFinder;
import com.liferay.portlet.asset.service.persistence.AssetTagPropertyKeyFinder;
import com.liferay.portlet.asset.service.persistence.AssetTagPropertyPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagStatsPersistence;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyFinder;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.blogs.service.BlogsEntryService;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryFinder;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.portlet.bookmarks.service.BookmarksEntryService;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryFinder;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalService;
import com.liferay.portlet.journal.service.JournalArticleService;
import com.liferay.portlet.journal.service.persistence.JournalArticleFinder;
import com.liferay.portlet.journal.service.persistence.JournalArticlePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.social.service.SocialActivityCounterLocalService;
import com.liferay.portlet.social.service.SocialActivityLocalService;
import com.liferay.portlet.social.service.persistence.SocialActivityCounterFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityCounterPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.wiki.service.WikiPageLocalService;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalService;
import com.liferay.portlet.wiki.service.WikiPageService;
import com.liferay.portlet.wiki.service.persistence.WikiPageFinder;
import com.liferay.portlet.wiki.service.persistence.WikiPagePersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the asset entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.asset.service.impl.AssetEntryLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.asset.service.impl.AssetEntryLocalServiceImpl
 * @see com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil
 * @generated
 */
public abstract class AssetEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements AssetEntryLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil} to access the asset entry local service.
	 */

	/**
	 * Adds the asset entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntry the asset entry
	 * @return the asset entry that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetEntry addAssetEntry(AssetEntry assetEntry)
		throws SystemException {
		assetEntry.setNew(true);

		return assetEntryPersistence.update(assetEntry);
	}

	/**
	 * Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	 *
	 * @param entryId the primary key for the new asset entry
	 * @return the new asset entry
	 */
	@Override
	public AssetEntry createAssetEntry(long entryId) {
		return assetEntryPersistence.create(entryId);
	}

	/**
	 * Deletes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the asset entry
	 * @return the asset entry that was removed
	 * @throws PortalException if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AssetEntry deleteAssetEntry(long entryId)
		throws PortalException, SystemException {
		return assetEntryPersistence.remove(entryId);
	}

	/**
	 * Deletes the asset entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntry the asset entry
	 * @return the asset entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AssetEntry deleteAssetEntry(AssetEntry assetEntry)
		throws SystemException {
		return assetEntryPersistence.remove(assetEntry);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(AssetEntry.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return assetEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return assetEntryPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return assetEntryPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return assetEntryPersistence.countWithDynamicQuery(dynamicQuery);
	}

	@Override
	public AssetEntry fetchAssetEntry(long entryId) throws SystemException {
		return assetEntryPersistence.fetchByPrimaryKey(entryId);
	}

	/**
	 * Returns the asset entry with the primary key.
	 *
	 * @param entryId the primary key of the asset entry
	 * @return the asset entry
	 * @throws PortalException if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetEntry getAssetEntry(long entryId)
		throws PortalException, SystemException {
		return assetEntryPersistence.findByPrimaryKey(entryId);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return assetEntryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the asset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of asset entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetEntry> getAssetEntries(int start, int end)
		throws SystemException {
		return assetEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of asset entries.
	 *
	 * @return the number of asset entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getAssetEntriesCount() throws SystemException {
		return assetEntryPersistence.countAll();
	}

	/**
	 * Updates the asset entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntry the asset entry
	 * @return the asset entry that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetEntry updateAssetEntry(AssetEntry assetEntry)
		throws SystemException {
		return assetEntryPersistence.update(assetEntry);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addAssetCategoryAssetEntry(long categoryId, long entryId)
		throws SystemException {
		assetCategoryPersistence.addAssetEntry(categoryId, entryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addAssetCategoryAssetEntry(long categoryId,
		AssetEntry assetEntry) throws SystemException {
		assetCategoryPersistence.addAssetEntry(categoryId, assetEntry);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addAssetCategoryAssetEntries(long categoryId, long[] entryIds)
		throws SystemException {
		assetCategoryPersistence.addAssetEntries(categoryId, entryIds);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addAssetCategoryAssetEntries(long categoryId,
		List<AssetEntry> AssetEntries) throws SystemException {
		assetCategoryPersistence.addAssetEntries(categoryId, AssetEntries);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void clearAssetCategoryAssetEntries(long categoryId)
		throws SystemException {
		assetCategoryPersistence.clearAssetEntries(categoryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteAssetCategoryAssetEntry(long categoryId, long entryId)
		throws SystemException {
		assetCategoryPersistence.removeAssetEntry(categoryId, entryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteAssetCategoryAssetEntry(long categoryId,
		AssetEntry assetEntry) throws SystemException {
		assetCategoryPersistence.removeAssetEntry(categoryId, assetEntry);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteAssetCategoryAssetEntries(long categoryId, long[] entryIds)
		throws SystemException {
		assetCategoryPersistence.removeAssetEntries(categoryId, entryIds);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteAssetCategoryAssetEntries(long categoryId,
		List<AssetEntry> AssetEntries) throws SystemException {
		assetCategoryPersistence.removeAssetEntries(categoryId, AssetEntries);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetEntry> getAssetCategoryAssetEntries(long categoryId)
		throws SystemException {
		return assetCategoryPersistence.getAssetEntries(categoryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetEntry> getAssetCategoryAssetEntries(long categoryId,
		int start, int end) throws SystemException {
		return assetCategoryPersistence.getAssetEntries(categoryId, start, end);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetEntry> getAssetCategoryAssetEntries(long categoryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return assetCategoryPersistence.getAssetEntries(categoryId, start, end,
			orderByComparator);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getAssetCategoryAssetEntriesCount(long categoryId)
		throws SystemException {
		return assetCategoryPersistence.getAssetEntriesSize(categoryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean hasAssetCategoryAssetEntry(long categoryId, long entryId)
		throws SystemException {
		return assetCategoryPersistence.containsAssetEntry(categoryId, entryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean hasAssetCategoryAssetEntries(long categoryId)
		throws SystemException {
		return assetCategoryPersistence.containsAssetEntries(categoryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setAssetCategoryAssetEntries(long categoryId, long[] entryIds)
		throws SystemException {
		assetCategoryPersistence.setAssetEntries(categoryId, entryIds);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addAssetTagAssetEntry(long tagId, long entryId)
		throws SystemException {
		assetTagPersistence.addAssetEntry(tagId, entryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addAssetTagAssetEntry(long tagId, AssetEntry assetEntry)
		throws SystemException {
		assetTagPersistence.addAssetEntry(tagId, assetEntry);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addAssetTagAssetEntries(long tagId, long[] entryIds)
		throws SystemException {
		assetTagPersistence.addAssetEntries(tagId, entryIds);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addAssetTagAssetEntries(long tagId,
		List<AssetEntry> AssetEntries) throws SystemException {
		assetTagPersistence.addAssetEntries(tagId, AssetEntries);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void clearAssetTagAssetEntries(long tagId) throws SystemException {
		assetTagPersistence.clearAssetEntries(tagId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteAssetTagAssetEntry(long tagId, long entryId)
		throws SystemException {
		assetTagPersistence.removeAssetEntry(tagId, entryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteAssetTagAssetEntry(long tagId, AssetEntry assetEntry)
		throws SystemException {
		assetTagPersistence.removeAssetEntry(tagId, assetEntry);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteAssetTagAssetEntries(long tagId, long[] entryIds)
		throws SystemException {
		assetTagPersistence.removeAssetEntries(tagId, entryIds);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteAssetTagAssetEntries(long tagId,
		List<AssetEntry> AssetEntries) throws SystemException {
		assetTagPersistence.removeAssetEntries(tagId, AssetEntries);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetEntry> getAssetTagAssetEntries(long tagId)
		throws SystemException {
		return assetTagPersistence.getAssetEntries(tagId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetEntry> getAssetTagAssetEntries(long tagId, int start,
		int end) throws SystemException {
		return assetTagPersistence.getAssetEntries(tagId, start, end);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetEntry> getAssetTagAssetEntries(long tagId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		return assetTagPersistence.getAssetEntries(tagId, start, end,
			orderByComparator);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getAssetTagAssetEntriesCount(long tagId)
		throws SystemException {
		return assetTagPersistence.getAssetEntriesSize(tagId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean hasAssetTagAssetEntry(long tagId, long entryId)
		throws SystemException {
		return assetTagPersistence.containsAssetEntry(tagId, entryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean hasAssetTagAssetEntries(long tagId)
		throws SystemException {
		return assetTagPersistence.containsAssetEntries(tagId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setAssetTagAssetEntries(long tagId, long[] entryIds)
		throws SystemException {
		assetTagPersistence.setAssetEntries(tagId, entryIds);
	}

	/**
	 * Returns the asset category local service.
	 *
	 * @return the asset category local service
	 */
	public AssetCategoryLocalService getAssetCategoryLocalService() {
		return assetCategoryLocalService;
	}

	/**
	 * Sets the asset category local service.
	 *
	 * @param assetCategoryLocalService the asset category local service
	 */
	public void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {
		this.assetCategoryLocalService = assetCategoryLocalService;
	}

	/**
	 * Returns the asset category remote service.
	 *
	 * @return the asset category remote service
	 */
	public AssetCategoryService getAssetCategoryService() {
		return assetCategoryService;
	}

	/**
	 * Sets the asset category remote service.
	 *
	 * @param assetCategoryService the asset category remote service
	 */
	public void setAssetCategoryService(
		AssetCategoryService assetCategoryService) {
		this.assetCategoryService = assetCategoryService;
	}

	/**
	 * Returns the asset category persistence.
	 *
	 * @return the asset category persistence
	 */
	public AssetCategoryPersistence getAssetCategoryPersistence() {
		return assetCategoryPersistence;
	}

	/**
	 * Sets the asset category persistence.
	 *
	 * @param assetCategoryPersistence the asset category persistence
	 */
	public void setAssetCategoryPersistence(
		AssetCategoryPersistence assetCategoryPersistence) {
		this.assetCategoryPersistence = assetCategoryPersistence;
	}

	/**
	 * Returns the asset category finder.
	 *
	 * @return the asset category finder
	 */
	public AssetCategoryFinder getAssetCategoryFinder() {
		return assetCategoryFinder;
	}

	/**
	 * Sets the asset category finder.
	 *
	 * @param assetCategoryFinder the asset category finder
	 */
	public void setAssetCategoryFinder(AssetCategoryFinder assetCategoryFinder) {
		this.assetCategoryFinder = assetCategoryFinder;
	}

	/**
	 * Returns the asset category property local service.
	 *
	 * @return the asset category property local service
	 */
	public AssetCategoryPropertyLocalService getAssetCategoryPropertyLocalService() {
		return assetCategoryPropertyLocalService;
	}

	/**
	 * Sets the asset category property local service.
	 *
	 * @param assetCategoryPropertyLocalService the asset category property local service
	 */
	public void setAssetCategoryPropertyLocalService(
		AssetCategoryPropertyLocalService assetCategoryPropertyLocalService) {
		this.assetCategoryPropertyLocalService = assetCategoryPropertyLocalService;
	}

	/**
	 * Returns the asset category property remote service.
	 *
	 * @return the asset category property remote service
	 */
	public AssetCategoryPropertyService getAssetCategoryPropertyService() {
		return assetCategoryPropertyService;
	}

	/**
	 * Sets the asset category property remote service.
	 *
	 * @param assetCategoryPropertyService the asset category property remote service
	 */
	public void setAssetCategoryPropertyService(
		AssetCategoryPropertyService assetCategoryPropertyService) {
		this.assetCategoryPropertyService = assetCategoryPropertyService;
	}

	/**
	 * Returns the asset category property persistence.
	 *
	 * @return the asset category property persistence
	 */
	public AssetCategoryPropertyPersistence getAssetCategoryPropertyPersistence() {
		return assetCategoryPropertyPersistence;
	}

	/**
	 * Sets the asset category property persistence.
	 *
	 * @param assetCategoryPropertyPersistence the asset category property persistence
	 */
	public void setAssetCategoryPropertyPersistence(
		AssetCategoryPropertyPersistence assetCategoryPropertyPersistence) {
		this.assetCategoryPropertyPersistence = assetCategoryPropertyPersistence;
	}

	/**
	 * Returns the asset category property finder.
	 *
	 * @return the asset category property finder
	 */
	public AssetCategoryPropertyFinder getAssetCategoryPropertyFinder() {
		return assetCategoryPropertyFinder;
	}

	/**
	 * Sets the asset category property finder.
	 *
	 * @param assetCategoryPropertyFinder the asset category property finder
	 */
	public void setAssetCategoryPropertyFinder(
		AssetCategoryPropertyFinder assetCategoryPropertyFinder) {
		this.assetCategoryPropertyFinder = assetCategoryPropertyFinder;
	}

	/**
	 * Returns the asset entry local service.
	 *
	 * @return the asset entry local service
	 */
	public AssetEntryLocalService getAssetEntryLocalService() {
		return assetEntryLocalService;
	}

	/**
	 * Sets the asset entry local service.
	 *
	 * @param assetEntryLocalService the asset entry local service
	 */
	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {
		this.assetEntryLocalService = assetEntryLocalService;
	}

	/**
	 * Returns the asset entry remote service.
	 *
	 * @return the asset entry remote service
	 */
	public AssetEntryService getAssetEntryService() {
		return assetEntryService;
	}

	/**
	 * Sets the asset entry remote service.
	 *
	 * @param assetEntryService the asset entry remote service
	 */
	public void setAssetEntryService(AssetEntryService assetEntryService) {
		this.assetEntryService = assetEntryService;
	}

	/**
	 * Returns the asset entry persistence.
	 *
	 * @return the asset entry persistence
	 */
	public AssetEntryPersistence getAssetEntryPersistence() {
		return assetEntryPersistence;
	}

	/**
	 * Sets the asset entry persistence.
	 *
	 * @param assetEntryPersistence the asset entry persistence
	 */
	public void setAssetEntryPersistence(
		AssetEntryPersistence assetEntryPersistence) {
		this.assetEntryPersistence = assetEntryPersistence;
	}

	/**
	 * Returns the asset entry finder.
	 *
	 * @return the asset entry finder
	 */
	public AssetEntryFinder getAssetEntryFinder() {
		return assetEntryFinder;
	}

	/**
	 * Sets the asset entry finder.
	 *
	 * @param assetEntryFinder the asset entry finder
	 */
	public void setAssetEntryFinder(AssetEntryFinder assetEntryFinder) {
		this.assetEntryFinder = assetEntryFinder;
	}

	/**
	 * Returns the asset link local service.
	 *
	 * @return the asset link local service
	 */
	public AssetLinkLocalService getAssetLinkLocalService() {
		return assetLinkLocalService;
	}

	/**
	 * Sets the asset link local service.
	 *
	 * @param assetLinkLocalService the asset link local service
	 */
	public void setAssetLinkLocalService(
		AssetLinkLocalService assetLinkLocalService) {
		this.assetLinkLocalService = assetLinkLocalService;
	}

	/**
	 * Returns the asset link persistence.
	 *
	 * @return the asset link persistence
	 */
	public AssetLinkPersistence getAssetLinkPersistence() {
		return assetLinkPersistence;
	}

	/**
	 * Sets the asset link persistence.
	 *
	 * @param assetLinkPersistence the asset link persistence
	 */
	public void setAssetLinkPersistence(
		AssetLinkPersistence assetLinkPersistence) {
		this.assetLinkPersistence = assetLinkPersistence;
	}

	/**
	 * Returns the asset link finder.
	 *
	 * @return the asset link finder
	 */
	public AssetLinkFinder getAssetLinkFinder() {
		return assetLinkFinder;
	}

	/**
	 * Sets the asset link finder.
	 *
	 * @param assetLinkFinder the asset link finder
	 */
	public void setAssetLinkFinder(AssetLinkFinder assetLinkFinder) {
		this.assetLinkFinder = assetLinkFinder;
	}

	/**
	 * Returns the asset tag local service.
	 *
	 * @return the asset tag local service
	 */
	public AssetTagLocalService getAssetTagLocalService() {
		return assetTagLocalService;
	}

	/**
	 * Sets the asset tag local service.
	 *
	 * @param assetTagLocalService the asset tag local service
	 */
	public void setAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {
		this.assetTagLocalService = assetTagLocalService;
	}

	/**
	 * Returns the asset tag remote service.
	 *
	 * @return the asset tag remote service
	 */
	public AssetTagService getAssetTagService() {
		return assetTagService;
	}

	/**
	 * Sets the asset tag remote service.
	 *
	 * @param assetTagService the asset tag remote service
	 */
	public void setAssetTagService(AssetTagService assetTagService) {
		this.assetTagService = assetTagService;
	}

	/**
	 * Returns the asset tag persistence.
	 *
	 * @return the asset tag persistence
	 */
	public AssetTagPersistence getAssetTagPersistence() {
		return assetTagPersistence;
	}

	/**
	 * Sets the asset tag persistence.
	 *
	 * @param assetTagPersistence the asset tag persistence
	 */
	public void setAssetTagPersistence(AssetTagPersistence assetTagPersistence) {
		this.assetTagPersistence = assetTagPersistence;
	}

	/**
	 * Returns the asset tag finder.
	 *
	 * @return the asset tag finder
	 */
	public AssetTagFinder getAssetTagFinder() {
		return assetTagFinder;
	}

	/**
	 * Sets the asset tag finder.
	 *
	 * @param assetTagFinder the asset tag finder
	 */
	public void setAssetTagFinder(AssetTagFinder assetTagFinder) {
		this.assetTagFinder = assetTagFinder;
	}

	/**
	 * Returns the asset tag property local service.
	 *
	 * @return the asset tag property local service
	 */
	public AssetTagPropertyLocalService getAssetTagPropertyLocalService() {
		return assetTagPropertyLocalService;
	}

	/**
	 * Sets the asset tag property local service.
	 *
	 * @param assetTagPropertyLocalService the asset tag property local service
	 */
	public void setAssetTagPropertyLocalService(
		AssetTagPropertyLocalService assetTagPropertyLocalService) {
		this.assetTagPropertyLocalService = assetTagPropertyLocalService;
	}

	/**
	 * Returns the asset tag property remote service.
	 *
	 * @return the asset tag property remote service
	 */
	public AssetTagPropertyService getAssetTagPropertyService() {
		return assetTagPropertyService;
	}

	/**
	 * Sets the asset tag property remote service.
	 *
	 * @param assetTagPropertyService the asset tag property remote service
	 */
	public void setAssetTagPropertyService(
		AssetTagPropertyService assetTagPropertyService) {
		this.assetTagPropertyService = assetTagPropertyService;
	}

	/**
	 * Returns the asset tag property persistence.
	 *
	 * @return the asset tag property persistence
	 */
	public AssetTagPropertyPersistence getAssetTagPropertyPersistence() {
		return assetTagPropertyPersistence;
	}

	/**
	 * Sets the asset tag property persistence.
	 *
	 * @param assetTagPropertyPersistence the asset tag property persistence
	 */
	public void setAssetTagPropertyPersistence(
		AssetTagPropertyPersistence assetTagPropertyPersistence) {
		this.assetTagPropertyPersistence = assetTagPropertyPersistence;
	}

	/**
	 * Returns the asset tag property finder.
	 *
	 * @return the asset tag property finder
	 */
	public AssetTagPropertyFinder getAssetTagPropertyFinder() {
		return assetTagPropertyFinder;
	}

	/**
	 * Sets the asset tag property finder.
	 *
	 * @param assetTagPropertyFinder the asset tag property finder
	 */
	public void setAssetTagPropertyFinder(
		AssetTagPropertyFinder assetTagPropertyFinder) {
		this.assetTagPropertyFinder = assetTagPropertyFinder;
	}

	/**
	 * Returns the asset tag property key finder.
	 *
	 * @return the asset tag property key finder
	 */
	public AssetTagPropertyKeyFinder getAssetTagPropertyKeyFinder() {
		return assetTagPropertyKeyFinder;
	}

	/**
	 * Sets the asset tag property key finder.
	 *
	 * @param assetTagPropertyKeyFinder the asset tag property key finder
	 */
	public void setAssetTagPropertyKeyFinder(
		AssetTagPropertyKeyFinder assetTagPropertyKeyFinder) {
		this.assetTagPropertyKeyFinder = assetTagPropertyKeyFinder;
	}

	/**
	 * Returns the asset tag stats local service.
	 *
	 * @return the asset tag stats local service
	 */
	public AssetTagStatsLocalService getAssetTagStatsLocalService() {
		return assetTagStatsLocalService;
	}

	/**
	 * Sets the asset tag stats local service.
	 *
	 * @param assetTagStatsLocalService the asset tag stats local service
	 */
	public void setAssetTagStatsLocalService(
		AssetTagStatsLocalService assetTagStatsLocalService) {
		this.assetTagStatsLocalService = assetTagStatsLocalService;
	}

	/**
	 * Returns the asset tag stats persistence.
	 *
	 * @return the asset tag stats persistence
	 */
	public AssetTagStatsPersistence getAssetTagStatsPersistence() {
		return assetTagStatsPersistence;
	}

	/**
	 * Sets the asset tag stats persistence.
	 *
	 * @param assetTagStatsPersistence the asset tag stats persistence
	 */
	public void setAssetTagStatsPersistence(
		AssetTagStatsPersistence assetTagStatsPersistence) {
		this.assetTagStatsPersistence = assetTagStatsPersistence;
	}

	/**
	 * Returns the asset vocabulary local service.
	 *
	 * @return the asset vocabulary local service
	 */
	public AssetVocabularyLocalService getAssetVocabularyLocalService() {
		return assetVocabularyLocalService;
	}

	/**
	 * Sets the asset vocabulary local service.
	 *
	 * @param assetVocabularyLocalService the asset vocabulary local service
	 */
	public void setAssetVocabularyLocalService(
		AssetVocabularyLocalService assetVocabularyLocalService) {
		this.assetVocabularyLocalService = assetVocabularyLocalService;
	}

	/**
	 * Returns the asset vocabulary remote service.
	 *
	 * @return the asset vocabulary remote service
	 */
	public AssetVocabularyService getAssetVocabularyService() {
		return assetVocabularyService;
	}

	/**
	 * Sets the asset vocabulary remote service.
	 *
	 * @param assetVocabularyService the asset vocabulary remote service
	 */
	public void setAssetVocabularyService(
		AssetVocabularyService assetVocabularyService) {
		this.assetVocabularyService = assetVocabularyService;
	}

	/**
	 * Returns the asset vocabulary persistence.
	 *
	 * @return the asset vocabulary persistence
	 */
	public AssetVocabularyPersistence getAssetVocabularyPersistence() {
		return assetVocabularyPersistence;
	}

	/**
	 * Sets the asset vocabulary persistence.
	 *
	 * @param assetVocabularyPersistence the asset vocabulary persistence
	 */
	public void setAssetVocabularyPersistence(
		AssetVocabularyPersistence assetVocabularyPersistence) {
		this.assetVocabularyPersistence = assetVocabularyPersistence;
	}

	/**
	 * Returns the asset vocabulary finder.
	 *
	 * @return the asset vocabulary finder
	 */
	public AssetVocabularyFinder getAssetVocabularyFinder() {
		return assetVocabularyFinder;
	}

	/**
	 * Sets the asset vocabulary finder.
	 *
	 * @param assetVocabularyFinder the asset vocabulary finder
	 */
	public void setAssetVocabularyFinder(
		AssetVocabularyFinder assetVocabularyFinder) {
		this.assetVocabularyFinder = assetVocabularyFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the company local service.
	 *
	 * @return the company local service
	 */
	public CompanyLocalService getCompanyLocalService() {
		return companyLocalService;
	}

	/**
	 * Sets the company local service.
	 *
	 * @param companyLocalService the company local service
	 */
	public void setCompanyLocalService(CompanyLocalService companyLocalService) {
		this.companyLocalService = companyLocalService;
	}

	/**
	 * Returns the company remote service.
	 *
	 * @return the company remote service
	 */
	public CompanyService getCompanyService() {
		return companyService;
	}

	/**
	 * Sets the company remote service.
	 *
	 * @param companyService the company remote service
	 */
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	/**
	 * Returns the company persistence.
	 *
	 * @return the company persistence
	 */
	public CompanyPersistence getCompanyPersistence() {
		return companyPersistence;
	}

	/**
	 * Sets the company persistence.
	 *
	 * @param companyPersistence the company persistence
	 */
	public void setCompanyPersistence(CompanyPersistence companyPersistence) {
		this.companyPersistence = companyPersistence;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group remote service.
	 *
	 * @return the group remote service
	 */
	public GroupService getGroupService() {
		return groupService;
	}

	/**
	 * Sets the group remote service.
	 *
	 * @param groupService the group remote service
	 */
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * Returns the group persistence.
	 *
	 * @return the group persistence
	 */
	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	/**
	 * Sets the group persistence.
	 *
	 * @param groupPersistence the group persistence
	 */
	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	/**
	 * Returns the group finder.
	 *
	 * @return the group finder
	 */
	public GroupFinder getGroupFinder() {
		return groupFinder;
	}

	/**
	 * Sets the group finder.
	 *
	 * @param groupFinder the group finder
	 */
	public void setGroupFinder(GroupFinder groupFinder) {
		this.groupFinder = groupFinder;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the system event local service.
	 *
	 * @return the system event local service
	 */
	public SystemEventLocalService getSystemEventLocalService() {
		return systemEventLocalService;
	}

	/**
	 * Sets the system event local service.
	 *
	 * @param systemEventLocalService the system event local service
	 */
	public void setSystemEventLocalService(
		SystemEventLocalService systemEventLocalService) {
		this.systemEventLocalService = systemEventLocalService;
	}

	/**
	 * Returns the system event persistence.
	 *
	 * @return the system event persistence
	 */
	public SystemEventPersistence getSystemEventPersistence() {
		return systemEventPersistence;
	}

	/**
	 * Sets the system event persistence.
	 *
	 * @param systemEventPersistence the system event persistence
	 */
	public void setSystemEventPersistence(
		SystemEventPersistence systemEventPersistence) {
		this.systemEventPersistence = systemEventPersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	/**
	 * Returns the blogs entry local service.
	 *
	 * @return the blogs entry local service
	 */
	public BlogsEntryLocalService getBlogsEntryLocalService() {
		return blogsEntryLocalService;
	}

	/**
	 * Sets the blogs entry local service.
	 *
	 * @param blogsEntryLocalService the blogs entry local service
	 */
	public void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {
		this.blogsEntryLocalService = blogsEntryLocalService;
	}

	/**
	 * Returns the blogs entry remote service.
	 *
	 * @return the blogs entry remote service
	 */
	public BlogsEntryService getBlogsEntryService() {
		return blogsEntryService;
	}

	/**
	 * Sets the blogs entry remote service.
	 *
	 * @param blogsEntryService the blogs entry remote service
	 */
	public void setBlogsEntryService(BlogsEntryService blogsEntryService) {
		this.blogsEntryService = blogsEntryService;
	}

	/**
	 * Returns the blogs entry persistence.
	 *
	 * @return the blogs entry persistence
	 */
	public BlogsEntryPersistence getBlogsEntryPersistence() {
		return blogsEntryPersistence;
	}

	/**
	 * Sets the blogs entry persistence.
	 *
	 * @param blogsEntryPersistence the blogs entry persistence
	 */
	public void setBlogsEntryPersistence(
		BlogsEntryPersistence blogsEntryPersistence) {
		this.blogsEntryPersistence = blogsEntryPersistence;
	}

	/**
	 * Returns the blogs entry finder.
	 *
	 * @return the blogs entry finder
	 */
	public BlogsEntryFinder getBlogsEntryFinder() {
		return blogsEntryFinder;
	}

	/**
	 * Sets the blogs entry finder.
	 *
	 * @param blogsEntryFinder the blogs entry finder
	 */
	public void setBlogsEntryFinder(BlogsEntryFinder blogsEntryFinder) {
		this.blogsEntryFinder = blogsEntryFinder;
	}

	/**
	 * Returns the bookmarks entry local service.
	 *
	 * @return the bookmarks entry local service
	 */
	public BookmarksEntryLocalService getBookmarksEntryLocalService() {
		return bookmarksEntryLocalService;
	}

	/**
	 * Sets the bookmarks entry local service.
	 *
	 * @param bookmarksEntryLocalService the bookmarks entry local service
	 */
	public void setBookmarksEntryLocalService(
		BookmarksEntryLocalService bookmarksEntryLocalService) {
		this.bookmarksEntryLocalService = bookmarksEntryLocalService;
	}

	/**
	 * Returns the bookmarks entry remote service.
	 *
	 * @return the bookmarks entry remote service
	 */
	public BookmarksEntryService getBookmarksEntryService() {
		return bookmarksEntryService;
	}

	/**
	 * Sets the bookmarks entry remote service.
	 *
	 * @param bookmarksEntryService the bookmarks entry remote service
	 */
	public void setBookmarksEntryService(
		BookmarksEntryService bookmarksEntryService) {
		this.bookmarksEntryService = bookmarksEntryService;
	}

	/**
	 * Returns the bookmarks entry persistence.
	 *
	 * @return the bookmarks entry persistence
	 */
	public BookmarksEntryPersistence getBookmarksEntryPersistence() {
		return bookmarksEntryPersistence;
	}

	/**
	 * Sets the bookmarks entry persistence.
	 *
	 * @param bookmarksEntryPersistence the bookmarks entry persistence
	 */
	public void setBookmarksEntryPersistence(
		BookmarksEntryPersistence bookmarksEntryPersistence) {
		this.bookmarksEntryPersistence = bookmarksEntryPersistence;
	}

	/**
	 * Returns the bookmarks entry finder.
	 *
	 * @return the bookmarks entry finder
	 */
	public BookmarksEntryFinder getBookmarksEntryFinder() {
		return bookmarksEntryFinder;
	}

	/**
	 * Sets the bookmarks entry finder.
	 *
	 * @param bookmarksEntryFinder the bookmarks entry finder
	 */
	public void setBookmarksEntryFinder(
		BookmarksEntryFinder bookmarksEntryFinder) {
		this.bookmarksEntryFinder = bookmarksEntryFinder;
	}

	/**
	 * Returns the d l app local service.
	 *
	 * @return the d l app local service
	 */
	public DLAppLocalService getDLAppLocalService() {
		return dlAppLocalService;
	}

	/**
	 * Sets the d l app local service.
	 *
	 * @param dlAppLocalService the d l app local service
	 */
	public void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		this.dlAppLocalService = dlAppLocalService;
	}

	/**
	 * Returns the d l app remote service.
	 *
	 * @return the d l app remote service
	 */
	public DLAppService getDLAppService() {
		return dlAppService;
	}

	/**
	 * Sets the d l app remote service.
	 *
	 * @param dlAppService the d l app remote service
	 */
	public void setDLAppService(DLAppService dlAppService) {
		this.dlAppService = dlAppService;
	}

	/**
	 * Returns the document library file entry local service.
	 *
	 * @return the document library file entry local service
	 */
	public DLFileEntryLocalService getDLFileEntryLocalService() {
		return dlFileEntryLocalService;
	}

	/**
	 * Sets the document library file entry local service.
	 *
	 * @param dlFileEntryLocalService the document library file entry local service
	 */
	public void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {
		this.dlFileEntryLocalService = dlFileEntryLocalService;
	}

	/**
	 * Returns the document library file entry remote service.
	 *
	 * @return the document library file entry remote service
	 */
	public DLFileEntryService getDLFileEntryService() {
		return dlFileEntryService;
	}

	/**
	 * Sets the document library file entry remote service.
	 *
	 * @param dlFileEntryService the document library file entry remote service
	 */
	public void setDLFileEntryService(DLFileEntryService dlFileEntryService) {
		this.dlFileEntryService = dlFileEntryService;
	}

	/**
	 * Returns the document library file entry persistence.
	 *
	 * @return the document library file entry persistence
	 */
	public DLFileEntryPersistence getDLFileEntryPersistence() {
		return dlFileEntryPersistence;
	}

	/**
	 * Sets the document library file entry persistence.
	 *
	 * @param dlFileEntryPersistence the document library file entry persistence
	 */
	public void setDLFileEntryPersistence(
		DLFileEntryPersistence dlFileEntryPersistence) {
		this.dlFileEntryPersistence = dlFileEntryPersistence;
	}

	/**
	 * Returns the document library file entry finder.
	 *
	 * @return the document library file entry finder
	 */
	public DLFileEntryFinder getDLFileEntryFinder() {
		return dlFileEntryFinder;
	}

	/**
	 * Sets the document library file entry finder.
	 *
	 * @param dlFileEntryFinder the document library file entry finder
	 */
	public void setDLFileEntryFinder(DLFileEntryFinder dlFileEntryFinder) {
		this.dlFileEntryFinder = dlFileEntryFinder;
	}

	/**
	 * Returns the document library folder local service.
	 *
	 * @return the document library folder local service
	 */
	public DLFolderLocalService getDLFolderLocalService() {
		return dlFolderLocalService;
	}

	/**
	 * Sets the document library folder local service.
	 *
	 * @param dlFolderLocalService the document library folder local service
	 */
	public void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {
		this.dlFolderLocalService = dlFolderLocalService;
	}

	/**
	 * Returns the document library folder remote service.
	 *
	 * @return the document library folder remote service
	 */
	public DLFolderService getDLFolderService() {
		return dlFolderService;
	}

	/**
	 * Sets the document library folder remote service.
	 *
	 * @param dlFolderService the document library folder remote service
	 */
	public void setDLFolderService(DLFolderService dlFolderService) {
		this.dlFolderService = dlFolderService;
	}

	/**
	 * Returns the document library folder persistence.
	 *
	 * @return the document library folder persistence
	 */
	public DLFolderPersistence getDLFolderPersistence() {
		return dlFolderPersistence;
	}

	/**
	 * Sets the document library folder persistence.
	 *
	 * @param dlFolderPersistence the document library folder persistence
	 */
	public void setDLFolderPersistence(DLFolderPersistence dlFolderPersistence) {
		this.dlFolderPersistence = dlFolderPersistence;
	}

	/**
	 * Returns the document library folder finder.
	 *
	 * @return the document library folder finder
	 */
	public DLFolderFinder getDLFolderFinder() {
		return dlFolderFinder;
	}

	/**
	 * Sets the document library folder finder.
	 *
	 * @param dlFolderFinder the document library folder finder
	 */
	public void setDLFolderFinder(DLFolderFinder dlFolderFinder) {
		this.dlFolderFinder = dlFolderFinder;
	}

	/**
	 * Returns the journal article local service.
	 *
	 * @return the journal article local service
	 */
	public JournalArticleLocalService getJournalArticleLocalService() {
		return journalArticleLocalService;
	}

	/**
	 * Sets the journal article local service.
	 *
	 * @param journalArticleLocalService the journal article local service
	 */
	public void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {
		this.journalArticleLocalService = journalArticleLocalService;
	}

	/**
	 * Returns the journal article remote service.
	 *
	 * @return the journal article remote service
	 */
	public JournalArticleService getJournalArticleService() {
		return journalArticleService;
	}

	/**
	 * Sets the journal article remote service.
	 *
	 * @param journalArticleService the journal article remote service
	 */
	public void setJournalArticleService(
		JournalArticleService journalArticleService) {
		this.journalArticleService = journalArticleService;
	}

	/**
	 * Returns the journal article persistence.
	 *
	 * @return the journal article persistence
	 */
	public JournalArticlePersistence getJournalArticlePersistence() {
		return journalArticlePersistence;
	}

	/**
	 * Sets the journal article persistence.
	 *
	 * @param journalArticlePersistence the journal article persistence
	 */
	public void setJournalArticlePersistence(
		JournalArticlePersistence journalArticlePersistence) {
		this.journalArticlePersistence = journalArticlePersistence;
	}

	/**
	 * Returns the journal article finder.
	 *
	 * @return the journal article finder
	 */
	public JournalArticleFinder getJournalArticleFinder() {
		return journalArticleFinder;
	}

	/**
	 * Sets the journal article finder.
	 *
	 * @param journalArticleFinder the journal article finder
	 */
	public void setJournalArticleFinder(
		JournalArticleFinder journalArticleFinder) {
		this.journalArticleFinder = journalArticleFinder;
	}

	/**
	 * Returns the journal article resource local service.
	 *
	 * @return the journal article resource local service
	 */
	public JournalArticleResourceLocalService getJournalArticleResourceLocalService() {
		return journalArticleResourceLocalService;
	}

	/**
	 * Sets the journal article resource local service.
	 *
	 * @param journalArticleResourceLocalService the journal article resource local service
	 */
	public void setJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {
		this.journalArticleResourceLocalService = journalArticleResourceLocalService;
	}

	/**
	 * Returns the journal article resource persistence.
	 *
	 * @return the journal article resource persistence
	 */
	public JournalArticleResourcePersistence getJournalArticleResourcePersistence() {
		return journalArticleResourcePersistence;
	}

	/**
	 * Sets the journal article resource persistence.
	 *
	 * @param journalArticleResourcePersistence the journal article resource persistence
	 */
	public void setJournalArticleResourcePersistence(
		JournalArticleResourcePersistence journalArticleResourcePersistence) {
		this.journalArticleResourcePersistence = journalArticleResourcePersistence;
	}

	/**
	 * Returns the message-boards message local service.
	 *
	 * @return the message-boards message local service
	 */
	public MBMessageLocalService getMBMessageLocalService() {
		return mbMessageLocalService;
	}

	/**
	 * Sets the message-boards message local service.
	 *
	 * @param mbMessageLocalService the message-boards message local service
	 */
	public void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {
		this.mbMessageLocalService = mbMessageLocalService;
	}

	/**
	 * Returns the message-boards message remote service.
	 *
	 * @return the message-boards message remote service
	 */
	public MBMessageService getMBMessageService() {
		return mbMessageService;
	}

	/**
	 * Sets the message-boards message remote service.
	 *
	 * @param mbMessageService the message-boards message remote service
	 */
	public void setMBMessageService(MBMessageService mbMessageService) {
		this.mbMessageService = mbMessageService;
	}

	/**
	 * Returns the message-boards message persistence.
	 *
	 * @return the message-boards message persistence
	 */
	public MBMessagePersistence getMBMessagePersistence() {
		return mbMessagePersistence;
	}

	/**
	 * Sets the message-boards message persistence.
	 *
	 * @param mbMessagePersistence the message-boards message persistence
	 */
	public void setMBMessagePersistence(
		MBMessagePersistence mbMessagePersistence) {
		this.mbMessagePersistence = mbMessagePersistence;
	}

	/**
	 * Returns the message-boards message finder.
	 *
	 * @return the message-boards message finder
	 */
	public MBMessageFinder getMBMessageFinder() {
		return mbMessageFinder;
	}

	/**
	 * Sets the message-boards message finder.
	 *
	 * @param mbMessageFinder the message-boards message finder
	 */
	public void setMBMessageFinder(MBMessageFinder mbMessageFinder) {
		this.mbMessageFinder = mbMessageFinder;
	}

	/**
	 * Returns the social activity local service.
	 *
	 * @return the social activity local service
	 */
	public SocialActivityLocalService getSocialActivityLocalService() {
		return socialActivityLocalService;
	}

	/**
	 * Sets the social activity local service.
	 *
	 * @param socialActivityLocalService the social activity local service
	 */
	public void setSocialActivityLocalService(
		SocialActivityLocalService socialActivityLocalService) {
		this.socialActivityLocalService = socialActivityLocalService;
	}

	/**
	 * Returns the social activity persistence.
	 *
	 * @return the social activity persistence
	 */
	public SocialActivityPersistence getSocialActivityPersistence() {
		return socialActivityPersistence;
	}

	/**
	 * Sets the social activity persistence.
	 *
	 * @param socialActivityPersistence the social activity persistence
	 */
	public void setSocialActivityPersistence(
		SocialActivityPersistence socialActivityPersistence) {
		this.socialActivityPersistence = socialActivityPersistence;
	}

	/**
	 * Returns the social activity finder.
	 *
	 * @return the social activity finder
	 */
	public SocialActivityFinder getSocialActivityFinder() {
		return socialActivityFinder;
	}

	/**
	 * Sets the social activity finder.
	 *
	 * @param socialActivityFinder the social activity finder
	 */
	public void setSocialActivityFinder(
		SocialActivityFinder socialActivityFinder) {
		this.socialActivityFinder = socialActivityFinder;
	}

	/**
	 * Returns the social activity counter local service.
	 *
	 * @return the social activity counter local service
	 */
	public SocialActivityCounterLocalService getSocialActivityCounterLocalService() {
		return socialActivityCounterLocalService;
	}

	/**
	 * Sets the social activity counter local service.
	 *
	 * @param socialActivityCounterLocalService the social activity counter local service
	 */
	public void setSocialActivityCounterLocalService(
		SocialActivityCounterLocalService socialActivityCounterLocalService) {
		this.socialActivityCounterLocalService = socialActivityCounterLocalService;
	}

	/**
	 * Returns the social activity counter persistence.
	 *
	 * @return the social activity counter persistence
	 */
	public SocialActivityCounterPersistence getSocialActivityCounterPersistence() {
		return socialActivityCounterPersistence;
	}

	/**
	 * Sets the social activity counter persistence.
	 *
	 * @param socialActivityCounterPersistence the social activity counter persistence
	 */
	public void setSocialActivityCounterPersistence(
		SocialActivityCounterPersistence socialActivityCounterPersistence) {
		this.socialActivityCounterPersistence = socialActivityCounterPersistence;
	}

	/**
	 * Returns the social activity counter finder.
	 *
	 * @return the social activity counter finder
	 */
	public SocialActivityCounterFinder getSocialActivityCounterFinder() {
		return socialActivityCounterFinder;
	}

	/**
	 * Sets the social activity counter finder.
	 *
	 * @param socialActivityCounterFinder the social activity counter finder
	 */
	public void setSocialActivityCounterFinder(
		SocialActivityCounterFinder socialActivityCounterFinder) {
		this.socialActivityCounterFinder = socialActivityCounterFinder;
	}

	/**
	 * Returns the wiki page local service.
	 *
	 * @return the wiki page local service
	 */
	public WikiPageLocalService getWikiPageLocalService() {
		return wikiPageLocalService;
	}

	/**
	 * Sets the wiki page local service.
	 *
	 * @param wikiPageLocalService the wiki page local service
	 */
	public void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {
		this.wikiPageLocalService = wikiPageLocalService;
	}

	/**
	 * Returns the wiki page remote service.
	 *
	 * @return the wiki page remote service
	 */
	public WikiPageService getWikiPageService() {
		return wikiPageService;
	}

	/**
	 * Sets the wiki page remote service.
	 *
	 * @param wikiPageService the wiki page remote service
	 */
	public void setWikiPageService(WikiPageService wikiPageService) {
		this.wikiPageService = wikiPageService;
	}

	/**
	 * Returns the wiki page persistence.
	 *
	 * @return the wiki page persistence
	 */
	public WikiPagePersistence getWikiPagePersistence() {
		return wikiPagePersistence;
	}

	/**
	 * Sets the wiki page persistence.
	 *
	 * @param wikiPagePersistence the wiki page persistence
	 */
	public void setWikiPagePersistence(WikiPagePersistence wikiPagePersistence) {
		this.wikiPagePersistence = wikiPagePersistence;
	}

	/**
	 * Returns the wiki page finder.
	 *
	 * @return the wiki page finder
	 */
	public WikiPageFinder getWikiPageFinder() {
		return wikiPageFinder;
	}

	/**
	 * Sets the wiki page finder.
	 *
	 * @param wikiPageFinder the wiki page finder
	 */
	public void setWikiPageFinder(WikiPageFinder wikiPageFinder) {
		this.wikiPageFinder = wikiPageFinder;
	}

	/**
	 * Returns the wiki page resource local service.
	 *
	 * @return the wiki page resource local service
	 */
	public WikiPageResourceLocalService getWikiPageResourceLocalService() {
		return wikiPageResourceLocalService;
	}

	/**
	 * Sets the wiki page resource local service.
	 *
	 * @param wikiPageResourceLocalService the wiki page resource local service
	 */
	public void setWikiPageResourceLocalService(
		WikiPageResourceLocalService wikiPageResourceLocalService) {
		this.wikiPageResourceLocalService = wikiPageResourceLocalService;
	}

	/**
	 * Returns the wiki page resource persistence.
	 *
	 * @return the wiki page resource persistence
	 */
	public WikiPageResourcePersistence getWikiPageResourcePersistence() {
		return wikiPageResourcePersistence;
	}

	/**
	 * Sets the wiki page resource persistence.
	 *
	 * @param wikiPageResourcePersistence the wiki page resource persistence
	 */
	public void setWikiPageResourcePersistence(
		WikiPageResourcePersistence wikiPageResourcePersistence) {
		this.wikiPageResourcePersistence = wikiPageResourcePersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portlet.asset.model.AssetEntry",
			assetEntryLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portlet.asset.model.AssetEntry");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected Class<?> getModelClass() {
		return AssetEntry.class;
	}

	protected String getModelClassName() {
		return AssetEntry.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = assetEntryPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = AssetCategoryLocalService.class)
	protected AssetCategoryLocalService assetCategoryLocalService;
	@BeanReference(type = AssetCategoryService.class)
	protected AssetCategoryService assetCategoryService;
	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetCategoryFinder.class)
	protected AssetCategoryFinder assetCategoryFinder;
	@BeanReference(type = AssetCategoryPropertyLocalService.class)
	protected AssetCategoryPropertyLocalService assetCategoryPropertyLocalService;
	@BeanReference(type = AssetCategoryPropertyService.class)
	protected AssetCategoryPropertyService assetCategoryPropertyService;
	@BeanReference(type = AssetCategoryPropertyPersistence.class)
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(type = AssetCategoryPropertyFinder.class)
	protected AssetCategoryPropertyFinder assetCategoryPropertyFinder;
	@BeanReference(type = AssetEntryLocalService.class)
	protected AssetEntryLocalService assetEntryLocalService;
	@BeanReference(type = AssetEntryService.class)
	protected AssetEntryService assetEntryService;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetEntryFinder.class)
	protected AssetEntryFinder assetEntryFinder;
	@BeanReference(type = AssetLinkLocalService.class)
	protected AssetLinkLocalService assetLinkLocalService;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = AssetLinkFinder.class)
	protected AssetLinkFinder assetLinkFinder;
	@BeanReference(type = AssetTagLocalService.class)
	protected AssetTagLocalService assetTagLocalService;
	@BeanReference(type = AssetTagService.class)
	protected AssetTagService assetTagService;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagFinder.class)
	protected AssetTagFinder assetTagFinder;
	@BeanReference(type = AssetTagPropertyLocalService.class)
	protected AssetTagPropertyLocalService assetTagPropertyLocalService;
	@BeanReference(type = AssetTagPropertyService.class)
	protected AssetTagPropertyService assetTagPropertyService;
	@BeanReference(type = AssetTagPropertyPersistence.class)
	protected AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(type = AssetTagPropertyFinder.class)
	protected AssetTagPropertyFinder assetTagPropertyFinder;
	@BeanReference(type = AssetTagPropertyKeyFinder.class)
	protected AssetTagPropertyKeyFinder assetTagPropertyKeyFinder;
	@BeanReference(type = AssetTagStatsLocalService.class)
	protected AssetTagStatsLocalService assetTagStatsLocalService;
	@BeanReference(type = AssetTagStatsPersistence.class)
	protected AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(type = AssetVocabularyLocalService.class)
	protected AssetVocabularyLocalService assetVocabularyLocalService;
	@BeanReference(type = AssetVocabularyService.class)
	protected AssetVocabularyService assetVocabularyService;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = AssetVocabularyFinder.class)
	protected AssetVocabularyFinder assetVocabularyFinder;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = CompanyLocalService.class)
	protected CompanyLocalService companyLocalService;
	@BeanReference(type = CompanyService.class)
	protected CompanyService companyService;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = GroupLocalService.class)
	protected GroupLocalService groupLocalService;
	@BeanReference(type = GroupService.class)
	protected GroupService groupService;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = GroupFinder.class)
	protected GroupFinder groupFinder;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = SystemEventLocalService.class)
	protected SystemEventLocalService systemEventLocalService;
	@BeanReference(type = SystemEventPersistence.class)
	protected SystemEventPersistence systemEventPersistence;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = BlogsEntryLocalService.class)
	protected BlogsEntryLocalService blogsEntryLocalService;
	@BeanReference(type = BlogsEntryService.class)
	protected BlogsEntryService blogsEntryService;
	@BeanReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(type = BlogsEntryFinder.class)
	protected BlogsEntryFinder blogsEntryFinder;
	@BeanReference(type = BookmarksEntryLocalService.class)
	protected BookmarksEntryLocalService bookmarksEntryLocalService;
	@BeanReference(type = BookmarksEntryService.class)
	protected BookmarksEntryService bookmarksEntryService;
	@BeanReference(type = BookmarksEntryPersistence.class)
	protected BookmarksEntryPersistence bookmarksEntryPersistence;
	@BeanReference(type = BookmarksEntryFinder.class)
	protected BookmarksEntryFinder bookmarksEntryFinder;
	@BeanReference(type = DLAppLocalService.class)
	protected DLAppLocalService dlAppLocalService;
	@BeanReference(type = DLAppService.class)
	protected DLAppService dlAppService;
	@BeanReference(type = DLFileEntryLocalService.class)
	protected DLFileEntryLocalService dlFileEntryLocalService;
	@BeanReference(type = DLFileEntryService.class)
	protected DLFileEntryService dlFileEntryService;
	@BeanReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(type = DLFileEntryFinder.class)
	protected DLFileEntryFinder dlFileEntryFinder;
	@BeanReference(type = DLFolderLocalService.class)
	protected DLFolderLocalService dlFolderLocalService;
	@BeanReference(type = DLFolderService.class)
	protected DLFolderService dlFolderService;
	@BeanReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(type = DLFolderFinder.class)
	protected DLFolderFinder dlFolderFinder;
	@BeanReference(type = JournalArticleLocalService.class)
	protected JournalArticleLocalService journalArticleLocalService;
	@BeanReference(type = JournalArticleService.class)
	protected JournalArticleService journalArticleService;
	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalArticleFinder.class)
	protected JournalArticleFinder journalArticleFinder;
	@BeanReference(type = JournalArticleResourceLocalService.class)
	protected JournalArticleResourceLocalService journalArticleResourceLocalService;
	@BeanReference(type = JournalArticleResourcePersistence.class)
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(type = MBMessageLocalService.class)
	protected MBMessageLocalService mbMessageLocalService;
	@BeanReference(type = MBMessageService.class)
	protected MBMessageService mbMessageService;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = MBMessageFinder.class)
	protected MBMessageFinder mbMessageFinder;
	@BeanReference(type = SocialActivityLocalService.class)
	protected SocialActivityLocalService socialActivityLocalService;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialActivityFinder.class)
	protected SocialActivityFinder socialActivityFinder;
	@BeanReference(type = SocialActivityCounterLocalService.class)
	protected SocialActivityCounterLocalService socialActivityCounterLocalService;
	@BeanReference(type = SocialActivityCounterPersistence.class)
	protected SocialActivityCounterPersistence socialActivityCounterPersistence;
	@BeanReference(type = SocialActivityCounterFinder.class)
	protected SocialActivityCounterFinder socialActivityCounterFinder;
	@BeanReference(type = WikiPageLocalService.class)
	protected WikiPageLocalService wikiPageLocalService;
	@BeanReference(type = WikiPageService.class)
	protected WikiPageService wikiPageService;
	@BeanReference(type = WikiPagePersistence.class)
	protected WikiPagePersistence wikiPagePersistence;
	@BeanReference(type = WikiPageFinder.class)
	protected WikiPageFinder wikiPageFinder;
	@BeanReference(type = WikiPageResourceLocalService.class)
	protected WikiPageResourceLocalService wikiPageResourceLocalService;
	@BeanReference(type = WikiPageResourcePersistence.class)
	protected WikiPageResourcePersistence wikiPageResourcePersistence;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}