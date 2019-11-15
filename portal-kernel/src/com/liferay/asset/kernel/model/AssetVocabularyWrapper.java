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
 * This class is a wrapper for {@link AssetVocabulary}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabulary
 * @generated
 */
public class AssetVocabularyWrapper
	extends BaseModelWrapper<AssetVocabulary>
	implements AssetVocabulary, ModelWrapper<AssetVocabulary> {

	public AssetVocabularyWrapper(AssetVocabulary assetVocabulary) {
		super(assetVocabulary);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
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

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
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

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
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
	public int getCategoriesCount() {
		return model.getCategoriesCount();
	}

	/**
	 * Returns the company ID of this asset vocabulary.
	 *
	 * @return the company ID of this asset vocabulary
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this asset vocabulary.
	 *
	 * @return the create date of this asset vocabulary
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this asset vocabulary.
	 *
	 * @return the ct collection ID of this asset vocabulary
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the description of this asset vocabulary.
	 *
	 * @return the description of this asset vocabulary
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the localized description of this asset vocabulary in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this asset vocabulary
	 */
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	 * Returns the localized description of this asset vocabulary in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this asset vocabulary. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	 * Returns the localized description of this asset vocabulary in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this asset vocabulary
	 */
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	 * Returns the localized description of this asset vocabulary in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this asset vocabulary
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
	 * Returns a map of the locales and localized descriptions of this asset vocabulary.
	 *
	 * @return the locales and localized descriptions of this asset vocabulary
	 */
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	 * Returns the external reference code of this asset vocabulary.
	 *
	 * @return the external reference code of this asset vocabulary
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this asset vocabulary.
	 *
	 * @return the group ID of this asset vocabulary
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this asset vocabulary.
	 *
	 * @return the last publish date of this asset vocabulary
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this asset vocabulary.
	 *
	 * @return the modified date of this asset vocabulary
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this asset vocabulary.
	 *
	 * @return the mvcc version of this asset vocabulary
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this asset vocabulary.
	 *
	 * @return the name of this asset vocabulary
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this asset vocabulary.
	 *
	 * @return the primary key of this asset vocabulary
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public long[] getRequiredClassNameIds() {
		return model.getRequiredClassNameIds();
	}

	@Override
	public long[] getSelectedClassNameIds() {
		return model.getSelectedClassNameIds();
	}

	@Override
	public long[] getSelectedClassTypePKs() {
		return model.getSelectedClassTypePKs();
	}

	/**
	 * Returns the settings of this asset vocabulary.
	 *
	 * @return the settings of this asset vocabulary
	 */
	@Override
	public String getSettings() {
		return model.getSettings();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getSettingsProperties() {

		return model.getSettingsProperties();
	}

	/**
	 * Returns the title of this asset vocabulary.
	 *
	 * @return the title of this asset vocabulary
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the localized title of this asset vocabulary in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized title of this asset vocabulary
	 */
	@Override
	public String getTitle(java.util.Locale locale) {
		return model.getTitle(locale);
	}

	/**
	 * Returns the localized title of this asset vocabulary in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this asset vocabulary. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getTitle(java.util.Locale locale, boolean useDefault) {
		return model.getTitle(locale, useDefault);
	}

	/**
	 * Returns the localized title of this asset vocabulary in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized title of this asset vocabulary
	 */
	@Override
	public String getTitle(String languageId) {
		return model.getTitle(languageId);
	}

	/**
	 * Returns the localized title of this asset vocabulary in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this asset vocabulary
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
	 * Returns a map of the locales and localized titles of this asset vocabulary.
	 *
	 * @return the locales and localized titles of this asset vocabulary
	 */
	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return model.getTitleMap();
	}

	@Override
	public String getUnambiguousTitle(
			java.util.List<AssetVocabulary> vocabularies, long groupId,
			java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getUnambiguousTitle(vocabularies, groupId, locale);
	}

	/**
	 * Returns the user ID of this asset vocabulary.
	 *
	 * @return the user ID of this asset vocabulary
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this asset vocabulary.
	 *
	 * @return the user name of this asset vocabulary
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this asset vocabulary.
	 *
	 * @return the user uuid of this asset vocabulary
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this asset vocabulary.
	 *
	 * @return the uuid of this asset vocabulary
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the vocabulary ID of this asset vocabulary.
	 *
	 * @return the vocabulary ID of this asset vocabulary
	 */
	@Override
	public long getVocabularyId() {
		return model.getVocabularyId();
	}

	@Override
	public boolean hasMoreThanOneCategorySelected(long[] categoryIds) {
		return model.hasMoreThanOneCategorySelected(categoryIds);
	}

	@Override
	public boolean isAssociatedToClassNameId(long classNameId) {
		return model.isAssociatedToClassNameId(classNameId);
	}

	@Override
	public boolean isAssociatedToClassNameIdAndClassTypePK(
		long classNameId, long classTypePK) {

		return model.isAssociatedToClassNameIdAndClassTypePK(
			classNameId, classTypePK);
	}

	@Override
	public boolean isMissingRequiredCategory(
		long classNameId, long classTypePK, long[] categoryIds) {

		return model.isMissingRequiredCategory(
			classNameId, classTypePK, categoryIds);
	}

	@Override
	public boolean isMultiValued() {
		return model.isMultiValued();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #isRequired(long, long)}
	 */
	@Deprecated
	@Override
	public boolean isRequired(long classNameId) {
		return model.isRequired(classNameId);
	}

	@Override
	public boolean isRequired(long classNameId, long classTypePK) {
		return model.isRequired(classNameId, classTypePK);
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset vocabulary model instance should use the <code>AssetVocabulary</code> interface instead.
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
	 * Sets the company ID of this asset vocabulary.
	 *
	 * @param companyId the company ID of this asset vocabulary
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this asset vocabulary.
	 *
	 * @param createDate the create date of this asset vocabulary
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this asset vocabulary.
	 *
	 * @param ctCollectionId the ct collection ID of this asset vocabulary
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this asset vocabulary.
	 *
	 * @param description the description of this asset vocabulary
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the localized description of this asset vocabulary in the language.
	 *
	 * @param description the localized description of this asset vocabulary
	 * @param locale the locale of the language
	 */
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	 * Sets the localized description of this asset vocabulary in the language, and sets the default locale.
	 *
	 * @param description the localized description of this asset vocabulary
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
	 * Sets the localized descriptions of this asset vocabulary from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this asset vocabulary
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		model.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the localized descriptions of this asset vocabulary from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this asset vocabulary
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {

		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	 * Sets the external reference code of this asset vocabulary.
	 *
	 * @param externalReferenceCode the external reference code of this asset vocabulary
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this asset vocabulary.
	 *
	 * @param groupId the group ID of this asset vocabulary
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this asset vocabulary.
	 *
	 * @param lastPublishDate the last publish date of this asset vocabulary
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this asset vocabulary.
	 *
	 * @param modifiedDate the modified date of this asset vocabulary
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this asset vocabulary.
	 *
	 * @param mvccVersion the mvcc version of this asset vocabulary
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this asset vocabulary.
	 *
	 * @param name the name of this asset vocabulary
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this asset vocabulary.
	 *
	 * @param primaryKey the primary key of this asset vocabulary
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the settings of this asset vocabulary.
	 *
	 * @param settings the settings of this asset vocabulary
	 */
	@Override
	public void setSettings(String settings) {
		model.setSettings(settings);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties) {

		model.setSettingsProperties(settingsProperties);
	}

	/**
	 * Sets the title of this asset vocabulary.
	 *
	 * @param title the title of this asset vocabulary
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the localized title of this asset vocabulary in the language.
	 *
	 * @param title the localized title of this asset vocabulary
	 * @param locale the locale of the language
	 */
	@Override
	public void setTitle(String title, java.util.Locale locale) {
		model.setTitle(title, locale);
	}

	/**
	 * Sets the localized title of this asset vocabulary in the language, and sets the default locale.
	 *
	 * @param title the localized title of this asset vocabulary
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
	 * Sets the localized titles of this asset vocabulary from the map of locales and localized titles.
	 *
	 * @param titleMap the locales and localized titles of this asset vocabulary
	 */
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		model.setTitleMap(titleMap);
	}

	/**
	 * Sets the localized titles of this asset vocabulary from the map of locales and localized titles, and sets the default locale.
	 *
	 * @param titleMap the locales and localized titles of this asset vocabulary
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitleMap(
		Map<java.util.Locale, String> titleMap,
		java.util.Locale defaultLocale) {

		model.setTitleMap(titleMap, defaultLocale);
	}

	/**
	 * Sets the user ID of this asset vocabulary.
	 *
	 * @param userId the user ID of this asset vocabulary
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this asset vocabulary.
	 *
	 * @param userName the user name of this asset vocabulary
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this asset vocabulary.
	 *
	 * @param userUuid the user uuid of this asset vocabulary
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this asset vocabulary.
	 *
	 * @param uuid the uuid of this asset vocabulary
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the vocabulary ID of this asset vocabulary.
	 *
	 * @param vocabularyId the vocabulary ID of this asset vocabulary
	 */
	@Override
	public void setVocabularyId(long vocabularyId) {
		model.setVocabularyId(vocabularyId);
	}

	@Override
	public Map<String, Function<AssetVocabulary, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<AssetVocabulary, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected AssetVocabularyWrapper wrap(AssetVocabulary assetVocabulary) {
		return new AssetVocabularyWrapper(assetVocabulary);
	}

}