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

package com.liferay.layout.seo.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LayoutSEOEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntry
 * @generated
 */
public class LayoutSEOEntryWrapper
	extends BaseModelWrapper<LayoutSEOEntry>
	implements LayoutSEOEntry, ModelWrapper<LayoutSEOEntry> {

	public LayoutSEOEntryWrapper(LayoutSEOEntry layoutSEOEntry) {
		super(layoutSEOEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("layoutSEOEntryId", getLayoutSEOEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("privateLayout", isPrivateLayout());
		attributes.put("layoutId", getLayoutId());
		attributes.put("enabled", isEnabled());
		attributes.put("canonicalURL", getCanonicalURL());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long layoutSEOEntryId = (Long)attributes.get("layoutSEOEntryId");

		if (layoutSEOEntryId != null) {
			setLayoutSEOEntryId(layoutSEOEntryId);
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

		Boolean privateLayout = (Boolean)attributes.get("privateLayout");

		if (privateLayout != null) {
			setPrivateLayout(privateLayout);
		}

		Long layoutId = (Long)attributes.get("layoutId");

		if (layoutId != null) {
			setLayoutId(layoutId);
		}

		Boolean enabled = (Boolean)attributes.get("enabled");

		if (enabled != null) {
			setEnabled(enabled);
		}

		String canonicalURL = (String)attributes.get("canonicalURL");

		if (canonicalURL != null) {
			setCanonicalURL(canonicalURL);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the canonical url of this layout seo entry.
	 *
	 * @return the canonical url of this layout seo entry
	 */
	@Override
	public String getCanonicalURL() {
		return model.getCanonicalURL();
	}

	/**
	 * Returns the localized canonical url of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized canonical url of this layout seo entry
	 */
	@Override
	public String getCanonicalURL(java.util.Locale locale) {
		return model.getCanonicalURL(locale);
	}

	/**
	 * Returns the localized canonical url of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized canonical url of this layout seo entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getCanonicalURL(java.util.Locale locale, boolean useDefault) {
		return model.getCanonicalURL(locale, useDefault);
	}

	/**
	 * Returns the localized canonical url of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized canonical url of this layout seo entry
	 */
	@Override
	public String getCanonicalURL(String languageId) {
		return model.getCanonicalURL(languageId);
	}

	/**
	 * Returns the localized canonical url of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized canonical url of this layout seo entry
	 */
	@Override
	public String getCanonicalURL(String languageId, boolean useDefault) {
		return model.getCanonicalURL(languageId, useDefault);
	}

	@Override
	public String getCanonicalURLCurrentLanguageId() {
		return model.getCanonicalURLCurrentLanguageId();
	}

	@Override
	public String getCanonicalURLCurrentValue() {
		return model.getCanonicalURLCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized canonical urls of this layout seo entry.
	 *
	 * @return the locales and localized canonical urls of this layout seo entry
	 */
	@Override
	public Map<java.util.Locale, String> getCanonicalURLMap() {
		return model.getCanonicalURLMap();
	}

	/**
	 * Returns the company ID of this layout seo entry.
	 *
	 * @return the company ID of this layout seo entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this layout seo entry.
	 *
	 * @return the create date of this layout seo entry
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
	 * Returns the enabled of this layout seo entry.
	 *
	 * @return the enabled of this layout seo entry
	 */
	@Override
	public boolean getEnabled() {
		return model.getEnabled();
	}

	/**
	 * Returns the group ID of this layout seo entry.
	 *
	 * @return the group ID of this layout seo entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this layout seo entry.
	 *
	 * @return the last publish date of this layout seo entry
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the layout ID of this layout seo entry.
	 *
	 * @return the layout ID of this layout seo entry
	 */
	@Override
	public long getLayoutId() {
		return model.getLayoutId();
	}

	/**
	 * Returns the layout seo entry ID of this layout seo entry.
	 *
	 * @return the layout seo entry ID of this layout seo entry
	 */
	@Override
	public long getLayoutSEOEntryId() {
		return model.getLayoutSEOEntryId();
	}

	/**
	 * Returns the modified date of this layout seo entry.
	 *
	 * @return the modified date of this layout seo entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this layout seo entry.
	 *
	 * @return the mvcc version of this layout seo entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this layout seo entry.
	 *
	 * @return the primary key of this layout seo entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the private layout of this layout seo entry.
	 *
	 * @return the private layout of this layout seo entry
	 */
	@Override
	public boolean getPrivateLayout() {
		return model.getPrivateLayout();
	}

	/**
	 * Returns the user ID of this layout seo entry.
	 *
	 * @return the user ID of this layout seo entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this layout seo entry.
	 *
	 * @return the user name of this layout seo entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this layout seo entry.
	 *
	 * @return the user uuid of this layout seo entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this layout seo entry.
	 *
	 * @return the uuid of this layout seo entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this layout seo entry is enabled.
	 *
	 * @return <code>true</code> if this layout seo entry is enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isEnabled() {
		return model.isEnabled();
	}

	/**
	 * Returns <code>true</code> if this layout seo entry is private layout.
	 *
	 * @return <code>true</code> if this layout seo entry is private layout; <code>false</code> otherwise
	 */
	@Override
	public boolean isPrivateLayout() {
		return model.isPrivateLayout();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a layout seo entry model instance should use the <code>LayoutSEOEntry</code> interface instead.
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
	 * Sets the canonical url of this layout seo entry.
	 *
	 * @param canonicalURL the canonical url of this layout seo entry
	 */
	@Override
	public void setCanonicalURL(String canonicalURL) {
		model.setCanonicalURL(canonicalURL);
	}

	/**
	 * Sets the localized canonical url of this layout seo entry in the language.
	 *
	 * @param canonicalURL the localized canonical url of this layout seo entry
	 * @param locale the locale of the language
	 */
	@Override
	public void setCanonicalURL(String canonicalURL, java.util.Locale locale) {
		model.setCanonicalURL(canonicalURL, locale);
	}

	/**
	 * Sets the localized canonical url of this layout seo entry in the language, and sets the default locale.
	 *
	 * @param canonicalURL the localized canonical url of this layout seo entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setCanonicalURL(
		String canonicalURL, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setCanonicalURL(canonicalURL, locale, defaultLocale);
	}

	@Override
	public void setCanonicalURLCurrentLanguageId(String languageId) {
		model.setCanonicalURLCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized canonical urls of this layout seo entry from the map of locales and localized canonical urls.
	 *
	 * @param canonicalURLMap the locales and localized canonical urls of this layout seo entry
	 */
	@Override
	public void setCanonicalURLMap(
		Map<java.util.Locale, String> canonicalURLMap) {

		model.setCanonicalURLMap(canonicalURLMap);
	}

	/**
	 * Sets the localized canonical urls of this layout seo entry from the map of locales and localized canonical urls, and sets the default locale.
	 *
	 * @param canonicalURLMap the locales and localized canonical urls of this layout seo entry
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setCanonicalURLMap(
		Map<java.util.Locale, String> canonicalURLMap,
		java.util.Locale defaultLocale) {

		model.setCanonicalURLMap(canonicalURLMap, defaultLocale);
	}

	/**
	 * Sets the company ID of this layout seo entry.
	 *
	 * @param companyId the company ID of this layout seo entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this layout seo entry.
	 *
	 * @param createDate the create date of this layout seo entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets whether this layout seo entry is enabled.
	 *
	 * @param enabled the enabled of this layout seo entry
	 */
	@Override
	public void setEnabled(boolean enabled) {
		model.setEnabled(enabled);
	}

	/**
	 * Sets the group ID of this layout seo entry.
	 *
	 * @param groupId the group ID of this layout seo entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this layout seo entry.
	 *
	 * @param lastPublishDate the last publish date of this layout seo entry
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the layout ID of this layout seo entry.
	 *
	 * @param layoutId the layout ID of this layout seo entry
	 */
	@Override
	public void setLayoutId(long layoutId) {
		model.setLayoutId(layoutId);
	}

	/**
	 * Sets the layout seo entry ID of this layout seo entry.
	 *
	 * @param layoutSEOEntryId the layout seo entry ID of this layout seo entry
	 */
	@Override
	public void setLayoutSEOEntryId(long layoutSEOEntryId) {
		model.setLayoutSEOEntryId(layoutSEOEntryId);
	}

	/**
	 * Sets the modified date of this layout seo entry.
	 *
	 * @param modifiedDate the modified date of this layout seo entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this layout seo entry.
	 *
	 * @param mvccVersion the mvcc version of this layout seo entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this layout seo entry.
	 *
	 * @param primaryKey the primary key of this layout seo entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this layout seo entry is private layout.
	 *
	 * @param privateLayout the private layout of this layout seo entry
	 */
	@Override
	public void setPrivateLayout(boolean privateLayout) {
		model.setPrivateLayout(privateLayout);
	}

	/**
	 * Sets the user ID of this layout seo entry.
	 *
	 * @param userId the user ID of this layout seo entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this layout seo entry.
	 *
	 * @param userName the user name of this layout seo entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this layout seo entry.
	 *
	 * @param userUuid the user uuid of this layout seo entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this layout seo entry.
	 *
	 * @param uuid the uuid of this layout seo entry
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
	protected LayoutSEOEntryWrapper wrap(LayoutSEOEntry layoutSEOEntry) {
		return new LayoutSEOEntryWrapper(layoutSEOEntry);
	}

}