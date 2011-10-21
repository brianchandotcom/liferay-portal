/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.model;

import com.liferay.portal.model.ModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link BookmarksFolder}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BookmarksFolder
 * @generated
 */
public class BookmarksFolderWrapper implements BookmarksFolder,
	ModelWrapper<BookmarksFolder> {
	public BookmarksFolderWrapper(BookmarksFolder bookmarksFolder) {
		_bookmarksFolder = bookmarksFolder;
	}

	public Class<?> getModelClass() {
		return BookmarksFolder.class;
	}

	public String getModelClassName() {
		return BookmarksFolder.class.getName();
	}

	/**
	* Returns the primary key of this bookmarks folder.
	*
	* @return the primary key of this bookmarks folder
	*/
	public long getPrimaryKey() {
		return _bookmarksFolder.getPrimaryKey();
	}

	/**
	* Sets the primary key of this bookmarks folder.
	*
	* @param primaryKey the primary key of this bookmarks folder
	*/
	public void setPrimaryKey(long primaryKey) {
		_bookmarksFolder.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this bookmarks folder.
	*
	* @return the uuid of this bookmarks folder
	*/
	public java.lang.String getUuid() {
		return _bookmarksFolder.getUuid();
	}

	/**
	* Sets the uuid of this bookmarks folder.
	*
	* @param uuid the uuid of this bookmarks folder
	*/
	public void setUuid(java.lang.String uuid) {
		_bookmarksFolder.setUuid(uuid);
	}

	/**
	* Returns the folder ID of this bookmarks folder.
	*
	* @return the folder ID of this bookmarks folder
	*/
	public long getFolderId() {
		return _bookmarksFolder.getFolderId();
	}

	/**
	* Sets the folder ID of this bookmarks folder.
	*
	* @param folderId the folder ID of this bookmarks folder
	*/
	public void setFolderId(long folderId) {
		_bookmarksFolder.setFolderId(folderId);
	}

	/**
	* Returns the group ID of this bookmarks folder.
	*
	* @return the group ID of this bookmarks folder
	*/
	public long getGroupId() {
		return _bookmarksFolder.getGroupId();
	}

	/**
	* Sets the group ID of this bookmarks folder.
	*
	* @param groupId the group ID of this bookmarks folder
	*/
	public void setGroupId(long groupId) {
		_bookmarksFolder.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this bookmarks folder.
	*
	* @return the company ID of this bookmarks folder
	*/
	public long getCompanyId() {
		return _bookmarksFolder.getCompanyId();
	}

	/**
	* Sets the company ID of this bookmarks folder.
	*
	* @param companyId the company ID of this bookmarks folder
	*/
	public void setCompanyId(long companyId) {
		_bookmarksFolder.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this bookmarks folder.
	*
	* @return the user ID of this bookmarks folder
	*/
	public long getUserId() {
		return _bookmarksFolder.getUserId();
	}

	/**
	* Sets the user ID of this bookmarks folder.
	*
	* @param userId the user ID of this bookmarks folder
	*/
	public void setUserId(long userId) {
		_bookmarksFolder.setUserId(userId);
	}

	/**
	* Returns the user uuid of this bookmarks folder.
	*
	* @return the user uuid of this bookmarks folder
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolder.getUserUuid();
	}

	/**
	* Sets the user uuid of this bookmarks folder.
	*
	* @param userUuid the user uuid of this bookmarks folder
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_bookmarksFolder.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this bookmarks folder.
	*
	* @return the user name of this bookmarks folder
	*/
	public java.lang.String getUserName() {
		return _bookmarksFolder.getUserName();
	}

	/**
	* Sets the user name of this bookmarks folder.
	*
	* @param userName the user name of this bookmarks folder
	*/
	public void setUserName(java.lang.String userName) {
		_bookmarksFolder.setUserName(userName);
	}

	/**
	* Returns the create date of this bookmarks folder.
	*
	* @return the create date of this bookmarks folder
	*/
	public java.util.Date getCreateDate() {
		return _bookmarksFolder.getCreateDate();
	}

	/**
	* Sets the create date of this bookmarks folder.
	*
	* @param createDate the create date of this bookmarks folder
	*/
	public void setCreateDate(java.util.Date createDate) {
		_bookmarksFolder.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this bookmarks folder.
	*
	* @return the modified date of this bookmarks folder
	*/
	public java.util.Date getModifiedDate() {
		return _bookmarksFolder.getModifiedDate();
	}

	/**
	* Sets the modified date of this bookmarks folder.
	*
	* @param modifiedDate the modified date of this bookmarks folder
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_bookmarksFolder.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the resource block ID of this bookmarks folder.
	*
	* @return the resource block ID of this bookmarks folder
	*/
	public long getResourceBlockId() {
		return _bookmarksFolder.getResourceBlockId();
	}

	/**
	* Sets the resource block ID of this bookmarks folder.
	*
	* @param resourceBlockId the resource block ID of this bookmarks folder
	*/
	public void setResourceBlockId(long resourceBlockId) {
		_bookmarksFolder.setResourceBlockId(resourceBlockId);
	}

	/**
	* Returns the parent folder ID of this bookmarks folder.
	*
	* @return the parent folder ID of this bookmarks folder
	*/
	public long getParentFolderId() {
		return _bookmarksFolder.getParentFolderId();
	}

	/**
	* Sets the parent folder ID of this bookmarks folder.
	*
	* @param parentFolderId the parent folder ID of this bookmarks folder
	*/
	public void setParentFolderId(long parentFolderId) {
		_bookmarksFolder.setParentFolderId(parentFolderId);
	}

	/**
	* Returns the name of this bookmarks folder.
	*
	* @return the name of this bookmarks folder
	*/
	public java.lang.String getName() {
		return _bookmarksFolder.getName();
	}

	/**
	* Sets the name of this bookmarks folder.
	*
	* @param name the name of this bookmarks folder
	*/
	public void setName(java.lang.String name) {
		_bookmarksFolder.setName(name);
	}

	/**
	* Returns the description of this bookmarks folder.
	*
	* @return the description of this bookmarks folder
	*/
	public java.lang.String getDescription() {
		return _bookmarksFolder.getDescription();
	}

	/**
	* Sets the description of this bookmarks folder.
	*
	* @param description the description of this bookmarks folder
	*/
	public void setDescription(java.lang.String description) {
		_bookmarksFolder.setDescription(description);
	}

	public boolean isNew() {
		return _bookmarksFolder.isNew();
	}

	public void setNew(boolean n) {
		_bookmarksFolder.setNew(n);
	}

	public boolean isCachedModel() {
		return _bookmarksFolder.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_bookmarksFolder.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _bookmarksFolder.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _bookmarksFolder.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_bookmarksFolder.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _bookmarksFolder.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_bookmarksFolder.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new BookmarksFolderWrapper((BookmarksFolder)_bookmarksFolder.clone());
	}

	public int compareTo(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder) {
		return _bookmarksFolder.compareTo(bookmarksFolder);
	}

	@Override
	public int hashCode() {
		return _bookmarksFolder.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.bookmarks.model.BookmarksFolder> toCacheModel() {
		return _bookmarksFolder.toCacheModel();
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder toEscapedModel() {
		return new BookmarksFolderWrapper(_bookmarksFolder.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _bookmarksFolder.toString();
	}

	public java.lang.String toXmlString() {
		return _bookmarksFolder.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolder.persist();
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolder.getAncestors();
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolder.getParentFolder();
	}

	public boolean isRoot() {
		return _bookmarksFolder.isRoot();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public BookmarksFolder getWrappedBookmarksFolder() {
		return _bookmarksFolder;
	}

	public BookmarksFolder getWrappedModel() {
		return _bookmarksFolder;
	}

	public void resetOriginalValues() {
		_bookmarksFolder.resetOriginalValues();
	}

	private BookmarksFolder _bookmarksFolder;
}