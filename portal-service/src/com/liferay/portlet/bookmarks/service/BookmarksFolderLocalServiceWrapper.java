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

package com.liferay.portlet.bookmarks.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BookmarksFolderLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolderLocalService
 * @generated
 */
public class BookmarksFolderLocalServiceWrapper
	implements BookmarksFolderLocalService,
		ServiceWrapper<BookmarksFolderLocalService> {
	public BookmarksFolderLocalServiceWrapper(
		BookmarksFolderLocalService bookmarksFolderLocalService) {
		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	/**
	* Adds the bookmarks folder to the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksFolder the bookmarks folder
	* @return the bookmarks folder that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder addBookmarksFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.addBookmarksFolder(bookmarksFolder);
	}

	/**
	* Creates a new bookmarks folder with the primary key. Does not add the bookmarks folder to the database.
	*
	* @param folderId the primary key for the new bookmarks folder
	* @return the new bookmarks folder
	*/
	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder createBookmarksFolder(
		long folderId) {
		return _bookmarksFolderLocalService.createBookmarksFolder(folderId);
	}

	/**
	* Deletes the bookmarks folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the bookmarks folder
	* @return the bookmarks folder that was removed
	* @throws PortalException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder deleteBookmarksFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.deleteBookmarksFolder(folderId);
	}

	/**
	* Deletes the bookmarks folder from the database. Also notifies the appropriate model listeners.
	*
	* @param bookmarksFolder the bookmarks folder
	* @return the bookmarks folder that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder deleteBookmarksFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.deleteBookmarksFolder(bookmarksFolder);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _bookmarksFolderLocalService.dynamicQuery();
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.bookmarks.model.impl.BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.bookmarks.model.impl.BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.dynamicQuery(dynamicQuery, start,
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
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder fetchBookmarksFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.fetchBookmarksFolder(folderId);
	}

	/**
	* Returns the bookmarks folder with the primary key.
	*
	* @param folderId the primary key of the bookmarks folder
	* @return the bookmarks folder
	* @throws PortalException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder getBookmarksFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getBookmarksFolder(folderId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the bookmarks folder matching the UUID and group.
	*
	* @param uuid the bookmarks folder's UUID
	* @param groupId the primary key of the group
	* @return the matching bookmarks folder
	* @throws PortalException if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder getBookmarksFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getBookmarksFolderByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the bookmarks folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.bookmarks.model.impl.BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getBookmarksFolders(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getBookmarksFolders(start, end);
	}

	/**
	* Returns the number of bookmarks folders.
	*
	* @return the number of bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getBookmarksFoldersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getBookmarksFoldersCount();
	}

	/**
	* Updates the bookmarks folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param bookmarksFolder the bookmarks folder
	* @return the bookmarks folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder updateBookmarksFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.updateBookmarksFolder(bookmarksFolder);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _bookmarksFolderLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_bookmarksFolderLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder addFolder(
		long userId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.addFolder(userId, parentFolderId,
			name, description, serviceContext);
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder deleteFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.deleteFolder(folder);
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder deleteFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.deleteFolder(folder,
			includeTrashedEntries);
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder deleteFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.deleteFolder(folderId);
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder deleteFolder(
		long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.deleteFolder(folderId,
			includeTrashedEntries);
	}

	@Override
	public void deleteFolders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderLocalService.deleteFolders(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getCompanyFolders(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getCompanyFolders(companyId, start,
			end);
	}

	@Override
	public int getCompanyFoldersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getCompanyFoldersCount(companyId);
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getFolder(folderId);
	}

	@Override
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getFolders(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getFolders(groupId, parentFolderId);
	}

	@Override
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getFolders(groupId, parentFolderId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getFolders(groupId, parentFolderId,
			status, start, end);
	}

	@Override
	public java.util.List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getFoldersAndEntries(groupId,
			folderId);
	}

	@Override
	public java.util.List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getFoldersAndEntries(groupId,
			folderId, status);
	}

	@Override
	public java.util.List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getFoldersAndEntries(groupId,
			folderId, status, start, end);
	}

	@Override
	public int getFoldersAndEntriesCount(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getFoldersAndEntriesCount(groupId,
			folderId, status);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getFoldersCount(groupId,
			parentFolderId);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getFoldersCount(groupId,
			parentFolderId, status);
	}

	@Override
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getNoAssetFolders()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.getNoAssetFolders();
	}

	@Override
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderLocalService.getSubfolderIds(folderIds, groupId,
			folderId);
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder moveFolder(
		long folderId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.moveFolder(folderId, parentFolderId);
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder moveFolderFromTrash(
		long userId, long folderId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.moveFolderFromTrash(userId,
			folderId, parentFolderId);
	}

	@Override
	public void moveFolderToTrash(long userId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderLocalService.moveFolderToTrash(userId, folderId);
	}

	@Override
	public void restoreFolderFromTrash(long userId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderLocalService.restoreFolderFromTrash(userId, folderId);
	}

	@Override
	public void subscribeFolder(long userId, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderLocalService.subscribeFolder(userId, groupId, folderId);
	}

	@Override
	public void unsubscribeFolder(long userId, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderLocalService.unsubscribeFolder(userId, groupId, folderId);
	}

	@Override
	public void updateAsset(long userId,
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderLocalService.updateAsset(userId, folder,
			assetCategoryIds, assetTagNames, assetLinkEntryIds);
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder updateFolder(
		long userId, long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.updateFolder(userId, folderId,
			parentFolderId, name, description, mergeWithParentFolder,
			serviceContext);
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder updateStatus(
		long userId,
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderLocalService.updateStatus(userId, folder, status);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public BookmarksFolderLocalService getWrappedBookmarksFolderLocalService() {
		return _bookmarksFolderLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedBookmarksFolderLocalService(
		BookmarksFolderLocalService bookmarksFolderLocalService) {
		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	@Override
	public BookmarksFolderLocalService getWrappedService() {
		return _bookmarksFolderLocalService;
	}

	@Override
	public void setWrappedService(
		BookmarksFolderLocalService bookmarksFolderLocalService) {
		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	private BookmarksFolderLocalService _bookmarksFolderLocalService;
}