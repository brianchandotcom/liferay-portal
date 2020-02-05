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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link AssetListEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntry
 * @generated
 */
public class AssetListEntryWrapper
	extends BaseModelWrapper<AssetListEntry>
	implements AssetListEntry, ModelWrapper<AssetListEntry> {

	public AssetListEntryWrapper(AssetListEntry assetListEntry) {
		super(assetListEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("assetListEntryId", getAssetListEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("assetListEntryKey", getAssetListEntryKey());
		attributes.put("title", getTitle());
		attributes.put("type", getType());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
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

		String assetListEntryKey = (String)attributes.get("assetListEntryKey");

		if (assetListEntryKey != null) {
			setAssetListEntryKey(assetListEntryKey);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
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

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 AssetListAssetEntryProvider#getAssetEntries(AssetListEntry,
	 long)}
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry>
		getAssetEntries(long segmentsEntryId) {

		return model.getAssetEntries(segmentsEntryId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 AssetListAssetEntryProvider#getAssetEntries(AssetListEntry,
	 long, int, int)}
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry>
		getAssetEntries(long segmentsEntryId, int start, int end) {

		return model.getAssetEntries(segmentsEntryId, start, end);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 AssetListAssetEntryProvider#getAssetEntries(AssetListEntry,
	 long[])}
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry>
		getAssetEntries(long[] segmentsEntryIds) {

		return model.getAssetEntries(segmentsEntryIds);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 AssetListAssetEntryProvider#getAssetEntries(AssetListEntry,
	 long[], int, int)}
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry>
		getAssetEntries(long[] segmentsEntryIds, int start, int end) {

		return model.getAssetEntries(segmentsEntryIds, start, end);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 AssetListAssetEntryProvider#getAssetEntriesCount(
	 AssetListEntry, long)}
	 */
	@Deprecated
	@Override
	public int getAssetEntriesCount(long segmentsEntryId) {
		return model.getAssetEntriesCount(segmentsEntryId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 AssetListAssetEntryProvider#getAssetEntriesCount(
	 AssetListEntry, long[])}
	 */
	@Deprecated
	@Override
	public int getAssetEntriesCount(long[] segmentsEntryIds) {
		return model.getAssetEntriesCount(segmentsEntryIds);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 AssetListAssetEntryProvider#getAssetEntryQuery(
	 AssetListEntry, long)}
	 */
	@Deprecated
	@Override
	public com.liferay.asset.kernel.service.persistence.AssetEntryQuery
		getAssetEntryQuery(long segmentsEntryId) {

		return model.getAssetEntryQuery(segmentsEntryId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 AssetListAssetEntryProvider#getAssetEntryQuery(
	 AssetListEntry, long[])}
	 */
	@Deprecated
	@Override
	public com.liferay.asset.kernel.service.persistence.AssetEntryQuery
		getAssetEntryQuery(long[] segmentsEntryIds) {

		return model.getAssetEntryQuery(segmentsEntryIds);
	}

	/**
	 * Returns the asset list entry ID of this asset list entry.
	 *
	 * @return the asset list entry ID of this asset list entry
	 */
	@Override
	public long getAssetListEntryId() {
		return model.getAssetListEntryId();
	}

	/**
	 * Returns the asset list entry key of this asset list entry.
	 *
	 * @return the asset list entry key of this asset list entry
	 */
	@Override
	public String getAssetListEntryKey() {
		return model.getAssetListEntryKey();
	}

	/**
	 * Returns the company ID of this asset list entry.
	 *
	 * @return the company ID of this asset list entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this asset list entry.
	 *
	 * @return the create date of this asset list entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this asset list entry.
	 *
	 * @return the ct collection ID of this asset list entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this asset list entry.
	 *
	 * @return the group ID of this asset list entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this asset list entry.
	 *
	 * @return the last publish date of this asset list entry
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this asset list entry.
	 *
	 * @return the modified date of this asset list entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this asset list entry.
	 *
	 * @return the mvcc version of this asset list entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this asset list entry.
	 *
	 * @return the primary key of this asset list entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the title of this asset list entry.
	 *
	 * @return the title of this asset list entry
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the type of this asset list entry.
	 *
	 * @return the type of this asset list entry
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	@Override
	public String getTypeLabel() {
		return model.getTypeLabel();
	}

	@Override
	public String getTypeSettings(long segmentsEntryId) {
		return model.getTypeSettings(segmentsEntryId);
	}

	@Override
	public String getUnambiguousTitle(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getUnambiguousTitle(locale);
	}

	/**
	 * Returns the user ID of this asset list entry.
	 *
	 * @return the user ID of this asset list entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this asset list entry.
	 *
	 * @return the user name of this asset list entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this asset list entry.
	 *
	 * @return the user uuid of this asset list entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this asset list entry.
	 *
	 * @return the uuid of this asset list entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the asset list entry ID of this asset list entry.
	 *
	 * @param assetListEntryId the asset list entry ID of this asset list entry
	 */
	@Override
	public void setAssetListEntryId(long assetListEntryId) {
		model.setAssetListEntryId(assetListEntryId);
	}

	/**
	 * Sets the asset list entry key of this asset list entry.
	 *
	 * @param assetListEntryKey the asset list entry key of this asset list entry
	 */
	@Override
	public void setAssetListEntryKey(String assetListEntryKey) {
		model.setAssetListEntryKey(assetListEntryKey);
	}

	/**
	 * Sets the company ID of this asset list entry.
	 *
	 * @param companyId the company ID of this asset list entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this asset list entry.
	 *
	 * @param createDate the create date of this asset list entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this asset list entry.
	 *
	 * @param ctCollectionId the ct collection ID of this asset list entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this asset list entry.
	 *
	 * @param groupId the group ID of this asset list entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this asset list entry.
	 *
	 * @param lastPublishDate the last publish date of this asset list entry
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this asset list entry.
	 *
	 * @param modifiedDate the modified date of this asset list entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this asset list entry.
	 *
	 * @param mvccVersion the mvcc version of this asset list entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this asset list entry.
	 *
	 * @param primaryKey the primary key of this asset list entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the title of this asset list entry.
	 *
	 * @param title the title of this asset list entry
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the type of this asset list entry.
	 *
	 * @param type the type of this asset list entry
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this asset list entry.
	 *
	 * @param userId the user ID of this asset list entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this asset list entry.
	 *
	 * @param userName the user name of this asset list entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this asset list entry.
	 *
	 * @param userUuid the user uuid of this asset list entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this asset list entry.
	 *
	 * @param uuid the uuid of this asset list entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<AssetListEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<AssetListEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected AssetListEntryWrapper wrap(AssetListEntry assetListEntry) {
		return new AssetListEntryWrapper(assetListEntry);
	}

}