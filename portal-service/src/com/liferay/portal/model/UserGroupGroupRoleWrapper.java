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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link UserGroupGroupRole}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRole
 * @generated
 */
@ProviderType
public class UserGroupGroupRoleWrapper implements UserGroupGroupRole,
	ModelWrapper<UserGroupGroupRole> {
	public UserGroupGroupRoleWrapper(UserGroupGroupRole userGroupGroupRole) {
		_userGroupGroupRole = userGroupGroupRole;
	}

	@Override
	public Class<?> getModelClass() {
		return UserGroupGroupRole.class;
	}

	@Override
	public String getModelClassName() {
		return UserGroupGroupRole.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("userGroupId", getUserGroupId());
		attributes.put("groupId", getGroupId());
		attributes.put("roleId", getRoleId());
		attributes.put("mvccVersion", getMvccVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long userGroupId = (Long)attributes.get("userGroupId");

		if (userGroupId != null) {
			setUserGroupId(userGroupId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long roleId = (Long)attributes.get("roleId");

		if (roleId != null) {
			setRoleId(roleId);
		}

		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}
	}

	/**
	* Returns the primary key of this user group group role.
	*
	* @return the primary key of this user group group role
	*/
	@Override
	public com.liferay.portal.service.persistence.UserGroupGroupRolePK getPrimaryKey() {
		return _userGroupGroupRole.getPrimaryKey();
	}

	/**
	* Sets the primary key of this user group group role.
	*
	* @param primaryKey the primary key of this user group group role
	*/
	@Override
	public void setPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK primaryKey) {
		_userGroupGroupRole.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the user group ID of this user group group role.
	*
	* @return the user group ID of this user group group role
	*/
	@Override
	public long getUserGroupId() {
		return _userGroupGroupRole.getUserGroupId();
	}

	/**
	* Sets the user group ID of this user group group role.
	*
	* @param userGroupId the user group ID of this user group group role
	*/
	@Override
	public void setUserGroupId(long userGroupId) {
		_userGroupGroupRole.setUserGroupId(userGroupId);
	}

	/**
	* Returns the group ID of this user group group role.
	*
	* @return the group ID of this user group group role
	*/
	@Override
	public long getGroupId() {
		return _userGroupGroupRole.getGroupId();
	}

	/**
	* Sets the group ID of this user group group role.
	*
	* @param groupId the group ID of this user group group role
	*/
	@Override
	public void setGroupId(long groupId) {
		_userGroupGroupRole.setGroupId(groupId);
	}

	/**
	* Returns the role ID of this user group group role.
	*
	* @return the role ID of this user group group role
	*/
	@Override
	public long getRoleId() {
		return _userGroupGroupRole.getRoleId();
	}

	/**
	* Sets the role ID of this user group group role.
	*
	* @param roleId the role ID of this user group group role
	*/
	@Override
	public void setRoleId(long roleId) {
		_userGroupGroupRole.setRoleId(roleId);
	}

	/**
	* Returns the mvcc version of this user group group role.
	*
	* @return the mvcc version of this user group group role
	*/
	@Override
	public long getMvccVersion() {
		return _userGroupGroupRole.getMvccVersion();
	}

	/**
	* Sets the mvcc version of this user group group role.
	*
	* @param mvccVersion the mvcc version of this user group group role
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_userGroupGroupRole.setMvccVersion(mvccVersion);
	}

	@Override
	public boolean isNew() {
		return _userGroupGroupRole.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_userGroupGroupRole.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _userGroupGroupRole.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userGroupGroupRole.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _userGroupGroupRole.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _userGroupGroupRole.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_userGroupGroupRole.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userGroupGroupRole.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_userGroupGroupRole.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_userGroupGroupRole.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userGroupGroupRole.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new UserGroupGroupRoleWrapper((UserGroupGroupRole)_userGroupGroupRole.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole) {
		return _userGroupGroupRole.compareTo(userGroupGroupRole);
	}

	@Override
	public int hashCode() {
		return _userGroupGroupRole.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.UserGroupGroupRole> toCacheModel() {
		return _userGroupGroupRole.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.UserGroupGroupRole toEscapedModel() {
		return new UserGroupGroupRoleWrapper(_userGroupGroupRole.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.UserGroupGroupRole toUnescapedModel() {
		return new UserGroupGroupRoleWrapper(_userGroupGroupRole.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _userGroupGroupRole.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _userGroupGroupRole.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupGroupRole.persist();
	}

	@Override
	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRole.getGroup();
	}

	@Override
	public com.liferay.portal.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRole.getRole();
	}

	@Override
	public com.liferay.portal.model.UserGroup getUserGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupGroupRole.getUserGroup();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserGroupGroupRoleWrapper)) {
			return false;
		}

		UserGroupGroupRoleWrapper userGroupGroupRoleWrapper = (UserGroupGroupRoleWrapper)obj;

		if (Validator.equals(_userGroupGroupRole,
					userGroupGroupRoleWrapper._userGroupGroupRole)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public UserGroupGroupRole getWrappedUserGroupGroupRole() {
		return _userGroupGroupRole;
	}

	@Override
	public UserGroupGroupRole getWrappedModel() {
		return _userGroupGroupRole;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _userGroupGroupRole.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _userGroupGroupRole.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_userGroupGroupRole.resetOriginalValues();
	}

	private UserGroupGroupRole _userGroupGroupRole;
}