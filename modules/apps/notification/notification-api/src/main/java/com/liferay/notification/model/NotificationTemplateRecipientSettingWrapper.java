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
 * This class is a wrapper for {@link NotificationTemplateRecipientSetting}.
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateRecipientSetting
 * @generated
 */
public class NotificationTemplateRecipientSettingWrapper
	extends BaseModelWrapper<NotificationTemplateRecipientSetting>
	implements ModelWrapper<NotificationTemplateRecipientSetting>,
			   NotificationTemplateRecipientSetting {

	public NotificationTemplateRecipientSettingWrapper(
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting) {

		super(notificationTemplateRecipientSetting);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"notificationTemplateRecipientSettingId",
			getNotificationTemplateRecipientSettingId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"notificationTemplateRecipientId",
			getNotificationTemplateRecipientId());
		attributes.put("name", getName());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long notificationTemplateRecipientSettingId = (Long)attributes.get(
			"notificationTemplateRecipientSettingId");

		if (notificationTemplateRecipientSettingId != null) {
			setNotificationTemplateRecipientSettingId(
				notificationTemplateRecipientSettingId);
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

		Long notificationTemplateRecipientId = (Long)attributes.get(
			"notificationTemplateRecipientId");

		if (notificationTemplateRecipientId != null) {
			setNotificationTemplateRecipientId(notificationTemplateRecipientId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public NotificationTemplateRecipientSetting cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this notification template recipient setting.
	 *
	 * @return the company ID of this notification template recipient setting
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this notification template recipient setting.
	 *
	 * @return the create date of this notification template recipient setting
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this notification template recipient setting.
	 *
	 * @return the modified date of this notification template recipient setting
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this notification template recipient setting.
	 *
	 * @return the mvcc version of this notification template recipient setting
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this notification template recipient setting.
	 *
	 * @return the name of this notification template recipient setting
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the notification template recipient ID of this notification template recipient setting.
	 *
	 * @return the notification template recipient ID of this notification template recipient setting
	 */
	@Override
	public long getNotificationTemplateRecipientId() {
		return model.getNotificationTemplateRecipientId();
	}

	/**
	 * Returns the notification template recipient setting ID of this notification template recipient setting.
	 *
	 * @return the notification template recipient setting ID of this notification template recipient setting
	 */
	@Override
	public long getNotificationTemplateRecipientSettingId() {
		return model.getNotificationTemplateRecipientSettingId();
	}

	/**
	 * Returns the primary key of this notification template recipient setting.
	 *
	 * @return the primary key of this notification template recipient setting
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this notification template recipient setting.
	 *
	 * @return the user ID of this notification template recipient setting
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this notification template recipient setting.
	 *
	 * @return the user name of this notification template recipient setting
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this notification template recipient setting.
	 *
	 * @return the user uuid of this notification template recipient setting
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the value of this notification template recipient setting.
	 *
	 * @return the value of this notification template recipient setting
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this notification template recipient setting.
	 *
	 * @param companyId the company ID of this notification template recipient setting
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this notification template recipient setting.
	 *
	 * @param createDate the create date of this notification template recipient setting
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this notification template recipient setting.
	 *
	 * @param modifiedDate the modified date of this notification template recipient setting
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this notification template recipient setting.
	 *
	 * @param mvccVersion the mvcc version of this notification template recipient setting
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this notification template recipient setting.
	 *
	 * @param name the name of this notification template recipient setting
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the notification template recipient ID of this notification template recipient setting.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID of this notification template recipient setting
	 */
	@Override
	public void setNotificationTemplateRecipientId(
		long notificationTemplateRecipientId) {

		model.setNotificationTemplateRecipientId(
			notificationTemplateRecipientId);
	}

	/**
	 * Sets the notification template recipient setting ID of this notification template recipient setting.
	 *
	 * @param notificationTemplateRecipientSettingId the notification template recipient setting ID of this notification template recipient setting
	 */
	@Override
	public void setNotificationTemplateRecipientSettingId(
		long notificationTemplateRecipientSettingId) {

		model.setNotificationTemplateRecipientSettingId(
			notificationTemplateRecipientSettingId);
	}

	/**
	 * Sets the primary key of this notification template recipient setting.
	 *
	 * @param primaryKey the primary key of this notification template recipient setting
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this notification template recipient setting.
	 *
	 * @param userId the user ID of this notification template recipient setting
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this notification template recipient setting.
	 *
	 * @param userName the user name of this notification template recipient setting
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this notification template recipient setting.
	 *
	 * @param userUuid the user uuid of this notification template recipient setting
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the value of this notification template recipient setting.
	 *
	 * @param value the value of this notification template recipient setting
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	protected NotificationTemplateRecipientSettingWrapper wrap(
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting) {

		return new NotificationTemplateRecipientSettingWrapper(
			notificationTemplateRecipientSetting);
	}

}