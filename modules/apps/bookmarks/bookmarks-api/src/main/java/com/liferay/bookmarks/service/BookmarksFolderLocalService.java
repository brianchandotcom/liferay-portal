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
 * Provides the local service interface for BookmarksFolder. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolderLocalServiceUtil
 * @see com.liferay.bookmarks.service.base.BookmarksFolderLocalServiceBaseImpl
 * @see com.liferay.bookmarks.service.impl.BookmarksFolderLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface BookmarksFolderLocalService extends BaseLocalService,
	PermissionedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BookmarksFolderLocalServiceUtil} to access the bookmarks folder local service. Add custom service methods to {@link com.liferay.bookmarks.service.impl.BookmarksFolderLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the bookmarks folder to the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksFolder the bookmarks folder
	* @return the bookmarks folder that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksFolder addBookmarksFolder(
		com.liferay.bookmarks.model.BookmarksFolder bookmarksFolder);

	public com.liferay.bookmarks.model.BookmarksFolder addFolder(long userId,
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new bookmarks folder with the primary key. Does not add the bookmarks folder to the database.
	*
	* @param folderId the primary key for the new bookmarks folder
	* @return the new bookmarks folder
	*/
	public com.liferay.bookmarks.model.BookmarksFolder createBookmarksFolder(
		long folderId);

	/**
	* Deletes the bookmarks folder from the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksFolder the bookmarks folder
	* @return the bookmarks folder that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.bookmarks.model.BookmarksFolder deleteBookmarksFolder(
		com.liferay.bookmarks.model.BookmarksFolder bookmarksFolder);

	/**
	* Deletes the bookmarks folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the bookmarks folder
	* @return the bookmarks folder that was removed
	* @throws PortalException if a bookmarks folder with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.bookmarks.model.BookmarksFolder deleteBookmarksFolder(
		long folderId) throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	@com.liferay.portal.kernel.systemevent.SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public com.liferay.bookmarks.model.BookmarksFolder deleteFolder(
		com.liferay.bookmarks.model.BookmarksFolder folder)
		throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	@com.liferay.portal.kernel.systemevent.SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public com.liferay.bookmarks.model.BookmarksFolder deleteFolder(
		com.liferay.bookmarks.model.BookmarksFolder folder,
		boolean includeTrashedEntries) throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.bookmarks.model.BookmarksFolder deleteFolder(
		long folderId) throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.bookmarks.model.BookmarksFolder deleteFolder(
		long folderId, boolean includeTrashedEntries) throws PortalException;

	public void deleteFolders(long groupId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.bookmarks.model.BookmarksFolder fetchBookmarksFolder(
		long folderId);

	/**
	* Returns the bookmarks folder matching the UUID and group.
	*
	* @param uuid the bookmarks folder's UUID
	* @param groupId the primary key of the group
	* @return the matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.bookmarks.model.BookmarksFolder fetchBookmarksFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the bookmarks folder with the primary key.
	*
	* @param folderId the primary key of the bookmarks folder
	* @return the bookmarks folder
	* @throws PortalException if a bookmarks folder with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.bookmarks.model.BookmarksFolder getBookmarksFolder(
		long folderId) throws PortalException;

	/**
	* Returns the bookmarks folder matching the UUID and group.
	*
	* @param uuid the bookmarks folder's UUID
	* @param groupId the primary key of the group
	* @return the matching bookmarks folder
	* @throws PortalException if a matching bookmarks folder could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.bookmarks.model.BookmarksFolder getBookmarksFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the bookmarks folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of bookmarks folders
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getBookmarksFolders(
		int start, int end);

	/**
	* Returns all the bookmarks folders matching the UUID and company.
	*
	* @param uuid the UUID of the bookmarks folders
	* @param companyId the primary key of the company
	* @return the matching bookmarks folders, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getBookmarksFoldersByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of bookmarks folders matching the UUID and company.
	*
	* @param uuid the UUID of the bookmarks folders
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching bookmarks folders, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getBookmarksFoldersByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksFolder> orderByComparator);

	/**
	* Returns the number of bookmarks folders.
	*
	* @return the number of bookmarks folders
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getBookmarksFoldersCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getCompanyFolders(
		long companyId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyFoldersCount(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.bookmarks.model.BookmarksFolder getFolder(long folderId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getFolders(
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndEntriesCount(long groupId, long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long groupId, long parentFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long groupId, long parentFolderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getNoAssetFolders();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId);

	public void mergeFolders(long folderId, long parentFolderId)
		throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksFolder moveFolder(
		long folderId, long parentFolderId) throws PortalException;

	public com.liferay.bookmarks.model.BookmarksFolder moveFolderFromTrash(
		long userId, long folderId, long parentFolderId)
		throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksFolder moveFolderToTrash(
		long userId, long folderId) throws PortalException;

	public void rebuildTree(long companyId) throws PortalException;

	public void rebuildTree(long companyId, long parentFolderId,
		java.lang.String parentTreePath, boolean reindex)
		throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksFolder restoreFolderFromTrash(
		long userId, long folderId) throws PortalException;

	public void subscribeFolder(long userId, long groupId, long folderId)
		throws PortalException;

	public void unsubscribeFolder(long userId, long groupId, long folderId)
		throws PortalException;

	public void updateAsset(long userId,
		com.liferay.bookmarks.model.BookmarksFolder folder,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds, java.lang.Double priority)
		throws PortalException;

	/**
	* Updates the bookmarks folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param bookmarksFolder the bookmarks folder
	* @return the bookmarks folder that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksFolder updateBookmarksFolder(
		com.liferay.bookmarks.model.BookmarksFolder bookmarksFolder);

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateFolder(long, long,
	long, String, String, ServiceContext)} and {@link
	#mergeFolders(long, long)}
	*/
	@java.lang.Deprecated
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksFolder updateFolder(
		long userId, long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.bookmarks.model.BookmarksFolder updateFolder(
		long userId, long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.bookmarks.model.BookmarksFolder updateStatus(
		long userId, com.liferay.bookmarks.model.BookmarksFolder folder,
		int status) throws PortalException;
}