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

package com.liferay.service.access.control.profile.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SACPEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SACPEntry
 * @generated
 */
@ProviderType
public class SACPEntryWrapper implements SACPEntry, ModelWrapper<SACPEntry> {
	public SACPEntryWrapper(SACPEntry sacpEntry) {
		_sacpEntry = sacpEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return SACPEntry.class;
	}

	@Override
	public String getModelClassName() {
		return SACPEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("sacpEntryId", getSacpEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("allowedServiceSignatures", getAllowedServiceSignatures());
		attributes.put("defaultSACPEntry", getDefaultSACPEntry());
		attributes.put("name", getName());
		attributes.put("title", getTitle());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long sacpEntryId = (Long)attributes.get("sacpEntryId");

		if (sacpEntryId != null) {
			setSacpEntryId(sacpEntryId);
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

		String allowedServiceSignatures = (String)attributes.get(
				"allowedServiceSignatures");

		if (allowedServiceSignatures != null) {
			setAllowedServiceSignatures(allowedServiceSignatures);
		}

		Boolean defaultSACPEntry = (Boolean)attributes.get("defaultSACPEntry");

		if (defaultSACPEntry != null) {
			setDefaultSACPEntry(defaultSACPEntry);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new SACPEntryWrapper((SACPEntry)_sacpEntry.clone());
	}

	@Override
	public int compareTo(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry) {
		return _sacpEntry.compareTo(sacpEntry);
	}

	/**
	* Returns the allowed service signatures of this s a c p entry.
	*
	* @return the allowed service signatures of this s a c p entry
	*/
	@Override
	public java.lang.String getAllowedServiceSignatures() {
		return _sacpEntry.getAllowedServiceSignatures();
	}

	@Override
	public java.util.List<java.lang.String> getAllowedServiceSignaturesList() {
		return _sacpEntry.getAllowedServiceSignaturesList();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _sacpEntry.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this s a c p entry.
	*
	* @return the company ID of this s a c p entry
	*/
	@Override
	public long getCompanyId() {
		return _sacpEntry.getCompanyId();
	}

	/**
	* Returns the create date of this s a c p entry.
	*
	* @return the create date of this s a c p entry
	*/
	@Override
	public Date getCreateDate() {
		return _sacpEntry.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _sacpEntry.getDefaultLanguageId();
	}

	/**
	* Returns the default s a c p entry of this s a c p entry.
	*
	* @return the default s a c p entry of this s a c p entry
	*/
	@Override
	public boolean getDefaultSACPEntry() {
		return _sacpEntry.getDefaultSACPEntry();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _sacpEntry.getExpandoBridge();
	}

	/**
	* Returns the modified date of this s a c p entry.
	*
	* @return the modified date of this s a c p entry
	*/
	@Override
	public Date getModifiedDate() {
		return _sacpEntry.getModifiedDate();
	}

	/**
	* Returns the name of this s a c p entry.
	*
	* @return the name of this s a c p entry
	*/
	@Override
	public java.lang.String getName() {
		return _sacpEntry.getName();
	}

	/**
	* Returns the primary key of this s a c p entry.
	*
	* @return the primary key of this s a c p entry
	*/
	@Override
	public long getPrimaryKey() {
		return _sacpEntry.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _sacpEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the sacp entry ID of this s a c p entry.
	*
	* @return the sacp entry ID of this s a c p entry
	*/
	@Override
	public long getSacpEntryId() {
		return _sacpEntry.getSacpEntryId();
	}

	/**
	* Returns the title of this s a c p entry.
	*
	* @return the title of this s a c p entry
	*/
	@Override
	public java.lang.String getTitle() {
		return _sacpEntry.getTitle();
	}

	/**
	* Returns the localized title of this s a c p entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this s a c p entry
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _sacpEntry.getTitle(languageId);
	}

	/**
	* Returns the localized title of this s a c p entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this s a c p entry
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _sacpEntry.getTitle(languageId, useDefault);
	}

	/**
	* Returns the localized title of this s a c p entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this s a c p entry
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _sacpEntry.getTitle(locale);
	}

	/**
	* Returns the localized title of this s a c p entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this s a c p entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _sacpEntry.getTitle(locale, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _sacpEntry.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _sacpEntry.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this s a c p entry.
	*
	* @return the locales and localized titles of this s a c p entry
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _sacpEntry.getTitleMap();
	}

	/**
	* Returns the user ID of this s a c p entry.
	*
	* @return the user ID of this s a c p entry
	*/
	@Override
	public long getUserId() {
		return _sacpEntry.getUserId();
	}

	/**
	* Returns the user name of this s a c p entry.
	*
	* @return the user name of this s a c p entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _sacpEntry.getUserName();
	}

	/**
	* Returns the user uuid of this s a c p entry.
	*
	* @return the user uuid of this s a c p entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _sacpEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this s a c p entry.
	*
	* @return the uuid of this s a c p entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _sacpEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _sacpEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _sacpEntry.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this s a c p entry is default s a c p entry.
	*
	* @return <code>true</code> if this s a c p entry is default s a c p entry; <code>false</code> otherwise
	*/
	@Override
	public boolean isDefaultSACPEntry() {
		return _sacpEntry.isDefaultSACPEntry();
	}

	@Override
	public boolean isEscapedModel() {
		return _sacpEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _sacpEntry.isNew();
	}

	@Override
	public void persist() {
		_sacpEntry.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_sacpEntry.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_sacpEntry.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets the allowed service signatures of this s a c p entry.
	*
	* @param allowedServiceSignatures the allowed service signatures of this s a c p entry
	*/
	@Override
	public void setAllowedServiceSignatures(
		java.lang.String allowedServiceSignatures) {
		_sacpEntry.setAllowedServiceSignatures(allowedServiceSignatures);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_sacpEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this s a c p entry.
	*
	* @param companyId the company ID of this s a c p entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_sacpEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this s a c p entry.
	*
	* @param createDate the create date of this s a c p entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_sacpEntry.setCreateDate(createDate);
	}

	/**
	* Sets whether this s a c p entry is default s a c p entry.
	*
	* @param defaultSACPEntry the default s a c p entry of this s a c p entry
	*/
	@Override
	public void setDefaultSACPEntry(boolean defaultSACPEntry) {
		_sacpEntry.setDefaultSACPEntry(defaultSACPEntry);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_sacpEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_sacpEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_sacpEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this s a c p entry.
	*
	* @param modifiedDate the modified date of this s a c p entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_sacpEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this s a c p entry.
	*
	* @param name the name of this s a c p entry
	*/
	@Override
	public void setName(java.lang.String name) {
		_sacpEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_sacpEntry.setNew(n);
	}

	/**
	* Sets the primary key of this s a c p entry.
	*
	* @param primaryKey the primary key of this s a c p entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_sacpEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_sacpEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the sacp entry ID of this s a c p entry.
	*
	* @param sacpEntryId the sacp entry ID of this s a c p entry
	*/
	@Override
	public void setSacpEntryId(long sacpEntryId) {
		_sacpEntry.setSacpEntryId(sacpEntryId);
	}

	/**
	* Sets the title of this s a c p entry.
	*
	* @param title the title of this s a c p entry
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_sacpEntry.setTitle(title);
	}

	/**
	* Sets the localized title of this s a c p entry in the language.
	*
	* @param title the localized title of this s a c p entry
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_sacpEntry.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this s a c p entry in the language, and sets the default locale.
	*
	* @param title the localized title of this s a c p entry
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_sacpEntry.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_sacpEntry.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this s a c p entry from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this s a c p entry
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap) {
		_sacpEntry.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this s a c p entry from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this s a c p entry
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_sacpEntry.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the user ID of this s a c p entry.
	*
	* @param userId the user ID of this s a c p entry
	*/
	@Override
	public void setUserId(long userId) {
		_sacpEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this s a c p entry.
	*
	* @param userName the user name of this s a c p entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_sacpEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this s a c p entry.
	*
	* @param userUuid the user uuid of this s a c p entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_sacpEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this s a c p entry.
	*
	* @param uuid the uuid of this s a c p entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_sacpEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.service.access.control.profile.model.SACPEntry> toCacheModel() {
		return _sacpEntry.toCacheModel();
	}

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry toEscapedModel() {
		return new SACPEntryWrapper(_sacpEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _sacpEntry.toString();
	}

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry toUnescapedModel() {
		return new SACPEntryWrapper(_sacpEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _sacpEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SACPEntryWrapper)) {
			return false;
		}

		SACPEntryWrapper sacpEntryWrapper = (SACPEntryWrapper)obj;

		if (Validator.equals(_sacpEntry, sacpEntryWrapper._sacpEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _sacpEntry.getStagedModelType();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public SACPEntry getWrappedSACPEntry() {
		return _sacpEntry;
	}

	@Override
	public SACPEntry getWrappedModel() {
		return _sacpEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _sacpEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _sacpEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_sacpEntry.resetOriginalValues();
	}

	private final SACPEntry _sacpEntry;
}