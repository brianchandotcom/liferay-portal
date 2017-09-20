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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceTirePriceEntryServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceTirePriceEntryServiceSoap
 * @generated
 */
@ProviderType
public class CommerceTirePriceEntrySoap implements Serializable {
	public static CommerceTirePriceEntrySoap toSoapModel(
		CommerceTirePriceEntry model) {
		CommerceTirePriceEntrySoap soapModel = new CommerceTirePriceEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceTirePriceEntryId(model.getCommerceTirePriceEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommercePriceEntryId(model.getCommercePriceEntryId());
		soapModel.setPrice(model.getPrice());
		soapModel.setMinQuantity(model.getMinQuantity());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CommerceTirePriceEntrySoap[] toSoapModels(
		CommerceTirePriceEntry[] models) {
		CommerceTirePriceEntrySoap[] soapModels = new CommerceTirePriceEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceTirePriceEntrySoap[][] toSoapModels(
		CommerceTirePriceEntry[][] models) {
		CommerceTirePriceEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceTirePriceEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceTirePriceEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceTirePriceEntrySoap[] toSoapModels(
		List<CommerceTirePriceEntry> models) {
		List<CommerceTirePriceEntrySoap> soapModels = new ArrayList<CommerceTirePriceEntrySoap>(models.size());

		for (CommerceTirePriceEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceTirePriceEntrySoap[soapModels.size()]);
	}

	public CommerceTirePriceEntrySoap() {
	}

	public long getPrimaryKey() {
		return _CommerceTirePriceEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceTirePriceEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceTirePriceEntryId() {
		return _CommerceTirePriceEntryId;
	}

	public void setCommerceTirePriceEntryId(long CommerceTirePriceEntryId) {
		_CommerceTirePriceEntryId = CommerceTirePriceEntryId;
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

	public double getPrice() {
		return _price;
	}

	public void setPrice(double price) {
		_price = price;
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
	private long _CommerceTirePriceEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commercePriceEntryId;
	private double _price;
	private int _minQuantity;
	private Date _lastPublishDate;
}