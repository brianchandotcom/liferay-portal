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

package com.liferay.commerce.product.model;

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
 * This class is a wrapper for {@link CPDefinitionMedia}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionMedia
 * @generated
 */
@ProviderType
public class CPDefinitionMediaWrapper implements CPDefinitionMedia,
	ModelWrapper<CPDefinitionMedia> {
	public CPDefinitionMediaWrapper(CPDefinitionMedia cpDefinitionMedia) {
		_cpDefinitionMedia = cpDefinitionMedia;
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinitionMedia.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinitionMedia.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionMediaId", getCPDefinitionMediaId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("DDMContent", getDDMContent());
		attributes.put("priority", getPriority());
		attributes.put("CPMediaTypeId", getCPMediaTypeId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDefinitionMediaId = (Long)attributes.get("CPDefinitionMediaId");

		if (CPDefinitionMediaId != null) {
			setCPDefinitionMediaId(CPDefinitionMediaId);
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

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		String DDMContent = (String)attributes.get("DDMContent");

		if (DDMContent != null) {
			setDDMContent(DDMContent);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Long CPMediaTypeId = (Long)attributes.get("CPMediaTypeId");

		if (CPMediaTypeId != null) {
			setCPMediaTypeId(CPMediaTypeId);
		}
	}

	@Override
	public CPDefinitionMedia toEscapedModel() {
		return new CPDefinitionMediaWrapper(_cpDefinitionMedia.toEscapedModel());
	}

	@Override
	public CPDefinitionMedia toUnescapedModel() {
		return new CPDefinitionMediaWrapper(_cpDefinitionMedia.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _cpDefinitionMedia.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpDefinitionMedia.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this cp definition media is in the Recycle Bin.
	*
	* @return <code>true</code> if this cp definition media is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _cpDefinitionMedia.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this cp definition media is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this cp definition media is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _cpDefinitionMedia.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _cpDefinitionMedia.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _cpDefinitionMedia.isInTrashImplicitly();
	}

	@Override
	public boolean isNew() {
		return _cpDefinitionMedia.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpDefinitionMedia.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPDefinitionMedia> toCacheModel() {
		return _cpDefinitionMedia.toCacheModel();
	}

	/**
	* Returns the trash handler for this cp definition media.
	*
	* @return the trash handler for this cp definition media
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _cpDefinitionMedia.getTrashHandler();
	}

	/**
	* Returns the trash entry created when this cp definition media was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this cp definition media.
	*
	* @return the trash entry created when this cp definition media was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMedia.getTrashEntry();
	}

	@Override
	public int compareTo(CPDefinitionMedia cpDefinitionMedia) {
		return _cpDefinitionMedia.compareTo(cpDefinitionMedia);
	}

	/**
	* Returns the priority of this cp definition media.
	*
	* @return the priority of this cp definition media
	*/
	@Override
	public int getPriority() {
		return _cpDefinitionMedia.getPriority();
	}

	/**
	* Returns the status of this cp definition media.
	*
	* @return the status of this cp definition media
	*/
	@Override
	public int getStatus() {
		return _cpDefinitionMedia.getStatus();
	}

	@Override
	public int hashCode() {
		return _cpDefinitionMedia.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpDefinitionMedia.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CPDefinitionMediaWrapper((CPDefinitionMedia)_cpDefinitionMedia.clone());
	}

	/**
	* Returns the ddm content of this cp definition media.
	*
	* @return the ddm content of this cp definition media
	*/
	@Override
	public java.lang.String getDDMContent() {
		return _cpDefinitionMedia.getDDMContent();
	}

	/**
	* Returns the user name of this cp definition media.
	*
	* @return the user name of this cp definition media
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpDefinitionMedia.getUserName();
	}

	/**
	* Returns the user uuid of this cp definition media.
	*
	* @return the user uuid of this cp definition media
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpDefinitionMedia.getUserUuid();
	}

	/**
	* Returns the uuid of this cp definition media.
	*
	* @return the uuid of this cp definition media
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpDefinitionMedia.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _cpDefinitionMedia.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpDefinitionMedia.toXmlString();
	}

	/**
	* Returns the create date of this cp definition media.
	*
	* @return the create date of this cp definition media
	*/
	@Override
	public Date getCreateDate() {
		return _cpDefinitionMedia.getCreateDate();
	}

	/**
	* Returns the modified date of this cp definition media.
	*
	* @return the modified date of this cp definition media
	*/
	@Override
	public Date getModifiedDate() {
		return _cpDefinitionMedia.getModifiedDate();
	}

	/**
	* Returns the cp definition ID of this cp definition media.
	*
	* @return the cp definition ID of this cp definition media
	*/
	@Override
	public long getCPDefinitionId() {
		return _cpDefinitionMedia.getCPDefinitionId();
	}

	/**
	* Returns the cp definition media ID of this cp definition media.
	*
	* @return the cp definition media ID of this cp definition media
	*/
	@Override
	public long getCPDefinitionMediaId() {
		return _cpDefinitionMedia.getCPDefinitionMediaId();
	}

	/**
	* Returns the cp media type ID of this cp definition media.
	*
	* @return the cp media type ID of this cp definition media
	*/
	@Override
	public long getCPMediaTypeId() {
		return _cpDefinitionMedia.getCPMediaTypeId();
	}

	/**
	* Returns the company ID of this cp definition media.
	*
	* @return the company ID of this cp definition media
	*/
	@Override
	public long getCompanyId() {
		return _cpDefinitionMedia.getCompanyId();
	}

	/**
	* Returns the file entry ID of this cp definition media.
	*
	* @return the file entry ID of this cp definition media
	*/
	@Override
	public long getFileEntryId() {
		return _cpDefinitionMedia.getFileEntryId();
	}

	/**
	* Returns the group ID of this cp definition media.
	*
	* @return the group ID of this cp definition media
	*/
	@Override
	public long getGroupId() {
		return _cpDefinitionMedia.getGroupId();
	}

	/**
	* Returns the primary key of this cp definition media.
	*
	* @return the primary key of this cp definition media
	*/
	@Override
	public long getPrimaryKey() {
		return _cpDefinitionMedia.getPrimaryKey();
	}

	/**
	* Returns the class primary key of the trash entry for this cp definition media.
	*
	* @return the class primary key of the trash entry for this cp definition media
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _cpDefinitionMedia.getTrashEntryClassPK();
	}

	/**
	* Returns the user ID of this cp definition media.
	*
	* @return the user ID of this cp definition media
	*/
	@Override
	public long getUserId() {
		return _cpDefinitionMedia.getUserId();
	}

	@Override
	public void persist() {
		_cpDefinitionMedia.persist();
	}

	/**
	* Sets the cp definition ID of this cp definition media.
	*
	* @param CPDefinitionId the cp definition ID of this cp definition media
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cpDefinitionMedia.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the cp definition media ID of this cp definition media.
	*
	* @param CPDefinitionMediaId the cp definition media ID of this cp definition media
	*/
	@Override
	public void setCPDefinitionMediaId(long CPDefinitionMediaId) {
		_cpDefinitionMedia.setCPDefinitionMediaId(CPDefinitionMediaId);
	}

	/**
	* Sets the cp media type ID of this cp definition media.
	*
	* @param CPMediaTypeId the cp media type ID of this cp definition media
	*/
	@Override
	public void setCPMediaTypeId(long CPMediaTypeId) {
		_cpDefinitionMedia.setCPMediaTypeId(CPMediaTypeId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpDefinitionMedia.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp definition media.
	*
	* @param companyId the company ID of this cp definition media
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpDefinitionMedia.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this cp definition media.
	*
	* @param createDate the create date of this cp definition media
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpDefinitionMedia.setCreateDate(createDate);
	}

	/**
	* Sets the ddm content of this cp definition media.
	*
	* @param DDMContent the ddm content of this cp definition media
	*/
	@Override
	public void setDDMContent(java.lang.String DDMContent) {
		_cpDefinitionMedia.setDDMContent(DDMContent);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpDefinitionMedia.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpDefinitionMedia.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpDefinitionMedia.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file entry ID of this cp definition media.
	*
	* @param fileEntryId the file entry ID of this cp definition media
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_cpDefinitionMedia.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the group ID of this cp definition media.
	*
	* @param groupId the group ID of this cp definition media
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpDefinitionMedia.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this cp definition media.
	*
	* @param modifiedDate the modified date of this cp definition media
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpDefinitionMedia.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpDefinitionMedia.setNew(n);
	}

	/**
	* Sets the primary key of this cp definition media.
	*
	* @param primaryKey the primary key of this cp definition media
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpDefinitionMedia.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpDefinitionMedia.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this cp definition media.
	*
	* @param priority the priority of this cp definition media
	*/
	@Override
	public void setPriority(int priority) {
		_cpDefinitionMedia.setPriority(priority);
	}

	/**
	* Sets the user ID of this cp definition media.
	*
	* @param userId the user ID of this cp definition media
	*/
	@Override
	public void setUserId(long userId) {
		_cpDefinitionMedia.setUserId(userId);
	}

	/**
	* Sets the user name of this cp definition media.
	*
	* @param userName the user name of this cp definition media
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpDefinitionMedia.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp definition media.
	*
	* @param userUuid the user uuid of this cp definition media
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpDefinitionMedia.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp definition media.
	*
	* @param uuid the uuid of this cp definition media
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpDefinitionMedia.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionMediaWrapper)) {
			return false;
		}

		CPDefinitionMediaWrapper cpDefinitionMediaWrapper = (CPDefinitionMediaWrapper)obj;

		if (Objects.equals(_cpDefinitionMedia,
					cpDefinitionMediaWrapper._cpDefinitionMedia)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpDefinitionMedia.getStagedModelType();
	}

	@Override
	public CPDefinitionMedia getWrappedModel() {
		return _cpDefinitionMedia;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpDefinitionMedia.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpDefinitionMedia.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpDefinitionMedia.resetOriginalValues();
	}

	private final CPDefinitionMedia _cpDefinitionMedia;
}