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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.discount.service.http.CommerceDiscountCommerceAccountGroupRelServiceSoap}.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountCommerceAccountGroupRelSoap
	implements Serializable {

	public static CommerceDiscountCommerceAccountGroupRelSoap toSoapModel(
		CommerceDiscountCommerceAccountGroupRel model) {

		CommerceDiscountCommerceAccountGroupRelSoap soapModel =
			new CommerceDiscountCommerceAccountGroupRelSoap();

		soapModel.setCommerceDiscountCommerceAccountGroupRelId(
			model.getCommerceDiscountCommerceAccountGroupRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceDiscountId(model.getCommerceDiscountId());
		soapModel.setCommerceAccountGroupId(model.getCommerceAccountGroupId());

		return soapModel;
	}

	public static CommerceDiscountCommerceAccountGroupRelSoap[] toSoapModels(
		CommerceDiscountCommerceAccountGroupRel[] models) {

		CommerceDiscountCommerceAccountGroupRelSoap[] soapModels =
			new CommerceDiscountCommerceAccountGroupRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceDiscountCommerceAccountGroupRelSoap[][] toSoapModels(
		CommerceDiscountCommerceAccountGroupRel[][] models) {

		CommerceDiscountCommerceAccountGroupRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceDiscountCommerceAccountGroupRelSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceDiscountCommerceAccountGroupRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceDiscountCommerceAccountGroupRelSoap[] toSoapModels(
		List<CommerceDiscountCommerceAccountGroupRel> models) {

		List<CommerceDiscountCommerceAccountGroupRelSoap> soapModels =
			new ArrayList<CommerceDiscountCommerceAccountGroupRelSoap>(
				models.size());

		for (CommerceDiscountCommerceAccountGroupRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceDiscountCommerceAccountGroupRelSoap[soapModels.size()]);
	}

	public CommerceDiscountCommerceAccountGroupRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceDiscountCommerceAccountGroupRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceDiscountCommerceAccountGroupRelId(pk);
	}

	public long getCommerceDiscountCommerceAccountGroupRelId() {
		return _commerceDiscountCommerceAccountGroupRelId;
	}

	public void setCommerceDiscountCommerceAccountGroupRelId(
		long commerceDiscountCommerceAccountGroupRelId) {

		_commerceDiscountCommerceAccountGroupRelId =
			commerceDiscountCommerceAccountGroupRelId;
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

	public long getCommerceAccountGroupId() {
		return _commerceAccountGroupId;
	}

	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		_commerceAccountGroupId = commerceAccountGroupId;
	}

	private long _commerceDiscountCommerceAccountGroupRelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceDiscountId;
	private long _commerceAccountGroupId;

}