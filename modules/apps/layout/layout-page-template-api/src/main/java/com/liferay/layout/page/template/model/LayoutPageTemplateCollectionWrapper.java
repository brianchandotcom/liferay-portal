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

package com.liferay.layout.page.template.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

/**
 * <p>
 * This class is a wrapper for {@link LayoutPageTemplateCollection}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateCollection
 * @generated
 */
@ProviderType
public class LayoutPageTemplateCollectionWrapper extends BaseModelWrapper<LayoutPageTemplateCollection>
	implements LayoutPageTemplateCollection,
		ModelWrapper<LayoutPageTemplateCollection> {
	public LayoutPageTemplateCollectionWrapper(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {
		super(layoutPageTemplateCollection);
	}

	/**
	* Returns the company ID of this layout page template collection.
	*
	* @return the company ID of this layout page template collection
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this layout page template collection.
	*
	* @return the create date of this layout page template collection
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the description of this layout page template collection.
	*
	* @return the description of this layout page template collection
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the group ID of this layout page template collection.
	*
	* @return the group ID of this layout page template collection
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the last publish date of this layout page template collection.
	*
	* @return the last publish date of this layout page template collection
	*/
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	* Returns the layout page template collection ID of this layout page template collection.
	*
	* @return the layout page template collection ID of this layout page template collection
	*/
	@Override
	public long getLayoutPageTemplateCollectionId() {
		return model.getLayoutPageTemplateCollectionId();
	}

	/**
	* Returns the modified date of this layout page template collection.
	*
	* @return the modified date of this layout page template collection
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the name of this layout page template collection.
	*
	* @return the name of this layout page template collection
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the primary key of this layout page template collection.
	*
	* @return the primary key of this layout page template collection
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this layout page template collection.
	*
	* @return the user ID of this layout page template collection
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this layout page template collection.
	*
	* @return the user name of this layout page template collection
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this layout page template collection.
	*
	* @return the user uuid of this layout page template collection
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this layout page template collection.
	*
	* @return the uuid of this layout page template collection
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
	* Sets the company ID of this layout page template collection.
	*
	* @param companyId the company ID of this layout page template collection
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this layout page template collection.
	*
	* @param createDate the create date of this layout page template collection
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the description of this layout page template collection.
	*
	* @param description the description of this layout page template collection
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the group ID of this layout page template collection.
	*
	* @param groupId the group ID of this layout page template collection
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this layout page template collection.
	*
	* @param lastPublishDate the last publish date of this layout page template collection
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the layout page template collection ID of this layout page template collection.
	*
	* @param layoutPageTemplateCollectionId the layout page template collection ID of this layout page template collection
	*/
	@Override
	public void setLayoutPageTemplateCollectionId(
		long layoutPageTemplateCollectionId) {
		model.setLayoutPageTemplateCollectionId(layoutPageTemplateCollectionId);
	}

	/**
	* Sets the modified date of this layout page template collection.
	*
	* @param modifiedDate the modified date of this layout page template collection
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this layout page template collection.
	*
	* @param name the name of this layout page template collection
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the primary key of this layout page template collection.
	*
	* @param primaryKey the primary key of this layout page template collection
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this layout page template collection.
	*
	* @param userId the user ID of this layout page template collection
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this layout page template collection.
	*
	* @param userName the user name of this layout page template collection
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this layout page template collection.
	*
	* @param userUuid the user uuid of this layout page template collection
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this layout page template collection.
	*
	* @param uuid the uuid of this layout page template collection
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
	protected LayoutPageTemplateCollectionWrapper wrap(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {
		return new LayoutPageTemplateCollectionWrapper(layoutPageTemplateCollection);
	}
}