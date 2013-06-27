/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CalEvent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CalEvent
 * @generated
 */
public class CalEventWrapper implements CalEvent, ModelWrapper<CalEvent> {
	public CalEventWrapper(CalEvent calEvent) {
		_calEvent = calEvent;
	}

	@Override
	public Class<?> getModelClass() {
		return CalEvent.class;
	}

	@Override
	public String getModelClassName() {
		return CalEvent.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("eventId", getEventId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("location", getLocation());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("durationHour", getDurationHour());
		attributes.put("durationMinute", getDurationMinute());
		attributes.put("allDay", getAllDay());
		attributes.put("timeZoneSensitive", getTimeZoneSensitive());
		attributes.put("type", getType());
		attributes.put("repeating", getRepeating());
		attributes.put("recurrence", getRecurrence());
		attributes.put("remindBy", getRemindBy());
		attributes.put("firstReminder", getFirstReminder());
		attributes.put("secondReminder", getSecondReminder());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long eventId = (Long)attributes.get("eventId");

		if (eventId != null) {
			setEventId(eventId);
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String location = (String)attributes.get("location");

		if (location != null) {
			setLocation(location);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Integer durationHour = (Integer)attributes.get("durationHour");

		if (durationHour != null) {
			setDurationHour(durationHour);
		}

		Integer durationMinute = (Integer)attributes.get("durationMinute");

		if (durationMinute != null) {
			setDurationMinute(durationMinute);
		}

		Boolean allDay = (Boolean)attributes.get("allDay");

		if (allDay != null) {
			setAllDay(allDay);
		}

		Boolean timeZoneSensitive = (Boolean)attributes.get("timeZoneSensitive");

		if (timeZoneSensitive != null) {
			setTimeZoneSensitive(timeZoneSensitive);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean repeating = (Boolean)attributes.get("repeating");

		if (repeating != null) {
			setRepeating(repeating);
		}

		String recurrence = (String)attributes.get("recurrence");

		if (recurrence != null) {
			setRecurrence(recurrence);
		}

		Integer remindBy = (Integer)attributes.get("remindBy");

		if (remindBy != null) {
			setRemindBy(remindBy);
		}

		Integer firstReminder = (Integer)attributes.get("firstReminder");

		if (firstReminder != null) {
			setFirstReminder(firstReminder);
		}

		Integer secondReminder = (Integer)attributes.get("secondReminder");

		if (secondReminder != null) {
			setSecondReminder(secondReminder);
		}
	}

	/**
	* Returns the primary key of this cal event.
	*
	* @return the primary key of this cal event
	*/
	@Override
	public long getPrimaryKey() {
		return _calEvent.getPrimaryKey();
	}

	/**
	* Sets the primary key of this cal event.
	*
	* @param primaryKey the primary key of this cal event
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_calEvent.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this cal event.
	*
	* @return the uuid of this cal event
	*/
	@Override
	public java.lang.String getUuid() {
		return _calEvent.getUuid();
	}

	/**
	* Sets the uuid of this cal event.
	*
	* @param uuid the uuid of this cal event
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_calEvent.setUuid(uuid);
	}

	/**
	* Returns the event ID of this cal event.
	*
	* @return the event ID of this cal event
	*/
	@Override
	public long getEventId() {
		return _calEvent.getEventId();
	}

	/**
	* Sets the event ID of this cal event.
	*
	* @param eventId the event ID of this cal event
	*/
	@Override
	public void setEventId(long eventId) {
		_calEvent.setEventId(eventId);
	}

	/**
	* Returns the group ID of this cal event.
	*
	* @return the group ID of this cal event
	*/
	@Override
	public long getGroupId() {
		return _calEvent.getGroupId();
	}

	/**
	* Sets the group ID of this cal event.
	*
	* @param groupId the group ID of this cal event
	*/
	@Override
	public void setGroupId(long groupId) {
		_calEvent.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this cal event.
	*
	* @return the company ID of this cal event
	*/
	@Override
	public long getCompanyId() {
		return _calEvent.getCompanyId();
	}

	/**
	* Sets the company ID of this cal event.
	*
	* @param companyId the company ID of this cal event
	*/
	@Override
	public void setCompanyId(long companyId) {
		_calEvent.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this cal event.
	*
	* @return the user ID of this cal event
	*/
	@Override
	public long getUserId() {
		return _calEvent.getUserId();
	}

	/**
	* Sets the user ID of this cal event.
	*
	* @param userId the user ID of this cal event
	*/
	@Override
	public void setUserId(long userId) {
		_calEvent.setUserId(userId);
	}

	/**
	* Returns the user uuid of this cal event.
	*
	* @return the user uuid of this cal event
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEvent.getUserUuid();
	}

	/**
	* Sets the user uuid of this cal event.
	*
	* @param userUuid the user uuid of this cal event
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_calEvent.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this cal event.
	*
	* @return the user name of this cal event
	*/
	@Override
	public java.lang.String getUserName() {
		return _calEvent.getUserName();
	}

	/**
	* Sets the user name of this cal event.
	*
	* @param userName the user name of this cal event
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_calEvent.setUserName(userName);
	}

	/**
	* Returns the create date of this cal event.
	*
	* @return the create date of this cal event
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _calEvent.getCreateDate();
	}

	/**
	* Sets the create date of this cal event.
	*
	* @param createDate the create date of this cal event
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_calEvent.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this cal event.
	*
	* @return the modified date of this cal event
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _calEvent.getModifiedDate();
	}

	/**
	* Sets the modified date of this cal event.
	*
	* @param modifiedDate the modified date of this cal event
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_calEvent.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the title of this cal event.
	*
	* @return the title of this cal event
	*/
	@Override
	public java.lang.String getTitle() {
		return _calEvent.getTitle();
	}

	/**
	* Sets the title of this cal event.
	*
	* @param title the title of this cal event
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_calEvent.setTitle(title);
	}

	/**
	* Returns the description of this cal event.
	*
	* @return the description of this cal event
	*/
	@Override
	public java.lang.String getDescription() {
		return _calEvent.getDescription();
	}

	/**
	* Sets the description of this cal event.
	*
	* @param description the description of this cal event
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_calEvent.setDescription(description);
	}

	/**
	* Returns the location of this cal event.
	*
	* @return the location of this cal event
	*/
	@Override
	public java.lang.String getLocation() {
		return _calEvent.getLocation();
	}

	/**
	* Sets the location of this cal event.
	*
	* @param location the location of this cal event
	*/
	@Override
	public void setLocation(java.lang.String location) {
		_calEvent.setLocation(location);
	}

	/**
	* Returns the start date of this cal event.
	*
	* @return the start date of this cal event
	*/
	@Override
	public java.util.Date getStartDate() {
		return _calEvent.getStartDate();
	}

	/**
	* Sets the start date of this cal event.
	*
	* @param startDate the start date of this cal event
	*/
	@Override
	public void setStartDate(java.util.Date startDate) {
		_calEvent.setStartDate(startDate);
	}

	/**
	* Returns the end date of this cal event.
	*
	* @return the end date of this cal event
	*/
	@Override
	public java.util.Date getEndDate() {
		return _calEvent.getEndDate();
	}

	/**
	* Sets the end date of this cal event.
	*
	* @param endDate the end date of this cal event
	*/
	@Override
	public void setEndDate(java.util.Date endDate) {
		_calEvent.setEndDate(endDate);
	}

	/**
	* Returns the duration hour of this cal event.
	*
	* @return the duration hour of this cal event
	*/
	@Override
	public int getDurationHour() {
		return _calEvent.getDurationHour();
	}

	/**
	* Sets the duration hour of this cal event.
	*
	* @param durationHour the duration hour of this cal event
	*/
	@Override
	public void setDurationHour(int durationHour) {
		_calEvent.setDurationHour(durationHour);
	}

	/**
	* Returns the duration minute of this cal event.
	*
	* @return the duration minute of this cal event
	*/
	@Override
	public int getDurationMinute() {
		return _calEvent.getDurationMinute();
	}

	/**
	* Sets the duration minute of this cal event.
	*
	* @param durationMinute the duration minute of this cal event
	*/
	@Override
	public void setDurationMinute(int durationMinute) {
		_calEvent.setDurationMinute(durationMinute);
	}

	/**
	* Returns the all day of this cal event.
	*
	* @return the all day of this cal event
	*/
	@Override
	public boolean getAllDay() {
		return _calEvent.getAllDay();
	}

	/**
	* Returns <code>true</code> if this cal event is all day.
	*
	* @return <code>true</code> if this cal event is all day; <code>false</code> otherwise
	*/
	@Override
	public boolean isAllDay() {
		return _calEvent.isAllDay();
	}

	/**
	* Sets whether this cal event is all day.
	*
	* @param allDay the all day of this cal event
	*/
	@Override
	public void setAllDay(boolean allDay) {
		_calEvent.setAllDay(allDay);
	}

	/**
	* Returns the time zone sensitive of this cal event.
	*
	* @return the time zone sensitive of this cal event
	*/
	@Override
	public boolean getTimeZoneSensitive() {
		return _calEvent.getTimeZoneSensitive();
	}

	/**
	* Returns <code>true</code> if this cal event is time zone sensitive.
	*
	* @return <code>true</code> if this cal event is time zone sensitive; <code>false</code> otherwise
	*/
	@Override
	public boolean isTimeZoneSensitive() {
		return _calEvent.isTimeZoneSensitive();
	}

	/**
	* Sets whether this cal event is time zone sensitive.
	*
	* @param timeZoneSensitive the time zone sensitive of this cal event
	*/
	@Override
	public void setTimeZoneSensitive(boolean timeZoneSensitive) {
		_calEvent.setTimeZoneSensitive(timeZoneSensitive);
	}

	/**
	* Returns the type of this cal event.
	*
	* @return the type of this cal event
	*/
	@Override
	public java.lang.String getType() {
		return _calEvent.getType();
	}

	/**
	* Sets the type of this cal event.
	*
	* @param type the type of this cal event
	*/
	@Override
	public void setType(java.lang.String type) {
		_calEvent.setType(type);
	}

	/**
	* Returns the repeating of this cal event.
	*
	* @return the repeating of this cal event
	*/
	@Override
	public boolean getRepeating() {
		return _calEvent.getRepeating();
	}

	/**
	* Returns <code>true</code> if this cal event is repeating.
	*
	* @return <code>true</code> if this cal event is repeating; <code>false</code> otherwise
	*/
	@Override
	public boolean isRepeating() {
		return _calEvent.isRepeating();
	}

	/**
	* Sets whether this cal event is repeating.
	*
	* @param repeating the repeating of this cal event
	*/
	@Override
	public void setRepeating(boolean repeating) {
		_calEvent.setRepeating(repeating);
	}

	/**
	* Returns the recurrence of this cal event.
	*
	* @return the recurrence of this cal event
	*/
	@Override
	public java.lang.String getRecurrence() {
		return _calEvent.getRecurrence();
	}

	/**
	* Sets the recurrence of this cal event.
	*
	* @param recurrence the recurrence of this cal event
	*/
	@Override
	public void setRecurrence(java.lang.String recurrence) {
		_calEvent.setRecurrence(recurrence);
	}

	/**
	* Returns the remind by of this cal event.
	*
	* @return the remind by of this cal event
	*/
	@Override
	public int getRemindBy() {
		return _calEvent.getRemindBy();
	}

	/**
	* Sets the remind by of this cal event.
	*
	* @param remindBy the remind by of this cal event
	*/
	@Override
	public void setRemindBy(int remindBy) {
		_calEvent.setRemindBy(remindBy);
	}

	/**
	* Returns the first reminder of this cal event.
	*
	* @return the first reminder of this cal event
	*/
	@Override
	public int getFirstReminder() {
		return _calEvent.getFirstReminder();
	}

	/**
	* Sets the first reminder of this cal event.
	*
	* @param firstReminder the first reminder of this cal event
	*/
	@Override
	public void setFirstReminder(int firstReminder) {
		_calEvent.setFirstReminder(firstReminder);
	}

	/**
	* Returns the second reminder of this cal event.
	*
	* @return the second reminder of this cal event
	*/
	@Override
	public int getSecondReminder() {
		return _calEvent.getSecondReminder();
	}

	/**
	* Sets the second reminder of this cal event.
	*
	* @param secondReminder the second reminder of this cal event
	*/
	@Override
	public void setSecondReminder(int secondReminder) {
		_calEvent.setSecondReminder(secondReminder);
	}

	@Override
	public boolean isNew() {
		return _calEvent.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_calEvent.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _calEvent.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_calEvent.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _calEvent.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _calEvent.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_calEvent.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _calEvent.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_calEvent.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_calEvent.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_calEvent.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new CalEventWrapper((CalEvent)_calEvent.clone());
	}

	@Override
	public int compareTo(com.liferay.portlet.calendar.model.CalEvent calEvent) {
		return _calEvent.compareTo(calEvent);
	}

	@Override
	public int hashCode() {
		return _calEvent.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.calendar.model.CalEvent> toCacheModel() {
		return _calEvent.toCacheModel();
	}

	@Override
	public com.liferay.portlet.calendar.model.CalEvent toEscapedModel() {
		return new CalEventWrapper(_calEvent.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.calendar.model.CalEvent toUnescapedModel() {
		return new CalEventWrapper(_calEvent.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _calEvent.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _calEvent.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_calEvent.persist();
	}

	@Override
	public com.liferay.portal.kernel.cal.TZSRecurrence getRecurrenceObj() {
		return _calEvent.getRecurrenceObj();
	}

	@Override
	public void setRecurrenceObj(
		com.liferay.portal.kernel.cal.TZSRecurrence recurrenceObj) {
		_calEvent.setRecurrenceObj(recurrenceObj);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CalEventWrapper)) {
			return false;
		}

		CalEventWrapper calEventWrapper = (CalEventWrapper)obj;

		if (Validator.equals(_calEvent, calEventWrapper._calEvent)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public CalEvent getWrappedCalEvent() {
		return _calEvent;
	}

	@Override
	public CalEvent getWrappedModel() {
		return _calEvent;
	}

	@Override
	public void resetOriginalValues() {
		_calEvent.resetOriginalValues();
	}

	private CalEvent _calEvent;
}