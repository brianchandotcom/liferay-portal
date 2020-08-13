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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.UserGroupRoleServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class UserGroupRoleSoap implements Serializable {

	public static UserGroupRoleSoap toSoapModel(UserGroupRole model) {
		UserGroupRoleSoap soapModel = new UserGroupRoleSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setUserGroupRoleId(model.getUserGroupRoleId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setRoleId(model.getRoleId());

		return soapModel;
	}

	public static UserGroupRoleSoap[] toSoapModels(UserGroupRole[] models) {
		UserGroupRoleSoap[] soapModels = new UserGroupRoleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static UserGroupRoleSoap[][] toSoapModels(UserGroupRole[][] models) {
		UserGroupRoleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new UserGroupRoleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new UserGroupRoleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static UserGroupRoleSoap[] toSoapModels(List<UserGroupRole> models) {
		List<UserGroupRoleSoap> soapModels = new ArrayList<UserGroupRoleSoap>(
			models.size());

		for (UserGroupRole model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new UserGroupRoleSoap[soapModels.size()]);
	}

	public UserGroupRoleSoap() {
	}

	public long getPrimaryKey() {
		return _userGroupRoleId;
	}

	public void setPrimaryKey(long pk) {
		setUserGroupRoleId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getUserGroupRoleId() {
		return _userGroupRoleId;
	}

	public void setUserGroupRoleId(long userGroupRoleId) {
		_userGroupRoleId = userGroupRoleId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
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

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _userGroupRoleId;
	private long _companyId;
	private long _userId;
	private long _groupId;
	private long _roleId;

}