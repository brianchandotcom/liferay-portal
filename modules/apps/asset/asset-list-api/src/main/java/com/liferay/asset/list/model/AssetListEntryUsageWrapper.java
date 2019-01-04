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
 * This class is a wrapper for {@link AssetListEntryUsage}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryUsage
 * @generated
 */
@ProviderType
public class AssetListEntryUsageWrapper extends BaseModelWrapper<AssetListEntryUsage>
	implements AssetListEntryUsage, ModelWrapper<AssetListEntryUsage> {
	public AssetListEntryUsageWrapper(AssetListEntryUsage assetListEntryUsage) {
		super(assetListEntryUsage);
	}

	/**
	* Returns the asset list entry ID of this asset list entry usage.
	*
	* @return the asset list entry ID of this asset list entry usage
	*/
	@Override
	public long getAssetListEntryId() {
		return model.getAssetListEntryId();
	}

	/**
	* Returns the asset list entry usage ID of this asset list entry usage.
	*
	* @return the asset list entry usage ID of this asset list entry usage
	*/
	@Override
	public long getAssetListEntryUsageId() {
		return model.getAssetListEntryUsageId();
	}

	/**
	* Returns the fully qualified class name of this asset list entry usage.
	*
	* @return the fully qualified class name of this asset list entry usage
	*/
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	* Returns the class name ID of this asset list entry usage.
	*
	* @return the class name ID of this asset list entry usage
	*/
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	* Returns the class pk of this asset list entry usage.
	*
	* @return the class pk of this asset list entry usage
	*/
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	* Returns the company ID of this asset list entry usage.
	*
	* @return the company ID of this asset list entry usage
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this asset list entry usage.
	*
	* @return the create date of this asset list entry usage
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the group ID of this asset list entry usage.
	*
	* @return the group ID of this asset list entry usage
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the last publish date of this asset list entry usage.
	*
	* @return the last publish date of this asset list entry usage
	*/
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	* Returns the modified date of this asset list entry usage.
	*
	* @return the modified date of this asset list entry usage
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the portlet ID of this asset list entry usage.
	*
	* @return the portlet ID of this asset list entry usage
	*/
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	* Returns the primary key of this asset list entry usage.
	*
	* @return the primary key of this asset list entry usage
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this asset list entry usage.
	*
	* @return the user ID of this asset list entry usage
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this asset list entry usage.
	*
	* @return the user name of this asset list entry usage
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this asset list entry usage.
	*
	* @return the user uuid of this asset list entry usage
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this asset list entry usage.
	*
	* @return the uuid of this asset list entry usage
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
	* Sets the asset list entry ID of this asset list entry usage.
	*
	* @param assetListEntryId the asset list entry ID of this asset list entry usage
	*/
	@Override
	public void setAssetListEntryId(long assetListEntryId) {
		model.setAssetListEntryId(assetListEntryId);
	}

	/**
	* Sets the asset list entry usage ID of this asset list entry usage.
	*
	* @param assetListEntryUsageId the asset list entry usage ID of this asset list entry usage
	*/
	@Override
	public void setAssetListEntryUsageId(long assetListEntryUsageId) {
		model.setAssetListEntryUsageId(assetListEntryUsageId);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	* Sets the class name ID of this asset list entry usage.
	*
	* @param classNameId the class name ID of this asset list entry usage
	*/
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this asset list entry usage.
	*
	* @param classPK the class pk of this asset list entry usage
	*/
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this asset list entry usage.
	*
	* @param companyId the company ID of this asset list entry usage
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this asset list entry usage.
	*
	* @param createDate the create date of this asset list entry usage
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the group ID of this asset list entry usage.
	*
	* @param groupId the group ID of this asset list entry usage
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this asset list entry usage.
	*
	* @param lastPublishDate the last publish date of this asset list entry usage
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this asset list entry usage.
	*
	* @param modifiedDate the modified date of this asset list entry usage
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the portlet ID of this asset list entry usage.
	*
	* @param portletId the portlet ID of this asset list entry usage
	*/
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	* Sets the primary key of this asset list entry usage.
	*
	* @param primaryKey the primary key of this asset list entry usage
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this asset list entry usage.
	*
	* @param userId the user ID of this asset list entry usage
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this asset list entry usage.
	*
	* @param userName the user name of this asset list entry usage
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this asset list entry usage.
	*
	* @param userUuid the user uuid of this asset list entry usage
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this asset list entry usage.
	*
	* @param uuid the uuid of this asset list entry usage
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
	protected AssetListEntryUsageWrapper wrap(
		AssetListEntryUsage assetListEntryUsage) {
		return new AssetListEntryUsageWrapper(assetListEntryUsage);
	}
}