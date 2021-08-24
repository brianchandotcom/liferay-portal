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

package com.liferay.commerce.price.list.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.price.list.service.http.CommercePriceListOrderTypeRelServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePriceListOrderTypeRelSoap implements Serializable {

	public static CommercePriceListOrderTypeRelSoap toSoapModel(
		CommercePriceListOrderTypeRel model) {

		CommercePriceListOrderTypeRelSoap soapModel =
			new CommercePriceListOrderTypeRelSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommercePriceListOrderTypeRelId(
			model.getCommercePriceListOrderTypeRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommercePriceListId(model.getCommercePriceListId());
		soapModel.setCommerceOrderTypeId(model.getCommerceOrderTypeId());
		soapModel.setPriority(model.getPriority());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CommercePriceListOrderTypeRelSoap[] toSoapModels(
		CommercePriceListOrderTypeRel[] models) {

		CommercePriceListOrderTypeRelSoap[] soapModels =
			new CommercePriceListOrderTypeRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceListOrderTypeRelSoap[][] toSoapModels(
		CommercePriceListOrderTypeRel[][] models) {

		CommercePriceListOrderTypeRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommercePriceListOrderTypeRelSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommercePriceListOrderTypeRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceListOrderTypeRelSoap[] toSoapModels(
		List<CommercePriceListOrderTypeRel> models) {

		List<CommercePriceListOrderTypeRelSoap> soapModels =
			new ArrayList<CommercePriceListOrderTypeRelSoap>(models.size());

		for (CommercePriceListOrderTypeRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommercePriceListOrderTypeRelSoap[soapModels.size()]);
	}

	public CommercePriceListOrderTypeRelSoap() {
	}

	public long getPrimaryKey() {
		return _commercePriceListOrderTypeRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommercePriceListOrderTypeRelId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommercePriceListOrderTypeRelId() {
		return _commercePriceListOrderTypeRelId;
	}

	public void setCommercePriceListOrderTypeRelId(
		long commercePriceListOrderTypeRelId) {

		_commercePriceListOrderTypeRelId = commercePriceListOrderTypeRelId;
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

	public long getCommerceOrderTypeId() {
		return _commerceOrderTypeId;
	}

	public void setCommerceOrderTypeId(long commerceOrderTypeId) {
		_commerceOrderTypeId = commerceOrderTypeId;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _commercePriceListOrderTypeRelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commercePriceListId;
	private long _commerceOrderTypeId;
	private int _priority;
	private Date _lastPublishDate;

}