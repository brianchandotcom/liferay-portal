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

package com.liferay.commerce.user.segment.model;

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
 * This class is a wrapper for {@link CommerceUserSegmentEntry}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceUserSegmentEntry
 * @generated
 */
@ProviderType
public class CommerceUserSegmentEntryWrapper implements CommerceUserSegmentEntry,
	ModelWrapper<CommerceUserSegmentEntry> {
	public CommerceUserSegmentEntryWrapper(
		CommerceUserSegmentEntry commerceUserSegmentEntry) {
		_commerceUserSegmentEntry = commerceUserSegmentEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceUserSegmentEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceUserSegmentEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceUserSegmentEntryId",
			getCommerceUserSegmentEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("priority", getPriority());
		attributes.put("active", getActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceUserSegmentEntryId = (Long)attributes.get(
				"commerceUserSegmentEntryId");

		if (commerceUserSegmentEntryId != null) {
			setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
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
		return new CommerceUserSegmentEntryWrapper((CommerceUserSegmentEntry)_commerceUserSegmentEntry.clone());
	}

	@Override
	public int compareTo(CommerceUserSegmentEntry commerceUserSegmentEntry) {
		return _commerceUserSegmentEntry.compareTo(commerceUserSegmentEntry);
	}

	/**
	* Returns the active of this commerce user segment entry.
	*
	* @return the active of this commerce user segment entry
	*/
	@Override
	public boolean getActive() {
		return _commerceUserSegmentEntry.getActive();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceUserSegmentEntry.getAvailableLanguageIds();
	}

	/**
	* Returns the commerce user segment entry ID of this commerce user segment entry.
	*
	* @return the commerce user segment entry ID of this commerce user segment entry
	*/
	@Override
	public long getCommerceUserSegmentEntryId() {
		return _commerceUserSegmentEntry.getCommerceUserSegmentEntryId();
	}

	/**
	* Returns the company ID of this commerce user segment entry.
	*
	* @return the company ID of this commerce user segment entry
	*/
	@Override
	public long getCompanyId() {
		return _commerceUserSegmentEntry.getCompanyId();
	}

	/**
	* Returns the create date of this commerce user segment entry.
	*
	* @return the create date of this commerce user segment entry
	*/
	@Override
	public Date getCreateDate() {
		return _commerceUserSegmentEntry.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceUserSegmentEntry.getDefaultLanguageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceUserSegmentEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce user segment entry.
	*
	* @return the group ID of this commerce user segment entry
	*/
	@Override
	public long getGroupId() {
		return _commerceUserSegmentEntry.getGroupId();
	}

	/**
	* Returns the modified date of this commerce user segment entry.
	*
	* @return the modified date of this commerce user segment entry
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceUserSegmentEntry.getModifiedDate();
	}

	/**
	* Returns the name of this commerce user segment entry.
	*
	* @return the name of this commerce user segment entry
	*/
	@Override
	public java.lang.String getName() {
		return _commerceUserSegmentEntry.getName();
	}

	/**
	* Returns the localized name of this commerce user segment entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this commerce user segment entry
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _commerceUserSegmentEntry.getName(locale);
	}

	/**
	* Returns the localized name of this commerce user segment entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce user segment entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _commerceUserSegmentEntry.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this commerce user segment entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this commerce user segment entry
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _commerceUserSegmentEntry.getName(languageId);
	}

	/**
	* Returns the localized name of this commerce user segment entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce user segment entry
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _commerceUserSegmentEntry.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _commerceUserSegmentEntry.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _commerceUserSegmentEntry.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this commerce user segment entry.
	*
	* @return the locales and localized names of this commerce user segment entry
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _commerceUserSegmentEntry.getNameMap();
	}

	/**
	* Returns the primary key of this commerce user segment entry.
	*
	* @return the primary key of this commerce user segment entry
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceUserSegmentEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceUserSegmentEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this commerce user segment entry.
	*
	* @return the priority of this commerce user segment entry
	*/
	@Override
	public double getPriority() {
		return _commerceUserSegmentEntry.getPriority();
	}

	/**
	* Returns the user ID of this commerce user segment entry.
	*
	* @return the user ID of this commerce user segment entry
	*/
	@Override
	public long getUserId() {
		return _commerceUserSegmentEntry.getUserId();
	}

	/**
	* Returns the user name of this commerce user segment entry.
	*
	* @return the user name of this commerce user segment entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceUserSegmentEntry.getUserName();
	}

	/**
	* Returns the user uuid of this commerce user segment entry.
	*
	* @return the user uuid of this commerce user segment entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceUserSegmentEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceUserSegmentEntry.hashCode();
	}

	/**
	* Returns <code>true</code> if this commerce user segment entry is active.
	*
	* @return <code>true</code> if this commerce user segment entry is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _commerceUserSegmentEntry.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceUserSegmentEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceUserSegmentEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceUserSegmentEntry.isNew();
	}

	@Override
	public void persist() {
		_commerceUserSegmentEntry.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceUserSegmentEntry.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceUserSegmentEntry.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets whether this commerce user segment entry is active.
	*
	* @param active the active of this commerce user segment entry
	*/
	@Override
	public void setActive(boolean active) {
		_commerceUserSegmentEntry.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceUserSegmentEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce user segment entry ID of this commerce user segment entry.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID of this commerce user segment entry
	*/
	@Override
	public void setCommerceUserSegmentEntryId(long commerceUserSegmentEntryId) {
		_commerceUserSegmentEntry.setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Sets the company ID of this commerce user segment entry.
	*
	* @param companyId the company ID of this commerce user segment entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceUserSegmentEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce user segment entry.
	*
	* @param createDate the create date of this commerce user segment entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceUserSegmentEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceUserSegmentEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceUserSegmentEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceUserSegmentEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce user segment entry.
	*
	* @param groupId the group ID of this commerce user segment entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceUserSegmentEntry.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce user segment entry.
	*
	* @param modifiedDate the modified date of this commerce user segment entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceUserSegmentEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce user segment entry.
	*
	* @param name the name of this commerce user segment entry
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceUserSegmentEntry.setName(name);
	}

	/**
	* Sets the localized name of this commerce user segment entry in the language.
	*
	* @param name the localized name of this commerce user segment entry
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_commerceUserSegmentEntry.setName(name, locale);
	}

	/**
	* Sets the localized name of this commerce user segment entry in the language, and sets the default locale.
	*
	* @param name the localized name of this commerce user segment entry
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceUserSegmentEntry.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_commerceUserSegmentEntry.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this commerce user segment entry from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this commerce user segment entry
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_commerceUserSegmentEntry.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this commerce user segment entry from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this commerce user segment entry
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_commerceUserSegmentEntry.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_commerceUserSegmentEntry.setNew(n);
	}

	/**
	* Sets the primary key of this commerce user segment entry.
	*
	* @param primaryKey the primary key of this commerce user segment entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceUserSegmentEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceUserSegmentEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce user segment entry.
	*
	* @param priority the priority of this commerce user segment entry
	*/
	@Override
	public void setPriority(double priority) {
		_commerceUserSegmentEntry.setPriority(priority);
	}

	/**
	* Sets the user ID of this commerce user segment entry.
	*
	* @param userId the user ID of this commerce user segment entry
	*/
	@Override
	public void setUserId(long userId) {
		_commerceUserSegmentEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce user segment entry.
	*
	* @param userName the user name of this commerce user segment entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceUserSegmentEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce user segment entry.
	*
	* @param userUuid the user uuid of this commerce user segment entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceUserSegmentEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceUserSegmentEntry> toCacheModel() {
		return _commerceUserSegmentEntry.toCacheModel();
	}

	@Override
	public CommerceUserSegmentEntry toEscapedModel() {
		return new CommerceUserSegmentEntryWrapper(_commerceUserSegmentEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceUserSegmentEntry.toString();
	}

	@Override
	public CommerceUserSegmentEntry toUnescapedModel() {
		return new CommerceUserSegmentEntryWrapper(_commerceUserSegmentEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceUserSegmentEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceUserSegmentEntryWrapper)) {
			return false;
		}

		CommerceUserSegmentEntryWrapper commerceUserSegmentEntryWrapper = (CommerceUserSegmentEntryWrapper)obj;

		if (Objects.equals(_commerceUserSegmentEntry,
					commerceUserSegmentEntryWrapper._commerceUserSegmentEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceUserSegmentEntry getWrappedModel() {
		return _commerceUserSegmentEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceUserSegmentEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceUserSegmentEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceUserSegmentEntry.resetOriginalValues();
	}

	private final CommerceUserSegmentEntry _commerceUserSegmentEntry;
}