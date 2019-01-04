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

package com.liferay.changeset.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

/**
 * <p>
 * This class is a wrapper for {@link ChangesetCollection}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetCollection
 * @generated
 */
@ProviderType
public class ChangesetCollectionWrapper extends BaseModelWrapper<ChangesetCollection>
	implements ChangesetCollection, ModelWrapper<ChangesetCollection> {
	public ChangesetCollectionWrapper(ChangesetCollection changesetCollection) {
		super(changesetCollection);
	}

	/**
	* Returns the changeset collection ID of this changeset collection.
	*
	* @return the changeset collection ID of this changeset collection
	*/
	@Override
	public long getChangesetCollectionId() {
		return model.getChangesetCollectionId();
	}

	/**
	* Returns the company ID of this changeset collection.
	*
	* @return the company ID of this changeset collection
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this changeset collection.
	*
	* @return the create date of this changeset collection
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the description of this changeset collection.
	*
	* @return the description of this changeset collection
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the group ID of this changeset collection.
	*
	* @return the group ID of this changeset collection
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the modified date of this changeset collection.
	*
	* @return the modified date of this changeset collection
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the name of this changeset collection.
	*
	* @return the name of this changeset collection
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the primary key of this changeset collection.
	*
	* @return the primary key of this changeset collection
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this changeset collection.
	*
	* @return the user ID of this changeset collection
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this changeset collection.
	*
	* @return the user name of this changeset collection
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this changeset collection.
	*
	* @return the user uuid of this changeset collection
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the changeset collection ID of this changeset collection.
	*
	* @param changesetCollectionId the changeset collection ID of this changeset collection
	*/
	@Override
	public void setChangesetCollectionId(long changesetCollectionId) {
		model.setChangesetCollectionId(changesetCollectionId);
	}

	/**
	* Sets the company ID of this changeset collection.
	*
	* @param companyId the company ID of this changeset collection
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this changeset collection.
	*
	* @param createDate the create date of this changeset collection
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the description of this changeset collection.
	*
	* @param description the description of this changeset collection
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the group ID of this changeset collection.
	*
	* @param groupId the group ID of this changeset collection
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this changeset collection.
	*
	* @param modifiedDate the modified date of this changeset collection
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this changeset collection.
	*
	* @param name the name of this changeset collection
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the primary key of this changeset collection.
	*
	* @param primaryKey the primary key of this changeset collection
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this changeset collection.
	*
	* @param userId the user ID of this changeset collection
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this changeset collection.
	*
	* @param userName the user name of this changeset collection
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this changeset collection.
	*
	* @param userUuid the user uuid of this changeset collection
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected ChangesetCollectionWrapper wrap(
		ChangesetCollection changesetCollection) {
		return new ChangesetCollectionWrapper(changesetCollection);
	}
}