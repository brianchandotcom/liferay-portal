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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceWarehouseServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceWarehouseServiceSoap
 * @generated
 */
@ProviderType
public class CommerceWarehouseSoap implements Serializable {
	public static CommerceWarehouseSoap toSoapModel(CommerceWarehouse model) {
		CommerceWarehouseSoap soapModel = new CommerceWarehouseSoap();

		soapModel.setCommerceWarehouseId(model.getCommerceWarehouseId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setActive(model.isActive());
		soapModel.setStreet1(model.getStreet1());
		soapModel.setStreet2(model.getStreet2());
		soapModel.setStreet3(model.getStreet3());
		soapModel.setCity(model.getCity());
		soapModel.setZip(model.getZip());
		soapModel.setCommerceRegionId(model.getCommerceRegionId());
		soapModel.setCommerceCountryId(model.getCommerceCountryId());
		soapModel.setLatitude(model.getLatitude());
		soapModel.setLongitude(model.getLongitude());

		return soapModel;
	}

	public static CommerceWarehouseSoap[] toSoapModels(
		CommerceWarehouse[] models) {
		CommerceWarehouseSoap[] soapModels = new CommerceWarehouseSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceWarehouseSoap[][] toSoapModels(
		CommerceWarehouse[][] models) {
		CommerceWarehouseSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceWarehouseSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceWarehouseSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceWarehouseSoap[] toSoapModels(
		List<CommerceWarehouse> models) {
		List<CommerceWarehouseSoap> soapModels = new ArrayList<CommerceWarehouseSoap>(models.size());

		for (CommerceWarehouse model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceWarehouseSoap[soapModels.size()]);
	}

	public CommerceWarehouseSoap() {
	}

	public long getPrimaryKey() {
		return _commerceWarehouseId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceWarehouseId(pk);
	}

	public long getCommerceWarehouseId() {
		return _commerceWarehouseId;
	}

	public void setCommerceWarehouseId(long commerceWarehouseId) {
		_commerceWarehouseId = commerceWarehouseId;
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

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public String getStreet1() {
		return _street1;
	}

	public void setStreet1(String street1) {
		_street1 = street1;
	}

	public String getStreet2() {
		return _street2;
	}

	public void setStreet2(String street2) {
		_street2 = street2;
	}

	public String getStreet3() {
		return _street3;
	}

	public void setStreet3(String street3) {
		_street3 = street3;
	}

	public String getCity() {
		return _city;
	}

	public void setCity(String city) {
		_city = city;
	}

	public String getZip() {
		return _zip;
	}

	public void setZip(String zip) {
		_zip = zip;
	}

	public long getCommerceRegionId() {
		return _commerceRegionId;
	}

	public void setCommerceRegionId(long commerceRegionId) {
		_commerceRegionId = commerceRegionId;
	}

	public long getCommerceCountryId() {
		return _commerceCountryId;
	}

	public void setCommerceCountryId(long commerceCountryId) {
		_commerceCountryId = commerceCountryId;
	}

	public double getLatitude() {
		return _latitude;
	}

	public void setLatitude(double latitude) {
		_latitude = latitude;
	}

	public double getLongitude() {
		return _longitude;
	}

	public void setLongitude(double longitude) {
		_longitude = longitude;
	}

	private long _commerceWarehouseId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private boolean _active;
	private String _street1;
	private String _street2;
	private String _street3;
	private String _city;
	private String _zip;
	private long _commerceRegionId;
	private long _commerceCountryId;
	private double _latitude;
	private double _longitude;
}