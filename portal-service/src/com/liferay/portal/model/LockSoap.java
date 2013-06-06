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
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LockSoap implements Serializable {
	public static LockSoap toSoapModel(Lock model) {
		LockSoap soapModel = new LockSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setLockId(model.getLockId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setClassName(model.getClassName());
		soapModel.setKey(model.getKey());
		soapModel.setOwner(model.getOwner());
		soapModel.setInheritable(model.getInheritable());
		soapModel.setExpirationDate(model.getExpirationDate());

		return soapModel;
	}

	public static LockSoap[] toSoapModels(Lock[] models) {
		LockSoap[] soapModels = new LockSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LockSoap[][] toSoapModels(Lock[][] models) {
		LockSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LockSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LockSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LockSoap[] toSoapModels(List<Lock> models) {
		List<LockSoap> soapModels = new ArrayList<LockSoap>(models.size());

		for (Lock model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LockSoap[soapModels.size()]);
	}

	public LockSoap() {
	}

	public long getPrimaryKey() {
		return _lockId;
	}

	public void setPrimaryKey(long pk) {
		setLockId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLockId() {
		return _lockId;
	}

	public void setLockId(long lockId) {
		_lockId = lockId;
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

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public String getOwner() {
		return _owner;
	}

	public void setOwner(String owner) {
		_owner = owner;
	}

	public boolean getInheritable() {
		return _inheritable;
	}

	public boolean isInheritable() {
		return _inheritable;
	}

	public void setInheritable(boolean inheritable) {
		_inheritable = inheritable;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	private String _uuid;
	private long _lockId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private String _className;
	private String _key;
	private String _owner;
	private boolean _inheritable;
	private Date _expirationDate;
}