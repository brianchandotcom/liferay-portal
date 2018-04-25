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

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.discount.service.http.CommerceDiscountUserSegmentRelServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.discount.service.http.CommerceDiscountUserSegmentRelServiceSoap
 * @generated
 */
@ProviderType
public class CommerceDiscountUserSegmentRelSoap implements Serializable {
	public static CommerceDiscountUserSegmentRelSoap toSoapModel(
		CommerceDiscountUserSegmentRel model) {
		CommerceDiscountUserSegmentRelSoap soapModel = new CommerceDiscountUserSegmentRelSoap();

		soapModel.setCommerceDiscountUserSegmentRelId(model.getCommerceDiscountUserSegmentRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceDiscountId(model.getCommerceDiscountId());
		soapModel.setCommerceUserSegmentEntryId(model.getCommerceUserSegmentEntryId());

		return soapModel;
	}

	public static CommerceDiscountUserSegmentRelSoap[] toSoapModels(
		CommerceDiscountUserSegmentRel[] models) {
		CommerceDiscountUserSegmentRelSoap[] soapModels = new CommerceDiscountUserSegmentRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceDiscountUserSegmentRelSoap[][] toSoapModels(
		CommerceDiscountUserSegmentRel[][] models) {
		CommerceDiscountUserSegmentRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceDiscountUserSegmentRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceDiscountUserSegmentRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceDiscountUserSegmentRelSoap[] toSoapModels(
		List<CommerceDiscountUserSegmentRel> models) {
		List<CommerceDiscountUserSegmentRelSoap> soapModels = new ArrayList<CommerceDiscountUserSegmentRelSoap>(models.size());

		for (CommerceDiscountUserSegmentRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceDiscountUserSegmentRelSoap[soapModels.size()]);
	}

	public CommerceDiscountUserSegmentRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceDiscountUserSegmentRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceDiscountUserSegmentRelId(pk);
	}

	public long getCommerceDiscountUserSegmentRelId() {
		return _commerceDiscountUserSegmentRelId;
	}

	public void setCommerceDiscountUserSegmentRelId(
		long commerceDiscountUserSegmentRelId) {
		_commerceDiscountUserSegmentRelId = commerceDiscountUserSegmentRelId;
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

	public long getCommerceDiscountId() {
		return _commerceDiscountId;
	}

	public void setCommerceDiscountId(long commerceDiscountId) {
		_commerceDiscountId = commerceDiscountId;
	}

	public long getCommerceUserSegmentEntryId() {
		return _commerceUserSegmentEntryId;
	}

	public void setCommerceUserSegmentEntryId(long commerceUserSegmentEntryId) {
		_commerceUserSegmentEntryId = commerceUserSegmentEntryId;
	}

	private long _commerceDiscountUserSegmentRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceDiscountId;
	private long _commerceUserSegmentEntryId;
}