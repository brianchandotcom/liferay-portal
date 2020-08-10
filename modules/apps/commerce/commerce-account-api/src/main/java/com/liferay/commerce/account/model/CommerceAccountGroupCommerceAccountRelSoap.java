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

package com.liferay.commerce.account.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.account.service.http.CommerceAccountGroupCommerceAccountRelServiceSoap}.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceAccountGroupCommerceAccountRelSoap
	implements Serializable {

	public static CommerceAccountGroupCommerceAccountRelSoap toSoapModel(
		CommerceAccountGroupCommerceAccountRel model) {

		CommerceAccountGroupCommerceAccountRelSoap soapModel =
			new CommerceAccountGroupCommerceAccountRelSoap();

		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setCommerceAccountGroupCommerceAccountRelId(
			model.getCommerceAccountGroupCommerceAccountRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceAccountGroupId(model.getCommerceAccountGroupId());
		soapModel.setCommerceAccountId(model.getCommerceAccountId());

		return soapModel;
	}

	public static CommerceAccountGroupCommerceAccountRelSoap[] toSoapModels(
		CommerceAccountGroupCommerceAccountRel[] models) {

		CommerceAccountGroupCommerceAccountRelSoap[] soapModels =
			new CommerceAccountGroupCommerceAccountRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceAccountGroupCommerceAccountRelSoap[][] toSoapModels(
		CommerceAccountGroupCommerceAccountRel[][] models) {

		CommerceAccountGroupCommerceAccountRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceAccountGroupCommerceAccountRelSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceAccountGroupCommerceAccountRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceAccountGroupCommerceAccountRelSoap[] toSoapModels(
		List<CommerceAccountGroupCommerceAccountRel> models) {

		List<CommerceAccountGroupCommerceAccountRelSoap> soapModels =
			new ArrayList<CommerceAccountGroupCommerceAccountRelSoap>(
				models.size());

		for (CommerceAccountGroupCommerceAccountRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceAccountGroupCommerceAccountRelSoap[soapModels.size()]);
	}

	public CommerceAccountGroupCommerceAccountRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceAccountGroupCommerceAccountRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceAccountGroupCommerceAccountRelId(pk);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getCommerceAccountGroupCommerceAccountRelId() {
		return _commerceAccountGroupCommerceAccountRelId;
	}

	public void setCommerceAccountGroupCommerceAccountRelId(
		long commerceAccountGroupCommerceAccountRelId) {

		_commerceAccountGroupCommerceAccountRelId =
			commerceAccountGroupCommerceAccountRelId;
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

	public long getCommerceAccountGroupId() {
		return _commerceAccountGroupId;
	}

	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		_commerceAccountGroupId = commerceAccountGroupId;
	}

	public long getCommerceAccountId() {
		return _commerceAccountId;
	}

	public void setCommerceAccountId(long commerceAccountId) {
		_commerceAccountId = commerceAccountId;
	}

	private String _externalReferenceCode;
	private long _commerceAccountGroupCommerceAccountRelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceAccountGroupId;
	private long _commerceAccountId;

}