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

package com.liferay.segments.model;

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
 * This class is a wrapper for {@link SegmentsEntry}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntry
 * @generated
 */
@ProviderType
public class SegmentsEntryWrapper implements SegmentsEntry,
	ModelWrapper<SegmentsEntry> {
	public SegmentsEntryWrapper(SegmentsEntry segmentsEntry) {
		_segmentsEntry = segmentsEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return SegmentsEntry.class;
	}

	@Override
	public String getModelClassName() {
		return SegmentsEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("segmentsEntryId", getSegmentsEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("active", isActive());
		attributes.put("criteria", getCriteria());
		attributes.put("key", getKey());
		attributes.put("source", getSource());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long segmentsEntryId = (Long)attributes.get("segmentsEntryId");

		if (segmentsEntryId != null) {
			setSegmentsEntryId(segmentsEntryId);
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

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String criteria = (String)attributes.get("criteria");

		if (criteria != null) {
			setCriteria(criteria);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		String source = (String)attributes.get("source");

		if (source != null) {
			setSource(source);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public Object clone() {
		return new SegmentsEntryWrapper((SegmentsEntry)_segmentsEntry.clone());
	}

	@Override
	public int compareTo(SegmentsEntry segmentsEntry) {
		return _segmentsEntry.compareTo(segmentsEntry);
	}

	/**
	* Returns the active of this segments entry.
	*
	* @return the active of this segments entry
	*/
	@Override
	public boolean getActive() {
		return _segmentsEntry.getActive();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _segmentsEntry.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this segments entry.
	*
	* @return the company ID of this segments entry
	*/
	@Override
	public long getCompanyId() {
		return _segmentsEntry.getCompanyId();
	}

	/**
	* Returns the create date of this segments entry.
	*
	* @return the create date of this segments entry
	*/
	@Override
	public Date getCreateDate() {
		return _segmentsEntry.getCreateDate();
	}

	/**
	* Returns the criteria of this segments entry.
	*
	* @return the criteria of this segments entry
	*/
	@Override
	public String getCriteria() {
		return _segmentsEntry.getCriteria();
	}

	@Override
	public com.liferay.segments.criteria.Criteria getCriteriaObj() {
		return _segmentsEntry.getCriteriaObj();
	}

	@Override
	public String getDefaultLanguageId() {
		return _segmentsEntry.getDefaultLanguageId();
	}

	/**
	* Returns the description of this segments entry.
	*
	* @return the description of this segments entry
	*/
	@Override
	public String getDescription() {
		return _segmentsEntry.getDescription();
	}

	/**
	* Returns the localized description of this segments entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this segments entry
	*/
	@Override
	public String getDescription(java.util.Locale locale) {
		return _segmentsEntry.getDescription(locale);
	}

	/**
	* Returns the localized description of this segments entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this segments entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return _segmentsEntry.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this segments entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this segments entry
	*/
	@Override
	public String getDescription(String languageId) {
		return _segmentsEntry.getDescription(languageId);
	}

	/**
	* Returns the localized description of this segments entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this segments entry
	*/
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _segmentsEntry.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _segmentsEntry.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return _segmentsEntry.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this segments entry.
	*
	* @return the locales and localized descriptions of this segments entry
	*/
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return _segmentsEntry.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _segmentsEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this segments entry.
	*
	* @return the group ID of this segments entry
	*/
	@Override
	public long getGroupId() {
		return _segmentsEntry.getGroupId();
	}

	/**
	* Returns the key of this segments entry.
	*
	* @return the key of this segments entry
	*/
	@Override
	public String getKey() {
		return _segmentsEntry.getKey();
	}

	/**
	* Returns the modified date of this segments entry.
	*
	* @return the modified date of this segments entry
	*/
	@Override
	public Date getModifiedDate() {
		return _segmentsEntry.getModifiedDate();
	}

	/**
	* Returns the name of this segments entry.
	*
	* @return the name of this segments entry
	*/
	@Override
	public String getName() {
		return _segmentsEntry.getName();
	}

	/**
	* Returns the localized name of this segments entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this segments entry
	*/
	@Override
	public String getName(java.util.Locale locale) {
		return _segmentsEntry.getName(locale);
	}

	/**
	* Returns the localized name of this segments entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this segments entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return _segmentsEntry.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this segments entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this segments entry
	*/
	@Override
	public String getName(String languageId) {
		return _segmentsEntry.getName(languageId);
	}

	/**
	* Returns the localized name of this segments entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this segments entry
	*/
	@Override
	public String getName(String languageId, boolean useDefault) {
		return _segmentsEntry.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _segmentsEntry.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return _segmentsEntry.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this segments entry.
	*
	* @return the locales and localized names of this segments entry
	*/
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return _segmentsEntry.getNameMap();
	}

	/**
	* Returns the primary key of this segments entry.
	*
	* @return the primary key of this segments entry
	*/
	@Override
	public long getPrimaryKey() {
		return _segmentsEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _segmentsEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the segments entry ID of this segments entry.
	*
	* @return the segments entry ID of this segments entry
	*/
	@Override
	public long getSegmentsEntryId() {
		return _segmentsEntry.getSegmentsEntryId();
	}

	/**
	* Returns the source of this segments entry.
	*
	* @return the source of this segments entry
	*/
	@Override
	public String getSource() {
		return _segmentsEntry.getSource();
	}

	/**
	* Returns the type of this segments entry.
	*
	* @return the type of this segments entry
	*/
	@Override
	public String getType() {
		return _segmentsEntry.getType();
	}

	/**
	* Returns the user ID of this segments entry.
	*
	* @return the user ID of this segments entry
	*/
	@Override
	public long getUserId() {
		return _segmentsEntry.getUserId();
	}

	/**
	* Returns the user name of this segments entry.
	*
	* @return the user name of this segments entry
	*/
	@Override
	public String getUserName() {
		return _segmentsEntry.getUserName();
	}

	/**
	* Returns the user uuid of this segments entry.
	*
	* @return the user uuid of this segments entry
	*/
	@Override
	public String getUserUuid() {
		return _segmentsEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _segmentsEntry.hashCode();
	}

	/**
	* Returns <code>true</code> if this segments entry is active.
	*
	* @return <code>true</code> if this segments entry is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _segmentsEntry.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _segmentsEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _segmentsEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _segmentsEntry.isNew();
	}

	@Override
	public void persist() {
		_segmentsEntry.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_segmentsEntry.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_segmentsEntry.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets whether this segments entry is active.
	*
	* @param active the active of this segments entry
	*/
	@Override
	public void setActive(boolean active) {
		_segmentsEntry.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_segmentsEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this segments entry.
	*
	* @param companyId the company ID of this segments entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_segmentsEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this segments entry.
	*
	* @param createDate the create date of this segments entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_segmentsEntry.setCreateDate(createDate);
	}

	/**
	* Sets the criteria of this segments entry.
	*
	* @param criteria the criteria of this segments entry
	*/
	@Override
	public void setCriteria(String criteria) {
		_segmentsEntry.setCriteria(criteria);
	}

	/**
	* Sets the description of this segments entry.
	*
	* @param description the description of this segments entry
	*/
	@Override
	public void setDescription(String description) {
		_segmentsEntry.setDescription(description);
	}

	/**
	* Sets the localized description of this segments entry in the language.
	*
	* @param description the localized description of this segments entry
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		_segmentsEntry.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this segments entry in the language, and sets the default locale.
	*
	* @param description the localized description of this segments entry
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_segmentsEntry.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		_segmentsEntry.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this segments entry from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this segments entry
	*/
	@Override
	public void setDescriptionMap(Map<java.util.Locale, String> descriptionMap) {
		_segmentsEntry.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this segments entry from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this segments entry
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {
		_segmentsEntry.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_segmentsEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_segmentsEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_segmentsEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this segments entry.
	*
	* @param groupId the group ID of this segments entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_segmentsEntry.setGroupId(groupId);
	}

	/**
	* Sets the key of this segments entry.
	*
	* @param key the key of this segments entry
	*/
	@Override
	public void setKey(String key) {
		_segmentsEntry.setKey(key);
	}

	/**
	* Sets the modified date of this segments entry.
	*
	* @param modifiedDate the modified date of this segments entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_segmentsEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this segments entry.
	*
	* @param name the name of this segments entry
	*/
	@Override
	public void setName(String name) {
		_segmentsEntry.setName(name);
	}

	/**
	* Sets the localized name of this segments entry in the language.
	*
	* @param name the localized name of this segments entry
	* @param locale the locale of the language
	*/
	@Override
	public void setName(String name, java.util.Locale locale) {
		_segmentsEntry.setName(name, locale);
	}

	/**
	* Sets the localized name of this segments entry in the language, and sets the default locale.
	*
	* @param name the localized name of this segments entry
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_segmentsEntry.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_segmentsEntry.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this segments entry from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this segments entry
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		_segmentsEntry.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this segments entry from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this segments entry
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap,
		java.util.Locale defaultLocale) {
		_segmentsEntry.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_segmentsEntry.setNew(n);
	}

	/**
	* Sets the primary key of this segments entry.
	*
	* @param primaryKey the primary key of this segments entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_segmentsEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_segmentsEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the segments entry ID of this segments entry.
	*
	* @param segmentsEntryId the segments entry ID of this segments entry
	*/
	@Override
	public void setSegmentsEntryId(long segmentsEntryId) {
		_segmentsEntry.setSegmentsEntryId(segmentsEntryId);
	}

	/**
	* Sets the source of this segments entry.
	*
	* @param source the source of this segments entry
	*/
	@Override
	public void setSource(String source) {
		_segmentsEntry.setSource(source);
	}

	/**
	* Sets the type of this segments entry.
	*
	* @param type the type of this segments entry
	*/
	@Override
	public void setType(String type) {
		_segmentsEntry.setType(type);
	}

	/**
	* Sets the user ID of this segments entry.
	*
	* @param userId the user ID of this segments entry
	*/
	@Override
	public void setUserId(long userId) {
		_segmentsEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this segments entry.
	*
	* @param userName the user name of this segments entry
	*/
	@Override
	public void setUserName(String userName) {
		_segmentsEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this segments entry.
	*
	* @param userUuid the user uuid of this segments entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_segmentsEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SegmentsEntry> toCacheModel() {
		return _segmentsEntry.toCacheModel();
	}

	@Override
	public SegmentsEntry toEscapedModel() {
		return new SegmentsEntryWrapper(_segmentsEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _segmentsEntry.toString();
	}

	@Override
	public SegmentsEntry toUnescapedModel() {
		return new SegmentsEntryWrapper(_segmentsEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _segmentsEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SegmentsEntryWrapper)) {
			return false;
		}

		SegmentsEntryWrapper segmentsEntryWrapper = (SegmentsEntryWrapper)obj;

		if (Objects.equals(_segmentsEntry, segmentsEntryWrapper._segmentsEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public SegmentsEntry getWrappedModel() {
		return _segmentsEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _segmentsEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _segmentsEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_segmentsEntry.resetOriginalValues();
	}

	private final SegmentsEntry _segmentsEntry;
}