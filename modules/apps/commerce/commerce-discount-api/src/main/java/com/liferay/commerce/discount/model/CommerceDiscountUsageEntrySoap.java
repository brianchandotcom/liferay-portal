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

package com.liferay.commerce.discount.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountUsageEntrySoap implements Serializable {

	public static CommerceDiscountUsageEntrySoap toSoapModel(
		CommerceDiscountUsageEntry model) {

		CommerceDiscountUsageEntrySoap soapModel =
			new CommerceDiscountUsageEntrySoap();

		soapModel.setCommerceDiscountUsageEntryId(
			model.getCommerceDiscountUsageEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceAccountId(model.getCommerceAccountId());
		soapModel.setCommerceOrderId(model.getCommerceOrderId());
		soapModel.setCommerceDiscountId(model.getCommerceDiscountId());

		return soapModel;
	}

	public static CommerceDiscountUsageEntrySoap[] toSoapModels(
		CommerceDiscountUsageEntry[] models) {

		CommerceDiscountUsageEntrySoap[] soapModels =
			new CommerceDiscountUsageEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceDiscountUsageEntrySoap[][] toSoapModels(
		CommerceDiscountUsageEntry[][] models) {

		CommerceDiscountUsageEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceDiscountUsageEntrySoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceDiscountUsageEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceDiscountUsageEntrySoap[] toSoapModels(
		List<CommerceDiscountUsageEntry> models) {

		List<CommerceDiscountUsageEntrySoap> soapModels =
			new ArrayList<CommerceDiscountUsageEntrySoap>(models.size());

		for (CommerceDiscountUsageEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceDiscountUsageEntrySoap[soapModels.size()]);
	}

	public CommerceDiscountUsageEntrySoap() {
	}

	public long getPrimaryKey() {
		return _commerceDiscountUsageEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceDiscountUsageEntryId(pk);
	}

	public long getCommerceDiscountUsageEntryId() {
		return _commerceDiscountUsageEntryId;
	}

	public void setCommerceDiscountUsageEntryId(
		long commerceDiscountUsageEntryId) {

		_commerceDiscountUsageEntryId = commerceDiscountUsageEntryId;
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

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderId = commerceOrderId;
	}

	public long getCommerceDiscountId() {
		return _commerceDiscountId;
	}

	public void setCommerceDiscountId(long commerceDiscountId) {
		_commerceDiscountId = commerceDiscountId;
	}

	private long _commerceDiscountUsageEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceAccountId;
	private long _commerceOrderId;
	private long _commerceDiscountId;

}