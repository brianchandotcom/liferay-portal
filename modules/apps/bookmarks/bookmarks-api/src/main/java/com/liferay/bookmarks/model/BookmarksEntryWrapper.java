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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class BookmarksEntryWrapper
	extends BaseModelWrapper<BookmarksEntry>
	implements BookmarksEntry, ModelWrapper<BookmarksEntry> {

	public BookmarksEntryWrapper(BookmarksEntry bookmarksEntry) {
		super(bookmarksEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("entryId", getEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.buildTreePath();
	}

	/**
	 * Returns the company ID of this bookmarks entry.
	 *
	 * @return the company ID of this bookmarks entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this bookmarks entry.
	 *
	 * @return the create date of this bookmarks entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this bookmarks entry.
	 *
	 * @return the description of this bookmarks entry
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the entry ID of this bookmarks entry.
	 *
	 * @return the entry ID of this bookmarks entry
	 */
	@Override
	public long getEntryId() {
		return model.getEntryId();
	}

	@Override
	public BookmarksFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFolder();
	}

	/**
	 * Returns the folder ID of this bookmarks entry.
	 *
	 * @return the folder ID of this bookmarks entry
	 */
	@Override
	public long getFolderId() {
		return model.getFolderId();
	}

	/**
	 * Returns the group ID of this bookmarks entry.
	 *
	 * @return the group ID of this bookmarks entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this bookmarks entry.
	 *
	 * @return the last publish date of this bookmarks entry
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this bookmarks entry.
	 *
	 * @return the modified date of this bookmarks entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this bookmarks entry.
	 *
	 * @return the mvcc version of this bookmarks entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this bookmarks entry.
	 *
	 * @return the name of this bookmarks entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this bookmarks entry.
	 *
	 * @return the primary key of this bookmarks entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this bookmarks entry.
	 *
	 * @return the priority of this bookmarks entry
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the status of this bookmarks entry.
	 *
	 * @return the status of this bookmarks entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this bookmarks entry.
	 *
	 * @return the status by user ID of this bookmarks entry
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this bookmarks entry.
	 *
	 * @return the status by user name of this bookmarks entry
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this bookmarks entry.
	 *
	 * @return the status by user uuid of this bookmarks entry
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this bookmarks entry.
	 *
	 * @return the status date of this bookmarks entry
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the trash entry created when this bookmarks entry was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this bookmarks entry.
	 *
	 * @return the trash entry created when this bookmarks entry was moved to the Recycle Bin
	 */
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTrashEntry();
	}

	/**
	 * Returns the class primary key of the trash entry for this bookmarks entry.
	 *
	 * @return the class primary key of the trash entry for this bookmarks entry
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the trash handler for this bookmarks entry.
	 *
	 * @return the trash handler for this bookmarks entry
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return model.getTrashHandler();
	}

	/**
	 * Returns the tree path of this bookmarks entry.
	 *
	 * @return the tree path of this bookmarks entry
	 */
	@Override
	public String getTreePath() {
		return model.getTreePath();
	}

	/**
	 * Returns the url of this bookmarks entry.
	 *
	 * @return the url of this bookmarks entry
	 */
	@Override
	public String getUrl() {
		return model.getUrl();
	}

	/**
	 * Returns the user ID of this bookmarks entry.
	 *
	 * @return the user ID of this bookmarks entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this bookmarks entry.
	 *
	 * @return the user name of this bookmarks entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this bookmarks entry.
	 *
	 * @return the user uuid of this bookmarks entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this bookmarks entry.
	 *
	 * @return the uuid of this bookmarks entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the visits of this bookmarks entry.
	 *
	 * @return the visits of this bookmarks entry
	 */
	@Override
	public int getVisits() {
		return model.getVisits();
	}

	/**
	 * Returns <code>true</code> if this bookmarks entry is approved.
	 *
	 * @return <code>true</code> if this bookmarks entry is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this bookmarks entry is denied.
	 *
	 * @return <code>true</code> if this bookmarks entry is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this bookmarks entry is a draft.
	 *
	 * @return <code>true</code> if this bookmarks entry is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this bookmarks entry is expired.
	 *
	 * @return <code>true</code> if this bookmarks entry is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this bookmarks entry is inactive.
	 *
	 * @return <code>true</code> if this bookmarks entry is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this bookmarks entry is incomplete.
	 *
	 * @return <code>true</code> if this bookmarks entry is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this bookmarks entry is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this bookmarks entry is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	/**
	 * Returns <code>true</code> if the parent of this bookmarks entry is in the Recycle Bin.
	 *
	 * @return <code>true</code> if the parent of this bookmarks entry is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrashContainer() {
		return model.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return model.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return model.isInTrashImplicitly();
	}

	/**
	 * Returns <code>true</code> if this bookmarks entry is pending.
	 *
	 * @return <code>true</code> if this bookmarks entry is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this bookmarks entry is scheduled.
	 *
	 * @return <code>true</code> if this bookmarks entry is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a bookmarks entry model instance should use the <code>BookmarksEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this bookmarks entry.
	 *
	 * @param companyId the company ID of this bookmarks entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this bookmarks entry.
	 *
	 * @param createDate the create date of this bookmarks entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this bookmarks entry.
	 *
	 * @param description the description of this bookmarks entry
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the entry ID of this bookmarks entry.
	 *
	 * @param entryId the entry ID of this bookmarks entry
	 */
	@Override
	public void setEntryId(long entryId) {
		model.setEntryId(entryId);
	}

	/**
	 * Sets the folder ID of this bookmarks entry.
	 *
	 * @param folderId the folder ID of this bookmarks entry
	 */
	@Override
	public void setFolderId(long folderId) {
		model.setFolderId(folderId);
	}

	/**
	 * Sets the group ID of this bookmarks entry.
	 *
	 * @param groupId the group ID of this bookmarks entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this bookmarks entry.
	 *
	 * @param lastPublishDate the last publish date of this bookmarks entry
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this bookmarks entry.
	 *
	 * @param modifiedDate the modified date of this bookmarks entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this bookmarks entry.
	 *
	 * @param mvccVersion the mvcc version of this bookmarks entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this bookmarks entry.
	 *
	 * @param name the name of this bookmarks entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this bookmarks entry.
	 *
	 * @param primaryKey the primary key of this bookmarks entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this bookmarks entry.
	 *
	 * @param priority the priority of this bookmarks entry
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the status of this bookmarks entry.
	 *
	 * @param status the status of this bookmarks entry
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this bookmarks entry.
	 *
	 * @param statusByUserId the status by user ID of this bookmarks entry
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this bookmarks entry.
	 *
	 * @param statusByUserName the status by user name of this bookmarks entry
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this bookmarks entry.
	 *
	 * @param statusByUserUuid the status by user uuid of this bookmarks entry
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this bookmarks entry.
	 *
	 * @param statusDate the status date of this bookmarks entry
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the tree path of this bookmarks entry.
	 *
	 * @param treePath the tree path of this bookmarks entry
	 */
	@Override
	public void setTreePath(String treePath) {
		model.setTreePath(treePath);
	}

	/**
	 * Sets the url of this bookmarks entry.
	 *
	 * @param url the url of this bookmarks entry
	 */
	@Override
	public void setUrl(String url) {
		model.setUrl(url);
	}

	/**
	 * Sets the user ID of this bookmarks entry.
	 *
	 * @param userId the user ID of this bookmarks entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this bookmarks entry.
	 *
	 * @param userName the user name of this bookmarks entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this bookmarks entry.
	 *
	 * @param userUuid the user uuid of this bookmarks entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this bookmarks entry.
	 *
	 * @param uuid the uuid of this bookmarks entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the visits of this bookmarks entry.
	 *
	 * @param visits the visits of this bookmarks entry
	 */
	@Override
	public void setVisits(int visits) {
		model.setVisits(visits);
	}

	@Override
	public void updateTreePath(String treePath) {
		model.updateTreePath(treePath);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected BookmarksEntryWrapper wrap(BookmarksEntry bookmarksEntry) {
		return new BookmarksEntryWrapper(bookmarksEntry);
	}

}