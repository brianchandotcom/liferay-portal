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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CPDefinitionInventoryServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CPDefinitionInventoryServiceSoap
 * @generated
 */
@ProviderType
public class CPDefinitionInventorySoap implements Serializable {
	public static CPDefinitionInventorySoap toSoapModel(
		CPDefinitionInventory model) {
		CPDefinitionInventorySoap soapModel = new CPDefinitionInventorySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPDefinitionInventoryId(model.getCPDefinitionInventoryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setCPDefinitionInventoryEngine(model.getCPDefinitionInventoryEngine());
		soapModel.setLowStockActivity(model.getLowStockActivity());
		soapModel.setDisplayAvailability(model.getDisplayAvailability());
		soapModel.setDisplayStockQuantity(model.getDisplayStockQuantity());
		soapModel.setMinStockQuantity(model.getMinStockQuantity());
		soapModel.setBackOrders(model.getBackOrders());
		soapModel.setMinOrderQuantity(model.getMinOrderQuantity());
		soapModel.setMaxOrderQuantity(model.getMaxOrderQuantity());
		soapModel.setAllowedOrderQuantities(model.getAllowedOrderQuantities());
		soapModel.setMultipleOrderQuantity(model.getMultipleOrderQuantity());

		return soapModel;
	}

	public static CPDefinitionInventorySoap[] toSoapModels(
		CPDefinitionInventory[] models) {
		CPDefinitionInventorySoap[] soapModels = new CPDefinitionInventorySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionInventorySoap[][] toSoapModels(
		CPDefinitionInventory[][] models) {
		CPDefinitionInventorySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPDefinitionInventorySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionInventorySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionInventorySoap[] toSoapModels(
		List<CPDefinitionInventory> models) {
		List<CPDefinitionInventorySoap> soapModels = new ArrayList<CPDefinitionInventorySoap>(models.size());

		for (CPDefinitionInventory model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPDefinitionInventorySoap[soapModels.size()]);
	}

	public CPDefinitionInventorySoap() {
	}

	public long getPrimaryKey() {
		return _CPDefinitionInventoryId;
	}

	public void setPrimaryKey(long pk) {
		setCPDefinitionInventoryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPDefinitionInventoryId() {
		return _CPDefinitionInventoryId;
	}

	public void setCPDefinitionInventoryId(long CPDefinitionInventoryId) {
		_CPDefinitionInventoryId = CPDefinitionInventoryId;
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

	public String getCPDefinitionInventoryEngine() {
		return _CPDefinitionInventoryEngine;
	}

	public void setCPDefinitionInventoryEngine(
		String CPDefinitionInventoryEngine) {
		_CPDefinitionInventoryEngine = CPDefinitionInventoryEngine;
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

	public int getMinOrderQuantity() {
		return _minOrderQuantity;
	}

	public void setMinOrderQuantity(int minOrderQuantity) {
		_minOrderQuantity = minOrderQuantity;
	}

	public int getMaxOrderQuantity() {
		return _maxOrderQuantity;
	}

	public void setMaxOrderQuantity(int maxOrderQuantity) {
		_maxOrderQuantity = maxOrderQuantity;
	}

	public String getAllowedOrderQuantities() {
		return _allowedOrderQuantities;
	}

	public void setAllowedOrderQuantities(String allowedOrderQuantities) {
		_allowedOrderQuantities = allowedOrderQuantities;
	}

	public int getMultipleOrderQuantity() {
		return _multipleOrderQuantity;
	}

	public void setMultipleOrderQuantity(int multipleOrderQuantity) {
		_multipleOrderQuantity = multipleOrderQuantity;
	}

	private String _uuid;
	private long _CPDefinitionInventoryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private String _CPDefinitionInventoryEngine;
	private String _lowStockActivity;
	private boolean _displayAvailability;
	private boolean _displayStockQuantity;
	private int _minStockQuantity;
	private boolean _backOrders;
	private int _minOrderQuantity;
	private int _maxOrderQuantity;
	private String _allowedOrderQuantities;
	private int _multipleOrderQuantity;
}