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

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link DDMFormInstance}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstance
 * @generated
 */
@ProviderType
public class DDMFormInstanceWrapper implements DDMFormInstance,
	ModelWrapper<DDMFormInstance> {
	public DDMFormInstanceWrapper(DDMFormInstance ddmFormInstance) {
		_ddmFormInstance = ddmFormInstance;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMFormInstance.class;
	}

	@Override
	public String getModelClassName() {
		return DDMFormInstance.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("formInstanceId", getFormInstanceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("versionUserId", getVersionUserId());
		attributes.put("versionUserName", getVersionUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("structureId", getStructureId());
		attributes.put("version", getVersion());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("settings", getSettings());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long formInstanceId = (Long)attributes.get("formInstanceId");

		if (formInstanceId != null) {
			setFormInstanceId(formInstanceId);
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

		Long versionUserId = (Long)attributes.get("versionUserId");

		if (versionUserId != null) {
			setVersionUserId(versionUserId);
		}

		String versionUserName = (String)attributes.get("versionUserName");

		if (versionUserName != null) {
			setVersionUserName(versionUserName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long structureId = (Long)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
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

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new DDMFormInstanceWrapper((DDMFormInstance)_ddmFormInstance.clone());
	}

	@Override
	public int compareTo(DDMFormInstance ddmFormInstance) {
		return _ddmFormInstance.compareTo(ddmFormInstance);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _ddmFormInstance.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this ddm form instance.
	*
	* @return the company ID of this ddm form instance
	*/
	@Override
	public long getCompanyId() {
		return _ddmFormInstance.getCompanyId();
	}

	/**
	* Returns the create date of this ddm form instance.
	*
	* @return the create date of this ddm form instance
	*/
	@Override
	public Date getCreateDate() {
		return _ddmFormInstance.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _ddmFormInstance.getDefaultLanguageId();
	}

	/**
	* Returns the description of this ddm form instance.
	*
	* @return the description of this ddm form instance
	*/
	@Override
	public java.lang.String getDescription() {
		return _ddmFormInstance.getDescription();
	}

	/**
	* Returns the localized description of this ddm form instance in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this ddm form instance
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _ddmFormInstance.getDescription(locale);
	}

	/**
	* Returns the localized description of this ddm form instance in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this ddm form instance. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _ddmFormInstance.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this ddm form instance in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this ddm form instance
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _ddmFormInstance.getDescription(languageId);
	}

	/**
	* Returns the localized description of this ddm form instance in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this ddm form instance
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _ddmFormInstance.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _ddmFormInstance.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _ddmFormInstance.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this ddm form instance.
	*
	* @return the locales and localized descriptions of this ddm form instance
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _ddmFormInstance.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddmFormInstance.getExpandoBridge();
	}

	/**
	* Returns the form instance ID of this ddm form instance.
	*
	* @return the form instance ID of this ddm form instance
	*/
	@Override
	public long getFormInstanceId() {
		return _ddmFormInstance.getFormInstanceId();
	}

	@Override
	public java.util.List<DDMFormInstanceRecord> getFormInstanceRecords() {
		return _ddmFormInstance.getFormInstanceRecords();
	}

	@Override
	public DDMFormInstanceVersion getFormInstanceVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstance.getFormInstanceVersion(version);
	}

	/**
	* Returns the group ID of this ddm form instance.
	*
	* @return the group ID of this ddm form instance
	*/
	@Override
	public long getGroupId() {
		return _ddmFormInstance.getGroupId();
	}

	/**
	* Returns the last publish date of this ddm form instance.
	*
	* @return the last publish date of this ddm form instance
	*/
	@Override
	public Date getLastPublishDate() {
		return _ddmFormInstance.getLastPublishDate();
	}

	/**
	* Returns the modified date of this ddm form instance.
	*
	* @return the modified date of this ddm form instance
	*/
	@Override
	public Date getModifiedDate() {
		return _ddmFormInstance.getModifiedDate();
	}

	/**
	* Returns the name of this ddm form instance.
	*
	* @return the name of this ddm form instance
	*/
	@Override
	public java.lang.String getName() {
		return _ddmFormInstance.getName();
	}

	/**
	* Returns the localized name of this ddm form instance in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this ddm form instance
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _ddmFormInstance.getName(locale);
	}

	/**
	* Returns the localized name of this ddm form instance in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this ddm form instance. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmFormInstance.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this ddm form instance in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this ddm form instance
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _ddmFormInstance.getName(languageId);
	}

	/**
	* Returns the localized name of this ddm form instance in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this ddm form instance
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddmFormInstance.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _ddmFormInstance.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _ddmFormInstance.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this ddm form instance.
	*
	* @return the locales and localized names of this ddm form instance
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmFormInstance.getNameMap();
	}

	/**
	* Returns the primary key of this ddm form instance.
	*
	* @return the primary key of this ddm form instance
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmFormInstance.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddmFormInstance.getPrimaryKeyObj();
	}

	/**
	* Returns the settings of this ddm form instance.
	*
	* @return the settings of this ddm form instance
	*/
	@Override
	public java.lang.String getSettings() {
		return _ddmFormInstance.getSettings();
	}

	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues getSettingsDDMFormValues()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstance.getSettingsDDMFormValues();
	}

	@Override
	public DDMFormInstanceSettings getSettingsModel()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstance.getSettingsModel();
	}

	@Override
	public DDMStructure getStructure()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstance.getStructure();
	}

	/**
	* Returns the structure ID of this ddm form instance.
	*
	* @return the structure ID of this ddm form instance
	*/
	@Override
	public long getStructureId() {
		return _ddmFormInstance.getStructureId();
	}

	/**
	* Returns the user ID of this ddm form instance.
	*
	* @return the user ID of this ddm form instance
	*/
	@Override
	public long getUserId() {
		return _ddmFormInstance.getUserId();
	}

	/**
	* Returns the user name of this ddm form instance.
	*
	* @return the user name of this ddm form instance
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddmFormInstance.getUserName();
	}

	/**
	* Returns the user uuid of this ddm form instance.
	*
	* @return the user uuid of this ddm form instance
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddmFormInstance.getUserUuid();
	}

	/**
	* Returns the uuid of this ddm form instance.
	*
	* @return the uuid of this ddm form instance
	*/
	@Override
	public java.lang.String getUuid() {
		return _ddmFormInstance.getUuid();
	}

	/**
	* Returns the version of this ddm form instance.
	*
	* @return the version of this ddm form instance
	*/
	@Override
	public java.lang.String getVersion() {
		return _ddmFormInstance.getVersion();
	}

	/**
	* Returns the version user ID of this ddm form instance.
	*
	* @return the version user ID of this ddm form instance
	*/
	@Override
	public long getVersionUserId() {
		return _ddmFormInstance.getVersionUserId();
	}

	/**
	* Returns the version user name of this ddm form instance.
	*
	* @return the version user name of this ddm form instance
	*/
	@Override
	public java.lang.String getVersionUserName() {
		return _ddmFormInstance.getVersionUserName();
	}

	/**
	* Returns the version user uuid of this ddm form instance.
	*
	* @return the version user uuid of this ddm form instance
	*/
	@Override
	public java.lang.String getVersionUserUuid() {
		return _ddmFormInstance.getVersionUserUuid();
	}

	@Override
	public int hashCode() {
		return _ddmFormInstance.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmFormInstance.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmFormInstance.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddmFormInstance.isNew();
	}

	@Override
	public void persist() {
		_ddmFormInstance.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_ddmFormInstance.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_ddmFormInstance.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmFormInstance.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this ddm form instance.
	*
	* @param companyId the company ID of this ddm form instance
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmFormInstance.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this ddm form instance.
	*
	* @param createDate the create date of this ddm form instance
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddmFormInstance.setCreateDate(createDate);
	}

	/**
	* Sets the description of this ddm form instance.
	*
	* @param description the description of this ddm form instance
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_ddmFormInstance.setDescription(description);
	}

	/**
	* Sets the localized description of this ddm form instance in the language.
	*
	* @param description the localized description of this ddm form instance
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_ddmFormInstance.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this ddm form instance in the language, and sets the default locale.
	*
	* @param description the localized description of this ddm form instance
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_ddmFormInstance.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_ddmFormInstance.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this ddm form instance from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this ddm form instance
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_ddmFormInstance.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this ddm form instance from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this ddm form instance
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddmFormInstance.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_ddmFormInstance.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ddmFormInstance.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ddmFormInstance.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the form instance ID of this ddm form instance.
	*
	* @param formInstanceId the form instance ID of this ddm form instance
	*/
	@Override
	public void setFormInstanceId(long formInstanceId) {
		_ddmFormInstance.setFormInstanceId(formInstanceId);
	}

	/**
	* Sets the group ID of this ddm form instance.
	*
	* @param groupId the group ID of this ddm form instance
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmFormInstance.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this ddm form instance.
	*
	* @param lastPublishDate the last publish date of this ddm form instance
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_ddmFormInstance.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this ddm form instance.
	*
	* @param modifiedDate the modified date of this ddm form instance
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmFormInstance.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this ddm form instance.
	*
	* @param name the name of this ddm form instance
	*/
	@Override
	public void setName(java.lang.String name) {
		_ddmFormInstance.setName(name);
	}

	/**
	* Sets the localized name of this ddm form instance in the language.
	*
	* @param name the localized name of this ddm form instance
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddmFormInstance.setName(name, locale);
	}

	/**
	* Sets the localized name of this ddm form instance in the language, and sets the default locale.
	*
	* @param name the localized name of this ddm form instance
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmFormInstance.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_ddmFormInstance.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this ddm form instance from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this ddm form instance
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmFormInstance.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this ddm form instance from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this ddm form instance
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmFormInstance.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddmFormInstance.setNew(n);
	}

	/**
	* Sets the primary key of this ddm form instance.
	*
	* @param primaryKey the primary key of this ddm form instance
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmFormInstance.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmFormInstance.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the settings of this ddm form instance.
	*
	* @param settings the settings of this ddm form instance
	*/
	@Override
	public void setSettings(java.lang.String settings) {
		_ddmFormInstance.setSettings(settings);
	}

	/**
	* Sets the structure ID of this ddm form instance.
	*
	* @param structureId the structure ID of this ddm form instance
	*/
	@Override
	public void setStructureId(long structureId) {
		_ddmFormInstance.setStructureId(structureId);
	}

	/**
	* Sets the user ID of this ddm form instance.
	*
	* @param userId the user ID of this ddm form instance
	*/
	@Override
	public void setUserId(long userId) {
		_ddmFormInstance.setUserId(userId);
	}

	/**
	* Sets the user name of this ddm form instance.
	*
	* @param userName the user name of this ddm form instance
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddmFormInstance.setUserName(userName);
	}

	/**
	* Sets the user uuid of this ddm form instance.
	*
	* @param userUuid the user uuid of this ddm form instance
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddmFormInstance.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this ddm form instance.
	*
	* @param uuid the uuid of this ddm form instance
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ddmFormInstance.setUuid(uuid);
	}

	/**
	* Sets the version of this ddm form instance.
	*
	* @param version the version of this ddm form instance
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_ddmFormInstance.setVersion(version);
	}

	/**
	* Sets the version user ID of this ddm form instance.
	*
	* @param versionUserId the version user ID of this ddm form instance
	*/
	@Override
	public void setVersionUserId(long versionUserId) {
		_ddmFormInstance.setVersionUserId(versionUserId);
	}

	/**
	* Sets the version user name of this ddm form instance.
	*
	* @param versionUserName the version user name of this ddm form instance
	*/
	@Override
	public void setVersionUserName(java.lang.String versionUserName) {
		_ddmFormInstance.setVersionUserName(versionUserName);
	}

	/**
	* Sets the version user uuid of this ddm form instance.
	*
	* @param versionUserUuid the version user uuid of this ddm form instance
	*/
	@Override
	public void setVersionUserUuid(java.lang.String versionUserUuid) {
		_ddmFormInstance.setVersionUserUuid(versionUserUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DDMFormInstance> toCacheModel() {
		return _ddmFormInstance.toCacheModel();
	}

	@Override
	public DDMFormInstance toEscapedModel() {
		return new DDMFormInstanceWrapper(_ddmFormInstance.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmFormInstance.toString();
	}

	@Override
	public DDMFormInstance toUnescapedModel() {
		return new DDMFormInstanceWrapper(_ddmFormInstance.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmFormInstance.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormInstanceWrapper)) {
			return false;
		}

		DDMFormInstanceWrapper ddmFormInstanceWrapper = (DDMFormInstanceWrapper)obj;

		if (Objects.equals(_ddmFormInstance,
					ddmFormInstanceWrapper._ddmFormInstance)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddmFormInstance.getStagedModelType();
	}

	@Override
	public DDMFormInstance getWrappedModel() {
		return _ddmFormInstance;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmFormInstance.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmFormInstance.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmFormInstance.resetOriginalValues();
	}

	private final DDMFormInstance _ddmFormInstance;
}