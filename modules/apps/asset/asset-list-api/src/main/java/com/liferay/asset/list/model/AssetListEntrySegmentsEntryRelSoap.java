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
public class AssetListEntrySegmentsEntryRelSoap implements Serializable {

	public static AssetListEntrySegmentsEntryRelSoap toSoapModel(
		AssetListEntrySegmentsEntryRel model) {

		AssetListEntrySegmentsEntryRelSoap soapModel =
			new AssetListEntrySegmentsEntryRelSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setAssetListEntrySegmentsEntryRelId(
			model.getAssetListEntrySegmentsEntryRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setAssetListEntryId(model.getAssetListEntryId());
		soapModel.setSegmentsEntryId(model.getSegmentsEntryId());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static AssetListEntrySegmentsEntryRelSoap[] toSoapModels(
		AssetListEntrySegmentsEntryRel[] models) {

		AssetListEntrySegmentsEntryRelSoap[] soapModels =
			new AssetListEntrySegmentsEntryRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetListEntrySegmentsEntryRelSoap[][] toSoapModels(
		AssetListEntrySegmentsEntryRel[][] models) {

		AssetListEntrySegmentsEntryRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetListEntrySegmentsEntryRelSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new AssetListEntrySegmentsEntryRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetListEntrySegmentsEntryRelSoap[] toSoapModels(
		List<AssetListEntrySegmentsEntryRel> models) {

		List<AssetListEntrySegmentsEntryRelSoap> soapModels =
			new ArrayList<AssetListEntrySegmentsEntryRelSoap>(models.size());

		for (AssetListEntrySegmentsEntryRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AssetListEntrySegmentsEntryRelSoap[soapModels.size()]);
	}

	public AssetListEntrySegmentsEntryRelSoap() {
	}

	public long getPrimaryKey() {
		return _assetListEntrySegmentsEntryRelId;
	}

	public void setPrimaryKey(long pk) {
		setAssetListEntrySegmentsEntryRelId(pk);
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

	public long getAssetListEntrySegmentsEntryRelId() {
		return _assetListEntrySegmentsEntryRelId;
	}

	public void setAssetListEntrySegmentsEntryRelId(
		long assetListEntrySegmentsEntryRelId) {

		_assetListEntrySegmentsEntryRelId = assetListEntrySegmentsEntryRelId;
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

	public long getSegmentsEntryId() {
		return _segmentsEntryId;
	}

	public void setSegmentsEntryId(long segmentsEntryId) {
		_segmentsEntryId = segmentsEntryId;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _assetListEntrySegmentsEntryRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _assetListEntryId;
	private long _segmentsEntryId;
	private String _typeSettings;
	private Date _lastPublishDate;

}