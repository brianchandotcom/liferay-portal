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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.discount.service.http.CommerceDiscountOrderTypeRelServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceDiscountOrderTypeRelSoap implements Serializable {

	public static CommerceDiscountOrderTypeRelSoap toSoapModel(
		CommerceDiscountOrderTypeRel model) {

		CommerceDiscountOrderTypeRelSoap soapModel =
			new CommerceDiscountOrderTypeRelSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceDiscountOrderTypeRelId(
			model.getCommerceDiscountOrderTypeRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceDiscountId(model.getCommerceDiscountId());
		soapModel.setCommerceOrderTypeId(model.getCommerceOrderTypeId());
		soapModel.setPriority(model.getPriority());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CommerceDiscountOrderTypeRelSoap[] toSoapModels(
		CommerceDiscountOrderTypeRel[] models) {

		CommerceDiscountOrderTypeRelSoap[] soapModels =
			new CommerceDiscountOrderTypeRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceDiscountOrderTypeRelSoap[][] toSoapModels(
		CommerceDiscountOrderTypeRel[][] models) {

		CommerceDiscountOrderTypeRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceDiscountOrderTypeRelSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceDiscountOrderTypeRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceDiscountOrderTypeRelSoap[] toSoapModels(
		List<CommerceDiscountOrderTypeRel> models) {

		List<CommerceDiscountOrderTypeRelSoap> soapModels =
			new ArrayList<CommerceDiscountOrderTypeRelSoap>(models.size());

		for (CommerceDiscountOrderTypeRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceDiscountOrderTypeRelSoap[soapModels.size()]);
	}

	public CommerceDiscountOrderTypeRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceDiscountOrderTypeRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceDiscountOrderTypeRelId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceDiscountOrderTypeRelId() {
		return _commerceDiscountOrderTypeRelId;
	}

	public void setCommerceDiscountOrderTypeRelId(
		long commerceDiscountOrderTypeRelId) {

		_commerceDiscountOrderTypeRelId = commerceDiscountOrderTypeRelId;
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
	private long _commerceDiscountOrderTypeRelId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceDiscountId;
	private long _commerceOrderTypeId;
	private int _priority;
	private Date _lastPublishDate;

}