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

/**
 * <p>
 * This class is a wrapper for {@link AssetListEntryAssetEntryRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryAssetEntryRel
 * @generated
 */
public class AssetListEntryAssetEntryRelWrapper
	extends BaseModelWrapper<AssetListEntryAssetEntryRel>
	implements AssetListEntryAssetEntryRel,
			   ModelWrapper<AssetListEntryAssetEntryRel> {

	public AssetListEntryAssetEntryRelWrapper(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {

		super(assetListEntryAssetEntryRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"assetListEntryAssetEntryRelId",
			getAssetListEntryAssetEntryRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("assetListEntryId", getAssetListEntryId());
		attributes.put("assetEntryId", getAssetEntryId());
		attributes.put("segmentsEntryId", getSegmentsEntryId());
		attributes.put("position", getPosition());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long assetListEntryAssetEntryRelId = (Long)attributes.get(
			"assetListEntryAssetEntryRelId");

		if (assetListEntryAssetEntryRelId != null) {
			setAssetListEntryAssetEntryRelId(assetListEntryAssetEntryRelId);
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

		Long assetListEntryId = (Long)attributes.get("assetListEntryId");

		if (assetListEntryId != null) {
			setAssetListEntryId(assetListEntryId);
		}

		Long assetEntryId = (Long)attributes.get("assetEntryId");

		if (assetEntryId != null) {
			setAssetEntryId(assetEntryId);
		}

		Long segmentsEntryId = (Long)attributes.get("segmentsEntryId");

		if (segmentsEntryId != null) {
			setSegmentsEntryId(segmentsEntryId);
		}

		Integer position = (Integer)attributes.get("position");

		if (position != null) {
			setPosition(position);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	 * Returns the asset entry ID of this asset list entry asset entry rel.
	 *
	 * @return the asset entry ID of this asset list entry asset entry rel
	 */
	@Override
	public long getAssetEntryId() {
		return model.getAssetEntryId();
	}

	@Override
	public String getAssetEntryUuid() {
		return model.getAssetEntryUuid();
	}

	/**
	 * Returns the asset list entry asset entry rel ID of this asset list entry asset entry rel.
	 *
	 * @return the asset list entry asset entry rel ID of this asset list entry asset entry rel
	 */
	@Override
	public long getAssetListEntryAssetEntryRelId() {
		return model.getAssetListEntryAssetEntryRelId();
	}

	/**
	 * Returns the asset list entry ID of this asset list entry asset entry rel.
	 *
	 * @return the asset list entry ID of this asset list entry asset entry rel
	 */
	@Override
	public long getAssetListEntryId() {
		return model.getAssetListEntryId();
	}

	/**
	 * Returns the company ID of this asset list entry asset entry rel.
	 *
	 * @return the company ID of this asset list entry asset entry rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this asset list entry asset entry rel.
	 *
	 * @return the create date of this asset list entry asset entry rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this asset list entry asset entry rel.
	 *
	 * @return the group ID of this asset list entry asset entry rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this asset list entry asset entry rel.
	 *
	 * @return the last publish date of this asset list entry asset entry rel
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this asset list entry asset entry rel.
	 *
	 * @return the modified date of this asset list entry asset entry rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this asset list entry asset entry rel.
	 *
	 * @return the mvcc version of this asset list entry asset entry rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the position of this asset list entry asset entry rel.
	 *
	 * @return the position of this asset list entry asset entry rel
	 */
	@Override
	public int getPosition() {
		return model.getPosition();
	}

	/**
	 * Returns the primary key of this asset list entry asset entry rel.
	 *
	 * @return the primary key of this asset list entry asset entry rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the segments entry ID of this asset list entry asset entry rel.
	 *
	 * @return the segments entry ID of this asset list entry asset entry rel
	 */
	@Override
	public long getSegmentsEntryId() {
		return model.getSegmentsEntryId();
	}

	/**
	 * Returns the user ID of this asset list entry asset entry rel.
	 *
	 * @return the user ID of this asset list entry asset entry rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this asset list entry asset entry rel.
	 *
	 * @return the user name of this asset list entry asset entry rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this asset list entry asset entry rel.
	 *
	 * @return the user uuid of this asset list entry asset entry rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this asset list entry asset entry rel.
	 *
	 * @return the uuid of this asset list entry asset entry rel
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset list entry asset entry rel model instance should use the <code>AssetListEntryAssetEntryRel</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the asset entry ID of this asset list entry asset entry rel.
	 *
	 * @param assetEntryId the asset entry ID of this asset list entry asset entry rel
	 */
	@Override
	public void setAssetEntryId(long assetEntryId) {
		model.setAssetEntryId(assetEntryId);
	}

	@Override
	public void setAssetEntryUuid(String assetEntryUuid) {
		model.setAssetEntryUuid(assetEntryUuid);
	}

	/**
	 * Sets the asset list entry asset entry rel ID of this asset list entry asset entry rel.
	 *
	 * @param assetListEntryAssetEntryRelId the asset list entry asset entry rel ID of this asset list entry asset entry rel
	 */
	@Override
	public void setAssetListEntryAssetEntryRelId(
		long assetListEntryAssetEntryRelId) {

		model.setAssetListEntryAssetEntryRelId(assetListEntryAssetEntryRelId);
	}

	/**
	 * Sets the asset list entry ID of this asset list entry asset entry rel.
	 *
	 * @param assetListEntryId the asset list entry ID of this asset list entry asset entry rel
	 */
	@Override
	public void setAssetListEntryId(long assetListEntryId) {
		model.setAssetListEntryId(assetListEntryId);
	}

	/**
	 * Sets the company ID of this asset list entry asset entry rel.
	 *
	 * @param companyId the company ID of this asset list entry asset entry rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this asset list entry asset entry rel.
	 *
	 * @param createDate the create date of this asset list entry asset entry rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this asset list entry asset entry rel.
	 *
	 * @param groupId the group ID of this asset list entry asset entry rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this asset list entry asset entry rel.
	 *
	 * @param lastPublishDate the last publish date of this asset list entry asset entry rel
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this asset list entry asset entry rel.
	 *
	 * @param modifiedDate the modified date of this asset list entry asset entry rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this asset list entry asset entry rel.
	 *
	 * @param mvccVersion the mvcc version of this asset list entry asset entry rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the position of this asset list entry asset entry rel.
	 *
	 * @param position the position of this asset list entry asset entry rel
	 */
	@Override
	public void setPosition(int position) {
		model.setPosition(position);
	}

	/**
	 * Sets the primary key of this asset list entry asset entry rel.
	 *
	 * @param primaryKey the primary key of this asset list entry asset entry rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the segments entry ID of this asset list entry asset entry rel.
	 *
	 * @param segmentsEntryId the segments entry ID of this asset list entry asset entry rel
	 */
	@Override
	public void setSegmentsEntryId(long segmentsEntryId) {
		model.setSegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Sets the user ID of this asset list entry asset entry rel.
	 *
	 * @param userId the user ID of this asset list entry asset entry rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this asset list entry asset entry rel.
	 *
	 * @param userName the user name of this asset list entry asset entry rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this asset list entry asset entry rel.
	 *
	 * @param userUuid the user uuid of this asset list entry asset entry rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this asset list entry asset entry rel.
	 *
	 * @param uuid the uuid of this asset list entry asset entry rel
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
	protected AssetListEntryAssetEntryRelWrapper wrap(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {

		return new AssetListEntryAssetEntryRelWrapper(
			assetListEntryAssetEntryRel);
	}

}