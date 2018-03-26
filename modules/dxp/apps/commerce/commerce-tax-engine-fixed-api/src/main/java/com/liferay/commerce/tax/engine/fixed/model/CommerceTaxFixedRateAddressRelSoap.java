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

package com.liferay.commerce.tax.engine.fixed.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.tax.engine.fixed.service.http.CommerceTaxFixedRateAddressRelServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.tax.engine.fixed.service.http.CommerceTaxFixedRateAddressRelServiceSoap
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateAddressRelSoap implements Serializable {
	public static CommerceTaxFixedRateAddressRelSoap toSoapModel(
		CommerceTaxFixedRateAddressRel model) {
		CommerceTaxFixedRateAddressRelSoap soapModel = new CommerceTaxFixedRateAddressRelSoap();

		soapModel.setCommerceTaxFixedRateAddressRelId(model.getCommerceTaxFixedRateAddressRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceTaxMethodId(model.getCommerceTaxMethodId());
		soapModel.setCommerceTaxCategoryId(model.getCommerceTaxCategoryId());
		soapModel.setCommerceCountryId(model.getCommerceCountryId());
		soapModel.setCommerceRegionId(model.getCommerceRegionId());
		soapModel.setZip(model.getZip());
		soapModel.setRate(model.getRate());

		return soapModel;
	}

	public static CommerceTaxFixedRateAddressRelSoap[] toSoapModels(
		CommerceTaxFixedRateAddressRel[] models) {
		CommerceTaxFixedRateAddressRelSoap[] soapModels = new CommerceTaxFixedRateAddressRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceTaxFixedRateAddressRelSoap[][] toSoapModels(
		CommerceTaxFixedRateAddressRel[][] models) {
		CommerceTaxFixedRateAddressRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceTaxFixedRateAddressRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceTaxFixedRateAddressRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceTaxFixedRateAddressRelSoap[] toSoapModels(
		List<CommerceTaxFixedRateAddressRel> models) {
		List<CommerceTaxFixedRateAddressRelSoap> soapModels = new ArrayList<CommerceTaxFixedRateAddressRelSoap>(models.size());

		for (CommerceTaxFixedRateAddressRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceTaxFixedRateAddressRelSoap[soapModels.size()]);
	}

	public CommerceTaxFixedRateAddressRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceTaxFixedRateAddressRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceTaxFixedRateAddressRelId(pk);
	}

	public long getCommerceTaxFixedRateAddressRelId() {
		return _commerceTaxFixedRateAddressRelId;
	}

	public void setCommerceTaxFixedRateAddressRelId(
		long commerceTaxFixedRateAddressRelId) {
		_commerceTaxFixedRateAddressRelId = commerceTaxFixedRateAddressRelId;
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

	public long getCommerceTaxMethodId() {
		return _commerceTaxMethodId;
	}

	public void setCommerceTaxMethodId(long commerceTaxMethodId) {
		_commerceTaxMethodId = commerceTaxMethodId;
	}

	public long getCommerceTaxCategoryId() {
		return _commerceTaxCategoryId;
	}

	public void setCommerceTaxCategoryId(long commerceTaxCategoryId) {
		_commerceTaxCategoryId = commerceTaxCategoryId;
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

	public double getRate() {
		return _rate;
	}

	public void setRate(double rate) {
		_rate = rate;
	}

	private long _commerceTaxFixedRateAddressRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceTaxMethodId;
	private long _commerceTaxCategoryId;
	private long _commerceCountryId;
	private long _commerceRegionId;
	private String _zip;
	private double _rate;
}