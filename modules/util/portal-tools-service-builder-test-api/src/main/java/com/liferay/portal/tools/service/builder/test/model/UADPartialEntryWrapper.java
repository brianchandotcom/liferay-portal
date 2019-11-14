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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link UADPartialEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UADPartialEntry
 * @generated
 */
public class UADPartialEntryWrapper
	extends BaseModelWrapper<UADPartialEntry>
	implements ModelWrapper<UADPartialEntry>, UADPartialEntry {

	public UADPartialEntryWrapper(UADPartialEntry uadPartialEntry) {
		super(uadPartialEntry);
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

	/**
	 * Returns the message of this uad partial entry.
	 *
	 * @return the message of this uad partial entry
	 */
	@Override
	public String getMessage() {
		return model.getMessage();
	}

	/**
	 * Returns the primary key of this uad partial entry.
	 *
	 * @return the primary key of this uad partial entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the uad partial entry ID of this uad partial entry.
	 *
	 * @return the uad partial entry ID of this uad partial entry
	 */
	@Override
	public long getUadPartialEntryId() {
		return model.getUadPartialEntryId();
	}

	/**
	 * Returns the user ID of this uad partial entry.
	 *
	 * @return the user ID of this uad partial entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this uad partial entry.
	 *
	 * @return the user name of this uad partial entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this uad partial entry.
	 *
	 * @return the user uuid of this uad partial entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a uad partial entry model instance should use the <code>UADPartialEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the message of this uad partial entry.
	 *
	 * @param message the message of this uad partial entry
	 */
	@Override
	public void setMessage(String message) {
		model.setMessage(message);
	}

	/**
	 * Sets the primary key of this uad partial entry.
	 *
	 * @param primaryKey the primary key of this uad partial entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the uad partial entry ID of this uad partial entry.
	 *
	 * @param uadPartialEntryId the uad partial entry ID of this uad partial entry
	 */
	@Override
	public void setUadPartialEntryId(long uadPartialEntryId) {
		model.setUadPartialEntryId(uadPartialEntryId);
	}

	/**
	 * Sets the user ID of this uad partial entry.
	 *
	 * @param userId the user ID of this uad partial entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this uad partial entry.
	 *
	 * @param userName the user name of this uad partial entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this uad partial entry.
	 *
	 * @param userUuid the user uuid of this uad partial entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected UADPartialEntryWrapper wrap(UADPartialEntry uadPartialEntry) {
		return new UADPartialEntryWrapper(uadPartialEntry);
	}

}