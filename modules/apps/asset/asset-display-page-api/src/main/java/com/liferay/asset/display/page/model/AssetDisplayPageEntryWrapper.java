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
 * This class is a wrapper for {@link AssetDisplayPageEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageEntry
 * @generated
 */
public class AssetDisplayPageEntryWrapper
	extends BaseModelWrapper<AssetDisplayPageEntry>
	implements AssetDisplayPageEntry, ModelWrapper<AssetDisplayPageEntry> {

	public AssetDisplayPageEntryWrapper(
		AssetDisplayPageEntry assetDisplayPageEntry) {

		super(assetDisplayPageEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
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
		attributes.put("plid", getPlid());

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

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}
	}

	/**
	 * Returns the asset display page entry ID of this asset display page entry.
	 *
	 * @return the asset display page entry ID of this asset display page entry
	 */
	@Override
	public long getAssetDisplayPageEntryId() {
		return model.getAssetDisplayPageEntryId();
	}

	/**
	 * Returns the fully qualified class name of this asset display page entry.
	 *
	 * @return the fully qualified class name of this asset display page entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this asset display page entry.
	 *
	 * @return the class name ID of this asset display page entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this asset display page entry.
	 *
	 * @return the class pk of this asset display page entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this asset display page entry.
	 *
	 * @return the company ID of this asset display page entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this asset display page entry.
	 *
	 * @return the create date of this asset display page entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this asset display page entry.
	 *
	 * @return the ct collection ID of this asset display page entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this asset display page entry.
	 *
	 * @return the group ID of this asset display page entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the layout page template entry ID of this asset display page entry.
	 *
	 * @return the layout page template entry ID of this asset display page entry
	 */
	@Override
	public long getLayoutPageTemplateEntryId() {
		return model.getLayoutPageTemplateEntryId();
	}

	/**
	 * Returns the modified date of this asset display page entry.
	 *
	 * @return the modified date of this asset display page entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this asset display page entry.
	 *
	 * @return the mvcc version of this asset display page entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the plid of this asset display page entry.
	 *
	 * @return the plid of this asset display page entry
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the primary key of this asset display page entry.
	 *
	 * @return the primary key of this asset display page entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this asset display page entry.
	 *
	 * @return the type of this asset display page entry
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this asset display page entry.
	 *
	 * @return the user ID of this asset display page entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this asset display page entry.
	 *
	 * @return the user name of this asset display page entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this asset display page entry.
	 *
	 * @return the user uuid of this asset display page entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this asset display page entry.
	 *
	 * @return the uuid of this asset display page entry
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
	 * Sets the asset display page entry ID of this asset display page entry.
	 *
	 * @param assetDisplayPageEntryId the asset display page entry ID of this asset display page entry
	 */
	@Override
	public void setAssetDisplayPageEntryId(long assetDisplayPageEntryId) {
		model.setAssetDisplayPageEntryId(assetDisplayPageEntryId);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this asset display page entry.
	 *
	 * @param classNameId the class name ID of this asset display page entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this asset display page entry.
	 *
	 * @param classPK the class pk of this asset display page entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this asset display page entry.
	 *
	 * @param companyId the company ID of this asset display page entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this asset display page entry.
	 *
	 * @param createDate the create date of this asset display page entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this asset display page entry.
	 *
	 * @param ctCollectionId the ct collection ID of this asset display page entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this asset display page entry.
	 *
	 * @param groupId the group ID of this asset display page entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the layout page template entry ID of this asset display page entry.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID of this asset display page entry
	 */
	@Override
	public void setLayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {
		model.setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
	}

	/**
	 * Sets the modified date of this asset display page entry.
	 *
	 * @param modifiedDate the modified date of this asset display page entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this asset display page entry.
	 *
	 * @param mvccVersion the mvcc version of this asset display page entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the plid of this asset display page entry.
	 *
	 * @param plid the plid of this asset display page entry
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the primary key of this asset display page entry.
	 *
	 * @param primaryKey the primary key of this asset display page entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this asset display page entry.
	 *
	 * @param type the type of this asset display page entry
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this asset display page entry.
	 *
	 * @param userId the user ID of this asset display page entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this asset display page entry.
	 *
	 * @param userName the user name of this asset display page entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this asset display page entry.
	 *
	 * @param userUuid the user uuid of this asset display page entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this asset display page entry.
	 *
	 * @param uuid the uuid of this asset display page entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<AssetDisplayPageEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<AssetDisplayPageEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected AssetDisplayPageEntryWrapper wrap(
		AssetDisplayPageEntry assetDisplayPageEntry) {

		return new AssetDisplayPageEntryWrapper(assetDisplayPageEntry);
	}

}