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

package com.liferay.notification.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link NotificationQueueEntryRecipient}.
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryRecipient
 * @generated
 */
public class NotificationQueueEntryRecipientWrapper
	extends BaseModelWrapper<NotificationQueueEntryRecipient>
	implements ModelWrapper<NotificationQueueEntryRecipient>,
			   NotificationQueueEntryRecipient {

	public NotificationQueueEntryRecipientWrapper(
		NotificationQueueEntryRecipient notificationQueueEntryRecipient) {

		super(notificationQueueEntryRecipient);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"notificationQueueEntryRecipientId",
			getNotificationQueueEntryRecipientId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"notificationQueueEntryId", getNotificationQueueEntryId());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long notificationQueueEntryRecipientId = (Long)attributes.get(
			"notificationQueueEntryRecipientId");

		if (notificationQueueEntryRecipientId != null) {
			setNotificationQueueEntryRecipientId(
				notificationQueueEntryRecipientId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long notificationQueueEntryId = (Long)attributes.get(
			"notificationQueueEntryId");

		if (notificationQueueEntryId != null) {
			setNotificationQueueEntryId(notificationQueueEntryId);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public NotificationQueueEntryRecipient cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this notification queue entry recipient.
	 *
	 * @return the company ID of this notification queue entry recipient
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this notification queue entry recipient.
	 *
	 * @return the create date of this notification queue entry recipient
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this notification queue entry recipient.
	 *
	 * @return the modified date of this notification queue entry recipient
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this notification queue entry recipient.
	 *
	 * @return the mvcc version of this notification queue entry recipient
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the notification queue entry ID of this notification queue entry recipient.
	 *
	 * @return the notification queue entry ID of this notification queue entry recipient
	 */
	@Override
	public long getNotificationQueueEntryId() {
		return model.getNotificationQueueEntryId();
	}

	/**
	 * Returns the notification queue entry recipient ID of this notification queue entry recipient.
	 *
	 * @return the notification queue entry recipient ID of this notification queue entry recipient
	 */
	@Override
	public long getNotificationQueueEntryRecipientId() {
		return model.getNotificationQueueEntryRecipientId();
	}

	/**
	 * Returns the primary key of this notification queue entry recipient.
	 *
	 * @return the primary key of this notification queue entry recipient
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this notification queue entry recipient.
	 *
	 * @return the type of this notification queue entry recipient
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this notification queue entry recipient.
	 *
	 * @return the user ID of this notification queue entry recipient
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this notification queue entry recipient.
	 *
	 * @return the user name of this notification queue entry recipient
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this notification queue entry recipient.
	 *
	 * @return the user uuid of this notification queue entry recipient
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
	 * Sets the company ID of this notification queue entry recipient.
	 *
	 * @param companyId the company ID of this notification queue entry recipient
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this notification queue entry recipient.
	 *
	 * @param createDate the create date of this notification queue entry recipient
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this notification queue entry recipient.
	 *
	 * @param modifiedDate the modified date of this notification queue entry recipient
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this notification queue entry recipient.
	 *
	 * @param mvccVersion the mvcc version of this notification queue entry recipient
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the notification queue entry ID of this notification queue entry recipient.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID of this notification queue entry recipient
	 */
	@Override
	public void setNotificationQueueEntryId(long notificationQueueEntryId) {
		model.setNotificationQueueEntryId(notificationQueueEntryId);
	}

	/**
	 * Sets the notification queue entry recipient ID of this notification queue entry recipient.
	 *
	 * @param notificationQueueEntryRecipientId the notification queue entry recipient ID of this notification queue entry recipient
	 */
	@Override
	public void setNotificationQueueEntryRecipientId(
		long notificationQueueEntryRecipientId) {

		model.setNotificationQueueEntryRecipientId(
			notificationQueueEntryRecipientId);
	}

	/**
	 * Sets the primary key of this notification queue entry recipient.
	 *
	 * @param primaryKey the primary key of this notification queue entry recipient
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this notification queue entry recipient.
	 *
	 * @param type the type of this notification queue entry recipient
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this notification queue entry recipient.
	 *
	 * @param userId the user ID of this notification queue entry recipient
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this notification queue entry recipient.
	 *
	 * @param userName the user name of this notification queue entry recipient
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this notification queue entry recipient.
	 *
	 * @param userUuid the user uuid of this notification queue entry recipient
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected NotificationQueueEntryRecipientWrapper wrap(
		NotificationQueueEntryRecipient notificationQueueEntryRecipient) {

		return new NotificationQueueEntryRecipientWrapper(
			notificationQueueEntryRecipient);
	}

}