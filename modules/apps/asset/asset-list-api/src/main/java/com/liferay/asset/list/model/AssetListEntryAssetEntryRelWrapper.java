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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link AssetListEntryAssetEntryRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryAssetEntryRel
 * @generated
 */
@ProviderType
public class AssetListEntryAssetEntryRelWrapper
	implements AssetListEntryAssetEntryRel,
		ModelWrapper<AssetListEntryAssetEntryRel> {
	public AssetListEntryAssetEntryRelWrapper(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {
		_assetListEntryAssetEntryRel = assetListEntryAssetEntryRel;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetListEntryAssetEntryRel.class;
	}

	@Override
	public String getModelClassName() {
		return AssetListEntryAssetEntryRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("assetListEntryAssetEntryRelId",
			getAssetListEntryAssetEntryRelId());
		attributes.put("assetListEntryId", getAssetListEntryId());
		attributes.put("assetEntryId", getAssetEntryId());
		attributes.put("position", getPosition());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long assetListEntryAssetEntryRelId = (Long)attributes.get(
				"assetListEntryAssetEntryRelId");

		if (assetListEntryAssetEntryRelId != null) {
			setAssetListEntryAssetEntryRelId(assetListEntryAssetEntryRelId);
		}

		Long assetListEntryId = (Long)attributes.get("assetListEntryId");

		if (assetListEntryId != null) {
			setAssetListEntryId(assetListEntryId);
		}

		Long assetEntryId = (Long)attributes.get("assetEntryId");

		if (assetEntryId != null) {
			setAssetEntryId(assetEntryId);
		}

		Integer position = (Integer)attributes.get("position");

		if (position != null) {
			setPosition(position);
		}
	}

	@Override
	public Object clone() {
		return new AssetListEntryAssetEntryRelWrapper((AssetListEntryAssetEntryRel)_assetListEntryAssetEntryRel.clone());
	}

	@Override
	public int compareTo(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {
		return _assetListEntryAssetEntryRel.compareTo(assetListEntryAssetEntryRel);
	}

	/**
	* Returns the asset entry ID of this asset list entry asset entry rel.
	*
	* @return the asset entry ID of this asset list entry asset entry rel
	*/
	@Override
	public long getAssetEntryId() {
		return _assetListEntryAssetEntryRel.getAssetEntryId();
	}

	/**
	* Returns the asset list entry asset entry rel ID of this asset list entry asset entry rel.
	*
	* @return the asset list entry asset entry rel ID of this asset list entry asset entry rel
	*/
	@Override
	public long getAssetListEntryAssetEntryRelId() {
		return _assetListEntryAssetEntryRel.getAssetListEntryAssetEntryRelId();
	}

	/**
	* Returns the asset list entry ID of this asset list entry asset entry rel.
	*
	* @return the asset list entry ID of this asset list entry asset entry rel
	*/
	@Override
	public long getAssetListEntryId() {
		return _assetListEntryAssetEntryRel.getAssetListEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetListEntryAssetEntryRel.getExpandoBridge();
	}

	/**
	* Returns the position of this asset list entry asset entry rel.
	*
	* @return the position of this asset list entry asset entry rel
	*/
	@Override
	public int getPosition() {
		return _assetListEntryAssetEntryRel.getPosition();
	}

	/**
	* Returns the primary key of this asset list entry asset entry rel.
	*
	* @return the primary key of this asset list entry asset entry rel
	*/
	@Override
	public long getPrimaryKey() {
		return _assetListEntryAssetEntryRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetListEntryAssetEntryRel.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _assetListEntryAssetEntryRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetListEntryAssetEntryRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetListEntryAssetEntryRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetListEntryAssetEntryRel.isNew();
	}

	@Override
	public void persist() {
		_assetListEntryAssetEntryRel.persist();
	}

	/**
	* Sets the asset entry ID of this asset list entry asset entry rel.
	*
	* @param assetEntryId the asset entry ID of this asset list entry asset entry rel
	*/
	@Override
	public void setAssetEntryId(long assetEntryId) {
		_assetListEntryAssetEntryRel.setAssetEntryId(assetEntryId);
	}

	/**
	* Sets the asset list entry asset entry rel ID of this asset list entry asset entry rel.
	*
	* @param assetListEntryAssetEntryRelId the asset list entry asset entry rel ID of this asset list entry asset entry rel
	*/
	@Override
	public void setAssetListEntryAssetEntryRelId(
		long assetListEntryAssetEntryRelId) {
		_assetListEntryAssetEntryRel.setAssetListEntryAssetEntryRelId(assetListEntryAssetEntryRelId);
	}

	/**
	* Sets the asset list entry ID of this asset list entry asset entry rel.
	*
	* @param assetListEntryId the asset list entry ID of this asset list entry asset entry rel
	*/
	@Override
	public void setAssetListEntryId(long assetListEntryId) {
		_assetListEntryAssetEntryRel.setAssetListEntryId(assetListEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetListEntryAssetEntryRel.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_assetListEntryAssetEntryRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetListEntryAssetEntryRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetListEntryAssetEntryRel.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_assetListEntryAssetEntryRel.setNew(n);
	}

	/**
	* Sets the position of this asset list entry asset entry rel.
	*
	* @param position the position of this asset list entry asset entry rel
	*/
	@Override
	public void setPosition(int position) {
		_assetListEntryAssetEntryRel.setPosition(position);
	}

	/**
	* Sets the primary key of this asset list entry asset entry rel.
	*
	* @param primaryKey the primary key of this asset list entry asset entry rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetListEntryAssetEntryRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetListEntryAssetEntryRel.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetListEntryAssetEntryRel> toCacheModel() {
		return _assetListEntryAssetEntryRel.toCacheModel();
	}

	@Override
	public AssetListEntryAssetEntryRel toEscapedModel() {
		return new AssetListEntryAssetEntryRelWrapper(_assetListEntryAssetEntryRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _assetListEntryAssetEntryRel.toString();
	}

	@Override
	public AssetListEntryAssetEntryRel toUnescapedModel() {
		return new AssetListEntryAssetEntryRelWrapper(_assetListEntryAssetEntryRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _assetListEntryAssetEntryRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetListEntryAssetEntryRelWrapper)) {
			return false;
		}

		AssetListEntryAssetEntryRelWrapper assetListEntryAssetEntryRelWrapper = (AssetListEntryAssetEntryRelWrapper)obj;

		if (Objects.equals(_assetListEntryAssetEntryRel,
					assetListEntryAssetEntryRelWrapper._assetListEntryAssetEntryRel)) {
			return true;
		}

		return false;
	}

	@Override
	public AssetListEntryAssetEntryRel getWrappedModel() {
		return _assetListEntryAssetEntryRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetListEntryAssetEntryRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetListEntryAssetEntryRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetListEntryAssetEntryRel.resetOriginalValues();
	}

	private final AssetListEntryAssetEntryRel _assetListEntryAssetEntryRel;
}