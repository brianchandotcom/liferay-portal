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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link AssetDisplayPageEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageEntry
 * @generated
 */
@ProviderType
public class AssetDisplayPageEntryWrapper implements AssetDisplayPageEntry,
	ModelWrapper<AssetDisplayPageEntry> {
	public AssetDisplayPageEntryWrapper(
		AssetDisplayPageEntry assetDisplayPageEntry) {
		_assetDisplayPageEntry = assetDisplayPageEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetDisplayPageEntry.class;
	}

	@Override
	public String getModelClassName() {
		return AssetDisplayPageEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("assetDisplayPageEntryId", getAssetDisplayPageEntryId());
		attributes.put("assetEntryId", getAssetEntryId());
		attributes.put("layoutId", getLayoutId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long assetDisplayPageEntryId = (Long)attributes.get(
				"assetDisplayPageEntryId");

		if (assetDisplayPageEntryId != null) {
			setAssetDisplayPageEntryId(assetDisplayPageEntryId);
		}

		Long assetEntryId = (Long)attributes.get("assetEntryId");

		if (assetEntryId != null) {
			setAssetEntryId(assetEntryId);
		}

		Long layoutId = (Long)attributes.get("layoutId");

		if (layoutId != null) {
			setLayoutId(layoutId);
		}
	}

	@Override
	public Object clone() {
		return new AssetDisplayPageEntryWrapper((AssetDisplayPageEntry)_assetDisplayPageEntry.clone());
	}

	@Override
	public int compareTo(AssetDisplayPageEntry assetDisplayPageEntry) {
		return _assetDisplayPageEntry.compareTo(assetDisplayPageEntry);
	}

	/**
	* Returns the asset display page entry ID of this asset display page entry.
	*
	* @return the asset display page entry ID of this asset display page entry
	*/
	@Override
	public long getAssetDisplayPageEntryId() {
		return _assetDisplayPageEntry.getAssetDisplayPageEntryId();
	}

	/**
	* Returns the asset entry ID of this asset display page entry.
	*
	* @return the asset entry ID of this asset display page entry
	*/
	@Override
	public long getAssetEntryId() {
		return _assetDisplayPageEntry.getAssetEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetDisplayPageEntry.getExpandoBridge();
	}

	/**
	* Returns the layout ID of this asset display page entry.
	*
	* @return the layout ID of this asset display page entry
	*/
	@Override
	public long getLayoutId() {
		return _assetDisplayPageEntry.getLayoutId();
	}

	/**
	* Returns the primary key of this asset display page entry.
	*
	* @return the primary key of this asset display page entry
	*/
	@Override
	public long getPrimaryKey() {
		return _assetDisplayPageEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetDisplayPageEntry.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _assetDisplayPageEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetDisplayPageEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetDisplayPageEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetDisplayPageEntry.isNew();
	}

	@Override
	public void persist() {
		_assetDisplayPageEntry.persist();
	}

	/**
	* Sets the asset display page entry ID of this asset display page entry.
	*
	* @param assetDisplayPageEntryId the asset display page entry ID of this asset display page entry
	*/
	@Override
	public void setAssetDisplayPageEntryId(long assetDisplayPageEntryId) {
		_assetDisplayPageEntry.setAssetDisplayPageEntryId(assetDisplayPageEntryId);
	}

	/**
	* Sets the asset entry ID of this asset display page entry.
	*
	* @param assetEntryId the asset entry ID of this asset display page entry
	*/
	@Override
	public void setAssetEntryId(long assetEntryId) {
		_assetDisplayPageEntry.setAssetEntryId(assetEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetDisplayPageEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_assetDisplayPageEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetDisplayPageEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetDisplayPageEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the layout ID of this asset display page entry.
	*
	* @param layoutId the layout ID of this asset display page entry
	*/
	@Override
	public void setLayoutId(long layoutId) {
		_assetDisplayPageEntry.setLayoutId(layoutId);
	}

	@Override
	public void setNew(boolean n) {
		_assetDisplayPageEntry.setNew(n);
	}

	/**
	* Sets the primary key of this asset display page entry.
	*
	* @param primaryKey the primary key of this asset display page entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetDisplayPageEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetDisplayPageEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetDisplayPageEntry> toCacheModel() {
		return _assetDisplayPageEntry.toCacheModel();
	}

	@Override
	public AssetDisplayPageEntry toEscapedModel() {
		return new AssetDisplayPageEntryWrapper(_assetDisplayPageEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _assetDisplayPageEntry.toString();
	}

	@Override
	public AssetDisplayPageEntry toUnescapedModel() {
		return new AssetDisplayPageEntryWrapper(_assetDisplayPageEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _assetDisplayPageEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetDisplayPageEntryWrapper)) {
			return false;
		}

		AssetDisplayPageEntryWrapper assetDisplayPageEntryWrapper = (AssetDisplayPageEntryWrapper)obj;

		if (Objects.equals(_assetDisplayPageEntry,
					assetDisplayPageEntryWrapper._assetDisplayPageEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public AssetDisplayPageEntry getWrappedModel() {
		return _assetDisplayPageEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetDisplayPageEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetDisplayPageEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetDisplayPageEntry.resetOriginalValues();
	}

	private final AssetDisplayPageEntry _assetDisplayPageEntry;
}