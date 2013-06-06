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

import com.liferay.portal.service.persistence.UserGroupGroupRolePK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.UserGroupGroupRoleServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.http.UserGroupGroupRoleServiceSoap
 * @generated
 */
public class UserGroupGroupRoleSoap implements Serializable {
	public static UserGroupGroupRoleSoap toSoapModel(UserGroupGroupRole model) {
		UserGroupGroupRoleSoap soapModel = new UserGroupGroupRoleSoap();

		soapModel.setUserGroupId(model.getUserGroupId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setRoleId(model.getRoleId());

		return soapModel;
	}

	public static UserGroupGroupRoleSoap[] toSoapModels(
		UserGroupGroupRole[] models) {
		UserGroupGroupRoleSoap[] soapModels = new UserGroupGroupRoleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static UserGroupGroupRoleSoap[][] toSoapModels(
		UserGroupGroupRole[][] models) {
		UserGroupGroupRoleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new UserGroupGroupRoleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new UserGroupGroupRoleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static UserGroupGroupRoleSoap[] toSoapModels(
		List<UserGroupGroupRole> models) {
		List<UserGroupGroupRoleSoap> soapModels = new ArrayList<UserGroupGroupRoleSoap>(models.size());

		for (UserGroupGroupRole model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new UserGroupGroupRoleSoap[soapModels.size()]);
	}

	public UserGroupGroupRoleSoap() {
	}

	public UserGroupGroupRolePK getPrimaryKey() {
		return new UserGroupGroupRolePK(_userGroupId, _groupId, _roleId);
	}

	public void setPrimaryKey(UserGroupGroupRolePK pk) {
		setUserGroupId(pk.userGroupId);
		setGroupId(pk.groupId);
		setRoleId(pk.roleId);
	}

	public long getUserGroupId() {
		return _userGroupId;
	}

	public void setUserGroupId(long userGroupId) {
		_userGroupId = userGroupId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getRoleId() {
		return _roleId;
	}

	public void setRoleId(long roleId) {
		_roleId = roleId;
	}

	private long _userGroupId;
	private long _groupId;
	private long _roleId;
}