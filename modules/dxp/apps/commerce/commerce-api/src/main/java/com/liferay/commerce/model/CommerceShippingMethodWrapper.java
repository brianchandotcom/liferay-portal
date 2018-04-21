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
 * This class is a wrapper for {@link CommerceShippingMethod}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingMethod
 * @generated
 */
@ProviderType
public class CommerceShippingMethodWrapper implements CommerceShippingMethod,
	ModelWrapper<CommerceShippingMethod> {
	public CommerceShippingMethodWrapper(
		CommerceShippingMethod commerceShippingMethod) {
		_commerceShippingMethod = commerceShippingMethod;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceShippingMethod.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceShippingMethod.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceShippingMethodId", getCommerceShippingMethodId());
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
		Long commerceShippingMethodId = (Long)attributes.get(
				"commerceShippingMethodId");

		if (commerceShippingMethodId != null) {
			setCommerceShippingMethodId(commerceShippingMethodId);
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
		return new CommerceShippingMethodWrapper((CommerceShippingMethod)_commerceShippingMethod.clone());
	}

	@Override
	public int compareTo(CommerceShippingMethod commerceShippingMethod) {
		return _commerceShippingMethod.compareTo(commerceShippingMethod);
	}

	/**
	* Returns the active of this commerce shipping method.
	*
	* @return the active of this commerce shipping method
	*/
	@Override
	public boolean getActive() {
		return _commerceShippingMethod.getActive();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceShippingMethod.getAvailableLanguageIds();
	}

	/**
	* Returns the commerce shipping method ID of this commerce shipping method.
	*
	* @return the commerce shipping method ID of this commerce shipping method
	*/
	@Override
	public long getCommerceShippingMethodId() {
		return _commerceShippingMethod.getCommerceShippingMethodId();
	}

	/**
	* Returns the company ID of this commerce shipping method.
	*
	* @return the company ID of this commerce shipping method
	*/
	@Override
	public long getCompanyId() {
		return _commerceShippingMethod.getCompanyId();
	}

	/**
	* Returns the create date of this commerce shipping method.
	*
	* @return the create date of this commerce shipping method
	*/
	@Override
	public Date getCreateDate() {
		return _commerceShippingMethod.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceShippingMethod.getDefaultLanguageId();
	}

	/**
	* Returns the description of this commerce shipping method.
	*
	* @return the description of this commerce shipping method
	*/
	@Override
	public java.lang.String getDescription() {
		return _commerceShippingMethod.getDescription();
	}

	/**
	* Returns the localized description of this commerce shipping method in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this commerce shipping method
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _commerceShippingMethod.getDescription(locale);
	}

	/**
	* Returns the localized description of this commerce shipping method in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce shipping method. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _commerceShippingMethod.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this commerce shipping method in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this commerce shipping method
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _commerceShippingMethod.getDescription(languageId);
	}

	/**
	* Returns the localized description of this commerce shipping method in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce shipping method
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _commerceShippingMethod.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _commerceShippingMethod.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _commerceShippingMethod.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this commerce shipping method.
	*
	* @return the locales and localized descriptions of this commerce shipping method
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _commerceShippingMethod.getDescriptionMap();
	}

	/**
	* Returns the engine key of this commerce shipping method.
	*
	* @return the engine key of this commerce shipping method
	*/
	@Override
	public java.lang.String getEngineKey() {
		return _commerceShippingMethod.getEngineKey();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceShippingMethod.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce shipping method.
	*
	* @return the group ID of this commerce shipping method
	*/
	@Override
	public long getGroupId() {
		return _commerceShippingMethod.getGroupId();
	}

	/**
	* Returns the image ID of this commerce shipping method.
	*
	* @return the image ID of this commerce shipping method
	*/
	@Override
	public long getImageId() {
		return _commerceShippingMethod.getImageId();
	}

	@Override
	public java.lang.String getImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {
		return _commerceShippingMethod.getImageURL(themeDisplay);
	}

	/**
	* Returns the modified date of this commerce shipping method.
	*
	* @return the modified date of this commerce shipping method
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceShippingMethod.getModifiedDate();
	}

	/**
	* Returns the name of this commerce shipping method.
	*
	* @return the name of this commerce shipping method
	*/
	@Override
	public java.lang.String getName() {
		return _commerceShippingMethod.getName();
	}

	/**
	* Returns the localized name of this commerce shipping method in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this commerce shipping method
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _commerceShippingMethod.getName(locale);
	}

	/**
	* Returns the localized name of this commerce shipping method in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce shipping method. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _commerceShippingMethod.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this commerce shipping method in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this commerce shipping method
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _commerceShippingMethod.getName(languageId);
	}

	/**
	* Returns the localized name of this commerce shipping method in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce shipping method
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _commerceShippingMethod.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _commerceShippingMethod.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _commerceShippingMethod.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this commerce shipping method.
	*
	* @return the locales and localized names of this commerce shipping method
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _commerceShippingMethod.getNameMap();
	}

	/**
	* Returns the primary key of this commerce shipping method.
	*
	* @return the primary key of this commerce shipping method
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceShippingMethod.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceShippingMethod.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this commerce shipping method.
	*
	* @return the priority of this commerce shipping method
	*/
	@Override
	public double getPriority() {
		return _commerceShippingMethod.getPriority();
	}

	/**
	* Returns the user ID of this commerce shipping method.
	*
	* @return the user ID of this commerce shipping method
	*/
	@Override
	public long getUserId() {
		return _commerceShippingMethod.getUserId();
	}

	/**
	* Returns the user name of this commerce shipping method.
	*
	* @return the user name of this commerce shipping method
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceShippingMethod.getUserName();
	}

	/**
	* Returns the user uuid of this commerce shipping method.
	*
	* @return the user uuid of this commerce shipping method
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceShippingMethod.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceShippingMethod.hashCode();
	}

	/**
	* Returns <code>true</code> if this commerce shipping method is active.
	*
	* @return <code>true</code> if this commerce shipping method is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _commerceShippingMethod.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceShippingMethod.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceShippingMethod.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceShippingMethod.isNew();
	}

	@Override
	public void persist() {
		_commerceShippingMethod.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceShippingMethod.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceShippingMethod.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets whether this commerce shipping method is active.
	*
	* @param active the active of this commerce shipping method
	*/
	@Override
	public void setActive(boolean active) {
		_commerceShippingMethod.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceShippingMethod.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce shipping method ID of this commerce shipping method.
	*
	* @param commerceShippingMethodId the commerce shipping method ID of this commerce shipping method
	*/
	@Override
	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		_commerceShippingMethod.setCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	* Sets the company ID of this commerce shipping method.
	*
	* @param companyId the company ID of this commerce shipping method
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceShippingMethod.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce shipping method.
	*
	* @param createDate the create date of this commerce shipping method
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceShippingMethod.setCreateDate(createDate);
	}

	/**
	* Sets the description of this commerce shipping method.
	*
	* @param description the description of this commerce shipping method
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_commerceShippingMethod.setDescription(description);
	}

	/**
	* Sets the localized description of this commerce shipping method in the language.
	*
	* @param description the localized description of this commerce shipping method
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_commerceShippingMethod.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this commerce shipping method in the language, and sets the default locale.
	*
	* @param description the localized description of this commerce shipping method
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_commerceShippingMethod.setDescription(description, locale,
			defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_commerceShippingMethod.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this commerce shipping method from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce shipping method
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_commerceShippingMethod.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this commerce shipping method from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce shipping method
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_commerceShippingMethod.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Sets the engine key of this commerce shipping method.
	*
	* @param engineKey the engine key of this commerce shipping method
	*/
	@Override
	public void setEngineKey(java.lang.String engineKey) {
		_commerceShippingMethod.setEngineKey(engineKey);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceShippingMethod.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceShippingMethod.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceShippingMethod.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce shipping method.
	*
	* @param groupId the group ID of this commerce shipping method
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceShippingMethod.setGroupId(groupId);
	}

	/**
	* Sets the image ID of this commerce shipping method.
	*
	* @param imageId the image ID of this commerce shipping method
	*/
	@Override
	public void setImageId(long imageId) {
		_commerceShippingMethod.setImageId(imageId);
	}

	/**
	* Sets the modified date of this commerce shipping method.
	*
	* @param modifiedDate the modified date of this commerce shipping method
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceShippingMethod.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce shipping method.
	*
	* @param name the name of this commerce shipping method
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceShippingMethod.setName(name);
	}

	/**
	* Sets the localized name of this commerce shipping method in the language.
	*
	* @param name the localized name of this commerce shipping method
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_commerceShippingMethod.setName(name, locale);
	}

	/**
	* Sets the localized name of this commerce shipping method in the language, and sets the default locale.
	*
	* @param name the localized name of this commerce shipping method
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceShippingMethod.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_commerceShippingMethod.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this commerce shipping method from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this commerce shipping method
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_commerceShippingMethod.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this commerce shipping method from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this commerce shipping method
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_commerceShippingMethod.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_commerceShippingMethod.setNew(n);
	}

	/**
	* Sets the primary key of this commerce shipping method.
	*
	* @param primaryKey the primary key of this commerce shipping method
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceShippingMethod.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceShippingMethod.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce shipping method.
	*
	* @param priority the priority of this commerce shipping method
	*/
	@Override
	public void setPriority(double priority) {
		_commerceShippingMethod.setPriority(priority);
	}

	/**
	* Sets the user ID of this commerce shipping method.
	*
	* @param userId the user ID of this commerce shipping method
	*/
	@Override
	public void setUserId(long userId) {
		_commerceShippingMethod.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce shipping method.
	*
	* @param userName the user name of this commerce shipping method
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceShippingMethod.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce shipping method.
	*
	* @param userUuid the user uuid of this commerce shipping method
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceShippingMethod.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceShippingMethod> toCacheModel() {
		return _commerceShippingMethod.toCacheModel();
	}

	@Override
	public CommerceShippingMethod toEscapedModel() {
		return new CommerceShippingMethodWrapper(_commerceShippingMethod.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceShippingMethod.toString();
	}

	@Override
	public CommerceShippingMethod toUnescapedModel() {
		return new CommerceShippingMethodWrapper(_commerceShippingMethod.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceShippingMethod.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceShippingMethodWrapper)) {
			return false;
		}

		CommerceShippingMethodWrapper commerceShippingMethodWrapper = (CommerceShippingMethodWrapper)obj;

		if (Objects.equals(_commerceShippingMethod,
					commerceShippingMethodWrapper._commerceShippingMethod)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceShippingMethod getWrappedModel() {
		return _commerceShippingMethod;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceShippingMethod.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceShippingMethod.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceShippingMethod.resetOriginalValues();
	}

	private final CommerceShippingMethod _commerceShippingMethod;
}