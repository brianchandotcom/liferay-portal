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
 * This class is a wrapper for {@link DDLRecordSetVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetVersion
 * @generated
 */
@ProviderType
public class DDLRecordSetVersionWrapper implements DDLRecordSetVersion,
	ModelWrapper<DDLRecordSetVersion> {
	public DDLRecordSetVersionWrapper(DDLRecordSetVersion ddlRecordSetVersion) {
		_ddlRecordSetVersion = ddlRecordSetVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return DDLRecordSetVersion.class;
	}

	@Override
	public String getModelClassName() {
		return DDLRecordSetVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

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
	public Object clone() {
		return new DDLRecordSetVersionWrapper((DDLRecordSetVersion)_ddlRecordSetVersion.clone());
	}

	@Override
	public int compareTo(DDLRecordSetVersion ddlRecordSetVersion) {
		return _ddlRecordSetVersion.compareTo(ddlRecordSetVersion);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _ddlRecordSetVersion.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this ddl record set version.
	*
	* @return the company ID of this ddl record set version
	*/
	@Override
	public long getCompanyId() {
		return _ddlRecordSetVersion.getCompanyId();
	}

	/**
	* Returns the create date of this ddl record set version.
	*
	* @return the create date of this ddl record set version
	*/
	@Override
	public Date getCreateDate() {
		return _ddlRecordSetVersion.getCreateDate();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion getDDMStructureVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordSetVersion.getDDMStructureVersion();
	}

	/**
	* Returns the ddm structure version ID of this ddl record set version.
	*
	* @return the ddm structure version ID of this ddl record set version
	*/
	@Override
	public long getDDMStructureVersionId() {
		return _ddlRecordSetVersion.getDDMStructureVersionId();
	}

	@Override
	public String getDefaultLanguageId() {
		return _ddlRecordSetVersion.getDefaultLanguageId();
	}

	/**
	* Returns the description of this ddl record set version.
	*
	* @return the description of this ddl record set version
	*/
	@Override
	public String getDescription() {
		return _ddlRecordSetVersion.getDescription();
	}

	/**
	* Returns the localized description of this ddl record set version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this ddl record set version
	*/
	@Override
	public String getDescription(java.util.Locale locale) {
		return _ddlRecordSetVersion.getDescription(locale);
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
		return _ddlRecordSetVersion.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this ddl record set version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this ddl record set version
	*/
	@Override
	public String getDescription(String languageId) {
		return _ddlRecordSetVersion.getDescription(languageId);
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
		return _ddlRecordSetVersion.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _ddlRecordSetVersion.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return _ddlRecordSetVersion.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this ddl record set version.
	*
	* @return the locales and localized descriptions of this ddl record set version
	*/
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return _ddlRecordSetVersion.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddlRecordSetVersion.getExpandoBridge();
	}

	/**
	* Returns the group ID of this ddl record set version.
	*
	* @return the group ID of this ddl record set version
	*/
	@Override
	public long getGroupId() {
		return _ddlRecordSetVersion.getGroupId();
	}

	/**
	* Returns the name of this ddl record set version.
	*
	* @return the name of this ddl record set version
	*/
	@Override
	public String getName() {
		return _ddlRecordSetVersion.getName();
	}

	/**
	* Returns the localized name of this ddl record set version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this ddl record set version
	*/
	@Override
	public String getName(java.util.Locale locale) {
		return _ddlRecordSetVersion.getName(locale);
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
		return _ddlRecordSetVersion.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this ddl record set version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this ddl record set version
	*/
	@Override
	public String getName(String languageId) {
		return _ddlRecordSetVersion.getName(languageId);
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
		return _ddlRecordSetVersion.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _ddlRecordSetVersion.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return _ddlRecordSetVersion.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this ddl record set version.
	*
	* @return the locales and localized names of this ddl record set version
	*/
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return _ddlRecordSetVersion.getNameMap();
	}

	/**
	* Returns the primary key of this ddl record set version.
	*
	* @return the primary key of this ddl record set version
	*/
	@Override
	public long getPrimaryKey() {
		return _ddlRecordSetVersion.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddlRecordSetVersion.getPrimaryKeyObj();
	}

	@Override
	public DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordSetVersion.getRecordSet();
	}

	/**
	* Returns the record set ID of this ddl record set version.
	*
	* @return the record set ID of this ddl record set version
	*/
	@Override
	public long getRecordSetId() {
		return _ddlRecordSetVersion.getRecordSetId();
	}

	/**
	* Returns the record set version ID of this ddl record set version.
	*
	* @return the record set version ID of this ddl record set version
	*/
	@Override
	public long getRecordSetVersionId() {
		return _ddlRecordSetVersion.getRecordSetVersionId();
	}

	/**
	* Returns the settings of this ddl record set version.
	*
	* @return the settings of this ddl record set version
	*/
	@Override
	public String getSettings() {
		return _ddlRecordSetVersion.getSettings();
	}

	/**
	* Returns the status of this ddl record set version.
	*
	* @return the status of this ddl record set version
	*/
	@Override
	public int getStatus() {
		return _ddlRecordSetVersion.getStatus();
	}

	/**
	* Returns the status by user ID of this ddl record set version.
	*
	* @return the status by user ID of this ddl record set version
	*/
	@Override
	public long getStatusByUserId() {
		return _ddlRecordSetVersion.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this ddl record set version.
	*
	* @return the status by user name of this ddl record set version
	*/
	@Override
	public String getStatusByUserName() {
		return _ddlRecordSetVersion.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this ddl record set version.
	*
	* @return the status by user uuid of this ddl record set version
	*/
	@Override
	public String getStatusByUserUuid() {
		return _ddlRecordSetVersion.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this ddl record set version.
	*
	* @return the status date of this ddl record set version
	*/
	@Override
	public Date getStatusDate() {
		return _ddlRecordSetVersion.getStatusDate();
	}

	/**
	* Returns the user ID of this ddl record set version.
	*
	* @return the user ID of this ddl record set version
	*/
	@Override
	public long getUserId() {
		return _ddlRecordSetVersion.getUserId();
	}

	/**
	* Returns the user name of this ddl record set version.
	*
	* @return the user name of this ddl record set version
	*/
	@Override
	public String getUserName() {
		return _ddlRecordSetVersion.getUserName();
	}

	/**
	* Returns the user uuid of this ddl record set version.
	*
	* @return the user uuid of this ddl record set version
	*/
	@Override
	public String getUserUuid() {
		return _ddlRecordSetVersion.getUserUuid();
	}

	/**
	* Returns the version of this ddl record set version.
	*
	* @return the version of this ddl record set version
	*/
	@Override
	public String getVersion() {
		return _ddlRecordSetVersion.getVersion();
	}

	@Override
	public int hashCode() {
		return _ddlRecordSetVersion.hashCode();
	}

	/**
	* Returns <code>true</code> if this ddl record set version is approved.
	*
	* @return <code>true</code> if this ddl record set version is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _ddlRecordSetVersion.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _ddlRecordSetVersion.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this ddl record set version is denied.
	*
	* @return <code>true</code> if this ddl record set version is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _ddlRecordSetVersion.isDenied();
	}

	/**
	* Returns <code>true</code> if this ddl record set version is a draft.
	*
	* @return <code>true</code> if this ddl record set version is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _ddlRecordSetVersion.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddlRecordSetVersion.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this ddl record set version is expired.
	*
	* @return <code>true</code> if this ddl record set version is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _ddlRecordSetVersion.isExpired();
	}

	/**
	* Returns <code>true</code> if this ddl record set version is inactive.
	*
	* @return <code>true</code> if this ddl record set version is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _ddlRecordSetVersion.isInactive();
	}

	/**
	* Returns <code>true</code> if this ddl record set version is incomplete.
	*
	* @return <code>true</code> if this ddl record set version is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _ddlRecordSetVersion.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _ddlRecordSetVersion.isNew();
	}

	/**
	* Returns <code>true</code> if this ddl record set version is pending.
	*
	* @return <code>true</code> if this ddl record set version is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _ddlRecordSetVersion.isPending();
	}

	/**
	* Returns <code>true</code> if this ddl record set version is scheduled.
	*
	* @return <code>true</code> if this ddl record set version is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _ddlRecordSetVersion.isScheduled();
	}

	@Override
	public void persist() {
		_ddlRecordSetVersion.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_ddlRecordSetVersion.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_ddlRecordSetVersion.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddlRecordSetVersion.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this ddl record set version.
	*
	* @param companyId the company ID of this ddl record set version
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddlRecordSetVersion.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this ddl record set version.
	*
	* @param createDate the create date of this ddl record set version
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddlRecordSetVersion.setCreateDate(createDate);
	}

	/**
	* Sets the ddm structure version ID of this ddl record set version.
	*
	* @param DDMStructureVersionId the ddm structure version ID of this ddl record set version
	*/
	@Override
	public void setDDMStructureVersionId(long DDMStructureVersionId) {
		_ddlRecordSetVersion.setDDMStructureVersionId(DDMStructureVersionId);
	}

	/**
	* Sets the description of this ddl record set version.
	*
	* @param description the description of this ddl record set version
	*/
	@Override
	public void setDescription(String description) {
		_ddlRecordSetVersion.setDescription(description);
	}

	/**
	* Sets the localized description of this ddl record set version in the language.
	*
	* @param description the localized description of this ddl record set version
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		_ddlRecordSetVersion.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this ddl record set version in the language, and sets the default locale.
	*
	* @param description the localized description of this ddl record set version
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddlRecordSetVersion.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		_ddlRecordSetVersion.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this ddl record set version from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this ddl record set version
	*/
	@Override
	public void setDescriptionMap(Map<java.util.Locale, String> descriptionMap) {
		_ddlRecordSetVersion.setDescriptionMap(descriptionMap);
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
		_ddlRecordSetVersion.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_ddlRecordSetVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ddlRecordSetVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ddlRecordSetVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this ddl record set version.
	*
	* @param groupId the group ID of this ddl record set version
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddlRecordSetVersion.setGroupId(groupId);
	}

	/**
	* Sets the name of this ddl record set version.
	*
	* @param name the name of this ddl record set version
	*/
	@Override
	public void setName(String name) {
		_ddlRecordSetVersion.setName(name);
	}

	/**
	* Sets the localized name of this ddl record set version in the language.
	*
	* @param name the localized name of this ddl record set version
	* @param locale the locale of the language
	*/
	@Override
	public void setName(String name, java.util.Locale locale) {
		_ddlRecordSetVersion.setName(name, locale);
	}

	/**
	* Sets the localized name of this ddl record set version in the language, and sets the default locale.
	*
	* @param name the localized name of this ddl record set version
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddlRecordSetVersion.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_ddlRecordSetVersion.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this ddl record set version from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this ddl record set version
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		_ddlRecordSetVersion.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this ddl record set version from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this ddl record set version
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap,
		java.util.Locale defaultLocale) {
		_ddlRecordSetVersion.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddlRecordSetVersion.setNew(n);
	}

	/**
	* Sets the primary key of this ddl record set version.
	*
	* @param primaryKey the primary key of this ddl record set version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddlRecordSetVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddlRecordSetVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the record set ID of this ddl record set version.
	*
	* @param recordSetId the record set ID of this ddl record set version
	*/
	@Override
	public void setRecordSetId(long recordSetId) {
		_ddlRecordSetVersion.setRecordSetId(recordSetId);
	}

	/**
	* Sets the record set version ID of this ddl record set version.
	*
	* @param recordSetVersionId the record set version ID of this ddl record set version
	*/
	@Override
	public void setRecordSetVersionId(long recordSetVersionId) {
		_ddlRecordSetVersion.setRecordSetVersionId(recordSetVersionId);
	}

	/**
	* Sets the settings of this ddl record set version.
	*
	* @param settings the settings of this ddl record set version
	*/
	@Override
	public void setSettings(String settings) {
		_ddlRecordSetVersion.setSettings(settings);
	}

	/**
	* Sets the status of this ddl record set version.
	*
	* @param status the status of this ddl record set version
	*/
	@Override
	public void setStatus(int status) {
		_ddlRecordSetVersion.setStatus(status);
	}

	/**
	* Sets the status by user ID of this ddl record set version.
	*
	* @param statusByUserId the status by user ID of this ddl record set version
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_ddlRecordSetVersion.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this ddl record set version.
	*
	* @param statusByUserName the status by user name of this ddl record set version
	*/
	@Override
	public void setStatusByUserName(String statusByUserName) {
		_ddlRecordSetVersion.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this ddl record set version.
	*
	* @param statusByUserUuid the status by user uuid of this ddl record set version
	*/
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		_ddlRecordSetVersion.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this ddl record set version.
	*
	* @param statusDate the status date of this ddl record set version
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_ddlRecordSetVersion.setStatusDate(statusDate);
	}

	/**
	* Sets the user ID of this ddl record set version.
	*
	* @param userId the user ID of this ddl record set version
	*/
	@Override
	public void setUserId(long userId) {
		_ddlRecordSetVersion.setUserId(userId);
	}

	/**
	* Sets the user name of this ddl record set version.
	*
	* @param userName the user name of this ddl record set version
	*/
	@Override
	public void setUserName(String userName) {
		_ddlRecordSetVersion.setUserName(userName);
	}

	/**
	* Sets the user uuid of this ddl record set version.
	*
	* @param userUuid the user uuid of this ddl record set version
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_ddlRecordSetVersion.setUserUuid(userUuid);
	}

	/**
	* Sets the version of this ddl record set version.
	*
	* @param version the version of this ddl record set version
	*/
	@Override
	public void setVersion(String version) {
		_ddlRecordSetVersion.setVersion(version);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DDLRecordSetVersion> toCacheModel() {
		return _ddlRecordSetVersion.toCacheModel();
	}

	@Override
	public DDLRecordSetVersion toEscapedModel() {
		return new DDLRecordSetVersionWrapper(_ddlRecordSetVersion.toEscapedModel());
	}

	@Override
	public String toString() {
		return _ddlRecordSetVersion.toString();
	}

	@Override
	public DDLRecordSetVersion toUnescapedModel() {
		return new DDLRecordSetVersionWrapper(_ddlRecordSetVersion.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _ddlRecordSetVersion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDLRecordSetVersionWrapper)) {
			return false;
		}

		DDLRecordSetVersionWrapper ddlRecordSetVersionWrapper = (DDLRecordSetVersionWrapper)obj;

		if (Objects.equals(_ddlRecordSetVersion,
					ddlRecordSetVersionWrapper._ddlRecordSetVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public DDLRecordSetVersion getWrappedModel() {
		return _ddlRecordSetVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddlRecordSetVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddlRecordSetVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddlRecordSetVersion.resetOriginalValues();
	}

	private final DDLRecordSetVersion _ddlRecordSetVersion;
}