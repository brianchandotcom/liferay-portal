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

package com.liferay.bookmarks.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PermissionedModelLocalService;

/**
 * Provides the local service interface for BookmarksEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntryLocalServiceUtil
 * @see com.liferay.bookmarks.service.base.BookmarksEntryLocalServiceBaseImpl
 * @see com.liferay.bookmarks.service.impl.BookmarksEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface BookmarksEntryLocalService extends BaseLocalService,
	PermissionedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BookmarksEntryLocalServiceUtil} to access the bookmarks entry local service. Add custom service methods to {@link com.liferay.bookmarks.service.impl.BookmarksEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the bookmarks entry to the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksEntry the bookmarks entry
	* @return the bookmarks entry that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksEntry addBookmarksEntry(
		com.liferay.bookmarks.model.BookmarksEntry bookmarksEntry);

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksEntry addEntry(long userId,
		long groupId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Creates a new bookmarks entry with the primary key. Does not add the bookmarks entry to the database.
	*
	* @param entryId the primary key for the new bookmarks entry
	* @return the new bookmarks entry
	*/
	public com.liferay.bookmarks.model.BookmarksEntry createBookmarksEntry(
		long entryId);

	/**
	* Deletes the bookmarks entry from the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksEntry the bookmarks entry
	* @return the bookmarks entry that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.bookmarks.model.BookmarksEntry deleteBookmarksEntry(
		com.liferay.bookmarks.model.BookmarksEntry bookmarksEntry);

	/**
	* Deletes the bookmarks entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the bookmarks entry
	* @return the bookmarks entry that was removed
	* @throws PortalException if a bookmarks entry with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.bookmarks.model.BookmarksEntry deleteBookmarksEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteEntries(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteEntries(long groupId, long folderId,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	@com.liferay.portal.kernel.systemevent.SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public com.liferay.bookmarks.model.BookmarksEntry deleteEntry(
		com.liferay.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.bookmarks.model.BookmarksEntry deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.bookmarks.model.BookmarksEntry fetchBookmarksEntry(
		long entryId);

	/**
	* Returns the bookmarks entry matching the UUID and group.
	*
	* @param uuid the bookmarks entry's UUID
	* @param groupId the primary key of the group
	* @return the matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.bookmarks.model.BookmarksEntry fetchBookmarksEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Returns a range of all the bookmarks entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of bookmarks entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getBookmarksEntries(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getBookmarksEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getBookmarksEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the number of bookmarks entries.
	*
	* @return the number of bookmarks entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getBookmarksEntriesCount();

	/**
	* Returns the bookmarks entry with the primary key.
	*
	* @param entryId the primary key of the bookmarks entry
	* @return the bookmarks entry
	* @throws PortalException if a bookmarks entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.bookmarks.model.BookmarksEntry getBookmarksEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the bookmarks entry matching the UUID and group.
	*
	* @param uuid the bookmarks entry's UUID
	* @param groupId the primary key of the group
	* @return the matching bookmarks entry
	* @throws PortalException if a matching bookmarks entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.bookmarks.model.BookmarksEntry getBookmarksEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long groupId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long groupId, long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.bookmarks.model.BookmarksEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersEntriesCount(long groupId,
		java.util.List<java.lang.Long> folderIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId, long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getNoAssetEntries();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksEntry moveEntry(long entryId,
		long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.bookmarks.model.BookmarksEntry moveEntryFromTrash(
		long userId, long entryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksEntry moveEntryToTrash(
		long userId, com.liferay.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksEntry moveEntryToTrash(
		long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.bookmarks.model.BookmarksEntry openEntry(long userId,
		com.liferay.bookmarks.model.BookmarksEntry entry);

	public com.liferay.bookmarks.model.BookmarksEntry openEntry(long userId,
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void rebuildTree(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksEntry restoreEntryFromTrash(
		long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.search.Hits search(long groupId,
		long userId, long creatorUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public void setTreePaths(long folderId, java.lang.String treePath,
		boolean reindex)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void subscribeEntry(long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void unsubscribeEntry(long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void updateAsset(long userId,
		com.liferay.bookmarks.model.BookmarksEntry entry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Updates the bookmarks entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param bookmarksEntry the bookmarks entry
	* @return the bookmarks entry that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksEntry updateBookmarksEntry(
		com.liferay.bookmarks.model.BookmarksEntry bookmarksEntry);

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksEntry updateEntry(long userId,
		long entryId, long groupId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.bookmarks.model.BookmarksEntry updateStatus(
		long userId, com.liferay.bookmarks.model.BookmarksEntry entry,
		int status) throws com.liferay.portal.kernel.exception.PortalException;
}