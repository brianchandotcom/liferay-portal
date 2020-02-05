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
public class AssetListEntryUsageSoap implements Serializable {

	public static AssetListEntryUsageSoap toSoapModel(
		AssetListEntryUsage model) {

		AssetListEntryUsageSoap soapModel = new AssetListEntryUsageSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setUuid(model.getUuid());
		soapModel.setAssetListEntryUsageId(model.getAssetListEntryUsageId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setAssetListEntryId(model.getAssetListEntryId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setPortletId(model.getPortletId());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static AssetListEntryUsageSoap[] toSoapModels(
		AssetListEntryUsage[] models) {

		AssetListEntryUsageSoap[] soapModels =
			new AssetListEntryUsageSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetListEntryUsageSoap[][] toSoapModels(
		AssetListEntryUsage[][] models) {

		AssetListEntryUsageSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AssetListEntryUsageSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetListEntryUsageSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetListEntryUsageSoap[] toSoapModels(
		List<AssetListEntryUsage> models) {

		List<AssetListEntryUsageSoap> soapModels =
			new ArrayList<AssetListEntryUsageSoap>(models.size());

		for (AssetListEntryUsage model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AssetListEntryUsageSoap[soapModels.size()]);
	}

	public AssetListEntryUsageSoap() {
	}

	public long getPrimaryKey() {
		return _assetListEntryUsageId;
	}

	public void setPrimaryKey(long pk) {
		setAssetListEntryUsageId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getAssetListEntryUsageId() {
		return _assetListEntryUsageId;
	}

	public void setAssetListEntryUsageId(long assetListEntryUsageId) {
		_assetListEntryUsageId = assetListEntryUsageId;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getPortletId() {
		return _portletId;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private long _assetListEntryUsageId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _assetListEntryId;
	private long _classNameId;
	private long _classPK;
	private String _portletId;
	private Date _lastPublishDate;

}