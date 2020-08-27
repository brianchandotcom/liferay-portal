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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.inventory.service.http.CommerceInventoryWarehouseItemServiceSoap}.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceInventoryWarehouseItemSoap implements Serializable {

	public static CommerceInventoryWarehouseItemSoap toSoapModel(
		CommerceInventoryWarehouseItem model) {

		CommerceInventoryWarehouseItemSoap soapModel =
			new CommerceInventoryWarehouseItemSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setCommerceInventoryWarehouseItemId(
			model.getCommerceInventoryWarehouseItemId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceInventoryWarehouseId(
			model.getCommerceInventoryWarehouseId());
		soapModel.setSku(model.getSku());
		soapModel.setQuantity(model.getQuantity());
		soapModel.setReservedQuantity(model.getReservedQuantity());

		return soapModel;
	}

	public static CommerceInventoryWarehouseItemSoap[] toSoapModels(
		CommerceInventoryWarehouseItem[] models) {

		CommerceInventoryWarehouseItemSoap[] soapModels =
			new CommerceInventoryWarehouseItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceInventoryWarehouseItemSoap[][] toSoapModels(
		CommerceInventoryWarehouseItem[][] models) {

		CommerceInventoryWarehouseItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceInventoryWarehouseItemSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceInventoryWarehouseItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceInventoryWarehouseItemSoap[] toSoapModels(
		List<CommerceInventoryWarehouseItem> models) {

		List<CommerceInventoryWarehouseItemSoap> soapModels =
			new ArrayList<CommerceInventoryWarehouseItemSoap>(models.size());

		for (CommerceInventoryWarehouseItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceInventoryWarehouseItemSoap[soapModels.size()]);
	}

	public CommerceInventoryWarehouseItemSoap() {
	}

	public long getPrimaryKey() {
		return _commerceInventoryWarehouseItemId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceInventoryWarehouseItemId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getCommerceInventoryWarehouseItemId() {
		return _commerceInventoryWarehouseItemId;
	}

	public void setCommerceInventoryWarehouseItemId(
		long commerceInventoryWarehouseItemId) {

		_commerceInventoryWarehouseItemId = commerceInventoryWarehouseItemId;
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

	public long getCommerceInventoryWarehouseId() {
		return _commerceInventoryWarehouseId;
	}

	public void setCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		_commerceInventoryWarehouseId = commerceInventoryWarehouseId;
	}

	public String getSku() {
		return _sku;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public int getReservedQuantity() {
		return _reservedQuantity;
	}

	public void setReservedQuantity(int reservedQuantity) {
		_reservedQuantity = reservedQuantity;
	}

	private long _mvccVersion;
	private String _externalReferenceCode;
	private long _commerceInventoryWarehouseItemId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceInventoryWarehouseId;
	private String _sku;
	private int _quantity;
	private int _reservedQuantity;

}