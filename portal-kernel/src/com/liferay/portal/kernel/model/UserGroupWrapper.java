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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link UserGroup}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroup
 * @generated
 */
@ProviderType
public class UserGroupWrapper implements UserGroup, ModelWrapper<UserGroup> {
	public UserGroupWrapper(UserGroup userGroup) {
		_userGroup = userGroup;
	}

	@Override
	public Class<?> getModelClass() {
		return UserGroup.class;
	}

	@Override
	public String getModelClassName() {
		return UserGroup.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("userGroupId", getUserGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentUserGroupId", getParentUserGroupId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("addedByLDAPImport", getAddedByLDAPImport());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long userGroupId = (Long)attributes.get("userGroupId");

		if (userGroupId != null) {
			setUserGroupId(userGroupId);
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

		Long parentUserGroupId = (Long)attributes.get("parentUserGroupId");

		if (parentUserGroupId != null) {
			setParentUserGroupId(parentUserGroupId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Boolean addedByLDAPImport = (Boolean)attributes.get("addedByLDAPImport");

		if (addedByLDAPImport != null) {
			setAddedByLDAPImport(addedByLDAPImport);
		}
	}

	@Override
	public CacheModel<UserGroup> toCacheModel() {
		return _userGroup.toCacheModel();
	}

	@Override
	public Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroup.getGroup();
	}

	@Override
	public UserGroup toEscapedModel() {
		return new UserGroupWrapper(_userGroup.toEscapedModel());
	}

	@Override
	public UserGroup toUnescapedModel() {
		return new UserGroupWrapper(_userGroup.toUnescapedModel());
	}

	/**
	* Returns the added by ldap import of this user group.
	*
	* @return the added by ldap import of this user group
	*/
	@Override
	public boolean getAddedByLDAPImport() {
		return _userGroup.getAddedByLDAPImport();
	}

	@Override
	public boolean hasPrivateLayouts()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroup.hasPrivateLayouts();
	}

	@Override
	public boolean hasPublicLayouts()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroup.hasPublicLayouts();
	}

	/**
	* Returns <code>true</code> if this user group is added by ldap import.
	*
	* @return <code>true</code> if this user group is added by ldap import; <code>false</code> otherwise
	*/
	@Override
	public boolean isAddedByLDAPImport() {
		return _userGroup.isAddedByLDAPImport();
	}

	@Override
	public boolean isCachedModel() {
		return _userGroup.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _userGroup.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _userGroup.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _userGroup.getExpandoBridge();
	}

	@Override
	public int compareTo(UserGroup userGroup) {
		return _userGroup.compareTo(userGroup);
	}

	@Override
	public int getPrivateLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroup.getPrivateLayoutsPageCount();
	}

