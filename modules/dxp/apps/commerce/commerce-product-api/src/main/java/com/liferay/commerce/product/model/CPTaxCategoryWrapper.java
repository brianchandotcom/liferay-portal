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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CPTaxCategory}.
 * </p>
 *
 * @author Marco Leo
 * @see CPTaxCategory
 * @generated
 */
@ProviderType
public class CPTaxCategoryWrapper implements CPTaxCategory,
	ModelWrapper<CPTaxCategory> {
	public CPTaxCategoryWrapper(CPTaxCategory cpTaxCategory) {
		_cpTaxCategory = cpTaxCategory;
	}

	@Override
	public Class<?> getModelClass() {
		return CPTaxCategory.class;
	}

	@Override
	public String getModelClassName() {
		return CPTaxCategory.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("CPTaxCategoryId", getCPTaxCategoryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long CPTaxCategoryId = (Long)attributes.get("CPTaxCategoryId");

		if (CPTaxCategoryId != null) {
			setCPTaxCategoryId(CPTaxCategoryId);
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
	}

	@Override
	public Object clone() {
		return new CPTaxCategoryWrapper((CPTaxCategory)_cpTaxCategory.clone());
	}

	@Override
	public int compareTo(CPTaxCategory cpTaxCategory) {
		return _cpTaxCategory.compareTo(cpTaxCategory);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _cpTaxCategory.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this cp tax category.
	*
	* @return the company ID of this cp tax category
	*/
	@Override
	public long getCompanyId() {
		return _cpTaxCategory.getCompanyId();
	}

	/**
	* Returns the cp tax category ID of this cp tax category.
	*
	* @return the cp tax category ID of this cp tax category
	*/
	@Override
	public long getCPTaxCategoryId() {
		return _cpTaxCategory.getCPTaxCategoryId();
	}

	/**
	* Returns the create date of this cp tax category.
	*
	* @return the create date of this cp tax category
	*/
	@Override
	public Date getCreateDate() {
		return _cpTaxCategory.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return _cpTaxCategory.getDefaultLanguageId();
	}

	/**
	* Returns the description of this cp tax category.
	*
	* @return the description of this cp tax category
	*/
	@Override
	public String getDescription() {
		return _cpTaxCategory.getDescription();
	}

	/**
	* Returns the localized description of this cp tax category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this cp tax category
	*/
	@Override
	public String getDescription(java.util.Locale locale) {
		return _cpTaxCategory.getDescription(locale);
	}

	/**
	* Returns the localized description of this cp tax category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this cp tax category. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return _cpTaxCategory.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this cp tax category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this cp tax category
	*/
	@Override
	public String getDescription(String languageId) {
		return _cpTaxCategory.getDescription(languageId);
	}

	/**
	* Returns the localized description of this cp tax category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this cp tax category
	*/
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _cpTaxCategory.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _cpTaxCategory.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return _cpTaxCategory.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this cp tax category.
	*
	* @return the locales and localized descriptions of this cp tax category
	*/
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return _cpTaxCategory.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpTaxCategory.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp tax category.
	*
	* @return the group ID of this cp tax category
	*/
	@Override
	public long getGroupId() {
		return _cpTaxCategory.getGroupId();
	}

	/**
	* Returns the modified date of this cp tax category.
	*
	* @return the modified date of this cp tax category
	*/
	@Override
	public Date getModifiedDate() {
		return _cpTaxCategory.getModifiedDate();
	}

	/**
	* Returns the name of this cp tax category.
	*
	* @return the name of this cp tax category
	*/
	@Override
	public String getName() {
		return _cpTaxCategory.getName();
	}

	/**
	* Returns the localized name of this cp tax category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this cp tax category
	*/
	@Override
	public String getName(java.util.Locale locale) {
		return _cpTaxCategory.getName(locale);
	}

	/**
	* Returns the localized name of this cp tax category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this cp tax category. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return _cpTaxCategory.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this cp tax category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this cp tax category
	*/
	@Override
	public String getName(String languageId) {
		return _cpTaxCategory.getName(languageId);
	}

	/**
	* Returns the localized name of this cp tax category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this cp tax category
	*/
	@Override
	public String getName(String languageId, boolean useDefault) {
		return _cpTaxCategory.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _cpTaxCategory.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return _cpTaxCategory.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this cp tax category.
	*
	* @return the locales and localized names of this cp tax category
	*/
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return _cpTaxCategory.getNameMap();
	}

	/**
	* Returns the primary key of this cp tax category.
	*
	* @return the primary key of this cp tax category
	*/
	@Override
	public long getPrimaryKey() {
		return _cpTaxCategory.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpTaxCategory.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this cp tax category.
	*
	* @return the user ID of this cp tax category
	*/
	@Override
	public long getUserId() {
		return _cpTaxCategory.getUserId();
	}

	/**
	* Returns the user name of this cp tax category.
	*
	* @return the user name of this cp tax category
	*/
	@Override
	public String getUserName() {
		return _cpTaxCategory.getUserName();
	}

	/**
	* Returns the user uuid of this cp tax category.
	*
	* @return the user uuid of this cp tax category
	*/
	@Override
	public String getUserUuid() {
		return _cpTaxCategory.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _cpTaxCategory.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpTaxCategory.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpTaxCategory.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpTaxCategory.isNew();
	}

	@Override
	public void persist() {
		_cpTaxCategory.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpTaxCategory.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpTaxCategory.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpTaxCategory.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp tax category.
	*
	* @param companyId the company ID of this cp tax category
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpTaxCategory.setCompanyId(companyId);
	}

	/**
	* Sets the cp tax category ID of this cp tax category.
	*
	* @param CPTaxCategoryId the cp tax category ID of this cp tax category
	*/
	@Override
	public void setCPTaxCategoryId(long CPTaxCategoryId) {
		_cpTaxCategory.setCPTaxCategoryId(CPTaxCategoryId);
	}

	/**
	* Sets the create date of this cp tax category.
	*
	* @param createDate the create date of this cp tax category
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpTaxCategory.setCreateDate(createDate);
	}

	/**
	* Sets the description of this cp tax category.
	*
	* @param description the description of this cp tax category
	*/
	@Override
	public void setDescription(String description) {
		_cpTaxCategory.setDescription(description);
	}

	/**
	* Sets the localized description of this cp tax category in the language.
	*
	* @param description the localized description of this cp tax category
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		_cpTaxCategory.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this cp tax category in the language, and sets the default locale.
	*
	* @param description the localized description of this cp tax category
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpTaxCategory.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		_cpTaxCategory.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this cp tax category from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this cp tax category
	*/
	@Override
	public void setDescriptionMap(Map<java.util.Locale, String> descriptionMap) {
		_cpTaxCategory.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this cp tax category from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this cp tax category
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {
		_cpTaxCategory.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpTaxCategory.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpTaxCategory.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpTaxCategory.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp tax category.
	*
	* @param groupId the group ID of this cp tax category
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpTaxCategory.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this cp tax category.
	*
	* @param modifiedDate the modified date of this cp tax category
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpTaxCategory.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this cp tax category.
	*
	* @param name the name of this cp tax category
	*/
	@Override
	public void setName(String name) {
		_cpTaxCategory.setName(name);
	}

	/**
	* Sets the localized name of this cp tax category in the language.
	*
	* @param name the localized name of this cp tax category
	* @param locale the locale of the language
	*/
	@Override
	public void setName(String name, java.util.Locale locale) {
		_cpTaxCategory.setName(name, locale);
	}

	/**
	* Sets the localized name of this cp tax category in the language, and sets the default locale.
	*
	* @param name the localized name of this cp tax category
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpTaxCategory.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_cpTaxCategory.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this cp tax category from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this cp tax category
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		_cpTaxCategory.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this cp tax category from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this cp tax category
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap,
		java.util.Locale defaultLocale) {
		_cpTaxCategory.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_cpTaxCategory.setNew(n);
	}

	/**
	* Sets the primary key of this cp tax category.
	*
	* @param primaryKey the primary key of this cp tax category
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpTaxCategory.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpTaxCategory.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this cp tax category.
	*
	* @param userId the user ID of this cp tax category
	*/
	@Override
	public void setUserId(long userId) {
		_cpTaxCategory.setUserId(userId);
	}

	/**
	* Sets the user name of this cp tax category.
	*
	* @param userName the user name of this cp tax category
	*/
	@Override
	public void setUserName(String userName) {
		_cpTaxCategory.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp tax category.
	*
	* @param userUuid the user uuid of this cp tax category
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpTaxCategory.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPTaxCategory> toCacheModel() {
		return _cpTaxCategory.toCacheModel();
	}

	@Override
	public CPTaxCategory toEscapedModel() {
		return new CPTaxCategoryWrapper(_cpTaxCategory.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpTaxCategory.toString();
	}

	@Override
	public CPTaxCategory toUnescapedModel() {
		return new CPTaxCategoryWrapper(_cpTaxCategory.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpTaxCategory.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPTaxCategoryWrapper)) {
			return false;
		}

		CPTaxCategoryWrapper cpTaxCategoryWrapper = (CPTaxCategoryWrapper)obj;

		if (Objects.equals(_cpTaxCategory, cpTaxCategoryWrapper._cpTaxCategory)) {
			return true;
		}

		return false;
	}

	@Override
	public CPTaxCategory getWrappedModel() {
		return _cpTaxCategory;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpTaxCategory.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpTaxCategory.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpTaxCategory.resetOriginalValues();
	}

	private final CPTaxCategory _cpTaxCategory;
}