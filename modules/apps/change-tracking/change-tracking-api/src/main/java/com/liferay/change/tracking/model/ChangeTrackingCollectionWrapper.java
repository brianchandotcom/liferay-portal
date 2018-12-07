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

package com.liferay.change.tracking.model;

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
 * This class is a wrapper for {@link ChangeTrackingCollection}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingCollection
 * @generated
 */
@ProviderType
public class ChangeTrackingCollectionWrapper implements ChangeTrackingCollection,
	ModelWrapper<ChangeTrackingCollection> {
	public ChangeTrackingCollectionWrapper(
		ChangeTrackingCollection changeTrackingCollection) {
		_changeTrackingCollection = changeTrackingCollection;
	}

	@Override
	public Class<?> getModelClass() {
		return ChangeTrackingCollection.class;
	}

	@Override
	public String getModelClassName() {
		return ChangeTrackingCollection.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("changeTrackingCollectionId",
			getChangeTrackingCollectionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long changeTrackingCollectionId = (Long)attributes.get(
				"changeTrackingCollectionId");

		if (changeTrackingCollectionId != null) {
			setChangeTrackingCollectionId(changeTrackingCollectionId);
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

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public Object clone() {
		return new ChangeTrackingCollectionWrapper((ChangeTrackingCollection)_changeTrackingCollection.clone());
	}

	@Override
	public int compareTo(ChangeTrackingCollection changeTrackingCollection) {
		return _changeTrackingCollection.compareTo(changeTrackingCollection);
	}

	/**
	* Returns the change tracking collection ID of this change tracking collection.
	*
	* @return the change tracking collection ID of this change tracking collection
	*/
	@Override
	public long getChangeTrackingCollectionId() {
		return _changeTrackingCollection.getChangeTrackingCollectionId();
	}

	/**
	* Returns the company ID of this change tracking collection.
	*
	* @return the company ID of this change tracking collection
	*/
	@Override
	public long getCompanyId() {
		return _changeTrackingCollection.getCompanyId();
	}

	/**
	* Returns the create date of this change tracking collection.
	*
	* @return the create date of this change tracking collection
	*/
	@Override
	public Date getCreateDate() {
		return _changeTrackingCollection.getCreateDate();
	}

	/**
	* Returns the description of this change tracking collection.
	*
	* @return the description of this change tracking collection
	*/
	@Override
	public String getDescription() {
		return _changeTrackingCollection.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _changeTrackingCollection.getExpandoBridge();
	}

	/**
	* Returns the modified date of this change tracking collection.
	*
	* @return the modified date of this change tracking collection
	*/
	@Override
	public Date getModifiedDate() {
		return _changeTrackingCollection.getModifiedDate();
	}

	/**
	* Returns the name of this change tracking collection.
	*
	* @return the name of this change tracking collection
	*/
	@Override
	public String getName() {
		return _changeTrackingCollection.getName();
	}

	/**
	* Returns the primary key of this change tracking collection.
	*
	* @return the primary key of this change tracking collection
	*/
	@Override
	public long getPrimaryKey() {
		return _changeTrackingCollection.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _changeTrackingCollection.getPrimaryKeyObj();
	}

	/**
	* Returns the status of this change tracking collection.
	*
	* @return the status of this change tracking collection
	*/
	@Override
	public int getStatus() {
		return _changeTrackingCollection.getStatus();
	}

	/**
	* Returns the status by user ID of this change tracking collection.
	*
	* @return the status by user ID of this change tracking collection
	*/
	@Override
	public long getStatusByUserId() {
		return _changeTrackingCollection.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this change tracking collection.
	*
	* @return the status by user name of this change tracking collection
	*/
	@Override
	public String getStatusByUserName() {
		return _changeTrackingCollection.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this change tracking collection.
	*
	* @return the status by user uuid of this change tracking collection
	*/
	@Override
	public String getStatusByUserUuid() {
		return _changeTrackingCollection.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this change tracking collection.
	*
	* @return the status date of this change tracking collection
	*/
	@Override
	public Date getStatusDate() {
		return _changeTrackingCollection.getStatusDate();
	}

	/**
	* Returns the user ID of this change tracking collection.
	*
	* @return the user ID of this change tracking collection
	*/
	@Override
	public long getUserId() {
		return _changeTrackingCollection.getUserId();
	}

	/**
	* Returns the user name of this change tracking collection.
	*
	* @return the user name of this change tracking collection
	*/
	@Override
	public String getUserName() {
		return _changeTrackingCollection.getUserName();
	}

	/**
	* Returns the user uuid of this change tracking collection.
	*
	* @return the user uuid of this change tracking collection
	*/
	@Override
	public String getUserUuid() {
		return _changeTrackingCollection.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _changeTrackingCollection.hashCode();
	}

	/**
	* Returns <code>true</code> if this change tracking collection is approved.
	*
	* @return <code>true</code> if this change tracking collection is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _changeTrackingCollection.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _changeTrackingCollection.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this change tracking collection is denied.
	*
	* @return <code>true</code> if this change tracking collection is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _changeTrackingCollection.isDenied();
	}

	/**
	* Returns <code>true</code> if this change tracking collection is a draft.
	*
	* @return <code>true</code> if this change tracking collection is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _changeTrackingCollection.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _changeTrackingCollection.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this change tracking collection is expired.
	*
	* @return <code>true</code> if this change tracking collection is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _changeTrackingCollection.isExpired();
	}

	/**
	* Returns <code>true</code> if this change tracking collection is inactive.
	*
	* @return <code>true</code> if this change tracking collection is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _changeTrackingCollection.isInactive();
	}

	/**
	* Returns <code>true</code> if this change tracking collection is incomplete.
	*
	* @return <code>true</code> if this change tracking collection is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _changeTrackingCollection.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _changeTrackingCollection.isNew();
	}

	/**
	* Returns <code>true</code> if this change tracking collection is pending.
	*
	* @return <code>true</code> if this change tracking collection is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _changeTrackingCollection.isPending();
	}

	/**
	* Returns <code>true</code> if this change tracking collection is scheduled.
	*
	* @return <code>true</code> if this change tracking collection is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _changeTrackingCollection.isScheduled();
	}

	@Override
	public void persist() {
		_changeTrackingCollection.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_changeTrackingCollection.setCachedModel(cachedModel);
	}

	/**
	* Sets the change tracking collection ID of this change tracking collection.
	*
	* @param changeTrackingCollectionId the change tracking collection ID of this change tracking collection
	*/
	@Override
	public void setChangeTrackingCollectionId(long changeTrackingCollectionId) {
		_changeTrackingCollection.setChangeTrackingCollectionId(changeTrackingCollectionId);
	}

	/**
	* Sets the company ID of this change tracking collection.
	*
	* @param companyId the company ID of this change tracking collection
	*/
	@Override
	public void setCompanyId(long companyId) {
		_changeTrackingCollection.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this change tracking collection.
	*
	* @param createDate the create date of this change tracking collection
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_changeTrackingCollection.setCreateDate(createDate);
	}

	/**
	* Sets the description of this change tracking collection.
	*
	* @param description the description of this change tracking collection
	*/
	@Override
	public void setDescription(String description) {
		_changeTrackingCollection.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_changeTrackingCollection.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_changeTrackingCollection.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_changeTrackingCollection.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this change tracking collection.
	*
	* @param modifiedDate the modified date of this change tracking collection
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_changeTrackingCollection.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this change tracking collection.
	*
	* @param name the name of this change tracking collection
	*/
	@Override
	public void setName(String name) {
		_changeTrackingCollection.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_changeTrackingCollection.setNew(n);
	}

	/**
	* Sets the primary key of this change tracking collection.
	*
	* @param primaryKey the primary key of this change tracking collection
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_changeTrackingCollection.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_changeTrackingCollection.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the status of this change tracking collection.
	*
	* @param status the status of this change tracking collection
	*/
	@Override
	public void setStatus(int status) {
		_changeTrackingCollection.setStatus(status);
	}

	/**
	* Sets the status by user ID of this change tracking collection.
	*
	* @param statusByUserId the status by user ID of this change tracking collection
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_changeTrackingCollection.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this change tracking collection.
	*
	* @param statusByUserName the status by user name of this change tracking collection
	*/
	@Override
	public void setStatusByUserName(String statusByUserName) {
		_changeTrackingCollection.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this change tracking collection.
	*
	* @param statusByUserUuid the status by user uuid of this change tracking collection
	*/
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		_changeTrackingCollection.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this change tracking collection.
	*
	* @param statusDate the status date of this change tracking collection
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_changeTrackingCollection.setStatusDate(statusDate);
	}

	/**
	* Sets the user ID of this change tracking collection.
	*
	* @param userId the user ID of this change tracking collection
	*/
	@Override
	public void setUserId(long userId) {
		_changeTrackingCollection.setUserId(userId);
	}

	/**
	* Sets the user name of this change tracking collection.
	*
	* @param userName the user name of this change tracking collection
	*/
	@Override
	public void setUserName(String userName) {
		_changeTrackingCollection.setUserName(userName);
	}

	/**
	* Sets the user uuid of this change tracking collection.
	*
	* @param userUuid the user uuid of this change tracking collection
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_changeTrackingCollection.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ChangeTrackingCollection> toCacheModel() {
		return _changeTrackingCollection.toCacheModel();
	}

	@Override
	public ChangeTrackingCollection toEscapedModel() {
		return new ChangeTrackingCollectionWrapper(_changeTrackingCollection.toEscapedModel());
	}

	@Override
	public String toString() {
		return _changeTrackingCollection.toString();
	}

	@Override
	public ChangeTrackingCollection toUnescapedModel() {
		return new ChangeTrackingCollectionWrapper(_changeTrackingCollection.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _changeTrackingCollection.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangeTrackingCollectionWrapper)) {
			return false;
		}

		ChangeTrackingCollectionWrapper changeTrackingCollectionWrapper = (ChangeTrackingCollectionWrapper)obj;

		if (Objects.equals(_changeTrackingCollection,
					changeTrackingCollectionWrapper._changeTrackingCollection)) {
			return true;
		}

		return false;
	}

	@Override
	public ChangeTrackingCollection getWrappedModel() {
		return _changeTrackingCollection;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _changeTrackingCollection.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _changeTrackingCollection.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_changeTrackingCollection.resetOriginalValues();
	}

	private final ChangeTrackingCollection _changeTrackingCollection;
}