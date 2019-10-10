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

package com.liferay.asset.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetEntryUsage}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryUsage
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 com.liferay.layout.model.impl.LayoutClassedModelUsageImpl}
 * @generated
 */
@Deprecated
public class AssetEntryUsageWrapper
	extends BaseModelWrapper<AssetEntryUsage>
	implements AssetEntryUsage, ModelWrapper<AssetEntryUsage> {

	public AssetEntryUsageWrapper(AssetEntryUsage assetEntryUsage) {
		super(assetEntryUsage);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("assetEntryUsageId", getAssetEntryUsageId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("assetEntryId", getAssetEntryId());
		attributes.put("containerType", getContainerType());
		attributes.put("containerKey", getContainerKey());
		attributes.put("plid", getPlid());
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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long assetEntryUsageId = (Long)attributes.get("assetEntryUsageId");

		if (assetEntryUsageId != null) {
			setAssetEntryUsageId(assetEntryUsageId);
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

		Long containerType = (Long)attributes.get("containerType");

		if (containerType != null) {
			setContainerType(containerType);
		}

		String containerKey = (String)attributes.get("containerKey");

		if (containerKey != null) {
			setContainerKey(containerKey);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
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
	 * Returns the asset entry ID of this asset entry usage.
	 *
	 * @return the asset entry ID of this asset entry usage
	 */
	@Override
	public long getAssetEntryId() {
		return model.getAssetEntryId();
	}

	/**
	 * Returns the asset entry usage ID of this asset entry usage.
	 *
	 * @return the asset entry usage ID of this asset entry usage
	 */
	@Override
	public long getAssetEntryUsageId() {
		return model.getAssetEntryUsageId();
	}

	/**
	 * Returns the company ID of this asset entry usage.
	 *
	 * @return the company ID of this asset entry usage
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container key of this asset entry usage.
	 *
	 * @return the container key of this asset entry usage
	 */
	@Override
	public String getContainerKey() {
		return model.getContainerKey();
	}

	/**
	 * Returns the container type of this asset entry usage.
	 *
	 * @return the container type of this asset entry usage
	 */
	@Override
	public long getContainerType() {
		return model.getContainerType();
	}

	/**
	 * Returns the create date of this asset entry usage.
	 *
	 * @return the create date of this asset entry usage
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this asset entry usage.
	 *
	 * @return the group ID of this asset entry usage
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this asset entry usage.
	 *
	 * @return the last publish date of this asset entry usage
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this asset entry usage.
	 *
	 * @return the modified date of this asset entry usage
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this asset entry usage.
	 *
	 * @return the mvcc version of this asset entry usage
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the plid of this asset entry usage.
	 *
	 * @return the plid of this asset entry usage
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the primary key of this asset entry usage.
	 *
	 * @return the primary key of this asset entry usage
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this asset entry usage.
	 *
	 * @return the type of this asset entry usage
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the uuid of this asset entry usage.
	 *
	 * @return the uuid of this asset entry usage
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset entry usage model instance should use the <code>AssetEntryUsage</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the asset entry ID of this asset entry usage.
	 *
	 * @param assetEntryId the asset entry ID of this asset entry usage
	 */
	@Override
	public void setAssetEntryId(long assetEntryId) {
		model.setAssetEntryId(assetEntryId);
	}

	/**
	 * Sets the asset entry usage ID of this asset entry usage.
	 *
	 * @param assetEntryUsageId the asset entry usage ID of this asset entry usage
	 */
	@Override
	public void setAssetEntryUsageId(long assetEntryUsageId) {
		model.setAssetEntryUsageId(assetEntryUsageId);
	}

	/**
	 * Sets the company ID of this asset entry usage.
	 *
	 * @param companyId the company ID of this asset entry usage
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container key of this asset entry usage.
	 *
	 * @param containerKey the container key of this asset entry usage
	 */
	@Override
	public void setContainerKey(String containerKey) {
		model.setContainerKey(containerKey);
	}

	/**
	 * Sets the container type of this asset entry usage.
	 *
	 * @param containerType the container type of this asset entry usage
	 */
	@Override
	public void setContainerType(long containerType) {
		model.setContainerType(containerType);
	}

	/**
	 * Sets the create date of this asset entry usage.
	 *
	 * @param createDate the create date of this asset entry usage
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this asset entry usage.
	 *
	 * @param groupId the group ID of this asset entry usage
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this asset entry usage.
	 *
	 * @param lastPublishDate the last publish date of this asset entry usage
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this asset entry usage.
	 *
	 * @param modifiedDate the modified date of this asset entry usage
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this asset entry usage.
	 *
	 * @param mvccVersion the mvcc version of this asset entry usage
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the plid of this asset entry usage.
	 *
	 * @param plid the plid of this asset entry usage
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the primary key of this asset entry usage.
	 *
	 * @param primaryKey the primary key of this asset entry usage
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this asset entry usage.
	 *
	 * @param type the type of this asset entry usage
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the uuid of this asset entry usage.
	 *
	 * @param uuid the uuid of this asset entry usage
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
	protected AssetEntryUsageWrapper wrap(AssetEntryUsage assetEntryUsage) {
		return new AssetEntryUsageWrapper(assetEntryUsage);
	}

}