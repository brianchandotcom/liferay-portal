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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CommerceChannelRelServiceSoap}.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceChannelRelSoap implements Serializable {

	public static CommerceChannelRelSoap toSoapModel(CommerceChannelRel model) {
		CommerceChannelRelSoap soapModel = new CommerceChannelRelSoap();

		soapModel.setCommerceChannelRelId(model.getCommerceChannelRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setCommerceChannelId(model.getCommerceChannelId());

		return soapModel;
	}

	public static CommerceChannelRelSoap[] toSoapModels(
		CommerceChannelRel[] models) {

		CommerceChannelRelSoap[] soapModels =
			new CommerceChannelRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceChannelRelSoap[][] toSoapModels(
		CommerceChannelRel[][] models) {

		CommerceChannelRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceChannelRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceChannelRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceChannelRelSoap[] toSoapModels(
		List<CommerceChannelRel> models) {

		List<CommerceChannelRelSoap> soapModels =
			new ArrayList<CommerceChannelRelSoap>(models.size());

		for (CommerceChannelRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceChannelRelSoap[soapModels.size()]);
	}

	public CommerceChannelRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceChannelRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceChannelRelId(pk);
	}

	public long getCommerceChannelRelId() {
		return _commerceChannelRelId;
	}

	public void setCommerceChannelRelId(long commerceChannelRelId) {
		_commerceChannelRelId = commerceChannelRelId;
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

	public long getCommerceChannelId() {
		return _commerceChannelId;
	}

	public void setCommerceChannelId(long commerceChannelId) {
		_commerceChannelId = commerceChannelId;
	}

	private long _commerceChannelRelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _commerceChannelId;

}