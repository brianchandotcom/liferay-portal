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

package com.liferay.commerce.model;

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
 * This class is a wrapper for {@link CommerceTaxCategory}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategory
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryWrapper implements CommerceTaxCategory,
	ModelWrapper<CommerceTaxCategory> {
	public CommerceTaxCategoryWrapper(CommerceTaxCategory commerceTaxCategory) {
		_commerceTaxCategory = commerceTaxCategory;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceTaxCategory.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceTaxCategory.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceTaxCategoryId", getCommerceTaxCategoryId());
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
		Long commerceTaxCategoryId = (Long)attributes.get(
				"commerceTaxCategoryId");

		if (commerceTaxCategoryId != null) {
			setCommerceTaxCategoryId(commerceTaxCategoryId);
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
	public java.lang.Object clone() {
		return new CommerceTaxCategoryWrapper((CommerceTaxCategory)_commerceTaxCategory.clone());
	}

	@Override
	public int compareTo(CommerceTaxCategory commerceTaxCategory) {
		return _commerceTaxCategory.compareTo(commerceTaxCategory);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceTaxCategory.getAvailableLanguageIds();
	}

	/**
	* Returns the commerce tax category ID of this commerce tax category.
	*
	* @return the commerce tax category ID of this commerce tax category
	*/
	@Override
	public long getCommerceTaxCategoryId() {
		return _commerceTaxCategory.getCommerceTaxCategoryId();
	}

	/**
	* Returns the company ID of this commerce tax category.
	*
	* @return the company ID of this commerce tax category
	*/
	@Override
	public long getCompanyId() {
		return _commerceTaxCategory.getCompanyId();
	}

	/**
	* Returns the create date of this commerce tax category.
	*
	* @return the create date of this commerce tax category
	*/
	@Override
	public Date getCreateDate() {
		return _commerceTaxCategory.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceTaxCategory.getDefaultLanguageId();
	}

	/**
	* Returns the description of this commerce tax category.
	*
	* @return the description of this commerce tax category
	*/
	@Override
	public java.lang.String getDescription() {
		return _commerceTaxCategory.getDescription();
	}

	/**
	* Returns the localized description of this commerce tax category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this commerce tax category
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _commerceTaxCategory.getDescription(locale);
	}

	/**
	* Returns the localized description of this commerce tax category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce tax category. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _commerceTaxCategory.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this commerce tax category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this commerce tax category
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _commerceTaxCategory.getDescription(languageId);
	}

	/**
	* Returns the localized description of this commerce tax category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce tax category
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _commerceTaxCategory.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _commerceTaxCategory.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _commerceTaxCategory.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this commerce tax category.
	*
	* @return the locales and localized descriptions of this commerce tax category
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _commerceTaxCategory.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceTaxCategory.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce tax category.
	*
	* @return the group ID of this commerce tax category
	*/
	@Override
	public long getGroupId() {
		return _commerceTaxCategory.getGroupId();
	}

	/**
	* Returns the modified date of this commerce tax category.
	*
	* @return the modified date of this commerce tax category
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceTaxCategory.getModifiedDate();
	}

	/**
	* Returns the name of this commerce tax category.
	*
	* @return the name of this commerce tax category
	*/
	@Override
	public java.lang.String getName() {
		return _commerceTaxCategory.getName();
	}

	/**
	* Returns the localized name of this commerce tax category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this commerce tax category
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _commerceTaxCategory.getName(locale);
	}

	/**
	* Returns the localized name of this commerce tax category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce tax category. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _commerceTaxCategory.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this commerce tax category in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this commerce tax category
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _commerceTaxCategory.getName(languageId);
	}

	/**
	* Returns the localized name of this commerce tax category in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce tax category
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _commerceTaxCategory.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _commerceTaxCategory.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _commerceTaxCategory.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this commerce tax category.
	*
	* @return the locales and localized names of this commerce tax category
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _commerceTaxCategory.getNameMap();
	}

	/**
	* Returns the primary key of this commerce tax category.
	*
	* @return the primary key of this commerce tax category
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceTaxCategory.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceTaxCategory.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce tax category.
	*
	* @return the user ID of this commerce tax category
	*/
	@Override
	public long getUserId() {
		return _commerceTaxCategory.getUserId();
	}

	/**
	* Returns the user name of this commerce tax category.
	*
	* @return the user name of this commerce tax category
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceTaxCategory.getUserName();
	}

	/**
	* Returns the user uuid of this commerce tax category.
	*
	* @return the user uuid of this commerce tax category
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceTaxCategory.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceTaxCategory.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceTaxCategory.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceTaxCategory.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceTaxCategory.isNew();
	}

	@Override
	public void persist() {
		_commerceTaxCategory.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceTaxCategory.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceTaxCategory.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceTaxCategory.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce tax category ID of this commerce tax category.
	*
	* @param commerceTaxCategoryId the commerce tax category ID of this commerce tax category
	*/
	@Override
	public void setCommerceTaxCategoryId(long commerceTaxCategoryId) {
		_commerceTaxCategory.setCommerceTaxCategoryId(commerceTaxCategoryId);
	}

	/**
	* Sets the company ID of this commerce tax category.
	*
	* @param companyId the company ID of this commerce tax category
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceTaxCategory.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce tax category.
	*
	* @param createDate the create date of this commerce tax category
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceTaxCategory.setCreateDate(createDate);
	}

	/**
	* Sets the description of this commerce tax category.
	*
	* @param description the description of this commerce tax category
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_commerceTaxCategory.setDescription(description);
	}

	/**
	* Sets the localized description of this commerce tax category in the language.
	*
	* @param description the localized description of this commerce tax category
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_commerceTaxCategory.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this commerce tax category in the language, and sets the default locale.
	*
	* @param description the localized description of this commerce tax category
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_commerceTaxCategory.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_commerceTaxCategory.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this commerce tax category from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce tax category
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_commerceTaxCategory.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this commerce tax category from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce tax category
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_commerceTaxCategory.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceTaxCategory.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceTaxCategory.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceTaxCategory.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce tax category.
	*
	* @param groupId the group ID of this commerce tax category
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceTaxCategory.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce tax category.
	*
	* @param modifiedDate the modified date of this commerce tax category
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceTaxCategory.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce tax category.
	*
	* @param name the name of this commerce tax category
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceTaxCategory.setName(name);
	}

	/**
	* Sets the localized name of this commerce tax category in the language.
	*
	* @param name the localized name of this commerce tax category
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_commerceTaxCategory.setName(name, locale);
	}

	/**
	* Sets the localized name of this commerce tax category in the language, and sets the default locale.
	*
	* @param name the localized name of this commerce tax category
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceTaxCategory.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_commerceTaxCategory.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this commerce tax category from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this commerce tax category
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_commerceTaxCategory.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this commerce tax category from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this commerce tax category
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_commerceTaxCategory.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_commerceTaxCategory.setNew(n);
	}

	/**
	* Sets the primary key of this commerce tax category.
	*
	* @param primaryKey the primary key of this commerce tax category
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceTaxCategory.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceTaxCategory.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce tax category.
	*
	* @param userId the user ID of this commerce tax category
	*/
	@Override
	public void setUserId(long userId) {
		_commerceTaxCategory.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce tax category.
	*
	* @param userName the user name of this commerce tax category
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceTaxCategory.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce tax category.
	*
	* @param userUuid the user uuid of this commerce tax category
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceTaxCategory.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceTaxCategory> toCacheModel() {
		return _commerceTaxCategory.toCacheModel();
	}

	@Override
	public CommerceTaxCategory toEscapedModel() {
		return new CommerceTaxCategoryWrapper(_commerceTaxCategory.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceTaxCategory.toString();
	}

	@Override
	public CommerceTaxCategory toUnescapedModel() {
		return new CommerceTaxCategoryWrapper(_commerceTaxCategory.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceTaxCategory.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTaxCategoryWrapper)) {
			return false;
		}

		CommerceTaxCategoryWrapper commerceTaxCategoryWrapper = (CommerceTaxCategoryWrapper)obj;

		if (Objects.equals(_commerceTaxCategory,
					commerceTaxCategoryWrapper._commerceTaxCategory)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceTaxCategory getWrappedModel() {
		return _commerceTaxCategory;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceTaxCategory.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceTaxCategory.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceTaxCategory.resetOriginalValues();
	}

	private final CommerceTaxCategory _commerceTaxCategory;
}