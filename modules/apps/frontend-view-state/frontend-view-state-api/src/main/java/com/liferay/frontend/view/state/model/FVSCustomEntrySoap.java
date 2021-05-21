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

package com.liferay.frontend.view.state.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class FVSCustomEntrySoap implements Serializable {

	public static FVSCustomEntrySoap toSoapModel(FVSCustomEntry model) {
		FVSCustomEntrySoap soapModel = new FVSCustomEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setFvsCustomEntryId(model.getFvsCustomEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setEntityId(model.getEntityId());
		soapModel.setFvsEntryId(model.getFvsEntryId());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static FVSCustomEntrySoap[] toSoapModels(FVSCustomEntry[] models) {
		FVSCustomEntrySoap[] soapModels = new FVSCustomEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FVSCustomEntrySoap[][] toSoapModels(
		FVSCustomEntry[][] models) {

		FVSCustomEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new FVSCustomEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new FVSCustomEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FVSCustomEntrySoap[] toSoapModels(
		List<FVSCustomEntry> models) {

		List<FVSCustomEntrySoap> soapModels = new ArrayList<FVSCustomEntrySoap>(
			models.size());

		for (FVSCustomEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FVSCustomEntrySoap[soapModels.size()]);
	}

	public FVSCustomEntrySoap() {
	}

	public long getPrimaryKey() {
		return _fvsCustomEntryId;
	}

	public void setPrimaryKey(long pk) {
		setFvsCustomEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getFvsCustomEntryId() {
		return _fvsCustomEntryId;
	}

	public void setFvsCustomEntryId(long fvsCustomEntryId) {
		_fvsCustomEntryId = fvsCustomEntryId;
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

	public String getEntityId() {
		return _entityId;
	}

	public void setEntityId(String entityId) {
		_entityId = entityId;
	}

	public long getFvsEntryId() {
		return _fvsEntryId;
	}

	public void setFvsEntryId(long fvsEntryId) {
		_fvsEntryId = fvsEntryId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _fvsCustomEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _entityId;
	private long _fvsEntryId;
	private String _name;

}