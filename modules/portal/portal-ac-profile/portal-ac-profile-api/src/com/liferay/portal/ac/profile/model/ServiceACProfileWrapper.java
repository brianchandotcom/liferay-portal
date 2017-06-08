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

package com.liferay.portal.ac.profile.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ServiceACProfile}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceACProfile
 * @generated
 */
@ProviderType
public class ServiceACProfileWrapper implements ServiceACProfile,
	ModelWrapper<ServiceACProfile> {
	public ServiceACProfileWrapper(ServiceACProfile serviceACProfile) {
		_serviceACProfile = serviceACProfile;
	}

	@Override
	public Class<?> getModelClass() {
		return ServiceACProfile.class;
	}

	@Override
	public String getModelClassName() {
		return ServiceACProfile.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("serviceACProfileId", getServiceACProfileId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("allowedServices", getAllowedServices());
		attributes.put("name", getName());
		attributes.put("title", getTitle());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long serviceACProfileId = (Long)attributes.get("serviceACProfileId");

		if (serviceACProfileId != null) {
			setServiceACProfileId(serviceACProfileId);
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

		String allowedServices = (String)attributes.get("allowedServices");

		if (allowedServices != null) {
			setAllowedServices(allowedServices);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new ServiceACProfileWrapper((ServiceACProfile)_serviceACProfile.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile) {
		return _serviceACProfile.compareTo(serviceACProfile);
	}

	/**
	* Returns the allowed services of this service a c profile.
	*
	* @return the allowed services of this service a c profile
	*/
	@Override
	public java.lang.String getAllowedServices() {
		return _serviceACProfile.getAllowedServices();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _serviceACProfile.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this service a c profile.
	*
	* @return the company ID of this service a c profile
	*/
	@Override
	public long getCompanyId() {
		return _serviceACProfile.getCompanyId();
	}

	/**
	* Returns the create date of this service a c profile.
	*
	* @return the create date of this service a c profile
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _serviceACProfile.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _serviceACProfile.getDefaultLanguageId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _serviceACProfile.getExpandoBridge();
	}

	/**
	* Returns the modified date of this service a c profile.
	*
	* @return the modified date of this service a c profile
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _serviceACProfile.getModifiedDate();
	}

	/**
	* Returns the name of this service a c profile.
	*
	* @return the name of this service a c profile
	*/
	@Override
	public java.lang.String getName() {
		return _serviceACProfile.getName();
	}

	/**
	* Returns the primary key of this service a c profile.
	*
	* @return the primary key of this service a c profile
	*/
	@Override
	public long getPrimaryKey() {
		return _serviceACProfile.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _serviceACProfile.getPrimaryKeyObj();
	}

	/**
	* Returns the service a c profile ID of this service a c profile.
	*
	* @return the service a c profile ID of this service a c profile
	*/
	@Override
	public long getServiceACProfileId() {
		return _serviceACProfile.getServiceACProfileId();
	}

	/**
	* Returns the title of this service a c profile.
	*
	* @return the title of this service a c profile
	*/
	@Override
	public java.lang.String getTitle() {
		return _serviceACProfile.getTitle();
	}

	/**
	* Returns the localized title of this service a c profile in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this service a c profile
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _serviceACProfile.getTitle(languageId);
	}

	/**
	* Returns the localized title of this service a c profile in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this service a c profile
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _serviceACProfile.getTitle(languageId, useDefault);
	}

	/**
	* Returns the localized title of this service a c profile in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this service a c profile
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _serviceACProfile.getTitle(locale);
	}

	/**
	* Returns the localized title of this service a c profile in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this service a c profile. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _serviceACProfile.getTitle(locale, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _serviceACProfile.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _serviceACProfile.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this service a c profile.
	*
	* @return the locales and localized titles of this service a c profile
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _serviceACProfile.getTitleMap();
	}

	/**
	* Returns the user ID of this service a c profile.
	*
	* @return the user ID of this service a c profile
	*/
	@Override
	public long getUserId() {
		return _serviceACProfile.getUserId();
	}

	/**
	* Returns the user name of this service a c profile.
	*
	* @return the user name of this service a c profile
	*/
	@Override
	public java.lang.String getUserName() {
		return _serviceACProfile.getUserName();
	}

	/**
	* Returns the user uuid of this service a c profile.
	*
	* @return the user uuid of this service a c profile
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _serviceACProfile.getUserUuid();
	}

	/**
	* Returns the uuid of this service a c profile.
	*
	* @return the uuid of this service a c profile
	*/
	@Override
	public java.lang.String getUuid() {
		return _serviceACProfile.getUuid();
	}

	@Override
	public int hashCode() {
		return _serviceACProfile.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _serviceACProfile.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _serviceACProfile.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _serviceACProfile.isNew();
	}

	@Override
	public void persist() {
		_serviceACProfile.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_serviceACProfile.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_serviceACProfile.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets the allowed services of this service a c profile.
	*
	* @param allowedServices the allowed services of this service a c profile
	*/
	@Override
	public void setAllowedServices(java.lang.String allowedServices) {
		_serviceACProfile.setAllowedServices(allowedServices);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_serviceACProfile.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this service a c profile.
	*
	* @param companyId the company ID of this service a c profile
	*/
	@Override
	public void setCompanyId(long companyId) {
		_serviceACProfile.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this service a c profile.
	*
	* @param createDate the create date of this service a c profile
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_serviceACProfile.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_serviceACProfile.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_serviceACProfile.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_serviceACProfile.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this service a c profile.
	*
	* @param modifiedDate the modified date of this service a c profile
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_serviceACProfile.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this service a c profile.
	*
	* @param name the name of this service a c profile
	*/
	@Override
	public void setName(java.lang.String name) {
		_serviceACProfile.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_serviceACProfile.setNew(n);
	}

	/**
	* Sets the primary key of this service a c profile.
	*
	* @param primaryKey the primary key of this service a c profile
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_serviceACProfile.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_serviceACProfile.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the service a c profile ID of this service a c profile.
	*
	* @param serviceACProfileId the service a c profile ID of this service a c profile
	*/
	@Override
	public void setServiceACProfileId(long serviceACProfileId) {
		_serviceACProfile.setServiceACProfileId(serviceACProfileId);
	}

	/**
	* Sets the title of this service a c profile.
	*
	* @param title the title of this service a c profile
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_serviceACProfile.setTitle(title);
	}

	/**
	* Sets the localized title of this service a c profile in the language.
	*
	* @param title the localized title of this service a c profile
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_serviceACProfile.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this service a c profile in the language, and sets the default locale.
	*
	* @param title the localized title of this service a c profile
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_serviceACProfile.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_serviceACProfile.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this service a c profile from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this service a c profile
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_serviceACProfile.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this service a c profile from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this service a c profile
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_serviceACProfile.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the user ID of this service a c profile.
	*
	* @param userId the user ID of this service a c profile
	*/
	@Override
	public void setUserId(long userId) {
		_serviceACProfile.setUserId(userId);
	}

	/**
	* Sets the user name of this service a c profile.
	*
	* @param userName the user name of this service a c profile
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_serviceACProfile.setUserName(userName);
	}

	/**
	* Sets the user uuid of this service a c profile.
	*
	* @param userUuid the user uuid of this service a c profile
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_serviceACProfile.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this service a c profile.
	*
	* @param uuid the uuid of this service a c profile
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_serviceACProfile.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.ac.profile.model.ServiceACProfile> toCacheModel() {
		return _serviceACProfile.toCacheModel();
	}

	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile toEscapedModel() {
		return new ServiceACProfileWrapper(_serviceACProfile.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _serviceACProfile.toString();
	}

	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile toUnescapedModel() {
		return new ServiceACProfileWrapper(_serviceACProfile.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _serviceACProfile.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ServiceACProfileWrapper)) {
			return false;
		}

		ServiceACProfileWrapper serviceACProfileWrapper = (ServiceACProfileWrapper)obj;

		if (Validator.equals(_serviceACProfile,
					serviceACProfileWrapper._serviceACProfile)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _serviceACProfile.getStagedModelType();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public ServiceACProfile getWrappedServiceACProfile() {
		return _serviceACProfile;
	}

	@Override
	public ServiceACProfile getWrappedModel() {
		return _serviceACProfile;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _serviceACProfile.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _serviceACProfile.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_serviceACProfile.resetOriginalValues();
	}

	private final ServiceACProfile _serviceACProfile;
}