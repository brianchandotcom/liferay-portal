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

package com.liferay.commerce.inventory.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.inventory.service.http.CommerceInventoryWarehouseServiceSoap}.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceInventoryWarehouseSoap implements Serializable {

	public static CommerceInventoryWarehouseSoap toSoapModel(
		CommerceInventoryWarehouse model) {

		CommerceInventoryWarehouseSoap soapModel =
			new CommerceInventoryWarehouseSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setCommerceInventoryWarehouseId(
			model.getCommerceInventoryWarehouseId());
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
		soapModel.setCommerceRegionCode(model.getCommerceRegionCode());
		soapModel.setCountryTwoLettersISOCode(
			model.getCountryTwoLettersISOCode());
		soapModel.setLatitude(model.getLatitude());
		soapModel.setLongitude(model.getLongitude());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static CommerceInventoryWarehouseSoap[] toSoapModels(
		CommerceInventoryWarehouse[] models) {

		CommerceInventoryWarehouseSoap[] soapModels =
			new CommerceInventoryWarehouseSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceInventoryWarehouseSoap[][] toSoapModels(
		CommerceInventoryWarehouse[][] models) {

		CommerceInventoryWarehouseSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceInventoryWarehouseSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceInventoryWarehouseSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceInventoryWarehouseSoap[] toSoapModels(
		List<CommerceInventoryWarehouse> models) {

		List<CommerceInventoryWarehouseSoap> soapModels =
			new ArrayList<CommerceInventoryWarehouseSoap>(models.size());

		for (CommerceInventoryWarehouse model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceInventoryWarehouseSoap[soapModels.size()]);
	}

	public CommerceInventoryWarehouseSoap() {
	}

	public long getPrimaryKey() {
		return _commerceInventoryWarehouseId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceInventoryWarehouseId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getCommerceInventoryWarehouseId() {
		return _commerceInventoryWarehouseId;
	}

	public void setCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		_commerceInventoryWarehouseId = commerceInventoryWarehouseId;
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

	public String getCommerceRegionCode() {
		return _commerceRegionCode;
	}

	public void setCommerceRegionCode(String commerceRegionCode) {
		_commerceRegionCode = commerceRegionCode;
	}

	public String getCountryTwoLettersISOCode() {
		return _countryTwoLettersISOCode;
	}

	public void setCountryTwoLettersISOCode(String countryTwoLettersISOCode) {
		_countryTwoLettersISOCode = countryTwoLettersISOCode;
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

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	private long _mvccVersion;
	private String _externalReferenceCode;
	private long _commerceInventoryWarehouseId;
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
	private String _commerceRegionCode;
	private String _countryTwoLettersISOCode;
	private double _latitude;
	private double _longitude;
	private String _type;

}