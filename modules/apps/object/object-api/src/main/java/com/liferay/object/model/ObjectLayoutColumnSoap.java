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

package com.liferay.object.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectLayoutColumnSoap implements Serializable {

	public static ObjectLayoutColumnSoap toSoapModel(ObjectLayoutColumn model) {
		ObjectLayoutColumnSoap soapModel = new ObjectLayoutColumnSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectLayoutColumnId(model.getObjectLayoutColumnId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setObjectFieldId(model.getObjectFieldId());
		soapModel.setObjectLayoutRowId(model.getObjectLayoutRowId());
		soapModel.setPriority(model.getPriority());
		soapModel.setSize(model.getSize());

		return soapModel;
	}

	public static ObjectLayoutColumnSoap[] toSoapModels(
		ObjectLayoutColumn[] models) {

		ObjectLayoutColumnSoap[] soapModels =
			new ObjectLayoutColumnSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectLayoutColumnSoap[][] toSoapModels(
		ObjectLayoutColumn[][] models) {

		ObjectLayoutColumnSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ObjectLayoutColumnSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectLayoutColumnSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectLayoutColumnSoap[] toSoapModels(
		List<ObjectLayoutColumn> models) {

		List<ObjectLayoutColumnSoap> soapModels =
			new ArrayList<ObjectLayoutColumnSoap>(models.size());

		for (ObjectLayoutColumn model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new ObjectLayoutColumnSoap[soapModels.size()]);
	}

	public ObjectLayoutColumnSoap() {
	}

	public long getPrimaryKey() {
		return _objectLayoutColumnId;
	}

	public void setPrimaryKey(long pk) {
		setObjectLayoutColumnId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getObjectLayoutColumnId() {
		return _objectLayoutColumnId;
	}

	public void setObjectLayoutColumnId(long objectLayoutColumnId) {
		_objectLayoutColumnId = objectLayoutColumnId;
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

	public long getObjectFieldId() {
		return _objectFieldId;
	}

	public void setObjectFieldId(long objectFieldId) {
		_objectFieldId = objectFieldId;
	}

	public long getObjectLayoutRowId() {
		return _objectLayoutRowId;
	}

	public void setObjectLayoutRowId(long objectLayoutRowId) {
		_objectLayoutRowId = objectLayoutRowId;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	public int getSize() {
		return _size;
	}

	public void setSize(int size) {
		_size = size;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectLayoutColumnId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _objectFieldId;
	private long _objectLayoutRowId;
	private int _priority;
	private int _size;

}