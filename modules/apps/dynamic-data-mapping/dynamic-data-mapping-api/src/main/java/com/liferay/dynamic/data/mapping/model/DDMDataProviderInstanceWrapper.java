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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMDataProviderInstance}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstance
 * @generated
 */
public class DDMDataProviderInstanceWrapper
	extends BaseModelWrapper<DDMDataProviderInstance>
	implements DDMDataProviderInstance, ModelWrapper<DDMDataProviderInstance> {

	public DDMDataProviderInstanceWrapper(
		DDMDataProviderInstance ddmDataProviderInstance) {

		super(ddmDataProviderInstance);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("dataProviderInstanceId", getDataProviderInstanceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("definition", getDefinition());
		attributes.put("type", getType());
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

		Long dataProviderInstanceId = (Long)attributes.get(
			"dataProviderInstanceId");

		if (dataProviderInstanceId != null) {
			setDataProviderInstanceId(dataProviderInstanceId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String definition = (String)attributes.get("definition");

		if (definition != null) {
			setDefinition(definition);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
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
	 * Returns the company ID of this ddm data provider instance.
	 *
	 * @return the company ID of this ddm data provider instance
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ddm data provider instance.
	 *
	 * @return the create date of this ddm data provider instance
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the data provider instance ID of this ddm data provider instance.
	 *
	 * @return the data provider instance ID of this ddm data provider instance
	 */
	@Override
	public long getDataProviderInstanceId() {
		return model.getDataProviderInstanceId();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the definition of this ddm data provider instance.
	 *
	 * @return the definition of this ddm data provider instance
	 */
	@Override
	public String getDefinition() {
		return model.getDefinition();
	}

	/**
	 * Returns the description of this ddm data provider instance.
	 *
	 * @return the description of this ddm data provider instance
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the localized description of this ddm data provider instance in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this ddm data provider instance
	 */
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	 * Returns the localized description of this ddm data provider instance in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this ddm data provider instance. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	 * Returns the localized description of this ddm data provider instance in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this ddm data provider instance
	 */
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	 * Returns the localized description of this ddm data provider instance in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this ddm data provider instance
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
	 * Returns a map of the locales and localized descriptions of this ddm data provider instance.
	 *
	 * @return the locales and localized descriptions of this ddm data provider instance
	 */
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	 * Returns the group ID of this ddm data provider instance.
	 *
	 * @return the group ID of this ddm data provider instance
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this ddm data provider instance.
	 *
	 * @return the last publish date of this ddm data provider instance
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this ddm data provider instance.
	 *
	 * @return the modified date of this ddm data provider instance
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this ddm data provider instance.
	 *
	 * @return the mvcc version of this ddm data provider instance
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this ddm data provider instance.
	 *
	 * @return the name of this ddm data provider instance
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this ddm data provider instance in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this ddm data provider instance
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this ddm data provider instance in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this ddm data provider instance. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this ddm data provider instance in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this ddm data provider instance
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this ddm data provider instance in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this ddm data provider instance
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
	 * Returns a map of the locales and localized names of this ddm data provider instance.
	 *
	 * @return the locales and localized names of this ddm data provider instance
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the primary key of this ddm data provider instance.
	 *
	 * @return the primary key of this ddm data provider instance
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this ddm data provider instance.
	 *
	 * @return the type of this ddm data provider instance
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this ddm data provider instance.
	 *
	 * @return the user ID of this ddm data provider instance
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this ddm data provider instance.
	 *
	 * @return the user name of this ddm data provider instance
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this ddm data provider instance.
	 *
	 * @return the user uuid of this ddm data provider instance
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this ddm data provider instance.
	 *
	 * @return the uuid of this ddm data provider instance
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
	 * Sets the company ID of this ddm data provider instance.
	 *
	 * @param companyId the company ID of this ddm data provider instance
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ddm data provider instance.
	 *
	 * @param createDate the create date of this ddm data provider instance
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the data provider instance ID of this ddm data provider instance.
	 *
	 * @param dataProviderInstanceId the data provider instance ID of this ddm data provider instance
	 */
	@Override
	public void setDataProviderInstanceId(long dataProviderInstanceId) {
		model.setDataProviderInstanceId(dataProviderInstanceId);
	}

	/**
	 * Sets the definition of this ddm data provider instance.
	 *
	 * @param definition the definition of this ddm data provider instance
	 */
	@Override
	public void setDefinition(String definition) {
		model.setDefinition(definition);
	}

	/**
	 * Sets the description of this ddm data provider instance.
	 *
	 * @param description the description of this ddm data provider instance
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the localized description of this ddm data provider instance in the language.
	 *
	 * @param description the localized description of this ddm data provider instance
	 * @param locale the locale of the language
	 */
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	 * Sets the localized description of this ddm data provider instance in the language, and sets the default locale.
	 *
	 * @param description the localized description of this ddm data provider instance
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
	 * Sets the localized descriptions of this ddm data provider instance from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this ddm data provider instance
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		model.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the localized descriptions of this ddm data provider instance from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this ddm data provider instance
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {

		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	 * Sets the group ID of this ddm data provider instance.
	 *
	 * @param groupId the group ID of this ddm data provider instance
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this ddm data provider instance.
	 *
	 * @param lastPublishDate the last publish date of this ddm data provider instance
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this ddm data provider instance.
	 *
	 * @param modifiedDate the modified date of this ddm data provider instance
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this ddm data provider instance.
	 *
	 * @param mvccVersion the mvcc version of this ddm data provider instance
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this ddm data provider instance.
	 *
	 * @param name the name of this ddm data provider instance
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this ddm data provider instance in the language.
	 *
	 * @param name the localized name of this ddm data provider instance
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this ddm data provider instance in the language, and sets the default locale.
	 *
	 * @param name the localized name of this ddm data provider instance
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
	 * Sets the localized names of this ddm data provider instance from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this ddm data provider instance
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this ddm data provider instance from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this ddm data provider instance
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the primary key of this ddm data provider instance.
	 *
	 * @param primaryKey the primary key of this ddm data provider instance
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this ddm data provider instance.
	 *
	 * @param type the type of this ddm data provider instance
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this ddm data provider instance.
	 *
	 * @param userId the user ID of this ddm data provider instance
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this ddm data provider instance.
	 *
	 * @param userName the user name of this ddm data provider instance
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this ddm data provider instance.
	 *
	 * @param userUuid the user uuid of this ddm data provider instance
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this ddm data provider instance.
	 *
	 * @param uuid the uuid of this ddm data provider instance
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
	protected DDMDataProviderInstanceWrapper wrap(
		DDMDataProviderInstance ddmDataProviderInstance) {

		return new DDMDataProviderInstanceWrapper(ddmDataProviderInstance);
	}

}