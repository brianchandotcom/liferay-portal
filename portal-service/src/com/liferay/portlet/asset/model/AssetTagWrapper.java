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

package com.liferay.portlet.asset.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetTag}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTag
 * @generated
 */
@ProviderType
public class AssetTagWrapper implements AssetTag, ModelWrapper<AssetTag> {
	public AssetTagWrapper(AssetTag assetTag) {
		_assetTag = assetTag;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetTag.class;
	}

	@Override
	public String getModelClassName() {
		return AssetTag.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("tagId", getTagId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("assetCount", getAssetCount());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long tagId = (Long)attributes.get("tagId");

		if (tagId != null) {
			setTagId(tagId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer assetCount = (Integer)attributes.get("assetCount");

		if (assetCount != null) {
			setAssetCount(assetCount);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new AssetTagWrapper((AssetTag)_assetTag.clone());
	}

	@Override
	public int compareTo(com.liferay.portlet.asset.model.AssetTag assetTag) {
		return _assetTag.compareTo(assetTag);
	}

	/**
	* Returns the asset count of this asset tag.
	*
	* @return the asset count of this asset tag
	*/
	@Override
	public int getAssetCount() {
		return _assetTag.getAssetCount();
	}

	/**
	* Returns the company ID of this asset tag.
	*
	* @return the company ID of this asset tag
	*/
	@Override
	public long getCompanyId() {
		return _assetTag.getCompanyId();
	}

	/**
	* Returns the create date of this asset tag.
	*
	* @return the create date of this asset tag
	*/
	@Override
	public Date getCreateDate() {
		return _assetTag.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetTag.getExpandoBridge();
	}

	/**
	* Returns the group ID of this asset tag.
	*
	* @return the group ID of this asset tag
	*/
	@Override
	public long getGroupId() {
		return _assetTag.getGroupId();
	}

	/**
	* Returns the last publish date of this asset tag.
	*
	* @return the last publish date of this asset tag
	*/
	@Override
	public Date getLastPublishDate() {
		return _assetTag.getLastPublishDate();
	}

	/**
	* Returns the modified date of this asset tag.
	*
	* @return the modified date of this asset tag
	*/
	@Override
	public Date getModifiedDate() {
		return _assetTag.getModifiedDate();
	}

	/**
	* Returns the name of this asset tag.
	*
	* @return the name of this asset tag
	*/
	@Override
	public java.lang.String getName() {
		return _assetTag.getName();
	}

	/**
	* Returns the primary key of this asset tag.
	*
	* @return the primary key of this asset tag
	*/
	@Override
	public long getPrimaryKey() {
		return _assetTag.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _assetTag.getPrimaryKeyObj();
	}

	/**
	* Returns the tag ID of this asset tag.
	*
	* @return the tag ID of this asset tag
	*/
	@Override
	public long getTagId() {
		return _assetTag.getTagId();
	}

	/**
	* Returns the user ID of this asset tag.
	*
	* @return the user ID of this asset tag
	*/
	@Override
	public long getUserId() {
		return _assetTag.getUserId();
	}

	/**
	* Returns the user name of this asset tag.
	*
	* @return the user name of this asset tag
	*/
	@Override
	public java.lang.String getUserName() {
		return _assetTag.getUserName();
	}

	/**
	* Returns the user uuid of this asset tag.
	*
	* @return the user uuid of this asset tag
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _assetTag.getUserUuid();
	}

	/**
	* Returns the uuid of this asset tag.
	*
	* @return the uuid of this asset tag
	*/
	@Override
	public java.lang.String getUuid() {
		return _assetTag.getUuid();
	}

	@Override
	public int hashCode() {
		return _assetTag.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetTag.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetTag.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetTag.isNew();
	}

	@Override
	public void persist() {
		_assetTag.persist();
	}

	/**
	* Sets the asset count of this asset tag.
	*
	* @param assetCount the asset count of this asset tag
	*/
	@Override
	public void setAssetCount(int assetCount) {
		_assetTag.setAssetCount(assetCount);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetTag.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this asset tag.
	*
	* @param companyId the company ID of this asset tag
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetTag.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this asset tag.
	*
	* @param createDate the create date of this asset tag
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_assetTag.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_assetTag.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_assetTag.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetTag.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this asset tag.
	*
	* @param groupId the group ID of this asset tag
	*/
	@Override
	public void setGroupId(long groupId) {
		_assetTag.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this asset tag.
	*
	* @param lastPublishDate the last publish date of this asset tag
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_assetTag.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this asset tag.
	*
	* @param modifiedDate the modified date of this asset tag
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_assetTag.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this asset tag.
	*
	* @param name the name of this asset tag
	*/
	@Override
	public void setName(java.lang.String name) {
		_assetTag.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_assetTag.setNew(n);
	}

	/**
	* Sets the primary key of this asset tag.
	*
	* @param primaryKey the primary key of this asset tag
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetTag.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_assetTag.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the tag ID of this asset tag.
	*
	* @param tagId the tag ID of this asset tag
	*/
	@Override
	public void setTagId(long tagId) {
		_assetTag.setTagId(tagId);
	}

	/**
	* Sets the user ID of this asset tag.
	*
	* @param userId the user ID of this asset tag
	*/
	@Override
	public void setUserId(long userId) {
		_assetTag.setUserId(userId);
	}

	/**
	* Sets the user name of this asset tag.
	*
	* @param userName the user name of this asset tag
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_assetTag.setUserName(userName);
	}

	/**
	* Sets the user uuid of this asset tag.
	*
	* @param userUuid the user uuid of this asset tag
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_assetTag.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this asset tag.
	*
	* @param uuid the uuid of this asset tag
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_assetTag.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.asset.model.AssetTag> toCacheModel() {
		return _assetTag.toCacheModel();
	}

	@Override
	public com.liferay.portlet.asset.model.AssetTag toEscapedModel() {
		return new AssetTagWrapper(_assetTag.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetTag.toString();
	}

	@Override
	public com.liferay.portlet.asset.model.AssetTag toUnescapedModel() {
		return new AssetTagWrapper(_assetTag.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _assetTag.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetTagWrapper)) {
			return false;
		}

		AssetTagWrapper assetTagWrapper = (AssetTagWrapper)obj;

		if (Validator.equals(_assetTag, assetTagWrapper._assetTag)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _assetTag.getStagedModelType();
	}

	@Override
	public AssetTag getWrappedModel() {
		return _assetTag;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetTag.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetTag.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetTag.resetOriginalValues();
	}

	private final AssetTag _assetTag;
}