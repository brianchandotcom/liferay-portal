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

package com.liferay.portal.rolling.restart.sample.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.rolling.restart.sample.service.http.RollingRestartSampleEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class RollingRestartSampleEntrySoap implements Serializable {

	public static RollingRestartSampleEntrySoap toSoapModel(
		RollingRestartSampleEntry model) {

		RollingRestartSampleEntrySoap soapModel =
			new RollingRestartSampleEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setEntryId(model.getEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static RollingRestartSampleEntrySoap[] toSoapModels(
		RollingRestartSampleEntry[] models) {

		RollingRestartSampleEntrySoap[] soapModels =
			new RollingRestartSampleEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RollingRestartSampleEntrySoap[][] toSoapModels(
		RollingRestartSampleEntry[][] models) {

		RollingRestartSampleEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new RollingRestartSampleEntrySoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new RollingRestartSampleEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RollingRestartSampleEntrySoap[] toSoapModels(
		List<RollingRestartSampleEntry> models) {

		List<RollingRestartSampleEntrySoap> soapModels =
			new ArrayList<RollingRestartSampleEntrySoap>(models.size());

		for (RollingRestartSampleEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new RollingRestartSampleEntrySoap[soapModels.size()]);
	}

	public RollingRestartSampleEntrySoap() {
	}

	public long getPrimaryKey() {
		return _entryId;
	}

	public void setPrimaryKey(long pk) {
		setEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _mvccVersion;
	private long _entryId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private int _status;

}