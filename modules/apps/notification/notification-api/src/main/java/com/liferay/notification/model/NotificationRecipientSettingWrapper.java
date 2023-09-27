/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link NotificationRecipientSetting}.
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationRecipientSetting
 * @generated
 */
public class NotificationRecipientSettingWrapper
	extends BaseModelWrapper<NotificationRecipientSetting>
	implements ModelWrapper<NotificationRecipientSetting>,
			   NotificationRecipientSetting {

	public NotificationRecipientSettingWrapper(
		NotificationRecipientSetting notificationRecipientSetting) {

		super(notificationRecipientSetting);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"notificationRecipientSettingId",
			getNotificationRecipientSettingId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("notificationRecipientId", getNotificationRecipientId());
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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long notificationRecipientSettingId = (Long)attributes.get(
			"notificationRecipientSettingId");

		if (notificationRecipientSettingId != null) {
			setNotificationRecipientSettingId(notificationRecipientSettingId);
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

		Long notificationRecipientId = (Long)attributes.get(
			"notificationRecipientId");

		if (notificationRecipientId != null) {
			setNotificationRecipientId(notificationRecipientId);
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
	public NotificationRecipientSetting cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this notification recipient setting.
	 *
	 * @return the company ID of this notification recipient setting
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this notification recipient setting.
	 *
	 * @return the create date of this notification recipient setting
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the modified date of this notification recipient setting.
	 *
	 * @return the modified date of this notification recipient setting
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this notification recipient setting.
	 *
	 * @return the mvcc version of this notification recipient setting
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this notification recipient setting.
	 *
	 * @return the name of this notification recipient setting
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the notification recipient ID of this notification recipient setting.
	 *
	 * @return the notification recipient ID of this notification recipient setting
	 */
	@Override
	public long getNotificationRecipientId() {
		return model.getNotificationRecipientId();
	}

	/**
	 * Returns the notification recipient setting ID of this notification recipient setting.
	 *
	 * @return the notification recipient setting ID of this notification recipient setting
	 */
	@Override
	public long getNotificationRecipientSettingId() {
		return model.getNotificationRecipientSettingId();
	}

	/**
	 * Returns the primary key of this notification recipient setting.
	 *
	 * @return the primary key of this notification recipient setting
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this notification recipient setting.
	 *
	 * @return the user ID of this notification recipient setting
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this notification recipient setting.
	 *
	 * @return the user name of this notification recipient setting
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this notification recipient setting.
	 *
	 * @return the user uuid of this notification recipient setting
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this notification recipient setting.
	 *
	 * @return the uuid of this notification recipient setting
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the value of this notification recipient setting.
	 *
	 * @return the value of this notification recipient setting
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	/**
	 * Returns the localized value of this notification recipient setting in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized value of this notification recipient setting
	 */
	@Override
	public String getValue(java.util.Locale locale) {
		return model.getValue(locale);
	}

	/**
	 * Returns the localized value of this notification recipient setting in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized value of this notification recipient setting. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getValue(java.util.Locale locale, boolean useDefault) {
		return model.getValue(locale, useDefault);
	}

	/**
	 * Returns the localized value of this notification recipient setting in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized value of this notification recipient setting
	 */
	@Override
	public String getValue(String languageId) {
		return model.getValue(languageId);
	}

	/**
	 * Returns the localized value of this notification recipient setting in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized value of this notification recipient setting
	 */
	@Override
	public String getValue(String languageId, boolean useDefault) {
		return model.getValue(languageId, useDefault);
	}

	@Override
	public String getValueCurrentLanguageId() {
		return model.getValueCurrentLanguageId();
	}

	@Override
	public String getValueCurrentValue() {
		return model.getValueCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized values of this notification recipient setting.
	 *
	 * @return the locales and localized values of this notification recipient setting
	 */
	@Override
	public Map<java.util.Locale, String> getValueMap() {
		return model.getValueMap();
	}

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
	 * Sets the company ID of this notification recipient setting.
	 *
	 * @param companyId the company ID of this notification recipient setting
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this notification recipient setting.
	 *
	 * @param createDate the create date of this notification recipient setting
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this notification recipient setting.
	 *
	 * @param modifiedDate the modified date of this notification recipient setting
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this notification recipient setting.
	 *
	 * @param mvccVersion the mvcc version of this notification recipient setting
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this notification recipient setting.
	 *
	 * @param name the name of this notification recipient setting
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the notification recipient ID of this notification recipient setting.
	 *
	 * @param notificationRecipientId the notification recipient ID of this notification recipient setting
	 */
	@Override
	public void setNotificationRecipientId(long notificationRecipientId) {
		model.setNotificationRecipientId(notificationRecipientId);
	}

	/**
	 * Sets the notification recipient setting ID of this notification recipient setting.
	 *
	 * @param notificationRecipientSettingId the notification recipient setting ID of this notification recipient setting
	 */
	@Override
	public void setNotificationRecipientSettingId(
		long notificationRecipientSettingId) {

		model.setNotificationRecipientSettingId(notificationRecipientSettingId);
	}

	/**
	 * Sets the primary key of this notification recipient setting.
	 *
	 * @param primaryKey the primary key of this notification recipient setting
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this notification recipient setting.
	 *
	 * @param userId the user ID of this notification recipient setting
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this notification recipient setting.
	 *
	 * @param userName the user name of this notification recipient setting
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this notification recipient setting.
	 *
	 * @param userUuid the user uuid of this notification recipient setting
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this notification recipient setting.
	 *
	 * @param uuid the uuid of this notification recipient setting
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the value of this notification recipient setting.
	 *
	 * @param value the value of this notification recipient setting
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	/**
	 * Sets the localized value of this notification recipient setting in the language.
	 *
	 * @param value the localized value of this notification recipient setting
	 * @param locale the locale of the language
	 */
	@Override
	public void setValue(String value, java.util.Locale locale) {
		model.setValue(value, locale);
	}

	/**
	 * Sets the localized value of this notification recipient setting in the language, and sets the default locale.
	 *
	 * @param value the localized value of this notification recipient setting
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setValue(
		String value, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setValue(value, locale, defaultLocale);
	}

	@Override
	public void setValueCurrentLanguageId(String languageId) {
		model.setValueCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized values of this notification recipient setting from the map of locales and localized values.
	 *
	 * @param valueMap the locales and localized values of this notification recipient setting
	 */
	@Override
	public void setValueMap(Map<java.util.Locale, String> valueMap) {
		model.setValueMap(valueMap);
	}

	/**
	 * Sets the localized values of this notification recipient setting from the map of locales and localized values, and sets the default locale.
	 *
	 * @param valueMap the locales and localized values of this notification recipient setting
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setValueMap(
		Map<java.util.Locale, String> valueMap,
		java.util.Locale defaultLocale) {

		model.setValueMap(valueMap, defaultLocale);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected NotificationRecipientSettingWrapper wrap(
		NotificationRecipientSetting notificationRecipientSetting) {

		return new NotificationRecipientSettingWrapper(
			notificationRecipientSetting);
	}

}