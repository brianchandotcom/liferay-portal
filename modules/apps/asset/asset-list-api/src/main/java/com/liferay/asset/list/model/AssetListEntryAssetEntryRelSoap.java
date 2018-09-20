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
public class AssetListEntryAssetEntryRelSoap implements Serializable {
	public static AssetListEntryAssetEntryRelSoap toSoapModel(
		AssetListEntryAssetEntryRel model) {
		AssetListEntryAssetEntryRelSoap soapModel = new AssetListEntryAssetEntryRelSoap();

		soapModel.setAssetListEntryAssetEntryRelId(model.getAssetListEntryAssetEntryRelId());
		soapModel.setAssetListEntryId(model.getAssetListEntryId());
		soapModel.setAssetEntryId(model.getAssetEntryId());
		soapModel.setPosition(model.getPosition());

		return soapModel;
	}

	public static AssetListEntryAssetEntryRelSoap[] toSoapModels(
		AssetListEntryAssetEntryRel[] models) {
		AssetListEntryAssetEntryRelSoap[] soapModels = new AssetListEntryAssetEntryRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetListEntryAssetEntryRelSoap[][] toSoapModels(
		AssetListEntryAssetEntryRel[][] models) {
		AssetListEntryAssetEntryRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetListEntryAssetEntryRelSoap[models.length][models[0].length];
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
		List<AssetListEntryAssetEntryRelSoap> soapModels = new ArrayList<AssetListEntryAssetEntryRelSoap>(models.size());

		for (AssetListEntryAssetEntryRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetListEntryAssetEntryRelSoap[soapModels.size()]);
	}

	public AssetListEntryAssetEntryRelSoap() {
	}

	public long getPrimaryKey() {
		return _assetListEntryAssetEntryRelId;
	}

	public void setPrimaryKey(long pk) {
		setAssetListEntryAssetEntryRelId(pk);
	}

	public long getAssetListEntryAssetEntryRelId() {
		return _assetListEntryAssetEntryRelId;
	}

	public void setAssetListEntryAssetEntryRelId(
		long assetListEntryAssetEntryRelId) {
		_assetListEntryAssetEntryRelId = assetListEntryAssetEntryRelId;
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

	public int getPosition() {
		return _position;
	}

	public void setPosition(int position) {
		_position = position;
	}

	private long _assetListEntryAssetEntryRelId;
	private long _assetListEntryId;
	private long _assetEntryId;
	private int _position;
}