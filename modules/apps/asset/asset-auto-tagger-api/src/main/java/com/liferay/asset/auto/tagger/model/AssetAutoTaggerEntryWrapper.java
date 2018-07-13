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

package com.liferay.asset.auto.tagger.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link AssetAutoTaggerEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetAutoTaggerEntry
 * @generated
 */
@ProviderType
public class AssetAutoTaggerEntryWrapper implements AssetAutoTaggerEntry,
	ModelWrapper<AssetAutoTaggerEntry> {
	public AssetAutoTaggerEntryWrapper(
		AssetAutoTaggerEntry assetAutoTaggerEntry) {
		_assetAutoTaggerEntry = assetAutoTaggerEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetAutoTaggerEntry.class;
	}

	@Override
	public String getModelClassName() {
		return AssetAutoTaggerEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("assetAutoTaggerEntryId", getAssetAutoTaggerEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("assetEntryId", getAssetEntryId());
		attributes.put("assetTagId", getAssetTagId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long assetAutoTaggerEntryId = (Long)attributes.get(
				"assetAutoTaggerEntryId");

		if (assetAutoTaggerEntryId != null) {
			setAssetAutoTaggerEntryId(assetAutoTaggerEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long assetEntryId = (Long)attributes.get("assetEntryId");

		if (assetEntryId != null) {
			setAssetEntryId(assetEntryId);
		}

		Long assetTagId = (Long)attributes.get("assetTagId");

		if (assetTagId != null) {
			setAssetTagId(assetTagId);
		}
	}

	@Override
	public Object clone() {
		return new AssetAutoTaggerEntryWrapper((AssetAutoTaggerEntry)_assetAutoTaggerEntry.clone());
	}

	@Override
	public int compareTo(AssetAutoTaggerEntry assetAutoTaggerEntry) {
		return _assetAutoTaggerEntry.compareTo(assetAutoTaggerEntry);
	}

	/**
	* Returns the asset auto tagger entry ID of this asset auto tagger entry.
	*
	* @return the asset auto tagger entry ID of this asset auto tagger entry
	*/
	@Override
	public long getAssetAutoTaggerEntryId() {
		return _assetAutoTaggerEntry.getAssetAutoTaggerEntryId();
	}

	/**
	* Returns the asset entry ID of this asset auto tagger entry.
	*
	* @return the asset entry ID of this asset auto tagger entry
	*/
	@Override
	public long getAssetEntryId() {
		return _assetAutoTaggerEntry.getAssetEntryId();
	}

	/**
	* Returns the asset tag ID of this asset auto tagger entry.
	*
	* @return the asset tag ID of this asset auto tagger entry
	*/
	@Override
	public long getAssetTagId() {
		return _assetAutoTaggerEntry.getAssetTagId();
	}

	/**
	* Returns the company ID of this asset auto tagger entry.
	*
	* @return the company ID of this asset auto tagger entry
	*/
	@Override
	public long getCompanyId() {
		return _assetAutoTaggerEntry.getCompanyId();
	}

	/**
	* Returns the create date of this asset auto tagger entry.
	*
	* @return the create date of this asset auto tagger entry
	*/
	@Override
	public Date getCreateDate() {
		return _assetAutoTaggerEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetAutoTaggerEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this asset auto tagger entry.
	*
	* @return the group ID of this asset auto tagger entry
	*/
	@Override
	public long getGroupId() {
		return _assetAutoTaggerEntry.getGroupId();
	}

	/**
	* Returns the modified date of this asset auto tagger entry.
	*
	* @return the modified date of this asset auto tagger entry
	*/
	@Override
	public Date getModifiedDate() {
		return _assetAutoTaggerEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this asset auto tagger entry.
	*
	* @return the primary key of this asset auto tagger entry
	*/
	@Override
	public long getPrimaryKey() {
		return _assetAutoTaggerEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetAutoTaggerEntry.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _assetAutoTaggerEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetAutoTaggerEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetAutoTaggerEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetAutoTaggerEntry.isNew();
	}

	@Override
	public void persist() {
		_assetAutoTaggerEntry.persist();
	}

	/**
	* Sets the asset auto tagger entry ID of this asset auto tagger entry.
	*
	* @param assetAutoTaggerEntryId the asset auto tagger entry ID of this asset auto tagger entry
	*/
	@Override
	public void setAssetAutoTaggerEntryId(long assetAutoTaggerEntryId) {
		_assetAutoTaggerEntry.setAssetAutoTaggerEntryId(assetAutoTaggerEntryId);
	}

	/**
	* Sets the asset entry ID of this asset auto tagger entry.
	*
	* @param assetEntryId the asset entry ID of this asset auto tagger entry
	*/
	@Override
	public void setAssetEntryId(long assetEntryId) {
		_assetAutoTaggerEntry.setAssetEntryId(assetEntryId);
	}

	/**
	* Sets the asset tag ID of this asset auto tagger entry.
	*
	* @param assetTagId the asset tag ID of this asset auto tagger entry
	*/
	@Override
	public void setAssetTagId(long assetTagId) {
		_assetAutoTaggerEntry.setAssetTagId(assetTagId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetAutoTaggerEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this asset auto tagger entry.
	*
	* @param companyId the company ID of this asset auto tagger entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetAutoTaggerEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this asset auto tagger entry.
	*
	* @param createDate the create date of this asset auto tagger entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_assetAutoTaggerEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_assetAutoTaggerEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetAutoTaggerEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetAutoTaggerEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this asset auto tagger entry.
	*
	* @param groupId the group ID of this asset auto tagger entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_assetAutoTaggerEntry.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this asset auto tagger entry.
	*
	* @param modifiedDate the modified date of this asset auto tagger entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_assetAutoTaggerEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_assetAutoTaggerEntry.setNew(n);
	}

	/**
	* Sets the primary key of this asset auto tagger entry.
	*
	* @param primaryKey the primary key of this asset auto tagger entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetAutoTaggerEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetAutoTaggerEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetAutoTaggerEntry> toCacheModel() {
		return _assetAutoTaggerEntry.toCacheModel();
	}

	@Override
	public AssetAutoTaggerEntry toEscapedModel() {
		return new AssetAutoTaggerEntryWrapper(_assetAutoTaggerEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _assetAutoTaggerEntry.toString();
	}

	@Override
	public AssetAutoTaggerEntry toUnescapedModel() {
		return new AssetAutoTaggerEntryWrapper(_assetAutoTaggerEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _assetAutoTaggerEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetAutoTaggerEntryWrapper)) {
			return false;
		}

		AssetAutoTaggerEntryWrapper assetAutoTaggerEntryWrapper = (AssetAutoTaggerEntryWrapper)obj;

		if (Objects.equals(_assetAutoTaggerEntry,
					assetAutoTaggerEntryWrapper._assetAutoTaggerEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public AssetAutoTaggerEntry getWrappedModel() {
		return _assetAutoTaggerEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetAutoTaggerEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetAutoTaggerEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetAutoTaggerEntry.resetOriginalValues();
	}

	private final AssetAutoTaggerEntry _assetAutoTaggerEntry;
}