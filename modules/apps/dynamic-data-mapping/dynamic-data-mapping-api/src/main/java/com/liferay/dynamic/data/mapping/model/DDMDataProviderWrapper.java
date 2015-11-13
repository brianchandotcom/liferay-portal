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
 * This class is a wrapper for {@link DDMDataProvider}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProvider
 * @generated
 */
@ProviderType
public class DDMDataProviderWrapper implements DDMDataProvider,
	ModelWrapper<DDMDataProvider> {
	public DDMDataProviderWrapper(DDMDataProvider ddmDataProvider) {
		_ddmDataProvider = ddmDataProvider;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMDataProvider.class;
	}

	@Override
	public String getModelClassName() {
		return DDMDataProvider.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("dataProviderId", getDataProviderId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("definition", getDefinition());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long dataProviderId = (Long)attributes.get("dataProviderId");

		if (dataProviderId != null) {
			setDataProviderId(dataProviderId);
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
	}

	@Override
	public java.lang.Object clone() {
		return new DDMDataProviderWrapper((DDMDataProvider)_ddmDataProvider.clone());
	}

	@Override
	public int compareTo(
		com.liferay.dynamic.data.mapping.model.DDMDataProvider ddmDataProvider) {
		return _ddmDataProvider.compareTo(ddmDataProvider);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _ddmDataProvider.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this d d m data provider.
	*
	* @return the company ID of this d d m data provider
	*/
	@Override
	public long getCompanyId() {
		return _ddmDataProvider.getCompanyId();
	}

	/**
	* Returns the create date of this d d m data provider.
	*
	* @return the create date of this d d m data provider
	*/
	@Override
	public Date getCreateDate() {
		return _ddmDataProvider.getCreateDate();
	}

	/**
	* Returns the data provider ID of this d d m data provider.
	*
	* @return the data provider ID of this d d m data provider
	*/
	@Override
	public long getDataProviderId() {
		return _ddmDataProvider.getDataProviderId();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _ddmDataProvider.getDefaultLanguageId();
	}

	/**
	* Returns the definition of this d d m data provider.
	*
	* @return the definition of this d d m data provider
	*/
	@Override
	public java.lang.String getDefinition() {
		return _ddmDataProvider.getDefinition();
	}

	/**
	* Returns the description of this d d m data provider.
	*
	* @return the description of this d d m data provider
	*/
	@Override
	public java.lang.String getDescription() {
		return _ddmDataProvider.getDescription();
	}

	/**
	* Returns the localized description of this d d m data provider in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this d d m data provider
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _ddmDataProvider.getDescription(languageId);
	}

	/**
	* Returns the localized description of this d d m data provider in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d m data provider
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _ddmDataProvider.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this d d m data provider in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this d d m data provider
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _ddmDataProvider.getDescription(locale);
	}

	/**
	* Returns the localized description of this d d m data provider in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d m data provider. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _ddmDataProvider.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _ddmDataProvider.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _ddmDataProvider.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this d d m data provider.
	*
	* @return the locales and localized descriptions of this d d m data provider
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _ddmDataProvider.getDescriptionMap();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmDataProvider.getExpandoBridge();
	}

	/**
	* Returns the group ID of this d d m data provider.
	*
	* @return the group ID of this d d m data provider
	*/
	@Override
	public long getGroupId() {
		return _ddmDataProvider.getGroupId();
	}

	/**
	* Returns the modified date of this d d m data provider.
	*
	* @return the modified date of this d d m data provider
	*/
	@Override
	public Date getModifiedDate() {
		return _ddmDataProvider.getModifiedDate();
	}

	/**
	* Returns the name of this d d m data provider.
	*
	* @return the name of this d d m data provider
	*/
	@Override
	public java.lang.String getName() {
		return _ddmDataProvider.getName();
	}

	/**
	* Returns the localized name of this d d m data provider in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this d d m data provider
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _ddmDataProvider.getName(languageId);
	}

	/**
	* Returns the localized name of this d d m data provider in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m data provider
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddmDataProvider.getName(languageId, useDefault);
	}

	/**
	* Returns the localized name of this d d m data provider in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this d d m data provider
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _ddmDataProvider.getName(locale);
	}

	/**
	* Returns the localized name of this d d m data provider in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m data provider. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmDataProvider.getName(locale, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _ddmDataProvider.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _ddmDataProvider.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this d d m data provider.
	*
	* @return the locales and localized names of this d d m data provider
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmDataProvider.getNameMap();
	}

	/**
	* Returns the primary key of this d d m data provider.
	*
	* @return the primary key of this d d m data provider
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmDataProvider.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmDataProvider.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this d d m data provider.
	*
	* @return the user ID of this d d m data provider
	*/
	@Override
	public long getUserId() {
		return _ddmDataProvider.getUserId();
	}

	/**
	* Returns the user name of this d d m data provider.
	*
	* @return the user name of this d d m data provider
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddmDataProvider.getUserName();
	}

	/**
	* Returns the user uuid of this d d m data provider.
	*
	* @return the user uuid of this d d m data provider
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddmDataProvider.getUserUuid();
	}

	/**
	* Returns the uuid of this d d m data provider.
	*
	* @return the uuid of this d d m data provider
	*/
	@Override
	public java.lang.String getUuid() {
		return _ddmDataProvider.getUuid();
	}

	@Override
	public int hashCode() {
		return _ddmDataProvider.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmDataProvider.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmDataProvider.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddmDataProvider.isNew();
	}

	@Override
	public void persist() {
		_ddmDataProvider.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_ddmDataProvider.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_ddmDataProvider.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmDataProvider.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this d d m data provider.
	*
	* @param companyId the company ID of this d d m data provider
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmDataProvider.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this d d m data provider.
	*
	* @param createDate the create date of this d d m data provider
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddmDataProvider.setCreateDate(createDate);
	}

	/**
	* Sets the data provider ID of this d d m data provider.
	*
	* @param dataProviderId the data provider ID of this d d m data provider
	*/
	@Override
	public void setDataProviderId(long dataProviderId) {
		_ddmDataProvider.setDataProviderId(dataProviderId);
	}

	/**
	* Sets the definition of this d d m data provider.
	*
	* @param definition the definition of this d d m data provider
	*/
	@Override
	public void setDefinition(java.lang.String definition) {
		_ddmDataProvider.setDefinition(definition);
	}

	/**
	* Sets the description of this d d m data provider.
	*
	* @param description the description of this d d m data provider
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_ddmDataProvider.setDescription(description);
	}

	/**
	* Sets the localized description of this d d m data provider in the language.
	*
	* @param description the localized description of this d d m data provider
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_ddmDataProvider.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this d d m data provider in the language, and sets the default locale.
	*
	* @param description the localized description of this d d m data provider
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_ddmDataProvider.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_ddmDataProvider.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this d d m data provider from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m data provider
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_ddmDataProvider.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this d d m data provider from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m data provider
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddmDataProvider.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddmDataProvider.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddmDataProvider.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmDataProvider.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this d d m data provider.
	*
	* @param groupId the group ID of this d d m data provider
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmDataProvider.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this d d m data provider.
	*
	* @param modifiedDate the modified date of this d d m data provider
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmDataProvider.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this d d m data provider.
	*
	* @param name the name of this d d m data provider
	*/
	@Override
	public void setName(java.lang.String name) {
		_ddmDataProvider.setName(name);
	}

	/**
	* Sets the localized name of this d d m data provider in the language.
	*
	* @param name the localized name of this d d m data provider
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddmDataProvider.setName(name, locale);
	}

	/**
	* Sets the localized name of this d d m data provider in the language, and sets the default locale.
	*
	* @param name the localized name of this d d m data provider
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmDataProvider.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_ddmDataProvider.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this d d m data provider from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d m data provider
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmDataProvider.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this d d m data provider from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this d d m data provider
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmDataProvider.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddmDataProvider.setNew(n);
	}

	/**
	* Sets the primary key of this d d m data provider.
	*
	* @param primaryKey the primary key of this d d m data provider
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmDataProvider.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmDataProvider.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this d d m data provider.
	*
	* @param userId the user ID of this d d m data provider
	*/
	@Override
	public void setUserId(long userId) {
		_ddmDataProvider.setUserId(userId);
	}

	/**
	* Sets the user name of this d d m data provider.
	*
	* @param userName the user name of this d d m data provider
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddmDataProvider.setUserName(userName);
	}

	/**
	* Sets the user uuid of this d d m data provider.
	*
	* @param userUuid the user uuid of this d d m data provider
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddmDataProvider.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this d d m data provider.
	*
	* @param uuid the uuid of this d d m data provider
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ddmDataProvider.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.dynamic.data.mapping.model.DDMDataProvider> toCacheModel() {
		return _ddmDataProvider.toCacheModel();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider toEscapedModel() {
		return new DDMDataProviderWrapper(_ddmDataProvider.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmDataProvider.toString();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider toUnescapedModel() {
		return new DDMDataProviderWrapper(_ddmDataProvider.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmDataProvider.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMDataProviderWrapper)) {
			return false;
		}

		DDMDataProviderWrapper ddmDataProviderWrapper = (DDMDataProviderWrapper)obj;

		if (Validator.equals(_ddmDataProvider,
					ddmDataProviderWrapper._ddmDataProvider)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddmDataProvider.getStagedModelType();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public DDMDataProvider getWrappedDDMDataProvider() {
		return _ddmDataProvider;
	}

	@Override
	public DDMDataProvider getWrappedModel() {
		return _ddmDataProvider;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmDataProvider.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmDataProvider.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmDataProvider.resetOriginalValues();
	}

	private final DDMDataProvider _ddmDataProvider;
}