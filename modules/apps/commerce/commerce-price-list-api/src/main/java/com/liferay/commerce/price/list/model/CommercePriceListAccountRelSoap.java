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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.price.list.service.http.CommercePriceListAccountRelServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceListAccountRelSoap implements Serializable {

	public static CommercePriceListAccountRelSoap toSoapModel(
		CommercePriceListAccountRel model) {

		CommercePriceListAccountRelSoap soapModel =
			new CommercePriceListAccountRelSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommercePriceListAccountRelId(
			model.getCommercePriceListAccountRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceAccountId(model.getCommerceAccountId());
		soapModel.setCommercePriceListId(model.getCommercePriceListId());
		soapModel.setOrder(model.getOrder());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CommercePriceListAccountRelSoap[] toSoapModels(
		CommercePriceListAccountRel[] models) {

		CommercePriceListAccountRelSoap[] soapModels =
			new CommercePriceListAccountRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceListAccountRelSoap[][] toSoapModels(
		CommercePriceListAccountRel[][] models) {

		CommercePriceListAccountRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommercePriceListAccountRelSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommercePriceListAccountRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommercePriceListAccountRelSoap[] toSoapModels(
		List<CommercePriceListAccountRel> models) {

		List<CommercePriceListAccountRelSoap> soapModels =
			new ArrayList<CommercePriceListAccountRelSoap>(models.size());

		for (CommercePriceListAccountRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommercePriceListAccountRelSoap[soapModels.size()]);
	}

	public CommercePriceListAccountRelSoap() {
	}

	public long getPrimaryKey() {
		return _commercePriceListAccountRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommercePriceListAccountRelId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommercePriceListAccountRelId() {
		return _commercePriceListAccountRelId;
	}

	public void setCommercePriceListAccountRelId(
		long commercePriceListAccountRelId) {

		_commercePriceListAccountRelId = commercePriceListAccountRelId;
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

	public long getCommerceAccountId() {
		return _commerceAccountId;
	}

	public void setCommerceAccountId(long commerceAccountId) {
		_commerceAccountId = commerceAccountId;
	}

	public long getCommercePriceListId() {
		return _commercePriceListId;
	}

	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListId = commercePriceListId;
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
	private long _commercePriceListAccountRelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceAccountId;
	private long _commercePriceListId;
	private int _order;
	private Date _lastPublishDate;

}