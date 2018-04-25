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

package com.liferay.commerce.price.list.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.price.list.service.http.CommerceTierPriceEntryServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.price.list.service.http.CommerceTierPriceEntryServiceSoap
 * @generated
 */
@ProviderType
public class CommerceTierPriceEntrySoap implements Serializable {
	public static CommerceTierPriceEntrySoap toSoapModel(
		CommerceTierPriceEntry model) {
		CommerceTierPriceEntrySoap soapModel = new CommerceTierPriceEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceTierPriceEntryId(model.getCommerceTierPriceEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommercePriceEntryId(model.getCommercePriceEntryId());
		soapModel.setPrice(model.getPrice());
		soapModel.setPromoPrice(model.getPromoPrice());
		soapModel.setMinQuantity(model.getMinQuantity());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CommerceTierPriceEntrySoap[] toSoapModels(
		CommerceTierPriceEntry[] models) {
		CommerceTierPriceEntrySoap[] soapModels = new CommerceTierPriceEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceTierPriceEntrySoap[][] toSoapModels(
		CommerceTierPriceEntry[][] models) {
		CommerceTierPriceEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceTierPriceEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceTierPriceEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceTierPriceEntrySoap[] toSoapModels(
		List<CommerceTierPriceEntry> models) {
		List<CommerceTierPriceEntrySoap> soapModels = new ArrayList<CommerceTierPriceEntrySoap>(models.size());

		for (CommerceTierPriceEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceTierPriceEntrySoap[soapModels.size()]);
	}

	public CommerceTierPriceEntrySoap() {
	}

	public long getPrimaryKey() {
		return _commerceTierPriceEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceTierPriceEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceTierPriceEntryId() {
		return _commerceTierPriceEntryId;
	}

	public void setCommerceTierPriceEntryId(long commerceTierPriceEntryId) {
		_commerceTierPriceEntryId = commerceTierPriceEntryId;
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

	public long getCommercePriceEntryId() {
		return _commercePriceEntryId;
	}

	public void setCommercePriceEntryId(long commercePriceEntryId) {
		_commercePriceEntryId = commercePriceEntryId;
	}

	public BigDecimal getPrice() {
		return _price;
	}

	public void setPrice(BigDecimal price) {
		_price = price;
	}

	public BigDecimal getPromoPrice() {
		return _promoPrice;
	}

	public void setPromoPrice(BigDecimal promoPrice) {
		_promoPrice = promoPrice;
	}

	public int getMinQuantity() {
		return _minQuantity;
	}

	public void setMinQuantity(int minQuantity) {
		_minQuantity = minQuantity;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _commerceTierPriceEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commercePriceEntryId;
	private BigDecimal _price;
	private BigDecimal _promoPrice;
	private int _minQuantity;
	private Date _lastPublishDate;
}