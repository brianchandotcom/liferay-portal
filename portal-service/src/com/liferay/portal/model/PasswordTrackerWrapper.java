/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PasswordTracker}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordTracker
 * @generated
 */
public class PasswordTrackerWrapper implements PasswordTracker,
	ModelWrapper<PasswordTracker> {
	public PasswordTrackerWrapper(PasswordTracker passwordTracker) {
		_passwordTracker = passwordTracker;
	}

	public Class<?> getModelClass() {
		return PasswordTracker.class;
	}

	public String getModelClassName() {
		return PasswordTracker.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("passwordTrackerId", getPasswordTrackerId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("password", getPassword());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long passwordTrackerId = (Long)attributes.get("passwordTrackerId");

		if (passwordTrackerId != null) {
			setPasswordTrackerId(passwordTrackerId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String password = (String)attributes.get("password");

		if (password != null) {
			setPassword(password);
		}
	}

	/**
	* Returns the primary key of this password tracker.
	*
	* @return the primary key of this password tracker
	*/
	public long getPrimaryKey() {
		return _passwordTracker.getPrimaryKey();
	}

	/**
	* Sets the primary key of this password tracker.
	*
	* @param primaryKey the primary key of this password tracker
	*/
	public void setPrimaryKey(long primaryKey) {
		_passwordTracker.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the password tracker ID of this password tracker.
	*
	* @return the password tracker ID of this password tracker
	*/
	public long getPasswordTrackerId() {
		return _passwordTracker.getPasswordTrackerId();
	}

	/**
	* Sets the password tracker ID of this password tracker.
	*
	* @param passwordTrackerId the password tracker ID of this password tracker
	*/
	public void setPasswordTrackerId(long passwordTrackerId) {
		_passwordTracker.setPasswordTrackerId(passwordTrackerId);
	}

	/**
	* Returns the user ID of this password tracker.
	*
	* @return the user ID of this password tracker
	*/
	public long getUserId() {
		return _passwordTracker.getUserId();
	}

	/**
	* Sets the user ID of this password tracker.
	*
	* @param userId the user ID of this password tracker
	*/
	public void setUserId(long userId) {
		_passwordTracker.setUserId(userId);
	}

	/**
	* Returns the user uuid of this password tracker.
	*
	* @return the user uuid of this password tracker
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTracker.getUserUuid();
	}

	/**
	* Sets the user uuid of this password tracker.
	*
	* @param userUuid the user uuid of this password tracker
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_passwordTracker.setUserUuid(userUuid);
	}

	/**
	* Returns the create date of this password tracker.
	*
	* @return the create date of this password tracker
	*/
	public java.util.Date getCreateDate() {
		return _passwordTracker.getCreateDate();
	}

	/**
	* Sets the create date of this password tracker.
	*
	* @param createDate the create date of this password tracker
	*/
	public void setCreateDate(java.util.Date createDate) {
		_passwordTracker.setCreateDate(createDate);
	}

	/**
	* Returns the password of this password tracker.
	*
	* @return the password of this password tracker
	*/
	public java.lang.String getPassword() {
		return _passwordTracker.getPassword();
	}

	/**
	* Sets the password of this password tracker.
	*
	* @param password the password of this password tracker
	*/
	public void setPassword(java.lang.String password) {
		_passwordTracker.setPassword(password);
	}

	public boolean isNew() {
		return _passwordTracker.isNew();
	}

	public void setNew(boolean n) {
		_passwordTracker.setNew(n);
	}

	public boolean isCachedModel() {
		return _passwordTracker.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_passwordTracker.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _passwordTracker.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _passwordTracker.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_passwordTracker.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _passwordTracker.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_passwordTracker.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PasswordTrackerWrapper((PasswordTracker)_passwordTracker.clone());
	}

	public int compareTo(
		com.liferay.portal.model.PasswordTracker passwordTracker) {
		return _passwordTracker.compareTo(passwordTracker);
	}

	@Override
	public int hashCode() {
		return _passwordTracker.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.PasswordTracker> toCacheModel() {
		return _passwordTracker.toCacheModel();
	}

	public com.liferay.portal.model.PasswordTracker toEscapedModel() {
		return new PasswordTrackerWrapper(_passwordTracker.toEscapedModel());
	}

	public com.liferay.portal.model.PasswordTracker toUnescapedModel() {
		return new PasswordTrackerWrapper(_passwordTracker.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _passwordTracker.toString();
	}

	public java.lang.String toXmlString() {
		return _passwordTracker.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordTracker.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public PasswordTracker getWrappedPasswordTracker() {
		return _passwordTracker;
	}

	public PasswordTracker getWrappedModel() {
		return _passwordTracker;
	}

	public void resetOriginalValues() {
		_passwordTracker.resetOriginalValues();
	}

	private PasswordTracker _passwordTracker;
}