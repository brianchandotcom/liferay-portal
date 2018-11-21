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

package com.liferay.change.tracking.engine.model;

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
 * This class is a wrapper for {@link ChangeEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeEntry
 * @generated
 */
@ProviderType
public class ChangeEntryWrapper implements ChangeEntry,
	ModelWrapper<ChangeEntry> {
	public ChangeEntryWrapper(ChangeEntry changeEntry) {
		_changeEntry = changeEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return ChangeEntry.class;
	}

	@Override
	public String getModelClassName() {
		return ChangeEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("changeEntryId", getChangeEntryId());
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
		Long changeEntryId = (Long)attributes.get("changeEntryId");

		if (changeEntryId != null) {
			setChangeEntryId(changeEntryId);
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
		return new ChangeEntryWrapper((ChangeEntry)_changeEntry.clone());
	}

	@Override
	public int compareTo(ChangeEntry changeEntry) {
		return _changeEntry.compareTo(changeEntry);
	}

	/**
	* Returns the change entry ID of this change entry.
	*
	* @return the change entry ID of this change entry
	*/
	@Override
	public long getChangeEntryId() {
		return _changeEntry.getChangeEntryId();
	}

	/**
	* Returns the fully qualified class name of this change entry.
	*
	* @return the fully qualified class name of this change entry
	*/
	@Override
	public String getClassName() {
		return _changeEntry.getClassName();
	}

	/**
	* Returns the class name ID of this change entry.
	*
	* @return the class name ID of this change entry
	*/
	@Override
	public long getClassNameId() {
		return _changeEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this change entry.
	*
	* @return the class pk of this change entry
	*/
	@Override
	public long getClassPK() {
		return _changeEntry.getClassPK();
	}

	/**
	* Returns the company ID of this change entry.
	*
	* @return the company ID of this change entry
	*/
	@Override
	public long getCompanyId() {
		return _changeEntry.getCompanyId();
	}

	/**
	* Returns the create date of this change entry.
	*
	* @return the create date of this change entry
	*/
	@Override
	public Date getCreateDate() {
		return _changeEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _changeEntry.getExpandoBridge();
	}

	/**
	* Returns the modified date of this change entry.
	*
	* @return the modified date of this change entry
	*/
	@Override
	public Date getModifiedDate() {
		return _changeEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this change entry.
	*
	* @return the primary key of this change entry
	*/
	@Override
	public long getPrimaryKey() {
		return _changeEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _changeEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the resource prim key of this change entry.
	*
	* @return the resource prim key of this change entry
	*/
	@Override
	public long getResourcePrimKey() {
		return _changeEntry.getResourcePrimKey();
	}

	/**
	* Returns the user ID of this change entry.
	*
	* @return the user ID of this change entry
	*/
	@Override
	public long getUserId() {
		return _changeEntry.getUserId();
	}

	/**
	* Returns the user name of this change entry.
	*
	* @return the user name of this change entry
	*/
	@Override
	public String getUserName() {
		return _changeEntry.getUserName();
	}

	/**
	* Returns the user uuid of this change entry.
	*
	* @return the user uuid of this change entry
	*/
	@Override
	public String getUserUuid() {
		return _changeEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _changeEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _changeEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _changeEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _changeEntry.isNew();
	}

	@Override
	public boolean isResourceMain() {
		return _changeEntry.isResourceMain();
	}

	@Override
	public void persist() {
		_changeEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_changeEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the change entry ID of this change entry.
	*
	* @param changeEntryId the change entry ID of this change entry
	*/
	@Override
	public void setChangeEntryId(long changeEntryId) {
		_changeEntry.setChangeEntryId(changeEntryId);
	}

	@Override
	public void setClassName(String className) {
		_changeEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this change entry.
	*
	* @param classNameId the class name ID of this change entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_changeEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this change entry.
	*
	* @param classPK the class pk of this change entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_changeEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this change entry.
	*
	* @param companyId the company ID of this change entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_changeEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this change entry.
	*
	* @param createDate the create date of this change entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_changeEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_changeEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_changeEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_changeEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this change entry.
	*
	* @param modifiedDate the modified date of this change entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_changeEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_changeEntry.setNew(n);
	}

	/**
	* Sets the primary key of this change entry.
	*
	* @param primaryKey the primary key of this change entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_changeEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_changeEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the resource prim key of this change entry.
	*
	* @param resourcePrimKey the resource prim key of this change entry
	*/
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		_changeEntry.setResourcePrimKey(resourcePrimKey);
	}

	/**
	* Sets the user ID of this change entry.
	*
	* @param userId the user ID of this change entry
	*/
	@Override
	public void setUserId(long userId) {
		_changeEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this change entry.
	*
	* @param userName the user name of this change entry
	*/
	@Override
	public void setUserName(String userName) {
		_changeEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this change entry.
	*
	* @param userUuid the user uuid of this change entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_changeEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ChangeEntry> toCacheModel() {
		return _changeEntry.toCacheModel();
	}

	@Override
	public ChangeEntry toEscapedModel() {
		return new ChangeEntryWrapper(_changeEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _changeEntry.toString();
	}

	@Override
	public ChangeEntry toUnescapedModel() {
		return new ChangeEntryWrapper(_changeEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _changeEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangeEntryWrapper)) {
			return false;
		}

		ChangeEntryWrapper changeEntryWrapper = (ChangeEntryWrapper)obj;

		if (Objects.equals(_changeEntry, changeEntryWrapper._changeEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public ChangeEntry getWrappedModel() {
		return _changeEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _changeEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _changeEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_changeEntry.resetOriginalValues();
	}

	private final ChangeEntry _changeEntry;
}