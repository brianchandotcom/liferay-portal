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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceAvailabilityRangeServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceAvailabilityRangeServiceSoap
 * @generated
 */
@ProviderType
public class CommerceAvailabilityRangeSoap implements Serializable {
	public static CommerceAvailabilityRangeSoap toSoapModel(
		CommerceAvailabilityRange model) {
		CommerceAvailabilityRangeSoap soapModel = new CommerceAvailabilityRangeSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceAvailabilityRangeId(model.getCommerceAvailabilityRangeId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setPriority(model.getPriority());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CommerceAvailabilityRangeSoap[] toSoapModels(
		CommerceAvailabilityRange[] models) {
		CommerceAvailabilityRangeSoap[] soapModels = new CommerceAvailabilityRangeSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceAvailabilityRangeSoap[][] toSoapModels(
		CommerceAvailabilityRange[][] models) {
		CommerceAvailabilityRangeSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceAvailabilityRangeSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceAvailabilityRangeSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceAvailabilityRangeSoap[] toSoapModels(
		List<CommerceAvailabilityRange> models) {
		List<CommerceAvailabilityRangeSoap> soapModels = new ArrayList<CommerceAvailabilityRangeSoap>(models.size());

		for (CommerceAvailabilityRange model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceAvailabilityRangeSoap[soapModels.size()]);
	}

	public CommerceAvailabilityRangeSoap() {
	}

	public long getPrimaryKey() {
		return _commerceAvailabilityRangeId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceAvailabilityRangeId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceAvailabilityRangeId() {
		return _commerceAvailabilityRangeId;
	}

	public void setCommerceAvailabilityRangeId(long commerceAvailabilityRangeId) {
		_commerceAvailabilityRangeId = commerceAvailabilityRangeId;
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

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _commerceAvailabilityRangeId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private double _priority;
	private Date _lastPublishDate;
}