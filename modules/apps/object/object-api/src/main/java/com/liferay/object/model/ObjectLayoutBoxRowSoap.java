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
 * This class is used by SOAP remote services, specifically {@link com.liferay.object.service.http.ObjectLayoutBoxRowServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectLayoutBoxRowSoap implements Serializable {

	public static ObjectLayoutBoxRowSoap toSoapModel(ObjectLayoutBoxRow model) {
		ObjectLayoutBoxRowSoap soapModel = new ObjectLayoutBoxRowSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectLayoutBoxRowId(model.getObjectLayoutBoxRowId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setObjectLayoutBoxId(model.getObjectLayoutBoxId());
		soapModel.setOrder(model.getOrder());

		return soapModel;
	}

	public static ObjectLayoutBoxRowSoap[] toSoapModels(
		ObjectLayoutBoxRow[] models) {

		ObjectLayoutBoxRowSoap[] soapModels =
			new ObjectLayoutBoxRowSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectLayoutBoxRowSoap[][] toSoapModels(
		ObjectLayoutBoxRow[][] models) {

		ObjectLayoutBoxRowSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ObjectLayoutBoxRowSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectLayoutBoxRowSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectLayoutBoxRowSoap[] toSoapModels(
		List<ObjectLayoutBoxRow> models) {

		List<ObjectLayoutBoxRowSoap> soapModels =
			new ArrayList<ObjectLayoutBoxRowSoap>(models.size());

		for (ObjectLayoutBoxRow model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new ObjectLayoutBoxRowSoap[soapModels.size()]);
	}

	public ObjectLayoutBoxRowSoap() {
	}

	public long getPrimaryKey() {
		return _objectLayoutBoxRowId;
	}

	public void setPrimaryKey(long pk) {
		setObjectLayoutBoxRowId(pk);
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

	public long getObjectLayoutBoxRowId() {
		return _objectLayoutBoxRowId;
	}

	public void setObjectLayoutBoxRowId(long objectLayoutBoxRowId) {
		_objectLayoutBoxRowId = objectLayoutBoxRowId;
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

	public long getObjectLayoutBoxId() {
		return _objectLayoutBoxId;
	}

	public void setObjectLayoutBoxId(long objectLayoutBoxId) {
		_objectLayoutBoxId = objectLayoutBoxId;
	}

	public int getOrder() {
		return _order;
	}

	public void setOrder(int order) {
		_order = order;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectLayoutBoxRowId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _objectLayoutBoxId;
	private int _order;

}