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

package com.liferay.portlet.mobiledevicerules.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MDRRule}.
 * </p>
 *
 * @author    Edward C. Han
 * @see       MDRRule
 * @generated
 */
public class MDRRuleWrapper implements MDRRule, ModelWrapper<MDRRule> {
	public MDRRuleWrapper(MDRRule mdrRule) {
		_mdrRule = mdrRule;
	}

	public Class<?> getModelClass() {
		return MDRRule.class;
	}

	public String getModelClassName() {
		return MDRRule.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("ruleId", getRuleId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("ruleGroupId", getRuleGroupId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long ruleId = (Long)attributes.get("ruleId");

		if (ruleId != null) {
			setRuleId(ruleId);
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

		Long ruleGroupId = (Long)attributes.get("ruleGroupId");

		if (ruleGroupId != null) {
			setRuleGroupId(ruleGroupId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}
	}

	/**
	* Returns the primary key of this m d r rule.
	*
	* @return the primary key of this m d r rule
	*/
	public long getPrimaryKey() {
		return _mdrRule.getPrimaryKey();
	}

	/**
	* Sets the primary key of this m d r rule.
	*
	* @param primaryKey the primary key of this m d r rule
	*/
	public void setPrimaryKey(long primaryKey) {
		_mdrRule.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this m d r rule.
	*
	* @return the uuid of this m d r rule
	*/
	public java.lang.String getUuid() {
		return _mdrRule.getUuid();
	}

	/**
	* Sets the uuid of this m d r rule.
	*
	* @param uuid the uuid of this m d r rule
	*/
	public void setUuid(java.lang.String uuid) {
		_mdrRule.setUuid(uuid);
	}

	/**
	* Returns the rule ID of this m d r rule.
	*
	* @return the rule ID of this m d r rule
	*/
	public long getRuleId() {
		return _mdrRule.getRuleId();
	}

	/**
	* Sets the rule ID of this m d r rule.
	*
	* @param ruleId the rule ID of this m d r rule
	*/
	public void setRuleId(long ruleId) {
		_mdrRule.setRuleId(ruleId);
	}

	/**
	* Returns the group ID of this m d r rule.
	*
	* @return the group ID of this m d r rule
	*/
	public long getGroupId() {
		return _mdrRule.getGroupId();
	}

	/**
	* Sets the group ID of this m d r rule.
	*
	* @param groupId the group ID of this m d r rule
	*/
	public void setGroupId(long groupId) {
		_mdrRule.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this m d r rule.
	*
	* @return the company ID of this m d r rule
	*/
	public long getCompanyId() {
		return _mdrRule.getCompanyId();
	}

	/**
	* Sets the company ID of this m d r rule.
	*
	* @param companyId the company ID of this m d r rule
	*/
	public void setCompanyId(long companyId) {
		_mdrRule.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this m d r rule.
	*
	* @return the user ID of this m d r rule
	*/
	public long getUserId() {
		return _mdrRule.getUserId();
	}

	/**
	* Sets the user ID of this m d r rule.
	*
	* @param userId the user ID of this m d r rule
	*/
	public void setUserId(long userId) {
		_mdrRule.setUserId(userId);
	}

	/**
	* Returns the user uuid of this m d r rule.
	*
	* @return the user uuid of this m d r rule
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRule.getUserUuid();
	}

	/**
	* Sets the user uuid of this m d r rule.
	*
	* @param userUuid the user uuid of this m d r rule
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_mdrRule.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this m d r rule.
	*
	* @return the user name of this m d r rule
	*/
	public java.lang.String getUserName() {
		return _mdrRule.getUserName();
	}

	/**
	* Sets the user name of this m d r rule.
	*
	* @param userName the user name of this m d r rule
	*/
	public void setUserName(java.lang.String userName) {
		_mdrRule.setUserName(userName);
	}

	/**
	* Returns the create date of this m d r rule.
	*
	* @return the create date of this m d r rule
	*/
	public java.util.Date getCreateDate() {
		return _mdrRule.getCreateDate();
	}

	/**
	* Sets the create date of this m d r rule.
	*
	* @param createDate the create date of this m d r rule
	*/
	public void setCreateDate(java.util.Date createDate) {
		_mdrRule.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this m d r rule.
	*
	* @return the modified date of this m d r rule
	*/
	public java.util.Date getModifiedDate() {
		return _mdrRule.getModifiedDate();
	}

	/**
	* Sets the modified date of this m d r rule.
	*
	* @param modifiedDate the modified date of this m d r rule
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mdrRule.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the rule group ID of this m d r rule.
	*
	* @return the rule group ID of this m d r rule
	*/
	public long getRuleGroupId() {
		return _mdrRule.getRuleGroupId();
	}

	/**
	* Sets the rule group ID of this m d r rule.
	*
	* @param ruleGroupId the rule group ID of this m d r rule
	*/
	public void setRuleGroupId(long ruleGroupId) {
		_mdrRule.setRuleGroupId(ruleGroupId);
	}

	/**
	* Returns the name of this m d r rule.
	*
	* @return the name of this m d r rule
	*/
	public java.lang.String getName() {
		return _mdrRule.getName();
	}

	/**
	* Returns the localized name of this m d r rule in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this m d r rule
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _mdrRule.getName(locale);
	}

	/**
	* Returns the localized name of this m d r rule in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this m d r rule. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _mdrRule.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this m d r rule in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this m d r rule
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _mdrRule.getName(languageId);
	}

	/**
	* Returns the localized name of this m d r rule in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this m d r rule
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _mdrRule.getName(languageId, useDefault);
	}

	public java.lang.String getNameCurrentLanguageId() {
		return _mdrRule.getNameCurrentLanguageId();
	}

	public java.lang.String getNameCurrentValue() {
		return _mdrRule.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this m d r rule.
	*
	* @return the locales and localized names of this m d r rule
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _mdrRule.getNameMap();
	}

	/**
	* Sets the name of this m d r rule.
	*
	* @param name the name of this m d r rule
	*/
	public void setName(java.lang.String name) {
		_mdrRule.setName(name);
	}

	/**
	* Sets the localized name of this m d r rule in the language.
	*
	* @param name the localized name of this m d r rule
	* @param locale the locale of the language
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_mdrRule.setName(name, locale);
	}

	/**
	* Sets the localized name of this m d r rule in the language, and sets the default locale.
	*
	* @param name the localized name of this m d r rule
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_mdrRule.setName(name, locale, defaultLocale);
	}

	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_mdrRule.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this m d r rule from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this m d r rule
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_mdrRule.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this m d r rule from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this m d r rule
	* @param defaultLocale the default locale
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_mdrRule.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this m d r rule.
	*
	* @return the description of this m d r rule
	*/
	public java.lang.String getDescription() {
		return _mdrRule.getDescription();
	}

	/**
	* Returns the localized description of this m d r rule in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this m d r rule
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _mdrRule.getDescription(locale);
	}

	/**
	* Returns the localized description of this m d r rule in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this m d r rule. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _mdrRule.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this m d r rule in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this m d r rule
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _mdrRule.getDescription(languageId);
	}

	/**
	* Returns the localized description of this m d r rule in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this m d r rule
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _mdrRule.getDescription(languageId, useDefault);
	}

	public java.lang.String getDescriptionCurrentLanguageId() {
		return _mdrRule.getDescriptionCurrentLanguageId();
	}

	public java.lang.String getDescriptionCurrentValue() {
		return _mdrRule.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this m d r rule.
	*
	* @return the locales and localized descriptions of this m d r rule
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _mdrRule.getDescriptionMap();
	}

	/**
	* Sets the description of this m d r rule.
	*
	* @param description the description of this m d r rule
	*/
	public void setDescription(java.lang.String description) {
		_mdrRule.setDescription(description);
	}

	/**
	* Sets the localized description of this m d r rule in the language.
	*
	* @param description the localized description of this m d r rule
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_mdrRule.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this m d r rule in the language, and sets the default locale.
	*
	* @param description the localized description of this m d r rule
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_mdrRule.setDescription(description, locale, defaultLocale);
	}

	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_mdrRule.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this m d r rule from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this m d r rule
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_mdrRule.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this m d r rule from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this m d r rule
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_mdrRule.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the type of this m d r rule.
	*
	* @return the type of this m d r rule
	*/
	public java.lang.String getType() {
		return _mdrRule.getType();
	}

	/**
	* Sets the type of this m d r rule.
	*
	* @param type the type of this m d r rule
	*/
	public void setType(java.lang.String type) {
		_mdrRule.setType(type);
	}

	/**
	* Returns the type settings of this m d r rule.
	*
	* @return the type settings of this m d r rule
	*/
	public java.lang.String getTypeSettings() {
		return _mdrRule.getTypeSettings();
	}

	/**
	* Sets the type settings of this m d r rule.
	*
	* @param typeSettings the type settings of this m d r rule
	*/
	public void setTypeSettings(java.lang.String typeSettings) {
		_mdrRule.setTypeSettings(typeSettings);
	}

	public boolean isNew() {
		return _mdrRule.isNew();
	}

	public void setNew(boolean n) {
		_mdrRule.setNew(n);
	}

	public boolean isCachedModel() {
		return _mdrRule.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mdrRule.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mdrRule.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mdrRule.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mdrRule.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mdrRule.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mdrRule.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MDRRuleWrapper((MDRRule)_mdrRule.clone());
	}

	public int compareTo(
		com.liferay.portlet.mobiledevicerules.model.MDRRule mdrRule) {
		return _mdrRule.compareTo(mdrRule);
	}

	@Override
	public int hashCode() {
		return _mdrRule.hashCode();
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale) {
		_mdrRule.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.mobiledevicerules.model.MDRRule> toCacheModel() {
		return _mdrRule.toCacheModel();
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule toEscapedModel() {
		return new MDRRuleWrapper(_mdrRule.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mdrRule.toString();
	}

	public java.lang.String toXmlString() {
		return _mdrRule.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrRule.persist();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _mdrRule.getTypeSettingsProperties();
	}

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_mdrRule.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public MDRRule getWrappedMDRRule() {
		return _mdrRule;
	}

	public MDRRule getWrappedModel() {
		return _mdrRule;
	}

	public void resetOriginalValues() {
		_mdrRule.resetOriginalValues();
	}

	private MDRRule _mdrRule;
}