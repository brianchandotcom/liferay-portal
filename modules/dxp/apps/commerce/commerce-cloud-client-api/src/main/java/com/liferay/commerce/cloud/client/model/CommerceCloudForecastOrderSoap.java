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

package com.liferay.commerce.cloud.client.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Andrea Di Giorgi
 * @generated
 */
@ProviderType
public class CommerceCloudForecastOrderSoap implements Serializable {
	public static CommerceCloudForecastOrderSoap toSoapModel(
		CommerceCloudForecastOrder model) {
		CommerceCloudForecastOrderSoap soapModel = new CommerceCloudForecastOrderSoap();

		soapModel.setCommerceCloudForecastOrderId(model.getCommerceCloudForecastOrderId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setCommerceOrderId(model.getCommerceOrderId());
		soapModel.setSyncDate(model.getSyncDate());

		return soapModel;
	}

	public static CommerceCloudForecastOrderSoap[] toSoapModels(
		CommerceCloudForecastOrder[] models) {
		CommerceCloudForecastOrderSoap[] soapModels = new CommerceCloudForecastOrderSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceCloudForecastOrderSoap[][] toSoapModels(
		CommerceCloudForecastOrder[][] models) {
		CommerceCloudForecastOrderSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceCloudForecastOrderSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceCloudForecastOrderSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceCloudForecastOrderSoap[] toSoapModels(
		List<CommerceCloudForecastOrder> models) {
		List<CommerceCloudForecastOrderSoap> soapModels = new ArrayList<CommerceCloudForecastOrderSoap>(models.size());

		for (CommerceCloudForecastOrder model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceCloudForecastOrderSoap[soapModels.size()]);
	}

	public CommerceCloudForecastOrderSoap() {
	}

	public long getPrimaryKey() {
		return _commerceCloudForecastOrderId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceCloudForecastOrderId(pk);
	}

	public long getCommerceCloudForecastOrderId() {
		return _commerceCloudForecastOrderId;
	}

	public void setCommerceCloudForecastOrderId(
		long commerceCloudForecastOrderId) {
		_commerceCloudForecastOrderId = commerceCloudForecastOrderId;
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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderId = commerceOrderId;
	}

	public Date getSyncDate() {
		return _syncDate;
	}

	public void setSyncDate(Date syncDate) {
		_syncDate = syncDate;
	}

	private long _commerceCloudForecastOrderId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private long _commerceOrderId;
	private Date _syncDate;
}