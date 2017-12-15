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

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link DDMFormInstanceRecord}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecord
 * @generated
 */
@ProviderType
public class DDMFormInstanceRecordWrapper implements DDMFormInstanceRecord,
	ModelWrapper<DDMFormInstanceRecord> {
	public DDMFormInstanceRecordWrapper(
		DDMFormInstanceRecord ddmFormInstanceRecord) {
		_ddmFormInstanceRecord = ddmFormInstanceRecord;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMFormInstanceRecord.class;
	}

	@Override
	public String getModelClassName() {
		return DDMFormInstanceRecord.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("formInstanceRecordId", getFormInstanceRecordId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("versionUserId", getVersionUserId());
		attributes.put("versionUserName", getVersionUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("formInstanceId", getFormInstanceId());
		attributes.put("formInstanceVersion", getFormInstanceVersion());
		attributes.put("storageId", getStorageId());
		attributes.put("version", getVersion());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long formInstanceRecordId = (Long)attributes.get("formInstanceRecordId");

		if (formInstanceRecordId != null) {
			setFormInstanceRecordId(formInstanceRecordId);
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

		Long versionUserId = (Long)attributes.get("versionUserId");

		if (versionUserId != null) {
			setVersionUserId(versionUserId);
		}

		String versionUserName = (String)attributes.get("versionUserName");

		if (versionUserName != null) {
			setVersionUserName(versionUserName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
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

		Long storageId = (Long)attributes.get("storageId");

		if (storageId != null) {
			setStorageId(storageId);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new DDMFormInstanceRecordWrapper((DDMFormInstanceRecord)_ddmFormInstanceRecord.clone());
	}

	@Override
	public int compareTo(DDMFormInstanceRecord ddmFormInstanceRecord) {
		return _ddmFormInstanceRecord.compareTo(ddmFormInstanceRecord);
	}

	/**
	* Returns the company ID of this ddm form instance record.
	*
	* @return the company ID of this ddm form instance record
	*/
	@Override
	public long getCompanyId() {
		return _ddmFormInstanceRecord.getCompanyId();
	}

	/**
	* Returns the create date of this ddm form instance record.
	*
	* @return the create date of this ddm form instance record
	*/
	@Override
	public Date getCreateDate() {
		return _ddmFormInstanceRecord.getCreateDate();
	}

	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues getDDMFormValues()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceRecord.getDDMFormValues();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddmFormInstanceRecord.getExpandoBridge();
	}

	@Override
	public DDMFormInstance getFormInstance()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceRecord.getFormInstance();
	}

	/**
	* Returns the form instance ID of this ddm form instance record.
	*
	* @return the form instance ID of this ddm form instance record
	*/
	@Override
	public long getFormInstanceId() {
		return _ddmFormInstanceRecord.getFormInstanceId();
	}

	/**
	* Returns the form instance record ID of this ddm form instance record.
	*
	* @return the form instance record ID of this ddm form instance record
	*/
	@Override
	public long getFormInstanceRecordId() {
		return _ddmFormInstanceRecord.getFormInstanceRecordId();
	}

	@Override
	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceRecord.getFormInstanceRecordVersion();
	}

	@Override
	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceRecord.getFormInstanceRecordVersion(version);
	}

	/**
	* Returns the form instance version of this ddm form instance record.
	*
	* @return the form instance version of this ddm form instance record
	*/
	@Override
	public java.lang.String getFormInstanceVersion() {
		return _ddmFormInstanceRecord.getFormInstanceVersion();
	}

	/**
	* Returns the group ID of this ddm form instance record.
	*
	* @return the group ID of this ddm form instance record
	*/
	@Override
	public long getGroupId() {
		return _ddmFormInstanceRecord.getGroupId();
	}

	/**
	* Returns the last publish date of this ddm form instance record.
	*
	* @return the last publish date of this ddm form instance record
	*/
	@Override
	public Date getLastPublishDate() {
		return _ddmFormInstanceRecord.getLastPublishDate();
	}

	@Override
	public DDMFormInstanceRecordVersion getLatestFormInstanceRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceRecord.getLatestFormInstanceRecordVersion();
	}

	/**
	* Returns the modified date of this ddm form instance record.
	*
	* @return the modified date of this ddm form instance record
	*/
	@Override
	public Date getModifiedDate() {
		return _ddmFormInstanceRecord.getModifiedDate();
	}

	/**
	* Returns the primary key of this ddm form instance record.
	*
	* @return the primary key of this ddm form instance record
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmFormInstanceRecord.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddmFormInstanceRecord.getPrimaryKeyObj();
	}

	@Override
	public int getStatus()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceRecord.getStatus();
	}

	/**
	* Returns the storage ID of this ddm form instance record.
	*
	* @return the storage ID of this ddm form instance record
	*/
	@Override
	public long getStorageId() {
		return _ddmFormInstanceRecord.getStorageId();
	}

	/**
	* Returns the user ID of this ddm form instance record.
	*
	* @return the user ID of this ddm form instance record
	*/
	@Override
	public long getUserId() {
		return _ddmFormInstanceRecord.getUserId();
	}

	/**
	* Returns the user name of this ddm form instance record.
	*
	* @return the user name of this ddm form instance record
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddmFormInstanceRecord.getUserName();
	}

	/**
	* Returns the user uuid of this ddm form instance record.
	*
	* @return the user uuid of this ddm form instance record
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddmFormInstanceRecord.getUserUuid();
	}

	/**
	* Returns the uuid of this ddm form instance record.
	*
	* @return the uuid of this ddm form instance record
	*/
	@Override
	public java.lang.String getUuid() {
		return _ddmFormInstanceRecord.getUuid();
	}

	/**
	* Returns the version of this ddm form instance record.
	*
	* @return the version of this ddm form instance record
	*/
	@Override
	public java.lang.String getVersion() {
		return _ddmFormInstanceRecord.getVersion();
	}

	/**
	* Returns the version user ID of this ddm form instance record.
	*
	* @return the version user ID of this ddm form instance record
	*/
	@Override
	public long getVersionUserId() {
		return _ddmFormInstanceRecord.getVersionUserId();
	}

	/**
	* Returns the version user name of this ddm form instance record.
	*
	* @return the version user name of this ddm form instance record
	*/
	@Override
	public java.lang.String getVersionUserName() {
		return _ddmFormInstanceRecord.getVersionUserName();
	}

	/**
	* Returns the version user uuid of this ddm form instance record.
	*
	* @return the version user uuid of this ddm form instance record
	*/
	@Override
	public java.lang.String getVersionUserUuid() {
		return _ddmFormInstanceRecord.getVersionUserUuid();
	}

	@Override
	public int hashCode() {
		return _ddmFormInstanceRecord.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmFormInstanceRecord.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmFormInstanceRecord.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddmFormInstanceRecord.isNew();
	}

	@Override
	public void persist() {
		_ddmFormInstanceRecord.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmFormInstanceRecord.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this ddm form instance record.
	*
	* @param companyId the company ID of this ddm form instance record
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmFormInstanceRecord.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this ddm form instance record.
	*
	* @param createDate the create date of this ddm form instance record
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddmFormInstanceRecord.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_ddmFormInstanceRecord.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ddmFormInstanceRecord.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ddmFormInstanceRecord.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the form instance ID of this ddm form instance record.
	*
	* @param formInstanceId the form instance ID of this ddm form instance record
	*/
	@Override
	public void setFormInstanceId(long formInstanceId) {
		_ddmFormInstanceRecord.setFormInstanceId(formInstanceId);
	}

	/**
	* Sets the form instance record ID of this ddm form instance record.
	*
	* @param formInstanceRecordId the form instance record ID of this ddm form instance record
	*/
	@Override
	public void setFormInstanceRecordId(long formInstanceRecordId) {
		_ddmFormInstanceRecord.setFormInstanceRecordId(formInstanceRecordId);
	}

	/**
	* Sets the form instance version of this ddm form instance record.
	*
	* @param formInstanceVersion the form instance version of this ddm form instance record
	*/
	@Override
	public void setFormInstanceVersion(java.lang.String formInstanceVersion) {
		_ddmFormInstanceRecord.setFormInstanceVersion(formInstanceVersion);
	}

	/**
	* Sets the group ID of this ddm form instance record.
	*
	* @param groupId the group ID of this ddm form instance record
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmFormInstanceRecord.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this ddm form instance record.
	*
	* @param lastPublishDate the last publish date of this ddm form instance record
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_ddmFormInstanceRecord.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this ddm form instance record.
	*
	* @param modifiedDate the modified date of this ddm form instance record
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmFormInstanceRecord.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_ddmFormInstanceRecord.setNew(n);
	}

	/**
	* Sets the primary key of this ddm form instance record.
	*
	* @param primaryKey the primary key of this ddm form instance record
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmFormInstanceRecord.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmFormInstanceRecord.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the storage ID of this ddm form instance record.
	*
	* @param storageId the storage ID of this ddm form instance record
	*/
	@Override
	public void setStorageId(long storageId) {
		_ddmFormInstanceRecord.setStorageId(storageId);
	}

	/**
	* Sets the user ID of this ddm form instance record.
	*
	* @param userId the user ID of this ddm form instance record
	*/
	@Override
	public void setUserId(long userId) {
		_ddmFormInstanceRecord.setUserId(userId);
	}

	/**
	* Sets the user name of this ddm form instance record.
	*
	* @param userName the user name of this ddm form instance record
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddmFormInstanceRecord.setUserName(userName);
	}

	/**
	* Sets the user uuid of this ddm form instance record.
	*
	* @param userUuid the user uuid of this ddm form instance record
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddmFormInstanceRecord.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this ddm form instance record.
	*
	* @param uuid the uuid of this ddm form instance record
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ddmFormInstanceRecord.setUuid(uuid);
	}

	/**
	* Sets the version of this ddm form instance record.
	*
	* @param version the version of this ddm form instance record
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_ddmFormInstanceRecord.setVersion(version);
	}

	/**
	* Sets the version user ID of this ddm form instance record.
	*
	* @param versionUserId the version user ID of this ddm form instance record
	*/
	@Override
	public void setVersionUserId(long versionUserId) {
		_ddmFormInstanceRecord.setVersionUserId(versionUserId);
	}

	/**
	* Sets the version user name of this ddm form instance record.
	*
	* @param versionUserName the version user name of this ddm form instance record
	*/
	@Override
	public void setVersionUserName(java.lang.String versionUserName) {
		_ddmFormInstanceRecord.setVersionUserName(versionUserName);
	}

	/**
	* Sets the version user uuid of this ddm form instance record.
	*
	* @param versionUserUuid the version user uuid of this ddm form instance record
	*/
	@Override
	public void setVersionUserUuid(java.lang.String versionUserUuid) {
		_ddmFormInstanceRecord.setVersionUserUuid(versionUserUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DDMFormInstanceRecord> toCacheModel() {
		return _ddmFormInstanceRecord.toCacheModel();
	}

	@Override
	public DDMFormInstanceRecord toEscapedModel() {
		return new DDMFormInstanceRecordWrapper(_ddmFormInstanceRecord.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmFormInstanceRecord.toString();
	}

	@Override
	public DDMFormInstanceRecord toUnescapedModel() {
		return new DDMFormInstanceRecordWrapper(_ddmFormInstanceRecord.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmFormInstanceRecord.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormInstanceRecordWrapper)) {
			return false;
		}

		DDMFormInstanceRecordWrapper ddmFormInstanceRecordWrapper = (DDMFormInstanceRecordWrapper)obj;

		if (Objects.equals(_ddmFormInstanceRecord,
					ddmFormInstanceRecordWrapper._ddmFormInstanceRecord)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddmFormInstanceRecord.getStagedModelType();
	}

	@Override
	public DDMFormInstanceRecord getWrappedModel() {
		return _ddmFormInstanceRecord;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmFormInstanceRecord.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmFormInstanceRecord.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmFormInstanceRecord.resetOriginalValues();
	}

	private final DDMFormInstanceRecord _ddmFormInstanceRecord;
}