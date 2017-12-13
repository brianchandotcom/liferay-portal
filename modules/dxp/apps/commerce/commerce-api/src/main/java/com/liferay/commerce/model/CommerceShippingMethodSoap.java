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

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceShippingMethodServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceShippingMethodServiceSoap
 * @generated
 */
@ProviderType
public class CommerceShippingMethodSoap implements Serializable {
	public static CommerceShippingMethodSoap toSoapModel(
		CommerceShippingMethod model) {
		CommerceShippingMethodSoap soapModel = new CommerceShippingMethodSoap();

		soapModel.setCommerceShippingMethodId(model.getCommerceShippingMethodId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setImageId(model.getImageId());
		soapModel.setEngineKey(model.getEngineKey());
		soapModel.setPriority(model.getPriority());
		soapModel.setActive(model.getActive());

		return soapModel;
	}

	public static CommerceShippingMethodSoap[] toSoapModels(
		CommerceShippingMethod[] models) {
		CommerceShippingMethodSoap[] soapModels = new CommerceShippingMethodSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceShippingMethodSoap[][] toSoapModels(
		CommerceShippingMethod[][] models) {
		CommerceShippingMethodSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceShippingMethodSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceShippingMethodSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceShippingMethodSoap[] toSoapModels(
		List<CommerceShippingMethod> models) {
		List<CommerceShippingMethodSoap> soapModels = new ArrayList<CommerceShippingMethodSoap>(models.size());

		for (CommerceShippingMethod model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceShippingMethodSoap[soapModels.size()]);
	}

	public CommerceShippingMethodSoap() {
	}

	public long getPrimaryKey() {
		return _commerceShippingMethodId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceShippingMethodId(pk);
	}

	public long getCommerceShippingMethodId() {
		return _commerceShippingMethodId;
	}

	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		_commerceShippingMethodId = commerceShippingMethodId;
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

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public long getImageId() {
		return _imageId;
	}

	public void setImageId(long imageId) {
		_imageId = imageId;
	}

	public String getEngineKey() {
		return _engineKey;
	}

	public void setEngineKey(String engineKey) {
		_engineKey = engineKey;
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

	private long _commerceShippingMethodId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private long _imageId;
	private String _engineKey;
	private double _priority;
	private boolean _active;
}