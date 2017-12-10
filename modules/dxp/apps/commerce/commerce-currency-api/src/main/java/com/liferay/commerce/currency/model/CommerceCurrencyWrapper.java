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

package com.liferay.commerce.currency.model;

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
 * This class is a wrapper for {@link CommerceCurrency}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrency
 * @generated
 */
@ProviderType
public class CommerceCurrencyWrapper implements CommerceCurrency,
	ModelWrapper<CommerceCurrency> {
	public CommerceCurrencyWrapper(CommerceCurrency commerceCurrency) {
		_commerceCurrency = commerceCurrency;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceCurrency.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceCurrency.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceCurrencyId", getCommerceCurrencyId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("code", getCode());
		attributes.put("name", getName());
		attributes.put("rate", getRate());
		attributes.put("roundingType", getRoundingType());
		attributes.put("primary", getPrimary());
		attributes.put("priority", getPriority());
		attributes.put("active", getActive());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceCurrencyId = (Long)attributes.get("commerceCurrencyId");

		if (commerceCurrencyId != null) {
			setCommerceCurrencyId(commerceCurrencyId);
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

		String code = (String)attributes.get("code");

		if (code != null) {
			setCode(code);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Double rate = (Double)attributes.get("rate");

		if (rate != null) {
			setRate(rate);
		}

		String roundingType = (String)attributes.get("roundingType");

		if (roundingType != null) {
			setRoundingType(roundingType);
		}

		Boolean primary = (Boolean)attributes.get("primary");

		if (primary != null) {
			setPrimary(primary);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceCurrencyWrapper((CommerceCurrency)_commerceCurrency.clone());
	}

	@Override
	public int compareTo(CommerceCurrency commerceCurrency) {
		return _commerceCurrency.compareTo(commerceCurrency);
	}

	/**
	* Returns the active of this commerce currency.
	*
	* @return the active of this commerce currency
	*/
	@Override
	public boolean getActive() {
		return _commerceCurrency.getActive();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceCurrency.getAvailableLanguageIds();
	}

	/**
	* Returns the code of this commerce currency.
	*
	* @return the code of this commerce currency
	*/
	@Override
	public java.lang.String getCode() {
		return _commerceCurrency.getCode();
	}

	/**
	* Returns the commerce currency ID of this commerce currency.
	*
	* @return the commerce currency ID of this commerce currency
	*/
	@Override
	public long getCommerceCurrencyId() {
		return _commerceCurrency.getCommerceCurrencyId();
	}

	/**
	* Returns the company ID of this commerce currency.
	*
	* @return the company ID of this commerce currency
	*/
	@Override
	public long getCompanyId() {
		return _commerceCurrency.getCompanyId();
	}

	/**
	* Returns the create date of this commerce currency.
	*
	* @return the create date of this commerce currency
	*/
	@Override
	public Date getCreateDate() {
		return _commerceCurrency.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceCurrency.getDefaultLanguageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceCurrency.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce currency.
	*
	* @return the group ID of this commerce currency
	*/
	@Override
	public long getGroupId() {
		return _commerceCurrency.getGroupId();
	}

	/**
	* Returns the last publish date of this commerce currency.
	*
	* @return the last publish date of this commerce currency
	*/
	@Override
	public Date getLastPublishDate() {
		return _commerceCurrency.getLastPublishDate();
	}

	/**
	* Returns the modified date of this commerce currency.
	*
	* @return the modified date of this commerce currency
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceCurrency.getModifiedDate();
	}

	/**
	* Returns the name of this commerce currency.
	*
	* @return the name of this commerce currency
	*/
	@Override
	public java.lang.String getName() {
		return _commerceCurrency.getName();
	}

	/**
	* Returns the localized name of this commerce currency in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this commerce currency
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _commerceCurrency.getName(locale);
	}

	/**
	* Returns the localized name of this commerce currency in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce currency. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _commerceCurrency.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this commerce currency in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this commerce currency
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _commerceCurrency.getName(languageId);
	}

	/**
	* Returns the localized name of this commerce currency in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce currency
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _commerceCurrency.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _commerceCurrency.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _commerceCurrency.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this commerce currency.
	*
	* @return the locales and localized names of this commerce currency
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _commerceCurrency.getNameMap();
	}

	/**
	* Returns the primary of this commerce currency.
	*
	* @return the primary of this commerce currency
	*/
	@Override
	public boolean getPrimary() {
		return _commerceCurrency.getPrimary();
	}

	/**
	* Returns the primary key of this commerce currency.
	*
	* @return the primary key of this commerce currency
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceCurrency.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceCurrency.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this commerce currency.
	*
	* @return the priority of this commerce currency
	*/
	@Override
	public double getPriority() {
		return _commerceCurrency.getPriority();
	}

	/**
	* Returns the rate of this commerce currency.
	*
	* @return the rate of this commerce currency
	*/
	@Override
	public double getRate() {
		return _commerceCurrency.getRate();
	}

	/**
	* Returns the rounding type of this commerce currency.
	*
	* @return the rounding type of this commerce currency
	*/
	@Override
	public java.lang.String getRoundingType() {
		return _commerceCurrency.getRoundingType();
	}

	/**
	* Returns the user ID of this commerce currency.
	*
	* @return the user ID of this commerce currency
	*/
	@Override
	public long getUserId() {
		return _commerceCurrency.getUserId();
	}

	/**
	* Returns the user name of this commerce currency.
	*
	* @return the user name of this commerce currency
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceCurrency.getUserName();
	}

	/**
	* Returns the user uuid of this commerce currency.
	*
	* @return the user uuid of this commerce currency
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceCurrency.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce currency.
	*
	* @return the uuid of this commerce currency
	*/
	@Override
	public java.lang.String getUuid() {
		return _commerceCurrency.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceCurrency.hashCode();
	}

	/**
	* Returns <code>true</code> if this commerce currency is active.
	*
	* @return <code>true</code> if this commerce currency is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _commerceCurrency.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceCurrency.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceCurrency.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceCurrency.isNew();
	}

	/**
	* Returns <code>true</code> if this commerce currency is primary.
	*
	* @return <code>true</code> if this commerce currency is primary; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrimary() {
		return _commerceCurrency.isPrimary();
	}

	@Override
	public void persist() {
		_commerceCurrency.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceCurrency.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceCurrency.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets whether this commerce currency is active.
	*
	* @param active the active of this commerce currency
	*/
	@Override
	public void setActive(boolean active) {
		_commerceCurrency.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceCurrency.setCachedModel(cachedModel);
	}

	/**
	* Sets the code of this commerce currency.
	*
	* @param code the code of this commerce currency
	*/
	@Override
	public void setCode(java.lang.String code) {
		_commerceCurrency.setCode(code);
	}

	/**
	* Sets the commerce currency ID of this commerce currency.
	*
	* @param commerceCurrencyId the commerce currency ID of this commerce currency
	*/
	@Override
	public void setCommerceCurrencyId(long commerceCurrencyId) {
		_commerceCurrency.setCommerceCurrencyId(commerceCurrencyId);
	}

	/**
	* Sets the company ID of this commerce currency.
	*
	* @param companyId the company ID of this commerce currency
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceCurrency.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce currency.
	*
	* @param createDate the create date of this commerce currency
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceCurrency.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceCurrency.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceCurrency.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceCurrency.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce currency.
	*
	* @param groupId the group ID of this commerce currency
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceCurrency.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this commerce currency.
	*
	* @param lastPublishDate the last publish date of this commerce currency
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commerceCurrency.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this commerce currency.
	*
	* @param modifiedDate the modified date of this commerce currency
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceCurrency.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce currency.
	*
	* @param name the name of this commerce currency
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceCurrency.setName(name);
	}

	/**
	* Sets the localized name of this commerce currency in the language.
	*
	* @param name the localized name of this commerce currency
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_commerceCurrency.setName(name, locale);
	}

	/**
	* Sets the localized name of this commerce currency in the language, and sets the default locale.
	*
	* @param name the localized name of this commerce currency
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceCurrency.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_commerceCurrency.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this commerce currency from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this commerce currency
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_commerceCurrency.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this commerce currency from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this commerce currency
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_commerceCurrency.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_commerceCurrency.setNew(n);
	}

	/**
	* Sets whether this commerce currency is primary.
	*
	* @param primary the primary of this commerce currency
	*/
	@Override
	public void setPrimary(boolean primary) {
		_commerceCurrency.setPrimary(primary);
	}

	/**
	* Sets the primary key of this commerce currency.
	*
	* @param primaryKey the primary key of this commerce currency
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceCurrency.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceCurrency.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce currency.
	*
	* @param priority the priority of this commerce currency
	*/
	@Override
	public void setPriority(double priority) {
		_commerceCurrency.setPriority(priority);
	}

	/**
	* Sets the rate of this commerce currency.
	*
	* @param rate the rate of this commerce currency
	*/
	@Override
	public void setRate(double rate) {
		_commerceCurrency.setRate(rate);
	}

	/**
	* Sets the rounding type of this commerce currency.
	*
	* @param roundingType the rounding type of this commerce currency
	*/
	@Override
	public void setRoundingType(java.lang.String roundingType) {
		_commerceCurrency.setRoundingType(roundingType);
	}

	/**
	* Sets the user ID of this commerce currency.
	*
	* @param userId the user ID of this commerce currency
	*/
	@Override
	public void setUserId(long userId) {
		_commerceCurrency.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce currency.
	*
	* @param userName the user name of this commerce currency
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceCurrency.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce currency.
	*
	* @param userUuid the user uuid of this commerce currency
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceCurrency.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce currency.
	*
	* @param uuid the uuid of this commerce currency
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_commerceCurrency.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceCurrency> toCacheModel() {
		return _commerceCurrency.toCacheModel();
	}

	@Override
	public CommerceCurrency toEscapedModel() {
		return new CommerceCurrencyWrapper(_commerceCurrency.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceCurrency.toString();
	}

	@Override
	public CommerceCurrency toUnescapedModel() {
		return new CommerceCurrencyWrapper(_commerceCurrency.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceCurrency.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCurrencyWrapper)) {
			return false;
		}

		CommerceCurrencyWrapper commerceCurrencyWrapper = (CommerceCurrencyWrapper)obj;

		if (Objects.equals(_commerceCurrency,
					commerceCurrencyWrapper._commerceCurrency)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceCurrency.getStagedModelType();
	}

	@Override
	public CommerceCurrency getWrappedModel() {
		return _commerceCurrency;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceCurrency.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceCurrency.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceCurrency.resetOriginalValues();
	}

	private final CommerceCurrency _commerceCurrency;
}