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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link UserTrackerPath}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerPath
 * @generated
 */
@ProviderType
public class UserTrackerPathWrapper implements UserTrackerPath,
	ModelWrapper<UserTrackerPath> {
	public UserTrackerPathWrapper(UserTrackerPath userTrackerPath) {
		_userTrackerPath = userTrackerPath;
	}

	@Override
	public Class<?> getModelClass() {
		return UserTrackerPath.class;
	}

	@Override
	public String getModelClassName() {
		return UserTrackerPath.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("userTrackerPathId", getUserTrackerPathId());
		attributes.put("userTrackerId", getUserTrackerId());
		attributes.put("path", getPath());
		attributes.put("pathDate", getPathDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long userTrackerPathId = (Long)attributes.get("userTrackerPathId");

		if (userTrackerPathId != null) {
			setUserTrackerPathId(userTrackerPathId);
		}

		Long userTrackerId = (Long)attributes.get("userTrackerId");

		if (userTrackerId != null) {
			setUserTrackerId(userTrackerId);
		}

		String path = (String)attributes.get("path");

		if (path != null) {
			setPath(path);
		}

		Date pathDate = (Date)attributes.get("pathDate");

		if (pathDate != null) {
			setPathDate(pathDate);
		}
	}

	/**
	* Returns the primary key of this user tracker path.
	*
	* @return the primary key of this user tracker path
	*/
	@Override
	public long getPrimaryKey() {
		return _userTrackerPath.getPrimaryKey();
	}

	/**
	* Sets the primary key of this user tracker path.
	*
	* @param primaryKey the primary key of this user tracker path
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_userTrackerPath.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the mvcc version of this user tracker path.
	*
	* @return the mvcc version of this user tracker path
	*/
	@Override
	public long getMvccVersion() {
		return _userTrackerPath.getMvccVersion();
	}

	/**
	* Sets the mvcc version of this user tracker path.
	*
	* @param mvccVersion the mvcc version of this user tracker path
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_userTrackerPath.setMvccVersion(mvccVersion);
	}

	/**
	* Returns the user tracker path ID of this user tracker path.
	*
	* @return the user tracker path ID of this user tracker path
	*/
	@Override
	public long getUserTrackerPathId() {
		return _userTrackerPath.getUserTrackerPathId();
	}

	/**
	* Sets the user tracker path ID of this user tracker path.
	*
	* @param userTrackerPathId the user tracker path ID of this user tracker path
	*/
	@Override
	public void setUserTrackerPathId(long userTrackerPathId) {
		_userTrackerPath.setUserTrackerPathId(userTrackerPathId);
	}

	/**
	* Returns the user tracker ID of this user tracker path.
	*
	* @return the user tracker ID of this user tracker path
	*/
	@Override
	public long getUserTrackerId() {
		return _userTrackerPath.getUserTrackerId();
	}

	/**
	* Sets the user tracker ID of this user tracker path.
	*
	* @param userTrackerId the user tracker ID of this user tracker path
	*/
	@Override
	public void setUserTrackerId(long userTrackerId) {
		_userTrackerPath.setUserTrackerId(userTrackerId);
	}

	/**
	* Returns the path of this user tracker path.
	*
	* @return the path of this user tracker path
	*/
	@Override
	public java.lang.String getPath() {
		return _userTrackerPath.getPath();
	}

	/**
	* Sets the path of this user tracker path.
	*
	* @param path the path of this user tracker path
	*/
	@Override
	public void setPath(java.lang.String path) {
		_userTrackerPath.setPath(path);
	}

	/**
	* Returns the path date of this user tracker path.
	*
	* @return the path date of this user tracker path
	*/
	@Override
	public java.util.Date getPathDate() {
		return _userTrackerPath.getPathDate();
	}

	/**
	* Sets the path date of this user tracker path.
	*
	* @param pathDate the path date of this user tracker path
	*/
	@Override
	public void setPathDate(java.util.Date pathDate) {
		_userTrackerPath.setPathDate(pathDate);
	}

	@Override
	public boolean isNew() {
		return _userTrackerPath.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_userTrackerPath.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _userTrackerPath.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userTrackerPath.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _userTrackerPath.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _userTrackerPath.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_userTrackerPath.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userTrackerPath.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_userTrackerPath.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_userTrackerPath.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userTrackerPath.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new UserTrackerPathWrapper((UserTrackerPath)_userTrackerPath.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.UserTrackerPath userTrackerPath) {
		return _userTrackerPath.compareTo(userTrackerPath);
	}

	@Override
	public int hashCode() {
		return _userTrackerPath.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.UserTrackerPath> toCacheModel() {
		return _userTrackerPath.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.UserTrackerPath toEscapedModel() {
		return new UserTrackerPathWrapper(_userTrackerPath.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.UserTrackerPath toUnescapedModel() {
		return new UserTrackerPathWrapper(_userTrackerPath.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _userTrackerPath.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _userTrackerPath.toXmlString();
	}

	@Override
	public void persist() {
		_userTrackerPath.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserTrackerPathWrapper)) {
			return false;
		}

		UserTrackerPathWrapper userTrackerPathWrapper = (UserTrackerPathWrapper)obj;

		if (Validator.equals(_userTrackerPath,
					userTrackerPathWrapper._userTrackerPath)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public UserTrackerPath getWrappedUserTrackerPath() {
		return _userTrackerPath;
	}

	@Override
	public UserTrackerPath getWrappedModel() {
		return _userTrackerPath;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _userTrackerPath.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _userTrackerPath.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_userTrackerPath.resetOriginalValues();
	}

	private UserTrackerPath _userTrackerPath;
}