/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * This class is a wrapper for {@link AssetVocabulary}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabulary
 * @generated
 */
public class AssetVocabularyWrapper implements AssetVocabulary,
	ModelWrapper<AssetVocabulary> {
	public AssetVocabularyWrapper(AssetVocabulary assetVocabulary) {
		_assetVocabulary = assetVocabulary;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetVocabulary.class;
	}

	@Override
	public String getModelClassName() {
		return AssetVocabulary.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("vocabularyId", getVocabularyId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("settings", getSettings());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long vocabularyId = (Long)attributes.get("vocabularyId");

		if (vocabularyId != null) {
			setVocabularyId(vocabularyId);
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}
	}

	/**
	* Returns the primary key of this asset vocabulary.
	*
	* @return the primary key of this asset vocabulary
	*/
	@Override
	public long getPrimaryKey() {
		return _assetVocabulary.getPrimaryKey();
	}

	/**
	* Sets the primary key of this asset vocabulary.
	*
	* @param primaryKey the primary key of this asset vocabulary
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetVocabulary.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this asset vocabulary.
	*
	* @return the uuid of this asset vocabulary
	*/
	@Override
	public java.lang.String getUuid() {
		return _assetVocabulary.getUuid();
	}

	/**
	* Sets the uuid of this asset vocabulary.
	*
	* @param uuid the uuid of this asset vocabulary
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_assetVocabulary.setUuid(uuid);
	}

	/**
	* Returns the vocabulary ID of this asset vocabulary.
	*
	* @return the vocabulary ID of this asset vocabulary
	*/
	@Override
	public long getVocabularyId() {
		return _assetVocabulary.getVocabularyId();
	}

	/**
	* Sets the vocabulary ID of this asset vocabulary.
	*
	* @param vocabularyId the vocabulary ID of this asset vocabulary
	*/
	@Override
	public void setVocabularyId(long vocabularyId) {
		_assetVocabulary.setVocabularyId(vocabularyId);
	}

	/**
	* Returns the group ID of this asset vocabulary.
	*
	* @return the group ID of this asset vocabulary
	*/
	@Override
	public long getGroupId() {
		return _assetVocabulary.getGroupId();
	}

	/**
	* Sets the group ID of this asset vocabulary.
	*
	* @param groupId the group ID of this asset vocabulary
	*/
	@Override
	public void setGroupId(long groupId) {
		_assetVocabulary.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this asset vocabulary.
	*
	* @return the company ID of this asset vocabulary
	*/
	@Override
	public long getCompanyId() {
		return _assetVocabulary.getCompanyId();
	}

	/**
	* Sets the company ID of this asset vocabulary.
	*
	* @param companyId the company ID of this asset vocabulary
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetVocabulary.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this asset vocabulary.
	*
	* @return the user ID of this asset vocabulary
	*/
	@Override
	public long getUserId() {
		return _assetVocabulary.getUserId();
	}

	/**
	* Sets the user ID of this asset vocabulary.
	*
	* @param userId the user ID of this asset vocabulary
	*/
	@Override
	public void setUserId(long userId) {
		_assetVocabulary.setUserId(userId);
	}

	/**
	* Returns the user uuid of this asset vocabulary.
	*
	* @return the user uuid of this asset vocabulary
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetVocabulary.getUserUuid();
	}

	/**
	* Sets the user uuid of this asset vocabulary.
	*
	* @param userUuid the user uuid of this asset vocabulary
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_assetVocabulary.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this asset vocabulary.
	*
	* @return the user name of this asset vocabulary
	*/
	@Override
	public java.lang.String getUserName() {
		return _assetVocabulary.getUserName();
	}

	/**
	* Sets the user name of this asset vocabulary.
	*
	* @param userName the user name of this asset vocabulary
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_assetVocabulary.setUserName(userName);
	}

	/**
	* Returns the create date of this asset vocabulary.
	*
	* @return the create date of this asset vocabulary
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _assetVocabulary.getCreateDate();
	}

	/**
	* Sets the create date of this asset vocabulary.
	*
	* @param createDate the create date of this asset vocabulary
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_assetVocabulary.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this asset vocabulary.
	*
	* @return the modified date of this asset vocabulary
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _assetVocabulary.getModifiedDate();
	}

	/**
	* Sets the modified date of this asset vocabulary.
	*
	* @param modifiedDate the modified date of this asset vocabulary
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetVocabulary.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the name of this asset vocabulary.
	*
	* @return the name of this asset vocabulary
	*/
	@Override
	public java.lang.String getName() {
		return _assetVocabulary.getName();
	}

	/**
	* Sets the name of this asset vocabulary.
	*
	* @param name the name of this asset vocabulary
	*/
	@Override
	public void setName(java.lang.String name) {
		_assetVocabulary.setName(name);
	}

	/**
	* Returns the title of this asset vocabulary.
	*
	* @return the title of this asset vocabulary
	*/
	@Override
	public java.lang.String getTitle() {
		return _assetVocabulary.getTitle();
	}

	/**
	* Returns the localized title of this asset vocabulary in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this asset vocabulary
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _assetVocabulary.getTitle(locale);
	}

	/**
	* Returns the localized title of this asset vocabulary in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this asset vocabulary. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _assetVocabulary.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this asset vocabulary in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this asset vocabulary
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _assetVocabulary.getTitle(languageId);
	}

	/**
	* Returns the localized title of this asset vocabulary in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this asset vocabulary
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _assetVocabulary.getTitle(languageId, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _assetVocabulary.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _assetVocabulary.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this asset vocabulary.
	*
	* @return the locales and localized titles of this asset vocabulary
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _assetVocabulary.getTitleMap();
	}

	/**
	* Sets the title of this asset vocabulary.
	*
	* @param title the title of this asset vocabulary
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_assetVocabulary.setTitle(title);
	}

	/**
	* Sets the localized title of this asset vocabulary in the language.
	*
	* @param title the localized title of this asset vocabulary
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_assetVocabulary.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this asset vocabulary in the language, and sets the default locale.
	*
	* @param title the localized title of this asset vocabulary
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_assetVocabulary.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_assetVocabulary.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this asset vocabulary from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this asset vocabulary
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_assetVocabulary.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this asset vocabulary from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this asset vocabulary
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_assetVocabulary.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Returns the description of this asset vocabulary.
	*
	* @return the description of this asset vocabulary
	*/
	@Override
	public java.lang.String getDescription() {
		return _assetVocabulary.getDescription();
	}

	/**
	* Returns the localized description of this asset vocabulary in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this asset vocabulary
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _assetVocabulary.getDescription(locale);
	}

	/**
	* Returns the localized description of this asset vocabulary in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this asset vocabulary. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _assetVocabulary.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this asset vocabulary in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this asset vocabulary
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _assetVocabulary.getDescription(languageId);
	}

	/**
	* Returns the localized description of this asset vocabulary in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this asset vocabulary
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _assetVocabulary.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _assetVocabulary.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _assetVocabulary.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this asset vocabulary.
	*
	* @return the locales and localized descriptions of this asset vocabulary
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _assetVocabulary.getDescriptionMap();
	}

	/**
	* Sets the description of this asset vocabulary.
	*
	* @param description the description of this asset vocabulary
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_assetVocabulary.setDescription(description);
	}

	/**
	* Sets the localized description of this asset vocabulary in the language.
	*
	* @param description the localized description of this asset vocabulary
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_assetVocabulary.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this asset vocabulary in the language, and sets the default locale.
	*
	* @param description the localized description of this asset vocabulary
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_assetVocabulary.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_assetVocabulary.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this asset vocabulary from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this asset vocabulary
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_assetVocabulary.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this asset vocabulary from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this asset vocabulary
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_assetVocabulary.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the settings of this asset vocabulary.
	*
	* @return the settings of this asset vocabulary
	*/
	@Override
	public java.lang.String getSettings() {
		return _assetVocabulary.getSettings();
	}

	/**
	* Sets the settings of this asset vocabulary.
	*
	* @param settings the settings of this asset vocabulary
	*/
	@Override
	public void setSettings(java.lang.String settings) {
		_assetVocabulary.setSettings(settings);
	}

	@Override
	public boolean isNew() {
		return _assetVocabulary.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_assetVocabulary.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _assetVocabulary.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetVocabulary.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _assetVocabulary.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _assetVocabulary.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_assetVocabulary.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetVocabulary.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_assetVocabulary.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_assetVocabulary.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetVocabulary.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_assetVocabulary.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new AssetVocabularyWrapper((AssetVocabulary)_assetVocabulary.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary) {
		return _assetVocabulary.compareTo(assetVocabulary);
	}

	@Override
	public int hashCode() {
		return _assetVocabulary.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.asset.model.AssetVocabulary> toCacheModel() {
		return _assetVocabulary.toCacheModel();
	}

	@Override
	public com.liferay.portlet.asset.model.AssetVocabulary toEscapedModel() {
		return new AssetVocabularyWrapper(_assetVocabulary.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.asset.model.AssetVocabulary toUnescapedModel() {
		return new AssetVocabularyWrapper(_assetVocabulary.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetVocabulary.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _assetVocabulary.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetVocabulary.persist();
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetVocabulary.getCategories();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getSettingsProperties() {
		return _assetVocabulary.getSettingsProperties();
	}

	@Override
	public boolean isMultiValued() {
		return _assetVocabulary.isMultiValued();
	}

	@Override
	public boolean isRequired(long classNameId) {
		return _assetVocabulary.isRequired(classNameId);
	}

	@Override
	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties) {
		_assetVocabulary.setSettingsProperties(settingsProperties);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public AssetVocabulary getWrappedAssetVocabulary() {
		return _assetVocabulary;
	}

	@Override
	public AssetVocabulary getWrappedModel() {
		return _assetVocabulary;
	}

	@Override
	public void resetOriginalValues() {
		_assetVocabulary.resetOriginalValues();
	}

	private AssetVocabulary _assetVocabulary;
}