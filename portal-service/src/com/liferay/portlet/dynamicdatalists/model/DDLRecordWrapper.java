/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDLRecord}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLRecord
 * @generated
 */
public class DDLRecordWrapper implements DDLRecord, ModelWrapper<DDLRecord> {
	public DDLRecordWrapper(DDLRecord ddlRecord) {
		_ddlRecord = ddlRecord;
	}

	public Class<?> getModelClass() {
		return DDLRecord.class;
	}

	public String getModelClassName() {
		return DDLRecord.class.getName();
	}

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

		return attributes;
	}

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
	}

	/**
	* Returns the primary key of this d d l record.
	*
	* @return the primary key of this d d l record
	*/
	public long getPrimaryKey() {
		return _ddlRecord.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d l record.
	*
	* @param primaryKey the primary key of this d d l record
	*/
	public void setPrimaryKey(long primaryKey) {
		_ddlRecord.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this d d l record.
	*
	* @return the uuid of this d d l record
	*/
	public java.lang.String getUuid() {
		return _ddlRecord.getUuid();
	}

	/**
	* Sets the uuid of this d d l record.
	*
	* @param uuid the uuid of this d d l record
	*/
	public void setUuid(java.lang.String uuid) {
		_ddlRecord.setUuid(uuid);
	}

	/**
	* Returns the record ID of this d d l record.
	*
	* @return the record ID of this d d l record
	*/
	public long getRecordId() {
		return _ddlRecord.getRecordId();
	}

	/**
	* Sets the record ID of this d d l record.
	*
	* @param recordId the record ID of this d d l record
	*/
	public void setRecordId(long recordId) {
		_ddlRecord.setRecordId(recordId);
	}

	/**
	* Returns the group ID of this d d l record.
	*
	* @return the group ID of this d d l record
	*/
	public long getGroupId() {
		return _ddlRecord.getGroupId();
	}

	/**
	* Sets the group ID of this d d l record.
	*
	* @param groupId the group ID of this d d l record
	*/
	public void setGroupId(long groupId) {
		_ddlRecord.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this d d l record.
	*
	* @return the company ID of this d d l record
	*/
	public long getCompanyId() {
		return _ddlRecord.getCompanyId();
	}

	/**
	* Sets the company ID of this d d l record.
	*
	* @param companyId the company ID of this d d l record
	*/
	public void setCompanyId(long companyId) {
		_ddlRecord.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this d d l record.
	*
	* @return the user ID of this d d l record
	*/
	public long getUserId() {
		return _ddlRecord.getUserId();
	}

	/**
	* Sets the user ID of this d d l record.
	*
	* @param userId the user ID of this d d l record
	*/
	public void setUserId(long userId) {
		_ddlRecord.setUserId(userId);
	}

	/**
	* Returns the user uuid of this d d l record.
	*
	* @return the user uuid of this d d l record
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecord.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d l record.
	*
	* @param userUuid the user uuid of this d d l record
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ddlRecord.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this d d l record.
	*
	* @return the user name of this d d l record
	*/
	public java.lang.String getUserName() {
		return _ddlRecord.getUserName();
	}

	/**
	* Sets the user name of this d d l record.
	*
	* @param userName the user name of this d d l record
	*/
	public void setUserName(java.lang.String userName) {
		_ddlRecord.setUserName(userName);
	}

	/**
	* Returns the version user ID of this d d l record.
	*
	* @return the version user ID of this d d l record
	*/
	public long getVersionUserId() {
		return _ddlRecord.getVersionUserId();
	}

	/**
	* Sets the version user ID of this d d l record.
	*
	* @param versionUserId the version user ID of this d d l record
	*/
	public void setVersionUserId(long versionUserId) {
		_ddlRecord.setVersionUserId(versionUserId);
	}

	/**
	* Returns the version user uuid of this d d l record.
	*
	* @return the version user uuid of this d d l record
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getVersionUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecord.getVersionUserUuid();
	}

	/**
	* Sets the version user uuid of this d d l record.
	*
	* @param versionUserUuid the version user uuid of this d d l record
	*/
	public void setVersionUserUuid(java.lang.String versionUserUuid) {
		_ddlRecord.setVersionUserUuid(versionUserUuid);
	}

	/**
	* Returns the version user name of this d d l record.
	*
	* @return the version user name of this d d l record
	*/
	public java.lang.String getVersionUserName() {
		return _ddlRecord.getVersionUserName();
	}

	/**
	* Sets the version user name of this d d l record.
	*
	* @param versionUserName the version user name of this d d l record
	*/
	public void setVersionUserName(java.lang.String versionUserName) {
		_ddlRecord.setVersionUserName(versionUserName);
	}

	/**
	* Returns the create date of this d d l record.
	*
	* @return the create date of this d d l record
	*/
	public java.util.Date getCreateDate() {
		return _ddlRecord.getCreateDate();
	}

	/**
	* Sets the create date of this d d l record.
	*
	* @param createDate the create date of this d d l record
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ddlRecord.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this d d l record.
	*
	* @return the modified date of this d d l record
	*/
	public java.util.Date getModifiedDate() {
		return _ddlRecord.getModifiedDate();
	}

	/**
	* Sets the modified date of this d d l record.
	*
	* @param modifiedDate the modified date of this d d l record
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ddlRecord.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the d d m storage ID of this d d l record.
	*
	* @return the d d m storage ID of this d d l record
	*/
	public long getDDMStorageId() {
		return _ddlRecord.getDDMStorageId();
	}

	/**
	* Sets the d d m storage ID of this d d l record.
	*
	* @param DDMStorageId the d d m storage ID of this d d l record
	*/
	public void setDDMStorageId(long DDMStorageId) {
		_ddlRecord.setDDMStorageId(DDMStorageId);
	}

	/**
	* Returns the record set ID of this d d l record.
	*
	* @return the record set ID of this d d l record
	*/
	public long getRecordSetId() {
		return _ddlRecord.getRecordSetId();
	}

	/**
	* Sets the record set ID of this d d l record.
	*
	* @param recordSetId the record set ID of this d d l record
	*/
	public void setRecordSetId(long recordSetId) {
		_ddlRecord.setRecordSetId(recordSetId);
	}

	/**
	* Returns the version of this d d l record.
	*
	* @return the version of this d d l record
	*/
	public java.lang.String getVersion() {
		return _ddlRecord.getVersion();
	}

	/**
	* Sets the version of this d d l record.
	*
	* @param version the version of this d d l record
	*/
	public void setVersion(java.lang.String version) {
		_ddlRecord.setVersion(version);
	}

	/**
	* Returns the display index of this d d l record.
	*
	* @return the display index of this d d l record
	*/
	public int getDisplayIndex() {
		return _ddlRecord.getDisplayIndex();
	}

	/**
	* Sets the display index of this d d l record.
	*
	* @param displayIndex the display index of this d d l record
	*/
	public void setDisplayIndex(int displayIndex) {
		_ddlRecord.setDisplayIndex(displayIndex);
	}

	public boolean isNew() {
		return _ddlRecord.isNew();
	}

	public void setNew(boolean n) {
		_ddlRecord.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddlRecord.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddlRecord.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddlRecord.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddlRecord.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddlRecord.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddlRecord.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddlRecord.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DDLRecordWrapper((DDLRecord)_ddlRecord.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord) {
		return _ddlRecord.compareTo(ddlRecord);
	}

	@Override
	public int hashCode() {
		return _ddlRecord.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.dynamicdatalists.model.DDLRecord> toCacheModel() {
		return _ddlRecord.toCacheModel();
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecord toEscapedModel() {
		return new DDLRecordWrapper(_ddlRecord.toEscapedModel());
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecord toUnescapedModel() {
		return new DDLRecordWrapper(_ddlRecord.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddlRecord.toString();
	}

	public java.lang.String toXmlString() {
		return _ddlRecord.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddlRecord.persist();
	}

	public com.liferay.portlet.dynamicdatamapping.storage.Field getField(
		java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecord.getField(fieldName);
	}

	public java.io.Serializable getFieldDataType(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecord.getFieldDataType(fieldName);
	}

	public com.liferay.portlet.dynamicdatamapping.storage.Fields getFields()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecord.getFields();
	}

	public java.io.Serializable getFieldType(java.lang.String fieldName)
		throws java.lang.Exception {
		return _ddlRecord.getFieldType(fieldName);
	}

	public java.io.Serializable getFieldValue(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecord.getFieldValue(fieldName);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getLatestRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecord.getLatestRecordVersion();
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecord.getRecordSet();
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecord.getRecordVersion();
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getRecordVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecord.getRecordVersion(version);
	}

	public int getStatus()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecord.getStatus();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public DDLRecord getWrappedDDLRecord() {
		return _ddlRecord;
	}

	public DDLRecord getWrappedModel() {
		return _ddlRecord;
	}

	public void resetOriginalValues() {
		_ddlRecord.resetOriginalValues();
	}

	private DDLRecord _ddlRecord;
}