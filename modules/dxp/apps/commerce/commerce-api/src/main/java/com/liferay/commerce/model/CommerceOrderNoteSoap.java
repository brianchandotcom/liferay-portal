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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceOrderNoteServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceOrderNoteServiceSoap
 * @generated
 */
@ProviderType
public class CommerceOrderNoteSoap implements Serializable {
	public static CommerceOrderNoteSoap toSoapModel(CommerceOrderNote model) {
		CommerceOrderNoteSoap soapModel = new CommerceOrderNoteSoap();

		soapModel.setCommerceOrderNoteId(model.getCommerceOrderNoteId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceOrderId(model.getCommerceOrderId());
		soapModel.setContent(model.getContent());
		soapModel.setRestricted(model.isRestricted());

		return soapModel;
	}

	public static CommerceOrderNoteSoap[] toSoapModels(
		CommerceOrderNote[] models) {
		CommerceOrderNoteSoap[] soapModels = new CommerceOrderNoteSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceOrderNoteSoap[][] toSoapModels(
		CommerceOrderNote[][] models) {
		CommerceOrderNoteSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceOrderNoteSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceOrderNoteSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceOrderNoteSoap[] toSoapModels(
		List<CommerceOrderNote> models) {
		List<CommerceOrderNoteSoap> soapModels = new ArrayList<CommerceOrderNoteSoap>(models.size());

		for (CommerceOrderNote model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceOrderNoteSoap[soapModels.size()]);
	}

	public CommerceOrderNoteSoap() {
	}

	public long getPrimaryKey() {
		return _commerceOrderNoteId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceOrderNoteId(pk);
	}

	public long getCommerceOrderNoteId() {
		return _commerceOrderNoteId;
	}

	public void setCommerceOrderNoteId(long commerceOrderNoteId) {
		_commerceOrderNoteId = commerceOrderNoteId;
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

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderId = commerceOrderId;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_content = content;
	}

	public boolean getRestricted() {
		return _restricted;
	}

	public boolean isRestricted() {
		return _restricted;
	}

	public void setRestricted(boolean restricted) {
		_restricted = restricted;
	}

	private long _commerceOrderNoteId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceOrderId;
	private String _content;
	private boolean _restricted;
}