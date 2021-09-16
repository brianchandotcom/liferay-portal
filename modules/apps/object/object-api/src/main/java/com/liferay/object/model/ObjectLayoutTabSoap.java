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
 * This class is used by SOAP remote services, specifically {@link com.liferay.object.service.http.ObjectLayoutTabServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectLayoutTabSoap implements Serializable {

	public static ObjectLayoutTabSoap toSoapModel(ObjectLayoutTab model) {
		ObjectLayoutTabSoap soapModel = new ObjectLayoutTabSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectLayoutTabId(model.getObjectLayoutTabId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setObjectLayoutId(model.getObjectLayoutId());
		soapModel.setObjectRelationshipId(model.getObjectRelationshipId());
		soapModel.setName(model.getName());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static ObjectLayoutTabSoap[] toSoapModels(ObjectLayoutTab[] models) {
		ObjectLayoutTabSoap[] soapModels =
			new ObjectLayoutTabSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectLayoutTabSoap[][] toSoapModels(
		ObjectLayoutTab[][] models) {

		ObjectLayoutTabSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ObjectLayoutTabSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectLayoutTabSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectLayoutTabSoap[] toSoapModels(
		List<ObjectLayoutTab> models) {

		List<ObjectLayoutTabSoap> soapModels =
			new ArrayList<ObjectLayoutTabSoap>(models.size());

		for (ObjectLayoutTab model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ObjectLayoutTabSoap[soapModels.size()]);
	}

	public ObjectLayoutTabSoap() {
	}

	public long getPrimaryKey() {
		return _objectLayoutTabId;
	}

	public void setPrimaryKey(long pk) {
		setObjectLayoutTabId(pk);
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

	public long getObjectLayoutTabId() {
		return _objectLayoutTabId;
	}

	public void setObjectLayoutTabId(long objectLayoutTabId) {
		_objectLayoutTabId = objectLayoutTabId;
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

	public long getObjectLayoutId() {
		return _objectLayoutId;
	}

	public void setObjectLayoutId(long objectLayoutId) {
		_objectLayoutId = objectLayoutId;
	}

	public long getObjectRelationshipId() {
		return _objectRelationshipId;
	}

	public void setObjectRelationshipId(long objectRelationshipId) {
		_objectRelationshipId = objectRelationshipId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectLayoutTabId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _objectLayoutId;
	private long _objectRelationshipId;
	private String _name;
	private int _priority;

}