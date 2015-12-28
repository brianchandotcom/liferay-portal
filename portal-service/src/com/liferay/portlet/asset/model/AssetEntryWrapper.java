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

package com.liferay.portlet.asset.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

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
@ProviderType
public class AssetEntryWrapper implements AssetEntry, ModelWrapper<AssetEntry> {
	public AssetEntryWrapper(AssetEntry assetEntry) {
		_assetEntry = assetEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetEntry.class;
	}

	@Override
	public String getModelClassName() {
		return AssetEntry.class.getName();
	}

	@Override
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
		attributes.put("listable", getListable());
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

	@Override
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

		Integer viewCount = (Integer)attributes.get("viewCount");

		if (viewCount != null) {
			setViewCount(viewCount);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new AssetEntryWrapper((AssetEntry)_assetEntry.clone());
	}

	@Override
	public int compareTo(com.liferay.portlet.asset.model.AssetEntry assetEntry) {
		return _assetEntry.compareTo(assetEntry);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetRenderer<?> getAssetRenderer() {
		return _assetEntry.getAssetRenderer();
	}

	@Override
	public com.liferay.portlet.asset.model.AssetRendererFactory<?> getAssetRendererFactory() {
		return _assetEntry.getAssetRendererFactory();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _assetEntry.getAvailableLanguageIds();
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories() {
		return _assetEntry.getCategories();
	}

	@Override
	public long[] getCategoryIds() {
		return _assetEntry.getCategoryIds();
	}

	/**
	* Returns the fully qualified class name of this asset entry.
	*
	* @return the fully qualified class name of this asset entry
	*/
	@Override
	public java.lang.String getClassName() {
		return _assetEntry.getClassName();
	}

	/**
	* Returns the class name ID of this asset entry.
	*
	* @return the class name ID of this asset entry
	*/
	@Override
	public long getClassNameId() {
		return _assetEntry.getClassNameId();
	}

	/**
	* Returns the class p k of this asset entry.
	*
	* @return the class p k of this asset entry
	*/
	@Override
	public long getClassPK() {
		return _assetEntry.getClassPK();
	}

	/**
	* Returns the class type ID of this asset entry.
	*
	* @return the class type ID of this asset entry
	*/
	@Override
	public long getClassTypeId() {
		return _assetEntry.getClassTypeId();
	}

	/**
	* Returns the class uuid of this asset entry.
	*
	* @return the class uuid of this asset entry
	*/
	@Override
	public java.lang.String getClassUuid() {
		return _assetEntry.getClassUuid();
	}

	/**
	* Returns the company ID of this asset entry.
	*
	* @return the company ID of this asset entry
	*/
	@Override
	public long getCompanyId() {
		return _assetEntry.getCompanyId();
	}

	/**
	* Returns the create date of this asset entry.
	*
	* @return the create date of this asset entry
	*/
	@Override
	public Date getCreateDate() {
		return _assetEntry.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _assetEntry.getDefaultLanguageId();
	}

	/**
	* Returns the description of this asset entry.
	*
	* @return the description of this asset entry
	*/
	@Override
	public java.lang.String getDescription() {
		return _assetEntry.getDescription();
	}

	/**
	* Returns the localized description of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this asset entry
	*/
	@Override
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
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _assetEntry.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this asset entry
	*/
	@Override
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
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _assetEntry.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _assetEntry.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _assetEntry.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this asset entry.
	*
	* @return the locales and localized descriptions of this asset entry
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _assetEntry.getDescriptionMap();
	}

	/**
	* Returns the end date of this asset entry.
	*
	* @return the end date of this asset entry
	*/
	@Override
	public Date getEndDate() {
		return _assetEntry.getEndDate();
	}

	/**
	* Returns the entry ID of this asset entry.
	*
	* @return the entry ID of this asset entry
	*/
	@Override
	public long getEntryId() {
		return _assetEntry.getEntryId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetEntry.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this asset entry.
	*
	* @return the expiration date of this asset entry
	*/
	@Override
	public Date getExpirationDate() {
		return _assetEntry.getExpirationDate();
	}

	/**
	* Returns the group ID of this asset entry.
	*
	* @return the group ID of this asset entry
	*/
	@Override
	public long getGroupId() {
		return _assetEntry.getGroupId();
	}

	/**
	* Returns the height of this asset entry.
	*
	* @return the height of this asset entry
	*/
	@Override
	public int getHeight() {
		return _assetEntry.getHeight();
	}

	/**
	* Returns the layout uuid of this asset entry.
	*
	* @return the layout uuid of this asset entry
	*/
	@Override
	public java.lang.String getLayoutUuid() {
		return _assetEntry.getLayoutUuid();
	}

	/**
	* Returns the listable of this asset entry.
	*
	* @return the listable of this asset entry
	*/
	@Override
	public boolean getListable() {
		return _assetEntry.getListable();
	}

	/**
	* Returns the mime type of this asset entry.
	*
	* @return the mime type of this asset entry
	*/
	@Override
	public java.lang.String getMimeType() {
		return _assetEntry.getMimeType();
	}

	/**
	* Returns the modified date of this asset entry.
	*
	* @return the modified date of this asset entry
	*/
	@Override
	public Date getModifiedDate() {
		return _assetEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this asset entry.
	*
	* @return the primary key of this asset entry
	*/
	@Override
	public long getPrimaryKey() {
		return _assetEntry.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _assetEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this asset entry.
	*
	* @return the priority of this asset entry
	*/
	@Override
	public double getPriority() {
		return _assetEntry.getPriority();
	}

	/**
	* Returns the publish date of this asset entry.
	*
	* @return the publish date of this asset entry
	*/
	@Override
	public Date getPublishDate() {
		return _assetEntry.getPublishDate();
	}

	/**
	* Returns the start date of this asset entry.
	*
	* @return the start date of this asset entry
	*/
	@Override
	public Date getStartDate() {
		return _assetEntry.getStartDate();
	}

	/**
	* Returns the summary of this asset entry.
	*
	* @return the summary of this asset entry
	*/
	@Override
	public java.lang.String getSummary() {
		return _assetEntry.getSummary();
	}

	/**
	* Returns the localized summary of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized summary of this asset entry
	*/
	@Override
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
	@Override
	public java.lang.String getSummary(java.lang.String languageId,
		boolean useDefault) {
		return _assetEntry.getSummary(languageId, useDefault);
	}

	/**
	* Returns the localized summary of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized summary of this asset entry
	*/
	@Override
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
	@Override
	public java.lang.String getSummary(java.util.Locale locale,
		boolean useDefault) {
		return _assetEntry.getSummary(locale, useDefault);
	}

	@Override
	public java.lang.String getSummaryCurrentLanguageId() {
		return _assetEntry.getSummaryCurrentLanguageId();
	}

	@Override
	public java.lang.String getSummaryCurrentValue() {
		return _assetEntry.getSummaryCurrentValue();
	}

	/**
	* Returns a map of the locales and localized summaries of this asset entry.
	*
	* @return the locales and localized summaries of this asset entry
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getSummaryMap() {
		return _assetEntry.getSummaryMap();
	}

	@Override
	public java.lang.String[] getTagNames() {
		return _assetEntry.getTagNames();
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags() {
		return _assetEntry.getTags();
	}

	/**
	* Returns the title of this asset entry.
	*
	* @return the title of this asset entry
	*/
	@Override
	public java.lang.String getTitle() {
		return _assetEntry.getTitle();
	}

	/**
	* Returns the localized title of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this asset entry
	*/
	@Override
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
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _assetEntry.getTitle(languageId, useDefault);
	}

	/**
	* Returns the localized title of this asset entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this asset entry
	*/
	@Override
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
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _assetEntry.getTitle(locale, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _assetEntry.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _assetEntry.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this asset entry.
	*
	* @return the locales and localized titles of this asset entry
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _assetEntry.getTitleMap();
	}

	/**
	* Returns the url of this asset entry.
	*
	* @return the url of this asset entry
	*/
	@Override
	public java.lang.String getUrl() {
		return _assetEntry.getUrl();
	}

	/**
	* Returns the user ID of this asset entry.
	*
	* @return the user ID of this asset entry
	*/
	@Override
	public long getUserId() {
		return _assetEntry.getUserId();
	}

	/**
	* Returns the user name of this asset entry.
	*
	* @return the user name of this asset entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _assetEntry.getUserName();
	}

	/**
	* Returns the user uuid of this asset entry.
	*
	* @return the user uuid of this asset entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _assetEntry.getUserUuid();
	}

	/**
	* Returns the view count of this asset entry.
	*
	* @return the view count of this asset entry
	*/
	@Override
	public int getViewCount() {
		return _assetEntry.getViewCount();
	}

	/**
	* Returns the visible of this asset entry.
	*
	* @return the visible of this asset entry
	*/
	@Override
	public boolean getVisible() {
		return _assetEntry.getVisible();
	}

	/**
	* Returns the width of this asset entry.
	*
	* @return the width of this asset entry
	*/
	@Override
	public int getWidth() {
		return _assetEntry.getWidth();
	}

	@Override
	public int hashCode() {
		return _assetEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetEntry.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this asset entry is listable.
	*
	* @return <code>true</code> if this asset entry is listable; <code>false</code> otherwise
	*/
	@Override
	public boolean isListable() {
		return _assetEntry.isListable();
	}

	@Override
	public boolean isNew() {
		return _assetEntry.isNew();
	}

	/**
	* Returns <code>true</code> if this asset entry is visible.
	*
	* @return <code>true</code> if this asset entry is visible; <code>false</code> otherwise
	*/
	@Override
	public boolean isVisible() {
		return _assetEntry.isVisible();
	}

	@Override
	public void persist() {
		_assetEntry.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_assetEntry.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_assetEntry.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_assetEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this asset entry.
	*
	* @param classNameId the class name ID of this asset entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_assetEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this asset entry.
	*
	* @param classPK the class p k of this asset entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_assetEntry.setClassPK(classPK);
	}

	/**
	* Sets the class type ID of this asset entry.
	*
	* @param classTypeId the class type ID of this asset entry
	*/
	@Override
	public void setClassTypeId(long classTypeId) {
		_assetEntry.setClassTypeId(classTypeId);
	}

	/**
	* Sets the class uuid of this asset entry.
	*
	* @param classUuid the class uuid of this asset entry
	*/
	@Override
	public void setClassUuid(java.lang.String classUuid) {
		_assetEntry.setClassUuid(classUuid);
	}

	/**
	* Sets the company ID of this asset entry.
	*
	* @param companyId the company ID of this asset entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this asset entry.
	*
	* @param createDate the create date of this asset entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_assetEntry.setCreateDate(createDate);
	}

	/**
	* Sets the description of this asset entry.
	*
	* @param description the description of this asset entry
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_assetEntry.setDescription(description);
	}

	/**
	* Sets the localized description of this asset entry in the language.
	*
	* @param description the localized description of this asset entry
	* @param locale the locale of the language
	*/
	@Override
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
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_assetEntry.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_assetEntry.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this asset entry from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this asset entry
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_assetEntry.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this asset entry from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this asset entry
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_assetEntry.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Sets the end date of this asset entry.
	*
	* @param endDate the end date of this asset entry
	*/
	@Override
	public void setEndDate(Date endDate) {
		_assetEntry.setEndDate(endDate);
	}

	/**
	* Sets the entry ID of this asset entry.
	*
	* @param entryId the entry ID of this asset entry
	*/
	@Override
	public void setEntryId(long entryId) {
		_assetEntry.setEntryId(entryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_assetEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_assetEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this asset entry.
	*
	* @param expirationDate the expiration date of this asset entry
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_assetEntry.setExpirationDate(expirationDate);
	}

	/**
	* Sets the group ID of this asset entry.
	*
	* @param groupId the group ID of this asset entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_assetEntry.setGroupId(groupId);
	}

	/**
	* Sets the height of this asset entry.
	*
	* @param height the height of this asset entry
	*/
	@Override
	public void setHeight(int height) {
		_assetEntry.setHeight(height);
	}

	/**
	* Sets the layout uuid of this asset entry.
	*
	* @param layoutUuid the layout uuid of this asset entry
	*/
	@Override
	public void setLayoutUuid(java.lang.String layoutUuid) {
		_assetEntry.setLayoutUuid(layoutUuid);
	}

	/**
	* Sets whether this asset entry is listable.
	*
	* @param listable the listable of this asset entry
	*/
	@Override
	public void setListable(boolean listable) {
		_assetEntry.setListable(listable);
	}

	/**
	* Sets the mime type of this asset entry.
	*
	* @param mimeType the mime type of this asset entry
	*/
	@Override
	public void setMimeType(java.lang.String mimeType) {
		_assetEntry.setMimeType(mimeType);
	}

	/**
	* Sets the modified date of this asset entry.
	*
	* @param modifiedDate the modified date of this asset entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_assetEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_assetEntry.setNew(n);
	}

	/**
	* Sets the primary key of this asset entry.
	*
	* @param primaryKey the primary key of this asset entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_assetEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this asset entry.
	*
	* @param priority the priority of this asset entry
	*/
	@Override
	public void setPriority(double priority) {
		_assetEntry.setPriority(priority);
	}

	/**
	* Sets the publish date of this asset entry.
	*
	* @param publishDate the publish date of this asset entry
	*/
	@Override
	public void setPublishDate(Date publishDate) {
		_assetEntry.setPublishDate(publishDate);
	}

	/**
	* Sets the start date of this asset entry.
	*
	* @param startDate the start date of this asset entry
	*/
	@Override
	public void setStartDate(Date startDate) {
		_assetEntry.setStartDate(startDate);
	}

	/**
	* Sets the summary of this asset entry.
	*
	* @param summary the summary of this asset entry
	*/
	@Override
	public void setSummary(java.lang.String summary) {
		_assetEntry.setSummary(summary);
	}

	/**
	* Sets the localized summary of this asset entry in the language.
	*
	* @param summary the localized summary of this asset entry
	* @param locale the locale of the language
	*/
	@Override
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
	@Override
	public void setSummary(java.lang.String summary, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_assetEntry.setSummary(summary, locale, defaultLocale);
	}

	@Override
	public void setSummaryCurrentLanguageId(java.lang.String languageId) {
		_assetEntry.setSummaryCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized summaries of this asset entry from the map of locales and localized summaries.
	*
	* @param summaryMap the locales and localized summaries of this asset entry
	*/
	@Override
	public void setSummaryMap(
		Map<java.util.Locale, java.lang.String> summaryMap) {
		_assetEntry.setSummaryMap(summaryMap);
	}

	/**
	* Sets the localized summaries of this asset entry from the map of locales and localized summaries, and sets the default locale.
	*
	* @param summaryMap the locales and localized summaries of this asset entry
	* @param defaultLocale the default locale
	*/
	@Override
	public void setSummaryMap(
		Map<java.util.Locale, java.lang.String> summaryMap,
		java.util.Locale defaultLocale) {
		_assetEntry.setSummaryMap(summaryMap, defaultLocale);
	}

	/**
	* Sets the title of this asset entry.
	*
	* @param title the title of this asset entry
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_assetEntry.setTitle(title);
	}

	/**
	* Sets the localized title of this asset entry in the language.
	*
	* @param title the localized title of this asset entry
	* @param locale the locale of the language
	*/
	@Override
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
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_assetEntry.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_assetEntry.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this asset entry from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this asset entry
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap) {
		_assetEntry.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this asset entry from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this asset entry
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_assetEntry.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the url of this asset entry.
	*
	* @param url the url of this asset entry
	*/
	@Override
	public void setUrl(java.lang.String url) {
		_assetEntry.setUrl(url);
	}

	/**
	* Sets the user ID of this asset entry.
	*
	* @param userId the user ID of this asset entry
	*/
	@Override
	public void setUserId(long userId) {
		_assetEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this asset entry.
	*
	* @param userName the user name of this asset entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_assetEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this asset entry.
	*
	* @param userUuid the user uuid of this asset entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_assetEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the view count of this asset entry.
	*
	* @param viewCount the view count of this asset entry
	*/
	@Override
	public void setViewCount(int viewCount) {
		_assetEntry.setViewCount(viewCount);
	}

	/**
	* Sets whether this asset entry is visible.
	*
	* @param visible the visible of this asset entry
	*/
	@Override
	public void setVisible(boolean visible) {
		_assetEntry.setVisible(visible);
	}

	/**
	* Sets the width of this asset entry.
	*
	* @param width the width of this asset entry
	*/
	@Override
	public void setWidth(int width) {
		_assetEntry.setWidth(width);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.asset.model.AssetEntry> toCacheModel() {
		return _assetEntry.toCacheModel();
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry toEscapedModel() {
		return new AssetEntryWrapper(_assetEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetEntry.toString();
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry toUnescapedModel() {
		return new AssetEntryWrapper(_assetEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _assetEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryWrapper)) {
			return false;
		}

		AssetEntryWrapper assetEntryWrapper = (AssetEntryWrapper)obj;

		if (Validator.equals(_assetEntry, assetEntryWrapper._assetEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public AssetEntry getWrappedModel() {
		return _assetEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetEntry.resetOriginalValues();
	}

	private final AssetEntry _assetEntry;
}