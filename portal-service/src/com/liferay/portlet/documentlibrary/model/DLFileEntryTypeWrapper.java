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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLFileEntryType}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryType
 * @generated
 */
public class DLFileEntryTypeWrapper implements DLFileEntryType,
	ModelWrapper<DLFileEntryType> {
	public DLFileEntryTypeWrapper(DLFileEntryType dlFileEntryType) {
		_dlFileEntryType = dlFileEntryType;
	}

	public Class<?> getModelClass() {
		return DLFileEntryType.class;
	}

	public String getModelClassName() {
		return DLFileEntryType.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("fileEntryTypeId", getFileEntryTypeId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long fileEntryTypeId = (Long)attributes.get("fileEntryTypeId");

		if (fileEntryTypeId != null) {
			setFileEntryTypeId(fileEntryTypeId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	/**
	* Returns the primary key of this document library file entry type.
	*
	* @return the primary key of this document library file entry type
	*/
	public long getPrimaryKey() {
		return _dlFileEntryType.getPrimaryKey();
	}

	/**
	* Sets the primary key of this document library file entry type.
	*
	* @param primaryKey the primary key of this document library file entry type
	*/
	public void setPrimaryKey(long primaryKey) {
		_dlFileEntryType.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this document library file entry type.
	*
	* @return the uuid of this document library file entry type
	*/
	public java.lang.String getUuid() {
		return _dlFileEntryType.getUuid();
	}

	/**
	* Sets the uuid of this document library file entry type.
	*
	* @param uuid the uuid of this document library file entry type
	*/
	public void setUuid(java.lang.String uuid) {
		_dlFileEntryType.setUuid(uuid);
	}

	/**
	* Returns the file entry type ID of this document library file entry type.
	*
	* @return the file entry type ID of this document library file entry type
	*/
	public long getFileEntryTypeId() {
		return _dlFileEntryType.getFileEntryTypeId();
	}

	/**
	* Sets the file entry type ID of this document library file entry type.
	*
	* @param fileEntryTypeId the file entry type ID of this document library file entry type
	*/
	public void setFileEntryTypeId(long fileEntryTypeId) {
		_dlFileEntryType.setFileEntryTypeId(fileEntryTypeId);
	}

	/**
	* Returns the group ID of this document library file entry type.
	*
	* @return the group ID of this document library file entry type
	*/
	public long getGroupId() {
		return _dlFileEntryType.getGroupId();
	}

	/**
	* Sets the group ID of this document library file entry type.
	*
	* @param groupId the group ID of this document library file entry type
	*/
	public void setGroupId(long groupId) {
		_dlFileEntryType.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this document library file entry type.
	*
	* @return the company ID of this document library file entry type
	*/
	public long getCompanyId() {
		return _dlFileEntryType.getCompanyId();
	}

	/**
	* Sets the company ID of this document library file entry type.
	*
	* @param companyId the company ID of this document library file entry type
	*/
	public void setCompanyId(long companyId) {
		_dlFileEntryType.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this document library file entry type.
	*
	* @return the user ID of this document library file entry type
	*/
	public long getUserId() {
		return _dlFileEntryType.getUserId();
	}

	/**
	* Sets the user ID of this document library file entry type.
	*
	* @param userId the user ID of this document library file entry type
	*/
	public void setUserId(long userId) {
		_dlFileEntryType.setUserId(userId);
	}

	/**
	* Returns the user uuid of this document library file entry type.
	*
	* @return the user uuid of this document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryType.getUserUuid();
	}

	/**
	* Sets the user uuid of this document library file entry type.
	*
	* @param userUuid the user uuid of this document library file entry type
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_dlFileEntryType.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this document library file entry type.
	*
	* @return the user name of this document library file entry type
	*/
	public java.lang.String getUserName() {
		return _dlFileEntryType.getUserName();
	}

	/**
	* Sets the user name of this document library file entry type.
	*
	* @param userName the user name of this document library file entry type
	*/
	public void setUserName(java.lang.String userName) {
		_dlFileEntryType.setUserName(userName);
	}

	/**
	* Returns the create date of this document library file entry type.
	*
	* @return the create date of this document library file entry type
	*/
	public java.util.Date getCreateDate() {
		return _dlFileEntryType.getCreateDate();
	}

	/**
	* Sets the create date of this document library file entry type.
	*
	* @param createDate the create date of this document library file entry type
	*/
	public void setCreateDate(java.util.Date createDate) {
		_dlFileEntryType.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this document library file entry type.
	*
	* @return the modified date of this document library file entry type
	*/
	public java.util.Date getModifiedDate() {
		return _dlFileEntryType.getModifiedDate();
	}

	/**
	* Sets the modified date of this document library file entry type.
	*
	* @param modifiedDate the modified date of this document library file entry type
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_dlFileEntryType.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the name of this document library file entry type.
	*
	* @return the name of this document library file entry type
	*/
	public java.lang.String getName() {
		return _dlFileEntryType.getName();
	}

	/**
	* Sets the name of this document library file entry type.
	*
	* @param name the name of this document library file entry type
	*/
	public void setName(java.lang.String name) {
		_dlFileEntryType.setName(name);
	}

	/**
	* Returns the description of this document library file entry type.
	*
	* @return the description of this document library file entry type
	*/
	public java.lang.String getDescription() {
		return _dlFileEntryType.getDescription();
	}

	/**
	* Sets the description of this document library file entry type.
	*
	* @param description the description of this document library file entry type
	*/
	public void setDescription(java.lang.String description) {
		_dlFileEntryType.setDescription(description);
	}

	public boolean isNew() {
		return _dlFileEntryType.isNew();
	}

	public void setNew(boolean n) {
		_dlFileEntryType.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFileEntryType.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFileEntryType.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFileEntryType.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileEntryType.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlFileEntryType.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileEntryType.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileEntryType.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DLFileEntryTypeWrapper((DLFileEntryType)_dlFileEntryType.clone());
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		return _dlFileEntryType.compareTo(dlFileEntryType);
	}

	@Override
	public int hashCode() {
		return _dlFileEntryType.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.documentlibrary.model.DLFileEntryType> toCacheModel() {
		return _dlFileEntryType.toCacheModel();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntryType toEscapedModel() {
		return new DLFileEntryTypeWrapper(_dlFileEntryType.toEscapedModel());
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntryType toUnescapedModel() {
		return new DLFileEntryTypeWrapper(_dlFileEntryType.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlFileEntryType.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileEntryType.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryType.persist();
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryType.getDDMStructures();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public DLFileEntryType getWrappedDLFileEntryType() {
		return _dlFileEntryType;
	}

	public DLFileEntryType getWrappedModel() {
		return _dlFileEntryType;
	}

	public void resetOriginalValues() {
		_dlFileEntryType.resetOriginalValues();
	}

	private DLFileEntryType _dlFileEntryType;
}