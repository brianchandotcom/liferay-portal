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

package com.liferay.portlet.dynamicdatamapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMStructureVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureVersion
 * @generated
 */
@ProviderType
public class DDMStructureVersionWrapper implements DDMStructureVersion,
	ModelWrapper<DDMStructureVersion> {
	public DDMStructureVersionWrapper(DDMStructureVersion ddmStructureVersion) {
		_ddmStructureVersion = ddmStructureVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMStructureVersion.class;
	}

	@Override
	public String getModelClassName() {
		return DDMStructureVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("structureVersionId", getStructureVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("structureId", getStructureId());
		attributes.put("version", getVersion());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("definition", getDefinition());
		attributes.put("storageType", getStorageType());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long structureVersionId = (Long)attributes.get("structureVersionId");

		if (structureVersionId != null) {
			setStructureVersionId(structureVersionId);
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

		String definition = (String)attributes.get("definition");

		if (definition != null) {
			setDefinition(definition);
		}

		String storageType = (String)attributes.get("storageType");

		if (storageType != null) {
			setStorageType(storageType);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new DDMStructureVersionWrapper((DDMStructureVersion)_ddmStructureVersion.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion ddmStructureVersion) {
		return _ddmStructureVersion.compareTo(ddmStructureVersion);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _ddmStructureVersion.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this d d m structure version.
	*
	* @return the company ID of this d d m structure version
	*/
	@Override
	public long getCompanyId() {
		return _ddmStructureVersion.getCompanyId();
	}

	/**
	* Returns the create date of this d d m structure version.
	*
	* @return the create date of this d d m structure version
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _ddmStructureVersion.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _ddmStructureVersion.getDefaultLanguageId();
	}

	/**
	* Returns the definition of this d d m structure version.
	*
	* @return the definition of this d d m structure version
	*/
	@Override
	public java.lang.String getDefinition() {
		return _ddmStructureVersion.getDefinition();
	}

	/**
	* Returns the description of this d d m structure version.
	*
	* @return the description of this d d m structure version
	*/
	@Override
	public java.lang.String getDescription() {
		return _ddmStructureVersion.getDescription();
	}

	/**
	* Returns the localized description of this d d m structure version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this d d m structure version
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _ddmStructureVersion.getDescription(languageId);
	}

	/**
	* Returns the localized description of this d d m structure version in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d m structure version
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _ddmStructureVersion.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this d d m structure version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this d d m structure version
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _ddmStructureVersion.getDescription(locale);
	}

	/**
	* Returns the localized description of this d d m structure version in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d m structure version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _ddmStructureVersion.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _ddmStructureVersion.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _ddmStructureVersion.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this d d m structure version.
	*
	* @return the locales and localized descriptions of this d d m structure version
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _ddmStructureVersion.getDescriptionMap();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmStructureVersion.getExpandoBridge();
	}

	/**
	* Returns the group ID of this d d m structure version.
	*
	* @return the group ID of this d d m structure version
	*/
	@Override
	public long getGroupId() {
		return _ddmStructureVersion.getGroupId();
	}

	/**
	* Returns the name of this d d m structure version.
	*
	* @return the name of this d d m structure version
	*/
	@Override
	public java.lang.String getName() {
		return _ddmStructureVersion.getName();
	}

	/**
	* Returns the localized name of this d d m structure version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this d d m structure version
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _ddmStructureVersion.getName(languageId);
	}

	/**
	* Returns the localized name of this d d m structure version in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m structure version
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddmStructureVersion.getName(languageId, useDefault);
	}

	/**
	* Returns the localized name of this d d m structure version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this d d m structure version
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _ddmStructureVersion.getName(locale);
	}

	/**
	* Returns the localized name of this d d m structure version in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m structure version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmStructureVersion.getName(locale, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _ddmStructureVersion.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _ddmStructureVersion.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this d d m structure version.
	*
	* @return the locales and localized names of this d d m structure version
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmStructureVersion.getNameMap();
	}

	/**
	* Returns the primary key of this d d m structure version.
	*
	* @return the primary key of this d d m structure version
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmStructureVersion.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmStructureVersion.getPrimaryKeyObj();
	}

	/**
	* Returns the storage type of this d d m structure version.
	*
	* @return the storage type of this d d m structure version
	*/
	@Override
	public java.lang.String getStorageType() {
		return _ddmStructureVersion.getStorageType();
	}

	/**
	* Returns the structure ID of this d d m structure version.
	*
	* @return the structure ID of this d d m structure version
	*/
	@Override
	public long getStructureId() {
		return _ddmStructureVersion.getStructureId();
	}

	/**
	* Returns the structure version ID of this d d m structure version.
	*
	* @return the structure version ID of this d d m structure version
	*/
	@Override
	public long getStructureVersionId() {
		return _ddmStructureVersion.getStructureVersionId();
	}

	/**
	* Returns the type of this d d m structure version.
	*
	* @return the type of this d d m structure version
	*/
	@Override
	public int getType() {
		return _ddmStructureVersion.getType();
	}

	/**
	* Returns the user ID of this d d m structure version.
	*
	* @return the user ID of this d d m structure version
	*/
	@Override
	public long getUserId() {
		return _ddmStructureVersion.getUserId();
	}

	/**
	* Returns the user name of this d d m structure version.
	*
	* @return the user name of this d d m structure version
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddmStructureVersion.getUserName();
	}

	/**
	* Returns the user uuid of this d d m structure version.
	*
	* @return the user uuid of this d d m structure version
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddmStructureVersion.getUserUuid();
	}

	/**
	* Returns the version of this d d m structure version.
	*
	* @return the version of this d d m structure version
	*/
	@Override
	public java.lang.String getVersion() {
		return _ddmStructureVersion.getVersion();
	}

	@Override
	public int hashCode() {
		return _ddmStructureVersion.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmStructureVersion.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmStructureVersion.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddmStructureVersion.isNew();
	}

	@Override
	public void persist() {
		_ddmStructureVersion.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_ddmStructureVersion.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_ddmStructureVersion.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmStructureVersion.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this d d m structure version.
	*
	* @param companyId the company ID of this d d m structure version
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmStructureVersion.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this d d m structure version.
	*
	* @param createDate the create date of this d d m structure version
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_ddmStructureVersion.setCreateDate(createDate);
	}

	/**
	* Sets the definition of this d d m structure version.
	*
	* @param definition the definition of this d d m structure version
	*/
	@Override
	public void setDefinition(java.lang.String definition) {
		_ddmStructureVersion.setDefinition(definition);
	}

	/**
	* Sets the description of this d d m structure version.
	*
	* @param description the description of this d d m structure version
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_ddmStructureVersion.setDescription(description);
	}

	/**
	* Sets the localized description of this d d m structure version in the language.
	*
	* @param description the localized description of this d d m structure version
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_ddmStructureVersion.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this d d m structure version in the language, and sets the default locale.
	*
	* @param description the localized description of this d d m structure version
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_ddmStructureVersion.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_ddmStructureVersion.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this d d m structure version from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m structure version
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_ddmStructureVersion.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this d d m structure version from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m structure version
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddmStructureVersion.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddmStructureVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddmStructureVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmStructureVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this d d m structure version.
	*
	* @param groupId the group ID of this d d m structure version
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmStructureVersion.setGroupId(groupId);
	}

	/**
	* Sets the name of this d d m structure version.
	*
	* @param name the name of this d d m structure version
	*/
	@Override
	public void setName(java.lang.String name) {
		_ddmStructureVersion.setName(name);
	}

	/**
	* Sets the localized name of this d d m structure version in the language.
	*
	* @param name the localized name of this d d m structure version
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddmStructureVersion.setName(name, locale);
	}

	/**
	* Sets the localized name of this d d m structure version in the language, and sets the default locale.
	*
	* @param name the localized name of this d d m structure version
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmStructureVersion.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_ddmStructureVersion.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this d d m structure version from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d m structure version
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmStructureVersion.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this d d m structure version from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this d d m structure version
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmStructureVersion.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddmStructureVersion.setNew(n);
	}

	/**
	* Sets the primary key of this d d m structure version.
	*
	* @param primaryKey the primary key of this d d m structure version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmStructureVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmStructureVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the storage type of this d d m structure version.
	*
	* @param storageType the storage type of this d d m structure version
	*/
	@Override
	public void setStorageType(java.lang.String storageType) {
		_ddmStructureVersion.setStorageType(storageType);
	}

	/**
	* Sets the structure ID of this d d m structure version.
	*
	* @param structureId the structure ID of this d d m structure version
	*/
	@Override
	public void setStructureId(long structureId) {
		_ddmStructureVersion.setStructureId(structureId);
	}

	/**
	* Sets the structure version ID of this d d m structure version.
	*
	* @param structureVersionId the structure version ID of this d d m structure version
	*/
	@Override
	public void setStructureVersionId(long structureVersionId) {
		_ddmStructureVersion.setStructureVersionId(structureVersionId);
	}

	/**
	* Sets the type of this d d m structure version.
	*
	* @param type the type of this d d m structure version
	*/
	@Override
	public void setType(int type) {
		_ddmStructureVersion.setType(type);
	}

	/**
	* Sets the user ID of this d d m structure version.
	*
	* @param userId the user ID of this d d m structure version
	*/
	@Override
	public void setUserId(long userId) {
		_ddmStructureVersion.setUserId(userId);
	}

	/**
	* Sets the user name of this d d m structure version.
	*
	* @param userName the user name of this d d m structure version
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddmStructureVersion.setUserName(userName);
	}

	/**
	* Sets the user uuid of this d d m structure version.
	*
	* @param userUuid the user uuid of this d d m structure version
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddmStructureVersion.setUserUuid(userUuid);
	}

	/**
	* Sets the version of this d d m structure version.
	*
	* @param version the version of this d d m structure version
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_ddmStructureVersion.setVersion(version);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion> toCacheModel() {
		return _ddmStructureVersion.toCacheModel();
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion toEscapedModel() {
		return new DDMStructureVersionWrapper(_ddmStructureVersion.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmStructureVersion.toString();
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion toUnescapedModel() {
		return new DDMStructureVersionWrapper(_ddmStructureVersion.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmStructureVersion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMStructureVersionWrapper)) {
			return false;
		}

		DDMStructureVersionWrapper ddmStructureVersionWrapper = (DDMStructureVersionWrapper)obj;

		if (Validator.equals(_ddmStructureVersion,
					ddmStructureVersionWrapper._ddmStructureVersion)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public DDMStructureVersion getWrappedDDMStructureVersion() {
		return _ddmStructureVersion;
	}

	@Override
	public DDMStructureVersion getWrappedModel() {
		return _ddmStructureVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmStructureVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmStructureVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmStructureVersion.resetOriginalValues();
	}

	private final DDMStructureVersion _ddmStructureVersion;
}