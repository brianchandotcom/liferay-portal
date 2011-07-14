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
 * This class is a wrapper for {@link DeviceProfileAction}.
 * </p>
 *
 * @author    Edward C. Han
 * @see       DeviceProfileAction
 * @generated
 */
public class DeviceProfileActionWrapper implements DeviceProfileAction {
	public DeviceProfileActionWrapper(DeviceProfileAction deviceProfileAction) {
		_deviceProfileAction = deviceProfileAction;
	}

	public Class<?> getModelClass() {
		return DeviceProfileAction.class;
	}

	public String getModelClassName() {
		return DeviceProfileAction.class.getName();
	}

	/**
	* Returns the primary key of this device profile action.
	*
	* @return the primary key of this device profile action
	*/
	public long getPrimaryKey() {
		return _deviceProfileAction.getPrimaryKey();
	}

	/**
	* Sets the primary key of this device profile action.
	*
	* @param primaryKey the primary key of this device profile action
	*/
	public void setPrimaryKey(long primaryKey) {
		_deviceProfileAction.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this device profile action.
	*
	* @return the uuid of this device profile action
	*/
	public java.lang.String getUuid() {
		return _deviceProfileAction.getUuid();
	}

	/**
	* Sets the uuid of this device profile action.
	*
	* @param uuid the uuid of this device profile action
	*/
	public void setUuid(java.lang.String uuid) {
		_deviceProfileAction.setUuid(uuid);
	}

	/**
	* Returns the device profile action ID of this device profile action.
	*
	* @return the device profile action ID of this device profile action
	*/
	public long getDeviceProfileActionId() {
		return _deviceProfileAction.getDeviceProfileActionId();
	}

	/**
	* Sets the device profile action ID of this device profile action.
	*
	* @param deviceProfileActionId the device profile action ID of this device profile action
	*/
	public void setDeviceProfileActionId(long deviceProfileActionId) {
		_deviceProfileAction.setDeviceProfileActionId(deviceProfileActionId);
	}

	/**
	* Returns the device profile ID of this device profile action.
	*
	* @return the device profile ID of this device profile action
	*/
	public long getDeviceProfileId() {
		return _deviceProfileAction.getDeviceProfileId();
	}

	/**
	* Sets the device profile ID of this device profile action.
	*
	* @param deviceProfileId the device profile ID of this device profile action
	*/
	public void setDeviceProfileId(long deviceProfileId) {
		_deviceProfileAction.setDeviceProfileId(deviceProfileId);
	}

	/**
	* Returns the device profile rule ID of this device profile action.
	*
	* @return the device profile rule ID of this device profile action
	*/
	public long getDeviceProfileRuleId() {
		return _deviceProfileAction.getDeviceProfileRuleId();
	}

	/**
	* Sets the device profile rule ID of this device profile action.
	*
	* @param deviceProfileRuleId the device profile rule ID of this device profile action
	*/
	public void setDeviceProfileRuleId(long deviceProfileRuleId) {
		_deviceProfileAction.setDeviceProfileRuleId(deviceProfileRuleId);
	}

	/**
	* Returns the name of this device profile action.
	*
	* @return the name of this device profile action
	*/
	public java.lang.String getName() {
		return _deviceProfileAction.getName();
	}

	/**
	* Returns the localized name of this device profile action in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this device profile action
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _deviceProfileAction.getName(locale);
	}

	/**
	* Returns the localized name of this device profile action in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this device profile action. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _deviceProfileAction.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this device profile action in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this device profile action
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _deviceProfileAction.getName(languageId);
	}

	/**
	* Returns the localized name of this device profile action in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this device profile action
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _deviceProfileAction.getName(languageId, useDefault);
	}

	/**
	* Returns a map of the locales and localized names of this device profile action.
	*
	* @return the locales and localized names of this device profile action
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _deviceProfileAction.getNameMap();
	}

	/**
	* Sets the name of this device profile action.
	*
	* @param name the name of this device profile action
	*/
	public void setName(java.lang.String name) {
		_deviceProfileAction.setName(name);
	}

	/**
	* Sets the localized name of this device profile action in the language.
	*
	* @param name the localized name of this device profile action
	* @param locale the locale of the language
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_deviceProfileAction.setName(name, locale);
	}

	/**
	* Sets the localized name of this device profile action in the language, and sets the default locale.
	*
	* @param name the localized name of this device profile action
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_deviceProfileAction.setName(name, locale, defaultLocale);
	}

	/**
	* Sets the localized names of this device profile action from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this device profile action
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_deviceProfileAction.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this device profile action from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this device profile action
	* @param defaultLocale the default locale
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_deviceProfileAction.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this device profile action.
	*
	* @return the description of this device profile action
	*/
	public java.lang.String getDescription() {
		return _deviceProfileAction.getDescription();
	}

	/**
	* Returns the localized description of this device profile action in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this device profile action
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _deviceProfileAction.getDescription(locale);
	}

	/**
	* Returns the localized description of this device profile action in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this device profile action. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _deviceProfileAction.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this device profile action in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this device profile action
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _deviceProfileAction.getDescription(languageId);
	}

	/**
	* Returns the localized description of this device profile action in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this device profile action
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _deviceProfileAction.getDescription(languageId, useDefault);
	}

	/**
	* Returns a map of the locales and localized descriptions of this device profile action.
	*
	* @return the locales and localized descriptions of this device profile action
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _deviceProfileAction.getDescriptionMap();
	}

	/**
	* Sets the description of this device profile action.
	*
	* @param description the description of this device profile action
	*/
	public void setDescription(java.lang.String description) {
		_deviceProfileAction.setDescription(description);
	}

	/**
	* Sets the localized description of this device profile action in the language.
	*
	* @param description the localized description of this device profile action
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_deviceProfileAction.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this device profile action in the language, and sets the default locale.
	*
	* @param description the localized description of this device profile action
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_deviceProfileAction.setDescription(description, locale, defaultLocale);
	}

	/**
	* Sets the localized descriptions of this device profile action from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this device profile action
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_deviceProfileAction.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this device profile action from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this device profile action
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_deviceProfileAction.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the type of this device profile action.
	*
	* @return the type of this device profile action
	*/
	public java.lang.String getType() {
		return _deviceProfileAction.getType();
	}

	/**
	* Sets the type of this device profile action.
	*
	* @param type the type of this device profile action
	*/
	public void setType(java.lang.String type) {
		_deviceProfileAction.setType(type);
	}

	/**
	* Returns the type settings of this device profile action.
	*
	* @return the type settings of this device profile action
	*/
	public java.lang.String getTypeSettings() {
		return _deviceProfileAction.getTypeSettings();
	}

	/**
	* Sets the type settings of this device profile action.
	*
	* @param typeSettings the type settings of this device profile action
	*/
	public void setTypeSettings(java.lang.String typeSettings) {
		_deviceProfileAction.setTypeSettings(typeSettings);
	}

	public boolean isNew() {
		return _deviceProfileAction.isNew();
	}

	public void setNew(boolean n) {
		_deviceProfileAction.setNew(n);
	}

	public boolean isCachedModel() {
		return _deviceProfileAction.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_deviceProfileAction.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _deviceProfileAction.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_deviceProfileAction.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _deviceProfileAction.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_deviceProfileAction.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _deviceProfileAction.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_deviceProfileAction.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DeviceProfileActionWrapper((DeviceProfileAction)_deviceProfileAction.clone());
	}

	public int compareTo(
		com.liferay.portal.mobile.model.DeviceProfileAction deviceProfileAction) {
		return _deviceProfileAction.compareTo(deviceProfileAction);
	}

	@Override
	public int hashCode() {
		return _deviceProfileAction.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.mobile.model.DeviceProfileAction> toCacheModel() {
		return _deviceProfileAction.toCacheModel();
	}

	public com.liferay.portal.mobile.model.DeviceProfileAction toEscapedModel() {
		return new DeviceProfileActionWrapper(_deviceProfileAction.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _deviceProfileAction.toString();
	}

	public java.lang.String toXmlString() {
		return _deviceProfileAction.toXmlString();
	}

	public void save()
		throws com.liferay.portal.kernel.exception.SystemException {
		_deviceProfileAction.save();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _deviceProfileAction.getTypeSettingsProperties();
	}

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_deviceProfileAction.setTypeSettingsProperties(typeSettingsProperties);
	}

	public DeviceProfileAction getWrappedDeviceProfileAction() {
		return _deviceProfileAction;
	}

	public void resetOriginalValues() {
		_deviceProfileAction.resetOriginalValues();
	}

	private DeviceProfileAction _deviceProfileAction;
}