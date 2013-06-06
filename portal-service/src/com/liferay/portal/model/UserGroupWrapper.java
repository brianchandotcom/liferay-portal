/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link UserGroup}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroup
 * @generated
 */
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
	* Sets the primary key of this user group.
	*
	* @param primaryKey the primary key of this user group
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_userGroup.setPrimaryKey(primaryKey);
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

	/**
	* Sets the uuid of this user group.
	*
	* @param uuid the uuid of this user group
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_userGroup.setUuid(uuid);
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
	* Sets the user group ID of this user group.
	*
	* @param userGroupId the user group ID of this user group
	*/
	@Override
	public void setUserGroupId(long userGroupId) {
		_userGroup.setUserGroupId(userGroupId);
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
	* Returns the user ID of this user group.
	*
	* @return the user ID of this user group
	*/
	@Override
	public long getUserId() {
		return _userGroup.getUserId();
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
	* Returns the user uuid of this user group.
	*
	* @return the user uuid of this user group
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.getUserUuid();
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
	* Returns the user name of this user group.
	*
	* @return the user name of this user group
	*/
	@Override
	public java.lang.String getUserName() {
		return _userGroup.getUserName();
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
	* Returns the create date of this user group.
	*
	* @return the create date of this user group
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _userGroup.getCreateDate();
	}

	/**
	* Sets the create date of this user group.
	*
	* @param createDate the create date of this user group
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_userGroup.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this user group.
	*
	* @return the modified date of this user group
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _userGroup.getModifiedDate();
	}

	/**
	* Sets the modified date of this user group.
	*
	* @param modifiedDate the modified date of this user group
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_userGroup.setModifiedDate(modifiedDate);
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
	* Sets the parent user group ID of this user group.
	*
	* @param parentUserGroupId the parent user group ID of this user group
	*/
	@Override
	public void setParentUserGroupId(long parentUserGroupId) {
		_userGroup.setParentUserGroupId(parentUserGroupId);
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
	* Sets the name of this user group.
	*
	* @param name the name of this user group
	*/
	@Override
	public void setName(java.lang.String name) {
		_userGroup.setName(name);
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
	* Sets the description of this user group.
	*
	* @param description the description of this user group
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_userGroup.setDescription(description);
	}

	/**
	* Returns the added by l d a p import of this user group.
	*
	* @return the added by l d a p import of this user group
	*/
	@Override
	public boolean getAddedByLDAPImport() {
		return _userGroup.getAddedByLDAPImport();
	}

	/**
	* Returns <code>true</code> if this user group is added by l d a p import.
	*
	* @return <code>true</code> if this user group is added by l d a p import; <code>false</code> otherwise
	*/
	@Override
	public boolean isAddedByLDAPImport() {
		return _userGroup.isAddedByLDAPImport();
	}

	/**
	* Sets whether this user group is added by l d a p import.
	*
	* @param addedByLDAPImport the added by l d a p import of this user group
	*/
	@Override
	public void setAddedByLDAPImport(boolean addedByLDAPImport) {
		_userGroup.setAddedByLDAPImport(addedByLDAPImport);
	}

	@Override
	public boolean isNew() {
		return _userGroup.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_userGroup.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _userGroup.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userGroup.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _userGroup.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _userGroup.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_userGroup.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userGroup.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_userGroup.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_userGroup.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userGroup.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new UserGroupWrapper((UserGroup)_userGroup.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.UserGroup userGroup) {
		return _userGroup.compareTo(userGroup);
	}

	@Override
	public int hashCode() {
		return _userGroup.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.UserGroup> toCacheModel() {
		return _userGroup.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.UserGroup toEscapedModel() {
		return new UserGroupWrapper(_userGroup.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.UserGroup toUnescapedModel() {
		return new UserGroupWrapper(_userGroup.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _userGroup.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _userGroup.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroup.persist();
	}

	@Override
	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.getGroup();
	}

	@Override
	public int getPrivateLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.getPrivateLayoutsPageCount();
	}

	@Override
	public int getPublicLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.getPublicLayoutsPageCount();
	}

	@Override
	public boolean hasPrivateLayouts()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.hasPrivateLayouts();
	}

	@Override
	public boolean hasPublicLayouts()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroup.hasPublicLayouts();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public UserGroup getWrappedUserGroup() {
		return _userGroup;
	}

	@Override
	public UserGroup getWrappedModel() {
		return _userGroup;
	}

	@Override
	public void resetOriginalValues() {
		_userGroup.resetOriginalValues();
	}

	private UserGroup _userGroup;
}