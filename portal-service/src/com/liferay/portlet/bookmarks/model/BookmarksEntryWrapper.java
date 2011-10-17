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
 * This class is a wrapper for {@link BookmarksEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BookmarksEntry
 * @generated
 */
public class BookmarksEntryWrapper implements BookmarksEntry,
	ModelWrapper<BookmarksEntry> {
	public BookmarksEntryWrapper(BookmarksEntry bookmarksEntry) {
		_bookmarksEntry = bookmarksEntry;
	}

	public Class<?> getModelClass() {
		return BookmarksEntry.class;
	}

	public String getModelClassName() {
		return BookmarksEntry.class.getName();
	}

	/**
	* Returns the primary key of this bookmarks entry.
	*
	* @return the primary key of this bookmarks entry
	*/
	public long getPrimaryKey() {
		return _bookmarksEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this bookmarks entry.
	*
	* @param primaryKey the primary key of this bookmarks entry
	*/
	public void setPrimaryKey(long primaryKey) {
		_bookmarksEntry.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this bookmarks entry.
	*
	* @return the uuid of this bookmarks entry
	*/
	public java.lang.String getUuid() {
		return _bookmarksEntry.getUuid();
	}

	/**
	* Sets the uuid of this bookmarks entry.
	*
	* @param uuid the uuid of this bookmarks entry
	*/
	public void setUuid(java.lang.String uuid) {
		_bookmarksEntry.setUuid(uuid);
	}

	/**
	* Returns the entry ID of this bookmarks entry.
	*
	* @return the entry ID of this bookmarks entry
	*/
	public long getEntryId() {
		return _bookmarksEntry.getEntryId();
	}

	/**
	* Sets the entry ID of this bookmarks entry.
	*
	* @param entryId the entry ID of this bookmarks entry
	*/
	public void setEntryId(long entryId) {
		_bookmarksEntry.setEntryId(entryId);
	}

	/**
	* Returns the group ID of this bookmarks entry.
	*
	* @return the group ID of this bookmarks entry
	*/
	public long getGroupId() {
		return _bookmarksEntry.getGroupId();
	}

	/**
	* Sets the group ID of this bookmarks entry.
	*
	* @param groupId the group ID of this bookmarks entry
	*/
	public void setGroupId(long groupId) {
		_bookmarksEntry.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this bookmarks entry.
	*
	* @return the company ID of this bookmarks entry
	*/
	public long getCompanyId() {
		return _bookmarksEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this bookmarks entry.
	*
	* @param companyId the company ID of this bookmarks entry
	*/
	public void setCompanyId(long companyId) {
		_bookmarksEntry.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this bookmarks entry.
	*
	* @return the user ID of this bookmarks entry
	*/
	public long getUserId() {
		return _bookmarksEntry.getUserId();
	}

	/**
	* Sets the user ID of this bookmarks entry.
	*
	* @param userId the user ID of this bookmarks entry
	*/
	public void setUserId(long userId) {
		_bookmarksEntry.setUserId(userId);
	}

	/**
	* Returns the user uuid of this bookmarks entry.
	*
	* @return the user uuid of this bookmarks entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this bookmarks entry.
	*
	* @param userUuid the user uuid of this bookmarks entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_bookmarksEntry.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this bookmarks entry.
	*
	* @return the user name of this bookmarks entry
	*/
	public java.lang.String getUserName() {
		return _bookmarksEntry.getUserName();
	}

	/**
	* Sets the user name of this bookmarks entry.
	*
	* @param userName the user name of this bookmarks entry
	*/
	public void setUserName(java.lang.String userName) {
		_bookmarksEntry.setUserName(userName);
	}

	/**
	* Returns the create date of this bookmarks entry.
	*
	* @return the create date of this bookmarks entry
	*/
	public java.util.Date getCreateDate() {
		return _bookmarksEntry.getCreateDate();
	}

	/**
	* Sets the create date of this bookmarks entry.
	*
	* @param createDate the create date of this bookmarks entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_bookmarksEntry.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this bookmarks entry.
	*
	* @return the modified date of this bookmarks entry
	*/
	public java.util.Date getModifiedDate() {
		return _bookmarksEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this bookmarks entry.
	*
	* @param modifiedDate the modified date of this bookmarks entry
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_bookmarksEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the resource block ID of this bookmarks entry.
	*
	* @return the resource block ID of this bookmarks entry
	*/
	public long getResourceBlockId() {
		return _bookmarksEntry.getResourceBlockId();
	}

	/**
	* Sets the resource block ID of this bookmarks entry.
	*
	* @param resourceBlockId the resource block ID of this bookmarks entry
	*/
	public void setResourceBlockId(long resourceBlockId) {
		_bookmarksEntry.setResourceBlockId(resourceBlockId);
	}

	/**
	* Returns the folder ID of this bookmarks entry.
	*
	* @return the folder ID of this bookmarks entry
	*/
	public long getFolderId() {
		return _bookmarksEntry.getFolderId();
	}

	/**
	* Sets the folder ID of this bookmarks entry.
	*
	* @param folderId the folder ID of this bookmarks entry
	*/
	public void setFolderId(long folderId) {
		_bookmarksEntry.setFolderId(folderId);
	}

	/**
	* Returns the name of this bookmarks entry.
	*
	* @return the name of this bookmarks entry
	*/
	public java.lang.String getName() {
		return _bookmarksEntry.getName();
	}

	/**
	* Sets the name of this bookmarks entry.
	*
	* @param name the name of this bookmarks entry
	*/
	public void setName(java.lang.String name) {
		_bookmarksEntry.setName(name);
	}

	/**
	* Returns the url of this bookmarks entry.
	*
	* @return the url of this bookmarks entry
	*/
	public java.lang.String getUrl() {
		return _bookmarksEntry.getUrl();
	}

	/**
	* Sets the url of this bookmarks entry.
	*
	* @param url the url of this bookmarks entry
	*/
	public void setUrl(java.lang.String url) {
		_bookmarksEntry.setUrl(url);
	}

	/**
	* Returns the description of this bookmarks entry.
	*
	* @return the description of this bookmarks entry
	*/
	public java.lang.String getDescription() {
		return _bookmarksEntry.getDescription();
	}

	/**
	* Sets the description of this bookmarks entry.
	*
	* @param description the description of this bookmarks entry
	*/
	public void setDescription(java.lang.String description) {
		_bookmarksEntry.setDescription(description);
	}

	/**
	* Returns the visits of this bookmarks entry.
	*
	* @return the visits of this bookmarks entry
	*/
	public int getVisits() {
		return _bookmarksEntry.getVisits();
	}

	/**
	* Sets the visits of this bookmarks entry.
	*
	* @param visits the visits of this bookmarks entry
	*/
	public void setVisits(int visits) {
		_bookmarksEntry.setVisits(visits);
	}

	/**
	* Returns the priority of this bookmarks entry.
	*
	* @return the priority of this bookmarks entry
	*/
	public int getPriority() {
		return _bookmarksEntry.getPriority();
	}

	/**
	* Sets the priority of this bookmarks entry.
	*
	* @param priority the priority of this bookmarks entry
	*/
	public void setPriority(int priority) {
		_bookmarksEntry.setPriority(priority);
	}

	public boolean isNew() {
		return _bookmarksEntry.isNew();
	}

	public void setNew(boolean n) {
		_bookmarksEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _bookmarksEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_bookmarksEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _bookmarksEntry.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _bookmarksEntry.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_bookmarksEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _bookmarksEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_bookmarksEntry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new BookmarksEntryWrapper((BookmarksEntry)_bookmarksEntry.clone());
	}

	public int compareTo(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry) {
		return _bookmarksEntry.compareTo(bookmarksEntry);
	}

	@Override
	public int hashCode() {
		return _bookmarksEntry.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.bookmarks.model.BookmarksEntry> toCacheModel() {
		return _bookmarksEntry.toCacheModel();
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry toEscapedModel() {
		return new BookmarksEntryWrapper(_bookmarksEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _bookmarksEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _bookmarksEntry.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_bookmarksEntry.persist();
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder getFolder() {
		return _bookmarksEntry.getFolder();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public BookmarksEntry getWrappedBookmarksEntry() {
		return _bookmarksEntry;
	}

	public BookmarksEntry getWrappedModel() {
		return _bookmarksEntry;
	}

	public void resetOriginalValues() {
		_bookmarksEntry.resetOriginalValues();
	}

	private BookmarksEntry _bookmarksEntry;
}