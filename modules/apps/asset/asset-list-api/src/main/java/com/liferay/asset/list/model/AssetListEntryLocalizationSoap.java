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
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AssetListEntryLocalizationSoap implements Serializable {
	public static AssetListEntryLocalizationSoap toSoapModel(
		AssetListEntryLocalization model) {
		AssetListEntryLocalizationSoap soapModel = new AssetListEntryLocalizationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setAssetListEntryLocalizationId(model.getAssetListEntryLocalizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setAssetListEntryId(model.getAssetListEntryId());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setGroupId(model.getGroupId());

		return soapModel;
	}

	public static AssetListEntryLocalizationSoap[] toSoapModels(
		AssetListEntryLocalization[] models) {
		AssetListEntryLocalizationSoap[] soapModels = new AssetListEntryLocalizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetListEntryLocalizationSoap[][] toSoapModels(
		AssetListEntryLocalization[][] models) {
		AssetListEntryLocalizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetListEntryLocalizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetListEntryLocalizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetListEntryLocalizationSoap[] toSoapModels(
		List<AssetListEntryLocalization> models) {
		List<AssetListEntryLocalizationSoap> soapModels = new ArrayList<AssetListEntryLocalizationSoap>(models.size());

		for (AssetListEntryLocalization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetListEntryLocalizationSoap[soapModels.size()]);
	}

	public AssetListEntryLocalizationSoap() {
	}

	public long getPrimaryKey() {
		return _assetListEntryLocalizationId;
	}

	public void setPrimaryKey(long pk) {
		setAssetListEntryLocalizationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getAssetListEntryLocalizationId() {
		return _assetListEntryLocalizationId;
	}

	public void setAssetListEntryLocalizationId(
		long assetListEntryLocalizationId) {
		_assetListEntryLocalizationId = assetListEntryLocalizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getAssetListEntryId() {
		return _assetListEntryId;
	}

	public void setAssetListEntryId(long assetListEntryId) {
		_assetListEntryId = assetListEntryId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	private long _mvccVersion;
	private long _assetListEntryLocalizationId;
	private long _companyId;
	private long _assetListEntryId;
	private String _languageId;
	private String _title;
	private String _description;
	private long _groupId;
}