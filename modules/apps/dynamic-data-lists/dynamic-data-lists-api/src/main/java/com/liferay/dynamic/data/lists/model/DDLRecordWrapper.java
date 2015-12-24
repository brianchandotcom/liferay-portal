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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDLRecord}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecord
 * @generated
 */
@ProviderType
public class DDLRecordWrapper implements DDLRecord, ModelWrapper<DDLRecord> {
	public DDLRecordWrapper(DDLRecord ddlRecord) {
		_ddlRecord = ddlRecord;
	}

	@Override
	public Class<?> getModelClass() {
		return DDLRecord.class;
	}

	@Override
	public String getModelClassName() {
		return DDLRecord.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("recordId", getRecordId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("versionUserId", getVersionUserId());
		attributes.put("versionUserName", getVersionUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("DDMStorageId", getDDMStorageId());
		attributes.put("recordSetId", getRecordSetId());
		attributes.put("version", getVersion());
		attributes.put("displayIndex", getDisplayIndex());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long recordId = (Long)attributes.get("recordId");

		if (recordId != null) {
			setRecordId(recordId);
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

		Long DDMStorageId = (Long)attributes.get("DDMStorageId");

		if (DDMStorageId != null) {
			setDDMStorageId(DDMStorageId);
		}

		Long recordSetId = (Long)attributes.get("recordSetId");

		if (recordSetId != null) {
			setRecordSetId(recordSetId);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Integer displayIndex = (Integer)attributes.get("displayIndex");

		if (displayIndex != null) {
			setDisplayIndex(displayIndex);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new DDLRecordWrapper((DDLRecord)_ddlRecord.clone());
	}

	@Override
	public int compareTo(
		com.liferay.dynamic.data.lists.model.DDLRecord ddlRecord) {
		return _ddlRecord.compareTo(ddlRecord);
	}

	/**
	* Returns the company ID of this d d l record.
	*
	* @return the company ID of this d d l record
	*/
	@Override
	public long getCompanyId() {
		return _ddlRecord.getCompanyId();
	}

	/**
	* Returns the create date of this d d l record.
	*
	* @return the create date of this d d l record
	*/
	@Override
	public Date getCreateDate() {
		return _ddlRecord.getCreateDate();
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue> getDDMFormFieldValues(
		java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecord.getDDMFormFieldValues(fieldName);
	}

	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues getDDMFormValues()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecord.getDDMFormValues();
	}

	/**
	* Returns the d d m storage ID of this d d l record.
	*
	* @return the d d m storage ID of this d d l record
	*/
	@Override
	public long getDDMStorageId() {
		return _ddlRecord.getDDMStorageId();
	}

	/**
	* Returns the display index of this d d l record.
	*
	* @return the display index of this d d l record
	*/
	@Override
	public int getDisplayIndex() {
		return _ddlRecord.getDisplayIndex();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddlRecord.getExpandoBridge();
	}

	@Override
	public java.io.Serializable getFieldDataType(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecord.getFieldDataType(fieldName);
	}

	@Override
	public java.io.Serializable getFieldType(java.lang.String fieldName)
		throws java.lang.Exception {
		return _ddlRecord.getFieldType(fieldName);
	}

	/**
	* Returns the group ID of this d d l record.
	*
	* @return the group ID of this d d l record
	*/
	@Override
	public long getGroupId() {
		return _ddlRecord.getGroupId();
	}

	/**
	* Returns the last publish date of this d d l record.
	*
	* @return the last publish date of this d d l record
	*/
	@Override
	public Date getLastPublishDate() {
		return _ddlRecord.getLastPublishDate();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getLatestRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecord.getLatestRecordVersion();
	}

	/**
	* Returns the modified date of this d d l record.
	*
	* @return the modified date of this d d l record
	*/
	@Override
	public Date getModifiedDate() {
		return _ddlRecord.getModifiedDate();
	}

	/**
	* Returns the primary key of this d d l record.
	*
	* @return the primary key of this d d l record
	*/
	@Override
	public long getPrimaryKey() {
		return _ddlRecord.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddlRecord.getPrimaryKeyObj();
	}

	/**
	* Returns the record ID of this d d l record.
	*
	* @return the record ID of this d d l record
	*/
	@Override
	public long getRecordId() {
		return _ddlRecord.getRecordId();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecord.getRecordSet();
	}

	/**
	* Returns the record set ID of this d d l record.
	*
	* @return the record set ID of this d d l record
	*/
	@Override
	public long getRecordSetId() {
		return _ddlRecord.getRecordSetId();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecord.getRecordVersion();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecord.getRecordVersion(version);
	}

	@Override
	public int getStatus()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecord.getStatus();
	}

	/**
	* Returns the user ID of this d d l record.
	*
	* @return the user ID of this d d l record
	*/
	@Override
	public long getUserId() {
		return _ddlRecord.getUserId();
	}

	/**
	* Returns the user name of this d d l record.
	*
	* @return the user name of this d d l record
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddlRecord.getUserName();
	}

	/**
	* Returns the user uuid of this d d l record.
	*
	* @return the user uuid of this d d l record
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddlRecord.getUserUuid();
	}

	/**
	* Returns the uuid of this d d l record.
	*
	* @return the uuid of this d d l record
	*/
	@Override
	public java.lang.String getUuid() {
		return _ddlRecord.getUuid();
	}

	/**
	* Returns the version of this d d l record.
	*
	* @return the version of this d d l record
	*/
	@Override
	public java.lang.String getVersion() {
		return _ddlRecord.getVersion();
	}

	/**
	* Returns the version user ID of this d d l record.
	*
	* @return the version user ID of this d d l record
	*/
	@Override
	public long getVersionUserId() {
		return _ddlRecord.getVersionUserId();
	}

	/**
	* Returns the version user name of this d d l record.
	*
	* @return the version user name of this d d l record
	*/
	@Override
	public java.lang.String getVersionUserName() {
		return _ddlRecord.getVersionUserName();
	}

	/**
	* Returns the version user uuid of this d d l record.
	*
	* @return the version user uuid of this d d l record
	*/
	@Override
	public java.lang.String getVersionUserUuid() {
		return _ddlRecord.getVersionUserUuid();
	}

	@Override
	public int hashCode() {
		return _ddlRecord.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ddlRecord.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddlRecord.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddlRecord.isNew();
	}

	@Override
	public void persist() {
		_ddlRecord.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddlRecord.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this d d l record.
	*
	* @param companyId the company ID of this d d l record
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddlRecord.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this d d l record.
	*
	* @param createDate the create date of this d d l record
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddlRecord.setCreateDate(createDate);
	}

	/**
	* Sets the d d m storage ID of this d d l record.
	*
	* @param DDMStorageId the d d m storage ID of this d d l record
	*/
	@Override
	public void setDDMStorageId(long DDMStorageId) {
		_ddlRecord.setDDMStorageId(DDMStorageId);
	}

	/**
	* Sets the display index of this d d l record.
	*
	* @param displayIndex the display index of this d d l record
	*/
	@Override
	public void setDisplayIndex(int displayIndex) {
		_ddlRecord.setDisplayIndex(displayIndex);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddlRecord.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddlRecord.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddlRecord.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this d d l record.
	*
	* @param groupId the group ID of this d d l record
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddlRecord.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this d d l record.
	*
	* @param lastPublishDate the last publish date of this d d l record
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_ddlRecord.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this d d l record.
	*
	* @param modifiedDate the modified date of this d d l record
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddlRecord.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_ddlRecord.setNew(n);
	}

	/**
	* Sets the primary key of this d d l record.
	*
	* @param primaryKey the primary key of this d d l record
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddlRecord.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddlRecord.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the record ID of this d d l record.
	*
	* @param recordId the record ID of this d d l record
	*/
	@Override
	public void setRecordId(long recordId) {
		_ddlRecord.setRecordId(recordId);
	}

	/**
	* Sets the record set ID of this d d l record.
	*
	* @param recordSetId the record set ID of this d d l record
	*/
	@Override
	public void setRecordSetId(long recordSetId) {
		_ddlRecord.setRecordSetId(recordSetId);
	}

	/**
	* Sets the user ID of this d d l record.
	*
	* @param userId the user ID of this d d l record
	*/
	@Override
	public void setUserId(long userId) {
		_ddlRecord.setUserId(userId);
	}

	/**
	* Sets the user name of this d d l record.
	*
	* @param userName the user name of this d d l record
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddlRecord.setUserName(userName);
	}

	/**
	* Sets the user uuid of this d d l record.
	*
	* @param userUuid the user uuid of this d d l record
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddlRecord.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this d d l record.
	*
	* @param uuid the uuid of this d d l record
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ddlRecord.setUuid(uuid);
	}

	/**
	* Sets the version of this d d l record.
	*
	* @param version the version of this d d l record
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_ddlRecord.setVersion(version);
	}

	/**
	* Sets the version user ID of this d d l record.
	*
	* @param versionUserId the version user ID of this d d l record
	*/
	@Override
	public void setVersionUserId(long versionUserId) {
		_ddlRecord.setVersionUserId(versionUserId);
	}

	/**
	* Sets the version user name of this d d l record.
	*
	* @param versionUserName the version user name of this d d l record
	*/
	@Override
	public void setVersionUserName(java.lang.String versionUserName) {
		_ddlRecord.setVersionUserName(versionUserName);
	}

	/**
	* Sets the version user uuid of this d d l record.
	*
	* @param versionUserUuid the version user uuid of this d d l record
	*/
	@Override
	public void setVersionUserUuid(java.lang.String versionUserUuid) {
		_ddlRecord.setVersionUserUuid(versionUserUuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.dynamic.data.lists.model.DDLRecord> toCacheModel() {
		return _ddlRecord.toCacheModel();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord toEscapedModel() {
		return new DDLRecordWrapper(_ddlRecord.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddlRecord.toString();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord toUnescapedModel() {
		return new DDLRecordWrapper(_ddlRecord.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddlRecord.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDLRecordWrapper)) {
			return false;
		}

		DDLRecordWrapper ddlRecordWrapper = (DDLRecordWrapper)obj;

		if (Validator.equals(_ddlRecord, ddlRecordWrapper._ddlRecord)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddlRecord.getStagedModelType();
	}

	@Override
	public DDLRecord getWrappedModel() {
		return _ddlRecord;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddlRecord.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddlRecord.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddlRecord.resetOriginalValues();
	}

	private final DDLRecord _ddlRecord;
}