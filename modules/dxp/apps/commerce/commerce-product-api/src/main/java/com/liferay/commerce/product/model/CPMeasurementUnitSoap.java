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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPMeasurementUnitServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CPMeasurementUnitServiceSoap
 * @generated
 */
@ProviderType
public class CPMeasurementUnitSoap implements Serializable {
	public static CPMeasurementUnitSoap toSoapModel(CPMeasurementUnit model) {
		CPMeasurementUnitSoap soapModel = new CPMeasurementUnitSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPMeasurementUnitId(model.getCPMeasurementUnitId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setKey(model.getKey());
		soapModel.setRate(model.getRate());
		soapModel.setPrimary(model.isPrimary());
		soapModel.setPriority(model.getPriority());
		soapModel.setType(model.getType());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CPMeasurementUnitSoap[] toSoapModels(
		CPMeasurementUnit[] models) {
		CPMeasurementUnitSoap[] soapModels = new CPMeasurementUnitSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPMeasurementUnitSoap[][] toSoapModels(
		CPMeasurementUnit[][] models) {
		CPMeasurementUnitSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPMeasurementUnitSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPMeasurementUnitSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPMeasurementUnitSoap[] toSoapModels(
		List<CPMeasurementUnit> models) {
		List<CPMeasurementUnitSoap> soapModels = new ArrayList<CPMeasurementUnitSoap>(models.size());

		for (CPMeasurementUnit model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPMeasurementUnitSoap[soapModels.size()]);
	}

	public CPMeasurementUnitSoap() {
	}

	public long getPrimaryKey() {
		return _CPMeasurementUnitId;
	}

	public void setPrimaryKey(long pk) {
		setCPMeasurementUnitId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPMeasurementUnitId() {
		return _CPMeasurementUnitId;
	}

	public void setCPMeasurementUnitId(long CPMeasurementUnitId) {
		_CPMeasurementUnitId = CPMeasurementUnitId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public double getRate() {
		return _rate;
	}

	public void setRate(double rate) {
		_rate = rate;
	}

	public boolean getPrimary() {
		return _primary;
	}

	public boolean isPrimary() {
		return _primary;
	}

	public void setPrimary(boolean primary) {
		_primary = primary;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _CPMeasurementUnitId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _key;
	private double _rate;
	private boolean _primary;
	private double _priority;
	private int _type;
	private Date _lastPublishDate;
}