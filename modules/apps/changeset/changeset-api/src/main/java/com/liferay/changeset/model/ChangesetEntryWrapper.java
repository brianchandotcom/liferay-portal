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

package com.liferay.changeset.model;

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
 * This class is a wrapper for {@link ChangesetEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetEntry
 * @generated
 */
@ProviderType
public class ChangesetEntryWrapper implements ChangesetEntry,
	ModelWrapper<ChangesetEntry> {
	public ChangesetEntryWrapper(ChangesetEntry changesetEntry) {
		_changesetEntry = changesetEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return ChangesetEntry.class;
	}

	@Override
	public String getModelClassName() {
		return ChangesetEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("changesetEntryId", getChangesetEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("changesetCollectionId", getChangesetCollectionId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long changesetEntryId = (Long)attributes.get("changesetEntryId");

		if (changesetEntryId != null) {
			setChangesetEntryId(changesetEntryId);
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

		Long changesetCollectionId = (Long)attributes.get(
				"changesetCollectionId");

		if (changesetCollectionId != null) {
			setChangesetCollectionId(changesetCollectionId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	@Override
	public Object clone() {
		return new ChangesetEntryWrapper((ChangesetEntry)_changesetEntry.clone());
	}

	@Override
	public int compareTo(ChangesetEntry changesetEntry) {
		return _changesetEntry.compareTo(changesetEntry);
	}

	/**
	* Returns the changeset collection ID of this changeset entry.
	*
	* @return the changeset collection ID of this changeset entry
	*/
	@Override
	public long getChangesetCollectionId() {
		return _changesetEntry.getChangesetCollectionId();
	}

	/**
	* Returns the changeset entry ID of this changeset entry.
	*
	* @return the changeset entry ID of this changeset entry
	*/
	@Override
	public long getChangesetEntryId() {
		return _changesetEntry.getChangesetEntryId();
	}

	/**
	* Returns the fully qualified class name of this changeset entry.
	*
	* @return the fully qualified class name of this changeset entry
	*/
	@Override
	public String getClassName() {
		return _changesetEntry.getClassName();
	}

	/**
	* Returns the class name ID of this changeset entry.
	*
	* @return the class name ID of this changeset entry
	*/
	@Override
	public long getClassNameId() {
		return _changesetEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this changeset entry.
	*
	* @return the class pk of this changeset entry
	*/
	@Override
	public long getClassPK() {
		return _changesetEntry.getClassPK();
	}

	/**
	* Returns the company ID of this changeset entry.
	*
	* @return the company ID of this changeset entry
	*/
	@Override
	public long getCompanyId() {
		return _changesetEntry.getCompanyId();
	}

	/**
	* Returns the create date of this changeset entry.
	*
	* @return the create date of this changeset entry
	*/
	@Override
	public Date getCreateDate() {
		return _changesetEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _changesetEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this changeset entry.
	*
	* @return the group ID of this changeset entry
	*/
	@Override
	public long getGroupId() {
		return _changesetEntry.getGroupId();
	}

	/**
	* Returns the modified date of this changeset entry.
	*
	* @return the modified date of this changeset entry
	*/
	@Override
	public Date getModifiedDate() {
		return _changesetEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this changeset entry.
	*
	* @return the primary key of this changeset entry
	*/
	@Override
	public long getPrimaryKey() {
		return _changesetEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _changesetEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this changeset entry.
	*
	* @return the user ID of this changeset entry
	*/
	@Override
	public long getUserId() {
		return _changesetEntry.getUserId();
	}

	/**
	* Returns the user name of this changeset entry.
	*
	* @return the user name of this changeset entry
	*/
	@Override
	public String getUserName() {
		return _changesetEntry.getUserName();
	}

	/**
	* Returns the user uuid of this changeset entry.
	*
	* @return the user uuid of this changeset entry
	*/
	@Override
	public String getUserUuid() {
		return _changesetEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _changesetEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _changesetEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _changesetEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _changesetEntry.isNew();
	}

	@Override
	public void persist() {
		_changesetEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_changesetEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the changeset collection ID of this changeset entry.
	*
	* @param changesetCollectionId the changeset collection ID of this changeset entry
	*/
	@Override
	public void setChangesetCollectionId(long changesetCollectionId) {
		_changesetEntry.setChangesetCollectionId(changesetCollectionId);
	}

	/**
	* Sets the changeset entry ID of this changeset entry.
	*
	* @param changesetEntryId the changeset entry ID of this changeset entry
	*/
	@Override
	public void setChangesetEntryId(long changesetEntryId) {
		_changesetEntry.setChangesetEntryId(changesetEntryId);
	}

	@Override
	public void setClassName(String className) {
		_changesetEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this changeset entry.
	*
	* @param classNameId the class name ID of this changeset entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_changesetEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this changeset entry.
	*
	* @param classPK the class pk of this changeset entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_changesetEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this changeset entry.
	*
	* @param companyId the company ID of this changeset entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_changesetEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this changeset entry.
	*
	* @param createDate the create date of this changeset entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_changesetEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_changesetEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_changesetEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_changesetEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this changeset entry.
	*
	* @param groupId the group ID of this changeset entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_changesetEntry.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this changeset entry.
	*
	* @param modifiedDate the modified date of this changeset entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_changesetEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_changesetEntry.setNew(n);
	}

	/**
	* Sets the primary key of this changeset entry.
	*
	* @param primaryKey the primary key of this changeset entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_changesetEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_changesetEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this changeset entry.
	*
	* @param userId the user ID of this changeset entry
	*/
	@Override
	public void setUserId(long userId) {
		_changesetEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this changeset entry.
	*
	* @param userName the user name of this changeset entry
	*/
	@Override
	public void setUserName(String userName) {
		_changesetEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this changeset entry.
	*
	* @param userUuid the user uuid of this changeset entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_changesetEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ChangesetEntry> toCacheModel() {
		return _changesetEntry.toCacheModel();
	}

	@Override
	public ChangesetEntry toEscapedModel() {
		return new ChangesetEntryWrapper(_changesetEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _changesetEntry.toString();
	}

	@Override
	public ChangesetEntry toUnescapedModel() {
		return new ChangesetEntryWrapper(_changesetEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _changesetEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangesetEntryWrapper)) {
			return false;
		}

		ChangesetEntryWrapper changesetEntryWrapper = (ChangesetEntryWrapper)obj;

		if (Objects.equals(_changesetEntry,
					changesetEntryWrapper._changesetEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public ChangesetEntry getWrappedModel() {
		return _changesetEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _changesetEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _changesetEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_changesetEntry.resetOriginalValues();
	}

	private final ChangesetEntry _changesetEntry;
}