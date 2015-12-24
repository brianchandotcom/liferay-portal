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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

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
@ProviderType
public class DDMDataProviderInstanceWrapper implements DDMDataProviderInstance,
	ModelWrapper<DDMDataProviderInstance> {
	public DDMDataProviderInstanceWrapper(
		DDMDataProviderInstance ddmDataProviderInstance) {
		_ddmDataProviderInstance = ddmDataProviderInstance;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMDataProviderInstance.class;
	}

	@Override
	public String getModelClassName() {
		return DDMDataProviderInstance.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

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

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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
	}

	@Override
	public java.lang.Object clone() {
		return new DDMDataProviderInstanceWrapper((DDMDataProviderInstance)_ddmDataProviderInstance.clone());
	}

	@Override
	public int compareTo(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance ddmDataProviderInstance) {
		return _ddmDataProviderInstance.compareTo(ddmDataProviderInstance);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _ddmDataProviderInstance.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this d d m data provider instance.
	*
	* @return the company ID of this d d m data provider instance
	*/
	@Override
	public long getCompanyId() {
		return _ddmDataProviderInstance.getCompanyId();
	}

	/**
	* Returns the create date of this d d m data provider instance.
	*
	* @return the create date of this d d m data provider instance
	*/
	@Override
	public Date getCreateDate() {
		return _ddmDataProviderInstance.getCreateDate();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.util.KeyValuePair> getData()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstance.getData();
	}

	/**
	* Returns the data provider instance ID of this d d m data provider instance.
	*
	* @return the data provider instance ID of this d d m data provider instance
	*/
	@Override
	public long getDataProviderInstanceId() {
		return _ddmDataProviderInstance.getDataProviderInstanceId();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _ddmDataProviderInstance.getDefaultLanguageId();
	}

	/**
	* Returns the definition of this d d m data provider instance.
	*
	* @return the definition of this d d m data provider instance
	*/
	@Override
	public java.lang.String getDefinition() {
		return _ddmDataProviderInstance.getDefinition();
	}

	/**
	* Returns the description of this d d m data provider instance.
	*
	* @return the description of this d d m data provider instance
	*/
	@Override
	public java.lang.String getDescription() {
		return _ddmDataProviderInstance.getDescription();
	}

	/**
	* Returns the localized description of this d d m data provider instance in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this d d m data provider instance
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _ddmDataProviderInstance.getDescription(languageId);
	}

	/**
	* Returns the localized description of this d d m data provider instance in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d m data provider instance
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _ddmDataProviderInstance.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this d d m data provider instance in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this d d m data provider instance
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _ddmDataProviderInstance.getDescription(locale);
	}

	/**
	* Returns the localized description of this d d m data provider instance in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d m data provider instance. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _ddmDataProviderInstance.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _ddmDataProviderInstance.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _ddmDataProviderInstance.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this d d m data provider instance.
	*
	* @return the locales and localized descriptions of this d d m data provider instance
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _ddmDataProviderInstance.getDescriptionMap();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmDataProviderInstance.getExpandoBridge();
	}

	/**
	* Returns the group ID of this d d m data provider instance.
	*
	* @return the group ID of this d d m data provider instance
	*/
	@Override
	public long getGroupId() {
		return _ddmDataProviderInstance.getGroupId();
	}

	/**
	* Returns the modified date of this d d m data provider instance.
	*
	* @return the modified date of this d d m data provider instance
	*/
	@Override
	public Date getModifiedDate() {
		return _ddmDataProviderInstance.getModifiedDate();
	}

	/**
	* Returns the name of this d d m data provider instance.
	*
	* @return the name of this d d m data provider instance
	*/
	@Override
	public java.lang.String getName() {
		return _ddmDataProviderInstance.getName();
	}

	/**
	* Returns the localized name of this d d m data provider instance in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this d d m data provider instance
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _ddmDataProviderInstance.getName(languageId);
	}

	/**
	* Returns the localized name of this d d m data provider instance in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m data provider instance
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddmDataProviderInstance.getName(languageId, useDefault);
	}

	/**
	* Returns the localized name of this d d m data provider instance in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this d d m data provider instance
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _ddmDataProviderInstance.getName(locale);
	}

	/**
	* Returns the localized name of this d d m data provider instance in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m data provider instance. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmDataProviderInstance.getName(locale, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _ddmDataProviderInstance.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _ddmDataProviderInstance.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this d d m data provider instance.
	*
	* @return the locales and localized names of this d d m data provider instance
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmDataProviderInstance.getNameMap();
	}

	/**
	* Returns the primary key of this d d m data provider instance.
	*
	* @return the primary key of this d d m data provider instance
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmDataProviderInstance.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmDataProviderInstance.getPrimaryKeyObj();
	}

	/**
	* Returns the type of this d d m data provider instance.
	*
	* @return the type of this d d m data provider instance
	*/
	@Override
	public java.lang.String getType() {
		return _ddmDataProviderInstance.getType();
	}

	/**
	* Returns the user ID of this d d m data provider instance.
	*
	* @return the user ID of this d d m data provider instance
	*/
	@Override
	public long getUserId() {
		return _ddmDataProviderInstance.getUserId();
	}

	/**
	* Returns the user name of this d d m data provider instance.
	*
	* @return the user name of this d d m data provider instance
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddmDataProviderInstance.getUserName();
	}

	/**
	* Returns the user uuid of this d d m data provider instance.
	*
	* @return the user uuid of this d d m data provider instance
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddmDataProviderInstance.getUserUuid();
	}

	/**
	* Returns the uuid of this d d m data provider instance.
	*
	* @return the uuid of this d d m data provider instance
	*/
	@Override
	public java.lang.String getUuid() {
		return _ddmDataProviderInstance.getUuid();
	}

	@Override
	public int hashCode() {
		return _ddmDataProviderInstance.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmDataProviderInstance.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmDataProviderInstance.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddmDataProviderInstance.isNew();
	}

	@Override
	public void persist() {
		_ddmDataProviderInstance.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_ddmDataProviderInstance.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_ddmDataProviderInstance.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmDataProviderInstance.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this d d m data provider instance.
	*
	* @param companyId the company ID of this d d m data provider instance
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmDataProviderInstance.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this d d m data provider instance.
	*
	* @param createDate the create date of this d d m data provider instance
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddmDataProviderInstance.setCreateDate(createDate);
	}

	/**
	* Sets the data provider instance ID of this d d m data provider instance.
	*
	* @param dataProviderInstanceId the data provider instance ID of this d d m data provider instance
	*/
	@Override
	public void setDataProviderInstanceId(long dataProviderInstanceId) {
		_ddmDataProviderInstance.setDataProviderInstanceId(dataProviderInstanceId);
	}

	/**
	* Sets the definition of this d d m data provider instance.
	*
	* @param definition the definition of this d d m data provider instance
	*/
	@Override
	public void setDefinition(java.lang.String definition) {
		_ddmDataProviderInstance.setDefinition(definition);
	}

	/**
	* Sets the description of this d d m data provider instance.
	*
	* @param description the description of this d d m data provider instance
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_ddmDataProviderInstance.setDescription(description);
	}

	/**
	* Sets the localized description of this d d m data provider instance in the language.
	*
	* @param description the localized description of this d d m data provider instance
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_ddmDataProviderInstance.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this d d m data provider instance in the language, and sets the default locale.
	*
	* @param description the localized description of this d d m data provider instance
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_ddmDataProviderInstance.setDescription(description, locale,
			defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_ddmDataProviderInstance.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this d d m data provider instance from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m data provider instance
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_ddmDataProviderInstance.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this d d m data provider instance from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m data provider instance
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddmDataProviderInstance.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddmDataProviderInstance.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddmDataProviderInstance.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmDataProviderInstance.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this d d m data provider instance.
	*
	* @param groupId the group ID of this d d m data provider instance
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmDataProviderInstance.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this d d m data provider instance.
	*
	* @param modifiedDate the modified date of this d d m data provider instance
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmDataProviderInstance.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this d d m data provider instance.
	*
	* @param name the name of this d d m data provider instance
	*/
	@Override
	public void setName(java.lang.String name) {
		_ddmDataProviderInstance.setName(name);
	}

	/**
	* Sets the localized name of this d d m data provider instance in the language.
	*
	* @param name the localized name of this d d m data provider instance
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddmDataProviderInstance.setName(name, locale);
	}

	/**
	* Sets the localized name of this d d m data provider instance in the language, and sets the default locale.
	*
	* @param name the localized name of this d d m data provider instance
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmDataProviderInstance.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_ddmDataProviderInstance.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this d d m data provider instance from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d m data provider instance
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmDataProviderInstance.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this d d m data provider instance from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this d d m data provider instance
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmDataProviderInstance.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddmDataProviderInstance.setNew(n);
	}

	/**
	* Sets the primary key of this d d m data provider instance.
	*
	* @param primaryKey the primary key of this d d m data provider instance
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmDataProviderInstance.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmDataProviderInstance.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the type of this d d m data provider instance.
	*
	* @param type the type of this d d m data provider instance
	*/
	@Override
	public void setType(java.lang.String type) {
		_ddmDataProviderInstance.setType(type);
	}

	/**
	* Sets the user ID of this d d m data provider instance.
	*
	* @param userId the user ID of this d d m data provider instance
	*/
	@Override
	public void setUserId(long userId) {
		_ddmDataProviderInstance.setUserId(userId);
	}

	/**
	* Sets the user name of this d d m data provider instance.
	*
	* @param userName the user name of this d d m data provider instance
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddmDataProviderInstance.setUserName(userName);
	}

	/**
	* Sets the user uuid of this d d m data provider instance.
	*
	* @param userUuid the user uuid of this d d m data provider instance
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddmDataProviderInstance.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this d d m data provider instance.
	*
	* @param uuid the uuid of this d d m data provider instance
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ddmDataProviderInstance.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> toCacheModel() {
		return _ddmDataProviderInstance.toCacheModel();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance toEscapedModel() {
		return new DDMDataProviderInstanceWrapper(_ddmDataProviderInstance.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmDataProviderInstance.toString();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance toUnescapedModel() {
		return new DDMDataProviderInstanceWrapper(_ddmDataProviderInstance.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmDataProviderInstance.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMDataProviderInstanceWrapper)) {
			return false;
		}

		DDMDataProviderInstanceWrapper ddmDataProviderInstanceWrapper = (DDMDataProviderInstanceWrapper)obj;

		if (Validator.equals(_ddmDataProviderInstance,
					ddmDataProviderInstanceWrapper._ddmDataProviderInstance)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddmDataProviderInstance.getStagedModelType();
	}

	@Override
	public DDMDataProviderInstance getWrappedModel() {
		return _ddmDataProviderInstance;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmDataProviderInstance.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmDataProviderInstance.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmDataProviderInstance.resetOriginalValues();
	}

	private final DDMDataProviderInstance _ddmDataProviderInstance;
}