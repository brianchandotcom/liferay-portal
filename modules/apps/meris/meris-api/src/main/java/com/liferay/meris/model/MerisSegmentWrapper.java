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

package com.liferay.meris.model;

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
 * This class is a wrapper for {@link MerisSegment}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see MerisSegment
 * @generated
 */
@ProviderType
public class MerisSegmentWrapper implements MerisSegment,
	ModelWrapper<MerisSegment> {
	public MerisSegmentWrapper(MerisSegment merisSegment) {
		_merisSegment = merisSegment;
	}

	@Override
	public Class<?> getModelClass() {
		return MerisSegment.class;
	}

	@Override
	public String getModelClassName() {
		return MerisSegment.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("merisSegmentId", getMerisSegmentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("key", getKey());
		attributes.put("active", isActive());
		attributes.put("type", getType());
		attributes.put("criteria", getCriteria());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long merisSegmentId = (Long)attributes.get("merisSegmentId");

		if (merisSegmentId != null) {
			setMerisSegmentId(merisSegmentId);
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

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String criteria = (String)attributes.get("criteria");

		if (criteria != null) {
			setCriteria(criteria);
		}
	}

	@Override
	public Object clone() {
		return new MerisSegmentWrapper((MerisSegment)_merisSegment.clone());
	}

	@Override
	public int compareTo(MerisSegment merisSegment) {
		return _merisSegment.compareTo(merisSegment);
	}

	/**
	* Returns the active of this meris segment.
	*
	* @return the active of this meris segment
	*/
	@Override
	public boolean getActive() {
		return _merisSegment.getActive();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _merisSegment.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this meris segment.
	*
	* @return the company ID of this meris segment
	*/
	@Override
	public long getCompanyId() {
		return _merisSegment.getCompanyId();
	}

	/**
	* Returns the create date of this meris segment.
	*
	* @return the create date of this meris segment
	*/
	@Override
	public Date getCreateDate() {
		return _merisSegment.getCreateDate();
	}

	/**
	* Returns the criteria of this meris segment.
	*
	* @return the criteria of this meris segment
	*/
	@Override
	public String getCriteria() {
		return _merisSegment.getCriteria();
	}

	@Override
	public String getDefaultLanguageId() {
		return _merisSegment.getDefaultLanguageId();
	}

	/**
	* Returns the description of this meris segment.
	*
	* @return the description of this meris segment
	*/
	@Override
	public String getDescription() {
		return _merisSegment.getDescription();
	}

	/**
	* Returns the localized description of this meris segment in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this meris segment
	*/
	@Override
	public String getDescription(java.util.Locale locale) {
		return _merisSegment.getDescription(locale);
	}

	/**
	* Returns the localized description of this meris segment in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this meris segment. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return _merisSegment.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this meris segment in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this meris segment
	*/
	@Override
	public String getDescription(String languageId) {
		return _merisSegment.getDescription(languageId);
	}

	/**
	* Returns the localized description of this meris segment in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this meris segment
	*/
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _merisSegment.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _merisSegment.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return _merisSegment.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this meris segment.
	*
	* @return the locales and localized descriptions of this meris segment
	*/
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return _merisSegment.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _merisSegment.getExpandoBridge();
	}

	/**
	* Returns the group ID of this meris segment.
	*
	* @return the group ID of this meris segment
	*/
	@Override
	public long getGroupId() {
		return _merisSegment.getGroupId();
	}

	/**
	* Returns the key of this meris segment.
	*
	* @return the key of this meris segment
	*/
	@Override
	public String getKey() {
		return _merisSegment.getKey();
	}

	/**
	* Returns the meris segment ID of this meris segment.
	*
	* @return the meris segment ID of this meris segment
	*/
	@Override
	public long getMerisSegmentId() {
		return _merisSegment.getMerisSegmentId();
	}

	/**
	* Returns the modified date of this meris segment.
	*
	* @return the modified date of this meris segment
	*/
	@Override
	public Date getModifiedDate() {
		return _merisSegment.getModifiedDate();
	}

	/**
	* Returns the name of this meris segment.
	*
	* @return the name of this meris segment
	*/
	@Override
	public String getName() {
		return _merisSegment.getName();
	}

	/**
	* Returns the localized name of this meris segment in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this meris segment
	*/
	@Override
	public String getName(java.util.Locale locale) {
		return _merisSegment.getName(locale);
	}

	/**
	* Returns the localized name of this meris segment in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this meris segment. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return _merisSegment.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this meris segment in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this meris segment
	*/
	@Override
	public String getName(String languageId) {
		return _merisSegment.getName(languageId);
	}

	/**
	* Returns the localized name of this meris segment in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this meris segment
	*/
	@Override
	public String getName(String languageId, boolean useDefault) {
		return _merisSegment.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _merisSegment.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return _merisSegment.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this meris segment.
	*
	* @return the locales and localized names of this meris segment
	*/
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return _merisSegment.getNameMap();
	}

	/**
	* Returns the primary key of this meris segment.
	*
	* @return the primary key of this meris segment
	*/
	@Override
	public long getPrimaryKey() {
		return _merisSegment.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _merisSegment.getPrimaryKeyObj();
	}

	/**
	* Returns the type of this meris segment.
	*
	* @return the type of this meris segment
	*/
	@Override
	public String getType() {
		return _merisSegment.getType();
	}

	/**
	* Returns the user ID of this meris segment.
	*
	* @return the user ID of this meris segment
	*/
	@Override
	public long getUserId() {
		return _merisSegment.getUserId();
	}

	/**
	* Returns the user name of this meris segment.
	*
	* @return the user name of this meris segment
	*/
	@Override
	public String getUserName() {
		return _merisSegment.getUserName();
	}

	/**
	* Returns the user uuid of this meris segment.
	*
	* @return the user uuid of this meris segment
	*/
	@Override
	public String getUserUuid() {
		return _merisSegment.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _merisSegment.hashCode();
	}

	/**
	* Returns <code>true</code> if this meris segment is active.
	*
	* @return <code>true</code> if this meris segment is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _merisSegment.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _merisSegment.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _merisSegment.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _merisSegment.isNew();
	}

	@Override
	public void persist() {
		_merisSegment.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_merisSegment.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_merisSegment.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets whether this meris segment is active.
	*
	* @param active the active of this meris segment
	*/
	@Override
	public void setActive(boolean active) {
		_merisSegment.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_merisSegment.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this meris segment.
	*
	* @param companyId the company ID of this meris segment
	*/
	@Override
	public void setCompanyId(long companyId) {
		_merisSegment.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this meris segment.
	*
	* @param createDate the create date of this meris segment
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_merisSegment.setCreateDate(createDate);
	}

	/**
	* Sets the criteria of this meris segment.
	*
	* @param criteria the criteria of this meris segment
	*/
	@Override
	public void setCriteria(String criteria) {
		_merisSegment.setCriteria(criteria);
	}

	/**
	* Sets the description of this meris segment.
	*
	* @param description the description of this meris segment
	*/
	@Override
	public void setDescription(String description) {
		_merisSegment.setDescription(description);
	}

	/**
	* Sets the localized description of this meris segment in the language.
	*
	* @param description the localized description of this meris segment
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		_merisSegment.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this meris segment in the language, and sets the default locale.
	*
	* @param description the localized description of this meris segment
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_merisSegment.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		_merisSegment.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this meris segment from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this meris segment
	*/
	@Override
	public void setDescriptionMap(Map<java.util.Locale, String> descriptionMap) {
		_merisSegment.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this meris segment from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this meris segment
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {
		_merisSegment.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_merisSegment.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_merisSegment.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_merisSegment.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this meris segment.
	*
	* @param groupId the group ID of this meris segment
	*/
	@Override
	public void setGroupId(long groupId) {
		_merisSegment.setGroupId(groupId);
	}

	/**
	* Sets the key of this meris segment.
	*
	* @param key the key of this meris segment
	*/
	@Override
	public void setKey(String key) {
		_merisSegment.setKey(key);
	}

	/**
	* Sets the meris segment ID of this meris segment.
	*
	* @param merisSegmentId the meris segment ID of this meris segment
	*/
	@Override
	public void setMerisSegmentId(long merisSegmentId) {
		_merisSegment.setMerisSegmentId(merisSegmentId);
	}

	/**
	* Sets the modified date of this meris segment.
	*
	* @param modifiedDate the modified date of this meris segment
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_merisSegment.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this meris segment.
	*
	* @param name the name of this meris segment
	*/
	@Override
	public void setName(String name) {
		_merisSegment.setName(name);
	}

	/**
	* Sets the localized name of this meris segment in the language.
	*
	* @param name the localized name of this meris segment
	* @param locale the locale of the language
	*/
	@Override
	public void setName(String name, java.util.Locale locale) {
		_merisSegment.setName(name, locale);
	}

	/**
	* Sets the localized name of this meris segment in the language, and sets the default locale.
	*
	* @param name the localized name of this meris segment
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_merisSegment.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_merisSegment.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this meris segment from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this meris segment
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		_merisSegment.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this meris segment from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this meris segment
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap,
		java.util.Locale defaultLocale) {
		_merisSegment.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_merisSegment.setNew(n);
	}

	/**
	* Sets the primary key of this meris segment.
	*
	* @param primaryKey the primary key of this meris segment
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_merisSegment.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_merisSegment.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the type of this meris segment.
	*
	* @param type the type of this meris segment
	*/
	@Override
	public void setType(String type) {
		_merisSegment.setType(type);
	}

	/**
	* Sets the user ID of this meris segment.
	*
	* @param userId the user ID of this meris segment
	*/
	@Override
	public void setUserId(long userId) {
		_merisSegment.setUserId(userId);
	}

	/**
	* Sets the user name of this meris segment.
	*
	* @param userName the user name of this meris segment
	*/
	@Override
	public void setUserName(String userName) {
		_merisSegment.setUserName(userName);
	}

	/**
	* Sets the user uuid of this meris segment.
	*
	* @param userUuid the user uuid of this meris segment
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_merisSegment.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<MerisSegment> toCacheModel() {
		return _merisSegment.toCacheModel();
	}

	@Override
	public MerisSegment toEscapedModel() {
		return new MerisSegmentWrapper(_merisSegment.toEscapedModel());
	}

	@Override
	public String toString() {
		return _merisSegment.toString();
	}

	@Override
	public MerisSegment toUnescapedModel() {
		return new MerisSegmentWrapper(_merisSegment.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _merisSegment.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MerisSegmentWrapper)) {
			return false;
		}

		MerisSegmentWrapper merisSegmentWrapper = (MerisSegmentWrapper)obj;

		if (Objects.equals(_merisSegment, merisSegmentWrapper._merisSegment)) {
			return true;
		}

		return false;
	}

	@Override
	public MerisSegment getWrappedModel() {
		return _merisSegment;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _merisSegment.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _merisSegment.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_merisSegment.resetOriginalValues();
	}

	private final MerisSegment _merisSegment;
}