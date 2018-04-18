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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link Changeset}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Changeset
 * @generated
 */
@ProviderType
public class ChangesetWrapper implements Changeset, ModelWrapper<Changeset> {
	public ChangesetWrapper(Changeset changeset) {
		_changeset = changeset;
	}

	@Override
	public Class<?> getModelClass() {
		return Changeset.class;
	}

	@Override
	public String getModelClassName() {
		return Changeset.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("changesetId", getChangesetId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long changesetId = (Long)attributes.get("changesetId");

		if (changesetId != null) {
			setChangesetId(changesetId);
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

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new ChangesetWrapper((Changeset)_changeset.clone());
	}

	@Override
	public int compareTo(Changeset changeset) {
		return _changeset.compareTo(changeset);
	}

	/**
	* Returns the changeset ID of this changeset.
	*
	* @return the changeset ID of this changeset
	*/
	@Override
	public long getChangesetId() {
		return _changeset.getChangesetId();
	}

	/**
	* Returns the company ID of this changeset.
	*
	* @return the company ID of this changeset
	*/
	@Override
	public long getCompanyId() {
		return _changeset.getCompanyId();
	}

	/**
	* Returns the create date of this changeset.
	*
	* @return the create date of this changeset
	*/
	@Override
	public Date getCreateDate() {
		return _changeset.getCreateDate();
	}

	/**
	* Returns the description of this changeset.
	*
	* @return the description of this changeset
	*/
	@Override
	public java.lang.String getDescription() {
		return _changeset.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _changeset.getExpandoBridge();
	}

	/**
	* Returns the group ID of this changeset.
	*
	* @return the group ID of this changeset
	*/
	@Override
	public long getGroupId() {
		return _changeset.getGroupId();
	}

	/**
	* Returns the modified date of this changeset.
	*
	* @return the modified date of this changeset
	*/
	@Override
	public Date getModifiedDate() {
		return _changeset.getModifiedDate();
	}

	/**
	* Returns the name of this changeset.
	*
	* @return the name of this changeset
	*/
	@Override
	public java.lang.String getName() {
		return _changeset.getName();
	}

	/**
	* Returns the primary key of this changeset.
	*
	* @return the primary key of this changeset
	*/
	@Override
	public long getPrimaryKey() {
		return _changeset.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _changeset.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this changeset.
	*
	* @return the user ID of this changeset
	*/
	@Override
	public long getUserId() {
		return _changeset.getUserId();
	}

	/**
	* Returns the user name of this changeset.
	*
	* @return the user name of this changeset
	*/
	@Override
	public java.lang.String getUserName() {
		return _changeset.getUserName();
	}

	/**
	* Returns the user uuid of this changeset.
	*
	* @return the user uuid of this changeset
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _changeset.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _changeset.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _changeset.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _changeset.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _changeset.isNew();
	}

	@Override
	public void persist() {
		_changeset.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_changeset.setCachedModel(cachedModel);
	}

	/**
	* Sets the changeset ID of this changeset.
	*
	* @param changesetId the changeset ID of this changeset
	*/
	@Override
	public void setChangesetId(long changesetId) {
		_changeset.setChangesetId(changesetId);
	}

	/**
	* Sets the company ID of this changeset.
	*
	* @param companyId the company ID of this changeset
	*/
	@Override
	public void setCompanyId(long companyId) {
		_changeset.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this changeset.
	*
	* @param createDate the create date of this changeset
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_changeset.setCreateDate(createDate);
	}

	/**
	* Sets the description of this changeset.
	*
	* @param description the description of this changeset
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_changeset.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_changeset.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_changeset.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_changeset.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this changeset.
	*
	* @param groupId the group ID of this changeset
	*/
	@Override
	public void setGroupId(long groupId) {
		_changeset.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this changeset.
	*
	* @param modifiedDate the modified date of this changeset
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_changeset.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this changeset.
	*
	* @param name the name of this changeset
	*/
	@Override
	public void setName(java.lang.String name) {
		_changeset.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_changeset.setNew(n);
	}

	/**
	* Sets the primary key of this changeset.
	*
	* @param primaryKey the primary key of this changeset
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_changeset.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_changeset.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this changeset.
	*
	* @param userId the user ID of this changeset
	*/
	@Override
	public void setUserId(long userId) {
		_changeset.setUserId(userId);
	}

	/**
	* Sets the user name of this changeset.
	*
	* @param userName the user name of this changeset
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_changeset.setUserName(userName);
	}

	/**
	* Sets the user uuid of this changeset.
	*
	* @param userUuid the user uuid of this changeset
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_changeset.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<Changeset> toCacheModel() {
		return _changeset.toCacheModel();
	}

	@Override
	public Changeset toEscapedModel() {
		return new ChangesetWrapper(_changeset.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _changeset.toString();
	}

	@Override
	public Changeset toUnescapedModel() {
		return new ChangesetWrapper(_changeset.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _changeset.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangesetWrapper)) {
			return false;
		}

		ChangesetWrapper changesetWrapper = (ChangesetWrapper)obj;

		if (Objects.equals(_changeset, changesetWrapper._changeset)) {
			return true;
		}

		return false;
	}

	@Override
	public Changeset getWrappedModel() {
		return _changeset;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _changeset.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _changeset.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_changeset.resetOriginalValues();
	}

	private final Changeset _changeset;
}