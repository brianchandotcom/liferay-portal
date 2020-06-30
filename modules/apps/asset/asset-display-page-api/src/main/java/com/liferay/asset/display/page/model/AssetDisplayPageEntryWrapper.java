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
 * This class is a wrapper for {@link AssetDisplayPageEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageEntry
 * @generated
 */
public class AssetDisplayPageEntryWrapper
	implements AssetDisplayPageEntry, ModelWrapper<AssetDisplayPageEntry> {

	public AssetDisplayPageEntryWrapper(
		AssetDisplayPageEntry assetDisplayPageEntry) {

		_assetDisplayPageEntry = assetDisplayPageEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetDisplayPageEntry.class;
	}

	@Override
	public String getModelClassName() {
		return AssetDisplayPageEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("assetDisplayPageEntryId", getAssetDisplayPageEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put(
			"layoutPageTemplateEntryId", getLayoutPageTemplateEntryId());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long assetDisplayPageEntryId = (Long)attributes.get(
			"assetDisplayPageEntryId");

		if (assetDisplayPageEntryId != null) {
			setAssetDisplayPageEntryId(assetDisplayPageEntryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long layoutPageTemplateEntryId = (Long)attributes.get(
			"layoutPageTemplateEntryId");

		if (layoutPageTemplateEntryId != null) {
			setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public Object clone() {
		return new AssetDisplayPageEntryWrapper(
			(AssetDisplayPageEntry)_assetDisplayPageEntry.clone());
	}

	@Override
	public int compareTo(AssetDisplayPageEntry assetDisplayPageEntry) {
		return _assetDisplayPageEntry.compareTo(assetDisplayPageEntry);
	}

	/**
	 * Returns the asset display page entry ID of this asset display page entry.
	 *
	 * @return the asset display page entry ID of this asset display page entry
	 */
	@Override
	public long getAssetDisplayPageEntryId() {
		return _assetDisplayPageEntry.getAssetDisplayPageEntryId();
	}

	/**
	 * Returns the fully qualified class name of this asset display page entry.
	 *
	 * @return the fully qualified class name of this asset display page entry
	 */
	@Override
	public String getClassName() {
		return _assetDisplayPageEntry.getClassName();
	}

	/**
	 * Returns the class name ID of this asset display page entry.
	 *
	 * @return the class name ID of this asset display page entry
	 */
	@Override
	public long getClassNameId() {
		return _assetDisplayPageEntry.getClassNameId();
	}

	/**
	 * Returns the class pk of this asset display page entry.
	 *
	 * @return the class pk of this asset display page entry
	 */
	@Override
	public long getClassPK() {
		return _assetDisplayPageEntry.getClassPK();
	}

	/**
	 * Returns the company ID of this asset display page entry.
	 *
	 * @return the company ID of this asset display page entry
	 */
	@Override
	public long getCompanyId() {
		return _assetDisplayPageEntry.getCompanyId();
	}

	/**
	 * Returns the create date of this asset display page entry.
	 *
	 * @return the create date of this asset display page entry
	 */
	@Override
	public Date getCreateDate() {
		return _assetDisplayPageEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetDisplayPageEntry.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this asset display page entry.
	 *
	 * @return the group ID of this asset display page entry
	 */
	@Override
	public long getGroupId() {
		return _assetDisplayPageEntry.getGroupId();
	}

	/**
	 * Returns the layout page template entry ID of this asset display page entry.
	 *
	 * @return the layout page template entry ID of this asset display page entry
	 */
	@Override
	public long getLayoutPageTemplateEntryId() {
		return _assetDisplayPageEntry.getLayoutPageTemplateEntryId();
	}

	/**
	 * Returns the modified date of this asset display page entry.
	 *
	 * @return the modified date of this asset display page entry
	 */
	@Override
	public Date getModifiedDate() {
		return _assetDisplayPageEntry.getModifiedDate();
	}

	/**
	 * Returns the primary key of this asset display page entry.
	 *
	 * @return the primary key of this asset display page entry
	 */
	@Override
	public long getPrimaryKey() {
		return _assetDisplayPageEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetDisplayPageEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the type of this asset display page entry.
	 *
	 * @return the type of this asset display page entry
	 */
	@Override
	public int getType() {
		return _assetDisplayPageEntry.getType();
	}

	/**
	 * Returns the user ID of this asset display page entry.
	 *
	 * @return the user ID of this asset display page entry
	 */
	@Override
	public long getUserId() {
		return _assetDisplayPageEntry.getUserId();
	}

	/**
	 * Returns the user name of this asset display page entry.
	 *
	 * @return the user name of this asset display page entry
	 */
	@Override
	public String getUserName() {
		return _assetDisplayPageEntry.getUserName();
	}

	/**
	 * Returns the user uuid of this asset display page entry.
	 *
	 * @return the user uuid of this asset display page entry
	 */
	@Override
	public String getUserUuid() {
		return _assetDisplayPageEntry.getUserUuid();
	}

	/**
	 * Returns the uuid of this asset display page entry.
	 *
	 * @return the uuid of this asset display page entry
	 */
	@Override
	public String getUuid() {
		return _assetDisplayPageEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _assetDisplayPageEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetDisplayPageEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetDisplayPageEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetDisplayPageEntry.isNew();
	}

	@Override
	public void persist() {
		_assetDisplayPageEntry.persist();
	}

	/**
	 * Sets the asset display page entry ID of this asset display page entry.
	 *
	 * @param assetDisplayPageEntryId the asset display page entry ID of this asset display page entry
	 */
	@Override
	public void setAssetDisplayPageEntryId(long assetDisplayPageEntryId) {
		_assetDisplayPageEntry.setAssetDisplayPageEntryId(
			assetDisplayPageEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetDisplayPageEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_assetDisplayPageEntry.setClassName(className);
	}

	/**
	 * Sets the class name ID of this asset display page entry.
	 *
	 * @param classNameId the class name ID of this asset display page entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		_assetDisplayPageEntry.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this asset display page entry.
	 *
	 * @param classPK the class pk of this asset display page entry
	 */
	@Override
	public void setClassPK(long classPK) {
		_assetDisplayPageEntry.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this asset display page entry.
	 *
	 * @param companyId the company ID of this asset display page entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_assetDisplayPageEntry.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this asset display page entry.
	 *
	 * @param createDate the create date of this asset display page entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_assetDisplayPageEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_assetDisplayPageEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetDisplayPageEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetDisplayPageEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this asset display page entry.
	 *
	 * @param groupId the group ID of this asset display page entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_assetDisplayPageEntry.setGroupId(groupId);
	}

	/**
	 * Sets the layout page template entry ID of this asset display page entry.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID of this asset display page entry
	 */
	@Override
	public void setLayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {
		_assetDisplayPageEntry.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
	}

	/**
	 * Sets the modified date of this asset display page entry.
	 *
	 * @param modifiedDate the modified date of this asset display page entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_assetDisplayPageEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_assetDisplayPageEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this asset display page entry.
	 *
	 * @param primaryKey the primary key of this asset display page entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetDisplayPageEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetDisplayPageEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the type of this asset display page entry.
	 *
	 * @param type the type of this asset display page entry
	 */
	@Override
	public void setType(int type) {
		_assetDisplayPageEntry.setType(type);
	}

	/**
	 * Sets the user ID of this asset display page entry.
	 *
	 * @param userId the user ID of this asset display page entry
	 */
	@Override
	public void setUserId(long userId) {
		_assetDisplayPageEntry.setUserId(userId);
	}

	/**
	 * Sets the user name of this asset display page entry.
	 *
	 * @param userName the user name of this asset display page entry
	 */
	@Override
	public void setUserName(String userName) {
		_assetDisplayPageEntry.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this asset display page entry.
	 *
	 * @param userUuid the user uuid of this asset display page entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_assetDisplayPageEntry.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this asset display page entry.
	 *
	 * @param uuid the uuid of this asset display page entry
	 */
	@Override
	public void setUuid(String uuid) {
		_assetDisplayPageEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetDisplayPageEntry>
		toCacheModel() {

		return _assetDisplayPageEntry.toCacheModel();
	}

	@Override
	public AssetDisplayPageEntry toEscapedModel() {
		return new AssetDisplayPageEntryWrapper(
			_assetDisplayPageEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _assetDisplayPageEntry.toString();
	}

	@Override
	public AssetDisplayPageEntry toUnescapedModel() {
		return new AssetDisplayPageEntryWrapper(
			_assetDisplayPageEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _assetDisplayPageEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetDisplayPageEntryWrapper)) {
			return false;
		}

		AssetDisplayPageEntryWrapper assetDisplayPageEntryWrapper =
			(AssetDisplayPageEntryWrapper)obj;

		if (Objects.equals(
				_assetDisplayPageEntry,
				assetDisplayPageEntryWrapper._assetDisplayPageEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _assetDisplayPageEntry.getStagedModelType();
	}

	@Override
	public AssetDisplayPageEntry getWrappedModel() {
		return _assetDisplayPageEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetDisplayPageEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetDisplayPageEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetDisplayPageEntry.resetOriginalValues();
	}

	private final AssetDisplayPageEntry _assetDisplayPageEntry;

}