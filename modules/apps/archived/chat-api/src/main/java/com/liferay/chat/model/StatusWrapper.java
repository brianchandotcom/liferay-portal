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

package com.liferay.chat.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Status}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Status
 * @generated
 */
public class StatusWrapper
	extends BaseModelWrapper<Status> implements ModelWrapper<Status>, Status {

	public StatusWrapper(Status status) {
		super(status);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("statusId", getStatusId());
		attributes.put("userId", getUserId());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("online", isOnline());
		attributes.put("awake", isAwake());
		attributes.put("activePanelIds", getActivePanelIds());
		attributes.put("message", getMessage());
		attributes.put("playSound", isPlaySound());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long statusId = (Long)attributes.get("statusId");

		if (statusId != null) {
			setStatusId(statusId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long modifiedDate = (Long)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Boolean online = (Boolean)attributes.get("online");

		if (online != null) {
			setOnline(online);
		}

		Boolean awake = (Boolean)attributes.get("awake");

		if (awake != null) {
			setAwake(awake);
		}

		String activePanelIds = (String)attributes.get("activePanelIds");

		if (activePanelIds != null) {
			setActivePanelIds(activePanelIds);
		}

		String message = (String)attributes.get("message");

		if (message != null) {
			setMessage(message);
		}

		Boolean playSound = (Boolean)attributes.get("playSound");

		if (playSound != null) {
			setPlaySound(playSound);
		}
	}

	/**
	 * Returns the active panel IDs of this status.
	 *
	 * @return the active panel IDs of this status
	 */
	@Override
	public String getActivePanelIds() {
		return model.getActivePanelIds();
	}

	/**
	 * Returns the awake of this status.
	 *
	 * @return the awake of this status
	 */
	@Override
	public boolean getAwake() {
		return model.getAwake();
	}

	/**
	 * Returns the message of this status.
	 *
	 * @return the message of this status
	 */
	@Override
	public String getMessage() {
		return model.getMessage();
	}

	/**
	 * Returns the modified date of this status.
	 *
	 * @return the modified date of this status
	 */
	@Override
	public long getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the online of this status.
	 *
	 * @return the online of this status
	 */
	@Override
	public boolean getOnline() {
		return model.getOnline();
	}

	/**
	 * Returns the play sound of this status.
	 *
	 * @return the play sound of this status
	 */
	@Override
	public boolean getPlaySound() {
		return model.getPlaySound();
	}

	/**
	 * Returns the primary key of this status.
	 *
	 * @return the primary key of this status
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status ID of this status.
	 *
	 * @return the status ID of this status
	 */
	@Override
	public long getStatusId() {
		return model.getStatusId();
	}

	/**
	 * Returns the user ID of this status.
	 *
	 * @return the user ID of this status
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this status.
	 *
	 * @return the user uuid of this status
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this status is awake.
	 *
	 * @return <code>true</code> if this status is awake; <code>false</code> otherwise
	 */
	@Override
	public boolean isAwake() {
		return model.isAwake();
	}

	/**
	 * Returns <code>true</code> if this status is online.
	 *
	 * @return <code>true</code> if this status is online; <code>false</code> otherwise
	 */
	@Override
	public boolean isOnline() {
		return model.isOnline();
	}

	/**
	 * Returns <code>true</code> if this status is play sound.
	 *
	 * @return <code>true</code> if this status is play sound; <code>false</code> otherwise
	 */
	@Override
	public boolean isPlaySound() {
		return model.isPlaySound();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a status model instance should use the <code>Status</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the active panel IDs of this status.
	 *
	 * @param activePanelIds the active panel IDs of this status
	 */
	@Override
	public void setActivePanelIds(String activePanelIds) {
		model.setActivePanelIds(activePanelIds);
	}

	/**
	 * Sets whether this status is awake.
	 *
	 * @param awake the awake of this status
	 */
	@Override
	public void setAwake(boolean awake) {
		model.setAwake(awake);
	}

	/**
	 * Sets the message of this status.
	 *
	 * @param message the message of this status
	 */
	@Override
	public void setMessage(String message) {
		model.setMessage(message);
	}

	/**
	 * Sets the modified date of this status.
	 *
	 * @param modifiedDate the modified date of this status
	 */
	@Override
	public void setModifiedDate(long modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets whether this status is online.
	 *
	 * @param online the online of this status
	 */
	@Override
	public void setOnline(boolean online) {
		model.setOnline(online);
	}

	/**
	 * Sets whether this status is play sound.
	 *
	 * @param playSound the play sound of this status
	 */
	@Override
	public void setPlaySound(boolean playSound) {
		model.setPlaySound(playSound);
	}

	/**
	 * Sets the primary key of this status.
	 *
	 * @param primaryKey the primary key of this status
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status ID of this status.
	 *
	 * @param statusId the status ID of this status
	 */
	@Override
	public void setStatusId(long statusId) {
		model.setStatusId(statusId);
	}

	/**
	 * Sets the user ID of this status.
	 *
	 * @param userId the user ID of this status
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this status.
	 *
	 * @param userUuid the user uuid of this status
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected StatusWrapper wrap(Status status) {
		return new StatusWrapper(status);
	}

}