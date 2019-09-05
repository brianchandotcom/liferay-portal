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

package com.liferay.mail.reader.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Folder}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Folder
 * @generated
 */
public class FolderWrapper
	extends BaseModelWrapper<Folder> implements Folder, ModelWrapper<Folder> {

	public FolderWrapper(Folder folder) {
		super(folder);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("folderId", getFolderId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("accountId", getAccountId());
		attributes.put("fullName", getFullName());
		attributes.put("displayName", getDisplayName());
		attributes.put("remoteMessageCount", getRemoteMessageCount());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
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

		Long accountId = (Long)attributes.get("accountId");

		if (accountId != null) {
			setAccountId(accountId);
		}

		String fullName = (String)attributes.get("fullName");

		if (fullName != null) {
			setFullName(fullName);
		}

		String displayName = (String)attributes.get("displayName");

		if (displayName != null) {
			setDisplayName(displayName);
		}

		Integer remoteMessageCount = (Integer)attributes.get(
			"remoteMessageCount");

		if (remoteMessageCount != null) {
			setRemoteMessageCount(remoteMessageCount);
		}
	}

	/**
	 * Returns the account ID of this folder.
	 *
	 * @return the account ID of this folder
	 */
	@Override
	public long getAccountId() {
		return model.getAccountId();
	}

	/**
	 * Returns the company ID of this folder.
	 *
	 * @return the company ID of this folder
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this folder.
	 *
	 * @return the create date of this folder
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the display name of this folder.
	 *
	 * @return the display name of this folder
	 */
	@Override
	public String getDisplayName() {
		return model.getDisplayName();
	}

	/**
	 * Returns the folder ID of this folder.
	 *
	 * @return the folder ID of this folder
	 */
	@Override
	public long getFolderId() {
		return model.getFolderId();
	}

	/**
	 * Returns the full name of this folder.
	 *
	 * @return the full name of this folder
	 */
	@Override
	public String getFullName() {
		return model.getFullName();
	}

	/**
	 * Returns the modified date of this folder.
	 *
	 * @return the modified date of this folder
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this folder.
	 *
	 * @return the primary key of this folder
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the remote message count of this folder.
	 *
	 * @return the remote message count of this folder
	 */
	@Override
	public int getRemoteMessageCount() {
		return model.getRemoteMessageCount();
	}

	/**
	 * Returns the user ID of this folder.
	 *
	 * @return the user ID of this folder
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this folder.
	 *
	 * @return the user name of this folder
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this folder.
	 *
	 * @return the user uuid of this folder
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account ID of this folder.
	 *
	 * @param accountId the account ID of this folder
	 */
	@Override
	public void setAccountId(long accountId) {
		model.setAccountId(accountId);
	}

	/**
	 * Sets the company ID of this folder.
	 *
	 * @param companyId the company ID of this folder
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this folder.
	 *
	 * @param createDate the create date of this folder
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the display name of this folder.
	 *
	 * @param displayName the display name of this folder
	 */
	@Override
	public void setDisplayName(String displayName) {
		model.setDisplayName(displayName);
	}

	/**
	 * Sets the folder ID of this folder.
	 *
	 * @param folderId the folder ID of this folder
	 */
	@Override
	public void setFolderId(long folderId) {
		model.setFolderId(folderId);
	}

	/**
	 * Sets the full name of this folder.
	 *
	 * @param fullName the full name of this folder
	 */
	@Override
	public void setFullName(String fullName) {
		model.setFullName(fullName);
	}

	/**
	 * Sets the modified date of this folder.
	 *
	 * @param modifiedDate the modified date of this folder
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this folder.
	 *
	 * @param primaryKey the primary key of this folder
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the remote message count of this folder.
	 *
	 * @param remoteMessageCount the remote message count of this folder
	 */
	@Override
	public void setRemoteMessageCount(int remoteMessageCount) {
		model.setRemoteMessageCount(remoteMessageCount);
	}

	/**
	 * Sets the user ID of this folder.
	 *
	 * @param userId the user ID of this folder
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this folder.
	 *
	 * @param userName the user name of this folder
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this folder.
	 *
	 * @param userUuid the user uuid of this folder
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected FolderWrapper wrap(Folder folder) {
		return new FolderWrapper(folder);
	}

}