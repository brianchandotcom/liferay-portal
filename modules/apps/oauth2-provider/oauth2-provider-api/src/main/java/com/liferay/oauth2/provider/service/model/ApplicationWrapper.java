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

package com.liferay.oauth2.provider.service.model;

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
 * This class is a wrapper for {@link Application}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Application
 * @generated
 */
@ProviderType
public class ApplicationWrapper implements Application,
	ModelWrapper<Application> {
	public ApplicationWrapper(Application application) {
		_application = application;
	}

	@Override
	public Class<?> getModelClass() {
		return Application.class;
	}

	@Override
	public String getModelClassName() {
		return Application.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("id", getId());
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

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long id = (Long)attributes.get("id");

		if (id != null) {
			setId(id);
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

	@Override
	public java.lang.Object clone() {
		return new ApplicationWrapper((Application)_application.clone());
	}

	@Override
	public int compareTo(Application application) {
		return _application.compareTo(application);
	}

	/**
	* Returns the company ID of this application.
	*
	* @return the company ID of this application
	*/
	@Override
	public long getCompanyId() {
		return _application.getCompanyId();
	}

	/**
	* Returns the create date of this application.
	*
	* @return the create date of this application
	*/
	@Override
	public Date getCreateDate() {
		return _application.getCreateDate();
	}

	/**
	* Returns the description of this application.
	*
	* @return the description of this application
	*/
	@Override
	public java.lang.String getDescription() {
		return _application.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _application.getExpandoBridge();
	}

	/**
	* Returns the group ID of this application.
	*
	* @return the group ID of this application
	*/
	@Override
	public long getGroupId() {
		return _application.getGroupId();
	}

	/**
	* Returns the ID of this application.
	*
	* @return the ID of this application
	*/
	@Override
	public long getId() {
		return _application.getId();
	}

	/**
	* Returns the modified date of this application.
	*
	* @return the modified date of this application
	*/
	@Override
	public Date getModifiedDate() {
		return _application.getModifiedDate();
	}

	/**
	* Returns the name of this application.
	*
	* @return the name of this application
	*/
	@Override
	public java.lang.String getName() {
		return _application.getName();
	}

	/**
	* Returns the primary key of this application.
	*
	* @return the primary key of this application
	*/
	@Override
	public long getPrimaryKey() {
		return _application.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _application.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this application.
	*
	* @return the user ID of this application
	*/
	@Override
	public long getUserId() {
		return _application.getUserId();
	}

	/**
	* Returns the user name of this application.
	*
	* @return the user name of this application
	*/
	@Override
	public java.lang.String getUserName() {
		return _application.getUserName();
	}

	/**
	* Returns the user uuid of this application.
	*
	* @return the user uuid of this application
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _application.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _application.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _application.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _application.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _application.isNew();
	}

	@Override
	public void persist() {
		_application.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_application.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this application.
	*
	* @param companyId the company ID of this application
	*/
	@Override
	public void setCompanyId(long companyId) {
		_application.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this application.
	*
	* @param createDate the create date of this application
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_application.setCreateDate(createDate);
	}

	/**
	* Sets the description of this application.
	*
	* @param description the description of this application
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_application.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_application.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_application.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_application.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this application.
	*
	* @param groupId the group ID of this application
	*/
	@Override
	public void setGroupId(long groupId) {
		_application.setGroupId(groupId);
	}

	/**
	* Sets the ID of this application.
	*
	* @param id the ID of this application
	*/
	@Override
	public void setId(long id) {
		_application.setId(id);
	}

	/**
	* Sets the modified date of this application.
	*
	* @param modifiedDate the modified date of this application
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_application.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this application.
	*
	* @param name the name of this application
	*/
	@Override
	public void setName(java.lang.String name) {
		_application.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_application.setNew(n);
	}

	/**
	* Sets the primary key of this application.
	*
	* @param primaryKey the primary key of this application
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_application.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_application.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this application.
	*
	* @param userId the user ID of this application
	*/
	@Override
	public void setUserId(long userId) {
		_application.setUserId(userId);
	}

	/**
	* Sets the user name of this application.
	*
	* @param userName the user name of this application
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_application.setUserName(userName);
	}

	/**
	* Sets the user uuid of this application.
	*
	* @param userUuid the user uuid of this application
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_application.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<Application> toCacheModel() {
		return _application.toCacheModel();
	}

	@Override
	public Application toEscapedModel() {
		return new ApplicationWrapper(_application.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _application.toString();
	}

	@Override
	public Application toUnescapedModel() {
		return new ApplicationWrapper(_application.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _application.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ApplicationWrapper)) {
			return false;
		}

		ApplicationWrapper applicationWrapper = (ApplicationWrapper)obj;

		if (Objects.equals(_application, applicationWrapper._application)) {
			return true;
		}

		return false;
	}

	@Override
	public Application getWrappedModel() {
		return _application;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _application.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _application.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_application.resetOriginalValues();
	}

	private final Application _application;
}