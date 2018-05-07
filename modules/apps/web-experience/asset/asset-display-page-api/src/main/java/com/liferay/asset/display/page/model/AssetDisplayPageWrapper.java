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
 * This class is a wrapper for {@link AssetDisplayPage}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPage
 * @generated
 */
@ProviderType
public class AssetDisplayPageWrapper implements AssetDisplayPage,
	ModelWrapper<AssetDisplayPage> {
	public AssetDisplayPageWrapper(AssetDisplayPage assetDisplayPage) {
		_assetDisplayPage = assetDisplayPage;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetDisplayPage.class;
	}

	@Override
	public String getModelClassName() {
		return AssetDisplayPage.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("assetDisplayPageId", getAssetDisplayPageId());
		attributes.put("assetEntryId", getAssetEntryId());
		attributes.put("layoutId", getLayoutId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long assetDisplayPageId = (Long)attributes.get("assetDisplayPageId");

		if (assetDisplayPageId != null) {
			setAssetDisplayPageId(assetDisplayPageId);
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
		return new AssetDisplayPageWrapper((AssetDisplayPage)_assetDisplayPage.clone());
	}

	@Override
	public int compareTo(AssetDisplayPage assetDisplayPage) {
		return _assetDisplayPage.compareTo(assetDisplayPage);
	}

	/**
	* Returns the asset display page ID of this asset display page.
	*
	* @return the asset display page ID of this asset display page
	*/
	@Override
	public long getAssetDisplayPageId() {
		return _assetDisplayPage.getAssetDisplayPageId();
	}

	/**
	* Returns the asset entry ID of this asset display page.
	*
	* @return the asset entry ID of this asset display page
	*/
	@Override
	public long getAssetEntryId() {
		return _assetDisplayPage.getAssetEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetDisplayPage.getExpandoBridge();
	}

	/**
	* Returns the layout ID of this asset display page.
	*
	* @return the layout ID of this asset display page
	*/
	@Override
	public long getLayoutId() {
		return _assetDisplayPage.getLayoutId();
	}

	/**
	* Returns the primary key of this asset display page.
	*
	* @return the primary key of this asset display page
	*/
	@Override
	public long getPrimaryKey() {
		return _assetDisplayPage.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetDisplayPage.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _assetDisplayPage.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetDisplayPage.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetDisplayPage.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetDisplayPage.isNew();
	}

	@Override
	public void persist() {
		_assetDisplayPage.persist();
	}

	/**
	* Sets the asset display page ID of this asset display page.
	*
	* @param assetDisplayPageId the asset display page ID of this asset display page
	*/
	@Override
	public void setAssetDisplayPageId(long assetDisplayPageId) {
		_assetDisplayPage.setAssetDisplayPageId(assetDisplayPageId);
	}

	/**
	* Sets the asset entry ID of this asset display page.
	*
	* @param assetEntryId the asset entry ID of this asset display page
	*/
	@Override
	public void setAssetEntryId(long assetEntryId) {
		_assetDisplayPage.setAssetEntryId(assetEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetDisplayPage.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_assetDisplayPage.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetDisplayPage.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetDisplayPage.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the layout ID of this asset display page.
	*
	* @param layoutId the layout ID of this asset display page
	*/
	@Override
	public void setLayoutId(long layoutId) {
		_assetDisplayPage.setLayoutId(layoutId);
	}

	@Override
	public void setNew(boolean n) {
		_assetDisplayPage.setNew(n);
	}

	/**
	* Sets the primary key of this asset display page.
	*
	* @param primaryKey the primary key of this asset display page
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetDisplayPage.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetDisplayPage.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetDisplayPage> toCacheModel() {
		return _assetDisplayPage.toCacheModel();
	}

	@Override
	public AssetDisplayPage toEscapedModel() {
		return new AssetDisplayPageWrapper(_assetDisplayPage.toEscapedModel());
	}

	@Override
	public String toString() {
		return _assetDisplayPage.toString();
	}

	@Override
	public AssetDisplayPage toUnescapedModel() {
		return new AssetDisplayPageWrapper(_assetDisplayPage.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _assetDisplayPage.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetDisplayPageWrapper)) {
			return false;
		}

		AssetDisplayPageWrapper assetDisplayPageWrapper = (AssetDisplayPageWrapper)obj;

		if (Objects.equals(_assetDisplayPage,
					assetDisplayPageWrapper._assetDisplayPage)) {
			return true;
		}

		return false;
	}

	@Override
	public AssetDisplayPage getWrappedModel() {
		return _assetDisplayPage;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetDisplayPage.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetDisplayPage.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetDisplayPage.resetOriginalValues();
	}

	private final AssetDisplayPage _assetDisplayPage;
}