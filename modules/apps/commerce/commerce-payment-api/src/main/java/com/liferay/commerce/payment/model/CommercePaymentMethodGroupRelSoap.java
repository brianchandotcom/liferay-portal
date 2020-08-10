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

package com.liferay.commerce.payment.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.payment.service.http.CommercePaymentMethodGroupRelServiceSoap}.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommercePaymentMethodGroupRelSoap implements Serializable {

	public static CommercePaymentMethodGroupRelSoap toSoapModel(
		CommercePaymentMethodGroupRel model) {

		CommercePaymentMethodGroupRelSoap soapModel =
			new CommercePaymentMethodGroupRelSoap();

		soapModel.setCommercePaymentMethodGroupRelId(
			model.getCommercePaymentMethodGroupRelId());
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
		soapModel.setActive(model.isActive());

		return soapModel;
	}

	public static CommercePaymentMethodGroupRelSoap[] toSoapModels(
		CommercePaymentMethodGroupRel[] models) {

		CommercePaymentMethodGroupRelSoap[] soapModels =
			new CommercePaymentMethodGroupRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommercePaymentMethodGroupRelSoap[][] toSoapModels(
		CommercePaymentMethodGroupRel[][] models) {

		CommercePaymentMethodGroupRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommercePaymentMethodGroupRelSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommercePaymentMethodGroupRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommercePaymentMethodGroupRelSoap[] toSoapModels(
		List<CommercePaymentMethodGroupRel> models) {

		List<CommercePaymentMethodGroupRelSoap> soapModels =
			new ArrayList<CommercePaymentMethodGroupRelSoap>(models.size());

		for (CommercePaymentMethodGroupRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommercePaymentMethodGroupRelSoap[soapModels.size()]);
	}

	public CommercePaymentMethodGroupRelSoap() {
	}

	public long getPrimaryKey() {
		return _commercePaymentMethodGroupRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommercePaymentMethodGroupRelId(pk);
	}

	public long getCommercePaymentMethodGroupRelId() {
		return _commercePaymentMethodGroupRelId;
	}

	public void setCommercePaymentMethodGroupRelId(
		long commercePaymentMethodGroupRelId) {

		_commercePaymentMethodGroupRelId = commercePaymentMethodGroupRelId;
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

	private long _commercePaymentMethodGroupRelId;
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