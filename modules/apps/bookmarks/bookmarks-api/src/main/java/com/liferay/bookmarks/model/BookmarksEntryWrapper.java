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

package com.liferay.bookmarks.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.io.Serializable;

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
@ProviderType
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
		attributes.put("treePath", getTreePath());
		attributes.put("name", getName());
		attributes.put("url", getUrl());
		attributes.put("description", getDescription());
		attributes.put("visits", getVisits());
		attributes.put("priority", getPriority());
		attributes.put("lastPublishDate", getLastPublishDate());
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

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
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

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
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

	@Override
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntry.buildTreePath();
	}

	@Override
	public java.lang.Object clone() {
		return new BookmarksEntryWrapper((BookmarksEntry)_bookmarksEntry.clone());
	}

	@Override
	public int compareTo(
		com.liferay.bookmarks.model.BookmarksEntry bookmarksEntry) {
		return _bookmarksEntry.compareTo(bookmarksEntry);
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
	* Returns the create date of this bookmarks entry.
	*
	* @return the create date of this bookmarks entry
	*/
	@Override
	public Date getCreateDate() {
		return _bookmarksEntry.getCreateDate();
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
	* Returns the entry ID of this bookmarks entry.
	*
	* @return the entry ID of this bookmarks entry
	*/
	@Override
	public long getEntryId() {
		return _bookmarksEntry.getEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _bookmarksEntry.getExpandoBridge();
	}

	@Override
	public com.liferay.bookmarks.model.BookmarksFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntry.getFolder();
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
	* Returns the group ID of this bookmarks entry.
	*
	* @return the group ID of this bookmarks entry
	*/
	@Override
	public long getGroupId() {
		return _bookmarksEntry.getGroupId();
	}

	/**
	* Returns the last publish date of this bookmarks entry.
	*
	* @return the last publish date of this bookmarks entry
	*/
	@Override
	public Date getLastPublishDate() {
		return _bookmarksEntry.getLastPublishDate();
	}

	/**
	* Returns the modified date of this bookmarks entry.
	*
	* @return the modified date of this bookmarks entry
	*/
	@Override
	public Date getModifiedDate() {
		return _bookmarksEntry.getModifiedDate();
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
	* Returns the primary key of this bookmarks entry.
	*
	* @return the primary key of this bookmarks entry
	*/
	@Override
	public long getPrimaryKey() {
		return _bookmarksEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _bookmarksEntry.getPrimaryKeyObj();
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
	* Returns the resource block ID of this bookmarks entry.
	*
	* @return the resource block ID of this bookmarks entry
	*/
	@Override
	public long getResourceBlockId() {
		return _bookmarksEntry.getResourceBlockId();
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
	* Returns the status by user ID of this bookmarks entry.
	*
	* @return the status by user ID of this bookmarks entry
	*/
	@Override
	public long getStatusByUserId() {
		return _bookmarksEntry.getStatusByUserId();
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
	* Returns the status by user uuid of this bookmarks entry.
	*
	* @return the status by user uuid of this bookmarks entry
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _bookmarksEntry.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this bookmarks entry.
	*
	* @return the status date of this bookmarks entry
	*/
	@Override
	public Date getStatusDate() {
		return _bookmarksEntry.getStatusDate();
	}

	/**
	* Returns the trash entry created when this bookmarks entry was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this bookmarks entry.
	*
	* @return the trash entry created when this bookmarks entry was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntry.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this bookmarks entry.
	*
	* @return the class primary key of the trash entry for this bookmarks entry
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _bookmarksEntry.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this bookmarks entry.
	*
	* @return the trash handler for this bookmarks entry
	*/
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _bookmarksEntry.getTrashHandler();
	}

	/**
	* Returns the tree path of this bookmarks entry.
	*
	* @return the tree path of this bookmarks entry
	*/
	@Override
	public java.lang.String getTreePath() {
		return _bookmarksEntry.getTreePath();
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
	* Returns the user ID of this bookmarks entry.
	*
	* @return the user ID of this bookmarks entry
	*/
	@Override
	public long getUserId() {
		return _bookmarksEntry.getUserId();
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
	* Returns the user uuid of this bookmarks entry.
	*
	* @return the user uuid of this bookmarks entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _bookmarksEntry.getUserUuid();
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
	* Returns the visits of this bookmarks entry.
	*
	* @return the visits of this bookmarks entry
	*/
	@Override
	public int getVisits() {
		return _bookmarksEntry.getVisits();
	}

	@Override
	public int hashCode() {
		return _bookmarksEntry.hashCode();
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

	@Override
	public boolean isCachedModel() {
		return _bookmarksEntry.isCachedModel();
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

	@Override
	public boolean isEscapedModel() {
		return _bookmarksEntry.isEscapedModel();
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
	* Returns <code>true</code> if this bookmarks entry is in the Recycle Bin.
	*
	* @return <code>true</code> if this bookmarks entry is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _bookmarksEntry.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this bookmarks entry is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this bookmarks entry is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _bookmarksEntry.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _bookmarksEntry.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _bookmarksEntry.isInTrashImplicitly();
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

	@Override
	public boolean isNew() {
		return _bookmarksEntry.isNew();
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
	public void persist() {
		_bookmarksEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_bookmarksEntry.setCachedModel(cachedModel);
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
	* Sets the create date of this bookmarks entry.
	*
	* @param createDate the create date of this bookmarks entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_bookmarksEntry.setCreateDate(createDate);
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
	* Sets the entry ID of this bookmarks entry.
	*
	* @param entryId the entry ID of this bookmarks entry
	*/
	@Override
	public void setEntryId(long entryId) {
		_bookmarksEntry.setEntryId(entryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_bookmarksEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_bookmarksEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_bookmarksEntry.setExpandoBridgeAttributes(serviceContext);
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
	* Sets the group ID of this bookmarks entry.
	*
	* @param groupId the group ID of this bookmarks entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_bookmarksEntry.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this bookmarks entry.
	*
	* @param lastPublishDate the last publish date of this bookmarks entry
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_bookmarksEntry.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this bookmarks entry.
	*
	* @param modifiedDate the modified date of this bookmarks entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_bookmarksEntry.setModifiedDate(modifiedDate);
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

	@Override
	public void setNew(boolean n) {
		_bookmarksEntry.setNew(n);
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

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_bookmarksEntry.setPrimaryKeyObj(primaryKeyObj);
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
	* Sets the resource block ID of this bookmarks entry.
	*
	* @param resourceBlockId the resource block ID of this bookmarks entry
	*/
	@Override
	public void setResourceBlockId(long resourceBlockId) {
		_bookmarksEntry.setResourceBlockId(resourceBlockId);
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
	* Sets the status by user ID of this bookmarks entry.
	*
	* @param statusByUserId the status by user ID of this bookmarks entry
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_bookmarksEntry.setStatusByUserId(statusByUserId);
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
	* Sets the status by user uuid of this bookmarks entry.
	*
	* @param statusByUserUuid the status by user uuid of this bookmarks entry
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_bookmarksEntry.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this bookmarks entry.
	*
	* @param statusDate the status date of this bookmarks entry
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_bookmarksEntry.setStatusDate(statusDate);
	}

	/**
	* Sets the tree path of this bookmarks entry.
	*
	* @param treePath the tree path of this bookmarks entry
	*/
	@Override
	public void setTreePath(java.lang.String treePath) {
		_bookmarksEntry.setTreePath(treePath);
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
	* Sets the user ID of this bookmarks entry.
	*
	* @param userId the user ID of this bookmarks entry
	*/
	@Override
	public void setUserId(long userId) {
		_bookmarksEntry.setUserId(userId);
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
	* Sets the user uuid of this bookmarks entry.
	*
	* @param userUuid the user uuid of this bookmarks entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_bookmarksEntry.setUserUuid(userUuid);
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
	* Sets the visits of this bookmarks entry.
	*
	* @param visits the visits of this bookmarks entry
	*/
	@Override
	public void setVisits(int visits) {
		_bookmarksEntry.setVisits(visits);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.bookmarks.model.BookmarksEntry> toCacheModel() {
		return _bookmarksEntry.toCacheModel();
	}

	@Override
	public com.liferay.bookmarks.model.BookmarksEntry toEscapedModel() {
		return new BookmarksEntryWrapper(_bookmarksEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _bookmarksEntry.toString();
	}

	@Override
	public com.liferay.bookmarks.model.BookmarksEntry toUnescapedModel() {
		return new BookmarksEntryWrapper(_bookmarksEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _bookmarksEntry.toXmlString();
	}

	@Override
	public void updateTreePath(java.lang.String treePath) {
		_bookmarksEntry.updateTreePath(treePath);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BookmarksEntryWrapper)) {
			return false;
		}

		BookmarksEntryWrapper bookmarksEntryWrapper = (BookmarksEntryWrapper)obj;

		if (Validator.equals(_bookmarksEntry,
					bookmarksEntryWrapper._bookmarksEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _bookmarksEntry.getStagedModelType();
	}

	@Override
	public BookmarksEntry getWrappedModel() {
		return _bookmarksEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _bookmarksEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _bookmarksEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_bookmarksEntry.resetOriginalValues();
	}

	private final BookmarksEntry _bookmarksEntry;
}