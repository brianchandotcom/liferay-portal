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
public class AssetListEntryAssetEntryRelSoap implements Serializable {

	public static AssetListEntryAssetEntryRelSoap toSoapModel(
		AssetListEntryAssetEntryRel model) {

		AssetListEntryAssetEntryRelSoap soapModel =
			new AssetListEntryAssetEntryRelSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setAssetListEntryAssetEntryRelId(
			model.getAssetListEntryAssetEntryRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setAssetListEntryId(model.getAssetListEntryId());
		soapModel.setAssetEntryId(model.getAssetEntryId());
		soapModel.setSegmentsEntryId(model.getSegmentsEntryId());
		soapModel.setPosition(model.getPosition());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static AssetListEntryAssetEntryRelSoap[] toSoapModels(
		AssetListEntryAssetEntryRel[] models) {

		AssetListEntryAssetEntryRelSoap[] soapModels =
			new AssetListEntryAssetEntryRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetListEntryAssetEntryRelSoap[][] toSoapModels(
		AssetListEntryAssetEntryRel[][] models) {

		AssetListEntryAssetEntryRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetListEntryAssetEntryRelSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new AssetListEntryAssetEntryRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetListEntryAssetEntryRelSoap[] toSoapModels(
		List<AssetListEntryAssetEntryRel> models) {

		List<AssetListEntryAssetEntryRelSoap> soapModels =
			new ArrayList<AssetListEntryAssetEntryRelSoap>(models.size());

		for (AssetListEntryAssetEntryRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AssetListEntryAssetEntryRelSoap[soapModels.size()]);
	}

	public AssetListEntryAssetEntryRelSoap() {
	}

	public long getPrimaryKey() {
		return _assetListEntryAssetEntryRelId;
	}

	public void setPrimaryKey(long pk) {
		setAssetListEntryAssetEntryRelId(pk);
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

	public long getAssetListEntryAssetEntryRelId() {
		return _assetListEntryAssetEntryRelId;
	}

	public void setAssetListEntryAssetEntryRelId(
		long assetListEntryAssetEntryRelId) {

		_assetListEntryAssetEntryRelId = assetListEntryAssetEntryRelId;
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

	public long getAssetListEntryId() {
		return _assetListEntryId;
	}

	public void setAssetListEntryId(long assetListEntryId) {
		_assetListEntryId = assetListEntryId;
	}

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public long getSegmentsEntryId() {
		return _segmentsEntryId;
	}

	public void setSegmentsEntryId(long segmentsEntryId) {
		_segmentsEntryId = segmentsEntryId;
	}

	public int getPosition() {
		return _position;
	}

	public void setPosition(int position) {
		_position = position;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _assetListEntryAssetEntryRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _assetListEntryId;
	private long _assetEntryId;
	private long _segmentsEntryId;
	private int _position;
	private Date _lastPublishDate;

}