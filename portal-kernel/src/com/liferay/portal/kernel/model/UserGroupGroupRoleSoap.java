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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.UserGroupGroupRoleServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class UserGroupGroupRoleSoap implements Serializable {

	public static UserGroupGroupRoleSoap toSoapModel(UserGroupGroupRole model) {
		UserGroupGroupRoleSoap soapModel = new UserGroupGroupRoleSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setUserGroupGroupRoleId(model.getUserGroupGroupRoleId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserGroupId(model.getUserGroupId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setRoleId(model.getRoleId());

		return soapModel;
	}

	public static UserGroupGroupRoleSoap[] toSoapModels(
		UserGroupGroupRole[] models) {

		UserGroupGroupRoleSoap[] soapModels =
			new UserGroupGroupRoleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static UserGroupGroupRoleSoap[][] toSoapModels(
		UserGroupGroupRole[][] models) {

		UserGroupGroupRoleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new UserGroupGroupRoleSoap[models.length][models[0].length];
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

		List<UserGroupGroupRoleSoap> soapModels =
			new ArrayList<UserGroupGroupRoleSoap>(models.size());

		for (UserGroupGroupRole model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new UserGroupGroupRoleSoap[soapModels.size()]);
	}

	public UserGroupGroupRoleSoap() {
	}

	public long getPrimaryKey() {
		return _userGroupGroupRoleId;
	}

	public void setPrimaryKey(long pk) {
		setUserGroupGroupRoleId(pk);
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

	public long getUserGroupGroupRoleId() {
		return _userGroupGroupRoleId;
	}

	public void setUserGroupGroupRoleId(long userGroupGroupRoleId) {
		_userGroupGroupRoleId = userGroupGroupRoleId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _userGroupGroupRoleId;
	private long _companyId;
	private long _userGroupId;
	private long _groupId;
	private long _roleId;

}