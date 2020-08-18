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

package com.liferay.commerce.inventory.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.inventory.service.http.CommerceInventoryAuditServiceSoap}.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceInventoryAuditSoap implements Serializable {

	public static CommerceInventoryAuditSoap toSoapModel(
		CommerceInventoryAudit model) {

		CommerceInventoryAuditSoap soapModel = new CommerceInventoryAuditSoap();

		soapModel.setCommerceInventoryAuditId(
			model.getCommerceInventoryAuditId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSku(model.getSku());
		soapModel.setLogType(model.getLogType());
		soapModel.setLogTypeSettings(model.getLogTypeSettings());
		soapModel.setQuantity(model.getQuantity());

		return soapModel;
	}

	public static CommerceInventoryAuditSoap[] toSoapModels(
		CommerceInventoryAudit[] models) {

		CommerceInventoryAuditSoap[] soapModels =
			new CommerceInventoryAuditSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceInventoryAuditSoap[][] toSoapModels(
		CommerceInventoryAudit[][] models) {

		CommerceInventoryAuditSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceInventoryAuditSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceInventoryAuditSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceInventoryAuditSoap[] toSoapModels(
		List<CommerceInventoryAudit> models) {

		List<CommerceInventoryAuditSoap> soapModels =
			new ArrayList<CommerceInventoryAuditSoap>(models.size());

		for (CommerceInventoryAudit model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceInventoryAuditSoap[soapModels.size()]);
	}

	public CommerceInventoryAuditSoap() {
	}

	public long getPrimaryKey() {
		return _commerceInventoryAuditId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceInventoryAuditId(pk);
	}

	public long getCommerceInventoryAuditId() {
		return _commerceInventoryAuditId;
	}

	public void setCommerceInventoryAuditId(long commerceInventoryAuditId) {
		_commerceInventoryAuditId = commerceInventoryAuditId;
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

	public String getSku() {
		return _sku;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public String getLogType() {
		return _logType;
	}

	public void setLogType(String logType) {
		_logType = logType;
	}

	public String getLogTypeSettings() {
		return _logTypeSettings;
	}

	public void setLogTypeSettings(String logTypeSettings) {
		_logTypeSettings = logTypeSettings;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	private long _commerceInventoryAuditId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _sku;
	private String _logType;
	private String _logTypeSettings;
	private int _quantity;

}