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

package com.liferay.notifications.admin.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link NotificationsTemplate}.
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationsTemplate
 * @generated
 */
public class NotificationsTemplateWrapper
	extends BaseModelWrapper<NotificationsTemplate>
	implements ModelWrapper<NotificationsTemplate>, NotificationsTemplate {

	public NotificationsTemplateWrapper(
		NotificationsTemplate notificationsTemplate) {

		super(notificationsTemplate);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("notificationsTemplateId", getNotificationsTemplateId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());

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

		Long notificationsTemplateId = (Long)attributes.get(
			"notificationsTemplateId");

		if (notificationsTemplateId != null) {
			setNotificationsTemplateId(notificationsTemplateId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public NotificationsTemplate cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this notifications template.
	 *
	 * @return the company ID of this notifications template
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this notifications template.
	 *
	 * @return the create date of this notifications template
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
	 * Returns the modified date of this notifications template.
	 *
	 * @return the modified date of this notifications template
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this notifications template.
	 *
	 * @return the mvcc version of this notifications template
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this notifications template.
	 *
	 * @return the name of this notifications template
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this notifications template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this notifications template
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this notifications template in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this notifications template. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this notifications template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this notifications template
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this notifications template in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this notifications template
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
	 * Returns a map of the locales and localized names of this notifications template.
	 *
	 * @return the locales and localized names of this notifications template
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the notifications template ID of this notifications template.
	 *
	 * @return the notifications template ID of this notifications template
	 */
	@Override
	public long getNotificationsTemplateId() {
		return model.getNotificationsTemplateId();
	}

	/**
	 * Returns the primary key of this notifications template.
	 *
	 * @return the primary key of this notifications template
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this notifications template.
	 *
	 * @return the user ID of this notifications template
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this notifications template.
	 *
	 * @return the user name of this notifications template
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this notifications template.
	 *
	 * @return the user uuid of this notifications template
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this notifications template.
	 *
	 * @return the uuid of this notifications template
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
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
	 * Sets the company ID of this notifications template.
	 *
	 * @param companyId the company ID of this notifications template
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this notifications template.
	 *
	 * @param createDate the create date of this notifications template
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this notifications template.
	 *
	 * @param modifiedDate the modified date of this notifications template
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this notifications template.
	 *
	 * @param mvccVersion the mvcc version of this notifications template
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this notifications template.
	 *
	 * @param name the name of this notifications template
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this notifications template in the language.
	 *
	 * @param name the localized name of this notifications template
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this notifications template in the language, and sets the default locale.
	 *
	 * @param name the localized name of this notifications template
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
	 * Sets the localized names of this notifications template from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this notifications template
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this notifications template from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this notifications template
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the notifications template ID of this notifications template.
	 *
	 * @param notificationsTemplateId the notifications template ID of this notifications template
	 */
	@Override
	public void setNotificationsTemplateId(long notificationsTemplateId) {
		model.setNotificationsTemplateId(notificationsTemplateId);
	}

	/**
	 * Sets the primary key of this notifications template.
	 *
	 * @param primaryKey the primary key of this notifications template
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this notifications template.
	 *
	 * @param userId the user ID of this notifications template
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this notifications template.
	 *
	 * @param userName the user name of this notifications template
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this notifications template.
	 *
	 * @param userUuid the user uuid of this notifications template
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this notifications template.
	 *
	 * @param uuid the uuid of this notifications template
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
	protected NotificationsTemplateWrapper wrap(
		NotificationsTemplate notificationsTemplate) {

		return new NotificationsTemplateWrapper(notificationsTemplate);
	}

}