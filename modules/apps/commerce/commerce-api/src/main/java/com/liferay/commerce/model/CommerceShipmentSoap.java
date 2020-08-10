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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceShipmentServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShipmentSoap implements Serializable {

	public static CommerceShipmentSoap toSoapModel(CommerceShipment model) {
		CommerceShipmentSoap soapModel = new CommerceShipmentSoap();

		soapModel.setCommerceShipmentId(model.getCommerceShipmentId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceAccountId(model.getCommerceAccountId());
		soapModel.setCommerceAddressId(model.getCommerceAddressId());
		soapModel.setCommerceShippingMethodId(
			model.getCommerceShippingMethodId());
		soapModel.setShippingOptionName(model.getShippingOptionName());
		soapModel.setCarrier(model.getCarrier());
		soapModel.setTrackingNumber(model.getTrackingNumber());
		soapModel.setShippingDate(model.getShippingDate());
		soapModel.setExpectedDate(model.getExpectedDate());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static CommerceShipmentSoap[] toSoapModels(
		CommerceShipment[] models) {

		CommerceShipmentSoap[] soapModels =
			new CommerceShipmentSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceShipmentSoap[][] toSoapModels(
		CommerceShipment[][] models) {

		CommerceShipmentSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceShipmentSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceShipmentSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceShipmentSoap[] toSoapModels(
		List<CommerceShipment> models) {

		List<CommerceShipmentSoap> soapModels =
			new ArrayList<CommerceShipmentSoap>(models.size());

		for (CommerceShipment model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceShipmentSoap[soapModels.size()]);
	}

	public CommerceShipmentSoap() {
	}

	public long getPrimaryKey() {
		return _commerceShipmentId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceShipmentId(pk);
	}

	public long getCommerceShipmentId() {
		return _commerceShipmentId;
	}

	public void setCommerceShipmentId(long commerceShipmentId) {
		_commerceShipmentId = commerceShipmentId;
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

	public long getCommerceAccountId() {
		return _commerceAccountId;
	}

	public void setCommerceAccountId(long commerceAccountId) {
		_commerceAccountId = commerceAccountId;
	}

	public long getCommerceAddressId() {
		return _commerceAddressId;
	}

	public void setCommerceAddressId(long commerceAddressId) {
		_commerceAddressId = commerceAddressId;
	}

	public long getCommerceShippingMethodId() {
		return _commerceShippingMethodId;
	}

	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		_commerceShippingMethodId = commerceShippingMethodId;
	}

	public String getShippingOptionName() {
		return _shippingOptionName;
	}

	public void setShippingOptionName(String shippingOptionName) {
		_shippingOptionName = shippingOptionName;
	}

	public String getCarrier() {
		return _carrier;
	}

	public void setCarrier(String carrier) {
		_carrier = carrier;
	}

	public String getTrackingNumber() {
		return _trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		_trackingNumber = trackingNumber;
	}

	public Date getShippingDate() {
		return _shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		_shippingDate = shippingDate;
	}

	public Date getExpectedDate() {
		return _expectedDate;
	}

	public void setExpectedDate(Date expectedDate) {
		_expectedDate = expectedDate;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _commerceShipmentId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceAccountId;
	private long _commerceAddressId;
	private long _commerceShippingMethodId;
	private String _shippingOptionName;
	private String _carrier;
	private String _trackingNumber;
	private Date _shippingDate;
	private Date _expectedDate;
	private int _status;

}