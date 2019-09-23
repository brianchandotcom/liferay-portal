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

package com.liferay.asset.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetEntryUsageSoap implements Serializable {

	public static AssetEntryUsageSoap toSoapModel(AssetEntryUsage model) {
		AssetEntryUsageSoap soapModel = new AssetEntryUsageSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setAssetEntryUsageId(model.getAssetEntryUsageId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setAssetEntryId(model.getAssetEntryId());
		soapModel.setContainerType(model.getContainerType());
		soapModel.setContainerKey(model.getContainerKey());
		soapModel.setPlid(model.getPlid());
		soapModel.setType(model.getType());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static AssetEntryUsageSoap[] toSoapModels(AssetEntryUsage[] models) {
		AssetEntryUsageSoap[] soapModels =
			new AssetEntryUsageSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetEntryUsageSoap[][] toSoapModels(
		AssetEntryUsage[][] models) {

		AssetEntryUsageSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AssetEntryUsageSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetEntryUsageSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetEntryUsageSoap[] toSoapModels(
		List<AssetEntryUsage> models) {

		List<AssetEntryUsageSoap> soapModels =
			new ArrayList<AssetEntryUsageSoap>(models.size());

		for (AssetEntryUsage model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetEntryUsageSoap[soapModels.size()]);
	}

	public AssetEntryUsageSoap() {
	}

	public long getPrimaryKey() {
		return _assetEntryUsageId;
	}

	public void setPrimaryKey(long pk) {
		setAssetEntryUsageId(pk);
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

	public long getAssetEntryUsageId() {
		return _assetEntryUsageId;
	}

	public void setAssetEntryUsageId(long assetEntryUsageId) {
		_assetEntryUsageId = assetEntryUsageId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
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

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public long getContainerType() {
		return _containerType;
	}

	public void setContainerType(long containerType) {
		_containerType = containerType;
	}

	public String getContainerKey() {
		return _containerKey;
	}

	public void setContainerKey(String containerKey) {
		_containerKey = containerKey;
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _assetEntryUsageId;
	private long _groupId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _assetEntryId;
	private long _containerType;
	private String _containerKey;
	private long _plid;
	private int _type;
	private Date _lastPublishDate;

}