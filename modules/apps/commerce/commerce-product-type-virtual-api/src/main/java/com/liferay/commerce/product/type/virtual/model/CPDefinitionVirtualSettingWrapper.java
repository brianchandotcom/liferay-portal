/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CPDefinitionVirtualSetting}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSetting
 * @generated
 */
public class CPDefinitionVirtualSettingWrapper
	extends BaseModelWrapper<CPDefinitionVirtualSetting>
	implements CPDefinitionVirtualSetting,
			   ModelWrapper<CPDefinitionVirtualSetting> {

	public CPDefinitionVirtualSettingWrapper(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {

		super(cpDefinitionVirtualSetting);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"CPDefinitionVirtualSettingId", getCPDefinitionVirtualSettingId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("url", getUrl());
		attributes.put("activationStatus", getActivationStatus());
		attributes.put("duration", getDuration());
		attributes.put("maxUsages", getMaxUsages());
		attributes.put("useSample", isUseSample());
		attributes.put("sampleFileEntryId", getSampleFileEntryId());
		attributes.put("sampleURL", getSampleURL());
		attributes.put("termsOfUseRequired", isTermsOfUseRequired());
		attributes.put("termsOfUseContent", getTermsOfUseContent());
		attributes.put(
			"termsOfUseJournalArticleResourcePrimKey",
			getTermsOfUseJournalArticleResourcePrimKey());
		attributes.put("override", isOverride());
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

		Long CPDefinitionVirtualSettingId = (Long)attributes.get(
			"CPDefinitionVirtualSettingId");

		if (CPDefinitionVirtualSettingId != null) {
			setCPDefinitionVirtualSettingId(CPDefinitionVirtualSettingId);
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

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		Integer activationStatus = (Integer)attributes.get("activationStatus");

		if (activationStatus != null) {
			setActivationStatus(activationStatus);
		}

		Long duration = (Long)attributes.get("duration");

		if (duration != null) {
			setDuration(duration);
		}

		Integer maxUsages = (Integer)attributes.get("maxUsages");

		if (maxUsages != null) {
			setMaxUsages(maxUsages);
		}

		Boolean useSample = (Boolean)attributes.get("useSample");

		if (useSample != null) {
			setUseSample(useSample);
		}

		Long sampleFileEntryId = (Long)attributes.get("sampleFileEntryId");

		if (sampleFileEntryId != null) {
			setSampleFileEntryId(sampleFileEntryId);
		}

		String sampleURL = (String)attributes.get("sampleURL");

		if (sampleURL != null) {
			setSampleURL(sampleURL);
		}

		Boolean termsOfUseRequired = (Boolean)attributes.get(
			"termsOfUseRequired");

		if (termsOfUseRequired != null) {
			setTermsOfUseRequired(termsOfUseRequired);
		}

		String termsOfUseContent = (String)attributes.get("termsOfUseContent");

		if (termsOfUseContent != null) {
			setTermsOfUseContent(termsOfUseContent);
		}

		Long termsOfUseJournalArticleResourcePrimKey = (Long)attributes.get(
			"termsOfUseJournalArticleResourcePrimKey");

		if (termsOfUseJournalArticleResourcePrimKey != null) {
			setTermsOfUseJournalArticleResourcePrimKey(
				termsOfUseJournalArticleResourcePrimKey);
		}

		Boolean override = (Boolean)attributes.get("override");

		if (override != null) {
			setOverride(override);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public CPDefinitionVirtualSetting cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the activation status of this cp definition virtual setting.
	 *
	 * @return the activation status of this cp definition virtual setting
	 */
	@Override
	public int getActivationStatus() {
		return model.getActivationStatus();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the fully qualified class name of this cp definition virtual setting.
	 *
	 * @return the fully qualified class name of this cp definition virtual setting
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this cp definition virtual setting.
	 *
	 * @return the class name ID of this cp definition virtual setting
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this cp definition virtual setting.
	 *
	 * @return the class pk of this cp definition virtual setting
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this cp definition virtual setting.
	 *
	 * @return the company ID of this cp definition virtual setting
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp definition virtual setting ID of this cp definition virtual setting.
	 *
	 * @return the cp definition virtual setting ID of this cp definition virtual setting
	 */
	@Override
	public long getCPDefinitionVirtualSettingId() {
		return model.getCPDefinitionVirtualSettingId();
	}

	/**
	 * Returns the create date of this cp definition virtual setting.
	 *
	 * @return the create date of this cp definition virtual setting
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
	 * Returns the duration of this cp definition virtual setting.
	 *
	 * @return the duration of this cp definition virtual setting
	 */
	@Override
	public long getDuration() {
		return model.getDuration();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFileEntry();
	}

	/**
	 * Returns the file entry ID of this cp definition virtual setting.
	 *
	 * @return the file entry ID of this cp definition virtual setting
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the group ID of this cp definition virtual setting.
	 *
	 * @return the group ID of this cp definition virtual setting
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this cp definition virtual setting.
	 *
	 * @return the last publish date of this cp definition virtual setting
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the max usages of this cp definition virtual setting.
	 *
	 * @return the max usages of this cp definition virtual setting
	 */
	@Override
	public int getMaxUsages() {
		return model.getMaxUsages();
	}

	/**
	 * Returns the modified date of this cp definition virtual setting.
	 *
	 * @return the modified date of this cp definition virtual setting
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp definition virtual setting.
	 *
	 * @return the mvcc version of this cp definition virtual setting
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the override of this cp definition virtual setting.
	 *
	 * @return the override of this cp definition virtual setting
	 */
	@Override
	public boolean getOverride() {
		return model.getOverride();
	}

	/**
	 * Returns the primary key of this cp definition virtual setting.
	 *
	 * @return the primary key of this cp definition virtual setting
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry
			getSampleFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getSampleFileEntry();
	}

	/**
	 * Returns the sample file entry ID of this cp definition virtual setting.
	 *
	 * @return the sample file entry ID of this cp definition virtual setting
	 */
	@Override
	public long getSampleFileEntryId() {
		return model.getSampleFileEntryId();
	}

	/**
	 * Returns the sample url of this cp definition virtual setting.
	 *
	 * @return the sample url of this cp definition virtual setting
	 */
	@Override
	public String getSampleURL() {
		return model.getSampleURL();
	}

	/**
	 * Returns the terms of use content of this cp definition virtual setting.
	 *
	 * @return the terms of use content of this cp definition virtual setting
	 */
	@Override
	public String getTermsOfUseContent() {
		return model.getTermsOfUseContent();
	}

	/**
	 * Returns the localized terms of use content of this cp definition virtual setting in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized terms of use content of this cp definition virtual setting
	 */
	@Override
	public String getTermsOfUseContent(java.util.Locale locale) {
		return model.getTermsOfUseContent(locale);
	}

	/**
	 * Returns the localized terms of use content of this cp definition virtual setting in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized terms of use content of this cp definition virtual setting. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getTermsOfUseContent(
		java.util.Locale locale, boolean useDefault) {

		return model.getTermsOfUseContent(locale, useDefault);
	}

	/**
	 * Returns the localized terms of use content of this cp definition virtual setting in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized terms of use content of this cp definition virtual setting
	 */
	@Override
	public String getTermsOfUseContent(String languageId) {
		return model.getTermsOfUseContent(languageId);
	}

	/**
	 * Returns the localized terms of use content of this cp definition virtual setting in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized terms of use content of this cp definition virtual setting
	 */
	@Override
	public String getTermsOfUseContent(String languageId, boolean useDefault) {
		return model.getTermsOfUseContent(languageId, useDefault);
	}

	@Override
	public String getTermsOfUseContentCurrentLanguageId() {
		return model.getTermsOfUseContentCurrentLanguageId();
	}

	@Override
	public String getTermsOfUseContentCurrentValue() {
		return model.getTermsOfUseContentCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized terms of use contents of this cp definition virtual setting.
	 *
	 * @return the locales and localized terms of use contents of this cp definition virtual setting
	 */
	@Override
	public Map<java.util.Locale, String> getTermsOfUseContentMap() {
		return model.getTermsOfUseContentMap();
	}

	@Override
	public com.liferay.journal.model.JournalArticle
			getTermsOfUseJournalArticle()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTermsOfUseJournalArticle();
	}

	/**
	 * Returns the terms of use journal article resource prim key of this cp definition virtual setting.
	 *
	 * @return the terms of use journal article resource prim key of this cp definition virtual setting
	 */
	@Override
	public long getTermsOfUseJournalArticleResourcePrimKey() {
		return model.getTermsOfUseJournalArticleResourcePrimKey();
	}

	/**
	 * Returns the terms of use required of this cp definition virtual setting.
	 *
	 * @return the terms of use required of this cp definition virtual setting
	 */
	@Override
	public boolean getTermsOfUseRequired() {
		return model.getTermsOfUseRequired();
	}

	/**
	 * Returns the url of this cp definition virtual setting.
	 *
	 * @return the url of this cp definition virtual setting
	 */
	@Override
	public String getUrl() {
		return model.getUrl();
	}

	/**
	 * Returns the user ID of this cp definition virtual setting.
	 *
	 * @return the user ID of this cp definition virtual setting
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp definition virtual setting.
	 *
	 * @return the user name of this cp definition virtual setting
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp definition virtual setting.
	 *
	 * @return the user uuid of this cp definition virtual setting
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the use sample of this cp definition virtual setting.
	 *
	 * @return the use sample of this cp definition virtual setting
	 */
	@Override
	public boolean getUseSample() {
		return model.getUseSample();
	}

	/**
	 * Returns the uuid of this cp definition virtual setting.
	 *
	 * @return the uuid of this cp definition virtual setting
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this cp definition virtual setting is override.
	 *
	 * @return <code>true</code> if this cp definition virtual setting is override; <code>false</code> otherwise
	 */
	@Override
	public boolean isOverride() {
		return model.isOverride();
	}

	/**
	 * Returns <code>true</code> if this cp definition virtual setting is terms of use required.
	 *
	 * @return <code>true</code> if this cp definition virtual setting is terms of use required; <code>false</code> otherwise
	 */
	@Override
	public boolean isTermsOfUseRequired() {
		return model.isTermsOfUseRequired();
	}

	/**
	 * Returns <code>true</code> if this cp definition virtual setting is use sample.
	 *
	 * @return <code>true</code> if this cp definition virtual setting is use sample; <code>false</code> otherwise
	 */
	@Override
	public boolean isUseSample() {
		return model.isUseSample();
	}

	@Override
	public boolean isUseSampleURL() {
		return model.isUseSampleURL();
	}

	@Override
	public boolean isUseTermsOfUseJournal() {
		return model.isUseTermsOfUseJournal();
	}

	@Override
	public boolean isUseUrl() {
		return model.isUseUrl();
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
	 * Sets the activation status of this cp definition virtual setting.
	 *
	 * @param activationStatus the activation status of this cp definition virtual setting
	 */
	@Override
	public void setActivationStatus(int activationStatus) {
		model.setActivationStatus(activationStatus);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this cp definition virtual setting.
	 *
	 * @param classNameId the class name ID of this cp definition virtual setting
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this cp definition virtual setting.
	 *
	 * @param classPK the class pk of this cp definition virtual setting
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this cp definition virtual setting.
	 *
	 * @param companyId the company ID of this cp definition virtual setting
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition virtual setting ID of this cp definition virtual setting.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID of this cp definition virtual setting
	 */
	@Override
	public void setCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId) {

		model.setCPDefinitionVirtualSettingId(CPDefinitionVirtualSettingId);
	}

	/**
	 * Sets the create date of this cp definition virtual setting.
	 *
	 * @param createDate the create date of this cp definition virtual setting
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the duration of this cp definition virtual setting.
	 *
	 * @param duration the duration of this cp definition virtual setting
	 */
	@Override
	public void setDuration(long duration) {
		model.setDuration(duration);
	}

	/**
	 * Sets the file entry ID of this cp definition virtual setting.
	 *
	 * @param fileEntryId the file entry ID of this cp definition virtual setting
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the group ID of this cp definition virtual setting.
	 *
	 * @param groupId the group ID of this cp definition virtual setting
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this cp definition virtual setting.
	 *
	 * @param lastPublishDate the last publish date of this cp definition virtual setting
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the max usages of this cp definition virtual setting.
	 *
	 * @param maxUsages the max usages of this cp definition virtual setting
	 */
	@Override
	public void setMaxUsages(int maxUsages) {
		model.setMaxUsages(maxUsages);
	}

	/**
	 * Sets the modified date of this cp definition virtual setting.
	 *
	 * @param modifiedDate the modified date of this cp definition virtual setting
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp definition virtual setting.
	 *
	 * @param mvccVersion the mvcc version of this cp definition virtual setting
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets whether this cp definition virtual setting is override.
	 *
	 * @param override the override of this cp definition virtual setting
	 */
	@Override
	public void setOverride(boolean override) {
		model.setOverride(override);
	}

	/**
	 * Sets the primary key of this cp definition virtual setting.
	 *
	 * @param primaryKey the primary key of this cp definition virtual setting
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the sample file entry ID of this cp definition virtual setting.
	 *
	 * @param sampleFileEntryId the sample file entry ID of this cp definition virtual setting
	 */
	@Override
	public void setSampleFileEntryId(long sampleFileEntryId) {
		model.setSampleFileEntryId(sampleFileEntryId);
	}

	/**
	 * Sets the sample url of this cp definition virtual setting.
	 *
	 * @param sampleURL the sample url of this cp definition virtual setting
	 */
	@Override
	public void setSampleURL(String sampleURL) {
		model.setSampleURL(sampleURL);
	}

	/**
	 * Sets the terms of use content of this cp definition virtual setting.
	 *
	 * @param termsOfUseContent the terms of use content of this cp definition virtual setting
	 */
	@Override
	public void setTermsOfUseContent(String termsOfUseContent) {
		model.setTermsOfUseContent(termsOfUseContent);
	}

	/**
	 * Sets the localized terms of use content of this cp definition virtual setting in the language.
	 *
	 * @param termsOfUseContent the localized terms of use content of this cp definition virtual setting
	 * @param locale the locale of the language
	 */
	@Override
	public void setTermsOfUseContent(
		String termsOfUseContent, java.util.Locale locale) {

		model.setTermsOfUseContent(termsOfUseContent, locale);
	}

	/**
	 * Sets the localized terms of use content of this cp definition virtual setting in the language, and sets the default locale.
	 *
	 * @param termsOfUseContent the localized terms of use content of this cp definition virtual setting
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTermsOfUseContent(
		String termsOfUseContent, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setTermsOfUseContent(termsOfUseContent, locale, defaultLocale);
	}

	@Override
	public void setTermsOfUseContentCurrentLanguageId(String languageId) {
		model.setTermsOfUseContentCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized terms of use contents of this cp definition virtual setting from the map of locales and localized terms of use contents.
	 *
	 * @param termsOfUseContentMap the locales and localized terms of use contents of this cp definition virtual setting
	 */
	@Override
	public void setTermsOfUseContentMap(
		Map<java.util.Locale, String> termsOfUseContentMap) {

		model.setTermsOfUseContentMap(termsOfUseContentMap);
	}

	/**
	 * Sets the localized terms of use contents of this cp definition virtual setting from the map of locales and localized terms of use contents, and sets the default locale.
	 *
	 * @param termsOfUseContentMap the locales and localized terms of use contents of this cp definition virtual setting
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTermsOfUseContentMap(
		Map<java.util.Locale, String> termsOfUseContentMap,
		java.util.Locale defaultLocale) {

		model.setTermsOfUseContentMap(termsOfUseContentMap, defaultLocale);
	}

	/**
	 * Sets the terms of use journal article resource prim key of this cp definition virtual setting.
	 *
	 * @param termsOfUseJournalArticleResourcePrimKey the terms of use journal article resource prim key of this cp definition virtual setting
	 */
	@Override
	public void setTermsOfUseJournalArticleResourcePrimKey(
		long termsOfUseJournalArticleResourcePrimKey) {

		model.setTermsOfUseJournalArticleResourcePrimKey(
			termsOfUseJournalArticleResourcePrimKey);
	}

	/**
	 * Sets whether this cp definition virtual setting is terms of use required.
	 *
	 * @param termsOfUseRequired the terms of use required of this cp definition virtual setting
	 */
	@Override
	public void setTermsOfUseRequired(boolean termsOfUseRequired) {
		model.setTermsOfUseRequired(termsOfUseRequired);
	}

	/**
	 * Sets the url of this cp definition virtual setting.
	 *
	 * @param url the url of this cp definition virtual setting
	 */
	@Override
	public void setUrl(String url) {
		model.setUrl(url);
	}

	/**
	 * Sets the user ID of this cp definition virtual setting.
	 *
	 * @param userId the user ID of this cp definition virtual setting
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp definition virtual setting.
	 *
	 * @param userName the user name of this cp definition virtual setting
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp definition virtual setting.
	 *
	 * @param userUuid the user uuid of this cp definition virtual setting
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets whether this cp definition virtual setting is use sample.
	 *
	 * @param useSample the use sample of this cp definition virtual setting
	 */
	@Override
	public void setUseSample(boolean useSample) {
		model.setUseSample(useSample);
	}

	/**
	 * Sets the uuid of this cp definition virtual setting.
	 *
	 * @param uuid the uuid of this cp definition virtual setting
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
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPDefinitionVirtualSettingWrapper wrap(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {

		return new CPDefinitionVirtualSettingWrapper(
			cpDefinitionVirtualSetting);
	}

}