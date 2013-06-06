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

package com.liferay.portlet.bookmarks.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BookmarksEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntry
 * @generated
 */
public class BookmarksEntryWrapper implements BookmarksEntry,
	ModelWrapper<BookmarksEntry> {
	public BookmarksEntryWrapper(BookmarksEntry bookmarksEntry) {
		_bookmarksEntry = bookmarksEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return BookmarksEntry.class;
	}

	@Override
	public String getModelClassName() {
		return BookmarksEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("entryId", getEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("resourceBlockId", getResourceBlockId());
		attributes.put("folderId", getFolderId());
		attributes.put("name", getName());
		attributes.put("url", getUrl());
		attributes.put("description", getDescription());
		attributes.put("visits", getVisits());
		attributes.put("priority", getPriority());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long resourceBlockId = (Long)attributes.get("resourceBlockId");

		if (resourceBlockId != null) {
			setResourceBlockId(resourceBlockId);
		}

		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer visits = (Integer)attributes.get("visits");

		if (visits != null) {
			setVisits(visits);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	/**
	* Returns the primary key of this bookmarks entry.
	*
	* @return the primary key of this bookmarks entry
	*/
	@Override
	public long getPrimaryKey() {
		return _bookmarksEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this bookmarks entry.
	*
	* @param primaryKey the primary key of this bookmarks entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_bookmarksEntry.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this bookmarks entry.
	*
	* @return the uuid of this bookmarks entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _bookmarksEntry.getUuid();
	}

	/**
	* Sets the uuid of this bookmarks entry.
	*
	* @param uuid the uuid of this bookmarks entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_bookmarksEntry.setUuid(uuid);
	}

	/**
	* Returns the entry ID of this bookmarks entry.
	*
	* @return the entry ID of this bookmarks entry
	*/
	@Override
	public long getEntryId() {
		return _bookmarksEntry.getEntryId();
	}

	/**
	* Sets the entry ID of this bookmarks entry.
	*
	* @param entryId the entry ID of this bookmarks entry
	*/
	@Override
	public void setEntryId(long entryId) {
		_bookmarksEntry.setEntryId(entryId);
	}

	/**
	* Returns the group ID of this bookmarks entry.
	*
	* @return the group ID of this bookmarks entry
	*/
	@Override
	public long getGroupId() {
		return _bookmarksEntry.getGroupId();
	}

	/**
	* Sets the group ID of this bookmarks entry.
	*
	* @param groupId the group ID of this bookmarks entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_bookmarksEntry.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this bookmarks entry.
	*
	* @return the company ID of this bookmarks entry
	*/
	@Override
	public long getCompanyId() {
		return _bookmarksEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this bookmarks entry.
	*
	* @param companyId the company ID of this bookmarks entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_bookmarksEntry.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this bookmarks entry.
	*
	* @return the user ID of this bookmarks entry
	*/
	@Override
	public long getUserId() {
		return _bookmarksEntry.getUserId();
	}

	/**
	* Sets the user ID of this bookmarks entry.
	*
	* @param userId the user ID of this bookmarks entry
	*/
	@Override
	public void setUserId(long userId) {
		_bookmarksEntry.setUserId(userId);
	}

	/**
	* Returns the user uuid of this bookmarks entry.
	*
	* @return the user uuid of this bookmarks entry
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this bookmarks entry.
	*
	* @param userUuid the user uuid of this bookmarks entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_bookmarksEntry.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this bookmarks entry.
	*
	* @return the user name of this bookmarks entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _bookmarksEntry.getUserName();
	}

	/**
	* Sets the user name of this bookmarks entry.
	*
	* @param userName the user name of this bookmarks entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_bookmarksEntry.setUserName(userName);
	}

	/**
	* Returns the create date of this bookmarks entry.
	*
	* @return the create date of this bookmarks entry
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _bookmarksEntry.getCreateDate();
	}

	/**
	* Sets the create date of this bookmarks entry.
	*
	* @param createDate the create date of this bookmarks entry
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_bookmarksEntry.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this bookmarks entry.
	*
	* @return the modified date of this bookmarks entry
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _bookmarksEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this bookmarks entry.
	*
	* @param modifiedDate the modified date of this bookmarks entry
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_bookmarksEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the resource block ID of this bookmarks entry.
	*
	* @return the resource block ID of this bookmarks entry
	*/
	@Override
	public long getResourceBlockId() {
		return _bookmarksEntry.getResourceBlockId();
	}

	/**
	* Sets the resource block ID of this bookmarks entry.
	*
	* @param resourceBlockId the resource block ID of this bookmarks entry
	*/
	@Override
	public void setResourceBlockId(long resourceBlockId) {
		_bookmarksEntry.setResourceBlockId(resourceBlockId);
	}

	/**
	* Returns the folder ID of this bookmarks entry.
	*
	* @return the folder ID of this bookmarks entry
	*/
	@Override
	public long getFolderId() {
		return _bookmarksEntry.getFolderId();
	}

	/**
	* Sets the folder ID of this bookmarks entry.
	*
	* @param folderId the folder ID of this bookmarks entry
	*/
	@Override
	public void setFolderId(long folderId) {
		_bookmarksEntry.setFolderId(folderId);
	}

	/**
	* Returns the name of this bookmarks entry.
	*
	* @return the name of this bookmarks entry
	*/
	@Override
	public java.lang.String getName() {
		return _bookmarksEntry.getName();
	}

	/**
	* Sets the name of this bookmarks entry.
	*
	* @param name the name of this bookmarks entry
	*/
	@Override
	public void setName(java.lang.String name) {
		_bookmarksEntry.setName(name);
	}

	/**
	* Returns the url of this bookmarks entry.
	*
	* @return the url of this bookmarks entry
	*/
	@Override
	public java.lang.String getUrl() {
		return _bookmarksEntry.getUrl();
	}

	/**
	* Sets the url of this bookmarks entry.
	*
	* @param url the url of this bookmarks entry
	*/
	@Override
	public void setUrl(java.lang.String url) {
		_bookmarksEntry.setUrl(url);
	}

	/**
	* Returns the description of this bookmarks entry.
	*
	* @return the description of this bookmarks entry
	*/
	@Override
	public java.lang.String getDescription() {
		return _bookmarksEntry.getDescription();
	}

	/**
	* Sets the description of this bookmarks entry.
	*
	* @param description the description of this bookmarks entry
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_bookmarksEntry.setDescription(description);
	}

	/**
	* Returns the visits of this bookmarks entry.
	*
	* @return the visits of this bookmarks entry
	*/
	@Override
	public int getVisits() {
		return _bookmarksEntry.getVisits();
	}

	/**
	* Sets the visits of this bookmarks entry.
	*
	* @param visits the visits of this bookmarks entry
	*/
	@Override
	public void setVisits(int visits) {
		_bookmarksEntry.setVisits(visits);
	}

	/**
	* Returns the priority of this bookmarks entry.
	*
	* @return the priority of this bookmarks entry
	*/
	@Override
	public int getPriority() {
		return _bookmarksEntry.getPriority();
	}

	/**
	* Sets the priority of this bookmarks entry.
	*
	* @param priority the priority of this bookmarks entry
	*/
	@Override
	public void setPriority(int priority) {
		_bookmarksEntry.setPriority(priority);
	}

	/**
	* Returns the status of this bookmarks entry.
	*
	* @return the status of this bookmarks entry
	*/
	@Override
	public int getStatus() {
		return _bookmarksEntry.getStatus();
	}

	/**
	* Sets the status of this bookmarks entry.
	*
	* @param status the status of this bookmarks entry
	*/
	@Override
	public void setStatus(int status) {
		_bookmarksEntry.setStatus(status);
	}

	/**
	* Returns the status by user ID of this bookmarks entry.
	*
	* @return the status by user ID of this bookmarks entry
	*/
	@Override
	public long getStatusByUserId() {
		return _bookmarksEntry.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this bookmarks entry.
	*
	* @param statusByUserId the status by user ID of this bookmarks entry
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_bookmarksEntry.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this bookmarks entry.
	*
	* @return the status by user uuid of this bookmarks entry
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntry.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this bookmarks entry.
	*
	* @param statusByUserUuid the status by user uuid of this bookmarks entry
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_bookmarksEntry.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this bookmarks entry.
	*
	* @return the status by user name of this bookmarks entry
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _bookmarksEntry.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this bookmarks entry.
	*
	* @param statusByUserName the status by user name of this bookmarks entry
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_bookmarksEntry.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this bookmarks entry.
	*
	* @return the status date of this bookmarks entry
	*/
	@Override
	public java.util.Date getStatusDate() {
		return _bookmarksEntry.getStatusDate();
	}

	/**
	* Sets the status date of this bookmarks entry.
	*
	* @param statusDate the status date of this bookmarks entry
	*/
	@Override
	public void setStatusDate(java.util.Date statusDate) {
		_bookmarksEntry.setStatusDate(statusDate);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #isApproved()}
	*/
	@Override
	public boolean getApproved() {
		return _bookmarksEntry.getApproved();
	}

	/**
	* Returns <code>true</code> if this bookmarks entry is approved.
	*
	* @return <code>true</code> if this bookmarks entry is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _bookmarksEntry.isApproved();
	}

	/**
	* Returns <code>true</code> if this bookmarks entry is denied.
	*
	* @return <code>true</code> if this bookmarks entry is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _bookmarksEntry.isDenied();
	}

	/**
	* Returns <code>true</code> if this bookmarks entry is a draft.
	*
	* @return <code>true</code> if this bookmarks entry is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _bookmarksEntry.isDraft();
	}

	/**
	* Returns <code>true</code> if this bookmarks entry is expired.
	*
	* @return <code>true</code> if this bookmarks entry is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _bookmarksEntry.isExpired();
	}

	/**
	* Returns <code>true</code> if this bookmarks entry is inactive.
	*
	* @return <code>true</code> if this bookmarks entry is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _bookmarksEntry.isInactive();
	}

	/**
	* Returns <code>true</code> if this bookmarks entry is incomplete.
	*
	* @return <code>true</code> if this bookmarks entry is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _bookmarksEntry.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this bookmarks entry is in the Recycle Bin.
	*
	* @return <code>true</code> if this bookmarks entry is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _bookmarksEntry.isInTrash();
	}

	/**
	* Returns <code>true</code> if this bookmarks entry is pending.
	*
	* @return <code>true</code> if this bookmarks entry is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _bookmarksEntry.isPending();
	}

	/**
	* Returns <code>true</code> if this bookmarks entry is scheduled.
	*
	* @return <code>true</code> if this bookmarks entry is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _bookmarksEntry.isScheduled();
	}

	@Override
	public boolean isNew() {
		return _bookmarksEntry.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_bookmarksEntry.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _bookmarksEntry.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_bookmarksEntry.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _bookmarksEntry.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _bookmarksEntry.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_bookmarksEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _bookmarksEntry.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_bookmarksEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_bookmarksEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_bookmarksEntry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new BookmarksEntryWrapper((BookmarksEntry)_bookmarksEntry.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry) {
		return _bookmarksEntry.compareTo(bookmarksEntry);
	}

	@Override
	public int hashCode() {
		return _bookmarksEntry.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.bookmarks.model.BookmarksEntry> toCacheModel() {
		return _bookmarksEntry.toCacheModel();
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksEntry toEscapedModel() {
		return new BookmarksEntryWrapper(_bookmarksEntry.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksEntry toUnescapedModel() {
		return new BookmarksEntryWrapper(_bookmarksEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _bookmarksEntry.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _bookmarksEntry.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_bookmarksEntry.persist();
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder getFolder() {
		return _bookmarksEntry.getFolder();
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder getTrashContainer() {
		return _bookmarksEntry.getTrashContainer();
	}

	@Override
	public boolean isInTrashContainer() {
		return _bookmarksEntry.isInTrashContainer();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public BookmarksEntry getWrappedBookmarksEntry() {
		return _bookmarksEntry;
	}

	@Override
	public BookmarksEntry getWrappedModel() {
		return _bookmarksEntry;
	}

	@Override
	public void resetOriginalValues() {
		_bookmarksEntry.resetOriginalValues();
	}

	private BookmarksEntry _bookmarksEntry;
}