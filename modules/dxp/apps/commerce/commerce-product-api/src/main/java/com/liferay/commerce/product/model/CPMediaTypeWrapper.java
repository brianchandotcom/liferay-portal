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

package com.liferay.commerce.product.model;

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
 * This class is a wrapper for {@link CPMediaType}.
 * </p>
 *
 * @author Marco Leo
 * @see CPMediaType
 * @generated
 */
@ProviderType
public class CPMediaTypeWrapper implements CPMediaType,
	ModelWrapper<CPMediaType> {
	public CPMediaTypeWrapper(CPMediaType cpMediaType) {
		_cpMediaType = cpMediaType;
	}

	@Override
	public Class<?> getModelClass() {
		return CPMediaType.class;
	}

	@Override
	public String getModelClassName() {
		return CPMediaType.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPMediaTypeId", getCPMediaTypeId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("priority", getPriority());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPMediaTypeId = (Long)attributes.get("CPMediaTypeId");

		if (CPMediaTypeId != null) {
			setCPMediaTypeId(CPMediaTypeId);
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public CPMediaType toEscapedModel() {
		return new CPMediaTypeWrapper(_cpMediaType.toEscapedModel());
	}

	@Override
	public CPMediaType toUnescapedModel() {
		return new CPMediaTypeWrapper(_cpMediaType.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _cpMediaType.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpMediaType.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpMediaType.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpMediaType.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPMediaType> toCacheModel() {
		return _cpMediaType.toCacheModel();
	}

	@Override
	public int compareTo(CPMediaType cpMediaType) {
		return _cpMediaType.compareTo(cpMediaType);
	}

	/**
	* Returns the priority of this cp media type.
	*
	* @return the priority of this cp media type
	*/
	@Override
	public int getPriority() {
		return _cpMediaType.getPriority();
	}

	@Override
	public int hashCode() {
		return _cpMediaType.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpMediaType.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CPMediaTypeWrapper((CPMediaType)_cpMediaType.clone());
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _cpMediaType.getDefaultLanguageId();
	}

	/**
	* Returns the description of this cp media type.
	*
	* @return the description of this cp media type
	*/
	@Override
	public java.lang.String getDescription() {
		return _cpMediaType.getDescription();
	}

	/**
	* Returns the localized description of this cp media type in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this cp media type
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _cpMediaType.getDescription(languageId);
	}

	/**
	* Returns the localized description of this cp media type in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this cp media type
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _cpMediaType.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this cp media type in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this cp media type
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _cpMediaType.getDescription(locale);
	}

	/**
	* Returns the localized description of this cp media type in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this cp media type. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _cpMediaType.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _cpMediaType.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _cpMediaType.getDescriptionCurrentValue();
	}

	/**
	* Returns the title of this cp media type.
	*
	* @return the title of this cp media type
	*/
	@Override
	public java.lang.String getTitle() {
		return _cpMediaType.getTitle();
	}

	/**
	* Returns the localized title of this cp media type in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this cp media type
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _cpMediaType.getTitle(languageId);
	}

	/**
	* Returns the localized title of this cp media type in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp media type
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _cpMediaType.getTitle(languageId, useDefault);
	}

	/**
	* Returns the localized title of this cp media type in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this cp media type
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _cpMediaType.getTitle(locale);
	}

	/**
	* Returns the localized title of this cp media type in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp media type. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _cpMediaType.getTitle(locale, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _cpMediaType.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _cpMediaType.getTitleCurrentValue();
	}

	/**
	* Returns the user name of this cp media type.
	*
	* @return the user name of this cp media type
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpMediaType.getUserName();
	}

	/**
	* Returns the user uuid of this cp media type.
	*
	* @return the user uuid of this cp media type
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpMediaType.getUserUuid();
	}

	/**
	* Returns the uuid of this cp media type.
	*
	* @return the uuid of this cp media type
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpMediaType.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _cpMediaType.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpMediaType.toXmlString();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _cpMediaType.getAvailableLanguageIds();
	}

	/**
	* Returns the create date of this cp media type.
	*
	* @return the create date of this cp media type
	*/
	@Override
	public Date getCreateDate() {
		return _cpMediaType.getCreateDate();
	}

	/**
	* Returns the modified date of this cp media type.
	*
	* @return the modified date of this cp media type
	*/
	@Override
	public Date getModifiedDate() {
		return _cpMediaType.getModifiedDate();
	}

	/**
	* Returns a map of the locales and localized descriptions of this cp media type.
	*
	* @return the locales and localized descriptions of this cp media type
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _cpMediaType.getDescriptionMap();
	}

	/**
	* Returns a map of the locales and localized titles of this cp media type.
	*
	* @return the locales and localized titles of this cp media type
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _cpMediaType.getTitleMap();
	}

	/**
	* Returns the cp media type ID of this cp media type.
	*
	* @return the cp media type ID of this cp media type
	*/
	@Override
	public long getCPMediaTypeId() {
		return _cpMediaType.getCPMediaTypeId();
	}

	/**
	* Returns the company ID of this cp media type.
	*
	* @return the company ID of this cp media type
	*/
	@Override
	public long getCompanyId() {
		return _cpMediaType.getCompanyId();
	}

	/**
	* Returns the group ID of this cp media type.
	*
	* @return the group ID of this cp media type
	*/
	@Override
	public long getGroupId() {
		return _cpMediaType.getGroupId();
	}

	/**
	* Returns the primary key of this cp media type.
	*
	* @return the primary key of this cp media type
	*/
	@Override
	public long getPrimaryKey() {
		return _cpMediaType.getPrimaryKey();
	}

	/**
	* Returns the user ID of this cp media type.
	*
	* @return the user ID of this cp media type
	*/
	@Override
	public long getUserId() {
		return _cpMediaType.getUserId();
	}

	@Override
	public void persist() {
		_cpMediaType.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpMediaType.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpMediaType.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets the cp media type ID of this cp media type.
	*
	* @param CPMediaTypeId the cp media type ID of this cp media type
	*/
	@Override
	public void setCPMediaTypeId(long CPMediaTypeId) {
		_cpMediaType.setCPMediaTypeId(CPMediaTypeId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpMediaType.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp media type.
	*
	* @param companyId the company ID of this cp media type
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpMediaType.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this cp media type.
	*
	* @param createDate the create date of this cp media type
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpMediaType.setCreateDate(createDate);
	}

	/**
	* Sets the description of this cp media type.
	*
	* @param description the description of this cp media type
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_cpMediaType.setDescription(description);
	}

	/**
	* Sets the localized description of this cp media type in the language.
	*
	* @param description the localized description of this cp media type
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_cpMediaType.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this cp media type in the language, and sets the default locale.
	*
	* @param description the localized description of this cp media type
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_cpMediaType.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_cpMediaType.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this cp media type from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this cp media type
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_cpMediaType.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this cp media type from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this cp media type
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_cpMediaType.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpMediaType.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpMediaType.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpMediaType.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp media type.
	*
	* @param groupId the group ID of this cp media type
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpMediaType.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this cp media type.
	*
	* @param modifiedDate the modified date of this cp media type
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpMediaType.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpMediaType.setNew(n);
	}

	/**
	* Sets the primary key of this cp media type.
	*
	* @param primaryKey the primary key of this cp media type
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpMediaType.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpMediaType.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this cp media type.
	*
	* @param priority the priority of this cp media type
	*/
	@Override
	public void setPriority(int priority) {
		_cpMediaType.setPriority(priority);
	}

	/**
	* Sets the title of this cp media type.
	*
	* @param title the title of this cp media type
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_cpMediaType.setTitle(title);
	}

	/**
	* Sets the localized title of this cp media type in the language.
	*
	* @param title the localized title of this cp media type
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_cpMediaType.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this cp media type in the language, and sets the default locale.
	*
	* @param title the localized title of this cp media type
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpMediaType.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_cpMediaType.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this cp media type from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this cp media type
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap) {
		_cpMediaType.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this cp media type from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this cp media type
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_cpMediaType.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the user ID of this cp media type.
	*
	* @param userId the user ID of this cp media type
	*/
	@Override
	public void setUserId(long userId) {
		_cpMediaType.setUserId(userId);
	}

	/**
	* Sets the user name of this cp media type.
	*
	* @param userName the user name of this cp media type
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpMediaType.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp media type.
	*
	* @param userUuid the user uuid of this cp media type
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpMediaType.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp media type.
	*
	* @param uuid the uuid of this cp media type
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpMediaType.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPMediaTypeWrapper)) {
			return false;
		}

		CPMediaTypeWrapper cpMediaTypeWrapper = (CPMediaTypeWrapper)obj;

		if (Objects.equals(_cpMediaType, cpMediaTypeWrapper._cpMediaType)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpMediaType.getStagedModelType();
	}

	@Override
	public CPMediaType getWrappedModel() {
		return _cpMediaType;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpMediaType.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpMediaType.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpMediaType.resetOriginalValues();
	}

	private final CPMediaType _cpMediaType;
}