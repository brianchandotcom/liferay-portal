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
 * This class is a wrapper for {@link AssetEntryDisplayPageRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryDisplayPageRel
 * @generated
 */
@ProviderType
public class AssetEntryDisplayPageRelWrapper implements AssetEntryDisplayPageRel,
	ModelWrapper<AssetEntryDisplayPageRel> {
	public AssetEntryDisplayPageRelWrapper(
		AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		_assetEntryDisplayPageRel = assetEntryDisplayPageRel;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetEntryDisplayPageRel.class;
	}

	@Override
	public String getModelClassName() {
		return AssetEntryDisplayPageRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("assetEntryDisplayPageRelId",
			getAssetEntryDisplayPageRelId());
		attributes.put("assetEntryId", getAssetEntryId());
		attributes.put("displayPageId", getDisplayPageId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long assetEntryDisplayPageRelId = (Long)attributes.get(
				"assetEntryDisplayPageRelId");

		if (assetEntryDisplayPageRelId != null) {
			setAssetEntryDisplayPageRelId(assetEntryDisplayPageRelId);
		}

		Long assetEntryId = (Long)attributes.get("assetEntryId");

		if (assetEntryId != null) {
			setAssetEntryId(assetEntryId);
		}

		Long displayPageId = (Long)attributes.get("displayPageId");

		if (displayPageId != null) {
			setDisplayPageId(displayPageId);
		}
	}

	@Override
	public Object clone() {
		return new AssetEntryDisplayPageRelWrapper((AssetEntryDisplayPageRel)_assetEntryDisplayPageRel.clone());
	}

	@Override
	public int compareTo(AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		return _assetEntryDisplayPageRel.compareTo(assetEntryDisplayPageRel);
	}

	/**
	* Returns the asset entry display page rel ID of this asset entry display page rel.
	*
	* @return the asset entry display page rel ID of this asset entry display page rel
	*/
	@Override
	public long getAssetEntryDisplayPageRelId() {
		return _assetEntryDisplayPageRel.getAssetEntryDisplayPageRelId();
	}

	/**
	* Returns the asset entry ID of this asset entry display page rel.
	*
	* @return the asset entry ID of this asset entry display page rel
	*/
	@Override
	public long getAssetEntryId() {
		return _assetEntryDisplayPageRel.getAssetEntryId();
	}

	/**
	* Returns the display page ID of this asset entry display page rel.
	*
	* @return the display page ID of this asset entry display page rel
	*/
	@Override
	public long getDisplayPageId() {
		return _assetEntryDisplayPageRel.getDisplayPageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetEntryDisplayPageRel.getExpandoBridge();
	}

	/**
	* Returns the primary key of this asset entry display page rel.
	*
	* @return the primary key of this asset entry display page rel
	*/
	@Override
	public long getPrimaryKey() {
		return _assetEntryDisplayPageRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetEntryDisplayPageRel.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _assetEntryDisplayPageRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetEntryDisplayPageRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetEntryDisplayPageRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetEntryDisplayPageRel.isNew();
	}

	@Override
	public void persist() {
		_assetEntryDisplayPageRel.persist();
	}

	/**
	* Sets the asset entry display page rel ID of this asset entry display page rel.
	*
	* @param assetEntryDisplayPageRelId the asset entry display page rel ID of this asset entry display page rel
	*/
	@Override
	public void setAssetEntryDisplayPageRelId(long assetEntryDisplayPageRelId) {
		_assetEntryDisplayPageRel.setAssetEntryDisplayPageRelId(assetEntryDisplayPageRelId);
	}

	/**
	* Sets the asset entry ID of this asset entry display page rel.
	*
	* @param assetEntryId the asset entry ID of this asset entry display page rel
	*/
	@Override
	public void setAssetEntryId(long assetEntryId) {
		_assetEntryDisplayPageRel.setAssetEntryId(assetEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetEntryDisplayPageRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the display page ID of this asset entry display page rel.
	*
	* @param displayPageId the display page ID of this asset entry display page rel
	*/
	@Override
	public void setDisplayPageId(long displayPageId) {
		_assetEntryDisplayPageRel.setDisplayPageId(displayPageId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_assetEntryDisplayPageRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetEntryDisplayPageRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetEntryDisplayPageRel.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_assetEntryDisplayPageRel.setNew(n);
	}

	/**
	* Sets the primary key of this asset entry display page rel.
	*
	* @param primaryKey the primary key of this asset entry display page rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetEntryDisplayPageRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetEntryDisplayPageRel.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetEntryDisplayPageRel> toCacheModel() {
		return _assetEntryDisplayPageRel.toCacheModel();
	}

	@Override
	public AssetEntryDisplayPageRel toEscapedModel() {
		return new AssetEntryDisplayPageRelWrapper(_assetEntryDisplayPageRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _assetEntryDisplayPageRel.toString();
	}

	@Override
	public AssetEntryDisplayPageRel toUnescapedModel() {
		return new AssetEntryDisplayPageRelWrapper(_assetEntryDisplayPageRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _assetEntryDisplayPageRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryDisplayPageRelWrapper)) {
			return false;
		}

		AssetEntryDisplayPageRelWrapper assetEntryDisplayPageRelWrapper = (AssetEntryDisplayPageRelWrapper)obj;

		if (Objects.equals(_assetEntryDisplayPageRel,
					assetEntryDisplayPageRelWrapper._assetEntryDisplayPageRel)) {
			return true;
		}

		return false;
	}

	@Override
	public AssetEntryDisplayPageRel getWrappedModel() {
		return _assetEntryDisplayPageRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetEntryDisplayPageRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetEntryDisplayPageRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetEntryDisplayPageRel.resetOriginalValues();
	}

	private final AssetEntryDisplayPageRel _assetEntryDisplayPageRel;
}