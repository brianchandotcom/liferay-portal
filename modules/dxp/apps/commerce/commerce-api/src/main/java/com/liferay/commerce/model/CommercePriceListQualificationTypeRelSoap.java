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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommercePriceListQualificationTypeRelServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommercePriceListQualificationTypeRelServiceSoap
 * @generated
 */
@ProviderType
public class CommercePriceListQualificationTypeRelSoap implements Serializable {
	public static CommercePriceListQualificationTypeRelSoap toSoapModel(
		CommercePriceListQualificationTypeRel model) {
		CommercePriceListQualificationTypeRelSoap soapModel = new CommercePriceListQualificationTypeRelSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommercePriceListQualificationTypeRelId(model.getCommercePriceListQualificationTypeRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommercePriceListId(model.getCommercePriceListId());
		soapModel.setCommercePriceListQualificationType(model.getCommercePriceListQualificationType());
		soapModel.setOrder(model.getOrder());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CommercePriceListQualificationTypeRelSoap[] toSoapModels(
		CommercePriceListQualificationTypeRel[] models) {
		CommercePriceListQualificationTypeRelSoap[] soapModels = new CommercePriceListQualificationTypeRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceListQualificationTypeRelSoap[][] toSoapModels(
		CommercePriceListQualificationTypeRel[][] models) {
		CommercePriceListQualificationTypeRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommercePriceListQualificationTypeRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommercePriceListQualificationTypeRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceListQualificationTypeRelSoap[] toSoapModels(
		List<CommercePriceListQualificationTypeRel> models) {
		List<CommercePriceListQualificationTypeRelSoap> soapModels = new ArrayList<CommercePriceListQualificationTypeRelSoap>(models.size());

		for (CommercePriceListQualificationTypeRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommercePriceListQualificationTypeRelSoap[soapModels.size()]);
	}

	public CommercePriceListQualificationTypeRelSoap() {
	}

	public long getPrimaryKey() {
		return _commercePriceListQualificationTypeRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommercePriceListQualificationTypeRelId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommercePriceListQualificationTypeRelId() {
		return _commercePriceListQualificationTypeRelId;
	}

	public void setCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId) {
		_commercePriceListQualificationTypeRelId = commercePriceListQualificationTypeRelId;
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

	public String getCommercePriceListQualificationType() {
		return _commercePriceListQualificationType;
	}

	public void setCommercePriceListQualificationType(
		String commercePriceListQualificationType) {
		_commercePriceListQualificationType = commercePriceListQualificationType;
	}

	public int getOrder() {
		return _order;
	}

	public void setOrder(int order) {
		_order = order;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _commercePriceListQualificationTypeRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commercePriceListId;
	private String _commercePriceListQualificationType;
	private int _order;
	private Date _lastPublishDate;
}