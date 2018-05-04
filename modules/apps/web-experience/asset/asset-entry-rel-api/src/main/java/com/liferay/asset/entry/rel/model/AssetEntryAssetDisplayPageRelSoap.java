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
public class AssetEntryAssetDisplayPageRelSoap implements Serializable {
	public static AssetEntryAssetDisplayPageRelSoap toSoapModel(
		AssetEntryAssetDisplayPageRel model) {
		AssetEntryAssetDisplayPageRelSoap soapModel = new AssetEntryAssetDisplayPageRelSoap();

		soapModel.setAssetEntryAssetDisplayPageId(model.getAssetEntryAssetDisplayPageId());
		soapModel.setAssetEntryId(model.getAssetEntryId());
		soapModel.setAssetDisplayPageId(model.getAssetDisplayPageId());

		return soapModel;
	}

	public static AssetEntryAssetDisplayPageRelSoap[] toSoapModels(
		AssetEntryAssetDisplayPageRel[] models) {
		AssetEntryAssetDisplayPageRelSoap[] soapModels = new AssetEntryAssetDisplayPageRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetEntryAssetDisplayPageRelSoap[][] toSoapModels(
		AssetEntryAssetDisplayPageRel[][] models) {
		AssetEntryAssetDisplayPageRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetEntryAssetDisplayPageRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetEntryAssetDisplayPageRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetEntryAssetDisplayPageRelSoap[] toSoapModels(
		List<AssetEntryAssetDisplayPageRel> models) {
		List<AssetEntryAssetDisplayPageRelSoap> soapModels = new ArrayList<AssetEntryAssetDisplayPageRelSoap>(models.size());

		for (AssetEntryAssetDisplayPageRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetEntryAssetDisplayPageRelSoap[soapModels.size()]);
	}

	public AssetEntryAssetDisplayPageRelSoap() {
	}

	public long getPrimaryKey() {
		return _assetEntryAssetDisplayPageId;
	}

	public void setPrimaryKey(long pk) {
		setAssetEntryAssetDisplayPageId(pk);
	}

	public long getAssetEntryAssetDisplayPageId() {
		return _assetEntryAssetDisplayPageId;
	}

	public void setAssetEntryAssetDisplayPageId(
		long assetEntryAssetDisplayPageId) {
		_assetEntryAssetDisplayPageId = assetEntryAssetDisplayPageId;
	}

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public long getAssetDisplayPageId() {
		return _assetDisplayPageId;
	}

	public void setAssetDisplayPageId(long assetDisplayPageId) {
		_assetDisplayPageId = assetDisplayPageId;
	}

	private long _assetEntryAssetDisplayPageId;
	private long _assetEntryId;
	private long _assetDisplayPageId;
}