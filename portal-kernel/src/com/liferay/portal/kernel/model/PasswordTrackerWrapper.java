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
 * This class is a wrapper for {@link PasswordTracker}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordTracker
 * @generated
 */
@ProviderType
public class PasswordTrackerWrapper extends BaseModelWrapper<PasswordTracker>
	implements PasswordTracker, ModelWrapper<PasswordTracker> {
	public PasswordTrackerWrapper(PasswordTracker passwordTracker) {
		super(passwordTracker);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("passwordTrackerId", getPasswordTrackerId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("password", getPassword());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long passwordTrackerId = (Long)attributes.get("passwordTrackerId");

		if (passwordTrackerId != null) {
			setPasswordTrackerId(passwordTrackerId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
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
	* Returns the company ID of this password tracker.
	*
	* @return the company ID of this password tracker
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this password tracker.
	*
	* @return the create date of this password tracker
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the mvcc version of this password tracker.
	*
	* @return the mvcc version of this password tracker
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the password of this password tracker.
	*
	* @return the password of this password tracker
	*/
	@Override
	public String getPassword() {
		return model.getPassword();
	}

	/**
	* Returns the password tracker ID of this password tracker.
	*
	* @return the password tracker ID of this password tracker
	*/
	@Override
	public long getPasswordTrackerId() {
		return model.getPasswordTrackerId();
	}

	/**
	* Returns the primary key of this password tracker.
	*
	* @return the primary key of this password tracker
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this password tracker.
	*
	* @return the user ID of this password tracker
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user uuid of this password tracker.
	*
	* @return the user uuid of this password tracker
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
	* Sets the company ID of this password tracker.
	*
	* @param companyId the company ID of this password tracker
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this password tracker.
	*
	* @param createDate the create date of this password tracker
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the mvcc version of this password tracker.
	*
	* @param mvccVersion the mvcc version of this password tracker
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the password of this password tracker.
	*
	* @param password the password of this password tracker
	*/
	@Override
	public void setPassword(String password) {
		model.setPassword(password);
	}

	/**
	* Sets the password tracker ID of this password tracker.
	*
	* @param passwordTrackerId the password tracker ID of this password tracker
	*/
	@Override
	public void setPasswordTrackerId(long passwordTrackerId) {
		model.setPasswordTrackerId(passwordTrackerId);
	}

	/**
	* Sets the primary key of this password tracker.
	*
	* @param primaryKey the primary key of this password tracker
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this password tracker.
	*
	* @param userId the user ID of this password tracker
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user uuid of this password tracker.
	*
	* @param userUuid the user uuid of this password tracker
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected PasswordTrackerWrapper wrap(PasswordTracker passwordTracker) {
		return new PasswordTrackerWrapper(passwordTracker);
	}
}