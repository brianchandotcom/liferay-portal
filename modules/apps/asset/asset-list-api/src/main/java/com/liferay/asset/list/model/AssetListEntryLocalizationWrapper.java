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
 * This class is a wrapper for {@link AssetListEntryLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryLocalization
 * @generated
 */
@ProviderType
public class AssetListEntryLocalizationWrapper
	implements AssetListEntryLocalization,
		ModelWrapper<AssetListEntryLocalization> {
	public AssetListEntryLocalizationWrapper(
		AssetListEntryLocalization assetListEntryLocalization) {
		_assetListEntryLocalization = assetListEntryLocalization;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetListEntryLocalization.class;
	}

	@Override
	public String getModelClassName() {
		return AssetListEntryLocalization.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("assetListEntryLocalizationId",
			getAssetListEntryLocalizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("assetListEntryId", getAssetListEntryId());
		attributes.put("languageId", getLanguageId());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("groupId", getGroupId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long assetListEntryLocalizationId = (Long)attributes.get(
				"assetListEntryLocalizationId");

		if (assetListEntryLocalizationId != null) {
			setAssetListEntryLocalizationId(assetListEntryLocalizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long assetListEntryId = (Long)attributes.get("assetListEntryId");

		if (assetListEntryId != null) {
			setAssetListEntryId(assetListEntryId);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}
	}

	@Override
	public Object clone() {
		return new AssetListEntryLocalizationWrapper((AssetListEntryLocalization)_assetListEntryLocalization.clone());
	}

	@Override
	public int compareTo(AssetListEntryLocalization assetListEntryLocalization) {
		return _assetListEntryLocalization.compareTo(assetListEntryLocalization);
	}

	/**
	* Returns the asset list entry ID of this asset list entry localization.
	*
	* @return the asset list entry ID of this asset list entry localization
	*/
	@Override
	public long getAssetListEntryId() {
		return _assetListEntryLocalization.getAssetListEntryId();
	}

	/**
	* Returns the asset list entry localization ID of this asset list entry localization.
	*
	* @return the asset list entry localization ID of this asset list entry localization
	*/
	@Override
	public long getAssetListEntryLocalizationId() {
		return _assetListEntryLocalization.getAssetListEntryLocalizationId();
	}

	/**
	* Returns the company ID of this asset list entry localization.
	*
	* @return the company ID of this asset list entry localization
	*/
	@Override
	public long getCompanyId() {
		return _assetListEntryLocalization.getCompanyId();
	}

	/**
	* Returns the description of this asset list entry localization.
	*
	* @return the description of this asset list entry localization
	*/
	@Override
	public String getDescription() {
		return _assetListEntryLocalization.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetListEntryLocalization.getExpandoBridge();
	}

	/**
	* Returns the group ID of this asset list entry localization.
	*
	* @return the group ID of this asset list entry localization
	*/
	@Override
	public long getGroupId() {
		return _assetListEntryLocalization.getGroupId();
	}

	/**
	* Returns the language ID of this asset list entry localization.
	*
	* @return the language ID of this asset list entry localization
	*/
	@Override
	public String getLanguageId() {
		return _assetListEntryLocalization.getLanguageId();
	}

	/**
	* Returns the mvcc version of this asset list entry localization.
	*
	* @return the mvcc version of this asset list entry localization
	*/
	@Override
	public long getMvccVersion() {
		return _assetListEntryLocalization.getMvccVersion();
	}

	/**
	* Returns the primary key of this asset list entry localization.
	*
	* @return the primary key of this asset list entry localization
	*/
	@Override
	public long getPrimaryKey() {
		return _assetListEntryLocalization.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetListEntryLocalization.getPrimaryKeyObj();
	}

	/**
	* Returns the title of this asset list entry localization.
	*
	* @return the title of this asset list entry localization
	*/
	@Override
	public String getTitle() {
		return _assetListEntryLocalization.getTitle();
	}

	@Override
	public int hashCode() {
		return _assetListEntryLocalization.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetListEntryLocalization.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetListEntryLocalization.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetListEntryLocalization.isNew();
	}

	/**
	* Sets the asset list entry ID of this asset list entry localization.
	*
	* @param assetListEntryId the asset list entry ID of this asset list entry localization
	*/
	@Override
	public void setAssetListEntryId(long assetListEntryId) {
		_assetListEntryLocalization.setAssetListEntryId(assetListEntryId);
	}

	/**
	* Sets the asset list entry localization ID of this asset list entry localization.
	*
	* @param assetListEntryLocalizationId the asset list entry localization ID of this asset list entry localization
	*/
	@Override
	public void setAssetListEntryLocalizationId(
		long assetListEntryLocalizationId) {
		_assetListEntryLocalization.setAssetListEntryLocalizationId(assetListEntryLocalizationId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetListEntryLocalization.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this asset list entry localization.
	*
	* @param companyId the company ID of this asset list entry localization
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetListEntryLocalization.setCompanyId(companyId);
	}

	/**
	* Sets the description of this asset list entry localization.
	*
	* @param description the description of this asset list entry localization
	*/
	@Override
	public void setDescription(String description) {
		_assetListEntryLocalization.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_assetListEntryLocalization.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetListEntryLocalization.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetListEntryLocalization.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this asset list entry localization.
	*
	* @param groupId the group ID of this asset list entry localization
	*/
	@Override
	public void setGroupId(long groupId) {
		_assetListEntryLocalization.setGroupId(groupId);
	}

	/**
	* Sets the language ID of this asset list entry localization.
	*
	* @param languageId the language ID of this asset list entry localization
	*/
	@Override
	public void setLanguageId(String languageId) {
		_assetListEntryLocalization.setLanguageId(languageId);
	}

	/**
	* Sets the mvcc version of this asset list entry localization.
	*
	* @param mvccVersion the mvcc version of this asset list entry localization
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_assetListEntryLocalization.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_assetListEntryLocalization.setNew(n);
	}

	/**
	* Sets the primary key of this asset list entry localization.
	*
	* @param primaryKey the primary key of this asset list entry localization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetListEntryLocalization.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetListEntryLocalization.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the title of this asset list entry localization.
	*
	* @param title the title of this asset list entry localization
	*/
	@Override
	public void setTitle(String title) {
		_assetListEntryLocalization.setTitle(title);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetListEntryLocalization> toCacheModel() {
		return _assetListEntryLocalization.toCacheModel();
	}

	@Override
	public AssetListEntryLocalization toEscapedModel() {
		return new AssetListEntryLocalizationWrapper(_assetListEntryLocalization.toEscapedModel());
	}

	@Override
	public String toString() {
		return _assetListEntryLocalization.toString();
	}

	@Override
	public AssetListEntryLocalization toUnescapedModel() {
		return new AssetListEntryLocalizationWrapper(_assetListEntryLocalization.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _assetListEntryLocalization.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetListEntryLocalizationWrapper)) {
			return false;
		}

		AssetListEntryLocalizationWrapper assetListEntryLocalizationWrapper = (AssetListEntryLocalizationWrapper)obj;

		if (Objects.equals(_assetListEntryLocalization,
					assetListEntryLocalizationWrapper._assetListEntryLocalization)) {
			return true;
		}

		return false;
	}

	@Override
	public AssetListEntryLocalization getWrappedModel() {
		return _assetListEntryLocalization;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetListEntryLocalization.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetListEntryLocalization.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetListEntryLocalization.resetOriginalValues();
	}

	private final AssetListEntryLocalization _assetListEntryLocalization;
}