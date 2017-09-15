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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CAvailabilityRangeEntryServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CAvailabilityRangeEntryServiceSoap
 * @generated
 */
@ProviderType
public class CAvailabilityRangeEntrySoap implements Serializable {
	public static CAvailabilityRangeEntrySoap toSoapModel(
		CAvailabilityRangeEntry model) {
		CAvailabilityRangeEntrySoap soapModel = new CAvailabilityRangeEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCAvailabilityRangeEntryId(model.getCAvailabilityRangeEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setCommerceAvailabilityRangeId(model.getCommerceAvailabilityRangeId());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CAvailabilityRangeEntrySoap[] toSoapModels(
		CAvailabilityRangeEntry[] models) {
		CAvailabilityRangeEntrySoap[] soapModels = new CAvailabilityRangeEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CAvailabilityRangeEntrySoap[][] toSoapModels(
		CAvailabilityRangeEntry[][] models) {
		CAvailabilityRangeEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CAvailabilityRangeEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CAvailabilityRangeEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CAvailabilityRangeEntrySoap[] toSoapModels(
		List<CAvailabilityRangeEntry> models) {
		List<CAvailabilityRangeEntrySoap> soapModels = new ArrayList<CAvailabilityRangeEntrySoap>(models.size());

		for (CAvailabilityRangeEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CAvailabilityRangeEntrySoap[soapModels.size()]);
	}

	public CAvailabilityRangeEntrySoap() {
	}

	public long getPrimaryKey() {
		return _CAvailabilityRangeEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCAvailabilityRangeEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCAvailabilityRangeEntryId() {
		return _CAvailabilityRangeEntryId;
	}

	public void setCAvailabilityRangeEntryId(long CAvailabilityRangeEntryId) {
		_CAvailabilityRangeEntryId = CAvailabilityRangeEntryId;
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

	public long getCommerceAvailabilityRangeId() {
		return _commerceAvailabilityRangeId;
	}

	public void setCommerceAvailabilityRangeId(long commerceAvailabilityRangeId) {
		_commerceAvailabilityRangeId = commerceAvailabilityRangeId;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _CAvailabilityRangeEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private long _commerceAvailabilityRangeId;
	private Date _lastPublishDate;
}