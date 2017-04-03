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
 * This class is a wrapper for {@link CommerceProductOption}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductOption
 * @generated
 */
@ProviderType
public class CommerceProductOptionWrapper implements CommerceProductOption,
	ModelWrapper<CommerceProductOption> {
	public CommerceProductOptionWrapper(
		CommerceProductOption commerceProductOption) {
		_commerceProductOption = commerceProductOption;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceProductOption.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceProductOption.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceProductOptionId", getCommerceProductOptionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("DDMFormFieldTypeName", getDDMFormFieldTypeName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceProductOptionId = (Long)attributes.get(
				"commerceProductOptionId");

		if (commerceProductOptionId != null) {
			setCommerceProductOptionId(commerceProductOptionId);
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

		String DDMFormFieldTypeName = (String)attributes.get(
				"DDMFormFieldTypeName");

		if (DDMFormFieldTypeName != null) {
			setDDMFormFieldTypeName(DDMFormFieldTypeName);
		}
	}

	@Override
	public CommerceProductOption toEscapedModel() {
		return new CommerceProductOptionWrapper(_commerceProductOption.toEscapedModel());
	}

	@Override
	public CommerceProductOption toUnescapedModel() {
		return new CommerceProductOptionWrapper(_commerceProductOption.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _commerceProductOption.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceProductOption.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceProductOption.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceProductOption.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceProductOption> toCacheModel() {
		return _commerceProductOption.toCacheModel();
	}

	@Override
	public int compareTo(CommerceProductOption commerceProductOption) {
		return _commerceProductOption.compareTo(commerceProductOption);
	}

	@Override
	public int hashCode() {
		return _commerceProductOption.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceProductOption.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceProductOptionWrapper((CommerceProductOption)_commerceProductOption.clone());
	}

	/**
	* Returns the ddm form field type name of this commerce product option.
	*
	* @return the ddm form field type name of this commerce product option
	*/
	@Override
	public java.lang.String getDDMFormFieldTypeName() {
		return _commerceProductOption.getDDMFormFieldTypeName();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceProductOption.getDefaultLanguageId();
	}

	/**
	* Returns the description of this commerce product option.
	*
	* @return the description of this commerce product option
	*/
	@Override
	public java.lang.String getDescription() {
		return _commerceProductOption.getDescription();
	}

	/**
	* Returns the localized description of this commerce product option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this commerce product option
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _commerceProductOption.getDescription(languageId);
	}

	/**
	* Returns the localized description of this commerce product option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce product option
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _commerceProductOption.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this commerce product option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this commerce product option
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _commerceProductOption.getDescription(locale);
	}

	/**
	* Returns the localized description of this commerce product option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce product option. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _commerceProductOption.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _commerceProductOption.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _commerceProductOption.getDescriptionCurrentValue();
	}

	/**
	* Returns the name of this commerce product option.
	*
	* @return the name of this commerce product option
	*/
	@Override
	public java.lang.String getName() {
		return _commerceProductOption.getName();
	}

	/**
	* Returns the localized name of this commerce product option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this commerce product option
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _commerceProductOption.getName(languageId);
	}

	/**
	* Returns the localized name of this commerce product option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce product option
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _commerceProductOption.getName(languageId, useDefault);
	}

	/**
	* Returns the localized name of this commerce product option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this commerce product option
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _commerceProductOption.getName(locale);
	}

	/**
	* Returns the localized name of this commerce product option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce product option. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _commerceProductOption.getName(locale, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _commerceProductOption.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _commerceProductOption.getNameCurrentValue();
	}

	/**
	* Returns the user name of this commerce product option.
	*
	* @return the user name of this commerce product option
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceProductOption.getUserName();
	}

	/**
	* Returns the user uuid of this commerce product option.
	*
	* @return the user uuid of this commerce product option
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceProductOption.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _commerceProductOption.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceProductOption.toXmlString();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceProductOption.getAvailableLanguageIds();
	}

	/**
	* Returns the create date of this commerce product option.
	*
	* @return the create date of this commerce product option
	*/
	@Override
	public Date getCreateDate() {
		return _commerceProductOption.getCreateDate();
	}

	/**
	* Returns the modified date of this commerce product option.
	*
	* @return the modified date of this commerce product option
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceProductOption.getModifiedDate();
	}

	/**
	* Returns a map of the locales and localized descriptions of this commerce product option.
	*
	* @return the locales and localized descriptions of this commerce product option
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _commerceProductOption.getDescriptionMap();
	}

	/**
	* Returns a map of the locales and localized names of this commerce product option.
	*
	* @return the locales and localized names of this commerce product option
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _commerceProductOption.getNameMap();
	}

	/**
	* Returns the commerce product option ID of this commerce product option.
	*
	* @return the commerce product option ID of this commerce product option
	*/
	@Override
	public long getCommerceProductOptionId() {
		return _commerceProductOption.getCommerceProductOptionId();
	}

	/**
	* Returns the company ID of this commerce product option.
	*
	* @return the company ID of this commerce product option
	*/
	@Override
	public long getCompanyId() {
		return _commerceProductOption.getCompanyId();
	}

	/**
	* Returns the group ID of this commerce product option.
	*
	* @return the group ID of this commerce product option
	*/
	@Override
	public long getGroupId() {
		return _commerceProductOption.getGroupId();
	}

	/**
	* Returns the primary key of this commerce product option.
	*
	* @return the primary key of this commerce product option
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceProductOption.getPrimaryKey();
	}

	/**
	* Returns the user ID of this commerce product option.
	*
	* @return the user ID of this commerce product option
	*/
	@Override
	public long getUserId() {
		return _commerceProductOption.getUserId();
	}

	@Override
	public void persist() {
		_commerceProductOption.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceProductOption.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceProductOption.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceProductOption.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce product option ID of this commerce product option.
	*
	* @param commerceProductOptionId the commerce product option ID of this commerce product option
	*/
	@Override
	public void setCommerceProductOptionId(long commerceProductOptionId) {
		_commerceProductOption.setCommerceProductOptionId(commerceProductOptionId);
	}

	/**
	* Sets the company ID of this commerce product option.
	*
	* @param companyId the company ID of this commerce product option
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceProductOption.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce product option.
	*
	* @param createDate the create date of this commerce product option
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceProductOption.setCreateDate(createDate);
	}

	/**
	* Sets the ddm form field type name of this commerce product option.
	*
	* @param DDMFormFieldTypeName the ddm form field type name of this commerce product option
	*/
	@Override
	public void setDDMFormFieldTypeName(java.lang.String DDMFormFieldTypeName) {
		_commerceProductOption.setDDMFormFieldTypeName(DDMFormFieldTypeName);
	}

	/**
	* Sets the description of this commerce product option.
	*
	* @param description the description of this commerce product option
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_commerceProductOption.setDescription(description);
	}

	/**
	* Sets the localized description of this commerce product option in the language.
	*
	* @param description the localized description of this commerce product option
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_commerceProductOption.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this commerce product option in the language, and sets the default locale.
	*
	* @param description the localized description of this commerce product option
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_commerceProductOption.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_commerceProductOption.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this commerce product option from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce product option
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_commerceProductOption.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this commerce product option from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce product option
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_commerceProductOption.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceProductOption.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceProductOption.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceProductOption.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce product option.
	*
	* @param groupId the group ID of this commerce product option
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceProductOption.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce product option.
	*
	* @param modifiedDate the modified date of this commerce product option
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceProductOption.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce product option.
	*
	* @param name the name of this commerce product option
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceProductOption.setName(name);
	}

	/**
	* Sets the localized name of this commerce product option in the language.
	*
	* @param name the localized name of this commerce product option
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_commerceProductOption.setName(name, locale);
	}

	/**
	* Sets the localized name of this commerce product option in the language, and sets the default locale.
	*
	* @param name the localized name of this commerce product option
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceProductOption.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_commerceProductOption.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this commerce product option from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this commerce product option
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_commerceProductOption.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this commerce product option from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this commerce product option
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_commerceProductOption.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_commerceProductOption.setNew(n);
	}

	/**
	* Sets the primary key of this commerce product option.
	*
	* @param primaryKey the primary key of this commerce product option
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceProductOption.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceProductOption.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce product option.
	*
	* @param userId the user ID of this commerce product option
	*/
	@Override
	public void setUserId(long userId) {
		_commerceProductOption.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce product option.
	*
	* @param userName the user name of this commerce product option
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceProductOption.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce product option.
	*
	* @param userUuid the user uuid of this commerce product option
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceProductOption.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductOptionWrapper)) {
			return false;
		}

		CommerceProductOptionWrapper commerceProductOptionWrapper = (CommerceProductOptionWrapper)obj;

		if (Objects.equals(_commerceProductOption,
					commerceProductOptionWrapper._commerceProductOption)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceProductOption getWrappedModel() {
		return _commerceProductOption;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceProductOption.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceProductOption.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceProductOption.resetOriginalValues();
	}

	private final CommerceProductOption _commerceProductOption;
}