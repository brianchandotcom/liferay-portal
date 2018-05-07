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

package com.liferay.asset.display.page.model;

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
public class AssetDisplayPageSoap implements Serializable {
	public static AssetDisplayPageSoap toSoapModel(AssetDisplayPage model) {
		AssetDisplayPageSoap soapModel = new AssetDisplayPageSoap();

		soapModel.setAssetDisplayPageId(model.getAssetDisplayPageId());
		soapModel.setAssetEntryId(model.getAssetEntryId());
		soapModel.setLayoutId(model.getLayoutId());

		return soapModel;
	}

	public static AssetDisplayPageSoap[] toSoapModels(AssetDisplayPage[] models) {
		AssetDisplayPageSoap[] soapModels = new AssetDisplayPageSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetDisplayPageSoap[][] toSoapModels(
		AssetDisplayPage[][] models) {
		AssetDisplayPageSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetDisplayPageSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetDisplayPageSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetDisplayPageSoap[] toSoapModels(
		List<AssetDisplayPage> models) {
		List<AssetDisplayPageSoap> soapModels = new ArrayList<AssetDisplayPageSoap>(models.size());

		for (AssetDisplayPage model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetDisplayPageSoap[soapModels.size()]);
	}

	public AssetDisplayPageSoap() {
	}

	public long getPrimaryKey() {
		return _assetDisplayPageId;
	}

	public void setPrimaryKey(long pk) {
		setAssetDisplayPageId(pk);
	}

	public long getAssetDisplayPageId() {
		return _assetDisplayPageId;
	}

	public void setAssetDisplayPageId(long assetDisplayPageId) {
		_assetDisplayPageId = assetDisplayPageId;
	}

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public long getLayoutId() {
		return _layoutId;
	}

	public void setLayoutId(long layoutId) {
		_layoutId = layoutId;
	}

	private long _assetDisplayPageId;
	private long _assetEntryId;
	private long _layoutId;
}