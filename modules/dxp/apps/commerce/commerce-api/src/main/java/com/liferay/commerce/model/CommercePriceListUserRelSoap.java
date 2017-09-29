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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommercePriceListUserRelServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommercePriceListUserRelServiceSoap
 * @generated
 */
@ProviderType
public class CommercePriceListUserRelSoap implements Serializable {
	public static CommercePriceListUserRelSoap toSoapModel(
		CommercePriceListUserRel model) {
		CommercePriceListUserRelSoap soapModel = new CommercePriceListUserRelSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommercePriceListUserRelId(model.getCommercePriceListUserRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommercePriceListId(model.getCommercePriceListId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CommercePriceListUserRelSoap[] toSoapModels(
		CommercePriceListUserRel[] models) {
		CommercePriceListUserRelSoap[] soapModels = new CommercePriceListUserRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceListUserRelSoap[][] toSoapModels(
		CommercePriceListUserRel[][] models) {
		CommercePriceListUserRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommercePriceListUserRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommercePriceListUserRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceListUserRelSoap[] toSoapModels(
		List<CommercePriceListUserRel> models) {
		List<CommercePriceListUserRelSoap> soapModels = new ArrayList<CommercePriceListUserRelSoap>(models.size());

		for (CommercePriceListUserRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommercePriceListUserRelSoap[soapModels.size()]);
	}

	public CommercePriceListUserRelSoap() {
	}

	public long getPrimaryKey() {
		return _commercePriceListUserRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommercePriceListUserRelId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommercePriceListUserRelId() {
		return _commercePriceListUserRelId;
	}

	public void setCommercePriceListUserRelId(long commercePriceListUserRelId) {
		_commercePriceListUserRelId = commercePriceListUserRelId;
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

	public long getCommercePriceListId() {
		return _commercePriceListId;
	}

	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListId = commercePriceListId;
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

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _commercePriceListUserRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commercePriceListId;
	private long _classNameId;
	private long _classPK;
	private Date _lastPublishDate;
}