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

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link AssetListEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntry
 * @generated
 */
@ProviderType
public class AssetListEntryWrapper implements AssetListEntry,
	ModelWrapper<AssetListEntry> {
	public AssetListEntryWrapper(AssetListEntry assetListEntry) {
		_assetListEntry = assetListEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetListEntry.class;
	}

	@Override
	public String getModelClassName() {
		return AssetListEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("defaultLanguageId", getDefaultLanguageId());
		attributes.put("assetListEntryId", getAssetListEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("type", getType());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String defaultLanguageId = (String)attributes.get("defaultLanguageId");

		if (defaultLanguageId != null) {
			setDefaultLanguageId(defaultLanguageId);
		}

		Long assetListEntryId = (Long)attributes.get("assetListEntryId");

		if (assetListEntryId != null) {
			setAssetListEntryId(assetListEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Object clone() {
		return new AssetListEntryWrapper((AssetListEntry)_assetListEntry.clone());
	}

	@Override
	public int compareTo(AssetListEntry assetListEntry) {
		return _assetListEntry.compareTo(assetListEntry);
	}

	/**
	* Returns the asset list entry ID of this asset list entry.
	*
	* @return the asset list entry ID of this asset list entry
	*/
	@Override
	public long getAssetListEntryId() {
		return _assetListEntry.getAssetListEntryId();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _assetListEntry.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this asset list entry.
	*
	* @return the company ID of this asset list entry
	*/
	@Override
	public long getCompanyId() {
		return _assetListEntry.getCompanyId();
	}

	/**
	* Returns the create date of this asset list entry.
	*
	* @return the create date of this asset list entry
	*/
	@Override
	public Date getCreateDate() {
		return _assetListEntry.getCreateDate();
	}

	/**
	* Returns the default language ID of this asset list entry.
	*
	* @return the default language ID of this asset list entry
	*/
	@Override
	public String getDefaultLanguageId() {
		return _assetListEntry.getDefaultLanguageId();
	}

	@Override
	public String getDescription() {
		return _assetListEntry.getDescription();
	}

	@Override
	public String getDescription(String languageId) {
		return _assetListEntry.getDescription(languageId);
	}

	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _assetListEntry.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionMapAsXML() {
		return _assetListEntry.getDescriptionMapAsXML();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetListEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this asset list entry.
	*
	* @return the group ID of this asset list entry
	*/
	@Override
	public long getGroupId() {
		return _assetListEntry.getGroupId();
	}

	@Override
	public Map<String, String> getLanguageIdToDescriptionMap() {
		return _assetListEntry.getLanguageIdToDescriptionMap();
	}

	@Override
	public Map<String, String> getLanguageIdToTitleMap() {
		return _assetListEntry.getLanguageIdToTitleMap();
	}

	/**
	* Returns the last publish date of this asset list entry.
	*
	* @return the last publish date of this asset list entry
	*/
	@Override
	public Date getLastPublishDate() {
		return _assetListEntry.getLastPublishDate();
	}

	/**
	* Returns the modified date of this asset list entry.
	*
	* @return the modified date of this asset list entry
	*/
	@Override
	public Date getModifiedDate() {
		return _assetListEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this asset list entry.
	*
	* @return the primary key of this asset list entry
	*/
	@Override
	public long getPrimaryKey() {
		return _assetListEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetListEntry.getPrimaryKeyObj();
	}

	@Override
	public String getTitle() {
		return _assetListEntry.getTitle();
	}

	@Override
	public String getTitle(String languageId) {
		return _assetListEntry.getTitle(languageId);
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return _assetListEntry.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleMapAsXML() {
		return _assetListEntry.getTitleMapAsXML();
	}

	/**
	* Returns the type of this asset list entry.
	*
	* @return the type of this asset list entry
	*/
	@Override
	public int getType() {
		return _assetListEntry.getType();
	}

	/**
	* Returns the user ID of this asset list entry.
	*
	* @return the user ID of this asset list entry
	*/
	@Override
	public long getUserId() {
		return _assetListEntry.getUserId();
	}

	/**
	* Returns the user name of this asset list entry.
	*
	* @return the user name of this asset list entry
	*/
	@Override
	public String getUserName() {
		return _assetListEntry.getUserName();
	}

	/**
	* Returns the user uuid of this asset list entry.
	*
	* @return the user uuid of this asset list entry
	*/
	@Override
	public String getUserUuid() {
		return _assetListEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this asset list entry.
	*
	* @return the uuid of this asset list entry
	*/
	@Override
	public String getUuid() {
		return _assetListEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _assetListEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetListEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetListEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetListEntry.isNew();
	}

	@Override
	public void persist() {
		_assetListEntry.persist();
	}

	/**
	* Sets the asset list entry ID of this asset list entry.
	*
	* @param assetListEntryId the asset list entry ID of this asset list entry
	*/
	@Override
	public void setAssetListEntryId(long assetListEntryId) {
		_assetListEntry.setAssetListEntryId(assetListEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetListEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this asset list entry.
	*
	* @param companyId the company ID of this asset list entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetListEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this asset list entry.
	*
	* @param createDate the create date of this asset list entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_assetListEntry.setCreateDate(createDate);
	}

	/**
	* Sets the default language ID of this asset list entry.
	*
	* @param defaultLanguageId the default language ID of this asset list entry
	*/
	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		_assetListEntry.setDefaultLanguageId(defaultLanguageId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_assetListEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetListEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetListEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this asset list entry.
	*
	* @param groupId the group ID of this asset list entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_assetListEntry.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this asset list entry.
	*
	* @param lastPublishDate the last publish date of this asset list entry
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_assetListEntry.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this asset list entry.
	*
	* @param modifiedDate the modified date of this asset list entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_assetListEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_assetListEntry.setNew(n);
	}

	/**
	* Sets the primary key of this asset list entry.
	*
	* @param primaryKey the primary key of this asset list entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetListEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetListEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the type of this asset list entry.
	*
	* @param type the type of this asset list entry
	*/
	@Override
	public void setType(int type) {
		_assetListEntry.setType(type);
	}

	/**
	* Sets the user ID of this asset list entry.
	*
	* @param userId the user ID of this asset list entry
	*/
	@Override
	public void setUserId(long userId) {
		_assetListEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this asset list entry.
	*
	* @param userName the user name of this asset list entry
	*/
	@Override
	public void setUserName(String userName) {
		_assetListEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this asset list entry.
	*
	* @param userUuid the user uuid of this asset list entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_assetListEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this asset list entry.
	*
	* @param uuid the uuid of this asset list entry
	*/
	@Override
	public void setUuid(String uuid) {
		_assetListEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetListEntry> toCacheModel() {
		return _assetListEntry.toCacheModel();
	}

	@Override
	public AssetListEntry toEscapedModel() {
		return new AssetListEntryWrapper(_assetListEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _assetListEntry.toString();
	}

	@Override
	public AssetListEntry toUnescapedModel() {
		return new AssetListEntryWrapper(_assetListEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _assetListEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetListEntryWrapper)) {
			return false;
		}

		AssetListEntryWrapper assetListEntryWrapper = (AssetListEntryWrapper)obj;

		if (Objects.equals(_assetListEntry,
					assetListEntryWrapper._assetListEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _assetListEntry.getStagedModelType();
	}

	@Override
	public AssetListEntry getWrappedModel() {
		return _assetListEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetListEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetListEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetListEntry.resetOriginalValues();
	}

	private final AssetListEntry _assetListEntry;
}