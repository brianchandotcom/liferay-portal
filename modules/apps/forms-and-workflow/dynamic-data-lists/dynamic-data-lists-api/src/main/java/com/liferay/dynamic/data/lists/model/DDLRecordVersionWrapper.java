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

package com.liferay.dynamic.data.lists.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link DDLRecordVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersion
 * @generated
 */
@ProviderType
public class DDLRecordVersionWrapper implements DDLRecordVersion,
	ModelWrapper<DDLRecordVersion> {
	public DDLRecordVersionWrapper(DDLRecordVersion ddlRecordVersion) {
		_ddlRecordVersion = ddlRecordVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return DDLRecordVersion.class;
	}

	@Override
	public String getModelClassName() {
		return DDLRecordVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("recordVersionId", getRecordVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("DDMStorageId", getDDMStorageId());
		attributes.put("recordSetId", getRecordSetId());
		attributes.put("recordId", getRecordId());
		attributes.put("version", getVersion());
		attributes.put("displayIndex", getDisplayIndex());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long recordVersionId = (Long)attributes.get("recordVersionId");

		if (recordVersionId != null) {
			setRecordVersionId(recordVersionId);
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

		Long DDMStorageId = (Long)attributes.get("DDMStorageId");

		if (DDMStorageId != null) {
			setDDMStorageId(DDMStorageId);
		}

		Long recordSetId = (Long)attributes.get("recordSetId");

		if (recordSetId != null) {
			setRecordSetId(recordSetId);
		}

		Long recordId = (Long)attributes.get("recordId");

		if (recordId != null) {
			setRecordId(recordId);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Integer displayIndex = (Integer)attributes.get("displayIndex");

		if (displayIndex != null) {
			setDisplayIndex(displayIndex);
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
	public DDLRecord getRecord()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersion.getRecord();
	}

	@Override
	public DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersion.getRecordSet();
	}

	@Override
	public DDLRecordVersion toEscapedModel() {
		return new DDLRecordVersionWrapper(_ddlRecordVersion.toEscapedModel());
	}

	@Override
	public DDLRecordVersion toUnescapedModel() {
		return new DDLRecordVersionWrapper(_ddlRecordVersion.toUnescapedModel());
	}

	/**
	* Returns <code>true</code> if this ddl record version is approved.
	*
	* @return <code>true</code> if this ddl record version is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _ddlRecordVersion.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _ddlRecordVersion.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this ddl record version is denied.
	*
	* @return <code>true</code> if this ddl record version is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _ddlRecordVersion.isDenied();
	}

	/**
	* Returns <code>true</code> if this ddl record version is a draft.
	*
	* @return <code>true</code> if this ddl record version is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _ddlRecordVersion.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddlRecordVersion.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this ddl record version is expired.
	*
	* @return <code>true</code> if this ddl record version is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _ddlRecordVersion.isExpired();
	}

	/**
	* Returns <code>true</code> if this ddl record version is inactive.
	*
	* @return <code>true</code> if this ddl record version is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _ddlRecordVersion.isInactive();
	}

	/**
	* Returns <code>true</code> if this ddl record version is incomplete.
	*
	* @return <code>true</code> if this ddl record version is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _ddlRecordVersion.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _ddlRecordVersion.isNew();
	}

	/**
	* Returns <code>true</code> if this ddl record version is pending.
	*
	* @return <code>true</code> if this ddl record version is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _ddlRecordVersion.isPending();
	}

	/**
	* Returns <code>true</code> if this ddl record version is scheduled.
	*
	* @return <code>true</code> if this ddl record version is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _ddlRecordVersion.isScheduled();
	}

	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues getDDMFormValues()
		throws com.liferay.dynamic.data.mapping.exception.StorageException {
		return _ddlRecordVersion.getDDMFormValues();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddlRecordVersion.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DDLRecordVersion> toCacheModel() {
		return _ddlRecordVersion.toCacheModel();
	}

	@Override
	public int compareTo(DDLRecordVersion ddlRecordVersion) {
		return _ddlRecordVersion.compareTo(ddlRecordVersion);
	}

	/**
	* Returns the display index of this ddl record version.
	*
	* @return the display index of this ddl record version
	*/
	@Override
	public int getDisplayIndex() {
		return _ddlRecordVersion.getDisplayIndex();
	}

	/**
	* Returns the status of this ddl record version.
	*
	* @return the status of this ddl record version
	*/
	@Override
	public int getStatus() {
		return _ddlRecordVersion.getStatus();
	}

	@Override
	public int hashCode() {
		return _ddlRecordVersion.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddlRecordVersion.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new DDLRecordVersionWrapper((DDLRecordVersion)_ddlRecordVersion.clone());
	}

	/**
	* Returns the status by user name of this ddl record version.
	*
	* @return the status by user name of this ddl record version
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _ddlRecordVersion.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this ddl record version.
	*
	* @return the status by user uuid of this ddl record version
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _ddlRecordVersion.getStatusByUserUuid();
	}

	/**
	* Returns the user name of this ddl record version.
	*
	* @return the user name of this ddl record version
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddlRecordVersion.getUserName();
	}

	/**
	* Returns the user uuid of this ddl record version.
	*
	* @return the user uuid of this ddl record version
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddlRecordVersion.getUserUuid();
	}

	/**
	* Returns the version of this ddl record version.
	*
	* @return the version of this ddl record version
	*/
	@Override
	public java.lang.String getVersion() {
		return _ddlRecordVersion.getVersion();
	}

	@Override
	public java.lang.String toString() {
		return _ddlRecordVersion.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddlRecordVersion.toXmlString();
	}

	/**
	* Returns the create date of this ddl record version.
	*
	* @return the create date of this ddl record version
	*/
	@Override
	public Date getCreateDate() {
		return _ddlRecordVersion.getCreateDate();
	}

	/**
	* Returns the status date of this ddl record version.
	*
	* @return the status date of this ddl record version
	*/
	@Override
	public Date getStatusDate() {
		return _ddlRecordVersion.getStatusDate();
	}

	/**
	* Returns the company ID of this ddl record version.
	*
	* @return the company ID of this ddl record version
	*/
	@Override
	public long getCompanyId() {
		return _ddlRecordVersion.getCompanyId();
	}

	/**
	* Returns the ddm storage ID of this ddl record version.
	*
	* @return the ddm storage ID of this ddl record version
	*/
	@Override
	public long getDDMStorageId() {
		return _ddlRecordVersion.getDDMStorageId();
	}

	/**
	* Returns the group ID of this ddl record version.
	*
	* @return the group ID of this ddl record version
	*/
	@Override
	public long getGroupId() {
		return _ddlRecordVersion.getGroupId();
	}

	/**
	* Returns the primary key of this ddl record version.
	*
	* @return the primary key of this ddl record version
	*/
	@Override
	public long getPrimaryKey() {
		return _ddlRecordVersion.getPrimaryKey();
	}

	/**
	* Returns the record ID of this ddl record version.
	*
	* @return the record ID of this ddl record version
	*/
	@Override
	public long getRecordId() {
		return _ddlRecordVersion.getRecordId();
	}

	/**
	* Returns the record set ID of this ddl record version.
	*
	* @return the record set ID of this ddl record version
	*/
	@Override
	public long getRecordSetId() {
		return _ddlRecordVersion.getRecordSetId();
	}

	/**
	* Returns the record version ID of this ddl record version.
	*
	* @return the record version ID of this ddl record version
	*/
	@Override
	public long getRecordVersionId() {
		return _ddlRecordVersion.getRecordVersionId();
	}

	/**
	* Returns the status by user ID of this ddl record version.
	*
	* @return the status by user ID of this ddl record version
	*/
	@Override
	public long getStatusByUserId() {
		return _ddlRecordVersion.getStatusByUserId();
	}

	/**
	* Returns the user ID of this ddl record version.
	*
	* @return the user ID of this ddl record version
	*/
	@Override
	public long getUserId() {
		return _ddlRecordVersion.getUserId();
	}

	@Override
	public void persist() {
		_ddlRecordVersion.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddlRecordVersion.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this ddl record version.
	*
	* @param companyId the company ID of this ddl record version
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddlRecordVersion.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this ddl record version.
	*
	* @param createDate the create date of this ddl record version
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddlRecordVersion.setCreateDate(createDate);
	}

	/**
	* Sets the ddm storage ID of this ddl record version.
	*
	* @param DDMStorageId the ddm storage ID of this ddl record version
	*/
	@Override
	public void setDDMStorageId(long DDMStorageId) {
		_ddlRecordVersion.setDDMStorageId(DDMStorageId);
	}

	/**
	* Sets the display index of this ddl record version.
	*
	* @param displayIndex the display index of this ddl record version
	*/
	@Override
	public void setDisplayIndex(int displayIndex) {
		_ddlRecordVersion.setDisplayIndex(displayIndex);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ddlRecordVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_ddlRecordVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ddlRecordVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this ddl record version.
	*
	* @param groupId the group ID of this ddl record version
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddlRecordVersion.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_ddlRecordVersion.setNew(n);
	}

	/**
	* Sets the primary key of this ddl record version.
	*
	* @param primaryKey the primary key of this ddl record version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddlRecordVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddlRecordVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the record ID of this ddl record version.
	*
	* @param recordId the record ID of this ddl record version
	*/
	@Override
	public void setRecordId(long recordId) {
		_ddlRecordVersion.setRecordId(recordId);
	}

	/**
	* Sets the record set ID of this ddl record version.
	*
	* @param recordSetId the record set ID of this ddl record version
	*/
	@Override
	public void setRecordSetId(long recordSetId) {
		_ddlRecordVersion.setRecordSetId(recordSetId);
	}

	/**
	* Sets the record version ID of this ddl record version.
	*
	* @param recordVersionId the record version ID of this ddl record version
	*/
	@Override
	public void setRecordVersionId(long recordVersionId) {
		_ddlRecordVersion.setRecordVersionId(recordVersionId);
	}

	/**
	* Sets the status of this ddl record version.
	*
	* @param status the status of this ddl record version
	*/
	@Override
	public void setStatus(int status) {
		_ddlRecordVersion.setStatus(status);
	}

	/**
	* Sets the status by user ID of this ddl record version.
	*
	* @param statusByUserId the status by user ID of this ddl record version
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_ddlRecordVersion.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this ddl record version.
	*
	* @param statusByUserName the status by user name of this ddl record version
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_ddlRecordVersion.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this ddl record version.
	*
	* @param statusByUserUuid the status by user uuid of this ddl record version
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_ddlRecordVersion.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this ddl record version.
	*
	* @param statusDate the status date of this ddl record version
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_ddlRecordVersion.setStatusDate(statusDate);
	}

	/**
	* Sets the user ID of this ddl record version.
	*
	* @param userId the user ID of this ddl record version
	*/
	@Override
	public void setUserId(long userId) {
		_ddlRecordVersion.setUserId(userId);
	}

	/**
	* Sets the user name of this ddl record version.
	*
	* @param userName the user name of this ddl record version
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddlRecordVersion.setUserName(userName);
	}

	/**
	* Sets the user uuid of this ddl record version.
	*
	* @param userUuid the user uuid of this ddl record version
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddlRecordVersion.setUserUuid(userUuid);
	}

	/**
	* Sets the version of this ddl record version.
	*
	* @param version the version of this ddl record version
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_ddlRecordVersion.setVersion(version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDLRecordVersionWrapper)) {
			return false;
		}

		DDLRecordVersionWrapper ddlRecordVersionWrapper = (DDLRecordVersionWrapper)obj;

		if (Objects.equals(_ddlRecordVersion,
					ddlRecordVersionWrapper._ddlRecordVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public DDLRecordVersion getWrappedModel() {
		return _ddlRecordVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddlRecordVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddlRecordVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddlRecordVersion.resetOriginalValues();
	}

	private final DDLRecordVersion _ddlRecordVersion;
}