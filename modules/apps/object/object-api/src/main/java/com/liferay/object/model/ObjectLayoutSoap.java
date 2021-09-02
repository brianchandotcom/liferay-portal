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
 * This class is used by SOAP remote services, specifically {@link com.liferay.object.service.http.ObjectLayoutServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectLayoutSoap implements Serializable {

	public static ObjectLayoutSoap toSoapModel(ObjectLayout model) {
		ObjectLayoutSoap soapModel = new ObjectLayoutSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectLayoutId(model.getObjectLayoutId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setObjectDefinitionId(model.getObjectDefinitionId());
		soapModel.setDefaultObjectLayout(model.isDefaultObjectLayout());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static ObjectLayoutSoap[] toSoapModels(ObjectLayout[] models) {
		ObjectLayoutSoap[] soapModels = new ObjectLayoutSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectLayoutSoap[][] toSoapModels(ObjectLayout[][] models) {
		ObjectLayoutSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ObjectLayoutSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectLayoutSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectLayoutSoap[] toSoapModels(List<ObjectLayout> models) {
		List<ObjectLayoutSoap> soapModels = new ArrayList<ObjectLayoutSoap>(
			models.size());

		for (ObjectLayout model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ObjectLayoutSoap[soapModels.size()]);
	}

	public ObjectLayoutSoap() {
	}

	public long getPrimaryKey() {
		return _objectLayoutId;
	}

	public void setPrimaryKey(long pk) {
		setObjectLayoutId(pk);
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

	public long getObjectLayoutId() {
		return _objectLayoutId;
	}

	public void setObjectLayoutId(long objectLayoutId) {
		_objectLayoutId = objectLayoutId;
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

	public long getObjectDefinitionId() {
		return _objectDefinitionId;
	}

	public void setObjectDefinitionId(long objectDefinitionId) {
		_objectDefinitionId = objectDefinitionId;
	}

	public boolean getDefaultObjectLayout() {
		return _defaultObjectLayout;
	}

	public boolean isDefaultObjectLayout() {
		return _defaultObjectLayout;
	}

	public void setDefaultObjectLayout(boolean defaultObjectLayout) {
		_defaultObjectLayout = defaultObjectLayout;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectLayoutId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _objectDefinitionId;
	private boolean _defaultObjectLayout;
	private String _name;

}