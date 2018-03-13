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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceTaxCategoryRelServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceTaxCategoryRelServiceSoap
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryRelSoap implements Serializable {
	public static CommerceTaxCategoryRelSoap toSoapModel(
		CommerceTaxCategoryRel model) {
		CommerceTaxCategoryRelSoap soapModel = new CommerceTaxCategoryRelSoap();

		soapModel.setCommerceTaxCategoryRelId(model.getCommerceTaxCategoryRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceTaxCategoryId(model.getCommerceTaxCategoryId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static CommerceTaxCategoryRelSoap[] toSoapModels(
		CommerceTaxCategoryRel[] models) {
		CommerceTaxCategoryRelSoap[] soapModels = new CommerceTaxCategoryRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceTaxCategoryRelSoap[][] toSoapModels(
		CommerceTaxCategoryRel[][] models) {
		CommerceTaxCategoryRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceTaxCategoryRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceTaxCategoryRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceTaxCategoryRelSoap[] toSoapModels(
		List<CommerceTaxCategoryRel> models) {
		List<CommerceTaxCategoryRelSoap> soapModels = new ArrayList<CommerceTaxCategoryRelSoap>(models.size());

		for (CommerceTaxCategoryRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceTaxCategoryRelSoap[soapModels.size()]);
	}

	public CommerceTaxCategoryRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceTaxCategoryRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceTaxCategoryRelId(pk);
	}

	public long getCommerceTaxCategoryRelId() {
		return _commerceTaxCategoryRelId;
	}

	public void setCommerceTaxCategoryRelId(long commerceTaxCategoryRelId) {
		_commerceTaxCategoryRelId = commerceTaxCategoryRelId;
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

	public long getCommerceTaxCategoryId() {
		return _commerceTaxCategoryId;
	}

	public void setCommerceTaxCategoryId(long commerceTaxCategoryId) {
		_commerceTaxCategoryId = commerceTaxCategoryId;
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

	private long _commerceTaxCategoryRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceTaxCategoryId;
	private long _classNameId;
	private long _classPK;
}