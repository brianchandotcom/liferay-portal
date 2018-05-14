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
public class AssetDisplayPageEntrySoap implements Serializable {
	public static AssetDisplayPageEntrySoap toSoapModel(
		AssetDisplayPageEntry model) {
		AssetDisplayPageEntrySoap soapModel = new AssetDisplayPageEntrySoap();

		soapModel.setAssetDisplayPageEntryId(model.getAssetDisplayPageEntryId());
		soapModel.setAssetEntryId(model.getAssetEntryId());
		soapModel.setLayoutPageTemplateEntryId(model.getLayoutPageTemplateEntryId());

		return soapModel;
	}

	public static AssetDisplayPageEntrySoap[] toSoapModels(
		AssetDisplayPageEntry[] models) {
		AssetDisplayPageEntrySoap[] soapModels = new AssetDisplayPageEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetDisplayPageEntrySoap[][] toSoapModels(
		AssetDisplayPageEntry[][] models) {
		AssetDisplayPageEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetDisplayPageEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetDisplayPageEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetDisplayPageEntrySoap[] toSoapModels(
		List<AssetDisplayPageEntry> models) {
		List<AssetDisplayPageEntrySoap> soapModels = new ArrayList<AssetDisplayPageEntrySoap>(models.size());

		for (AssetDisplayPageEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetDisplayPageEntrySoap[soapModels.size()]);
	}

	public AssetDisplayPageEntrySoap() {
	}

	public long getPrimaryKey() {
		return _assetDisplayPageEntryId;
	}

	public void setPrimaryKey(long pk) {
		setAssetDisplayPageEntryId(pk);
	}

	public long getAssetDisplayPageEntryId() {
		return _assetDisplayPageEntryId;
	}

	public void setAssetDisplayPageEntryId(long assetDisplayPageEntryId) {
		_assetDisplayPageEntryId = assetDisplayPageEntryId;
	}

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public long getLayoutPageTemplateEntryId() {
		return _layoutPageTemplateEntryId;
	}

	public void setLayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {
		_layoutPageTemplateEntryId = layoutPageTemplateEntryId;
	}

	private long _assetDisplayPageEntryId;
	private long _assetEntryId;
	private long _layoutPageTemplateEntryId;
}