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

package com.liferay.commerce.product.type.virtual.order.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.type.virtual.order.service.http.CommerceVirtualOrderItemServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceVirtualOrderItemSoap implements Serializable {

	public static CommerceVirtualOrderItemSoap toSoapModel(
		CommerceVirtualOrderItem model) {

		CommerceVirtualOrderItemSoap soapModel =
			new CommerceVirtualOrderItemSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceVirtualOrderItemId(
			model.getCommerceVirtualOrderItemId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceOrderItemId(model.getCommerceOrderItemId());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setUrl(model.getUrl());
		soapModel.setActivationStatus(model.getActivationStatus());
		soapModel.setDuration(model.getDuration());
		soapModel.setUsages(model.getUsages());
		soapModel.setMaxUsages(model.getMaxUsages());
		soapModel.setActive(model.isActive());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setEndDate(model.getEndDate());

		return soapModel;
	}

	public static CommerceVirtualOrderItemSoap[] toSoapModels(
		CommerceVirtualOrderItem[] models) {

		CommerceVirtualOrderItemSoap[] soapModels =
			new CommerceVirtualOrderItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceVirtualOrderItemSoap[][] toSoapModels(
		CommerceVirtualOrderItem[][] models) {

		CommerceVirtualOrderItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceVirtualOrderItemSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceVirtualOrderItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceVirtualOrderItemSoap[] toSoapModels(
		List<CommerceVirtualOrderItem> models) {

		List<CommerceVirtualOrderItemSoap> soapModels =
			new ArrayList<CommerceVirtualOrderItemSoap>(models.size());

		for (CommerceVirtualOrderItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceVirtualOrderItemSoap[soapModels.size()]);
	}

	public CommerceVirtualOrderItemSoap() {
	}

	public long getPrimaryKey() {
		return _commerceVirtualOrderItemId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceVirtualOrderItemId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceVirtualOrderItemId() {
		return _commerceVirtualOrderItemId;
	}

	public void setCommerceVirtualOrderItemId(long commerceVirtualOrderItemId) {
		_commerceVirtualOrderItemId = commerceVirtualOrderItemId;
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

	public long getCommerceOrderItemId() {
		return _commerceOrderItemId;
	}

	public void setCommerceOrderItemId(long commerceOrderItemId) {
		_commerceOrderItemId = commerceOrderItemId;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public int getActivationStatus() {
		return _activationStatus;
	}

	public void setActivationStatus(int activationStatus) {
		_activationStatus = activationStatus;
	}

	public long getDuration() {
		return _duration;
	}

	public void setDuration(long duration) {
		_duration = duration;
	}

	public int getUsages() {
		return _usages;
	}

	public void setUsages(int usages) {
		_usages = usages;
	}

	public int getMaxUsages() {
		return _maxUsages;
	}

	public void setMaxUsages(int maxUsages) {
		_maxUsages = maxUsages;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	private String _uuid;
	private long _commerceVirtualOrderItemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceOrderItemId;
	private long _fileEntryId;
	private String _url;
	private int _activationStatus;
	private long _duration;
	private int _usages;
	private int _maxUsages;
	private boolean _active;
	private Date _startDate;
	private Date _endDate;

}