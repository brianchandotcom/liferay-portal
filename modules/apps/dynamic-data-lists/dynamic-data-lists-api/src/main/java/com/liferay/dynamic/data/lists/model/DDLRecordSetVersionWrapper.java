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

package com.liferay.dynamic.data.lists.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DDLRecordSetVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetVersion
 * @generated
 */
public class DDLRecordSetVersionWrapper
	extends BaseModelWrapper<DDLRecordSetVersion>
	implements DDLRecordSetVersion, ModelWrapper<DDLRecordSetVersion> {

	public DDLRecordSetVersionWrapper(DDLRecordSetVersion ddlRecordSetVersion) {
		super(ddlRecordSetVersion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("recordSetVersionId", getRecordSetVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("recordSetId", getRecordSetId());
		attributes.put("DDMStructureVersionId", getDDMStructureVersionId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("settings", getSettings());
		attributes.put("version", getVersion());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long recordSetVersionId = (Long)attributes.get("recordSetVersionId");

		if (recordSetVersionId != null) {
			setRecordSetVersionId(recordSetVersionId);
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

		Long recordSetId = (Long)attributes.get("recordSetId");

		if (recordSetId != null) {
			setRecordSetId(recordSetId);
		}

		Long DDMStructureVersionId = (Long)attributes.get(
			"DDMStructureVersionId");

		if (DDMStructureVersionId != null) {
			setDDMStructureVersionId(DDMStructureVersionId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this ddl record set version.
	 *
	 * @return the company ID of this ddl record set version
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ddl record set version.
	 *
	 * @return the create date of this ddl record set version
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this ddl record set version.
	 *
	 * @return the ct collection ID of this ddl record set version
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion
			getDDMStructureVersion()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDDMStructureVersion();
	}

	/**
	 * Returns the ddm structure version ID of this ddl record set version.
	 *
	 * @return the ddm structure version ID of this ddl record set version
	 */
	@Override
	public long getDDMStructureVersionId() {
		return model.getDDMStructureVersionId();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the description of this ddl record set version.
	 *
	 * @return the description of this ddl record set version
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the localized description of this ddl record set version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this ddl record set version
	 */
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	 * Returns the localized description of this ddl record set version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this ddl record set version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	 * Returns the localized description of this ddl record set version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this ddl record set version
	 */
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	 * Returns the localized description of this ddl record set version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this ddl record set version
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
	 * Returns a map of the locales and localized descriptions of this ddl record set version.
	 *
	 * @return the locales and localized descriptions of this ddl record set version
	 */
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	 * Returns the group ID of this ddl record set version.
	 *
	 * @return the group ID of this ddl record set version
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this ddl record set version.
	 *
	 * @return the mvcc version of this ddl record set version
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this ddl record set version.
	 *
	 * @return the name of this ddl record set version
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this ddl record set version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this ddl record set version
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this ddl record set version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this ddl record set version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this ddl record set version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this ddl record set version
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this ddl record set version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this ddl record set version
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
	 * Returns a map of the locales and localized names of this ddl record set version.
	 *
	 * @return the locales and localized names of this ddl record set version
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the primary key of this ddl record set version.
	 *
	 * @return the primary key of this ddl record set version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getRecordSet();
	}

	/**
	 * Returns the record set ID of this ddl record set version.
	 *
	 * @return the record set ID of this ddl record set version
	 */
	@Override
	public long getRecordSetId() {
		return model.getRecordSetId();
	}

	/**
	 * Returns the record set version ID of this ddl record set version.
	 *
	 * @return the record set version ID of this ddl record set version
	 */
	@Override
	public long getRecordSetVersionId() {
		return model.getRecordSetVersionId();
	}

	/**
	 * Returns the settings of this ddl record set version.
	 *
	 * @return the settings of this ddl record set version
	 */
	@Override
	public String getSettings() {
		return model.getSettings();
	}

	/**
	 * Returns the status of this ddl record set version.
	 *
	 * @return the status of this ddl record set version
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this ddl record set version.
	 *
	 * @return the status by user ID of this ddl record set version
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this ddl record set version.
	 *
	 * @return the status by user name of this ddl record set version
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this ddl record set version.
	 *
	 * @return the status by user uuid of this ddl record set version
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this ddl record set version.
	 *
	 * @return the status date of this ddl record set version
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the user ID of this ddl record set version.
	 *
	 * @return the user ID of this ddl record set version
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this ddl record set version.
	 *
	 * @return the user name of this ddl record set version
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this ddl record set version.
	 *
	 * @return the user uuid of this ddl record set version
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the version of this ddl record set version.
	 *
	 * @return the version of this ddl record set version
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns <code>true</code> if this ddl record set version is approved.
	 *
	 * @return <code>true</code> if this ddl record set version is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this ddl record set version is denied.
	 *
	 * @return <code>true</code> if this ddl record set version is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this ddl record set version is a draft.
	 *
	 * @return <code>true</code> if this ddl record set version is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this ddl record set version is expired.
	 *
	 * @return <code>true</code> if this ddl record set version is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this ddl record set version is inactive.
	 *
	 * @return <code>true</code> if this ddl record set version is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this ddl record set version is incomplete.
	 *
	 * @return <code>true</code> if this ddl record set version is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this ddl record set version is pending.
	 *
	 * @return <code>true</code> if this ddl record set version is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this ddl record set version is scheduled.
	 *
	 * @return <code>true</code> if this ddl record set version is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
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
	 * Sets the company ID of this ddl record set version.
	 *
	 * @param companyId the company ID of this ddl record set version
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ddl record set version.
	 *
	 * @param createDate the create date of this ddl record set version
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this ddl record set version.
	 *
	 * @param ctCollectionId the ct collection ID of this ddl record set version
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ddm structure version ID of this ddl record set version.
	 *
	 * @param DDMStructureVersionId the ddm structure version ID of this ddl record set version
	 */
	@Override
	public void setDDMStructureVersionId(long DDMStructureVersionId) {
		model.setDDMStructureVersionId(DDMStructureVersionId);
	}

	/**
	 * Sets the description of this ddl record set version.
	 *
	 * @param description the description of this ddl record set version
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the localized description of this ddl record set version in the language.
	 *
	 * @param description the localized description of this ddl record set version
	 * @param locale the locale of the language
	 */
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	 * Sets the localized description of this ddl record set version in the language, and sets the default locale.
	 *
	 * @param description the localized description of this ddl record set version
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
	 * Sets the localized descriptions of this ddl record set version from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this ddl record set version
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		model.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the localized descriptions of this ddl record set version from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this ddl record set version
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {

		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	 * Sets the group ID of this ddl record set version.
	 *
	 * @param groupId the group ID of this ddl record set version
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this ddl record set version.
	 *
	 * @param mvccVersion the mvcc version of this ddl record set version
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this ddl record set version.
	 *
	 * @param name the name of this ddl record set version
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this ddl record set version in the language.
	 *
	 * @param name the localized name of this ddl record set version
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this ddl record set version in the language, and sets the default locale.
	 *
	 * @param name the localized name of this ddl record set version
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
	 * Sets the localized names of this ddl record set version from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this ddl record set version
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this ddl record set version from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this ddl record set version
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the primary key of this ddl record set version.
	 *
	 * @param primaryKey the primary key of this ddl record set version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the record set ID of this ddl record set version.
	 *
	 * @param recordSetId the record set ID of this ddl record set version
	 */
	@Override
	public void setRecordSetId(long recordSetId) {
		model.setRecordSetId(recordSetId);
	}

	/**
	 * Sets the record set version ID of this ddl record set version.
	 *
	 * @param recordSetVersionId the record set version ID of this ddl record set version
	 */
	@Override
	public void setRecordSetVersionId(long recordSetVersionId) {
		model.setRecordSetVersionId(recordSetVersionId);
	}

	/**
	 * Sets the settings of this ddl record set version.
	 *
	 * @param settings the settings of this ddl record set version
	 */
	@Override
	public void setSettings(String settings) {
		model.setSettings(settings);
	}

	/**
	 * Sets the status of this ddl record set version.
	 *
	 * @param status the status of this ddl record set version
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this ddl record set version.
	 *
	 * @param statusByUserId the status by user ID of this ddl record set version
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this ddl record set version.
	 *
	 * @param statusByUserName the status by user name of this ddl record set version
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this ddl record set version.
	 *
	 * @param statusByUserUuid the status by user uuid of this ddl record set version
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this ddl record set version.
	 *
	 * @param statusDate the status date of this ddl record set version
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the user ID of this ddl record set version.
	 *
	 * @param userId the user ID of this ddl record set version
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this ddl record set version.
	 *
	 * @param userName the user name of this ddl record set version
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this ddl record set version.
	 *
	 * @param userUuid the user uuid of this ddl record set version
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the version of this ddl record set version.
	 *
	 * @param version the version of this ddl record set version
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	@Override
	public Map<String, Function<DDLRecordSetVersion, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DDLRecordSetVersion, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected DDLRecordSetVersionWrapper wrap(
		DDLRecordSetVersion ddlRecordSetVersion) {

		return new DDLRecordSetVersionWrapper(ddlRecordSetVersion);
	}

}