	@Override
	public int getPublicLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroup.getPublicLayoutsPageCount();
	}

	@Override
	public int hashCode() {
		return _userGroup.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _userGroup.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new UserGroupWrapper((UserGroup)_userGroup.clone());
	}

	/**
	* Returns the description of this user group.
	*
	* @return the description of this user group
	*/
	@Override
	public java.lang.String getDescription() {
		return _userGroup.getDescription();
	}

	/**
	* Returns the name of this user group.
	*
	* @return the name of this user group
	*/
	@Override
	public java.lang.String getName() {
		return _userGroup.getName();
	}

	/**
	* Returns the user name of this user group.
	*
	* @return the user name of this user group
	*/
	@Override
	public java.lang.String getUserName() {
		return _userGroup.getUserName();
	}

	/**
	* Returns the user uuid of this user group.
	*
	* @return the user uuid of this user group
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _userGroup.getUserUuid();
	}

	/**
	* Returns the uuid of this user group.
	*
	* @return the uuid of this user group
	*/
	@Override
	public java.lang.String getUuid() {
		return _userGroup.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _userGroup.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _userGroup.toXmlString();
	}

	/**
	* Returns the create date of this user group.
	*
	* @return the create date of this user group
	*/
	@Override
	public Date getCreateDate() {
		return _userGroup.getCreateDate();
	}

	/**
	* Returns the modified date of this user group.
	*
	* @return the modified date of this user group
	*/
	@Override
	public Date getModifiedDate() {
		return _userGroup.getModifiedDate();
	}

	/**
	* Returns the company ID of this user group.
	*
	* @return the company ID of this user group
	*/
	@Override
	public long getCompanyId() {
		return _userGroup.getCompanyId();
	}

	@Override
	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroup.getGroupId();
	}

	/**
	* Returns the mvcc version of this user group.
	*
	* @return the mvcc version of this user group
	*/
	@Override
	public long getMvccVersion() {
		return _userGroup.getMvccVersion();
	}

	/**
	* Returns the parent user group ID of this user group.
	*
	* @return the parent user group ID of this user group
	*/
	@Override
	public long getParentUserGroupId() {
		return _userGroup.getParentUserGroupId();
	}

	/**
	* Returns the primary key of this user group.
	*
	* @return the primary key of this user group
	*/
	@Override
	public long getPrimaryKey() {
		return _userGroup.getPrimaryKey();
	}

	/**
	* Returns the user group ID of this user group.
	*
	* @return the user group ID of this user group
	*/
	@Override
	public long getUserGroupId() {
		return _userGroup.getUserGroupId();
	}

	/**
	* Returns the user ID of this user group.
	*
	* @return the user ID of this user group
	*/
	@Override
	public long getUserId() {
		return _userGroup.getUserId();
	}

	@Override
	public void persist() {
		_userGroup.persist();
	}

	/**
	* Sets whether this user group is added by ldap import.
	*
	* @param addedByLDAPImport the added by ldap import of this user group
	*/
	@Override
	public void setAddedByLDAPImport(boolean addedByLDAPImport) {
		_userGroup.setAddedByLDAPImport(addedByLDAPImport);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userGroup.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this user group.
	*
	* @param companyId the company ID of this user group
	*/
	@Override
	public void setCompanyId(long companyId) {
		_userGroup.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this user group.
	*
	* @param createDate the create date of this user group
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_userGroup.setCreateDate(createDate);
	}

	/**
	* Sets the description of this user group.
	*
	* @param description the description of this user group
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_userGroup.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_userGroup.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_userGroup.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_userGroup.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this user group.
	*
	* @param modifiedDate the modified date of this user group
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_userGroup.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this user group.
	*
	* @param mvccVersion the mvcc version of this user group
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_userGroup.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this user group.
	*
	* @param name the name of this user group
	*/
	@Override
	public void setName(java.lang.String name) {
		_userGroup.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_userGroup.setNew(n);
	}

	/**
	* Sets the parent user group ID of this user group.
	*
	* @param parentUserGroupId the parent user group ID of this user group
	*/
	@Override
	public void setParentUserGroupId(long parentUserGroupId) {
		_userGroup.setParentUserGroupId(parentUserGroupId);
	}

	/**
	* Sets the primary key of this user group.
	*
	* @param primaryKey the primary key of this user group
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_userGroup.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_userGroup.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user group ID of this user group.
	*
	* @param userGroupId the user group ID of this user group
	*/
	@Override
	public void setUserGroupId(long userGroupId) {
		_userGroup.setUserGroupId(userGroupId);
	}

	/**
	* Sets the user ID of this user group.
	*
	* @param userId the user ID of this user group
	*/
	@Override
	public void setUserId(long userId) {
		_userGroup.setUserId(userId);
	}

	/**
	* Sets the user name of this user group.
	*
	* @param userName the user name of this user group
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_userGroup.setUserName(userName);
	}

	/**
	* Sets the user uuid of this user group.
	*
	* @param userUuid the user uuid of this user group
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_userGroup.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this user group.
	*
	* @param uuid the uuid of this user group
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_userGroup.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserGroupWrapper)) {
			return false;
		}

		UserGroupWrapper userGroupWrapper = (UserGroupWrapper)obj;

		if (Objects.equals(_userGroup, userGroupWrapper._userGroup)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _userGroup.getStagedModelType();
	}

	@Override
	public UserGroup getWrappedModel() {
		return _userGroup;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _userGroup.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _userGroup.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_userGroup.resetOriginalValues();
	}

	private final UserGroup _userGroup;
}