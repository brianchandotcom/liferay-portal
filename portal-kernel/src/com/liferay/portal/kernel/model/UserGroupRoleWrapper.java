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

import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link UserGroupRole}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupRole
 * @generated
 */
@ProviderType
public class UserGroupRoleWrapper implements UserGroupRole,
	ModelWrapper<UserGroupRole> {
	public UserGroupRoleWrapper(UserGroupRole userGroupRole) {
		_userGroupRole = userGroupRole;
	}

	@Override
	public Class<?> getModelClass() {
		return UserGroupRole.class;
	}

	@Override
	public String getModelClassName() {
		return UserGroupRole.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("userId", getUserId());
		attributes.put("groupId", getGroupId());
		attributes.put("roleId", getRoleId());
		attributes.put("companyId", getCompanyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long roleId = (Long)attributes.get("roleId");

		if (roleId != null) {
			setRoleId(roleId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	@Override
	public CacheModel<com.liferay.portal.kernel.model.UserGroupRole> toCacheModel() {
		return _userGroupRole.toCacheModel();
	}

	@Override
	public boolean isCachedModel() {
		return _userGroupRole.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _userGroupRole.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _userGroupRole.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _userGroupRole.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupRole.getGroup();
	}

	@Override
	public com.liferay.portal.kernel.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupRole.getRole();
	}

	@Override
	public com.liferay.portal.kernel.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupRole.getUser();
	}

	@Override
	public com.liferay.portal.kernel.model.UserGroupRole toEscapedModel() {
		return new UserGroupRoleWrapper(_userGroupRole.toEscapedModel());
	}

	@Override
	public com.liferay.portal.kernel.model.UserGroupRole toUnescapedModel() {
		return new UserGroupRoleWrapper(_userGroupRole.toUnescapedModel());
	}

	/**
	* Returns the primary key of this user group role.
	*
	* @return the primary key of this user group role
	*/
	@Override
	public com.liferay.portal.kernel.service.persistence.UserGroupRolePK getPrimaryKey() {
		return _userGroupRole.getPrimaryKey();
	}

	@Override
	public int compareTo(
		com.liferay.portal.kernel.model.UserGroupRole userGroupRole) {
		return _userGroupRole.compareTo(userGroupRole);
	}

	@Override
	public int hashCode() {
		return _userGroupRole.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _userGroupRole.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new UserGroupRoleWrapper((UserGroupRole)_userGroupRole.clone());
	}

	/**
	* Returns the user uuid of this user group role.
	*
	* @return the user uuid of this user group role
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _userGroupRole.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _userGroupRole.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _userGroupRole.toXmlString();
	}

	/**
	* Returns the company ID of this user group role.
	*
	* @return the company ID of this user group role
	*/
	@Override
	public long getCompanyId() {
		return _userGroupRole.getCompanyId();
	}

	/**
	* Returns the group ID of this user group role.
	*
	* @return the group ID of this user group role
	*/
	@Override
	public long getGroupId() {
		return _userGroupRole.getGroupId();
	}

	/**
	* Returns the mvcc version of this user group role.
	*
	* @return the mvcc version of this user group role
	*/
	@Override
	public long getMvccVersion() {
		return _userGroupRole.getMvccVersion();
	}

	/**
	* Returns the role ID of this user group role.
	*
	* @return the role ID of this user group role
	*/
	@Override
	public long getRoleId() {
		return _userGroupRole.getRoleId();
	}

	/**
	* Returns the user ID of this user group role.
	*
	* @return the user ID of this user group role
	*/
	@Override
	public long getUserId() {
		return _userGroupRole.getUserId();
	}

	@Override
	public void persist() {
		_userGroupRole.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userGroupRole.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this user group role.
	*
	* @param companyId the company ID of this user group role
	*/
	@Override
	public void setCompanyId(long companyId) {
		_userGroupRole.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_userGroupRole.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_userGroupRole.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_userGroupRole.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this user group role.
	*
	* @param groupId the group ID of this user group role
	*/
	@Override
	public void setGroupId(long groupId) {
		_userGroupRole.setGroupId(groupId);
	}

	/**
	* Sets the mvcc version of this user group role.
	*
	* @param mvccVersion the mvcc version of this user group role
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_userGroupRole.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_userGroupRole.setNew(n);
	}

	/**
	* Sets the primary key of this user group role.
	*
	* @param primaryKey the primary key of this user group role
	*/
	@Override
	public void setPrimaryKey(
		com.liferay.portal.kernel.service.persistence.UserGroupRolePK primaryKey) {
		_userGroupRole.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_userGroupRole.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the role ID of this user group role.
	*
	* @param roleId the role ID of this user group role
	*/
	@Override
	public void setRoleId(long roleId) {
		_userGroupRole.setRoleId(roleId);
	}

	/**
	* Sets the user ID of this user group role.
	*
	* @param userId the user ID of this user group role
	*/
	@Override
	public void setUserId(long userId) {
		_userGroupRole.setUserId(userId);
	}

	/**
	* Sets the user uuid of this user group role.
	*
	* @param userUuid the user uuid of this user group role
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_userGroupRole.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserGroupRoleWrapper)) {
			return false;
		}

		UserGroupRoleWrapper userGroupRoleWrapper = (UserGroupRoleWrapper)obj;

		if (Objects.equals(_userGroupRole, userGroupRoleWrapper._userGroupRole)) {
			return true;
		}

		return false;
	}

	@Override
	public UserGroupRole getWrappedModel() {
		return _userGroupRole;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _userGroupRole.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _userGroupRole.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_userGroupRole.resetOriginalValues();
	}

	private final UserGroupRole _userGroupRole;
}