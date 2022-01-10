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
 * This class is used by SOAP remote services, specifically {@link com.liferay.object.service.http.ObjectViewServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectViewSoap implements Serializable {

	public static ObjectViewSoap toSoapModel(ObjectView model) {
		ObjectViewSoap soapModel = new ObjectViewSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectViewId(model.getObjectViewId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setObjectDefinitionId(model.getObjectDefinitionId());
		soapModel.setDefaultObjectView(model.isDefaultObjectView());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static ObjectViewSoap[] toSoapModels(ObjectView[] models) {
		ObjectViewSoap[] soapModels = new ObjectViewSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectViewSoap[][] toSoapModels(ObjectView[][] models) {
		ObjectViewSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ObjectViewSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectViewSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectViewSoap[] toSoapModels(List<ObjectView> models) {
		List<ObjectViewSoap> soapModels = new ArrayList<ObjectViewSoap>(
			models.size());

		for (ObjectView model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ObjectViewSoap[soapModels.size()]);
	}

	public ObjectViewSoap() {
	}

	public long getPrimaryKey() {
		return _objectViewId;
	}

	public void setPrimaryKey(long pk) {
		setObjectViewId(pk);
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

	public long getObjectViewId() {
		return _objectViewId;
	}

	public void setObjectViewId(long objectViewId) {
		_objectViewId = objectViewId;
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

	public boolean getDefaultObjectView() {
		return _defaultObjectView;
	}

	public boolean isDefaultObjectView() {
		return _defaultObjectView;
	}

	public void setDefaultObjectView(boolean defaultObjectView) {
		_defaultObjectView = defaultObjectView;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectViewId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _objectDefinitionId;
	private boolean _defaultObjectView;
	private String _name;

}