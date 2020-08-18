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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CommerceChannelServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceChannelSoap implements Serializable {

	public static CommerceChannelSoap toSoapModel(CommerceChannel model) {
		CommerceChannelSoap soapModel = new CommerceChannelSoap();

		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setCommerceChannelId(model.getCommerceChannelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSiteGroupId(model.getSiteGroupId());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setCommerceCurrencyCode(model.getCommerceCurrencyCode());
		soapModel.setPriceDisplayType(model.getPriceDisplayType());
		soapModel.setDiscountsTargetNetPrice(model.isDiscountsTargetNetPrice());

		return soapModel;
	}

	public static CommerceChannelSoap[] toSoapModels(CommerceChannel[] models) {
		CommerceChannelSoap[] soapModels =
			new CommerceChannelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceChannelSoap[][] toSoapModels(
		CommerceChannel[][] models) {

		CommerceChannelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceChannelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceChannelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceChannelSoap[] toSoapModels(
		List<CommerceChannel> models) {

		List<CommerceChannelSoap> soapModels =
			new ArrayList<CommerceChannelSoap>(models.size());

		for (CommerceChannel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceChannelSoap[soapModels.size()]);
	}

	public CommerceChannelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceChannelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceChannelId(pk);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getCommerceChannelId() {
		return _commerceChannelId;
	}

	public void setCommerceChannelId(long commerceChannelId) {
		_commerceChannelId = commerceChannelId;
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

	public long getSiteGroupId() {
		return _siteGroupId;
	}

	public void setSiteGroupId(long siteGroupId) {
		_siteGroupId = siteGroupId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public String getCommerceCurrencyCode() {
		return _commerceCurrencyCode;
	}

	public void setCommerceCurrencyCode(String commerceCurrencyCode) {
		_commerceCurrencyCode = commerceCurrencyCode;
	}

	public String getPriceDisplayType() {
		return _priceDisplayType;
	}

	public void setPriceDisplayType(String priceDisplayType) {
		_priceDisplayType = priceDisplayType;
	}

	public boolean getDiscountsTargetNetPrice() {
		return _discountsTargetNetPrice;
	}

	public boolean isDiscountsTargetNetPrice() {
		return _discountsTargetNetPrice;
	}

	public void setDiscountsTargetNetPrice(boolean discountsTargetNetPrice) {
		_discountsTargetNetPrice = discountsTargetNetPrice;
	}

	private String _externalReferenceCode;
	private long _commerceChannelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _siteGroupId;
	private String _name;
	private String _type;
	private String _typeSettings;
	private String _commerceCurrencyCode;
	private String _priceDisplayType;
	private boolean _discountsTargetNetPrice;

}