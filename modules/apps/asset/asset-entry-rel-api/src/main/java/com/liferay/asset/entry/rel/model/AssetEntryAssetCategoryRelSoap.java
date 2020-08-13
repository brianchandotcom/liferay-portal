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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AssetEntryAssetCategoryRelSoap implements Serializable {

	public static AssetEntryAssetCategoryRelSoap toSoapModel(
		AssetEntryAssetCategoryRel model) {

		AssetEntryAssetCategoryRelSoap soapModel =
			new AssetEntryAssetCategoryRelSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setAssetEntryAssetCategoryRelId(
			model.getAssetEntryAssetCategoryRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setAssetEntryId(model.getAssetEntryId());
		soapModel.setAssetCategoryId(model.getAssetCategoryId());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static AssetEntryAssetCategoryRelSoap[] toSoapModels(
		AssetEntryAssetCategoryRel[] models) {

		AssetEntryAssetCategoryRelSoap[] soapModels =
			new AssetEntryAssetCategoryRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetEntryAssetCategoryRelSoap[][] toSoapModels(
		AssetEntryAssetCategoryRel[][] models) {

		AssetEntryAssetCategoryRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AssetEntryAssetCategoryRelSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new AssetEntryAssetCategoryRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetEntryAssetCategoryRelSoap[] toSoapModels(
		List<AssetEntryAssetCategoryRel> models) {

		List<AssetEntryAssetCategoryRelSoap> soapModels =
			new ArrayList<AssetEntryAssetCategoryRelSoap>(models.size());

		for (AssetEntryAssetCategoryRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AssetEntryAssetCategoryRelSoap[soapModels.size()]);
	}

	public AssetEntryAssetCategoryRelSoap() {
	}

	public long getPrimaryKey() {
		return _assetEntryAssetCategoryRelId;
	}

	public void setPrimaryKey(long pk) {
		setAssetEntryAssetCategoryRelId(pk);
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

	public long getAssetEntryAssetCategoryRelId() {
		return _assetEntryAssetCategoryRelId;
	}

	public void setAssetEntryAssetCategoryRelId(
		long assetEntryAssetCategoryRelId) {

		_assetEntryAssetCategoryRelId = assetEntryAssetCategoryRelId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public long getAssetCategoryId() {
		return _assetCategoryId;
	}

	public void setAssetCategoryId(long assetCategoryId) {
		_assetCategoryId = assetCategoryId;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _assetEntryAssetCategoryRelId;
	private long _companyId;
	private long _assetEntryId;
	private long _assetCategoryId;
	private int _priority;

}