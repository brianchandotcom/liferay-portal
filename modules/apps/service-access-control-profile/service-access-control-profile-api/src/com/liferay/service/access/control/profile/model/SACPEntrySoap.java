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

package com.liferay.service.access.control.profile.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.service.access.control.profile.service.http.SACPEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.service.access.control.profile.service.http.SACPEntryServiceSoap
 * @generated
 */
@ProviderType
public class SACPEntrySoap implements Serializable {
	public static SACPEntrySoap toSoapModel(SACPEntry model) {
		SACPEntrySoap soapModel = new SACPEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setSacpEntryId(model.getSacpEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setAllowedServices(model.getAllowedServices());
		soapModel.setName(model.getName());
		soapModel.setTitle(model.getTitle());

		return soapModel;
	}

	public static SACPEntrySoap[] toSoapModels(SACPEntry[] models) {
		SACPEntrySoap[] soapModels = new SACPEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SACPEntrySoap[][] toSoapModels(SACPEntry[][] models) {
		SACPEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SACPEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new SACPEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SACPEntrySoap[] toSoapModels(List<SACPEntry> models) {
		List<SACPEntrySoap> soapModels = new ArrayList<SACPEntrySoap>(models.size());

		for (SACPEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SACPEntrySoap[soapModels.size()]);
	}

	public SACPEntrySoap() {
	}

	public long getPrimaryKey() {
		return _sacpEntryId;
	}

	public void setPrimaryKey(long pk) {
		setSacpEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getSacpEntryId() {
		return _sacpEntryId;
	}

	public void setSacpEntryId(long sacpEntryId) {
		_sacpEntryId = sacpEntryId;
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

	public String getAllowedServices() {
		return _allowedServices;
	}

	public void setAllowedServices(String allowedServices) {
		_allowedServices = allowedServices;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private String _uuid;
	private long _sacpEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _allowedServices;
	private String _name;
	private String _title;
}