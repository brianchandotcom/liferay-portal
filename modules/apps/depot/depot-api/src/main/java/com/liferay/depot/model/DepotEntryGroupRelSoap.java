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

package com.liferay.depot.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.depot.service.http.DepotEntryGroupRelServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DepotEntryGroupRelSoap implements Serializable {

	public static DepotEntryGroupRelSoap toSoapModel(DepotEntryGroupRel model) {
		DepotEntryGroupRelSoap soapModel = new DepotEntryGroupRelSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setDepotEntryGroupRelId(model.getDepotEntryGroupRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setDdmStructuresAvailable(model.isDdmStructuresAvailable());
		soapModel.setDepotEntryId(model.getDepotEntryId());
		soapModel.setSearchable(model.isSearchable());
		soapModel.setToGroupId(model.getToGroupId());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static DepotEntryGroupRelSoap[] toSoapModels(
		DepotEntryGroupRel[] models) {

		DepotEntryGroupRelSoap[] soapModels =
			new DepotEntryGroupRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DepotEntryGroupRelSoap[][] toSoapModels(
		DepotEntryGroupRel[][] models) {

		DepotEntryGroupRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DepotEntryGroupRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DepotEntryGroupRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DepotEntryGroupRelSoap[] toSoapModels(
		List<DepotEntryGroupRel> models) {

		List<DepotEntryGroupRelSoap> soapModels =
			new ArrayList<DepotEntryGroupRelSoap>(models.size());

		for (DepotEntryGroupRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DepotEntryGroupRelSoap[soapModels.size()]);
	}

	public DepotEntryGroupRelSoap() {
	}

	public long getPrimaryKey() {
		return _depotEntryGroupRelId;
	}

	public void setPrimaryKey(long pk) {
		setDepotEntryGroupRelId(pk);
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

	public long getDepotEntryGroupRelId() {
		return _depotEntryGroupRelId;
	}

	public void setDepotEntryGroupRelId(long depotEntryGroupRelId) {
		_depotEntryGroupRelId = depotEntryGroupRelId;
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

	public boolean getDdmStructuresAvailable() {
		return _ddmStructuresAvailable;
	}

	public boolean isDdmStructuresAvailable() {
		return _ddmStructuresAvailable;
	}

	public void setDdmStructuresAvailable(boolean ddmStructuresAvailable) {
		_ddmStructuresAvailable = ddmStructuresAvailable;
	}

	public long getDepotEntryId() {
		return _depotEntryId;
	}

	public void setDepotEntryId(long depotEntryId) {
		_depotEntryId = depotEntryId;
	}

	public boolean getSearchable() {
		return _searchable;
	}

	public boolean isSearchable() {
		return _searchable;
	}

	public void setSearchable(boolean searchable) {
		_searchable = searchable;
	}

	public long getToGroupId() {
		return _toGroupId;
	}

	public void setToGroupId(long toGroupId) {
		_toGroupId = toGroupId;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _depotEntryGroupRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _ddmStructuresAvailable;
	private long _depotEntryId;
	private boolean _searchable;
	private long _toGroupId;
	private Date _lastPublishDate;

}