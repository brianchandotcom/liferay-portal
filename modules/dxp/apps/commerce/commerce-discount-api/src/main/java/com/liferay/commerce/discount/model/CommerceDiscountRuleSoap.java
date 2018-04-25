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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.discount.service.http.CommerceDiscountRuleServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.discount.service.http.CommerceDiscountRuleServiceSoap
 * @generated
 */
@ProviderType
public class CommerceDiscountRuleSoap implements Serializable {
	public static CommerceDiscountRuleSoap toSoapModel(
		CommerceDiscountRule model) {
		CommerceDiscountRuleSoap soapModel = new CommerceDiscountRuleSoap();

		soapModel.setCommerceDiscountRuleId(model.getCommerceDiscountRuleId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceDiscountId(model.getCommerceDiscountId());
		soapModel.setType(model.getType());
		soapModel.setTypeSettings(model.getTypeSettings());

		return soapModel;
	}

	public static CommerceDiscountRuleSoap[] toSoapModels(
		CommerceDiscountRule[] models) {
		CommerceDiscountRuleSoap[] soapModels = new CommerceDiscountRuleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceDiscountRuleSoap[][] toSoapModels(
		CommerceDiscountRule[][] models) {
		CommerceDiscountRuleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceDiscountRuleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceDiscountRuleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceDiscountRuleSoap[] toSoapModels(
		List<CommerceDiscountRule> models) {
		List<CommerceDiscountRuleSoap> soapModels = new ArrayList<CommerceDiscountRuleSoap>(models.size());

		for (CommerceDiscountRule model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceDiscountRuleSoap[soapModels.size()]);
	}

	public CommerceDiscountRuleSoap() {
	}

	public long getPrimaryKey() {
		return _commerceDiscountRuleId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceDiscountRuleId(pk);
	}

	public long getCommerceDiscountRuleId() {
		return _commerceDiscountRuleId;
	}

	public void setCommerceDiscountRuleId(long commerceDiscountRuleId) {
		_commerceDiscountRuleId = commerceDiscountRuleId;
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

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	private long _commerceDiscountRuleId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceDiscountId;
	private String _type;
	private String _typeSettings;
}