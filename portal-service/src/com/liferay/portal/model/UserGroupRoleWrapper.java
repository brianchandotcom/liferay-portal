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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link UserGroupRole}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupRole
 * @generated
 */
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

		attributes.put("userId", getUserId());
		attributes.put("groupId", getGroupId());
		attributes.put("roleId", getRoleId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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
	}

	/**
	* Returns the primary key of this user group role.
	*
	* @return the primary key of this user group role
	*/
	@Override
	public com.liferay.portal.service.persistence.UserGroupRolePK getPrimaryKey() {
		return _userGroupRole.getPrimaryKey();
	}

	/**
	* Sets the primary key of this user group role.
	*
	* @param primaryKey the primary key of this user group role
	*/
	@Override
	public void setPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupRolePK primaryKey) {
		_userGroupRole.setPrimaryKey(primaryKey);
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
	* Returns the user uuid of this user group role.
	*
	* @return the user uuid of this user group role
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupRole.getUserUuid();
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
	* Sets the group ID of this user group role.
	*
	* @param groupId the group ID of this user group role
	*/
	@Override
	public void setGroupId(long groupId) {
		_userGroupRole.setGroupId(groupId);
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
	* Sets the role ID of this user group role.
	*
	* @param roleId the role ID of this user group role
	*/
	@Override
	public void setRoleId(long roleId) {
		_userGroupRole.setRoleId(roleId);
	}

	@Override
	public boolean isNew() {
		return _userGroupRole.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_userGroupRole.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _userGroupRole.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userGroupRole.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _userGroupRole.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _userGroupRole.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_userGroupRole.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userGroupRole.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_userGroupRole.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_userGroupRole.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userGroupRole.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new UserGroupRoleWrapper((UserGroupRole)_userGroupRole.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.UserGroupRole userGroupRole) {
		return _userGroupRole.compareTo(userGroupRole);
	}

	@Override
	public int hashCode() {
		return _userGroupRole.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.UserGroupRole> toCacheModel() {
		return _userGroupRole.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.UserGroupRole toEscapedModel() {
		return new UserGroupRoleWrapper(_userGroupRole.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.UserGroupRole toUnescapedModel() {
		return new UserGroupRoleWrapper(_userGroupRole.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _userGroupRole.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _userGroupRole.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_userGroupRole.persist();
	}

	@Override
	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupRole.getGroup();
	}

	@Override
	public com.liferay.portal.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupRole.getRole();
	}

	@Override
	public com.liferay.portal.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupRole.getUser();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public UserGroupRole getWrappedUserGroupRole() {
		return _userGroupRole;
	}

	@Override
	public UserGroupRole getWrappedModel() {
		return _userGroupRole;
	}

	@Override
	public void resetOriginalValues() {
		_userGroupRole.resetOriginalValues();
	}

	private UserGroupRole _userGroupRole;
}