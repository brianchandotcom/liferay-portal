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

package com.liferay.portal.tools.service.builder.test.model;

import java.io.Serializable;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.service.http.EagerBlobEntityServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class EagerBlobEntitySoap implements Serializable {

	public static EagerBlobEntitySoap toSoapModel(EagerBlobEntity model) {
		EagerBlobEntitySoap soapModel = new EagerBlobEntitySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setEagerBlobEntityId(model.getEagerBlobEntityId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setField1(model.getField1());
		soapModel.setField2(model.isField2());
		soapModel.setField3(model.getField3());
		soapModel.setField4(model.getField4());
		soapModel.setField5(model.getField5());
		soapModel.setFieldBlob(model.getFieldBlob());

		return soapModel;
	}

	public static EagerBlobEntitySoap[] toSoapModels(EagerBlobEntity[] models) {
		EagerBlobEntitySoap[] soapModels =
			new EagerBlobEntitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static EagerBlobEntitySoap[][] toSoapModels(
		EagerBlobEntity[][] models) {

		EagerBlobEntitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new EagerBlobEntitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new EagerBlobEntitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static EagerBlobEntitySoap[] toSoapModels(
		List<EagerBlobEntity> models) {

		List<EagerBlobEntitySoap> soapModels =
			new ArrayList<EagerBlobEntitySoap>(models.size());

		for (EagerBlobEntity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new EagerBlobEntitySoap[soapModels.size()]);
	}

	public EagerBlobEntitySoap() {
	}

	public long getPrimaryKey() {
		return _eagerBlobEntityId;
	}

	public void setPrimaryKey(long pk) {
		setEagerBlobEntityId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getEagerBlobEntityId() {
		return _eagerBlobEntityId;
	}

	public void setEagerBlobEntityId(long eagerBlobEntityId) {
		_eagerBlobEntityId = eagerBlobEntityId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
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

	public String getField1() {
		return _field1;
	}

	public void setField1(String field1) {
		_field1 = field1;
	}

	public boolean getField2() {
		return _field2;
	}

	public boolean isField2() {
		return _field2;
	}

	public void setField2(boolean field2) {
		_field2 = field2;
	}

	public int getField3() {
		return _field3;
	}

	public void setField3(int field3) {
		_field3 = field3;
	}

	public Date getField4() {
		return _field4;
	}

	public void setField4(Date field4) {
		_field4 = field4;
	}

	public String getField5() {
		return _field5;
	}

	public void setField5(String field5) {
		_field5 = field5;
	}

	public Blob getFieldBlob() {
		return _fieldBlob;
	}

	public void setFieldBlob(Blob fieldBlob) {
		_fieldBlob = fieldBlob;
	}

	private String _uuid;
	private long _eagerBlobEntityId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _field1;
	private boolean _field2;
	private int _field3;
	private Date _field4;
	private String _field5;
	private Blob _fieldBlob;

}