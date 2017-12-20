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

package com.liferay.commerce.shipping.engine.fixed.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.shipping.engine.fixed.service.http.CommerceShippingFixedOptionServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.shipping.engine.fixed.service.http.CommerceShippingFixedOptionServiceSoap
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionSoap implements Serializable {
	public static CommerceShippingFixedOptionSoap toSoapModel(
		CommerceShippingFixedOption model) {
		CommerceShippingFixedOptionSoap soapModel = new CommerceShippingFixedOptionSoap();

		soapModel.setCommerceShippingFixedOptionId(model.getCommerceShippingFixedOptionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceShippingMethodId(model.getCommerceShippingMethodId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setAmount(model.getAmount());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static CommerceShippingFixedOptionSoap[] toSoapModels(
		CommerceShippingFixedOption[] models) {
		CommerceShippingFixedOptionSoap[] soapModels = new CommerceShippingFixedOptionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceShippingFixedOptionSoap[][] toSoapModels(
		CommerceShippingFixedOption[][] models) {
		CommerceShippingFixedOptionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceShippingFixedOptionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceShippingFixedOptionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceShippingFixedOptionSoap[] toSoapModels(
		List<CommerceShippingFixedOption> models) {
		List<CommerceShippingFixedOptionSoap> soapModels = new ArrayList<CommerceShippingFixedOptionSoap>(models.size());

		for (CommerceShippingFixedOption model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceShippingFixedOptionSoap[soapModels.size()]);
	}

	public CommerceShippingFixedOptionSoap() {
	}

	public long getPrimaryKey() {
		return _commerceShippingFixedOptionId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceShippingFixedOptionId(pk);
	}

	public long getCommerceShippingFixedOptionId() {
		return _commerceShippingFixedOptionId;
	}

	public void setCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		_commerceShippingFixedOptionId = commerceShippingFixedOptionId;
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

	public long getCommerceShippingMethodId() {
		return _commerceShippingMethodId;
	}

	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		_commerceShippingMethodId = commerceShippingMethodId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public double getAmount() {
		return _amount;
	}

	public void setAmount(double amount) {
		_amount = amount;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	private long _commerceShippingFixedOptionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceShippingMethodId;
	private String _name;
	private String _description;
	private double _amount;
	private double _priority;
}