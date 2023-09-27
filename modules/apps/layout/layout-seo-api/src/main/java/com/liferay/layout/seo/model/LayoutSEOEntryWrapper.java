/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

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
		attributes.put("ctCollectionId", getCtCollectionId());
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
		attributes.put("canonicalURL", getCanonicalURL());
		attributes.put("canonicalURLEnabled", isCanonicalURLEnabled());
		attributes.put("DDMStorageId", getDDMStorageId());
		attributes.put("openGraphDescription", getOpenGraphDescription());
		attributes.put(
			"openGraphDescriptionEnabled", isOpenGraphDescriptionEnabled());
		attributes.put("openGraphImageAlt", getOpenGraphImageAlt());
		attributes.put(
			"openGraphImageFileEntryId", getOpenGraphImageFileEntryId());
		attributes.put("openGraphTitle", getOpenGraphTitle());
		attributes.put("openGraphTitleEnabled", isOpenGraphTitleEnabled());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
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

		String canonicalURL = (String)attributes.get("canonicalURL");

		if (canonicalURL != null) {
			setCanonicalURL(canonicalURL);
		}

		Boolean canonicalURLEnabled = (Boolean)attributes.get(
			"canonicalURLEnabled");

		if (canonicalURLEnabled != null) {
			setCanonicalURLEnabled(canonicalURLEnabled);
		}

		Long DDMStorageId = (Long)attributes.get("DDMStorageId");

		if (DDMStorageId != null) {
			setDDMStorageId(DDMStorageId);
		}

		String openGraphDescription = (String)attributes.get(
			"openGraphDescription");

		if (openGraphDescription != null) {
			setOpenGraphDescription(openGraphDescription);
		}

		Boolean openGraphDescriptionEnabled = (Boolean)attributes.get(
			"openGraphDescriptionEnabled");

		if (openGraphDescriptionEnabled != null) {
			setOpenGraphDescriptionEnabled(openGraphDescriptionEnabled);
		}

		String openGraphImageAlt = (String)attributes.get("openGraphImageAlt");

		if (openGraphImageAlt != null) {
			setOpenGraphImageAlt(openGraphImageAlt);
		}

		Long openGraphImageFileEntryId = (Long)attributes.get(
			"openGraphImageFileEntryId");

		if (openGraphImageFileEntryId != null) {
			setOpenGraphImageFileEntryId(openGraphImageFileEntryId);
		}

		String openGraphTitle = (String)attributes.get("openGraphTitle");

		if (openGraphTitle != null) {
			setOpenGraphTitle(openGraphTitle);
		}

		Boolean openGraphTitleEnabled = (Boolean)attributes.get(
			"openGraphTitleEnabled");

		if (openGraphTitleEnabled != null) {
			setOpenGraphTitleEnabled(openGraphTitleEnabled);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public LayoutSEOEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
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
	 * Returns the canonical url enabled of this layout seo entry.
	 *
	 * @return the canonical url enabled of this layout seo entry
	 */
	@Override
	public boolean getCanonicalURLEnabled() {
		return model.getCanonicalURLEnabled();
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

	/**
	 * Returns the ct collection ID of this layout seo entry.
	 *
	 * @return the ct collection ID of this layout seo entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the ddm storage ID of this layout seo entry.
	 *
	 * @return the ddm storage ID of this layout seo entry
	 */
	@Override
	public long getDDMStorageId() {
		return model.getDDMStorageId();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
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
	 * Returns the open graph description of this layout seo entry.
	 *
	 * @return the open graph description of this layout seo entry
	 */
	@Override
	public String getOpenGraphDescription() {
		return model.getOpenGraphDescription();
	}

	/**
	 * Returns the localized open graph description of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized open graph description of this layout seo entry
	 */
	@Override
	public String getOpenGraphDescription(java.util.Locale locale) {
		return model.getOpenGraphDescription(locale);
	}

	/**
	 * Returns the localized open graph description of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized open graph description of this layout seo entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getOpenGraphDescription(
		java.util.Locale locale, boolean useDefault) {

		return model.getOpenGraphDescription(locale, useDefault);
	}

	/**
	 * Returns the localized open graph description of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized open graph description of this layout seo entry
	 */
	@Override
	public String getOpenGraphDescription(String languageId) {
		return model.getOpenGraphDescription(languageId);
	}

	/**
	 * Returns the localized open graph description of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized open graph description of this layout seo entry
	 */
	@Override
	public String getOpenGraphDescription(
		String languageId, boolean useDefault) {

		return model.getOpenGraphDescription(languageId, useDefault);
	}

	@Override
	public String getOpenGraphDescriptionCurrentLanguageId() {
		return model.getOpenGraphDescriptionCurrentLanguageId();
	}

	@Override
	public String getOpenGraphDescriptionCurrentValue() {
		return model.getOpenGraphDescriptionCurrentValue();
	}

	/**
	 * Returns the open graph description enabled of this layout seo entry.
	 *
	 * @return the open graph description enabled of this layout seo entry
	 */
	@Override
	public boolean getOpenGraphDescriptionEnabled() {
		return model.getOpenGraphDescriptionEnabled();
	}

	/**
	 * Returns a map of the locales and localized open graph descriptions of this layout seo entry.
	 *
	 * @return the locales and localized open graph descriptions of this layout seo entry
	 */
	@Override
	public Map<java.util.Locale, String> getOpenGraphDescriptionMap() {
		return model.getOpenGraphDescriptionMap();
	}

	/**
	 * Returns the open graph image alt of this layout seo entry.
	 *
	 * @return the open graph image alt of this layout seo entry
	 */
	@Override
	public String getOpenGraphImageAlt() {
		return model.getOpenGraphImageAlt();
	}

	/**
	 * Returns the localized open graph image alt of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized open graph image alt of this layout seo entry
	 */
	@Override
	public String getOpenGraphImageAlt(java.util.Locale locale) {
		return model.getOpenGraphImageAlt(locale);
	}

	/**
	 * Returns the localized open graph image alt of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized open graph image alt of this layout seo entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getOpenGraphImageAlt(
		java.util.Locale locale, boolean useDefault) {

		return model.getOpenGraphImageAlt(locale, useDefault);
	}

	/**
	 * Returns the localized open graph image alt of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized open graph image alt of this layout seo entry
	 */
	@Override
	public String getOpenGraphImageAlt(String languageId) {
		return model.getOpenGraphImageAlt(languageId);
	}

	/**
	 * Returns the localized open graph image alt of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized open graph image alt of this layout seo entry
	 */
	@Override
	public String getOpenGraphImageAlt(String languageId, boolean useDefault) {
		return model.getOpenGraphImageAlt(languageId, useDefault);
	}

	@Override
	public String getOpenGraphImageAltCurrentLanguageId() {
		return model.getOpenGraphImageAltCurrentLanguageId();
	}

	@Override
	public String getOpenGraphImageAltCurrentValue() {
		return model.getOpenGraphImageAltCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized open graph image alts of this layout seo entry.
	 *
	 * @return the locales and localized open graph image alts of this layout seo entry
	 */
	@Override
	public Map<java.util.Locale, String> getOpenGraphImageAltMap() {
		return model.getOpenGraphImageAltMap();
	}

	/**
	 * Returns the open graph image file entry ID of this layout seo entry.
	 *
	 * @return the open graph image file entry ID of this layout seo entry
	 */
	@Override
	public long getOpenGraphImageFileEntryId() {
		return model.getOpenGraphImageFileEntryId();
	}

	/**
	 * Returns the open graph title of this layout seo entry.
	 *
	 * @return the open graph title of this layout seo entry
	 */
	@Override
	public String getOpenGraphTitle() {
		return model.getOpenGraphTitle();
	}

	/**
	 * Returns the localized open graph title of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized open graph title of this layout seo entry
	 */
	@Override
	public String getOpenGraphTitle(java.util.Locale locale) {
		return model.getOpenGraphTitle(locale);
	}

	/**
	 * Returns the localized open graph title of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized open graph title of this layout seo entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getOpenGraphTitle(
		java.util.Locale locale, boolean useDefault) {

		return model.getOpenGraphTitle(locale, useDefault);
	}

	/**
	 * Returns the localized open graph title of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized open graph title of this layout seo entry
	 */
	@Override
	public String getOpenGraphTitle(String languageId) {
		return model.getOpenGraphTitle(languageId);
	}

	/**
	 * Returns the localized open graph title of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized open graph title of this layout seo entry
	 */
	@Override
	public String getOpenGraphTitle(String languageId, boolean useDefault) {
		return model.getOpenGraphTitle(languageId, useDefault);
	}

	@Override
	public String getOpenGraphTitleCurrentLanguageId() {
		return model.getOpenGraphTitleCurrentLanguageId();
	}

	@Override
	public String getOpenGraphTitleCurrentValue() {
		return model.getOpenGraphTitleCurrentValue();
	}

	/**
	 * Returns the open graph title enabled of this layout seo entry.
	 *
	 * @return the open graph title enabled of this layout seo entry
	 */
	@Override
	public boolean getOpenGraphTitleEnabled() {
		return model.getOpenGraphTitleEnabled();
	}

	/**
	 * Returns a map of the locales and localized open graph titles of this layout seo entry.
	 *
	 * @return the locales and localized open graph titles of this layout seo entry
	 */
	@Override
	public Map<java.util.Locale, String> getOpenGraphTitleMap() {
		return model.getOpenGraphTitleMap();
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
	 * Returns <code>true</code> if this layout seo entry is canonical url enabled.
	 *
	 * @return <code>true</code> if this layout seo entry is canonical url enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isCanonicalURLEnabled() {
		return model.isCanonicalURLEnabled();
	}

	/**
	 * Returns <code>true</code> if this layout seo entry is open graph description enabled.
	 *
	 * @return <code>true</code> if this layout seo entry is open graph description enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isOpenGraphDescriptionEnabled() {
		return model.isOpenGraphDescriptionEnabled();
	}

	/**
	 * Returns <code>true</code> if this layout seo entry is open graph title enabled.
	 *
	 * @return <code>true</code> if this layout seo entry is open graph title enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isOpenGraphTitleEnabled() {
		return model.isOpenGraphTitleEnabled();
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
	 * Sets whether this layout seo entry is canonical url enabled.
	 *
	 * @param canonicalURLEnabled the canonical url enabled of this layout seo entry
	 */
	@Override
	public void setCanonicalURLEnabled(boolean canonicalURLEnabled) {
		model.setCanonicalURLEnabled(canonicalURLEnabled);
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
	 * Sets the ct collection ID of this layout seo entry.
	 *
	 * @param ctCollectionId the ct collection ID of this layout seo entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ddm storage ID of this layout seo entry.
	 *
	 * @param DDMStorageId the ddm storage ID of this layout seo entry
	 */
	@Override
	public void setDDMStorageId(long DDMStorageId) {
		model.setDDMStorageId(DDMStorageId);
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
	 * Sets the open graph description of this layout seo entry.
	 *
	 * @param openGraphDescription the open graph description of this layout seo entry
	 */
	@Override
	public void setOpenGraphDescription(String openGraphDescription) {
		model.setOpenGraphDescription(openGraphDescription);
	}

	/**
	 * Sets the localized open graph description of this layout seo entry in the language.
	 *
	 * @param openGraphDescription the localized open graph description of this layout seo entry
	 * @param locale the locale of the language
	 */
	@Override
	public void setOpenGraphDescription(
		String openGraphDescription, java.util.Locale locale) {

		model.setOpenGraphDescription(openGraphDescription, locale);
	}

	/**
	 * Sets the localized open graph description of this layout seo entry in the language, and sets the default locale.
	 *
	 * @param openGraphDescription the localized open graph description of this layout seo entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setOpenGraphDescription(
		String openGraphDescription, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setOpenGraphDescription(
			openGraphDescription, locale, defaultLocale);
	}

	@Override
	public void setOpenGraphDescriptionCurrentLanguageId(String languageId) {
		model.setOpenGraphDescriptionCurrentLanguageId(languageId);
	}

	/**
	 * Sets whether this layout seo entry is open graph description enabled.
	 *
	 * @param openGraphDescriptionEnabled the open graph description enabled of this layout seo entry
	 */
	@Override
	public void setOpenGraphDescriptionEnabled(
		boolean openGraphDescriptionEnabled) {

		model.setOpenGraphDescriptionEnabled(openGraphDescriptionEnabled);
	}

	/**
	 * Sets the localized open graph descriptions of this layout seo entry from the map of locales and localized open graph descriptions.
	 *
	 * @param openGraphDescriptionMap the locales and localized open graph descriptions of this layout seo entry
	 */
	@Override
	public void setOpenGraphDescriptionMap(
		Map<java.util.Locale, String> openGraphDescriptionMap) {

		model.setOpenGraphDescriptionMap(openGraphDescriptionMap);
	}

	/**
	 * Sets the localized open graph descriptions of this layout seo entry from the map of locales and localized open graph descriptions, and sets the default locale.
	 *
	 * @param openGraphDescriptionMap the locales and localized open graph descriptions of this layout seo entry
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setOpenGraphDescriptionMap(
		Map<java.util.Locale, String> openGraphDescriptionMap,
		java.util.Locale defaultLocale) {

		model.setOpenGraphDescriptionMap(
			openGraphDescriptionMap, defaultLocale);
	}

	/**
	 * Sets the open graph image alt of this layout seo entry.
	 *
	 * @param openGraphImageAlt the open graph image alt of this layout seo entry
	 */
	@Override
	public void setOpenGraphImageAlt(String openGraphImageAlt) {
		model.setOpenGraphImageAlt(openGraphImageAlt);
	}

	/**
	 * Sets the localized open graph image alt of this layout seo entry in the language.
	 *
	 * @param openGraphImageAlt the localized open graph image alt of this layout seo entry
	 * @param locale the locale of the language
	 */
	@Override
	public void setOpenGraphImageAlt(
		String openGraphImageAlt, java.util.Locale locale) {

		model.setOpenGraphImageAlt(openGraphImageAlt, locale);
	}

	/**
	 * Sets the localized open graph image alt of this layout seo entry in the language, and sets the default locale.
	 *
	 * @param openGraphImageAlt the localized open graph image alt of this layout seo entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setOpenGraphImageAlt(
		String openGraphImageAlt, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setOpenGraphImageAlt(openGraphImageAlt, locale, defaultLocale);
	}

	@Override
	public void setOpenGraphImageAltCurrentLanguageId(String languageId) {
		model.setOpenGraphImageAltCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized open graph image alts of this layout seo entry from the map of locales and localized open graph image alts.
	 *
	 * @param openGraphImageAltMap the locales and localized open graph image alts of this layout seo entry
	 */
	@Override
	public void setOpenGraphImageAltMap(
		Map<java.util.Locale, String> openGraphImageAltMap) {

		model.setOpenGraphImageAltMap(openGraphImageAltMap);
	}

	/**
	 * Sets the localized open graph image alts of this layout seo entry from the map of locales and localized open graph image alts, and sets the default locale.
	 *
	 * @param openGraphImageAltMap the locales and localized open graph image alts of this layout seo entry
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setOpenGraphImageAltMap(
		Map<java.util.Locale, String> openGraphImageAltMap,
		java.util.Locale defaultLocale) {

		model.setOpenGraphImageAltMap(openGraphImageAltMap, defaultLocale);
	}

	/**
	 * Sets the open graph image file entry ID of this layout seo entry.
	 *
	 * @param openGraphImageFileEntryId the open graph image file entry ID of this layout seo entry
	 */
	@Override
	public void setOpenGraphImageFileEntryId(long openGraphImageFileEntryId) {
		model.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);
	}

	/**
	 * Sets the open graph title of this layout seo entry.
	 *
	 * @param openGraphTitle the open graph title of this layout seo entry
	 */
	@Override
	public void setOpenGraphTitle(String openGraphTitle) {
		model.setOpenGraphTitle(openGraphTitle);
	}

	/**
	 * Sets the localized open graph title of this layout seo entry in the language.
	 *
	 * @param openGraphTitle the localized open graph title of this layout seo entry
	 * @param locale the locale of the language
	 */
	@Override
	public void setOpenGraphTitle(
		String openGraphTitle, java.util.Locale locale) {

		model.setOpenGraphTitle(openGraphTitle, locale);
	}

	/**
	 * Sets the localized open graph title of this layout seo entry in the language, and sets the default locale.
	 *
	 * @param openGraphTitle the localized open graph title of this layout seo entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setOpenGraphTitle(
		String openGraphTitle, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setOpenGraphTitle(openGraphTitle, locale, defaultLocale);
	}

	@Override
	public void setOpenGraphTitleCurrentLanguageId(String languageId) {
		model.setOpenGraphTitleCurrentLanguageId(languageId);
	}

	/**
	 * Sets whether this layout seo entry is open graph title enabled.
	 *
	 * @param openGraphTitleEnabled the open graph title enabled of this layout seo entry
	 */
	@Override
	public void setOpenGraphTitleEnabled(boolean openGraphTitleEnabled) {
		model.setOpenGraphTitleEnabled(openGraphTitleEnabled);
	}

	/**
	 * Sets the localized open graph titles of this layout seo entry from the map of locales and localized open graph titles.
	 *
	 * @param openGraphTitleMap the locales and localized open graph titles of this layout seo entry
	 */
	@Override
	public void setOpenGraphTitleMap(
		Map<java.util.Locale, String> openGraphTitleMap) {

		model.setOpenGraphTitleMap(openGraphTitleMap);
	}

	/**
	 * Sets the localized open graph titles of this layout seo entry from the map of locales and localized open graph titles, and sets the default locale.
	 *
	 * @param openGraphTitleMap the locales and localized open graph titles of this layout seo entry
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setOpenGraphTitleMap(
		Map<java.util.Locale, String> openGraphTitleMap,
		java.util.Locale defaultLocale) {

		model.setOpenGraphTitleMap(openGraphTitleMap, defaultLocale);
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
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<LayoutSEOEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<LayoutSEOEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
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