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
 * This class is a wrapper for {@link AssetEntryAssetCategoryRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetCategoryRel
 * @generated
 */
@ProviderType
public class AssetEntryAssetCategoryRelWrapper
	implements AssetEntryAssetCategoryRel,
		ModelWrapper<AssetEntryAssetCategoryRel> {
	public AssetEntryAssetCategoryRelWrapper(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {
		_assetEntryAssetCategoryRel = assetEntryAssetCategoryRel;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetEntryAssetCategoryRel.class;
	}

	@Override
	public String getModelClassName() {
		return AssetEntryAssetCategoryRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("assetEntryAssetCategoryRelId",
			getAssetEntryAssetCategoryRelId());
		attributes.put("assetEntryId", getAssetEntryId());
		attributes.put("assetCategoryId", getAssetCategoryId());
		attributes.put("priority", getPriority());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long assetEntryAssetCategoryRelId = (Long)attributes.get(
				"assetEntryAssetCategoryRelId");

		if (assetEntryAssetCategoryRelId != null) {
			setAssetEntryAssetCategoryRelId(assetEntryAssetCategoryRelId);
		}

		Long assetEntryId = (Long)attributes.get("assetEntryId");

		if (assetEntryId != null) {
			setAssetEntryId(assetEntryId);
		}

		Long assetCategoryId = (Long)attributes.get("assetCategoryId");

		if (assetCategoryId != null) {
			setAssetCategoryId(assetCategoryId);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new AssetEntryAssetCategoryRelWrapper((AssetEntryAssetCategoryRel)_assetEntryAssetCategoryRel.clone());
	}

	@Override
	public int compareTo(AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {
		return _assetEntryAssetCategoryRel.compareTo(assetEntryAssetCategoryRel);
	}

	/**
	* Returns the asset category ID of this asset entry asset category rel.
	*
	* @return the asset category ID of this asset entry asset category rel
	*/
	@Override
	public long getAssetCategoryId() {
		return _assetEntryAssetCategoryRel.getAssetCategoryId();
	}

	/**
	* Returns the asset entry asset category rel ID of this asset entry asset category rel.
	*
	* @return the asset entry asset category rel ID of this asset entry asset category rel
	*/
	@Override
	public long getAssetEntryAssetCategoryRelId() {
		return _assetEntryAssetCategoryRel.getAssetEntryAssetCategoryRelId();
	}

	/**
	* Returns the asset entry ID of this asset entry asset category rel.
	*
	* @return the asset entry ID of this asset entry asset category rel
	*/
	@Override
	public long getAssetEntryId() {
		return _assetEntryAssetCategoryRel.getAssetEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetEntryAssetCategoryRel.getExpandoBridge();
	}

	/**
	* Returns the primary key of this asset entry asset category rel.
	*
	* @return the primary key of this asset entry asset category rel
	*/
	@Override
	public long getPrimaryKey() {
		return _assetEntryAssetCategoryRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetEntryAssetCategoryRel.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this asset entry asset category rel.
	*
	* @return the priority of this asset entry asset category rel
	*/
	@Override
	public int getPriority() {
		return _assetEntryAssetCategoryRel.getPriority();
	}

	@Override
	public int hashCode() {
		return _assetEntryAssetCategoryRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetEntryAssetCategoryRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetEntryAssetCategoryRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetEntryAssetCategoryRel.isNew();
	}

	@Override
	public void persist() {
		_assetEntryAssetCategoryRel.persist();
	}

	/**
	* Sets the asset category ID of this asset entry asset category rel.
	*
	* @param assetCategoryId the asset category ID of this asset entry asset category rel
	*/
	@Override
	public void setAssetCategoryId(long assetCategoryId) {
		_assetEntryAssetCategoryRel.setAssetCategoryId(assetCategoryId);
	}

	/**
	* Sets the asset entry asset category rel ID of this asset entry asset category rel.
	*
	* @param assetEntryAssetCategoryRelId the asset entry asset category rel ID of this asset entry asset category rel
	*/
	@Override
	public void setAssetEntryAssetCategoryRelId(
		long assetEntryAssetCategoryRelId) {
		_assetEntryAssetCategoryRel.setAssetEntryAssetCategoryRelId(assetEntryAssetCategoryRelId);
	}

	/**
	* Sets the asset entry ID of this asset entry asset category rel.
	*
	* @param assetEntryId the asset entry ID of this asset entry asset category rel
	*/
	@Override
	public void setAssetEntryId(long assetEntryId) {
		_assetEntryAssetCategoryRel.setAssetEntryId(assetEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetEntryAssetCategoryRel.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_assetEntryAssetCategoryRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetEntryAssetCategoryRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetEntryAssetCategoryRel.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_assetEntryAssetCategoryRel.setNew(n);
	}

	/**
	* Sets the primary key of this asset entry asset category rel.
	*
	* @param primaryKey the primary key of this asset entry asset category rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetEntryAssetCategoryRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetEntryAssetCategoryRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this asset entry asset category rel.
	*
	* @param priority the priority of this asset entry asset category rel
	*/
	@Override
	public void setPriority(int priority) {
		_assetEntryAssetCategoryRel.setPriority(priority);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetEntryAssetCategoryRel> toCacheModel() {
		return _assetEntryAssetCategoryRel.toCacheModel();
	}

	@Override
	public AssetEntryAssetCategoryRel toEscapedModel() {
		return new AssetEntryAssetCategoryRelWrapper(_assetEntryAssetCategoryRel.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetEntryAssetCategoryRel.toString();
	}

	@Override
	public AssetEntryAssetCategoryRel toUnescapedModel() {
		return new AssetEntryAssetCategoryRelWrapper(_assetEntryAssetCategoryRel.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _assetEntryAssetCategoryRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryAssetCategoryRelWrapper)) {
			return false;
		}

		AssetEntryAssetCategoryRelWrapper assetEntryAssetCategoryRelWrapper = (AssetEntryAssetCategoryRelWrapper)obj;

		if (Objects.equals(_assetEntryAssetCategoryRel,
					assetEntryAssetCategoryRelWrapper._assetEntryAssetCategoryRel)) {
			return true;
		}

		return false;
	}

	@Override
	public AssetEntryAssetCategoryRel getWrappedModel() {
		return _assetEntryAssetCategoryRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetEntryAssetCategoryRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetEntryAssetCategoryRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetEntryAssetCategoryRel.resetOriginalValues();
	}

	private final AssetEntryAssetCategoryRel _assetEntryAssetCategoryRel;
}