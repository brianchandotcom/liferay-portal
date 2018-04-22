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

package com.liferay.commerce.currency.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.currency.service.http.CommerceCurrencyServiceSoap}.
 *
 * @author Andrea Di Giorgi
 * @see com.liferay.commerce.currency.service.http.CommerceCurrencyServiceSoap
 * @generated
 */
@ProviderType
public class CommerceCurrencySoap implements Serializable {
	public static CommerceCurrencySoap toSoapModel(CommerceCurrency model) {
		CommerceCurrencySoap soapModel = new CommerceCurrencySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceCurrencyId(model.getCommerceCurrencyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCode(model.getCode());
		soapModel.setName(model.getName());
		soapModel.setRate(model.getRate());
		soapModel.setRoundingType(model.getRoundingType());
		soapModel.setPrimary(model.isPrimary());
		soapModel.setPriority(model.getPriority());
		soapModel.setActive(model.isActive());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CommerceCurrencySoap[] toSoapModels(CommerceCurrency[] models) {
		CommerceCurrencySoap[] soapModels = new CommerceCurrencySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceCurrencySoap[][] toSoapModels(
		CommerceCurrency[][] models) {
		CommerceCurrencySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceCurrencySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceCurrencySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceCurrencySoap[] toSoapModels(
		List<CommerceCurrency> models) {
		List<CommerceCurrencySoap> soapModels = new ArrayList<CommerceCurrencySoap>(models.size());

		for (CommerceCurrency model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceCurrencySoap[soapModels.size()]);
	}

	public CommerceCurrencySoap() {
	}

	public long getPrimaryKey() {
		return _commerceCurrencyId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceCurrencyId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceCurrencyId() {
		return _commerceCurrencyId;
	}

	public void setCommerceCurrencyId(long commerceCurrencyId) {
		_commerceCurrencyId = commerceCurrencyId;
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

	public String getCode() {
		return _code;
	}

	public void setCode(String code) {
		_code = code;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public BigDecimal getRate() {
		return _rate;
	}

	public void setRate(BigDecimal rate) {
		_rate = rate;
	}

	public String getRoundingType() {
		return _roundingType;
	}

	public void setRoundingType(String roundingType) {
		_roundingType = roundingType;
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

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _commerceCurrencyId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _code;
	private String _name;
	private BigDecimal _rate;
	private String _roundingType;
	private boolean _primary;
	private double _priority;
	private boolean _active;
	private Date _lastPublishDate;
}