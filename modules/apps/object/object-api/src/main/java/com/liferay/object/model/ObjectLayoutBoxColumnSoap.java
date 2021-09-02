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
 * This class is used by SOAP remote services, specifically {@link com.liferay.object.service.http.ObjectLayoutBoxColumnServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectLayoutBoxColumnSoap implements Serializable {

	public static ObjectLayoutBoxColumnSoap toSoapModel(
		ObjectLayoutBoxColumn model) {

		ObjectLayoutBoxColumnSoap soapModel = new ObjectLayoutBoxColumnSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectLayoutBoxColumnId(
			model.getObjectLayoutBoxColumnId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setObjectFieldId(model.getObjectFieldId());
		soapModel.setObjectLayoutBoxRowId(model.getObjectLayoutBoxRowId());
		soapModel.setOrder(model.getOrder());

		return soapModel;
	}

	public static ObjectLayoutBoxColumnSoap[] toSoapModels(
		ObjectLayoutBoxColumn[] models) {

		ObjectLayoutBoxColumnSoap[] soapModels =
			new ObjectLayoutBoxColumnSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectLayoutBoxColumnSoap[][] toSoapModels(
		ObjectLayoutBoxColumn[][] models) {

		ObjectLayoutBoxColumnSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ObjectLayoutBoxColumnSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectLayoutBoxColumnSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectLayoutBoxColumnSoap[] toSoapModels(
		List<ObjectLayoutBoxColumn> models) {

		List<ObjectLayoutBoxColumnSoap> soapModels =
			new ArrayList<ObjectLayoutBoxColumnSoap>(models.size());

		for (ObjectLayoutBoxColumn model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new ObjectLayoutBoxColumnSoap[soapModels.size()]);
	}

	public ObjectLayoutBoxColumnSoap() {
	}

	public long getPrimaryKey() {
		return _objectLayoutBoxColumnId;
	}

	public void setPrimaryKey(long pk) {
		setObjectLayoutBoxColumnId(pk);
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

	public long getObjectLayoutBoxColumnId() {
		return _objectLayoutBoxColumnId;
	}

	public void setObjectLayoutBoxColumnId(long objectLayoutBoxColumnId) {
		_objectLayoutBoxColumnId = objectLayoutBoxColumnId;
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

	public long getObjectLayoutBoxRowId() {
		return _objectLayoutBoxRowId;
	}

	public void setObjectLayoutBoxRowId(long objectLayoutBoxRowId) {
		_objectLayoutBoxRowId = objectLayoutBoxRowId;
	}

	public int getOrder() {
		return _order;
	}

	public void setOrder(int order) {
		_order = order;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectLayoutBoxColumnId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _objectFieldId;
	private long _objectLayoutBoxRowId;
	private int _order;

}