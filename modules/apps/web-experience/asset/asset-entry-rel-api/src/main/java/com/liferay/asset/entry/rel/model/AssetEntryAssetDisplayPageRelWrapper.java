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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link AssetEntryAssetDisplayPageRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetDisplayPageRel
 * @generated
 */
@ProviderType
public class AssetEntryAssetDisplayPageRelWrapper
	implements AssetEntryAssetDisplayPageRel,
		ModelWrapper<AssetEntryAssetDisplayPageRel> {
	public AssetEntryAssetDisplayPageRelWrapper(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel) {
		_assetEntryAssetDisplayPageRel = assetEntryAssetDisplayPageRel;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetEntryAssetDisplayPageRel.class;
	}

	@Override
	public String getModelClassName() {
		return AssetEntryAssetDisplayPageRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("assetEntryAssetDisplayPageId",
			getAssetEntryAssetDisplayPageId());
		attributes.put("assetEntryId", getAssetEntryId());
		attributes.put("assetDisplayPageId", getAssetDisplayPageId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long assetEntryAssetDisplayPageId = (Long)attributes.get(
				"assetEntryAssetDisplayPageId");

		if (assetEntryAssetDisplayPageId != null) {
			setAssetEntryAssetDisplayPageId(assetEntryAssetDisplayPageId);
		}

		Long assetEntryId = (Long)attributes.get("assetEntryId");

		if (assetEntryId != null) {
			setAssetEntryId(assetEntryId);
		}

		Long assetDisplayPageId = (Long)attributes.get("assetDisplayPageId");

		if (assetDisplayPageId != null) {
			setAssetDisplayPageId(assetDisplayPageId);
		}
	}

	@Override
	public Object clone() {
		return new AssetEntryAssetDisplayPageRelWrapper((AssetEntryAssetDisplayPageRel)_assetEntryAssetDisplayPageRel.clone());
	}

	@Override
	public int compareTo(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel) {
		return _assetEntryAssetDisplayPageRel.compareTo(assetEntryAssetDisplayPageRel);
	}

	/**
	* Returns the asset display page ID of this asset entry asset display page rel.
	*
	* @return the asset display page ID of this asset entry asset display page rel
	*/
	@Override
	public long getAssetDisplayPageId() {
		return _assetEntryAssetDisplayPageRel.getAssetDisplayPageId();
	}

	/**
	* Returns the asset entry asset display page ID of this asset entry asset display page rel.
	*
	* @return the asset entry asset display page ID of this asset entry asset display page rel
	*/
	@Override
	public long getAssetEntryAssetDisplayPageId() {
		return _assetEntryAssetDisplayPageRel.getAssetEntryAssetDisplayPageId();
	}

	/**
	* Returns the asset entry ID of this asset entry asset display page rel.
	*
	* @return the asset entry ID of this asset entry asset display page rel
	*/
	@Override
	public long getAssetEntryId() {
		return _assetEntryAssetDisplayPageRel.getAssetEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetEntryAssetDisplayPageRel.getExpandoBridge();
	}

	/**
	* Returns the primary key of this asset entry asset display page rel.
	*
	* @return the primary key of this asset entry asset display page rel
	*/
	@Override
	public long getPrimaryKey() {
		return _assetEntryAssetDisplayPageRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetEntryAssetDisplayPageRel.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _assetEntryAssetDisplayPageRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetEntryAssetDisplayPageRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetEntryAssetDisplayPageRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetEntryAssetDisplayPageRel.isNew();
	}

	@Override
	public void persist() {
		_assetEntryAssetDisplayPageRel.persist();
	}

	/**
	* Sets the asset display page ID of this asset entry asset display page rel.
	*
	* @param assetDisplayPageId the asset display page ID of this asset entry asset display page rel
	*/
	@Override
	public void setAssetDisplayPageId(long assetDisplayPageId) {
		_assetEntryAssetDisplayPageRel.setAssetDisplayPageId(assetDisplayPageId);
	}

	/**
	* Sets the asset entry asset display page ID of this asset entry asset display page rel.
	*
	* @param assetEntryAssetDisplayPageId the asset entry asset display page ID of this asset entry asset display page rel
	*/
	@Override
	public void setAssetEntryAssetDisplayPageId(
		long assetEntryAssetDisplayPageId) {
		_assetEntryAssetDisplayPageRel.setAssetEntryAssetDisplayPageId(assetEntryAssetDisplayPageId);
	}

	/**
	* Sets the asset entry ID of this asset entry asset display page rel.
	*
	* @param assetEntryId the asset entry ID of this asset entry asset display page rel
	*/
	@Override
	public void setAssetEntryId(long assetEntryId) {
		_assetEntryAssetDisplayPageRel.setAssetEntryId(assetEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetEntryAssetDisplayPageRel.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_assetEntryAssetDisplayPageRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetEntryAssetDisplayPageRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetEntryAssetDisplayPageRel.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_assetEntryAssetDisplayPageRel.setNew(n);
	}

	/**
	* Sets the primary key of this asset entry asset display page rel.
	*
	* @param primaryKey the primary key of this asset entry asset display page rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetEntryAssetDisplayPageRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetEntryAssetDisplayPageRel.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetEntryAssetDisplayPageRel> toCacheModel() {
		return _assetEntryAssetDisplayPageRel.toCacheModel();
	}

	@Override
	public AssetEntryAssetDisplayPageRel toEscapedModel() {
		return new AssetEntryAssetDisplayPageRelWrapper(_assetEntryAssetDisplayPageRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _assetEntryAssetDisplayPageRel.toString();
	}

	@Override
	public AssetEntryAssetDisplayPageRel toUnescapedModel() {
		return new AssetEntryAssetDisplayPageRelWrapper(_assetEntryAssetDisplayPageRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _assetEntryAssetDisplayPageRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryAssetDisplayPageRelWrapper)) {
			return false;
		}

		AssetEntryAssetDisplayPageRelWrapper assetEntryAssetDisplayPageRelWrapper =
			(AssetEntryAssetDisplayPageRelWrapper)obj;

		if (Objects.equals(_assetEntryAssetDisplayPageRel,
					assetEntryAssetDisplayPageRelWrapper._assetEntryAssetDisplayPageRel)) {
			return true;
		}

		return false;
	}

	@Override
	public AssetEntryAssetDisplayPageRel getWrappedModel() {
		return _assetEntryAssetDisplayPageRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetEntryAssetDisplayPageRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetEntryAssetDisplayPageRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetEntryAssetDisplayPageRel.resetOriginalValues();
	}

	private final AssetEntryAssetDisplayPageRel _assetEntryAssetDisplayPageRel;
}