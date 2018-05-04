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

package com.liferay.asset.entry.rel.model;

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
public class AssetEntryDisplayPageRelSoap implements Serializable {
	public static AssetEntryDisplayPageRelSoap toSoapModel(
		AssetEntryDisplayPageRel model) {
		AssetEntryDisplayPageRelSoap soapModel = new AssetEntryDisplayPageRelSoap();

		soapModel.setAssetEntryDisplayPageRelId(model.getAssetEntryDisplayPageRelId());
		soapModel.setAssetEntryId(model.getAssetEntryId());
		soapModel.setDisplayPageId(model.getDisplayPageId());

		return soapModel;
	}

	public static AssetEntryDisplayPageRelSoap[] toSoapModels(
		AssetEntryDisplayPageRel[] models) {
		AssetEntryDisplayPageRelSoap[] soapModels = new AssetEntryDisplayPageRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetEntryDisplayPageRelSoap[][] toSoapModels(
		AssetEntryDisplayPageRel[][] models) {
		AssetEntryDisplayPageRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetEntryDisplayPageRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetEntryDisplayPageRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetEntryDisplayPageRelSoap[] toSoapModels(
		List<AssetEntryDisplayPageRel> models) {
		List<AssetEntryDisplayPageRelSoap> soapModels = new ArrayList<AssetEntryDisplayPageRelSoap>(models.size());

		for (AssetEntryDisplayPageRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetEntryDisplayPageRelSoap[soapModels.size()]);
	}

	public AssetEntryDisplayPageRelSoap() {
	}

	public long getPrimaryKey() {
		return _assetEntryDisplayPageRelId;
	}

	public void setPrimaryKey(long pk) {
		setAssetEntryDisplayPageRelId(pk);
	}

	public long getAssetEntryDisplayPageRelId() {
		return _assetEntryDisplayPageRelId;
	}

	public void setAssetEntryDisplayPageRelId(long assetEntryDisplayPageRelId) {
		_assetEntryDisplayPageRelId = assetEntryDisplayPageRelId;
	}

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public long getDisplayPageId() {
		return _displayPageId;
	}

	public void setDisplayPageId(long displayPageId) {
		_displayPageId = displayPageId;
	}

	private long _assetEntryDisplayPageRelId;
	private long _assetEntryId;
	private long _displayPageId;
}