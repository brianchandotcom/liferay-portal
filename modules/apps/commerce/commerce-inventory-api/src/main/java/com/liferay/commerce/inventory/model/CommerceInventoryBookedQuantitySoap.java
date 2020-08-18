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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.inventory.service.http.CommerceInventoryBookedQuantityServiceSoap}.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceInventoryBookedQuantitySoap implements Serializable {

	public static CommerceInventoryBookedQuantitySoap toSoapModel(
		CommerceInventoryBookedQuantity model) {

		CommerceInventoryBookedQuantitySoap soapModel =
			new CommerceInventoryBookedQuantitySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCommerceInventoryBookedQuantityId(
			model.getCommerceInventoryBookedQuantityId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSku(model.getSku());
		soapModel.setQuantity(model.getQuantity());
		soapModel.setExpirationDate(model.getExpirationDate());
		soapModel.setBookedNote(model.getBookedNote());

		return soapModel;
	}

	public static CommerceInventoryBookedQuantitySoap[] toSoapModels(
		CommerceInventoryBookedQuantity[] models) {

		CommerceInventoryBookedQuantitySoap[] soapModels =
			new CommerceInventoryBookedQuantitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceInventoryBookedQuantitySoap[][] toSoapModels(
		CommerceInventoryBookedQuantity[][] models) {

		CommerceInventoryBookedQuantitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceInventoryBookedQuantitySoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceInventoryBookedQuantitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceInventoryBookedQuantitySoap[] toSoapModels(
		List<CommerceInventoryBookedQuantity> models) {

		List<CommerceInventoryBookedQuantitySoap> soapModels =
			new ArrayList<CommerceInventoryBookedQuantitySoap>(models.size());

		for (CommerceInventoryBookedQuantity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceInventoryBookedQuantitySoap[soapModels.size()]);
	}

	public CommerceInventoryBookedQuantitySoap() {
	}

	public long getPrimaryKey() {
		return _commerceInventoryBookedQuantityId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceInventoryBookedQuantityId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCommerceInventoryBookedQuantityId() {
		return _commerceInventoryBookedQuantityId;
	}

	public void setCommerceInventoryBookedQuantityId(
		long commerceInventoryBookedQuantityId) {

		_commerceInventoryBookedQuantityId = commerceInventoryBookedQuantityId;
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

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public String getBookedNote() {
		return _bookedNote;
	}

	public void setBookedNote(String bookedNote) {
		_bookedNote = bookedNote;
	}

	private long _mvccVersion;
	private long _commerceInventoryBookedQuantityId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _sku;
	private int _quantity;
	private Date _expirationDate;
	private String _bookedNote;

}