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

package com.liferay.portlet.softwarecatalog.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SCFrameworkVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersion
 * @generated
 */
@ProviderType
public class SCFrameworkVersionWrapper implements SCFrameworkVersion,
	ModelWrapper<SCFrameworkVersion> {
	public SCFrameworkVersionWrapper(SCFrameworkVersion scFrameworkVersion) {
		_scFrameworkVersion = scFrameworkVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return SCFrameworkVersion.class;
	}

	@Override
	public String getModelClassName() {
		return SCFrameworkVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("frameworkVersionId", getFrameworkVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("url", getUrl());
		attributes.put("active", getActive());
		attributes.put("priority", getPriority());
		attributes.put("companyId", getCompanyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long frameworkVersionId = (Long)attributes.get("frameworkVersionId");

		if (frameworkVersionId != null) {
			setFrameworkVersionId(frameworkVersionId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new SCFrameworkVersionWrapper((SCFrameworkVersion)_scFrameworkVersion.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		return _scFrameworkVersion.compareTo(scFrameworkVersion);
	}

	/**
	* Returns the active of this s c framework version.
	*
	* @return the active of this s c framework version
	*/
	@Override
	public boolean getActive() {
		return _scFrameworkVersion.getActive();
	}

	/**
	* Returns the company ID of this s c framework version.
	*
	* @return the company ID of this s c framework version
	*/
	@Override
	public long getCompanyId() {
		return _scFrameworkVersion.getCompanyId();
	}

	/**
	* Returns the create date of this s c framework version.
	*
	* @return the create date of this s c framework version
	*/
	@Override
	public Date getCreateDate() {
		return _scFrameworkVersion.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scFrameworkVersion.getExpandoBridge();
	}

	/**
	* Returns the framework version ID of this s c framework version.
	*
	* @return the framework version ID of this s c framework version
	*/
	@Override
	public long getFrameworkVersionId() {
		return _scFrameworkVersion.getFrameworkVersionId();
	}

	/**
	* Returns the group ID of this s c framework version.
	*
	* @return the group ID of this s c framework version
	*/
	@Override
	public long getGroupId() {
		return _scFrameworkVersion.getGroupId();
	}

	/**
	* Returns the modified date of this s c framework version.
	*
	* @return the modified date of this s c framework version
	*/
	@Override
	public Date getModifiedDate() {
		return _scFrameworkVersion.getModifiedDate();
	}

	/**
	* Returns the name of this s c framework version.
	*
	* @return the name of this s c framework version
	*/
	@Override
	public java.lang.String getName() {
		return _scFrameworkVersion.getName();
	}

	/**
	* Returns the primary key of this s c framework version.
	*
	* @return the primary key of this s c framework version
	*/
	@Override
	public long getPrimaryKey() {
		return _scFrameworkVersion.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _scFrameworkVersion.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this s c framework version.
	*
	* @return the priority of this s c framework version
	*/
	@Override
	public int getPriority() {
		return _scFrameworkVersion.getPriority();
	}

	/**
	* Returns the url of this s c framework version.
	*
	* @return the url of this s c framework version
	*/
	@Override
	public java.lang.String getUrl() {
		return _scFrameworkVersion.getUrl();
	}

	/**
	* Returns the user ID of this s c framework version.
	*
	* @return the user ID of this s c framework version
	*/
	@Override
	public long getUserId() {
		return _scFrameworkVersion.getUserId();
	}

	/**
	* Returns the user name of this s c framework version.
	*
	* @return the user name of this s c framework version
	*/
	@Override
	public java.lang.String getUserName() {
		return _scFrameworkVersion.getUserName();
	}

	/**
	* Returns the user uuid of this s c framework version.
	*
	* @return the user uuid of this s c framework version
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _scFrameworkVersion.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _scFrameworkVersion.hashCode();
	}

	/**
	* Returns <code>true</code> if this s c framework version is active.
	*
	* @return <code>true</code> if this s c framework version is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _scFrameworkVersion.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _scFrameworkVersion.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _scFrameworkVersion.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _scFrameworkVersion.isNew();
	}

	@Override
	public void persist() {
		_scFrameworkVersion.persist();
	}

	/**
	* Sets whether this s c framework version is active.
	*
	* @param active the active of this s c framework version
	*/
	@Override
	public void setActive(boolean active) {
		_scFrameworkVersion.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_scFrameworkVersion.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this s c framework version.
	*
	* @param companyId the company ID of this s c framework version
	*/
	@Override
	public void setCompanyId(long companyId) {
		_scFrameworkVersion.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this s c framework version.
	*
	* @param createDate the create date of this s c framework version
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_scFrameworkVersion.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_scFrameworkVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_scFrameworkVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scFrameworkVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the framework version ID of this s c framework version.
	*
	* @param frameworkVersionId the framework version ID of this s c framework version
	*/
	@Override
	public void setFrameworkVersionId(long frameworkVersionId) {
		_scFrameworkVersion.setFrameworkVersionId(frameworkVersionId);
	}

	/**
	* Sets the group ID of this s c framework version.
	*
	* @param groupId the group ID of this s c framework version
	*/
	@Override
	public void setGroupId(long groupId) {
		_scFrameworkVersion.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this s c framework version.
	*
	* @param modifiedDate the modified date of this s c framework version
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_scFrameworkVersion.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this s c framework version.
	*
	* @param name the name of this s c framework version
	*/
	@Override
	public void setName(java.lang.String name) {
		_scFrameworkVersion.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_scFrameworkVersion.setNew(n);
	}

	/**
	* Sets the primary key of this s c framework version.
	*
	* @param primaryKey the primary key of this s c framework version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_scFrameworkVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_scFrameworkVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this s c framework version.
	*
	* @param priority the priority of this s c framework version
	*/
	@Override
	public void setPriority(int priority) {
		_scFrameworkVersion.setPriority(priority);
	}

	/**
	* Sets the url of this s c framework version.
	*
	* @param url the url of this s c framework version
	*/
	@Override
	public void setUrl(java.lang.String url) {
		_scFrameworkVersion.setUrl(url);
	}

	/**
	* Sets the user ID of this s c framework version.
	*
	* @param userId the user ID of this s c framework version
	*/
	@Override
	public void setUserId(long userId) {
		_scFrameworkVersion.setUserId(userId);
	}

	/**
	* Sets the user name of this s c framework version.
	*
	* @param userName the user name of this s c framework version
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_scFrameworkVersion.setUserName(userName);
	}

	/**
	* Sets the user uuid of this s c framework version.
	*
	* @param userUuid the user uuid of this s c framework version
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_scFrameworkVersion.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> toCacheModel() {
		return _scFrameworkVersion.toCacheModel();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion toEscapedModel() {
		return new SCFrameworkVersionWrapper(_scFrameworkVersion.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _scFrameworkVersion.toString();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion toUnescapedModel() {
		return new SCFrameworkVersionWrapper(_scFrameworkVersion.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _scFrameworkVersion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SCFrameworkVersionWrapper)) {
			return false;
		}

		SCFrameworkVersionWrapper scFrameworkVersionWrapper = (SCFrameworkVersionWrapper)obj;

		if (Validator.equals(_scFrameworkVersion,
					scFrameworkVersionWrapper._scFrameworkVersion)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public SCFrameworkVersion getWrappedSCFrameworkVersion() {
		return _scFrameworkVersion;
	}

	@Override
	public SCFrameworkVersion getWrappedModel() {
		return _scFrameworkVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _scFrameworkVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _scFrameworkVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_scFrameworkVersion.resetOriginalValues();
	}

	private final SCFrameworkVersion _scFrameworkVersion;
}