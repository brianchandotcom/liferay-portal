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
 * This class is a wrapper for {@link UserTrackerPath}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerPath
 * @generated
 */
@ProviderType
public class UserTrackerPathWrapper extends BaseModelWrapper<UserTrackerPath>
	implements UserTrackerPath, ModelWrapper<UserTrackerPath> {
	public UserTrackerPathWrapper(UserTrackerPath userTrackerPath) {
		super(userTrackerPath);
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

	/**
	* Returns the company ID of this user tracker path.
	*
	* @return the company ID of this user tracker path
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the mvcc version of this user tracker path.
	*
	* @return the mvcc version of this user tracker path
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the path of this user tracker path.
	*
	* @return the path of this user tracker path
	*/
	@Override
	public String getPath() {
		return model.getPath();
	}

	/**
	* Returns the path date of this user tracker path.
	*
	* @return the path date of this user tracker path
	*/
	@Override
	public Date getPathDate() {
		return model.getPathDate();
	}

	/**
	* Returns the primary key of this user tracker path.
	*
	* @return the primary key of this user tracker path
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user tracker ID of this user tracker path.
	*
	* @return the user tracker ID of this user tracker path
	*/
	@Override
	public long getUserTrackerId() {
		return model.getUserTrackerId();
	}

	/**
	* Returns the user tracker path ID of this user tracker path.
	*
	* @return the user tracker path ID of this user tracker path
	*/
	@Override
	public long getUserTrackerPathId() {
		return model.getUserTrackerPathId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this user tracker path.
	*
	* @param companyId the company ID of this user tracker path
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the mvcc version of this user tracker path.
	*
	* @param mvccVersion the mvcc version of this user tracker path
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the path of this user tracker path.
	*
	* @param path the path of this user tracker path
	*/
	@Override
	public void setPath(String path) {
		model.setPath(path);
	}

	/**
	* Sets the path date of this user tracker path.
	*
	* @param pathDate the path date of this user tracker path
	*/
	@Override
	public void setPathDate(Date pathDate) {
		model.setPathDate(pathDate);
	}

	/**
	* Sets the primary key of this user tracker path.
	*
	* @param primaryKey the primary key of this user tracker path
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user tracker ID of this user tracker path.
	*
	* @param userTrackerId the user tracker ID of this user tracker path
	*/
	@Override
	public void setUserTrackerId(long userTrackerId) {
		model.setUserTrackerId(userTrackerId);
	}

	/**
	* Sets the user tracker path ID of this user tracker path.
	*
	* @param userTrackerPathId the user tracker path ID of this user tracker path
	*/
	@Override
	public void setUserTrackerPathId(long userTrackerPathId) {
		model.setUserTrackerPathId(userTrackerPathId);
	}

	@Override
	protected UserTrackerPathWrapper wrap(UserTrackerPath userTrackerPath) {
		return new UserTrackerPathWrapper(userTrackerPath);
	}
}