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

package com.liferay.portlet.dynamicdatalists.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDLRecordVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersion
 * @generated
 */
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

	/**
	* Returns the primary key of this d d l record version.
	*
	* @return the primary key of this d d l record version
	*/
	@Override
	public long getPrimaryKey() {
		return _ddlRecordVersion.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d l record version.
	*
	* @param primaryKey the primary key of this d d l record version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddlRecordVersion.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the record version ID of this d d l record version.
	*
	* @return the record version ID of this d d l record version
	*/
	@Override
	public long getRecordVersionId() {
		return _ddlRecordVersion.getRecordVersionId();
	}

	/**
	* Sets the record version ID of this d d l record version.
	*
	* @param recordVersionId the record version ID of this d d l record version
	*/
	@Override
	public void setRecordVersionId(long recordVersionId) {
		_ddlRecordVersion.setRecordVersionId(recordVersionId);
	}

	/**
	* Returns the group ID of this d d l record version.
	*
	* @return the group ID of this d d l record version
	*/
	@Override
	public long getGroupId() {
		return _ddlRecordVersion.getGroupId();
	}

	/**
	* Sets the group ID of this d d l record version.
	*
	* @param groupId the group ID of this d d l record version
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddlRecordVersion.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this d d l record version.
	*
	* @return the company ID of this d d l record version
	*/
	@Override
	public long getCompanyId() {
		return _ddlRecordVersion.getCompanyId();
	}

	/**
	* Sets the company ID of this d d l record version.
	*
	* @param companyId the company ID of this d d l record version
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddlRecordVersion.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this d d l record version.
	*
	* @return the user ID of this d d l record version
	*/
	@Override
	public long getUserId() {
		return _ddlRecordVersion.getUserId();
	}

	/**
	* Sets the user ID of this d d l record version.
	*
	* @param userId the user ID of this d d l record version
	*/
	@Override
	public void setUserId(long userId) {
		_ddlRecordVersion.setUserId(userId);
	}

	/**
	* Returns the user uuid of this d d l record version.
	*
	* @return the user uuid of this d d l record version
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordVersion.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d l record version.
	*
	* @param userUuid the user uuid of this d d l record version
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddlRecordVersion.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this d d l record version.
	*
	* @return the user name of this d d l record version
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddlRecordVersion.getUserName();
	}

	/**
	* Sets the user name of this d d l record version.
	*
	* @param userName the user name of this d d l record version
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddlRecordVersion.setUserName(userName);
	}

	/**
	* Returns the create date of this d d l record version.
	*
	* @return the create date of this d d l record version
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _ddlRecordVersion.getCreateDate();
	}

	/**
	* Sets the create date of this d d l record version.
	*
	* @param createDate the create date of this d d l record version
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_ddlRecordVersion.setCreateDate(createDate);
	}

	/**
	* Returns the d d m storage ID of this d d l record version.
	*
	* @return the d d m storage ID of this d d l record version
	*/
	@Override
	public long getDDMStorageId() {
		return _ddlRecordVersion.getDDMStorageId();
	}

	/**
	* Sets the d d m storage ID of this d d l record version.
	*
	* @param DDMStorageId the d d m storage ID of this d d l record version
	*/
	@Override
	public void setDDMStorageId(long DDMStorageId) {
		_ddlRecordVersion.setDDMStorageId(DDMStorageId);
	}

	/**
	* Returns the record set ID of this d d l record version.
	*
	* @return the record set ID of this d d l record version
	*/
	@Override
	public long getRecordSetId() {
		return _ddlRecordVersion.getRecordSetId();
	}

	/**
	* Sets the record set ID of this d d l record version.
	*
	* @param recordSetId the record set ID of this d d l record version
	*/
	@Override
	public void setRecordSetId(long recordSetId) {
		_ddlRecordVersion.setRecordSetId(recordSetId);
	}

	/**
	* Returns the record ID of this d d l record version.
	*
	* @return the record ID of this d d l record version
	*/
	@Override
	public long getRecordId() {
		return _ddlRecordVersion.getRecordId();
	}

	/**
	* Sets the record ID of this d d l record version.
	*
	* @param recordId the record ID of this d d l record version
	*/
	@Override
	public void setRecordId(long recordId) {
		_ddlRecordVersion.setRecordId(recordId);
	}

	/**
	* Returns the version of this d d l record version.
	*
	* @return the version of this d d l record version
	*/
	@Override
	public java.lang.String getVersion() {
		return _ddlRecordVersion.getVersion();
	}

	/**
	* Sets the version of this d d l record version.
	*
	* @param version the version of this d d l record version
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_ddlRecordVersion.setVersion(version);
	}

	/**
	* Returns the display index of this d d l record version.
	*
	* @return the display index of this d d l record version
	*/
	@Override
	public int getDisplayIndex() {
		return _ddlRecordVersion.getDisplayIndex();
	}

	/**
	* Sets the display index of this d d l record version.
	*
	* @param displayIndex the display index of this d d l record version
	*/
	@Override
	public void setDisplayIndex(int displayIndex) {
		_ddlRecordVersion.setDisplayIndex(displayIndex);
	}

	/**
	* Returns the status of this d d l record version.
	*
	* @return the status of this d d l record version
	*/
	@Override
	public int getStatus() {
		return _ddlRecordVersion.getStatus();
	}

	/**
	* Sets the status of this d d l record version.
	*
	* @param status the status of this d d l record version
	*/
	@Override
	public void setStatus(int status) {
		_ddlRecordVersion.setStatus(status);
	}

	/**
	* Returns the status by user ID of this d d l record version.
	*
	* @return the status by user ID of this d d l record version
	*/
	@Override
	public long getStatusByUserId() {
		return _ddlRecordVersion.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this d d l record version.
	*
	* @param statusByUserId the status by user ID of this d d l record version
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_ddlRecordVersion.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this d d l record version.
	*
	* @return the status by user uuid of this d d l record version
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordVersion.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this d d l record version.
	*
	* @param statusByUserUuid the status by user uuid of this d d l record version
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_ddlRecordVersion.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this d d l record version.
	*
	* @return the status by user name of this d d l record version
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _ddlRecordVersion.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this d d l record version.
	*
	* @param statusByUserName the status by user name of this d d l record version
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_ddlRecordVersion.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this d d l record version.
	*
	* @return the status date of this d d l record version
	*/
	@Override
	public java.util.Date getStatusDate() {
		return _ddlRecordVersion.getStatusDate();
	}

	/**
	* Sets the status date of this d d l record version.
	*
	* @param statusDate the status date of this d d l record version
	*/
	@Override
	public void setStatusDate(java.util.Date statusDate) {
		_ddlRecordVersion.setStatusDate(statusDate);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #isApproved()}
	*/
	@Override
	public boolean getApproved() {
		return _ddlRecordVersion.getApproved();
	}

	/**
	* Returns <code>true</code> if this d d l record version is approved.
	*
	* @return <code>true</code> if this d d l record version is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _ddlRecordVersion.isApproved();
	}

	/**
	* Returns <code>true</code> if this d d l record version is denied.
	*
	* @return <code>true</code> if this d d l record version is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _ddlRecordVersion.isDenied();
	}

	/**
	* Returns <code>true</code> if this d d l record version is a draft.
	*
	* @return <code>true</code> if this d d l record version is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _ddlRecordVersion.isDraft();
	}

	/**
	* Returns <code>true</code> if this d d l record version is expired.
	*
	* @return <code>true</code> if this d d l record version is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _ddlRecordVersion.isExpired();
	}

	/**
	* Returns <code>true</code> if this d d l record version is inactive.
	*
	* @return <code>true</code> if this d d l record version is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _ddlRecordVersion.isInactive();
	}

	/**
	* Returns <code>true</code> if this d d l record version is incomplete.
	*
	* @return <code>true</code> if this d d l record version is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _ddlRecordVersion.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this d d l record version is in the Recycle Bin.
	*
	* @return <code>true</code> if this d d l record version is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _ddlRecordVersion.isInTrash();
	}

	/**
	* Returns <code>true</code> if this d d l record version is pending.
	*
	* @return <code>true</code> if this d d l record version is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _ddlRecordVersion.isPending();
	}

	/**
	* Returns <code>true</code> if this d d l record version is scheduled.
	*
	* @return <code>true</code> if this d d l record version is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _ddlRecordVersion.isScheduled();
	}

	@Override
	public boolean isNew() {
		return _ddlRecordVersion.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_ddlRecordVersion.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _ddlRecordVersion.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddlRecordVersion.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _ddlRecordVersion.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddlRecordVersion.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddlRecordVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddlRecordVersion.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddlRecordVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddlRecordVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddlRecordVersion.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DDLRecordVersionWrapper((DDLRecordVersion)_ddlRecordVersion.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion ddlRecordVersion) {
		return _ddlRecordVersion.compareTo(ddlRecordVersion);
	}

	@Override
	public int hashCode() {
		return _ddlRecordVersion.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> toCacheModel() {
		return _ddlRecordVersion.toCacheModel();
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion toEscapedModel() {
		return new DDLRecordVersionWrapper(_ddlRecordVersion.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion toUnescapedModel() {
		return new DDLRecordVersionWrapper(_ddlRecordVersion.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddlRecordVersion.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddlRecordVersion.toXmlString();
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord getRecord()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordVersion.getRecord();
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordVersion.getRecordSet();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public DDLRecordVersion getWrappedDDLRecordVersion() {
		return _ddlRecordVersion;
	}

	@Override
	public DDLRecordVersion getWrappedModel() {
		return _ddlRecordVersion;
	}

	@Override
	public void resetOriginalValues() {
		_ddlRecordVersion.resetOriginalValues();
	}

	private DDLRecordVersion _ddlRecordVersion;
}