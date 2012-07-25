/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetEntry
 * @generated
 */
public class AssetEntryWrapper implements AssetEntry, ModelWrapper<AssetEntry> {
	public AssetEntryWrapper(AssetEntry assetEntry) {
		_assetEntry = assetEntry;
	}

	public Class<?> getModelClass() {
		return AssetEntry.class;
	}

	public String getModelClassName() {
		return AssetEntry.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

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
		attributes.put("visible", getVisible());
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
		attributes.put("viewCount", getViewCount());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
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

		Integer viewCount = (Integer)attributes.get("viewCount");

		if (viewCount != null) {
			setViewCount(viewCount);
		}
	}

	/**
	* Returns the primary key of this asset entry.
	*
	* @return the primary key of this asset entry
	*/
	public long getPrimaryKey() {
		return _assetEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this asset entry.
	*
	* @param primaryKey the primary key of this asset entry
	*/
	public void setPrimaryKey(long primaryKey) {
		_assetEntry.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the entry ID of this asset entry.
	*
	* @return the entry ID of this asset entry
	*/
	public long getEntryId() {
		return _assetEntry.getEntryId();
	}

	/**
	* Sets the entry ID of this asset entry.
	*
	* @param entryId the entry ID of this asset entry
	*/
	public void setEntryId(long entryId) {
		_assetEntry.setEntryId(entryId);
	}

	/**
	* Returns the group ID of this asset entry.
	*
	* @return the group ID of this asset entry
	*/
	public long getGroupId() {
		return _assetEntry.getGroupId();
	}

	/**
	* Sets the group ID of this asset entry.
	*
	* @param groupId the group ID of this asset entry
	*/
	public void setGroupId(long groupId) {
		_assetEntry.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this asset entry.
	*
	* @return the company ID of this asset entry
	*/
	public long getCompanyId() {
		return _assetEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this asset entry.
	*
	* @param companyId the company ID of this asset entry
	*/
	public void setCompanyId(long companyId) {
		_assetEntry.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this asset entry.
	*
	* @return the user ID of this asset entry
	*/
	public long getUserId() {
		return _assetEntry.getUserId();
	}

	/**
	* Sets the user ID of this asset entry.
	*
	* @param userId the user ID of this asset entry
	*/
	public void setUserId(long userId) {
		_assetEntry.setUserId(userId);
	}

	/**
	* Returns the user uuid of this asset entry.
	*
	* @return the user uuid of this asset entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this asset entry.
	*
	* @param userUuid the user uuid of this asset entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_assetEntry.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this asset entry.
	*
	* @return the user name of this asset entry
	*/
	public java.lang.String getUserName() {
		return _assetEntry.getUserName();
	}

	/**
	* Sets the user name of this asset entry.
	*
	* @param userName the user name of this asset entry
	*/
	public void setUserName(java.lang.String userName) {
		_assetEntry.setUserName(userName);
	}

	/**
	* Returns the create date of this asset entry.
	*
	* @return the create date of this asset entry
	*/
	public java.util.Date getCreateDate() {
		return _assetEntry.getCreateDate();
	}

	/**
	* Sets the create date of this asset entry.
	*
	* @param createDate the create date of this asset entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_assetEntry.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this asset entry.
	*
	* @return the modified date of this asset entry
	*/
	public java.util.Date getModifiedDate() {
		return _assetEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this asset entry.
	*
	* @param modifiedDate the modified date of this asset entry
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this asset entry.
	*
	* @return the fully qualified class name of this asset entry
	*/
	public java.lang.String getClassName() {
		return _assetEntry.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_assetEntry.setClassName(className);
	}

	/**
	* Returns the class name ID of this asset entry.
	*
	* @return the class name ID of this asset entry
	*/
	public long getClassNameId() {
		return _assetEntry.getClassNameId();
	}

	/**
	* Sets the class name ID of this asset entry.
	*
	* @param classNameId the class name ID of this asset entry
	*/
	public void setClassNameId(long classNameId) {
		_assetEntry.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this asset entry.
	*
	* @return the class p k of this asset entry
	*/
	public long getClassPK() {
		return _assetEntry.getClassPK();
	}

	/**
	* Sets the class p k of this asset entry.
	*
	* @param classPK the class p k of this asset entry
	*/
	public void setClassPK(long classPK) {
		_assetEntry.setClassPK(classPK);
	}

	/**
	* Returns the class uuid of this asset entry.
	*
	* @return the class uuid of this asset entry
	*/
	public java.lang.String getClassUuid() {
		return _assetEntry.getClassUuid();
	}

	/**
	* Sets the class uuid of this asset entry.
	*
	* @param classUuid the class uuid of this asset entry
	*/
	public void setClassUuid(java.lang.String classUuid) {
		_assetEntry.setClassUuid(classUuid);
	}

	/**
	* Returns the class type ID of this asset entry.
	*
	* @return the class type ID of this asset entry
	*/
	public long getClassTypeId() {
		return _assetEntry.getClassTypeId();
	}

	/**
	* Sets the class type ID of this asset entry.
	*
	* @param classTypeId the class type ID of this asset entry
	*/
	public void setClassTypeId(long classTypeId) {
		_assetEntry.setClassTypeId(classTypeId);
	}

	/**
	* Returns the visible of this asset entry.
	*
	* @return the visible of this asset entry
	*/
	public boolean getVisible() {
		return _assetEntry.getVisible();
	}

	/**
	* Returns <code>true</code> if this asset entry is visible.
	*
	* @return <code>true</code> if this asset entry is visible; <code>false</code> otherwise
	*/
	public boolean isVisible() {
		return _assetEntry.isVisible();
	}

	/**
	* Sets whether this asset entry is visible.
	*
	* @param visible the visible of this asset entry
	*/
	public void setVisible(boolean visible) {
		_assetEntry.setVisible(visible);
	}

	/**
	* Returns the start date of this asset entry.
	*
	* @return the start date of this asset entry
	*/
	public java.util.Date getStartDate() {
		return _assetEntry.getStartDate();
	}

	/**
	* Sets the start date of this asset entry.
	*
	* @param startDate the start date of this asset entry
	*/
	public void setStartDate(java.util.Date startDate) {
		_assetEntry.setStartDate(startDate);
	}

	/**
	* Returns the end date of this asset entry.
	*
	* @return the end date of this asset entry
	*/
	public java.util.Date getEndDate() {
		return _assetEntry.getEndDate();
	}

	/**
	* Sets the end date of this asset entry.
	*
	* @param endDate the end date of this asset entry
	*/
	public void setEndDate(java.util.Date endDate) {
		_assetEntry.setEndDate(endDate);
	}

	/**
	* Returns the publish date of this asset entry.
	*
	* @return the publish date of this asset entry
	*/
	public java.util.Date getPublishDate() {
		return _assetEntry.getPublishDate();
	}

	/**
	* Sets the publish date of this asset entry.
	*
	* @param publishDate the publish date of this asset entry
	*/
	public void setPublishDate(java.util.Date publishDate) {
		_assetEntry.setPublishDate(publishDate);
	}

	/**
	* Returns the expiration date of this asset entry.
	*
	* @return the expiration date of this asset entry
	*/
	public java.util.Date getExpirationDate() {
		return _assetEntry.getExpirationDate();
	}

	/**
	* Sets the expiration date of this asset entry.
	*
	* @param expirationDate the expiration date of this asset entry
	*/
	public void setExpirationDate(java.util.Date expirationDate) {
		_assetEntry.setExpirationDate(expirationDate);
	}

	/**
	* Returns the mime type of this asset entry.
	*
	* @return the mime type of this asset entry
	*/
	public java.lang.String getMimeType() {
		return _assetEntry.getMimeType();
	}

	/**
	* Sets the mime type of this asset entry.
	*
	* @param mimeType the mime type of this asset entry
	*/
	public void setMimeType(java.lang.String mimeType) {
		_assetEntry.setMimeType(mimeType);
	}

	/**
	* Returns the title of this asset entry.
	*
	* @return the title of this asset entry
	*/
	public java.lang.String getTitle() {
		return _assetEntry.getTitle();
	}

	/**
	* Returns the localized title of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this asset entry
	*/
	public java.lang.String getTitle(java.util.Locale locale) {
		return _assetEntry.getTitle(locale);
	}

	/**
	* Returns the localized title of this asset entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this asset entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _assetEntry.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this asset entry
	*/
	public java.lang.String getTitle(java.lang.String languageId) {
		return _assetEntry.getTitle(languageId);
	}

	/**
	* Returns the localized title of this asset entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this asset entry
	*/
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _assetEntry.getTitle(languageId, useDefault);
	}

	public java.lang.String getTitleCurrentLanguageId() {
		return _assetEntry.getTitleCurrentLanguageId();
	}

	public java.lang.String getTitleCurrentValue() {
		return _assetEntry.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this asset entry.
	*
	* @return the locales and localized titles of this asset entry
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _assetEntry.getTitleMap();
	}

	/**
	* Sets the title of this asset entry.
	*
	* @param title the title of this asset entry
	*/
	public void setTitle(java.lang.String title) {
		_assetEntry.setTitle(title);
	}

	/**
	* Sets the localized title of this asset entry in the language.
	*
	* @param title the localized title of this asset entry
	* @param locale the locale of the language
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_assetEntry.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this asset entry in the language, and sets the default locale.
	*
	* @param title the localized title of this asset entry
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_assetEntry.setTitle(title, locale, defaultLocale);
	}

	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_assetEntry.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this asset entry from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this asset entry
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_assetEntry.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this asset entry from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this asset entry
	* @param defaultLocale the default locale
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_assetEntry.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Returns the description of this asset entry.
	*
	* @return the description of this asset entry
	*/
	public java.lang.String getDescription() {
		return _assetEntry.getDescription();
	}

	/**
	* Returns the localized description of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this asset entry
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _assetEntry.getDescription(locale);
	}

	/**
	* Returns the localized description of this asset entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this asset entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _assetEntry.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this asset entry
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _assetEntry.getDescription(languageId);
	}

	/**
	* Returns the localized description of this asset entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this asset entry
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _assetEntry.getDescription(languageId, useDefault);
	}

	public java.lang.String getDescriptionCurrentLanguageId() {
		return _assetEntry.getDescriptionCurrentLanguageId();
	}

	public java.lang.String getDescriptionCurrentValue() {
		return _assetEntry.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this asset entry.
	*
	* @return the locales and localized descriptions of this asset entry
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _assetEntry.getDescriptionMap();
	}

	/**
	* Sets the description of this asset entry.
	*
	* @param description the description of this asset entry
	*/
	public void setDescription(java.lang.String description) {
		_assetEntry.setDescription(description);
	}

	/**
	* Sets the localized description of this asset entry in the language.
	*
	* @param description the localized description of this asset entry
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_assetEntry.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this asset entry in the language, and sets the default locale.
	*
	* @param description the localized description of this asset entry
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_assetEntry.setDescription(description, locale, defaultLocale);
	}

	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_assetEntry.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this asset entry from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this asset entry
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_assetEntry.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this asset entry from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this asset entry
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_assetEntry.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the summary of this asset entry.
	*
	* @return the summary of this asset entry
	*/
	public java.lang.String getSummary() {
		return _assetEntry.getSummary();
	}

	/**
	* Returns the localized summary of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized summary of this asset entry
	*/
	public java.lang.String getSummary(java.util.Locale locale) {
		return _assetEntry.getSummary(locale);
	}

	/**
	* Returns the localized summary of this asset entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized summary of this asset entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getSummary(java.util.Locale locale,
		boolean useDefault) {
		return _assetEntry.getSummary(locale, useDefault);
	}

	/**
	* Returns the localized summary of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized summary of this asset entry
	*/
	public java.lang.String getSummary(java.lang.String languageId) {
		return _assetEntry.getSummary(languageId);
	}

	/**
	* Returns the localized summary of this asset entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized summary of this asset entry
	*/
	public java.lang.String getSummary(java.lang.String languageId,
		boolean useDefault) {
		return _assetEntry.getSummary(languageId, useDefault);
	}

	public java.lang.String getSummaryCurrentLanguageId() {
		return _assetEntry.getSummaryCurrentLanguageId();
	}

	public java.lang.String getSummaryCurrentValue() {
		return _assetEntry.getSummaryCurrentValue();
	}

	/**
	* Returns a map of the locales and localized summaries of this asset entry.
	*
	* @return the locales and localized summaries of this asset entry
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getSummaryMap() {
		return _assetEntry.getSummaryMap();
	}

	/**
	* Sets the summary of this asset entry.
	*
	* @param summary the summary of this asset entry
	*/
	public void setSummary(java.lang.String summary) {
		_assetEntry.setSummary(summary);
	}

	/**
	* Sets the localized summary of this asset entry in the language.
	*
	* @param summary the localized summary of this asset entry
	* @param locale the locale of the language
	*/
	public void setSummary(java.lang.String summary, java.util.Locale locale) {
		_assetEntry.setSummary(summary, locale);
	}

	/**
	* Sets the localized summary of this asset entry in the language, and sets the default locale.
	*
	* @param summary the localized summary of this asset entry
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setSummary(java.lang.String summary, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_assetEntry.setSummary(summary, locale, defaultLocale);
	}

	public void setSummaryCurrentLanguageId(java.lang.String languageId) {
		_assetEntry.setSummaryCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized summaries of this asset entry from the map of locales and localized summaries.
	*
	* @param summaryMap the locales and localized summaries of this asset entry
	*/
	public void setSummaryMap(
		java.util.Map<java.util.Locale, java.lang.String> summaryMap) {
		_assetEntry.setSummaryMap(summaryMap);
	}

	/**
	* Sets the localized summaries of this asset entry from the map of locales and localized summaries, and sets the default locale.
	*
	* @param summaryMap the locales and localized summaries of this asset entry
	* @param defaultLocale the default locale
	*/
	public void setSummaryMap(
		java.util.Map<java.util.Locale, java.lang.String> summaryMap,
		java.util.Locale defaultLocale) {
		_assetEntry.setSummaryMap(summaryMap, defaultLocale);
	}

	/**
	* Returns the url of this asset entry.
	*
	* @return the url of this asset entry
	*/
	public java.lang.String getUrl() {
		return _assetEntry.getUrl();
	}

	/**
	* Sets the url of this asset entry.
	*
	* @param url the url of this asset entry
	*/
	public void setUrl(java.lang.String url) {
		_assetEntry.setUrl(url);
	}

	/**
	* Returns the layout uuid of this asset entry.
	*
	* @return the layout uuid of this asset entry
	*/
	public java.lang.String getLayoutUuid() {
		return _assetEntry.getLayoutUuid();
	}

	/**
	* Sets the layout uuid of this asset entry.
	*
	* @param layoutUuid the layout uuid of this asset entry
	*/
	public void setLayoutUuid(java.lang.String layoutUuid) {
		_assetEntry.setLayoutUuid(layoutUuid);
	}

	/**
	* Returns the height of this asset entry.
	*
	* @return the height of this asset entry
	*/
	public int getHeight() {
		return _assetEntry.getHeight();
	}

	/**
	* Sets the height of this asset entry.
	*
	* @param height the height of this asset entry
	*/
	public void setHeight(int height) {
		_assetEntry.setHeight(height);
	}

	/**
	* Returns the width of this asset entry.
	*
	* @return the width of this asset entry
	*/
	public int getWidth() {
		return _assetEntry.getWidth();
	}

	/**
	* Sets the width of this asset entry.
	*
	* @param width the width of this asset entry
	*/
	public void setWidth(int width) {
		_assetEntry.setWidth(width);
	}

	/**
	* Returns the priority of this asset entry.
	*
	* @return the priority of this asset entry
	*/
	public double getPriority() {
		return _assetEntry.getPriority();
	}

	/**
	* Sets the priority of this asset entry.
	*
	* @param priority the priority of this asset entry
	*/
	public void setPriority(double priority) {
		_assetEntry.setPriority(priority);
	}

	/**
	* Returns the view count of this asset entry.
	*
	* @return the view count of this asset entry
	*/
	public int getViewCount() {
		return _assetEntry.getViewCount();
	}

	/**
	* Sets the view count of this asset entry.
	*
	* @param viewCount the view count of this asset entry
	*/
	public void setViewCount(int viewCount) {
		_assetEntry.setViewCount(viewCount);
	}

	public boolean isNew() {
		return _assetEntry.isNew();
	}

	public void setNew(boolean n) {
		_assetEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetEntry.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetEntry.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_assetEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetEntry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new AssetEntryWrapper((AssetEntry)_assetEntry.clone());
	}

	public int compareTo(com.liferay.portlet.asset.model.AssetEntry assetEntry) {
		return _assetEntry.compareTo(assetEntry);
	}

	@Override
	public int hashCode() {
		return _assetEntry.hashCode();
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale) {
		_assetEntry.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.asset.model.AssetEntry> toCacheModel() {
		return _assetEntry.toCacheModel();
	}

	public com.liferay.portlet.asset.model.AssetEntry toEscapedModel() {
		return new AssetEntryWrapper(_assetEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _assetEntry.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntry.persist();
	}

	public com.liferay.portlet.asset.model.AssetRenderer getAssetRenderer() {
		return _assetEntry.getAssetRenderer();
	}

	public com.liferay.portlet.asset.model.AssetRendererFactory getAssetRendererFactory() {
		return _assetEntry.getAssetRendererFactory();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getCategories();
	}

	public long[] getCategoryIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getCategoryIds();
	}

	public java.lang.String[] getTagNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getTagNames();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntry.getTags();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public AssetEntry getWrappedAssetEntry() {
		return _assetEntry;
	}

	public AssetEntry getWrappedModel() {
		return _assetEntry;
	}

	public void resetOriginalValues() {
		_assetEntry.resetOriginalValues();
	}

	private AssetEntry _assetEntry;
}