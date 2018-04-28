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

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPRuleAssetCategoryRelServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CPRuleAssetCategoryRelServiceSoap
 * @generated
 */
@ProviderType
public class CPRuleAssetCategoryRelSoap implements Serializable {
	public static CPRuleAssetCategoryRelSoap toSoapModel(
		CPRuleAssetCategoryRel model) {
		CPRuleAssetCategoryRelSoap soapModel = new CPRuleAssetCategoryRelSoap();

		soapModel.setCPRuleAssetCategoryRelId(model.getCPRuleAssetCategoryRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPRuleId(model.getCPRuleId());
		soapModel.setAssetCategoryId(model.getAssetCategoryId());

		return soapModel;
	}

	public static CPRuleAssetCategoryRelSoap[] toSoapModels(
		CPRuleAssetCategoryRel[] models) {
		CPRuleAssetCategoryRelSoap[] soapModels = new CPRuleAssetCategoryRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPRuleAssetCategoryRelSoap[][] toSoapModels(
		CPRuleAssetCategoryRel[][] models) {
		CPRuleAssetCategoryRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPRuleAssetCategoryRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPRuleAssetCategoryRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPRuleAssetCategoryRelSoap[] toSoapModels(
		List<CPRuleAssetCategoryRel> models) {
		List<CPRuleAssetCategoryRelSoap> soapModels = new ArrayList<CPRuleAssetCategoryRelSoap>(models.size());

		for (CPRuleAssetCategoryRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPRuleAssetCategoryRelSoap[soapModels.size()]);
	}

	public CPRuleAssetCategoryRelSoap() {
	}

	public long getPrimaryKey() {
		return _CPRuleAssetCategoryRelId;
	}

	public void setPrimaryKey(long pk) {
		setCPRuleAssetCategoryRelId(pk);
	}

	public long getCPRuleAssetCategoryRelId() {
		return _CPRuleAssetCategoryRelId;
	}

	public void setCPRuleAssetCategoryRelId(long CPRuleAssetCategoryRelId) {
		_CPRuleAssetCategoryRelId = CPRuleAssetCategoryRelId;
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

	public long getCPRuleId() {
		return _CPRuleId;
	}

	public void setCPRuleId(long CPRuleId) {
		_CPRuleId = CPRuleId;
	}

	public long getAssetCategoryId() {
		return _assetCategoryId;
	}

	public void setAssetCategoryId(long assetCategoryId) {
		_assetCategoryId = assetCategoryId;
	}

	private long _CPRuleAssetCategoryRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPRuleId;
	private long _assetCategoryId;
}