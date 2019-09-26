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
 * This class is a wrapper for {@link Calendar}.
 * </p>
 *
 * @author Eduardo Lundgren
 * @see Calendar
 * @generated
 */
public class CalendarWrapper
	extends BaseModelWrapper<Calendar>
	implements Calendar, ModelWrapper<Calendar> {

	public CalendarWrapper(Calendar calendar) {
		super(calendar);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("calendarId", getCalendarId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("calendarResourceId", getCalendarResourceId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("timeZoneId", getTimeZoneId());
		attributes.put("color", getColor());
		attributes.put("defaultCalendar", isDefaultCalendar());
		attributes.put("enableComments", isEnableComments());
		attributes.put("enableRatings", isEnableRatings());
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

		Long calendarId = (Long)attributes.get("calendarId");

		if (calendarId != null) {
			setCalendarId(calendarId);
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

		Long calendarResourceId = (Long)attributes.get("calendarResourceId");

		if (calendarResourceId != null) {
			setCalendarResourceId(calendarResourceId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String timeZoneId = (String)attributes.get("timeZoneId");

		if (timeZoneId != null) {
			setTimeZoneId(timeZoneId);
		}

		Integer color = (Integer)attributes.get("color");

		if (color != null) {
			setColor(color);
		}

		Boolean defaultCalendar = (Boolean)attributes.get("defaultCalendar");

		if (defaultCalendar != null) {
			setDefaultCalendar(defaultCalendar);
		}

		Boolean enableComments = (Boolean)attributes.get("enableComments");

		if (enableComments != null) {
			setEnableComments(enableComments);
		}

		Boolean enableRatings = (Boolean)attributes.get("enableRatings");

		if (enableRatings != null) {
			setEnableRatings(enableRatings);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the calendar ID of this calendar.
	 *
	 * @return the calendar ID of this calendar
	 */
	@Override
	public long getCalendarId() {
		return model.getCalendarId();
	}

	@Override
	public CalendarResource getCalendarResource()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCalendarResource();
	}

	/**
	 * Returns the calendar resource ID of this calendar.
	 *
	 * @return the calendar resource ID of this calendar
	 */
	@Override
	public long getCalendarResourceId() {
		return model.getCalendarResourceId();
	}

	/**
	 * Returns the color of this calendar.
	 *
	 * @return the color of this calendar
	 */
	@Override
	public int getColor() {
		return model.getColor();
	}

	/**
	 * Returns the company ID of this calendar.
	 *
	 * @return the company ID of this calendar
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this calendar.
	 *
	 * @return the create date of this calendar
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the default calendar of this calendar.
	 *
	 * @return the default calendar of this calendar
	 */
	@Override
	public boolean getDefaultCalendar() {
		return model.getDefaultCalendar();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the description of this calendar.
	 *
	 * @return the description of this calendar
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the localized description of this calendar in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this calendar
	 */
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	 * Returns the localized description of this calendar in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this calendar. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	 * Returns the localized description of this calendar in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this calendar
	 */
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	 * Returns the localized description of this calendar in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this calendar
	 */
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return model.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return model.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return model.getDescriptionCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized descriptions of this calendar.
	 *
	 * @return the locales and localized descriptions of this calendar
	 */
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	 * Returns the enable comments of this calendar.
	 *
	 * @return the enable comments of this calendar
	 */
	@Override
	public boolean getEnableComments() {
		return model.getEnableComments();
	}

	/**
	 * Returns the enable ratings of this calendar.
	 *
	 * @return the enable ratings of this calendar
	 */
	@Override
	public boolean getEnableRatings() {
		return model.getEnableRatings();
	}

	/**
	 * Returns the group ID of this calendar.
	 *
	 * @return the group ID of this calendar
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this calendar.
	 *
	 * @return the last publish date of this calendar
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this calendar.
	 *
	 * @return the modified date of this calendar
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this calendar.
	 *
	 * @return the mvcc version of this calendar
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this calendar.
	 *
	 * @return the name of this calendar
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this calendar in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this calendar
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this calendar in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this calendar. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this calendar in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this calendar
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this calendar in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this calendar
	 */
	@Override
	public String getName(String languageId, boolean useDefault) {
		return model.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return model.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return model.getNameCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized names of this calendar.
	 *
	 * @return the locales and localized names of this calendar
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the primary key of this calendar.
	 *
	 * @return the primary key of this calendar
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public java.util.TimeZone getTimeZone() {
		return model.getTimeZone();
	}

	/**
	 * Returns the time zone ID of this calendar.
	 *
	 * @return the time zone ID of this calendar
	 */
	@Override
	public String getTimeZoneId() {
		return model.getTimeZoneId();
	}

	/**
	 * Returns the user ID of this calendar.
	 *
	 * @return the user ID of this calendar
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this calendar.
	 *
	 * @return the user name of this calendar
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this calendar.
	 *
	 * @return the user uuid of this calendar
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this calendar.
	 *
	 * @return the uuid of this calendar
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this calendar is default calendar.
	 *
	 * @return <code>true</code> if this calendar is default calendar; <code>false</code> otherwise
	 */
	@Override
	public boolean isDefaultCalendar() {
		return model.isDefaultCalendar();
	}

	/**
	 * Returns <code>true</code> if this calendar is enable comments.
	 *
	 * @return <code>true</code> if this calendar is enable comments; <code>false</code> otherwise
	 */
	@Override
	public boolean isEnableComments() {
		return model.isEnableComments();
	}

	/**
	 * Returns <code>true</code> if this calendar is enable ratings.
	 *
	 * @return <code>true</code> if this calendar is enable ratings; <code>false</code> otherwise
	 */
	@Override
	public boolean isEnableRatings() {
		return model.isEnableRatings();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a calendar model instance should use the <code>Calendar</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	 * Sets the calendar ID of this calendar.
	 *
	 * @param calendarId the calendar ID of this calendar
	 */
	@Override
	public void setCalendarId(long calendarId) {
		model.setCalendarId(calendarId);
	}

	/**
	 * Sets the calendar resource ID of this calendar.
	 *
	 * @param calendarResourceId the calendar resource ID of this calendar
	 */
	@Override
	public void setCalendarResourceId(long calendarResourceId) {
		model.setCalendarResourceId(calendarResourceId);
	}

	/**
	 * Sets the color of this calendar.
	 *
	 * @param color the color of this calendar
	 */
	@Override
	public void setColor(int color) {
		model.setColor(color);
	}

	/**
	 * Sets the company ID of this calendar.
	 *
	 * @param companyId the company ID of this calendar
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this calendar.
	 *
	 * @param createDate the create date of this calendar
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets whether this calendar is default calendar.
	 *
	 * @param defaultCalendar the default calendar of this calendar
	 */
	@Override
	public void setDefaultCalendar(boolean defaultCalendar) {
		model.setDefaultCalendar(defaultCalendar);
	}

	/**
	 * Sets the description of this calendar.
	 *
	 * @param description the description of this calendar
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the localized description of this calendar in the language.
	 *
	 * @param description the localized description of this calendar
	 * @param locale the locale of the language
	 */
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	 * Sets the localized description of this calendar in the language, and sets the default locale.
	 *
	 * @param description the localized description of this calendar
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescription(
		String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		model.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized descriptions of this calendar from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this calendar
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		model.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the localized descriptions of this calendar from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this calendar
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {

		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	 * Sets whether this calendar is enable comments.
	 *
	 * @param enableComments the enable comments of this calendar
	 */
	@Override
	public void setEnableComments(boolean enableComments) {
		model.setEnableComments(enableComments);
	}

	/**
	 * Sets whether this calendar is enable ratings.
	 *
	 * @param enableRatings the enable ratings of this calendar
	 */
	@Override
	public void setEnableRatings(boolean enableRatings) {
		model.setEnableRatings(enableRatings);
	}

	/**
	 * Sets the group ID of this calendar.
	 *
	 * @param groupId the group ID of this calendar
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this calendar.
	 *
	 * @param lastPublishDate the last publish date of this calendar
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this calendar.
	 *
	 * @param modifiedDate the modified date of this calendar
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this calendar.
	 *
	 * @param mvccVersion the mvcc version of this calendar
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this calendar.
	 *
	 * @param name the name of this calendar
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this calendar in the language.
	 *
	 * @param name the localized name of this calendar
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this calendar in the language, and sets the default locale.
	 *
	 * @param name the localized name of this calendar
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setName(
		String name, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized names of this calendar from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this calendar
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this calendar from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this calendar
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the primary key of this calendar.
	 *
	 * @param primaryKey the primary key of this calendar
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the time zone ID of this calendar.
	 *
	 * @param timeZoneId the time zone ID of this calendar
	 */
	@Override
	public void setTimeZoneId(String timeZoneId) {
		model.setTimeZoneId(timeZoneId);
	}

	/**
	 * Sets the user ID of this calendar.
	 *
	 * @param userId the user ID of this calendar
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this calendar.
	 *
	 * @param userName the user name of this calendar
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this calendar.
	 *
	 * @param userUuid the user uuid of this calendar
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this calendar.
	 *
	 * @param uuid the uuid of this calendar
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
	protected CalendarWrapper wrap(Calendar calendar) {
		return new CalendarWrapper(calendar);
	}

}