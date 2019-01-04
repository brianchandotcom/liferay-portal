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

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

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
public class AssetListEntryWrapper extends BaseModelWrapper<AssetListEntry>
	implements AssetListEntry, ModelWrapper<AssetListEntry> {
	public AssetListEntryWrapper(AssetListEntry assetListEntry) {
		super(assetListEntry);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getAssetEntries() {
		return model.getAssetEntries();
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getAssetEntries(
		int start, int end) {
		return model.getAssetEntries(start, end);
	}

	@Override
	public int getAssetEntriesCount() {
		return model.getAssetEntriesCount();
	}

	@Override
	public com.liferay.asset.kernel.service.persistence.AssetEntryQuery getAssetEntryQuery() {
		return model.getAssetEntryQuery();
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

	/**
	* Returns the type settings of this asset list entry.
	*
	* @return the type settings of this asset list entry
	*/
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
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
	* Sets the type settings of this asset list entry.
	*
	* @param typeSettings the type settings of this asset list entry
	*/
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
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
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected AssetListEntryWrapper wrap(AssetListEntry assetListEntry) {
		return new AssetListEntryWrapper(assetListEntry);
	}
}