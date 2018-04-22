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
 * This class is a wrapper for {@link CPMeasurementUnit}.
 * </p>
 *
 * @author Marco Leo
 * @see CPMeasurementUnit
 * @generated
 */
@ProviderType
public class CPMeasurementUnitWrapper implements CPMeasurementUnit,
	ModelWrapper<CPMeasurementUnit> {
	public CPMeasurementUnitWrapper(CPMeasurementUnit cpMeasurementUnit) {
		_cpMeasurementUnit = cpMeasurementUnit;
	}

	@Override
	public Class<?> getModelClass() {
		return CPMeasurementUnit.class;
	}

	@Override
	public String getModelClassName() {
		return CPMeasurementUnit.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPMeasurementUnitId", getCPMeasurementUnitId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("key", getKey());
		attributes.put("rate", getRate());
		attributes.put("primary", isPrimary());
		attributes.put("priority", getPriority());
		attributes.put("type", getType());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPMeasurementUnitId = (Long)attributes.get("CPMeasurementUnitId");

		if (CPMeasurementUnitId != null) {
			setCPMeasurementUnitId(CPMeasurementUnitId);
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

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		Double rate = (Double)attributes.get("rate");

		if (rate != null) {
			setRate(rate);
		}

		Boolean primary = (Boolean)attributes.get("primary");

		if (primary != null) {
			setPrimary(primary);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CPMeasurementUnitWrapper((CPMeasurementUnit)_cpMeasurementUnit.clone());
	}

	@Override
	public int compareTo(CPMeasurementUnit cpMeasurementUnit) {
		return _cpMeasurementUnit.compareTo(cpMeasurementUnit);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _cpMeasurementUnit.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this cp measurement unit.
	*
	* @return the company ID of this cp measurement unit
	*/
	@Override
	public long getCompanyId() {
		return _cpMeasurementUnit.getCompanyId();
	}

	/**
	* Returns the cp measurement unit ID of this cp measurement unit.
	*
	* @return the cp measurement unit ID of this cp measurement unit
	*/
	@Override
	public long getCPMeasurementUnitId() {
		return _cpMeasurementUnit.getCPMeasurementUnitId();
	}

	/**
	* Returns the create date of this cp measurement unit.
	*
	* @return the create date of this cp measurement unit
	*/
	@Override
	public Date getCreateDate() {
		return _cpMeasurementUnit.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _cpMeasurementUnit.getDefaultLanguageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpMeasurementUnit.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp measurement unit.
	*
	* @return the group ID of this cp measurement unit
	*/
	@Override
	public long getGroupId() {
		return _cpMeasurementUnit.getGroupId();
	}

	/**
	* Returns the key of this cp measurement unit.
	*
	* @return the key of this cp measurement unit
	*/
	@Override
	public java.lang.String getKey() {
		return _cpMeasurementUnit.getKey();
	}

	/**
	* Returns the last publish date of this cp measurement unit.
	*
	* @return the last publish date of this cp measurement unit
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpMeasurementUnit.getLastPublishDate();
	}

	/**
	* Returns the modified date of this cp measurement unit.
	*
	* @return the modified date of this cp measurement unit
	*/
	@Override
	public Date getModifiedDate() {
		return _cpMeasurementUnit.getModifiedDate();
	}

	/**
	* Returns the name of this cp measurement unit.
	*
	* @return the name of this cp measurement unit
	*/
	@Override
	public java.lang.String getName() {
		return _cpMeasurementUnit.getName();
	}

	/**
	* Returns the localized name of this cp measurement unit in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this cp measurement unit
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _cpMeasurementUnit.getName(locale);
	}

	/**
	* Returns the localized name of this cp measurement unit in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this cp measurement unit. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _cpMeasurementUnit.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this cp measurement unit in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this cp measurement unit
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _cpMeasurementUnit.getName(languageId);
	}

	/**
	* Returns the localized name of this cp measurement unit in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this cp measurement unit
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _cpMeasurementUnit.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _cpMeasurementUnit.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _cpMeasurementUnit.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this cp measurement unit.
	*
	* @return the locales and localized names of this cp measurement unit
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _cpMeasurementUnit.getNameMap();
	}

	/**
	* Returns the primary of this cp measurement unit.
	*
	* @return the primary of this cp measurement unit
	*/
	@Override
	public boolean getPrimary() {
		return _cpMeasurementUnit.getPrimary();
	}

	/**
	* Returns the primary key of this cp measurement unit.
	*
	* @return the primary key of this cp measurement unit
	*/
	@Override
	public long getPrimaryKey() {
		return _cpMeasurementUnit.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpMeasurementUnit.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this cp measurement unit.
	*
	* @return the priority of this cp measurement unit
	*/
	@Override
	public double getPriority() {
		return _cpMeasurementUnit.getPriority();
	}

	/**
	* Returns the rate of this cp measurement unit.
	*
	* @return the rate of this cp measurement unit
	*/
	@Override
	public double getRate() {
		return _cpMeasurementUnit.getRate();
	}

	/**
	* Returns the type of this cp measurement unit.
	*
	* @return the type of this cp measurement unit
	*/
	@Override
	public int getType() {
		return _cpMeasurementUnit.getType();
	}

	/**
	* Returns the user ID of this cp measurement unit.
	*
	* @return the user ID of this cp measurement unit
	*/
	@Override
	public long getUserId() {
		return _cpMeasurementUnit.getUserId();
	}

	/**
	* Returns the user name of this cp measurement unit.
	*
	* @return the user name of this cp measurement unit
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpMeasurementUnit.getUserName();
	}

	/**
	* Returns the user uuid of this cp measurement unit.
	*
	* @return the user uuid of this cp measurement unit
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpMeasurementUnit.getUserUuid();
	}

	/**
	* Returns the uuid of this cp measurement unit.
	*
	* @return the uuid of this cp measurement unit
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpMeasurementUnit.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpMeasurementUnit.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpMeasurementUnit.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpMeasurementUnit.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpMeasurementUnit.isNew();
	}

	/**
	* Returns <code>true</code> if this cp measurement unit is primary.
	*
	* @return <code>true</code> if this cp measurement unit is primary; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrimary() {
		return _cpMeasurementUnit.isPrimary();
	}

	@Override
	public void persist() {
		_cpMeasurementUnit.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpMeasurementUnit.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpMeasurementUnit.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpMeasurementUnit.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp measurement unit.
	*
	* @param companyId the company ID of this cp measurement unit
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpMeasurementUnit.setCompanyId(companyId);
	}

	/**
	* Sets the cp measurement unit ID of this cp measurement unit.
	*
	* @param CPMeasurementUnitId the cp measurement unit ID of this cp measurement unit
	*/
	@Override
	public void setCPMeasurementUnitId(long CPMeasurementUnitId) {
		_cpMeasurementUnit.setCPMeasurementUnitId(CPMeasurementUnitId);
	}

	/**
	* Sets the create date of this cp measurement unit.
	*
	* @param createDate the create date of this cp measurement unit
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpMeasurementUnit.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpMeasurementUnit.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpMeasurementUnit.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpMeasurementUnit.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp measurement unit.
	*
	* @param groupId the group ID of this cp measurement unit
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpMeasurementUnit.setGroupId(groupId);
	}

	/**
	* Sets the key of this cp measurement unit.
	*
	* @param key the key of this cp measurement unit
	*/
	@Override
	public void setKey(java.lang.String key) {
		_cpMeasurementUnit.setKey(key);
	}

	/**
	* Sets the last publish date of this cp measurement unit.
	*
	* @param lastPublishDate the last publish date of this cp measurement unit
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpMeasurementUnit.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this cp measurement unit.
	*
	* @param modifiedDate the modified date of this cp measurement unit
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpMeasurementUnit.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this cp measurement unit.
	*
	* @param name the name of this cp measurement unit
	*/
	@Override
	public void setName(java.lang.String name) {
		_cpMeasurementUnit.setName(name);
	}

	/**
	* Sets the localized name of this cp measurement unit in the language.
	*
	* @param name the localized name of this cp measurement unit
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_cpMeasurementUnit.setName(name, locale);
	}

	/**
	* Sets the localized name of this cp measurement unit in the language, and sets the default locale.
	*
	* @param name the localized name of this cp measurement unit
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpMeasurementUnit.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_cpMeasurementUnit.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this cp measurement unit from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this cp measurement unit
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_cpMeasurementUnit.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this cp measurement unit from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this cp measurement unit
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_cpMeasurementUnit.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_cpMeasurementUnit.setNew(n);
	}

	/**
	* Sets whether this cp measurement unit is primary.
	*
	* @param primary the primary of this cp measurement unit
	*/
	@Override
	public void setPrimary(boolean primary) {
		_cpMeasurementUnit.setPrimary(primary);
	}

	/**
	* Sets the primary key of this cp measurement unit.
	*
	* @param primaryKey the primary key of this cp measurement unit
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpMeasurementUnit.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpMeasurementUnit.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this cp measurement unit.
	*
	* @param priority the priority of this cp measurement unit
	*/
	@Override
	public void setPriority(double priority) {
		_cpMeasurementUnit.setPriority(priority);
	}

	/**
	* Sets the rate of this cp measurement unit.
	*
	* @param rate the rate of this cp measurement unit
	*/
	@Override
	public void setRate(double rate) {
		_cpMeasurementUnit.setRate(rate);
	}

	/**
	* Sets the type of this cp measurement unit.
	*
	* @param type the type of this cp measurement unit
	*/
	@Override
	public void setType(int type) {
		_cpMeasurementUnit.setType(type);
	}

	/**
	* Sets the user ID of this cp measurement unit.
	*
	* @param userId the user ID of this cp measurement unit
	*/
	@Override
	public void setUserId(long userId) {
		_cpMeasurementUnit.setUserId(userId);
	}

	/**
	* Sets the user name of this cp measurement unit.
	*
	* @param userName the user name of this cp measurement unit
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpMeasurementUnit.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp measurement unit.
	*
	* @param userUuid the user uuid of this cp measurement unit
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpMeasurementUnit.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp measurement unit.
	*
	* @param uuid the uuid of this cp measurement unit
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpMeasurementUnit.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPMeasurementUnit> toCacheModel() {
		return _cpMeasurementUnit.toCacheModel();
	}

	@Override
	public CPMeasurementUnit toEscapedModel() {
		return new CPMeasurementUnitWrapper(_cpMeasurementUnit.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _cpMeasurementUnit.toString();
	}

	@Override
	public CPMeasurementUnit toUnescapedModel() {
		return new CPMeasurementUnitWrapper(_cpMeasurementUnit.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpMeasurementUnit.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPMeasurementUnitWrapper)) {
			return false;
		}

		CPMeasurementUnitWrapper cpMeasurementUnitWrapper = (CPMeasurementUnitWrapper)obj;

		if (Objects.equals(_cpMeasurementUnit,
					cpMeasurementUnitWrapper._cpMeasurementUnit)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpMeasurementUnit.getStagedModelType();
	}

	@Override
	public CPMeasurementUnit getWrappedModel() {
		return _cpMeasurementUnit;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpMeasurementUnit.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpMeasurementUnit.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpMeasurementUnit.resetOriginalValues();
	}

	private final CPMeasurementUnit _cpMeasurementUnit;
}