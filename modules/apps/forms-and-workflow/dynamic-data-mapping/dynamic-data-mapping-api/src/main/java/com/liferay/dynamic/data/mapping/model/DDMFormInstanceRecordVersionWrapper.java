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

package com.liferay.dynamic.data.mapping.model;

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
 * This class is a wrapper for {@link DDMFormInstanceRecordVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordVersion
 * @generated
 */
@ProviderType
public class DDMFormInstanceRecordVersionWrapper
	implements DDMFormInstanceRecordVersion,
		ModelWrapper<DDMFormInstanceRecordVersion> {
	public DDMFormInstanceRecordVersionWrapper(
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion) {
		_ddmFormInstanceRecordVersion = ddmFormInstanceRecordVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMFormInstanceRecordVersion.class;
	}

	@Override
	public String getModelClassName() {
		return DDMFormInstanceRecordVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("formInstanceRecordVersionId",
			getFormInstanceRecordVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("formInstanceId", getFormInstanceId());
		attributes.put("formInstanceVersion", getFormInstanceVersion());
		attributes.put("formInstanceRecordId", getFormInstanceRecordId());
		attributes.put("version", getVersion());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("storageId", getStorageId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long formInstanceRecordVersionId = (Long)attributes.get(
				"formInstanceRecordVersionId");

		if (formInstanceRecordVersionId != null) {
			setFormInstanceRecordVersionId(formInstanceRecordVersionId);
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

		Long formInstanceId = (Long)attributes.get("formInstanceId");

		if (formInstanceId != null) {
			setFormInstanceId(formInstanceId);
		}

		String formInstanceVersion = (String)attributes.get(
				"formInstanceVersion");

		if (formInstanceVersion != null) {
			setFormInstanceVersion(formInstanceVersion);
		}

		Long formInstanceRecordId = (Long)attributes.get("formInstanceRecordId");

		if (formInstanceRecordId != null) {
			setFormInstanceRecordId(formInstanceRecordId);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
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

		Long storageId = (Long)attributes.get("storageId");

		if (storageId != null) {
			setStorageId(storageId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new DDMFormInstanceRecordVersionWrapper((DDMFormInstanceRecordVersion)_ddmFormInstanceRecordVersion.clone());
	}

	@Override
	public int compareTo(
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion) {
		return _ddmFormInstanceRecordVersion.compareTo(ddmFormInstanceRecordVersion);
	}

	/**
	* Returns the company ID of this ddm form instance record version.
	*
	* @return the company ID of this ddm form instance record version
	*/
	@Override
	public long getCompanyId() {
		return _ddmFormInstanceRecordVersion.getCompanyId();
	}

	/**
	* Returns the create date of this ddm form instance record version.
	*
	* @return the create date of this ddm form instance record version
	*/
	@Override
	public Date getCreateDate() {
		return _ddmFormInstanceRecordVersion.getCreateDate();
	}

	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues getDDMFormValues()
		throws com.liferay.dynamic.data.mapping.exception.StorageException {
		return _ddmFormInstanceRecordVersion.getDDMFormValues();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddmFormInstanceRecordVersion.getExpandoBridge();
	}

	@Override
	public DDMFormInstance getFormInstance()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceRecordVersion.getFormInstance();
	}

	/**
	* Returns the form instance ID of this ddm form instance record version.
	*
	* @return the form instance ID of this ddm form instance record version
	*/
	@Override
	public long getFormInstanceId() {
		return _ddmFormInstanceRecordVersion.getFormInstanceId();
	}

	@Override
	public DDMFormInstanceRecord getFormInstanceRecord()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceRecordVersion.getFormInstanceRecord();
	}

	/**
	* Returns the form instance record ID of this ddm form instance record version.
	*
	* @return the form instance record ID of this ddm form instance record version
	*/
	@Override
	public long getFormInstanceRecordId() {
		return _ddmFormInstanceRecordVersion.getFormInstanceRecordId();
	}

	/**
	* Returns the form instance record version ID of this ddm form instance record version.
	*
	* @return the form instance record version ID of this ddm form instance record version
	*/
	@Override
	public long getFormInstanceRecordVersionId() {
		return _ddmFormInstanceRecordVersion.getFormInstanceRecordVersionId();
	}

	/**
	* Returns the form instance version of this ddm form instance record version.
	*
	* @return the form instance version of this ddm form instance record version
	*/
	@Override
	public java.lang.String getFormInstanceVersion() {
		return _ddmFormInstanceRecordVersion.getFormInstanceVersion();
	}

	/**
	* Returns the group ID of this ddm form instance record version.
	*
	* @return the group ID of this ddm form instance record version
	*/
	@Override
	public long getGroupId() {
		return _ddmFormInstanceRecordVersion.getGroupId();
	}

	/**
	* Returns the primary key of this ddm form instance record version.
	*
	* @return the primary key of this ddm form instance record version
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmFormInstanceRecordVersion.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddmFormInstanceRecordVersion.getPrimaryKeyObj();
	}

	/**
	* Returns the status of this ddm form instance record version.
	*
	* @return the status of this ddm form instance record version
	*/
	@Override
	public int getStatus() {
		return _ddmFormInstanceRecordVersion.getStatus();
	}

	/**
	* Returns the status by user ID of this ddm form instance record version.
	*
	* @return the status by user ID of this ddm form instance record version
	*/
	@Override
	public long getStatusByUserId() {
		return _ddmFormInstanceRecordVersion.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this ddm form instance record version.
	*
	* @return the status by user name of this ddm form instance record version
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _ddmFormInstanceRecordVersion.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this ddm form instance record version.
	*
	* @return the status by user uuid of this ddm form instance record version
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _ddmFormInstanceRecordVersion.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this ddm form instance record version.
	*
	* @return the status date of this ddm form instance record version
	*/
	@Override
	public Date getStatusDate() {
		return _ddmFormInstanceRecordVersion.getStatusDate();
	}

	/**
	* Returns the storage ID of this ddm form instance record version.
	*
	* @return the storage ID of this ddm form instance record version
	*/
	@Override
	public long getStorageId() {
		return _ddmFormInstanceRecordVersion.getStorageId();
	}

	/**
	* Returns the user ID of this ddm form instance record version.
	*
	* @return the user ID of this ddm form instance record version
	*/
	@Override
	public long getUserId() {
		return _ddmFormInstanceRecordVersion.getUserId();
	}

	/**
	* Returns the user name of this ddm form instance record version.
	*
	* @return the user name of this ddm form instance record version
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddmFormInstanceRecordVersion.getUserName();
	}

	/**
	* Returns the user uuid of this ddm form instance record version.
	*
	* @return the user uuid of this ddm form instance record version
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddmFormInstanceRecordVersion.getUserUuid();
	}

	/**
	* Returns the version of this ddm form instance record version.
	*
	* @return the version of this ddm form instance record version
	*/
	@Override
	public java.lang.String getVersion() {
		return _ddmFormInstanceRecordVersion.getVersion();
	}

	@Override
	public int hashCode() {
		return _ddmFormInstanceRecordVersion.hashCode();
	}

	/**
	* Returns <code>true</code> if this ddm form instance record version is approved.
	*
	* @return <code>true</code> if this ddm form instance record version is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _ddmFormInstanceRecordVersion.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmFormInstanceRecordVersion.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this ddm form instance record version is denied.
	*
	* @return <code>true</code> if this ddm form instance record version is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _ddmFormInstanceRecordVersion.isDenied();
	}

	/**
	* Returns <code>true</code> if this ddm form instance record version is a draft.
	*
	* @return <code>true</code> if this ddm form instance record version is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _ddmFormInstanceRecordVersion.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmFormInstanceRecordVersion.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this ddm form instance record version is expired.
	*
	* @return <code>true</code> if this ddm form instance record version is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _ddmFormInstanceRecordVersion.isExpired();
	}

	/**
	* Returns <code>true</code> if this ddm form instance record version is inactive.
	*
	* @return <code>true</code> if this ddm form instance record version is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _ddmFormInstanceRecordVersion.isInactive();
	}

	/**
	* Returns <code>true</code> if this ddm form instance record version is incomplete.
	*
	* @return <code>true</code> if this ddm form instance record version is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _ddmFormInstanceRecordVersion.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _ddmFormInstanceRecordVersion.isNew();
	}

	/**
	* Returns <code>true</code> if this ddm form instance record version is pending.
	*
	* @return <code>true</code> if this ddm form instance record version is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _ddmFormInstanceRecordVersion.isPending();
	}

	/**
	* Returns <code>true</code> if this ddm form instance record version is scheduled.
	*
	* @return <code>true</code> if this ddm form instance record version is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _ddmFormInstanceRecordVersion.isScheduled();
	}

	@Override
	public void persist() {
		_ddmFormInstanceRecordVersion.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmFormInstanceRecordVersion.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this ddm form instance record version.
	*
	* @param companyId the company ID of this ddm form instance record version
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmFormInstanceRecordVersion.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this ddm form instance record version.
	*
	* @param createDate the create date of this ddm form instance record version
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddmFormInstanceRecordVersion.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_ddmFormInstanceRecordVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ddmFormInstanceRecordVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ddmFormInstanceRecordVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the form instance ID of this ddm form instance record version.
	*
	* @param formInstanceId the form instance ID of this ddm form instance record version
	*/
	@Override
	public void setFormInstanceId(long formInstanceId) {
		_ddmFormInstanceRecordVersion.setFormInstanceId(formInstanceId);
	}

	/**
	* Sets the form instance record ID of this ddm form instance record version.
	*
	* @param formInstanceRecordId the form instance record ID of this ddm form instance record version
	*/
	@Override
	public void setFormInstanceRecordId(long formInstanceRecordId) {
		_ddmFormInstanceRecordVersion.setFormInstanceRecordId(formInstanceRecordId);
	}

	/**
	* Sets the form instance record version ID of this ddm form instance record version.
	*
	* @param formInstanceRecordVersionId the form instance record version ID of this ddm form instance record version
	*/
	@Override
	public void setFormInstanceRecordVersionId(long formInstanceRecordVersionId) {
		_ddmFormInstanceRecordVersion.setFormInstanceRecordVersionId(formInstanceRecordVersionId);
	}

	/**
	* Sets the form instance version of this ddm form instance record version.
	*
	* @param formInstanceVersion the form instance version of this ddm form instance record version
	*/
	@Override
	public void setFormInstanceVersion(java.lang.String formInstanceVersion) {
		_ddmFormInstanceRecordVersion.setFormInstanceVersion(formInstanceVersion);
	}

	/**
	* Sets the group ID of this ddm form instance record version.
	*
	* @param groupId the group ID of this ddm form instance record version
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmFormInstanceRecordVersion.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_ddmFormInstanceRecordVersion.setNew(n);
	}

	/**
	* Sets the primary key of this ddm form instance record version.
	*
	* @param primaryKey the primary key of this ddm form instance record version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmFormInstanceRecordVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmFormInstanceRecordVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the status of this ddm form instance record version.
	*
	* @param status the status of this ddm form instance record version
	*/
	@Override
	public void setStatus(int status) {
		_ddmFormInstanceRecordVersion.setStatus(status);
	}

	/**
	* Sets the status by user ID of this ddm form instance record version.
	*
	* @param statusByUserId the status by user ID of this ddm form instance record version
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_ddmFormInstanceRecordVersion.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this ddm form instance record version.
	*
	* @param statusByUserName the status by user name of this ddm form instance record version
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_ddmFormInstanceRecordVersion.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this ddm form instance record version.
	*
	* @param statusByUserUuid the status by user uuid of this ddm form instance record version
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_ddmFormInstanceRecordVersion.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this ddm form instance record version.
	*
	* @param statusDate the status date of this ddm form instance record version
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_ddmFormInstanceRecordVersion.setStatusDate(statusDate);
	}

	/**
	* Sets the storage ID of this ddm form instance record version.
	*
	* @param storageId the storage ID of this ddm form instance record version
	*/
	@Override
	public void setStorageId(long storageId) {
		_ddmFormInstanceRecordVersion.setStorageId(storageId);
	}

	/**
	* Sets the user ID of this ddm form instance record version.
	*
	* @param userId the user ID of this ddm form instance record version
	*/
	@Override
	public void setUserId(long userId) {
		_ddmFormInstanceRecordVersion.setUserId(userId);
	}

	/**
	* Sets the user name of this ddm form instance record version.
	*
	* @param userName the user name of this ddm form instance record version
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddmFormInstanceRecordVersion.setUserName(userName);
	}

	/**
	* Sets the user uuid of this ddm form instance record version.
	*
	* @param userUuid the user uuid of this ddm form instance record version
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddmFormInstanceRecordVersion.setUserUuid(userUuid);
	}

	/**
	* Sets the version of this ddm form instance record version.
	*
	* @param version the version of this ddm form instance record version
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_ddmFormInstanceRecordVersion.setVersion(version);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DDMFormInstanceRecordVersion> toCacheModel() {
		return _ddmFormInstanceRecordVersion.toCacheModel();
	}

	@Override
	public DDMFormInstanceRecordVersion toEscapedModel() {
		return new DDMFormInstanceRecordVersionWrapper(_ddmFormInstanceRecordVersion.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmFormInstanceRecordVersion.toString();
	}

	@Override
	public DDMFormInstanceRecordVersion toUnescapedModel() {
		return new DDMFormInstanceRecordVersionWrapper(_ddmFormInstanceRecordVersion.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmFormInstanceRecordVersion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormInstanceRecordVersionWrapper)) {
			return false;
		}

		DDMFormInstanceRecordVersionWrapper ddmFormInstanceRecordVersionWrapper = (DDMFormInstanceRecordVersionWrapper)obj;

		if (Objects.equals(_ddmFormInstanceRecordVersion,
					ddmFormInstanceRecordVersionWrapper._ddmFormInstanceRecordVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public DDMFormInstanceRecordVersion getWrappedModel() {
		return _ddmFormInstanceRecordVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmFormInstanceRecordVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmFormInstanceRecordVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmFormInstanceRecordVersion.resetOriginalValues();
	}

	private final DDMFormInstanceRecordVersion _ddmFormInstanceRecordVersion;
}