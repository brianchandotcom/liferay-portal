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

package com.liferay.commerce.shipping.engine.fixed.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceShippingFixedOption}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOption
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionWrapper
	implements CommerceShippingFixedOption,
		ModelWrapper<CommerceShippingFixedOption> {
	public CommerceShippingFixedOptionWrapper(
		CommerceShippingFixedOption commerceShippingFixedOption) {
		_commerceShippingFixedOption = commerceShippingFixedOption;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceShippingFixedOption.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceShippingFixedOption.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceShippingFixedOptionId",
			getCommerceShippingFixedOptionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceShippingMethodId", getCommerceShippingMethodId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("amount", getAmount());
		attributes.put("priority", getPriority());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceShippingFixedOptionId = (Long)attributes.get(
				"commerceShippingFixedOptionId");

		if (commerceShippingFixedOptionId != null) {
			setCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
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

		Long commerceShippingMethodId = (Long)attributes.get(
				"commerceShippingMethodId");

		if (commerceShippingMethodId != null) {
			setCommerceShippingMethodId(commerceShippingMethodId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		BigDecimal amount = (BigDecimal)attributes.get("amount");

		if (amount != null) {
			setAmount(amount);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceShippingFixedOptionWrapper((CommerceShippingFixedOption)_commerceShippingFixedOption.clone());
	}

	@Override
	public int compareTo(
		CommerceShippingFixedOption commerceShippingFixedOption) {
		return _commerceShippingFixedOption.compareTo(commerceShippingFixedOption);
	}

	/**
	* Returns the amount of this commerce shipping fixed option.
	*
	* @return the amount of this commerce shipping fixed option
	*/
	@Override
	public BigDecimal getAmount() {
		return _commerceShippingFixedOption.getAmount();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceShippingFixedOption.getAvailableLanguageIds();
	}

	/**
	* Returns the commerce shipping fixed option ID of this commerce shipping fixed option.
	*
	* @return the commerce shipping fixed option ID of this commerce shipping fixed option
	*/
	@Override
	public long getCommerceShippingFixedOptionId() {
		return _commerceShippingFixedOption.getCommerceShippingFixedOptionId();
	}

	/**
	* Returns the commerce shipping method ID of this commerce shipping fixed option.
	*
	* @return the commerce shipping method ID of this commerce shipping fixed option
	*/
	@Override
	public long getCommerceShippingMethodId() {
		return _commerceShippingFixedOption.getCommerceShippingMethodId();
	}

	/**
	* Returns the company ID of this commerce shipping fixed option.
	*
	* @return the company ID of this commerce shipping fixed option
	*/
	@Override
	public long getCompanyId() {
		return _commerceShippingFixedOption.getCompanyId();
	}

	/**
	* Returns the create date of this commerce shipping fixed option.
	*
	* @return the create date of this commerce shipping fixed option
	*/
	@Override
	public Date getCreateDate() {
		return _commerceShippingFixedOption.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceShippingFixedOption.getDefaultLanguageId();
	}

	/**
	* Returns the description of this commerce shipping fixed option.
	*
	* @return the description of this commerce shipping fixed option
	*/
	@Override
	public java.lang.String getDescription() {
		return _commerceShippingFixedOption.getDescription();
	}

	/**
	* Returns the localized description of this commerce shipping fixed option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this commerce shipping fixed option
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _commerceShippingFixedOption.getDescription(locale);
	}

	/**
	* Returns the localized description of this commerce shipping fixed option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce shipping fixed option. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _commerceShippingFixedOption.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this commerce shipping fixed option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this commerce shipping fixed option
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _commerceShippingFixedOption.getDescription(languageId);
	}

	/**
	* Returns the localized description of this commerce shipping fixed option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce shipping fixed option
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _commerceShippingFixedOption.getDescription(languageId,
			useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _commerceShippingFixedOption.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _commerceShippingFixedOption.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this commerce shipping fixed option.
	*
	* @return the locales and localized descriptions of this commerce shipping fixed option
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _commerceShippingFixedOption.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceShippingFixedOption.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce shipping fixed option.
	*
	* @return the group ID of this commerce shipping fixed option
	*/
	@Override
	public long getGroupId() {
		return _commerceShippingFixedOption.getGroupId();
	}

	/**
	* Returns the modified date of this commerce shipping fixed option.
	*
	* @return the modified date of this commerce shipping fixed option
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceShippingFixedOption.getModifiedDate();
	}

	/**
	* Returns the name of this commerce shipping fixed option.
	*
	* @return the name of this commerce shipping fixed option
	*/
	@Override
	public java.lang.String getName() {
		return _commerceShippingFixedOption.getName();
	}

	/**
	* Returns the localized name of this commerce shipping fixed option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this commerce shipping fixed option
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _commerceShippingFixedOption.getName(locale);
	}

	/**
	* Returns the localized name of this commerce shipping fixed option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce shipping fixed option. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _commerceShippingFixedOption.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this commerce shipping fixed option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this commerce shipping fixed option
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _commerceShippingFixedOption.getName(languageId);
	}

	/**
	* Returns the localized name of this commerce shipping fixed option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce shipping fixed option
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _commerceShippingFixedOption.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _commerceShippingFixedOption.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _commerceShippingFixedOption.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this commerce shipping fixed option.
	*
	* @return the locales and localized names of this commerce shipping fixed option
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _commerceShippingFixedOption.getNameMap();
	}

	/**
	* Returns the primary key of this commerce shipping fixed option.
	*
	* @return the primary key of this commerce shipping fixed option
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceShippingFixedOption.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceShippingFixedOption.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this commerce shipping fixed option.
	*
	* @return the priority of this commerce shipping fixed option
	*/
	@Override
	public double getPriority() {
		return _commerceShippingFixedOption.getPriority();
	}

	/**
	* Returns the user ID of this commerce shipping fixed option.
	*
	* @return the user ID of this commerce shipping fixed option
	*/
	@Override
	public long getUserId() {
		return _commerceShippingFixedOption.getUserId();
	}

	/**
	* Returns the user name of this commerce shipping fixed option.
	*
	* @return the user name of this commerce shipping fixed option
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceShippingFixedOption.getUserName();
	}

	/**
	* Returns the user uuid of this commerce shipping fixed option.
	*
	* @return the user uuid of this commerce shipping fixed option
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceShippingFixedOption.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceShippingFixedOption.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceShippingFixedOption.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceShippingFixedOption.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceShippingFixedOption.isNew();
	}

	@Override
	public void persist() {
		_commerceShippingFixedOption.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceShippingFixedOption.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceShippingFixedOption.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets the amount of this commerce shipping fixed option.
	*
	* @param amount the amount of this commerce shipping fixed option
	*/
	@Override
	public void setAmount(BigDecimal amount) {
		_commerceShippingFixedOption.setAmount(amount);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceShippingFixedOption.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce shipping fixed option ID of this commerce shipping fixed option.
	*
	* @param commerceShippingFixedOptionId the commerce shipping fixed option ID of this commerce shipping fixed option
	*/
	@Override
	public void setCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		_commerceShippingFixedOption.setCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
	}

	/**
	* Sets the commerce shipping method ID of this commerce shipping fixed option.
	*
	* @param commerceShippingMethodId the commerce shipping method ID of this commerce shipping fixed option
	*/
	@Override
	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		_commerceShippingFixedOption.setCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	* Sets the company ID of this commerce shipping fixed option.
	*
	* @param companyId the company ID of this commerce shipping fixed option
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceShippingFixedOption.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce shipping fixed option.
	*
	* @param createDate the create date of this commerce shipping fixed option
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceShippingFixedOption.setCreateDate(createDate);
	}

	/**
	* Sets the description of this commerce shipping fixed option.
	*
	* @param description the description of this commerce shipping fixed option
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_commerceShippingFixedOption.setDescription(description);
	}

	/**
	* Sets the localized description of this commerce shipping fixed option in the language.
	*
	* @param description the localized description of this commerce shipping fixed option
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_commerceShippingFixedOption.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this commerce shipping fixed option in the language, and sets the default locale.
	*
	* @param description the localized description of this commerce shipping fixed option
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_commerceShippingFixedOption.setDescription(description, locale,
			defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_commerceShippingFixedOption.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this commerce shipping fixed option from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce shipping fixed option
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_commerceShippingFixedOption.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this commerce shipping fixed option from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce shipping fixed option
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_commerceShippingFixedOption.setDescriptionMap(descriptionMap,
			defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceShippingFixedOption.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceShippingFixedOption.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceShippingFixedOption.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce shipping fixed option.
	*
	* @param groupId the group ID of this commerce shipping fixed option
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceShippingFixedOption.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce shipping fixed option.
	*
	* @param modifiedDate the modified date of this commerce shipping fixed option
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceShippingFixedOption.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce shipping fixed option.
	*
	* @param name the name of this commerce shipping fixed option
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceShippingFixedOption.setName(name);
	}

	/**
	* Sets the localized name of this commerce shipping fixed option in the language.
	*
	* @param name the localized name of this commerce shipping fixed option
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_commerceShippingFixedOption.setName(name, locale);
	}

	/**
	* Sets the localized name of this commerce shipping fixed option in the language, and sets the default locale.
	*
	* @param name the localized name of this commerce shipping fixed option
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceShippingFixedOption.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_commerceShippingFixedOption.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this commerce shipping fixed option from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this commerce shipping fixed option
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_commerceShippingFixedOption.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this commerce shipping fixed option from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this commerce shipping fixed option
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_commerceShippingFixedOption.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_commerceShippingFixedOption.setNew(n);
	}

	/**
	* Sets the primary key of this commerce shipping fixed option.
	*
	* @param primaryKey the primary key of this commerce shipping fixed option
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceShippingFixedOption.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceShippingFixedOption.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce shipping fixed option.
	*
	* @param priority the priority of this commerce shipping fixed option
	*/
	@Override
	public void setPriority(double priority) {
		_commerceShippingFixedOption.setPriority(priority);
	}

	/**
	* Sets the user ID of this commerce shipping fixed option.
	*
	* @param userId the user ID of this commerce shipping fixed option
	*/
	@Override
	public void setUserId(long userId) {
		_commerceShippingFixedOption.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce shipping fixed option.
	*
	* @param userName the user name of this commerce shipping fixed option
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceShippingFixedOption.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce shipping fixed option.
	*
	* @param userUuid the user uuid of this commerce shipping fixed option
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceShippingFixedOption.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceShippingFixedOption> toCacheModel() {
		return _commerceShippingFixedOption.toCacheModel();
	}

	@Override
	public CommerceShippingFixedOption toEscapedModel() {
		return new CommerceShippingFixedOptionWrapper(_commerceShippingFixedOption.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceShippingFixedOption.toString();
	}

	@Override
	public CommerceShippingFixedOption toUnescapedModel() {
		return new CommerceShippingFixedOptionWrapper(_commerceShippingFixedOption.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceShippingFixedOption.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceShippingFixedOptionWrapper)) {
			return false;
		}

		CommerceShippingFixedOptionWrapper commerceShippingFixedOptionWrapper = (CommerceShippingFixedOptionWrapper)obj;

		if (Objects.equals(_commerceShippingFixedOption,
					commerceShippingFixedOptionWrapper._commerceShippingFixedOption)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceShippingFixedOption getWrappedModel() {
		return _commerceShippingFixedOption;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceShippingFixedOption.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceShippingFixedOption.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceShippingFixedOption.resetOriginalValues();
	}

	private final CommerceShippingFixedOption _commerceShippingFixedOption;
}