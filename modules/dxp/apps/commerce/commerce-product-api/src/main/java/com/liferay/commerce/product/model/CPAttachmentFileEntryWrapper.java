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
 * This class is a wrapper for {@link CPAttachmentFileEntry}.
 * </p>
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntry
 * @generated
 */
@ProviderType
public class CPAttachmentFileEntryWrapper implements CPAttachmentFileEntry,
	ModelWrapper<CPAttachmentFileEntry> {
	public CPAttachmentFileEntryWrapper(
		CPAttachmentFileEntry cpAttachmentFileEntry) {
		_cpAttachmentFileEntry = cpAttachmentFileEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CPAttachmentFileEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CPAttachmentFileEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPAttachmentFileEntryId", getCPAttachmentFileEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("json", getJson());
		attributes.put("priority", getPriority());
		attributes.put("type", getType());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPAttachmentFileEntryId = (Long)attributes.get(
				"CPAttachmentFileEntryId");

		if (CPAttachmentFileEntryId != null) {
			setCPAttachmentFileEntryId(CPAttachmentFileEntryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		String json = (String)attributes.get("json");

		if (json != null) {
			setJson(json);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public CPAttachmentFileEntry toEscapedModel() {
		return new CPAttachmentFileEntryWrapper(_cpAttachmentFileEntry.toEscapedModel());
	}

	@Override
	public CPAttachmentFileEntry toUnescapedModel() {
		return new CPAttachmentFileEntryWrapper(_cpAttachmentFileEntry.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _cpAttachmentFileEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpAttachmentFileEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpAttachmentFileEntry.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpAttachmentFileEntry.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPAttachmentFileEntry> toCacheModel() {
		return _cpAttachmentFileEntry.toCacheModel();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntry.getFileEntry();
	}

	@Override
	public int compareTo(CPAttachmentFileEntry cpAttachmentFileEntry) {
		return _cpAttachmentFileEntry.compareTo(cpAttachmentFileEntry);
	}

	/**
	* Returns the priority of this cp attachment file entry.
	*
	* @return the priority of this cp attachment file entry
	*/
	@Override
	public int getPriority() {
		return _cpAttachmentFileEntry.getPriority();
	}

	/**
	* Returns the type of this cp attachment file entry.
	*
	* @return the type of this cp attachment file entry
	*/
	@Override
	public int getType() {
		return _cpAttachmentFileEntry.getType();
	}

	@Override
	public int hashCode() {
		return _cpAttachmentFileEntry.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpAttachmentFileEntry.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CPAttachmentFileEntryWrapper((CPAttachmentFileEntry)_cpAttachmentFileEntry.clone());
	}

	/**
	* Returns the fully qualified class name of this cp attachment file entry.
	*
	* @return the fully qualified class name of this cp attachment file entry
	*/
	@Override
	public java.lang.String getClassName() {
		return _cpAttachmentFileEntry.getClassName();
	}

	/**
	* Returns the json of this cp attachment file entry.
	*
	* @return the json of this cp attachment file entry
	*/
	@Override
	public java.lang.String getJson() {
		return _cpAttachmentFileEntry.getJson();
	}

	/**
	* Returns the user name of this cp attachment file entry.
	*
	* @return the user name of this cp attachment file entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpAttachmentFileEntry.getUserName();
	}

	/**
	* Returns the user uuid of this cp attachment file entry.
	*
	* @return the user uuid of this cp attachment file entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpAttachmentFileEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this cp attachment file entry.
	*
	* @return the uuid of this cp attachment file entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpAttachmentFileEntry.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _cpAttachmentFileEntry.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpAttachmentFileEntry.toXmlString();
	}

	/**
	* Returns the create date of this cp attachment file entry.
	*
	* @return the create date of this cp attachment file entry
	*/
	@Override
	public Date getCreateDate() {
		return _cpAttachmentFileEntry.getCreateDate();
	}

	/**
	* Returns the display date of this cp attachment file entry.
	*
	* @return the display date of this cp attachment file entry
	*/
	@Override
	public Date getDisplayDate() {
		return _cpAttachmentFileEntry.getDisplayDate();
	}

	/**
	* Returns the expiration date of this cp attachment file entry.
	*
	* @return the expiration date of this cp attachment file entry
	*/
	@Override
	public Date getExpirationDate() {
		return _cpAttachmentFileEntry.getExpirationDate();
	}

	/**
	* Returns the last publish date of this cp attachment file entry.
	*
	* @return the last publish date of this cp attachment file entry
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpAttachmentFileEntry.getLastPublishDate();
	}

	/**
	* Returns the modified date of this cp attachment file entry.
	*
	* @return the modified date of this cp attachment file entry
	*/
	@Override
	public Date getModifiedDate() {
		return _cpAttachmentFileEntry.getModifiedDate();
	}

	/**
	* Returns the cp attachment file entry ID of this cp attachment file entry.
	*
	* @return the cp attachment file entry ID of this cp attachment file entry
	*/
	@Override
	public long getCPAttachmentFileEntryId() {
		return _cpAttachmentFileEntry.getCPAttachmentFileEntryId();
	}

	/**
	* Returns the class name ID of this cp attachment file entry.
	*
	* @return the class name ID of this cp attachment file entry
	*/
	@Override
	public long getClassNameId() {
		return _cpAttachmentFileEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this cp attachment file entry.
	*
	* @return the class pk of this cp attachment file entry
	*/
	@Override
	public long getClassPK() {
		return _cpAttachmentFileEntry.getClassPK();
	}

	/**
	* Returns the company ID of this cp attachment file entry.
	*
	* @return the company ID of this cp attachment file entry
	*/
	@Override
	public long getCompanyId() {
		return _cpAttachmentFileEntry.getCompanyId();
	}

	/**
	* Returns the file entry ID of this cp attachment file entry.
	*
	* @return the file entry ID of this cp attachment file entry
	*/
	@Override
	public long getFileEntryId() {
		return _cpAttachmentFileEntry.getFileEntryId();
	}

	/**
	* Returns the group ID of this cp attachment file entry.
	*
	* @return the group ID of this cp attachment file entry
	*/
	@Override
	public long getGroupId() {
		return _cpAttachmentFileEntry.getGroupId();
	}

	/**
	* Returns the primary key of this cp attachment file entry.
	*
	* @return the primary key of this cp attachment file entry
	*/
	@Override
	public long getPrimaryKey() {
		return _cpAttachmentFileEntry.getPrimaryKey();
	}

	/**
	* Returns the user ID of this cp attachment file entry.
	*
	* @return the user ID of this cp attachment file entry
	*/
	@Override
	public long getUserId() {
		return _cpAttachmentFileEntry.getUserId();
	}

	@Override
	public void persist() {
		_cpAttachmentFileEntry.persist();
	}

	/**
	* Sets the cp attachment file entry ID of this cp attachment file entry.
	*
	* @param CPAttachmentFileEntryId the cp attachment file entry ID of this cp attachment file entry
	*/
	@Override
	public void setCPAttachmentFileEntryId(long CPAttachmentFileEntryId) {
		_cpAttachmentFileEntry.setCPAttachmentFileEntryId(CPAttachmentFileEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpAttachmentFileEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_cpAttachmentFileEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this cp attachment file entry.
	*
	* @param classNameId the class name ID of this cp attachment file entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_cpAttachmentFileEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this cp attachment file entry.
	*
	* @param classPK the class pk of this cp attachment file entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_cpAttachmentFileEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this cp attachment file entry.
	*
	* @param companyId the company ID of this cp attachment file entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpAttachmentFileEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this cp attachment file entry.
	*
	* @param createDate the create date of this cp attachment file entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpAttachmentFileEntry.setCreateDate(createDate);
	}

	/**
	* Sets the display date of this cp attachment file entry.
	*
	* @param displayDate the display date of this cp attachment file entry
	*/
	@Override
	public void setDisplayDate(Date displayDate) {
		_cpAttachmentFileEntry.setDisplayDate(displayDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpAttachmentFileEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpAttachmentFileEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpAttachmentFileEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this cp attachment file entry.
	*
	* @param expirationDate the expiration date of this cp attachment file entry
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_cpAttachmentFileEntry.setExpirationDate(expirationDate);
	}

	/**
	* Sets the file entry ID of this cp attachment file entry.
	*
	* @param fileEntryId the file entry ID of this cp attachment file entry
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_cpAttachmentFileEntry.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the group ID of this cp attachment file entry.
	*
	* @param groupId the group ID of this cp attachment file entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpAttachmentFileEntry.setGroupId(groupId);
	}

	/**
	* Sets the json of this cp attachment file entry.
	*
	* @param json the json of this cp attachment file entry
	*/
	@Override
	public void setJson(java.lang.String json) {
		_cpAttachmentFileEntry.setJson(json);
	}

	/**
	* Sets the last publish date of this cp attachment file entry.
	*
	* @param lastPublishDate the last publish date of this cp attachment file entry
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpAttachmentFileEntry.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this cp attachment file entry.
	*
	* @param modifiedDate the modified date of this cp attachment file entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpAttachmentFileEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpAttachmentFileEntry.setNew(n);
	}

	/**
	* Sets the primary key of this cp attachment file entry.
	*
	* @param primaryKey the primary key of this cp attachment file entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpAttachmentFileEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpAttachmentFileEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this cp attachment file entry.
	*
	* @param priority the priority of this cp attachment file entry
	*/
	@Override
	public void setPriority(int priority) {
		_cpAttachmentFileEntry.setPriority(priority);
	}

	/**
	* Sets the type of this cp attachment file entry.
	*
	* @param type the type of this cp attachment file entry
	*/
	@Override
	public void setType(int type) {
		_cpAttachmentFileEntry.setType(type);
	}

	/**
	* Sets the user ID of this cp attachment file entry.
	*
	* @param userId the user ID of this cp attachment file entry
	*/
	@Override
	public void setUserId(long userId) {
		_cpAttachmentFileEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this cp attachment file entry.
	*
	* @param userName the user name of this cp attachment file entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpAttachmentFileEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp attachment file entry.
	*
	* @param userUuid the user uuid of this cp attachment file entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpAttachmentFileEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp attachment file entry.
	*
	* @param uuid the uuid of this cp attachment file entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpAttachmentFileEntry.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPAttachmentFileEntryWrapper)) {
			return false;
		}

		CPAttachmentFileEntryWrapper cpAttachmentFileEntryWrapper = (CPAttachmentFileEntryWrapper)obj;

		if (Objects.equals(_cpAttachmentFileEntry,
					cpAttachmentFileEntryWrapper._cpAttachmentFileEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpAttachmentFileEntry.getStagedModelType();
	}

	@Override
	public CPAttachmentFileEntry getWrappedModel() {
		return _cpAttachmentFileEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpAttachmentFileEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpAttachmentFileEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpAttachmentFileEntry.resetOriginalValues();
	}

	private final CPAttachmentFileEntry _cpAttachmentFileEntry;
}