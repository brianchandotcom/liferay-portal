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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
		attributes.put("companyId", getCompanyId());
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

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
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

	@Override
	public CacheModel<UserTrackerPath> toCacheModel() {
		return _userTrackerPath.toCacheModel();
	}

	@Override
	public UserTrackerPath toEscapedModel() {
		return new UserTrackerPathWrapper(_userTrackerPath.toEscapedModel());
	}

	@Override
	public UserTrackerPath toUnescapedModel() {
		return new UserTrackerPathWrapper(_userTrackerPath.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _userTrackerPath.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _userTrackerPath.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _userTrackerPath.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _userTrackerPath.getExpandoBridge();
	}

	@Override
	public int compareTo(UserTrackerPath userTrackerPath) {
		return _userTrackerPath.compareTo(userTrackerPath);
	}

	@Override
	public int hashCode() {
		return _userTrackerPath.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _userTrackerPath.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new UserTrackerPathWrapper((UserTrackerPath)_userTrackerPath.clone());
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

	@Override
	public java.lang.String toString() {
		return _userTrackerPath.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _userTrackerPath.toXmlString();
	}

	/**
	* Returns the path date of this user tracker path.
	*
	* @return the path date of this user tracker path
	*/
	@Override
	public Date getPathDate() {
		return _userTrackerPath.getPathDate();
	}

	/**
	* Returns the company ID of this user tracker path.
	*
	* @return the company ID of this user tracker path
	*/
	@Override
	public long getCompanyId() {
		return _userTrackerPath.getCompanyId();
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
	* Returns the primary key of this user tracker path.
	*
	* @return the primary key of this user tracker path
	*/
	@Override
	public long getPrimaryKey() {
		return _userTrackerPath.getPrimaryKey();
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
	* Returns the user tracker path ID of this user tracker path.
	*
	* @return the user tracker path ID of this user tracker path
	*/
	@Override
	public long getUserTrackerPathId() {
		return _userTrackerPath.getUserTrackerPathId();
	}

	@Override
	public void persist() {
		_userTrackerPath.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userTrackerPath.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this user tracker path.
	*
	* @param companyId the company ID of this user tracker path
	*/
	@Override
	public void setCompanyId(long companyId) {
		_userTrackerPath.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_userTrackerPath.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_userTrackerPath.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_userTrackerPath.setExpandoBridgeAttributes(serviceContext);
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

	@Override
	public void setNew(boolean n) {
		_userTrackerPath.setNew(n);
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
	* Sets the path date of this user tracker path.
	*
	* @param pathDate the path date of this user tracker path
	*/
	@Override
	public void setPathDate(Date pathDate) {
		_userTrackerPath.setPathDate(pathDate);
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

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_userTrackerPath.setPrimaryKeyObj(primaryKeyObj);
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
	* Sets the user tracker path ID of this user tracker path.
	*
	* @param userTrackerPathId the user tracker path ID of this user tracker path
	*/
	@Override
	public void setUserTrackerPathId(long userTrackerPathId) {
		_userTrackerPath.setUserTrackerPathId(userTrackerPathId);
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

		if (Objects.equals(_userTrackerPath,
					userTrackerPathWrapper._userTrackerPath)) {
			return true;
		}

		return false;
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

	private final UserTrackerPath _userTrackerPath;
}