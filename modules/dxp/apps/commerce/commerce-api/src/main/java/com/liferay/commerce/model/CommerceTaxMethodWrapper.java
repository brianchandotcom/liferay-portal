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
 * This class is a wrapper for {@link CommerceTaxMethod}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxMethod
 * @generated
 */
@ProviderType
public class CommerceTaxMethodWrapper implements CommerceTaxMethod,
	ModelWrapper<CommerceTaxMethod> {
	public CommerceTaxMethodWrapper(CommerceTaxMethod commerceTaxMethod) {
		_commerceTaxMethod = commerceTaxMethod;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceTaxMethod.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceTaxMethod.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceTaxMethodId", getCommerceTaxMethodId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("engineKey", getEngineKey());
		attributes.put("percentage", getPercentage());
		attributes.put("active", getActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceTaxMethodId = (Long)attributes.get("commerceTaxMethodId");

		if (commerceTaxMethodId != null) {
			setCommerceTaxMethodId(commerceTaxMethodId);
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

		String engineKey = (String)attributes.get("engineKey");

		if (engineKey != null) {
			setEngineKey(engineKey);
		}

		Boolean percentage = (Boolean)attributes.get("percentage");

		if (percentage != null) {
			setPercentage(percentage);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceTaxMethodWrapper((CommerceTaxMethod)_commerceTaxMethod.clone());
	}

	@Override
	public int compareTo(CommerceTaxMethod commerceTaxMethod) {
		return _commerceTaxMethod.compareTo(commerceTaxMethod);
	}

	/**
	* Returns the active of this commerce tax method.
	*
	* @return the active of this commerce tax method
	*/
	@Override
	public boolean getActive() {
		return _commerceTaxMethod.getActive();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceTaxMethod.getAvailableLanguageIds();
	}

	/**
	* Returns the commerce tax method ID of this commerce tax method.
	*
	* @return the commerce tax method ID of this commerce tax method
	*/
	@Override
	public long getCommerceTaxMethodId() {
		return _commerceTaxMethod.getCommerceTaxMethodId();
	}

	/**
	* Returns the company ID of this commerce tax method.
	*
	* @return the company ID of this commerce tax method
	*/
	@Override
	public long getCompanyId() {
		return _commerceTaxMethod.getCompanyId();
	}

	/**
	* Returns the create date of this commerce tax method.
	*
	* @return the create date of this commerce tax method
	*/
	@Override
	public Date getCreateDate() {
		return _commerceTaxMethod.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceTaxMethod.getDefaultLanguageId();
	}

	/**
	* Returns the description of this commerce tax method.
	*
	* @return the description of this commerce tax method
	*/
	@Override
	public java.lang.String getDescription() {
		return _commerceTaxMethod.getDescription();
	}

	/**
	* Returns the localized description of this commerce tax method in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this commerce tax method
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _commerceTaxMethod.getDescription(locale);
	}

	/**
	* Returns the localized description of this commerce tax method in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce tax method. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _commerceTaxMethod.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this commerce tax method in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this commerce tax method
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _commerceTaxMethod.getDescription(languageId);
	}

	/**
	* Returns the localized description of this commerce tax method in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce tax method
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _commerceTaxMethod.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _commerceTaxMethod.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _commerceTaxMethod.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this commerce tax method.
	*
	* @return the locales and localized descriptions of this commerce tax method
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _commerceTaxMethod.getDescriptionMap();
	}

	/**
	* Returns the engine key of this commerce tax method.
	*
	* @return the engine key of this commerce tax method
	*/
	@Override
	public java.lang.String getEngineKey() {
		return _commerceTaxMethod.getEngineKey();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceTaxMethod.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce tax method.
	*
	* @return the group ID of this commerce tax method
	*/
	@Override
	public long getGroupId() {
		return _commerceTaxMethod.getGroupId();
	}

	/**
	* Returns the modified date of this commerce tax method.
	*
	* @return the modified date of this commerce tax method
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceTaxMethod.getModifiedDate();
	}

	/**
	* Returns the name of this commerce tax method.
	*
	* @return the name of this commerce tax method
	*/
	@Override
	public java.lang.String getName() {
		return _commerceTaxMethod.getName();
	}

	/**
	* Returns the localized name of this commerce tax method in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this commerce tax method
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _commerceTaxMethod.getName(locale);
	}

	/**
	* Returns the localized name of this commerce tax method in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce tax method. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _commerceTaxMethod.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this commerce tax method in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this commerce tax method
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _commerceTaxMethod.getName(languageId);
	}

	/**
	* Returns the localized name of this commerce tax method in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce tax method
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _commerceTaxMethod.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _commerceTaxMethod.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _commerceTaxMethod.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this commerce tax method.
	*
	* @return the locales and localized names of this commerce tax method
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _commerceTaxMethod.getNameMap();
	}

	/**
	* Returns the percentage of this commerce tax method.
	*
	* @return the percentage of this commerce tax method
	*/
	@Override
	public boolean getPercentage() {
		return _commerceTaxMethod.getPercentage();
	}

	/**
	* Returns the primary key of this commerce tax method.
	*
	* @return the primary key of this commerce tax method
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceTaxMethod.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceTaxMethod.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce tax method.
	*
	* @return the user ID of this commerce tax method
	*/
	@Override
	public long getUserId() {
		return _commerceTaxMethod.getUserId();
	}

	/**
	* Returns the user name of this commerce tax method.
	*
	* @return the user name of this commerce tax method
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceTaxMethod.getUserName();
	}

	/**
	* Returns the user uuid of this commerce tax method.
	*
	* @return the user uuid of this commerce tax method
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceTaxMethod.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceTaxMethod.hashCode();
	}

	/**
	* Returns <code>true</code> if this commerce tax method is active.
	*
	* @return <code>true</code> if this commerce tax method is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _commerceTaxMethod.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceTaxMethod.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceTaxMethod.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceTaxMethod.isNew();
	}

	/**
	* Returns <code>true</code> if this commerce tax method is percentage.
	*
	* @return <code>true</code> if this commerce tax method is percentage; <code>false</code> otherwise
	*/
	@Override
	public boolean isPercentage() {
		return _commerceTaxMethod.isPercentage();
	}

	@Override
	public void persist() {
		_commerceTaxMethod.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceTaxMethod.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceTaxMethod.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets whether this commerce tax method is active.
	*
	* @param active the active of this commerce tax method
	*/
	@Override
	public void setActive(boolean active) {
		_commerceTaxMethod.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceTaxMethod.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce tax method ID of this commerce tax method.
	*
	* @param commerceTaxMethodId the commerce tax method ID of this commerce tax method
	*/
	@Override
	public void setCommerceTaxMethodId(long commerceTaxMethodId) {
		_commerceTaxMethod.setCommerceTaxMethodId(commerceTaxMethodId);
	}

	/**
	* Sets the company ID of this commerce tax method.
	*
	* @param companyId the company ID of this commerce tax method
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceTaxMethod.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce tax method.
	*
	* @param createDate the create date of this commerce tax method
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceTaxMethod.setCreateDate(createDate);
	}

	/**
	* Sets the description of this commerce tax method.
	*
	* @param description the description of this commerce tax method
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_commerceTaxMethod.setDescription(description);
	}

	/**
	* Sets the localized description of this commerce tax method in the language.
	*
	* @param description the localized description of this commerce tax method
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_commerceTaxMethod.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this commerce tax method in the language, and sets the default locale.
	*
	* @param description the localized description of this commerce tax method
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_commerceTaxMethod.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_commerceTaxMethod.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this commerce tax method from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce tax method
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_commerceTaxMethod.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this commerce tax method from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce tax method
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_commerceTaxMethod.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Sets the engine key of this commerce tax method.
	*
	* @param engineKey the engine key of this commerce tax method
	*/
	@Override
	public void setEngineKey(java.lang.String engineKey) {
		_commerceTaxMethod.setEngineKey(engineKey);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceTaxMethod.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceTaxMethod.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceTaxMethod.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce tax method.
	*
	* @param groupId the group ID of this commerce tax method
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceTaxMethod.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce tax method.
	*
	* @param modifiedDate the modified date of this commerce tax method
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceTaxMethod.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce tax method.
	*
	* @param name the name of this commerce tax method
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceTaxMethod.setName(name);
	}

	/**
	* Sets the localized name of this commerce tax method in the language.
	*
	* @param name the localized name of this commerce tax method
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_commerceTaxMethod.setName(name, locale);
	}

	/**
	* Sets the localized name of this commerce tax method in the language, and sets the default locale.
	*
	* @param name the localized name of this commerce tax method
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceTaxMethod.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_commerceTaxMethod.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this commerce tax method from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this commerce tax method
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_commerceTaxMethod.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this commerce tax method from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this commerce tax method
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_commerceTaxMethod.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_commerceTaxMethod.setNew(n);
	}

	/**
	* Sets whether this commerce tax method is percentage.
	*
	* @param percentage the percentage of this commerce tax method
	*/
	@Override
	public void setPercentage(boolean percentage) {
		_commerceTaxMethod.setPercentage(percentage);
	}

	/**
	* Sets the primary key of this commerce tax method.
	*
	* @param primaryKey the primary key of this commerce tax method
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceTaxMethod.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceTaxMethod.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce tax method.
	*
	* @param userId the user ID of this commerce tax method
	*/
	@Override
	public void setUserId(long userId) {
		_commerceTaxMethod.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce tax method.
	*
	* @param userName the user name of this commerce tax method
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceTaxMethod.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce tax method.
	*
	* @param userUuid the user uuid of this commerce tax method
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceTaxMethod.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceTaxMethod> toCacheModel() {
		return _commerceTaxMethod.toCacheModel();
	}

	@Override
	public CommerceTaxMethod toEscapedModel() {
		return new CommerceTaxMethodWrapper(_commerceTaxMethod.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceTaxMethod.toString();
	}

	@Override
	public CommerceTaxMethod toUnescapedModel() {
		return new CommerceTaxMethodWrapper(_commerceTaxMethod.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceTaxMethod.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTaxMethodWrapper)) {
			return false;
		}

		CommerceTaxMethodWrapper commerceTaxMethodWrapper = (CommerceTaxMethodWrapper)obj;

		if (Objects.equals(_commerceTaxMethod,
					commerceTaxMethodWrapper._commerceTaxMethod)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceTaxMethod getWrappedModel() {
		return _commerceTaxMethod;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceTaxMethod.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceTaxMethod.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceTaxMethod.resetOriginalValues();
	}

	private final CommerceTaxMethod _commerceTaxMethod;
}