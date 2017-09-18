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

package com.liferay.modern.site.building.fragment.model;

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
 * This class is a wrapper for {@link MSBFragmentCollection}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentCollection
 * @generated
 */
@ProviderType
public class MSBFragmentCollectionWrapper implements MSBFragmentCollection,
	ModelWrapper<MSBFragmentCollection> {
	public MSBFragmentCollectionWrapper(
		MSBFragmentCollection msbFragmentCollection) {
		_msbFragmentCollection = msbFragmentCollection;
	}

	@Override
	public Class<?> getModelClass() {
		return MSBFragmentCollection.class;
	}

	@Override
	public String getModelClassName() {
		return MSBFragmentCollection.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("msbFragmentCollectionId", getMsbFragmentCollectionId());
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
		Long msbFragmentCollectionId = (Long)attributes.get(
				"msbFragmentCollectionId");

		if (msbFragmentCollectionId != null) {
			setMsbFragmentCollectionId(msbFragmentCollectionId);
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
		return new MSBFragmentCollectionWrapper((MSBFragmentCollection)_msbFragmentCollection.clone());
	}

	@Override
	public int compareTo(MSBFragmentCollection msbFragmentCollection) {
		return _msbFragmentCollection.compareTo(msbFragmentCollection);
	}

	/**
	* Returns the company ID of this msb fragment collection.
	*
	* @return the company ID of this msb fragment collection
	*/
	@Override
	public long getCompanyId() {
		return _msbFragmentCollection.getCompanyId();
	}

	/**
	* Returns the create date of this msb fragment collection.
	*
	* @return the create date of this msb fragment collection
	*/
	@Override
	public Date getCreateDate() {
		return _msbFragmentCollection.getCreateDate();
	}

	/**
	* Returns the description of this msb fragment collection.
	*
	* @return the description of this msb fragment collection
	*/
	@Override
	public java.lang.String getDescription() {
		return _msbFragmentCollection.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _msbFragmentCollection.getExpandoBridge();
	}

	/**
	* Returns the group ID of this msb fragment collection.
	*
	* @return the group ID of this msb fragment collection
	*/
	@Override
	public long getGroupId() {
		return _msbFragmentCollection.getGroupId();
	}

	/**
	* Returns the modified date of this msb fragment collection.
	*
	* @return the modified date of this msb fragment collection
	*/
	@Override
	public Date getModifiedDate() {
		return _msbFragmentCollection.getModifiedDate();
	}

	/**
	* Returns the msb fragment collection ID of this msb fragment collection.
	*
	* @return the msb fragment collection ID of this msb fragment collection
	*/
	@Override
	public long getMsbFragmentCollectionId() {
		return _msbFragmentCollection.getMsbFragmentCollectionId();
	}

	/**
	* Returns the name of this msb fragment collection.
	*
	* @return the name of this msb fragment collection
	*/
	@Override
	public java.lang.String getName() {
		return _msbFragmentCollection.getName();
	}

	/**
	* Returns the primary key of this msb fragment collection.
	*
	* @return the primary key of this msb fragment collection
	*/
	@Override
	public long getPrimaryKey() {
		return _msbFragmentCollection.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _msbFragmentCollection.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this msb fragment collection.
	*
	* @return the user ID of this msb fragment collection
	*/
	@Override
	public long getUserId() {
		return _msbFragmentCollection.getUserId();
	}

	/**
	* Returns the user name of this msb fragment collection.
	*
	* @return the user name of this msb fragment collection
	*/
	@Override
	public java.lang.String getUserName() {
		return _msbFragmentCollection.getUserName();
	}

	/**
	* Returns the user uuid of this msb fragment collection.
	*
	* @return the user uuid of this msb fragment collection
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _msbFragmentCollection.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _msbFragmentCollection.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _msbFragmentCollection.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _msbFragmentCollection.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _msbFragmentCollection.isNew();
	}

	@Override
	public void persist() {
		_msbFragmentCollection.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_msbFragmentCollection.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this msb fragment collection.
	*
	* @param companyId the company ID of this msb fragment collection
	*/
	@Override
	public void setCompanyId(long companyId) {
		_msbFragmentCollection.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this msb fragment collection.
	*
	* @param createDate the create date of this msb fragment collection
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_msbFragmentCollection.setCreateDate(createDate);
	}

	/**
	* Sets the description of this msb fragment collection.
	*
	* @param description the description of this msb fragment collection
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_msbFragmentCollection.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_msbFragmentCollection.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_msbFragmentCollection.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_msbFragmentCollection.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this msb fragment collection.
	*
	* @param groupId the group ID of this msb fragment collection
	*/
	@Override
	public void setGroupId(long groupId) {
		_msbFragmentCollection.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this msb fragment collection.
	*
	* @param modifiedDate the modified date of this msb fragment collection
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_msbFragmentCollection.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the msb fragment collection ID of this msb fragment collection.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID of this msb fragment collection
	*/
	@Override
	public void setMsbFragmentCollectionId(long msbFragmentCollectionId) {
		_msbFragmentCollection.setMsbFragmentCollectionId(msbFragmentCollectionId);
	}

	/**
	* Sets the name of this msb fragment collection.
	*
	* @param name the name of this msb fragment collection
	*/
	@Override
	public void setName(java.lang.String name) {
		_msbFragmentCollection.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_msbFragmentCollection.setNew(n);
	}

	/**
	* Sets the primary key of this msb fragment collection.
	*
	* @param primaryKey the primary key of this msb fragment collection
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_msbFragmentCollection.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_msbFragmentCollection.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this msb fragment collection.
	*
	* @param userId the user ID of this msb fragment collection
	*/
	@Override
	public void setUserId(long userId) {
		_msbFragmentCollection.setUserId(userId);
	}

	/**
	* Sets the user name of this msb fragment collection.
	*
	* @param userName the user name of this msb fragment collection
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_msbFragmentCollection.setUserName(userName);
	}

	/**
	* Sets the user uuid of this msb fragment collection.
	*
	* @param userUuid the user uuid of this msb fragment collection
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_msbFragmentCollection.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<MSBFragmentCollection> toCacheModel() {
		return _msbFragmentCollection.toCacheModel();
	}

	@Override
	public MSBFragmentCollection toEscapedModel() {
		return new MSBFragmentCollectionWrapper(_msbFragmentCollection.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _msbFragmentCollection.toString();
	}

	@Override
	public MSBFragmentCollection toUnescapedModel() {
		return new MSBFragmentCollectionWrapper(_msbFragmentCollection.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _msbFragmentCollection.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MSBFragmentCollectionWrapper)) {
			return false;
		}

		MSBFragmentCollectionWrapper msbFragmentCollectionWrapper = (MSBFragmentCollectionWrapper)obj;

		if (Objects.equals(_msbFragmentCollection,
					msbFragmentCollectionWrapper._msbFragmentCollection)) {
			return true;
		}

		return false;
	}

	@Override
	public MSBFragmentCollection getWrappedModel() {
		return _msbFragmentCollection;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _msbFragmentCollection.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _msbFragmentCollection.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_msbFragmentCollection.resetOriginalValues();
	}

	private final MSBFragmentCollection _msbFragmentCollection;
}