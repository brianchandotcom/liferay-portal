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
 * This class is a wrapper for {@link CommercePaymentMethod}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePaymentMethod
 * @generated
 */
@ProviderType
public class CommercePaymentMethodWrapper implements CommercePaymentMethod,
	ModelWrapper<CommercePaymentMethod> {
	public CommercePaymentMethodWrapper(
		CommercePaymentMethod commercePaymentMethod) {
		_commercePaymentMethod = commercePaymentMethod;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePaymentMethod.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePaymentMethod.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commercePaymentMethodId", getCommercePaymentMethodId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("imageId", getImageId());
		attributes.put("engineKey", getEngineKey());
		attributes.put("priority", getPriority());
		attributes.put("active", isActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commercePaymentMethodId = (Long)attributes.get(
				"commercePaymentMethodId");

		if (commercePaymentMethodId != null) {
			setCommercePaymentMethodId(commercePaymentMethodId);
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

		Long imageId = (Long)attributes.get("imageId");

		if (imageId != null) {
			setImageId(imageId);
		}

		String engineKey = (String)attributes.get("engineKey");

		if (engineKey != null) {
			setEngineKey(engineKey);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommercePaymentMethodWrapper((CommercePaymentMethod)_commercePaymentMethod.clone());
	}

	@Override
	public int compareTo(CommercePaymentMethod commercePaymentMethod) {
		return _commercePaymentMethod.compareTo(commercePaymentMethod);
	}

	/**
	* Returns the active of this commerce payment method.
	*
	* @return the active of this commerce payment method
	*/
	@Override
	public boolean getActive() {
		return _commercePaymentMethod.getActive();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commercePaymentMethod.getAvailableLanguageIds();
	}

	/**
	* Returns the commerce payment method ID of this commerce payment method.
	*
	* @return the commerce payment method ID of this commerce payment method
	*/
	@Override
	public long getCommercePaymentMethodId() {
		return _commercePaymentMethod.getCommercePaymentMethodId();
	}

	/**
	* Returns the company ID of this commerce payment method.
	*
	* @return the company ID of this commerce payment method
	*/
	@Override
	public long getCompanyId() {
		return _commercePaymentMethod.getCompanyId();
	}

	/**
	* Returns the create date of this commerce payment method.
	*
	* @return the create date of this commerce payment method
	*/
	@Override
	public Date getCreateDate() {
		return _commercePaymentMethod.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commercePaymentMethod.getDefaultLanguageId();
	}

	/**
	* Returns the description of this commerce payment method.
	*
	* @return the description of this commerce payment method
	*/
	@Override
	public java.lang.String getDescription() {
		return _commercePaymentMethod.getDescription();
	}

	/**
	* Returns the localized description of this commerce payment method in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this commerce payment method
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _commercePaymentMethod.getDescription(locale);
	}

	/**
	* Returns the localized description of this commerce payment method in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce payment method. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _commercePaymentMethod.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this commerce payment method in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this commerce payment method
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _commercePaymentMethod.getDescription(languageId);
	}

	/**
	* Returns the localized description of this commerce payment method in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce payment method
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _commercePaymentMethod.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _commercePaymentMethod.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _commercePaymentMethod.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this commerce payment method.
	*
	* @return the locales and localized descriptions of this commerce payment method
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _commercePaymentMethod.getDescriptionMap();
	}

	/**
	* Returns the engine key of this commerce payment method.
	*
	* @return the engine key of this commerce payment method
	*/
	@Override
	public java.lang.String getEngineKey() {
		return _commercePaymentMethod.getEngineKey();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePaymentMethod.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce payment method.
	*
	* @return the group ID of this commerce payment method
	*/
	@Override
	public long getGroupId() {
		return _commercePaymentMethod.getGroupId();
	}

	/**
	* Returns the image ID of this commerce payment method.
	*
	* @return the image ID of this commerce payment method
	*/
	@Override
	public long getImageId() {
		return _commercePaymentMethod.getImageId();
	}

	@Override
	public java.lang.String getImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {
		return _commercePaymentMethod.getImageURL(themeDisplay);
	}

	/**
	* Returns the modified date of this commerce payment method.
	*
	* @return the modified date of this commerce payment method
	*/
	@Override
	public Date getModifiedDate() {
		return _commercePaymentMethod.getModifiedDate();
	}

	/**
	* Returns the name of this commerce payment method.
	*
	* @return the name of this commerce payment method
	*/
	@Override
	public java.lang.String getName() {
		return _commercePaymentMethod.getName();
	}

	/**
	* Returns the localized name of this commerce payment method in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this commerce payment method
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _commercePaymentMethod.getName(locale);
	}

	/**
	* Returns the localized name of this commerce payment method in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce payment method. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _commercePaymentMethod.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this commerce payment method in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this commerce payment method
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _commercePaymentMethod.getName(languageId);
	}

	/**
	* Returns the localized name of this commerce payment method in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce payment method
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _commercePaymentMethod.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _commercePaymentMethod.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _commercePaymentMethod.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this commerce payment method.
	*
	* @return the locales and localized names of this commerce payment method
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _commercePaymentMethod.getNameMap();
	}

	/**
	* Returns the primary key of this commerce payment method.
	*
	* @return the primary key of this commerce payment method
	*/
	@Override
	public long getPrimaryKey() {
		return _commercePaymentMethod.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePaymentMethod.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this commerce payment method.
	*
	* @return the priority of this commerce payment method
	*/
	@Override
	public double getPriority() {
		return _commercePaymentMethod.getPriority();
	}

	/**
	* Returns the user ID of this commerce payment method.
	*
	* @return the user ID of this commerce payment method
	*/
	@Override
	public long getUserId() {
		return _commercePaymentMethod.getUserId();
	}

	/**
	* Returns the user name of this commerce payment method.
	*
	* @return the user name of this commerce payment method
	*/
	@Override
	public java.lang.String getUserName() {
		return _commercePaymentMethod.getUserName();
	}

	/**
	* Returns the user uuid of this commerce payment method.
	*
	* @return the user uuid of this commerce payment method
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commercePaymentMethod.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commercePaymentMethod.hashCode();
	}

	/**
	* Returns <code>true</code> if this commerce payment method is active.
	*
	* @return <code>true</code> if this commerce payment method is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _commercePaymentMethod.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePaymentMethod.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePaymentMethod.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commercePaymentMethod.isNew();
	}

	@Override
	public void persist() {
		_commercePaymentMethod.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commercePaymentMethod.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commercePaymentMethod.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets whether this commerce payment method is active.
	*
	* @param active the active of this commerce payment method
	*/
	@Override
	public void setActive(boolean active) {
		_commercePaymentMethod.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePaymentMethod.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce payment method ID of this commerce payment method.
	*
	* @param commercePaymentMethodId the commerce payment method ID of this commerce payment method
	*/
	@Override
	public void setCommercePaymentMethodId(long commercePaymentMethodId) {
		_commercePaymentMethod.setCommercePaymentMethodId(commercePaymentMethodId);
	}

	/**
	* Sets the company ID of this commerce payment method.
	*
	* @param companyId the company ID of this commerce payment method
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commercePaymentMethod.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce payment method.
	*
	* @param createDate the create date of this commerce payment method
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commercePaymentMethod.setCreateDate(createDate);
	}

	/**
	* Sets the description of this commerce payment method.
	*
	* @param description the description of this commerce payment method
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_commercePaymentMethod.setDescription(description);
	}

	/**
	* Sets the localized description of this commerce payment method in the language.
	*
	* @param description the localized description of this commerce payment method
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_commercePaymentMethod.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this commerce payment method in the language, and sets the default locale.
	*
	* @param description the localized description of this commerce payment method
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_commercePaymentMethod.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_commercePaymentMethod.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this commerce payment method from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce payment method
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_commercePaymentMethod.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this commerce payment method from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce payment method
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_commercePaymentMethod.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Sets the engine key of this commerce payment method.
	*
	* @param engineKey the engine key of this commerce payment method
	*/
	@Override
	public void setEngineKey(java.lang.String engineKey) {
		_commercePaymentMethod.setEngineKey(engineKey);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commercePaymentMethod.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePaymentMethod.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePaymentMethod.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce payment method.
	*
	* @param groupId the group ID of this commerce payment method
	*/
	@Override
	public void setGroupId(long groupId) {
		_commercePaymentMethod.setGroupId(groupId);
	}

	/**
	* Sets the image ID of this commerce payment method.
	*
	* @param imageId the image ID of this commerce payment method
	*/
	@Override
	public void setImageId(long imageId) {
		_commercePaymentMethod.setImageId(imageId);
	}

	/**
	* Sets the modified date of this commerce payment method.
	*
	* @param modifiedDate the modified date of this commerce payment method
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePaymentMethod.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce payment method.
	*
	* @param name the name of this commerce payment method
	*/
	@Override
	public void setName(java.lang.String name) {
		_commercePaymentMethod.setName(name);
	}

	/**
	* Sets the localized name of this commerce payment method in the language.
	*
	* @param name the localized name of this commerce payment method
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_commercePaymentMethod.setName(name, locale);
	}

	/**
	* Sets the localized name of this commerce payment method in the language, and sets the default locale.
	*
	* @param name the localized name of this commerce payment method
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commercePaymentMethod.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_commercePaymentMethod.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this commerce payment method from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this commerce payment method
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_commercePaymentMethod.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this commerce payment method from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this commerce payment method
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_commercePaymentMethod.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_commercePaymentMethod.setNew(n);
	}

	/**
	* Sets the primary key of this commerce payment method.
	*
	* @param primaryKey the primary key of this commerce payment method
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePaymentMethod.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePaymentMethod.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce payment method.
	*
	* @param priority the priority of this commerce payment method
	*/
	@Override
	public void setPriority(double priority) {
		_commercePaymentMethod.setPriority(priority);
	}

	/**
	* Sets the user ID of this commerce payment method.
	*
	* @param userId the user ID of this commerce payment method
	*/
	@Override
	public void setUserId(long userId) {
		_commercePaymentMethod.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce payment method.
	*
	* @param userName the user name of this commerce payment method
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commercePaymentMethod.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce payment method.
	*
	* @param userUuid the user uuid of this commerce payment method
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commercePaymentMethod.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommercePaymentMethod> toCacheModel() {
		return _commercePaymentMethod.toCacheModel();
	}

	@Override
	public CommercePaymentMethod toEscapedModel() {
		return new CommercePaymentMethodWrapper(_commercePaymentMethod.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commercePaymentMethod.toString();
	}

	@Override
	public CommercePaymentMethod toUnescapedModel() {
		return new CommercePaymentMethodWrapper(_commercePaymentMethod.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commercePaymentMethod.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommercePaymentMethodWrapper)) {
			return false;
		}

		CommercePaymentMethodWrapper commercePaymentMethodWrapper = (CommercePaymentMethodWrapper)obj;

		if (Objects.equals(_commercePaymentMethod,
					commercePaymentMethodWrapper._commercePaymentMethod)) {
			return true;
		}

		return false;
	}

	@Override
	public CommercePaymentMethod getWrappedModel() {
		return _commercePaymentMethod;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePaymentMethod.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePaymentMethod.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePaymentMethod.resetOriginalValues();
	}

	private final CommercePaymentMethod _commercePaymentMethod;
}