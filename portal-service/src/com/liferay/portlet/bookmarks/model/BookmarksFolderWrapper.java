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
 * This class is a wrapper for {@link BookmarksFolder}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolder
 * @generated
 */
public class BookmarksFolderWrapper implements BookmarksFolder,
	ModelWrapper<BookmarksFolder> {
	public BookmarksFolderWrapper(BookmarksFolder bookmarksFolder) {
		_bookmarksFolder = bookmarksFolder;
	}

	@Override
	public Class<?> getModelClass() {
		return BookmarksFolder.class;
	}

	@Override
	public String getModelClassName() {
		return BookmarksFolder.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("folderId", getFolderId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("resourceBlockId", getResourceBlockId());
		attributes.put("parentFolderId", getParentFolderId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
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

		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
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

		Long parentFolderId = (Long)attributes.get("parentFolderId");

		if (parentFolderId != null) {
			setParentFolderId(parentFolderId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
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
	* Returns the primary key of this bookmarks folder.
	*
	* @return the primary key of this bookmarks folder
	*/
	@Override
	public long getPrimaryKey() {
		return _bookmarksFolder.getPrimaryKey();
	}

	/**
	* Sets the primary key of this bookmarks folder.
	*
	* @param primaryKey the primary key of this bookmarks folder
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_bookmarksFolder.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this bookmarks folder.
	*
	* @return the uuid of this bookmarks folder
	*/
	@Override
	public java.lang.String getUuid() {
		return _bookmarksFolder.getUuid();
	}

	/**
	* Sets the uuid of this bookmarks folder.
	*
	* @param uuid the uuid of this bookmarks folder
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_bookmarksFolder.setUuid(uuid);
	}

	/**
	* Returns the folder ID of this bookmarks folder.
	*
	* @return the folder ID of this bookmarks folder
	*/
	@Override
	public long getFolderId() {
		return _bookmarksFolder.getFolderId();
	}

	/**
	* Sets the folder ID of this bookmarks folder.
	*
	* @param folderId the folder ID of this bookmarks folder
	*/
	@Override
	public void setFolderId(long folderId) {
		_bookmarksFolder.setFolderId(folderId);
	}

	/**
	* Returns the group ID of this bookmarks folder.
	*
	* @return the group ID of this bookmarks folder
	*/
	@Override
	public long getGroupId() {
		return _bookmarksFolder.getGroupId();
	}

	/**
	* Sets the group ID of this bookmarks folder.
	*
	* @param groupId the group ID of this bookmarks folder
	*/
	@Override
	public void setGroupId(long groupId) {
		_bookmarksFolder.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this bookmarks folder.
	*
	* @return the company ID of this bookmarks folder
	*/
	@Override
	public long getCompanyId() {
		return _bookmarksFolder.getCompanyId();
	}

	/**
	* Sets the company ID of this bookmarks folder.
	*
	* @param companyId the company ID of this bookmarks folder
	*/
	@Override
	public void setCompanyId(long companyId) {
		_bookmarksFolder.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this bookmarks folder.
	*
	* @return the user ID of this bookmarks folder
	*/
	@Override
	public long getUserId() {
		return _bookmarksFolder.getUserId();
	}

	/**
	* Sets the user ID of this bookmarks folder.
	*
	* @param userId the user ID of this bookmarks folder
	*/
	@Override
	public void setUserId(long userId) {
		_bookmarksFolder.setUserId(userId);
	}

	/**
	* Returns the user uuid of this bookmarks folder.
	*
	* @return the user uuid of this bookmarks folder
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolder.getUserUuid();
	}

	/**
	* Sets the user uuid of this bookmarks folder.
	*
	* @param userUuid the user uuid of this bookmarks folder
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_bookmarksFolder.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this bookmarks folder.
	*
	* @return the user name of this bookmarks folder
	*/
	@Override
	public java.lang.String getUserName() {
		return _bookmarksFolder.getUserName();
	}

	/**
	* Sets the user name of this bookmarks folder.
	*
	* @param userName the user name of this bookmarks folder
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_bookmarksFolder.setUserName(userName);
	}

	/**
	* Returns the create date of this bookmarks folder.
	*
	* @return the create date of this bookmarks folder
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _bookmarksFolder.getCreateDate();
	}

	/**
	* Sets the create date of this bookmarks folder.
	*
	* @param createDate the create date of this bookmarks folder
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_bookmarksFolder.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this bookmarks folder.
	*
	* @return the modified date of this bookmarks folder
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _bookmarksFolder.getModifiedDate();
	}

	/**
	* Sets the modified date of this bookmarks folder.
	*
	* @param modifiedDate the modified date of this bookmarks folder
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_bookmarksFolder.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the resource block ID of this bookmarks folder.
	*
	* @return the resource block ID of this bookmarks folder
	*/
	@Override
	public long getResourceBlockId() {
		return _bookmarksFolder.getResourceBlockId();
	}

	/**
	* Sets the resource block ID of this bookmarks folder.
	*
	* @param resourceBlockId the resource block ID of this bookmarks folder
	*/
	@Override
	public void setResourceBlockId(long resourceBlockId) {
		_bookmarksFolder.setResourceBlockId(resourceBlockId);
	}

	/**
	* Returns the parent folder ID of this bookmarks folder.
	*
	* @return the parent folder ID of this bookmarks folder
	*/
	@Override
	public long getParentFolderId() {
		return _bookmarksFolder.getParentFolderId();
	}

	/**
	* Sets the parent folder ID of this bookmarks folder.
	*
	* @param parentFolderId the parent folder ID of this bookmarks folder
	*/
	@Override
	public void setParentFolderId(long parentFolderId) {
		_bookmarksFolder.setParentFolderId(parentFolderId);
	}

	/**
	* Returns the name of this bookmarks folder.
	*
	* @return the name of this bookmarks folder
	*/
	@Override
	public java.lang.String getName() {
		return _bookmarksFolder.getName();
	}

	/**
	* Sets the name of this bookmarks folder.
	*
	* @param name the name of this bookmarks folder
	*/
	@Override
	public void setName(java.lang.String name) {
		_bookmarksFolder.setName(name);
	}

	/**
	* Returns the description of this bookmarks folder.
	*
	* @return the description of this bookmarks folder
	*/
	@Override
	public java.lang.String getDescription() {
		return _bookmarksFolder.getDescription();
	}

	/**
	* Sets the description of this bookmarks folder.
	*
	* @param description the description of this bookmarks folder
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_bookmarksFolder.setDescription(description);
	}

	/**
	* Returns the status of this bookmarks folder.
	*
	* @return the status of this bookmarks folder
	*/
	@Override
	public int getStatus() {
		return _bookmarksFolder.getStatus();
	}

	/**
	* Sets the status of this bookmarks folder.
	*
	* @param status the status of this bookmarks folder
	*/
	@Override
	public void setStatus(int status) {
		_bookmarksFolder.setStatus(status);
	}

	/**
	* Returns the status by user ID of this bookmarks folder.
	*
	* @return the status by user ID of this bookmarks folder
	*/
	@Override
	public long getStatusByUserId() {
		return _bookmarksFolder.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this bookmarks folder.
	*
	* @param statusByUserId the status by user ID of this bookmarks folder
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_bookmarksFolder.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this bookmarks folder.
	*
	* @return the status by user uuid of this bookmarks folder
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolder.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this bookmarks folder.
	*
	* @param statusByUserUuid the status by user uuid of this bookmarks folder
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_bookmarksFolder.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this bookmarks folder.
	*
	* @return the status by user name of this bookmarks folder
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _bookmarksFolder.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this bookmarks folder.
	*
	* @param statusByUserName the status by user name of this bookmarks folder
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_bookmarksFolder.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this bookmarks folder.
	*
	* @return the status date of this bookmarks folder
	*/
	@Override
	public java.util.Date getStatusDate() {
		return _bookmarksFolder.getStatusDate();
	}

	/**
	* Sets the status date of this bookmarks folder.
	*
	* @param statusDate the status date of this bookmarks folder
	*/
	@Override
	public void setStatusDate(java.util.Date statusDate) {
		_bookmarksFolder.setStatusDate(statusDate);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #isApproved()}
	*/
	@Override
	public boolean getApproved() {
		return _bookmarksFolder.getApproved();
	}

	/**
	* Returns <code>true</code> if this bookmarks folder is approved.
	*
	* @return <code>true</code> if this bookmarks folder is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _bookmarksFolder.isApproved();
	}

	/**
	* Returns <code>true</code> if this bookmarks folder is denied.
	*
	* @return <code>true</code> if this bookmarks folder is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _bookmarksFolder.isDenied();
	}

	/**
	* Returns <code>true</code> if this bookmarks folder is a draft.
	*
	* @return <code>true</code> if this bookmarks folder is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _bookmarksFolder.isDraft();
	}

	/**
	* Returns <code>true</code> if this bookmarks folder is expired.
	*
	* @return <code>true</code> if this bookmarks folder is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _bookmarksFolder.isExpired();
	}

	/**
	* Returns <code>true</code> if this bookmarks folder is inactive.
	*
	* @return <code>true</code> if this bookmarks folder is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _bookmarksFolder.isInactive();
	}

	/**
	* Returns <code>true</code> if this bookmarks folder is incomplete.
	*
	* @return <code>true</code> if this bookmarks folder is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _bookmarksFolder.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this bookmarks folder is in the Recycle Bin.
	*
	* @return <code>true</code> if this bookmarks folder is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _bookmarksFolder.isInTrash();
	}

	/**
	* Returns <code>true</code> if this bookmarks folder is pending.
	*
	* @return <code>true</code> if this bookmarks folder is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _bookmarksFolder.isPending();
	}

	/**
	* Returns <code>true</code> if this bookmarks folder is scheduled.
	*
	* @return <code>true</code> if this bookmarks folder is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _bookmarksFolder.isScheduled();
	}

	/**
	* Returns the container model ID of this bookmarks folder.
	*
	* @return the container model ID of this bookmarks folder
	*/
	@Override
	public long getContainerModelId() {
		return _bookmarksFolder.getContainerModelId();
	}

	/**
	* Sets the container model ID of this bookmarks folder.
	*
	* @param container model ID of this bookmarks folder
	*/
	@Override
	public void setContainerModelId(long containerModelId) {
		_bookmarksFolder.setContainerModelId(containerModelId);
	}

	/**
	* Returns the container name of this bookmarks folder.
	*
	* @return the container name of this bookmarks folder
	*/
	@Override
	public java.lang.String getContainerModelName() {
		return _bookmarksFolder.getContainerModelName();
	}

	/**
	* Returns the parent container model ID of this bookmarks folder.
	*
	* @return the parent container model ID of this bookmarks folder
	*/
	@Override
	public long getParentContainerModelId() {
		return _bookmarksFolder.getParentContainerModelId();
	}

	/**
	* Sets the parent container model ID of this bookmarks folder.
	*
	* @param parent container model ID of this bookmarks folder
	*/
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		_bookmarksFolder.setParentContainerModelId(parentContainerModelId);
	}

	@Override
	public boolean isNew() {
		return _bookmarksFolder.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_bookmarksFolder.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _bookmarksFolder.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_bookmarksFolder.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _bookmarksFolder.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _bookmarksFolder.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_bookmarksFolder.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _bookmarksFolder.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_bookmarksFolder.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_bookmarksFolder.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_bookmarksFolder.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new BookmarksFolderWrapper((BookmarksFolder)_bookmarksFolder.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder) {
		return _bookmarksFolder.compareTo(bookmarksFolder);
	}

	@Override
	public int hashCode() {
		return _bookmarksFolder.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.bookmarks.model.BookmarksFolder> toCacheModel() {
		return _bookmarksFolder.toCacheModel();
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder toEscapedModel() {
		return new BookmarksFolderWrapper(_bookmarksFolder.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder toUnescapedModel() {
		return new BookmarksFolderWrapper(_bookmarksFolder.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _bookmarksFolder.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _bookmarksFolder.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolder.persist();
	}

	@Override
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolder.getAncestors();
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolder.getParentFolder();
	}

	@Override
	public com.liferay.portlet.bookmarks.model.BookmarksFolder getTrashContainer() {
		return _bookmarksFolder.getTrashContainer();
	}

	@Override
	public boolean isInTrashContainer() {
		return _bookmarksFolder.isInTrashContainer();
	}

	@Override
	public boolean isRoot() {
		return _bookmarksFolder.isRoot();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public BookmarksFolder getWrappedBookmarksFolder() {
		return _bookmarksFolder;
	}

	@Override
	public BookmarksFolder getWrappedModel() {
		return _bookmarksFolder;
	}

	@Override
	public void resetOriginalValues() {
		_bookmarksFolder.resetOriginalValues();
	}

	private BookmarksFolder _bookmarksFolder;
}