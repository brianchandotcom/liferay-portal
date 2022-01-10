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
 * This class is used by SOAP remote services, specifically {@link com.liferay.object.service.http.ObjectViewColumnServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectViewColumnSoap implements Serializable {

	public static ObjectViewColumnSoap toSoapModel(ObjectViewColumn model) {
		ObjectViewColumnSoap soapModel = new ObjectViewColumnSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectViewColumnId(model.getObjectViewColumnId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setObjectViewId(model.getObjectViewId());
		soapModel.setObjectFieldName(model.getObjectFieldName());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static ObjectViewColumnSoap[] toSoapModels(
		ObjectViewColumn[] models) {

		ObjectViewColumnSoap[] soapModels =
			new ObjectViewColumnSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectViewColumnSoap[][] toSoapModels(
		ObjectViewColumn[][] models) {

		ObjectViewColumnSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ObjectViewColumnSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectViewColumnSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectViewColumnSoap[] toSoapModels(
		List<ObjectViewColumn> models) {

		List<ObjectViewColumnSoap> soapModels =
			new ArrayList<ObjectViewColumnSoap>(models.size());

		for (ObjectViewColumn model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ObjectViewColumnSoap[soapModels.size()]);
	}

	public ObjectViewColumnSoap() {
	}

	public long getPrimaryKey() {
		return _objectViewColumnId;
	}

	public void setPrimaryKey(long pk) {
		setObjectViewColumnId(pk);
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

	public long getObjectViewColumnId() {
		return _objectViewColumnId;
	}

	public void setObjectViewColumnId(long objectViewColumnId) {
		_objectViewColumnId = objectViewColumnId;
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

	public long getObjectViewId() {
		return _objectViewId;
	}

	public void setObjectViewId(long objectViewId) {
		_objectViewId = objectViewId;
	}

	public String getObjectFieldName() {
		return _objectFieldName;
	}

	public void setObjectFieldName(String objectFieldName) {
		_objectFieldName = objectFieldName;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectViewColumnId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _objectViewId;
	private String _objectFieldName;
	private int _priority;

}