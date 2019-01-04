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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
@ProviderType
public class UserTrackerWrapper extends BaseModelWrapper<UserTracker>
	implements UserTracker, ModelWrapper<UserTracker> {
	public UserTrackerWrapper(UserTracker userTracker) {
		super(userTracker);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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

	@Override
	public void addPath(UserTrackerPath path) {
		model.addPath(path);
	}

	@Override
	public int compareTo(UserTracker userTracker) {
		return model.compareTo(userTracker);
	}

	/**
	* Returns the company ID of this user tracker.
	*
	* @return the company ID of this user tracker
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public String getEmailAddress() {
		return model.getEmailAddress();
	}

	@Override
	public String getFullName() {
		return model.getFullName();
	}

	@Override
	public int getHits() {
		return model.getHits();
	}

	/**
	* Returns the modified date of this user tracker.
	*
	* @return the modified date of this user tracker
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this user tracker.
	*
	* @return the mvcc version of this user tracker
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	@Override
	public java.util.List<UserTrackerPath> getPaths() {
		return model.getPaths();
	}

	/**
	* Returns the primary key of this user tracker.
	*
	* @return the primary key of this user tracker
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the remote addr of this user tracker.
	*
	* @return the remote addr of this user tracker
	*/
	@Override
	public String getRemoteAddr() {
		return model.getRemoteAddr();
	}

	/**
	* Returns the remote host of this user tracker.
	*
	* @return the remote host of this user tracker
	*/
	@Override
	public String getRemoteHost() {
		return model.getRemoteHost();
	}

	/**
	* Returns the session ID of this user tracker.
	*
	* @return the session ID of this user tracker
	*/
	@Override
	public String getSessionId() {
		return model.getSessionId();
	}

	/**
	* Returns the user agent of this user tracker.
	*
	* @return the user agent of this user tracker
	*/
	@Override
	public String getUserAgent() {
		return model.getUserAgent();
	}

	/**
	* Returns the user ID of this user tracker.
	*
	* @return the user ID of this user tracker
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user tracker ID of this user tracker.
	*
	* @return the user tracker ID of this user tracker
	*/
	@Override
	public long getUserTrackerId() {
		return model.getUserTrackerId();
	}

	/**
	* Returns the user uuid of this user tracker.
	*
	* @return the user uuid of this user tracker
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
	* Sets the company ID of this user tracker.
	*
	* @param companyId the company ID of this user tracker
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the modified date of this user tracker.
	*
	* @param modifiedDate the modified date of this user tracker
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this user tracker.
	*
	* @param mvccVersion the mvcc version of this user tracker
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the primary key of this user tracker.
	*
	* @param primaryKey the primary key of this user tracker
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the remote addr of this user tracker.
	*
	* @param remoteAddr the remote addr of this user tracker
	*/
	@Override
	public void setRemoteAddr(String remoteAddr) {
		model.setRemoteAddr(remoteAddr);
	}

	/**
	* Sets the remote host of this user tracker.
	*
	* @param remoteHost the remote host of this user tracker
	*/
	@Override
	public void setRemoteHost(String remoteHost) {
		model.setRemoteHost(remoteHost);
	}

	/**
	* Sets the session ID of this user tracker.
	*
	* @param sessionId the session ID of this user tracker
	*/
	@Override
	public void setSessionId(String sessionId) {
		model.setSessionId(sessionId);
	}

	/**
	* Sets the user agent of this user tracker.
	*
	* @param userAgent the user agent of this user tracker
	*/
	@Override
	public void setUserAgent(String userAgent) {
		model.setUserAgent(userAgent);
	}

	/**
	* Sets the user ID of this user tracker.
	*
	* @param userId the user ID of this user tracker
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user tracker ID of this user tracker.
	*
	* @param userTrackerId the user tracker ID of this user tracker
	*/
	@Override
	public void setUserTrackerId(long userTrackerId) {
		model.setUserTrackerId(userTrackerId);
	}

	/**
	* Sets the user uuid of this user tracker.
	*
	* @param userUuid the user uuid of this user tracker
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected UserTrackerWrapper wrap(UserTracker userTracker) {
		return new UserTrackerWrapper(userTracker);
	}
}