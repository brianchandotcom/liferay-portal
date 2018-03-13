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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceTaxMethodServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceTaxMethodServiceSoap
 * @generated
 */
@ProviderType
public class CommerceTaxMethodSoap implements Serializable {
	public static CommerceTaxMethodSoap toSoapModel(CommerceTaxMethod model) {
		CommerceTaxMethodSoap soapModel = new CommerceTaxMethodSoap();

		soapModel.setCommerceTaxMethodId(model.getCommerceTaxMethodId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setEngineKey(model.getEngineKey());
		soapModel.setPercentage(model.getPercentage());
		soapModel.setActive(model.getActive());

		return soapModel;
	}

	public static CommerceTaxMethodSoap[] toSoapModels(
		CommerceTaxMethod[] models) {
		CommerceTaxMethodSoap[] soapModels = new CommerceTaxMethodSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceTaxMethodSoap[][] toSoapModels(
		CommerceTaxMethod[][] models) {
		CommerceTaxMethodSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceTaxMethodSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceTaxMethodSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceTaxMethodSoap[] toSoapModels(
		List<CommerceTaxMethod> models) {
		List<CommerceTaxMethodSoap> soapModels = new ArrayList<CommerceTaxMethodSoap>(models.size());

		for (CommerceTaxMethod model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceTaxMethodSoap[soapModels.size()]);
	}

	public CommerceTaxMethodSoap() {
	}

	public long getPrimaryKey() {
		return _commerceTaxMethodId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceTaxMethodId(pk);
	}

	public long getCommerceTaxMethodId() {
		return _commerceTaxMethodId;
	}

	public void setCommerceTaxMethodId(long commerceTaxMethodId) {
		_commerceTaxMethodId = commerceTaxMethodId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getEngineKey() {
		return _engineKey;
	}

	public void setEngineKey(String engineKey) {
		_engineKey = engineKey;
	}

	public boolean getPercentage() {
		return _percentage;
	}

	public boolean isPercentage() {
		return _percentage;
	}

	public void setPercentage(boolean percentage) {
		_percentage = percentage;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	private long _commerceTaxMethodId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private String _engineKey;
	private boolean _percentage;
	private boolean _active;
}