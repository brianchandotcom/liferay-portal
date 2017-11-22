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

package com.liferay.reading.time.model;

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
 * This class is a wrapper for {@link ReadingTimeEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReadingTimeEntry
 * @generated
 */
@ProviderType
public class ReadingTimeEntryWrapper implements ReadingTimeEntry,
	ModelWrapper<ReadingTimeEntry> {
	public ReadingTimeEntryWrapper(ReadingTimeEntry readingTimeEntry) {
		_readingTimeEntry = readingTimeEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return ReadingTimeEntry.class;
	}

	@Override
	public String getModelClassName() {
		return ReadingTimeEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("entryId", getEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("readingTime", getReadingTime());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
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

		Long readingTime = (Long)attributes.get("readingTime");

		if (readingTime != null) {
			setReadingTime(readingTime);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new ReadingTimeEntryWrapper((ReadingTimeEntry)_readingTimeEntry.clone());
	}

	@Override
	public int compareTo(ReadingTimeEntry readingTimeEntry) {
		return _readingTimeEntry.compareTo(readingTimeEntry);
	}

	/**
	* Returns the fully qualified class name of this reading time entry.
	*
	* @return the fully qualified class name of this reading time entry
	*/
	@Override
	public java.lang.String getClassName() {
		return _readingTimeEntry.getClassName();
	}

	/**
	* Returns the class name ID of this reading time entry.
	*
	* @return the class name ID of this reading time entry
	*/
	@Override
	public long getClassNameId() {
		return _readingTimeEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this reading time entry.
	*
	* @return the class pk of this reading time entry
	*/
	@Override
	public long getClassPK() {
		return _readingTimeEntry.getClassPK();
	}

	/**
	* Returns the company ID of this reading time entry.
	*
	* @return the company ID of this reading time entry
	*/
	@Override
	public long getCompanyId() {
		return _readingTimeEntry.getCompanyId();
	}

	/**
	* Returns the create date of this reading time entry.
	*
	* @return the create date of this reading time entry
	*/
	@Override
	public Date getCreateDate() {
		return _readingTimeEntry.getCreateDate();
	}

	/**
	* Returns the entry ID of this reading time entry.
	*
	* @return the entry ID of this reading time entry
	*/
	@Override
	public long getEntryId() {
		return _readingTimeEntry.getEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _readingTimeEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this reading time entry.
	*
	* @return the group ID of this reading time entry
	*/
	@Override
	public long getGroupId() {
		return _readingTimeEntry.getGroupId();
	}

	/**
	* Returns the modified date of this reading time entry.
	*
	* @return the modified date of this reading time entry
	*/
	@Override
	public Date getModifiedDate() {
		return _readingTimeEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this reading time entry.
	*
	* @return the primary key of this reading time entry
	*/
	@Override
	public long getPrimaryKey() {
		return _readingTimeEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _readingTimeEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the reading time of this reading time entry.
	*
	* @return the reading time of this reading time entry
	*/
	@Override
	public long getReadingTime() {
		return _readingTimeEntry.getReadingTime();
	}

	/**
	* Returns the status of this reading time entry.
	*
	* @return the status of this reading time entry
	*/
	@Override
	public int getStatus() {
		return _readingTimeEntry.getStatus();
	}

	/**
	* Returns the trash entry created when this reading time entry was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this reading time entry.
	*
	* @return the trash entry created when this reading time entry was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _readingTimeEntry.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this reading time entry.
	*
	* @return the class primary key of the trash entry for this reading time entry
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _readingTimeEntry.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this reading time entry.
	*
	* @return the trash handler for this reading time entry
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _readingTimeEntry.getTrashHandler();
	}

	/**
	* Returns the uuid of this reading time entry.
	*
	* @return the uuid of this reading time entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _readingTimeEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _readingTimeEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _readingTimeEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _readingTimeEntry.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this reading time entry is in the Recycle Bin.
	*
	* @return <code>true</code> if this reading time entry is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _readingTimeEntry.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this reading time entry is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this reading time entry is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _readingTimeEntry.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _readingTimeEntry.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _readingTimeEntry.isInTrashImplicitly();
	}

	@Override
	public boolean isNew() {
		return _readingTimeEntry.isNew();
	}

	@Override
	public void persist() {
		_readingTimeEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_readingTimeEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_readingTimeEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this reading time entry.
	*
	* @param classNameId the class name ID of this reading time entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_readingTimeEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this reading time entry.
	*
	* @param classPK the class pk of this reading time entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_readingTimeEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this reading time entry.
	*
	* @param companyId the company ID of this reading time entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_readingTimeEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this reading time entry.
	*
	* @param createDate the create date of this reading time entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_readingTimeEntry.setCreateDate(createDate);
	}

	/**
	* Sets the entry ID of this reading time entry.
	*
	* @param entryId the entry ID of this reading time entry
	*/
	@Override
	public void setEntryId(long entryId) {
		_readingTimeEntry.setEntryId(entryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_readingTimeEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_readingTimeEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_readingTimeEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this reading time entry.
	*
	* @param groupId the group ID of this reading time entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_readingTimeEntry.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this reading time entry.
	*
	* @param modifiedDate the modified date of this reading time entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_readingTimeEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_readingTimeEntry.setNew(n);
	}

	/**
	* Sets the primary key of this reading time entry.
	*
	* @param primaryKey the primary key of this reading time entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_readingTimeEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_readingTimeEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the reading time of this reading time entry.
	*
	* @param readingTime the reading time of this reading time entry
	*/
	@Override
	public void setReadingTime(long readingTime) {
		_readingTimeEntry.setReadingTime(readingTime);
	}

	/**
	* Sets the uuid of this reading time entry.
	*
	* @param uuid the uuid of this reading time entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_readingTimeEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ReadingTimeEntry> toCacheModel() {
		return _readingTimeEntry.toCacheModel();
	}

	@Override
	public ReadingTimeEntry toEscapedModel() {
		return new ReadingTimeEntryWrapper(_readingTimeEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _readingTimeEntry.toString();
	}

	@Override
	public ReadingTimeEntry toUnescapedModel() {
		return new ReadingTimeEntryWrapper(_readingTimeEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _readingTimeEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ReadingTimeEntryWrapper)) {
			return false;
		}

		ReadingTimeEntryWrapper readingTimeEntryWrapper = (ReadingTimeEntryWrapper)obj;

		if (Objects.equals(_readingTimeEntry,
					readingTimeEntryWrapper._readingTimeEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _readingTimeEntry.getStagedModelType();
	}

	@Override
	public ReadingTimeEntry getWrappedModel() {
		return _readingTimeEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _readingTimeEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _readingTimeEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_readingTimeEntry.resetOriginalValues();
	}

	private final ReadingTimeEntry _readingTimeEntry;
}