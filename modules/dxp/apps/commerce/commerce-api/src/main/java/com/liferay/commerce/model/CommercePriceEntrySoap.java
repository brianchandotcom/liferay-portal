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

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommercePriceEntryServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommercePriceEntryServiceSoap
 * @generated
 */
@ProviderType
public class CommercePriceEntrySoap implements Serializable {
	public static CommercePriceEntrySoap toSoapModel(CommercePriceEntry model) {
		CommercePriceEntrySoap soapModel = new CommercePriceEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommercePriceEntryId(model.getCommercePriceEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPInstanceId(model.getCPInstanceId());
		soapModel.setCommercePriceListId(model.getCommercePriceListId());
		soapModel.setPrice(model.getPrice());
		soapModel.setPromoPrice(model.getPromoPrice());
		soapModel.setHasTierPrice(model.isHasTierPrice());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CommercePriceEntrySoap[] toSoapModels(
		CommercePriceEntry[] models) {
		CommercePriceEntrySoap[] soapModels = new CommercePriceEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceEntrySoap[][] toSoapModels(
		CommercePriceEntry[][] models) {
		CommercePriceEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommercePriceEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommercePriceEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceEntrySoap[] toSoapModels(
		List<CommercePriceEntry> models) {
		List<CommercePriceEntrySoap> soapModels = new ArrayList<CommercePriceEntrySoap>(models.size());

		for (CommercePriceEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommercePriceEntrySoap[soapModels.size()]);
	}

	public CommercePriceEntrySoap() {
	}

	public long getPrimaryKey() {
		return _commercePriceEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCommercePriceEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommercePriceEntryId() {
		return _commercePriceEntryId;
	}

	public void setCommercePriceEntryId(long commercePriceEntryId) {
		_commercePriceEntryId = commercePriceEntryId;
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

	public long getCPInstanceId() {
		return _CPInstanceId;
	}

	public void setCPInstanceId(long CPInstanceId) {
		_CPInstanceId = CPInstanceId;
	}

	public long getCommercePriceListId() {
		return _commercePriceListId;
	}

	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListId = commercePriceListId;
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

	public boolean getHasTierPrice() {
		return _hasTierPrice;
	}

	public boolean isHasTierPrice() {
		return _hasTierPrice;
	}

	public void setHasTierPrice(boolean hasTierPrice) {
		_hasTierPrice = hasTierPrice;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _commercePriceEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPInstanceId;
	private long _commercePriceListId;
	private BigDecimal _price;
	private BigDecimal _promoPrice;
	private boolean _hasTierPrice;
	private Date _lastPublishDate;
}