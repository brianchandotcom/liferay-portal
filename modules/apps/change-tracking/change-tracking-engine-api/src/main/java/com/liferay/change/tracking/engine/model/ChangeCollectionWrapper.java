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

package com.liferay.change.tracking.engine.model;

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
 * This class is a wrapper for {@link ChangeCollection}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeCollection
 * @generated
 */
@ProviderType
public class ChangeCollectionWrapper implements ChangeCollection,
	ModelWrapper<ChangeCollection> {
	public ChangeCollectionWrapper(ChangeCollection changeCollection) {
		_changeCollection = changeCollection;
	}

	@Override
	public Class<?> getModelClass() {
		return ChangeCollection.class;
	}

	@Override
	public String getModelClassName() {
		return ChangeCollection.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("changeCollectionId", getChangeCollectionId());
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
		Long changeCollectionId = (Long)attributes.get("changeCollectionId");

		if (changeCollectionId != null) {
			setChangeCollectionId(changeCollectionId);
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
	public Object clone() {
		return new ChangeCollectionWrapper((ChangeCollection)_changeCollection.clone());
	}

	@Override
	public int compareTo(ChangeCollection changeCollection) {
		return _changeCollection.compareTo(changeCollection);
	}

	/**
	* Returns the change collection ID of this change collection.
	*
	* @return the change collection ID of this change collection
	*/
	@Override
	public long getChangeCollectionId() {
		return _changeCollection.getChangeCollectionId();
	}

	/**
	* Returns the company ID of this change collection.
	*
	* @return the company ID of this change collection
	*/
	@Override
	public long getCompanyId() {
		return _changeCollection.getCompanyId();
	}

	/**
	* Returns the create date of this change collection.
	*
	* @return the create date of this change collection
	*/
	@Override
	public Date getCreateDate() {
		return _changeCollection.getCreateDate();
	}

	/**
	* Returns the description of this change collection.
	*
	* @return the description of this change collection
	*/
	@Override
	public String getDescription() {
		return _changeCollection.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _changeCollection.getExpandoBridge();
	}

	/**
	* Returns the modified date of this change collection.
	*
	* @return the modified date of this change collection
	*/
	@Override
	public Date getModifiedDate() {
		return _changeCollection.getModifiedDate();
	}

	/**
	* Returns the name of this change collection.
	*
	* @return the name of this change collection
	*/
	@Override
	public String getName() {
		return _changeCollection.getName();
	}

	/**
	* Returns the primary key of this change collection.
	*
	* @return the primary key of this change collection
	*/
	@Override
	public long getPrimaryKey() {
		return _changeCollection.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _changeCollection.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this change collection.
	*
	* @return the user ID of this change collection
	*/
	@Override
	public long getUserId() {
		return _changeCollection.getUserId();
	}

	/**
	* Returns the user name of this change collection.
	*
	* @return the user name of this change collection
	*/
	@Override
	public String getUserName() {
		return _changeCollection.getUserName();
	}

	/**
	* Returns the user uuid of this change collection.
	*
	* @return the user uuid of this change collection
	*/
	@Override
	public String getUserUuid() {
		return _changeCollection.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _changeCollection.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _changeCollection.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _changeCollection.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _changeCollection.isNew();
	}

	@Override
	public void persist() {
		_changeCollection.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_changeCollection.setCachedModel(cachedModel);
	}

	/**
	* Sets the change collection ID of this change collection.
	*
	* @param changeCollectionId the change collection ID of this change collection
	*/
	@Override
	public void setChangeCollectionId(long changeCollectionId) {
		_changeCollection.setChangeCollectionId(changeCollectionId);
	}

	/**
	* Sets the company ID of this change collection.
	*
	* @param companyId the company ID of this change collection
	*/
	@Override
	public void setCompanyId(long companyId) {
		_changeCollection.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this change collection.
	*
	* @param createDate the create date of this change collection
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_changeCollection.setCreateDate(createDate);
	}

	/**
	* Sets the description of this change collection.
	*
	* @param description the description of this change collection
	*/
	@Override
	public void setDescription(String description) {
		_changeCollection.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_changeCollection.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_changeCollection.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_changeCollection.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this change collection.
	*
	* @param modifiedDate the modified date of this change collection
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_changeCollection.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this change collection.
	*
	* @param name the name of this change collection
	*/
	@Override
	public void setName(String name) {
		_changeCollection.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_changeCollection.setNew(n);
	}

	/**
	* Sets the primary key of this change collection.
	*
	* @param primaryKey the primary key of this change collection
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_changeCollection.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_changeCollection.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this change collection.
	*
	* @param userId the user ID of this change collection
	*/
	@Override
	public void setUserId(long userId) {
		_changeCollection.setUserId(userId);
	}

	/**
	* Sets the user name of this change collection.
	*
	* @param userName the user name of this change collection
	*/
	@Override
	public void setUserName(String userName) {
		_changeCollection.setUserName(userName);
	}

	/**
	* Sets the user uuid of this change collection.
	*
	* @param userUuid the user uuid of this change collection
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_changeCollection.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ChangeCollection> toCacheModel() {
		return _changeCollection.toCacheModel();
	}

	@Override
	public ChangeCollection toEscapedModel() {
		return new ChangeCollectionWrapper(_changeCollection.toEscapedModel());
	}

	@Override
	public String toString() {
		return _changeCollection.toString();
	}

	@Override
	public ChangeCollection toUnescapedModel() {
		return new ChangeCollectionWrapper(_changeCollection.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _changeCollection.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangeCollectionWrapper)) {
			return false;
		}

		ChangeCollectionWrapper changeCollectionWrapper = (ChangeCollectionWrapper)obj;

		if (Objects.equals(_changeCollection,
					changeCollectionWrapper._changeCollection)) {
			return true;
		}

		return false;
	}

	@Override
	public ChangeCollection getWrappedModel() {
		return _changeCollection;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _changeCollection.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _changeCollection.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_changeCollection.resetOriginalValues();
	}

	private final ChangeCollection _changeCollection;
}