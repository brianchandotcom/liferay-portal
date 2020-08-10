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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.account.service.http.CommerceAccountGroupRelServiceSoap}.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceAccountGroupRelSoap implements Serializable {

	public static CommerceAccountGroupRelSoap toSoapModel(
		CommerceAccountGroupRel model) {

		CommerceAccountGroupRelSoap soapModel =
			new CommerceAccountGroupRelSoap();

		soapModel.setCommerceAccountGroupRelId(
			model.getCommerceAccountGroupRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setCommerceAccountGroupId(model.getCommerceAccountGroupId());

		return soapModel;
	}

	public static CommerceAccountGroupRelSoap[] toSoapModels(
		CommerceAccountGroupRel[] models) {

		CommerceAccountGroupRelSoap[] soapModels =
			new CommerceAccountGroupRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceAccountGroupRelSoap[][] toSoapModels(
		CommerceAccountGroupRel[][] models) {

		CommerceAccountGroupRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceAccountGroupRelSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceAccountGroupRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceAccountGroupRelSoap[] toSoapModels(
		List<CommerceAccountGroupRel> models) {

		List<CommerceAccountGroupRelSoap> soapModels =
			new ArrayList<CommerceAccountGroupRelSoap>(models.size());

		for (CommerceAccountGroupRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceAccountGroupRelSoap[soapModels.size()]);
	}

	public CommerceAccountGroupRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceAccountGroupRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceAccountGroupRelId(pk);
	}

	public long getCommerceAccountGroupRelId() {
		return _commerceAccountGroupRelId;
	}

	public void setCommerceAccountGroupRelId(long commerceAccountGroupRelId) {
		_commerceAccountGroupRelId = commerceAccountGroupRelId;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getCommerceAccountGroupId() {
		return _commerceAccountGroupId;
	}

	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		_commerceAccountGroupId = commerceAccountGroupId;
	}

	private long _commerceAccountGroupRelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _commerceAccountGroupId;

}