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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetAutoTaggerEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetAutoTaggerEntry
 * @generated
 */
public class AssetAutoTaggerEntryWrapper
	extends BaseModelWrapper<AssetAutoTaggerEntry>
	implements AssetAutoTaggerEntry, ModelWrapper<AssetAutoTaggerEntry> {

	public AssetAutoTaggerEntryWrapper(
		AssetAutoTaggerEntry assetAutoTaggerEntry) {

		super(assetAutoTaggerEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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

	/**
	 * Returns the asset auto tagger entry ID of this asset auto tagger entry.
	 *
	 * @return the asset auto tagger entry ID of this asset auto tagger entry
	 */
	@Override
	public long getAssetAutoTaggerEntryId() {
		return model.getAssetAutoTaggerEntryId();
	}

	/**
	 * Returns the asset entry ID of this asset auto tagger entry.
	 *
	 * @return the asset entry ID of this asset auto tagger entry
	 */
	@Override
	public long getAssetEntryId() {
		return model.getAssetEntryId();
	}

	/**
	 * Returns the asset tag ID of this asset auto tagger entry.
	 *
	 * @return the asset tag ID of this asset auto tagger entry
	 */
	@Override
	public long getAssetTagId() {
		return model.getAssetTagId();
	}

	/**
	 * Returns the company ID of this asset auto tagger entry.
	 *
	 * @return the company ID of this asset auto tagger entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this asset auto tagger entry.
	 *
	 * @return the create date of this asset auto tagger entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this asset auto tagger entry.
	 *
	 * @return the group ID of this asset auto tagger entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this asset auto tagger entry.
	 *
	 * @return the modified date of this asset auto tagger entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this asset auto tagger entry.
	 *
	 * @return the mvcc version of this asset auto tagger entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this asset auto tagger entry.
	 *
	 * @return the primary key of this asset auto tagger entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset auto tagger entry model instance should use the <code>AssetAutoTaggerEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the asset auto tagger entry ID of this asset auto tagger entry.
	 *
	 * @param assetAutoTaggerEntryId the asset auto tagger entry ID of this asset auto tagger entry
	 */
	@Override
	public void setAssetAutoTaggerEntryId(long assetAutoTaggerEntryId) {
		model.setAssetAutoTaggerEntryId(assetAutoTaggerEntryId);
	}

	/**
	 * Sets the asset entry ID of this asset auto tagger entry.
	 *
	 * @param assetEntryId the asset entry ID of this asset auto tagger entry
	 */
	@Override
	public void setAssetEntryId(long assetEntryId) {
		model.setAssetEntryId(assetEntryId);
	}

	/**
	 * Sets the asset tag ID of this asset auto tagger entry.
	 *
	 * @param assetTagId the asset tag ID of this asset auto tagger entry
	 */
	@Override
	public void setAssetTagId(long assetTagId) {
		model.setAssetTagId(assetTagId);
	}

	/**
	 * Sets the company ID of this asset auto tagger entry.
	 *
	 * @param companyId the company ID of this asset auto tagger entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this asset auto tagger entry.
	 *
	 * @param createDate the create date of this asset auto tagger entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this asset auto tagger entry.
	 *
	 * @param groupId the group ID of this asset auto tagger entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this asset auto tagger entry.
	 *
	 * @param modifiedDate the modified date of this asset auto tagger entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this asset auto tagger entry.
	 *
	 * @param mvccVersion the mvcc version of this asset auto tagger entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this asset auto tagger entry.
	 *
	 * @param primaryKey the primary key of this asset auto tagger entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected AssetAutoTaggerEntryWrapper wrap(
		AssetAutoTaggerEntry assetAutoTaggerEntry) {

		return new AssetAutoTaggerEntryWrapper(assetAutoTaggerEntry);
	}

}