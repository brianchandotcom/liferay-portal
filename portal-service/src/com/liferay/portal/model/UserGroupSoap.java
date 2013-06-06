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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.UserGroupServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.http.UserGroupServiceSoap
 * @generated
 */
public class UserGroupSoap implements Serializable {
	public static UserGroupSoap toSoapModel(UserGroup model) {
		UserGroupSoap soapModel = new UserGroupSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setUserGroupId(model.getUserGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setParentUserGroupId(model.getParentUserGroupId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setAddedByLDAPImport(model.getAddedByLDAPImport());

		return soapModel;
	}

	public static UserGroupSoap[] toSoapModels(UserGroup[] models) {
		UserGroupSoap[] soapModels = new UserGroupSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static UserGroupSoap[][] toSoapModels(UserGroup[][] models) {
		UserGroupSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new UserGroupSoap[models.length][models[0].length];
		}
		else {
			soapModels = new UserGroupSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static UserGroupSoap[] toSoapModels(List<UserGroup> models) {
		List<UserGroupSoap> soapModels = new ArrayList<UserGroupSoap>(models.size());

		for (UserGroup model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new UserGroupSoap[soapModels.size()]);
	}

	public UserGroupSoap() {
	}

	public long getPrimaryKey() {
		return _userGroupId;
	}

	public void setPrimaryKey(long pk) {
		setUserGroupId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getUserGroupId() {
		return _userGroupId;
	}

	public void setUserGroupId(long userGroupId) {
		_userGroupId = userGroupId;
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

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getParentUserGroupId() {
		return _parentUserGroupId;
	}

	public void setParentUserGroupId(long parentUserGroupId) {
		_parentUserGroupId = parentUserGroupId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public boolean getAddedByLDAPImport() {
		return _addedByLDAPImport;
	}

	public boolean isAddedByLDAPImport() {
		return _addedByLDAPImport;
	}

	public void setAddedByLDAPImport(boolean addedByLDAPImport) {
		_addedByLDAPImport = addedByLDAPImport;
	}

	private String _uuid;
	private long _userGroupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _parentUserGroupId;
	private String _name;
	private String _description;
	private boolean _addedByLDAPImport;
}