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

package com.liferay.change.tracking.model;

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
 * This class is a wrapper for {@link ChangeTrackingEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingEntry
 * @generated
 */
@ProviderType
public class ChangeTrackingEntryWrapper implements ChangeTrackingEntry,
	ModelWrapper<ChangeTrackingEntry> {
	public ChangeTrackingEntryWrapper(ChangeTrackingEntry changeTrackingEntry) {
		_changeTrackingEntry = changeTrackingEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return ChangeTrackingEntry.class;
	}

	@Override
	public String getModelClassName() {
		return ChangeTrackingEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("changeTrackingEntryId", getChangeTrackingEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("resourcePrimKey", getResourcePrimKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long changeTrackingEntryId = (Long)attributes.get(
				"changeTrackingEntryId");

		if (changeTrackingEntryId != null) {
			setChangeTrackingEntryId(changeTrackingEntryId);
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

		Long resourcePrimKey = (Long)attributes.get("resourcePrimKey");

		if (resourcePrimKey != null) {
			setResourcePrimKey(resourcePrimKey);
		}
	}

	@Override
	public Object clone() {
		return new ChangeTrackingEntryWrapper((ChangeTrackingEntry)_changeTrackingEntry.clone());
	}

	@Override
	public int compareTo(ChangeTrackingEntry changeTrackingEntry) {
		return _changeTrackingEntry.compareTo(changeTrackingEntry);
	}

	/**
	* Returns the change tracking entry ID of this change tracking entry.
	*
	* @return the change tracking entry ID of this change tracking entry
	*/
	@Override
	public long getChangeTrackingEntryId() {
		return _changeTrackingEntry.getChangeTrackingEntryId();
	}

	/**
	* Returns the fully qualified class name of this change tracking entry.
	*
	* @return the fully qualified class name of this change tracking entry
	*/
	@Override
	public String getClassName() {
		return _changeTrackingEntry.getClassName();
	}

	/**
	* Returns the class name ID of this change tracking entry.
	*
	* @return the class name ID of this change tracking entry
	*/
	@Override
	public long getClassNameId() {
		return _changeTrackingEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this change tracking entry.
	*
	* @return the class pk of this change tracking entry
	*/
	@Override
	public long getClassPK() {
		return _changeTrackingEntry.getClassPK();
	}

	/**
	* Returns the company ID of this change tracking entry.
	*
	* @return the company ID of this change tracking entry
	*/
	@Override
	public long getCompanyId() {
		return _changeTrackingEntry.getCompanyId();
	}

	/**
	* Returns the create date of this change tracking entry.
	*
	* @return the create date of this change tracking entry
	*/
	@Override
	public Date getCreateDate() {
		return _changeTrackingEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _changeTrackingEntry.getExpandoBridge();
	}

	/**
	* Returns the modified date of this change tracking entry.
	*
	* @return the modified date of this change tracking entry
	*/
	@Override
	public Date getModifiedDate() {
		return _changeTrackingEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this change tracking entry.
	*
	* @return the primary key of this change tracking entry
	*/
	@Override
	public long getPrimaryKey() {
		return _changeTrackingEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _changeTrackingEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the resource prim key of this change tracking entry.
	*
	* @return the resource prim key of this change tracking entry
	*/
	@Override
	public long getResourcePrimKey() {
		return _changeTrackingEntry.getResourcePrimKey();
	}

	/**
	* Returns the user ID of this change tracking entry.
	*
	* @return the user ID of this change tracking entry
	*/
	@Override
	public long getUserId() {
		return _changeTrackingEntry.getUserId();
	}

	/**
	* Returns the user name of this change tracking entry.
	*
	* @return the user name of this change tracking entry
	*/
	@Override
	public String getUserName() {
		return _changeTrackingEntry.getUserName();
	}

	/**
	* Returns the user uuid of this change tracking entry.
	*
	* @return the user uuid of this change tracking entry
	*/
	@Override
	public String getUserUuid() {
		return _changeTrackingEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _changeTrackingEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _changeTrackingEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _changeTrackingEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _changeTrackingEntry.isNew();
	}

	@Override
	public boolean isResourceMain() {
		return _changeTrackingEntry.isResourceMain();
	}

	@Override
	public void persist() {
		_changeTrackingEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_changeTrackingEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the change tracking entry ID of this change tracking entry.
	*
	* @param changeTrackingEntryId the change tracking entry ID of this change tracking entry
	*/
	@Override
	public void setChangeTrackingEntryId(long changeTrackingEntryId) {
		_changeTrackingEntry.setChangeTrackingEntryId(changeTrackingEntryId);
	}

	@Override
	public void setClassName(String className) {
		_changeTrackingEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this change tracking entry.
	*
	* @param classNameId the class name ID of this change tracking entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_changeTrackingEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this change tracking entry.
	*
	* @param classPK the class pk of this change tracking entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_changeTrackingEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this change tracking entry.
	*
	* @param companyId the company ID of this change tracking entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_changeTrackingEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this change tracking entry.
	*
	* @param createDate the create date of this change tracking entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_changeTrackingEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_changeTrackingEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_changeTrackingEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_changeTrackingEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this change tracking entry.
	*
	* @param modifiedDate the modified date of this change tracking entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_changeTrackingEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_changeTrackingEntry.setNew(n);
	}

	/**
	* Sets the primary key of this change tracking entry.
	*
	* @param primaryKey the primary key of this change tracking entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_changeTrackingEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_changeTrackingEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the resource prim key of this change tracking entry.
	*
	* @param resourcePrimKey the resource prim key of this change tracking entry
	*/
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		_changeTrackingEntry.setResourcePrimKey(resourcePrimKey);
	}

	/**
	* Sets the user ID of this change tracking entry.
	*
	* @param userId the user ID of this change tracking entry
	*/
	@Override
	public void setUserId(long userId) {
		_changeTrackingEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this change tracking entry.
	*
	* @param userName the user name of this change tracking entry
	*/
	@Override
	public void setUserName(String userName) {
		_changeTrackingEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this change tracking entry.
	*
	* @param userUuid the user uuid of this change tracking entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_changeTrackingEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ChangeTrackingEntry> toCacheModel() {
		return _changeTrackingEntry.toCacheModel();
	}

	@Override
	public ChangeTrackingEntry toEscapedModel() {
		return new ChangeTrackingEntryWrapper(_changeTrackingEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _changeTrackingEntry.toString();
	}

	@Override
	public ChangeTrackingEntry toUnescapedModel() {
		return new ChangeTrackingEntryWrapper(_changeTrackingEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _changeTrackingEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangeTrackingEntryWrapper)) {
			return false;
		}

		ChangeTrackingEntryWrapper changeTrackingEntryWrapper = (ChangeTrackingEntryWrapper)obj;

		if (Objects.equals(_changeTrackingEntry,
					changeTrackingEntryWrapper._changeTrackingEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public ChangeTrackingEntry getWrappedModel() {
		return _changeTrackingEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _changeTrackingEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _changeTrackingEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_changeTrackingEntry.resetOriginalValues();
	}

	private final ChangeTrackingEntry _changeTrackingEntry;
}