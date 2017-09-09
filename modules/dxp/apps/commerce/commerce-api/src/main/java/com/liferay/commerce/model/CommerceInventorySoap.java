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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceInventoryServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceInventoryServiceSoap
 * @generated
 */
@ProviderType
public class CommerceInventorySoap implements Serializable {
	public static CommerceInventorySoap toSoapModel(CommerceInventory model) {
		CommerceInventorySoap soapModel = new CommerceInventorySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceInventoryId(model.getCommerceInventoryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setCommerceInventoryEngine(model.getCommerceInventoryEngine());
		soapModel.setLowStockActivity(model.getLowStockActivity());
		soapModel.setDisplayAvailability(model.getDisplayAvailability());
		soapModel.setDisplayStockQuantity(model.getDisplayStockQuantity());
		soapModel.setMinStockQuantity(model.getMinStockQuantity());
		soapModel.setBackOrders(model.getBackOrders());
		soapModel.setMinCartQuantity(model.getMinCartQuantity());
		soapModel.setMaxCartQuantity(model.getMaxCartQuantity());
		soapModel.setAllowedCartQuantities(model.getAllowedCartQuantities());
		soapModel.setMultipleCartQuantity(model.getMultipleCartQuantity());

		return soapModel;
	}

	public static CommerceInventorySoap[] toSoapModels(
		CommerceInventory[] models) {
		CommerceInventorySoap[] soapModels = new CommerceInventorySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceInventorySoap[][] toSoapModels(
		CommerceInventory[][] models) {
		CommerceInventorySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceInventorySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceInventorySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceInventorySoap[] toSoapModels(
		List<CommerceInventory> models) {
		List<CommerceInventorySoap> soapModels = new ArrayList<CommerceInventorySoap>(models.size());

		for (CommerceInventory model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceInventorySoap[soapModels.size()]);
	}

	public CommerceInventorySoap() {
	}

	public long getPrimaryKey() {
		return _commerceInventoryId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceInventoryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceInventoryId() {
		return _commerceInventoryId;
	}

	public void setCommerceInventoryId(long commerceInventoryId) {
		_commerceInventoryId = commerceInventoryId;
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

	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	public void setCPDefinitionId(long CPDefinitionId) {
		_CPDefinitionId = CPDefinitionId;
	}

	public String getCommerceInventoryEngine() {
		return _commerceInventoryEngine;
	}

	public void setCommerceInventoryEngine(String commerceInventoryEngine) {
		_commerceInventoryEngine = commerceInventoryEngine;
	}

	public String getLowStockActivity() {
		return _lowStockActivity;
	}

	public void setLowStockActivity(String lowStockActivity) {
		_lowStockActivity = lowStockActivity;
	}

	public boolean getDisplayAvailability() {
		return _displayAvailability;
	}

	public boolean isDisplayAvailability() {
		return _displayAvailability;
	}

	public void setDisplayAvailability(boolean displayAvailability) {
		_displayAvailability = displayAvailability;
	}

	public boolean getDisplayStockQuantity() {
		return _displayStockQuantity;
	}

	public boolean isDisplayStockQuantity() {
		return _displayStockQuantity;
	}

	public void setDisplayStockQuantity(boolean displayStockQuantity) {
		_displayStockQuantity = displayStockQuantity;
	}

	public int getMinStockQuantity() {
		return _minStockQuantity;
	}

	public void setMinStockQuantity(int minStockQuantity) {
		_minStockQuantity = minStockQuantity;
	}

	public boolean getBackOrders() {
		return _backOrders;
	}

	public boolean isBackOrders() {
		return _backOrders;
	}

	public void setBackOrders(boolean backOrders) {
		_backOrders = backOrders;
	}

	public int getMinCartQuantity() {
		return _minCartQuantity;
	}

	public void setMinCartQuantity(int minCartQuantity) {
		_minCartQuantity = minCartQuantity;
	}

	public int getMaxCartQuantity() {
		return _maxCartQuantity;
	}

	public void setMaxCartQuantity(int maxCartQuantity) {
		_maxCartQuantity = maxCartQuantity;
	}

	public String getAllowedCartQuantities() {
		return _allowedCartQuantities;
	}

	public void setAllowedCartQuantities(String allowedCartQuantities) {
		_allowedCartQuantities = allowedCartQuantities;
	}

	public int getMultipleCartQuantity() {
		return _multipleCartQuantity;
	}

	public void setMultipleCartQuantity(int multipleCartQuantity) {
		_multipleCartQuantity = multipleCartQuantity;
	}

	private String _uuid;
	private long _commerceInventoryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private String _commerceInventoryEngine;
	private String _lowStockActivity;
	private boolean _displayAvailability;
	private boolean _displayStockQuantity;
	private int _minStockQuantity;
	private boolean _backOrders;
	private int _minCartQuantity;
	private int _maxCartQuantity;
	private String _allowedCartQuantities;
	private int _multipleCartQuantity;
}