/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * This class is a wrapper for {@link UserTracker}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserTracker
 * @generated
 */
public class UserTrackerWrapper implements UserTracker,
	ModelWrapper<UserTracker> {
	public UserTrackerWrapper(UserTracker userTracker) {
		_userTracker = userTracker;
	}

	@Override
	public Class<?> getModelClass() {
		return UserTracker.class;
	}

	@Override
	public String getModelClassName() {
		return UserTracker.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("userTrackerId", getUserTrackerId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("sessionId", getSessionId());
		attributes.put("remoteAddr", getRemoteAddr());
		attributes.put("remoteHost", getRemoteHost());
		attributes.put("userAgent", getUserAgent());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long userTrackerId = (Long)attributes.get("userTrackerId");

		if (userTrackerId != null) {
			setUserTrackerId(userTrackerId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String sessionId = (String)attributes.get("sessionId");

		if (sessionId != null) {
			setSessionId(sessionId);
		}

		String remoteAddr = (String)attributes.get("remoteAddr");

		if (remoteAddr != null) {
			setRemoteAddr(remoteAddr);
		}

		String remoteHost = (String)attributes.get("remoteHost");

		if (remoteHost != null) {
			setRemoteHost(remoteHost);
		}

		String userAgent = (String)attributes.get("userAgent");

		if (userAgent != null) {
			setUserAgent(userAgent);
		}
	}

	/**
	* Returns the primary key of this user tracker.
	*
	* @return the primary key of this user tracker
	*/
	@Override
	public long getPrimaryKey() {
		return _userTracker.getPrimaryKey();
	}

	/**
	* Sets the primary key of this user tracker.
	*
	* @param primaryKey the primary key of this user tracker
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_userTracker.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the user tracker ID of this user tracker.
	*
	* @return the user tracker ID of this user tracker
	*/
	@Override
	public long getUserTrackerId() {
		return _userTracker.getUserTrackerId();
	}

	/**
	* Sets the user tracker ID of this user tracker.
	*
	* @param userTrackerId the user tracker ID of this user tracker
	*/
	@Override
	public void setUserTrackerId(long userTrackerId) {
		_userTracker.setUserTrackerId(userTrackerId);
	}

	/**
	* Returns the company ID of this user tracker.
	*
	* @return the company ID of this user tracker
	*/
	@Override
	public long getCompanyId() {
		return _userTracker.getCompanyId();
	}

	/**
	* Sets the company ID of this user tracker.
	*
	* @param companyId the company ID of this user tracker
	*/
	@Override
	public void setCompanyId(long companyId) {
		_userTracker.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this user tracker.
	*
	* @return the user ID of this user tracker
	*/
	@Override
	public long getUserId() {
		return _userTracker.getUserId();
	}

	/**
	* Sets the user ID of this user tracker.
	*
	* @param userId the user ID of this user tracker
	*/
	@Override
	public void setUserId(long userId) {
		_userTracker.setUserId(userId);
	}

	/**
	* Returns the user uuid of this user tracker.
	*
	* @return the user uuid of this user tracker
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTracker.getUserUuid();
	}

	/**
	* Sets the user uuid of this user tracker.
	*
	* @param userUuid the user uuid of this user tracker
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_userTracker.setUserUuid(userUuid);
	}

	/**
	* Returns the modified date of this user tracker.
	*
	* @return the modified date of this user tracker
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _userTracker.getModifiedDate();
	}

	/**
	* Sets the modified date of this user tracker.
	*
	* @param modifiedDate the modified date of this user tracker
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_userTracker.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the session ID of this user tracker.
	*
	* @return the session ID of this user tracker
	*/
	@Override
	public java.lang.String getSessionId() {
		return _userTracker.getSessionId();
	}

	/**
	* Sets the session ID of this user tracker.
	*
	* @param sessionId the session ID of this user tracker
	*/
	@Override
	public void setSessionId(java.lang.String sessionId) {
		_userTracker.setSessionId(sessionId);
	}

	/**
	* Returns the remote addr of this user tracker.
	*
	* @return the remote addr of this user tracker
	*/
	@Override
	public java.lang.String getRemoteAddr() {
		return _userTracker.getRemoteAddr();
	}

	/**
	* Sets the remote addr of this user tracker.
	*
	* @param remoteAddr the remote addr of this user tracker
	*/
	@Override
	public void setRemoteAddr(java.lang.String remoteAddr) {
		_userTracker.setRemoteAddr(remoteAddr);
	}

	/**
	* Returns the remote host of this user tracker.
	*
	* @return the remote host of this user tracker
	*/
	@Override
	public java.lang.String getRemoteHost() {
		return _userTracker.getRemoteHost();
	}

	/**
	* Sets the remote host of this user tracker.
	*
	* @param remoteHost the remote host of this user tracker
	*/
	@Override
	public void setRemoteHost(java.lang.String remoteHost) {
		_userTracker.setRemoteHost(remoteHost);
	}

	/**
	* Returns the user agent of this user tracker.
	*
	* @return the user agent of this user tracker
	*/
	@Override
	public java.lang.String getUserAgent() {
		return _userTracker.getUserAgent();
	}

	/**
	* Sets the user agent of this user tracker.
	*
	* @param userAgent the user agent of this user tracker
	*/
	@Override
	public void setUserAgent(java.lang.String userAgent) {
		_userTracker.setUserAgent(userAgent);
	}

	@Override
	public boolean isNew() {
		return _userTracker.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_userTracker.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _userTracker.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userTracker.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _userTracker.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _userTracker.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_userTracker.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userTracker.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_userTracker.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_userTracker.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userTracker.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new UserTrackerWrapper((UserTracker)_userTracker.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.UserTracker userTracker) {
		return _userTracker.compareTo(userTracker);
	}

	@Override
	public int hashCode() {
		return _userTracker.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.UserTracker> toCacheModel() {
		return _userTracker.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.UserTracker toEscapedModel() {
		return new UserTrackerWrapper(_userTracker.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.UserTracker toUnescapedModel() {
		return new UserTrackerWrapper(_userTracker.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _userTracker.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _userTracker.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_userTracker.persist();
	}

	@Override
	public void addPath(com.liferay.portal.model.UserTrackerPath path) {
		_userTracker.addPath(path);
	}

	@Override
	public java.lang.String getEmailAddress() {
		return _userTracker.getEmailAddress();
	}

	@Override
	public java.lang.String getFullName() {
		return _userTracker.getFullName();
	}

	@Override
	public int getHits() {
		return _userTracker.getHits();
	}

	@Override
	public java.util.List<com.liferay.portal.model.UserTrackerPath> getPaths() {
		return _userTracker.getPaths();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public UserTracker getWrappedUserTracker() {
		return _userTracker;
	}

	@Override
	public UserTracker getWrappedModel() {
		return _userTracker;
	}

	@Override
	public void resetOriginalValues() {
		_userTracker.resetOriginalValues();
	}

	private UserTracker _userTracker;
}