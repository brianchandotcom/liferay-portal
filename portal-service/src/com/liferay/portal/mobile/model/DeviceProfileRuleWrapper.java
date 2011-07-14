/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.mobile.model;

/**
 * <p>
 * This class is a wrapper for {@link DeviceProfileRule}.
 * </p>
 *
 * @author    Edward C. Han
 * @see       DeviceProfileRule
 * @generated
 */
public class DeviceProfileRuleWrapper implements DeviceProfileRule {
	public DeviceProfileRuleWrapper(DeviceProfileRule deviceProfileRule) {
		_deviceProfileRule = deviceProfileRule;
	}

	public Class<?> getModelClass() {
		return DeviceProfileRule.class;
	}

	public String getModelClassName() {
		return DeviceProfileRule.class.getName();
	}

	/**
	* Returns the primary key of this device profile rule.
	*
	* @return the primary key of this device profile rule
	*/
	public long getPrimaryKey() {
		return _deviceProfileRule.getPrimaryKey();
	}

	/**
	* Sets the primary key of this device profile rule.
	*
	* @param primaryKey the primary key of this device profile rule
	*/
	public void setPrimaryKey(long primaryKey) {
		_deviceProfileRule.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this device profile rule.
	*
	* @return the uuid of this device profile rule
	*/
	public java.lang.String getUuid() {
		return _deviceProfileRule.getUuid();
	}

	/**
	* Sets the uuid of this device profile rule.
	*
	* @param uuid the uuid of this device profile rule
	*/
	public void setUuid(java.lang.String uuid) {
		_deviceProfileRule.setUuid(uuid);
	}

	/**
	* Returns the device profile rule ID of this device profile rule.
	*
	* @return the device profile rule ID of this device profile rule
	*/
	public long getDeviceProfileRuleId() {
		return _deviceProfileRule.getDeviceProfileRuleId();
	}

	/**
	* Sets the device profile rule ID of this device profile rule.
	*
	* @param deviceProfileRuleId the device profile rule ID of this device profile rule
	*/
	public void setDeviceProfileRuleId(long deviceProfileRuleId) {
		_deviceProfileRule.setDeviceProfileRuleId(deviceProfileRuleId);
	}

	/**
	* Returns the device profile ID of this device profile rule.
	*
	* @return the device profile ID of this device profile rule
	*/
	public long getDeviceProfileId() {
		return _deviceProfileRule.getDeviceProfileId();
	}

	/**
	* Sets the device profile ID of this device profile rule.
	*
	* @param deviceProfileId the device profile ID of this device profile rule
	*/
	public void setDeviceProfileId(long deviceProfileId) {
		_deviceProfileRule.setDeviceProfileId(deviceProfileId);
	}

	/**
	* Returns the name of this device profile rule.
	*
	* @return the name of this device profile rule
	*/
	public java.lang.String getName() {
		return _deviceProfileRule.getName();
	}

	/**
	* Returns the localized name of this device profile rule in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this device profile rule
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _deviceProfileRule.getName(locale);
	}

	/**
	* Returns the localized name of this device profile rule in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this device profile rule. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _deviceProfileRule.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this device profile rule in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this device profile rule
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _deviceProfileRule.getName(languageId);
	}

	/**
	* Returns the localized name of this device profile rule in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this device profile rule
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _deviceProfileRule.getName(languageId, useDefault);
	}

	/**
	* Returns a map of the locales and localized names of this device profile rule.
	*
	* @return the locales and localized names of this device profile rule
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _deviceProfileRule.getNameMap();
	}

	/**
	* Sets the name of this device profile rule.
	*
	* @param name the name of this device profile rule
	*/
	public void setName(java.lang.String name) {
		_deviceProfileRule.setName(name);
	}

	/**
	* Sets the localized name of this device profile rule in the language.
	*
	* @param name the localized name of this device profile rule
	* @param locale the locale of the language
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_deviceProfileRule.setName(name, locale);
	}

	/**
	* Sets the localized name of this device profile rule in the language, and sets the default locale.
	*
	* @param name the localized name of this device profile rule
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_deviceProfileRule.setName(name, locale, defaultLocale);
	}

	/**
	* Sets the localized names of this device profile rule from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this device profile rule
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_deviceProfileRule.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this device profile rule from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this device profile rule
	* @param defaultLocale the default locale
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_deviceProfileRule.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this device profile rule.
	*
	* @return the description of this device profile rule
	*/
	public java.lang.String getDescription() {
		return _deviceProfileRule.getDescription();
	}

	/**
	* Returns the localized description of this device profile rule in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this device profile rule
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _deviceProfileRule.getDescription(locale);
	}

	/**
	* Returns the localized description of this device profile rule in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this device profile rule. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _deviceProfileRule.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this device profile rule in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this device profile rule
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _deviceProfileRule.getDescription(languageId);
	}

	/**
	* Returns the localized description of this device profile rule in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this device profile rule
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _deviceProfileRule.getDescription(languageId, useDefault);
	}

	/**
	* Returns a map of the locales and localized descriptions of this device profile rule.
	*
	* @return the locales and localized descriptions of this device profile rule
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _deviceProfileRule.getDescriptionMap();
	}

	/**
	* Sets the description of this device profile rule.
	*
	* @param description the description of this device profile rule
	*/
	public void setDescription(java.lang.String description) {
		_deviceProfileRule.setDescription(description);
	}

	/**
	* Sets the localized description of this device profile rule in the language.
	*
	* @param description the localized description of this device profile rule
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_deviceProfileRule.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this device profile rule in the language, and sets the default locale.
	*
	* @param description the localized description of this device profile rule
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_deviceProfileRule.setDescription(description, locale, defaultLocale);
	}

	/**
	* Sets the localized descriptions of this device profile rule from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this device profile rule
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_deviceProfileRule.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this device profile rule from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this device profile rule
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_deviceProfileRule.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the rule type of this device profile rule.
	*
	* @return the rule type of this device profile rule
	*/
	public java.lang.String getRuleType() {
		return _deviceProfileRule.getRuleType();
	}

	/**
	* Sets the rule type of this device profile rule.
	*
	* @param ruleType the rule type of this device profile rule
	*/
	public void setRuleType(java.lang.String ruleType) {
		_deviceProfileRule.setRuleType(ruleType);
	}

	/**
	* Returns the rule type settings of this device profile rule.
	*
	* @return the rule type settings of this device profile rule
	*/
	public java.lang.String getRuleTypeSettings() {
		return _deviceProfileRule.getRuleTypeSettings();
	}

	/**
	* Sets the rule type settings of this device profile rule.
	*
	* @param ruleTypeSettings the rule type settings of this device profile rule
	*/
	public void setRuleTypeSettings(java.lang.String ruleTypeSettings) {
		_deviceProfileRule.setRuleTypeSettings(ruleTypeSettings);
	}

	public boolean isNew() {
		return _deviceProfileRule.isNew();
	}

	public void setNew(boolean n) {
		_deviceProfileRule.setNew(n);
	}

	public boolean isCachedModel() {
		return _deviceProfileRule.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_deviceProfileRule.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _deviceProfileRule.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_deviceProfileRule.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _deviceProfileRule.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_deviceProfileRule.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _deviceProfileRule.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_deviceProfileRule.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DeviceProfileRuleWrapper((DeviceProfileRule)_deviceProfileRule.clone());
	}

	public int compareTo(
		com.liferay.portal.mobile.model.DeviceProfileRule deviceProfileRule) {
		return _deviceProfileRule.compareTo(deviceProfileRule);
	}

	@Override
	public int hashCode() {
		return _deviceProfileRule.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.mobile.model.DeviceProfileRule> toCacheModel() {
		return _deviceProfileRule.toCacheModel();
	}

	public com.liferay.portal.mobile.model.DeviceProfileRule toEscapedModel() {
		return new DeviceProfileRuleWrapper(_deviceProfileRule.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _deviceProfileRule.toString();
	}

	public java.lang.String toXmlString() {
		return _deviceProfileRule.toXmlString();
	}

	public void save()
		throws com.liferay.portal.kernel.exception.SystemException {
		_deviceProfileRule.save();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getRuleTypeSettingsProperties() {
		return _deviceProfileRule.getRuleTypeSettingsProperties();
	}

	public void setRuleTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties ruleTypeSettingsProperties) {
		_deviceProfileRule.setRuleTypeSettingsProperties(ruleTypeSettingsProperties);
	}

	public java.util.Collection<com.liferay.portal.mobile.model.DeviceProfileAction> getDeviceProfileActions()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRule.getDeviceProfileActions();
	}

	public DeviceProfileRule getWrappedDeviceProfileRule() {
		return _deviceProfileRule;
	}

	public void resetOriginalValues() {
		_deviceProfileRule.resetOriginalValues();
	}

	private DeviceProfileRule _deviceProfileRule;
}