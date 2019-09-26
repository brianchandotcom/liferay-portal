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

package com.liferay.calendar.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CalendarNotificationTemplate}.
 * </p>
 *
 * @author Eduardo Lundgren
 * @see CalendarNotificationTemplate
 * @generated
 */
public class CalendarNotificationTemplateWrapper
	extends BaseModelWrapper<CalendarNotificationTemplate>
	implements CalendarNotificationTemplate,
			   ModelWrapper<CalendarNotificationTemplate> {

	public CalendarNotificationTemplateWrapper(
		CalendarNotificationTemplate calendarNotificationTemplate) {

		super(calendarNotificationTemplate);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"calendarNotificationTemplateId",
			getCalendarNotificationTemplateId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("calendarId", getCalendarId());
		attributes.put("notificationType", getNotificationType());
		attributes.put(
			"notificationTypeSettings", getNotificationTypeSettings());
		attributes.put(
			"notificationTemplateType", getNotificationTemplateType());
		attributes.put("subject", getSubject());
		attributes.put("body", getBody());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long calendarNotificationTemplateId = (Long)attributes.get(
			"calendarNotificationTemplateId");

		if (calendarNotificationTemplateId != null) {
			setCalendarNotificationTemplateId(calendarNotificationTemplateId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		Long calendarId = (Long)attributes.get("calendarId");

		if (calendarId != null) {
			setCalendarId(calendarId);
		}

		String notificationType = (String)attributes.get("notificationType");

		if (notificationType != null) {
			setNotificationType(notificationType);
		}

		String notificationTypeSettings = (String)attributes.get(
			"notificationTypeSettings");

		if (notificationTypeSettings != null) {
			setNotificationTypeSettings(notificationTypeSettings);
		}

		String notificationTemplateType = (String)attributes.get(
			"notificationTemplateType");

		if (notificationTemplateType != null) {
			setNotificationTemplateType(notificationTemplateType);
		}

		String subject = (String)attributes.get("subject");

		if (subject != null) {
			setSubject(subject);
		}

		String body = (String)attributes.get("body");

		if (body != null) {
			setBody(body);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	 * Returns the body of this calendar notification template.
	 *
	 * @return the body of this calendar notification template
	 */
	@Override
	public String getBody() {
		return model.getBody();
	}

	/**
	 * Returns the calendar ID of this calendar notification template.
	 *
	 * @return the calendar ID of this calendar notification template
	 */
	@Override
	public long getCalendarId() {
		return model.getCalendarId();
	}

	/**
	 * Returns the calendar notification template ID of this calendar notification template.
	 *
	 * @return the calendar notification template ID of this calendar notification template
	 */
	@Override
	public long getCalendarNotificationTemplateId() {
		return model.getCalendarNotificationTemplateId();
	}

	/**
	 * Returns the company ID of this calendar notification template.
	 *
	 * @return the company ID of this calendar notification template
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this calendar notification template.
	 *
	 * @return the create date of this calendar notification template
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this calendar notification template.
	 *
	 * @return the group ID of this calendar notification template
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this calendar notification template.
	 *
	 * @return the last publish date of this calendar notification template
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this calendar notification template.
	 *
	 * @return the modified date of this calendar notification template
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this calendar notification template.
	 *
	 * @return the mvcc version of this calendar notification template
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the notification template type of this calendar notification template.
	 *
	 * @return the notification template type of this calendar notification template
	 */
	@Override
	public String getNotificationTemplateType() {
		return model.getNotificationTemplateType();
	}

	/**
	 * Returns the notification type of this calendar notification template.
	 *
	 * @return the notification type of this calendar notification template
	 */
	@Override
	public String getNotificationType() {
		return model.getNotificationType();
	}

	/**
	 * Returns the notification type settings of this calendar notification template.
	 *
	 * @return the notification type settings of this calendar notification template
	 */
	@Override
	public String getNotificationTypeSettings() {
		return model.getNotificationTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getNotificationTypeSettingsProperties() {

		return model.getNotificationTypeSettingsProperties();
	}

	/**
	 * Returns the primary key of this calendar notification template.
	 *
	 * @return the primary key of this calendar notification template
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the subject of this calendar notification template.
	 *
	 * @return the subject of this calendar notification template
	 */
	@Override
	public String getSubject() {
		return model.getSubject();
	}

	/**
	 * Returns the user ID of this calendar notification template.
	 *
	 * @return the user ID of this calendar notification template
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this calendar notification template.
	 *
	 * @return the user name of this calendar notification template
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this calendar notification template.
	 *
	 * @return the user uuid of this calendar notification template
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this calendar notification template.
	 *
	 * @return the uuid of this calendar notification template
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a calendar notification template model instance should use the <code>CalendarNotificationTemplate</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the body of this calendar notification template.
	 *
	 * @param body the body of this calendar notification template
	 */
	@Override
	public void setBody(String body) {
		model.setBody(body);
	}

	/**
	 * Sets the calendar ID of this calendar notification template.
	 *
	 * @param calendarId the calendar ID of this calendar notification template
	 */
	@Override
	public void setCalendarId(long calendarId) {
		model.setCalendarId(calendarId);
	}

	/**
	 * Sets the calendar notification template ID of this calendar notification template.
	 *
	 * @param calendarNotificationTemplateId the calendar notification template ID of this calendar notification template
	 */
	@Override
	public void setCalendarNotificationTemplateId(
		long calendarNotificationTemplateId) {

		model.setCalendarNotificationTemplateId(calendarNotificationTemplateId);
	}

	/**
	 * Sets the company ID of this calendar notification template.
	 *
	 * @param companyId the company ID of this calendar notification template
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this calendar notification template.
	 *
	 * @param createDate the create date of this calendar notification template
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this calendar notification template.
	 *
	 * @param groupId the group ID of this calendar notification template
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this calendar notification template.
	 *
	 * @param lastPublishDate the last publish date of this calendar notification template
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this calendar notification template.
	 *
	 * @param modifiedDate the modified date of this calendar notification template
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this calendar notification template.
	 *
	 * @param mvccVersion the mvcc version of this calendar notification template
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the notification template type of this calendar notification template.
	 *
	 * @param notificationTemplateType the notification template type of this calendar notification template
	 */
	@Override
	public void setNotificationTemplateType(String notificationTemplateType) {
		model.setNotificationTemplateType(notificationTemplateType);
	}

	/**
	 * Sets the notification type of this calendar notification template.
	 *
	 * @param notificationType the notification type of this calendar notification template
	 */
	@Override
	public void setNotificationType(String notificationType) {
		model.setNotificationType(notificationType);
	}

	/**
	 * Sets the notification type settings of this calendar notification template.
	 *
	 * @param notificationTypeSettings the notification type settings of this calendar notification template
	 */
	@Override
	public void setNotificationTypeSettings(String notificationTypeSettings) {
		model.setNotificationTypeSettings(notificationTypeSettings);
	}

	/**
	 * Sets the primary key of this calendar notification template.
	 *
	 * @param primaryKey the primary key of this calendar notification template
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the subject of this calendar notification template.
	 *
	 * @param subject the subject of this calendar notification template
	 */
	@Override
	public void setSubject(String subject) {
		model.setSubject(subject);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			notificationTypeSettingsProperties) {

		model.setTypeSettingsProperties(notificationTypeSettingsProperties);
	}

	/**
	 * Sets the user ID of this calendar notification template.
	 *
	 * @param userId the user ID of this calendar notification template
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this calendar notification template.
	 *
	 * @param userName the user name of this calendar notification template
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this calendar notification template.
	 *
	 * @param userUuid the user uuid of this calendar notification template
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this calendar notification template.
	 *
	 * @param uuid the uuid of this calendar notification template
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CalendarNotificationTemplateWrapper wrap(
		CalendarNotificationTemplate calendarNotificationTemplate) {

		return new CalendarNotificationTemplateWrapper(
			calendarNotificationTemplate);
	}

}