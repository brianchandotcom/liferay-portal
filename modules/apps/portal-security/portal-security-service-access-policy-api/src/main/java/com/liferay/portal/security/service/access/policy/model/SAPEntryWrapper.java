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

package com.liferay.portal.security.service.access.policy.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SAPEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntry
 * @generated
 */
public class SAPEntryWrapper
	extends BaseModelWrapper<SAPEntry>
	implements ModelWrapper<SAPEntry>, SAPEntry {

	public SAPEntryWrapper(SAPEntry sapEntry) {
		super(sapEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("sapEntryId", getSapEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"allowedServiceSignatures", getAllowedServiceSignatures());
		attributes.put("defaultSAPEntry", isDefaultSAPEntry());
		attributes.put("enabled", isEnabled());
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

		Long sapEntryId = (Long)attributes.get("sapEntryId");

		if (sapEntryId != null) {
			setSapEntryId(sapEntryId);
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

		Boolean defaultSAPEntry = (Boolean)attributes.get("defaultSAPEntry");

		if (defaultSAPEntry != null) {
			setDefaultSAPEntry(defaultSAPEntry);
		}

		Boolean enabled = (Boolean)attributes.get("enabled");

		if (enabled != null) {
			setEnabled(enabled);
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

	/**
	 * Returns the allowed service signatures of this sap entry.
	 *
	 * @return the allowed service signatures of this sap entry
	 */
	@Override
	public String getAllowedServiceSignatures() {
		return model.getAllowedServiceSignatures();
	}

	@Override
	public java.util.List<String> getAllowedServiceSignaturesList() {
		return model.getAllowedServiceSignaturesList();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this sap entry.
	 *
	 * @return the company ID of this sap entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this sap entry.
	 *
	 * @return the create date of this sap entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the default sap entry of this sap entry.
	 *
	 * @return the default sap entry of this sap entry
	 */
	@Override
	public boolean getDefaultSAPEntry() {
		return model.getDefaultSAPEntry();
	}

	/**
	 * Returns the enabled of this sap entry.
	 *
	 * @return the enabled of this sap entry
	 */
	@Override
	public boolean getEnabled() {
		return model.getEnabled();
	}

	/**
	 * Returns the modified date of this sap entry.
	 *
	 * @return the modified date of this sap entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this sap entry.
	 *
	 * @return the name of this sap entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this sap entry.
	 *
	 * @return the primary key of this sap entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the sap entry ID of this sap entry.
	 *
	 * @return the sap entry ID of this sap entry
	 */
	@Override
	public long getSapEntryId() {
		return model.getSapEntryId();
	}

	/**
	 * Returns the title of this sap entry.
	 *
	 * @return the title of this sap entry
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the localized title of this sap entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized title of this sap entry
	 */
	@Override
	public String getTitle(java.util.Locale locale) {
		return model.getTitle(locale);
	}

	/**
	 * Returns the localized title of this sap entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this sap entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getTitle(java.util.Locale locale, boolean useDefault) {
		return model.getTitle(locale, useDefault);
	}

	/**
	 * Returns the localized title of this sap entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized title of this sap entry
	 */
	@Override
	public String getTitle(String languageId) {
		return model.getTitle(languageId);
	}

	/**
	 * Returns the localized title of this sap entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this sap entry
	 */
	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return model.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return model.getTitleCurrentLanguageId();
	}

	@Override
	public String getTitleCurrentValue() {
		return model.getTitleCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized titles of this sap entry.
	 *
	 * @return the locales and localized titles of this sap entry
	 */
	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return model.getTitleMap();
	}

	/**
	 * Returns the user ID of this sap entry.
	 *
	 * @return the user ID of this sap entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this sap entry.
	 *
	 * @return the user name of this sap entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this sap entry.
	 *
	 * @return the user uuid of this sap entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this sap entry.
	 *
	 * @return the uuid of this sap entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this sap entry is default sap entry.
	 *
	 * @return <code>true</code> if this sap entry is default sap entry; <code>false</code> otherwise
	 */
	@Override
	public boolean isDefaultSAPEntry() {
		return model.isDefaultSAPEntry();
	}

	/**
	 * Returns <code>true</code> if this sap entry is enabled.
	 *
	 * @return <code>true</code> if this sap entry is enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isEnabled() {
		return model.isEnabled();
	}

	@Override
	public boolean isSystem()
		throws com.liferay.portal.kernel.module.configuration.
			ConfigurationException {

		return model.isSystem();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a sap entry model instance should use the <code>SAPEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	 * Sets the allowed service signatures of this sap entry.
	 *
	 * @param allowedServiceSignatures the allowed service signatures of this sap entry
	 */
	@Override
	public void setAllowedServiceSignatures(String allowedServiceSignatures) {
		model.setAllowedServiceSignatures(allowedServiceSignatures);
	}

	/**
	 * Sets the company ID of this sap entry.
	 *
	 * @param companyId the company ID of this sap entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this sap entry.
	 *
	 * @param createDate the create date of this sap entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets whether this sap entry is default sap entry.
	 *
	 * @param defaultSAPEntry the default sap entry of this sap entry
	 */
	@Override
	public void setDefaultSAPEntry(boolean defaultSAPEntry) {
		model.setDefaultSAPEntry(defaultSAPEntry);
	}

	/**
	 * Sets whether this sap entry is enabled.
	 *
	 * @param enabled the enabled of this sap entry
	 */
	@Override
	public void setEnabled(boolean enabled) {
		model.setEnabled(enabled);
	}

	/**
	 * Sets the modified date of this sap entry.
	 *
	 * @param modifiedDate the modified date of this sap entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this sap entry.
	 *
	 * @param name the name of this sap entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this sap entry.
	 *
	 * @param primaryKey the primary key of this sap entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the sap entry ID of this sap entry.
	 *
	 * @param sapEntryId the sap entry ID of this sap entry
	 */
	@Override
	public void setSapEntryId(long sapEntryId) {
		model.setSapEntryId(sapEntryId);
	}

	/**
	 * Sets the title of this sap entry.
	 *
	 * @param title the title of this sap entry
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the localized title of this sap entry in the language.
	 *
	 * @param title the localized title of this sap entry
	 * @param locale the locale of the language
	 */
	@Override
	public void setTitle(String title, java.util.Locale locale) {
		model.setTitle(title, locale);
	}

	/**
	 * Sets the localized title of this sap entry in the language, and sets the default locale.
	 *
	 * @param title the localized title of this sap entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitle(
		String title, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		model.setTitleCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized titles of this sap entry from the map of locales and localized titles.
	 *
	 * @param titleMap the locales and localized titles of this sap entry
	 */
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		model.setTitleMap(titleMap);
	}

	/**
	 * Sets the localized titles of this sap entry from the map of locales and localized titles, and sets the default locale.
	 *
	 * @param titleMap the locales and localized titles of this sap entry
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitleMap(
		Map<java.util.Locale, String> titleMap,
		java.util.Locale defaultLocale) {

		model.setTitleMap(titleMap, defaultLocale);
	}

	/**
	 * Sets the user ID of this sap entry.
	 *
	 * @param userId the user ID of this sap entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this sap entry.
	 *
	 * @param userName the user name of this sap entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this sap entry.
	 *
	 * @param userUuid the user uuid of this sap entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this sap entry.
	 *
	 * @param uuid the uuid of this sap entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected SAPEntryWrapper wrap(SAPEntry sapEntry) {
		return new SAPEntryWrapper(sapEntry);
	}

}