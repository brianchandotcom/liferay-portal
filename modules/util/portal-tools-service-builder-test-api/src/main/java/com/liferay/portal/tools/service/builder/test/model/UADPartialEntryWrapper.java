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

package com.liferay.portal.tools.service.builder.test.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link UADPartialEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UADPartialEntry
 * @generated
 */
@ProviderType
public class UADPartialEntryWrapper implements UADPartialEntry,
	ModelWrapper<UADPartialEntry> {
	public UADPartialEntryWrapper(UADPartialEntry uadPartialEntry) {
		_uadPartialEntry = uadPartialEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return UADPartialEntry.class;
	}

	@Override
	public String getModelClassName() {
		return UADPartialEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uadPartialEntryId", getUadPartialEntryId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("message", getMessage());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long uadPartialEntryId = (Long)attributes.get("uadPartialEntryId");

		if (uadPartialEntryId != null) {
			setUadPartialEntryId(uadPartialEntryId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		String message = (String)attributes.get("message");

		if (message != null) {
			setMessage(message);
		}
	}

	@Override
	public Object clone() {
		return new UADPartialEntryWrapper((UADPartialEntry)_uadPartialEntry.clone());
	}

	@Override
	public int compareTo(UADPartialEntry uadPartialEntry) {
		return _uadPartialEntry.compareTo(uadPartialEntry);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _uadPartialEntry.getExpandoBridge();
	}

	/**
	* Returns the message of this uad partial entry.
	*
	* @return the message of this uad partial entry
	*/
	@Override
	public String getMessage() {
		return _uadPartialEntry.getMessage();
	}

	/**
	* Returns the primary key of this uad partial entry.
	*
	* @return the primary key of this uad partial entry
	*/
	@Override
	public long getPrimaryKey() {
		return _uadPartialEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _uadPartialEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the uad partial entry ID of this uad partial entry.
	*
	* @return the uad partial entry ID of this uad partial entry
	*/
	@Override
	public long getUadPartialEntryId() {
		return _uadPartialEntry.getUadPartialEntryId();
	}

	/**
	* Returns the user ID of this uad partial entry.
	*
	* @return the user ID of this uad partial entry
	*/
	@Override
	public long getUserId() {
		return _uadPartialEntry.getUserId();
	}

	/**
	* Returns the user name of this uad partial entry.
	*
	* @return the user name of this uad partial entry
	*/
	@Override
	public String getUserName() {
		return _uadPartialEntry.getUserName();
	}

	/**
	* Returns the user uuid of this uad partial entry.
	*
	* @return the user uuid of this uad partial entry
	*/
	@Override
	public String getUserUuid() {
		return _uadPartialEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _uadPartialEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _uadPartialEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _uadPartialEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _uadPartialEntry.isNew();
	}

	@Override
	public void persist() {
		_uadPartialEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_uadPartialEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_uadPartialEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_uadPartialEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_uadPartialEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the message of this uad partial entry.
	*
	* @param message the message of this uad partial entry
	*/
	@Override
	public void setMessage(String message) {
		_uadPartialEntry.setMessage(message);
	}

	@Override
	public void setNew(boolean n) {
		_uadPartialEntry.setNew(n);
	}

	/**
	* Sets the primary key of this uad partial entry.
	*
	* @param primaryKey the primary key of this uad partial entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_uadPartialEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_uadPartialEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the uad partial entry ID of this uad partial entry.
	*
	* @param uadPartialEntryId the uad partial entry ID of this uad partial entry
	*/
	@Override
	public void setUadPartialEntryId(long uadPartialEntryId) {
		_uadPartialEntry.setUadPartialEntryId(uadPartialEntryId);
	}

	/**
	* Sets the user ID of this uad partial entry.
	*
	* @param userId the user ID of this uad partial entry
	*/
	@Override
	public void setUserId(long userId) {
		_uadPartialEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this uad partial entry.
	*
	* @param userName the user name of this uad partial entry
	*/
	@Override
	public void setUserName(String userName) {
		_uadPartialEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this uad partial entry.
	*
	* @param userUuid the user uuid of this uad partial entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_uadPartialEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<UADPartialEntry> toCacheModel() {
		return _uadPartialEntry.toCacheModel();
	}

	@Override
	public UADPartialEntry toEscapedModel() {
		return new UADPartialEntryWrapper(_uadPartialEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _uadPartialEntry.toString();
	}

	@Override
	public UADPartialEntry toUnescapedModel() {
		return new UADPartialEntryWrapper(_uadPartialEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _uadPartialEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UADPartialEntryWrapper)) {
			return false;
		}

		UADPartialEntryWrapper uadPartialEntryWrapper = (UADPartialEntryWrapper)obj;

		if (Objects.equals(_uadPartialEntry,
					uadPartialEntryWrapper._uadPartialEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public UADPartialEntry getWrappedModel() {
		return _uadPartialEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _uadPartialEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _uadPartialEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_uadPartialEntry.resetOriginalValues();
	}

	private final UADPartialEntry _uadPartialEntry;
}