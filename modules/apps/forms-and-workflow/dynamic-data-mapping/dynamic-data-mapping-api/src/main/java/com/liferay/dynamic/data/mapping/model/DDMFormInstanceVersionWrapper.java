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

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link DDMFormInstanceVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceVersion
 * @generated
 */
@ProviderType
public class DDMFormInstanceVersionWrapper implements DDMFormInstanceVersion,
	ModelWrapper<DDMFormInstanceVersion> {
	public DDMFormInstanceVersionWrapper(
		DDMFormInstanceVersion ddmFormInstanceVersion) {
		_ddmFormInstanceVersion = ddmFormInstanceVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMFormInstanceVersion.class;
	}

	@Override
	public String getModelClassName() {
		return DDMFormInstanceVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("formInstanceVersionId", getFormInstanceVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("formInstanceId", getFormInstanceId());
		attributes.put("structureVersionId", getStructureVersionId());
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
		Long formInstanceVersionId = (Long)attributes.get(
				"formInstanceVersionId");

		if (formInstanceVersionId != null) {
			setFormInstanceVersionId(formInstanceVersionId);
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

		Long formInstanceId = (Long)attributes.get("formInstanceId");

		if (formInstanceId != null) {
			setFormInstanceId(formInstanceId);
		}

		Long structureVersionId = (Long)attributes.get("structureVersionId");

		if (structureVersionId != null) {
			setStructureVersionId(structureVersionId);
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
	public java.lang.Object clone() {
		return new DDMFormInstanceVersionWrapper((DDMFormInstanceVersion)_ddmFormInstanceVersion.clone());
	}

	@Override
	public int compareTo(DDMFormInstanceVersion ddmFormInstanceVersion) {
		return _ddmFormInstanceVersion.compareTo(ddmFormInstanceVersion);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _ddmFormInstanceVersion.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this ddm form instance version.
	*
	* @return the company ID of this ddm form instance version
	*/
	@Override
	public long getCompanyId() {
		return _ddmFormInstanceVersion.getCompanyId();
	}

	/**
	* Returns the create date of this ddm form instance version.
	*
	* @return the create date of this ddm form instance version
	*/
	@Override
	public Date getCreateDate() {
		return _ddmFormInstanceVersion.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _ddmFormInstanceVersion.getDefaultLanguageId();
	}

	/**
	* Returns the description of this ddm form instance version.
	*
	* @return the description of this ddm form instance version
	*/
	@Override
	public java.lang.String getDescription() {
		return _ddmFormInstanceVersion.getDescription();
	}

	/**
	* Returns the localized description of this ddm form instance version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this ddm form instance version
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _ddmFormInstanceVersion.getDescription(locale);
	}

	/**
	* Returns the localized description of this ddm form instance version in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this ddm form instance version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _ddmFormInstanceVersion.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this ddm form instance version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this ddm form instance version
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _ddmFormInstanceVersion.getDescription(languageId);
	}

	/**
	* Returns the localized description of this ddm form instance version in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this ddm form instance version
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _ddmFormInstanceVersion.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _ddmFormInstanceVersion.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _ddmFormInstanceVersion.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this ddm form instance version.
	*
	* @return the locales and localized descriptions of this ddm form instance version
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _ddmFormInstanceVersion.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddmFormInstanceVersion.getExpandoBridge();
	}

	@Override
	public DDMFormInstance getFormInstance()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceVersion.getFormInstance();
	}

	/**
	* Returns the form instance ID of this ddm form instance version.
	*
	* @return the form instance ID of this ddm form instance version
	*/
	@Override
	public long getFormInstanceId() {
		return _ddmFormInstanceVersion.getFormInstanceId();
	}

	/**
	* Returns the form instance version ID of this ddm form instance version.
	*
	* @return the form instance version ID of this ddm form instance version
	*/
	@Override
	public long getFormInstanceVersionId() {
		return _ddmFormInstanceVersion.getFormInstanceVersionId();
	}

	/**
	* Returns the group ID of this ddm form instance version.
	*
	* @return the group ID of this ddm form instance version
	*/
	@Override
	public long getGroupId() {
		return _ddmFormInstanceVersion.getGroupId();
	}

	/**
	* Returns the name of this ddm form instance version.
	*
	* @return the name of this ddm form instance version
	*/
	@Override
	public java.lang.String getName() {
		return _ddmFormInstanceVersion.getName();
	}

	/**
	* Returns the localized name of this ddm form instance version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this ddm form instance version
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _ddmFormInstanceVersion.getName(locale);
	}

	/**
	* Returns the localized name of this ddm form instance version in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this ddm form instance version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmFormInstanceVersion.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this ddm form instance version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this ddm form instance version
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _ddmFormInstanceVersion.getName(languageId);
	}

	/**
	* Returns the localized name of this ddm form instance version in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this ddm form instance version
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddmFormInstanceVersion.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _ddmFormInstanceVersion.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _ddmFormInstanceVersion.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this ddm form instance version.
	*
	* @return the locales and localized names of this ddm form instance version
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmFormInstanceVersion.getNameMap();
	}

	/**
	* Returns the primary key of this ddm form instance version.
	*
	* @return the primary key of this ddm form instance version
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmFormInstanceVersion.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddmFormInstanceVersion.getPrimaryKeyObj();
	}

	/**
	* Returns the settings of this ddm form instance version.
	*
	* @return the settings of this ddm form instance version
	*/
	@Override
	public java.lang.String getSettings() {
		return _ddmFormInstanceVersion.getSettings();
	}

	/**
	* Returns the status of this ddm form instance version.
	*
	* @return the status of this ddm form instance version
	*/
	@Override
	public int getStatus() {
		return _ddmFormInstanceVersion.getStatus();
	}

	/**
	* Returns the status by user ID of this ddm form instance version.
	*
	* @return the status by user ID of this ddm form instance version
	*/
	@Override
	public long getStatusByUserId() {
		return _ddmFormInstanceVersion.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this ddm form instance version.
	*
	* @return the status by user name of this ddm form instance version
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _ddmFormInstanceVersion.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this ddm form instance version.
	*
	* @return the status by user uuid of this ddm form instance version
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _ddmFormInstanceVersion.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this ddm form instance version.
	*
	* @return the status date of this ddm form instance version
	*/
	@Override
	public Date getStatusDate() {
		return _ddmFormInstanceVersion.getStatusDate();
	}

	@Override
	public DDMStructureVersion getStructureVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceVersion.getStructureVersion();
	}

	/**
	* Returns the structure version ID of this ddm form instance version.
	*
	* @return the structure version ID of this ddm form instance version
	*/
	@Override
	public long getStructureVersionId() {
		return _ddmFormInstanceVersion.getStructureVersionId();
	}

	/**
	* Returns the user ID of this ddm form instance version.
	*
	* @return the user ID of this ddm form instance version
	*/
	@Override
	public long getUserId() {
		return _ddmFormInstanceVersion.getUserId();
	}

	/**
	* Returns the user name of this ddm form instance version.
	*
	* @return the user name of this ddm form instance version
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddmFormInstanceVersion.getUserName();
	}

	/**
	* Returns the user uuid of this ddm form instance version.
	*
	* @return the user uuid of this ddm form instance version
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddmFormInstanceVersion.getUserUuid();
	}

	/**
	* Returns the version of this ddm form instance version.
	*
	* @return the version of this ddm form instance version
	*/
	@Override
	public java.lang.String getVersion() {
		return _ddmFormInstanceVersion.getVersion();
	}

	@Override
	public int hashCode() {
		return _ddmFormInstanceVersion.hashCode();
	}

	/**
	* Returns <code>true</code> if this ddm form instance version is approved.
	*
	* @return <code>true</code> if this ddm form instance version is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _ddmFormInstanceVersion.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmFormInstanceVersion.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this ddm form instance version is denied.
	*
	* @return <code>true</code> if this ddm form instance version is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _ddmFormInstanceVersion.isDenied();
	}

	/**
	* Returns <code>true</code> if this ddm form instance version is a draft.
	*
	* @return <code>true</code> if this ddm form instance version is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _ddmFormInstanceVersion.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmFormInstanceVersion.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this ddm form instance version is expired.
	*
	* @return <code>true</code> if this ddm form instance version is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _ddmFormInstanceVersion.isExpired();
	}

	/**
	* Returns <code>true</code> if this ddm form instance version is inactive.
	*
	* @return <code>true</code> if this ddm form instance version is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _ddmFormInstanceVersion.isInactive();
	}

	/**
	* Returns <code>true</code> if this ddm form instance version is incomplete.
	*
	* @return <code>true</code> if this ddm form instance version is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _ddmFormInstanceVersion.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _ddmFormInstanceVersion.isNew();
	}

	/**
	* Returns <code>true</code> if this ddm form instance version is pending.
	*
	* @return <code>true</code> if this ddm form instance version is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _ddmFormInstanceVersion.isPending();
	}

	/**
	* Returns <code>true</code> if this ddm form instance version is scheduled.
	*
	* @return <code>true</code> if this ddm form instance version is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _ddmFormInstanceVersion.isScheduled();
	}

	@Override
	public void persist() {
		_ddmFormInstanceVersion.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_ddmFormInstanceVersion.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_ddmFormInstanceVersion.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmFormInstanceVersion.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this ddm form instance version.
	*
	* @param companyId the company ID of this ddm form instance version
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmFormInstanceVersion.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this ddm form instance version.
	*
	* @param createDate the create date of this ddm form instance version
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddmFormInstanceVersion.setCreateDate(createDate);
	}

	/**
	* Sets the description of this ddm form instance version.
	*
	* @param description the description of this ddm form instance version
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_ddmFormInstanceVersion.setDescription(description);
	}

	/**
	* Sets the localized description of this ddm form instance version in the language.
	*
	* @param description the localized description of this ddm form instance version
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_ddmFormInstanceVersion.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this ddm form instance version in the language, and sets the default locale.
	*
	* @param description the localized description of this ddm form instance version
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_ddmFormInstanceVersion.setDescription(description, locale,
			defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_ddmFormInstanceVersion.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this ddm form instance version from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this ddm form instance version
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_ddmFormInstanceVersion.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this ddm form instance version from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this ddm form instance version
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddmFormInstanceVersion.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_ddmFormInstanceVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ddmFormInstanceVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ddmFormInstanceVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the form instance ID of this ddm form instance version.
	*
	* @param formInstanceId the form instance ID of this ddm form instance version
	*/
	@Override
	public void setFormInstanceId(long formInstanceId) {
		_ddmFormInstanceVersion.setFormInstanceId(formInstanceId);
	}

	/**
	* Sets the form instance version ID of this ddm form instance version.
	*
	* @param formInstanceVersionId the form instance version ID of this ddm form instance version
	*/
	@Override
	public void setFormInstanceVersionId(long formInstanceVersionId) {
		_ddmFormInstanceVersion.setFormInstanceVersionId(formInstanceVersionId);
	}

	/**
	* Sets the group ID of this ddm form instance version.
	*
	* @param groupId the group ID of this ddm form instance version
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmFormInstanceVersion.setGroupId(groupId);
	}

	/**
	* Sets the name of this ddm form instance version.
	*
	* @param name the name of this ddm form instance version
	*/
	@Override
	public void setName(java.lang.String name) {
		_ddmFormInstanceVersion.setName(name);
	}

	/**
	* Sets the localized name of this ddm form instance version in the language.
	*
	* @param name the localized name of this ddm form instance version
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddmFormInstanceVersion.setName(name, locale);
	}

	/**
	* Sets the localized name of this ddm form instance version in the language, and sets the default locale.
	*
	* @param name the localized name of this ddm form instance version
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmFormInstanceVersion.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_ddmFormInstanceVersion.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this ddm form instance version from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this ddm form instance version
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmFormInstanceVersion.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this ddm form instance version from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this ddm form instance version
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmFormInstanceVersion.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddmFormInstanceVersion.setNew(n);
	}

	/**
	* Sets the primary key of this ddm form instance version.
	*
	* @param primaryKey the primary key of this ddm form instance version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmFormInstanceVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmFormInstanceVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the settings of this ddm form instance version.
	*
	* @param settings the settings of this ddm form instance version
	*/
	@Override
	public void setSettings(java.lang.String settings) {
		_ddmFormInstanceVersion.setSettings(settings);
	}

	/**
	* Sets the status of this ddm form instance version.
	*
	* @param status the status of this ddm form instance version
	*/
	@Override
	public void setStatus(int status) {
		_ddmFormInstanceVersion.setStatus(status);
	}

	/**
	* Sets the status by user ID of this ddm form instance version.
	*
	* @param statusByUserId the status by user ID of this ddm form instance version
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_ddmFormInstanceVersion.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this ddm form instance version.
	*
	* @param statusByUserName the status by user name of this ddm form instance version
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_ddmFormInstanceVersion.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this ddm form instance version.
	*
	* @param statusByUserUuid the status by user uuid of this ddm form instance version
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_ddmFormInstanceVersion.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this ddm form instance version.
	*
	* @param statusDate the status date of this ddm form instance version
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_ddmFormInstanceVersion.setStatusDate(statusDate);
	}

	/**
	* Sets the structure version ID of this ddm form instance version.
	*
	* @param structureVersionId the structure version ID of this ddm form instance version
	*/
	@Override
	public void setStructureVersionId(long structureVersionId) {
		_ddmFormInstanceVersion.setStructureVersionId(structureVersionId);
	}

	/**
	* Sets the user ID of this ddm form instance version.
	*
	* @param userId the user ID of this ddm form instance version
	*/
	@Override
	public void setUserId(long userId) {
		_ddmFormInstanceVersion.setUserId(userId);
	}

	/**
	* Sets the user name of this ddm form instance version.
	*
	* @param userName the user name of this ddm form instance version
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddmFormInstanceVersion.setUserName(userName);
	}

	/**
	* Sets the user uuid of this ddm form instance version.
	*
	* @param userUuid the user uuid of this ddm form instance version
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddmFormInstanceVersion.setUserUuid(userUuid);
	}

	/**
	* Sets the version of this ddm form instance version.
	*
	* @param version the version of this ddm form instance version
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_ddmFormInstanceVersion.setVersion(version);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DDMFormInstanceVersion> toCacheModel() {
		return _ddmFormInstanceVersion.toCacheModel();
	}

	@Override
	public DDMFormInstanceVersion toEscapedModel() {
		return new DDMFormInstanceVersionWrapper(_ddmFormInstanceVersion.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmFormInstanceVersion.toString();
	}

	@Override
	public DDMFormInstanceVersion toUnescapedModel() {
		return new DDMFormInstanceVersionWrapper(_ddmFormInstanceVersion.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmFormInstanceVersion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormInstanceVersionWrapper)) {
			return false;
		}

		DDMFormInstanceVersionWrapper ddmFormInstanceVersionWrapper = (DDMFormInstanceVersionWrapper)obj;

		if (Objects.equals(_ddmFormInstanceVersion,
					ddmFormInstanceVersionWrapper._ddmFormInstanceVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public DDMFormInstanceVersion getWrappedModel() {
		return _ddmFormInstanceVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmFormInstanceVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmFormInstanceVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmFormInstanceVersion.resetOriginalValues();
	}

	private final DDMFormInstanceVersion _ddmFormInstanceVersion;
}