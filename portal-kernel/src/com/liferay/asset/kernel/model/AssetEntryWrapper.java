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

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntry
 * @generated
 */
public class AssetEntryWrapper
	extends BaseModelWrapper<AssetEntry>
	implements AssetEntry, ModelWrapper<AssetEntry> {

	public AssetEntryWrapper(AssetEntry assetEntry) {
		super(assetEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("entryId", getEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("classUuid", getClassUuid());
		attributes.put("classTypeId", getClassTypeId());
		attributes.put("listable", isListable());
		attributes.put("visible", isVisible());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("publishDate", getPublishDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("mimeType", getMimeType());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("summary", getSummary());
		attributes.put("url", getUrl());
		attributes.put("layoutUuid", getLayoutUuid());
		attributes.put("height", getHeight());
		attributes.put("width", getWidth());
		attributes.put("priority", getPriority());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String classUuid = (String)attributes.get("classUuid");

		if (classUuid != null) {
			setClassUuid(classUuid);
		}

		Long classTypeId = (Long)attributes.get("classTypeId");

		if (classTypeId != null) {
			setClassTypeId(classTypeId);
		}

		Boolean listable = (Boolean)attributes.get("listable");

		if (listable != null) {
			setListable(listable);
		}

		Boolean visible = (Boolean)attributes.get("visible");

		if (visible != null) {
			setVisible(visible);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Date publishDate = (Date)attributes.get("publishDate");

		if (publishDate != null) {
			setPublishDate(publishDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		String mimeType = (String)attributes.get("mimeType");

		if (mimeType != null) {
			setMimeType(mimeType);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String summary = (String)attributes.get("summary");

		if (summary != null) {
			setSummary(summary);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		String layoutUuid = (String)attributes.get("layoutUuid");

		if (layoutUuid != null) {
			setLayoutUuid(layoutUuid);
		}

		Integer height = (Integer)attributes.get("height");

		if (height != null) {
			setHeight(height);
		}

		Integer width = (Integer)attributes.get("width");

		if (width != null) {
			setWidth(width);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public AssetRenderer<?> getAssetRenderer() {
		return model.getAssetRenderer();
	}

	@Override
	public AssetRendererFactory<?> getAssetRendererFactory() {
		return model.getAssetRendererFactory();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	@Override
	public java.util.List<AssetCategory> getCategories() {
		return model.getCategories();
	}

	@Override
	public long[] getCategoryIds() {
		return model.getCategoryIds();
	}

	/**
	 * Returns the fully qualified class name of this asset entry.
	 *
	 * @return the fully qualified class name of this asset entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this asset entry.
	 *
	 * @return the class name ID of this asset entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this asset entry.
	 *
	 * @return the class pk of this asset entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the class type ID of this asset entry.
	 *
	 * @return the class type ID of this asset entry
	 */
	@Override
	public long getClassTypeId() {
		return model.getClassTypeId();
	}

	/**
	 * Returns the class uuid of this asset entry.
	 *
	 * @return the class uuid of this asset entry
	 */
	@Override
	public String getClassUuid() {
		return model.getClassUuid();
	}

	/**
	 * Returns the company ID of this asset entry.
	 *
	 * @return the company ID of this asset entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this asset entry.
	 *
	 * @return the create date of this asset entry
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
	 * Returns the description of this asset entry.
	 *
	 * @return the description of this asset entry
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the localized description of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this asset entry
	 */
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	 * Returns the localized description of this asset entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this asset entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	 * Returns the localized description of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this asset entry
	 */
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	 * Returns the localized description of this asset entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this asset entry
	 */
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return model.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return model.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return model.getDescriptionCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized descriptions of this asset entry.
	 *
	 * @return the locales and localized descriptions of this asset entry
	 */
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	 * Returns the end date of this asset entry.
	 *
	 * @return the end date of this asset entry
	 */
	@Override
	public Date getEndDate() {
		return model.getEndDate();
	}

	/**
	 * Returns the entry ID of this asset entry.
	 *
	 * @return the entry ID of this asset entry
	 */
	@Override
	public long getEntryId() {
		return model.getEntryId();
	}

	/**
	 * Returns the expiration date of this asset entry.
	 *
	 * @return the expiration date of this asset entry
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the group ID of this asset entry.
	 *
	 * @return the group ID of this asset entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the height of this asset entry.
	 *
	 * @return the height of this asset entry
	 */
	@Override
	public int getHeight() {
		return model.getHeight();
	}

	/**
	 * Returns the layout uuid of this asset entry.
	 *
	 * @return the layout uuid of this asset entry
	 */
	@Override
	public String getLayoutUuid() {
		return model.getLayoutUuid();
	}

	/**
	 * Returns the listable of this asset entry.
	 *
	 * @return the listable of this asset entry
	 */
	@Override
	public boolean getListable() {
		return model.getListable();
	}

	/**
	 * Returns the mime type of this asset entry.
	 *
	 * @return the mime type of this asset entry
	 */
	@Override
	public String getMimeType() {
		return model.getMimeType();
	}

	/**
	 * Returns the modified date of this asset entry.
	 *
	 * @return the modified date of this asset entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this asset entry.
	 *
	 * @return the mvcc version of this asset entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this asset entry.
	 *
	 * @return the primary key of this asset entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this asset entry.
	 *
	 * @return the priority of this asset entry
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the publish date of this asset entry.
	 *
	 * @return the publish date of this asset entry
	 */
	@Override
	public Date getPublishDate() {
		return model.getPublishDate();
	}

	/**
	 * Returns the start date of this asset entry.
	 *
	 * @return the start date of this asset entry
	 */
	@Override
	public Date getStartDate() {
		return model.getStartDate();
	}

	/**
	 * Returns the summary of this asset entry.
	 *
	 * @return the summary of this asset entry
	 */
	@Override
	public String getSummary() {
		return model.getSummary();
	}

	/**
	 * Returns the localized summary of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized summary of this asset entry
	 */
	@Override
	public String getSummary(java.util.Locale locale) {
		return model.getSummary(locale);
	}

	/**
	 * Returns the localized summary of this asset entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized summary of this asset entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getSummary(java.util.Locale locale, boolean useDefault) {
		return model.getSummary(locale, useDefault);
	}

	/**
	 * Returns the localized summary of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized summary of this asset entry
	 */
	@Override
	public String getSummary(String languageId) {
		return model.getSummary(languageId);
	}

	/**
	 * Returns the localized summary of this asset entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized summary of this asset entry
	 */
	@Override
	public String getSummary(String languageId, boolean useDefault) {
		return model.getSummary(languageId, useDefault);
	}

	@Override
	public String getSummaryCurrentLanguageId() {
		return model.getSummaryCurrentLanguageId();
	}

	@Override
	public String getSummaryCurrentValue() {
		return model.getSummaryCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized summaries of this asset entry.
	 *
	 * @return the locales and localized summaries of this asset entry
	 */
	@Override
	public Map<java.util.Locale, String> getSummaryMap() {
		return model.getSummaryMap();
	}

	@Override
	public String[] getTagNames() {
		return model.getTagNames();
	}

	@Override
	public java.util.List<AssetTag> getTags() {
		return model.getTags();
	}

	/**
	 * Returns the title of this asset entry.
	 *
	 * @return the title of this asset entry
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the localized title of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized title of this asset entry
	 */
	@Override
	public String getTitle(java.util.Locale locale) {
		return model.getTitle(locale);
	}

	/**
	 * Returns the localized title of this asset entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this asset entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getTitle(java.util.Locale locale, boolean useDefault) {
		return model.getTitle(locale, useDefault);
	}

	/**
	 * Returns the localized title of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized title of this asset entry
	 */
	@Override
	public String getTitle(String languageId) {
		return model.getTitle(languageId);
	}

	/**
	 * Returns the localized title of this asset entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this asset entry
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
	 * Returns a map of the locales and localized titles of this asset entry.
	 *
	 * @return the locales and localized titles of this asset entry
	 */
	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return model.getTitleMap();
	}

	/**
	 * Returns the url of this asset entry.
	 *
	 * @return the url of this asset entry
	 */
	@Override
	public String getUrl() {
		return model.getUrl();
	}

	/**
	 * Returns the user ID of this asset entry.
	 *
	 * @return the user ID of this asset entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this asset entry.
	 *
	 * @return the user name of this asset entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this asset entry.
	 *
	 * @return the user uuid of this asset entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public long getViewCount() {
		return model.getViewCount();
	}

	/**
	 * Returns the visible of this asset entry.
	 *
	 * @return the visible of this asset entry
	 */
	@Override
	public boolean getVisible() {
		return model.getVisible();
	}

	/**
	 * Returns the width of this asset entry.
	 *
	 * @return the width of this asset entry
	 */
	@Override
	public int getWidth() {
		return model.getWidth();
	}

	/**
	 * Returns <code>true</code> if this asset entry is listable.
	 *
	 * @return <code>true</code> if this asset entry is listable; <code>false</code> otherwise
	 */
	@Override
	public boolean isListable() {
		return model.isListable();
	}

	/**
	 * Returns <code>true</code> if this asset entry is visible.
	 *
	 * @return <code>true</code> if this asset entry is visible; <code>false</code> otherwise
	 */
	@Override
	public boolean isVisible() {
		return model.isVisible();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset entry model instance should use the <code>AssetEntry</code> interface instead.
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

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this asset entry.
	 *
	 * @param classNameId the class name ID of this asset entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this asset entry.
	 *
	 * @param classPK the class pk of this asset entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the class type ID of this asset entry.
	 *
	 * @param classTypeId the class type ID of this asset entry
	 */
	@Override
	public void setClassTypeId(long classTypeId) {
		model.setClassTypeId(classTypeId);
	}

	/**
	 * Sets the class uuid of this asset entry.
	 *
	 * @param classUuid the class uuid of this asset entry
	 */
	@Override
	public void setClassUuid(String classUuid) {
		model.setClassUuid(classUuid);
	}

	/**
	 * Sets the company ID of this asset entry.
	 *
	 * @param companyId the company ID of this asset entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this asset entry.
	 *
	 * @param createDate the create date of this asset entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this asset entry.
	 *
	 * @param description the description of this asset entry
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the localized description of this asset entry in the language.
	 *
	 * @param description the localized description of this asset entry
	 * @param locale the locale of the language
	 */
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	 * Sets the localized description of this asset entry in the language, and sets the default locale.
	 *
	 * @param description the localized description of this asset entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescription(
		String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		model.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized descriptions of this asset entry from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this asset entry
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		model.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the localized descriptions of this asset entry from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this asset entry
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {

		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	 * Sets the end date of this asset entry.
	 *
	 * @param endDate the end date of this asset entry
	 */
	@Override
	public void setEndDate(Date endDate) {
		model.setEndDate(endDate);
	}

	/**
	 * Sets the entry ID of this asset entry.
	 *
	 * @param entryId the entry ID of this asset entry
	 */
	@Override
	public void setEntryId(long entryId) {
		model.setEntryId(entryId);
	}

	/**
	 * Sets the expiration date of this asset entry.
	 *
	 * @param expirationDate the expiration date of this asset entry
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the group ID of this asset entry.
	 *
	 * @param groupId the group ID of this asset entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the height of this asset entry.
	 *
	 * @param height the height of this asset entry
	 */
	@Override
	public void setHeight(int height) {
		model.setHeight(height);
	}

	/**
	 * Sets the layout uuid of this asset entry.
	 *
	 * @param layoutUuid the layout uuid of this asset entry
	 */
	@Override
	public void setLayoutUuid(String layoutUuid) {
		model.setLayoutUuid(layoutUuid);
	}

	/**
	 * Sets whether this asset entry is listable.
	 *
	 * @param listable the listable of this asset entry
	 */
	@Override
	public void setListable(boolean listable) {
		model.setListable(listable);
	}

	/**
	 * Sets the mime type of this asset entry.
	 *
	 * @param mimeType the mime type of this asset entry
	 */
	@Override
	public void setMimeType(String mimeType) {
		model.setMimeType(mimeType);
	}

	/**
	 * Sets the modified date of this asset entry.
	 *
	 * @param modifiedDate the modified date of this asset entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this asset entry.
	 *
	 * @param mvccVersion the mvcc version of this asset entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this asset entry.
	 *
	 * @param primaryKey the primary key of this asset entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this asset entry.
	 *
	 * @param priority the priority of this asset entry
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the publish date of this asset entry.
	 *
	 * @param publishDate the publish date of this asset entry
	 */
	@Override
	public void setPublishDate(Date publishDate) {
		model.setPublishDate(publishDate);
	}

	/**
	 * Sets the start date of this asset entry.
	 *
	 * @param startDate the start date of this asset entry
	 */
	@Override
	public void setStartDate(Date startDate) {
		model.setStartDate(startDate);
	}

	/**
	 * Sets the summary of this asset entry.
	 *
	 * @param summary the summary of this asset entry
	 */
	@Override
	public void setSummary(String summary) {
		model.setSummary(summary);
	}

	/**
	 * Sets the localized summary of this asset entry in the language.
	 *
	 * @param summary the localized summary of this asset entry
	 * @param locale the locale of the language
	 */
	@Override
	public void setSummary(String summary, java.util.Locale locale) {
		model.setSummary(summary, locale);
	}

	/**
	 * Sets the localized summary of this asset entry in the language, and sets the default locale.
	 *
	 * @param summary the localized summary of this asset entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setSummary(
		String summary, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setSummary(summary, locale, defaultLocale);
	}

	@Override
	public void setSummaryCurrentLanguageId(String languageId) {
		model.setSummaryCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized summaries of this asset entry from the map of locales and localized summaries.
	 *
	 * @param summaryMap the locales and localized summaries of this asset entry
	 */
	@Override
	public void setSummaryMap(Map<java.util.Locale, String> summaryMap) {
		model.setSummaryMap(summaryMap);
	}

	/**
	 * Sets the localized summaries of this asset entry from the map of locales and localized summaries, and sets the default locale.
	 *
	 * @param summaryMap the locales and localized summaries of this asset entry
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setSummaryMap(
		Map<java.util.Locale, String> summaryMap,
		java.util.Locale defaultLocale) {

		model.setSummaryMap(summaryMap, defaultLocale);
	}

	/**
	 * Sets the title of this asset entry.
	 *
	 * @param title the title of this asset entry
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the localized title of this asset entry in the language.
	 *
	 * @param title the localized title of this asset entry
	 * @param locale the locale of the language
	 */
	@Override
	public void setTitle(String title, java.util.Locale locale) {
		model.setTitle(title, locale);
	}

	/**
	 * Sets the localized title of this asset entry in the language, and sets the default locale.
	 *
	 * @param title the localized title of this asset entry
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
	 * Sets the localized titles of this asset entry from the map of locales and localized titles.
	 *
	 * @param titleMap the locales and localized titles of this asset entry
	 */
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		model.setTitleMap(titleMap);
	}

	/**
	 * Sets the localized titles of this asset entry from the map of locales and localized titles, and sets the default locale.
	 *
	 * @param titleMap the locales and localized titles of this asset entry
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitleMap(
		Map<java.util.Locale, String> titleMap,
		java.util.Locale defaultLocale) {

		model.setTitleMap(titleMap, defaultLocale);
	}

	/**
	 * Sets the url of this asset entry.
	 *
	 * @param url the url of this asset entry
	 */
	@Override
	public void setUrl(String url) {
		model.setUrl(url);
	}

	/**
	 * Sets the user ID of this asset entry.
	 *
	 * @param userId the user ID of this asset entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this asset entry.
	 *
	 * @param userName the user name of this asset entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this asset entry.
	 *
	 * @param userUuid the user uuid of this asset entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets whether this asset entry is visible.
	 *
	 * @param visible the visible of this asset entry
	 */
	@Override
	public void setVisible(boolean visible) {
		model.setVisible(visible);
	}

	/**
	 * Sets the width of this asset entry.
	 *
	 * @param width the width of this asset entry
	 */
	@Override
	public void setWidth(int width) {
		model.setWidth(width);
	}

	@Override
	protected AssetEntryWrapper wrap(AssetEntry assetEntry) {
		return new AssetEntryWrapper(assetEntry);
	}

}