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

package com.liferay.portal.security.service.access.policy.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.security.service.access.policy.service.http.SAPEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SAPEntrySoap implements Serializable {

	public static SAPEntrySoap toSoapModel(SAPEntry model) {
		SAPEntrySoap soapModel = new SAPEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setSapEntryId(model.getSapEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setAllowedServiceSignatures(
			model.getAllowedServiceSignatures());
		soapModel.setDefaultSAPEntry(model.isDefaultSAPEntry());
		soapModel.setEnabled(model.isEnabled());
		soapModel.setName(model.getName());
		soapModel.setTitle(model.getTitle());

		return soapModel;
	}

	public static SAPEntrySoap[] toSoapModels(SAPEntry[] models) {
		SAPEntrySoap[] soapModels = new SAPEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SAPEntrySoap[][] toSoapModels(SAPEntry[][] models) {
		SAPEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SAPEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new SAPEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SAPEntrySoap[] toSoapModels(List<SAPEntry> models) {
		List<SAPEntrySoap> soapModels = new ArrayList<SAPEntrySoap>(
			models.size());

		for (SAPEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SAPEntrySoap[soapModels.size()]);
	}

	public SAPEntrySoap() {
	}

	public long getPrimaryKey() {
		return _sapEntryId;
	}

	public void setPrimaryKey(long pk) {
		setSapEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getSapEntryId() {
		return _sapEntryId;
	}

	public void setSapEntryId(long sapEntryId) {
		_sapEntryId = sapEntryId;
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

	public String getAllowedServiceSignatures() {
		return _allowedServiceSignatures;
	}

	public void setAllowedServiceSignatures(String allowedServiceSignatures) {
		_allowedServiceSignatures = allowedServiceSignatures;
	}

	public boolean getDefaultSAPEntry() {
		return _defaultSAPEntry;
	}

	public boolean isDefaultSAPEntry() {
		return _defaultSAPEntry;
	}

	public void setDefaultSAPEntry(boolean defaultSAPEntry) {
		_defaultSAPEntry = defaultSAPEntry;
	}

	public boolean getEnabled() {
		return _enabled;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
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
	private long _sapEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _allowedServiceSignatures;
	private boolean _defaultSAPEntry;
	private boolean _enabled;
	private String _name;
	private String _title;

}