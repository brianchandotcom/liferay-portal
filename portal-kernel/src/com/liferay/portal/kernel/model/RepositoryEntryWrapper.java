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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RepositoryEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryEntry
 * @generated
 */
@ProviderType
public class RepositoryEntryWrapper extends BaseModelWrapper<RepositoryEntry>
	implements RepositoryEntry, ModelWrapper<RepositoryEntry> {
	public RepositoryEntryWrapper(RepositoryEntry repositoryEntry) {
		super(repositoryEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("repositoryEntryId", getRepositoryEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("mappedId", getMappedId());
		attributes.put("manualCheckInRequired", isManualCheckInRequired());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Long repositoryEntryId = (Long)attributes.get("repositoryEntryId");

		if (repositoryEntryId != null) {
			setRepositoryEntryId(repositoryEntryId);
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

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
		}

		String mappedId = (String)attributes.get("mappedId");

		if (mappedId != null) {
			setMappedId(mappedId);
		}

		Boolean manualCheckInRequired = (Boolean)attributes.get(
				"manualCheckInRequired");

		if (manualCheckInRequired != null) {
			setManualCheckInRequired(manualCheckInRequired);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	* Returns the company ID of this repository entry.
	*
	* @return the company ID of this repository entry
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this repository entry.
	*
	* @return the create date of this repository entry
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the group ID of this repository entry.
	*
	* @return the group ID of this repository entry
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the last publish date of this repository entry.
	*
	* @return the last publish date of this repository entry
	*/
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	* Returns the manual check in required of this repository entry.
	*
	* @return the manual check in required of this repository entry
	*/
	@Override
	public boolean getManualCheckInRequired() {
		return model.getManualCheckInRequired();
	}

	/**
	* Returns the mapped ID of this repository entry.
	*
	* @return the mapped ID of this repository entry
	*/
	@Override
	public String getMappedId() {
		return model.getMappedId();
	}

	/**
	* Returns the modified date of this repository entry.
	*
	* @return the modified date of this repository entry
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this repository entry.
	*
	* @return the mvcc version of this repository entry
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the primary key of this repository entry.
	*
	* @return the primary key of this repository entry
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the repository entry ID of this repository entry.
	*
	* @return the repository entry ID of this repository entry
	*/
	@Override
	public long getRepositoryEntryId() {
		return model.getRepositoryEntryId();
	}

	/**
	* Returns the repository ID of this repository entry.
	*
	* @return the repository ID of this repository entry
	*/
	@Override
	public long getRepositoryId() {
		return model.getRepositoryId();
	}

	/**
	* Returns the user ID of this repository entry.
	*
	* @return the user ID of this repository entry
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this repository entry.
	*
	* @return the user name of this repository entry
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this repository entry.
	*
	* @return the user uuid of this repository entry
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this repository entry.
	*
	* @return the uuid of this repository entry
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	* Returns <code>true</code> if this repository entry is manual check in required.
	*
	* @return <code>true</code> if this repository entry is manual check in required; <code>false</code> otherwise
	*/
	@Override
	public boolean isManualCheckInRequired() {
		return model.isManualCheckInRequired();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this repository entry.
	*
	* @param companyId the company ID of this repository entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this repository entry.
	*
	* @param createDate the create date of this repository entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the group ID of this repository entry.
	*
	* @param groupId the group ID of this repository entry
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this repository entry.
	*
	* @param lastPublishDate the last publish date of this repository entry
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets whether this repository entry is manual check in required.
	*
	* @param manualCheckInRequired the manual check in required of this repository entry
	*/
	@Override
	public void setManualCheckInRequired(boolean manualCheckInRequired) {
		model.setManualCheckInRequired(manualCheckInRequired);
	}

	/**
	* Sets the mapped ID of this repository entry.
	*
	* @param mappedId the mapped ID of this repository entry
	*/
	@Override
	public void setMappedId(String mappedId) {
		model.setMappedId(mappedId);
	}

	/**
	* Sets the modified date of this repository entry.
	*
	* @param modifiedDate the modified date of this repository entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this repository entry.
	*
	* @param mvccVersion the mvcc version of this repository entry
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the primary key of this repository entry.
	*
	* @param primaryKey the primary key of this repository entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the repository entry ID of this repository entry.
	*
	* @param repositoryEntryId the repository entry ID of this repository entry
	*/
	@Override
	public void setRepositoryEntryId(long repositoryEntryId) {
		model.setRepositoryEntryId(repositoryEntryId);
	}

	/**
	* Sets the repository ID of this repository entry.
	*
	* @param repositoryId the repository ID of this repository entry
	*/
	@Override
	public void setRepositoryId(long repositoryId) {
		model.setRepositoryId(repositoryId);
	}

	/**
	* Sets the user ID of this repository entry.
	*
	* @param userId the user ID of this repository entry
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this repository entry.
	*
	* @param userName the user name of this repository entry
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this repository entry.
	*
	* @param userUuid the user uuid of this repository entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this repository entry.
	*
	* @param uuid the uuid of this repository entry
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected RepositoryEntryWrapper wrap(RepositoryEntry repositoryEntry) {
		return new RepositoryEntryWrapper(repositoryEntry);
	}
}