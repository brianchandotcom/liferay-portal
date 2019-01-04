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

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
@ProviderType
public class UserGroupRoleWrapper extends BaseModelWrapper<UserGroupRole>
	implements UserGroupRole, ModelWrapper<UserGroupRole> {
	public UserGroupRoleWrapper(UserGroupRole userGroupRole) {
		super(userGroupRole);
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

	/**
	* Returns the company ID of this user group role.
	*
	* @return the company ID of this user group role
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getGroup();
	}

	/**
	* Returns the group ID of this user group role.
	*
	* @return the group ID of this user group role
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the mvcc version of this user group role.
	*
	* @return the mvcc version of this user group role
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the primary key of this user group role.
	*
	* @return the primary key of this user group role
	*/
	@Override
	public com.liferay.portal.kernel.service.persistence.UserGroupRolePK getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getRole();
	}

	/**
	* Returns the role ID of this user group role.
	*
	* @return the role ID of this user group role
	*/
	@Override
	public long getRoleId() {
		return model.getRoleId();
	}

	@Override
	public User getUser()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getUser();
	}

	/**
	* Returns the user ID of this user group role.
	*
	* @return the user ID of this user group role
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user uuid of this user group role.
	*
	* @return the user uuid of this user group role
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this user group role.
	*
	* @param companyId the company ID of this user group role
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the group ID of this user group role.
	*
	* @param groupId the group ID of this user group role
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the mvcc version of this user group role.
	*
	* @param mvccVersion the mvcc version of this user group role
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the primary key of this user group role.
	*
	* @param primaryKey the primary key of this user group role
	*/
	@Override
	public void setPrimaryKey(
		com.liferay.portal.kernel.service.persistence.UserGroupRolePK primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the role ID of this user group role.
	*
	* @param roleId the role ID of this user group role
	*/
	@Override
	public void setRoleId(long roleId) {
		model.setRoleId(roleId);
	}

	/**
	* Sets the user ID of this user group role.
	*
	* @param userId the user ID of this user group role
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user uuid of this user group role.
	*
	* @param userUuid the user uuid of this user group role
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected UserGroupRoleWrapper wrap(UserGroupRole userGroupRole) {
		return new UserGroupRoleWrapper(userGroupRole);
	}
}