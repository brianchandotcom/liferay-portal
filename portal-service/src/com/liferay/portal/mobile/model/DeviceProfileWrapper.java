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
 * This class is a wrapper for {@link DeviceProfile}.
 * </p>
 *
 * @author    Edward C. Han
 * @see       DeviceProfile
 * @generated
 */
public class DeviceProfileWrapper implements DeviceProfile {
	public DeviceProfileWrapper(DeviceProfile deviceProfile) {
		_deviceProfile = deviceProfile;
	}

	public Class<?> getModelClass() {
		return DeviceProfile.class;
	}

	public String getModelClassName() {
		return DeviceProfile.class.getName();
	}

	/**
	* Returns the primary key of this device profile.
	*
	* @return the primary key of this device profile
	*/
	public long getPrimaryKey() {
		return _deviceProfile.getPrimaryKey();
	}

	/**
	* Sets the primary key of this device profile.
	*
	* @param primaryKey the primary key of this device profile
	*/
	public void setPrimaryKey(long primaryKey) {
		_deviceProfile.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this device profile.
	*
	* @return the uuid of this device profile
	*/
	public java.lang.String getUuid() {
		return _deviceProfile.getUuid();
	}

	/**
	* Sets the uuid of this device profile.
	*
	* @param uuid the uuid of this device profile
	*/
	public void setUuid(java.lang.String uuid) {
		_deviceProfile.setUuid(uuid);
	}

	/**
	* Returns the device profile ID of this device profile.
	*
	* @return the device profile ID of this device profile
	*/
	public long getDeviceProfileId() {
		return _deviceProfile.getDeviceProfileId();
	}

	/**
	* Sets the device profile ID of this device profile.
	*
	* @param deviceProfileId the device profile ID of this device profile
	*/
	public void setDeviceProfileId(long deviceProfileId) {
		_deviceProfile.setDeviceProfileId(deviceProfileId);
	}

	/**
	* Returns the name of this device profile.
	*
	* @return the name of this device profile
	*/
	public java.lang.String getName() {
		return _deviceProfile.getName();
	}

	/**
	* Returns the localized name of this device profile in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this device profile
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _deviceProfile.getName(locale);
	}

	/**
	* Returns the localized name of this device profile in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this device profile. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _deviceProfile.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this device profile in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this device profile
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _deviceProfile.getName(languageId);
	}

	/**
	* Returns the localized name of this device profile in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this device profile
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _deviceProfile.getName(languageId, useDefault);
	}

	/**
	* Returns a map of the locales and localized names of this device profile.
	*
	* @return the locales and localized names of this device profile
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _deviceProfile.getNameMap();
	}

	/**
	* Sets the name of this device profile.
	*
	* @param name the name of this device profile
	*/
	public void setName(java.lang.String name) {
		_deviceProfile.setName(name);
	}

	/**
	* Sets the localized name of this device profile in the language.
	*
	* @param name the localized name of this device profile
	* @param locale the locale of the language
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_deviceProfile.setName(name, locale);
	}

	/**
	* Sets the localized name of this device profile in the language, and sets the default locale.
	*
	* @param name the localized name of this device profile
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_deviceProfile.setName(name, locale, defaultLocale);
	}

	/**
	* Sets the localized names of this device profile from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this device profile
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_deviceProfile.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this device profile from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this device profile
	* @param defaultLocale the default locale
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_deviceProfile.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this device profile.
	*
	* @return the description of this device profile
	*/
	public java.lang.String getDescription() {
		return _deviceProfile.getDescription();
	}

	/**
	* Returns the localized description of this device profile in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this device profile
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _deviceProfile.getDescription(locale);
	}

	/**
	* Returns the localized description of this device profile in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this device profile. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _deviceProfile.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this device profile in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this device profile
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _deviceProfile.getDescription(languageId);
	}

	/**
	* Returns the localized description of this device profile in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this device profile
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _deviceProfile.getDescription(languageId, useDefault);
	}

	/**
	* Returns a map of the locales and localized descriptions of this device profile.
	*
	* @return the locales and localized descriptions of this device profile
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _deviceProfile.getDescriptionMap();
	}

	/**
	* Sets the description of this device profile.
	*
	* @param description the description of this device profile
	*/
	public void setDescription(java.lang.String description) {
		_deviceProfile.setDescription(description);
	}

	/**
	* Sets the localized description of this device profile in the language.
	*
	* @param description the localized description of this device profile
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_deviceProfile.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this device profile in the language, and sets the default locale.
	*
	* @param description the localized description of this device profile
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_deviceProfile.setDescription(description, locale, defaultLocale);
	}

	/**
	* Sets the localized descriptions of this device profile from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this device profile
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_deviceProfile.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this device profile from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this device profile
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_deviceProfile.setDescriptionMap(descriptionMap, defaultLocale);
	}

	public boolean isNew() {
		return _deviceProfile.isNew();
	}

	public void setNew(boolean n) {
		_deviceProfile.setNew(n);
	}

	public boolean isCachedModel() {
		return _deviceProfile.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_deviceProfile.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _deviceProfile.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_deviceProfile.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _deviceProfile.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_deviceProfile.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _deviceProfile.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_deviceProfile.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DeviceProfileWrapper((DeviceProfile)_deviceProfile.clone());
	}

	public int compareTo(
		com.liferay.portal.mobile.model.DeviceProfile deviceProfile) {
		return _deviceProfile.compareTo(deviceProfile);
	}

	@Override
	public int hashCode() {
		return _deviceProfile.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.mobile.model.DeviceProfile> toCacheModel() {
		return _deviceProfile.toCacheModel();
	}

	public com.liferay.portal.mobile.model.DeviceProfile toEscapedModel() {
		return new DeviceProfileWrapper(_deviceProfile.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _deviceProfile.toString();
	}

	public java.lang.String toXmlString() {
		return _deviceProfile.toXmlString();
	}

	public void save()
		throws com.liferay.portal.kernel.exception.SystemException {
		_deviceProfile.save();
	}

	public java.util.Collection<com.liferay.portal.mobile.model.DeviceProfileRule> getDeviceProfileRules()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfile.getDeviceProfileRules();
	}

	public DeviceProfile getWrappedDeviceProfile() {
		return _deviceProfile;
	}

	public void resetOriginalValues() {
		_deviceProfile.resetOriginalValues();
	}

	private DeviceProfile _deviceProfile;
}