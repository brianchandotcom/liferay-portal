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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.shipping.engine.fixed.service.http.CShippingFixedOptionRelServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.shipping.engine.fixed.service.http.CShippingFixedOptionRelServiceSoap
 * @generated
 */
@ProviderType
public class CShippingFixedOptionRelSoap implements Serializable {
	public static CShippingFixedOptionRelSoap toSoapModel(
		CShippingFixedOptionRel model) {
		CShippingFixedOptionRelSoap soapModel = new CShippingFixedOptionRelSoap();

		soapModel.setCShippingFixedOptionRelId(model.getCShippingFixedOptionRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceShippingMethodId(model.getCommerceShippingMethodId());
		soapModel.setCommerceShippingFixedOptionId(model.getCommerceShippingFixedOptionId());
		soapModel.setCommerceWarehouseId(model.getCommerceWarehouseId());
		soapModel.setCommerceCountryId(model.getCommerceCountryId());
		soapModel.setCommerceRegionId(model.getCommerceRegionId());
		soapModel.setZip(model.getZip());
		soapModel.setWeightFrom(model.getWeightFrom());
		soapModel.setWeightTo(model.getWeightTo());
		soapModel.setFixedPrice(model.getFixedPrice());
		soapModel.setRateUnitWeightPrice(model.getRateUnitWeightPrice());
		soapModel.setRatePercentage(model.getRatePercentage());

		return soapModel;
	}

	public static CShippingFixedOptionRelSoap[] toSoapModels(
		CShippingFixedOptionRel[] models) {
		CShippingFixedOptionRelSoap[] soapModels = new CShippingFixedOptionRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CShippingFixedOptionRelSoap[][] toSoapModels(
		CShippingFixedOptionRel[][] models) {
		CShippingFixedOptionRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CShippingFixedOptionRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CShippingFixedOptionRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CShippingFixedOptionRelSoap[] toSoapModels(
		List<CShippingFixedOptionRel> models) {
		List<CShippingFixedOptionRelSoap> soapModels = new ArrayList<CShippingFixedOptionRelSoap>(models.size());

		for (CShippingFixedOptionRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CShippingFixedOptionRelSoap[soapModels.size()]);
	}

	public CShippingFixedOptionRelSoap() {
	}

	public long getPrimaryKey() {
		return _CShippingFixedOptionRelId;
	}

	public void setPrimaryKey(long pk) {
		setCShippingFixedOptionRelId(pk);
	}

	public long getCShippingFixedOptionRelId() {
		return _CShippingFixedOptionRelId;
	}

	public void setCShippingFixedOptionRelId(long CShippingFixedOptionRelId) {
		_CShippingFixedOptionRelId = CShippingFixedOptionRelId;
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

	public long getCommerceShippingFixedOptionId() {
		return _commerceShippingFixedOptionId;
	}

	public void setCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		_commerceShippingFixedOptionId = commerceShippingFixedOptionId;
	}

	public long getCommerceWarehouseId() {
		return _commerceWarehouseId;
	}

	public void setCommerceWarehouseId(long commerceWarehouseId) {
		_commerceWarehouseId = commerceWarehouseId;
	}

	public long getCommerceCountryId() {
		return _commerceCountryId;
	}

	public void setCommerceCountryId(long commerceCountryId) {
		_commerceCountryId = commerceCountryId;
	}

	public long getCommerceRegionId() {
		return _commerceRegionId;
	}

	public void setCommerceRegionId(long commerceRegionId) {
		_commerceRegionId = commerceRegionId;
	}

	public String getZip() {
		return _zip;
	}

	public void setZip(String zip) {
		_zip = zip;
	}

	public double getWeightFrom() {
		return _weightFrom;
	}

	public void setWeightFrom(double weightFrom) {
		_weightFrom = weightFrom;
	}

	public double getWeightTo() {
		return _weightTo;
	}

	public void setWeightTo(double weightTo) {
		_weightTo = weightTo;
	}

	public double getFixedPrice() {
		return _fixedPrice;
	}

	public void setFixedPrice(double fixedPrice) {
		_fixedPrice = fixedPrice;
	}

	public double getRateUnitWeightPrice() {
		return _rateUnitWeightPrice;
	}

	public void setRateUnitWeightPrice(double rateUnitWeightPrice) {
		_rateUnitWeightPrice = rateUnitWeightPrice;
	}

	public double getRatePercentage() {
		return _ratePercentage;
	}

	public void setRatePercentage(double ratePercentage) {
		_ratePercentage = ratePercentage;
	}

	private long _CShippingFixedOptionRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceShippingMethodId;
	private long _commerceShippingFixedOptionId;
	private long _commerceWarehouseId;
	private long _commerceCountryId;
	private long _commerceRegionId;
	private String _zip;
	private double _weightFrom;
	private double _weightTo;
	private double _fixedPrice;
	private double _rateUnitWeightPrice;
	private double _ratePercentage;
}