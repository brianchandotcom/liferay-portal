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

package com.liferay.asset.list.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.asset.list.service.http.AssetListEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.list.service.http.AssetListEntryServiceSoap
 * @generated
 */
@ProviderType
public class AssetListEntrySoap implements Serializable {
	public static AssetListEntrySoap toSoapModel(AssetListEntry model) {
		AssetListEntrySoap soapModel = new AssetListEntrySoap();

		soapModel.setAssetListEntryId(model.getAssetListEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setTitle(model.getTitle());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static AssetListEntrySoap[] toSoapModels(AssetListEntry[] models) {
		AssetListEntrySoap[] soapModels = new AssetListEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetListEntrySoap[][] toSoapModels(AssetListEntry[][] models) {
		AssetListEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetListEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetListEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetListEntrySoap[] toSoapModels(List<AssetListEntry> models) {
		List<AssetListEntrySoap> soapModels = new ArrayList<AssetListEntrySoap>(models.size());

		for (AssetListEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetListEntrySoap[soapModels.size()]);
	}

	public AssetListEntrySoap() {
	}

	public long getPrimaryKey() {
		return _assetListEntryId;
	}

	public void setPrimaryKey(long pk) {
		setAssetListEntryId(pk);
	}

	public long getAssetListEntryId() {
		return _assetListEntryId;
	}

	public void setAssetListEntryId(long assetListEntryId) {
		_assetListEntryId = assetListEntryId;
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

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private long _assetListEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _typeSettings;
	private String _title;
	private int _type;
}