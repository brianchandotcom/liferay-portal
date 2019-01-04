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

package com.liferay.mobile.device.rules.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MDRRuleGroup}.
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleGroup
 * @generated
 */
@ProviderType
public class MDRRuleGroupWrapper extends BaseModelWrapper<MDRRuleGroup>
	implements MDRRuleGroup, ModelWrapper<MDRRuleGroup> {
	public MDRRuleGroupWrapper(MDRRuleGroup mdrRuleGroup) {
		super(mdrRuleGroup);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this mdr rule group.
	*
	* @return the company ID of this mdr rule group
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this mdr rule group.
	*
	* @return the create date of this mdr rule group
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
	* Returns the description of this mdr rule group.
	*
	* @return the description of this mdr rule group
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the localized description of this mdr rule group in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this mdr rule group
	*/
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	* Returns the localized description of this mdr rule group in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this mdr rule group. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this mdr rule group in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this mdr rule group
	*/
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	* Returns the localized description of this mdr rule group in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this mdr rule group
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
	* Returns a map of the locales and localized descriptions of this mdr rule group.
	*
	* @return the locales and localized descriptions of this mdr rule group
	*/
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	* Returns the group ID of this mdr rule group.
	*
	* @return the group ID of this mdr rule group
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the last publish date of this mdr rule group.
	*
	* @return the last publish date of this mdr rule group
	*/
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	* Returns the modified date of this mdr rule group.
	*
	* @return the modified date of this mdr rule group
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the name of this mdr rule group.
	*
	* @return the name of this mdr rule group
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the localized name of this mdr rule group in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this mdr rule group
	*/
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	* Returns the localized name of this mdr rule group in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this mdr rule group. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this mdr rule group in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this mdr rule group
	*/
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	* Returns the localized name of this mdr rule group in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this mdr rule group
	*/
	@Override
	public String getName(String languageId, boolean useDefault) {
		return model.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return model.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return model.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this mdr rule group.
	*
	* @return the locales and localized names of this mdr rule group
	*/
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	* Returns the primary key of this mdr rule group.
	*
	* @return the primary key of this mdr rule group
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the rule group ID of this mdr rule group.
	*
	* @return the rule group ID of this mdr rule group
	*/
	@Override
	public long getRuleGroupId() {
		return model.getRuleGroupId();
	}

	@Override
	public java.util.List<MDRRule> getRules() {
		return model.getRules();
	}

	/**
	* Returns the user ID of this mdr rule group.
	*
	* @return the user ID of this mdr rule group
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this mdr rule group.
	*
	* @return the user name of this mdr rule group
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this mdr rule group.
	*
	* @return the user uuid of this mdr rule group
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this mdr rule group.
	*
	* @return the uuid of this mdr rule group
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
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
	* Sets the company ID of this mdr rule group.
	*
	* @param companyId the company ID of this mdr rule group
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this mdr rule group.
	*
	* @param createDate the create date of this mdr rule group
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the description of this mdr rule group.
	*
	* @param description the description of this mdr rule group
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the localized description of this mdr rule group in the language.
	*
	* @param description the localized description of this mdr rule group
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this mdr rule group in the language, and sets the default locale.
	*
	* @param description the localized description of this mdr rule group
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		model.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		model.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this mdr rule group from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this mdr rule group
	*/
	@Override
	public void setDescriptionMap(Map<java.util.Locale, String> descriptionMap) {
		model.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this mdr rule group from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this mdr rule group
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {
		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Sets the group ID of this mdr rule group.
	*
	* @param groupId the group ID of this mdr rule group
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this mdr rule group.
	*
	* @param lastPublishDate the last publish date of this mdr rule group
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this mdr rule group.
	*
	* @param modifiedDate the modified date of this mdr rule group
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this mdr rule group.
	*
	* @param name the name of this mdr rule group
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the localized name of this mdr rule group in the language.
	*
	* @param name the localized name of this mdr rule group
	* @param locale the locale of the language
	*/
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	* Sets the localized name of this mdr rule group in the language, and sets the default locale.
	*
	* @param name the localized name of this mdr rule group
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		model.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this mdr rule group from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this mdr rule group
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this mdr rule group from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this mdr rule group
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap,
		java.util.Locale defaultLocale) {
		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Sets the primary key of this mdr rule group.
	*
	* @param primaryKey the primary key of this mdr rule group
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the rule group ID of this mdr rule group.
	*
	* @param ruleGroupId the rule group ID of this mdr rule group
	*/
	@Override
	public void setRuleGroupId(long ruleGroupId) {
		model.setRuleGroupId(ruleGroupId);
	}

	/**
	* Sets the user ID of this mdr rule group.
	*
	* @param userId the user ID of this mdr rule group
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this mdr rule group.
	*
	* @param userName the user name of this mdr rule group
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this mdr rule group.
	*
	* @param userUuid the user uuid of this mdr rule group
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this mdr rule group.
	*
	* @param uuid the uuid of this mdr rule group
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
	protected MDRRuleGroupWrapper wrap(MDRRuleGroup mdrRuleGroup) {
		return new MDRRuleGroupWrapper(mdrRuleGroup);
	}
}